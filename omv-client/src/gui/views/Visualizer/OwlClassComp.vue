<template>
    <div>
        <svg :x='owlclasssvg.startx+"em"' :y='owlclasssvg.starty+"em"' :height='owlclasssvg.height+"em"' :width='owlclasssvg.width+"em"'>
            <OwlClassNode :owlclasssvg='owlclasssvg' @show-class='showClass' />
        </svg>
        <v-dialog v-model='show'>
            <v-card>
                <span> {{ selectedOwlClass.label }}</span>
            </v-card>
        </v-dialog>
    </div>
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
    /** @type {Number} */
    linex1;
    /** @type {Number} */
    liney1;
    /** @type {Number} */
    linex2;
    /** @type {Number} */
    liney2;

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
        this.cx = this.startx + 2*this.r;
        this.cy = this.starty + this.r;
        this.textx = this.cx + this.r;
        this.texty = this.cy + this.r/2;
        this.linex1 = this.cx;
        this.liney1 = this.starty + 2*this.r;
        this.linex2 = this.cx;
        this.liney2 = this.liney1;
        this.width = this.cx + this.r + this.owlclass.label.length;
        let nextx = this.startx + 2*this.r;
        let nexty = this.starty + 2*this.r;
        for (let child of this.children) {
            let newwidth=0, childcy=0;
            [nexty, newwidth, childcy] = child.calcPositions(nextx, nexty);
            if (newwidth > this.width) {
                this.width = newwidth;
            }
            this.liney2 = childcy;
        }
        this.height = nexty - this.starty;
        return [nexty, this.width, this.cy];
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
            owlclasssvg: null,
            show: false,
            selectedOwlClass: {}
        };
    },
    methods: {
        showClass(owlclass) {
            this.selectedOwlClass = owlclass;
            this.show = true;
        },
    },
    created() {
        this.owlclasssvg = OwlClassSVG.fromOwlClass(this.owlclass);
        this.owlclasssvg.calcPositions(0, 0);

        console.log(this.owlclasssvg.linex2);
        console.log(this.owlclasssvg.liney2);
    }
};

</script>