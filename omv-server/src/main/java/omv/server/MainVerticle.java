package omv.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;

public class MainVerticle extends AbstractVerticle {
    Database database=null;
    Controller controller=null;
    EventBus eventbus=null;

    @Override
    public void start(Promise<Void> promise) {
        this.database = new Database();
        this.controller = new Controller();
        this.eventbus = vertx.eventBus();

        Promise<String> databasePromise = Promise.promise();
        vertx.deployVerticle(this.database, databasePromise);
        databasePromise.future().compose((id) -> {
            Promise<String> controllerPromise = Promise.promise();
            vertx.deployVerticle(this.controller, controllerPromise);
            return controllerPromise.future();
        }).onComplete((ar) -> {
            if (ar.succeeded()) {
                System.out.println("Verticles succeded");
                promise.complete();
            } else {
                System.out.println("Verticles failed");
                promise.fail(ar.cause());
            }
        });
    }
}