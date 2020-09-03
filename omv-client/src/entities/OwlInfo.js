import Annotation from "./Annotation.js";
import OwlEntity from "./OwlEntity.js";

class OwlInfo {
    /** @type {String} */
    iri;
    /** @type {String} */
    versionIri;
    /** @type {String} */
    label;
    /** @type {Annotation[]} */
    annotations;
    /** @type {OwlEntity} */
    owlclasses;
    /** @type {OwlEntity} */
    owlobjprops;
    /** @type {OwlEntity} */
    owldataprops;

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
        owlinfo.owlclasses = OwlEntity.fromObject(owlinfoobject.owlclasses);
        owlinfo.owlobjprops = OwlEntity.fromObject(owlinfoobject.owlobjprops);
        owlinfo.owldataprops = OwlEntity.fromObject(owlinfoobject.owldataprops);
        return owlinfo;
    }
}

export default OwlInfo;