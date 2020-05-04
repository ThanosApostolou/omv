package omv.server.actions;

import java.util.ArrayList;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import omv.server.entities.User;
import omv.server.services.Service;

public class UserAction {

    public Future<ArrayList<User>> get(String search) {
		Promise<ArrayList<User>> promise = Promise.promise();
		Service service = new Service();
		service.startConnection().onComplete((ar1) -> {
			if (ar1.succeeded()) {
				service.userservice.select(null).onComplete((ar2) -> {
					if (ar2.succeeded()) {
						ArrayList<User> users = ar2.result();
						promise.complete(users);
					} else {
						promise.fail(ar2.cause());
					}
					service.closeConnection();
				});
			} else {
				promise.fail(ar1.cause());
			}
		});
        return promise.future();
	}

	public Future<Boolean> post(String email, String username, String password1, String password2) {
		Promise<Boolean> promise = Promise.promise();
		Service service = new Service();
		service.startConnection().onComplete((ar1) -> {
			if (ar1.succeeded()) {
				service.userservice.createFromInput(email, username, password1, password2).onComplete((ar2) -> {
					if (ar2.succeeded()) {
						User newuser = ar2.result();
						service.userservice.insert(newuser).onComplete((ar3) -> {
							if (ar3.succeeded()) {
								promise.complete();
							} else {
								promise.fail(ar3.cause());
							}
							service.closeConnection();
						});
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

	public Future<Boolean> delete(int userid) {
		Promise<Boolean> promise = Promise.promise();
		return promise.future();
	}
}