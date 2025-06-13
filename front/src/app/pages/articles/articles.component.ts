import { Component, OnInit } from '@angular/core';
import { ArticlesService, Article } from 'src/app/services/articles.service';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.scss']
})
export class ArticlesComponent implements OnInit {
    isFilterOpen = false;
    articles: Article[] = [];

  constructor(private articlesService: ArticlesService) { }

  ngOnInit(): void {
    this.articlesService.getAllArticles().subscribe({
      next: (data) => this.articles = data,
      error: (err) => console.error('Failed to fetch articles', err)
    });
  }

  toggleFilter() {
    this.isFilterOpen = !this.isFilterOpen;
  }

}
