package omv.server.services;

import java.io.File;

import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import omv.server.App;
import omv.server.entities.Visualization;

public class VisualizationService {
    Service service;

    public VisualizationService(Service service) {
        this.service = service;
    }

    public Future<Void> createFromInput(String owl1Path, String owl2Path, String mappingsPath) {
        Promise<Void> promise = Promise.promise();
        App.debug(mappingsPath + " " + owl2Path + " " + mappingsPath);
        try {
            File owl1File = new File(owl1Path);
            OWLOntology owl1 = App.app.owlmanager.loadOntologyFromOntologyDocument(owl1File);
            File owl2File = new File(owl2Path);
            OWLOntology owl2 = App.app.owlmanager.loadOntologyFromOntologyDocument(owl2File);
            //new FunctionalSyntaxDocumentFormat()
            App.app.fs.readFile(mappingsPath, (ar) -> {
                if (ar.succeeded()) {
                    Buffer mappingsBuffer = ar.result();
                    JsonObject mappings = mappingsBuffer.toJsonObject();
                    Visualization visualization = new Visualization();
                    visualization.create(owl1, owl2, mappings);
                    promise.complete();
                } else {
                    promise.fail(ar.cause());
                }
            });
        } catch (Exception e) {
            promise.fail(e.getCause());
        }
        return promise.future();
    }
}