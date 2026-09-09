package badminton_booking_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import badminton_booking_backend.entity.Reward;
import badminton_booking_backend.entity.User;
import badminton_booking_backend.entity.UserReward;
import badminton_booking_backend.exception.ConflictException;
import badminton_booking_backend.exception.ResourceNotFoundException;
import badminton_booking_backend.repository.RewardRepository;
import badminton_booking_backend.repository.UserRepository;
import badminton_booking_backend.repository.UserRewardRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RewardService {

    private final RewardRepository rewardRepository;
    private final UserRewardRepository userRewardRepository;
    private final UserRepository userRepository;

    public List<Reward> getRewardCatalogue() {
        return rewardRepository.findAll();
    }

    public List<UserReward> getRewardsForUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " was not found"));
        return userRewardRepository.findByUserId(userId);
    }

    public UserReward redeemReward(Long rewardId, Long userId) {
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new ResourceNotFoundException("Reward with ID " + rewardId + " was not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " was not found"));

        if (user.getPoints() < reward.getPointsRequired()) {
            throw new ConflictException("User does not have enough points to redeem this reward");
        }

        user.setPoints(user.getPoints() - reward.getPointsRequired());
        userRepository.save(user);

        UserReward userReward = UserReward.builder()
                .user(user)
                .reward(reward)
                .build();
        return userRewardRepository.save(userReward);
    }
}
