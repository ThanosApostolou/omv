import { Buildinfo } from "./Buildinfo.js";
import { VueRoot } from "./gui/VueRoot.js";
import { ApiConsumer } from "./ApiConsumer.js";

export class App {
    static app;
    buildinfo;
    vueroot;
    apiconsumer;

    constructor() {
        App.app = this;
        this.buildinfo = new Buildinfo();
        this.apiconsumer = new ApiConsumer;
        this.vueroot = new VueRoot();
    }
}