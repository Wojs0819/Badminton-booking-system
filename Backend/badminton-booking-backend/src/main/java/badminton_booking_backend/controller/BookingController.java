package badminton_booking_backend.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import badminton_booking_backend.dto.BookingRequest;
import badminton_booking_backend.dto.CourtAvailability;
import badminton_booking_backend.entity.Booking;
import badminton_booking_backend.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// Feature 1 & 2: Booking REST endpoints implemented with @RestController/@RequestMapping and HTTP-verb annotations.
@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // Feature 4: CRUD Read-all
    @GetMapping
    public List<Booking> getBookings() {
        return bookingService.getAllBookings();
    }

    // Feature 1 & 5: query parameters (?venueId=&date=) driving a JPQL query (BookingRepository.findBookingsForVenueAndDate)
    @GetMapping("/availability")
    public List<CourtAvailability> getAvailability(
            @RequestParam Long venueId,
            @RequestParam LocalDate date) {
        return bookingService.getAvailability(venueId, date);
    }

    // Feature 1 & 4: path variable + CRUD Read-by-id
    @GetMapping("/{bookingId}")
    public Booking getBooking(@PathVariable Long bookingId) {
        return bookingService.getBooking(bookingId);
    }

    // Feature 4: CRUD Create
    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(request));
    }

    // Feature 4: CRUD Update
    @PutMapping("/{bookingId}")
    public Booking updateBooking(@PathVariable Long bookingId, @Valid @RequestBody BookingRequest request) {
        return bookingService.updateBooking(bookingId, request);
    }

    // Feature 4: CRUD Delete
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}
