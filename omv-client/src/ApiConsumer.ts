import axios from "axios";
import { App } from "./App";

export class ApiConsumer {
    baseurl;
    axios;

    constructor() {
        this.baseurl = App.app.settingsmanager.defaultServer + "/api/";
        this.axios = axios;
        this.axios.defaults.baseURL = this.baseurl;
    }

    postVisualization(data: any): any {
        return this.axios({
            method: "post",
            url: "visualization",
            headers: {
                "Content-Type": "multipart/form-data"
            },
            data: data,
        });
    }
}