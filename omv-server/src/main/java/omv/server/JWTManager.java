package omv.server;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;

public class JWTManager {
    public JWTAuth provider;

    JWTManager() {
        this.provider = JWTAuth.create(App.app.getVertx(),
                                          new JWTAuthOptions().addPubSecKey(new PubSecKeyOptions()
                                                              .setAlgorithm("HS256")
                                                              .setPublicKey("password")
                                                              .setSymmetric(true)));
    }
}