import OwlInfo from "./OwlInfo.js";

export class Visualization {
    owl1;
    owl2;
    mapping;

    static fromObject (Visualizationobject) {
        let visualization = new Visualization();
        visualization.owl1 = OwlInfo.fromObject(Visualizationobject.owl1);
        visualization.owl2 = OwlInfo.fromObject(Visualizationobject.owl2);
        visualization.mapping = Visualizationobject.mapping;
        return visualization;
    }
}