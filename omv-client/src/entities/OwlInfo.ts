import { Annotation } from "./Annotation";
import { AnnotationProperty } from "./AnnotationProperty";
import { OwlEntity } from "./OwlEntity";

export class OwlInfo {
    /** @type {String} */ iri: string = "";
    /** @type {String} */ versionIri: string = "";
    /** @type {String} */ label: string = "";
    /** @type {Annotation[]} */ annotations: Annotation[] = [];
    annotationproperties: AnnotationProperty[] = [];
    owlclasses: OwlEntity = null;
    owlobjprops: OwlEntity = null;
    owldataprops: OwlEntity = null;

    /** @param {object} owlinfoobject
     *  @returns {OwlInfo}
     */
    static fromObject (owlinfoobject: any): OwlInfo {
        const owlinfo = new OwlInfo();
        owlinfo.label = owlinfoobject.label;
        owlinfo.iri = owlinfoobject.iri;
        owlinfo.versionIri = owlinfoobject.versionIri;
        owlinfo.label = owlinfoobject.label;
        owlinfo.annotations = Annotation.listFromObject(owlinfoobject.annotations);
        owlinfo.annotationproperties = AnnotationProperty.listFromObject(owlinfoobject.annotationproperties);
        owlinfo.owlclasses = OwlEntity.fromObject(owlinfoobject.owlclasses);
        owlinfo.owlobjprops = OwlEntity.fromObject(owlinfoobject.owlobjprops);
        owlinfo.owldataprops = OwlEntity.fromObject(owlinfoobject.owldataprops);
        return owlinfo;
    }
}
