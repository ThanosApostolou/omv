<template>
    <div>
        <svg :x='owlentitysvg.startx' :y='owlentitysvg.starty' :height='owlentitysvg.height' :width='owlentitysvg.width'>
            <OwlEntityNode :owlentitysvg='owlentitysvg' @show-entity='showEntity' />
        </svg>
        <v-dialog v-model='show'>
            <v-card>
                <span> {{ selectedOwlEntity.label }}</span>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
import OwlEntityNode from './OwlEntityNode.vue';

export class OwlEntitySVG {
    /** @type {Object} */
    owlentity;
    /** @type {Object[]} */
    children;
    /** @type {String} */
    type;
    /** @type {String} */
    color;

    /** @type {Boolean} */
    visible;
    /** @type {Number} */
    height;
    /** @type {Number} */
    width;
    /** @type {Number} */
    r = 10;
    /** @type {Number} */
    stroke = 1;
    /** @type {Number} */
    fontSize = 18;
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

    /** @param {Object} owlentity
     *  @returns {OwlEntitySVG}
     */
    static fromOwlEntity(owlentity, type) {
        let owlentitysvg = new OwlEntitySVG();
        owlentitysvg.owlentity = owlentity;
        owlentitysvg.type = type;
        if (type === 'class') {
            owlentitysvg.color = 'yellow';
        } else if (type === 'objprop') {
            owlentitysvg.color = 'lightblue';
        } else if (type === 'dataprop') {
            owlentitysvg.color = 'lightgreen';
        }
        owlentitysvg.children = [];
        for (let child of owlentity.children) {
            owlentitysvg.children.push(OwlEntitySVG.fromOwlEntity(child, type));
        }
        return owlentitysvg;
    }

    /** @param {Number} x
     *  @returns {Number}
     */
    calcPositions(x, y) {
        this.startx = x;
        this.starty = y;
        if (this.type === 'class') {
            this.cx = this.startx + 2*this.r;
        } else {
            this.cx = this.startx + this.r;
        }
        this.cy = this.starty + this.r;
        if (this.type === 'class') {
            this.textx = this.cx + this.r + 2;
        } else {
            this.textx = this.cx + 2*this.r + 2;
        }
        this.texty = this.cy + this.r/2;
        if (this.type === 'class') {
            this.linex1 = this.cx;
        } else {
            this.linex1 = this.cx + this.r;
        }
        this.liney1 = this.starty + 2*this.r;
        if (this.type === 'class') {
            this.linex2 = this.cx;
        } else {
            this.linex2 = this.cx + this.r;
        }
        this.liney2 = this.liney1;
        this.width = this.cx + this.r + this.fontSize/2 * this.owlentity.label.length + 2;
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
    name: 'OwlEntityComp',
    components: {
        OwlEntityNode
    },
    props: {
        owlentity: {
            type: Object,
            default: null
        },
        type: {
            type: String,
            default: 'class'
        },
    },
    data() {
        return {
            owlentitysvg: null,
            show: false,
            selectedOwlEntity: {}
        };
    },
    methods: {
        showEntity(owlentity) {
            this.selectedOwlEntity = owlentity;
            this.show = true;
        },
    },
    created() {
        this.owlentitysvg = OwlEntitySVG.fromOwlEntity(this.owlentity, this.type);
        this.owlentitysvg.calcPositions(0, 0);

        console.log(this.owlentitysvg.linex2);
        console.log(this.owlentitysvg.liney2);
    }
};

</script>