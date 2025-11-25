import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Register } from './pages/register/register';
import { AdminDashboard } from './pages/admin-dashboard/admin-dashboard';
import { UserDashboard } from './pages/user-dashboard/user-dashboard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  { path: 'login', component: Login },
  { path: 'register', component: Register },
    {path:'admin-dashboard',component:AdminDashboard},
    {path:'user-dashbard',component:UserDashboard},
    
  // Wildcard route (optional)
  { path: '**', redirectTo: 'login' }
];
