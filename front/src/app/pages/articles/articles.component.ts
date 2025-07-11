import { Component, OnInit } from '@angular/core';
import { ArticlesService, Article } from 'src/app/services/articles.service';
import { SubscriptionService, UserThemeSubscriptionDto } from 'src/app/services/subscription.service';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.scss']
})
export class ArticlesComponent implements OnInit {
  isFilterOpen = false;
  articles: Article[] = [];
  sortDescending = true;


  constructor(
    private articlesService: ArticlesService,
    private subscriptionService: SubscriptionService
  ) { }

  ngOnInit(): void {
    this.loadArticlesForSubscribedThemes();
  }

  toggleFilter() {
    this.isFilterOpen = !this.isFilterOpen;

    this.articles.sort((a, b) =>
      this.sortDescending
        ? new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
        : new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime()
    );

    this.sortDescending = !this.sortDescending;
  }

  private loadArticlesForSubscribedThemes(): void {
    this.subscriptionService.getUserSubscriptions().subscribe({
      next: (subscriptions: UserThemeSubscriptionDto[]) => {
        const themeIds = subscriptions.map(sub => sub.themeId);

        this.articlesService.getAllArticles().subscribe({
          next: (allArticles: Article[]) => {
            this.articles = allArticles.filter(article =>
              themeIds.includes(article.theme.id)
            );
          },
          error: err => console.error('Failed to fetch articles', err)
        });
      },
      error: err => console.error('Failed to fetch user subscriptions', err)
    });
  }



}
