import { Component, OnInit } from '@angular/core';
import { MatToolbar } from '@angular/material/toolbar';

@Component({
    selector: 'app-footer',
    templateUrl: './footer.component.html',
    styleUrls: ['./footer.component.scss'],
    imports: [MatToolbar]
})
export class FooterComponent implements OnInit {
	constructor() {}

	ngOnInit(): void {}
}
