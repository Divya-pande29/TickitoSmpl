package com.example.tikito.entities;

import androidx.annotation.NonNull;

public class DateItem
{
    private String day;
    private String date;

    public DateItem(String day, String date) {
        this.day = day;
        this.date = date;
    }

    public DateItem() {

    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @NonNull
    @Override
    public String toString() {
        return "DateItem{" +
                "day='" + day + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
