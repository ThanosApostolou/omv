<template>
    <v-app-bar app short color="primary" dark elevation="5">
        <div class="d-flex align-center">
            <v-img
                alt="Logo"
                src="../assets/omv_logo_light.svg"
                class="shrink mr-2 my-2"
                contain
                height="50"
                width="50"
            />
        </div>
        <div class="d-none d-md-flex">
            <v-btn v-for="navitem in navitems" :key="navitem.path" :to="navitem.path" text>
                <v-icon>{{ navitem.icon }}</v-icon>
                <span class="ml-2">{{ navitem.label }}</span>
            </v-btn>
        </div>
        <v-spacer />

        <v-btn to="/settings" text class="d-none d-md-flex">
            <v-icon>settings</v-icon>
            <span class="ml-2">settings</span>
        </v-btn>
        <div class="d-flex d-md-none">
            <v-menu offset-y>
                <template v-slot:activator="{ on }">
                    <v-btn text v-on="on">
                        <v-icon>menu</v-icon>
                    </v-btn>
                </template>
                <v-list color="primary" dark>
                    <v-list-item v-for="navitem in navitems" :key="navitem.path" :to="navitem.path" text>
                        <v-icon>{{ navitem.icon }}</v-icon>
                        <span class="ml-2">{{ navitem.label }}</span>
                    </v-list-item>
                    <v-divider />
                    <v-list-item to="/settings" text>
                        <v-icon>settings</v-icon>
                        <span class="ml-2">settings</span>
                    </v-list-item>
                </v-list>
            </v-menu>
        </div>
    </v-app-bar>
</template>

<script>
const navitems = [
    {
        path: "/home",
        label: "Home",
        icon: "home"
    },
    {
        path: "/visualizer",
        label: "Visualizer",
        icon: "play_arrow"
    },
    {
        path: "/about",
        label: "About",
        icon: "info"
    }
];

export default {
    name: "MyHeader",
    data: function () {
        return {
            navitems: navitems
        };
    }
};
</script>