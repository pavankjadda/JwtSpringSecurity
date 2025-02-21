import { Component } from '@angular/core';
import { MatSidenavContainer } from '@angular/material/sidenav';
import { RouterOutlet } from '@angular/router';
import { FooterComponent } from '../footer/footer.component';
import { HeaderComponent } from '../header/header.component';

@Component({
	selector: 'app-main',
	templateUrl: './main.component.html',
	styleUrls: ['./main.component.scss'],
	imports: [MatSidenavContainer, HeaderComponent, RouterOutlet, FooterComponent],
})
export class MainComponent {}
