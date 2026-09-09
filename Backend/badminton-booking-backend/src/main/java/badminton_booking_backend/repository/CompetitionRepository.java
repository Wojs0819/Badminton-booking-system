package badminton_booking_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import badminton_booking_backend.entity.Competition;

// Feature 4: extending JpaRepository provides CRUD operations for Competition out of the box.
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    // Feature 5: derived query
    List<Competition> findBySkillLevel(String skillLevel);
}
