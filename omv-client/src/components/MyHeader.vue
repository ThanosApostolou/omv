<template>
    <v-app-bar app short color='primary' dark elevation='5'>
        <div class='d-flex align-center'>
            <v-img
                alt='Logo'
                src='@/assets/logo.svg'
                class='shrink mr-2 my-2'
                contain
                height='50'
                width='50'
            />
        </div>
        <div class='d-none d-md-flex'>
            <v-btn v-for='navitem in navitems' :key='navitem.path' :to='navitem.path' text>
                <v-icon>{{ navitem.icon }}</v-icon>
                <span class='ml-2'>{{ navitem.label }}</span>
            </v-btn>
        </div>
        <v-spacer />

        <v-btn to='/account' text class='d-none d-md-flex'>
            <v-icon>account_circle</v-icon>
            <span class='ml-2'>account</span>
        </v-btn>
        <div class='d-flex d-md-none'>
            <v-menu offset-y>
                <template v-slot:activator='{ on }'>
                    <v-btn text v-on='on'>
                        <v-icon>menu</v-icon>
                    </v-btn>
                </template>
                <v-list color='primary' dark>
                    <v-list-item v-for='navitem in navitems' :key='navitem.path' :to='navitem.path' text>
                        <v-icon>{{ navitem.icon }}</v-icon>
                        <span class='ml-2'>{{ navitem.label }}</span>
                    </v-list-item>
                    <v-divider />
                    <v-list-item to='/account' text>
                        <v-icon>account_circle</v-icon>
                        <span class='ml-2'>account</span>
                    </v-list-item>
                </v-list>
            </v-menu>
        </div>
    </v-app-bar>
</template>

<script>
let navitems = [
    {
        path: '/home',
        label: 'Home',
        icon: 'home'
    },
    {
        path: '/about',
        label: 'About',
        icon: 'info'
    },
    {
        path: '/settings',
        label: 'Settings',
        icon: 'settings'
    }
];

export default {
    name: 'MyHeader',
    props: {
        appdata: {
            type: Object,
            default: null
        }
    },
    data: () => ({
        navitems: navitems
    })
};
</script>