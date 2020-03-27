package omv.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class MainVerticle extends AbstractVerticle {
    Controller controller;

    @Override
    public void start(Promise<Void> promise) {
        controller = new Controller(vertx);
        controller.start(promise);
    }
}