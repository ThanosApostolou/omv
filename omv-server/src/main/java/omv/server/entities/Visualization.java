package omv.server.entities;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import io.vertx.core.json.JsonObject;
import omv.server.App;

public class Visualization {
    OwlInfo owl1;
    OwlInfo owl2;
    String mappings;
    String output;

    public void create (OWLOntology owl1Object, OWLOntology owl2Object, JsonObject mappingsObject) {
        try {
            this.owl1 = new OwlInfo(owl1Object);
            this.owl2 = new OwlInfo(owl2Object);
            this.mappings = mappingsObject.toString();

            System.out.println(this.owl1.toJsonObject().encodePrettily());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JsonObject toJsonObject() {
        JsonObject result = new JsonObject();
        result.put("owl1", this.owl1.toJsonObject());
        result.put("owl2", this.owl2.toJsonObject());
        result.put("mappings", this.mappings);
        return result;
    }
}