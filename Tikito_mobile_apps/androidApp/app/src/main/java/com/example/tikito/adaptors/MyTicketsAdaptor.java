package com.example.tikito.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tikito.R;
import com.example.tikito.entities.Tickets;

import java.util.List;

public class MyTicketsAdaptor extends RecyclerView.Adapter<MyTicketsAdaptor.MyViewHolder>
{
    Context context;
    List<Tickets> ticketsList;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.adaptor_mytickets, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return ticketsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
