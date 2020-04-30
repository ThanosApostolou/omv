package omv.server.controllers;

import java.util.ArrayList;

import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import omv.server.MyError;
import omv.server.actions.UserAction;
import omv.server.models.User;

public class UserController extends Controller {

	public UserController(RoutingContext routingContext) {
		super(routingContext);
		this.contentType="application/json";
		this.statusCode=200;
		this.body = new JsonObject();
	}

	public void GET() {
		this.statusCode = 200;
		new UserAction().get(null).onComplete((ar) -> {
			if (ar.succeeded()) {
				ArrayList<User> users = ar.result();
				this.body.put("users", User.toJsonArray(users));
				this.end();
			} else {
				this.routingContext.fail(550, ar.cause());
			}
		});
	}

	public void POST() {
		if (this.request == null) {
			this.routingContext.fail(400);
			return;
		}
		String email = this.request.getString("email");
		String username = this.request.getString("username");
		String password = this.request.getString("password");
		System.out.println(email);
		if (email.isEmpty()) {
			System.out.println("nulll email");
		}
		new UserAction().post(email, username, password).onComplete((ar) -> {
			if (ar.succeeded()) {
				this.body.put("success", true);
				this.end();
			} else {
				this.routingContext.fail(550, ar.cause());
			}
		});
	}

}