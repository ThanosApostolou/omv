<template>
    <svg>
        <svg class='svg' @click='showObjprop(owlentitysvg.owlentity)'>
            <circle v-if='owlentitysvg.type === "class"' :cx='owlentitysvg.cx+"em"' :cy='owlentitysvg.cy+"em"' :r='owlentitysvg.r+"em"' stroke='black' :stroke-width='owlentitysvg.stroke+"em"' :fill='owlentitysvg.color' />
            <rect v-if='owlentitysvg.type !== "class"' :x='owlentitysvg.cx+"em"' :y='owlentitysvg.starty+"em"' :width='2*owlentitysvg.r+"em"' :height='2*owlentitysvg.r+"em"' :cx='owlentitysvg.cx+"em"' stroke='black' :stroke-width='owlentitysvg.stroke+"em"' :fill='owlentitysvg.color' />
            <text :x='owlentitysvg.textx+"em"' :y='owlentitysvg.texty+"em"'> {{ owlentitysvg.owlentity.label }}</text>
            <line :x1='owlentitysvg.startx+"em"' :y1='owlentitysvg.cy+"em"' :x2='owlentitysvg.startx+owlentitysvg.r+"em"' :y2='owlentitysvg.cy+"em"' stroke='black' :stroke-width='owlentitysvg.stroke+"em"' />
            <line :x1='owlentitysvg.linex1+"em"' :y1='owlentitysvg.liney1+"em"' :x2='owlentitysvg.linex2+"em"' :y2='owlentitysvg.liney2+"em"' stroke='black' :stroke-width='owlentitysvg.stroke+"em"' />
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