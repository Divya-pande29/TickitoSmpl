package com.example.tikito.entities;

import java.util.List;

public class ShowResponse {
    private List<ShowDate> dates;
    private List<VenueShows> venues;

    public List<ShowDate> getDates() {
        return dates;
    }

    public void setDates(List<ShowDate> dates) {
        this.dates = dates;
    }

    public List<VenueShows> getVenues() {
        return venues;
    }

    public void setVenues(List<VenueShows> venues) {
        this.venues = venues;
    }
}

