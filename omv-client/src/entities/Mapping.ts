import { OwlInfo } from "./OwlInfo";
import { Rule } from "./Rule";
import { Visualization } from "./Visualization";

export class Mapping {
    visualization: Visualization = null;
    owl1iri: string = "";
    owl2iri: string = "";
    classrules: Rule[] = [];
    proprules: Rule[] = [];
    error: string = null;
    classRulesLeft: Rule[] = [];
    propRulesLeft: Rule[] = [];
    classRulesRight: Rule[] = [];
    propRulesRight: Rule[] = [];

    /** @param {any} mappingobject
     * @returns {Mapping}
    */
    static fromObject (mappingobject: any, visualization: Visualization): Mapping {
        const mapping = new Mapping();
        mapping.visualization = visualization;
        mapping.owl1iri = mappingobject.owl1iri;
        mapping.owl2iri = mappingobject.owl2iri;
        mapping.classrules = Rule.listFromObject(mappingobject.classrules, mapping);
        mapping.proprules = Rule.listFromObject(mappingobject.proprules, mapping);
        mapping.error = mappingobject.error;
        return mapping;
    }

    findOrderedRules(owl1: OwlInfo, owl2: OwlInfo): void {
        const classRulesLeft: Rule[] = [];
        Rule.findRule(this.classrules, 1, "class", owl1.owlclasses, classRulesLeft, true);
        this.classRulesLeft = classRulesLeft;
        const propRulesLeft: Rule[] = [];
        Rule.findRule(this.proprules, 1, "class", owl1.owlclasses, propRulesLeft, false);
        Rule.findRule(this.proprules, 1, "objprop", owl1.owlobjprops, propRulesLeft, false);
        Rule.findRule(this.proprules, 1, "dataprop", owl1.owldataprops, propRulesLeft, false);
        this.propRulesLeft = propRulesLeft;

        const classRulesRight: Rule[] = [];
        Rule.findRule(this.classrules, 2, "class", owl2.owlclasses, classRulesRight, true);
        this.classRulesRight = classRulesRight;
        const propRulesRight: Rule[] = [];
        Rule.findRule(this.proprules, 2, "class", owl2.owlclasses, propRulesRight, false);
        Rule.findRule(this.proprules, 2, "objprop", owl2.owlobjprops, propRulesRight, false);
        Rule.findRule(this.proprules, 2, "dataprop", owl2.owldataprops, propRulesRight, false);
        this.propRulesRight = propRulesRight;

    }
}