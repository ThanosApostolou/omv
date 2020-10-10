import { OwlEntity } from "../../../entities/OwlEntity";
import { Rule } from "../../../entities/Rule";
import { RuleSVG } from "./RuleSVG";

export class OwlEntitySVG {
    owlentity: OwlEntity;
    parent: OwlEntitySVG;
    children: OwlEntitySVG[];
    entityType: string;
    color: string;

    visible: boolean = false;
    hasRule: boolean = false;
    rulesvgs: RuleSVG[] = [];
    isSelected: boolean = false;
    isActive: boolean = false;

    height: number;
    width: number;
    r: number = 8;
    stroke: number = 1;
    fontSize: number = 12;
    startx: number;
    starty: number;
    endx: number;
    cx: number;
    cy: number;
    textx: number;
    texty: number;
    textLength: number;
    textAnchor: string = "";
    line1_x2: number;
    linex1: number;
    liney1: number;
    linex2: number;
    liney2: number;

    /** @param {Object} owlentity
     *  @returns {OwlEntitySVG}
     */
    static fromOwlEntity(owlentity: any, entityType: string, parent: OwlEntitySVG): OwlEntitySVG {
        const owlentitysvg: OwlEntitySVG = new OwlEntitySVG();
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
        for (const child of owlentity.children) {
            owlentitysvg.children.push(OwlEntitySVG.fromOwlEntity(child, entityType, owlentitysvg));
        }
        return owlentitysvg;
    }

    /** @param {Rule[]} rules
     * @param {String} ruleEntityType
     */
    calcVisibility(rules: Rule[], ruleEntityType: string) {
        if (rules == null) {
            this.setAllVisible();
        } else {
            let entity = null;
            for (const rule of rules) {
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
                for (const ruleentity of ruleentities) {
                    const foundentity = this.findByIri(ruleentity.iri);
                    foundentity.hasRule = true;
                    foundentity.setVisible();
                }
            }
        }
        this.removeNonVisible();
    }

    setVisible() {
        this.visible = true;
        if (this.parent != null) {
            this.parent.setVisible();
        }
    }
    setAllVisible() {
        this.visible = true;
        for (const child of this.children) {
            child.setAllVisible();
        }
    }
    removeNonVisible() {
        if (this.visible) {
            for (let i=this.children.length-1; i>=0; i--) {
                this.children[i] = this.children[i].removeNonVisible();
            }
            for (let i=this.children.length-1; i>=0; i--) {
                if (this.children[i] == null) {
                    this.children.splice(i, 1);
                }
            }
            return this;
        } else {
            return null;
        }
    }

    /** @param {Number} depth */
    calcWidth(depth: number) {
        this.width = 0;
        if (this.visible) {
            this.width = depth*3*this.r + this.textLength + 2;
            for (const child of this.children) {
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
     *  @param {Number} y
     *  @returns {Number}
     */
    calcPositions(x: number, y: number): [number, number] {
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
        const nextx = this.startx + 2*this.r;
        let nexty = this.starty + 2*this.r;
        for (const child of this.children) {
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
    calcPositionsReverse(x: number, y: number): [number, number] {
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
        const nextx = this.startx - 2*this.r;
        let nexty = this.starty + 2*this.r;
        for (const child of this.children) {
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
    findByIri(iri: string): OwlEntitySVG {
        if (this.owlentity.iri == iri) {
            return this;
        } else {
            for (const child of this.children) {
                const foundentitysvg = child.findByIri(iri);
                if (foundentitysvg != null) {
                    return foundentitysvg;
                }
            }
        }
        return null;
    }

    setSelected() {
        this.isSelected = true;
        this.setActive();
    }
    setActive() {
        this.isActive = true;
        for (const rulesvg of this.rulesvgs) {
            rulesvg.isVisible = true;
        }
        for (const child of this.children) {
            child.setActive();
        }
    }
    unsetSelected() {
        this.isSelected = false;
        this.unsetActive();
    }
    unsetActive() {
        this.isActive = false;
        for (const rulesvg of this.rulesvgs) {
            rulesvg.isVisible = false;
        }
        for (const child of this.children) {
            child.unsetActive();
        }
    }

}