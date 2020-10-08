import { Annotation } from "./Annotation";

export class AnnotationProperty {
    iri: string = "";
    name: string = "";
    label: string = "";
    annotations: Annotation[] = [];

    public static fromObject(annotationpropertyobject: any): AnnotationProperty {
        const annotationproperty = new AnnotationProperty();
        annotationproperty.iri = annotationpropertyobject.iri;
        annotationproperty.name = annotationpropertyobject.name;
        annotationproperty.label = annotationpropertyobject.label;
        annotationproperty.annotations = Annotation.listFromObject(annotationpropertyobject.annotations);
        return annotationproperty;
    }
    public static listFromObject(annotationpropertiesobject: any[]): AnnotationProperty[] {
        const annotationproperties: AnnotationProperty[] = [];
        for (const annotationpropertyobject of annotationpropertiesobject) {
            annotationproperties.push(AnnotationProperty.fromObject(annotationpropertyobject));
        }
        return annotationproperties;
    }
}