import { Component } from '@angular/core';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html'
})
export class AppComponent {
    static app: AppComponent;

    title: string;
    constructor() {
        AppComponent.app = this;
        this.title = 'omv-client';
    }

}
