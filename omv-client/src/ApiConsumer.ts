import axios, { AxiosStatic, AxiosPromise } from "axios";
import { App } from "./App";

export class ApiConsumer {
    axios: AxiosStatic;

    constructor() {
        this.axios = axios;
    }

    setServer(server: string) {
        this.axios.defaults.baseURL = server + "/api";
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