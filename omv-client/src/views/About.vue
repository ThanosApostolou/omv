<template>
    <v-container>
        <v-row align='align' justify='center' class='text-center'>
            <v-col cols='12' lg='6' xs='12'>
                <v-card height='100%'>
                    <v-row align='center' justify='center' class='text-center'>
                        <v-card-title>About Me</v-card-title>
                    </v-row>
                    <v-row align='center' justify='center' class='text-center'>
                        <p>Thanos Apostolou</p>
                    </v-row>
                </v-card>
            </v-col>
            <v-col cols='12' lg='6' xs='12'>
                <v-card height='100%'>
                    <v-row align='center' justify='center' class='text-center'>
                        <v-card-title>Application Information</v-card-title>
                    </v-row>
                    <v-row align='center' justify='center' class='text-center'>
                        <v-col>
                            Application Name:
                        </v-col>
                        <v-col>
                            {{ appdata.appNodeName }}
                        </v-col>
                    </v-row>
                    <v-row align='center' justify='center' class='text-center'>
                        <v-col>
                            Application Displayed Name:
                        </v-col>
                        <v-col>
                            {{ appdata.appName }}
                        </v-col>
                    </v-row>
                    <v-row align='center' justify='center' class='text-center'>
                        <v-col>
                            Application Version:
                        </v-col>
                        <v-col>
                            {{ appdata.appVersion }}
                        </v-col>
                    </v-row>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    name: 'About',
    props: {
        appdata: {
            type: Object,
            default: null
        }
    }
};
</script>
