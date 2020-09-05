<template>
    <div>
        <svg :x="0" :y="0" :height="this.mappingsvg.height" :width="this.mappingsvg.width">
            <OwlEntityNodeSVG :owlentitysvg="mappingsvg.owl1classessvg" key="1" @show-entity="showEntity" />
            <OwlEntityNodeSVG :owlentitysvg="mappingsvg.owl1objpropssvg" key="2" @show-entity="showEntity" />
            <OwlEntityNodeSVG :owlentitysvg="mappingsvg.owl1datapropssvg" key="3" @show-entity="showEntity" />
            <RulesSVGComp :rulessvg="mappingsvg.rulessvg" key="4" />
        </svg>
        <v-dialog v-if="show" v-model="show">
            <v-card>
                <span> {{ selectedOwlEntity.label }}</span>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
import { OwlEntitySVG } from "./OwlEntitySVG.js";
import OwlEntityNodeSVG from "./OwlEntityNodeSVG.vue";
import RulesSVGComp from "./RulesSVGComp.vue";
import { MappingSVG } from "./MappingSVG.js";
import { RuleSVG } from "./RuleSVG.js";

export default {
    name: "OwlMappingComp",
    components: {
        OwlEntityNodeSVG,
        RulesSVGComp
    },
    props: {
        owl1: {
            type: Object,
            default: null
        },
        owl2: {
            type: Object,
            default: null
        },
        rules: {
            type: Array,
            default: null
        },
        visibilityType: {
            type: String,
            default: "AllRules"
        }
    },
    data() {
        return {
            show: false,
            selectedOwlEntity: null,
            width: 0,
            height: 0,
            owl1classes: {
                owlentitysvg: null
            },
            owl1objprops: {
                owlentitysvg: null
            },
            owl1dataprops: {
                owlentitysvg: null
            },
            rulessvgcomp: {
                rulessvg: null
            },
            mappingsvg: {}
        };
    },
    methods: {
        showEntity(owlentity) {
            this.selectedOwlEntity = owlentity;
            this.show = true;
        }
    },
    created() {
        this.mappingsvg = new MappingSVG();
        this.mappingsvg.init(this.owl1.owlclasses, this.owl1.owlobjprops, this.owl1.owldataprops, null, null, null, this.rules, this.visibilityType);

        /*
        this.owl1classes.owlentitysvg = OwlEntitySVG.fromOwlEntity(this.owl1.owlclasses, "class", null);
        this.owl1classes.owlentitysvg.init(0, 0, this.visibilityType, false);
        this.width = this.owl1classes.owlentitysvg.width;
        this.height = this.owl1classes.owlentitysvg.height;

        this.owl1objprops.owlentitysvg = OwlEntitySVG.fromOwlEntity(this.owl1.owlobjprops, "objprop", null);
        this.owl1objprops.owlentitysvg.init(0, this.height, this.visibilityType, false);
        let newwidth = this.owl1objprops.owlentitysvg.width;
        this.width = Math.max(this.width, newwidth);
        this.height += this.owl1objprops.owlentitysvg.height;

        this.owl1dataprops.owlentitysvg = OwlEntitySVG.fromOwlEntity(this.owl1.owldataprops, "dataprop", null);
        this.owl1dataprops.owlentitysvg.init(0, this.height, this.visibilityType, false);
        newwidth = this.owl1dataprops.owlentitysvg.width;
        this.width = Math.max(this.width, newwidth);
        this.height += this.owl1dataprops.owlentitysvg.height;

        this.width = Math.max(this.width + 100, 700);

        this.rulessvgcomp.rulessvg = RuleSVG.listFromRules(this.rules);
        let newheight = RuleSVG.listInit(this.rulessvgcomp.rulessvg, this.width, 0);
        RuleSVG.listFindEntities(this.rulessvgcomp.rulessvg, this.owl1classes.owlentitysvg, this.owl1objprops.owlentitysvg, this.owl1dataprops.owlentitysvg);

        this.width += 60;
        if (newheight > this.height) {
            this.height = newheight;
        }
        */
    }
};
</script>