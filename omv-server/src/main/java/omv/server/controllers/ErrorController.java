package omv.server.controllers;

import io.vertx.ext.web.RoutingContext;
import omv.server.App;

public class ErrorController {
	RtxManager rtxmanager;

	public ErrorController(RoutingContext rtx) {
		rtxmanager = new RtxManager(rtx);
		Throwable failure = rtx.failure();
		this.rtxmanager.responsebody.put("path", rtx.normalisedPath());

		switch(this.rtxmanager.statusCode) {
			case 550:
			case 500: // Internal Server Error
				this.rtxmanager.responsebody.put("message", failure.getMessage());
				this.rtxmanager.responsebody.put("timestamp", System.currentTimeMillis());
				if (failure != null) {
					App.app.logger.error(failure.getMessage());
					failure.printStackTrace();
				}
				break;
			case 422: // Unaccceptable Input
				App.app.logger.info("Error Response: " + failure.getMessage());
				this.rtxmanager.responsebody.put("message", failure.getMessage());
				break;
			case 405: //If a route matches the path but don’t match the HTTP Method
				App.app.logger.info("Error Response: Requested URL exists, but method is not allowed");
				this.rtxmanager.responsebody.put("message", "Requested URL exists, but method is not allowed");
				break;
			case 404: // If no route matches the path
				App.app.logger.info("Error Response: Requested URL not found. All api URLs start with /api/");
				this.rtxmanager.responsebody.put("message", "Requested URL not found. All api URLs start with /api/");
				break;
			case 403: // Permission Denied
				App.app.logger.info("Error Response: Permission Denied");
				this.rtxmanager.responsebody.put("message", "Permission Denied");
				break;
			case 400: // If a route matches the path and the method but It can’t accept an empty body
				App.app.logger.info("Error Response: Bad Request");
				this.rtxmanager.responsebody.put("message", "Bad Request");
				break;
			/*
			case 406 If a route matches the path and the method but It can’t provide a response with a content type matching Accept header
			case 415 If a route matches the path and the method but It can’t accept the Content-type
			*/
		}
		this.rtxmanager.sendResponse();
	}

}