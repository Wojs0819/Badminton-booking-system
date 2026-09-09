package badminton_booking_backend.dto;

import java.util.List;

public record CourtAvailability(Long courtId, String courtName, List<TimeRange> bookedSlots) {
}
