<template>
    <div>
        <v-row class="center">
            <v-col cols="3" class="center">
                <v-select v-bind="relationSelect" v-model="relationSelectModel" @change="selectChanged" />
            </v-col>
            <v-col cols="3" class="center">
                <v-switch class="center" v-model="showboxSwitch" label="Show Rule Box" @change="selectChanged" />
            </v-col>
            <v-col v-if="showboxSwitch" cols="3" class="center">
                <v-select v-bind="orderSelect" v-model="orderSelectModel" @change="selectChanged" />
            </v-col>
        </v-row>
        <v-divider />
        <br>
        <v-row v-if="!ready" align="center" justify="center" class="text-center">
            <v-progress-circular indeterminate rotate />
        </v-row>
        <div v-if="ready" :key="relationSelectModel+showboxSwitch+orderSelectModel" class="center">
            <MappingSvgComp v-if="rules != []" :mappingsvg="mappingsvg" @show-entity="showEntity" />
        </div>
        <v-dialog v-if="showdialog" v-model="showdialog">
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
import { RuleSVG } from "./RuleSVG";

import MappingSvgComp from "./MappingSvgComp.vue";
import OwlEntityInfoComp from "./OwlEntityInfoComp";

export default {
    name: "VisualizationEpopticComp",
    components: {
        MappingSvgComp,
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
            showboxSwitch: true,
            ready: false,
            rules: [],
            mappingsvg: null,
            selectedOwlentitysvg: null,
            showdialog: false
        };
    },
    computed: {
        allrulesLength() {
            return this.visualization.mapping.classrules.length+this.visualization.mapping.proprules.length;
        }
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
            this.mappingsvg = new MappingSVG();
            this.mappingsvg.init(this.visualization.owl1.owlclasses, this.visualization.owl1.owlobjprops, this.visualization.owl1.owldataprops, this.visualization.owl2.owlclasses, this.visualization.owl2.owlobjprops, this.visualization.owl2.owldataprops, this.rules, this.showboxSwitch);
            this.ready = true;
        },
        showEntity(owlentity) {
            if (this.selectedOwlentitysvg == null) {
                RuleSVG.listUnsetVisible(this.mappingsvg.rulessvg);
                this.selectedOwlentitysvg = owlentity;
                this.selectedOwlentitysvg.setSelected();
                this.showdialog = true;
            } else if (owlentity == this.selectedOwlentitysvg) {
                this.selectedOwlentitysvg.unsetSelected();
                this.selectedOwlentitysvg = null;
                RuleSVG.listSetVisible(this.mappingsvg.rulessvg);
            } else {
                RuleSVG.listUnsetVisible(this.mappingsvg.rulessvg);
                this.selectedOwlentitysvg.unsetSelected();
                this.selectedOwlentitysvg = owlentity;
                this.selectedOwlentitysvg.setSelected();
                this.showdialog = true;
            }
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