import Annotation from "./Annotation.js";

class OwlEntity {
    /** @type {String} */
    iri;
    /** @type {String} */
    name;
    /** @type {String} */
    label;
    /** @type {Annotation[]} */
    annotations;
    /** @type {OwlEntity[]} */
    children;

    /** @param {object} owlentityobject
     *  @returns {OwlEntity}
     */
    static fromObject(owlentityobject) {
        let owlentity = new OwlEntity();
        owlentity.iri = owlentityobject.iri;
        owlentity.name = owlentityobject.name;
        owlentity.label = owlentityobject.label;
        owlentity.annotations = Annotation.listFromObject(owlentityobject.annotations);
        owlentity.children = [];
        for (let child of owlentityobject.children) {
            owlentity.children.push(OwlEntity.fromObject(child));
        }
        return owlentity;
    }
}

export default OwlEntity;