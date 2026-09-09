package badminton_booking_backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import badminton_booking_backend.entity.Venue;
import badminton_booking_backend.exception.ResourceNotFoundException;
import badminton_booking_backend.repository.VenueRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepository venueRepository;

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    public Venue getVenue(Long venueId) {
        return venueRepository.findById(venueId)
                .orElseThrow(() -> new ResourceNotFoundException("Venue with ID " + venueId + " was not found"));
    }

    public Venue createVenue(Venue venue) {
        return venueRepository.save(venue);
    }

    public Venue updateVenue(Long venueId, Venue update) {
        Venue venue = getVenue(venueId);
        venue.setName(update.getName());
        venue.setAddress(update.getAddress());
        venue.setDescription(update.getDescription());
        venue.setOpeningTime(update.getOpeningTime());
        venue.setClosingTime(update.getClosingTime());
        venue.setPricePerHour(update.getPricePerHour());
        venue.setImageUrl(update.getImageUrl());
        venue.setLocation(update.getLocation());
        return venueRepository.save(venue);
    }

    public void deleteVenue(Long venueId) {
        Venue venue = getVenue(venueId);
        venueRepository.delete(venue);
    }

    // Uses the derived query VenueRepository.findByLocationContainingIgnoreCaseAndPricePerHourLessThanEqual
    public List<Venue> searchVenues(String location, BigDecimal maxPrice) {
        if (location != null && maxPrice != null) {
            return venueRepository.findByLocationContainingIgnoreCaseAndPricePerHourLessThanEqual(location, maxPrice);
        }
        if (location != null) {
            return venueRepository.findByLocationContainingIgnoreCase(location);
        }
        return venueRepository.findAll();
    }
}
