package com.example.tikito.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tikito.R;
import com.example.tikito.adapters.MyTicketsAdapter;
import com.example.tikito.constants.AppConstants;
import com.example.tikito.entities.BookingHistory;
import com.example.tikito.utils.API;
import com.example.tikito.utils.SessionManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketHistoryActivity extends AppCompatActivity implements MyTicketsAdapter.OnTicketClickListener
{

    RecyclerView history;
    MyTicketsAdapter historyAdaptor;
    List<BookingHistory> historyList;
    Toolbar ticketsToolbar;
    SessionManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);

        //load all views
        historyList = new ArrayList<>();
        ticketsToolbar = findViewById(R.id.ticketsToolbar);
        ticketsToolbar.setTitle("Booking History");
        history = findViewById(R.id.ticketsRecyclerView);
        historyAdaptor = new MyTicketsAdapter(this, historyList, null, this);
        history.setLayoutManager(new LinearLayoutManager(this));
        history.setAdapter(historyAdaptor);
        manager = new SessionManager(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBookings();
    }

    @Override
    public void onTicketClicked(BookingHistory booking)
    {
        Intent intent = new Intent(this, TicketDetailsActivity.class);
        intent.putExtra("ticket", booking);
        startActivity(intent);
    }

    private void loadBookings()
    {
        API.getApi(this).getBookingAPI().getMyBookings().enqueue(new Callback<JsonObject>()
        {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
            {
                Log.e("TEST", "onResponse()");
                Log.e("TEST", "HTTP = " + response.code());
                try
                {
                    if(response.isSuccessful())
                    {
                        JsonObject responseBody = response.body();
                        Log.e("STEP", "1");

                        if(responseBody == null)
                        {
                            Toast.makeText(TicketHistoryActivity.this, "Empty response", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Log.e("STEP", "2");
                        Log.e("STEP", responseBody.toString());
                        Log.e("RESPONSE", responseBody.toString());

                        Log.e("STATUS_KEY",
                                responseBody.get(AppConstants.RESPONSE_STATUS).toString());

                        Log.e("EXPECTED",
                                AppConstants.SUCCESS_RESPONSE);
                        if(responseBody.get(AppConstants.RESPONSE_STATUS).getAsString().equals(AppConstants.SUCCESS_RESPONSE))
                        {
                            Log.e("STEP", "3");
                            historyList.clear();
                            JsonArray jsonArray = responseBody.getAsJsonArray(AppConstants.RESPONSE_DATA);
                            Log.e("JSON", responseBody.toString());
                            Log.e("COUNT", "Bookings = " + jsonArray.size());

                            LocalDate today = LocalDate.now();
                            LocalTime now = LocalTime.now();

                            for(JsonElement element : jsonArray)
                            {
                                Log.e("STEP", "4");
                                Log.e("STEP", "Array size = " + jsonArray.size());
                                JsonObject obj = element.getAsJsonObject();
                                BookingHistory history = new BookingHistory();

                                history.setBookingId(obj.get("bookingId").getAsLong());
                                history.setShowId(obj.get("showId").getAsLong());

                                history.setEventName(obj.get("eventName").getAsString());
                                history.setVenueName(obj.get("venueName").getAsString());

                                history.setShowDate(LocalDate.parse(obj.get("showDate").getAsString()));
                                history.setShowStartTime(LocalTime.parse(obj.get("showStartTime").getAsString()));
                                history.setShowEndTime(LocalTime.parse(obj.get("showEndTime").getAsString()));

                                history.setTotalAmt(obj.get("totalAmt").getAsDouble());

                                List<String> seatNumbers = new ArrayList<>();
                                JsonArray seatsArray = obj.getAsJsonArray("seatNumbers");

                                for(JsonElement seat : seatsArray)
                                {
                                    seatNumbers.add(seat.getAsString());
                                }

                                history.setSeatNumbers(seatNumbers);

                                history.setPaymentStatus(obj.get("paymentStatus").getAsString());
                                history.setBookingStatus(obj.get("bookingStatus").getAsString());

                                // Filter Booking History
                                if (history.getBookingStatus().equalsIgnoreCase("CANCELLED"))
                                {
                                    historyList.add(history);
                                }
                                else if (history.getShowDate().isBefore(today))
                                {
                                    historyList.add(history);
                                }
                                else if (history.getShowDate().isEqual(today)
                                        && history.getShowEndTime().isBefore(now))
                                {
                                    historyList.add(history);
                                }
                            }

                            historyAdaptor.setTicketsList(historyList);
                            historyAdaptor.notifyDataSetChanged();
                            Log.e("SIZE", "Upcoming size = " + historyList.size());
                        }
                        else
                        {
                            Log.e("RESPONSE", responseBody.toString());
                            Toast.makeText(TicketHistoryActivity.this, "Unable to load bookings", Toast.LENGTH_SHORT).show();
                            Log.e("RESPONSE", responseBody.toString());

                        }
                    }
                    else
                    {
                        Toast.makeText(TicketHistoryActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e)
                {
                    Log.e("CRASH", "Exception while parsing", e);
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t)
            {
                Log.e("TEST", "onFailure", t);
            }
        });
    }
}