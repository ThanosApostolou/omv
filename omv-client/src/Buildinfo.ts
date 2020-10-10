export class Buildinfo {
    /** @type {String} */ appNodeName: string;
    /** @type {String} */ appVersion: string;
    /** @type {String} */ appName: string;
    /** @type {String} */ buildPlatform: string;
    /** @type {String} */ buildMode: string;

    constructor() {
        let buildPlatform="";
        if (process.env.VUE_APP_IS_CORDOVA) {
            buildPlatform="cordova";
        } else {
            if (process.env.IS_ELECTRON) {
                buildPlatform="electron";
            } else {
                buildPlatform="web";
            }
        }
        this.appNodeName = process.env.VUE_APP_NAME;
        this.appVersion = process.env.VUE_APP_VERSION;
        this.appName = "Ontology Mapping Visualizer";
        this.buildPlatform = buildPlatform;
        this.buildMode = process.env.NODE_ENV;
    }

}