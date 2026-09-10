import { Court } from './court.model';
import { User } from './user.model';
import { Venue } from './venue.model';

export type BookingStatus = 'PENDING' | 'CONFIRMED' | 'CANCELLED';

// Mirrors badminton_booking_backend.entity.Booking
export interface Booking {
  id: number;
  user: User;
  venue: Venue;
  court: Court;
  bookingDate: string;
  startTime: string;
  endTime: string;
  status: BookingStatus;
  totalPrice: number;
  createdAt: string;
}

// Mirrors badminton_booking_backend.dto.BookingRequest
export interface BookingRequest {
  userId: number;
  venueId: number;
  courtId: number;
  bookingDate: string;
  startTime: string;
  endTime: string;
}

// Mirrors badminton_booking_backend.dto.TimeRange
export interface TimeRange {
  startTime: string;
  endTime: string;
}

// Mirrors badminton_booking_backend.dto.CourtAvailability
export interface CourtAvailability {
  courtId: number;
  courtName: string;
  bookedSlots: TimeRange[];
}
