<template>
    <div>
        <v-card elevation="0">
            <v-card-title class="justify-center text-center">
                STATISTICS
            </v-card-title>
            <v-card outlined>
                <v-card-title>
                    Mapping Rules
                </v-card-title>
                <v-card-text>
                    <p>There are total {{ statistics.numberofAllRules }} Rules.</p>
                    <p>There are {{ statistics.numberofClassRules }} ({{ statistics.percentClassRules }}%) Class Rules which connect only classes with each ontology.</p>
                    <p>There are {{ statistics.numberofPropRules }} ({{ statistics.percentPropRules }}%) Properties Rules which connect classes, object properties and data properties with each ontology.</p>
                </v-card-text>
            </v-card>
            <v-card v-if="isReference" outlined>
                <v-card-title>
                    Reference Model
                </v-card-title>
                <v-card-text>
                    <p>asdfasd</p>
                </v-card-text>
            </v-card>
            <v-card outlined>
                <v-card-title>
                    Ontologies
                </v-card-title>
                <v-card-text>
                    <v-row class="text-start">
                        <v-col>
                            "<b>{{ owl1.label }}</b>" Ontology Includes:
                        </v-col>
                        <v-col>
                            <p>{{ owl1.owlclasses.size() }} Classes</p>
                            <p>{{ owl1.owlobjprops.size() }} Object Properties</p>
                            <p>{{ owl1.owldataprops.size() }} Data Properties</p>
                            <p>{{ owl1.annotationproperties.length }} Annotation Properties</p>
                        </v-col>
                    </v-row>
                    <v-row class="text-start">
                        <v-col>
                            "<b>{{ owl2.label }}</b>" Ontology Includes:
                        </v-col>
                        <v-col>
                            <p>{{ owl2.owlclasses.size() }} Classes</p>
                            <p>{{ owl2.owlobjprops.size() }} Object Properties</p>
                            <p>{{ owl2.owldataprops.size() }} Data Properties</p>
                            <p>{{ owl2.annotationproperties.length }} Annotation Properties</p>
                        </v-col>
                    </v-row>
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
    data() {
        return {
            owl1: this.statistics.visualization.owl1,
            owl2: this.statistics.visualization.owl2
        };
    },
    computed: {
        isReference() {
            return this.owl2.iri == "http://www.semanticweb.org/ntua/iccs/harmonicss/terminology";
        }
    }

};
</script>