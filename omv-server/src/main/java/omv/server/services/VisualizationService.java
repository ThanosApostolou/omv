package omv.server.services;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

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

    public Future<Visualization> createFromInput(String owl1Path, String owl2Path, String mappingPath) {
        Promise<Visualization> promise = Promise.promise();
        OWLOntologyManager owlmanager = OWLManager.createOWLOntologyManager();
        try {
            File owl1File = new File(owl1Path);
            OWLOntology owl1 = owlmanager.loadOntologyFromOntologyDocument(owl1File);
            File owl2File = new File(owl2Path);
            OWLOntology owl2 = owlmanager.loadOntologyFromOntologyDocument(owl2File);
            App.app.fs.readFile(mappingPath, (ar) -> {
                if (ar.succeeded()) {
                    Buffer mappingBuffer = ar.result();
                    JsonObject mapping = mappingBuffer.toJsonObject();
                    Visualization visualization = new Visualization();
                    visualization.create(owl1, owl2, mapping);
                    promise.complete(visualization);
                } else {
                    promise.fail(ar.cause());
                }
            });
        } catch (Exception e) {
            promise.fail(e.getCause());
        }
        return promise.future();
    }

    public Future<Visualization> createFromNoreferenceInput(String owl1Path, String mappingPath) {
        Promise<Visualization> promise = Promise.promise();
        OWLOntologyManager owlmanager = OWLManager.createOWLOntologyManager();
        try {
            File owl1File = new File(owl1Path);
            OWLOntology owl1 = owlmanager.loadOntologyFromOntologyDocument(owl1File);
            OWLOntology owl2 = owlmanager.loadOntologyFromOntologyDocument(App.readResourceStream("HarmonicSS-Reference-Model+Vocabularies-v.0.9.3.owl"));
            App.app.fs.readFile(mappingPath, (ar) -> {
                if (ar.succeeded()) {
                    Buffer mappingBuffer = ar.result();
                    JsonObject mapping = mappingBuffer.toJsonObject();
                    Visualization visualization = new Visualization();
                    visualization.create(owl1, owl2, mapping);
                    promise.complete(visualization);
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