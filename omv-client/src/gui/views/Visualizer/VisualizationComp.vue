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
                    <v-col class="col2">
                        <v-select v-bind="relationSelect" v-model="relationSelectModel" @change="selectChanged" />
                    </v-col>
                    <v-col class="col2">
                        <v-select v-bind="displaySelect" v-model="displaySelectModel" @change="selectChanged" />
                    </v-col>
                    <v-col class="col2">
                        <v-select v-bind="orderSelect" v-model="orderSelectModel" @change="selectChanged" />
                    </v-col>
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
        <v-divider />
        <br>
        <div v-if="displaySelectModel == 'Whole'" class="center">
            <OwlMappingComp v-if="rules != []" :owl1="visualization.owl1" :owl2="visualization.owl2" :rules="rules" :reverse="false" :visibility-type.camel="relationSelectModel" :key="relationSelectModel+displaySelectModel+orderSelectModel" />
        </div>
        <div v-if="displaySelectModel == 'ByRule'" class="center">
            <OwlMappingComp v-for="(rule, index) in rules" :key="index" :owl1="visualization.owl1" :owl2="visualization.owl2" :rules="[rule]" :reverse="false" :visibility-type.camel="relationSelectModel" />
        </div>
    </div>
</template>

<script>
import { Visualization } from "../../../entities/Visualization.js";

import OwlInfoComp from "./OwlInfoComp.vue";
import OwlMappingComp from "./OwlMappingComp.vue";
export default {
    name: "VisualizationComp",
    components: {
        OwlInfoComp,
        OwlMappingComp
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
            rules: []
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