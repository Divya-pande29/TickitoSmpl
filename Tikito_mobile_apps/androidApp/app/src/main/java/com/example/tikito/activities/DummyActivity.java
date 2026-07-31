package com.example.tikito.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tikito.R;

public class DummyActivity extends AppCompatActivity {

    Button upcoming, history,booking;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        upcoming = findViewById(R.id.upcoming);
        history = findViewById(R.id.history);
        booking = findViewById(R.id.booking);

        upcoming.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, UpcomingTicketsActivity.class);
            startActivity(intent);
        });

        booking.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, BookSeatActivity.class);
            startActivity(intent);
        });

        history.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, TicketHistoryActivity.class);
            startActivity(intent);
        });


    }
}