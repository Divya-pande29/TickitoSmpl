package com.example.tikito.entities;

import androidx.annotation.NonNull;

public class SeatItem
{
    private Long seatId;
    private String seatNo;
    private boolean isSelected;

    private boolean isBooked;

    public SeatItem(String seatNo) {
        this.seatNo = seatNo;
        this.isSelected = false;
    }

    public SeatItem(Long seatId, String seatNo, boolean isSelected, boolean isBooked) {
        this.seatId = seatId;
        this.seatNo = seatNo;
        this.isSelected = isSelected;
        this.isBooked = isBooked;
    }

    public SeatItem() {
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    @NonNull

    @Override
    public String toString() {
        return "SeatItem{" +
                "seatId=" + seatId +
                ", seatNo='" + seatNo + '\'' +
                ", isSelected=" + isSelected +
                ", isBooked=" + isBooked +
                '}';
    }

    public enum SeatStatus
    {
        AVAILABLE, BOOKED, SELECTED
    }
}
