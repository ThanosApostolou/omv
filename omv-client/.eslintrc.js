module.exports = {
    root: true,

    env: {
        es6: true,
        node: true
    },

    parser: "vue-eslint-parser",

    parserOptions: {
        parser: "@typescript-eslint/parser",
        ecmaVersion: 2020,
        sourceType: "module"
    },

    ignorePatterns: [ "node_modules/", "dist/", "dist_electron/", "build_cordova/" ],
    plugins: [ "vue" ],
    "extends": [
        "eslint:recommended",
        "plugin:@typescript-eslint/eslint-recommended",
        "plugin:@typescript-eslint/recommended",
        "plugin:vue/strongly-recommended",
        "@vue/typescript"
    ],
    rules: {
        "no-console": process.env.NODE_ENV === "production" ? "warn" : "off",
        "no-debugger": process.env.NODE_ENV === "production" ? "warn" : "off",
        "strict":                   "error",
        "semi":                     "error",
        "no-unused-vars":           "warn",
        "no-template-curly-in-string":"error",
        "block-scoped-var":         "error",
        "class-methods-use-this":   "error",
        "consistent-return":        "error",
        "no-param-reassign":        "error",
        "no-invalid-this":          "error",
        "init-declarations":        "error",
        "no-label-var":             "error",
        "no-undef-init":            "error",
        "no-undefined":             "error",
        "no-use-before-define":     "error",
        "indent":                   [1, 4],
        "quotes":                   [1, "double"],
        "vue/html-indent":          [1, 4],
        "vue/script-indent":        [1, 4],
        "vue/html-quotes":          [1, "double"],
        "vue/max-attributes-per-line": 0,
        "@typescript-eslint/no-explicit-any": "off",
        "@typescript-eslint/no-inferrable-types": "off",
        "@typescript-eslint/camelcase": "off",
        "@typescript-eslint/explicit-function-return-type": "off"
    }
};
