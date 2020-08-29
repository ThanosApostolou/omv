<template>
    <div>
        <svg :x='owlobjpropsvg.startx+"em"' :y='owlobjpropsvg.starty+"em"' :height='owlobjpropsvg.height+"em"' :width='owlobjpropsvg.width+"em"'>
            <OwlObjectPropertyNode :owlobjpropsvg='owlobjpropsvg' @show-objprop='showObjprop' />
        </svg>
        <v-dialog v-model='show'>
            <v-card>
                <span> {{ selectedObjectProperty.label }}</span>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
import OwlObjectProperty from '../../../entities/OwlObjectProperty.js';
import OwlObjectPropertyNode from './OwlObjectPropertyNode.vue';

export class OwlObjectPropertySVG {
    /** @type {OwlObjectProperty} */
    owlobjprop;
    /** @type {OwlObjectPropertySVG[]} */
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

    /** @param {OwlObjectProperty} owlobjprop
     *  @returns {OwlObjectPropertySVG}
     */
    static fromOwlObjectProperty(owlobjprop) {
        let owlobjpropsvg = new OwlObjectPropertySVG();
        owlobjpropsvg.owlobjprop = owlobjprop;
        owlobjpropsvg.children = [];
        for (let child of owlobjprop.children) {
            owlobjpropsvg.children.push(OwlObjectPropertySVG.fromOwlObjectProperty(child));
        }
        return owlobjpropsvg;
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
        this.textx = this.cx + 2*this.r + 0.5;
        this.texty = this.cy + this.r/2;
        this.linex1 = this.cx + this.r;
        this.liney1 = this.starty + 2*this.r;
        this.linex2 = this.cx + this.r;
        this.liney2 = this.liney1;
        this.width = this.cx + this.r + this.owlobjprop.label.length;
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
    name: 'OwlObjectPropertyComp',
    components: {
        OwlObjectPropertyNode
    },
    props: {
        owlobjprop: {
            type: OwlObjectProperty,
            default: null
        },
    },
    data() {
        return {
            owlobjpropsvg: null,
            show: false,
            selectedObjectProperty: {}
        };
    },
    methods: {
        showObjprop(objprop) {
            this.selectedObjectProperty = objprop;
            this.show = true;
        },
    },
    created() {
        this.owlobjpropsvg = OwlObjectPropertySVG.fromOwlObjectProperty(this.owlobjprop);
        this.owlobjpropsvg.calcPositions(0, 0);

        console.log(this.owlobjpropsvg.linex2);
        console.log(this.owlobjpropsvg.liney2);
    }
};

</script>