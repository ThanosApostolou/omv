package omv.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;

import omv.server.models.User;

public class MainVerticle extends AbstractVerticle {
    Database database=null;
    HttpServer httpserver=null;
    EventBus eventbus=null;

    @Override
    public void start(Promise<Void> promise) {
        this.database = new Database();
        this.httpserver = new HttpServer();
        this.eventbus = vertx.eventBus();

        Promise<String> databasePromise = Promise.promise();
        vertx.deployVerticle(this.database, databasePromise);
        databasePromise.future().compose((id) -> {
            Promise<String> httpserverPromise = Promise.promise();
            vertx.deployVerticle(this.httpserver, httpserverPromise);
            return httpserverPromise.future();
        }).onComplete((ar) -> {
            if (ar.succeeded()) {
                System.out.println("Verticles succeded");
                promise.complete();
            } else {
                System.out.println("Verticles failed");
                promise.fail(ar.cause());
            }
            User myuser = new User();
            myuser.init("aaa", "baa", "caa");
            System.out.println(myuser.insertQuery());
            this.eventbus.request("executeQuery", myuser.insertQuery(), (ar1) -> {
                if (ar1.succeeded()) {
                    System.out.println("Received reply: " + ar1.result().body());
                }
            });
        });
    }
}