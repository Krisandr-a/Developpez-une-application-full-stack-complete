import { Component, OnInit, HostListener } from '@angular/core';
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
  isMobile = false;
  // Pages where navbar should be hidden on mobile
  mobilePages = ['/connexion', '/inscription'];

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.checkScreenSize();

    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(event => {
        const navEndEvent = event as NavigationEnd;
        const currentUrl = navEndEvent.urlAfterRedirects;

        this.hideLinks = currentUrl.includes('/connexion') || currentUrl.includes('/inscription');

        // Always hides navbar on home page
        if (currentUrl === '/') {
          this.hideNavbar = true;
          return;
        }

        // Hides navbar on mobile if on register or login page
        if (this.isMobile && this.mobilePages.includes(currentUrl)) {
          this.hideNavbar = true;
          return;
        }

        // Displays navbar in all other situations
        this.hideNavbar = false;
      });

  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.checkScreenSize();
  }

  checkScreenSize() {
    this.isMobile = window.innerWidth <= 600;
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }

}
