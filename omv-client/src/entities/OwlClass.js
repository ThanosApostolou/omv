import Annotation from "./Annotation.js";

class OwlClass {
    /** @type {String} */
    iri;
    /** @type {String} */
    name;
    /** @type {String} */
    label;
    /** @type {Annotation[]} */
    annotations;
    /** @type {OwlClass[]} */
    children;

    /** @param {object} owlclassobject
     *  @returns {OwlClass}
     */
    static fromObject(owlclassobject) {
        let owlclass = new OwlClass();
        owlclass.iri = owlclassobject.iri;
        owlclass.name = owlclassobject.name;
        owlclass.label = owlclassobject.label;
        owlclass.annotations = Annotation.listFromObject(owlclassobject.annotations);
        owlclass.children = [];
        for (let child of owlclassobject.children) {
            owlclass.children.push(OwlClass.fromObject(child));
        }
        return owlclass;
    }
}

export default OwlClass;