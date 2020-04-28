package omv.server.actions;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import omv.server.Controller;

public abstract class Action {
    public static Controller controller;

    public String contentType;
	public int statusCode;
    public JsonObject body;
    RoutingContext routingContext;
    JsonObject request;
	HttpServerResponse response;

    Action(RoutingContext routingContext) {
        this.routingContext = routingContext;
        this.contentType = "application/json";
        this.statusCode = 200;
        this.body = new JsonObject();
        this.request = routingContext.getBodyAsJson();
	    this.response = routingContext.response();
    }

    public void end() {
        this.response.putHeader("content-type", this.contentType)
                    .setStatusCode(this.statusCode)
                    .end(this.body.encode());
    }

}