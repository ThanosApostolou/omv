<template>
    <div>
        <v-row>
            <v-col cols="4">
                <p>
                    {{ visualization.owl1.label }}
                    <v-btn icon color="primary" @click="owl1info()">
                        <v-icon>info</v-icon>
                    </v-btn>
                </p>
            </v-col>
            <v-col cols="4" class="col2">
                <v-row class="col2">
                    <v-tabs v-model="displayTab">
                        <v-tab key="epoptic">Epoptic View</v-tab>
                        <v-tab key="byrule">View By Rule</v-tab>
                        <v-tab key="statistics">Statistics</v-tab>
                    </v-tabs>
                </v-row>
            </v-col>
            <v-col cols="4" class="col3">
                <p>
                    {{ visualization.owl2.label }}
                    <v-btn icon color="primary" @click="owl2info()">
                        <v-icon>info</v-icon>
                    </v-btn>
                </p>
            </v-col>
        </v-row>
        <v-dialog v-model="showdialog">
            <v-card>
                <v-card-actions>
                    <v-spacer />
                    <v-btn icon @click="showdialog = false">
                        <v-icon>close</v-icon>
                    </v-btn>
                </v-card-actions>
                <OwlInfoComp :owl="current_owl" :key="current_owl_key" />
            </v-card>
        </v-dialog>
        <v-tabs-items v-model="displayTab">
            <v-tab-item key="epoptic">
                <VisualizationEpopticComp :visualization="visualization" />
            </v-tab-item>
            <v-tab-item key="byrule">
                <VisualizationByruleComp :visualization="visualization" />
            </v-tab-item>
            <v-tab-item key="statistics">asdf</v-tab-item>
        </v-tabs-items>
    </div>
</template>

<script>
import { Visualization } from "../../../entities/Visualization.js";
import OwlInfoComp from "./OwlInfoComp.vue";
import VisualizationEpopticComp from "./VisualizationEpopticComp.vue";
import VisualizationByruleComp from "./VisualizationByruleComp.vue";

export default {
    name: "VisualizationComp",
    components: {
        OwlInfoComp,
        VisualizationEpopticComp,
        VisualizationByruleComp
    },
    props: {
        receivedvisualization: {
            type: Visualization,
            default: null
        }
    },
    data: function () {
        return {
            visualization: this.receivedvisualization,
            showdialog: false,
            current_owl: {},
            current_owl_key: null,
            displayTab: null
        };
    },
    methods: {
        owl1info() {
            this.current_owl = this.visualization.owl1;
            this.current_owl_key = "owl1";
            this.showdialog = true;
        },
        owl2info() {
            this.current_owl = this.visualization.owl2;
            this.current_owl_key = "owl2";
            this.showdialog = true;
        }
    }
};
</script>

<style scoped>
.center {
    text-align: center;
    align-self: center;
    align-content: center;
    justify-content: center;
}
.col2 p {
    text-align: center;
    align-content: center;
    justify-content: center;
}
.col3 {
    text-align: end;
    align-content: end;
    justify-content: end;
}
</style>