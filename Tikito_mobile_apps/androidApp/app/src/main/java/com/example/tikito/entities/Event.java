package com.example.tikito.entities;

import java.io.Serializable;

public class Event implements Serializable
{
    private Long eventId;
    private String eventName;
    private String eventType;
    private String eventDescription;
    private Long eventDurationMin;
    private Integer ageRestriction;
    private String posterUrl;

    public Event(Long eventId, String eventName, String eventType, String eventDescription, Long eventDurationMin, Integer ageRestriction, String posterUrl) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventDescription = eventDescription;
        this.eventDurationMin = eventDurationMin;
        this.ageRestriction = ageRestriction;
        this.posterUrl = posterUrl;
    }

    public Event() {
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Long getEventDurationMin() {
        return eventDurationMin;
    }

    public void setEventDurationMin(Long eventDurationMin) {
        this.eventDurationMin = eventDurationMin;
    }

    public Integer getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(Integer ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
