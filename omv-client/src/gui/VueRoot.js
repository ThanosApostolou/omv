import Vue from 'vue';
import VueApp from './VueApp.vue';
import router from './plugins/router';
import vuetify from './plugins/vuetify';

import { App } from '../App';

import './assets/css/style.css';

export class VueRoot {
    vue;

    constructor() {
        Vue.config.productionTip = false;
        this.vue = new Vue({
            router,
            vuetify,
            data: {
                app: App.app
            },
            render: (h) => {
                return h(VueApp);
            },
            mounted() {
                if (process.env.IS_ELECTRON || process.env.VUE_APP_IS_CORDOVA) {
                    this.$router.push('/');
                }
            }
        }).$mount('#app');
    }
}