package omv.server;

import io.vertx.core.json.JsonObject;

public class MyError {
    public boolean hasError=false;
    public int status=200;
    public String cause=null;

    public static MyError fromJsonObject(JsonObject error_jsonobject) {
        MyError error = new MyError();
        error.hasError = error_jsonobject.getBoolean("hasError");
        error.status = error_jsonobject.getInteger("status");
        error.cause = error_jsonobject.getString("cause");
        return error;
    }
}