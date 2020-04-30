package omv.server.actions;

import java.util.ArrayList;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.sqlclient.SqlConnection;
import omv.server.App;
import omv.server.models.User;
import omv.server.services.UserService;

public class UserAction {

    public Future<ArrayList<User>> get(String search) {
		Promise<ArrayList<User>> promise = Promise.promise();

		App.app.dbmanager.connect().onSuccess((SqlConnection conn) -> {
			new UserService(conn).select(null).onComplete((ar) -> {
				if (ar.succeeded()) {
					ArrayList<User> users = ar.result();
					promise.complete(users);
				} else {
					promise.fail(ar.cause());
				}
				conn.close();
			});
		});
        return promise.future();
    }

}