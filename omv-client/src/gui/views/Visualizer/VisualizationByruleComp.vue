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
        <div :key="relationSelectModel+orderSelectModel" class="center">
            <MappingSvgComp v-for="(newmappingsvg, index) in this.mappingsvg" :key="index" :mappingsvg="newmappingsvg" />
        </div>
    </div>
</template>

<script>
import { Visualization } from "../../../entities/Visualization.js";
import { MappingSVG } from "./MappingSVG.js";

import MappingSvgComp from "./MappingSvgComp.vue";

export default {
    name: "VisualizationByruleComp",
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
            rules: [],
            mappingsvg: null
        };
    },
    methods: {
        selectChanged() {
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
            this.mappingsvg = [];
            for (let rule of this.rules) {
                let newmappingsvg = new MappingSVG();
                newmappingsvg.init(this.visualization.owl1.owlclasses, this.visualization.owl1.owlobjprops, this.visualization.owl1.owldataprops, this.visualization.owl2.owlclasses, this.visualization.owl2.owlobjprops, this.visualization.owl2.owldataprops, [rule]);
                this.mappingsvg.push(newmappingsvg);
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