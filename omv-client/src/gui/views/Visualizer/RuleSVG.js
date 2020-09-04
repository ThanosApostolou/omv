// eslint-disable-next-line no-unused-vars
import { Rule } from "../../../entities/Rule.js";

class RuleSVG {
    /** @type {Rule} */ rule;
    /** @type {Number} */ r = 10;
    /** @type {Number} */ stroke = 1;
    /** @type {Number} */ startx;
    /** @type {Number} */ starty;

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

export default RuleSVG;