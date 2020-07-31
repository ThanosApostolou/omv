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
                        <v-file-input chips counter show-size outlined label='OWL File 1' />
                    </v-col>
                    <v-col>
                        <v-file-input chips counter show-size outlined label='OWL File 2' />
                    </v-col>
                    <v-col>
                        <v-file-input chips counter show-size outlined label='JSON Mappings' />
                    </v-col>
                </v-row>
                <v-row align='center' justify='center' class='text-center'>
                    <v-btn color='primary' @click='submit'>
                        Visualize
                    </v-btn>
                </v-row>
            </v-container>
        </v-form>
        <v-row v-if="ready" align='center' justify='center' class='text-center'>
            {{ result }}
        </v-row>
    </v-container>
</template>

<script>
const axios = require('axios').default;

export default {
    name: 'Visualizer',
    data: function () {
        return {
            valid: true,
            ready: false,
            result: ''
        };
    },
    methods: {
        submit() {
            axios({
                    method: "get",
                    url: "http://localhost:8080/api"
                }).then((response) => {
                    this.result = response.data;
                    this.ready = true;
                }).catch((error) => {
                    console.log(error);
                });
        }
    }
};
</script>
