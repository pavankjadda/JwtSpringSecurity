import { HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

/**
 * HTTP Error Interceptor that intercepts all errors from backend
 *
 * @author Pavan Kumar Jadda
 * @since 2.0.0
 */
export function errorInterceptor(request: HttpRequest<any>, next: HttpHandlerFn) {
	const cookieService = inject(CookieService);
	const router = inject(Router);

	/**
   * The fetch() raw response object from backend looks like below and nested error object has details about the error. Add generic error message if error message is not available

   */
	return next(request).pipe(
		catchError((error) => {
			if (error.status === 401) {
				//Save current URL
				if (router.url !== '/login') {
					sessionStorage.setItem('returnUrl', router.url);
				}

				//Delete all cookies
				cookieService.deleteAll();
				router.navigate(['/login']);
			} else if (error.status === 404) {
				error.error.message = error.error.message ?? 'The requested resource is not found. Please try again';
			} else if (error.status === 500 || error.status === 502 || error.status === 503 || error.status === 504) {
				error.error.message = error.error.message ?? 'Unable to process the request. Please try again';
			} else if (error.status === 0 || error.message === 'Unknown Error') {
				router.navigate(['/error']);
			} else {
				error.message = error.message ?? 'Unable to process the request. Please try again';
			}
			// Get error message from response body (Spring Boot error object)
			return throwError(error.error);
		}),
	);
}
