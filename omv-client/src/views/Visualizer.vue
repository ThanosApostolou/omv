<template>
    <v-container>
        <div align='center' justify='center' class='text-center'>
            <h1>Visualizer</h1>
            <p>Select 2 owl files and their json mappings.</p>
        </div>
        <v-form v-model='valid' outlined>
            <v-container>
                <v-row>
                    <v-col>
                        <v-file-input v-model='owl1' chips counter show-size outlined label='OWL File 1' />
                    </v-col>
                    <v-col>
                        <v-file-input v-model='owl2' chips counter show-size outlined label='OWL File 2' />
                    </v-col>
                    <v-col>
                        <v-file-input v-model='mappings' chips counter show-size outlined label='JSON Mappings' />
                    </v-col>
                </v-row>
                <v-row v-model='submitBtn' align='center' justify='center' class='text-center'>
                    <v-btn :disabled='submitBtn.disabled' color='primary' @click='submit'>
                        Visualize
                    </v-btn>
                </v-row>
            </v-container>
        </v-form>
        <v-row v-if='submited && !ready' align='center' justify='center' class='text-center'>
            <v-progress-circular indeterminate rotate />
        </v-row>
        <VisualizationSVG v-if='ready && submited && successful' :received-result='result' />
        <div v-if='ready && submited && !successful'>
            {{ result }}
        </div>
    </v-container>
</template>

<script>
const axios = require('axios').default;
import VisualizationSVG from '../components/VisualizationSVG.vue';

export default {
    name: 'Visualizer',
    components: {
        VisualizationSVG
    },
    data: function () {
        return {
            valid: true,
            result: '',
            owl1: null,
            owl2: null,
            mappings: null,
            submitBtn: {
                disabled: false
            },
            submited: false,
            ready: false,
        };
    },
    methods: {
        submit() {
            this.submitBtn.disabled = true;
            this.submited = true;
            this.successful = false;
            this.ready = false;
            this.result = null;
            const formData = new FormData();
            formData.append('owl1', this.owl1);
            formData.append('owl2', this.owl2);
            formData.append('mappings', this.mappings);
            axios({
                method: 'post',
                url: 'http://localhost:8080/api/visualization',
                headers: {
                    'Content-Type': 'multipart/form-data'
                },
                data: formData,
            }).then((response) => {
                this.result = {
                    'cls1': [
                        {
                            'cls2': [
                            ]
                        }
                    ]
                };
                console.log(this.result);
                console.log(response.data);
                this.successful = true;
            }).catch((error) => {
                this.result = error;
                console.log(error);
                this.successful = false;
            }).finally(() => {
                console.log(this.submitBtn);
                this.ready = true;
                this.submitBtn.disabled = false;
            });
        },
        visualize() {

        }
    }
};
</script>
