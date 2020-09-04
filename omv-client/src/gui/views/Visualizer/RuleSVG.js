// eslint-disable-next-line no-unused-vars
import { Rule } from "../../../entities/Rule.js";

export class RuleSVG {
    /** @type {Rule} */ rule;
    /** @type {Number} */ r = 10;
    /** @type {Number} */ stroke = 1;
    /** @type {Number} */ fill = "red";
    /** @type {Number} */ startx;
    /** @type {Number} */ starty;
    /** @type {Number} */ endcx;
    /** @type {Number} */ endcy;

    /** @param {RuleSVG[]} rulessvg
     * @param {Number} x
     * @param {Number} y
     */
    static listInit(rulessvg, x, y) {
        let nexty = y;
        for (let rulesvg of rulessvg) {
            rulesvg.startx = x;
            rulesvg.starty = nexty;
            rulesvg.endcx = x+2*rulesvg.r;
            rulesvg.endcy = y+rulesvg.r;
            nexty += 3*rulesvg.r;
        }
        return nexty;
    }

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

}