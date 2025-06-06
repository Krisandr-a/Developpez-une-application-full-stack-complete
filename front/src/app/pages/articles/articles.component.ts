import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.scss']
})
export class ArticlesComponent implements OnInit {
    isFilterOpen = false;

  constructor() { }

  ngOnInit(): void {
  }

  toggleFilter() {
    this.isFilterOpen = !this.isFilterOpen;
  }

}
