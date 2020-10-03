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
            relationSelectModel: "allrules",
            relationSelect: {
                label: "Rules' Relation:",
                items: [
                    {
                        text: "All Rules",
                        value: "allrules"
                    },
                    {
                        text: "Class Rules",
                        value: "classrules"
                    },
                    {
                        text: "Properties Rules",
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