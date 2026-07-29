package com.example.tikito.entities;

public class BookingHistory {

    private String movieName;
    private String venueName;
    private String showDate;
    private String showTime;
    private String seatNumber;
    private String status;

    public BookingHistory(String movieName,
                          String venueName,
                          String showDate,
                          String showTime,
                          String seatNumber,
                          String status) {

        this.movieName = movieName;
        this.venueName = venueName;
        this.showDate = showDate;
        this.showTime = showTime;
        this.seatNumber = seatNumber;
        this.status = status;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getVenueName() {
        return venueName;
    }

    public String getShowDate() {
        return showDate;
    }

    public String getShowTime() {
        return showTime;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getStatus() {
        return status;
    }
}