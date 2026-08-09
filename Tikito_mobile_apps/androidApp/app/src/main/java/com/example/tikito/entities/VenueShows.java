package com.example.tikito.entities;

import java.util.ArrayList;
import java.util.List;

public class VenueShows {

    private Long venueId;
    private String venueName;
    private String address;
    private boolean areFacilitiesAvailable;

    // All shows of this venue
    private List<ShowTiming> shows = new ArrayList<>();

    // Filtered shows according to selected date
    private List<ShowTiming> filteredShows = new ArrayList<>();

    public VenueShows() {
    }

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAreFacilitiesAvailable() {
        return areFacilitiesAvailable;
    }

    public void setAreFacilitiesAvailable(boolean areFacilitiesAvailable) {
        this.areFacilitiesAvailable = areFacilitiesAvailable;
    }

    public List<ShowTiming> getShows() {
        return shows;
    }

    public void setShows(List<ShowTiming> shows) {
        this.shows = shows;
    }

    public List<ShowTiming> getFilteredShows() {
        return filteredShows;
    }

    public void setFilteredShows(List<ShowTiming> filteredShows) {
        this.filteredShows = filteredShows;
    }
}