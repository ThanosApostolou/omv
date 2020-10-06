<template>
    <svg :x="0" :y="0" :height="owlentitysvg.height" :width="owlentitysvg.width">
        <OwlEntityNodeSVG :owlentitysvg="owlentitysvg" @show-entity="showEntity" />
    </svg>
</template>

<script>
import { OwlEntity } from "@/entities/OwlEntity";
import { OwlEntitySVG } from "./OwlEntitySVG";

import OwlEntityNodeSVG from "./OwlEntityNodeSVG";

export default {
    name: "OwlEntitySingleSvg",
    components: {
        OwlEntityNodeSVG
    },
    props: {
        owlentity: {
            type: OwlEntity,
            default: null
        },
        entityType: {
            type: String,
            default: null
        },
        position: {
            type: String,
            default: null
        }
    },
    data() {
        return {
            owlentitysvg: null
        };
    },
    methods: {
        showEntity(owlentity) {
            this.$emit("show-entity", owlentity);
        }
    },
    created() {
        this.owlentitysvg = OwlEntitySVG.fromOwlEntity(this.owlentity, this.entityType, null);
        this.owlentitysvg.children = [];
        this.owlentitysvg.visible = true;
        this.owlentitysvg.calcWidth(1);
        if (this.position == "left") {
            this.owlentitysvg.calcPositions(0, 0);
        } else {
            this.owlentitysvg.calcPositionsReverse(this.owlentitysvg.width, 0);
        }
    }
};
</script>

<style>
.svg {
    cursor: pointer;
}
</style>