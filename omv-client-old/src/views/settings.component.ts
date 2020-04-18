import { Component } from '@angular/core';
import { AppComponent } from '../app/app.component';

@Component({
    selector: 'settings',
    templateUrl: './settings.component.html'
})
export class Settings {
    app: AppComponent;
    constructor() {
        this.app = AppComponent.app;
    }
}
