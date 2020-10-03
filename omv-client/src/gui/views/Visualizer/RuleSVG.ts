import { Rule } from "../../../entities/Rule";
import { OwlEntitySVG } from "./OwlEntitySVG";

interface OwlInfoEntitySvgs {
    classessvg: OwlEntitySVG[];
    objpropssvg: OwlEntitySVG[];
    datapropssvg: OwlEntitySVG[];
}
interface Line {
    x1: number;
    y1: number;
    x2: number;
    y2: number;
}

export class RuleSVG {
    rule: Rule;
    entity1: OwlInfoEntitySvgs = {
        classessvg: [],
        objpropssvg: [],
        datapropssvg: []
    };
    entity2: OwlInfoEntitySvgs = {
        classessvg: [],
        objpropssvg: [],
        datapropssvg: []
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
            this.entity1.classessvg.push(foundentity);
        }
        for (const entityobj of this.rule.entity1.objectprops) {
            const foundentity = owl1objpropssvg.findByIri(entityobj.iri);
            this.entity1.objpropssvg.push(foundentity);
        }
        for (const entityobj of this.rule.entity1.dataprops) {
            const foundentity = owl1datapropssvg.findByIri(entityobj.iri);
            this.entity1.datapropssvg.push(foundentity);
        }
        for (const entityobj of this.rule.entity2.classes) {
            const foundentity = owl2classessvg.findByIri(entityobj.iri);
            this.entity2.classessvg.push(foundentity);
        }
        for (const entityobj of this.rule.entity2.objectprops) {
            const foundentity = owl2objpropssvg.findByIri(entityobj.iri);
            this.entity2.objpropssvg.push(foundentity);
        }
        for (const entityobj of this.rule.entity2.dataprops) {
            const foundentity = owl2datapropssvg.findByIri(entityobj.iri);
            this.entity2.datapropssvg.push(foundentity);
        }
        this.findLines();
    }

    findLines(): void {
        if (this.showBox) {
            for (const owlentitysvg of this.entity1.classessvg) {
                this.addLeftLine(owlentitysvg);
            }
            for (const owlentitysvg of this.entity1.objpropssvg) {
                this.addLeftLine(owlentitysvg);
            }
            for (const owlentitysvg of this.entity1.datapropssvg) {
                this.addLeftLine(owlentitysvg);
            }
            for (const owlentitysvg of this.entity2.classessvg) {
                this.addRightLine(owlentitysvg);
            }
            for (const owlentitysvg of this.entity2.objpropssvg) {
                this.addRightLine(owlentitysvg);
            }
            for (const owlentitysvg of this.entity2.datapropssvg) {
                this.addRightLine(owlentitysvg);
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

    addLeftLine(owlentitysvg: OwlEntitySVG) {
        const line: Line = {
            x1: this.startx,
            y1: this.cy,
            x2: owlentitysvg.endx,
            y2: owlentitysvg.cy
        };
        this.lines.push(line);
    }
    addRightLine(owlentitysvg: OwlEntitySVG) {
        const line: Line = {
            x1: this.endx,
            y1: this.cy,
            x2: owlentitysvg.endx,
            y2: owlentitysvg.cy
        };
        this.lines.push(line);
    }
    addLineNoBox(owlentitysvg1: OwlEntitySVG, owlentitysvg2: OwlEntitySVG) {
        const line: Line = {
            x1: owlentitysvg1.endx,
            y1: owlentitysvg1.cy,
            x2: owlentitysvg2.endx,
            y2: owlentitysvg2.cy
        };
        this.lines.push(line);
    }

    static listFindEntities(rulesvgs: RuleSVG[], owl1classessvg: OwlEntitySVG, owl1objpropssvg: OwlEntitySVG, owl1datapropssvg: OwlEntitySVG, owl2classessvg: OwlEntitySVG, owl2objpropssvg: OwlEntitySVG, owl2datapropssvg: OwlEntitySVG) {
        for (const rulesvg of rulesvgs) {
            rulesvg.findEntities(owl1classessvg, owl1objpropssvg, owl1datapropssvg, owl2classessvg, owl2objpropssvg, owl2datapropssvg);
        }

    }

}