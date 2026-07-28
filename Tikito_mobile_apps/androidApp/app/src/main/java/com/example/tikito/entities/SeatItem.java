package com.example.tikito.entities;

import androidx.annotation.NonNull;

public class SeatItem
{
    private String seatNo;
    private boolean isSelected;

    public SeatItem(String seatNo) {
        this.seatNo = seatNo;
        this.isSelected = false;
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

    @NonNull
    @Override
    public String toString() {
        return "SeatItem{" +
                "seatNo='" + seatNo + '\'' +
                '}';
    }
}
