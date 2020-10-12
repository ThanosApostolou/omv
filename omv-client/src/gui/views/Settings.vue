<template>
    <v-container>
        <div align="center" justify="center" class="text-center">
            <h1>Change Application Settings</h1>
        </div>

        <v-form v-model="valid" outlined>
            <v-container>
                <v-row align="center" justify="center" class="text-center">
                    <v-col cols="6" md="4">
                        <v-text-field chips outlined v-model="serverInputModel" v-bind="serverInput" />
                    </v-col>
                    <v-col cols="6" md="4">
                        <v-switch v-model="serverSwitch" label="default" @change="switchChanged()" />
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
import { App } from "@/App";

export default {
    name: "Settings",
    data: function () {
        return {
            serverInputModel: null,
            serverSwitch: true,
            serverInput: {
                label: "Server",
                disabled: false
            },
            valid: true
        };
    },
    methods: {
        switchChanged() {
            if (this.serverSwitch) {
                this.serverInputModel = App.app.settingsmanager.defaultServer;
            }
            this.serverInput.disabled = this.serverSwitch;
        },
        submit() {
            if (this.serverSwitch) {
                App.app.settingsmanager.setServer(null);
            } else {
                App.app.settingsmanager.setServer(this.serverInputModel);
            }
        }
    },
    computed: {
        serverInputDisabled() {
            return this.serverSwitch;
        }
    },
    created() {
        this.serverInputModel = App.app.settingsmanager.server;
        this.serverSwitch = App.app.settingsmanager.isServerDefault;
        this.switchChanged();
    }

};
</script>
