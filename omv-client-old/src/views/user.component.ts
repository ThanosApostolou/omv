import { Component } from '@angular/core';
import { AppComponent } from '../app/app.component';

@Component({
    selector: 'user',
    templateUrl: './user.component.html'
})
export class User {
    app: AppComponent;
    constructor() {
        this.app = AppComponent.app;
    }
}
