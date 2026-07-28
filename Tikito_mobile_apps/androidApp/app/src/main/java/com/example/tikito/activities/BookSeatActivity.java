package com.example.tikito.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tikito.R;
import com.example.tikito.adaptors.DateAdaptor;
import com.example.tikito.adaptors.SeatAdaptor;
import com.example.tikito.adaptors.TimeAdaptor;
import com.example.tikito.entities.DateItem;
import com.example.tikito.entities.SeatItem;
import com.example.tikito.entities.TimeItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BookSeatActivity extends AppCompatActivity implements SeatAdaptor.OnSeatSelectedListener
{
    RecyclerView recyclerViewDates, recyclerViewTimes, recyclerViewSeats;
    DateAdaptor dateAdaptor;
    TimeAdaptor timeAdaptor;
    SeatAdaptor seatAdaptor;
    TextView txtNoOfSeats, txtSeatNos, txtMovieName, txtVenueNameAndAdr;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_seat);

        txtSeatNos = findViewById(R.id.txtSeatNos);
        txtNoOfSeats = findViewById(R.id.txtNoOfSeats);
        txtMovieName = findViewById(R.id.txtMovieName);
        txtVenueNameAndAdr = findViewById(R.id.txtVenueNameAndAdr);

        txtMovieName.setText("Odyssey");
        txtVenueNameAndAdr.setText("INOX VJ Happiness");

        LinearLayoutManager layoutManagerDate = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManagerTime = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        List<DateItem> dates = new ArrayList<>();
        DateItem d1 = new DateItem("Fri", "21 Jul");
        DateItem d2 = new DateItem("Sat", "22 Jun");
        DateItem d3 = new DateItem("Sat", "22 Jun");
        DateItem d4 = new DateItem("Sat", "22 Jun");

        recyclerViewDates = findViewById(R.id.recyclerViewDates);
        dateAdaptor = new DateAdaptor(this, dates);

        dates.add(d1);
        dates.add(d2);
        dates.add(d3);
        dates.add(d4);

        recyclerViewDates.setAdapter(dateAdaptor);
        recyclerViewDates.setLayoutManager(layoutManagerDate);

        List<TimeItem> times = new ArrayList<>();
        TimeItem t1 = new TimeItem("12.30");
        TimeItem t2 = new TimeItem("11.09");
        TimeItem t3 = new TimeItem("04.45");

        recyclerViewTimes = findViewById(R.id.recyclerViewTimes);
        timeAdaptor = new TimeAdaptor(this, times);

        times.add(t1);
        times.add(t2);
        times.add(t3);

        recyclerViewTimes.setAdapter(timeAdaptor);
        recyclerViewTimes.setLayoutManager(layoutManagerTime);

        recyclerViewSeats = findViewById(R.id.recyclerViewSeats);

        SeatItem s1 = new SeatItem("a1");
        SeatItem s2 = new SeatItem("a2");
        SeatItem s3 = new SeatItem("a3");
        SeatItem s4 = new SeatItem("a4");
        SeatItem s5 = new SeatItem("a5");
        SeatItem s6 = new SeatItem("a1");
        SeatItem s7 = new SeatItem("a2");
        SeatItem s8 = new SeatItem("a3");
        SeatItem s9 = new SeatItem("a4");
        SeatItem s10 = new SeatItem("a5");
        List<SeatItem> seats = Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10);
        seatAdaptor = new SeatAdaptor(this, seats, this);
        recyclerViewSeats.setAdapter(seatAdaptor);
        recyclerViewSeats.setLayoutManager(new GridLayoutManager(this, 3));
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
}