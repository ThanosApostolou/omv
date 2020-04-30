package omv.server.db;

import java.util.ArrayList;

import io.vertx.core.Future;
import io.vertx.core.Promise;

import omv.server.models.User;

public class UserManager {
    public static Future<ArrayList<User>> select(String where) {
        Promise<ArrayList<User>> promise = Promise.promise();
        String myquery = "SELECT * FROM USERS";
        if (where != null && !where.isEmpty()) {
            myquery += " WHERE " + where;
        }
        /*WebServer.webserver.eventbus.request("DBManager", myquery, (ar1) -> {
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
        });*/
        return promise.future();
    }
}