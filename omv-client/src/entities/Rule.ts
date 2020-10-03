import { OwlEntity } from "./OwlEntity";

export class Rule {
    /** @type {String} */ label = "";
    /** @type {String} */ relation = "";
    /** @type {String} */ direction = "";
    /** @type {String} */ comments = "";
    /** @type {String} */ similarity = "";
    /** @type {String} */ simcomments = "";
    /** @type {any} */ directTransformation: any = null;
    /** @type {any} */ inverseTransformation: any = null;
    /** @type {any} */ entity1: any = null;
    /** @type {any} */ entity2: any = null;

    /** @param {any} ruleobject
     * @returns {Rule}
     */
    static fromObject(ruleobject: any): Rule {
        const rule = new Rule();
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
        const rules = [];
        for (const ruleobject of rulesobject) {
            rules.push(Rule.fromObject(ruleobject));
        }
        return rules;
    }

    /** @param {Rule[]} rules
     * @param {Rule} rule
     * @return {Boolean}
    */
    static listExistsRule(rules: Rule[], rule: Rule): boolean {
        for (const rl of rules) {
            if (rl.label == rule.label) {
                return true;
            }
        }
        return false;
    }

    /** Find rules
    */
    static findRule(rules: Rule[], owlNumber: number, entityType: string, owlentity: OwlEntity, orderedRules: Rule[]): void {
        for (const rule of rules) {
            const ruleowl = (owlNumber == 1) ? rule.entity1 : rule.entity2;
            let ruleentities = [];
            if (entityType == "class") {
                ruleentities = ruleowl.classes;
            } else if (entityType == "objprop") {
                ruleentities = ruleowl.objectprops;
            } else {
                ruleentities = ruleowl.dataprops;
            }
            for (const ruleentity of ruleentities) {
                if (owlentity.iri == ruleentity.iri) {
                    if (!Rule.listExistsRule(orderedRules, rule)) {
                        orderedRules.push(rule);
                    }
                }
            }
        }
        for (const child of owlentity.children) {
            Rule.findRule(rules, owlNumber, entityType, child, orderedRules);
        }
    }
}