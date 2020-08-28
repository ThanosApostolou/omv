<template>
    <svg>
        <circle :cx='owlclasssvg.cx+"em"' :cy='owlclasssvg.cy+"em"' :r='owlclasssvg.r+"em"' stroke='black' :stroke-width='owlclasssvg.stroke+"em"' fill='yellow' />
        <text :x='owlclasssvg.textx+"em"' :y='owlclasssvg.texty+"em"'>{{ owlclasssvg.owlclass.label }}</text>
        <line :x1='owlclasssvg.startx+"em"' :y1='owlclasssvg.cy+"em"' :x2='owlclasssvg.startx+owlclasssvg.r+"em"' :y2='owlclasssvg.cy+"em"' stroke='black' :stroke-width='owlclasssvg.stroke+"em"' />
        <line :x1='owlclasssvg.linex1+"em"' :y1='owlclasssvg.liney1+"em"' :x2='owlclasssvg.linex2+"em"' :y2='owlclasssvg.liney2+"em"' stroke='black' :stroke-width='owlclasssvg.stroke+"em"' />
        <OwlClassNode v-for='child in owlclasssvg.children' :key='child.iri' :owlclasssvg='child' />
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
    }
};
</script>