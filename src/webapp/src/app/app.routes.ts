import { Routes } from '@angular/router';
import { UserAuthGuard } from './core/guards/user-auth-guard';
import { LoginComponent } from './core/login/login.component';
import { HomeComponent } from './home/home.component';

export const routes: Routes = [
	{
		path: '',
		component: HomeComponent,
		canActivate: [UserAuthGuard],
	},
	{
		path: 'login',
		component: LoginComponent,
	},
	{
		path: 'home',
		component: HomeComponent,
		canActivate: [UserAuthGuard],
	},
];
