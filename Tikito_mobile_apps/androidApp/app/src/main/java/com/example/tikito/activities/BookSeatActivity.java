package com.example.tikito.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tikito.R;
import com.example.tikito.adapters.DateAdapter;
import com.example.tikito.adapters.SeatAdapter;
import com.example.tikito.adapters.TimeAdapter;
import com.example.tikito.entities.DateItem;
import com.example.tikito.entities.Event;
import com.example.tikito.entities.SeatItem;
import com.example.tikito.entities.Show;
import com.example.tikito.entities.TimeItem;
import com.example.tikito.entities.Venue;
import com.example.tikito.utils.API;
import com.example.tikito.utils.SessionManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookSeatActivity extends AppCompatActivity implements SeatAdapter.OnSeatSelectedListener,
                                                                   TimeAdapter.OnTimeClickListener,
                                                                   DateAdapter.OnDateClickListener
{
    RecyclerView recyclerViewDates, recyclerViewTimes, recyclerViewSeats;
    DateAdapter dateAdapter;
    TimeAdapter timeAdapter;
    SeatAdapter seatAdapter;
    TextView txtNoOfSeats, txtSeatNos, txtMovieName, txtVenueNameAndAdr;
    SessionManager manager = new SessionManager(this);
    List<TimeItem> times = new ArrayList<>();
    List<DateItem> dates = new ArrayList<>();
    List<Show> showList = new ArrayList<>();
    List<SeatItem> seats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_seat);

        //Views
        txtSeatNos = findViewById(R.id.txtSeatNos);
        txtNoOfSeats = findViewById(R.id.txtNoOfSeats);
        txtMovieName = findViewById(R.id.txtMovieName);
        txtVenueNameAndAdr = findViewById(R.id.txtVenueNameAndAdr);
        recyclerViewDates = findViewById(R.id.recyclerViewDates);
        recyclerViewTimes = findViewById(R.id.recyclerViewTimes);
        recyclerViewSeats = findViewById(R.id.recyclerViewSeats);

        //set layout for adaptors
        LinearLayoutManager layoutManagerDate = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManagerTime = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        //set Adapters
        dateAdapter = new DateAdapter(this, dates, this);
        recyclerViewDates.setAdapter(dateAdapter);
        recyclerViewDates.setLayoutManager(layoutManagerDate);

        timeAdapter = new TimeAdapter(this, times, this);
        recyclerViewTimes.setAdapter(timeAdapter);
        recyclerViewTimes.setLayoutManager(layoutManagerTime);

        seatAdapter = new SeatAdapter(this, seats, this);
        recyclerViewSeats.setAdapter(seatAdapter);
        recyclerViewSeats.setLayoutManager(new GridLayoutManager(this, 5));

        Event SelectedEvent = (Event) getIntent().getSerializableExtra("event");
        Show SelectedShow = (Show) getIntent().getSerializableExtra("show");
        Venue SelectedVenue = (Venue) getIntent().getSerializableExtra("venue");

        List<Show> allShows = new ArrayList<>();

        //load all shows through retrofit
        loadShows(SelectedEvent, allShows);

        txtMovieName.setText(SelectedEvent.getEventName());
        txtVenueNameAndAdr.setText(SelectedVenue.getName() + SelectedVenue.getAddress());
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

    @Override
    public void onTimeClicked(TimeItem item)
    {
//        // call booked seats API
//        Log.d("TIME", item.getTime());
//        Log.d("SHOW ID", String.valueOf(item.getShowId()));
    }

    @Override
    public void onDateClicked(DateItem dateItem)
    {
//        List<TimeItem> filteredTimes = new ArrayList<>();
//
//        switch (dateItem.getDate())
//        {
//            case "21 Jul":
//                filteredTimes.add(new TimeItem(101L, "12.30"));
//                filteredTimes.add(new TimeItem(102L, "11.09"));
//                break;
//
//            case "22 Jun":
//                filteredTimes.add(new TimeItem(201L, "04.45"));
//                filteredTimes.add(new TimeItem(202L, "06.30"));
//                break;
//
//            default:
//                filteredTimes.add(new TimeItem("09.00"));
//                break;
//        }
//
//        timeAdapter.setTimeItems(filteredTimes);
    }
    private void loadShows(Event event, List<Show> shows)
    {
        API.getApi().getShowAPI().findShowByEvent(event.getEventId())
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
                    {

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t)
                    {
                        Toast.makeText(BookSeatActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}