import { HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { USER_API_URL } from 'src/app/constants/app.constants';
import { AuthService } from 'src/app/core/auth/auth.service';
import { environment } from 'src/environments/environment';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
	message: string;
	loginForm: FormGroup;
	submitted = false;
	returnUrl: string;
	loginFailed: boolean;

	constructor(
		private formBuilder: FormBuilder,
		private route: ActivatedRoute,
		private router: Router,
		private authService: AuthService,
		private spinner: NgxSpinnerService,
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
				console.log(error);
				this.loginFailed = true;
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

	isUserLoggedIn() {}

	resetForm() {
		this.f.username.setValue(null);
		this.f.password.setValue(null);
	}

	private setMessage() {
		this.message = 'Logged ' + (this.authService.isUserLoggedIn() ? 'in' : 'out');
	}
}
