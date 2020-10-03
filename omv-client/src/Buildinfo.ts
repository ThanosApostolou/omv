export class Buildinfo {
    /** @type {String} */ appNodeName;
    /** @type {String} */ appVersion;
    /** @type {String} */ appName;
    /** @type {String} */ buildPlatform;
    /** @type {String} */ buildMode;

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