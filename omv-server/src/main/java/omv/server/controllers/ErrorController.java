package omv.server.controllers;

import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.RoutingContext;

public class ErrorController {
	RtxManager rtxmanager;

	public ErrorController(RoutingContext rtx) {
		rtxmanager = new RtxManager(rtx);
		Throwable failure = rtx.failure();
		this.rtxmanager.replybody.put("path", rtx.normalisedPath());

		switch(this.rtxmanager.statusCode) {
			case 550:
			case 500: // Internal Server Error
				this.rtxmanager.replybody.put("message", failure.getMessage());
				this.rtxmanager.replybody.put("timestamp", System.currentTimeMillis());
				if (failure != null) {
					System.out.println(failure.getMessage());
					JsonArray stackTrace = new JsonArray();
					for (StackTraceElement line : failure.getStackTrace()) {
						stackTrace.add(line.toString());
					}
					this.rtxmanager.replybody.put("stacktrace", stackTrace);
					failure.printStackTrace();
				}
				break;
			case 422: // Unaccceptable Input
				this.rtxmanager.replybody.put("message", "test");//failure.getMessage());
				break;
			case 405: //If a route matches the path but don’t match the HTTP Method
				this.rtxmanager.replybody.put("message", "Requested URL exists, but method is not allowed");
				break;
			case 404: // If no route matches the path
				this.rtxmanager.replybody.put("message", "Requested URL not found. All api URLs start with /api/");
				break;
			case 403: // Permission Denied
				this.rtxmanager.replybody.put("message", "Permission Denied");
				break;
			case 400: // If a route matches the path and the method but It can’t accept an empty body
				this.rtxmanager.replybody.put("message", "Bad Request. Cannot accept an empty body");
				break;
			/*
			case 406 If a route matches the path and the method but It can’t provide a response with a content type matching Accept header
			case 415 If a route matches the path and the method but It can’t accept the Content-type
			*/
		}
		this.rtxmanager.sendResponse();
	}

}