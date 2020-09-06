import { Buildinfo } from "./Buildinfo.js";
import { VueRoot } from "./gui/VueRoot.js";
import { ApiConsumer } from "./ApiConsumer.js";
import { SettingsManager } from "./SettingsManager.js";

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