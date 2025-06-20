import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Comment {
  id: number;
  content: string;
  created_at: string;
  user: string;
  articleId: number;
}


@Injectable({
  providedIn: 'root',
})
export class CommentsService {
  private baseUrl = 'http://localhost:8081/api/articles';

  constructor(private http: HttpClient) {}

  getComments(articleId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.baseUrl}/${articleId}/comments`);
  }

  addComment(articleId: number, content: string): Observable<Comment> {
    return this.http.post<Comment>(`${this.baseUrl}/${articleId}/comments`, { content });
  }

}
