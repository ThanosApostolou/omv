package omv.server.services;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import omv.server.App;

public class VisualizationService {
    Service service;

    public VisualizationService(Service service) {
        this.service = service;
    }

    public Future<Void> createFromInput(String owl1Path, String owl2Path, String mappingsPath) {
        Promise<Void> promise = Promise.promise();
        App.debug(mappingsPath + " " + owl2Path + " " + mappingsPath);
        App.app.fs.readFile(owl1Path, (ar1) -> {
            if (ar1.succeeded()) {
                Buffer owl1Buffer = ar1.result();
                App.debug(owl1Buffer.toString());
                App.app.fs.readFile(owl2Path, (ar2) -> {
                    if (ar2.succeeded()) {
                        Buffer owl2Buffer = ar2.result();
                        App.debug(owl2Buffer.toString());
                        App.app.fs.readFile(mappingsPath, (ar3) -> {
                            if (ar3.succeeded()) {
                                Buffer mappingsBuffer = ar3.result();
                                App.debug(mappingsBuffer.toString());
                                promise.complete();
                            } else {
                                promise.fail(ar3.cause());
                            }
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
}