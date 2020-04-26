package omv.server.actions;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class RootAction extends Action {
	private String start_datetime;

	public RootAction(RoutingContext routingContext) {
		super(routingContext);
	}

	public void GET() {
		this.statusCode = 200;
		this.body.put("start_datetime", Action.controller.runtime.start_datetime);
		this.end();
	}
}