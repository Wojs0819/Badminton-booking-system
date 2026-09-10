import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Reward, UserReward } from '../models/reward.model';
import { JoinRequest } from '../models/common.model';

@Injectable({ providedIn: 'root' })
export class RewardService {
  private readonly apiUrl = 'http://localhost:8080/api/rewards';

  constructor(private http: HttpClient) {}

  // 10: GET returns an Observable<Reward[]> for the reward catalogue.
  getRewardCatalogue(): Observable<Reward[]> {
    return this.http.get<Reward[]>(this.apiUrl);
  }

  redeemReward(rewardId: number, userId: number): Observable<UserReward> {
    const request: JoinRequest = { userId };
    return this.http.post<UserReward>(`${this.apiUrl}/${rewardId}/redeem`, request);
  }
}
