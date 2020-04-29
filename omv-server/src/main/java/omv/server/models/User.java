package omv.server.models;

import java.util.ArrayList;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import omv.server.Controller;
import omv.server.MyError;

public class User extends Model{
    public int id;
    public String email="";
    public String username="";
    public String password="";

    public User() {}

    public void init (String email, String name, String password) {
        this.email = email;
        this.username = name;
        this.password = password;
    }
    public static MyError inputError(String email, String name, String password) {
        MyError error= new MyError();
        if (email == null || email.length() < 3) {
            error.hasError = true;
            error.cause = "Email must be more than 3 characters";
        }
        if (error.hasError) {
            error.status = 422;
        }
        return error;
    }

    public JsonObject toJsonObject() {
        JsonObject user_jsonobject = new JsonObject();
        user_jsonobject.put("id", this.id);
        user_jsonobject.put("email", this.email);
        user_jsonobject.put("username", this.username);
        user_jsonobject.put("password", this.password);
        return user_jsonobject;
    }
    public String toString() {
        return this.toJsonObject().encode();
    }
    public static JsonArray toJsonArray(ArrayList<User> users) {
        JsonArray user_jsonarray = new JsonArray();
        users.forEach((User user) -> {
            JsonObject user_jsonobject = user.toJsonObject();
            user_jsonarray.add(user_jsonobject);
        });
        return user_jsonarray;
    }
    public static User fromJsonObject(JsonObject user_jsonobject) {
        User user = new User();
        user.id = user_jsonobject.getInteger("id");
        user.email = user_jsonobject.getString("email");
        user.username = user_jsonobject.getString("username");
        user.password = user_jsonobject.getString("password");
        return user;
    }
    public static ArrayList<User> fromJsonArray(JsonArray users_jsonarray) {
        ArrayList<User> users = new ArrayList<User>();
        users_jsonarray.forEach((Object user_object) -> {
            JsonObject user_jsonobject = JsonObject.mapFrom(user_object);
            User user = User.fromJsonObject(user_jsonobject);
            users.add(user);
        });
        return users;
    }

    public void insert(Promise<Boolean> promise) {
        String myquery = "INSERT INTO USER (email, username, password) VALUES ('"+this.email+"', '"+this.username+"', '"+this.password+"')";
        Controller.controller.eventbus.request("DBManager", myquery, (ar1) -> {
			if (ar1.succeeded()) {
                JsonObject body = JsonObject.mapFrom(ar1.result().body());
                if (body.getBoolean("succeded")) {
                    promise.complete(true);
                } else {
                    promise.fail(new RuntimeException(body.getString("cause_message")));
                }
			} else {
                throw new RuntimeException("Error connecting to Database Verticle");
			}
        });
    }

    public static Future<ArrayList<User>> select(String where) {
        Promise<ArrayList<User>> promise = Promise.promise();
        String myquery = "SELECT * FROM USER";
        if (where != null && !where.isEmpty()) {
            myquery += " WHERE " + where;
        }
        Controller.controller.eventbus.request("DBManager", myquery, (ar1) -> {
			if (ar1.succeeded()) {
                JsonObject body = JsonObject.mapFrom(ar1.result().body());
                if (body.getBoolean("succeded")) {
                    JsonArray users_jsonarray = body.getJsonArray("data");
                    ArrayList<User> users = User.fromJsonArray(users_jsonarray);
                    promise.complete(users);
                } else {
                    promise.fail(new RuntimeException(body.getString("cause_message")));
                }
			} else {
                promise.fail(new RuntimeException("Error connecting to Database Verticle"));
            }
        });
        return promise.future();
    }
}