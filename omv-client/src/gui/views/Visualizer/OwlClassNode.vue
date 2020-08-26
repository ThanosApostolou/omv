<template>
    <div>
        <svg :height='owlclass.height' :width='owlclass.width'>
            <circle :cx='owlclass.cx' :cy='owlclass.cy' :r='owlclass.r' stroke='black' :stroke-width='strokeWidth' fill='yellow' />
            <text :x='owlclass.textx' :y='texty'></text>
        </svg>
        <small> {{ owlclass.label }}</small>
        <OwlClassNode v-for='child in owlclass.children' :key='child.iri' :owlclass='child' />
    </div>
</template>

<script>
class OwlClassSVG {

}

export default {
    name: 'OwlClassNode',
    props: {
        owlclass: {
            type: Object,
            default: null
        },
        startx: {
            type: Number,
            default: 0
        },
        starty: {
            type: Number,
            default: 0
        }
    },
    data() {
        return {
            r: 10,
            strokeWidth: 1
        };
    },
    computed: {
        cx() {
            return this.startx + this.r + this.strokeWidth;
        },
        cy() {
            return this.starty + this.r + this.strokeWidth;
        },
        textx() {
            return this.cx + this.r + this.strokeWidth;
        },
        texty() {
            return this.cy + (this.r + this.strokeWidth)/2;
        },
        childrenstarty() {
            let childrenstarty = [];
            let y = this.starty;
            for (let child of this.owlclass.children) {
                y += 2*this.r;
                childrenstarty.push(y);
            }
            return childrenstarty;
        }
    }

};
</script>