package badminton_booking_backend.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CompetitionRequest(
        @NotNull(message = "Creator is required") Long creatorId,
        @NotBlank(message = "Name is required") String name,
        String description,
        @NotNull(message = "Venue is required") Long venueId,
        @NotNull(message = "Competition date is required") LocalDate competitionDate,
        @NotNull(message = "Registration deadline is required") LocalDate registrationDeadline,
        String skillLevel,
        @Min(value = 2, message = "At least 2 participants required") Integer maxParticipants) {
}
