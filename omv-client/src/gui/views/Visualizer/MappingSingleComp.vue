<template>
    <v-card elevation="4">
        <v-card-text>
            <MappingSvgComp :mappingsvg="mappingsvg" @show-entity="showEntity" />
            <v-row v-if="rule.label != null">
                <v-col>
                    label:
                </v-col>
                <v-col>
                    {{ rule.label }}
                </v-col>
            </v-row>
            <v-row v-if="rule.relation != null">
                <v-col>
                    relation:
                </v-col>
                <v-col>
                    {{ rule.relation }}
                </v-col>
            </v-row>
            <v-row v-if="rule.comments != null">
                <v-col>
                    comments:
                </v-col>
                <v-col>
                    {{ rule.comments }}
                </v-col>
            </v-row>
            <v-row v-if="rule.simcomments != null">
                <v-col>
                    simcomments:
                </v-col>
                <v-col>
                    {{ rule.simcomments }}
                </v-col>
            </v-row>
            <v-row v-if="rule.direction != null">
                <v-col>
                    Direction:
                </v-col>
                <v-col>
                    {{ rule.direction }}
                </v-col>
            </v-row>
            <v-row v-if="rule.directTransformation != null">
                <v-col>
                    Direct Transformation:
                </v-col>
                <v-col>
                    {{ JSON.stringify(rule.directTransformation) }}
                </v-col>
            </v-row>
            <v-row v-if="rule.inverseTransformation != null">
                <v-col>
                    Inverse Transformation:
                </v-col>
                <v-col>
                    {{ JSON.stringify(rule.inverseTransformation) }}
                </v-col>
            </v-row>
        </v-card-text>
    </v-card>
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
    data() {
        return {
            rule: this.mappingsvg.rulessvg[0].rule
        };
    },
    methods: {
        showEntity(owlentity) {
            this.$emit("show-entity", owlentity);
        }
    }
};
</script>