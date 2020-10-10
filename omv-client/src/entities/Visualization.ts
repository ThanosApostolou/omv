import { Mapping } from "./Mapping";
import { OwlInfo } from "./OwlInfo";
import { Statistics } from "./Statistics";

export class Visualization {
    owl1: OwlInfo;
    owl2: OwlInfo;
    mapping: Mapping;
    statistics: Statistics;

    /** @param {any} Visualizationobject
     * @return {Visualization}
    */
    static fromObject (Visualizationobject: any): Visualization {
        const visualization: Visualization = new Visualization();
        visualization.owl1 = OwlInfo.fromObject(Visualizationobject.owl1);
        visualization.owl2 = OwlInfo.fromObject(Visualizationobject.owl2);
        visualization.mapping = Mapping.fromObject(Visualizationobject.mapping, visualization);
        visualization.mapping.findOrderedRules(visualization.owl1, visualization.owl2);
        visualization.statistics = new Statistics();
        visualization.statistics.init(visualization);


        // calc total rules
        visualization.owl1.owlclasses.calcTotalRules();
        visualization.owl1.owlobjprops.calcTotalRules();
        visualization.owl1.owldataprops.calcTotalRules();
        visualization.owl2.owlclasses.calcTotalRules();
        visualization.owl2.owlobjprops.calcTotalRules();
        visualization.owl2.owldataprops.calcTotalRules();
        return visualization;
    }
}