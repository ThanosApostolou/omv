package omv.server.controllers;

import java.util.ArrayList;
import io.vertx.core.json.JsonObject;
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
				this.rtxmanager.responsebody.put("users", User.toJsonArray(users));
				this.rtxmanager.sendResponse();
			} else {
				this.rtxmanager.fail(ar.cause());
			}
		});
	}

	public void POST() {
		JsonObject requestbody;
		String email;
		String username;
		String password1;
		String password2;
		try {
			requestbody = this.rtxmanager.rtx.getBodyAsJson();
			email = requestbody.getString("email");
			username = requestbody.getString("username");
			password1 = requestbody.getString("password1");
			password2 = requestbody.getString("password2");
		} catch (RuntimeException e) {
			this.rtxmanager.fail(new Throwable("400::"));
			return;
		}
		new UserAction().post(email, username, password1, password2).onComplete((ar) -> {
			if (ar.succeeded()) {
				this.rtxmanager.responsebody.put("success", true);
				this.rtxmanager.sendResponse();
			} else {
				this.rtxmanager.fail(ar.cause());
			}
		});
	}

}