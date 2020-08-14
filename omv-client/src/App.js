import { Buildinfo } from './Buildinfo.js';
import { VueRoot } from './gui/VueRoot.js';

export class App {
    static app;
    buildinfo;
    vueroot;

    constructor() {
        App.app = this;
        this.buildinfo = new Buildinfo();
        this.vueroot = new VueRoot();
    }
}