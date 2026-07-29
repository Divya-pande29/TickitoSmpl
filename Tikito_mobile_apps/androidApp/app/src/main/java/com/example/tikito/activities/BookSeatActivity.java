package com.example.tikito.activities;

import android.os.Bundle;
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
import com.example.tikito.entities.SeatItem;
import com.example.tikito.entities.TimeItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookSeatActivity extends AppCompatActivity implements SeatAdapter.OnSeatSelectedListener
{
    RecyclerView recyclerViewDates, recyclerViewTimes, recyclerViewSeats;
    DateAdapter dateAdapter;
    TimeAdapter timeAdapter;
    SeatAdapter seatAdapter;
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
        dateAdapter = new DateAdapter(this, dates);

        dates.add(d1);
        dates.add(d2);
        dates.add(d3);
        dates.add(d4);

        recyclerViewDates.setAdapter(dateAdapter);
        recyclerViewDates.setLayoutManager(layoutManagerDate);

        List<TimeItem> times = new ArrayList<>();
        TimeItem t1 = new TimeItem("12.30");
        TimeItem t2 = new TimeItem("11.09");
        TimeItem t3 = new TimeItem("04.45");

        recyclerViewTimes = findViewById(R.id.recyclerViewTimes);
        timeAdapter = new TimeAdapter(this, times);

        times.add(t1);
        times.add(t2);
        times.add(t3);

        recyclerViewTimes.setAdapter(timeAdapter);
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
        seatAdapter = new SeatAdapter(this, seats, this);
        recyclerViewSeats.setAdapter(seatAdapter);
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

    public static class ConcertActivity extends AppCompatActivity {
        private ImageView btnBack, btnSearch;
        private Button btnPop, btnRock, btnEdm, btnClassical;
        private LinearLayout concertArijit, concertSunburn, concertColdplay, concertRahman, artistAmit, artistSanam;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_concert);
            btnBack = findViewById(R.id.btnBack);
            btnSearch = findViewById(R.id.btnSearch);

            btnPop = findViewById(R.id.btnPop);
            btnRock = findViewById(R.id.btnRock);
            btnEdm = findViewById(R.id.btnEdm);
            btnClassical = findViewById(R.id.btnClassical);

            concertArijit = findViewById(R.id.concertArijit);
            concertSunburn = findViewById(R.id.concertSunburn);
            concertColdplay = findViewById(R.id.concertColdplay);
            concertRahman = findViewById(R.id.concertRahman);
            artistAmit = findViewById(R.id.artistAmit);
            artistSanam = findViewById(R.id.artistSanam);

            // --- CLICK LISTENERS ---

            // Back Button
            if (btnBack != null) {
                btnBack.setOnClickListener(v -> finish());
            }

            // Search Button
            if (btnSearch != null) {
                btnSearch.setOnClickListener(v ->
                        Toast.makeText(this, "Search Concerts Clicked", Toast.LENGTH_SHORT).show()
                );
            }

            // Category Buttons
            if (btnPop != null) {
                btnPop.setOnClickListener(v ->
                        Toast.makeText(this, "Filter: Pop", Toast.LENGTH_SHORT).show()
                );
            }

            if (btnRock != null) {
                btnRock.setOnClickListener(v ->
                        Toast.makeText(this, "Filter: Rock", Toast.LENGTH_SHORT).show()
                );
            }

            if (btnEdm != null) {
                btnEdm.setOnClickListener(v ->
                        Toast.makeText(this, "Filter: EDM", Toast.LENGTH_SHORT).show()
                );
            }

            if (btnClassical != null) {
                btnClassical.setOnClickListener(v ->
                        Toast.makeText(this, "Filter: Classical", Toast.LENGTH_SHORT).show()
                );
            }

            // Concert Clicks
            if (concertArijit != null) {
                concertArijit.setOnClickListener(v ->
                        Toast.makeText(this, "Arijit Singh Live Clicked", Toast.LENGTH_SHORT).show()
                );
            }

            if (concertSunburn != null) {
                concertSunburn.setOnClickListener(v ->
                        Toast.makeText(this, "Sunburn Arena Clicked", Toast.LENGTH_SHORT).show()
                );
            }

            if (concertColdplay != null) {
                concertColdplay.setOnClickListener(v ->
                        Toast.makeText(this, "Coldplay India Tour Clicked", Toast.LENGTH_SHORT).show()
                );
            }

            if (concertRahman != null) {
                concertRahman.setOnClickListener(v ->
                        Toast.makeText(this, "AR Rahman Live Clicked", Toast.LENGTH_SHORT).show()
                );
            }

            if (artistAmit != null) {
                artistAmit.setOnClickListener(v ->
                        Toast.makeText(this, "Amit Trivedi Clicked", Toast.LENGTH_SHORT).show()
                );
            }

            if (artistSanam != null) {
                artistSanam.setOnClickListener(v ->
                        Toast.makeText(this, "Sanam Band Clicked", Toast.LENGTH_SHORT).show()
                );
            }
        }
    }
}