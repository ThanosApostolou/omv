package omv.server.services;

import java.util.ArrayList;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import omv.server.App;
import omv.server.entities.User;

public class UserService {
    Service service;

    public UserService(Service service) {
        this.service = service;
    }

    public Future<User> createFromInput(String email, String username, String password1, String password2) {
        Promise<User> promise = Promise.promise();
        if (email == null || email.length() < 6 || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            promise.fail("422::Email must be more than 5 characters with correct format");
        } else if (username == null || username.length() < 6) {
            promise.fail("422::Username must be more than 5 characters");
        } else if (password1 == null || password1.length() < 6 || !password1.equals(password2)) {
            promise.fail("422::Passwords must match and be more than 5 characters");
        } else {
            this.select("email='"+email+"'").onComplete((ar) -> {
                if (ar.succeeded()) {
                    ArrayList<User> users = ar.result();
                    if (users.size() > 0) {
                        promise.fail("422::Email already exists");
                    } else {
                        User user = new User(email, username, password1);
                        promise.complete(user);
                    }
                } else {
                    promise.fail(ar.cause());
                }
            });
        }
        return promise.future();
    }

    public Future<ArrayList<User>> select(String where) {
        Promise<ArrayList<User>> promise = Promise.promise();
        String myquery = "SELECT * FROM USERS";
        if (where != null && !where.isEmpty()) {
            myquery += " WHERE " + where;
        }
        App.app.dbmanager.query(this.service.conn, myquery)
            .onSuccess((RowSet<Row> rows) -> {
                ArrayList<User> users = new ArrayList<User>();
                rows.forEach((row) -> {
                    User user = new User();
                    user.userid = row.getInteger("userid");
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
        String myquery = "INSERT INTO USERS (email, username, password) " +
                         "VALUES ('"+user.email+"', '"+user.username+"', '"+user.password+"')";
        App.app.dbmanager.query(this.service.conn, myquery).onComplete((ar) -> {
            if (ar.succeeded()) {
                promise.complete();
            } else {
                promise.fail(ar.cause());
            }
        });
        return promise.future();
    }
}