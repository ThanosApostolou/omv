<template>
    <v-card elevation="1">
        <v-card-title>
            {{ transformation.type }}
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
            <v-row v-if="transformation.arguments != []">
                <v-col>
                    arguments:
                </v-col>
                <v-col>
                    <v-row>
                        <v-col>
                            <b>ARGUMENT NAME</b>
                        </v-col>
                        <v-col>
                            <b>ARGUMENT VALUE</b>
                        </v-col>
                    </v-row>
                    <v-row v-for="argument in transformation.arguments" :key="argument">
                        <v-col>
                            {{ argument.argname }}
                        </v-col>
                        <v-col>
                            {{ argument.argvalue }}
                        </v-col>
                    </v-row>
                </v-col>
            </v-row>
        </v-card-text>
    </v-card>
</template>

<script>
import { Transformation } from "@/entities/Transformation";
export default {
    name: "TransformationComp",
    props: {
        transformation: {
            type: Transformation,
            default: null
        }
    }
};
</script>