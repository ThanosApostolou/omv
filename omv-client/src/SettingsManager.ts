import { App } from "./App";

export class SettingsManager {
    /** @type {String} */ defaultServer: string = "";

    init(): void {
        if (App.app.buildinfo.buildMode == "development") {
            this.defaultServer = "http://127.0.0.1:8080/";
        } else {
            this.defaultServer = "https://omv-server.herokuapp.com/";
        }
        this;
    }
}