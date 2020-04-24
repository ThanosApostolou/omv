package omv.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlConnection;

public class Database extends AbstractVerticle {
    MySQLPool client;

    public Database() {
        this.client=null;
    }

    public void start(Promise<Void> promise) {
        MySQLConnectOptions connectOptions = new MySQLConnectOptions()
            .setPort(3306)
            .setHost("localhost")
            .setDatabase("omvdb")
            .setUser("omv")
            .setPassword("omv");
        PoolOptions poolOptions = new PoolOptions()
            .setMaxSize(30);
        this.client = MySQLPool.pool(vertx, connectOptions, poolOptions);

        this.client.getConnection((ar1) -> {
            if (ar1.succeeded()) {
                System.out.println("Connected");
                // Obtain our connection
                SqlConnection conn = ar1.result();

                // All operations execute on the same connection
                conn.query("SELECT * FROM users WHERE id='julien'")
                    .execute(ar2 -> {
                        if (ar2.succeeded()) {
                        conn
                            .query("SELECT * FROM users WHERE id='emad'")
                            .execute(ar3 -> {
                            // Release the connection to the pool
                            conn.close();
                        });
                        } else {
                            // Release the connection to the pool
                            conn.close();
                        }
                    });
            } else {
              System.out.println("Could not connect: " + ar1.cause().getMessage());
            }
          });

        System.out.println("test");
        promise.complete();
    }

}