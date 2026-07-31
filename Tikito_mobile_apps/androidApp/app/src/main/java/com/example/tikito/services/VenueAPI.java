package com.example.tikito.services;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VenueAPI
{
    @GET("venue/{venueId}")
    Call<JsonObject> getVenueById(@Path("venueId") long venueId);
}
