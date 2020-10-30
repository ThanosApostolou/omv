<template>
    <div>
        <v-card class="ma-0 pa-0" outlined>
            <v-card-title class="ma-0 pa-0">
                <h3>{{ transformation.type }}:</h3>
            </v-card-title>
            <v-card-text class="ma-0 pa-0">
                <v-row no-gutters v-if="transformation.uri != null" class="text-start">
                    <v-col cols="2" md="3" lg="2" />
                    <v-col>
                        URI:
                    </v-col>
                    <v-col>
                        {{ transformation.uri }}
                    </v-col>
                </v-row>
                <v-row no-gutters v-if="transformation.description != null" class="text-start">
                    <v-col cols="4" md="3" lg="2" />
                    <v-col>
                        Description:
                    </v-col>
                    <v-col>
                        {{ transformation.description }}
                    </v-col>
                </v-row>
            </v-card-text>
            <v-card class="ma-0 pa-0" v-if="transformation.arguments.length > 0" elevation="0">
                <v-card-title class="ma-0 pa-0 pt-1">
                    <v-row no-gutters class="text-start">
                        <v-col cols="4" md="3" lg="2">
                            <h4>Arguments:</h4>
                        </v-col>
                        <v-col>
                            <h5>ARGUMENT NAME</h5>
                        </v-col>
                        <v-col>
                            <h5>ARGUMENT VALUE</h5>
                        </v-col>
                    </v-row>
                </v-card-title>
                <v-card-text class="ma-0 pa-0">
                    <v-row no-gutters v-for="(argument, index) in transformation.arguments" :key="index" class="text-start">
                        <v-col cols="4" md="3" lg="2" />
                        <v-col>
                            {{ argument.argname }}
                        </v-col>
                        <v-col>
                            <v-row no-gutters>
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