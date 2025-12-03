import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterModule, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './nav.html',
  styleUrl: './nav.css'
})
export class Nav {

  role = '';
  isLoggedIn = false;
  hideNav = false;

  constructor(public router: Router) {}

  ngOnInit() {
    // Check on page load
    this.checkNavbarState();

    // Check on every route change
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => this.checkNavbarState());
  }

checkNavbarState() {
  const url = this.router.url.split('?')[0].split('#')[0];  // clean URL

  // Hide navbar ONLY on login & register
  this.hideNav = (url === '/login' || url === '/register');

  // Check login status
  this.isLoggedIn = !!localStorage.getItem('token');
  this.role = localStorage.getItem('role') || '';
}


  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}
