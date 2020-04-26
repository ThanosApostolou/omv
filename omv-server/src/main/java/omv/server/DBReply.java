package omv.server;

import io.vertx.core.json.JsonObject;

public class DBReply {
    boolean connection_success;
    boolean query_success;
    JsonObject data;

}