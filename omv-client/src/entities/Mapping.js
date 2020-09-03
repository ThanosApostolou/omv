import Rule from "./Rule.js";

class Mapping {
    /** @type {String} */
    owl1iri;
    /** @type {String} */
    owl2iri;
    /** @type {Rule[]} */
    equivalent;
    /** @type {Rule[]} */
    linkedwith;
    /** @type {Rule[]} */
    other;

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
}

export default Mapping;