package omv.server;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class MainVerticle extends AbstractVerticle {
    Database database;
    HttpServer httpserver;

    @Override
    public void start(Promise<Void> promise) {
        this.database = new Database();
        this.httpserver = new HttpServer();
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
        });
    }
}