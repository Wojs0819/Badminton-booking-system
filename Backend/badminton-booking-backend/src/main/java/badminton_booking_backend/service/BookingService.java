package badminton_booking_backend.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import badminton_booking_backend.dto.BookingRequest;
import badminton_booking_backend.dto.CourtAvailability;
import badminton_booking_backend.dto.TimeRange;
import badminton_booking_backend.entity.Booking;
import badminton_booking_backend.entity.Court;
import badminton_booking_backend.entity.User;
import badminton_booking_backend.entity.Venue;
import badminton_booking_backend.exception.ConflictException;
import badminton_booking_backend.exception.ResourceNotFoundException;
import badminton_booking_backend.repository.BookingRepository;
import badminton_booking_backend.repository.CourtRepository;
import badminton_booking_backend.repository.UserRepository;
import badminton_booking_backend.repository.VenueRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final VenueRepository venueRepository;
    private final CourtRepository courtRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBooking(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking with ID " + bookingId + " was not found"));
    }

    // Derived query used to list bookings for a specific user, most recent first
    public List<Booking> getBookingsForUser(Long userId) {
        return bookingRepository.findByUserIdOrderByBookingDateDesc(userId);
    }

    public Booking createBooking(BookingRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + request.userId() + " was not found"));
        Venue venue = venueRepository.findById(request.venueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue with ID " + request.venueId() + " was not found"));
        Court court = courtRepository.findById(request.courtId())
                .orElseThrow(() -> new ResourceNotFoundException("Court with ID " + request.courtId() + " was not found"));

        if (!request.endTime().isAfter(request.startTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        boolean overlaps = bookingRepository.findBookingsForVenueAndDate(request.venueId(), request.bookingDate())
                .stream()
                .filter(b -> b.getCourt().getId().equals(request.courtId()))
                .filter(b -> b.getStatus() != Booking.Status.CANCELLED)
                .anyMatch(b -> b.getStartTime().isBefore(request.endTime()) && request.startTime().isBefore(b.getEndTime()));
        if (overlaps) {
            throw new ConflictException("Court " + court.getCourtNumber() + " is already booked for the selected time slot");
        }

        double hours = Duration.between(request.startTime(), request.endTime()).toMinutes() / 60.0;
        BigDecimal totalPrice = venue.getPricePerHour().multiply(BigDecimal.valueOf(hours));

        Booking booking = Booking.builder()
                .user(user)
                .venue(venue)
                .court(court)
                .bookingDate(request.bookingDate())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .status(Booking.Status.CONFIRMED)
                .totalPrice(totalPrice)
                .build();

        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Long bookingId, BookingRequest request) {
        Booking booking = getBooking(bookingId);
        Court court = courtRepository.findById(request.courtId())
                .orElseThrow(() -> new ResourceNotFoundException("Court with ID " + request.courtId() + " was not found"));

        booking.setCourt(court);
        booking.setBookingDate(request.bookingDate());
        booking.setStartTime(request.startTime());
        booking.setEndTime(request.endTime());
        return bookingRepository.save(booking);
    }

    public void cancelBooking(Long bookingId) {
        Booking booking = getBooking(bookingId);
        bookingRepository.delete(booking);
    }

    // Demonstrates a real backend query (JPQL) driving availability rather than hard-coded data
    public List<CourtAvailability> getAvailability(Long venueId, LocalDate date) {
        venueRepository.findById(venueId)
                .orElseThrow(() -> new ResourceNotFoundException("Venue with ID " + venueId + " was not found"));

        List<Booking> bookings = bookingRepository.findBookingsForVenueAndDate(venueId, date).stream()
                .filter(b -> b.getStatus() != Booking.Status.CANCELLED)
                .toList();

        List<Court> courts = courtRepository.findByVenueId(venueId);

        return courts.stream()
                .map(court -> new CourtAvailability(
                        court.getId(),
                        court.getCourtName(),
                        bookings.stream()
                                .filter(b -> b.getCourt().getId().equals(court.getId()))
                                .map(b -> new TimeRange(b.getStartTime(), b.getEndTime()))
                                .collect(Collectors.toList())))
                .toList();
    }
}
