import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { SubscriptionService } from 'src/app/services/subscription.service';

@Component({
  selector: 'app-theme-card',
  templateUrl: './theme-card.component.html',
  styleUrls: ['./theme-card.component.scss']
})
export class ThemeCardComponent implements OnInit, OnChanges {
  @Input() themeId!: number;
  @Input() title!: string;
  @Input() description!: string;
  @Input() subscribed = false;
  @Input() unsubscribeMode = false;

  isSubscribed = false;
  loading = false;

  constructor(private subscriptionService: SubscriptionService) { }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['subscribed']) {
      this.isSubscribed = changes['subscribed'].currentValue;
    }
  }

  subscribe(): void {
    this.loading = true;
    this.subscriptionService.subscribeToTheme(this.themeId).subscribe({
      next: () => {
        this.isSubscribed = true;
        this.loading = false;
      },
      error: (err) => {
        console.error('Subscription failed:', err);
        this.loading = false;
      }
    });
  }

  unsubscribe(): void {
    this.loading = true;
    this.subscriptionService.unsubscribeFromTheme(this.themeId).subscribe({
      next: () => {
        alert('Vous vous êtes désabonné avec succès.');
        this.isSubscribed = false;
        this.loading = false;
        window.location.reload();
      },
      error: (err) => {
        console.error('Failed to unsubscribe from theme:', err);
        alert('La désinscription a échoué. Veuillez réessayer plus tard.');
        this.loading = false;
      }
    });
  }

}
