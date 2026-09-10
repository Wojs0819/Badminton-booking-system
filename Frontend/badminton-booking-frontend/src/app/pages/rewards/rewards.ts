import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Reward } from '../../core/models/reward.model';
import { AuthService } from '../../core/services/auth';
import { RewardService } from '../../core/services/reward';
import { UserService } from '../../core/services/user';

@Component({
  imports: [],
  selector: 'app-rewards',
  styleUrl: './rewards.css',
  templateUrl: './rewards.html',
})
export class Rewards implements OnInit {
  rewards: Reward[] = [];
  userPoints = 0;
  loading = true;
  message = '';

  constructor(private rewardService: RewardService, private auth: AuthService, private userService: UserService, private cdr: ChangeDetectorRef) {}

  get currentUserId(): number | null {
    return this.auth.currentUser()?.id ?? null;
  }

  ngOnInit(): void {
    this.rewardService.getRewardCatalogue().subscribe({
      next: (rewards) => {
        this.rewards = rewards;
        this.loading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        this.loading = false;
        this.cdr.markForCheck();
      },
    });

    this.refreshPoints();
  }

  private refreshPoints(): void {
    if (this.currentUserId) {
      this.userService.getUser(this.currentUserId).subscribe((user) => {
        this.userPoints = user.points;
        this.cdr.markForCheck();
      });
    }
  }

  // A3/A2: event binding + [disabled] property binding based on the user's points.
  redeem(reward: Reward): void {
    if (!this.currentUserId || this.userPoints < reward.pointsRequired) return;
    this.message = '';
    this.rewardService.redeemReward(reward.id, this.currentUserId).subscribe({
      next: () => {
        this.message = `Redeemed "${reward.name}"!`;
        this.refreshPoints();
      },
      error: (err) => (this.message = err.error?.message || 'Unable to redeem reward.'),
    });
  }
}
