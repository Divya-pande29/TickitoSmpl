package com.example.tikito.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tikito.R;
import com.example.tikito.entities.DateItem;
import com.example.tikito.entities.TimeItem;

import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.MyViewHolder>
{
    Context context;
    List<DateItem> dateItemList;

    public void setDateItems(List<DateItem> dateItems) {
        this.dateItemList = dateItems;
        selectedPosition = RecyclerView.NO_POSITION;
        notifyDataSetChanged();
    }
    OnDateClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;


    public DateAdapter(Context context, List<DateItem> dateItemList, OnDateClickListener listener) {
        this.context = context;
        this.dateItemList = dateItemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.adaptor_date, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        DateItem  d = dateItemList.get(position);

        holder.txtDate.setText(d.getDate());

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

            listener.onDateClicked(d);
        });
    }

    @Override
    public int getItemCount() {
        return dateItemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }

    public interface OnDateClickListener
    {
        void onDateClicked(DateItem dateItem);
    }
}
