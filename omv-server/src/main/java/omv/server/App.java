package omv.server;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.file.FileSystem;

public class App extends AbstractVerticle {
    public static App app;
    public String VERTXWEB_ENVIRONMENT;
    public DBManager dbmanager=null;
    public WebServer webserver=null;
    public FileSystem fs=null;
    public Runtime runtime=null;
    public OWLOntologyManager owlmanager=null;
    public JWTManager jwtmanager=null;

    @Override
    public void start(Promise<Void> promise) {
        App.app = this;
        this.VERTXWEB_ENVIRONMENT = System.getenv("VERTXWEB_ENVIRONMENT");
        this.dbmanager = new DBManager();
        this.webserver = new WebServer();
        this.fs = vertx.fileSystem();
        this.runtime = new Runtime();
        this.owlmanager = OWLManager.createOWLOntologyManager();
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
}