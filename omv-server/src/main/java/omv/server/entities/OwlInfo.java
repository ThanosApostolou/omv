package omv.server.entities;

import java.util.stream.Stream;

import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLOntology;

import io.vertx.core.json.JsonObject;

public class OwlInfo {
    public final OWLOntology ontology;
    public String iri;
    public String versionIri;
    public String label;
    public JsonObject annotations;
    public OwlClassTree owlclasses;


    public OwlInfo(OWLOntology ontology) {
        this.ontology = ontology;
        this.iri = this.ontology.getOntologyID().getOntologyIRI().isPresent() ? this.ontology.getOntologyID().getOntologyIRI().get().getIRIString() : "";
        this.versionIri = this.ontology.getOntologyID().getVersionIRI().isPresent() ? this.ontology.getOntologyID().getVersionIRI().get().getIRIString() : "";

        this.annotations = new JsonObject();
        Stream<OWLAnnotation> found_annotations = this.ontology.annotations();
        for (OWLAnnotation found_annotation : found_annotations.toArray(OWLAnnotation[]::new)) {
            String annotation_property = found_annotation.getProperty().toString();
            String annotation_value = found_annotation.getValue().toString();
            if (annotation_property != null && annotation_value != null ) {
                this.annotations.put(annotation_property, annotation_value);
            }
        }

        String rdfs_label = this.annotations.getString("rdfs:label");
        if (rdfs_label != null) {
            String[] rdfs_label_splited = rdfs_label.split("\"");
            if (rdfs_label_splited.length > 1) {
                this.label =  rdfs_label_splited[1];
            } else {
                this.label = this.iri;
            }
        } else {
            this.label = this.iri;
        }

        this.owlclasses = new OwlClassTree(this.ontology);
        this.owlclasses.init();
    }

    public JsonObject toJsonObject() {
        JsonObject result = new JsonObject();
        result.put("iri", this.iri);
        result.put("versionIri", this.versionIri);
        result.put("label", this.label);
        result.put("annotations", this.annotations);
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