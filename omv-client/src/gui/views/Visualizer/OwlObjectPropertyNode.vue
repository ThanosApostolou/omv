<template>
    <svg>
        <svg class='svg' @click='showObjprop(owlobjpropsvg.owlobjprop)'>
            <rect :x='owlobjpropsvg.cx+"em"' :y='owlobjpropsvg.starty+"em"' :width='2*owlobjpropsvg.r+"em"' :height='2*owlobjpropsvg.r+"em"' :cx='owlobjpropsvg.cx+"em"' stroke='black' :stroke-width='owlobjpropsvg.stroke+"em"' fill='lightblue' />
            <text :x='owlobjpropsvg.textx+"em"' :y='owlobjpropsvg.texty+"em"'> {{ owlobjpropsvg.owlobjprop.label }}</text>
            <line :x1='owlobjpropsvg.startx+"em"' :y1='owlobjpropsvg.cy+"em"' :x2='owlobjpropsvg.startx+owlobjpropsvg.r+"em"' :y2='owlobjpropsvg.cy+"em"' stroke='black' :stroke-width='owlobjpropsvg.stroke+"em"' />
            <line :x1='owlobjpropsvg.linex1+"em"' :y1='owlobjpropsvg.liney1+"em"' :x2='owlobjpropsvg.linex2+"em"' :y2='owlobjpropsvg.liney2+"em"' stroke='black' :stroke-width='owlobjpropsvg.stroke+"em"' />
        </svg>
        <OwlObjectPropertyNode v-for='child in owlobjpropsvg.children' :key='child.owlobjprop.iri' :owlobjpropsvg='child' @show-objprop='showObjprop' />
    </svg>
</template>

<script>

export default {
    name: 'OwlObjectPropertyNode',
    props: {
        owlobjpropsvg: {
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
        showObjprop(owlobjprop) {
            this.$emit('show-objprop', owlobjprop);
        }
    }
};
</script>

<style>
.svg {
    cursor: pointer;
}
</style>