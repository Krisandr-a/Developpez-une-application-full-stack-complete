import { Component, OnInit } from '@angular/core';
import { ThemesService, Theme } from 'src/app/services/themes.service';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss']
})
export class ThemesComponent implements OnInit {
  themes: Theme[] = [];

  constructor(private themesService: ThemesService) { }

  ngOnInit(): void {
    this.themesService.getThemes().subscribe({
      next: (data) => this.themes = data,
      error: (err) => console.error('Error loading themes:', err)
    });
  }

}
