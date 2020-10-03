/** @class Annotation */
export class Annotation {
    /** @type {String} */ property: string = "";
    /** @type {String} */ value: string = "";
    /** @type {String} */ type: string = "";
    /** @type {String} */ lang: string = "";

    /** @param {any} annotationobject
     *  @returns {Annotation}
     */
    static fromObject(annotationobject: any) {
        let annotation = new Annotation();
        annotation.property = annotationobject.property;
        annotation.value = annotationobject.value;
        annotation.type = annotationobject.type;
        annotation.lang = annotationobject.lang;
        return annotation;
    }

    /** @param {object[]} annotationsobject
     *  @returns {Annotation[]}
    */
    static listFromObject(annotationsobject: any) {
        let annotations: Annotation[] = [];
        for (let annotationobject of annotationsobject) {
            let annotation = Annotation.fromObject(annotationobject);
            annotations.push(annotation);
        }
        return annotations;
    }
}