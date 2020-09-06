package omv.server.actions;

import java.util.ArrayList;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import omv.server.services.Service;
import omv.server.App;
import omv.server.entities.User;

public class LoginAction {

	public Future<JsonObject> post(String email, String password) {
		Promise<JsonObject> promise = Promise.promise();
		Service service = new Service();
		service.startConnection().onComplete((ar1) -> {
			if (ar1.succeeded()) {
				service.userservice.selectByEmail(email).onComplete((ar2) -> {
					if (ar2.succeeded()) {
						ArrayList<User> users = ar2.result();
						if (users != null && users.size() > 0) {
							User user = users.get(0);
							if (user.comparePassword(password)) {
								JsonObject principal = new JsonObject().put("userid", user.userid);
								String token = App.app.jwtmanager.provider.generateToken(principal);
								JsonObject logininfo = new JsonObject();
								logininfo.put("email", user.email)
										 .put("username", user.username)
										 .put("token", token);
								promise.complete(logininfo);
							} else {
								promise.fail("422::Email or password are incorect");
							}
						} else {
							promise.fail("422::Email or password are incorect");
						}
					} else {
						promise.fail(ar2.cause());
					}
				});

			} else {
				promise.fail(ar1.cause());
			}
		});
		return promise.future();
	}

}