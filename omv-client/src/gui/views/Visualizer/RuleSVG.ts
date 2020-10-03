import { Rule } from "../../../entities/Rule";
import { OwlEntitySVG } from "./OwlEntitySVG";

export class RuleSVG {
    /** @type {Rule} */ rule: Rule;

    /** @type {any} */ entity1: any = {
        classessvg: [],
        objpropssvg: [],
        datapropssvg: []
    };
    /** @type {any} */ entity2: any = {
        classessvg: [],
        objpropssvg: [],
        datapropssvg: []
    };

    /** @type {Number} */ r: number = 8;
    /** @type {Number} */ width: number = 40;
    /** @type {Number} */ stroke: number = 1;
    /** @type {string} */ fill: string = "lightgrey";
    /** @type {Number} */ fontSize: number = 10;
    /** @type {Number} */ startx: number;
    /** @type {Number} */ starty: number;
    /** @type {Number} */ cx: number;
    /** @type {Number} */ cy: number;
    /** @type {Number} */ endx: number;
    /** @type {Number} */ endy: number;

    /** @type {Object[]} */ lines: object[] = [];


    /** @param {Rule} rule
     * @returns {RuleSVG}
     */
    static fromRule(rule: Rule): RuleSVG {
        const rulesvg = new RuleSVG();
        rulesvg.rule = rule;
        return rulesvg;
    }

    /** @param {Rule[]} rule
     * @returns {RuleSVG[]}
     */
    static listFromRules(rules: Rule[]): RuleSVG[] {
        const rulessvg = [];
        for (const rule of rules) {
            rulessvg.push(RuleSVG.fromRule(rule));
        }
        return rulessvg;
    }

    /** @param {RuleSVG[]} rulessvg
     * @param {Number} x
     * @param {Number} y
     */
    static listInit(rulessvg: RuleSVG[], x: number, y: number) {
        let nexty = y;
        for (const rulesvg of rulessvg) {
            rulesvg.startx = x;
            rulesvg.starty = nexty;
            rulesvg.cx = rulesvg.startx + rulesvg.width/2;
            rulesvg.cy = nexty+rulesvg.r;
            rulesvg.endx = x+rulesvg.width;
            rulesvg.endy = rulesvg.starty + 2*rulesvg.r;
            nexty += 3*rulesvg.r;
        }
        return nexty;
    }


    findEntities(owl1classessvg: OwlEntitySVG, owl1objpropssvg: OwlEntitySVG, owl1datapropssvg: OwlEntitySVG, owl2classessvg: OwlEntitySVG, owl2objpropssvg: OwlEntitySVG, owl2datapropssvg: OwlEntitySVG) {
        for (const entityobj of this.rule.entity1.classes) {
            const foundentity = owl1classessvg.findByIri(entityobj.iri);
            this.entity1.classessvg.push(foundentity);
            const line = {
                x1: this.startx,
                y1: this.cy,
                x2: foundentity.endx,
                y2: foundentity.cy
            };
            this.lines.push(line);
        }
        for (const entityobj of this.rule.entity1.objectprops) {
            const foundentity = owl1objpropssvg.findByIri(entityobj.iri);
            this.entity1.objpropssvg.push(foundentity);
            const line = {
                x1: this.startx,
                y1: this.cy,
                x2: foundentity.endx,
                y2: foundentity.cy
            };
            this.lines.push(line);
        }
        for (const entityobj of this.rule.entity1.dataprops) {
            const foundentity = owl1datapropssvg.findByIri(entityobj.iri);
            this.entity1.datapropssvg.push(foundentity);
            const line = {
                x1: this.startx,
                y1: this.cy,
                x2: foundentity.endx,
                y2: foundentity.cy
            };
            this.lines.push(line);
        }
        for (const entityobj of this.rule.entity2.classes) {
            const foundentity = owl2classessvg.findByIri(entityobj.iri);
            this.entity2.classessvg.push(foundentity);
            const line = {
                x1: this.endx,
                y1: this.cy,
                x2: foundentity.endx,
                y2: foundentity.cy
            };
            this.lines.push(line);
        }
        for (const entityobj of this.rule.entity2.objectprops) {
            const foundentity = owl2objpropssvg.findByIri(entityobj.iri);
            this.entity2.objpropssvg.push(foundentity);
            const line = {
                x1: this.endx,
                y1: this.cy,
                x2: foundentity.endx,
                y2: foundentity.cy
            };
            this.lines.push(line);
        }
        for (const entityobj of this.rule.entity2.dataprops) {
            const foundentity = owl2datapropssvg.findByIri(entityobj.iri);
            this.entity2.datapropssvg.push(foundentity);
            const line = {
                x1: this.endx,
                y1: this.cy,
                x2: foundentity.endx,
                y2: foundentity.cy
            };
            this.lines.push(line);
        }

    }

    static listFindEntities(rulesvgs: RuleSVG[], owl1classessvg: OwlEntitySVG, owl1objpropssvg: OwlEntitySVG, owl1datapropssvg: OwlEntitySVG, owl2classessvg: OwlEntitySVG, owl2objpropssvg: OwlEntitySVG, owl2datapropssvg: OwlEntitySVG) {
        for (const rulesvg of rulesvgs) {
            rulesvg.findEntities(owl1classessvg, owl1objpropssvg, owl1datapropssvg, owl2classessvg, owl2objpropssvg, owl2datapropssvg);
        }

    }

}