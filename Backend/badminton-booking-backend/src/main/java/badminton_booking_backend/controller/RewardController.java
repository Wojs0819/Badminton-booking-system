package badminton_booking_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import badminton_booking_backend.dto.JoinRequest;
import badminton_booking_backend.entity.Reward;
import badminton_booking_backend.entity.UserReward;
import badminton_booking_backend.service.RewardService;
import lombok.RequiredArgsConstructor;

// Feature 1 & 2: Reward catalogue + redemption endpoints using @RestController/@GetMapping/@PostMapping.
@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
public class RewardController {

    private final RewardService rewardService;

    // Feature 4: CRUD Read-all
    @GetMapping
    public List<Reward> getRewardCatalogue() {
        return rewardService.getRewardCatalogue();
    }

    // Feature 1: path variable identifies the reward being redeemed
    @PostMapping("/{rewardId}/redeem")
    public UserReward redeemReward(@PathVariable Long rewardId, @RequestBody JoinRequest request) {
        return rewardService.redeemReward(rewardId, request.userId());
    }
}
