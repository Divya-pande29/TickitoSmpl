package com.example.tikito.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tikito.R;
import com.example.tikito.adapters.FoodAdapter;
import com.example.tikito.entities.ApiResponse;
import com.example.tikito.entities.CreateOrderRequest;
import com.example.tikito.entities.CreateOrderResponse;
import com.example.tikito.entities.Food;
import com.example.tikito.services.FoodAPI;
import com.example.tikito.services.PaymentAPI;
import com.example.tikito.utils.API;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodActivity extends AppCompatActivity {

    private static final String TAG = "FOOD_API";
    private RecyclerView foodRecyclerView;
    private FoodAdapter foodAdapter;
    private MaterialButton confirmBtn;
    private long showId;
    private ArrayList<Long> seatIds;
    private double ticketPrice;
    private FoodAPI foodAPI;
    private ArrayList<String> seatNumbers;
    private PaymentAPI paymentAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        confirmBtn = findViewById(R.id.confirmBtn);
        foodRecyclerView = findViewById(R.id.foodRecyclerView);

        Intent intent = getIntent();

        showId = intent.getLongExtra("showId", 0);
        seatIds = (ArrayList<Long>) intent.getSerializableExtra("seatIds");
        seatNumbers = intent.getStringArrayListExtra("seatNumbers");
        String eventName = intent.getStringExtra("eventName");
        String venueName = intent.getStringExtra("venueName");
        String showDate = intent.getStringExtra("showDate");
        String showTime = intent.getStringExtra("showTime");
        ticketPrice = intent.getDoubleExtra("price", 0);

        Log.d(TAG, "ShowId = " + showId);
        Log.d(TAG, "SeatIds = " + seatIds);
        Log.d(TAG, "Ticket Price = ₹" + ticketPrice);

        foodRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodRecyclerView.setHasFixedSize(true);

        foodAPI = API.getApi(this).getFoodAPI();
        paymentAPI = API.getApi(this).getPaymentAPI();

        loadFoods();

        confirmBtn.setOnClickListener(v -> {

            List<Food> selectedFoods = foodAdapter.getSelectedFoods();

            double foodTotal = 0;

            Log.d(TAG, "---------------------------");
            Log.d(TAG, "Selected Foods");

            for (Food food : selectedFoods) {

                double amount = food.getPrice() * food.getQuantity();

                foodTotal += amount;

                Log.d(TAG,
                        food.getFoodName()
                                + " x "
                                + food.getQuantity()
                                + " = ₹"
                                + amount);
            }

            double ticketTotal = ticketPrice * seatIds.size();

            double grandTotal = ticketTotal + foodTotal;

            Log.d(TAG, "Ticket Price = ₹" + ticketPrice);
            Log.d(TAG, "Seats Selected = " + seatIds.size());
            Log.d(TAG, "Ticket Total = ₹" + ticketTotal);
            Log.d(TAG, "Food Total = ₹" + foodTotal);
            Log.d(TAG, "Grand Total = ₹" + grandTotal);

            Intent summaryIntent = new Intent(FoodActivity.this, BookingSummaryActivity.class);

            summaryIntent.putExtra("showId", showId);
            summaryIntent.putExtra("seatIds", seatIds);

            summaryIntent.putExtra("selectedFoods", new ArrayList<>(selectedFoods));

            summaryIntent.putExtra("ticketPrice", ticketPrice);
            summaryIntent.putExtra("ticketTotal", ticketTotal);
            summaryIntent.putExtra("foodTotal", foodTotal);
            summaryIntent.putExtra("grandTotal", grandTotal);
            summaryIntent.putStringArrayListExtra("seatNumbers", seatNumbers);
            summaryIntent.putExtra("eventName", eventName);
            summaryIntent.putExtra("venueName", venueName);
            summaryIntent.putExtra("showDate", showDate);
            summaryIntent.putExtra("showTime", showTime);

            startActivity(summaryIntent);

        });

    }

    private void loadFoods() {

        foodAPI.getAvailableFoods().enqueue(new Callback<ApiResponse<List<Food>>>() {

            @Override
            public void onResponse(Call<ApiResponse<List<Food>>> call,
                                   Response<ApiResponse<List<Food>>> response) {

                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().getData() != null) {

                    List<Food> foods = response.body().getData();

                    Log.d(TAG, "Total Foods : " + foods.size());

                    foodAdapter = new FoodAdapter(foods);

                    foodRecyclerView.setAdapter(foodAdapter);

                } else {

                    Log.e(TAG, "Response Failed");

                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Food>>> call,
                                  Throwable t) {

                Log.e(TAG, "API Error : " + t.getMessage());

            }
        });

    }
}