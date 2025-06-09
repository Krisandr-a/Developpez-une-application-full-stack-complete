import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Theme {
  title: string;
  description: string;
}

@Injectable({
  providedIn: 'root'
})
export class ThemesService {

  private baseUrl = 'http://localhost:8081/api/themes';

  constructor(private http: HttpClient) { }

  getThemes(): Observable<Theme[]> {
    return this.http.get<Theme[]>(this.baseUrl);
  }

}
