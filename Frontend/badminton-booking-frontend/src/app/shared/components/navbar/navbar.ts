import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../../core/services/auth';

// A4: root of the component hierarchy's shared UI (AppComponent -> NavbarComponent).
@Component({
  imports: [RouterLink, RouterLinkActive],
  selector: 'app-navbar',
  styleUrl: './navbar.css',
  templateUrl: './navbar.html',
})
export class Navbar {
  constructor(private auth: AuthService, private router: Router) {}

  get isLoggedIn() {
    return this.auth.isLoggedIn();
  }

  get currentUser() {
    return this.auth.currentUser();
  }

  // Demonstrates event binding + programmatic navigation after logout.
  logout(): void {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
