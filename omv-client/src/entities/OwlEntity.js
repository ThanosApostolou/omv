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
    /** @type {Boolean} */
    hasEquivalentRule;
    /** @type {Boolean} */
    hasLinkedWithRule;
    /** @type {Boolean} */
    hasOtherRule;

    /** @param {object} owlentityobject
     *  @returns {OwlEntity}
     */
    static fromObject(owlentityobject) {
        let owlentity = new OwlEntity();
        owlentity.iri = owlentityobject.iri;
        owlentity.name = owlentityobject.name;
        owlentity.label = owlentityobject.label;
        owlentity.hasEquivalentRule = owlentityobject.hasEquivalentRule;
        owlentity.hasLinkedWithRule = owlentityobject.hasLinkedWithRule;
        owlentity.hasOtherRule = owlentityobject.hasOtherRule;
        owlentity.annotations = Annotation.listFromObject(owlentityobject.annotations);
        owlentity.children = [];
        for (let child of owlentityobject.children) {
            owlentity.children.push(OwlEntity.fromObject(child));
        }
        return owlentity;
    }
}

export default OwlEntity;