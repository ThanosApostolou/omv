import { Rule } from "@/entities/Rule";
import { OwlEntity } from "../../../entities/OwlEntity";
import { OwlEntitySVG } from "./OwlEntitySVG";
import { RuleSVG } from "./RuleSVG";

export class MappingSVG {
    /** @type {Number} */ width: number;
    /** @type {Number} */ height: number;
    /** @type {OwlEntitySVG} */ owl1classessvg: OwlEntitySVG;
    /** @type {OwlEntitySVG} */ owl1objpropssvg: OwlEntitySVG;
    /** @type {OwlEntitySVG} */ owl1datapropssvg: OwlEntitySVG;
    /** @type {OwlEntitySVG} */ owl2classessvg: OwlEntitySVG;
    /** @type {OwlEntitySVG} */ owl2objpropssvg: OwlEntitySVG;
    /** @type {OwlEntitySVG} */ owl2datapropssvg: OwlEntitySVG;
    /** @type {RuleSVG[]} */ rulessvg: RuleSVG[] = [];

    init(owl1classes: OwlEntity, owl1objprops: OwlEntity, owl1dataprops: OwlEntity, owl2classes: OwlEntity, owl2objprops: OwlEntity, owl2dataprops: OwlEntity, rules: Rule[], showbox: boolean) {
        // calculate widths
        //// for owl1
        this.owl1classessvg = OwlEntitySVG.fromOwlEntity(owl1classes, "class", null);
        this.owl1classessvg.calcVisibility(rules, "entity1");
        this.owl1classessvg.calcWidth(1);

        this.owl1objpropssvg = OwlEntitySVG.fromOwlEntity(owl1objprops, "objprop", null);
        this.owl1objpropssvg.calcVisibility(rules, "entity1");
        this.owl1objpropssvg.calcWidth(1);

        this.owl1datapropssvg = OwlEntitySVG.fromOwlEntity(owl1dataprops, "dataprop", null);
        this.owl1datapropssvg.calcVisibility(rules, "entity1");
        this.owl1datapropssvg.calcWidth(1);

        //// for owl2
        this.owl2classessvg = OwlEntitySVG.fromOwlEntity(owl2classes, "class", null);
        this.owl2classessvg.calcVisibility(rules, "entity2");
        this.owl2classessvg.calcWidth(1);

        this.owl2objpropssvg = OwlEntitySVG.fromOwlEntity(owl2objprops, "objprop", null);
        this.owl2objpropssvg.calcVisibility(rules, "entity2");
        this.owl2objpropssvg.calcWidth(1);

        this.owl2datapropssvg = OwlEntitySVG.fromOwlEntity(owl2dataprops, "dataprop", null);
        this.owl2datapropssvg.calcVisibility(rules, "entity2");
        this.owl2datapropssvg.calcWidth(1);

        const owl1width = Math.max(this.owl1classessvg.width, this.owl1objpropssvg.width, this.owl1datapropssvg.width);
        const owl2width = Math.max(this.owl2classessvg.width, this.owl2objpropssvg.width, this.owl2datapropssvg.width);

        this.width = 2*Math.max(owl1width, owl2width);
        this.width += 100;
        const ruleswidth = this.width / 2;

        // calculate positions
        //// for owl1 classes
        this.owl1classessvg.calcPositions(0, 0);
        let owl1height = this.owl1classessvg.height;
        //// for owl2 classes
        this.owl2classessvg.calcPositionsReverse(this.width, 0);
        let owl2height = this.owl2classessvg.height;

        owl1height = Math.max(owl1height, owl2height);
        owl2height = Math.max(owl1height, owl2height);
        //if (this.rulessvg.length > 1) {
        owl1height += 16;
        owl2height += 16;
        //}

        this.owl1objpropssvg.calcPositions(0, owl1height);
        owl1height += this.owl1objpropssvg.height;

        this.owl1datapropssvg.calcPositions(0, owl1height);
        owl1height += this.owl1datapropssvg.height;

        this.owl2objpropssvg.calcPositionsReverse(this.width, owl2height);
        owl2height += this.owl2objpropssvg.height;
        this.owl2datapropssvg.calcPositionsReverse(this.width, owl2height);
        owl2height += this.owl2datapropssvg.height;

        this.height = Math.max(owl1height, owl2height);

        this.rulessvg = RuleSVG.listFromRules(rules);
        let rulesheight = RuleSVG.listInit(this.rulessvg, ruleswidth, 0, showbox);
        RuleSVG.listFindEntities(this.rulessvg, this.owl1classessvg, this.owl1objpropssvg, this.owl1datapropssvg, this.owl2classessvg, this.owl2objpropssvg, this.owl2datapropssvg);
        if (rulesheight > this.height) {
            this.height = rulesheight;
        }
        // if single rule
        if (this.rulessvg.length == 1) {
            const rulesvg = this.rulessvg[0];
            let newshowbox = showbox;
            if (rulesvg.isOneForOne()) {
                newshowbox = false;
            }
            this.rulessvg = RuleSVG.listFromRules(rules);
            rulesheight = RuleSVG.listInit(this.rulessvg, ruleswidth, this.height/2 - 5, newshowbox);
            RuleSVG.listFindEntities(this.rulessvg, this.owl1classessvg, this.owl1objpropssvg, this.owl1datapropssvg, this.owl2classessvg, this.owl2objpropssvg, this.owl2datapropssvg);
        }
    }

    static async listFromRules(owl1classes: OwlEntity, owl1objprops: OwlEntity, owl1dataprops: OwlEntity, owl2classes: OwlEntity, owl2objprops: OwlEntity, owl2dataprops: OwlEntity, rules: Rule[], showbox: boolean) {
        const mappingsvgs = new Array(rules.length);
        const promises = [];
        let i=0;
        for (const rule of rules) {
            promises.push(new Promise((resolve) => {
                const index = i;
                setTimeout(() => {
                    const newmappingsvg = new MappingSVG();
                    newmappingsvg.init(owl1classes, owl1objprops, owl1dataprops, owl2classes, owl2objprops, owl2dataprops, [rule], showbox);
                    mappingsvgs[index] = newmappingsvg;
                    resolve();
                }, 0);
            }));
            i++;
        }
        await Promise.all(promises);
        return mappingsvgs;
    }
}