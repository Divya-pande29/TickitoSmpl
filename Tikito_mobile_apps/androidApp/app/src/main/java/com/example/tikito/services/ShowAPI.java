package com.example.tikito.services;
import com.example.tikito.entities.Show;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ShowAPI
{
    // GET /tikito/shows
    @GET("tikito/shows")
    Call<JsonObject> findAllShows();

    // GET /tikito/shows/{showId}
    @GET("tikito/shows/{showId}")
    Call<JsonObject> findShowById(@Path("showId") long showId);

    // GET /tikito/shows/event/{eventId}
    @GET("tikito/shows/event/{eventId}")
    Call<JsonObject> findShowByEvent(@Path("eventId") long eventId);

    // GET /tikito/shows/date/{showDate}
    @GET("tikito/shows/date/{showDate}")
    Call<JsonObject> findByDate(@Path("showDate") String showDate);

    // GET /tikito/shows/time/{showTime}
    @GET("tikito/shows/time/{showTime}")
    Call<JsonObject> findByTime(@Path("showTime") String showTime);

    // POST /tikito/shows/admin
    @POST("tikito/shows/admin")
    Call<JsonObject> saveShow(
            @Header("token") String token,
            @Body Show show);

    // PUT /tikito/shows/admin/{showId}
    @PUT("tikito/shows/admin/{showId}")
    Call<JsonObject> updateShow(
            @Header("token") String token,
            @Path("showId") long showId,
            @Body Show show);

    // DELETE /tikito/shows/admin/{showId}
    @DELETE("tikito/shows/admin/{showId}")
    Call<JsonObject> deleteShow(
            @Header("token") String token,
            @Path("showId") long showId);
}

