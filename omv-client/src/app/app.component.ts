import { Component } from '@angular/core';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html'
})
export class AppComponent {
    static app: AppComponent;

    title: string;
    navitems: object[];

    constructor() {
        AppComponent.app = this;
        this.title = 'omv-client';
        this.navitems = [
            {
                route: "home",
                label: "Home",
                icon: "home"
            },
            {
                route: "runvisualizer",
                label: "Run Visualizer",
                icon: "play_arrow"
            },
            {
                route: "about",
                label: "About",
                icon: "info"
            },
            {
                route: "settings",
                label: "Settings",
                icon: "settings"
            }
        ];
    }

}
