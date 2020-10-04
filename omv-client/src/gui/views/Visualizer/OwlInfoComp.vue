<template>
    <div>
        <v-card-title class="text-center justify-center">
            {{ owl.label }}
        </v-card-title>
        <v-card-text class="text-center justify-center">
            <v-tabs v-model="tab" centered>
                <v-tab key="owlinfo">
                    INFO
                </v-tab>
                <v-tab key="owlclasses">
                    Classes
                </v-tab>
                <v-tab key="owlobjprops">
                    Object Properties
                </v-tab>
                <v-tab key="owldataprops">
                    Data Properties
                </v-tab>
            </v-tabs>

            <v-tabs-items v-model="tab">
                <v-tab-item key="owlinfo">
                    <v-row align="center" justify="center" class="text-center">
                        <v-col>
                            IRI:
                        </v-col>
                        <v-col>
                            {{ owl.iri }}
                        </v-col>
                    </v-row>
                    <v-row align="center" justify="center" class="text-center">
                        <v-col>
                            VersionIRI:
                        </v-col>
                        <v-col>
                            {{ owl.versionIri }}
                        </v-col>
                    </v-row>
                    <AnnotationsComp :annotations="owl.annotations" />
                </v-tab-item>
                <v-tab-item key="owlclasses">
                    <OwlEntityComp :owlentity="owl.owlclasses" entity-type="class" />
                </v-tab-item>
                <v-tab-item key="owlobjprops">
                    <OwlEntityComp :owlentity="owl.owlobjprops" entity-type="objprop" />
                </v-tab-item>
                <v-tab-item key="owldataprops">
                    <OwlEntityComp :owlentity="owl.owldataprops" entity-type="dataprop" />
                </v-tab-item>
            </v-tabs-items>
        </v-card-text>
    </div>
</template>

<script>
import OwlEntityComp from "./OwlEntityComp.vue";
import AnnotationsComp from "./AnnotationsComp.vue";

export default {
    name: "OwlInfoComp",
    components: {
        OwlEntityComp,
        AnnotationsComp
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
    }
};
</script>