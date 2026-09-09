package badminton_booking_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import badminton_booking_backend.entity.Court;

// Feature 4: extending JpaRepository provides CRUD operations for Court out of the box.
public interface CourtRepository extends JpaRepository<Court, Long> {

    // Feature 5: derived query used to list courts for a venue
    List<Court> findByVenueId(Long venueId);
}
