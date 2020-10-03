import { Mapping } from "./Mapping";
import { OwlInfo } from "./OwlInfo";

export class Visualization {
    /** @type {OwlInfo} */ owl1: OwlInfo;
    /** @type {OwlInfo} */ owl2: OwlInfo;
    /** @type {Mapping} */ mapping: Mapping;

    /** @param {any} Visualizationobject
     * @return {Visualization}
    */
    static fromObject (Visualizationobject: any): Visualization {
        const visualization: Visualization = new Visualization();
        visualization.owl1 = OwlInfo.fromObject(Visualizationobject.owl1);
        visualization.owl2 = OwlInfo.fromObject(Visualizationobject.owl2);
        visualization.mapping = Mapping.fromObject(Visualizationobject.mapping);
        visualization.mapping.findOrderedRules(visualization.owl1, visualization.owl2);
        return visualization;
    }
}