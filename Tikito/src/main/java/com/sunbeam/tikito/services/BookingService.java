package com.sunbeam.tikito.services;

import java.util.List;

import com.sunbeam.tikito.dto.AllBookingsDto;
import com.sunbeam.tikito.dto.CancelTicketDto;
import com.sunbeam.tikito.dto.TicketBookedDto;
import com.sunbeam.tikito.dto.TicketBookingDto;
import com.sunbeam.tikito.dto.UserBookingDto;

public interface BookingService
{
	TicketBookedDto bookTicket(TicketBookingDto dto);
	CancelTicketDto cancelTicket(long bookingId, long userId);
	UserBookingDto getBookingsByUser(long bookingId, long userId);
	List<UserBookingDto> getAllBookingsByUser(long userId);
	List<AllBookingsDto> getAllBookingsByShow(long showId);
	int getAllAvailableSeats(long showId, long venueId, long bookingId);
}
