import { Buildinfo } from "./Buildinfo";
import { VueRoot } from "./gui/VueRoot";
import { ApiConsumer } from "./ApiConsumer";
import { SettingsManager } from "./SettingsManager";
import { Visualization } from "./entities/Visualization";

export class App {
    /** @type {App} */  static app: App;
    /** @type {Buildinfo} */ buildinfo: Buildinfo;
    /** @type {SettingsManager} */ settingsmanager: SettingsManager;
    /** @type {ApiConsumer} */ apiconsumer: ApiConsumer;
    /** @type {VueRoot} */ vueroot: VueRoot;
    visualization: Visualization = null;

    constructor() {
        App.app = this;
        this.buildinfo = new Buildinfo();
        this.apiconsumer = new ApiConsumer;
        this.settingsmanager = new SettingsManager();
        this.settingsmanager.init();
        this.vueroot = new VueRoot();
    }
}