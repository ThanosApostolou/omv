import { Component } from '@angular/core';
import { AppComponent } from '../app/app.component';

@Component({
    selector: 'about',
    templateUrl: './about.component.html'
})
export class About {
    app: AppComponent;
    constructor() {
        this.app = AppComponent.app;
    }

}
