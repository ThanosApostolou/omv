package omv.server;

import java.util.HashSet;
import java.util.Set;

import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.StaticHandler;
import omv.server.controllers.*;

public class WebServer {
    public Router router;

    public WebServer() {
        this.router = null;
    }

    public void start(Promise<Void> promise) {
        this.router = Router.router(App.app.getVertx());

        Set<String> allowedHeaders = new HashSet<>();
        allowedHeaders.add("x-requested-with");
        allowedHeaders.add("Access-Control-Allow-Origin");
        allowedHeaders.add("Access-Control-Request-Method");
        allowedHeaders.add("Access-Control-Allow-Credentials");
        allowedHeaders.add("Access-Control-Allow-Headers");
        allowedHeaders.add("origin");
        allowedHeaders.add("Content-Type");
        allowedHeaders.add("accept");
        allowedHeaders.add("X-PINGARUNER");
        allowedHeaders.add("Authorization");

        Set<HttpMethod> allowedMethods = new HashSet<>();
        allowedMethods.add(HttpMethod.GET);
        allowedMethods.add(HttpMethod.POST);
        allowedMethods.add(HttpMethod.OPTIONS);
        allowedMethods.add(HttpMethod.DELETE);
        allowedMethods.add(HttpMethod.PATCH);
        allowedMethods.add(HttpMethod.PUT);

        this.router.route().handler(CorsHandler.create("*").allowedHeaders(allowedHeaders).allowedMethods(allowedMethods));

        this.router.route().handler(BodyHandler.create()
                                               .setMergeFormAttributes(true)
                                               .setDeleteUploadedFilesOnEnd(true));

        router.get("/api")
              .produces("application/json")
              .handler((routingContext) -> {
            new RootController(routingContext).GET();
        });
        /* for future database
        router.get("/api/user")
              .produces("application/json")
              .handler((routingContext) -> {
            new UserController(routingContext).get();
        });
        router.post("/api/user")
              .produces("application/json")
              .handler((routingContext) -> {
            new UserController(routingContext).post();
        });
        router.delete("/api/user")
              .produces("application/json")
              .handler((routingContext) -> {
            new UserController(routingContext).delete();
        });
        router.post("/api/login")
              .produces("application/json")
              .handler((routingContext) -> {
            new LoginController(routingContext).post();
        });
        */
        router.post("/api/visualization")
              .produces("application/json")
              .handler((routingContext) -> {
            new VisualizationController(routingContext).post();
        });


        boolean withclient=false;
        String withclientstring = System.getProperty("withclient");
        if (withclientstring != null) {
            withclient = Boolean.parseBoolean(withclientstring);
        }
        if (withclient) {
            router.route("/api/*")
                  .handler((routingContext) -> {
                routingContext.fail(404);
            });
            router.route("/*").handler(StaticHandler.create("dist"));
            router.get()
                  .handler((routingContext) -> {
                routingContext.response().sendFile("dist/index.html");
            });
        }

        int[] errors = {550, 500, 422, 405, 404, 403, 400};
        for (int error : errors) {
            router.errorHandler(error, (routingContext) -> { new ErrorController(routingContext); });
        }

        int port=8080;
        String portstring = System.getProperty("port");
        if (portstring != null) {
            try {
                port = Integer.parseInt(portstring);
            } catch (NumberFormatException e) {
                System.out.println("given port: " + portstring + " is not valid. Using port 8080");
                port = 8080;
            }
        }
        // Create the HTTP server and pass the "accept" method to the request handler.
        HttpServerOptions options = new HttpServerOptions();
        options.setMaxHeaderSize(1024 * 16);
        App.app.getVertx().createHttpServer(options)
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