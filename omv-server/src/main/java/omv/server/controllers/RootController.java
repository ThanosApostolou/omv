package omv.server.controllers;

import io.vertx.ext.web.RoutingContext;

import omv.server.WebServer;

public class RootController extends Controller {

	public RootController(RoutingContext routingContext) {
		super(routingContext);
	}

	public void GET() {
		this.statusCode = 200;
		this.body.put("start_datetime", WebServer.webserver.runtime.start_datetime);
		this.end();
	}
}