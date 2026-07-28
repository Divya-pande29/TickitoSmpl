package com.example.tikito.adaptors;

import android.content.Context;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tikito.R;
import com.example.tikito.entities.TimeItem;

import java.util.List;

public class TimeAdaptor extends RecyclerView.Adapter<TimeAdaptor.MyViewHolder>
{
    Context context;
    List<TimeItem> timeItems;

    public TimeAdaptor(Context context, List<TimeItem> timeItems) {
        this.context = context;
        this.timeItems = timeItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.adaptor_time, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        TimeItem t = timeItems.get(position);

        holder.txtTime.setText(t.getTime());

        holder.itemView.setOnClickListener(v ->
        {
            Toast.makeText(context, t.getTime() + " selected", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return timeItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txtTime);
        }
    }
}
