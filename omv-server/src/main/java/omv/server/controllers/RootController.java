package omv.server.controllers;

import io.vertx.ext.web.RoutingContext;

import omv.server.WebServer;

public class RootController {
	RtxManager rtxmanager;

	public RootController(RoutingContext rtx) {
		this.rtxmanager = new RtxManager(rtx);
	}

	public void GET() {
		this.rtxmanager.replybody.put("start_datetime", WebServer.webserver.runtime.start_datetime);
		this.rtxmanager.sendResponse();
	}
}