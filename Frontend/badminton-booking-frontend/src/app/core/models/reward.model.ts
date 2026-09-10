import { User } from './user.model';

// Mirrors badminton_booking_backend.entity.Reward
export interface Reward {
  id: number;
  name: string;
  description?: string;
  pointsRequired: number;
  imageUrl?: string;
}

// Mirrors badminton_booking_backend.entity.UserReward
export interface UserReward {
  id: number;
  user: User;
  reward: Reward;
  redeemedAt: string;
}
