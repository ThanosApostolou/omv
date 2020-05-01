package omv.server.controllers;

import io.vertx.ext.web.RoutingContext;
import omv.server.App;

public class RootController {
	RtxManager rtxmanager;

	public RootController(RoutingContext rtx) {
		this.rtxmanager = new RtxManager(rtx);
	}

	public void GET() {
		this.rtxmanager.responsebody.put("name", App.app.runtime.name);
		this.rtxmanager.responsebody.put("version", App.app.runtime.version);
		this.rtxmanager.responsebody.put("start_datetime", App.app.runtime.start_datetime);
		this.rtxmanager.sendResponse();
	}
}