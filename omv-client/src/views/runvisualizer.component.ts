import { Component } from '@angular/core';
import { AppComponent } from '../app/app.component';

@Component({
    selector: 'runvisualizer',
    templateUrl: './runvisualizer.component.html'
})
export class RunVisualizer {
    app: AppComponent;
    constructor() {
        this.app = AppComponent.app;
    }
}
