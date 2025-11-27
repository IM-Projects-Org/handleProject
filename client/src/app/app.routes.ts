import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Register } from './pages/register/register';
import { AdminDashboard } from './pages/admin-dashboard/admin-dashboard';
import { UserDashboard } from './pages/user-dashboard/user-dashboard';
import { AuthGuard } from './guards/auth-guard';
import { AdminGuard } from './guards/admin-guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  { path: 'login', component: Login },
  { path: 'register', component: Register },

  {
    path: 'admin-dashboard',
    component: AdminDashboard,
    canActivate: [AuthGuard, AdminGuard]
  },

  {
    path: 'user-dashboard',
    component: UserDashboard,
    canActivate: [AuthGuard]  // only logged in required
  },

  { path: '**', redirectTo: 'login' }
];
