<template>
    <div>
        <v-row>
            <v-col cols="4">
                <p>
                    {{ visualization.owl1.label }}
                    <v-btn icon color="primary" @click="owl1info()">
                        <v-icon>info</v-icon>
                    </v-btn>
                </p>
            </v-col>
            <v-col cols="4" class="col2">
                <v-row class="col2">
                    <v-tabs v-model="displayTab">
                        <v-tab key="epoptic">Epoptic View</v-tab>
                        <v-tab key="byrule">View By Rule</v-tab>
                        <v-tab key="statistics">Statistics</v-tab>
                    </v-tabs>
                    <!--<v-col class="col2">
                        <v-select v-bind="relationSelect" v-model="relationSelectModel" @change="selectChanged" />
                    </v-col>
                    <v-col class="col2">
                        <v-select v-bind="displaySelect" v-model="displaySelectModel" @change="selectChanged" />
                    </v-col>
                    <v-col class="col2">
                        <v-select v-bind="orderSelect" v-model="orderSelectModel" @change="selectChanged" />
                    </v-col>-->
                </v-row>
            </v-col>
            <v-col cols="4" class="col3">
                <p>
                    {{ visualization.owl2.label }}
                    <v-btn icon color="primary" @click="owl2info()">
                        <v-icon>info</v-icon>
                    </v-btn>
                </p>
            </v-col>
        </v-row>
        <v-dialog v-model="showdialog">
            <v-card>
                <v-card-actions>
                    <v-spacer />
                    <v-btn icon @click="showdialog = false">
                        <v-icon>close</v-icon>
                    </v-btn>
                </v-card-actions>
                <OwlInfoComp :owl="current_owl" :key="current_owl_key" />
            </v-card>
        </v-dialog>
        <v-tabs-items v-model="displayTab">
            <v-tab-item key="epoptic">
                <VisualizationEpopticComp :visualization="visualization" />
            </v-tab-item>
            <v-tab-item key="byrule">
                <VisualizationByruleComp :visualization="visualization" />
            </v-tab-item>
            <v-tab-item key="statistics"> asdf</v-tab-item>
        </v-tabs-items>
    </div>
</template>

<script>
import { Visualization } from "../../../entities/Visualization.js";
import { MappingSVG } from "./MappingSVG.js";
import OwlInfoComp from "./OwlInfoComp.vue";
import MappingSvgComp from "./MappingSvgComp.vue";
import VisualizationEpopticComp from "./VisualizationEpopticComp.vue";
import VisualizationByruleComp from "./VisualizationByruleComp.vue";

export default {
    name: "VisualizationComp",
    components: {
        OwlInfoComp,
        VisualizationEpopticComp,
        VisualizationByruleComp
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
            current_owl: {},
            current_owl_key: null,
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
            displaySelectModel: "Whole",
            displaySelect: {
                label: "Display rules",
                items: [
                    "Whole",
                    "ByRule"
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
            displayTab: null,
            mappingsvg: null
        };
    },
    methods: {
        owl1info() {
            this.current_owl = this.visualization.owl1;
            this.current_owl_key = "owl1";
            this.showdialog = true;
        },
        owl2info() {
            this.current_owl = this.visualization.owl2;
            this.current_owl_key = "owl2";
            this.showdialog = true;
        },
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

            if (this.displaySelectModel == "Whole") {
                this.mappingsvg = new MappingSVG();
                this.mappingsvg.init(this.visualization.owl1.owlclasses, this.visualization.owl1.owlobjprops, this.visualization.owl1.owldataprops, this.visualization.owl2.owlclasses, this.visualization.owl2.owlobjprops, this.visualization.owl2.owldataprops, this.rules);
            } else if (this.displaySelectModel == "ByRule") {
                this.mappingsvg = [];
                for (let rule of this.rules) {
                    let newmappingsvg = new MappingSVG();
                    newmappingsvg.init(this.visualization.owl1.owlclasses, this.visualization.owl1.owlobjprops, this.visualization.owl1.owldataprops, this.visualization.owl2.owlclasses, this.visualization.owl2.owlobjprops, this.visualization.owl2.owldataprops, [rule]);
                    this.mappingsvg.push(newmappingsvg);
                }
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