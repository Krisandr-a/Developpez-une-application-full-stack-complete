import { Component, OnInit } from '@angular/core';
import { ThemesService, Theme } from 'src/app/services/themes.service';
import { SubscriptionService } from 'src/app/services/subscription.service';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss']
})
export class ThemesComponent implements OnInit {
  themes: Theme[] = [];
  subscribedThemeIds: Set<number> = new Set();

  constructor(
    private themesService: ThemesService,
    private subscriptionService: SubscriptionService
    ) { }

  ngOnInit(): void {
    this.themesService.getThemes().subscribe({
      next: (data) => {
        this.themes = data;
      },
      error: (err) => console.error('Error loading themes:', err)
    });

    this.subscriptionService.getUserSubscriptions().subscribe({
      next: (subs) => {
        this.subscribedThemeIds = new Set(subs.map((s) => s.themeId));
      },
      error: (err) => console.error('Error loading subscriptions:', err)
    });

  }

}
