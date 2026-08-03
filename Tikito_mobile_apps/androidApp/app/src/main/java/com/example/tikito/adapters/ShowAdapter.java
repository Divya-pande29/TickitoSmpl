package com.example.tikito.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tikito.R;
//import com.example.tikito.activities.BookingActivity;
import com.example.tikito.activities.BookSeatActivity;
import com.example.tikito.entities.ShowTiming;
import com.example.tikito.entities.VenueShows;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ViewHolder> {

    private final Context context;
    private final List<VenueShows> venueList;
    private Long eventId;
    private String eventName;
    private String imgUrl;

    public ShowAdapter(Context context, List<VenueShows> venueList, Long eventId, String eventName, String imgUrl)
    {
        this.context = context;
        this.venueList = venueList;
        this.eventId = eventId;
        this.eventName = eventName;
        this.imgUrl = imgUrl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_venue, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        VenueShows venue = venueList.get(position);

        holder.txtVenueName.setText(venue.getVenueName());
        holder.txtAddress.setText(venue.getAddress());

        if (venue.isAreFacilitiesAvailable()) {
            holder.imgFood.setVisibility(View.VISIBLE);
        } else {
            holder.imgFood.setVisibility(View.GONE);
        }

        holder.layoutTimings.removeAllViews();

        // Create single Book Seats button
        Button bookButton = new Button(context);

        bookButton.setText("Book Seats");
        bookButton.setTextColor(Color.BLACK);
        bookButton.setTextSize(16);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor("#FFC107"));
        drawable.setCornerRadius(40);

        bookButton.setBackground(drawable);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(10, 10, 10, 10);
        bookButton.setLayoutParams(params);

        bookButton.setOnClickListener(v -> {

            Intent intent = new Intent(context, BookSeatActivity.class);

            intent.putExtra("eventId", eventId);
            intent.putExtra("eventName", eventName);
            intent.putExtra("posterUrl", imgUrl);

            intent.putExtra("venueId", venue.getVenueId());
            intent.putExtra("venueName", venue.getVenueName());
            intent.putExtra("venueAddress", venue.getAddress());

            context.startActivity(intent);
        });

        holder.layoutTimings.addView(bookButton);
    }

    @Override
    public int getItemCount() {
        return venueList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView txtVenueName;
        MaterialTextView txtAddress;
        ImageView imgFood;
        FlexboxLayout layoutTimings;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtVenueName = itemView.findViewById(R.id.txtVenueName);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            imgFood = itemView.findViewById(R.id.imgFood);
            layoutTimings = itemView.findViewById(R.id.layoutTimings);
        }
    }
}