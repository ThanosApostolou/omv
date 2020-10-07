<template>
    <v-container>
        <v-row>
            <v-col class="justify-center text-center" cols="12" lg="6" xs="12">
                <v-card height="100%">
                    <v-card-title>
                        <v-col cols="12" class="justify-center text-center">
                            About Me
                        </v-col>
                    </v-card-title>
                    <v-card-text>
                        <v-col cols="12" class="justify-center text-center">
                            <p>Thanos Apostolou<br></p>
                            <p>Student at Electrical and Computer Engineering of National Technical University of Athens</p>
                        </v-col>
                    </v-card-text>
                    <v-row align="center" justify="center" class="text-center">
                        <v-img src="@/gui/assets/thanos_profile.jpg" max-height="150" max-width="150" />
                    </v-row>
                </v-card>
            </v-col>
            <v-col justify="center" cols="12" lg="6" xs="12">
                <v-card height="100%">
                    <v-row align="center" justify="center" class="text-center">
                        <v-card-title>Application Information</v-card-title>
                    </v-row>
                    <v-row align="center" justify="center" class="text-center">
                        <v-col>
                            Application Name:
                        </v-col>
                        <v-col>
                            {{ buildinfo.appNodeName }}
                        </v-col>
                    </v-row>
                    <v-row align="center" justify="center" class="text-center">
                        <v-col>
                            Application Displayed Name:
                        </v-col>
                        <v-col>
                            {{ buildinfo.appName }}
                        </v-col>
                    </v-row>
                    <v-row align="center" justify="center" class="text-center">
                        <v-col>
                            Application Version:
                        </v-col>
                        <v-col>
                            {{ buildinfo.appVersion }}
                        </v-col>
                    </v-row>
                    <v-row align="center" justify="center" class="text-center">
                        <v-col>
                            Build Platform:
                        </v-col>
                        <v-col>
                            {{ buildinfo.buildPlatform }}
                        </v-col>
                    </v-row>
                    <v-row align="center" justify="center" class="text-center">
                        <v-col>
                            Build Mode:
                        </v-col>
                        <v-col>
                            {{ buildinfo.buildMode }}
                        </v-col>
                    </v-row>
                </v-card>
            </v-col>
            <v-col cols="12">
                <v-card height="100%">
                    <v-card-title class="justify-center text-center">
                        App Usage
                    </v-card-title>
                    <v-card-text>
                        <p>Run a visualization by selecting 2 owl files and their mapping in json format which contains the rules that connect these ontologies.</p>
                        <p>The mapping.json should define the 2 ontologies by their IRI in a correct format</p>
                        <p>
                            The app is able to show:<br>
                            1) Information about each ontology<br>
                            2) An epoptic view of the rules and the owl entities that are part of these rules<br>
                            3) A view separated by each rule with full description of the specific rule<br>
                            4) Useful statistics about the rules
                        </p>
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    name: "About",
    data: function () {
        return {
            buildinfo: this.$root.app.buildinfo
        };
    }
};
</script>
