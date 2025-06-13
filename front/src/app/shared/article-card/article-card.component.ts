import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-article-card',
  templateUrl: './article-card.component.html',
  styleUrls: ['./article-card.component.scss']
})
export class ArticleCardComponent implements OnInit {
  @Input() title!: string;
  @Input() date!: Date | string;
  @Input() author!: string;
  @Input() content!: string;
  @Input() id!: number;

  constructor() { }

  ngOnInit(): void {
  }

}
