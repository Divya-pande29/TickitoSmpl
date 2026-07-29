package com.example.tikito.services;

import com.example.tikito.entities.Event;


import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EventAPI {


    // Get All Events
    @GET("tikito/events")
    Call<Resp<List<Event>>> getAllEvents();

    // Get Events by Type
    @GET("tikito/events/type/{eventType}")
    Call<Resp<List<Event>>> getEventsByType(
            @Path("eventType") String eventType
    );

    // Get Event by Id
    @GET("tikito/events/{eventId}")
    Call<Resp<Event>> getEventById(
            @Path("eventId") int eventId
    );
}
