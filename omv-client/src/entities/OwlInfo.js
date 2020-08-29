import Annotation from './Annotation.js';
import OwlClass from './OwlClass.js';
import OwlObjectProperty from './OwlObjectProperty.js';


class OwlInfo {
    /** @type {String} */
    iri;
    /** @type {String} */
    versionIri;
    /** @type {String} */
    label;
    /** @type {Annotation[]} */
    annotations;
    /** @type {OwlClass} */
    owlclasses;
    /** @type {OwlObjectProperty} */
    owlobjprops;

    /** @param {object} owlinfoobject
     *  @returns {OwlInfo}
     */
    static fromObject (owlinfoobject) {
        let owlinfo = new OwlInfo();
        owlinfo.label = owlinfoobject.label;
        owlinfo.iri = owlinfoobject.iri;
        owlinfo.versionIri = owlinfoobject.versionIri;
        owlinfo.label = owlinfoobject.label;
        owlinfo.annotations = Annotation.listFromObject(owlinfoobject.annotations);
        owlinfo.owlclasses = OwlClass.fromObject(owlinfoobject.owlclasses);
        owlinfo.owlobjprops = OwlObjectProperty.fromObject(owlinfoobject.owlobjprops);
        return owlinfo;
    }
}

export default OwlInfo;