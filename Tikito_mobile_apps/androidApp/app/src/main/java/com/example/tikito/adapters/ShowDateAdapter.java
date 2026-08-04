package com.example.tikito.adapters;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tikito.R;
import com.example.tikito.activities.ShowActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tikito.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ShowDateAdapter extends RecyclerView.Adapter<ShowDateAdapter.ShowDateViewHolder> {

    public interface OnDateClickListener {
        void onDateSelected(String date);
    }

    private final Context context;
    private final List<String> dateList;
    private final OnDateClickListener listener;

    private int selectedPosition = 0;

    public ShowDateAdapter(Context context,
                           List<String> dateList,
                           OnDateClickListener listener) {

        this.context = context;
        this.dateList = dateList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShowDateViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                 int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_date, parent, false);

        return new ShowDateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowDateViewHolder holder,
                                 int position) {

        String dateString = dateList.get(position);

        LocalDate date = LocalDate.parse(dateString);

        holder.txtDay.setText(
                date.format(DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH))
        );

        holder.txtDate.setText(
                date.format(DateTimeFormatter.ofPattern("dd"))
        );

        holder.txtMonth.setText(
                date.format(DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH))
                        .toUpperCase()
        );

        if (position == selectedPosition) {

            holder.container.setBackgroundResource(R.drawable.bg_selected_date);

            holder.txtDay.setTextColor(Color.WHITE);
            holder.txtDate.setTextColor(Color.WHITE);
            holder.txtMonth.setTextColor(Color.WHITE);

        } else {

            holder.container.setBackgroundResource(R.drawable.bg_unselected_date);

            holder.txtDay.setTextColor(Color.parseColor("#BDBDBD"));
            holder.txtDate.setTextColor(Color.WHITE);
            holder.txtMonth.setTextColor(Color.parseColor("#BDBDBD"));
        }

        holder.container.setOnClickListener(v -> {

            int previous = selectedPosition;
            selectedPosition = holder.getAdapterPosition();

            notifyItemChanged(previous);
            notifyItemChanged(selectedPosition);

            listener.onDateSelected(dateString);

        });
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    static class ShowDateViewHolder extends RecyclerView.ViewHolder {

        LinearLayout container;
        TextView txtDay;
        TextView txtDate;
        TextView txtMonth;

        public ShowDateViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.dateContainer);
            txtDay = itemView.findViewById(R.id.txtDay);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtMonth = itemView.findViewById(R.id.txtMonth);
        }
    }
}