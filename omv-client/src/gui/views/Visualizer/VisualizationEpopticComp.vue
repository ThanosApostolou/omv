<template>
    <div>
        <v-row class="center">
            <v-col cols="2" class="center">
                <v-select v-bind="relationSelect" v-model="relationSelectModel" @change="selectChanged" />
            </v-col>
            <v-col cols="2" class="center">
                <v-select v-bind="orderSelect" v-model="orderSelectModel" @change="selectChanged" />
            </v-col>
        </v-row>
        <v-divider />
        <br>
        <v-row v-if="!ready" align="center" justify="center" class="text-center">
            <v-progress-circular indeterminate rotate />
        </v-row>
        <div v-if="ready" :key="relationSelectModel+orderSelectModel" class="center">
            <MappingSvgComp v-if="rules != []" :mappingsvg="mappingsvg" />
        </div>
    </div>
</template>

<script>
import { Visualization } from "../../../entities/Visualization";
import { MappingSVG } from "./MappingSVG";

import MappingSvgComp from "./MappingSvgComp.vue";

export default {
    name: "VisualizationEpopticComp",
    components: {
        MappingSvgComp
    },
    props: {
        visualization: {
            type: Visualization,
            default: null
        }
    },
    data() {
        return {
            relationSelectModel: "AllRules",
            relationSelect: {
                label: "Rules' Relation:",
                items: [
                    "AllRules",
                    "EquivalentRules",
                    "LinkedWithRules",
                    "OtherRules"
                ]
            },
            orderSelectModel: "left",
            orderSelect: {
                label: "Order by Ontology:",
                items: [
                    "left",
                    "right"
                ]
            },
            ready: false,
            rules: [],
            mappingsvg: null
        };
    },
    methods: {
        selectChanged() {
            this.ready = false;
            console.log(this.relationSelectModel);
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
            if (this.relationSelectModel == "AllRules") {
                this.rules = classRules.concat(propRules);
            } else if (this.relationSelectModel == "EquivalentRules") {
                this.rules = classRules;
            } else if (this.relationSelectModel == "LinkedWithRules") {
                this.rules = propRules;
            }
            this.mappingsvg = new MappingSVG();
            this.mappingsvg.init(this.visualization.owl1.owlclasses, this.visualization.owl1.owlobjprops, this.visualization.owl1.owldataprops, this.visualization.owl2.owlclasses, this.visualization.owl2.owlobjprops, this.visualization.owl2.owldataprops, this.rules);
            this.ready = true;
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