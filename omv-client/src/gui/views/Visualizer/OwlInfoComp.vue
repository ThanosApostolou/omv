<template>
    <div>
        <v-card-title class="text-center justify-center">
            {{ owl.label }}
        </v-card-title>
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
            <v-tab key="annotationproperties">
                Annotation Properties
            </v-tab>
        </v-tabs>
        <v-tabs-items v-model="tab">
            <v-tab-item key="owlinfo">
                <v-card-text class="text-center justify-center">
                    <v-row class="text-start">
                        <v-col cols="4" md="3" lg="2" />
                        <v-col>
                            IRI:
                        </v-col>
                        <v-col>
                            {{ owl.iri }}
                        </v-col>
                    </v-row>
                    <v-row v-if="owl.versionIri != null" class="text-start">
                        <v-col cols="4" md="3" lg="2" />
                        <v-col>
                            VersionIRI:
                        </v-col>
                        <v-col>
                            {{ owl.versionIri }}
                        </v-col>
                    </v-row>
                </v-card-text>
                <AnnotationsComp :annotations="owl.annotations" />
                <v-card-text>
                    <v-row class="text-start">
                        <v-col cols="4" md="3" lg="2" />
                        <v-col>
                            Includes:
                        </v-col>
                        <v-col>
                            <p>{{ owl.owlclasses.size() }} Classes</p>
                            <p>{{ owl.owlobjprops.size() }} Object Properties</p>
                            <p>{{ owl.owldataprops.size() }} Data Properties</p>
                            <p>{{ owl.annotationproperties.length }} Annotation Properties</p>
                        </v-col>
                    </v-row>
                </v-card-text>
            </v-tab-item>
            <v-card-text class="text-center justify-center">
                <v-tab-item key="owlclasses">
                    <OwlEntityComp :owlentity="owl.owlclasses" entity-type="class" />
                </v-tab-item>
                <v-tab-item key="owlobjprops">
                    <OwlEntityComp :owlentity="owl.owlobjprops" entity-type="objprop" />
                </v-tab-item>
                <v-tab-item key="owldataprops">
                    <OwlEntityComp :owlentity="owl.owldataprops" entity-type="dataprop" />
                </v-tab-item>
                <v-tab-item key="annotationproperties">
                    <AnnotationPropertiesComp :annotationproperties="owl.annotationproperties" />
                </v-tab-item>
            </v-card-text>
        </v-tabs-items>
    </div>
</template>

<script>
import OwlEntityComp from "./OwlEntityComp.vue";
import AnnotationsComp from "./AnnotationsComp.vue";
import AnnotationPropertiesComp from "./AnnotationPropertiesComp.vue";

export default {
    name: "OwlInfoComp",
    components: {
        OwlEntityComp,
        AnnotationPropertiesComp,
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