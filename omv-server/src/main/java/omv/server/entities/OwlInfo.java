package omv.server.entities;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import io.vertx.core.json.JsonObject;

public class OwlInfo {
    public OWLOntology ontology;
    public OwlClassTree owlclasses;

    public OwlInfo(OWLOntology ontology) {
        this.ontology = ontology;
        this.owlclasses = new OwlClassTree(this.ontology);
        this.owlclasses.init();
    }

    public JsonObject toJsonObject() {
        JsonObject result = new JsonObject();
        result.put("owlclasses", this.owlclasses.toJsonObject());
        return result;
    }

    public String toString() {
        StringDocumentTarget owlstringtarget = new StringDocumentTarget();
        try {
            this.ontology.saveOntology(owlstringtarget);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return owlstringtarget.toString();
    }
}