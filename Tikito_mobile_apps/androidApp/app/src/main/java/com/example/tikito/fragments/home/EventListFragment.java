package com.example.tikito.fragments.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tikito.R;
import com.example.tikito.adapters.EventTypeAdapter;
import com.example.tikito.entities.Event;
import com.example.tikito.entities.EventType;
import com.example.tikito.utils.API;
import com.example.tikito.utils.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListFragment extends Fragment {
    RecyclerView recyclerView;
    List<EventType> eventTypeList;


    EventTypeAdapter eventTypeAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        eventTypeList = new ArrayList<>();

        API.getApi()
                .getEventAPI()
                .getEventCountByType()
                .enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            Log.d("API", response.body().toString());
                            JsonObject responseBody = response.body();

                            if (responseBody.get(Constants.RESPONSE_STATUS)
                                    .getAsString()
                                    .equals(Constants.SUCCESS_RESPONSE)) {

                                JsonArray jsonArray =
                                        responseBody.getAsJsonArray(Constants.RESPONSE_DATA);

                                for (JsonElement element : jsonArray) {

                                    JsonObject jsonObject = element.getAsJsonObject();

                                    EventType eventType = new EventType();
                                    eventType.setEventType(jsonObject.get("eventType").getAsString());
                                    eventType.setCount(jsonObject.get("count").getAsLong());

                                    eventTypeList.add(eventType);
                                }

                                eventTypeAdapter.notifyDataSetChanged();

                            } else {
                                Toast.makeText(getContext(),
                                        "Failed to fetch events",
                                        Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getContext(),
                                    "Server Error",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eventTypeAdapter = new EventTypeAdapter(getContext(), eventTypeList);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(eventTypeAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
    }
}