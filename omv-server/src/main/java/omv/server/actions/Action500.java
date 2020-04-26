package omv.server.actions;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class Action500 extends Action {

	public Action500(RoutingContext routingContext) {
		super(routingContext);
		Throwable failure = routingContext.failure();

		this.contentType="application/json";
		this.body = new JsonObject();
		this.statusCode=500;
		this.body.put("message", "Internal Server Error");
		this.body.put("path", routingContext.normalisedPath());
		this.body.put("timestamp", System.currentTimeMillis());
		if (failure != null) {
			JsonArray stackTrace = new JsonArray();
			for (StackTraceElement line : failure.getStackTrace()) {
				stackTrace.add(line.toString());
			}
			this.body.put("stacktrace", stackTrace);
			System.err.println("Handling failure");
			failure.printStackTrace();
		}
		this.end();
	}

}