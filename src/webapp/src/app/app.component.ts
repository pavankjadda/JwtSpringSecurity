import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { MainComponent } from './layout/main/main.component';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	imports: [MainComponent, RouterOutlet, RouterLink],
})
export class AppComponent {}
