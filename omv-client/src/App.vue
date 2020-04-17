<template>
    <v-app>
        <MyHeader />
        <v-content>
            <transition name='fade' mode='out-in'>
                <router-view />
            </transition>
        </v-content>
        <v-footer elevation='3' class='primary lighten-2 text-center' dark>
            <v-row justify='center'>
                footer
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
        name: name
    }),
    mounted() {
        console.log(JSON.stringify(this.$router.options.routes));
        console.log(this.$options.name);
    },
};
</script>