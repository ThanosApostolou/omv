package omv.server.actions;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class Action403 extends Action {

	public Action403(RoutingContext routingContext) {
		super(routingContext);
		this.statusCode=403;
		this.body.put("message", "Permission Denied");
		this.body.put("path", routingContext.normalisedPath());
		this.end();
	}

}