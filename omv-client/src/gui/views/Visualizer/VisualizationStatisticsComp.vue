<template>
    <div>
        <v-card elevation="2">
            <v-card-title>
                <v-row class="justify-center text-center">
                    STATISTICS
                </v-row>
            </v-card-title>
            <v-card outlined>
                <v-card-title>
                    Rules
                </v-card-title>
                <v-card-text>
                    <p>There are total {{ statistics.numberofAllRules }} Rules.</p>
                    <p>There are {{ statistics.numberofClassRules }} ({{ statistics.percentClassRules }}%) Class Rules which connect only classes with each ontology.</p>
                    <p>There are {{ statistics.numberofPropRules }} ({{ statistics.percentPropRules }}%) Properties Rules which connect classes, object properties and data properties with each ontology.</p>
                </v-card-text>
            </v-card>
            <v-card outlined>
                <v-card-title>
                    Ontologies
                </v-card-title>
                <v-card-text>
                    <p>Left ontology has {{ this.statistics.owl1classessize }} Classes</p>
                    <p>Left ontology has {{ this.statistics.owl1objpropssize }} Object Properties</p>
                    <p>Left ontology has {{ this.statistics.owl1datapropssize }} Data Properties.</p>
                    <p>Right ontology has {{ this.statistics.owl2classessize }} Classes</p>
                    <p>Right ontology has {{ this.statistics.owl2objpropssize }} Object Properties</p>
                    <p>Right ontology has {{ this.statistics.owl2datapropssize }} Data Properties.</p>
                </v-card-text>
            </v-card>
        </v-card>
    </div>
</template>

<script>
import { Statistics } from "@/entities/Statistics";
export default {
    name: "VisualizationStatisticsComp",
    props: {
        statistics: {
            type: Statistics,
            default: null
        }
    },

};
</script>