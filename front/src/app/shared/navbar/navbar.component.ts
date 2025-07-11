import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { AuthService } from 'src/app/auth/services/auth.service';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  hideLinks = false;
  hideNavbar = false;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(event => {
        const navEndEvent = event as NavigationEnd;
        const currentUrl = navEndEvent.urlAfterRedirects;

        this.hideLinks = currentUrl.includes('/connexion') || currentUrl.includes('/inscription');

        this.hideNavbar = currentUrl === '/';

      });

  }


  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }

}
