package com.example.tikito.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tikito.R;
import com.example.tikito.adapters.ShowAdapter;
import com.example.tikito.constants.AppConstants;
import com.example.tikito.entities.ApiResponse;
import com.example.tikito.entities.Show;
import com.example.tikito.entities.VenueShows;
import com.example.tikito.utils.API;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowActivity extends AppCompatActivity {

    private ImageView imgPoster;
    private TextView txtEventName;
    private RecyclerView recyclerVenues;

    private ShowAdapter adapter;
    private List<VenueShows> showList;

    private Long eventId;
    private String eventName;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        imgPoster = findViewById(R.id.imgPoster);
        txtEventName = findViewById(R.id.txtEventName);
        recyclerVenues = findViewById(R.id.recyclerVenues);

        eventId = getIntent().getLongExtra("eventId",0);
        Log.d("SHOW_DEBUG", "EventId = " + eventId);
        Toast.makeText(this, "EventId = " + eventId, Toast.LENGTH_LONG).show();
        eventName = getIntent().getStringExtra("eventName");
        imageUrl = getIntent().getStringExtra("ImageUrl");


        showList = new ArrayList<>();
        adapter = new ShowAdapter(this, showList, eventId, eventName, imageUrl);

        recyclerVenues.setLayoutManager(new LinearLayoutManager(this));
        recyclerVenues.setAdapter(adapter);

        txtEventName.setText(eventName);

        Glide.with(this)
                .load(imageUrl)
                .into(imgPoster);

        loadShows();
    }

    private void loadShows(){

        API.getApi(this)
                .getShowAPI()
                .getShowsByEvent(eventId)
                .enqueue(new Callback<ApiResponse<List<VenueShows>>>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse<List<VenueShows>>> call,
                            Response<ApiResponse<List<VenueShows>>> response) {
                        Log.d("SHOW_DEBUG", "HTTP Code = " + response.code());
                        if(response.isSuccessful() && response.body()!=null
                                && response.body().getStatus().equals(AppConstants.SUCCESS_RESPONSE)){

                            List<VenueShows> data = response.body().getData();

                            for (VenueShows venue : data) {

                                Log.d("SHOW_DEBUG", "====================");
                                Log.d("SHOW_DEBUG", "Venue = " + venue.getVenueName());
                                Log.d("SHOW_DEBUG", "Address = " + venue.getAddress());
                                Log.d("SHOW_DEBUG", "Shows object = " + venue.getShows());

                                if (venue.getShows() != null) {
                                    Log.d("SHOW_DEBUG", "Shows size = " + venue.getShows().size());
                                } else {
                                    Log.d("SHOW_DEBUG", "Shows is NULL");
                                }
                            }

                            showList.clear();
                            showList.addAll(data);

                            adapter.notifyDataSetChanged();

                        }else{
                            Toast.makeText(ShowActivity.this,
                                    "No shows available",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ApiResponse<List<VenueShows>>> call,
                                          Throwable t) {

                        Toast.makeText(
                                ShowActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}