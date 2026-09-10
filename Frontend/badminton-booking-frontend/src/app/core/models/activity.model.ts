import { User } from './user.model';
import { Venue } from './venue.model';

export type ActivityStatus = 'OPEN' | 'FULL' | 'CLOSED';

// Mirrors badminton_booking_backend.entity.Activity
export interface Activity {
  id: number;
  creator: User;
  title: string;
  description?: string;
  venue: Venue;
  activityDate: string;
  startTime: string;
  endTime: string;
  skillLevel?: string;
  maxParticipants: number;
  status: ActivityStatus;
}

// Mirrors badminton_booking_backend.entity.ActivityParticipant
export interface ActivityParticipant {
  id: number;
  activity: Activity;
  user: User;
  joinedAt: string;
}

// Mirrors badminton_booking_backend.dto.ActivityRequest
export interface ActivityRequest {
  creatorId: number;
  title: string;
  description?: string;
  venueId: number;
  activityDate: string;
  startTime: string;
  endTime: string;
  skillLevel?: string;
  maxParticipants: number;
}
