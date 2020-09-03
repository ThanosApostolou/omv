import Annotation from "./Annotation.js";

class OwlDataProperty {
    /** @type {String} */
    iri;
    /** @type {String} */
    name;
    /** @type {String} */
    label;
    /** @type {Annotation[]} */
    annotations;
    /** @type {OwlObjectProperty[]} */
    children;

    /** @param {object} owldatapropobject
     *  @returns {OwlDataProperty}
     */
    static fromObject(owldatapropobject) {
        let owldataprop = new OwlDataProperty();
        owldataprop.iri = owldatapropobject.iri;
        owldataprop.name = owldatapropobject.name;
        owldataprop.label = owldatapropobject.label;
        owldataprop.annotations = Annotation.listFromObject(owldatapropobject.annotations);
        owldataprop.children = [];
        for (let child of owldatapropobject.children) {
            owldataprop.children.push(OwlDataProperty.fromObject(child));
        }
        return owldataprop;
    }
}

export default OwlDataProperty;