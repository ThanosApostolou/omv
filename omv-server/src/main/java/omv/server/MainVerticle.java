package omv.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class MainVerticle extends AbstractVerticle {
    Vertx vertx;
    Controller controller;

    @Override
    public void start(Promise<Void> promise) {
        vertx = Vertx.vertx();
        controller = new Controller();
        //System.out.println("test");
        vertx.deployVerticle(controller);
    }
}