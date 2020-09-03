<template>
    <svg>
        <OwlEntityNodeSVG :owlentitysvg="owlentitysvg" @show-entity="showEntity" />
    </svg>
</template>

<script>
import OwlEntityNodeSVG from "./OwlEntityNodeSVG.vue";

export class OwlEntitySVG {
    /** @type {Object} */
    owlentity;
    /** @type {Object[]} */
    children;
    /** @type {String} */
    type;
    /** @type {Boolean} */
    reverse;
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
    textLength;
    /** @type {Number} */
    line1_x2;
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
    static fromOwlEntity(owlentity, type, reverse) {
        let owlentitysvg = new OwlEntitySVG();
        owlentitysvg.owlentity = owlentity;
        owlentitysvg.type = type;
        owlentitysvg.reverse = reverse;
        owlentitysvg.textLength = owlentitysvg.fontSize/1.9 * owlentitysvg.owlentity.label.length;
        if (type === "class") {
            owlentitysvg.color = "yellow";
        } else if (type === "objprop") {
            owlentitysvg.color = "lightblue";
        } else if (type === "dataprop") {
            owlentitysvg.color = "lightgreen";
        }
        owlentitysvg.children = [];
        for (let child of owlentity.children) {
            owlentitysvg.children.push(OwlEntitySVG.fromOwlEntity(child, type));
        }
        return owlentitysvg;
    }

    calcWidth(depth) {
        this.width = depth*3*this.r + this.textLength;
        for (let child of this.children) {
            let newwidth=0;
            newwidth = child.calcWidth(depth+1);
            if (newwidth > this.width) {
                this.width = newwidth;
            }
        }
        return this.width;
    }

    /** @param {Number} x
     *  @returns {Number}
     */
    calcPositions(x, y) {
        this.textAnchor = "start";
        this.startx = x;
        this.starty = y;
        if (this.type === "class") {
            this.cx = this.startx + 2*this.r;
        } else {
            this.cx = this.startx + this.r;
        }
        this.cy = this.starty + this.r;
        if (this.type === "class") {
            this.textx = this.cx + this.r + 2;
        } else {
            this.textx = this.cx + 2*this.r + 2;
        }
        this.texty = this.cy + this.r/2;
        this.line1_x2 = this.startx + this.r ;
        if (this.type === "class") {
            this.linex1 = this.cx;
        } else {
            this.linex1 = this.cx + this.r;
        }
        this.liney1 = this.starty + 2*this.r;
        if (this.type === "class") {
            this.linex2 = this.cx;
        } else {
            this.linex2 = this.cx + this.r;
        }
        this.liney2 = this.liney1;
        let nextx = this.startx + 2*this.r;
        let nexty = this.starty + 2*this.r;
        for (let child of this.children) {
            let childcy=0;
            [nexty, childcy] = child.calcPositions(nextx, nexty);
            this.liney2 = childcy;
        }
        this.height = nexty - this.starty;
        return [nexty, this.cy];
    }

    /** @param {Number} x
     *  @param {Number} y
     *  @returns {Number}
     */
    calcPositionsReverse(x, y) {
        this.textAnchor = "end";
        this.startx = x;
        this.starty = y;
        if (this.type === "class") {
            this.cx = this.startx - 2*this.r;
        } else {
            this.cx = this.startx - this.r;
        }
        this.cy = this.starty + this.r;
        if (this.type === "class") {
            this.textx = this.cx - this.r - 2;
        } else {
            this.textx = this.cx - 2*this.r - 2;
        }
        this.texty = this.cy + this.r/2;
        this.line1_x2 = this.startx - this.r ;
        if (this.type === "class") {
            this.linex1 = this.cx;
        } else {
            this.linex1 = this.cx - this.r;
        }
        this.liney1 = this.starty + 2*this.r;
        if (this.type === "class") {
            this.linex2 = this.cx;
        } else {
            this.linex2 = this.cx - this.r;
        }
        this.liney2 = this.liney1;
        let nextx = this.startx - 2*this.r;
        let nexty = this.starty + 2*this.r;
        for (let child of this.children) {
            let childcy=0;
            [nexty, childcy] = child.calcPositionsReverse(nextx, nexty);
            this.liney2 = childcy;
        }
        this.height = nexty - this.starty;
        return [nexty, this.cy];
    }

}

export default {
    name: "OwlEntityTreeSVG",
    components: {
        OwlEntityNodeSVG
    },
    props: {
        owlentity: {
            type: Object,
            default: null
        },
        type: {
            type: String,
            default: "class"
        },
        reverse: {
            type: Boolean,
            default: false
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
            this.$emit("show-entity", owlentity);
        },
    },
    created() {
        this.owlentitysvg = OwlEntitySVG.fromOwlEntity(this.owlentity, this.type, this.reverse);
        this.owlentitysvg.calcWidth(1);
        if (!this.reverse) {
            this.owlentitysvg.calcPositions(0, 0);
        } else {
            this.owlentitysvg.calcPositionsReverse(this.owlentitysvg.width, 0);
        }
    }
};

</script>