# omv
Ontology Mapping Visualizer

# omv-server
Server is deployed on heroku at url:
https://omv-server.herokuapp.com/

### First build on heroku:
```
heroku login
heroku create
heroku apps:rename omv-server
heroku buildpacks:set https://github.com/timanovsky/subdir-heroku-buildpack
heroku buildpacks:add heroku/gradle
heroku config:set PROJECT_PATH=omv-server/
heroku config:set GRADLE_TASK="shadowJar"
```

### Build every time:
```
git push heroku master
```

# omv-client
Client is deployed on netlify:
https://omv-client.netlify.com
