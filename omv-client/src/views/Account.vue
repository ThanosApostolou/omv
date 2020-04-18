<template>
    <v-container>
        <div align='center' justify='center' class='text-center'>
            <h1>Login or Register</h1>
            <p>Login or Register in order to be able to save your favorite visualizations</p>
        </div>
        <v-tabs centered>
            <v-tabs-slider />
            <v-tab key='1' href='#tab-1'>
                Register
            </v-tab>
            <v-tab key='2' href='#tab-2'>
                Login
            </v-tab>
            <v-tab-item key='1' value='tab-1'>
                <v-form v-model='valid' outlined>
                    <v-container>
                        <v-row>
                            <v-col cols='12' xs='12' sm='3'>
                                <v-text-field chips outlined label='Email' />
                            </v-col>
                            <v-col cols='12' xs='12' sm='3'>
                                <v-text-field chips outlined label='Username' />
                            </v-col>
                            <v-col cols='12' xs='12' sm='3'>
                                <v-text-field
                                    v-model='password'
                                    :append-icon='show1 ? "mdi-eye" : "mdi-eye-off"'
                                    :type='show1 ? "text" : "password"'
                                    name='input-10-1'
                                    label='Password'
                                    hint='At least 8 characters'
                                    @click:append='show1 = !show1'
                                />
                            </v-col>
                            <v-col cols='12' xs='12' sm='3'>
                                <v-text-field
                                    v-model='password'
                                    :append-icon='show2 ? "mdi-eye" : "mdi-eye-off"'
                                    :type='show2 ? "text" : "password"'
                                    name='input-10-1'
                                    label='Password'
                                    hint='At least 8 characters'
                                    @click:append='show2 = !show2'
                                />
                            </v-col>
                        </v-row>
                        <v-row align='center' justify='center' class='text-center'>
                            <v-btn color='primary' @click='submit'>
                                Register
                            </v-btn>
                        </v-row>
                    </v-container>
                </v-form>
            </v-tab-item>
            <v-tab-item key='2' value='tab-2'>
                <v-form v-model='valid' outlined>
                    <v-container>
                        <v-row>
                            <v-col cols='12' xs='12' sm='6'>
                                <v-text-field chips outlined label='Email' />
                            </v-col>
                            <v-col cols='12' xs='12' sm='6'>
                                <v-text-field
                                    v-model='password'
                                    :append-icon='show1 ? "mdi-eye" : "mdi-eye-off"'
                                    :type='show1 ? "text" : "password"'
                                    name='input-10-1'
                                    label='Password'
                                    hint='At least 8 characters'
                                    @click:append='show1 = !show1'
                                />
                            </v-col>
                        </v-row>
                        <v-row align='center' justify='center' class='text-center'>
                            <v-btn color='primary' @click='submit'>
                                Login
                            </v-btn>
                        </v-row>
                    </v-container>
                </v-form>
            </v-tab-item>
        </v-tabs>
    </v-container>
</template>

<script>
export default {
    name: 'Account',
    data: () => ({
        show1: false,
        show2: false
    })
};
</script>
