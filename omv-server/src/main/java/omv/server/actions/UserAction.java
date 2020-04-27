package omv.server.actions;

import java.util.ArrayList;

import io.vertx.core.Promise;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import omv.server.models.User;

public class UserAction extends Action {

	public UserAction(RoutingContext routingContext) {
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
		Promise<ArrayList<User>> selectpromise = Promise.promise();
		User.select(selectpromise);
		selectpromise.future().onComplete(ar -> {
			if (ar.succeeded()) {
				this.body.put("users", User.toJsonArray(ar.result()));
				this.end();
			} else {
				this.routingContext.fail(550, ar.cause());
			}
		});
	}

	public void POST() {
		String email = this.request.getString("email");
		String name = this.request.getString("name");
		String password = this.request.getString("password");
		User newuser = new User();
		newuser.init(email, name, password);
		if (newuser.isValid()) {
			Action.controller.eventbus.request("executeQuery", newuser.insertQuery(), (ar1) -> {
                if (ar1.succeeded()) {
					System.out.println("Received reply: " + ar1.result().body());
					this.body.put("dbresult", ar1.result().body());
                } else {
					this.body.put("dbresult", ar1.result().body());
				}
				this.end();
            });
		}
	}

}