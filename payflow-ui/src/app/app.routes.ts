import { Routes } from '@angular/router';
import { Login } from './pages/login/login/login';
import { Dashboard } from './pages/dashboard/dashboard/dashboard';
import { CreateTransaction } from './pages/create-transaction/create-transaction/create-transaction';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [

  { path: '', redirectTo: 'login', pathMatch: 'full' },

  { path: 'login', component: Login },

  {
    path: 'dashboard',
    component: Dashboard,
    canActivate:[authGuard]
  },

  {
    path: 'create',
    component: CreateTransaction,
    canActivate:[authGuard]
  }

];
