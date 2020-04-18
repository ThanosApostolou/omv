<template>
    <v-app>
        <MyHeader :appdata='appdata' />
        <v-content>
            <transition name='fade' mode='out-in'>
                <router-view :appdata='appdata' />
            </transition>
        </v-content>
        <v-footer elevation='3' class='primary lighten-2 caption' dark>
            <v-row align='center' justify='center'>
                {{ appdata.appName }} - Thanos Apostolou 2020
            </v-row>
        </v-footer>
    </v-app>
</template>

<script>
import MyHeader from './components/MyHeader.vue';

export default {
    name: 'App',
    components: {
        MyHeader
    },
    data: () => ({
        appdata: {
            appNodeName: process.env.VUE_APP_NAME,
            appVersion: process.env.VUE_APP_VERSION,
            appName: 'Ontology Mapping Visualizer'
        }
    })
};
</script>