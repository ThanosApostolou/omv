package omv.server.entities;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLOntology;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class AnnotationProperty {
    public OWLOntology owlontology;
    public OWLAnnotationProperty owlannotationproperty;
    public String iri = "";
    public String name = "";
    public String label = "";
    ArrayList<Annotation> annotations = new ArrayList<Annotation>();

    public AnnotationProperty(OWLOntology owlontology, OWLAnnotationProperty owlannotationproperty) {
        this.owlontology = owlontology;
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

    public void addAnnotations() {
        Stream<OWLAnnotationAssertionAxiom> found_annotations = this.owlontology.annotationAssertionAxioms(this.owlannotationproperty.getIRI());
        for (OWLAnnotationAssertionAxiom found_annotation_axiom : found_annotations.toArray(OWLAnnotationAssertionAxiom[]::new)) {
            OWLAnnotation found_owlannotation = found_annotation_axiom.getAnnotation();
            Annotation myannotation = Annotation.fromOwlAnnotation(found_owlannotation);
            this.annotations.add(myannotation);
        }
    }

    public static void listAddAnnotations(ArrayList<AnnotationProperty> annotationproperties) {
        for (AnnotationProperty annotationproperty : annotationproperties) {
            System.out.println(annotationproperty.iri);
            annotationproperty.addAnnotations();
        }
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
