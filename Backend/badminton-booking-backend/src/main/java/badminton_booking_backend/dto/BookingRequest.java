package badminton_booking_backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;

public record BookingRequest(
        @NotNull(message = "User is required") Long userId,
        @NotNull(message = "Venue is required") Long venueId,
        @NotNull(message = "Court is required") Long courtId,
        @NotNull(message = "Booking date is required") LocalDate bookingDate,
        @NotNull(message = "Start time is required") LocalTime startTime,
        @NotNull(message = "End time is required") LocalTime endTime) {
}
