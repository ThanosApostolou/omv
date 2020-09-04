<template>
    <svg>
        <OwlEntityNodeSVG :owlentitysvg="owlentitysvg" @show-entity="showEntity" />
    </svg>
</template>

<script>
import OwlEntityNodeSVG from "./OwlEntityNodeSVG.vue";
// eslint-disable-next-line no-unused-vars
import OwlEntity from "../../../entities/OwlEntity.js";
import OwlEntitySVG from "./OwlEntitySVG.js";

export default {
    name: "OwlEntityTreeSVG",
    components: {
        OwlEntityNodeSVG
    },
    props: {
        owlentity: {
            type: Object,
            default: null
        },
        type: {
            type: String,
            default: "class"
        },
        visibilityType: {
            type: String,
            default: "All"
        },
        reverse: {
            type: Boolean,
            default: false
        },
        startx: {
            type: Number,
            default: 0
        },
        starty: {
            type: Number,
            default: 0
        },
    },
    data() {
        return {
            owlentitysvg: null,
            show: false,
            selectedOwlEntity: {}
        };
    },
    methods: {
        showEntity(owlentity) {
            this.$emit("show-entity", owlentity);
        },
    },
    created() {
        this.owlentitysvg = OwlEntitySVG.fromOwlEntity(this.owlentity, this.type, this.reverse, null);
        this.owlentitysvg.init(this.startx, this.starty, this.visibilityType, this.reverse);

    }
};

</script>