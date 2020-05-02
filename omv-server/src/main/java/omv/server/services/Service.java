package omv.server.services;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.sqlclient.SqlConnection;
import omv.server.App;

public class Service {
    SqlConnection conn;
    public UserService userservice;

    public Future<Void> start() {
        Promise<Void> promise = Promise.promise();
        App.app.dbmanager.connect().onComplete((ar) -> {
            if (ar.succeeded()) {
                this.conn = ar.result();
                userservice = new UserService(this);
                promise.complete();
            } else {
                promise.fail(ar.cause());
            }
        });
        return promise.future();
    }

    public void close() {
        this.conn.close();
    }
}