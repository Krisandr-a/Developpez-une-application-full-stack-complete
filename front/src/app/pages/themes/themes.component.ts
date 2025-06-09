import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss']
})
export class ThemesComponent implements OnInit {
  themes: Array<{ title: string; description: string }> = [];

  constructor() { }

  ngOnInit(): void {
  }

  //test data
//   themes = [
//       {
//         title: 'Ocean Blue',
//         description: 'A cool and calming theme inspired by the ocean.'
//       },
//       {
//         title: 'Sunset Glow',
//         description: 'A warm and vibrant theme with sunset tones.'
//       },
//       {
//         title: 'Forest Whisper',
//         description: 'A nature-based theme with shades of green and brown.'
//       }
//     ];

}
