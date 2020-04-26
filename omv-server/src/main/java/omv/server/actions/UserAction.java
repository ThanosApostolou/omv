package omv.server.actions;

import io.vertx.core.Promise;
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
		Action.controller.eventbus.request("selectUser", "", (ar1) -> {
			if (ar1.succeeded()) {
				this.body = JsonObject.mapFrom(ar1.result().body());
			} else {
				// TODO
			}
			this.end();
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