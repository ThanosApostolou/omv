package omv.server.entities;

import java.util.ArrayList;
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
    public ArrayList<Annotation> annotations;
    public OwlClassNode owlclasses;
    public OwlObjectPropertyNode owlobjprops;
    public OwlDataPropertyNode owldataprops;


    public OwlInfo(OWLOntology ontology) {
        this.ontology = ontology;
        this.iri = this.ontology.getOntologyID().getOntologyIRI().isPresent() ? this.ontology.getOntologyID().getOntologyIRI().get().getIRIString() : "";
        this.versionIri = this.ontology.getOntologyID().getVersionIRI().isPresent() ? this.ontology.getOntologyID().getVersionIRI().get().getIRIString() : "";

        this.annotations = new ArrayList<Annotation>();
        Stream<OWLAnnotation> found_annotations = this.ontology.annotations();
        for (OWLAnnotation found_annotation : found_annotations.toArray(OWLAnnotation[]::new)) {
            Annotation myannotation = Annotation.fromStrings(found_annotation.getProperty().toString(), found_annotation.getValue().toString());
            this.annotations.add(myannotation);
        }

        this.label = Annotation.getLabel(this.annotations);
        if (this.label.isEmpty()) {
            this.label = this.iri;
        }

        this.owlclasses = new OwlClassNode();
        this.owlclasses.createRoot(this.ontology);
        this.owlobjprops = new OwlObjectPropertyNode();
        this.owlobjprops.createRoot(this.ontology);
        this.owldataprops = new OwlDataPropertyNode();
        this.owldataprops.createRoot(this.ontology);
    }

    public JsonObject toJsonObject() {
        JsonObject result = new JsonObject();
        result.put("iri", this.iri);
        result.put("versionIri", this.versionIri);
        result.put("label", this.label);
        result.put("annotations", Annotation.listToJsonArray(this.annotations));
        result.put("owlclasses", this.owlclasses.toJsonObject());
        result.put("owlobjprops", this.owlobjprops.toJsonObject());
        result.put("owldataprops", this.owldataprops.toJsonObject());
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