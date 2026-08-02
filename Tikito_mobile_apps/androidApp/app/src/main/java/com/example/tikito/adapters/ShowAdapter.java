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
import com.example.tikito.entities.ShowTiming;
import com.example.tikito.entities.VenueShows;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ViewHolder> {

    private final Context context;
    private final List<VenueShows> venueList;

    public ShowAdapter(Context context, List<VenueShows> venueList) {
        this.context = context;
        this.venueList = venueList;
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

        for (ShowTiming show : venue.getShows()) {

            Button timingButton = new Button(context);

            timingButton.setText(show.getShowStartTime());

            timingButton.setTextColor(Color.BLACK);
            timingButton.setTextSize(14);

            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(Color.parseColor("#FFC107"));
            drawable.setCornerRadius(40);

            timingButton.setBackground(drawable);

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);

            params.setMargins(10,10,10,10);

            timingButton.setLayoutParams(params);

            timingButton.setOnClickListener(v -> {

                //Intent intent = new Intent(context, BookingActivity.class);

                //intent.putExtra("showId", show.getShowId());
                //intent.putExtra("venueId", venue.getVenueId());

                //context.startActivity(intent);
            });

            holder.layoutTimings.addView(timingButton);
        }
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