import { Mapping } from "./Mapping";
import { OwlInfo } from "./OwlInfo";

export class Visualization {
    /** @type {OwlInfo} */ owl1;
    /** @type {OwlInfo} */ owl2;
    /** @type {Mapping} */ mapping;

    /** @param {Object} Visualizationobject
     * @return {Visualization}
    */
    static fromObject (Visualizationobject) {
        let visualization = new Visualization();
        visualization.owl1 = OwlInfo.fromObject(Visualizationobject.owl1);
        visualization.owl2 = OwlInfo.fromObject(Visualizationobject.owl2);
        visualization.mapping = Mapping.fromObject(Visualizationobject.mapping);
        visualization.mapping.findOrderedRules(visualization.owl1, visualization.owl2);
        return visualization;
    }
}