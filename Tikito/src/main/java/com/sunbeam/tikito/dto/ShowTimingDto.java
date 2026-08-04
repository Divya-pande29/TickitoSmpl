package com.sunbeam.tikito.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
	public class ShowTimingDto {

<<<<<<< HEAD
	    private Long showId;
	    
=======
	  private Long showId;

>>>>>>> Sumer
	    private LocalDate showDate;

	    private LocalTime showStartTime;

	    private LocalTime showEndTime;

	    private Double price;

	    private boolean eighteenPlus;

	    private String language;
	}
