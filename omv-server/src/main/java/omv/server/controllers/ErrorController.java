package omv.server.controllers;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class ErrorController extends Controller {

	public ErrorController(RoutingContext routingContext) {
		super(routingContext);
		Throwable failure = routingContext.failure();
		this.contentType="application/json";
		this.statusCode=this.routingContext.statusCode();
		this.body = new JsonObject();
		this.body.put("path", routingContext.normalisedPath());

		switch(this.statusCode) {
			case 550:
				this.body.put("message", failure.getMessage());
				this.body.put("timestamp", System.currentTimeMillis());
				break;
			case 500: // Internal Server Error
				this.body.put("message", failure.getMessage());
				this.body.put("timestamp", System.currentTimeMillis());
				break;
			case 422: // Unaccceptable Input
				this.body.put("message", "test");//failure.getMessage());
				break;
			case 404: // If no route matches the path
				this.body.put("message", "Requested URL not found. All api URLs start with /api/");
				break;
			case 403: // Permission Denied
				this.body.put("message", "Permission Denied");
				break;
			/*
			case 405 If a route matches the path but don’t match the HTTP Method
			case 406 If a route matches the path and the method but It can’t provide a response with a content type matching Accept header
			case 415 If a route matches the path and the method but It can’t accept the Content-type
			case 400 If a route matches the path and the method but It can’t accept an empty body
			*/
		}

		if (failure != null) {
			System.out.println(failure.getMessage());
			JsonArray stackTrace = new JsonArray();
			for (StackTraceElement line : failure.getStackTrace()) {
				stackTrace.add(line.toString());
			}
			this.body.put("stacktrace", stackTrace);
			failure.printStackTrace();
		}
		this.end();
	}

}