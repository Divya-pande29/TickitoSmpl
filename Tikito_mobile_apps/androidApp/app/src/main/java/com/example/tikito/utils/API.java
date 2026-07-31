package com.example.tikito.utils;

import static com.example.tikito.constants.AppConstants.BASE_URL;

import android.content.Context;

import com.example.tikito.constants.AppConstants;
import com.example.tikito.services.BookingAPI;
import com.example.tikito.services.EventAPI;
import com.example.tikito.services.ShowAPI;
import com.example.tikito.services.UserAPI;
import com.example.tikito.services.VenueAPI;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API
{
    private static API api = null;

    private EventAPI eventAPI;
    private VenueAPI venueAPI;
    private ShowAPI showAPI;
    private BookingAPI bookingAPI;
    private UserAPI userAPI;

    private API(Context context) {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        bookingAPI = retrofit.create(BookingAPI.class);
        venueAPI = retrofit.create(VenueAPI.class);
        showAPI = retrofit.create(ShowAPI.class);
        eventAPI = retrofit.create(EventAPI.class);
        userAPI = retrofit.create(UserAPI.class);
    }

    public static API getApi(Context context) {

        if (api == null)
            api = new API(context);

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


