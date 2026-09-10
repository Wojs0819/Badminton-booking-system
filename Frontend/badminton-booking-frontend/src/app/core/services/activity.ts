import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Activity, ActivityParticipant, ActivityRequest } from '../models/activity.model';
import { JoinRequest } from '../models/common.model';

@Injectable({ providedIn: 'root' })
export class ActivityService {
  private readonly apiUrl = 'http://localhost:8080/api/activities';

  constructor(private http: HttpClient) {}

  // 10: HttpClient GET returning an Observable<Activity[]> for asynchronous network interaction.
  getActivities(): Observable<Activity[]> {
    return this.http.get<Activity[]>(this.apiUrl);
  }

  // Query-parameter search (?level=&date=) backed by a Spring Data derived query.
  searchActivities(level?: string, date?: string): Observable<Activity[]> {
    const params: Record<string, string> = {};
    if (level) params['level'] = level;
    if (date) params['date'] = date;
    return this.http.get<Activity[]>(`${this.apiUrl}/search`, { params });
  }

  getActivity(activityId: number): Observable<Activity> {
    return this.http.get<Activity>(`${this.apiUrl}/${activityId}`);
  }

  createActivity(request: ActivityRequest): Observable<Activity> {
    return this.http.post<Activity>(this.apiUrl, request);
  }

  updateActivity(activityId: number, request: ActivityRequest): Observable<Activity> {
    return this.http.put<Activity>(`${this.apiUrl}/${activityId}`, request);
  }

  deleteActivity(activityId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${activityId}`);
  }

  joinActivity(activityId: number, userId: number): Observable<ActivityParticipant> {
    const request: JoinRequest = { userId };
    return this.http.post<ActivityParticipant>(`${this.apiUrl}/${activityId}/participants`, request);
  }

  leaveActivity(activityId: number, userId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${activityId}/participants/${userId}`);
  }
}
