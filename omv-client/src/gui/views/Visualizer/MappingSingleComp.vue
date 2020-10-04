<template>
    <div>
        <MappingSvgComp :mappingsvg="mappingsvg" @show-entity="showEntity" />
    </div>
</template>

<script>
import { MappingSVG } from "./MappingSVG";

import MappingSvgComp from "./MappingSvgComp.vue";

export default {
    name: "MappingSingleComp",
    components: {
        MappingSvgComp
    },
    props: {
        mappingsvg: {
            type: MappingSVG,
            default: null
        }
    },
    methods: {
        showEntity(owlentity) {
            this.$emit("show-entity", owlentity);
        }
    }
};
</script>