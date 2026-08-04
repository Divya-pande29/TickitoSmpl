package com.example.tikito.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tikito.R;
import com.example.tikito.adapters.SeatAdapter;
import com.example.tikito.constants.AppConstants;
import com.example.tikito.entities.Event;
import com.example.tikito.entities.SeatItem;
import com.example.tikito.utils.API;
import com.example.tikito.utils.SessionManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.example.tikito.entities.Ticket;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookSeatActivity extends AppCompatActivity implements SeatAdapter.OnSeatSelectedListener {

    RecyclerView recyclerViewSeats;
    SeatAdapter seatAdapter;
    TextView txtNoOfSeats, txtSeatNos, txtMovieName, txtVenueNameAndAdr, txtDate, txtTime;
    Button confirm;
    private long venueId;
    private long selectedShowId;
    SessionManager manager;
    List<SeatItem> seats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_seat);

        //session manager for jwt token auth -> isko context pass karna hota hai
        manager = new SessionManager(this);

        //Views
        txtSeatNos = findViewById(R.id.txtSeatNos);
        txtNoOfSeats = findViewById(R.id.txtNoOfSeats);
        txtMovieName = findViewById(R.id.txtMovieName);
        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);
        txtVenueNameAndAdr = findViewById(R.id.txtVenueNameAndAdr);
        recyclerViewSeats = findViewById(R.id.recyclerViewSeats);
        confirm = findViewById(R.id.confirmBtn);

        //Adaptors for Seat
        seatAdapter = new SeatAdapter(this, seats, this);
        recyclerViewSeats.setAdapter(seatAdapter);
        recyclerViewSeats.setLayoutManager(new GridLayoutManager(this, 5));

        //Intent to get Data from ShowActivity
        Intent intent = getIntent();

        //data from Intent
        String eventName = intent.getStringExtra("eventName");
        String posterUrl = intent.getStringExtra("posterUrl");
        venueId = intent.getLongExtra("venueId",0);
        String venueName = intent.getStringExtra("venueName");
        String venueAddress = intent.getStringExtra("venueAddress");
        String showDate = intent.getStringExtra("showDate");
        selectedShowId = intent.getLongExtra("showId", 0);
        String showStartTime = intent.getStringExtra("showStartTime");

        //call to load Seat Layout
        loadSeatLayout(selectedShowId);

        //set Data in txt Views
        txtMovieName.setText(eventName);
        txtVenueNameAndAdr.setText(venueName + ", " + venueAddress);
        txtDate.setText(showDate);
        txtTime.setText(showStartTime);

        confirm.setOnClickListener(v ->
        {
            bookSeats();
            Toast.makeText(this, "Booking Confirmed", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onSeatSelectionChanged(List<SeatItem> selectedSeats)
    {
        txtNoOfSeats.setText(selectedSeats.size() + " seats selected");

        StringBuilder seatNums = new StringBuilder();
        for(SeatItem si : selectedSeats)
        {
            seatNums.append(si.getSeatNo()).append(", ");
        }
        if(seatNums.length() > 0)
        {
            seatNums.setLength(seatNums.length() - 2); // remove ","
        }
        txtSeatNos.setText(seatNums.toString());
    }

    private void loadSeatLayout(Long showId)
    {
        selectedShowId = showId;

        loadVenueSeats();
    }

    private void loadVenueSeats() {
        seats.clear();

        API.getApi(this).getVenueAPI().getVenueById(venueId)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        try {
                            JsonObject responseBody = response.body();

                            if (responseBody.get(AppConstants.RESPONSE_STATUS).getAsString().equals(AppConstants.SUCCESS_RESPONSE)) {
                                JsonObject venueObj = responseBody.getAsJsonObject(AppConstants.RESPONSE_DATA);

                                JsonArray seatArray = venueObj.getAsJsonArray("seatList");

                                for (JsonElement element : seatArray) {
                                    JsonObject seatObj = element.getAsJsonObject();

                                    SeatItem item = new SeatItem();

                                    item.setSeatId(seatObj.get("seatId").getAsLong());
                                    item.setSeatNo(seatObj.get("seatNo").getAsString());

                                    // initially every seat is available
                                    item.setBooked(false);
                                    item.setSelected(false);

                                    seats.add(item);
                                }

                                // NEXT STEP
                                loadAlreadyBookedSeats(selectedShowId);
                            }
                        } catch (Exception e) {
                            Log.e("BOOK", "Exception while parsing", e);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(BookSeatActivity.this,
                                "Unable to load seats",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadAlreadyBookedSeats(Long showId)
    {
        API.getApi(this)
                .getBookingAPI()
                .getAvailableSeats(showId)
                .enqueue(new Callback<JsonObject>()
                {
                    @Override
                    public void onResponse(Call<JsonObject> call,
                                           Response<JsonObject> response)
                    {
                        if(!response.isSuccessful())
                        {
                            Log.e("HTTP", "Code = " + response.code());

                            try
                            {
                                if(response.errorBody() != null)
                                {
                                    Log.e("HTTP", response.errorBody().string());
                                }
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                            return;
                        }

                        try
                        {
                            JsonObject body = response.body();

                            for(SeatItem seat : seats)
                            {
                                seat.setBooked(true);
                            }

                            JsonArray arr =
                                    body.getAsJsonArray(AppConstants.RESPONSE_DATA);

                            for(JsonElement element : arr)
                            {
                                JsonObject obj = element.getAsJsonObject();

                                long availableSeatId =
                                        obj.get("seatId").getAsLong();

                                for(SeatItem seat : seats)
                                {
                                    if(seat.getSeatId().equals(availableSeatId))
                                    {
                                        seat.setBooked(false);
                                        break;
                                    }
                                }
                            }

                            seatAdapter.setSeatItems(seats);
                        }
                        catch(Exception e)
                        {
                            Log.e("BOOK","Parsing error",e);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t)
                    {
                        Log.e("BOOK","Network Error",t);

                        Toast.makeText(BookSeatActivity.this,
                                "Cannot load seats",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void bookSeats() {
        List<Long> seatIds = new ArrayList<>();

        for (SeatItem seat : seats) {
            if (seat.isSelected()) {
                seatIds.add(seat.getSeatId());
            }
        }

        if (seatIds.isEmpty()) {
            Toast.makeText(this,
                    "Please select at least one seat",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Ticket ticket = new Ticket();
        ticket.setShowId(selectedShowId);
        ticket.setSeatIds(seatIds);

        API.getApi(this)
                .getBookingAPI()
                .bookTicket(ticket)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call,
                                           Response<JsonObject> response) {
                        if (!response.isSuccessful()) {
                            Log.e("HTTP", "Code = " + response.code());

                            try {
                                if (response.errorBody() != null) {
                                    Log.e("HTTP", response.errorBody().string());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            return;
                        }
                        Intent intent = new Intent(BookSeatActivity.this, ConfirmBookingActivity.class);

                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(BookSeatActivity.this,
                                "Booking Failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}