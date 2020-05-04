package omv.server.controllers;

import java.util.ArrayList;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import omv.server.actions.LoginAction;
import omv.server.entities.User;

public class LoginController {
	RtxManager rtxmanager;

	public LoginController(RoutingContext rtx) {
		rtxmanager = new RtxManager(rtx);
	}

	public void post() {
		JsonObject requestbody;
		String email;
		String password;
		try {
			requestbody = this.rtxmanager.rtx.getBodyAsJson();
			email = requestbody.getString("email");
			password = requestbody.getString("password");
			if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
				this.rtxmanager.fail(new Throwable("400::"));
			}
		} catch (RuntimeException e) {
			this.rtxmanager.fail(new Throwable("400::"));
			return;
		}
		new LoginAction().post(email, password).onComplete((ar) -> {
			if (ar.succeeded()) {
				String token = ar.result();
				this.rtxmanager.responsebody.put("token", token);
				this.rtxmanager.sendResponse();
			} else {
				this.rtxmanager.fail(ar.cause());
			}
		});
	}

}