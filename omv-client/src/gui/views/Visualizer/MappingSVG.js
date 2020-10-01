// eslint-disable-next-line no-unused-vars
import { OwlEntity } from "../../../entities/OwlEntity.js";
import { OwlEntitySVG } from "./OwlEntitySVG.js";
import { RuleSVG } from "./RuleSVG.js";

export class MappingSVG {
    /** @type {Number} */ width;
    /** @type {Number} */ height;
    /** @type {OwlEntitySVG} */ owl1classessvg;
    /** @type {OwlEntitySVG} */ owl1objpropssvg;
    /** @type {OwlEntitySVG} */ owl1datapropssvg;
    /** @type {OwlEntitySVG} */ owl2classessvg;
    /** @type {OwlEntitySVG} */ owl2objpropssvg;
    /** @type {OwlEntitySVG} */ owl2datapropssvg;
    /** @type {RuleSVG[]} */ rulessvg = [];

    init(owl1classes, owl1objprops, owl1dataprops, owl2classes, owl2objprops, owl2dataprops, rules) {
        this.owl1classessvg = OwlEntitySVG.fromOwlEntity(owl1classes, "class", null);
        this.owl1classessvg.calcVisibility(rules, "entity1");
        this.owl1classessvg.calcWidth(1);
        this.owl1classessvg.calcPositions(0, 0);
        this.width = this.owl1classessvg.width;
        this.height = this.owl1classessvg.height;

        this.owl1objpropssvg = OwlEntitySVG.fromOwlEntity(owl1objprops, "objprop", null);
        this.owl1objpropssvg.calcVisibility(rules, "entity1");
        this.owl1objpropssvg.calcWidth(1);
        this.owl1objpropssvg.calcPositions(0, this.height);
        let newwidth = this.owl1objpropssvg.width;
        this.width = Math.max(this.width, newwidth);
        this.height += this.owl1objpropssvg.height;

        this.owl1datapropssvg = OwlEntitySVG.fromOwlEntity(owl1dataprops, "dataprop", null);
        this.owl1datapropssvg.calcVisibility(rules, "entity1");
        this.owl1datapropssvg.calcWidth(1);
        this.owl1datapropssvg.calcPositions(0, this.height);
        newwidth = this.owl1datapropssvg.width;
        this.width = Math.max(this.width, newwidth);
        this.height += this.owl1datapropssvg.height;

        this.width = Math.max(this.width + 100, 700);

        let ruleswidth = this.width;
        this.width += 60;

        this.owl2classessvg = OwlEntitySVG.fromOwlEntity(owl2classes, "class", null);
        this.owl2classessvg.calcVisibility(rules, "entity2");
        this.owl2classessvg.calcWidth(1);

        this.owl2objpropssvg = OwlEntitySVG.fromOwlEntity(owl2objprops, "objprop", null);
        this.owl2objpropssvg.calcVisibility(rules, "entity2");
        this.owl2objpropssvg.calcWidth(1);

        this.owl2datapropssvg = OwlEntitySVG.fromOwlEntity(owl2dataprops, "dataprop", null);
        this.owl2datapropssvg.calcVisibility(rules, "entity2");
        this.owl2datapropssvg.calcWidth(1);

        newwidth = Math.max(this.owl2classessvg.width, this.owl2objpropssvg.width, this.owl2datapropssvg.width);
        this.width += Math.max(newwidth + 100, 700);

        this.owl2classessvg.calcPositionsReverse(this.width, 0);
        let newheight = this.owl2classessvg.height;
        this.owl2objpropssvg.calcPositionsReverse(this.width, newheight);
        newheight += this.owl2objpropssvg.height;
        this.owl2datapropssvg.calcPositionsReverse(this.width, newheight);
        newheight += this.owl2datapropssvg.height;

        this.height = Math.max(this.height, newheight);

        this.rulessvg = RuleSVG.listFromRules(rules);
        newheight = RuleSVG.listInit(this.rulessvg, ruleswidth, 0);
        RuleSVG.listFindEntities(this.rulessvg, this.owl1classessvg, this.owl1objpropssvg, this.owl1datapropssvg, this.owl2classessvg, this.owl2objpropssvg, this.owl2datapropssvg);
        if (newheight > this.height) {
            this.height = newheight;
        }
    }

    static async listFromRules(owl1classes, owl1objprops, owl1dataprops, owl2classes, owl2objprops, owl2dataprops, rules) {
        let mappingsvgs = new Array(rules.length);
        let promises = [];
        let i=0;
        for (let rule of rules) {
            promises.push(new Promise((resolve) => {
                let index = i;
                setTimeout(() => {
                    let newmappingsvg = new MappingSVG();
                    newmappingsvg.init(owl1classes, owl1objprops, owl1dataprops, owl2classes, owl2objprops, owl2dataprops, [rule]);
                    mappingsvgs[index] = newmappingsvg;
                    console.log("promise " + index + " resolved");
                    resolve();
                }, 0);
            }));
            i++;
        }
        await Promise.all(promises);
        return mappingsvgs;
    }
}