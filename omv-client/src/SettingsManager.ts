import { App } from "./App";

interface Settingsobject {
    server: string;
}

export class SettingsManager {
    defaultServer: string = "";
    server: string = "";
    isServerDefault: boolean = true;

    init(): void {
        this.defaultServer = process.env.VUE_APP_DEFAULT_SERVER;
        // read from local storage
        this.readSettings();
    }

    readSettings(): void {
        const settingsobject: Settingsobject = JSON.parse(localStorage.getItem("settings"));
        if (settingsobject != null) {
            if (settingsobject.server == null) {
                this.server = this.defaultServer;
                this.isServerDefault = true;
            } else {
                this.server = settingsobject.server;
                this.isServerDefault = false;
            }
            App.app.apiconsumer.setServer(this.server);
        } else {
            this.setServer(null);
        }
    }

    setServer(newserver: string): void {
        this.server = newserver;
        const settingsobject: Settingsobject = {
            "server": newserver
        };
        localStorage.setItem("settings", JSON.stringify(settingsobject));
        if (newserver == null) {
            this.server = this.defaultServer;
            this.isServerDefault = true;
        } else {
            this.server = newserver;
            this.isServerDefault = false;
        }
        App.app.apiconsumer.setServer(this.server);
    }
}