import { Visualization } from "./Visualization";

export class Statistics {
    visualization: Visualization = null;
    numberofClassRules: number;
    numberofPropRules: number;
    numberofAllRules: number;
    percentClassRules: string;
    percentPropRules: string;

    owl1classessize: number;
    owl1objpropssize: number;
    owl1datapropssize: number;
    owl2classessize: number;
    owl2objpropssize: number;
    owl2datapropssize: number;

    init(visualization: Visualization) {
        this.visualization = visualization;
        this.numberofClassRules = this.visualization.mapping.classrules.length;
        this.numberofPropRules = this.visualization.mapping.proprules.length;
        this.numberofAllRules = this.numberofClassRules + this.numberofPropRules;
        this.percentClassRules = (this.numberofClassRules / this.numberofAllRules * 100).toFixed(2);
        this.percentPropRules = (this.numberofPropRules / this.numberofAllRules * 100).toFixed(2);
        this.owl1classessize = this.visualization.owl1.owlclasses.size();
        this.owl1objpropssize = this.visualization.owl1.owlobjprops.size();
        this.owl1datapropssize = this.visualization.owl1.owldataprops.size();
        this.owl2classessize = this.visualization.owl2.owlclasses.size();
        this.owl2objpropssize = this.visualization.owl2.owlobjprops.size();
        this.owl2datapropssize = this.visualization.owl2.owldataprops.size();

    }
}