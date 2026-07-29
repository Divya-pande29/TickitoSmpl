package com.example.tikito.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Tickets implements Serializable
{
    private Long BookingId;
    private double amount;
    private String paymentStatus;
    private String bookingStatus;
    private LocalDateTime date;

    public Tickets(Long bookingId, double amount, String paymentStatus, String bookingStatus, LocalDateTime date)
    {
        BookingId = bookingId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.bookingStatus = bookingStatus;
        this.date = date;
    }

    public Tickets(double amount, String paymentStatus, String bookingStatus, LocalDateTime date)
    {
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.bookingStatus = bookingStatus;
        this.date = date;
    }

    public Tickets()
    {

    }

    public Long getBookingId() {
        return BookingId;
    }

    public void setBookingId(Long bookingId) {
        BookingId = bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Tickets)) return false;
        Tickets tickets = (Tickets) o;
        return Objects.equals(BookingId, tickets.BookingId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(BookingId);
    }
}
