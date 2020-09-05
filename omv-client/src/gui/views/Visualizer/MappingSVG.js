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

    init(owl1classes, owl1objprops, owl1dataprops, owl2classes, owl2objpropssvg, owl2dataprops, rules, visibilityType) {
        this.owl1classessvg = OwlEntitySVG.fromOwlEntity(owl1classes, "class", null);
        this.owl1classessvg.init(0, 0, visibilityType, false);
        this.width = this.owl1classessvg.width;
        this.height = this.owl1classessvg.height;

        this.owl1objpropssvg = OwlEntitySVG.fromOwlEntity(owl1objprops, "objprop", null);
        this.owl1objpropssvg.init(0, this.height, visibilityType, false);
        let newwidth = this.owl1objpropssvg.width;
        this.width = Math.max(this.width, newwidth);
        this.height += this.owl1objpropssvg.height;

        this.owl1datapropssvg = OwlEntitySVG.fromOwlEntity(owl1dataprops, "dataprop", null);
        this.owl1datapropssvg.init(0, this.height, visibilityType, false);
        newwidth = this.owl1datapropssvg.width;
        this.width = Math.max(this.width, newwidth);
        this.height += this.owl1datapropssvg.height;

        this.width = Math.max(this.width + 100, 700);

        this.rulessvg = RuleSVG.listFromRules(rules);
        let newheight = RuleSVG.listInit(this.rulessvg, this.width, 0);
        RuleSVG.listFindEntities(this.rulessvg, this.owl1classessvg, this.owl1objpropssvg, this.owl1datapropssvg);

        this.width += 60;
        if (newheight > this.height) {
            this.height = newheight;
        }
    }

}