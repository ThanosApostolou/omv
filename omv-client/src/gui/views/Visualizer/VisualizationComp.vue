<template>
    <div>
        <v-row>
            <v-col cols="5">
                <p>
                    {{ visualization.owl1.label }}
                    <v-btn icon color="primary" @click="owl1info()">
                        <v-icon>info</v-icon>
                    </v-btn>
                </p>
            </v-col>
            <v-col cols="2" class="col2">
                <v-select v-bind="mappingSelect" v-model="selectModel" @change="selectChanged">
                    asd
                </v-select>
            </v-col>
            <v-col cols="5" class="col3">
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
        <v-row>
            <OwlMappingComp :owl1="visualization.owl1" :owl2="visualization.owl2" :mapping="visualization.mapping" type="class" :reverse="false" :visibility-type.camel="selectModel" :key="selectModel" />
        </v-row>
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
            selectModel: "AllRules",
            mappingSelect: {
                label: "Mapping Rules:",
                items: [
                    "AllRules",
                    "EquivalentRules",
                    "LinkedWithRules",
                    "OtherRules"
                ]
            }
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
            console.log(this.selectModel);
        }
    }
};
</script>

<style scoped>
.vcard {
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