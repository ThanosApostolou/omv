package omv.server.actions;

import java.util.ArrayList;

import io.vertx.core.Future;
import io.vertx.core.Promise;

import omv.server.models.User;

public class UserAction {

    public Future<ArrayList<User>> get(String search) {
        Promise<ArrayList<User>> promise = Promise.promise();
        User.select(null).onComplete((ar) -> {
			if (ar.succeeded()) {
				ArrayList<User> users = ar.result();
				promise.complete(users);
			} else {
				promise.fail(ar.cause());
			}
		});
        return promise.future();
    }

}