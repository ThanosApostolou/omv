import { Buildinfo } from "./Buildinfo";
import { VueRoot } from "./gui/VueRoot";
import { ApiConsumer } from "./ApiConsumer";
import { SettingsManager } from "./SettingsManager";

export class App {
    /** @type {App} */  static app;
    /** @type {Buildinfo} */ buildinfo;
    /** @type {SettingsManager} */ settingsmanager;
    /** @type {ApiConsumer} */ apiconsumer;
    /** @type {VueRoot} */ vueroot;

    constructor() {
        App.app = this;
        this.buildinfo = new Buildinfo();
        this.settingsmanager = new SettingsManager();
        this.settingsmanager.init();
        this.apiconsumer = new ApiConsumer;
        this.vueroot = new VueRoot();
    }
}