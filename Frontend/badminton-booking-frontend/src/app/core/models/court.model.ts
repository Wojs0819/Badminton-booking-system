import { Venue } from './venue.model';

export type CourtStatus = 'AVAILABLE' | 'UNAVAILABLE' | 'MAINTENANCE';

// Mirrors badminton_booking_backend.entity.Court
export interface Court {
  id: number;
  venue: Venue;
  courtNumber: number;
  courtName?: string;
  courtType?: string;
  status: CourtStatus;
}
