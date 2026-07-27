package com.sunbeam.tikito.dto;

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
//reponse dto
public class TicketBookedDto
{
	private Long bookingId;
	private Long showId;
	private List<String> seatNums;
	private Double totalAmt;
	private PaymentStatus paymentStatus;
	private BookingStatus bookingStatus;
}
