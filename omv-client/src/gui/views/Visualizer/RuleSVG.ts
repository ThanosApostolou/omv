// eslint-disable-next-line no-unused-vars
import { Rule } from "../../../entities/Rule";

export class RuleSVG {
    /** @type {Rule} */ rule;

    /** @type {Object} */ entity1 = {
        classessvg: [],
        objpropssvg: [],
        datapropssvg: []
    };
    /** @type {Object} */ entity2 = {
        classessvg: [],
        objpropssvg: [],
        datapropssvg: []
    };

    /** @type {Number} */ r = 8;
    /** @type {Number} */ width = 40;
    /** @type {Number} */ stroke = 1;
    /** @type {Number} */ fill = "lightgrey";
    /** @type {Number} */ fontSize = 10;
    /** @type {Number} */ startx;
    /** @type {Number} */ starty;
    /** @type {Number} */ cx;
    /** @type {Number} */ cy;
    /** @type {Number} */ endx;
    /** @type {Number} */ endy;

    /** @type {Object[]} */ lines = [];


    /** @param {Rule} rule
     * @returns {RuleSVG}
     */
    static fromRule(rule) {
        const rulesvg = new RuleSVG();
        rulesvg.rule = rule;
        return rulesvg;
    }

    /** @param {Rule[]} rule
     * @returns {RuleSVG[]}
     */
    static listFromRules(rules) {
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
    static listInit(rulessvg, x, y) {
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


    findEntities(owl1classessvg, owl1objpropssvg, owl1datapropssvg, owl2classessvg, owl2objpropssvg, owl2datapropssvg) {
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

    static listFindEntities(rules, owl1classessvg, owl1objpropssvg, owl1datapropssvg, owl2classessvg, owl2objpropssvg, owl2datapropssvg) {
        for (const rule of rules) {
            rule.findEntities(owl1classessvg, owl1objpropssvg, owl1datapropssvg, owl2classessvg, owl2objpropssvg, owl2datapropssvg);
        }

    }

}