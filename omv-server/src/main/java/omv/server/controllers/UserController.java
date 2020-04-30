package omv.server.controllers;

import java.util.ArrayList;

import io.vertx.core.Promise;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import omv.server.MyError;
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
		DeliveryOptions options = new DeliveryOptions();
		options.addHeader("action", "select");
		JsonObject message = new JsonObject();
		message.put("table", "USER");
		User.select(null).onComplete((ar) -> {
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
		String email = this.request.getString("email");
		String username = this.request.getString("username");
		String password = this.request.getString("password");
		MyError error = User.inputError(email, username, password);
		if (!error.hasError) {
			User newuser = new User();
			newuser.init(email, username, password);
			Promise<Boolean> promise = Promise.promise();
			newuser.insert(promise);
			promise.future().onComplete(ar -> {
				if (ar.succeeded()) {
					this.end();
				} else {
					this.routingContext.fail(550, ar.cause());
				}
			});
		} else {
			Throwable test = new Throwable("test", new Throwable("test"));
			this.routingContext.fail(422, test);
		}
	}

}