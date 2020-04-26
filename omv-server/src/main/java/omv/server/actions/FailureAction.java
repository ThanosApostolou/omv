package omv.server.actions;

import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import omv.server.models.User;

public class FailureAction extends Action {

	public FailureAction(RoutingContext routingContext) {
		super(routingContext);
		this.contentType="application/json";
		this.body = new JsonObject();
		if (routingContext.statusCode() == 404) {
			this.statusCode=404;
			this.body.put("message", "Requested URL not found. All api URLs start with /api/");
		} else if (routingContext.statusCode() == 402) {
			this.statusCode=402;
			this.body.put("message", "Permission Denied");
		}
		this.end();
	}

}