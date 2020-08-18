package omv.server.entities;

import org.semanticweb.owlapi.model.OWLOntology;

import io.vertx.core.json.JsonObject;

public class Visualization {
    OwlInfo owl1;
    OwlInfo owl2;
    JsonObject mappings;
    String output;

    public void create (OWLOntology owl1Object, OWLOntology owl2Object, JsonObject mappingsObject) {
        try {
            this.owl1 = new OwlInfo(owl1Object);
            this.owl2 = new OwlInfo(owl2Object);
            this.mappings = mappingsObject;
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