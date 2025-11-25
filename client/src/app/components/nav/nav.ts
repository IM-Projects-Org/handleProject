import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-nav',
  imports: [CommonModule,RouterModule],
  templateUrl: './nav.html',
  styleUrl: './nav.css',
})
export class Nav {

  constructor(public router: Router) {}
}
