package omv.server.actions;

import java.util.ArrayList;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import omv.server.models.User;
import omv.server.services.Service;

public class UserAction {

    public Future<ArrayList<User>> get(String search) {
		Promise<ArrayList<User>> promise = Promise.promise();
		Service service = new Service();
		service.start().onComplete((ar1) -> {
			if (ar1.succeeded()) {
				service.user().select(null).onComplete((ar2) -> {
					if (ar2.succeeded()) {
						ArrayList<User> users = ar2.result();
						promise.complete(users);
					} else {
						promise.fail(ar2.cause());
					}
					service.close();
				});
			} else {
				promise.fail(ar1.cause());
			}
		});
        return promise.future();
	}

	public Future<Boolean> post(String email, String username, String password1, String password2) {
		Promise<Boolean> promise = Promise.promise();
		String error = User.inputError(email, username, password1, password2);
		if (error != "") {
			promise.fail(error);
		} else {
			User newuser = new User();
			newuser.init(email, username, password1);
			Service service = new Service();
			service.start().onComplete((ar1) -> {
				if (ar1.succeeded()) {
					service.user().insert(newuser).onComplete((ar2) -> {
						if (ar2.succeeded()) {
							promise.complete();
						} else {
							promise.fail(ar2.cause());
						}
						service.close();
					});
				} else {
					promise.fail(ar1.cause());
				}
			});
		}
		return promise.future();
	}

}