package omv.server.entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class OwlClassNode {
    public OWLOntology owlontology;
    public OWLClass owlclass;
    public String id;
    public String name;
    public String label;
    public ArrayList<OwlClassNode> children;
    public JsonObject annotations;

    public OwlClassNode() {
        this.owlontology = null;
        this.owlclass = null;
        this.id = null;
        this.name = null;
        this.label = null;
        this.children = new ArrayList<OwlClassNode>();
        this.annotations = new JsonObject();
    }
    public void create(OWLClass owlclass, OWLOntology ontology) {
        this.owlontology = ontology;
        this.owlclass = owlclass;
        this.id = owlclass.toStringID();
        this.name = this.id.split("#")[1];
        Stream<OWLAnnotationAssertionAxiom> found_annotations = this.owlontology.annotationAssertionAxioms(this.owlclass.getIRI());
        for (OWLAnnotationAssertionAxiom found_annotation : found_annotations.toArray(OWLAnnotationAssertionAxiom[]::new)) {
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
                this.label = name;
            }
        } else {
            this.label = name;
        }
    }

    public String getLabel() {
        return this.label;
    }

    public void sortChildren() {
        this.children.sort(Comparator.comparing(OwlClassNode::getLabel));
    }

    public void addChild(OwlClassNode child) {
        this.children.add(child);
    }

    public void addSubClasses() {
        Stream<OWLSubClassOfAxiom> subClassAxioms = this.owlontology.subClassAxiomsForSuperClass(this.owlclass);
        for (OWLSubClassOfAxiom axiom : subClassAxioms.toArray(OWLSubClassOfAxiom[]::new)) {
            if(axiom.getSubClass().getClassExpressionType().getName().equals("Class")) {
                Stream<OWLClass> subClasses = axiom.getSubClass().classesInSignature();
                for (OWLClass subclass : subClasses.toArray(OWLClass[]::new)) {
                    OwlClassNode subclassnode = new OwlClassNode();
                    subclassnode.create(subclass, this.owlontology);
                    this.addChild(subclassnode);
                }
            }
        }
        this.sortChildren();
        for (OwlClassNode child : this.children) {
            child.addSubClasses();
        }
    }

    public JsonObject toJsonObject() {
        JsonObject result = new JsonObject();
        JsonObject informationJsonObject = new JsonObject();
        informationJsonObject.put("id", this.id);
        informationJsonObject.put("name", this.name);
        informationJsonObject.put("label", this.label);
        informationJsonObject.put("annotations", this.annotations);
        JsonArray childrenJsonArray = new JsonArray();
        for (OwlClassNode child : this.children) {
            childrenJsonArray.add(child.toJsonObject());
        }
        informationJsonObject.put("children", childrenJsonArray);
        result.put(this.id, informationJsonObject);
        return result;
    }

    public String toString(String dashes) {
        String result = dashes+this.label+"\n";
        for (OwlClassNode child : this.children) {
            result += child.toString(dashes+"-");
        }
        return result;
    }

    public String toString() {
        return this.toString("");
    }
}