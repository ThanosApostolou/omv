package omv.server;

import java.io.IOException;
import java.io.InputStream;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.file.FileSystem;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class App extends AbstractVerticle {
    public static App app;
    public Logger logger = null;
    public String VERTXWEB_ENVIRONMENT;
    public DBManager dbmanager = null;
    public WebServer webserver = null;
    public FileSystem fs = null;
    public Runtime runtime = null;
    public JWTManager jwtmanager = null;

    @Override
    public void start(Promise<Void> promise) throws IOException {
        App.app = this;
        System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory");
        LoggerFactory.initialise();
        this.logger = LoggerFactory.getLogger(App.class);
        this.logger.info("info");
        this.VERTXWEB_ENVIRONMENT = System.getenv("VERTXWEB_ENVIRONMENT");
        this.dbmanager = new DBManager();
        this.webserver = new WebServer();
        this.fs = vertx.fileSystem();
        this.runtime = new Runtime();
        this.jwtmanager = new JWTManager();

        Promise<Void> databasePromise = Promise.promise();
        this.dbmanager.start(databasePromise);
        databasePromise.future().compose((ar) -> {
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

    public static void debug(String message) {
        if (App.app.VERTXWEB_ENVIRONMENT != null) {
            if (App.app.VERTXWEB_ENVIRONMENT.equals("dev")) {
                System.out.println(message);
            }
        }
    }

    public static InputStream readResourceStream (String source) throws IOException {
        InputStream stream = App.app.getClass().getClassLoader().getResourceAsStream(source);
        return stream;
    }
    public static String readResourcePath (String source) {
        String path = App.app.getClass().getClassLoader().getResource(source).getPath();
        return path;
    }
}