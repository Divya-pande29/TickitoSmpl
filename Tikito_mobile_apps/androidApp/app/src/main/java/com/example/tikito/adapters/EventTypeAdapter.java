package com.example.tikito.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tikito.R;
import com.example.tikito.activities.EventActivity;
import com.example.tikito.entities.EventType;
import com.example.tikito.fragments.home.EventListFragment;

import java.util.List;

public class EventTypeAdapter extends RecyclerView.Adapter<EventTypeAdapter.MyViewHolder> {
    Context context;
    List<EventType> eventTypeList;

    public EventTypeAdapter(Context context, List<EventType> eventTypeList) {
        this.context = context;
        this.eventTypeList = eventTypeList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_event, parent, false)
        );  }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
     EventType eventType=eventTypeList.get(position);
        holder.eventType.setText(eventType.getEventType());
        holder.count.setText("Live("+eventType.getCount()+")");
        holder.count.setOnClickListener(v->{
            Intent intent = new Intent(context, EventActivity.class);
            intent.putExtra("eventType", eventType.getEventType());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return eventTypeList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView eventType;
        Button count;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            eventType = itemView.findViewById(R.id.eventType);
            count = itemView.findViewById(R.id.count);
        }
    }
}
