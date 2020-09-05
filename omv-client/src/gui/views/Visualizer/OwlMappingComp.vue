<template>
    <div>
        <svg :x="0" :y="0" :height="this.mappingsvg.height" :width="this.mappingsvg.width">
            <OwlEntityNodeSVG :owlentitysvg="mappingsvg.owl1classessvg" key="1" @show-entity="showEntity" />
            <OwlEntityNodeSVG :owlentitysvg="mappingsvg.owl1objpropssvg" key="2" @show-entity="showEntity" />
            <OwlEntityNodeSVG :owlentitysvg="mappingsvg.owl1datapropssvg" key="3" @show-entity="showEntity" />
            <RulesSVGComp :rulessvg="mappingsvg.rulessvg" key="4" />
            <OwlEntityNodeSVG :owlentitysvg="mappingsvg.owl2classessvg" key="5" @show-entity="showEntity" />
            <OwlEntityNodeSVG :owlentitysvg="mappingsvg.owl2objpropssvg" key="6" @show-entity="showEntity" />
            <OwlEntityNodeSVG :owlentitysvg="mappingsvg.owl2datapropssvg" key="7" @show-entity="showEntity" />
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
        this.mappingsvg.init(this.owl1.owlclasses, this.owl1.owlobjprops, this.owl1.owldataprops, this.owl2.owlclasses, this.owl2.owlobjprops, this.owl2.owldataprops, this.rules);
    }
};
</script>