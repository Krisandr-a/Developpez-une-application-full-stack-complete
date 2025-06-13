import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';

import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
JwtInterceptor

import { ArticlesComponent } from './pages/articles/articles.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { ArticleCardComponent } from './shared/article-card/article-card.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { JwtInterceptor } from 'src/app/auth/interceptors/jwt.interceptor';
import { ThemeCardComponent } from './shared/theme-card/theme-card.component';
import { ThemesComponent } from './pages/themes/themes.component';
import { ArticleDetailComponent } from './pages/article-detail/article-detail.component';
import { ArticleCreationComponent } from './pages/article-creation/article-creation.component';


@NgModule({
  declarations: [AppComponent, HomeComponent, RegisterComponent, LoginComponent, ArticlesComponent, NavbarComponent, ArticleCardComponent, ProfileComponent, ThemeCardComponent, ThemesComponent, ArticleDetailComponent, ArticleCreationComponent],
  imports: [
    MatInputModule,
    MatFormFieldModule,
    MatToolbarModule,
    MatIconModule,
    ReactiveFormsModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    HttpClientModule,
    CommonModule,
    MatCardModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
