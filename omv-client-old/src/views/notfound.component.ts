import { Component } from '@angular/core';
import { AppComponent } from '../app/app.component';

@Component({
    selector: 'notfound',
    templateUrl: './notfound.component.html'
})
export class NotFound {
    app: AppComponent;
    constructor() {
        this.app = AppComponent.app;
    }
}
