<template>
    <div>
        <v-card-title class='text-center justify-center'>
            {{ owl.label }}
        </v-card-title>
        <v-card-text class='text-center justify-center'>
            <v-tabs v-model='tab' centered>
                <v-tab key='owlinfo'>
                    INFO
                </v-tab>
                <v-tab key='owlclasses'>
                    Classes
                </v-tab>
                <v-tab key='owlobjprops'>
                    Object Properties
                </v-tab>
                <v-tab key='owldataprops'>
                    Data Properties
                </v-tab>
            </v-tabs>

            <v-tabs-items v-model='tab'>
                <v-tab-item key='owlinfo'>
                    <v-row align='center' justify='center' class='text-center'>
                        <v-col>
                            IRI:
                        </v-col>
                        <v-col>
                            {{ owl.iri }}
                        </v-col>
                    </v-row>
                    <v-row align='center' justify='center' class='text-center'>
                        <v-col>
                            VersionIRI:
                        </v-col>
                        <v-col>
                            {{ owl.versionIri }}
                        </v-col>
                    </v-row>
                    <v-row justify='center' class='text-center'>
                        <v-col>
                            Annotations:
                        </v-col>
                        <v-col>
                            <v-row justify='center' class='text-center'>
                                <v-col>
                                    PROPERTY
                                </v-col>
                                <v-col>
                                    VALUE
                                </v-col>
                            </v-row>
                            <v-row v-for='annotation in owl.annotations' :key='annotation.property' justify='center' class='text-center'>
                                <v-col>
                                    {{ annotation.property }}
                                </v-col>
                                <v-col>
                                    {{ annotation.value }}
                                    <small>
                                        {{ annotation.type != '' ? '[Type: ' + annotation.type + ']' : '' }}
                                        {{ annotation.lang != '' ? '[Lang: ' + annotation.lang + ']' : '' }}
                                    </small>
                                </v-col>
                            </v-row>
                        </v-col>
                    </v-row>
                </v-tab-item>
                <v-tab-item key='owlclasses'>
                    <OwlEntityComp v-if='owl != null' :owlentity='owl.owlclasses' type='class' />
                </v-tab-item>
                <v-tab-item key='owlobjprops'>
                    <OwlEntityComp v-if='owl != null' :owlentity='owl.owlobjprops' type='objprop' />
                </v-tab-item>
                <v-tab-item key='owldataprops'>
                    <OwlEntityComp v-if='owl != null' :owlentity='owl.owldataprops' type='dataprop' />
                </v-tab-item>
            </v-tabs-items>
        </v-card-text>
    </div>
</template>

<script>
import OwlEntityComp from './OwlEntityComp.vue';

export default {
    name: 'OwlInfoComp',
    components: {
        OwlEntityComp
    },
    props: {
        owl: {
            type: Object,
            default: null
        }
    },
    data() {
        return {
            tab: null
        };
    },
    mounted() {
    }
};
</script>