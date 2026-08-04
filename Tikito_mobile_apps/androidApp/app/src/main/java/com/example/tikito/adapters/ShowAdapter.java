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

import java.util.ArrayList;
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

            holder.imgFood.setVisibility(
                    venue.isAreFacilitiesAvailable()
                            ? View.VISIBLE
                            : View.GONE);

            holder.layoutTimings.removeAllViews();

            //Use filtered shows if available otherwise all shows
            List<ShowTiming> timings =
                    venue.getFilteredShows().isEmpty()
                            ? venue.getShows()
                            : venue.getFilteredShows();

            for (ShowTiming show : timings) {

                Button btn = new Button(context);

                btn.setText(show.getShowStartTime());

                btn.setTextSize(13);
                btn.setTextColor(Color.parseColor("#1A1A1A"));
                btn.setAllCaps(false);

                GradientDrawable drawable = new GradientDrawable();
                drawable.setColor(Color.parseColor("#F5C242"));
                drawable.setCornerRadius(60);
                drawable.setStroke(2, Color.parseColor("#D8A600"));

                btn.setBackground(drawable);

                LinearLayout.LayoutParams params =
                        new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);

                params.setMargins(10,10,10,10);

                btn.setLayoutParams(params);

                btn.setPadding(40,20,40,20);

//                btn.setOnClickListener(v -> {
//
//                    Intent intent =
//                            new Intent(context, BookingActivity.class);
//
//                   intent.putExtra("showId", show.getShowId());
//                    intent.putExtra("venueId", venue.getVenueId());
//                    intent.putExtra("price", show.getPrice());
//                    intent.putExtra("language", show.getLanguage());
//                   intent.putExtra("date", show.getShowDate());
//                    intent.putExtra("startTime", show.getShowStartTime());
//                    intent.putExtra("endTime", show.getShowEndTime());
//                    intent.putExtra("isAdult", show.isEighteenPlus());
//                    intent.putExtra("showDate",show.getShowDate());
//                    context.startActivity(intent);
//                });

                holder.layoutTimings.addView(btn);
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