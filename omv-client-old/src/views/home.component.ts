import { Component } from '@angular/core';
import { AppComponent } from '../app/app.component';

@Component({
    selector: 'home',
    templateUrl: './home.component.html'
})
export class Home {
    app: AppComponent;
    constructor() {
        this.app = AppComponent.app;
    }
}
