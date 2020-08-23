package omv.server.entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;
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
    public String iri;
    public String name;
    public String label;
    public ArrayList<Annotation> annotations;
    public ArrayList<OwlClassNode> children;

    public OwlClassNode() {
        this.owlontology = null;
        this.owlclass = null;
        this.iri = null;
        this.name = null;
        this.label = null;
        this.annotations = new ArrayList<Annotation>();
        this.children = new ArrayList<OwlClassNode>();
    }
    public void create(OWLClass owlclass, OWLOntology ontology) {
        this.owlontology = ontology;
        this.owlclass = owlclass;
        this.iri = owlclass.toStringID();
        this.name = this.iri.split("#")[1];
        Stream<OWLAnnotationAssertionAxiom> found_annotations = this.owlontology.annotationAssertionAxioms(this.owlclass.getIRI());
        for (OWLAnnotationAssertionAxiom found_annotation : found_annotations.toArray(OWLAnnotationAssertionAxiom[]::new)) {
            Annotation myannotation = Annotation.fromStrings(found_annotation.getProperty().toString(), found_annotation.getValue().toString());
            this.annotations.add(myannotation);
        }
        this.label = Annotation.getLabel(this.annotations);
        if (this.label.isEmpty()) {
            this.label = this.iri;
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
        JsonObject jsonobject = new JsonObject();
        jsonobject.put("iri", this.iri);
        jsonobject.put("name", this.name);
        jsonobject.put("label", this.label);
        jsonobject.put("annotations", Annotation.listToJsonArray(this.annotations));
        JsonArray childrenJsonArray = new JsonArray();
        for (OwlClassNode child : this.children) {
            childrenJsonArray.add(child.toJsonObject());
        }
        jsonobject.put("children", childrenJsonArray);
        return jsonobject;
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

    public void createRoot(OWLOntology owlontology) {
        this.owlontology = owlontology;
        this.name = "owl:Thing";
        this.iri = "owl:Thing";
        this.label = "owl:Thing";
        for (OWLClass cls : this.owlontology.classesInSignature().toArray(OWLClass[]::new)) {
            OwlClassNode clsnode = new OwlClassNode();
            clsnode.create(cls, this.owlontology);
            if (!clsnode.owlclass.isOWLThing()) {
                Stream<OWLSubClassOfAxiom> superClassAxioms = this.owlontology.subClassAxiomsForSubClass(cls);
                AtomicBoolean istoplevel = new AtomicBoolean();
                istoplevel.set(true);
                for (OWLSubClassOfAxiom axiom : superClassAxioms.toArray(OWLSubClassOfAxiom[]::new)) {
                    if(axiom.getSuperClass().getClassExpressionType().getName().equals("Class")) {
                        Stream<OWLClass> superClasses = axiom.getSuperClass().classesInSignature();
                        for (OWLClass superclass : superClasses.toArray(OWLClass[]::new)) {
                            OwlClassNode superclassnode = new OwlClassNode();
                            superclassnode.create(superclass, this.owlontology);

                            if (!superclassnode.name.equals("Thing")) {
                                istoplevel.set(false);
                                break;
                            }
                        };
                    }
                };
                if (istoplevel.get()) {
                    if (clsnode != null) {
                        this.children.add(clsnode);
                    }
                }
            }
        }
        for (OwlClassNode toplevelnode : this.children) {
            toplevelnode.addSubClasses();
        }
    }
}