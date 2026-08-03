package com.example.tikito.adapters;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tikito.R;
import com.example.tikito.activities.EventActivity;
import com.example.tikito.activities.ShowActivity;
import com.example.tikito.constants.AppConstants;
import com.example.tikito.entities.Event;
import com.example.tikito.utils.API;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private Context context;
    private List<Event> eventList;

    public EventAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_event_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Event event = eventList.get(position);

        holder.txtEventName.setText(event.getEventName());
        holder.txtDescription.setText(event.getEventDescription());

        holder.txtDuration.setText(
                event.getEventDurationMin() + " mins"
        );

        if (event.getAgeRestriction() >= 18)
            holder.txtAge.setText("18+");
        else
            holder.txtAge.setText("All Ages");

        String imageUrl = AppConstants.BASE_URL + event.getPosterUrl().substring(1);

        Log.d("POSTER_URL", imageUrl);

        Glide.with(context)
                .load(imageUrl)
                .into(holder.imgPoster);
        holder.btnBook.setOnClickListener(v -> {
            Intent intent = new Intent(context, ShowActivity.class);
            intent.putExtra("eventId", event.getEventId());
            intent.putExtra("eventName", event.getEventName());
            intent.putExtra("ImageUrl", imageUrl);

            context.startActivity(intent);

        });



    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPoster;

        TextView txtEventName;
        TextView txtDescription;
        TextView txtDuration;
        TextView txtAge;
        Button btnBook;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.imgPoster);

            txtEventName = itemView.findViewById(R.id.txtEventName);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtDuration = itemView.findViewById(R.id.txtDuration);
            txtAge = itemView.findViewById(R.id.txtAge);
            btnBook=itemView.findViewById(R.id.btnBook);

        }
    }}