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

import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.MyViewHolder>
{
    Context context;
    List<DateItem> dateItemList;

    public DateAdapter(Context context, List<DateItem> dateItemList) {
        this.context = context;
        this.dateItemList = dateItemList;
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


        holder.itemView.setOnClickListener(v ->
        {
            Toast.makeText(context, d.getDate() + " selected", Toast.LENGTH_SHORT).show();
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
}
