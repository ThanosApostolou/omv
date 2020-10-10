<template>
    <svg :x="0" :y="0" :height="this.mappingsvg.height" :width="this.mappingsvg.width">
        <OwlEntityNodeSVG :owlentitysvg="mappingsvg.owl1classessvg" key="1" @show-entity="showEntity" />
        <OwlEntityNodeSVG :owlentitysvg="mappingsvg.owl1objpropssvg" key="2" @show-entity="showEntity" />
        <OwlEntityNodeSVG :owlentitysvg="mappingsvg.owl1datapropssvg" key="3" @show-entity="showEntity" />
        <RulesSVGComp :rulessvg="mappingsvg.rulessvg" key="4" />
        <OwlEntityNodeSVG :owlentitysvg="mappingsvg.owl2classessvg" key="5" @show-entity="showEntity" />
        <OwlEntityNodeSVG :owlentitysvg="mappingsvg.owl2objpropssvg" key="6" @show-entity="showEntity" />
        <OwlEntityNodeSVG :owlentitysvg="mappingsvg.owl2datapropssvg" key="7" @show-entity="showEntity" />
    </svg>
</template>

<script>
import { MappingSVG } from "./MappingSVG";

import OwlEntityNodeSVG from "./OwlEntityNodeSVG.vue";
import RulesSVGComp from "./RulesSVGComp.vue";

export default {
    name: "MappingSvgComp",
    components: {
        OwlEntityNodeSVG,
        RulesSVGComp
    },
    props: {
        mappingsvg: {
            type: MappingSVG,
            default: null
        }
    },
    data() {
        return {
            show: false,
            selectedOwlEntity: null,
            width: 0,
            height: 0
        };
    },
    methods: {
        showEntity(owlentity) {
            this.$emit("show-entity", owlentity);
        }
    }
};
</script>