import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from '../auth/auth.service';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
	constructor(
		private authService: AuthService,
		private router: Router,
	) {}

	intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
		return next.handle(request).pipe(
			catchError((err) => {
				if (err.status === 401) {
					console.log('Inside ErrorInterceptor, Http Status: 401');
					this.authService.logout();
					this.router.navigate(['/login']);
				}
				if (err.status === 403) {
					console.log('Inside ErrorInterceptor, Http Status: 403');
					this.authService.logout();
					this.router.navigate(['/403']);
				}

				if (err.status === 404) {
					console.log('Inside ErrorInterceptor, Http Status: 404');
				}
				if (err.status === 500) {
					console.log('Inside ErrorInterceptor, Http Status: 500');
				}

				if (err.status === 0 || err.statusText === 'Unknown Error') {
					console.log('Inside ErrorInterceptor, Http Status: Unknown Error');
				}

				let error = err.error.message || err.error;
				return throwError(error);
			}),
		);
	}
}
