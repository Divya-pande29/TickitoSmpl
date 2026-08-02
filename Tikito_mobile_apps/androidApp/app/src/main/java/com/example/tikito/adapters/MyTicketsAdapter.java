package com.example.tikito.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tikito.R;
import com.example.tikito.entities.BookingHistory;
import com.example.tikito.entities.SeatItem;
import com.example.tikito.entities.Tickets;

import java.util.List;

public class MyTicketsAdapter extends RecyclerView.Adapter<MyTicketsAdapter.MyViewHolder>
{
    Context context;
    List<BookingHistory> ticketsList;
    OnTicketActionListener actionListener;
    OnTicketClickListener ticketListener;

    public MyTicketsAdapter(Context context, List<BookingHistory> ticketsList, OnTicketActionListener actionListener, OnTicketClickListener ticketListener)
    {
        this.context = context;
        this.ticketsList = ticketsList;
        this.actionListener = actionListener;
        this.ticketListener = ticketListener;
    }

    public void setTicketsList(List<BookingHistory> ticketsList)
    {
        this.ticketsList = ticketsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adaptor_mytickets, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        BookingHistory bookingHistory = ticketsList.get(position);
        holder.bookingId.setText("Booking ID: "+bookingHistory.getBookingId());
        holder.movie.setText(bookingHistory.getEventName());
        holder.venue.setText(bookingHistory.getVenueName());
        holder.date.setText(""+bookingHistory.getShowDate());

        List<String> seatNumbers = bookingHistory.getSeatNumbers();
        if (seatNumbers == null || seatNumbers.isEmpty())
        {
            holder.seats.setVisibility(View.GONE);
        }
        else
        {
            holder.seats.setVisibility(View.VISIBLE);
            holder.seats.setText(String.join(", ", seatNumbers));
        }
        holder.amount.setText(""+ bookingHistory.getTotalAmt());

        setStatus(holder, bookingHistory);

        if(actionListener != null)
        {
            holder.itemView.setOnLongClickListener(v ->
            {
                actionListener.onCancelClicked(bookingHistory);
                return true;
            });
        }

        holder.itemView.setOnClickListener(v ->
        {
            if (ticketListener != null) {
                ticketListener.onTicketClicked(bookingHistory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ticketsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView bookingId;
        TextView movie;
        TextView venue;
        TextView date;
        TextView seats;
        TextView amount;
        TextView payStatus;
        TextView bookingStatus;

        ImageView paymentImage;
        ImageView bookingImage;
        ImageView poster;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            bookingId = itemView.findViewById(R.id.bookingId);
            movie = itemView.findViewById(R.id.Movie);
            venue = itemView.findViewById(R.id.venue);
            date = itemView.findViewById(R.id.date);
            seats = itemView.findViewById(R.id.seats);
            amount = itemView.findViewById(R.id.amount);
            payStatus = itemView.findViewById(R.id.payStat);
            bookingStatus = itemView.findViewById(R.id.BookingStat);
            paymentImage = itemView.findViewById(R.id.paymentImage);
            bookingImage = itemView.findViewById(R.id.bookingImage);
        }
    }

    private void setStatus(MyViewHolder holder, BookingHistory bookingHistory)
    {
        // Booking Status
        String bookingStatus = bookingHistory.getBookingStatus();
        holder.bookingStatus.setText(bookingStatus);

        if ("SUCCESS".equalsIgnoreCase(bookingStatus))
        {
            holder.bookingStatus.setBackgroundResource(R.drawable.bg_available);
            holder.bookingImage.setImageResource(R.drawable.successful);
        }
        else if ("CANCELLED".equalsIgnoreCase(bookingStatus))
        {
            holder.bookingStatus.setBackgroundResource(R.drawable.bg_booked);
            holder.bookingImage.setImageResource(R.drawable.cancel);
        }
        else
        {
            holder.bookingStatus.setBackgroundResource(R.drawable.refund);
            holder.bookingImage.setImageResource(R.drawable.refunded); // or remove if you don't have one
        }

        // Payment Status
        String paymentStatus = bookingHistory.getPaymentStatus();
        holder.payStatus.setText(paymentStatus);

        if ("PAID".equalsIgnoreCase(paymentStatus))
        {
            holder.payStatus.setBackgroundResource(R.drawable.bg_available);
            holder.paymentImage.setImageResource(R.drawable.successful);
        }
        else if ("REFUNDED".equalsIgnoreCase(paymentStatus))
        {
            holder.payStatus.setBackgroundResource(R.drawable.refund);
            holder.paymentImage.setImageResource(R.drawable.refunded); // or cancel icon
        }
        else
        {
            holder.payStatus.setBackgroundResource(R.drawable.refund);
            holder.paymentImage.setImageResource(R.drawable.refunded);
        }
    }

    public interface OnTicketActionListener
    {
        void onCancelClicked(BookingHistory booking);
    }

    public interface  OnTicketClickListener
    {
        void onTicketClicked(BookingHistory history);
    }
}
