package omv.server.services;

import java.util.ArrayList;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
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
            this.selectByEmail(email).onComplete((ar) -> {
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

    public Future<Integer> authenticate(String token) {
        Promise<Integer> promise = Promise.promise();
        JsonObject authinfo = new JsonObject().put("jwt", token);
        App.app.jwtmanager.provider.authenticate(authinfo, (ar) -> {
            if (ar.succeeded()) {
                io.vertx.ext.auth.User authuser = ar.result();
                Integer userid = authuser.principal().getInteger("userid");
                App.debug("userid: " + userid);
                promise.complete(userid);
            } else {
                promise.fail("422::Invalid authentication token");
            }
        });
        return promise.future();
    }

    public Future<ArrayList<User>> selectSearch(String search) {
        return this.select("email LIKE '%"+search+"%' OR username LIKE '%"+search+"%'");
    }
    public Future<ArrayList<User>> selectByEmail(String email) {
        return this.select("email='"+email+"'");
    }
    public Future<ArrayList<User>> selectById(int id) {
        return this.select("id='"+id+"'");
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
                    User user = new User(row.getInteger("userid"),
                                         row.getString("email"),
                                         row.getString("username"),
                                         row.getString("password"),
                                         row.getString("salt"));
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
        String myquery = "INSERT INTO USERS (email, username, password, salt) " +
                         "VALUES ('"+user.email+"', '"+user.username+"', '"+user.password+"', '"+user.salt+"')";
        App.app.dbmanager.query(this.service.conn, myquery).onComplete((ar) -> {
            if (ar.succeeded()) {
                promise.complete();
            } else {
                promise.fail(ar.cause());
            }
        });
        return promise.future();
    }

    public Future<Void> deleteByEmail(String email) {
        return this.delete("email='"+email+"'");
    }
    public Future<Void> deleteById(int userid) {
        return this.delete("userid='"+userid+"'");
    }
    public Future<Void> delete(String where) {
        Promise<Void> promise = Promise.promise();
        String myquery = "DELETE FROM USERS WHERE " + where;
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