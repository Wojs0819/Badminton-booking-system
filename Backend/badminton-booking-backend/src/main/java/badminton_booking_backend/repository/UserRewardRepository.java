package badminton_booking_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import badminton_booking_backend.entity.UserReward;

// Feature 4: extending JpaRepository provides CRUD operations for UserReward out of the box.
public interface UserRewardRepository extends JpaRepository<UserReward, Long> {

    // Feature 5: derived query used by GET /api/users/{userId}/rewards
    List<UserReward> findByUserId(Long userId);

    // Feature 5: derived query with two conditions combined via method name
    Optional<UserReward> findByUserIdAndRewardId(Long userId, Long rewardId);
}
