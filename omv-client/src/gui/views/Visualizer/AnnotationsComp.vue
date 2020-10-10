<template>
    <v-card outlined>
        <v-card-title>
            <v-row class="text-start">
                <v-col cols="4" md="3" lg="2">
                    <h4>Annotations:</h4>
                </v-col>
                <v-col>
                    <h5>Property</h5>
                </v-col>
                <v-col>
                    <h5>Value</h5>
                </v-col>
            </v-row>
        </v-card-title>
        <v-card-text>
            <v-row v-for="(annotation, index) in annotations" :key="index" class="text-start">
                <v-col cols="4" md="3" lg="2" />
                <v-col v-if="annotation.property == null">
                    {{ annotation.propertyiri }}
                </v-col>
                <v-col v-if="annotation.property != null">
                    <AnnotationPropertiesSvgComp key="fromannotation" :annotationproperties="[annotation.property]" />
                </v-col>
                <v-col v-if="annotation.valueentity == null">
                    {{ annotation.value }}
                    <small>
                        {{ annotation.type != '' ? '[Type: ' + annotation.type + ']' : '' }}
                        {{ annotation.lang != '' ? '[Lang: ' + annotation.lang + ']' : '' }}
                    </small>
                </v-col>
                <v-col v-if="annotation.valueentity != null">
                    <OwlEntitySingleSvg :owlentity="annotation.valueentity" :entity-type.camel="annotation.valueentitytype" position="left" />
                </v-col>
            </v-row>
        </v-card-text>
    </v-card>
</template>
<script>
import AnnotationPropertiesSvgComp from "./AnnotationPropertiesSvgComp.vue";
import OwlEntitySingleSvg from "./OwlEntitySingleSvg.vue";

export default {
    name: "AnnotationsComp",
    components: {
        AnnotationPropertiesSvgComp,
        OwlEntitySingleSvg
    },
    props: {
        annotations: {
            type: Array,
            default() {return [];}
        }
    }
};
</script>