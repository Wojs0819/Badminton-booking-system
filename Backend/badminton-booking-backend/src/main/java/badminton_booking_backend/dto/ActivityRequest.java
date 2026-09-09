package badminton_booking_backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActivityRequest(
        @NotNull(message = "Creator is required") Long creatorId,
        @NotBlank(message = "Title is required") String title,
        String description,
        @NotNull(message = "Venue is required") Long venueId,
        @NotNull(message = "Activity date is required") LocalDate activityDate,
        @NotNull(message = "Start time is required") LocalTime startTime,
        @NotNull(message = "End time is required") LocalTime endTime,
        String skillLevel,
        @Min(value = 2, message = "At least 2 participants required") Integer maxParticipants) {
}
