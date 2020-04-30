package omv.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public class App extends AbstractVerticle {
    public static App app;

    public DBManager dbmanager=null;
    public WebServer webserver=null;
    public EventBus eventbus=null;

    @Override
    public void start(Promise<Void> promise) {
        App.app = this;
        this.dbmanager = new DBManager();
        this.webserver = new WebServer();
        this.eventbus = vertx.eventBus();

        Promise<Void> databasePromise = Promise.promise();
        this.dbmanager.start(databasePromise);
        //vertx.deployVerticle(this.dbmanager, databasePromise);
        databasePromise.future().compose((id) -> {
            Promise<Void> controllerPromise = Promise.promise();
            this.webserver.start(controllerPromise);
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

    public Vertx getVertx() {
        return this.vertx;
    }
}