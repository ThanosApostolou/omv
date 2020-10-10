import { AnnotationProperty } from "./AnnotationProperty";
import { OwlEntity } from "./OwlEntity";
import { OwlInfo } from "./OwlInfo";

/** @class Annotation */
export class Annotation {
    property: AnnotationProperty = null;
    propertyiri: string = "";
    value: string = "";
    valueentity: OwlEntity = null;
    valueentitytype: string = "";
    type: string = "";
    lang: string = "";
    isValueIri: boolean = false;

    /** @param {any} annotationobject
     *  @returns {Annotation}
     */
    static fromObject(annotationobject: any): Annotation {
        const annotation: Annotation = new Annotation();
        annotation.propertyiri = annotationobject.property;
        annotation.value = annotationobject.value;
        annotation.type = annotationobject.type;
        annotation.lang = annotationobject.lang;
        annotation.isValueIri = annotationobject.isValueIri;
        return annotation;
    }

    /** @param {any[]} annotationsobject
     *  @returns {Annotation[]}
    */
    static listFromObject(annotationsobject: any): Annotation[] {
        const annotations: Annotation[] = [];
        for (const annotationobject of annotationsobject) {
            const annotation = Annotation.fromObject(annotationobject);
            annotations.push(annotation);
        }
        return annotations;
    }

    findEntities(owlinfo: OwlInfo) {
        // find annotation property if exists
        for (const annotationproperty of owlinfo.annotationproperties) {
            if (annotationproperty.iri == this.propertyiri) {
                this.property = annotationproperty;
                break;
            }
        }
        // find value entity if exists
        if (this.isValueIri) {
            let foundentity: OwlEntity = null;
            foundentity = owlinfo.owlclasses.findByIri(this.value);
            if (foundentity != null) {
                this.valueentity = foundentity;
                this.valueentitytype = "class";
            } else {
                foundentity = owlinfo.owlobjprops.findByIri(this.value);
                if (foundentity != null) {
                    this.valueentity = foundentity;
                    this.valueentitytype = "objprop";
                } else {
                    foundentity = owlinfo.owldataprops.findByIri(this.value);
                    if (foundentity != null) {
                        this.valueentity = foundentity;
                        this.valueentitytype = "dataprop";
                    }
                }
            }
        }
    }
    static listFindEntities(annotations: Annotation[], owlinfo: OwlInfo) {
        for (const annotation of annotations) {
            annotation.findEntities(owlinfo);
        }
    }
}