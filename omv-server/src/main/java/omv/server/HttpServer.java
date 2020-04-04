package omv.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

import omv.server.actions.*;

public class HttpServer extends AbstractVerticle {
    Runtime runtime;
    Router router;

    public HttpServer() {
        this.runtime = new Runtime();
        this.router = null;
    }

    public void start(Promise<Void> promise) {
        this.router = Router.router(vertx);

        // Bind "/" to our hello message - so we are still compatible.
        router.route("/api").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			Root root = new Root(runtime.start_datetime);
			response.putHeader("content-type", "application/json")
                    .end(root.toString());
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