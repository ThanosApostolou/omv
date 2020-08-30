<template>
    <div>
        <v-row>
            <v-col cols='5'>
                <p>
                    {{ visualization.owl1.label }}
                    <v-btn icon color='primary' @click='owl1info()'>
                        <v-icon>info</v-icon>
                    </v-btn>
                </p>
            </v-col>
            <v-col cols='2' class='col2'>
                <v-select v-bind='mappingsSelect'>
                    asd
                </v-select>
            </v-col>
            <v-col cols='5' class='col3'>
                <p>
                    {{ visualization.owl2.label }}
                    <v-btn icon color='primary' @click='owl2info()'>
                        <v-icon>info</v-icon>
                    </v-btn>
                </p>
            </v-col>
        </v-row>
        <v-dialog v-if='showdialog' v-model='showdialog'>
            <v-card>
                <v-card-actions>
                    <v-spacer />
                    <v-btn icon @click='showdialog = false'>
                        <v-icon>close</v-icon>
                    </v-btn>
                </v-card-actions>
                <OwlInfoComp :owl='current_owl' />
            </v-card>
        </v-dialog>
        <v-divider />
        <v-row>
            <OwlEntityComp :owlentity='visualization.owl1.owlclasses' type='class' :reverse='false' />
        </v-row>
    </div>
</template>

<script>
import OwlInfoComp from './OwlInfoComp.vue';
import OwlEntityComp from './OwlEntityComp.vue';
export default {
    name: 'VisualizationComp',
    components: {
        OwlInfoComp,
        OwlEntityComp
    },
    props: {
        receivedvisualization: {
            type: Object,
            default: null
        }
    },
    data: function () {
        return {
            visualization: this.receivedvisualization,
            showdialog: false,
            current_owl: {},
            mappingsSelect: {
                label: 'Mappings Rules:',
                items: [
                    'all',
                    'simple'
                ]
            }
        };
    },
    methods: {
        owl1info() {
            this.current_owl = this.visualization.owl1;
            this.showdialog = true;
        },
        owl2info() {
            this.current_owl = this.visualization.owl2;
            this.showdialog = true;
        }
    },
    mounted() {
        //let r = this.visualization;
        //console.log(r);
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