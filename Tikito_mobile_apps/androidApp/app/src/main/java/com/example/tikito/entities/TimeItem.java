package com.example.tikito.entities;

import androidx.annotation.NonNull;

public class TimeItem
{
    private String time;

    public TimeItem() {
    }

    public TimeItem(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @NonNull
    @Override
    public String toString() {
        return "TimeItem{" +
                "time='" + time + '\'' +
                '}';
    }
}
