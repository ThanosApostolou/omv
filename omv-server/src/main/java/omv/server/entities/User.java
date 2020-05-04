package omv.server.entities;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.ArrayList;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class User {
    public int userid = 0;
    public String email = "";
    public String username = "";
    public String password = "";
    public String salt = "";

    public User() {
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.salt = password;
    }

    public User(int userid, String email, String username, String password, String salt) {
        this.userid = userid;
        this.email = email;
        this.username = username;
        this.password = password;
        this.salt = salt;
    }

    public void create(String email, String username, String password) {
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
        user_jsonobject.put("salt", this.salt);
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
        user.salt = user_jsonobject.getString("salt");
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
    public Boolean comparePassword(String received_password) {
        if (received_password != null && received_password.equals(this.password)) {
            return true;
        } else {
            return false;
        }
    }
    public String calcSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salt.toString();
    }
}