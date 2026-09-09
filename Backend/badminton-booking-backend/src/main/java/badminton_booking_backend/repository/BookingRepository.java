package badminton_booking_backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import badminton_booking_backend.entity.Booking;

// Feature 4: extending JpaRepository provides CRUD operations for Booking out of the box.
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Feature 5: derived query (Spring Data method-name query) used by GET /api/users/{userId}/bookings
    List<Booking> findByUserIdOrderByBookingDateDesc(Long userId);

    // Feature 5: JPQL query (@Query) used by GET /api/bookings/availability to determine occupied courts/time slots
    @Query("""
        SELECT b
        FROM Booking b
        WHERE b.venue.id = :venueId
          AND b.bookingDate = :bookingDate
        """)
    List<Booking> findBookingsForVenueAndDate(
            @Param("venueId") Long venueId,
            @Param("bookingDate") LocalDate bookingDate);
}
