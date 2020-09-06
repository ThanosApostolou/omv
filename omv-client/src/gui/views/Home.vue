<template>
    <v-container>
        <v-row align="center" justify="center" class="text-center">
            <h1>
                Welcome to {{ buildinfo.appName }}
            </h1>
        </v-row>
        <v-row align="center" justify="center" class="text-center">
            <v-img
                alt="Logo"
                src="../assets/omv_logo.svg"
                class="shrink mr-2 my-2"
                contain
                height="128"
                width="128"
            />
        </v-row>
        <v-row align="center" justify="center" class="text-center">
            <p>
                {{ buildinfo.appName }} is an application where you can choose 2 owl files
                and a json file with their mapping.<br>
                The application will visualize this information in a understandable way.
            </p>
        </v-row>
        <v-row align="center" justify="center" class="text-center">
            <p>
                You can Run the Visualizer
                <router-link to="/visualizer">
                    Here
                </router-link>
            </p>
        </v-row>
    </v-container>
</template>

<script>
export default {
    name: "Home",
    data: function () {
        return {
            buildinfo: this.$root.app.buildinfo
        };
    }
};
</script>
