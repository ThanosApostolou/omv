package omv.server.models;

import java.util.ArrayList;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class User {
    public int id;
    public String email="";
    public String username="";
    public String password="";

    public User() {}

    public void init (String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
    public static String inputError(String email, String username, String password1, String password2) {
        String error = "";
        if (email == null || email.length() < 6 || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            error = "422::Email must be more than 5 characters with correct format";
        } else if (username == null || username.length() < 6) {
            error = "422::Username must be more than 5 characters";
        } else if (password1 == null || password1.length() < 6 || !password1.equals(password2)) {
            error = "422::Passwords must match and be more than 5 characters";
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
}