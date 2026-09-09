package badminton_booking_backend.dto;

import java.time.LocalTime;

public record TimeRange(LocalTime startTime, LocalTime endTime) {
}
