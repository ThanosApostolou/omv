<template>
    <v-card outlined>
        <v-card-title>
            <v-row class="text-start">
                <v-col cols="4" md="3" lg="2">
                    <h4>Annotations:</h4>
                </v-col>
                <v-col>
                    <h5>Property</h5>
                </v-col>
                <v-col>
                    <h5>Value</h5>
                </v-col>
            </v-row>
        </v-card-title>
        <v-card-text>
            <v-row v-for="annotation in annotations" :key="annotation.property" class="text-start">
                <v-col cols="4" md="3" lg="2" />
                <v-col>
                    {{ annotation.propertyiri }}
                </v-col>
                <v-col>
                    {{ annotation.value }}
                    <small>
                        {{ annotation.type != '' ? '[Type: ' + annotation.type + ']' : '' }}
                        {{ annotation.lang != '' ? '[Lang: ' + annotation.lang + ']' : '' }}
                    </small>
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