package omv.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import omv.server.actions.*;

public class Controller extends AbstractVerticle {
	public EventBus eventbus = null;
    public Runtime runtime;
    public Router router;

    public Controller() {
        this.runtime = new Runtime();
        this.router = null;
    }

    public void start(Promise<Void> promise) {
        this.eventbus = vertx.eventBus();
        this.router = Router.router(vertx);
        UserAction.controller = this;

        this.router.route().handler(BodyHandler.create());

        // Bind "/" to our hello message - so we are still compatible.
        router.get("/api")
              .produces("application/json")
              .handler((routingContext) -> {
            RootAction root = new RootAction(routingContext);
            root.GET();
        });

        router.get("/user")
              .produces("application/json")
              .handler((routingContext) -> {
            UserAction useraction = new UserAction(routingContext);
            useraction.GET();
        });
        router.post("/user")
              .produces("application/json")
              .handler((routingContext) -> {
            UserAction useraction = new UserAction(routingContext);
            useraction.POST();
        });
        router.errorHandler(500, (routingContext) -> {
            Action500 action500 = new Action500(routingContext);
        });
        router.errorHandler(404, (routingContext) -> {
            Action404 action404 = new Action404(routingContext);
        });
        router.errorHandler(403, (routingContext) -> {
            Action403 action403 = new Action403(routingContext);
        });

        int port=8080;
        String portstring = System.getProperty("port");
        if (portstring != null) {
            port = Integer.parseInt(portstring);
        }
        // Create the HTTP server and pass the "accept" method to the request handler.
        vertx.createHttpServer()
            .requestHandler(this.router)
            .listen(port, (result) -> {
                if (result.succeeded()) {
                    promise.complete();
                } else {
                    promise.fail(result.cause());
                }
            });
    }

}