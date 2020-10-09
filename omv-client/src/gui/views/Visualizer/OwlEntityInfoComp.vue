<template>
    <div>
        <v-card-title>
            <v-row class="text-start">
                <v-col cols="4" md="3" lg="2" />
                <v-col>
                    <h5>Name:</h5>
                </v-col>
                <v-col>
                    <h5>{{ owlentity.label }}</h5>
                </v-col>
            </v-row>
        </v-card-title>
        <v-card-text>
            <v-row class="text-start">
                <v-col cols="4" md="3" lg="2" />
                <v-col>
                    IRI:
                </v-col>
                <v-col>
                    {{ owlentity.iri }}
                </v-col>
            </v-row>
        </v-card-text>
        <AnnotationsComp v-if="owlentity.annotations != null && owlentity.annotations.length > 0" :annotations="owlentity.annotations" />
        <v-card-text>
            <v-row v-if="owlentity.classrules.length > 0" class="text-start">
                <v-col cols="4" md="3" lg="2" />
                <v-col>
                    takes part in:
                </v-col>
                <v-col>
                    {{ owlentity.classrules.length }} Class Rules
                </v-col>
            </v-row>
            <v-row v-if="owlentity.proprules.length > 0" class="text-start">
                <v-col cols="4" md="3" lg="2" />
                <v-col>
                    takes part in:
                </v-col>
                <v-col>
                    {{ owlentity.proprules.length }} Properties Rules
                </v-col>
            </v-row>
        </v-card-text>
    </div>
</template>

<script>
import { OwlEntity } from "@/entities/OwlEntity";
import { AnnotationProperty } from "@/entities/AnnotationProperty";

import AnnotationsComp from "./AnnotationsComp.vue";

export default {
    name: "OwlEntityInfoComp",
    components: {
        AnnotationsComp
    },
    props: {
        owlentity: {
            type: [OwlEntity, AnnotationProperty],
            default: null
        }
    }
};
</script>