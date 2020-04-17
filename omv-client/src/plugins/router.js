import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '../views/Home.vue';

Vue.use(VueRouter);

const routes = [
    {
        path: '/home',
        name: 'Home',
        component: Home
    },
    {
        path: '/about',
        name: 'About',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "About" */ '../views/About.vue')
    },
    {
        path: '/',
        redirect: '/home'
    },
    {
        path: '*',
        component: () => import(/* webpackChunkName: "Notfound" */ '../views/Notfound.vue')
    }
];

const router = new VueRouter({
    mode: (process.env.IS_ELECTRON || process.env.VUE_APP_IS_CORDOVA) ? 'hash' : 'history',
    base: process.env.BASE_URL,
    routes
});

export default router;
