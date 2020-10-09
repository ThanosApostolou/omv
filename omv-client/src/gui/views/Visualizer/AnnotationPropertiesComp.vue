
<template>
    <div>
        <AnnotationPropertiesSvgComp :annotationproperties="annotationproperties" @clicked-annotationproperty="clickedAnnotationProperty" />
        <v-dialog v-if="showdialog" v-model="showdialog" max-width="1400">
            <v-card>
                <v-card-actions>
                    <v-spacer />
                    <v-btn icon @click="showdialog = false">
                        <v-icon>close</v-icon>
                    </v-btn>
                </v-card-actions>
                <AnnotationPropertyInfoComp :owlentity="selectedEntity" />
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
import AnnotationPropertyInfoComp from "./AnnotationPropertyInfoComp.vue";
import AnnotationPropertiesSvgComp from "./AnnotationPropertiesSvgComp.vue";

export default {
    name: "AnnotationPropertiesComp",
    components: {
        AnnotationPropertyInfoComp,
        AnnotationPropertiesSvgComp
    },
    props: {
        annotationproperties: {
            type: Array,
            default: null
        }
    },
    data() {
        return {
            selectedEntity: null,
            showdialog: false
        };
    },
    methods: {
        clickedAnnotationProperty(annotationpropertysvg) {
            this.selectedEntity = annotationpropertysvg.annotationproperty;
            this.showdialog = true;
        }
    }
};
</script>

<style>
.svg {
    cursor: pointer;
}
</style>