class Mappings {
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

    static fromObject (mappingsobject) {
        let mappings = new Mappings();
        mappings.owl1iri = mappingsobject.owl1iri;
        mappings.owl2iri = mappingsobject.owl2iri;
        mappings.equivalent = mappingsobject.equivalent;
        mappings.linkedwith = mappingsobject.linkedwith;
        mappings.other = mappingsobject.other;
        return mappings;
    }
}

export default Mappings;