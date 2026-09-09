package badminton_booking_backend.exception;

/** Feature 6: custom exception mapped to HTTP 409 Conflict, e.g. duplicate email or double-booking. */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
