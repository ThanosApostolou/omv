<template>
    <v-card outlined>
        <v-card-title>
            <v-row justify="center" class="text-center">
                <v-col>
                    <b>Annotations:</b>
                </v-col>
                <v-col />
            </v-row>
        </v-card-title>
        <v-card-text>
            <v-row justify="center" class="text-center">
                <v-col>
                    <v-row justify="center" class="text-center">
                        <v-col>
                            PROPERTY
                        </v-col>
                        <v-col>
                            VALUE
                        </v-col>
                    </v-row>
                    <v-row v-for="annotation in annotations" :key="annotation.property" justify="center" class="text-center">
                        <v-col>
                            {{ annotation.property }}
                        </v-col>
                        <v-col>
                            {{ annotation.value }}
                            <small>
                                {{ annotation.type != '' ? '[Type: ' + annotation.type + ']' : '' }}
                                {{ annotation.lang != '' ? '[Lang: ' + annotation.lang + ']' : '' }}
                            </small>
                        </v-col>
                    </v-row>
                </v-col>
            </v-row>
        </v-card-text>
    </v-card>
</template>
<script>
export default {
    name: "AnnotationsComp",
    props: {
        annotations: {
            type: Array,
            default() {return [];}
        }
    }
};
</script>