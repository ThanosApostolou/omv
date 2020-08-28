<template>
    <svg>
        <circle :cx='owlclasssvg.cx+"em"' :cy='owlclasssvg.cy+"em"' :r='owlclasssvg.r+"em"' stroke='black' :stroke-width='owlclasssvg.stroke+"em"' fill='yellow' />
        <text :x='owlclasssvg.textx+"em"' :y='owlclasssvg.texty+"em"'>{{ owlclasssvg.owlclass.label }}</text>
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