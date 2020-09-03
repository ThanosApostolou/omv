import axios from "axios";

export class ApiConsumer {
    baseurl;
    axios;

    constructor() {
        this.baseurl = "http://localhost:8080/api/";
        this.axios = axios;
        this.axios.defaults.baseURL = this.baseurl;
    }

    postVisualization(data) {
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