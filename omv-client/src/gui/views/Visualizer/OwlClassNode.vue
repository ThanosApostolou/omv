<template>
    <svg>
        <svg class='svg' @click='showClass(owlclasssvg.owlclass)'>
            <circle :cx='owlclasssvg.cx+"em"' :cy='owlclasssvg.cy+"em"' :r='owlclasssvg.r+"em"' stroke='black' :stroke-width='owlclasssvg.stroke+"em"' fill='yellow' />
            <text :x='owlclasssvg.textx+"em"' :y='owlclasssvg.texty+"em"'> {{ owlclasssvg.owlclass.label }}</text>
            <line :x1='owlclasssvg.startx+"em"' :y1='owlclasssvg.cy+"em"' :x2='owlclasssvg.startx+owlclasssvg.r+"em"' :y2='owlclasssvg.cy+"em"' stroke='black' :stroke-width='owlclasssvg.stroke+"em"' />
            <line :x1='owlclasssvg.linex1+"em"' :y1='owlclasssvg.liney1+"em"' :x2='owlclasssvg.linex2+"em"' :y2='owlclasssvg.liney2+"em"' stroke='black' :stroke-width='owlclasssvg.stroke+"em"' />
        </svg>
        <OwlClassNode v-for='child in owlclasssvg.children' :key='child.iri' :owlclasssvg='child' @show-class='showClass' />
    </svg>
</template>

<script>

export default {
    name: 'OwlClassNode',
    props: {
        owlclasssvg: {
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
        showClass(owlclass) {
            this.$emit('show-class', owlclass);
        }
    }
};
</script>

<style>
.svg {
    cursor: pointer;
}
</style>