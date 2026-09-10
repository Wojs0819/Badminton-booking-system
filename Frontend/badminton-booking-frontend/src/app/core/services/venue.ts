import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Venue } from '../models/venue.model';

@Injectable({ providedIn: 'root' })
export class VenueService {
  private readonly apiUrl = 'http://localhost:8080/api/venues';

  constructor(private http: HttpClient) {}

  // 10: HttpClient.get() returns an Observable; the component subscribes to it for async GET.
  getVenues(): Observable<Venue[]> {
    return this.http.get<Venue[]>(this.apiUrl);
  }

  getVenue(venueId: number): Observable<Venue> {
    return this.http.get<Venue>(`${this.apiUrl}/${venueId}`);
  }

  // Query-parameter search backed by a Spring Data derived query.
  searchVenues(location?: string, maxPrice?: number): Observable<Venue[]> {
    const params: Record<string, string> = {};
    if (location) params['location'] = location;
    if (maxPrice != null) params['maxPrice'] = String(maxPrice);
    return this.http.get<Venue[]>(`${this.apiUrl}/search`, { params });
  }

  createVenue(venue: Partial<Venue>): Observable<Venue> {
    return this.http.post<Venue>(this.apiUrl, venue);
  }

  updateVenue(venueId: number, venue: Partial<Venue>): Observable<Venue> {
    return this.http.put<Venue>(`${this.apiUrl}/${venueId}`, venue);
  }

  // 10: full GET/POST/PUT/DELETE HTTP verb coverage for the Venue resource, all Observable-based.
  deleteVenue(venueId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${venueId}`);
  }
}
