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
	    
>>>>>>> 9c112bdc6c4f8f8fb395358b315aeb6f36a7ff29
	    private LocalDate showDate;

	    private LocalTime showStartTime;

	    private LocalTime showEndTime;

	    private Double price;

	    private boolean eighteenPlus;

	    private String language;
	}
