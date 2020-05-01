package omv.server;

import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import omv.server.controllers.*;

public class WebServer {
    public Router router;

    public WebServer() {
        this.router = null;
    }

    public void start(Promise<Void> promise) {
        this.router = Router.router(App.app.getVertx());

        this.router.route().handler(BodyHandler.create());

        router.get("/api")
              .produces("application/json")
              .handler((routingContext) -> {
            new RootController(routingContext).GET();
        });

        router.get("/api/user")
              .produces("application/json")
              .handler((routingContext) -> {
            new UserController(routingContext).GET();
        });
        router.post("/api/user")
              .produces("application/json")
              .handler((routingContext) -> {
            new UserController(routingContext).POST();
        });
        int[] errors = {550, 500, 422, 405, 404, 403, 400};
        for (int error : errors) {
            router.errorHandler(error, (routingContext) -> { new ErrorController(routingContext); });
        }

        int port=8080;
        String portstring = System.getProperty("port");
        if (portstring != null) {
            port = Integer.parseInt(portstring);
        }
        // Create the HTTP server and pass the "accept" method to the request handler.
        App.app.getVertx().createHttpServer()
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