package com.sunbeam.tikito.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.sunbeam.tikito.enums.BookingStatus;
import com.sunbeam.tikito.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserBookingDto 
{
	private long bookingId;
	private long showId;
	private double totalAmt;
	private List<String> seatNumbers;
	private PaymentStatus paymentStatus;
	private BookingStatus bookingStatus;
	private LocalDateTime bookingDate;
}
