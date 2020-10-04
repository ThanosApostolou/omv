<template>
    <v-card-text>
        <v-row align="center" justify="center" class="text-center">
            <v-col>
                IRI:
            </v-col>
            <v-col>
                {{ owlentity.iri }}
            </v-col>
        </v-row>
        <v-row align="center" justify="center" class="text-center">
            <v-col>
                Name:
            </v-col>
            <v-col>
                {{ owlentity.label }}
            </v-col>
        </v-row>
        <AnnotationsComp :annotations="owlentity.annotations" />
    </v-card-text>
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