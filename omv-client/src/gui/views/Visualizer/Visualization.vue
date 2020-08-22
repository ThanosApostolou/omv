<template>
    <div>
        <v-row>
            <v-col cols='5'>
                <p>
                    {{ result.visualization.owl1.label }}
                    <v-btn icon @click='owl1info(1)'>
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
                    <v-btn icon @click='owl1info(2)'>
                        <v-icon>info</v-icon>
                    </v-btn>
                </p>
            </v-col>
        </v-row>
        <v-dialog v-model='showdialog'>
            <v-card>
                <v-card-actions>
                    <v-spacer />
                    <v-btn icon @click='showdialog = null'>
                        <v-icon>close</v-icon>
                    </v-btn>
                </v-card-actions>
                adsfa
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

export default {
    name: 'Visualization',
    props: {
        receivedresult: {
            type: Object,
            default: null
        }
    },
    data: function () {
        return {
            result: this.receivedresult,
            showdialog: null,
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
        owl1info(owlnumb) {
            this.showdialog = owlnumb;
            console.log('test', owlnumb);
        }
    },
    mounted() {
        let r = this.result;
        console.log(r);
    }
};
</script>

<style scoped>
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