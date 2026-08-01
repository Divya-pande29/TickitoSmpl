//package com.example.tikito.activities;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.tikito.R;
//import com.example.tikito.adapters.DateAdapter;
//import com.example.tikito.adapters.SeatAdapter;
//import com.example.tikito.adapters.TimeAdapter;
//import com.example.tikito.constants.AppConstants;
//import com.example.tikito.entities.DateItem;
//import com.example.tikito.entities.Event;
//import com.example.tikito.entities.SeatItem;
//import com.example.tikito.entities.Show;
//import com.example.tikito.entities.TimeItem;
//import com.example.tikito.entities.Venue;
//import com.example.tikito.utils.API;
//import com.example.tikito.utils.SessionManager;
//import com.google.android.material.snackbar.Snackbar;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.example.tikito.entities.Ticket;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class BookSeatActivity extends AppCompatActivity implements SeatAdapter.OnSeatSelectedListener,
//        TimeAdapter.OnTimeClickListener,
//        DateAdapter.OnDateClickListener
//{
//    RecyclerView recyclerViewDates, recyclerViewTimes, recyclerViewSeats;
//    DateAdapter dateAdapter;
//    TimeAdapter timeAdapter;
//    SeatAdapter seatAdapter;
//    TextView txtNoOfSeats, txtSeatNos, txtMovieName, txtVenueNameAndAdr;
//    Button confirm;
//    private long venueId;
//    private long selectedShowId;
//    SessionManager manager;
//    List<TimeItem> times = new ArrayList<>();
//    List<DateItem> dates = new ArrayList<>();
//    List<Show> showList = new ArrayList<>();
//    List<SeatItem> seats = new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_book_seat);
//
//        //session manager for jwt token auth -> isko context pass karna hota hai
//        manager = new SessionManager(this);
//
//        //Views
//        txtSeatNos = findViewById(R.id.txtSeatNos);
//        txtNoOfSeats = findViewById(R.id.txtNoOfSeats);
//        txtMovieName = findViewById(R.id.txtMovieName);
//        txtVenueNameAndAdr = findViewById(R.id.txtVenueNameAndAdr);
//        recyclerViewDates = findViewById(R.id.recyclerViewDates);
//        recyclerViewTimes = findViewById(R.id.recyclerViewTimes);
//        recyclerViewSeats = findViewById(R.id.recyclerViewSeats);
//        confirm = findViewById(R.id.confirmBtn);
//
//        //set layout for adaptors
//        LinearLayoutManager layoutManagerDate = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        LinearLayoutManager layoutManagerTime = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//
//        //set Adapters
//        dateAdapter = new DateAdapter(this, dates, this);
//        recyclerViewDates.setAdapter(dateAdapter);
//        recyclerViewDates.setLayoutManager(layoutManagerDate);
//
//        timeAdapter = new TimeAdapter(this, times, this);
//        recyclerViewTimes.setAdapter(timeAdapter);
//        recyclerViewTimes.setLayoutManager(layoutManagerTime);
//
//        seatAdapter = new SeatAdapter(this, seats, this);
//        recyclerViewSeats.setAdapter(seatAdapter);
//        recyclerViewSeats.setLayoutManager(new GridLayoutManager(this, 5));
//
//        Event SelectedEvent = (Event) getIntent().getSerializableExtra("event");
//        Show SelectedShow = (Show) getIntent().getSerializableExtra("show");
//        Venue SelectedVenue = (Venue) getIntent().getSerializableExtra("venue");
//
//        Event event = new Event();
//        event.setEventId(1L);
//        event.setEventName("Avengers Endgame IMAX");
//        event.setEventType("Movie");
//        event.setEventDescription("SuperHero Action Movie");
//        event.setEventDurationMin(189L);
//        event.setAgeRestriction(13);
//        event.setPosterUrl("postername.url");
//
//        Venue venue = new Venue();
//        venue.setVenueId(2L);
//        venue.setName("INOX VJ Happiness");
//        venue.setAddress(" Hinjewadi P2, Pune");
//        venue.setAreFacilitiesAvailable(true);
//
//        venueId = venue.getVenueId();
//
//
//        //load all shows through retrofit
//        loadShows(event);
//
//        txtMovieName.setText(event.getEventName());
//        txtVenueNameAndAdr.setText(venue.getName() + venue.getAddress());
//
//        confirm.setOnClickListener(v ->
//        {
//            bookSeats();
//        });
//    }
//
//    @Override
//    public void onSeatSelectionChanged(List<SeatItem> selectedSeats)
//    {
//        txtNoOfSeats.setText(selectedSeats.size() + " seats selected");
//
//        StringBuilder seatNums = new StringBuilder();
//
//        for(SeatItem si : selectedSeats)
//        {
//            seatNums.append(si.getSeatNo()).append(", ");
//        }
//
//        if(seatNums.length() > 0)
//        {
//            seatNums.setLength(seatNums.length() - 2); // remove ","
//        }
//
//        txtSeatNos.setText(seatNums.toString());
//    }
//
//    @Override
//    public void onTimeClicked(TimeItem item)
//    {
//        loadBookedSeats(item.getShowId());
//    }
//
//    @Override
//    public void onDateClicked(DateItem dateItem)
//    {
//        loadTimes(dateItem.getDate());
//    }
//    private void loadShows(Event event)
//    {
//        Log.d("BOOK", "Calling API for eventId = " + event.getEventId());
//        showList.clear();
//        API.getApi(this).getShowAPI().getShowsByEvent(event.getEventId())
//                .enqueue(new Callback<JsonObject>() {
//                    @Override
//                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//
//                        try {
//
//                            Log.d("BOOK", "Response received");
//
//                            JsonObject responseBody = response.body();
//                            Log.d("BOOK", responseBody.toString());
//                            JsonArray jsonArr = responseBody.getAsJsonArray(AppConstants.RESPONSE_DATA);
//                            showList.clear();
//                            for (JsonElement element : jsonArr) {
//                                JsonObject obj = element.getAsJsonObject();
//                                Log.d("BOOK", obj.toString());
//                                Show show = new Show();
//
//                                show.setShowId(obj.get("showId").getAsLong());
//                                show.setLanguage(obj.get("language").getAsString());
//                                show.setShowDate(LocalDate.parse(obj.get("showDate").getAsString()));
//                                show.setShowStartTime(LocalTime.parse(obj.get("showStartTime").getAsString()));
//                                show.setShowEndTime(LocalTime.parse(obj.get("showEndTime").getAsString()));
//                                show.setPrice(obj.get("price").getAsDouble());
//                                show.setEighteenPlus(obj.get("eighteenPlus").getAsBoolean());
//
//                                showList.add(show);
//                            }
//                            Log.d("BOOK", "Shows = " + showList.size());
//                            loadDates();
//                        } catch (Exception e) {
//                            Log.e("BOOK", "Exception while parsing", e);
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<JsonObject> call, Throwable t)
//                    {
//                        Log.e("BOOK", "API FAILED", t);
//                        Toast.makeText(BookSeatActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    private void loadDates()
//    {
//        dates.clear();
//
//        for(Show show : showList)
//        {
//            String date = show.getShowDate().toString();
//            boolean exists = false;
//            for(DateItem item : dates)
//            {
//                if(item.getDate().equals(date))
//                {
//                    exists = true;
//                    break;
//                }
//            }
//
//            if(!exists)
//            {
//                dates.add(new DateItem("", date));
//            }
//        }
//
//        dateAdapter.setDateItems(dates);
//        if (!dates.isEmpty())
//        {
//            dateAdapter.setSelectedPosition(0);
//            loadTimes(dates.get(0).getDate());
//        }
//        Log.d("DATES", "Dates = " + dates.size());
//    }
//
//    private void loadTimes(String selectedDate)
//    {
//        List<TimeItem> filtered = new ArrayList<>();
//
//        for(Show show : showList)
//        {
//            if(show.getShowDate().toString().equals(selectedDate))
//            {
//                filtered.add(
//                        new TimeItem(
//                                show.getShowId(),
//                                show.getShowStartTime().toString()
//                        )
//                );
//            }
//        }
//
//        Log.d("TIME", "Times = " + filtered.size());
//
//        timeAdapter.setTimeItems(filtered);
//
//        if (!filtered.isEmpty())
//        {
//            timeAdapter.setSelectedPosition(0);
//            loadBookedSeats(filtered.get(0).getShowId());
//        }
//    }
//
//    private void loadBookedSeats(Long showId)
//    {
//        selectedShowId = showId;
//
//        loadVenueSeats();
//    }
//
//    private void loadVenueSeats() {
//        seats.clear();
//
//        API.getApi(this).getVenueAPI().getVenueById(venueId)
//                .enqueue(new Callback<JsonObject>() {
//                    @Override
//                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                        try {
//                            JsonObject responseBody = response.body();
//
//                            if (responseBody.get(AppConstants.RESPONSE_STATUS).getAsString().equals(AppConstants.SUCCESS_RESPONSE)) {
//                                JsonObject venueObj = responseBody.getAsJsonObject(AppConstants.RESPONSE_DATA);
//
//                                JsonArray seatArray = venueObj.getAsJsonArray("seatList");
//
//                                for (JsonElement element : seatArray) {
//                                    JsonObject seatObj = element.getAsJsonObject();
//
//                                    SeatItem item = new SeatItem();
//
//                                    item.setSeatId(seatObj.get("seatId").getAsLong());
//                                    item.setSeatNo(seatObj.get("seatNo").getAsString());
//
//                                    // initially every seat is available
//                                    item.setBooked(false);
//                                    item.setSelected(false);
//
//                                    seats.add(item);
//                                }
//
//                                // NEXT STEP
//                                loadAlreadyBookedSeats(selectedShowId);
//                            }
//                        } catch (Exception e) {
//                            Log.e("BOOK", "Exception while parsing", e);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<JsonObject> call, Throwable t) {
//                        Toast.makeText(BookSeatActivity.this,
//                                "Unable to load seats",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//        }
//
//    private void loadAlreadyBookedSeats(Long showId)
//    {
//        // Temporary hardcoded token
//        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbW9naEBnbWFpbC5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzg1Mzc4OTg3LCJleHAiOjE3ODU0MTQ5ODd9.lvIg5mfBWCefAjctT-GkxpXOn8zyabClypdnLOwULoU";
//
//        API.getApi(this)
//                .getBookingAPI()
//                .getAvailableSeats(token, showId)
//                .enqueue(new Callback<JsonObject>()
//                {
//                    @Override
//                    public void onResponse(Call<JsonObject> call,
//                                           Response<JsonObject> response)
//                    {
//                        if(!response.isSuccessful())
//                        {
//                            Log.e("HTTP", "Code = " + response.code());
//
//                            try
//                            {
//                                if(response.errorBody() != null)
//                                {
//                                    Log.e("HTTP", response.errorBody().string());
//                                }
//                            }
//                            catch (Exception e)
//                            {
//                                e.printStackTrace();
//                            }
//
//                            return;
//                        }
//
//                        try
//                        {
//                            JsonObject body = response.body();
//
//                            for(SeatItem seat : seats)
//                            {
//                                seat.setBooked(true);
//                            }
//
//                            JsonArray arr =
//                                    body.getAsJsonArray(AppConstants.RESPONSE_DATA);
//
//                            for(JsonElement element : arr)
//                            {
//                                JsonObject obj = element.getAsJsonObject();
//
//                                long availableSeatId =
//                                        obj.get("seatId").getAsLong();
//
//                                for(SeatItem seat : seats)
//                                {
//                                    if(seat.getSeatId().equals(availableSeatId))
//                                    {
//                                        seat.setBooked(false);
//                                        break;
//                                    }
//                                }
//                            }
//
//                            seatAdapter.setSeatItems(seats);
//                        }
//                        catch(Exception e)
//                        {
//                            Log.e("BOOK","Parsing error",e);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<JsonObject> call, Throwable t)
//                    {
//                        Log.e("BOOK","Network Error",t);
//
//                        Toast.makeText(BookSeatActivity.this,
//                                "Cannot load seats",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    private void bookSeats() {
//        List<Long> seatIds = new ArrayList<>();
//
//        for (SeatItem seat : seats) {
//            if (seat.isSelected()) {
//                seatIds.add(seat.getSeatId());
//            }
//        }
//
//        if (seatIds.isEmpty()) {
//            Toast.makeText(this,
//                    "Please select at least one seat",
//                    Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Ticket ticket = new Ticket();
//        ticket.setShowId(selectedShowId);
//        ticket.setSeatIds(seatIds);
//
//        // Temporary hardcoded token
//        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbW9naEBnbWFpbC5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzg1Mzc4OTg3LCJleHAiOjE3ODU0MTQ5ODd9.lvIg5mfBWCefAjctT-GkxpXOn8zyabClypdnLOwULoU";
//
//        API.getApi(this)
//                .getBookingAPI()
//                .bookTicket(token, ticket)
//                .enqueue(new Callback<JsonObject>() {
//                    @Override
//                    public void onResponse(Call<JsonObject> call,
//                                           Response<JsonObject> response) {
//                        if (!response.isSuccessful()) {
//                            Log.e("HTTP", "Code = " + response.code());
//
//                            try {
//                                if (response.errorBody() != null) {
//                                    Log.e("HTTP", response.errorBody().string());
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                            return;
//                        }
//
//                        Toast.makeText(BookSeatActivity.this,
//                                "Booking Successful",
//                                Toast.LENGTH_SHORT).show();
//
//                        loadBookedSeats(selectedShowId);
//                    }
//
//                    @Override
//                    public void onFailure(Call<JsonObject> call, Throwable t) {
//                        Toast.makeText(BookSeatActivity.this,
//                                "Booking Failed",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//        }
//    }
//
//
