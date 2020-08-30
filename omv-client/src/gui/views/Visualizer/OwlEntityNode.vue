<template>
    <svg>
        <svg class='svg' @click='showEntity(owlentitysvg.owlentity)'>
            <circle v-if='owlentitysvg.type === "class"' :cx='owlentitysvg.cx' :cy='owlentitysvg.cy' :r='owlentitysvg.r' stroke='black' :stroke-width='owlentitysvg.stroke' :fill='owlentitysvg.color' />
            <rect v-if='owlentitysvg.type !== "class"' :x='owlentitysvg.cx' :y='owlentitysvg.starty' :width='2*owlentitysvg.r' :height='2*owlentitysvg.r' :cx='owlentitysvg.cx' stroke='black' :stroke-width='owlentitysvg.stroke' :fill='owlentitysvg.color' />
            <text :x='owlentitysvg.textx' :y='owlentitysvg.texty' :font-size='owlentitysvg.fontSize' :text-anchor='owlentitysvg.textAnchor'> {{ owlentitysvg.owlentity.label }}</text>
            <line :x1='owlentitysvg.startx' :y1='owlentitysvg.cy' :x2='owlentitysvg.line1_x2' :y2='owlentitysvg.cy' stroke='black' :stroke-width='owlentitysvg.stroke' />
            <line :x1='owlentitysvg.linex1' :y1='owlentitysvg.liney1' :x2='owlentitysvg.linex2' :y2='owlentitysvg.liney2' stroke='black' :stroke-width='owlentitysvg.stroke' />
        </svg>
        <OwlObjectPropertyNode v-for='child in owlentitysvg.children' :key='child.owlentity.iri' :owlentitysvg='child' @show-entity='showEntity' />
    </svg>
</template>

<script>

export default {
    name: 'OwlObjectPropertyNode',
    props: {
        owlentitysvg: {
            type: Object,
            default: null
        },
        isRoot: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
        };
    },
    methods: {
        showEntity(owlentity) {
            this.$emit('show-entity', owlentity);
        }
    }
};
</script>

<style>
.svg {
    cursor: pointer;
}
</style>