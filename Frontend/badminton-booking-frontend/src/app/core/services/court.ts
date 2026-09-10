import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Court } from '../models/court.model';

@Injectable({ providedIn: 'root' })
export class CourtService {
  private readonly apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  // 10: Observable-based GET via HttpClient.
  getCourtsForVenue(venueId: number): Observable<Court[]> {
    return this.http.get<Court[]>(`${this.apiUrl}/venues/${venueId}/courts`);
  }

  getCourt(courtId: number): Observable<Court> {
    return this.http.get<Court>(`${this.apiUrl}/courts/${courtId}`);
  }

  createCourt(venueId: number, court: Partial<Court>): Observable<Court> {
    return this.http.post<Court>(`${this.apiUrl}/venues/${venueId}/courts`, court);
  }

  updateCourt(courtId: number, court: Partial<Court>): Observable<Court> {
    return this.http.put<Court>(`${this.apiUrl}/courts/${courtId}`, court);
  }

  deleteCourt(courtId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/courts/${courtId}`);
  }
}
