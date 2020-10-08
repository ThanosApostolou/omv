package omv.server.entities;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLOntology;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class AnnotationProperty {
    public OWLOntology ontology;
    public OWLAnnotationProperty owlannotationproperty;
    public String iri = "";
    public String name = "";
    public String label = "";

    public AnnotationProperty(OWLOntology owlontology, OWLAnnotationProperty owlannotationproperty) {
        this.ontology = owlontology;
        this.owlannotationproperty = owlannotationproperty;
        this.iri = this.owlannotationproperty.getIRI().getIRIString();
        this.name = this.owlannotationproperty.getIRI().getShortForm();
    }

    public String toString() {
        return this.owlannotationproperty.toString();
    }

    static ArrayList<AnnotationProperty> listFromOwl(OWLOntology owlontology) {
        ArrayList<AnnotationProperty> annotationproperties = new ArrayList<AnnotationProperty>();

        for (OWLAnnotationProperty annotprop : owlontology.annotationPropertiesInSignature().toArray(OWLAnnotationProperty[]::new)) {
            AnnotationProperty annotationproperty = new AnnotationProperty(owlontology, annotprop);
            annotationproperties.add(annotationproperty);
        }
        return annotationproperties;
    }

    public JsonObject toJsonObject() {
        JsonObject annotationpropertyjsonobject = new JsonObject();
        annotationpropertyjsonobject.put("iri", this.iri);
        annotationpropertyjsonobject.put("name", this.name);
        return annotationpropertyjsonobject;
    }
    public static JsonArray listToJsonArray(ArrayList<AnnotationProperty> annotationproperties) {
        JsonArray annotationpropertiesjsonarray = new JsonArray();
        for (AnnotationProperty annotationproperty : annotationproperties) {
            annotationpropertiesjsonarray.add(annotationproperty.toJsonObject());
        }
        return annotationpropertiesjsonarray;
    }
    static void printList(ArrayList<AnnotationProperty> annotationproperties) {
        for (AnnotationProperty annotationproperty : annotationproperties) {
            System.out.println(annotationproperty.iri);
            System.out.println(annotationproperty.name);
        }
    }

}
