import { Component } from '@angular/core';

@Component({
    selector: 'header',
    templateUrl: './header.component.html'
})
export class Header {
    firstNavitems: object[];
    lastNavitems: object[];

    constructor() {
        this.firstNavitems = [
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

        this.lastNavitems=[
            {
                route: "user",
                label: "User",
                icon: "account_circle"
            }
        ];
    }

}
