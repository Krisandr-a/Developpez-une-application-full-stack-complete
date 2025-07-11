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
    console.log("Testing init")

    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(event => {
        const navEndEvent = event as NavigationEnd;
        const currentUrl = navEndEvent.urlAfterRedirects;

        console.log('NavigationEnd URL:', currentUrl);

        this.hideLinks = currentUrl.includes('/connexion') || currentUrl.includes('/inscription');

        this.hideNavbar = currentUrl === '/';

        console.log('hideLinks set to:', this.hideLinks);
        console.log('hideNavbar set to:', this.hideNavbar);
      });

  }


  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }

}
