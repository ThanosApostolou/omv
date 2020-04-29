package omv.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import omv.server.actions.*;

public class Controller extends AbstractVerticle {
    public static Controller controller;

	public EventBus eventbus;
    public Runtime runtime;
    public Router router;

    public Controller() {
        super();
        this.eventbus = null;
        this.runtime = new Runtime();
        this.router = null;
    }

    public void start(Promise<Void> promise) {
        Controller.controller = this;

        this.eventbus = vertx.eventBus();
        this.router = Router.router(vertx);

        this.router.route().handler(BodyHandler.create());

        router.get("/api")
              .produces("application/json")
              .handler((routingContext) -> {
            RootAction root = new RootAction(routingContext);
            root.GET();
        });

        router.get("/api/user")
              .produces("application/json")
              .handler((routingContext) -> {
            UserAction useraction = new UserAction(routingContext);
            useraction.GET();
        });
        router.post("/api/user")
              .produces("application/json")
              .handler((routingContext) -> {
            UserAction useraction = new UserAction(routingContext);
            useraction.POST();
        });
        router.errorHandler(550, (routingContext) -> { new ErrorAction(routingContext); });
        router.errorHandler(500, (routingContext) -> { new ErrorAction(routingContext); });
        router.errorHandler(422, (routingContext) -> { new ErrorAction(routingContext); });
        router.errorHandler(404, (routingContext) -> { new ErrorAction(routingContext); });
        router.errorHandler(403, (routingContext) -> { new ErrorAction(routingContext); });

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