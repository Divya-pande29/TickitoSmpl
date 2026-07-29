package com.example.tikito.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tikito.R;
import com.example.tikito.entities.BookingHistory;

import java.util.List;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.ViewHolder> {

    private final List<BookingHistory> historyList;

    public BookingHistoryAdapter(List<BookingHistory> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking_history, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        BookingHistory booking = historyList.get(position);

        holder.textMovieName.setText(booking.getMovieName());
        holder.textVenue.setText("Venue : " + booking.getVenueName());
        holder.textDate.setText("Date : " + booking.getShowDate());
        holder.textTime.setText("Time : " + booking.getShowTime());
        holder.textSeat.setText("Seat : " + booking.getSeatNumber());
        holder.textStatus.setText("Status : " + booking.getStatus());

    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textMovieName;
        TextView textVenue;
        TextView textDate;
        TextView textTime;
        TextView textSeat;
        TextView textStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textMovieName = itemView.findViewById(R.id.textMovieName);
            textVenue = itemView.findViewById(R.id.textVenue);
            textDate = itemView.findViewById(R.id.textDate);
            textTime = itemView.findViewById(R.id.textTime);
            textSeat = itemView.findViewById(R.id.textSeat);
            textStatus = itemView.findViewById(R.id.textStatus);
        }
    }
}