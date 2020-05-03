package omv.server.actions;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import omv.server.App;
import omv.server.services.Service;

public class VisualizationAction {

    public Future<Void> create(String owl1Path, String owl2Path, String mappingsPath) {
        Promise<Void> promise = Promise.promise();
        App.debug(mappingsPath + " " + owl2Path + " " + mappingsPath);
        Service service = new Service();
        service.visualizationservice.createFromInput(owl1Path, owl2Path, mappingsPath).onComplete((ar) -> {
            if (ar.succeeded()) {
                promise.complete();
            } else {
                promise.fail(ar.cause());
            }
        });
        return promise.future();
    }

}