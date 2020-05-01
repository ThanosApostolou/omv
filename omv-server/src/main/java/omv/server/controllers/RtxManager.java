package omv.server.controllers;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class RtxManager {
    RoutingContext rtx;
    public String contentType;
	public int statusCode;
    public JsonObject responsebody;

    RtxManager(RoutingContext rtx) {
        this.rtx = rtx;
        this.contentType = "application/json";
        if (rtx.statusCode() > 0) { // meaning that it's send from a fail
            this.statusCode=this.rtx.statusCode();
        } else {
            this.statusCode = 200;
        }
        this.responsebody = new JsonObject();
    }

    public void sendResponse() {
        this.rtx.response()
                .putHeader("content-type", this.contentType)
                .setStatusCode(this.statusCode)
                .end(this.responsebody.encode());
    }

    public void fail(Throwable cause) {
        String[] code_strings = cause.getMessage().split("::");
        int code = 500;
        if (code_strings.length > 0 && code_strings[0].length() == 3) {
            try {
                code = Integer.parseInt(code_strings[0]);
            } catch (NumberFormatException e) {
                code = 500;
            }
            if (code_strings.length > 1) {
                cause = new Throwable(code_strings[1]);
            }
        }
        this.rtx.fail(code, cause);
    }
}