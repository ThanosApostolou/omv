module.exports = {
    root: true,
    env: {
        es6: true,
        node: true
    },
    extends: [
        'plugin:vue/strongly-recommended',
        'eslint:recommended',
        'plugin:import/errors',
        'plugin:import/warnings'
    ],
    parser: 'vue-eslint-parser',
    parserOptions: {
        parser: 'babel-eslint',
        ecmaVersion: 2020,
        sourceType: 'module'
    },
    ignorePatterns: [ 'node_modules/', 'dist/', 'dist_electron/', 'build_cordova/' ],
    plugins: [ 'vue', 'import' ],
    rules: {
        'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
        'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
        'strict':                   'error',
        'semi':                     'error',
        'no-unused-vars':           'warn',
        'no-template-curly-in-string':'error',
        'block-scoped-var':         'error',
        'class-methods-use-this':   'error',
        'consistent-return':        'error',
        'no-param-reassign':        'error',
        'no-invalid-this':          'error',
        'init-declarations':        'error',
        'no-label-var':             'error',
        'no-undef-init':            'error',
        'no-undefined':             'error',
        'no-use-before-define':     'error',
        'indent':                   [1, 4],
        'quotes':                   [1, 'single'],
        'vue/html-indent':          [1, 4],
        'vue/script-indent':        [1, 4],
        'vue/html-quotes':          [1, 'single'],
        'vue/max-attributes-per-line': 0
    }
};
