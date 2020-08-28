<template>
    <svg :x='owlclasssvg.startx+"em"' :y='owlclasssvg.starty+"em"' :height='owlclasssvg.height+"em"' :width='owlclasssvg.width+"em"'>
        <OwlClassNode :owlclasssvg='owlclasssvg' />
    </svg>
</template>

<script>
import OwlClass from '../../../entities/OwlClass.js';
import OwlClassNode from './OwlClassNode.vue';

export class OwlClassSVG {
    /** @type {OwlClass} */
    owlclass;
    /** @type {OwlClassSVG[]} */
    children;

    /** @type {Boolean} */
    visible;
    /** @type {Number} */
    height;
    /** @type {Number} */
    width;
    /** @type {Number} */
    r;
    /** @type {Number} */
    stroke = 0.05;
    /** @type {Number} */
    startx;
    /** @type {Number} */
    starty;
    /** @type {Number} */
    cx;
    /** @type {Number} */
    cy;
    /** @type {Number} */
    textx;
    /** @type {Number} */
    texty;

    /** @param {OwlClass} owlclass
     *  @returns {OwlClassSVG}
     */
    static fromOwlClass(owlclass) {
        let owlclasssvg = new OwlClassSVG();
        owlclasssvg.owlclass = owlclass;
        owlclasssvg.children = [];
        for (let child of owlclass.children) {
            owlclasssvg.children.push(OwlClassSVG.fromOwlClass(child));
        }
        return owlclasssvg;
    }

    /** @param {Number} x
     *  @returns {Number}
     */
    calcPositions(x, y) {
        this.r = 0.6;
        this.startx = x;
        this.starty = y;
        this.cx = this.startx + this.r;
        this.cy = this.starty + this.r;
        this.width = this.cx + this.r + this.owlclass.label.length;
        this.textx = 0.5+this.cx + this.r;
        this.texty = this.cy + this.r/2;
        let nextx = this.startx + 2*this.r;
        let nexty = this.starty + 2*this.r;
        for (let child of this.children) {
            let newwidth=0;
            [nexty, newwidth] = child.calcPositions(nextx, nexty);
            if (newwidth > this.width) {
                this.width = newwidth;
            }
        }
        this.height = nexty - this.starty;
        return [nexty, this.width];
    }
}

export default {
    name: 'OwlClassComp',
    components: {
        OwlClassNode
    },
    props: {
        owlclass: {
            type: OwlClass,
            default: null
        },
    },
    data() {
        return {
            owlclasssvg: null
        };
    },
    created() {
        this.owlclasssvg = OwlClassSVG.fromOwlClass(this.owlclass);
        this.owlclasssvg.calcPositions(0, 0);

        console.log(this.owlclasssvg.children[2].height);
        console.log(this.owlclasssvg.children[2].starty);
    }
};

</script>