package omv.server.entities;

import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLOntology;

import io.vertx.core.json.JsonObject;
import omv.server.App;

public class Visualization {
    String owl1;
    String owl2;
    String mappings;
    String output;

    public void create (OWLOntology owl1Object, OWLOntology owl2Object, JsonObject mappingsObject) {
        try {
            StringDocumentTarget owl1stringtarget = new StringDocumentTarget();
            owl1Object.saveOntology(owl1stringtarget);
            this.owl1 = owl1stringtarget.toString();
            StringDocumentTarget owl2stringtarget = new StringDocumentTarget();
            owl2Object.saveOntology(owl2stringtarget);
            this.owl2 = owl2stringtarget.toString();
            this.mappings = mappingsObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.output = owl2;
        App.debug(this.output);
    }
}