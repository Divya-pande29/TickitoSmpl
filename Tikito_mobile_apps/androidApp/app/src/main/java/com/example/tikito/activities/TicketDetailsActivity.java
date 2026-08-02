package com.example.tikito.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tikito.R;
import com.example.tikito.entities.BookingHistory;

public class TicketDetailsActivity extends AppCompatActivity
{
    TextView txtMovieName, txtVenue, txtDate, txtTime, txtSeats, txtAmt, txtBookingId, txtBookingStat, txtPayStat;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        initialise();

        BookingHistory booking = (BookingHistory) getIntent().getSerializableExtra("ticket");

        if (booking == null) {
            finish();
            return;
        }

        txtMovieName.setText(booking.getEventName());

        txtVenue.setText(booking.getVenueName());

        txtDate.setText(booking.getShowDate().toString());

        txtTime.setText(booking.getShowStartTime() + " - " + booking.getShowEndTime());

        txtSeats.setText(TextUtils.join(", ", booking.getSeatNumbers()));

        txtAmt.setText("₹ " + booking.getTotalAmt());

        txtBookingId.setText(String.valueOf(booking.getBookingId()));

        setStatus(booking);
    }

    private void initialise()
    {
        txtMovieName = findViewById(R.id.txtMovieName);
        txtVenue = findViewById(R.id.txtVenue);
        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);
        txtSeats = findViewById(R.id.txtSeats);
        txtAmt = findViewById(R.id.txtAmount);
        txtBookingId = findViewById(R.id.txtBookingId);
        txtBookingStat = findViewById(R.id.txtBookingStatus);
        txtPayStat = findViewById(R.id.txtPaymentStatus);

    }

    private void setStatus(BookingHistory booking)
    {
        // Booking Status
        String bookingStatus = booking.getBookingStatus();
        txtBookingStat.setText(bookingStatus);

        if ("SUCCESS".equalsIgnoreCase(bookingStatus))
        {
            txtBookingStat.setBackgroundResource(R.drawable.bg_available);
        }
        else if ("CANCELLED".equalsIgnoreCase(bookingStatus))
        {
            txtBookingStat.setBackgroundResource(R.drawable.bg_booked);
        }
        else
        {
            txtBookingStat.setBackgroundResource(R.drawable.refund);
        }

        // Payment Status
        String paymentStatus = booking.getPaymentStatus();
        txtPayStat.setText(paymentStatus);

        if ("PAID".equalsIgnoreCase(paymentStatus))
        {
            txtPayStat.setBackgroundResource(R.drawable.bg_available);
        }
        else if ("REFUNDED".equalsIgnoreCase(paymentStatus))
        {
            txtPayStat.setBackgroundResource(R.drawable.refund);
        }
        else
        {
            txtPayStat.setBackgroundResource(R.drawable.refund);
        }
    }
}