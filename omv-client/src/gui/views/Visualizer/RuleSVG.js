// eslint-disable-next-line no-unused-vars
import { Rule } from "../../../entities/Rule.js";

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
    /** @type {Number} */ startx;
    /** @type {Number} */ starty;
    /** @type {Number} */ cy;
    /** @type {Number} */ endx;

    /** @type {Object[]} */ lines = [];


    /** @param {Rule} rule
     * @returns {RuleSVG}
     */
    static fromRule(rule) {
        let rulesvg = new RuleSVG();
        rulesvg.rule = rule;
        return rulesvg;
    }

    /** @param {Rule[]} rule
     * @returns {RuleSVG[]}
     */
    static listFromRules(rules) {
        let rulessvg = [];
        for (let rule of rules) {
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
        for (let rulesvg of rulessvg) {
            rulesvg.startx = x;
            rulesvg.starty = nexty;
            rulesvg.cy = nexty+rulesvg.r;
            rulesvg.endx = x+rulesvg.width;
            nexty += 3*rulesvg.r;
        }
        return nexty;
    }


    findEntities(owl1classessvg, owl1objpropssvg, owl1datapropssvg, owl2classessvg, owl2objpropssvg, owl2datapropssvg) {
        for (let entityobj of this.rule.entity1.classes) {
            let foundentity = owl1classessvg.findByIri(entityobj.iri);
            this.entity1.classessvg.push(foundentity);
            let line = {
                x1: this.startx,
                y1: this.cy,
                x2: foundentity.endx,
                y2: foundentity.cy
            };
            this.lines.push(line);
        }
        for (let entityobj of this.rule.entity1.objectprops) {
            let foundentity = owl1objpropssvg.findByIri(entityobj.iri);
            this.entity1.objpropssvg.push(foundentity);
            let line = {
                x1: this.startx,
                y1: this.cy,
                x2: foundentity.endx,
                y2: foundentity.cy
            };
            this.lines.push(line);
        }
        for (let entityobj of this.rule.entity1.dataprops) {
            let foundentity = owl1datapropssvg.findByIri(entityobj.iri);
            this.entity1.datapropssvg.push(foundentity);
            let line = {
                x1: this.startx,
                y1: this.cy,
                x2: foundentity.endx,
                y2: foundentity.cy
            };
            this.lines.push(line);
        }
        for (let entityobj of this.rule.entity2.classes) {
            let foundentity = owl2classessvg.findByIri(entityobj.iri);
            this.entity2.classessvg.push(foundentity);
            let line = {
                x1: this.endx,
                y1: this.cy,
                x2: foundentity.endx,
                y2: foundentity.cy
            };
            this.lines.push(line);
        }
        for (let entityobj of this.rule.entity2.objectprops) {
            let foundentity = owl2objpropssvg.findByIri(entityobj.iri);
            this.entity2.objpropssvg.push(foundentity);
            let line = {
                x1: this.endx,
                y1: this.cy,
                x2: foundentity.endx,
                y2: foundentity.cy
            };
            this.lines.push(line);
        }
        for (let entityobj of this.rule.entity2.dataprops) {
            let foundentity = owl2datapropssvg.findByIri(entityobj.iri);
            this.entity2.datapropssvg.push(foundentity);
            let line = {
                x1: this.endx,
                y1: this.cy,
                x2: foundentity.endx,
                y2: foundentity.cy
            };
            this.lines.push(line);
        }

    }

    static listFindEntities(rules, owl1classessvg, owl1objpropssvg, owl1datapropssvg, owl2classessvg, owl2objpropssvg, owl2datapropssvg) {
        for (let rule of rules) {
            rule.findEntities(owl1classessvg, owl1objpropssvg, owl1datapropssvg, owl2classessvg, owl2objpropssvg, owl2datapropssvg);
        }

    }

}