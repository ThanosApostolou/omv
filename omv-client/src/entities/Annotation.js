/** @class Annotation */
export class Annotation {
    /** @type {String} */ property = null;
    /** @type {String} */ value = null;
    /** @type {String} */ type = null;
    /** @type {String} */ lang = null;

    /** @param {object} annotationobject
     *  @returns {Annotation}
     */
    static fromObject(annotationobject) {
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
    static listFromObject(annotationsobject) {
        let annotations = [];
        for (let annotationobject of annotationsobject) {
            let annotation = Annotation.fromObject(annotationobject);
            annotations.push(annotation);
        }
        return annotations;
    }
}