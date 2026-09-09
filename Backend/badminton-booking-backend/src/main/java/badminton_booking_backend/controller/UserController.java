package badminton_booking_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import badminton_booking_backend.dto.UserUpdateRequest;
import badminton_booking_backend.entity.Booking;
import badminton_booking_backend.entity.User;
import badminton_booking_backend.entity.UserReward;
import badminton_booking_backend.service.BookingService;
import badminton_booking_backend.service.RewardService;
import badminton_booking_backend.service.UserService;
import lombok.RequiredArgsConstructor;

// Feature 1 & 2: User REST endpoints, plus nested sub-resources (bookings, rewards) using path variables.
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BookingService bookingService;
    private final RewardService rewardService;

    // Feature 1 & 4: path variable + CRUD Read-by-id
    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    // Feature 4: CRUD Update
    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    // Feature 4: CRUD Delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    // Feature 1 & 5: nested resource (path variable) backed by a derived query, BookingRepository.findByUserIdOrderByBookingDateDesc
    @GetMapping("/{userId}/bookings")
    public List<Booking> getUserBookings(@PathVariable Long userId) {
        return bookingService.getBookingsForUser(userId);
    }

    // Feature 1 & 4: nested resource (path variable) + CRUD Read-all for this user's redeemed rewards
    @GetMapping("/{userId}/rewards")
    public List<UserReward> getUserRewards(@PathVariable Long userId) {
        return rewardService.getRewardsForUser(userId);
    }
}
