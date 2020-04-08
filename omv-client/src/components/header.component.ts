import { Component } from '@angular/core';

@Component({
    selector: 'header',
    templateUrl: './header.component.html'
})
export class Header {
    navitems: object[];

    constructor() {
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
