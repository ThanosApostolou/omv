<template>
    <div>
        <v-card :min-width="this.mappingsvg.width+30" elevation="5" outlined color="grey lighten-4" class="card">
            <v-card-text>
                <v-row no-gutters v-if="rule.label != null" class="text-start">
                    <v-col cols="4" md="3" lg="2" />
                    <v-col>
                        Mapping Rule Number:
                    </v-col>
                    <v-col>
                        {{ rule.label }}
                    </v-col>
                </v-row>
                <v-row no-gutters v-if="rule.relation != null" class="text-start">
                    <v-col cols="4" md="3" lg="2" />
                    <v-col>
                        Relation:
                    </v-col>
                    <v-col>
                        {{ rule.relation }}
                    </v-col>
                </v-row>
                <v-row no-gutters v-if="rule.comments != null" class="text-start">
                    <v-col cols="4" md="3" lg="2" />
                    <v-col>
                        Comments:
                    </v-col>
                    <v-col>
                        {{ rule.comments }}
                    </v-col>
                </v-row>
                <v-row no-gutters v-if="rule.simcomments != null" class="text-start">
                    <v-col cols="4" md="3" lg="2" />
                    <v-col>
                        Simcomments:
                    </v-col>
                    <v-col>
                        {{ rule.simcomments }}
                    </v-col>
                </v-row>
                <v-row no-gutters v-if="rule.similarity != null" class="text-start">
                    <v-col cols="4" md="3" lg="2" />
                    <v-col>
                        Similarity:
                    </v-col>
                    <v-col>
                        {{ rule.similarity }}
                    </v-col>
                </v-row>
                <v-row no-gutters v-if="rule.direction != null" class="text-start">
                    <v-col cols="4" md="3" lg="2" />
                    <v-col>
                        Direction:
                    </v-col>
                    <v-col>
                        {{ rule.direction }}
                    </v-col>
                </v-row>
            </v-card-text>
            <v-card outlined>
                <br>
                <MappingSvgComp :mappingsvg="mappingsvg" @show-entity="showEntity" />
            </v-card>
            <TransformationComp v-if="rule.directTransformation != null" :transformation="rule.directTransformation" @show-entity="showEntity" />
            <TransformationComp v-if="rule.inverseTransformation != null" :transformation="rule.inverseTransformation" @show-entity="showEntity" />
            <v-card v-if="rulesvg.entity1.parametervalues.length > 0 || rulesvg.entity2.parametervalues.length > 0" outlined>
                <v-card-title>
                    <v-row no-gutters class="text-start">
                        <v-col cols="4" md="3" lg="2">
                            <h4>Parameter Values:</h4>
                        </v-col>
                        <v-col />
                        <v-col />
                    </v-row>
                </v-card-title>
                <v-card-text>
                    <v-expansion-panels v-model="panelModel">
                        <v-expansion-panel v-for="(parametervalue, index) in rulesvg.entity1.parametervalues" :key="index">
                            <v-expansion-panel-header>
                                <OwlEntitySingleSvg :owlentity="parametervalue" :entity-type.camel="parametervalue.type" position="left" @show-entity="showEntity" />
                                Show Relevant Rules
                            </v-expansion-panel-header>
                            <v-expansion-panel-content v-if="panelModel == index">
                                <MappingSingleRelevantRulesComp :parametervalue="parametervalue" @show-entity="showEntity" />
                            </v-expansion-panel-content>
                        </v-expansion-panel>
                        <v-expansion-panel v-for="(parametervalue, index) in rulesvg.entity2.parametervalues" :key="index">
                            <v-expansion-panel-header>
                                <OwlEntitySingleSvg :owlentity="parametervalue" :entity-type.camel="parametervalue.type" position="right" @show-entity="showEntity" />
                                Show Relevant Rules
                            </v-expansion-panel-header>
                            <v-expansion-panel-content v-if="panelModel == index">
                                <MappingSingleRelevantRulesComp :parametervalue="parametervalue" @show-entity="showEntity" />
                            </v-expansion-panel-content>
                        </v-expansion-panel>
                    </v-expansion-panels>
                </v-card-text>
            </v-card>
        </v-card>
        <br>
    </div>
</template>

<script>
import { MappingSVG } from "./MappingSVG";

import MappingSvgComp from "./MappingSvgComp.vue";
import TransformationComp from "./TransformationComp.vue";
import OwlEntitySingleSvg from "./OwlEntitySingleSvg.vue";
import MappingSingleRelevantRulesComp from "./MappingSingleRelevantRulesComp.vue";

export default {
    name: "MappingSingleComp",
    components: {
        MappingSvgComp,
        TransformationComp,
        OwlEntitySingleSvg,
        MappingSingleRelevantRulesComp
    },
    props: {
        mappingsvg: {
            type: MappingSVG,
            default: null
        }
    },
    data() {
        return {
            rulesvg: this.mappingsvg.rulessvg[0],
            rule: this.mappingsvg.rulessvg[0].rule,
            panelModel: null
        };
    },
    methods: {
        showEntity(owlentity) {
            this.$emit("show-entity", owlentity);
        }
    }
};
</script>