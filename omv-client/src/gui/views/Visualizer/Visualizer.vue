<template>
    <v-container fluid>
        <v-expansion-panels v-bind="expansionPanel">
            <v-expansion-panel>
                <v-expansion-panel-header>
                    <div class="text-center">
                        <p>Run a new visualization:</p>
                    </div>
                </v-expansion-panel-header>
                <v-expansion-panel-content>
                    <v-row class="col2">
                        <v-tabs centered v-model="displayTab">
                            <v-tab key="single">
                                1 OWL + 1 Mapping
                            </v-tab>
                            <v-tab key="double">
                                2 OWL + 1 Mapping
                            </v-tab>
                        </v-tabs>
                    </v-row>
                    <v-tabs-items v-model="displayTab">
                        <v-tab-item key="single">
                            <v-row class="justify-center text-center">
                                <p>Select an OWL file to visualize against the standard reference model <b>"HarmonicSS-Reference-Model+Vocabularies-v.0.9.3.owl"</b> with their json mapping</p>
                            </v-row>
                            <v-form v-model="valid" outlined>
                                <v-container>
                                    <v-row>
                                        <v-col>
                                            <v-file-input accept=".owl" id="owl1" v-model="owl1" chips counter show-size outlined dense label="OWL File 1" />
                                        </v-col>
                                        <v-col>
                                            <v-file-input accept=".json" v-model="mapping" chips counter show-size outlined dense label="JSON Mapping" />
                                        </v-col>
                                        <v-col align="center" justify="center" class="text-center">
                                            <v-btn v-bind="submitBtn" color="primary" @click="submit('single')">
                                                Visualize
                                            </v-btn>
                                        </v-col>
                                    </v-row>
                                </v-container>
                            </v-form>
                        </v-tab-item>
                        <v-tab-item key="double">
                            <v-row class="justify-center text-center">
                                <p>Select 2 OWL files to visualize with their json mapping</p>
                            </v-row>
                            <v-form v-model="valid" outlined>
                                <v-container>
                                    <v-row>
                                        <v-col>
                                            <v-file-input accept=".owl" id="owl1" v-model="owl1" chips counter show-size outlined dense label="OWL File 1" />
                                        </v-col>
                                        <v-col>
                                            <v-file-input accept=".owl" v-model="owl2" chips counter show-size outlined dense label="OWL File 2" />
                                        </v-col>
                                        <v-col>
                                            <v-file-input accept=".json" v-model="mapping" chips counter show-size outlined dense label="JSON Mapping" />
                                        </v-col>
                                        <v-col align="center" justify="center" class="text-center">
                                            <v-btn v-bind="submitBtn" color="primary" @click="submit('double')">
                                                Visualize
                                            </v-btn>
                                        </v-col>
                                    </v-row>
                                </v-container>
                            </v-form>
                        </v-tab-item>
                    </v-tabs-items>
                </v-expansion-panel-content>
            </v-expansion-panel>
        </v-expansion-panels>
        <v-row v-if="submited" align="center" justify="center" class="text-center">
            <v-progress-circular v-if="!ready" indeterminate rotate />
            <div v-if="ready && !successful">
                {{ result }}
            </div>
        </v-row>
        <div v-if="ready && successful">
            <VisualizationComp v-if="submited && ready" :receivedvisualization="result" />
        </div>
    </v-container>
</template>

<script>
import VisualizationComp from "./VisualizationComp.vue";
import { App } from "../../../App";
import { Visualization } from "../../../entities/Visualization";


export default {
    name: "Visualizer",
    components: {
        VisualizationComp
    },
    data: function () {
        return {
            valid: true,
            result: {},
            owl1: null,
            owl2: null,
            mapping: null,
            submitBtn: {
                disabled: false
            },
            submited: false,
            ready: false,
            expansionPanel: {
                popout: false,
                focusable: true,
                hover: true,
                flat: false,
                multiple: true,
                value: [0]
            },
            displayTab: "single"
        };
    },
    methods: {
        submit(type) {
            this.submitBtn.disabled = true;
            this.submited = true;
            this.successful = false;
            this.ready = false;
            this.result = {};
            const formData = new FormData();
            formData.append("owl1", this.owl1);
            if (type == "double") {
                formData.append("owl2", this.owl2);
            }
            formData.append("mapping", this.mapping);
            App.app.apiconsumer.postVisualization(formData).then((response) => {
                this.result = Visualization.fromObject(response.data.visualization);
                App.app.visualization = this.result;
                this.expansionPanel.value = [];
                this.successful = true;
            }).catch((error) => {
                this.result = error;
                App.app.visualization = null;
                console.log(error);
                this.successful = false;
            }).finally(() => {
                this.ready = true;
                this.submitBtn.disabled = false;
            });
        }
    },
    created() {
        if (App.app.visualization == null) {
            this.ready = false;
            this.submited = false;
            this.successful = false;
            this.result = {};
        } else {
            this.result = App.app.visualization;
            this.submited = true;
            this.successful = true;
            this.ready = true;
            this.expansionPanel.value = [];
        }
    }
};
</script>
