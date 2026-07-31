package com.example.tikito.entities;

import androidx.annotation.NonNull;

public class TimeItem
{
    private Long showId;
    private String time;

    public TimeItem() {
    }

    public TimeItem(String time) {
        this.time = time;
    }

    public TimeItem(Long showId, String time) {
        this.time = time;
        this.showId = showId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    @NonNull
    @Override
    public String toString() {
        return "TimeItem{" +
                "showId=" + showId +
                ", time='" + time + '\'' +
                '}';
    }
}
