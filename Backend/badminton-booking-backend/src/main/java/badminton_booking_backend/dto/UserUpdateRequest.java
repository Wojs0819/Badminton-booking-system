package badminton_booking_backend.dto;

public record UserUpdateRequest(
        String name,
        String phone,
        String profileImage) {
}
