import Annotation from './Annotation.js';
import OwlClass from './OwlClass.js';


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
        owlinfo.owlclasses.calcPositions(0);
        return owlinfo;
    }
}

export default OwlInfo;