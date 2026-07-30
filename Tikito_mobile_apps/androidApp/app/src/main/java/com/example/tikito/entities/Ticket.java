package com.example.tikito.entities;

import java.io.Serializable;
import java.util.List;

public class Ticket implements Serializable
{
    private Long showId;
    private List<Long> seatIds;

    public Ticket(Long showId, List<Long> seatIds) {
        this.showId = showId;
        this.seatIds = seatIds;
    }

    public Ticket() {
    }

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    public List<Long> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<Long> seatIds) {
        this.seatIds = seatIds;
    }
}
