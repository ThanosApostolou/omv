import { Annotation } from "./Annotation";

export class OwlEntity {
    /** @type {String} */ iri: string = "";
    /** @type {String} */ name: string = "";
    /** @type {String} */ label: string = "";
    /** @type {Annotation[]} */ annotations: Annotation[] = [];
    /** @type {OwlEntity[]} */ children: OwlEntity[] = [];
    /** @type {Boolean} */ hasEquivalentRule: boolean = false;
    /** @type {Boolean} */ hasLinkedWithRule: boolean = false;
    /** @type {Boolean} */ hasOtherRule: boolean = false;

    /** @param {object} owlentityobject
     *  @returns {OwlEntity}
     */
    static fromObject(owlentityobject: any): OwlEntity {
        const owlentity = new OwlEntity();
        owlentity.iri = owlentityobject.iri;
        owlentity.name = owlentityobject.name;
        owlentity.label = owlentityobject.label;
        owlentity.hasEquivalentRule = owlentityobject.hasEquivalentRule;
        owlentity.hasLinkedWithRule = owlentityobject.hasLinkedWithRule;
        owlentity.hasOtherRule = owlentityobject.hasOtherRule;
        owlentity.annotations = Annotation.listFromObject(owlentityobject.annotations);
        owlentity.children = [];
        for (const child of owlentityobject.children) {
            owlentity.children.push(OwlEntity.fromObject(child));
        }
        return owlentity;
    }
}