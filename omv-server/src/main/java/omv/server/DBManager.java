package omv.server;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.SqlConnection;

public class DBManager {
    PgPool client=null;

    public void start(Promise<Void> promise) {
        String database_url = System.getenv("DATABASE_URL");
        String database;
        String user;
        int port;
        String password;
        String host;
        if (database_url == null) {
            database_url = "postgres://omv:omv@localhost:5432/omvdb";
            System.out.println("DATABASE_URL is not set. Using default value: " + database_url);
        }
        String[] parts = database_url.split("/");
        database = parts[3];
        parts = parts[2].split(":");
        user = parts[0];
        port = Integer.parseInt(parts[2]);
        parts = parts[1].split("@");
        password = parts[0];
        host = parts[1];

        PgConnectOptions connectOptions = new PgConnectOptions()
            .setPort(port)
            .setHost(host)
            .setDatabase(database)
            .setUser(user)
            .setPassword(password);
        PoolOptions poolOptions = new PoolOptions().setMaxSize(20);
        this.client = PgPool.pool(App.app.getVertx(), connectOptions, poolOptions);
        promise.complete();
    }

    public Future<SqlConnection> connect() {
        Promise<SqlConnection> promise = Promise.promise();
        this.client.getConnection((ar) -> {
            if (ar.succeeded()) {
                SqlConnection conn = ar.result();
                promise.complete(conn);
            } else {
                promise.fail(ar.cause());
            }
        });
        return promise.future();
    }

    public Future<RowSet<Row>> query(SqlConnection conn, String myquery) {
        Promise<RowSet<Row>> promise = Promise.promise();
        conn.query(myquery).execute((ar) -> {
            if (ar.succeeded()) {
                System.out.println("Myquery succeded");
                RowSet<Row> result = ar.result();
                promise.complete(result);
            } else {
                promise.fail(ar.cause());
            }
        });
        return promise.future();
    }
}