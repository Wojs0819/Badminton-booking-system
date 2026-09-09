package badminton_booking_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import badminton_booking_backend.entity.Court;
import badminton_booking_backend.entity.Venue;
import badminton_booking_backend.exception.ResourceNotFoundException;
import badminton_booking_backend.repository.CourtRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourtService {

    private final CourtRepository courtRepository;
    private final VenueService venueService;

    public List<Court> getCourtsForVenue(Long venueId) {
        venueService.getVenue(venueId);
        return courtRepository.findByVenueId(venueId);
    }

    public Court getCourt(Long courtId) {
        return courtRepository.findById(courtId)
                .orElseThrow(() -> new ResourceNotFoundException("Court with ID " + courtId + " was not found"));
    }

    public Court createCourt(Long venueId, Court court) {
        Venue venue = venueService.getVenue(venueId);
        court.setVenue(venue);
        return courtRepository.save(court);
    }

    public Court updateCourt(Long courtId, Court update) {
        Court court = getCourt(courtId);
        court.setCourtNumber(update.getCourtNumber());
        court.setCourtName(update.getCourtName());
        court.setCourtType(update.getCourtType());
        court.setStatus(update.getStatus());
        return courtRepository.save(court);
    }

    public void deleteCourt(Long courtId) {
        Court court = getCourt(courtId);
        courtRepository.delete(court);
    }
}
