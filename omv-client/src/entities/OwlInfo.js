import Annotation from './Annotation.js'

class OwlInfo {
    /** @type {String} */
    label;
    /** @type {String} */
    iri;
    /** @type {String} */
    versionIri;
    /** @type {Annotation} */
    annotations;


    static fromObject (theobject) {
        this.label = theobject.label;
        this.iri = theobject.iri;
        this.versionIri = theobject.versionIri;
        this.label = theobject.label;
    }
}

export default OwlInfo;