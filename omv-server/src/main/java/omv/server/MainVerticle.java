package omv.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class MainVerticle extends AbstractVerticle {
    HttpServer httpserver;

    @Override
    public void start(Promise<Void> promise) {
        this.httpserver = new HttpServer();
        Promise<String> httpserverpromise = Promise.promise();
        vertx.deployVerticle(this.httpserver, httpserverpromise);
    }
}