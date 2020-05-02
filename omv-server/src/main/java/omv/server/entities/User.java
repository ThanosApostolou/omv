package omv.server.entities;

import java.util.ArrayList;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class User {
    public int userid;
    public String email="";
    public String username="";
    public String password="";

    public User() {}

    public void create (String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public JsonObject toJsonObject() {
        JsonObject user_jsonobject = new JsonObject();
        user_jsonobject.put("userid", this.userid);
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
        user.userid = user_jsonobject.getInteger("userid");
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
}