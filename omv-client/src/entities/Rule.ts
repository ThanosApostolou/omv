import { Mapping } from "./Mapping";
import { OwlEntity } from "./OwlEntity";
import { Transformation } from "./Transformation";

export class Rule {
    mapping: Mapping = null;
    label: string = "";
    relation: string = "";
    direction: string = "";
    comments: string = "";
    similarity: string = "";
    simcomments: string = "";
    directTransformation: Transformation = null;
    inverseTransformation: Transformation = null;
    entity1: any = null;
    entity2: any = null;

    /** @param {any} ruleobject
     * @returns {Rule}
     */
    static fromObject(ruleobject: any, mapping: Mapping): Rule {
        const rule = new Rule();
        rule.mapping = mapping;
        rule.label = ruleobject.label === "" ? null : ruleobject.label;
        rule.relation = ruleobject.relation === "" ? null : ruleobject.relation;
        rule.direction = ruleobject.direction === "" ? null : ruleobject.direction;
        rule.comments = ruleobject.comments === "" ? null : ruleobject.comments;
        rule.similarity = ruleobject.similarity === "" ? null : ruleobject.similarity;
        rule.simcomments = ruleobject.simcomments === "" ? null : ruleobject.simcomments;
        rule.directTransformation = Transformation.fromObject(ruleobject.directTransformation, "DirectTransformation", rule);
        rule.inverseTransformation = Transformation.fromObject(ruleobject.inverseTransformation, "InverseTransformation", rule);
        rule.entity1 = ruleobject.entity1 === "" ? null : ruleobject.entity1;
        rule.entity2 = ruleobject.entity2 === "" ? null : ruleobject.entity2;
        return rule;
    }

    /** @param {any[]} rulesobject
     * @return {Rule[]}
    */
    static listFromObject(rulesobject: any[], mapping: Mapping): Rule[] {
        const rules = [];
        for (const ruleobject of rulesobject) {
            rules.push(Rule.fromObject(ruleobject, mapping));
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