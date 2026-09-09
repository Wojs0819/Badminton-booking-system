package badminton_booking_backend.exception;

import java.time.LocalDateTime;

// Feature 6: custom error response body returned by GlobalExceptionHandler for all handled error cases
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path) {
}
