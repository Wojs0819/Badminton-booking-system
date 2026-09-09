package badminton_booking_backend.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import badminton_booking_backend.entity.Venue;

// Feature 4: extending JpaRepository provides CRUD (+ sorting) operations for Venue out of the box.
public interface VenueRepository extends JpaRepository<Venue, Long> {

    // Feature 5: derived query (method name parsed by Spring Data into a WHERE clause), used by GET /api/venues/search
    List<Venue> findByLocationContainingIgnoreCaseAndPricePerHourLessThanEqual(String location, BigDecimal maxPrice);

    // Feature 5: derived query
    List<Venue> findByLocationContainingIgnoreCase(String location);
}
