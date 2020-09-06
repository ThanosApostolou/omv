package omv.server;

import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;

public class JWTManager {
    public JWTAuth provider;

    JWTManager() {
        String publickey = System.getenv("PUBLIC_KEY");
        if (publickey == null) {
            System.out.println("PUBLIC_KEY is not set. Exiting...");
            publickey = "password";
        }
        PubSecKeyOptions pubseckeyoptions = new PubSecKeyOptions().setAlgorithm("HS256")
                                                                  .setPublicKey(publickey)
                                                                  .setSymmetric(true);
        JWTAuthOptions jwtauthoptions = new JWTAuthOptions().addPubSecKey(pubseckeyoptions);
        this.provider = JWTAuth.create(App.app.getVertx(), jwtauthoptions);
    }
}