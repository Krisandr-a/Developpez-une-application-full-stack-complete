import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Article {
  id: number;
  title: string;
  content: string;
  createdAt: string;
  author: {
    name: string;
  };
  theme: {
    id: number;
    title: string;
    description: string;
  };
}

@Injectable({
  providedIn: 'root'
})
export class ArticlesService {

  private baseUrl = 'http://localhost:8081/api/articles';

  constructor(private http: HttpClient) { }

  getAllArticles(): Observable<Article[]> {
    return this.http.get<Article[]>(this.baseUrl);
  }
}
