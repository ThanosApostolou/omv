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
                    <p>There are total <b>{{ statistics.numberofAllRules }}</b> Rules.</p>
                    <p>There are <b>{{ statistics.numberofClassRules }}</b> (<b>{{ statistics.percentClassRules }}%</b>) Class Rules which connect only classes with each ontology.</p>
                    <p>There are <b>{{ statistics.numberofPropRules }}</b> (<b>{{ statistics.percentPropRules }}%</b>) Properties Rules which connect classes, object properties and data properties with each ontology.</p>
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
                        <v-row v-if="child1.totalclassrules > 0 || child1.totalproprules > 0" no-gutters>
                            <v-col cols="3" lg="2">
                                <v-container fluid>
                                    <OwlEntitySingleSvg :owlentity="child1" entity-type="class" position="left" @show-entity="showEntity" /> has:
                                </v-container>
                            </v-col>
                            <v-col cols="3" lg="2">
                                <v-row no-gutters>
                                    <p><b>{{ child1.totalclassrules }}</b> Class Rules</p>
                                </v-row>
                                <v-row no-gutters>
                                    <p><b>{{ child1.totalproprules }}</b> Properties Rules</p>
                                </v-row>
                                <v-row v-if="child1.children.length > 0" no-gutters>
                                    <p>of which:</p>
                                </v-row>
                            </v-col>
                            <v-col cols="3" lg="2" />
                            <v-col cols="3" lg="2" />
                        </v-row>
                        <div v-for="(child2, index2) in child1.children" :key="index2">
                            <v-row v-if="child2.totalclassrules > 0 || child2.totalproprules > 0" no-gutters>
                                <v-col cols="3" lg="2" />
                                <v-col cols="3" lg="2">
                                    <v-container fluid>
                                        <OwlEntitySingleSvg :owlentity="child2" entity-type="class" position="left" @show-entity="showEntity" /> has:
                                    </v-container>
                                </v-col>
                                <v-col cols="3" lg="2">
                                    <v-row no-gutters>
                                        <p><b>{{ child2.totalclassrules }}</b> Class Rules</p>
                                    </v-row>
                                    <v-row no-gutters>
                                        <p><b>{{ child2.totalproprules }}</b> Properties Rules</p>
                                    </v-row>
                                    <v-row no-gutters v-if="child2.children.length > 0">
                                        of which:
                                    </v-row>
                                </v-col>
                                <v-col cols="3" lg="2" />
                            </v-row>
                            <div v-for="(child3, index3) in child2.children" :key="index3">
                                <v-row no-gutters v-if="child3.totalclassrules > 0 || child3.totalproprules > 0">
                                    <v-col cols="3" lg="2" />
                                    <v-col cols="3" lg="2" />
                                    <v-col cols="3" lg="2">
                                        <v-container fluid>
                                            <OwlEntitySingleSvg :owlentity="child3" entity-type="class" position="left" @show-entity="showEntity" /> has:
                                        </v-container>
                                    </v-col>
                                    <v-col cols="3" lg="2">
                                        <v-row no-gutters>
                                            <p><b>{{ child3.totalclassrules }}</b> Class Rules</p>
                                        </v-row>
                                        <v-row no-gutters>
                                            <p><b>{{ child3.totalproprules }}</b> Properties Rules</p>
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
                    <v-row no-gutters class="text-start">
                        <v-col>
                            <p>"<b>{{ owl1.label }}</b>" Ontology Includes:</p>
                        </v-col>
                        <v-col>
                            <p><b>{{ owl1.owlclasses.size() }}</b> Classes</p>
                            <p><b>{{ owl1.owlobjprops.size() }}</b> Object Properties</p>
                            <p><b>{{ owl1.owldataprops.size() }}</b> Data Properties</p>
                            <p><b>{{ owl1.annotationproperties.length }}</b> Annotation Properties</p>
                        </v-col>
                    </v-row>
                    <v-row no-gutters class="text-start">
                        <v-col>
                            <p>"<b>{{ owl2.label }}</b>" Ontology Includes:</p>
                        </v-col>
                        <v-col>
                            <p><b>{{ owl2.owlclasses.size() }}</b> Classes</p>
                            <p><b>{{ owl2.owlobjprops.size() }}</b> Object Properties</p>
                            <p><b>{{ owl2.owldataprops.size() }}</b> Data Properties</p>
                            <p><b>{{ owl2.annotationproperties.length }}</b> Annotation Properties</p>
                        </v-col>
                    </v-row>
                </v-card-text>
            </v-card>
        </v-card>
        <v-dialog v-if="showdialog" v-model="showdialog" max-width="1400">
            <v-card>
                <v-card-actions>
                    <v-spacer />
                    <v-btn icon @click="showdialog = false">
                        <v-icon>close</v-icon>
                    </v-btn>
                </v-card-actions>
                <OwlEntityInfoComp :owlentity="selectedOwlEntity" />
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
import { Statistics } from "@/entities/Statistics";

import OwlEntitySingleSvg from "./OwlEntitySingleSvg.vue";
import OwlEntityInfoComp from "./OwlEntityInfoComp.vue";

export default {
    name: "VisualizationStatisticsComp",
    components: {
        OwlEntitySingleSvg,
        OwlEntityInfoComp
    },
    props: {
        statistics: {
            type: Statistics,
            default: null
        }
    },
    data() {
        return {
            owl1: this.statistics.visualization.owl1,
            owl2: this.statistics.visualization.owl2,
            showdialog: false,
            selectedOwlEntity: null
        };
    },
    methods: {
        showEntity(owlentitysvg) {
            this.selectedOwlEntity = owlentitysvg.owlentity;
            this.showdialog = true;
        }
    },

};
</script>

<style scoped>
.p {
    min-width: 200px;
}
</style>