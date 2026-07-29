package com.example.tikito.entities;

import java.io.Serializable;

public class Venue implements Serializable
{
    private String venueId;
    private String name;
    private String address;
    private boolean isAreFacilitiesAvailable;
    private Integer seatCapacity;


    public Venue(String venueId, String name, String address, boolean isAreFacilitiesAvailable, Integer seatCapacity) {
        this.venueId = venueId;
        this.name = name;
        this.address = address;
        this.isAreFacilitiesAvailable = isAreFacilitiesAvailable;
        this.seatCapacity = seatCapacity;
    }

    public Venue() {
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
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

    public Integer getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(Integer seatCapacity) {
        this.seatCapacity = seatCapacity;
    }
}
