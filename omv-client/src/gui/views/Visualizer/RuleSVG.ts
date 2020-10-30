import { OwlEntity } from "@/entities/OwlEntity";
import { Rule } from "../../../entities/Rule";
import { OwlEntitySVG } from "./OwlEntitySVG";

interface OwlInfoEntitySvgs {
    classessvg: OwlEntitySVG[];
    objpropssvg: OwlEntitySVG[];
    datapropssvg: OwlEntitySVG[];
    parametervalues: OwlEntity[];
}
interface Line {
    x1: number;
    y1: number;
    x2: number;
    y2: number;
    title: string;
}

export class RuleSVG {
    rule: Rule;
    entity1: OwlInfoEntitySvgs = {
        classessvg: [],
        objpropssvg: [],
        datapropssvg: [],
        parametervalues: []
    };
    entity2: OwlInfoEntitySvgs = {
        classessvg: [],
        objpropssvg: [],
        datapropssvg: [],
        parametervalues: []
    };

    r: number = 8;
    width: number = 40;
    stroke: number = 1;
    fill: string = "lightgrey";
    fontSize: number = 10;
    startx: number;
    starty: number;
    cx: number;
    cy: number;
    endx: number;
    endy: number;
    lines: Line[] = [];
    showBox: boolean = true;
    isVisible: boolean = true;
    titles: string[] = [];


    /** create a RuleSVG instance from a Rule instance
     */
    static fromRule(rule: Rule): RuleSVG {
        const rulesvg = new RuleSVG();
        rulesvg.rule = rule;
        return rulesvg;
    }

    /** create a RuleSVG[] list from a Rule[] list]
     */
    static listFromRules(rules: Rule[]): RuleSVG[] {
        const rulessvg = [];
        for (const rule of rules) {
            rulessvg.push(RuleSVG.fromRule(rule));
        }
        return rulessvg;
    }

    isOneForOne(): boolean {
        if (this.entity1.classessvg.length == 1 && this.entity2.classessvg.length == 1 &&
            this.entity1.objpropssvg.length == 0 && this.entity1.datapropssvg.length == 0 &&
            this.entity2.objpropssvg.length == 0 && this.entity2.datapropssvg.length == 0) {
            return true;
        }
        return false;
    }

    /** calculate positions for RuleSVG[] list starting from x an y parameters
    */
    static listInit(rulessvg: RuleSVG[], x: number, y: number, showBox: boolean) {
        let nexty = y;
        for (const rulesvg of rulessvg) {
            rulesvg.startx = x;
            rulesvg.starty = nexty;
            rulesvg.cx = rulesvg.startx + rulesvg.width/2;
            rulesvg.cy = nexty+rulesvg.r;
            rulesvg.endx = x+rulesvg.width;
            rulesvg.endy = rulesvg.starty + 2*rulesvg.r;
            rulesvg.showBox = showBox;
            nexty += 3*rulesvg.r;
        }
        if (showBox) {
            return nexty;
        } else {
            return 0;
        }
    }


    findEntities(owl1classessvg: OwlEntitySVG, owl1objpropssvg: OwlEntitySVG, owl1datapropssvg: OwlEntitySVG, owl2classessvg: OwlEntitySVG, owl2objpropssvg: OwlEntitySVG, owl2datapropssvg: OwlEntitySVG) {
        for (const entityobj of this.rule.entity1.classes) {
            const foundentity = owl1classessvg.findByIri(entityobj.iri);
            foundentity.rulesvgs.push(this);
            this.entity1.classessvg.push(foundentity);
            if (entityobj.index == null) {
                this.titles.push("class parameter");
            } else {
                this.titles.push("parameter " + entityobj.index);
            }
        }
        for (const entityobj of this.rule.entity1.objectprops) {
            const foundentity = owl1objpropssvg.findByIri(entityobj.iri);
            foundentity.rulesvgs.push(this);
            this.entity1.objpropssvg.push(foundentity);
            if (entityobj.index == null) {
                this.titles.push("property parameter");
            } else {
                this.titles.push("parameter " + entityobj.index);
            }
        }
        for (const entityobj of this.rule.entity1.dataprops) {
            const foundentity = owl1datapropssvg.findByIri(entityobj.iri);
            foundentity.rulesvgs.push(this);
            this.entity1.datapropssvg.push(foundentity);
            if (entityobj.index == null) {
                this.titles.push("property parameter");
            } else {
                this.titles.push("parameter " + entityobj.index);
            }
        }
        for (const entityobj of this.rule.entity2.classes) {
            const foundentity = owl2classessvg.findByIri(entityobj.iri);
            foundentity.rulesvgs.push(this);
            this.entity2.classessvg.push(foundentity);
            if (entityobj.index == null) {
                this.titles.push("class parameter");
            } else {
                this.titles.push("parameter " + entityobj.index);
            }
        }
        for (const entityobj of this.rule.entity2.objectprops) {
            const foundentity = owl2objpropssvg.findByIri(entityobj.iri);
            foundentity.rulesvgs.push(this);
            this.entity2.objpropssvg.push(foundentity);
            if (entityobj.index == null) {
                this.titles.push("property parameter");
            } else {
                this.titles.push("parameter " + entityobj.index);
            }
        }
        for (const entityobj of this.rule.entity2.dataprops) {
            const foundentity = owl2datapropssvg.findByIri(entityobj.iri);
            foundentity.rulesvgs.push(this);
            this.entity2.datapropssvg.push(foundentity);
            if (entityobj.index == null) {
                this.titles.push("property parameter");
            } else {
                this.titles.push("parameter " + entityobj.index);
            }
        }
        this.findParameterValues();
        this.findLines();
    }

    findParameterValues() {
        for (const owlentitysvg of this.entity1.classessvg) {
            for (const parametervalue of owlentitysvg.owlentity.parametervalues) {
                this.entity1.parametervalues.push(parametervalue);
            }
        }
        for (const owlentitysvg of this.entity1.objpropssvg) {
            for (const parametervalue of owlentitysvg.owlentity.parametervalues) {
                this.entity1.parametervalues.push(parametervalue);
            }
        }
        for (const owlentitysvg of this.entity1.datapropssvg) {
            for (const parametervalue of owlentitysvg.owlentity.parametervalues) {
                this.entity1.parametervalues.push(parametervalue);
            }
        }
        for (const owlentitysvg of this.entity2.classessvg) {
            for (const parametervalue of owlentitysvg.owlentity.parametervalues) {
                this.entity2.parametervalues.push(parametervalue);
            }
        }
        for (const owlentitysvg of this.entity2.objpropssvg) {
            for (const parametervalue of owlentitysvg.owlentity.parametervalues) {
                this.entity2.parametervalues.push(parametervalue);
            }
        }
        for (const owlentitysvg of this.entity2.datapropssvg) {
            for (const parametervalue of owlentitysvg.owlentity.parametervalues) {
                this.entity2.parametervalues.push(parametervalue);
            }
        }
    }

    findLines(): void {
        if (this.showBox) {
            let counter=0;
            for (const owlentitysvg of this.entity1.classessvg) {
                this.addLeftLine(owlentitysvg, this.titles[counter]);
                counter++;
            }
            for (const owlentitysvg of this.entity1.objpropssvg) {
                this.addLeftLine(owlentitysvg, this.titles[counter]);
                counter++;
            }
            for (const owlentitysvg of this.entity1.datapropssvg) {
                this.addLeftLine(owlentitysvg, this.titles[counter]);
                counter++;
            }
            for (const owlentitysvg of this.entity2.classessvg) {
                this.addRightLine(owlentitysvg, this.titles[counter]);
                counter++;
            }
            for (const owlentitysvg of this.entity2.objpropssvg) {
                this.addRightLine(owlentitysvg, this.titles[counter]);
                counter++;
            }
            for (const owlentitysvg of this.entity2.datapropssvg) {
                this.addRightLine(owlentitysvg, this.titles[counter]);
                counter++;
            }
        } else {
            for (const owlentitysvg1 of this.entity1.classessvg) {
                for (const owlentitysvg2 of this.entity2.classessvg) {
                    this.addLineNoBox(owlentitysvg1, owlentitysvg2);
                }
                for (const owlentitysvg2 of this.entity2.objpropssvg) {
                    this.addLineNoBox(owlentitysvg1, owlentitysvg2);
                }
                for (const owlentitysvg2 of this.entity2.datapropssvg) {
                    this.addLineNoBox(owlentitysvg1, owlentitysvg2);
                }
            }
            for (const owlentitysvg1 of this.entity1.objpropssvg) {
                for (const owlentitysvg2 of this.entity2.classessvg) {
                    this.addLineNoBox(owlentitysvg1, owlentitysvg2);
                }
                for (const owlentitysvg2 of this.entity2.objpropssvg) {
                    this.addLineNoBox(owlentitysvg1, owlentitysvg2);
                }
                for (const owlentitysvg2 of this.entity2.datapropssvg) {
                    this.addLineNoBox(owlentitysvg1, owlentitysvg2);
                }
            }
            for (const owlentitysvg1 of this.entity1.datapropssvg) {
                for (const owlentitysvg2 of this.entity2.classessvg) {
                    this.addLineNoBox(owlentitysvg1, owlentitysvg2);
                }
                for (const owlentitysvg2 of this.entity2.objpropssvg) {
                    this.addLineNoBox(owlentitysvg1, owlentitysvg2);
                }
                for (const owlentitysvg2 of this.entity2.datapropssvg) {
                    this.addLineNoBox(owlentitysvg1, owlentitysvg2);
                }
            }
        }
    }

    addLeftLine(owlentitysvg: OwlEntitySVG, title: string) {
        const line: Line = {
            x1: this.startx,
            y1: this.cy,
            x2: owlentitysvg.endx,
            y2: owlentitysvg.cy,
            title: title
        };
        this.lines.push(line);
    }
    addRightLine(owlentitysvg: OwlEntitySVG, title: string) {
        const line: Line = {
            x1: this.endx,
            y1: this.cy,
            x2: owlentitysvg.endx,
            y2: owlentitysvg.cy,
            title: title
        };
        this.lines.push(line);
    }
    addLineNoBox(owlentitysvg1: OwlEntitySVG, owlentitysvg2: OwlEntitySVG) {
        const line: Line = {
            x1: owlentitysvg1.endx,
            y1: owlentitysvg1.cy,
            x2: owlentitysvg2.endx,
            y2: owlentitysvg2.cy,
            title: null
        };
        this.lines.push(line);
    }

    static listFindEntities(rulesvgs: RuleSVG[], owl1classessvg: OwlEntitySVG, owl1objpropssvg: OwlEntitySVG, owl1datapropssvg: OwlEntitySVG, owl2classessvg: OwlEntitySVG, owl2objpropssvg: OwlEntitySVG, owl2datapropssvg: OwlEntitySVG) {
        for (const rulesvg of rulesvgs) {
            rulesvg.findEntities(owl1classessvg, owl1objpropssvg, owl1datapropssvg, owl2classessvg, owl2objpropssvg, owl2datapropssvg);
        }
    }

    static listSetVisible(rulesvgs: RuleSVG[]) {
        for (const rulesvg of rulesvgs){
            rulesvg.isVisible = true;
        }
    }
    static listUnsetVisible(rulesvgs: RuleSVG[]) {
        for (const rulesvg of rulesvgs){
            rulesvg.isVisible = false;
        }
    }

}