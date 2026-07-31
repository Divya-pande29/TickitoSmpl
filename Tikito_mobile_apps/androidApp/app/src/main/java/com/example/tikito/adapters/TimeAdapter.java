package com.example.tikito.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tikito.R;
import com.example.tikito.entities.TimeItem;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.MyViewHolder>
{
    Context context;
    List<TimeItem> timeItems;
    public void setTimeItems(List<TimeItem> dateItems) {
        this.timeItems = dateItems;
        selectedPosition = RecyclerView.NO_POSITION;
        notifyDataSetChanged();
    }

    public void setSelectedPosition(int position)
    {
        selectedPosition = position;
        notifyDataSetChanged();
    }
    private OnTimeClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public TimeAdapter(Context context, List<TimeItem> timeItems, OnTimeClickListener listener) {
        this.context = context;
        this.timeItems = timeItems;
        this.listener = listener;
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
        if(position == selectedPosition)
        {
            holder.itemView.setBackgroundResource(R.drawable.bg_selected_datetime);
        }
        else
        {
            holder.itemView.setBackgroundResource(R.drawable.bg_normal_datetime);
        }

        holder.itemView.setOnClickListener(v ->
        {
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();

            if(previousPosition != RecyclerView.NO_POSITION)
                notifyItemChanged(previousPosition);

            notifyItemChanged(selectedPosition);

            listener.onTimeClicked(t);
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

    public interface OnTimeClickListener
    {
        void onTimeClicked(TimeItem timeItems);
    }
}
