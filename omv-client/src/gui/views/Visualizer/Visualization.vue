<template>
    <div>
        <v-row>
            <v-col cols='5'>
                <p>
                    {{ result.visualization.owl1.label }}
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
                    {{ result.visualization.owl2.label }}
                    <v-btn icon color='primary' @click='owl2info()'>
                        <v-icon>info</v-icon>
                    </v-btn>
                </p>
            </v-col>
        </v-row>
        <v-dialog v-model='showdialog'>
            <v-card>
                <v-card-actions>
                    <v-spacer />
                    <v-btn icon @click='showdialog = false'>
                        <v-icon>close</v-icon>
                    </v-btn>
                </v-card-actions>
                <OwlInfo :owl='current_owl' />
            </v-card>
        </v-dialog>
        <v-divider />
        <v-row>
            <v-col cols='5'>
                <svg height='100%' width='100%'>
                    <circle cx='11' cy='11' r='10' stroke='black' stroke-width='1' fill='yellow' />
                    <svg width='100' height='100'>
                        <circle cx='22' cy='21' r='10' stroke='black' stroke-width='1' fill='yellow' />
                    </svg>
                </svg>
            </v-col>
            <v-col cols='2' class='col2'>
                ad
            </v-col>
            <v-col cols='5' class='col3'>
                <svg height='100%' width='100%'>
                    <circle cx='11' cy='11' r='10' stroke='black' stroke-width='1' fill='yellow' />
                    <svg width='100' height='100'>
                        <circle cx='22' cy='21' r='10' stroke='black' stroke-width='1' fill='yellow' />
                    </svg>
                </svg>
            </v-col>
        </v-row>
    </div>
</template>

<script>
import OwlInfo from './OwlInfo.vue';
export default {
    name: 'Visualization',
    components: {
        OwlInfo
    },
    props: {
        receivedresult: {
            type: Object,
            default: null
        }
    },
    data: function () {
        return {
            result: this.receivedresult,
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
            this.current_owl = this.result.visualization.owl1;
            this.showdialog = true;
        },
        owl2info() {
            this.current_owl = this.result.visualization.owl2;
            this.showdialog = true;
        }
    },
    mounted() {
        let r = this.result;
        console.log(r);
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