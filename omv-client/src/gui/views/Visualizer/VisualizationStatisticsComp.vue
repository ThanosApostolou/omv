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
            <v-card outlined>
                <v-card-title>
                    Reference Model
                </v-card-title>
                <v-card-text>
                    <p>
                        Metrics about top level classes of right Ontology <b>"{{ owl2.label }}"</b>, which is usually used as a reference model:
                    </p>
                    <div v-for="(child1, index1) in owl2.owlclasses.children" :key="index1">
                        <v-row v-if="child1.totalclassrules > 0 || child1.totalproprules > 0">
                            <v-col cols="3" md="2">
                                <b>"{{ child1.label }}"</b> has:
                            </v-col>
                            <v-col cols="3" md="2">
                                <v-row>
                                    {{ child1.totalclassrules }} Class Rules
                                </v-row>
                                <v-row>
                                    {{ child1.totalproprules }} Properties Rules
                                </v-row>
                                <v-row v-if="child1.children.length > 0">
                                    of which:
                                </v-row>
                            </v-col>
                            <v-col cols="3" md="2" />
                            <v-col cols="3" md="2" />
                        </v-row>
                        <div v-for="(child2, index2) in child1.children" :key="index2">
                            <v-row v-if="child2.totalclassrules > 0 || child2.totalproprules > 0">
                                <v-col cols="3" md="2" />
                                <v-col cols="3" md="2">
                                    <b>"{{ child2.label }}"</b> has:
                                </v-col>
                                <v-col cols="3" md="2">
                                    <v-row>
                                        {{ child2.totalclassrules }} Class Rules
                                    </v-row>
                                    <v-row>
                                        {{ child2.totalproprules }} Properties Rules
                                    </v-row>
                                    <v-row v-if="child2.children.length > 0">
                                        of which:
                                    </v-row>
                                </v-col>
                                <v-col cols="3" md="2" />
                            </v-row>
                            <div v-for="(child3, index3) in child2.children" :key="index3">
                                <v-row v-if="child3.totalclassrules > 0 || child3.totalproprules > 0">
                                    <v-col cols="3" md="2" />
                                    <v-col cols="3" md="2" />
                                    <v-col cols="3" md="2">
                                        <b>"{{ child3.label }}"</b> has:
                                    </v-col>
                                    <v-col cols="3" md="2">
                                        <v-row>
                                            {{ child3.totalclassrules }} Class Rules
                                        </v-row>
                                        <v-row>
                                            {{ child3.totalproprules }} Properties Rules
                                        </v-row>
                                    </v-col>
                                </v-row>
                            </div>
                        </div>
                    </div>
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
    }

};
</script>