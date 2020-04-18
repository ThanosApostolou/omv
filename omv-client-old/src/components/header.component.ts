import { Component } from '@angular/core';

class Navitem {
    route: string;
    label: string;
    icon: string;
    constructor(route: string, label: string, icon: string) {
        this.route=route;
        this.label=label;
        this.icon=icon;
    }
}

@Component({
    selector: 'header',
    templateUrl: './header.component.html'
})
export class Header {
    firstNavitems: Navitem[];
    lastNavitems: Navitem[];
    navitemMaxwidth: string;

    constructor() {
        this.firstNavitems = [
            new Navitem("home", "Home", "home"),
            new Navitem("runvisualizer", "Run Visualizer", "play_arrow"),
            new Navitem("about", "About", "info"),
            new Navitem("settings", "Settings", "settings")
        ];

        this.lastNavitems=[
            new Navitem("user", "User", "account_circle")
        ];

        this.navitemMaxwidth = this.calcNavitemMaxwidth();
        console.log("maxwidth " + this.navitemMaxwidth);
    }

    calcNavitemMaxwidth(): string {
        let maxlength=0;
        this.firstNavitems.forEach((item) => {
            const itemlength = item.label.length;
            if (itemlength > maxlength) {
                maxlength = itemlength;
            }
        });
        this.lastNavitems.forEach((item) => {
            const itemlength = item.label.length;
            if (itemlength > maxlength) {
                maxlength = itemlength;
            }
        });
        return "" + maxlength + "em";
    }

}
