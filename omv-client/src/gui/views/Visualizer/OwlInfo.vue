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
                                    NAME
                                </v-col>
                                <v-col>
                                    VALUE
                                </v-col>
                            </v-row>
                            <v-row v-for='annotation in Object.entries(owl.annotations)' :key='annotation' justify='center' class='text-center'>
                                <v-col>
                                    {{ annotation[0] }}
                                </v-col>
                                <v-col>
                                    {{ annotation[1].split('\"')[1] }}
                                    <small>
                                        {{ annotation[1].split('^^')[1] != null ? '[Type: ' + annotation[1].split('^^')[1] + ']' : '' }}
                                    </small>
                                </v-col>
                            </v-row>
                        </v-col>
                    </v-row>
                </v-tab-item>
                <v-tab-item key='owlclasses'>
                    asdf
                </v-tab-item>
            </v-tabs-items>
        </v-card-text>
    </div>
</template>

<script>
export default {
    name: 'OwlInfo',
    props: {
        owl: {
            type: Object,
            default: null
        }
    },
    data() {
        return {
            tab: null,
        };
    },
    mounted() {
    }
};
</script>