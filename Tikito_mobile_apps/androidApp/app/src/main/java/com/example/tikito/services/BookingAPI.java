package com.example.tikito.services;

import com.example.tikito.entities.Ticket;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BookingAPI
{
    @GET("booking/admin/getAvailableSeats")
    Call<JsonObject> getAvailableSeats(
            @Header("Authorization") String token,
            @Query("showId") long showId
    );
    @POST("booking/user")
    Call<JsonObject> bookTicket(
            @Header("Authorization") String token,
            @Body Ticket ticket
    );
}
