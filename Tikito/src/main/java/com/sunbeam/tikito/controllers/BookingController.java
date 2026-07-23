package com.sunbeam.tikito.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sunbeam.tikito.dto.AllBookingsDto;
import com.sunbeam.tikito.dto.CancelTicketDto;
import com.sunbeam.tikito.dto.TicketBookedDto;
import com.sunbeam.tikito.dto.TicketBookingDto;
import com.sunbeam.tikito.dto.UserBookingDto;
import com.sunbeam.tikito.serviceimpl.BookingServiceImpl;
import com.sunbeam.tikito.utils.Resp;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tikito/api/booking")
public class BookingController 
{
	@Autowired
	private BookingServiceImpl ser;
	
	@PostMapping
	public Resp<?> bookTicket(@Valid @RequestBody TicketBookingDto dto)
	{
		TicketBookedDto ticket = ser.bookTicket(dto);
		return Resp.success(ticket);
	}
	
	@PatchMapping("/cancel/{bookingId}")
	public Resp<?> cancelTicket(@PathVariable long bookingId, @RequestParam long userId)
	{
		CancelTicketDto cancelledTicket = ser.cancelTicket(bookingId, userId);
		return Resp.success(cancelledTicket);
	}
	
	@GetMapping("/getByUser/{bookingId}")
	public Resp<?> getBookingsByUser(@PathVariable long bookingId, @RequestParam long userId)
	{
		UserBookingDto userDetails = ser.getBookingsByUser(bookingId, userId);
		return Resp.success(userDetails);
	}
	
	@GetMapping("/getAllBookings/{userId}") //My tickets in ui
	public Resp<?> getAllBookingsByUser(@PathVariable long userId)
	{
		List<UserBookingDto> userDetailsList = ser.getAllBookingsByUser(userId);
		return Resp.success(userDetailsList);
	}
	
	@GetMapping("/getByShowId/{showId}")
	public Resp<?> getAllBookingByShow(@PathVariable long showId)
	{
		List<AllBookingsDto> allBookings = ser.getAllBookingsByShow(showId);
		return Resp.success(allBookings);
	}
	
	@GetMapping("/getAvailableSeats")
	public Resp<?> getAllAvailableSeats(@RequestParam long venueId, @RequestParam long showId, @RequestParam long bookingId)
	{
		int countAvailableSeats = ser.getAllAvailableSeats(showId, venueId, bookingId);
		return Resp.success(countAvailableSeats);
	}
}
