import { Component, OnInit } from '@angular/core';
import { MatCard, MatCardContent, MatCardTitle } from '@angular/material/card';
import { NgxSpinnerComponent } from 'ngx-spinner';
import { environment } from '../../environments/environment';
import { USER_API_URL } from '../constants/app.constants';
import { AuthService } from '../core/auth/auth.service';
import { UserService } from '../core/user/user.service';

@Component({
	selector: 'app-home',
	templateUrl: './home.component.html',
	styleUrls: ['./home.component.scss'],
	imports: [NgxSpinnerComponent, MatCard, MatCardTitle, MatCardContent],
})
export class HomeComponent implements OnInit {
	constructor(
		public authService: AuthService,
		private userService: UserService,
	) {}

	ngOnInit(): void {
		this.getUserInformation();
	}

	private getUserInformation() {
		const url = environment.BASE_URL + USER_API_URL + '/home';

		return this.userService.getUserInformation(url).subscribe((data) => {});
	}
}
