package com.example.tikito.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tikito.R;
import com.example.tikito.adapters.SummaryFoodAdapter;
import com.example.tikito.entities.CreateOrderRequest;
import com.example.tikito.entities.CreateOrderResponse;
import com.example.tikito.entities.Food;
import com.example.tikito.entities.SeatItem;
import com.example.tikito.entities.Ticket;
import com.example.tikito.entities.VerifyPaymentRequest;
import com.google.android.material.button.MaterialButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.tikito.entities.ApiResponse;
import com.example.tikito.services.PaymentAPI;
import com.example.tikito.utils.API;
import com.google.gson.JsonObject;
import com.razorpay.PaymentResultWithDataListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookingSummaryActivity extends AppCompatActivity implements PaymentResultWithDataListener
{
    private long showId;
    private ArrayList<Long> seatIds;
    private ArrayList<Food> selectedFoods;
    private double ticketPrice;
    private double ticketTotal;
    private double foodTotal;
    private double grandTotal;
    private RecyclerView recyclerFoods;
    private TextView txtTicketTotal;
    private TextView txtFoodTotal;
    private TextView txtGrandTotal;
    private TextView txtMovieName;
    private TextView txtVenue;
    private TextView txtDateTime;
    private TextView txtSeats;
    private ArrayList<String> seatNumbers;
    MaterialButton btnPay;
    private PaymentAPI paymentAPI;
    String showDate, showTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_summary);

        paymentAPI = API.getApi(this).getPaymentAPI();

        Intent intent = getIntent();

        recyclerFoods = findViewById(R.id.recyclerFoods);
        txtTicketTotal = findViewById(R.id.txtTicketTotal);
        txtFoodTotal = findViewById(R.id.txtFoodTotal);
        txtGrandTotal = findViewById(R.id.txtGrandTotal);
        txtMovieName = findViewById(R.id.txtMovieName);
        txtVenue = findViewById(R.id.txtVenue);
        txtDateTime = findViewById(R.id.txtDateTime);
        txtSeats = findViewById(R.id.txtSeats);
        btnPay = findViewById(R.id.btnProceedPayment);

        showId = intent.getLongExtra("showId", 0);

        seatIds = (ArrayList<Long>) intent.getSerializableExtra("seatIds");

        selectedFoods = (ArrayList<Food>) intent.getSerializableExtra("selectedFoods");
        seatNumbers = intent.getStringArrayListExtra("seatNumbers");

        Checkout.preload(getApplicationContext());

        Log.d("SUMMARY", "Selected Foods Count = " +
                (selectedFoods == null ? 0 : selectedFoods.size()));

        ticketPrice = intent.getDoubleExtra("ticketPrice", 0);
        ticketTotal = intent.getDoubleExtra("ticketTotal", 0);
        foodTotal = intent.getDoubleExtra("foodTotal", 0);
        grandTotal = intent.getDoubleExtra("grandTotal", 0);
        String eventName = intent.getStringExtra("eventName");
        String venueName = intent.getStringExtra("venueName");
        showDate = intent.getStringExtra("showDate");
        showTime = intent.getStringExtra("showTime");

        Log.d("SUMMARY", "Show = " + showId);

        Log.d("SUMMARY", "Seats = " + seatIds);

        for (Food food : selectedFoods) {

            Log.d("SUMMARY",
                    food.getFoodName()
                            + " x "
                            + food.getQuantity());
        }

        Log.d("SUMMARY",
                "Ticket Total = ₹" + ticketTotal);

        Log.d("SUMMARY",
                "Food Total = ₹" + foodTotal);

        Log.d("SUMMARY",
                "Grand Total = ₹" + grandTotal);

        txtTicketTotal.setText("₹" + ticketTotal);

        txtFoodTotal.setText("₹" + foodTotal);

        txtGrandTotal.setText("₹" + grandTotal);

        txtSeats.setText(String.join(", ", seatNumbers));

        txtMovieName.setText(eventName);

        txtVenue.setText(venueName);

        txtDateTime.setText(showDate + " | " + showTime);

        recyclerFoods.setLayoutManager(new LinearLayoutManager(this));

        SummaryFoodAdapter adapter = new SummaryFoodAdapter(selectedFoods);

        recyclerFoods.setAdapter(adapter);

        btnPay.setOnClickListener(v -> {
            Log.d("PAYMENT", "Proceed To Pay Clicked");

            CreateOrderRequest request = new CreateOrderRequest();

            request.setAmount(grandTotal);
            request.setCurrency("INR");
            request.setReceipt("BOOK_" + System.currentTimeMillis());

            paymentAPI.createOrder(request).enqueue(new Callback<ApiResponse<CreateOrderResponse>>() {

                @Override
                public void onResponse(
                        Call<ApiResponse<CreateOrderResponse>> call,
                        Response<ApiResponse<CreateOrderResponse>> response) {

                    if (response.isSuccessful()
                            && response.body() != null
                            && response.body().getData() != null) {

                        CreateOrderResponse order = response.body().getData();
                        startPayment(order);

                        Log.d("SUMMARY", "Order Id = " + order.getOrderId());
                        Log.d("SUMMARY", "Amount = " + order.getAmount());
                        Log.d("SUMMARY", "Currency = " + order.getCurrency());
                        Log.d("SUMMARY", "Key = " + order.getKeyId());

                        // NEXT STEP:
                        // Open Razorpay Checkout here

                    } else {

                        Log.e("SUMMARY", "Create Order Failed");

                    }
                }

                @Override
                public void onFailure(
                        Call<ApiResponse<CreateOrderResponse>> call,
                        Throwable t) {

                    Log.e("SUMMARY", "Payment API Error", t);

                }
            });

        });
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentId, PaymentData paymentData) {

        Log.d("PAYMENT", "SUCCESS");
        Log.d("PAYMENT", razorpayPaymentId);

        Log.d("PAYMENT", "Payment Id = " + paymentData.getPaymentId());
        Log.d("PAYMENT", "Order Id = " + paymentData.getOrderId());
        Log.d("PAYMENT", "Signature = " + paymentData.getSignature());

        VerifyPaymentRequest dto = new VerifyPaymentRequest();

        dto.setRazorpayOrderId(paymentData.getOrderId());
        dto.setRazorpayPaymentId(paymentData.getPaymentId());
        dto.setRazorpaySignature(paymentData.getSignature());

        paymentAPI.verifyPayment(dto).enqueue(new Callback<ApiResponse<String>>() {

            @Override
            public void onResponse(Call<ApiResponse<String>> call,
                                   Response<ApiResponse<String>> response) {

                if(response.isSuccessful()
                        && response.body()!=null){

                    Log.d("PAYMENT","Payment Verified");

                    bookTicket();

                }else{

                    Log.e("PAYMENT","Verification Failed");

                }
            }

            @Override
            public void onFailure(Call<ApiResponse<String>> call,
                                  Throwable t) {

                Log.e("PAYMENT","Verification Error",t);

            }
        });
    }

    @Override
    public void onPaymentError(int code,
                               String description,
                               PaymentData paymentData) {

        Log.e("PAYMENT", "FAILED");
        Log.e("PAYMENT", description);

    }

    private void startPayment(CreateOrderResponse order) {

        Checkout checkout = new Checkout();

        checkout.setKeyID(order.getKeyId());

        try {

            JSONObject options = new JSONObject();

            options.put("name", "Tikito");
            options.put("description", "Movie Ticket Booking");
            options.put("order_id", order.getOrderId());
            options.put("currency", order.getCurrency());
            options.put("amount", order.getAmount());

            checkout.open(this, options);

        } catch (Exception e) {

            Log.e("PAYMENT", e.getMessage(), e);

        }
    }

    private void bookTicket() {

        Ticket ticket = new Ticket();

        ticket.setShowId(showId);
        ticket.setSeatIds(seatIds);

        API.getApi(this)
                .getBookingAPI()
                .bookTicket(ticket)
                .enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(Call<JsonObject> call,
                                           Response<JsonObject> response) {

                        if (response.isSuccessful()) {

                            Intent intent = new Intent(BookingSummaryActivity.this, TicketActivity.class);

                            intent.putExtra("showId", showId);
                            intent.putExtra("eventName", txtMovieName.getText().toString());
                            intent.putExtra("venueName", txtVenue.getText().toString());
                            intent.putExtra("showDate", intent.getStringExtra("showDate"));
                            intent.putExtra("showTime", intent.getStringExtra("showTime"));
                            intent.putExtra("ticketTotal", ticketTotal);
                            intent.putExtra("foodTotal", foodTotal);
                            intent.putExtra("grandTotal", grandTotal);
                            intent.putExtra("ticketPrice", ticketPrice);
                            intent.putExtra("seatIds", seatIds);
                            intent.putExtra("seatNumbers", seatNumbers);
                            intent.putExtra("selectedFoods", selectedFoods);
                            intent.putExtra("showDate", showDate);
                            intent.putExtra("showTime", showTime);

                            startActivity(intent);
                            finish();

                        } else {

                            Log.e("BOOKING",
                                    "Booking Failed " + response.code());

                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call,
                                          Throwable t) {

                        Toast.makeText(
                                BookingSummaryActivity.this,
                                "Booking Failed",
                                Toast.LENGTH_SHORT).show();

                    }
                });
    }
}