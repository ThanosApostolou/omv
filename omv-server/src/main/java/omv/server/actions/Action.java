package omv.server.actions;

import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class Action {
    public static EventBus eventbus;

    public String contentType;
	public int statusCode;
    public JsonObject body;
    JsonObject request;
	HttpServerResponse response;

    Action(RoutingContext routingContext) {
        this.request = routingContext.getBodyAsJson();
	    this.response = routingContext.response();
    }

    public void end() {
        this.response.putHeader("content-type", this.contentType)
                    .setStatusCode(this.statusCode)
                    .end(this.body.encode());
    }

}