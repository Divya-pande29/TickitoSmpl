package com.sunbeam.tikito.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunbeam.tikito.daos.EventDao;
import com.sunbeam.tikito.daos.ShowDao;
import com.sunbeam.tikito.daos.VenueDao;
import com.sunbeam.tikito.dto.ShowDto;
import com.sunbeam.tikito.dto.ShowTimingDto;
import com.sunbeam.tikito.dto.VenueShowsDto;
import com.sunbeam.tikito.entity.EventEntity;
import com.sunbeam.tikito.entity.ShowEntity;
import com.sunbeam.tikito.entity.VenueEntity;
import com.sunbeam.tikito.services.ShowService;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class ShowServiceImpl implements ShowService {

	private final ShowDao showDao;
	private final EventDao eventDao;
	private final VenueDao venueDao;

	public ShowServiceImpl(
			ShowDao showDao,
			EventDao eventDao,
			VenueDao venueDao) {

		this.showDao = showDao;
		this.eventDao = eventDao;
		this.venueDao = venueDao;
	}

	@Override
	public List<ShowEntity> findAllShows() {
		return showDao.findAll();
	}

	@Override
	public ShowEntity findShowById(Long showId) {

		return showDao.findById(showId)
				.orElseThrow(() -> new RuntimeException("Show not found"));
	}

	@Override
	public ShowEntity saveShow(ShowDto dto) {

		VenueEntity venue = venueDao.findById(dto.getVenueId())
				.orElseThrow(() -> new RuntimeException("Venue not found"));

		EventEntity event = eventDao.findById(dto.getEventId())
				.orElseThrow(() -> new RuntimeException("Event not found"));

		ShowEntity show = new ShowEntity();

		show.setVenue(venue);
		show.setEvent(event);

		show.setLanguage(dto.getLanguage());
		show.setPrice(dto.getPrice());
		show.setEighteenPlus(dto.isEighteenPlus());

		show.setShowDate(dto.getShowDate());
		show.setShowStartTime(dto.getShowStartTime());
		show.setShowEndTime(dto.getShowEndTime());

		return showDao.save(show);
	}

	@Override
	public ShowEntity updateShow(Long showId, ShowDto dto) {

		ShowEntity show = showDao.findById(showId)
				.orElseThrow(() -> new RuntimeException("Show not found"));

		VenueEntity venue = venueDao.findById(dto.getVenueId())
				.orElseThrow(() -> new RuntimeException("Venue not found"));

		EventEntity event = eventDao.findById(dto.getEventId())
				.orElseThrow(() -> new RuntimeException("Event not found"));

		show.setVenue(venue);
		show.setEvent(event);

		show.setLanguage(dto.getLanguage());
		show.setPrice(dto.getPrice());
		show.setEighteenPlus(dto.isEighteenPlus());

		show.setShowDate(dto.getShowDate());
		show.setShowStartTime(dto.getShowStartTime());
		show.setShowEndTime(dto.getShowEndTime());

		return showDao.save(show);
	}

	@Override
	public void deleteShowById(Long showId) {

		ShowEntity show = showDao.findById(showId)
				.orElseThrow(() -> new RuntimeException("Show not found"));

		showDao.delete(show);
	}

	@Override
	public List<ShowEntity> findByEvent(Long eventId) {
		return showDao.findByEventEventId(eventId);
	}

	@Override
	public List<ShowEntity> findByDate(LocalDate showDate) {
		return showDao.findByShowDate(showDate);
	}

	@Override
	public List<ShowEntity> findByTime(LocalTime showStartTime) {
		return showDao.findByShowStartTime(showStartTime);
	}
	
	@Override
	public List<VenueShowsDto> getShowsByEvent(Long eventId) {

	    List<ShowEntity> shows = showDao.findByEvent_EventId(eventId);

	    Map<Long, VenueShowsDto> venueMap = new LinkedHashMap<>();

	    for (ShowEntity show : shows) {

	        VenueEntity venue = show.getVenue();

	        VenueShowsDto dto = venueMap.get(venue.getVenueId());

	        if (dto == null) {

	            dto = new VenueShowsDto();

	            dto.setVenueId(venue.getVenueId());
	            dto.setVenueName(venue.getName());
	            dto.setAddress(venue.getAddress());
	            dto.setAreFacilitiesAvailable(
	                    venue.isAreFacilitiesAvailable());

	            dto.setShows(new ArrayList<>());

	            venueMap.put(venue.getVenueId(), dto);
	        }

	        ShowTimingDto timing = new ShowTimingDto();

	        timing.setShowId(show.getShowId());
	        timing.setShowStartTime(show.getShowStartTime());
	        timing.setShowEndTime(show.getShowEndTime());
	        timing.setPrice(show.getPrice());
	        timing.setLanguage(show.getLanguage());
	        timing.setEighteenPlus(show.isEighteenPlus());

	        dto.getShows().add(timing);
	    }

	    return new ArrayList<>(venueMap.values());
	}
}