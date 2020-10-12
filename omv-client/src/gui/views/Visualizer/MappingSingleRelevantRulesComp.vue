<template>
    <div>
        <v-row v-if="!ready" align="center" justify="center" class="text-center">
            <v-progress-circular v-if="!ready" indeterminate rotate />
        </v-row>
        <div v-if="ready">
            <v-row v-for="(mappingsvg, index) in mappingsvgs" :key="index" class="justify-center text-center">
                <MappingSvgComp :mappingsvg="mappingsvg" @show-entity="showEntity" />
            </v-row>
        </div>
    </div>
</template>

<script>
import { OwlEntity } from "@/entities/OwlEntity";
import { MappingSVG } from "./MappingSVG";

import MappingSvgComp from "./MappingSvgComp.vue";

export default {
    name: "MappingSingleRelevantRulesComp",
    components: {
        MappingSvgComp
    },
    props: {
        parametervalue: {
            type: OwlEntity,
            default: null
        }
    },
    data() {
        return {
            ready: false,
            rules: [],
            mappingsvgs: [],
            visualization: this.$root.app.visualization
        };
    },
    methods: {
        showEntity(owlentity) {
            this.$emit("show-entity", owlentity);
        }
    },
    created() {
        for (const child of this.parametervalue.children) {
            this.rules = this.rules.concat(child.classrules);
        }
        MappingSVG.listFromRules(this.visualization.owl1.owlclasses, this.visualization.owl1.owlobjprops, this.visualization.owl1.owldataprops, this.visualization.owl2.owlclasses, this.visualization.owl2.owlobjprops, this.visualization.owl2.owldataprops, this.rules, true).then((mappingsvgs) => {
            this.mappingsvgs = mappingsvgs;
            this.ready = true;
        });
    }
};
</script>