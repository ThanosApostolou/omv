# OMV
Ontologies' Mappings Visualizer

# OMV server
Server is deployed on heroku: https://omv-server.herokuapp.com/

### First deploy on heroku:
```
heroku login
heroku create
heroku apps:rename omv-server
heroku buildpacks:set https://github.com/timanovsky/subdir-heroku-buildpack
heroku buildpacks:add heroku/gradle
heroku config:set PROJECT_PATH=omv-server/
heroku config:set GRADLE_TASK="shadowJar"
heroku addons:create heroku-postgresql:hobby-dev --app=omv-server
```

### Deploy changes on heroku:
```
git push heroku master
```

### Build and run server
```
cd omv-server
./gradlew shadowJar
java -jar build/libs/omv-server-*-fat.jar
```

# OMV client
Client is deployed on netlify: https://omv-client.netlify.com

### Build client
```
cd omv-client
npm install
npm run build
```