<template>
    <div>
        <v-card :min-width="this.mappingsvg.width+30" elevation="5" outlined>
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
                <v-row v-if="rule.similarity != null">
                    <v-col>
                        similarity:
                    </v-col>
                    <v-col>
                        {{ rule.similarity }}
                    </v-col>
                </v-row>
                <v-row v-if="rule.direction != null">
                    <v-col>
                        direction:
                    </v-col>
                    <v-col>
                        {{ rule.direction }}
                    </v-col>
                </v-row>
                <TransformationComp v-if="rule.directTransformation != null" :transformation="rule.directTransformation" />
                <TransformationComp v-if="rule.inverseTransformation != null" :transformation="rule.inverseTransformation" />
            </v-card-text>
        </v-card>
        <br>
    </div>
</template>

<script>
import { MappingSVG } from "./MappingSVG";

import MappingSvgComp from "./MappingSvgComp.vue";
import TransformationComp from "./TransformationComp.vue";

export default {
    name: "MappingSingleComp",
    components: {
        MappingSvgComp,
        TransformationComp
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