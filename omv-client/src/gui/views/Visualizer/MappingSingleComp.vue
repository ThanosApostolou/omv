<template>
    <div>
        <v-card :min-width="this.mappingsvg.width+30" elevation="5" outlined color="grey lighten-3" class="card">
            <v-card-text>
                <v-row v-if="rule.label != null" class="text-start">
                    <v-col cols="4" md="3" lg="2" />
                    <v-col>
                        Mapping Rule Number:
                    </v-col>
                    <v-col>
                        {{ rule.label }}
                    </v-col>
                </v-row>
                <v-row v-if="rule.relation != null" class="text-start">
                    <v-col cols="4" md="3" lg="2" />
                    <v-col>
                        Relation:
                    </v-col>
                    <v-col>
                        {{ rule.relation }}
                    </v-col>
                </v-row>
                <v-row v-if="rule.comments != null" class="text-start">
                    <v-col cols="4" md="3" lg="2" />
                    <v-col>
                        Comments:
                    </v-col>
                    <v-col>
                        {{ rule.comments }}
                    </v-col>
                </v-row>
                <v-row v-if="rule.simcomments != null" class="text-start">
                    <v-col cols="4" md="3" lg="2" />
                    <v-col>
                        Simcomments:
                    </v-col>
                    <v-col>
                        {{ rule.simcomments }}
                    </v-col>
                </v-row>
                <v-row v-if="rule.similarity != null" class="text-start">
                    <v-col cols="4" md="3" lg="2" />
                    <v-col>
                        Similarity:
                    </v-col>
                    <v-col>
                        {{ rule.similarity }}
                    </v-col>
                </v-row>
                <v-row v-if="rule.direction != null" class="text-start">
                    <v-col cols="4" md="3" lg="2" />
                    <v-col>
                        Direction:
                    </v-col>
                    <v-col>
                        {{ rule.direction }}
                    </v-col>
                </v-row>
            </v-card-text>
            <MappingSvgComp :mappingsvg="mappingsvg" @show-entity="showEntity" />
            <TransformationComp v-if="rule.directTransformation != null" :transformation="rule.directTransformation" @show-entity="showEntity" />
            <TransformationComp v-if="rule.inverseTransformation != null" :transformation="rule.inverseTransformation" @show-entity="showEntity" />
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