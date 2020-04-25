package omv.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
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
                                                                    .setPassword("omv");
        PoolOptions poolOptions = new PoolOptions().setMaxSize(30);
        this.client = MySQLPool.pool(vertx, connectOptions, poolOptions);

        this.eventbus = vertx.eventBus();
        MessageConsumer<String> executeQueryConsumer = this.eventbus.consumer("executeQuery");
        executeQueryConsumer.handler((message) -> {
            this.executeQuery(message);
        });
        promise.complete();
    }

    public void executeQuery(Message<String> message) {
        String myquery = message.body();
        this.client.getConnection((ar1) -> {
            if (ar1.succeeded()) {
                System.out.println("Connected");
                SqlConnection conn = ar1.result();
                conn.query(myquery)
                    .execute((ar2) -> {
                        if (ar2.succeeded()) {
                            System.out.println("Myquery succeded");
                            RowSet<Row> result = ar2.result();
                            result.forEach((row) -> {
                                System.out.println(row.getString("email"));
                            });
                            message.reply("ok");
                        } else {
                            System.out.println("Myquery failed");
                        }
                        conn.close();
                    });
            } else {
              System.out.println("Could not connect: " + ar1.cause().getMessage());
            }
        });
    }

}