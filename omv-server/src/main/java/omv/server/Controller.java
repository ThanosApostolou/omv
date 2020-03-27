package omv.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

import omv.server.actions.*;

public class Controller extends AbstractVerticle {
    Runtime runtime;
    Router router;

    public Controller() {
        super();
        runtime = new Runtime();
        router = null;
    }

    @Override
    public void start(Promise<Void> startPromise) {
        router = Router.router(vertx);

        // Bind "/" to our hello message - so we are still compatible.
        router.route("/").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			Root root = new Root(runtime.start_datetime);
			response
				.putHeader("content-type", "text/html")
				.end("<h1>Hello from my first Vert.x 3 application " + root.getStart_datetime() + " </h1>");
        });

        // Create the HTTP server and pass the "accept" method to the request handler.
        vertx.createHttpServer()
            .requestHandler(router)
            .listen(
                // Retrieve the port from the configuration,
                // default to 8080.
                config().getInteger("http.port", 8080), result -> {
                    if (result.succeeded()) {
                        startPromise.complete();
                    } else {
                        startPromise.fail(result.cause());
                    }
                }
            );
    }

}