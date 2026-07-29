package com.example.tikito.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Show implements Serializable
{
    private Long showId;
    private Long venueId;

    private Long eventId;

    private String language;

    private Double price;

    private boolean isEighteenPlus;

    private LocalDate showDate;

    private LocalTime showStartTime;

    private LocalTime showEndTime;

    public Show(Long showId, Long venueId, Long eventId, String language, Double price, boolean isEighteenPlus, LocalDate showDate, LocalTime showStartTime, LocalTime showEndTime) {
        this.showId = showId;
        this.venueId = venueId;
        this.eventId = eventId;
        this.language = language;
        this.price = price;
        this.isEighteenPlus = isEighteenPlus;
        this.showDate = showDate;
        this.showStartTime = showStartTime;
        this.showEndTime = showEndTime;
    }

    public Show() {
    }

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isEighteenPlus() {
        return isEighteenPlus;
    }

    public void setEighteenPlus(boolean eighteenPlus) {
        isEighteenPlus = eighteenPlus;
    }

    public LocalDate getShowDate() {
        return showDate;
    }

    public void setShowDate(LocalDate showDate) {
        this.showDate = showDate;
    }

    public LocalTime getShowStartTime() {
        return showStartTime;
    }

    public void setShowStartTime(LocalTime showStartTime) {
        this.showStartTime = showStartTime;
    }

    public LocalTime getShowEndTime() {
        return showEndTime;
    }

    public void setShowEndTime(LocalTime showEndTime) {
        this.showEndTime = showEndTime;
    }
}
