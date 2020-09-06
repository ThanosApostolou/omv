process.env.VUE_APP_NAME = require("./package.json").name;
process.env.VUE_APP_VERSION = require("./package.json").version;

if (process.env.NODE_ENV == "production") {
    process.env.VUE_APP_DEFAULT_SERVER = "https://localhost";
} else {
    process.env.VUE_APP_DEFAULT_SERVER = "http://localhost:8080";
}



module.exports = {
    "publicPath": "",
    "transpileDependencies": [
        "vuetify"
    ]
};