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
heroku pg:psql --app=omv-server
    \i omv-server/sql/omvdb/Scripts/create-tables.sql;
```

### Deploy changes on heroku:
```
heroku git:remote --app=omv-server
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

# Build and Run
dependencies:
  - Java SDK >= 8
  - Nodejs >= 10 and NPM

## Run locally
```
# from 1 terminal
cd omv-server
./gradlew run # or "gradlew.bat run" on windows
# server will run in port 8080

# from 2nd terminal
cd omv-client
npm install
npm run server # will run on 1st port after 8080, so probably 8081
```

## Build and Distribute
### Separate server and client
```
cd omv-server
./gradlew shadowJar
# distribute "omv-server/build/libs/omv-server-*-fat.jar" and run
java -jar omv-server-*-fat.jar -Dport=$PORT #where $PORT is your desired port

cd omv-client
npm install
npm run build
# distribute "omv-client/dist" to a static web server with configure redirection to index.html
npm run electron:build # distribute the desktop app in "omv-client/dist_electron"
```

### Server with bundled client
```
cd omv-client
npm install
npm run buildvertx # to build client in server's resources

cd omv-server
./gradlew shadowJar
# distribute "omv-server/build/libs/omv-server-*-fat.jar" and run:
java -jar omv-server-*-fat.jar -Dport=$PORT -Dwithclient=true
```