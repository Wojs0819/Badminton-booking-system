import { User } from './user.model';
import { Venue } from './venue.model';

export type CompetitionStatus = 'OPEN' | 'FULL' | 'CLOSED';

// Mirrors badminton_booking_backend.entity.Competition
export interface Competition {
  id: number;
  creator: User;
  name: string;
  description?: string;
  venue: Venue;
  competitionDate: string;
  registrationDeadline: string;
  skillLevel?: string;
  maxParticipants: number;
  status: CompetitionStatus;
}

// Mirrors badminton_booking_backend.entity.CompetitionParticipant
export interface CompetitionParticipant {
  id: number;
  competition: Competition;
  user: User;
  joinedAt: string;
}

// Mirrors badminton_booking_backend.dto.CompetitionRequest
export interface CompetitionRequest {
  creatorId: number;
  name: string;
  description?: string;
  venueId: number;
  competitionDate: string;
  registrationDeadline: string;
  skillLevel?: string;
  maxParticipants: number;
}
