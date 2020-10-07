<template>
    <div>
        <v-card-title>
            <v-row class="text-start">
                <v-col cols="1" />
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
                <v-col cols="1" />
                <v-col>
                    IRI:
                </v-col>
                <v-col>
                    {{ owlentity.iri }}
                </v-col>
            </v-row>
        </v-card-text>
        <AnnotationsComp :annotations="owlentity.annotations" />
    </div>
</template>

<script>
import { OwlEntity } from "@/entities/OwlEntity";
import AnnotationsComp from "./AnnotationsComp.vue";

export default {
    name: "OwlEntityInfoComp",
    components: {
        AnnotationsComp
    },
    props: {
        owlentity: {
            type: OwlEntity,
            default: null
        }
    }
};
</script>