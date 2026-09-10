import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Booking, BookingRequest, CourtAvailability } from '../models/booking.model';

@Injectable({ providedIn: 'root' })
export class BookingService {
  private readonly apiUrl = 'http://localhost:8080/api/bookings';

  constructor(private http: HttpClient) {}

  // 10: Observable-returning GET; caller subscribes to receive the async response.
  getBookings(): Observable<Booking[]> {
    return this.http.get<Booking[]>(this.apiUrl);
  }

  getBooking(bookingId: number): Observable<Booking> {
    return this.http.get<Booking>(`${this.apiUrl}/${bookingId}`);
  }

  // Query parameters drive a JPQL query on the backend (bookings for a venue/date).
  getAvailability(venueId: number, date: string): Observable<CourtAvailability[]> {
    return this.http.get<CourtAvailability[]>(`${this.apiUrl}/availability`, {
      params: { venueId, date },
    });
  }

  createBooking(request: BookingRequest): Observable<Booking> {
    return this.http.post<Booking>(this.apiUrl, request);
  }

  updateBooking(bookingId: number, request: BookingRequest): Observable<Booking> {
    return this.http.put<Booking>(`${this.apiUrl}/${bookingId}`, request);
  }

  cancelBooking(bookingId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${bookingId}`);
  }
}
