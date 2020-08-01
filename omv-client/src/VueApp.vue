<template>
    <v-app>
        <MyHeader />
        <v-main>
            <transition name='fade' mode='out-in'>
                <router-view />
            </transition>
        </v-main>
        <v-footer elevation='3' class='primary lighten-2 caption' dark>
            <v-row align='center' justify='center'>
                {{ buildinfo.appName }} - Thanos Apostolou 2020
            </v-row>
        </v-footer>
    </v-app>
</template>

<script>
import MyHeader from './components/MyHeader.vue';

export default {
    name: 'VueApp',
    components: {
        MyHeader
    },
    data: function () {
        return {
            buildinfo: this.$root.app.buildinfo
        };
    }
};
</script>