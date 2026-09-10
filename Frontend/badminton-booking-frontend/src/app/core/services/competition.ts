import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Competition, CompetitionParticipant, CompetitionRequest } from '../models/competition.model';
import { JoinRequest } from '../models/common.model';

@Injectable({ providedIn: 'root' })
export class CompetitionService {
  private readonly apiUrl = 'http://localhost:8080/api/competitions';

  constructor(private http: HttpClient) {}

  // 10: Observable-based GET via HttpClient.
  getCompetitions(): Observable<Competition[]> {
    return this.http.get<Competition[]>(this.apiUrl);
  }

  getCompetition(competitionId: number): Observable<Competition> {
    return this.http.get<Competition>(`${this.apiUrl}/${competitionId}`);
  }

  createCompetition(request: CompetitionRequest): Observable<Competition> {
    return this.http.post<Competition>(this.apiUrl, request);
  }

  updateCompetition(competitionId: number, request: CompetitionRequest): Observable<Competition> {
    return this.http.put<Competition>(`${this.apiUrl}/${competitionId}`, request);
  }

  deleteCompetition(competitionId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${competitionId}`);
  }

  joinCompetition(competitionId: number, userId: number): Observable<CompetitionParticipant> {
    const request: JoinRequest = { userId };
    return this.http.post<CompetitionParticipant>(`${this.apiUrl}/${competitionId}/participants`, request);
  }

  leaveCompetition(competitionId: number, userId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${competitionId}/participants/${userId}`);
  }
}
