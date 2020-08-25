<template>
    <v-container>
        <v-expansion-panels v-bind='expansionPanel'>
            <v-expansion-panel>
                <v-expansion-panel-header>
                    <div align='center' justify='center' class='text-center'>
                        <p>Visualizer: Select 2 owl files and their json mappings.</p>
                    </div>
                </v-expansion-panel-header>
                <v-expansion-panel-content>
                    <v-form v-model='valid' outlined>
                        <v-container>
                            <v-row>
                                <v-col>
                                    <v-file-input id='owl1' v-model='owl1' chips counter show-size outlined dense label='OWL File 1' />
                                </v-col>
                                <v-col>
                                    <v-file-input v-model='owl2' chips counter show-size outlined dense label='OWL File 2' />
                                </v-col>
                                <v-col>
                                    <v-file-input v-model='mappings' chips counter show-size outlined dense label='JSON Mappings' />
                                </v-col>
                                <v-col align='center' justify='center' class='text-center'>
                                    <v-btn v-bind='submitBtn' color='primary' @click='submit'>
                                        Visualize
                                    </v-btn>
                                </v-col>
                            </v-row>
                        </v-container>
                    </v-form>
                </v-expansion-panel-content>
            </v-expansion-panel>
        </v-expansion-panels>
        <v-row v-if='submited' align='center' justify='center' class='text-center'>
            <v-progress-circular v-if='!ready' indeterminate rotate />
            <div v-if='ready && !successful'>
                {{ result }}
            </div>
        </v-row>
        <div v-if='ready && successful'>
            <VisualizationComp v-if='submited && ready' :receivedvisualization='result' />
        </div>
    </v-container>
</template>

<script>
import VisualizationComp from './VisualizationComp.vue';
import { App } from '../../../App.js';
import { Visualization } from '../../../entities/Visualization.js';


export default {
    name: 'Visualizer',
    components: {
        VisualizationComp
    },
    data: function () {
        return {
            valid: true,
            result: {},
            owl1: null,
            owl2: null,
            mappings: null,
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
            }
        };
    },
    methods: {
        submit() {
            this.submitBtn.disabled = true;
            this.submited = true;
            this.successful = false;
            this.ready = false;
            this.result = {};
            const formData = new FormData();
            formData.append('owl1', this.owl1);
            formData.append('owl2', this.owl2);
            formData.append('mappings', this.mappings);
            App.app.apiconsumer.postVisualization(formData).then((response) => {
                this.result = Visualization.fromObject(response.data.visualization);
                console.log('response.data: ', this.result.owl1);
                this.expansionPanel.value = [];
                this.successful = true;
            }).catch((error) => {
                this.result = error;
                console.log(error);
                this.successful = false;
            }).finally(() => {
                this.ready = true;
                this.submitBtn.disabled = false;
            });
        }
    }
};
</script>
