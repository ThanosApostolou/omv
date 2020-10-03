/** @class Annotation */
export class Annotation {
    /** @type {String} */ property: string = "";
    /** @type {String} */ value: string = "";
    /** @type {String} */ type: string = "";
    /** @type {String} */ lang: string = "";

    /** @param {any} annotationobject
     *  @returns {Annotation}
     */
    static fromObject(annotationobject: any): Annotation {
        const annotation: Annotation = new Annotation();
        annotation.property = annotationobject.property;
        annotation.value = annotationobject.value;
        annotation.type = annotationobject.type;
        annotation.lang = annotationobject.lang;
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
}