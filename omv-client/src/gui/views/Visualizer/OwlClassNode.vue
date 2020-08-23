<template>
    <div>
        <svg width='42' height='42'>
            <circle :cx='cx' :cy='cy' :r='r' stroke='black' :stroke-width='strokeWidth' fill='yellow' />
            <text :x='textx' :y='texty'>{{ owlclass.label }}</text>
        </svg>
        asdf
        <OwlClassNode v-for='(child, index) in owlclass.children' :key='child.id' :owlclass='child[Object.keys(child)[0]]' :startx='startx+r' :starty='childrenstarty[index]' />
    </div>

</template>

<script>
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