import Annotation from './Annotation.js';

class OwlObjectProperty {
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

    /** @param {object} owlobjpropobject
     *  @returns {OwlObjectProperty}
     */
    static fromObject(owlobjpropobject) {
        let owlobjprop = new OwlObjectProperty();
        owlobjprop.iri = owlobjpropobject.iri;
        owlobjprop.name = owlobjpropobject.name;
        owlobjprop.label = owlobjpropobject.label;
        owlobjprop.annotations = Annotation.listFromObject(owlobjpropobject.annotations);
        owlobjprop.children = [];
        for (let child of owlobjpropobject.children) {
            owlobjprop.children.push(OwlObjectProperty.fromObject(child));
        }
        return owlobjprop;
    }
}

export default OwlObjectProperty;