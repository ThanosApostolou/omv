<template>
    <div>
        <v-row class="center">
            <v-col xs="6" sm="5" md="4" lg="3" xl="2" class="center">
                <v-select v-bind="relationSelect" v-model="relationSelectModel" @change="selectChanged" />
            </v-col>
            <v-col xs="6" sm="5" md="4" lg="3" xl="2" class="center">
                <v-select v-bind="orderSelect" v-model="orderSelectModel" @change="selectChanged" />
            </v-col>
        </v-row>
        <v-divider />
        <br>
        <v-row v-if="!ready" align="center" justify="center" class="text-center">
            <v-progress-circular v-if="!ready" indeterminate rotate />
        </v-row>
        <div v-if="ready" :key="relationSelectModel+orderSelectModel" class="center">
            <MappingSingleComp v-for="(mappingsvg, index) in this.mappingsvgs" :key="index" :mappingsvg="mappingsvg" @show-entity="showEntity" />
        </div>
        <v-dialog v-if="showdialog" v-model="showdialog" max-width="1400">
            <v-card>
                <v-card-actions>
                    <v-spacer />
                    <v-btn icon @click="showdialog = false">
                        <v-icon>close</v-icon>
                    </v-btn>
                </v-card-actions>
                <OwlEntityInfoComp :owlentity="selectedOwlentitysvg.owlentity" />
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
import { Visualization } from "../../../entities/Visualization";
import { MappingSVG } from "./MappingSVG";

import MappingSingleComp from "./MappingSingleComp.vue";
import OwlEntityInfoComp from "./OwlEntityInfoComp";

export default {
    name: "VisualizationByruleComp",
    components: {
        MappingSingleComp,
        OwlEntityInfoComp
    },
    props: {
        visualization: {
            type: Visualization,
            default: null
        }
    },
    data() {
        return {
            relationSelectModel: "allrules",
            relationSelect: {
                label: "Rules Type:",
                items: [
                    {
                        text: "All Rules " + "("+(this.visualization.mapping.classrules.length+this.visualization.mapping.proprules.length)+")",
                        value: "allrules"
                    },
                    {
                        text: "Class Rules " + "("+this.visualization.mapping.classrules.length+")",
                        value: "classrules"
                    },
                    {
                        text: "Properties Rules " + "("+this.visualization.mapping.proprules.length+")",
                        value: "proprules"
                    }
                ]
            },
            orderSelectModel: "left",
            orderSelect: {
                label: "Order by Ontology:",
                items: [
                    {
                        text: "Left",
                        value: "left"
                    },
                    {
                        text: "Right",
                        value: "right"
                    }
                ]
            },
            ready: false,
            rules: [],
            mappingsvgs: null,
            selectedOwlentitysvg: null,
            showdialog: false
        };
    },
    methods: {
        selectChanged() {
            this.ready = false;
            let classRules = [];
            let propRules = [];
            if (this.orderSelectModel == "left") {
                classRules = this.visualization.mapping.classRulesLeft;
                propRules = this.visualization.mapping.propRulesLeft;
            } else {
                classRules = this.visualization.mapping.classRulesRight;
                propRules = this.visualization.mapping.propRulesRight;
            }
            this.rules = [];
            if (this.relationSelectModel == "allrules") {
                this.rules = classRules.concat(propRules);
            } else if (this.relationSelectModel == "classrules") {
                this.rules = classRules;
            } else if (this.relationSelectModel == "proprules") {
                this.rules = propRules;
            }
            MappingSVG.listFromRules(this.visualization.owl1.owlclasses, this.visualization.owl1.owlobjprops, this.visualization.owl1.owldataprops, this.visualization.owl2.owlclasses, this.visualization.owl2.owlobjprops, this.visualization.owl2.owldataprops, this.rules, true).then((mappingsvgs) => {
                this.mappingsvgs = mappingsvgs;
                this.ready = true;
            });
        },
        showEntity(owlentitysvg) {
            this.selectedOwlentitysvg = owlentitysvg;
            this.showdialog = true;
        }
    },
    created() {
        this.selectChanged();
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
</style>