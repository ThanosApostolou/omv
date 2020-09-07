import { OwlEntity } from "./OwlEntity.js";

export class Rule {
    /** @type {String} */ label = "";
    /** @type {String} */ relation = "";
    /** @type {String} */ direction = "";
    /** @type {String} */ comments = "";
    /** @type {String} */ similarity = "";
    /** @type {String} */ simcomments = "";
    /** @type {Object} */ directTransformation = null;
    /** @type {Object} */ inverseTransformation = null;
    /** @type {Object} */ entity1 = null;
    /** @type {Object} */ entity2 = null;

    /** @param {Object} ruleobject
     * @returns {Rule}
     */
    static fromObject(ruleobject) {
        let rule = new Rule();
        rule.label = ruleobject.label;
        rule.relation = ruleobject.relation;
        rule.direction = ruleobject.direction;
        rule.comments = ruleobject.comments;
        rule.similarity = ruleobject.similarity;
        rule.simcomments = ruleobject.simcomments;
        rule.directTransformation = ruleobject.directTransformation;
        rule.inverseTransformation = ruleobject.inverseTransformation;
        rule.entity1 = ruleobject.entity1;
        rule.entity2 = ruleobject.entity2;
        return rule;
    }

    /** @param {Object[]} rulesobject
     * @return {Rule[]}
    */
    static listFromObject(rulesobject) {
        let rules = [];
        for (let ruleobject of rulesobject) {
            rules.push(Rule.fromObject(ruleobject));
        }
        return rules;
    }

    /** @param {Rule[]} rules
     * @param {Rule} rule
     * @return {Boolean}
    */
    static listExistsRule(rules, rule) {
        for (let rl of rules) {
            if (rl.label == rule.label) {
                return true;
            }
        }
        return false;
    }

    /** @param {Rule[]} rules
     * @param {OwlEntity} owlclasses
     * @return {Rule[]}
    */
    static findRule(rules, owlNumber, entityType, owlentity, orderedRules) {
        for (let rule of rules) {
            let ruleowl = (owlNumber == 1) ? rule.entity1 : rule.entity2;
            let ruleentities = [];
            if (entityType == "class") {
                ruleentities = ruleowl.classes;
            } else if (entityType == "objprop") {
                ruleentities = ruleowl.objectprops;
            } else {
                ruleentities = ruleowl.dataprops;
            }
            for (let ruleentity of ruleentities) {
                if (owlentity.iri == ruleentity.iri) {
                    if (!Rule.listExistsRule(orderedRules, rule)) {
                        orderedRules.push(rule);
                    }
                }
            }
        }
        for (let child of owlentity.children) {
            Rule.findRule(rules, owlNumber, entityType, child, orderedRules);
        }
    }

    /** @param {Rule[]} rules
     * @param {OwlEntity} owlclasses
     * @return {Rule[]}
    */
    static listOrdered(rules, owlNumber, owlclasses, objprops, dataprops) {
        let rulesclone = Object.assign({}, rules);
        let orderedRules = [];
        Rule.findRule(rules, owlNumber, "class", owlclasses, orderedRules);
        return orderedRules;
    }
}