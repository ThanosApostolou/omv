import { Annotation } from "./Annotation";
import { OwlInfo } from "./OwlInfo";

export class OwlEntity {
    owlinfo: OwlInfo = null;
    iri: string = "";
    name: string = "";
    label: string = "";
    annotations: Annotation[] = [];
    children: OwlEntity[] = [];

    /** @param {object} owlentityobject
     *  @returns {OwlEntity}
     */
    static fromObject(owlentityobject: any, owlinfo: OwlInfo): OwlEntity {
        const owlentity = new OwlEntity();
        owlentity.owlinfo = owlinfo;
        owlentity.iri = owlentityobject.iri;
        owlentity.name = owlentityobject.name;
        owlentity.label = owlentityobject.label;
        owlentity.annotations = Annotation.listFromObject(owlentityobject.annotations);
        owlentity.children = [];
        for (const child of owlentityobject.children) {
            owlentity.children.push(OwlEntity.fromObject(child, owlinfo));
        }
        return owlentity;
    }
    static findAnnotationEntities(rootentity: OwlEntity) {
        Annotation.listFindEntities(rootentity.annotations, rootentity.owlinfo);
        for (const child of rootentity.children) {
            OwlEntity.findAnnotationEntities(child);
        }
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