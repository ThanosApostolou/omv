import { Component } from '@angular/core';
import { AppComponent } from '../app/app.component';

@Component({
  selector: 'index',
  templateUrl: './index.component.html'
})
export class Index {
    app: AppComponent;
    constructor() {
        this.app = AppComponent.app;
    }
}
