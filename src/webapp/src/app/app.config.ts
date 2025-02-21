import { CommonModule, DatePipe } from '@angular/common';
import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { ApplicationConfig, importProvidersFrom, provideExperimentalZonelessChangeDetection } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MAT_DIALOG_DEFAULT_OPTIONS } from '@angular/material/dialog';
import { MatSelectModule } from '@angular/material/select';
import { BrowserModule, Title } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
	PreloadAllModules,
	provideRouter,
	withComponentInputBinding,
	withInMemoryScrolling,
	withPreloading,
	withRouterConfig,
} from '@angular/router';

import { CookieService } from 'ngx-cookie-service';
import { routes } from './app.routes';
import { errorInterceptor } from './core/intercepters/error.interceptor';
import { httpTokenInterceptor } from './core/intercepters/http-token.interceptor';

export const appConfig: ApplicationConfig = {
	providers: [
		provideExperimentalZonelessChangeDetection(),
		provideRouter(
			routes,
			withComponentInputBinding(),
			withRouterConfig({
				onSameUrlNavigation: 'reload',
			}),
			withInMemoryScrolling(),
			withPreloading(PreloadAllModules),
			withRouterConfig({
				paramsInheritanceStrategy: 'always',
			}),
		),
		importProvidersFrom(
			BrowserModule,
			CommonModule,
			FormsModule,
			ReactiveFormsModule,
			BrowserAnimationsModule,
			MatSelectModule,
			MatAutocompleteModule,
			MatNativeDateModule,
			MatDatepickerModule,
		),
		provideHttpClient(withFetch(), withInterceptors([errorInterceptor, httpTokenInterceptor])),
		Title,
		DatePipe,
		CookieService,
		{
			provide: MAT_DIALOG_DEFAULT_OPTIONS,
			useValue: { hasBackdrop: true, disableClose: true },
		},
	],
};
