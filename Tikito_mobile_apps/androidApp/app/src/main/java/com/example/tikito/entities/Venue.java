package com.example.tikito.entities;

import java.io.Serializable;
import java.util.List;

public class Venue implements Serializable
{
    private Long venueId;
    private String name;
    private String address;
    private boolean isAreFacilitiesAvailable;
    private Integer seatCapacity;
    private List<SeatItem> seats;


    public Venue(Long venueId, String name, String address, boolean isAreFacilitiesAvailable, Integer seatCapacity) {
        this.venueId = venueId;
        this.name = name;
        this.address = address;
        this.isAreFacilitiesAvailable = isAreFacilitiesAvailable;
        this.seatCapacity = seatCapacity;
    }

    public Venue(Long venueId, String name, String address, boolean isAreFacilitiesAvailable, Integer seatCapacity, List<SeatItem> seats) {
        this.venueId = venueId;
        this.name = name;
        this.address = address;
        this.isAreFacilitiesAvailable = isAreFacilitiesAvailable;
        this.seatCapacity = seatCapacity;
        this.seats = seats;
    }

    public Venue() {
    }

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAreFacilitiesAvailable() {
        return isAreFacilitiesAvailable;
    }

    public void setAreFacilitiesAvailable(boolean areFacilitiesAvailable) {
        isAreFacilitiesAvailable = areFacilitiesAvailable;
    }

    public List<SeatItem> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatItem> seats) {
        this.seats = seats;
    }

    public Integer getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(Integer seatCapacity) {
        this.seatCapacity = seatCapacity;
    }
}
