package omv.server.actions;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class Action404 extends Action {

	public Action404(RoutingContext routingContext) {
		super(routingContext);
		this.statusCode=404;
		this.body.put("message", "Requested URL not found. All api URLs start with /api/");
		this.body.put("path", routingContext.normalisedPath());
		this.end();
	}

}