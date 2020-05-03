package omv.server.actions;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import omv.server.App;

public class VisualizationAction {

    public Future<Void> create(String owl1Path, String owl2Path, String mappingsPath) {
        Promise<Void> promise = Promise.promise();
        App.debug(owl1Path + " " + owl2Path + " " + mappingsPath);
        promise.complete();
        return promise.future();
    }

}