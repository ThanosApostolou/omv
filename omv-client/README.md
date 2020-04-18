# template-vue

## Initialize
```
vue create template-vue
    manual choose-> unselect all
    separate config files
cd template-vue
vue add babel
vue add eslint
    error only
    lint on save
vue add router
    use history mode
vue add electron-builder
    latest version
npm install --save-dev eslint-plugin-import
vue add vuetify
npm install --save-dev material-design-icons-iconfont

cordova create build_cordova
cd build_cordova
npm install --save-dev cordova
cordova platform add --save android
keytool -genkey -v -keystore template-vue.keystore -alias template-vue-key -storepass template-vue -keypass template-vue -keyalg RSA -validity 36500
```

## Project setup
```
npm install
cd build_cordova
npx cordova prepare

npm run serve
npm run build
npm run electron:serve
npm run electron:build
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).

# Vue Learnings
  - Access data: `this.variable`
  - Access props: `this.$props.variable`
  - Access options: `this.$options.variable`
