package omv.server.actions;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import omv.server.App;
import omv.server.entities.Visualization;
import omv.server.services.Service;

public class VisualizationAction {

    public Future<Visualization> create(String owl1Path, String owl2Path, String mappingPath) {
        Promise<Visualization> promise = Promise.promise();
        //App.debug(owl1Path + " " + owl2Path + " " + mappingPath);
        App.app.logger.info("Requested new Visualization with uploaded files:\n\t" + owl1Path + "\n\t" + owl2Path + "\n\t" + mappingPath);
        Service service = new Service();
        service.visualizationservice.createFromInput(owl1Path, owl2Path, mappingPath).onComplete((ar) -> {
            if (ar.succeeded()) {
                promise.complete(ar.result());
            } else {
                promise.fail(ar.cause());
            }
        });
        return promise.future();
    }

    public Future<Visualization> createNoreference(String owl1Path, String mappingPath) {
        Promise<Visualization> promise = Promise.promise();
        //App.debug(owl1Path + " " + mappingPath);
        App.app.logger.info("Requested new Visualization with uploaded files:\n\t" + owl1Path + "\n\t" + mappingPath);
        Service service = new Service();
        service.visualizationservice.createFromNoreferenceInput(owl1Path, mappingPath).onComplete((ar) -> {
            if (ar.succeeded()) {
                promise.complete(ar.result());
            } else {
                promise.fail(ar.cause());
            }
        });
        return promise.future();
    }

}