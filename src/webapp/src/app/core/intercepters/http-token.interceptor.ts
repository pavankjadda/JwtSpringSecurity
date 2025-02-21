import { HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

/**
 * HTTP Token Interceptor that intercepts all requests HTTP requests and adds base URL
 *
 * @author Pavan Kumar Jadda
 * @since 2.0.0
 */
export function httpTokenInterceptor(request: HttpRequest<unknown>, next: HttpHandlerFn) {
	let jwtToken = inject(CookieService).get('jwtToken');
	if (jwtToken) {
		request = request.clone({
			setHeaders: {
				Authorization: 'Bearer ' + jwtToken,
			},
		});
	}
	return next(request);
}
