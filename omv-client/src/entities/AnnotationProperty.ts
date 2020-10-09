import { Annotation } from "./Annotation";
import { OwlInfo } from "./OwlInfo";

export class AnnotationProperty {
    owlinfo: OwlInfo = null;
    iri: string = "";
    name: string = "";
    label: string = "";
    annotations: Annotation[] = [];

    public static fromObject(annotationpropertyobject: any, owlinfo: OwlInfo): AnnotationProperty {
        const annotationproperty = new AnnotationProperty();
        annotationproperty.owlinfo = owlinfo;
        annotationproperty.iri = annotationpropertyobject.iri;
        annotationproperty.name = annotationpropertyobject.name;
        annotationproperty.label = annotationpropertyobject.label;
        annotationproperty.annotations = Annotation.listFromObject(annotationpropertyobject.annotations);
        return annotationproperty;
    }
    public static listFromObject(annotationpropertiesobject: any[], owlinfo: OwlInfo): AnnotationProperty[] {
        const annotationproperties: AnnotationProperty[] = [];
        for (const annotationpropertyobject of annotationpropertiesobject) {
            annotationproperties.push(AnnotationProperty.fromObject(annotationpropertyobject, owlinfo));
        }

        for (const annotationproperty of annotationproperties) {
            Annotation.listFindEntities(annotationproperty.annotations, annotationproperty.owlinfo);
        }
        return annotationproperties;
    }
}