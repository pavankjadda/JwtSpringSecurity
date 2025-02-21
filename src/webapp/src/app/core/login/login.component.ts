import { NgIf } from '@angular/common';
import { HttpHeaders } from '@angular/common/http';
import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { MatCard, MatCardContent, MatCardTitle } from '@angular/material/card';
import { MatError, MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerComponent, NgxSpinnerService } from 'ngx-spinner';
import { USER_API_URL } from 'src/app/constants/app.constants';
import { AuthService } from 'src/app/core/auth/auth.service';
import { environment } from 'src/environments/environment';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.scss'],
	imports: [
		NgxSpinnerComponent,
		FormsModule,
		ReactiveFormsModule,
		NgIf,
		MatCardTitle,
		MatCardContent,
		MatFormField,
		MatInput,
		MatButton,
		MatError,
		MatLabel,
		MatCard,
	],
})
export class LoginComponent implements OnInit {
	message: string;
	loginForm: FormGroup;
	submitted = false;
	returnUrl: string;
	loginFailed = signal(false);

	constructor(
		private readonly formBuilder: FormBuilder,
		private readonly route: ActivatedRoute,
		private readonly router: Router,
		private readonly authService: AuthService,
		private readonly spinner: NgxSpinnerService,
	) {}

	// convenience getter for easy access to form fields
	get f() {
		return this.loginForm.controls;
	}

	ngOnInit() {
		// redirect to home if already logged in
		if (this.authService.isUserLoggedIn()) {
			this.router.navigate(['/home']);
		}

		this.loginForm = this.formBuilder.group({
			username: ['', Validators.required],
			password: ['', Validators.required],
		});

		// get return url from route parameters or default to '/'
		this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
		// Logout user if already logged in
		this.logout();
	}

	login() {
		this.spinner.show();

		const formData = new FormData();
		formData.append('username', this.f.username.value);
		formData.append('password', this.f.password.value);
		let url = `${environment.BASE_URL + USER_API_URL}/login`;
		let headers = new HttpHeaders();
		headers = headers.append('Authorization', `Basic ${btoa(`${this.f.username.value}:${this.f.password.value}`)}`);

		this.authService.login(url, headers).subscribe(
			(response) => {
				if (response.token && this.authService.isUserLoggedIn()) {
					this.router.navigate(['/home']);
				} else {
					localStorage.removeItem('currentUser');
					this.router.navigate(['/login']);
				}
			},
			(error) => {
				this.loginFailed.set(true);
				this.spinner.hide();
			},
			() => {
				this.spinner.hide();
			},
		);
	}

	logout() {
		this.authService.logout();
		this.setMessage();
	}

	private setMessage() {
		this.message = 'Logged ' + (this.authService.isUserLoggedIn() ? 'in' : 'out');
	}
}
