package com.example.tikito.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tikito.R;
import com.example.tikito.adapters.MyTicketsAdapter;
import com.example.tikito.constants.AppConstants;
import com.example.tikito.entities.ApiResponse;
import com.example.tikito.entities.BookingHistory;
import com.example.tikito.utils.API;
import com.example.tikito.utils.Constants;
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

public class UpcomingTicketsActivity extends AppCompatActivity implements MyTicketsAdapter.OnTicketActionListener, MyTicketsAdapter.OnTicketClickListener
{
    RecyclerView upcoming;
    MyTicketsAdapter upcomingAdaptor;
    List<BookingHistory> upcomingList;
    Toolbar upcomingToolbar;
    SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);

        //load all views
        upcomingList = new ArrayList<>();
        upcomingToolbar = findViewById(R.id.ticketsToolbar);
        upcomingToolbar.setTitle("Upcoming Movies");
        upcoming = findViewById(R.id.ticketsRecyclerView);
        upcomingAdaptor = new MyTicketsAdapter(this, upcomingList, this, this);
        upcoming.setLayoutManager(new LinearLayoutManager(this));
        upcoming.setAdapter(upcomingAdaptor);

        manager = new SessionManager(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBookings();
    }

    @Override
    public void onCancelClicked(BookingHistory booking)
    {
        new AlertDialog.Builder(this).setTitle("Cancel Ticket").setMessage("Are you sure you want to cancel this ticket?")
                .setPositiveButton("Yes", (dialog, which) ->
                {
                    cancelBooking(booking.getBookingId());
                })
                .setNegativeButton("No", (dialog, which) ->
                {
                    dialog.dismiss();
                }).show();
    }

    @Override
    public void onTicketClicked(BookingHistory history)
    {
        Intent intent = new Intent(this, TicketDetailsActivity.class);
        intent.putExtra("ticket", history);
        startActivity(intent);
    }

    private void cancelBooking(Long bookingId)
    {
        String token = "Bearer " + manager.getToken();
        String token1 = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbW9naEBnbWFpbC5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzg1NDIyNjgzLCJleHAiOjE3ODU0NTg2ODN9.qPpRjrJ-w_M2LpTzwSyOStBmwNAippnUi51-aezXgrY";
        API.getApi().getBookingAPI().cancelBooking(token, bookingId).enqueue(new Callback<JsonObject>()
        {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
            {
                JsonObject responseBody = response.body();
                if(responseBody.get(AppConstants.RESPONSE_STATUS).getAsString().equals(AppConstants.SUCCESS_RESPONSE))
                {
                    Toast.makeText(UpcomingTicketsActivity.this, "Ticked Cancelled", Toast.LENGTH_SHORT).show();

                    loadBookings();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t)
            {
                Toast.makeText(UpcomingTicketsActivity.this, "Failed to cancel Booking", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadBookings()
    {
        String token = "Bearer " + manager.getToken();

        String token2 = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbW9naEBnbWFpbC5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzg1NDIyNjgzLCJleHAiOjE3ODU0NTg2ODN9.qPpRjrJ-w_M2LpTzwSyOStBmwNAippnUi51-aezXgrY";

        API.getApi().getBookingAPI().getMyBookings(token).enqueue(new Callback<JsonObject>()
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
                            Toast.makeText(UpcomingTicketsActivity.this, "Empty response", Toast.LENGTH_SHORT).show();
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
                            upcomingList.clear();
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

                                // Filter Upcoming Tickets
                                if(!history.getBookingStatus().equalsIgnoreCase("CANCELLED"))
                                {
                                    if(history.getShowDate().isAfter(today))
                                    {
                                        upcomingList.add(history);
                                    }
                                    else if(history.getShowDate().isEqual(today) && history.getShowStartTime().isAfter(now))
                                    {
                                        upcomingList.add(history);
                                    }
                                }
                            }

                            upcomingAdaptor.setTicketsList(upcomingList);
                            upcomingAdaptor.notifyDataSetChanged();
                            Log.e("SIZE", "Upcoming size = " + upcomingList.size());
                        }
                        else
                        {
                            Log.e("RESPONSE", responseBody.toString());
                            Toast.makeText(UpcomingTicketsActivity.this, "Unable to load bookings", Toast.LENGTH_SHORT).show();
                            Log.e("RESPONSE", responseBody.toString());

                        }
                    }
                    else
                    {
                        Toast.makeText(UpcomingTicketsActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
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