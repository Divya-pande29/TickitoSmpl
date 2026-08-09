package com.example.tikito.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tikito.R;
import com.example.tikito.adapters.ShowAdapter;
import com.example.tikito.adapters.ShowDateAdapter;
import com.example.tikito.constants.AppConstants;
import com.example.tikito.entities.ApiResponse;
import com.example.tikito.entities.ShowDate;
import com.example.tikito.entities.ShowResponse;
import com.example.tikito.entities.ShowTiming;
import com.example.tikito.entities.VenueShows;
import com.example.tikito.services.ShowAPI;
import com.example.tikito.utils.API;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowActivity extends AppCompatActivity
        implements ShowDateAdapter.OnDateClickListener {

    private ImageView imgPoster;
    private TextView txtEventName;

    private RecyclerView recyclerDates;
    private RecyclerView recyclerVenues;

    private ShowDateAdapter dateAdapter;
    private ShowAdapter venueAdapter;

    private final List<String> dateList = new ArrayList<>();
    private final List<VenueShows> allVenues = new ArrayList<>();
    private final List<VenueShows> filteredVenues = new ArrayList<>();

    private Long eventId;
    private String eventName;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        imgPoster = findViewById(R.id.imgPoster);
        txtEventName = findViewById(R.id.txtEventName);

        recyclerDates = findViewById(R.id.recyclerDates);
        recyclerVenues = findViewById(R.id.recyclerVenues);

        recyclerDates.setLayoutManager(
                new LinearLayoutManager(this,
                        LinearLayoutManager.HORIZONTAL,
                        false));

        recyclerVenues.setLayoutManager(
                new LinearLayoutManager(this));

        dateAdapter = new ShowDateAdapter(
                this,
                dateList,
                this);

        venueAdapter = new ShowAdapter(
                this,
                filteredVenues);

        recyclerDates.setAdapter(dateAdapter);
        recyclerVenues.setAdapter(venueAdapter);

        eventId = getIntent().getLongExtra("eventId", 0);
        eventName = getIntent().getStringExtra("eventName");
        imageUrl = getIntent().getStringExtra("ImageUrl");

        txtEventName.setText(eventName);

        Glide.with(this)
                .load(imageUrl)
                .into(imgPoster);

        loadShows();
    }

    private void loadShows() {

        ShowAPI api = API.getApi(this).getShowAPI();

        api.getShowsByEvent(eventId)
                .enqueue(new Callback<ApiResponse<ShowResponse>>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse<ShowResponse>> call,
                            Response<ApiResponse<ShowResponse>> response) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && AppConstants.SUCCESS_RESPONSE.equals(response.body().getStatus())) {

                            ShowResponse showResponse = response.body().getData();

                            if (showResponse == null) {
                                Toast.makeText(
                                        ShowActivity.this,
                                        "No data found",
                                        Toast.LENGTH_SHORT
                                ).show();
                                return;
                            }

                            allVenues.clear();
                            allVenues.addAll(showResponse.getVenues());

                            dateList.clear();

                            for (ShowDate date : showResponse.getDates()) {
                                dateList.add(date.getShowDate());
                            }

                            dateAdapter.notifyDataSetChanged();

                            if (!dateList.isEmpty()) {
                                filterShows(dateList.get(0));
                            }

                        } else {

                            Toast.makeText(
                                    ShowActivity.this,
                                    "No shows available",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse<ShowResponse>> call,
                            Throwable t) {

                        Log.e("ShowActivity", "API Failure", t);

                        Toast.makeText(
                                ShowActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }

    /**
     * Filter venues according to selected date.
     */
    private void filterShows(String selectedDate) {

        filteredVenues.clear();

        for (VenueShows venue : allVenues) {

            List<ShowTiming> timings = new ArrayList<>();

            if (venue.getShows() == null)
                continue;

            for (ShowTiming show : venue.getShows()) {

                if (selectedDate.equals(show.getShowDate())) {
                    timings.add(show);
                }
            }

            if (!timings.isEmpty()) {

                VenueShows newVenue = new VenueShows();

                newVenue.setVenueId(venue.getVenueId());
                newVenue.setVenueName(venue.getVenueName());
                newVenue.setAddress(venue.getAddress());
                newVenue.setAreFacilitiesAvailable(
                        venue.isAreFacilitiesAvailable());

                newVenue.setShows(timings);

                filteredVenues.add(newVenue);
            }
        }

        venueAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDateSelected(String date) {
        filterShows(date);
    }
}