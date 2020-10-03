import { Buildinfo } from "./Buildinfo";
import { VueRoot } from "./gui/VueRoot";
import { ApiConsumer } from "./ApiConsumer";
import { SettingsManager } from "./SettingsManager";

export class App {
    /** @type {App} */  static app: App;
    /** @type {Buildinfo} */ buildinfo: Buildinfo;
    /** @type {SettingsManager} */ settingsmanager: SettingsManager;
    /** @type {ApiConsumer} */ apiconsumer: ApiConsumer;
    /** @type {VueRoot} */ vueroot: VueRoot;

    constructor() {
        App.app = this;
        this.buildinfo = new Buildinfo();
        this.settingsmanager = new SettingsManager();
        this.settingsmanager.init();
        this.apiconsumer = new ApiConsumer;
        this.vueroot = new VueRoot();
    }
}