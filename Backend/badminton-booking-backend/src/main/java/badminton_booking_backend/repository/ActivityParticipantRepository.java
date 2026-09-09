package badminton_booking_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import badminton_booking_backend.entity.ActivityParticipant;

// Feature 4: extending JpaRepository provides CRUD operations for ActivityParticipant out of the box.
public interface ActivityParticipantRepository extends JpaRepository<ActivityParticipant, Long> {

    // Feature 5: derived query
    List<ActivityParticipant> findByActivityId(Long activityId);

    // Feature 5: derived query with two conditions combined via method name
    Optional<ActivityParticipant> findByActivityIdAndUserId(Long activityId, Long userId);

    // Feature 5: derived count query
    long countByActivityId(Long activityId);
}
