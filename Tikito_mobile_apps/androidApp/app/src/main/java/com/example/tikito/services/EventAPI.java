package com.example.tikito.services;


import com.example.tikito.entities.ApiResponse;
import com.example.tikito.entities.Event;
import com.example.tikito.entities.EventType;
import com.google.gson.JsonObject;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EventAPI {


    @GET("tikito/events")
    Call<ApiResponse<List<Event>>> getAllEvents();

    @GET("tikito/events/type/{eventType}")
    Call<ApiResponse<List<Event>>> getEventsByType(
            @Path("eventType") String eventType
    );

    @GET("tikito/events/{eventId}")
    Call<ApiResponse<Event>> getEventById(
            @Path("eventId") int eventId
    );

    @GET("/tikito/events/count-by-type")
    Call<JsonObject> getEventCountByType();

}
