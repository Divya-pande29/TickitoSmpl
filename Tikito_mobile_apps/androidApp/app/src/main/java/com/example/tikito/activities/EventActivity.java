package com.example.tikito.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tikito.R;
import com.example.tikito.adapters.EventAdapter;
import com.example.tikito.entities.ApiResponse;
import com.example.tikito.entities.Event;
import com.example.tikito.utils.API;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private List<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        recyclerView = findViewById(R.id.recyclerViewEvents);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventList = new ArrayList<>();
        adapter = new EventAdapter(this, eventList);

        recyclerView.setAdapter(adapter);

        String eventType = getIntent().getStringExtra("eventType");

        if (eventType == null || eventType.isEmpty()) {
            Toast.makeText(this, "Invalid Event Type", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadEvents(eventType);
    }

    private void loadEvents(String eventType) {

        API.getApi()
                .getEventAPI()
                .getEventsByType(eventType)
                .enqueue(new Callback<ApiResponse<List<Event>>>() {

                    @Override
                    public void onResponse(@NonNull Call<ApiResponse<List<Event>>> call,
                                           @NonNull Response<ApiResponse<List<Event>>> response) {

                        if (response.isSuccessful() && response.body() != null) {

                            ApiResponse<List<Event>> apiResponse = response.body();

                            if ("success".equalsIgnoreCase(apiResponse.getStatus())) {

                                eventList.clear();

                                if (apiResponse.getData() != null) {
                                    eventList.addAll(apiResponse.getData());
                                }

                                adapter.notifyDataSetChanged();

                            } else {

                                Toast.makeText(EventActivity.this,
                                        "No events found",
                                        Toast.LENGTH_SHORT).show();
                            }

                        } else {

                            Toast.makeText(EventActivity.this,
                                    "Server Error",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiResponse<List<Event>>> call,
                                          @NonNull Throwable t) {

                        Toast.makeText(EventActivity.this,
                                "Connection Failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}