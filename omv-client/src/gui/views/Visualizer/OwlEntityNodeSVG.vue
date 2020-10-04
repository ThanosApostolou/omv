<template>
    <svg v-if="owlentitysvg.visible">
        <svg class="svg" @click="showEntity(owlentitysvg)">
            <circle v-if="owlentitysvg.entityType === 'class'" :cx="owlentitysvg.cx" :cy="owlentitysvg.cy" :r="owlentitysvg.r" stroke="black" :stroke-width="owlentitysvg.stroke" :fill="owlentitysvg.color" />
            <rect v-if="owlentitysvg.entityType !== 'class'" :x="owlentitysvg.cx" :y="owlentitysvg.starty" :width="2*owlentitysvg.r" :height="2*owlentitysvg.r" :cx="owlentitysvg.cx" stroke="black" :stroke-width="owlentitysvg.stroke" :fill="owlentitysvg.color" />
            <text :x="owlentitysvg.textx" :y="owlentitysvg.texty" :font-size="owlentitysvg.fontSize" :text-anchor="owlentitysvg.textAnchor" :textLength="owlentitysvg.textLength" :font-weight="owlentitysvg.hasRule ? 'bold' : 'normal'" :fill="textColor" :text-decoration="textDecoration"> {{ owlentitysvg.owlentity.label }}</text>
            <line :x1="owlentitysvg.startx" :y1="owlentitysvg.cy" :x2="owlentitysvg.line1_x2" :y2="owlentitysvg.cy" stroke="black" :stroke-width="owlentitysvg.stroke" />
            <line :x1="owlentitysvg.linex1" :y1="owlentitysvg.liney1" :x2="owlentitysvg.linex2" :y2="owlentitysvg.liney2" stroke="black" :stroke-width="owlentitysvg.stroke" />
        </svg>
        <template v-for="child in owlentitysvg.children">
            <OwlEntityNodeSVG :key="child.owlentity.iri" :owlentitysvg="child" @show-entity="showEntity" />
        </template>
    </svg>
</template>

<script>

export default {
    name: "OwlEntityNodeSVG",
    props: {
        owlentitysvg: {
            type: Object,
            default: null
        }
    },
    data() {
        return {
        };
    },
    computed: {
        textColor() {
            return this.owlentitysvg.isSelected || (this.owlentitysvg.isActive && this.owlentitysvg.hasRule) ? "blue" : "black";
        },
        textDecoration() {
            return this.owlentitysvg.isSelected ? "underline" : "";
        }
    },
    methods: {
        showEntity(owlentity) {
            this.$emit("show-entity", owlentity);
        }
    }
};
</script>

<style>
.svg {
    cursor: pointer;
}
</style>