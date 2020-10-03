import { OwlEntity } from "./OwlEntity";

export class Rule {
    /** @type {String} */ label: string = "";
    /** @type {String} */ relation: string = "";
    /** @type {String} */ direction: string = "";
    /** @type {String} */ comments: string = "";
    /** @type {String} */ similarity: string = "";
    /** @type {String} */ simcomments: string = "";
    /** @type {any} */ directTransformation: any = null;
    /** @type {any} */ inverseTransformation: any = null;
    /** @type {any} */ entity1: any = null;
    /** @type {any} */ entity2: any = null;

    /** @param {any} ruleobject
     * @returns {Rule}
     */
    static fromObject(ruleobject: any): Rule {
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

    /** @param {any[]} rulesobject
     * @return {Rule[]}
    */
    static listFromObject(rulesobject: any[]): Rule[] {
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
    static listExistsRule(rules: Rule[], rule: Rule): boolean {
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
    static findRule(rules: Rule[], owlNumber, entityType, owlentity, orderedRules) {
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
    static listOrdered(rules: Rule[], owlNumber, owlclasses: OwlEntity, objprops, dataprops): Rule[] {
        let rulesclone = Object.assign({}, rules);
        let orderedRules = [];
        Rule.findRule(rules, owlNumber, "class", owlclasses, orderedRules);
        return orderedRules;
    }
}