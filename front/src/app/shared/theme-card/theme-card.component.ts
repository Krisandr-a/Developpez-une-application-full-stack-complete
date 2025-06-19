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

}
