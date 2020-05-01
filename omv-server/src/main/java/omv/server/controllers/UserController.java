package omv.server.controllers;

import java.util.ArrayList;
import io.vertx.ext.web.RoutingContext;
import omv.server.actions.UserAction;
import omv.server.models.User;

public class UserController {
	RtxManager rtxmanager;

	public UserController(RoutingContext rtx) {
		rtxmanager = new RtxManager(rtx);
	}

	public void GET() {
		this.rtxmanager.contentType="application/json";
		new UserAction().get(null).onComplete((ar) -> {
			if (ar.succeeded()) {
				ArrayList<User> users = ar.result();
				this.rtxmanager.replybody.put("users", User.toJsonArray(users));
				this.rtxmanager.sendResponse();
			} else {
				this.rtxmanager.fail(ar.cause());
			}
		});
	}

	public void POST() {
		if (this.rtxmanager.requestbody == null) {
			this.rtxmanager.fail(new Throwable("400::"));
			return;
		}
		String email = this.rtxmanager.requestbody.getString("email");
		String username = this.rtxmanager.requestbody.getString("username");
		String password = this.rtxmanager.requestbody.getString("password");
		new UserAction().post(email, username, password).onComplete((ar) -> {
			if (ar.succeeded()) {
				this.rtxmanager.requestbody.put("success", true);
				this.rtxmanager.sendResponse();
			} else {
				this.rtxmanager.fail(ar.cause());
			}
		});
	}

}