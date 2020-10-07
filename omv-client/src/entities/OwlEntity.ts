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

    findByIri(iri: string): OwlEntity {
        if (this.iri == iri) {
            return this;
        } else {
            for (const child of this.children) {
                const foundentity = child.findByIri(iri);
                if (foundentity != null) {
                    return foundentity;
                }
            }
        }
        return null;
    }

    findSize(count: number): number {
        let newcount: number = count + 1;
        for (const child of this.children) {
            newcount = child.findSize(newcount);
        }
        return newcount;
    }
    size(): number {
        return this.findSize(0);
    }
}