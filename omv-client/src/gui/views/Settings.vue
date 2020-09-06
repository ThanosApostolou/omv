<template>
    <v-container>
        <div align="center" justify="center" class="text-center">
            <h1>Change Application Settings</h1>
        </div>

        <v-form v-model="valid" outlined>
            <v-container>
                <v-row align="center" justify="center" class="text-center">
                    <v-col cols="12" xs="12" lg="6">
                        <v-text-field chips outlined label="Server" value="localhost" />
                    </v-col>
                    <v-col cols="12" xs="12" lg="6">
                        <v-switch v-model="theme_switch" label="Dark Theme" />
                    </v-col>
                </v-row>
                <v-row align="center" justify="center" class="text-center">
                    <v-btn color="primary" @click="submit">
                        Save
                    </v-btn>
                </v-row>
            </v-container>
        </v-form>
    </v-container>
</template>

<script>
export default {
    name: "Settings",
    data: function () {
        return {
            theme_switch: true,
            valid: true
        };
    },
    methods: {
        submit() {

        }
    }
};
</script>
