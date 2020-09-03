class Mapping {
    /** @type {String} */
    owl1iri;
    /** @type {String} */
    owl2iri;
    /** @type {Object[]} */
    equivalent;
    /** @type {Object[]} */
    linkedwith;
    /** @type {Object[]} */
    other;

    static fromObject (mappingobject) {
        let mapping = new Mapping();
        mapping.owl1iri = mappingobject.owl1iri;
        mapping.owl2iri = mappingobject.owl2iri;
        mapping.equivalent = mappingobject.equivalent;
        mapping.linkedwith = mappingobject.linkedwith;
        mapping.other = mappingobject.other;
        return mapping;
    }
}

export default Mapping;