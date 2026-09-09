package badminton_booking_backend.exception;

// Feature 6: custom exception mapped to HTTP 404 by GlobalExceptionHandler
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
