
<template>
    <svg :x="0" :y="0" :height="this.annotationpropertiessvg.totalheight" :width="this.annotationpropertiessvg.totalwidth">
        <svg v-for="(annotationpropertysvg, index) in annotationpropertiessvg.list" :key="index" class="svg" @click="clickedAnnotationProperty(annotationpropertysvg)">
            <line v-for="(line, index2) in annotationpropertysvg.lines" :key="index2" :x1="line.x1" :y1="line.y1" :x2="line.x2" :y2="line.y2" stroke="black" stroke-width="1" />
            <rect :x="annotationpropertysvg.rect.x" :y="annotationpropertysvg.rect.y" :width="2*annotationpropertiessvg.r" :height="2*annotationpropertiessvg.r" stroke="black" stroke-width="1" fill="brown" />
            <text :x="annotationpropertysvg.text.x" :y="annotationpropertysvg.text.y" :font-size="annotationpropertiessvg.fontSize" :textLength="annotationpropertysvg.text.textLength"> {{ annotationpropertysvg.annotationproperty.label }}</text>

        </svg>
    </svg>
</template>

<script>
import { AnnotationPropertiesSvg } from "./AnnotationPropertiesSvg";

export default {
    name: "AnnotationPropertiesSvgComp",
    props: {
        annotationproperties: {
            type: Array,
            default: null
        }
    },
    data() {
        return {
            annotationpropertiessvg: null
        };
    },
    methods: {
        clickedAnnotationProperty(annotationpropertysvg) {
            this.$emit("clicked-annotationproperty", annotationpropertysvg);
        }
    },
    created() {
        this.annotationpropertiessvg = new AnnotationPropertiesSvg();
        this.annotationpropertiessvg.create(this.annotationproperties);
    }
};
</script>

<style>
.svg {
    cursor: pointer;
}
</style>