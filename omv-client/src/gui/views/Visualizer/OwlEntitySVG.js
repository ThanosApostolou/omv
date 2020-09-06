// eslint-disable-next-line no-unused-vars
import { OwlEntity } from "../../../entities/OwlEntity.js";

export class OwlEntitySVG {
    /** @type {OwlEntity} */ owlentity;
    /** @type {OwlEntitySVG} */ parent;
    /** @type {Object[]} */ children;
    /** @type {String} */ entityType;
    /** @type {String} */ color;

    /** @type {Boolean} */ visible = false;
    /** @type {Boolean} */ hasRule = false;
    /** @type {Number} */ height;
    /** @type {Number} */ width;
    /** @type {Number} */ r = 8;
    /** @type {Number} */ stroke = 1;
    /** @type {Number} */ fontSize = 12;
    /** @type {Number} */ startx;
    /** @type {Number} */ starty;
    /** @type {Number} */ endx;
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
        owlentitysvg.textLength = owlentitysvg.fontSize/1.7 * owlentitysvg.owlentity.label.length;
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

    /** @param {Rule[]} rules
     * @param {String} ruleEntityType
     */
    calcVisibility(rules, ruleEntityType) {
        if (rules == null) {
            this.setAllVisible();
        } else {
            let entity = null;
            for (let rule of rules) {
                let ruleentities = [];
                if (ruleEntityType == "entity1") {
                    entity = rule.entity1;
                } else if (ruleEntityType == "entity2") {
                    entity = rule.entity2;
                }
                if (this.entityType == "class") {
                    ruleentities = entity.classes;
                } else if (this.entityType == "objprop") {
                    ruleentities = entity.objectprops;
                } else if (this.entityType == "dataprop") {
                    ruleentities = entity.dataprops;
                }
                for (let ruleentity of ruleentities) {
                    let foundentity = this.findByIri(ruleentity.iri);
                    foundentity.hasRule = true;
                    foundentity.setVisible();
                }
            }
        }
    }

    setVisible() {
        this.visible = true;
        if (this.parent != null) {
            this.parent.setVisible();
        }
    }
    setAllVisible() {
        this.visible = true;
        for (let child of this.children) {
            child.setAllVisible();
        }
    }

    /** @param {Number} depth */
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
        this.endx = this.startx + 3*this.r + this.textLength + this.r/2;
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
        this.endx = this.startx - 3*this.r - this.textLength - this.r/2;
        if (this.entityType === "class") {
            this.cx = this.startx - 2*this.r;
        } else {
            this.cx = this.startx - 3*this.r;
        }
        this.cy = this.starty + this.r;
        if (this.entityType === "class") {
            this.textx = this.cx - this.r - 2;
        } else {
            this.textx = this.cx - 2;
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

    /** @param {String}
     * @returns {OwlEntitySVG}
    */
    findByIri(iri) {
        if (this.owlentity.iri == iri) {
            return this;
        } else {
            for (let child of this.children) {
                let foundentitysvg = child.findByIri(iri);
                if (foundentitysvg != null) {
                    return foundentitysvg;
                }
            }
        }
        return null;
    }

}