package com.sunbeam.tikito.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.sunbeam.tikito.daos.BookedSeatsDao;
import com.sunbeam.tikito.daos.BookingDao;
import com.sunbeam.tikito.daos.SeatDao;
import com.sunbeam.tikito.daos.ShowDao;
import com.sunbeam.tikito.daos.UserDao;
import com.sunbeam.tikito.daos.VenueDao;
import com.sunbeam.tikito.dto.AllBookingsDto;
import com.sunbeam.tikito.dto.CancelTicketDto;
import com.sunbeam.tikito.dto.TicketBookedDto;
import com.sunbeam.tikito.dto.TicketBookingDto;
import com.sunbeam.tikito.dto.UserBookingDto;
import com.sunbeam.tikito.entity.UserEntity;
import com.sunbeam.tikito.entity.VenueEntity;
import com.sunbeam.tikito.enums.BookingStatus;
import com.sunbeam.tikito.enums.PaymentStatus;
import com.sunbeam.tikito.exceptions.InvalidBookingException;
import com.sunbeam.tikito.exceptions.InvalidSeatsException;
import com.sunbeam.tikito.exceptions.InvalidShowException;
import com.sunbeam.tikito.exceptions.UserNotFoundException;
import com.sunbeam.tikito.services.BookingService;
import com.sunbeam.tikito.entity.BookedSeatsEntity;
import com.sunbeam.tikito.entity.BookingEntity;
import com.sunbeam.tikito.entity.SeatEntity;
import com.sunbeam.tikito.entity.ShowEntity;
import jakarta.transaction.Transactional;

@Transactional
@Service
public class BookingServiceImpl implements BookingService 
{	
	private UserDao userDao;
	private ShowDao showDao;
	private SeatDao seatDao;
	private BookedSeatsDao bookedSeatDao;
	private BookingDao bookingDao;
	private ModelMapper mapper;
	private VenueDao venueDao;
	
	public BookingServiceImpl(UserDao userDao, ShowDao showDao, SeatDao seatDao, BookedSeatsDao bookedSeatDao, BookingDao bookingDao, ModelMapper mapper, VenueDao venueDao)
	{
		this.userDao = userDao;
		this.showDao = showDao;
		this.seatDao = seatDao;
		this.bookedSeatDao = bookedSeatDao;
		this.bookingDao = bookingDao;
		this.mapper = mapper;
		this.venueDao = venueDao;
	}

	@Override
	public TicketBookedDto bookTicket(TicketBookingDto dto) 
	{
		TicketBookedDto ticket = new TicketBookedDto();
		
		Optional<UserEntity> userOptional = userDao.findById(dto.getUserId());
		if(!userOptional.isPresent())
		{
			throw new UserNotFoundException("User cannot be found");
		}
		else
		{
			UserEntity user = userOptional.get();
			Optional<ShowEntity> showOptional = showDao.findById(dto.getShowId());
			if(!showOptional.isPresent())
			{
				throw new InvalidShowException("Show is not Available");
			}
			else
			{
				ShowEntity show = showOptional.get();
				long venueId = show.getVenue().getVenueId();
				
				if(isDateValid(show))
				{
					List<SeatEntity> seats = seatDao.findBySeatIdIn(dto.getSeatIds());
					if(seats.size() != dto.getSeatIds().size())
					{
						throw new InvalidSeatsException("seats are invalid");
					}
					else
					{
						for(SeatEntity s : seats)
						{
							if(s.getVenue().getVenueId() != venueId)
							{
								throw new InvalidSeatsException("seats are invalid");
							}
						}
						
						List<BookedSeatsEntity> bookedSeats = bookedSeatDao.findByShowShowIdAndSeatSeatIdIn(dto.getShowId(), dto.getSeatIds());
						if(!bookedSeats.isEmpty())
						{
							throw new InvalidSeatsException("seats are already booked");
						}
						
						Double totalAmt = show.getPrice() * seats.size();
						List<BookedSeatsEntity> newBookingSeats = new ArrayList<>();
						
	 					BookingEntity booking = new BookingEntity(null, user, show , new ArrayList<>(), totalAmt, PaymentStatus.PAID, BookingStatus.SUCCESS);
						
	 					for(SeatEntity s : seats)
	 					{
	 						BookedSeatsEntity bs = new BookedSeatsEntity(null, booking, s, show);
	 						newBookingSeats.add(bs);
	 					}
	 					
	 					try
	 					{
	 						booking.setBookedSeats(newBookingSeats);
	 	 					BookingEntity newBooking = bookingDao.save(booking);
	 	 					
	 	 					List<String> seatNums = new ArrayList<>();
	 	 					List<BookedSeatsEntity> newSeatsBooked = bookedSeatDao.saveAll(newBookingSeats);
	 	 					
	 	 					for(BookedSeatsEntity bs : newSeatsBooked)
	 	 					{
	 	 						seatNums.add(bs.getSeat().getSeatNo());
	 	 					}
	 	 					
	 	 					ticket = new TicketBookedDto(newBooking.getBookingId(), show.getShowId(), seatNums, totalAmt, PaymentStatus.PAID, BookingStatus.SUCCESS);
	 					}
	 					catch(DataIntegrityViolationException e)
	 					{
	 						throw new InvalidSeatsException("seat are already booked");
	 					}
					}
				}
				else
				{
					throw new InvalidShowException("The show has already started");
				}
				
			}
		}
		
		return ticket;
	}

	@Override
	public CancelTicketDto cancelTicket(long bookingId, long userId) 
	{
		CancelTicketDto cancelledTicket = new CancelTicketDto();
		Optional<UserEntity> userOptional = userDao.findById(userId);
		if(!userOptional.isPresent())
		{
			throw new UserNotFoundException("User cannot be found");
		}
		else
		{
			Optional<BookingEntity> optionalBooking = bookingDao.findByBookingIdAndUserUserId(bookingId, userId);
			if(!optionalBooking.isPresent())
			{
				throw new InvalidBookingException("Booking not found");
			}
			else
			{
				BookingEntity booking = optionalBooking.get();
				if(booking.getBookingStatus().equals(BookingStatus.CANCELLED))
				{
					throw new InvalidBookingException("Ticket booking is already cancelled");
				}
				else
				{
					ShowEntity show = booking.getShow();
					if(isDateValid(show))
					{
						booking.setBookingStatus(BookingStatus.CANCELLED);
						booking.setPaymentStatus(PaymentStatus.REFUNDED);
						
						bookingDao.save(booking);
						bookedSeatDao.deleteByBookingBookingId(bookingId);
						
						cancelledTicket = new CancelTicketDto(bookingId, booking.getBookingStatus(), booking.getPaymentStatus());
					}
					else
					{
						throw new InvalidShowException("The show has already started");
					}
				}
			}
		}
		
		return cancelledTicket;
	}
	
	public boolean isDateValid(ShowEntity show)
	{
		LocalDate sDate = show.getShowDate();
		LocalTime sTime = show.getShowStartTime(); 
		
		if(LocalDate.now().isBefore(sDate))
			return true;
		else if(LocalDate.now().isEqual(sDate))
		{
			if(LocalTime.now().isBefore(sTime))
			{
				return true;
			}
			else 
				return false;
		}
		else
			return false;
	}

	@Override
	public UserBookingDto getBookingsByUser(long bookingId, long userId) 
	{
		UserBookingDto bookingDetails = new UserBookingDto();
		Optional<UserEntity> optionalUser = userDao.findById(userId);
		if(!optionalUser.isPresent())
		{
			throw new UserNotFoundException("User is not available");
		}
		else
		{
			Optional<BookingEntity> optionalBooking = bookingDao.findByBookingIdAndUserUserId(bookingId, userId);
			if(!optionalBooking.isPresent())
			{
				throw new InvalidBookingException("Booking unavailable");
			}
			else
			{
				BookingEntity booking = optionalBooking.get();
				
				List<String> seatNums = new ArrayList<>();
				List<BookedSeatsEntity> seats = booking.getBookedSeats();
				for(BookedSeatsEntity bs : seats)
				{
					seatNums.add(bs.getSeat().getSeatNo());
				}
				
				bookingDetails = new UserBookingDto(booking.getBookingId(), 
													booking.getShow().getShowId(), 
													booking.getTotalAmt(), 
													seatNums, 
													booking.getPaymentStatus(), 
													booking.getBookingStatus(), 
													booking.getCreatedAt());
				
			}
		}
		
		return bookingDetails;
	}

	@Override
	public List<UserBookingDto> getAllBookingsByUser(long userId)
	{
		UserBookingDto bookingDetails = new UserBookingDto();
		List<UserBookingDto> bookingList = new ArrayList<>();
		Optional<UserEntity> optionalUser = userDao.findById(userId);
		if(!optionalUser.isPresent())
		{
			throw new UserNotFoundException("User not found");
		}
		else
		{
			List<BookingEntity> bookings = bookingDao.findByUserUserId(userId);
			if(!bookings.isEmpty())
			{
				for(BookingEntity b : bookings)
				{
					List<String> seatNums = new ArrayList<>();
					List<BookedSeatsEntity> seats = b.getBookedSeats();
					for(BookedSeatsEntity bs : seats)
					{
						seatNums.add(bs.getSeat().getSeatNo());
					}
					
					bookingDetails = new UserBookingDto(b.getBookingId(), 
														b.getShow().getShowId(), 
														b.getTotalAmt(), 
														seatNums, 
														b.getPaymentStatus(), 
														b.getBookingStatus(), 
														b.getCreatedAt());
					
					bookingList.add(bookingDetails);
				}
			}
		}
		
		return bookingList;
	}

	//work on this one for serialization issue
	@Override
	public List<AllBookingsDto> getAllBookingsByShow(long showId) 
	{
		List<AllBookingsDto> dtos = new ArrayList<>();
		List<BookingEntity> bookings = bookingDao.findByShowShowId(showId);
		for(BookingEntity b : bookings)
		{
			AllBookingsDto dto = mapper.map(b, AllBookingsDto.class);
			dto.setShowId(showId);
			dto.setUserId(b.getUser().getUserId());
			dtos.add(dto);
		}
		
		return dtos;
	}

	@Override
	public int getAllAvailableSeats(long showId, long venueId, long bookingId) 
	{
		int count = 0;
		Optional<VenueEntity> optionalVenue = venueDao.findById(venueId);
		if(!optionalVenue.isPresent())
		{
			throw new RuntimeException("Invalid venue");
		}
		else
		{
			VenueEntity venue = optionalVenue.get();
			List<SeatEntity> seats = venue.getSeatList();
			int totalSeats = seats.size();
			
			Optional<BookingEntity> optionalBooking = bookingDao.findById(bookingId);
			if(!optionalBooking.isPresent())
			{
				throw new InvalidBookingException("Booking does not exception");
			}
			else
			{
				BookingEntity booking = optionalBooking.get();
				List<BookedSeatsEntity> bookedSeats = booking.getBookedSeats();
				int totalBookedSeats = bookedSeats.size();
				count = totalSeats - totalBookedSeats;
			}
		}
		
		return count;
	}
}
