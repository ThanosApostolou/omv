// eslint-disable-next-line no-unused-vars
import { OwlEntity } from "../../../entities/OwlEntity.js";

class OwlEntitySVG {
    /** @type {OwlEntity} */ owlentity;
    /** @type {OwlEntitySVG} */ parent;
    /** @type {Object[]} */ children;
    /** @type {String} */ entityType;
    /** @type {String} */ color;

    /** @type {Boolean} */ visible = false;
    /** @type {Number} */ height;
    /** @type {Number} */ width;
    /** @type {Number} */ r = 10;
    /** @type {Number} */ stroke = 1;
    /** @type {Number} */ fontSize = 18;
    /** @type {Number} */ startx;
    /** @type {Number} */ starty;
    /** @type {Number} */ cx;
    /** @type {Number} */ cy;
    /** @type {Number} */ textx;
    /** @type {Number} */ texty;
    /** @type {Number} */ textLength;
    /** @type {Number} */ line1_x2;
    /** @type {Number} */ linex1;
    /** @type {Number} */ liney1;
    /** @type {Number} */ linex2;
    /** @type {Number} */ liney2;

    /** @param {Object} owlentity
     *  @returns {OwlEntitySVG}
     */
    static fromOwlEntity(owlentity, entityType, parent) {
        let owlentitysvg = new OwlEntitySVG();
        owlentitysvg.owlentity = owlentity;
        owlentitysvg.parent = parent;
        owlentitysvg.entityType = entityType;
        owlentitysvg.textLength = owlentitysvg.fontSize/1.9 * owlentitysvg.owlentity.label.length;
        if (entityType === "class") {
            owlentitysvg.color = "yellow";
        } else if (entityType === "objprop") {
            owlentitysvg.color = "lightblue";
        } else if (entityType === "dataprop") {
            owlentitysvg.color = "lightgreen";
        }
        owlentitysvg.children = [];
        for (let child of owlentity.children) {
            owlentitysvg.children.push(OwlEntitySVG.fromOwlEntity(child, entityType, owlentitysvg));
        }
        return owlentitysvg;
    }

    init(startx, starty, visibilityType, reverse) {
        this.calcVisibility(visibilityType);
        this.calcWidth(1);
        if (!reverse) {
            this.calcPositions(startx, starty);
        } else {
            this.calcPositionsReverse(startx + this.width, starty);
        }
    }

    setVisible() {
        this.visible = true;
        if (this.parent != null) {
            this.parent.setVisible();
        }
    }
    calcVisibility(visibilityType) {
        let shallSetVisible = false;
        if (visibilityType == "All") {
            shallSetVisible = true;
        } else if (visibilityType == "AllRules") {
            shallSetVisible = this.owlentity.hasEquivalentRule || this.owlentity.hasLinkedWithRule || this.owlentity.hasOtherRule;
        } else if (visibilityType == "EquivalentRules") {
            shallSetVisible = this.owlentity.hasEquivalentRule;
        } else if (visibilityType == "LinkedWithRules") {
            shallSetVisible = this.owlentity.hasLinkedWithRule;
        } else if (visibilityType == "OtherRules") {
            shallSetVisible = this.owlentity.hasOtherRule;
        }
        if (shallSetVisible) {
            this.setVisible();
        }
        for (let child of this.children) {
            child.calcVisibility(visibilityType);
        }
    }

    calcWidth(depth) {
        this.width = 0;
        if (this.visible) {
            this.width = depth*3*this.r + this.textLength;
            for (let child of this.children) {
                let newwidth=0;
                newwidth = child.calcWidth(depth+1);
                if (newwidth > this.width) {
                    this.width = newwidth;
                }
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
        if (this.entityType === "class") {
            this.cx = this.startx + 2*this.r;
        } else {
            this.cx = this.startx + this.r;
        }
        this.cy = this.starty + this.r;
        if (this.entityType === "class") {
            this.textx = this.cx + this.r + 2;
        } else {
            this.textx = this.cx + 2*this.r + 2;
        }
        this.texty = this.cy + this.r/2;
        this.line1_x2 = this.startx + this.r ;
        if (this.entityType === "class") {
            this.linex1 = this.cx;
        } else {
            this.linex1 = this.cx + this.r;
        }
        this.liney1 = this.starty + 2*this.r;
        if (this.entityType === "class") {
            this.linex2 = this.cx;
        } else {
            this.linex2 = this.cx + this.r;
        }
        this.liney2 = this.liney1;
        let nextx = this.startx + 2*this.r;
        let nexty = this.starty + 2*this.r;
        for (let child of this.children) {
            if (child.visible) {
                let childcy=0;
                [nexty, childcy] = child.calcPositions(nextx, nexty);
                this.liney2 = childcy;
            }
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
        if (this.entityType === "class") {
            this.cx = this.startx - 2*this.r;
        } else {
            this.cx = this.startx - this.r;
        }
        this.cy = this.starty + this.r;
        if (this.entityType === "class") {
            this.textx = this.cx - this.r - 2;
        } else {
            this.textx = this.cx - 2*this.r - 2;
        }
        this.texty = this.cy + this.r/2;
        this.line1_x2 = this.startx - this.r ;
        if (this.entityType === "class") {
            this.linex1 = this.cx;
        } else {
            this.linex1 = this.cx - this.r;
        }
        this.liney1 = this.starty + 2*this.r;
        if (this.entityType === "class") {
            this.linex2 = this.cx;
        } else {
            this.linex2 = this.cx - this.r;
        }
        this.liney2 = this.liney1;
        let nextx = this.startx - 2*this.r;
        let nexty = this.starty + 2*this.r;
        for (let child of this.children) {
            if (child.visible) {
                let childcy=0;
                [nexty, childcy] = child.calcPositionsReverse(nextx, nexty);
                this.liney2 = childcy;
            }
        }
        this.height = nexty - this.starty;
        return [nexty, this.cy];
    }

}

export default OwlEntitySVG;