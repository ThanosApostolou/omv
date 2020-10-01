import { Rule } from "./Rule.js";

export class Mapping {
    /** @type {String} */ owl1iri;
    /** @type {String} */ owl2iri;
    /** @type {Rule[]} */ classrules;
    /** @type {Rule[]} */ proprules;
    /** @type {Rule[]} */ classRulesLeft;
    /** @type {Rule[]} */ propRulesLeft;

    /** @param {Object} mappingobject
     * @returns {Mapping}
    */
    static fromObject (mappingobject) {
        let mapping = new Mapping();
        mapping.owl1iri = mappingobject.owl1iri;
        mapping.owl2iri = mappingobject.owl2iri;
        mapping.classrules = Rule.listFromObject(mappingobject.classrules);
        mapping.proprules = Rule.listFromObject(mappingobject.proprules);
        return mapping;
    }

    findOrderedRules(owl1, owl2) {
        let classRulesLeft = [];
        Rule.findRule(this.classrules, 1, "class", owl1.owlclasses, classRulesLeft);
        this.classRulesLeft = classRulesLeft;
        let propRulesLeft = [];
        Rule.findRule(this.proprules, 1, "class", owl1.owlclasses, propRulesLeft);
        Rule.findRule(this.proprules, 1, "objprop", owl1.owlobjprops, propRulesLeft);
        Rule.findRule(this.proprules, 1, "dataprop", owl1.owldataprops, propRulesLeft);
        this.propRulesLeft = propRulesLeft;

        let classRulesRight = [];
        Rule.findRule(this.classrules, 2, "class", owl2.owlclasses, classRulesRight);
        this.classRulesRight = classRulesRight;
        let propRulesRight = [];
        Rule.findRule(this.proprules, 2, "class", owl2.owlclasses, propRulesRight);
        Rule.findRule(this.proprules, 2, "objprop", owl2.owlobjprops, propRulesRight);
        Rule.findRule(this.proprules, 2, "dataprop", owl2.owldataprops, propRulesRight);
        this.propRulesRight = propRulesRight;

    }
}