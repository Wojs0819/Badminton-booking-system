package badminton_booking_backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import badminton_booking_backend.entity.Activity;

// Feature 4: extending JpaRepository provides CRUD operations for Activity out of the box.
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    // Feature 5: derived query (two conditions combined via method name) used by GET /api/activities/search
    List<Activity> findBySkillLevelAndActivityDate(String skillLevel, LocalDate activityDate);

    // Feature 5: derived query
    List<Activity> findBySkillLevel(String skillLevel);

    // Feature 5: derived query
    List<Activity> findByActivityDate(LocalDate activityDate);
}
