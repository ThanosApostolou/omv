import { Annotation } from "./Annotation";
import { AnnotationProperty } from "./AnnotationProperty";
import { OwlEntity } from "./OwlEntity";

export class OwlInfo {
    iri: string = "";
    versionIri: string = "";
    label: string = "";
    annotations: Annotation[] = [];
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
        owlinfo.versionIri = owlinfoobject.versionIri == "" ? null : owlinfoobject.versionIri;
        owlinfo.label = owlinfoobject.label;
        owlinfo.annotationproperties = AnnotationProperty.listFromObject(owlinfoobject.annotationproperties, owlinfo);
        owlinfo.owlclasses = OwlEntity.fromObject(owlinfoobject.owlclasses, owlinfo, "class");
        owlinfo.owlobjprops = OwlEntity.fromObject(owlinfoobject.owlobjprops, owlinfo, "objprop");
        owlinfo.owldataprops = OwlEntity.fromObject(owlinfoobject.owldataprops, owlinfo, "dataprop");
        owlinfo.annotations = Annotation.listFromObject(owlinfoobject.annotations);
        AnnotationProperty.findAnnotationEntities(owlinfo.annotationproperties);
        OwlEntity.findAnnotationEntities(owlinfo.owlclasses);
        OwlEntity.findAnnotationEntities(owlinfo.owlobjprops);
        OwlEntity.findAnnotationEntities(owlinfo.owldataprops);
        Annotation.listFindEntities(owlinfo.annotations, owlinfo);
        owlinfo.owlclasses.findParameterValues();
        owlinfo.owlobjprops.findParameterValues();
        owlinfo.owldataprops.findParameterValues();
        return owlinfo;
    }
}
