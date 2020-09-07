import { Rule } from "./Rule.js";

export class Mapping {
    /** @type {String} */ owl1iri;
    /** @type {String} */ owl2iri;
    /** @type {Rule[]} */ equivalent;
    /** @type {Rule[]} */ linkedwith;
    /** @type {Rule[]} */ classRulesLeft;
    /** @type {Rule[]} */ propRulesLeft;
    /** @type {Rule[]} */ other;

    /** @param {Object} mappingobject
     * @returns {Mapping}
    */
    static fromObject (mappingobject) {
        let mapping = new Mapping();
        mapping.owl1iri = mappingobject.owl1iri;
        mapping.owl2iri = mappingobject.owl2iri;
        mapping.equivalent = Rule.listFromObject(mappingobject.equivalent);
        mapping.linkedwith = Rule.listFromObject(mappingobject.linkedwith);
        mapping.other = Rule.listFromObject(mappingobject.other);
        return mapping;
    }

    findOrderedRules(owl1, owl2) {
        let classRulesLeft = [];
        Rule.findRule(this.equivalent, 1, "class", owl1.owlclasses, classRulesLeft);
        this.classRulesLeft = classRulesLeft;
        let propRulesLeft = [];
        Rule.findRule(this.linkedwith, 1, "class", owl1.owlclasses, propRulesLeft);
        Rule.findRule(this.linkedwith, 1, "objprop", owl1.owlobjprops, propRulesLeft);
        Rule.findRule(this.linkedwith, 1, "dataprop", owl1.owldataprops, propRulesLeft);
        this.propRulesLeft = propRulesLeft;

        let classRulesRight = [];
        Rule.findRule(this.equivalent, 2, "class", owl2.owlclasses, classRulesRight);
        this.classRulesRight = classRulesRight;
        let propRulesRight = [];
        Rule.findRule(this.linkedwith, 2, "class", owl2.owlclasses, propRulesRight);
        Rule.findRule(this.linkedwith, 2, "objprop", owl2.owlobjprops, propRulesRight);
        Rule.findRule(this.linkedwith, 2, "dataprop", owl2.owldataprops, propRulesRight);
        this.propRulesRight = propRulesRight;

    }
}