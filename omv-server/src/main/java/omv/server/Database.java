package omv.server;

import java.util.List;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.SqlConnection;

public class Database extends AbstractVerticle {
    MySQLPool client=null;
    EventBus eventbus=null;

    public void start(Promise<Void> promise) {
        MySQLConnectOptions connectOptions = new MySQLConnectOptions().setPort(3306)
                                                                    .setHost("localhost")
                                                                    .setDatabase("omvdb")
                                                                    .setUser("omv")
                                                                    .setPassword("omv")
                                                                    .setCharset("utf8mb4");
        PoolOptions poolOptions = new PoolOptions().setMaxSize(30);
        this.client = MySQLPool.pool(vertx, connectOptions, poolOptions);

        this.eventbus = vertx.eventBus();
        MessageConsumer<String> DBManagerConsumer = this.eventbus.consumer("DBManager");
        DBManagerConsumer.handler((message) -> {
            this.executeQuery(message);
        });
        promise.complete();
    }

    public void executeQuery(Message<String> message) {
        System.out.println(message.headers().get("action"));
            String myquery = message.body();
            this.client.getConnection((ar1) -> {
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