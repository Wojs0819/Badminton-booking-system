package badminton_booking_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import badminton_booking_backend.entity.Reward;

// Feature 4: extending JpaRepository provides full CRUD (+ sorting) for Reward out of the box, no extra queries needed.
public interface RewardRepository extends JpaRepository<Reward, Long> {
}
