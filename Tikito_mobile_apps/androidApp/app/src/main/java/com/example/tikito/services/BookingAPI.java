package com.example.tikito.services;

import com.example.tikito.entities.Ticket;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookingAPI
{
    @GET("tikito/booking/admin/getAvailableSeats")
    Call<JsonObject> getAvailableSeats(@Query("showId") long showId);
    @POST("tikito/booking/user")
    Call<JsonObject> bookTicket(@Body Ticket ticket);

    @GET("tikito/booking/user/getMyBooking")
    Call<JsonObject> getMyBookings();

    @PATCH("tikito/booking/user/cancel/{bookingId}")
    Call<JsonObject> cancelBooking(@Path("bookingId") Long bookingId);
}
