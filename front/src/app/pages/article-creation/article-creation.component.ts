import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ThemesService, Theme } from 'src/app/services/themes.service';
import { ArticlesService, Article } from 'src/app/services/articles.service'; // adjust path

@Component({
  selector: 'app-article-creation',
  templateUrl: './article-creation.component.html',
  styleUrls: ['./article-creation.component.scss']
})
export class ArticleCreationComponent implements OnInit {
  articleForm!: FormGroup;
  isFormDisabled = false;
  themes: Theme[] = [];

//   themes = ['Santé publique', 'Prévention', 'Innovation']; // example themes

  constructor(
    private fb: FormBuilder,
    private themesService: ThemesService,
    private articlesService: ArticlesService
  ) {}

  ngOnInit(): void {
    this.articleForm = this.fb.group({
      theme: ['', Validators.required],
      title: ['', Validators.required],
      content: ['', Validators.required]
    });

    this.themesService.getThemes().subscribe({
      next: (data) => this.themes = data,
      error: (err) => console.error('Erreur lors du chargement des thèmes :', err)
    });
  }

   onSubmit() {
     if (this.articleForm.valid) {
       const formValue = this.articleForm.value;

       const articleToSend = {
         title: formValue.title,
         content: formValue.content,
         themeId: formValue.theme.id
       };

       this.isFormDisabled = true;
       this.articlesService.createArticle(articleToSend).subscribe({
         next: (response) => {
           alert('Article créé avec succès !');
           this.articleForm.reset();
           this.isFormDisabled = false;
         },
         error: (err) => {
           alert('Erreur lors de la création de l\'article.');
           this.isFormDisabled = false;
         }
       });
     }
   }

}
