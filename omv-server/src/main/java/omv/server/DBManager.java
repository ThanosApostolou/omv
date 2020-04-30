package omv.server;

import java.util.List;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.SqlConnection;

public class DBManager {
    PgPool client=null;

    public void start(Promise<Void> promise) {
        // default "postgres://omv:omv@localhost:5432/omvdb"
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
        MessageConsumer<String> DBManagerConsumer = App.app.eventbus.consumer("DBManager");
        DBManagerConsumer.handler((message) -> {
            this.executeQuery(message);
        });
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

    public void executeQuery(Message<String> message) {
        System.out.println(message.headers().get("action"));
            String myquery = message.body();
            this.connect().onComplete((ar1) -> {
                JsonObject reply_message = new JsonObject();
                if (ar1.succeeded()) {
                    SqlConnection conn = ar1.result();
                    conn.query(myquery).execute((ar2) -> {
                        if (ar2.succeeded()) {
                            System.out.println("Myquery succeded");
                            RowSet<Row> result = ar2.result();
                            List<String> columns = result.columnsNames();
                            JsonArray data = new JsonArray();
                            result.forEach((row) -> {
                                JsonObject item = new JsonObject();
                                columns.forEach((column) -> {
                                    item.put(column, row.getValue(column));
                                });
                                data.add(item);
                            });
                            reply_message.put("succeded", ar2.succeeded());
                            reply_message.put("affected_rows", result.rowCount());
                            reply_message.put("data", data);
                            message.reply(reply_message);
                        } else {
                            reply_message.put("succeded", ar2.succeeded());
                            reply_message.put("cause_message", ar2.cause().getMessage());
                            System.out.println("Myquery failed");
                            //throw new Exception(ar2.cause());
                            message.reply(reply_message);
                        }
                        conn.close();
                    });
                } else {
                    reply_message.put("succeded", ar1.succeeded());
                    reply_message.put("cause_message", ar1.cause().getMessage());
                    message.reply(reply_message);
                }
            });
    }

}