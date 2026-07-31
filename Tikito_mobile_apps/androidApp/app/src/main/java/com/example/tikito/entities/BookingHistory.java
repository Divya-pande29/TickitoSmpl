package com.example.tikito.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class BookingHistory implements Serializable
{
    private Long bookingId;
    private Long showId;

    private String eventName;
    String venueName;

    private LocalDate showDate;
    private LocalTime showStartTime;
    private LocalTime showEndTime;

    private Double totalAmt;

    private List<String> seatNumbers;

    private String paymentStatus;

    private String bookingStatus;

    public BookingHistory(Long bookingId, Long showId, String eventName, String venueName, LocalDate showDate, LocalTime showStartTime, LocalTime showEndTime, Double totalAmt, List<String> seatNumbers, String paymentStatus, String bookingStatus) {
        this.bookingId = bookingId;
        this.showId = showId;
        this.eventName = eventName;
        this.venueName = venueName;
        this.showDate = showDate;
        this.showStartTime = showStartTime;
        this.showEndTime = showEndTime;
        this.totalAmt = totalAmt;
        this.seatNumbers = seatNumbers;
        this.paymentStatus = paymentStatus;
        this.bookingStatus = bookingStatus;
    }

    public BookingHistory() {
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
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

    public Double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Double totalAmt) {
        this.totalAmt = totalAmt;
    }

    public List<String> getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(List<String> seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}

