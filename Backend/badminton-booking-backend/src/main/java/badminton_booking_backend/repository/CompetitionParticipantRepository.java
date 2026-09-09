package badminton_booking_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import badminton_booking_backend.entity.CompetitionParticipant;

// Feature 4: extending JpaRepository provides CRUD operations for CompetitionParticipant out of the box.
public interface CompetitionParticipantRepository extends JpaRepository<CompetitionParticipant, Long> {

    // Feature 5: derived query
    List<CompetitionParticipant> findByCompetitionId(Long competitionId);

    // Feature 5: derived query with two conditions combined via method name
    Optional<CompetitionParticipant> findByCompetitionIdAndUserId(Long competitionId, Long userId);

    // Feature 5: derived count query
    long countByCompetitionId(Long competitionId);
}
