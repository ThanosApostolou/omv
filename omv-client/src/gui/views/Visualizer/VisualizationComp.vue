<template>
    <div>
        <div class="">
            <v-row>
                <v-col cols="6" lg="4" order="1">
                    <p>
                        {{ visualization.owl1.label }}
                        <v-tooltip bottom>
                            <template v-slot:activator="{ on, attrs }">
                                <v-btn icon color="primary" @click="owl1info()" v-bind="attrs" v-on="on">
                                    <v-icon>info</v-icon>
                                </v-btn>
                            </template>
                            <span>Show OWL Info</span>
                        </v-tooltip>
                    </p>
                </v-col>
                <v-col v-if="receivedvisualization.mapping.error == null" class="col2" cols="12" sm="12" lg="4" order="3" order-lg="2">
                    <v-row class="col2">
                        <v-tabs centered v-model="displayTab">
                            <v-tab key="statistics">
                                Statistics
                            </v-tab>
                            <v-tab key="epoptic">
                                Epoptic View
                            </v-tab>
                            <v-tab key="byrule">
                                View By Rule
                            </v-tab>
                        </v-tabs>
                    </v-row>
                </v-col>
                <v-col cols="6" lg="4" class="col3" order="2" order-lg="3">
                    <p>
                        {{ visualization.owl2.label }}
                        <v-tooltip bottom>
                            <template v-slot:activator="{ on, attrs }">
                                <v-btn icon color="primary" @click="owl2info()" v-bind="attrs" v-on="on">
                                    <v-icon>info</v-icon>
                                </v-btn>
                            </template>
                            <span>Show OWL Info</span>
                        </v-tooltip>
                    </p>
                </v-col>
            </v-row>
        </div>
        <v-dialog v-if="showdialog" v-model="showdialog" max-width="1400">
            <v-card>
                <v-card-actions>
                    <v-spacer />
                    <v-btn icon @click="showdialog = false">
                        <v-icon>close</v-icon>
                    </v-btn>
                </v-card-actions>
                <OwlInfoComp :owl="currentOwl" :key="currentOwlkey" />
            </v-card>
        </v-dialog>
        <v-tabs-items v-if="receivedvisualization.mapping.error == null" v-model="displayTab">
            <v-tab-item key="statistics">
                <VisualizationStatisticsComp v-if="displayTab == 0" :statistics="visualization.statistics" />
            </v-tab-item>
            <v-tab-item key="epoptic">
                <VisualizationEpopticComp v-if="displayTab == 1" :visualization="visualization" />
            </v-tab-item>
            <v-tab-item key="byrule">
                <VisualizationByruleComp v-if="displayTab == 2" :visualization="visualization" />
            </v-tab-item>
        </v-tabs-items>
        <v-row v-if="receivedvisualization.mapping.error != null" class="justify-center text-center">
            <p>{{ receivedvisualization.mapping.error }}</p>
        </v-row>
    </div>
</template>

<script>
import { Visualization } from "../../../entities/Visualization.ts";
import OwlInfoComp from "./OwlInfoComp.vue";
import VisualizationEpopticComp from "./VisualizationEpopticComp";
import VisualizationByruleComp from "./VisualizationByruleComp";
import VisualizationStatisticsComp from "./VisualizationStatisticsComp";

export default {
    name: "VisualizationComp",
    components: {
        OwlInfoComp,
        VisualizationEpopticComp,
        VisualizationByruleComp,
        VisualizationStatisticsComp
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
            currentOwl: {},
            currentOwlkey: null,
            displayTab: null
        };
    },
    methods: {
        owl1info() {
            this.currentOwl = this.visualization.owl1;
            this.currentOwlkey = "owl1";
            this.showdialog = true;
        },
        owl2info() {
            this.currentOwl = this.visualization.owl2;
            this.currentOwlkey = "owl2";
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