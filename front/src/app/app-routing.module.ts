import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ArticlesComponent } from './pages/articles/articles.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { ThemesComponent } from './pages/themes/themes.component';
import { AuthGuard } from './auth/guards/auth.guard';


const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'connexion', component: LoginComponent },
  { path: 'inscription', component: RegisterComponent },
  { path: 'articles', component: ArticlesComponent, canActivate: [AuthGuard] },
  { path: 'profil', component: ProfileComponent, canActivate: [AuthGuard] },
  { path: 'themes', component: ThemesComponent, canActivate: [AuthGuard] },

  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
