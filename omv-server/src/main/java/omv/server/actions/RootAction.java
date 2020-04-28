package omv.server.actions;

import io.vertx.ext.web.RoutingContext;

import omv.server.Controller;

public class RootAction extends Action {

	public RootAction(RoutingContext routingContext) {
		super(routingContext);
	}

	public void GET() {
		this.statusCode = 200;
		this.body.put("start_datetime", Controller.controller.runtime.start_datetime);
		this.end();
	}
}