package com.example.tikito.entities;

import java.io.Serializable;
import java.util.List;

public class Ticket implements Serializable
{
    private Long bookingId;
    private Long showId;
    private List<String> seatNums;
    private Double totalAmt;
    private String paymentStatus;
    private String bookingStatus;

    public Ticket(Long bookingId, Long showId, List<String> seatNums, Double totalAmt, String paymentStatus, String bookingStatus) {
        this.bookingId = bookingId;
        this.showId = showId;
        this.seatNums = seatNums;
        this.totalAmt = totalAmt;
        this.paymentStatus = paymentStatus;
        this.bookingStatus = bookingStatus;
    }

    public Ticket() {
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

    public List<String> getSeatNums() {
        return seatNums;
    }

    public void setSeatNums(List<String> seatNums) {
        this.seatNums = seatNums;
    }

    public Double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Double totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
