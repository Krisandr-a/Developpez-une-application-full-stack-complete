import { Component, OnInit } from '@angular/core';
import { ArticlesService, Article } from 'src/app/services/articles.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-article-detail',
  templateUrl: './article-detail.component.html',
  styleUrls: ['./article-detail.component.scss']
})
export class ArticleDetailComponent implements OnInit {
  article!: Article;

  constructor(
    private route: ActivatedRoute,
    private articlesService: ArticlesService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const articleId = Number(this.route.snapshot.paramMap.get('id'));
    this.articlesService.getArticleById(articleId).subscribe((data) => {
      this.article = data;
    });
  }

  goBack(): void {
    this.router.navigate(['/articles']);
  }

}
