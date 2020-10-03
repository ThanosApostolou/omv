import axios, { AxiosStatic, AxiosPromise } from "axios";
import { App } from "./App";

export class ApiConsumer {
    baseurl: string;
    axios: AxiosStatic;

    constructor() {
        this.baseurl = App.app.settingsmanager.defaultServer + "/api/";
        this.axios = axios;
        this.axios.defaults.baseURL = this.baseurl;
    }

    postVisualization(data: any): AxiosPromise {
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