import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.html',
  styleUrls: ['./register.css'],
})
export class Register {

  adminName: string = '';
  adminEmailId: string = '';
  password: string = '';
  phone: string = '';
  role: string = 'ADMIN';  // default for backend

  constructor(
    private auth: AuthService,
    private router: Router
  ) {}

  onRegister() {
    const body = {
      adminName: this.adminName,
      adminEmailId: this.adminEmailId,
      password: this.password,
      phone: this.phone,
      role: this.role
    };

    this.auth.register(body).subscribe(
      (res: any) => {
        alert("Registration successful!");
        this.router.navigate(['/login']);
      },
      (error: any) => {
        console.error(error);
        alert("Registration failed!");
      }
    );
  }
}
