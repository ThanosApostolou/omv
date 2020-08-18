package omv.server.actions;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import omv.server.App;
import omv.server.entities.Visualization;
import omv.server.services.Service;

public class VisualizationAction {

    public Future<Visualization> create(String owl1Path, String owl2Path, String mappingsPath) {
        Promise<Visualization> promise = Promise.promise();
        App.debug(mappingsPath + " " + owl2Path + " " + mappingsPath);
        Service service = new Service();
        service.visualizationservice.createFromInput(owl1Path, owl2Path, mappingsPath).onComplete((ar) -> {
            if (ar.succeeded()) {
                promise.complete(ar.result());
            } else {
                promise.fail(ar.cause());
            }
        });
        return promise.future();
    }

}