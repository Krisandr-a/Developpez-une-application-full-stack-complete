import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface UserThemeSubscriptionDto {
  themeId: number;
  themeTitle: string;
  subscribedAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {
  private baseUrl = 'http://localhost:8081/api/subscriptions';

  constructor(private http: HttpClient) {}

  subscribeToTheme(themeId: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/subscribe?themeId=${themeId}`, {});
  }

  getUserSubscriptions(): Observable<UserThemeSubscriptionDto[]> {
    return this.http.get<UserThemeSubscriptionDto[]>(`${this.baseUrl}`);
  }
}
