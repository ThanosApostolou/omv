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
                height="100"
                width="100"
            />
        </v-row>
        <v-row align="center" justify="center" class="text-center">
            <v-col cols="12">
                <p>
                    {{ buildinfo.appName }} is an application where you can choose 2 owl files
                    and a json file with their mapping.<br>
                    The application will visualize this information in a understandable way.
                </p>
                <p>
                    You can Run the Visualizer
                    <router-link to="/visualizer">
                        Here
                    </router-link>
                    <br>
                    You can see information about the app
                    <router-link to="/About">
                        Here
                    </router-link>
                </p>
                <v-row justify="center">
                    <v-responsive max-height="480" max-width="850">
                        <v-carousel height="480" cycle>
                            <v-carousel-item src="../assets/Visualizer1.png" />
                            <v-carousel-item src="../assets/Visualizer2.png" />
                            <v-carousel-item src="../assets/Visualizer3.png" />
                        </v-carousel>
                    </v-responsive>
                </v-row>
                <br>
                <p>
                    Mapping Rules are created with the
                    <v-btn href="http://ponte.grid.ece.ntua.gr:8080/OntoMapping/" target="_blank" text link color="primary">
                        <span>Ontologies Alignment Tool</span>
                        <v-icon>launch</v-icon>
                    </v-btn>
                </p>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    name: "Home",
    data() {
        return {
            buildinfo: this.$root.app.buildinfo
        };
    }
};
</script>
