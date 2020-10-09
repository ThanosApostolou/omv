<template>
    <div>
        <v-card outlined>
            <v-card-title>
                <v-row>
                    {{ transformation.type }}:
                </v-row>
            </v-card-title>
            <v-card-text>
                <v-row v-if="transformation.uri != null">
                    <v-col>
                        URI:
                    </v-col>
                    <v-col>
                        {{ transformation.uri }}
                    </v-col>
                </v-row>
                <v-row v-if="transformation.description != null">
                    <v-col>
                        description:
                    </v-col>
                    <v-col>
                        {{ transformation.description }}
                    </v-col>
                </v-row>
                <v-card outlined>
                    <v-card-title>
                        <v-row>
                            <b>ARGUMENTS:</b>
                        </v-row>
                    </v-card-title>
                    <v-card-text>
                        <v-row>
                            <v-col>
                                <b>ARGUMENT NAME</b>
                            </v-col>
                            <v-col>
                                <b>ARGUMENT VALUE</b>
                            </v-col>
                        </v-row>
                        <v-row v-for="(argument, index) in transformation.arguments" :key="index">
                            <v-col>
                                {{ argument.argname }}
                            </v-col>
                            <v-col>
                                <v-row>
                                    <v-col v-if="argument.argvalue.owlentity == null">
                                        {{ argument.argvalue.stringvalue }}
                                    </v-col>
                                    <v-col v-if="argument.argvalue.owlentity != null">
                                        <OwlEntitySingleSvg :owlentity="argument.argvalue.owlentity" :entity-type.camel="argument.argvalue.type" :position="argument.argvalue.position" @show-entity="showEntity" />
                                    </v-col>
                                </v-row>
                            </v-col>
                        </v-row>
                    </v-card-text>
                </v-card>
            </v-card-text>
        </v-card>
    </div>
</template>

<script>
import { Transformation } from "@/entities/Transformation";

import OwlEntitySingleSvg from "./OwlEntitySingleSvg";

export default {
    name: "TransformationComp",
    components: {
        OwlEntitySingleSvg
    },
    props: {
        transformation: {
            type: Transformation,
            default: null
        }
    },
    methods: {
        showEntity(owlentity) {
            this.$emit("show-entity", owlentity);
        }
    },
};
</script>