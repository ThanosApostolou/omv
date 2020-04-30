package omv.server.services;

import java.util.ArrayList;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlConnection;
import omv.server.App;
import omv.server.models.User;

public class UserService {
    SqlConnection conn;

    public UserService(SqlConnection conn) {
        this.conn = conn;
    }

    public Future<ArrayList<User>> select(String where) {
        Promise<ArrayList<User>> promise = Promise.promise();
        String myquery = "SELECT * FROM USERS";
        if (where != null && !where.isEmpty()) {
            myquery += " WHERE " + where;
        }
        App.app.dbmanager.query(this.conn, myquery)
            .onSuccess((RowSet<Row> rows) -> {
                ArrayList<User> users = new ArrayList<User>();
                rows.forEach((row) -> {
                    User user = new User();
                    user.id = row.getInteger("id");
                    user.email = row.getString("email");
                    user.username = row.getString("username");
                    user.password = row.getString("password");
                    users.add(user);
                });
                promise.complete(users);
            }).onFailure((cause) -> {
                promise.fail(cause);
            });
        return promise.future();
    }

    public Future<Void> insert(User user) {
        Promise<Void> promise = Promise.promise();
        String myquery = "INSERT INTO USERS (email, username, password) VALUES ('"+user.email+"', '"+user.username+"', '"+user.password+"')";
        App.app.dbmanager.query(this.conn, myquery)
            .onSuccess((RowSet<Row> rows) -> {
                promise.complete();
            }).onFailure((cause) -> {
                promise.fail(cause);
            });
        return promise.future();
    }
}