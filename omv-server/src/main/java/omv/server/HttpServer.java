package omv.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import omv.server.actions.*;

public class HttpServer extends AbstractVerticle {
	EventBus eventbus = null;
    Runtime runtime;
    Router router;

    public HttpServer() {
        this.runtime = new Runtime();
        this.router = null;
    }

    public void start(Promise<Void> promise) {
        this.eventbus = vertx.eventBus();
        this.router = Router.router(vertx);
        UserAction.eventbus = this.eventbus;
        this.router.route().handler(BodyHandler.create());

        // Bind "/" to our hello message - so we are still compatible.
        router.get("/api").handler((routingContext) -> {
			HttpServerResponse response = routingContext.response();
			Root root = new Root(runtime.start_datetime);
			response.putHeader("content-type", "application/json")
                    .end(root.toString());
        });

        router.get("/user").handler((routingContext) -> {
            UserAction useraction = new UserAction(routingContext);
            Promise<Void> useractionPromise = Promise.promise();
            useraction.GET(useractionPromise);
            useractionPromise.future().onComplete((ar1) -> {
                useraction.end();
            });
        });
        router.post("/user").handler((routingContext) -> {
            UserAction useraction = new UserAction(routingContext);
            Promise<Void> useractionPromise = Promise.promise();
            useraction.POST(useractionPromise);
            useractionPromise.future().onComplete((ar1) -> {
                useraction.end();
            });
        });

        router.errorHandler(500, rc -> {
            System.err.println("Handling failure");
            Throwable failure = rc.failure();
            if (failure != null) {
              failure.printStackTrace();
            }
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