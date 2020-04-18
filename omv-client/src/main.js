import '@babel/polyfill';
import 'mutationobserver-shim';

import Vue from 'vue';
import App from './App.vue';
import router from './plugins/router';
import vuetify from './plugins/vuetify';

import './assets/css/style.css';

Vue.config.productionTip = false;

new Vue({
    router,
    vuetify,
    render: (h) => {
        return h(App);
    },
    mounted() {
        if (process.env.IS_ELECTRON || process.env.VUE_APP_IS_CORDOVA) {
            this.$router.push('/');
        }
        if (process.env.NODE_ENV !== 'production') {
            //console.log(JSON.stringify(this.$router.options.routes));
        }
    }
}).$mount('#app');
