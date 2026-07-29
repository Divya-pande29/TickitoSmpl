package com.example.tikito.utils;

import com.example.tikito.constants.AppConstants;
import com.example.tikito.services.BookingAPI;
import com.example.tikito.services.EventAPI;
import com.example.tikito.services.ShowAPI;
import com.example.tikito.services.UserAPI;
import com.example.tikito.services.VenueAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API
{
    public static final String URL = "http://192.168.68.116:8080/";
    private static API api = null;

    private EventAPI eventAPI;
    private VenueAPI venueAPI;
    private ShowAPI showAPI;
    private BookingAPI bookingAPI;
    private UserAPI userAPI;

    private API()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        bookingAPI = retrofit.create(BookingAPI.class);
        venueAPI = retrofit.create(VenueAPI.class);
        showAPI = retrofit.create(ShowAPI.class);
        eventAPI = retrofit.create(EventAPI.class);
        userAPI = retrofit.create(UserAPI.class);
    }

    public static API getApi(){
        if (api == null)
            api = new API();
        return api;
    }

    public UserAPI getUserAPI() {
        return userAPI;
    }

    public ShowAPI getShowAPI() {
        return showAPI;
    }

    public VenueAPI getVenueAPI() {
        return venueAPI;
    }

    public BookingAPI getBookingAPI()
    {
        return bookingAPI;
    }

    public  EventAPI getEventAPI()
    {
        return eventAPI;
    }
}


