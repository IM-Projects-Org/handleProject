import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
})
export class Login {

  email: string = '';
  password: string = '';

  constructor(
    private auth: AuthService,
    private router: Router
  ) {}

 onLogin() {
  const body = {
    email: this.email,
    password: this.password
  };

  this.auth.login(body).subscribe(
    (res: any) => {

      // No 'status' returned by backend, so check token
      if (res && res.token) {

        localStorage.setItem('token', res.token);
        localStorage.setItem('role', res.role);
        localStorage.setItem('name', res.name);

        if (res.role === 'ADMIN') {
          this.router.navigate(['/admin-dashboard']);
        } else {
          this.router.navigate(['/user-dashboard']);
        }

      } else {
        alert("Invalid login");
      }

    },
    error => {
      alert(error.error?.message || "API Error");
      console.error(error);
    }
  );
}

}
