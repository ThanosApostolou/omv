package omv.server.actions;

import java.util.ArrayList;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import omv.server.services.Service;
import omv.server.App;
import omv.server.entities.User;

public class LoginAction {

	public Future<String> post(String email, String password) {
		Promise<String> promise = Promise.promise();
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
								JsonObject authinfo = new JsonObject().put("jwt", token);
								App.app.jwtmanager.provider.authenticate(authinfo, (ar3) -> {
									io.vertx.ext.auth.User authuser = ar3.result();
									App.debug(authuser.principal().encode());
								});
								promise.complete(token);
							} else {
								promise.fail("403::");
							}
						} else {
							promise.fail("403::");
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