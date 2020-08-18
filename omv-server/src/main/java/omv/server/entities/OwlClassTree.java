package omv.server.entities;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class OwlClassTree {
    private final OWLOntology owlontology;
    public OWLClass owlclass;
    public String name;
    public String id;
    public String label;
    public JsonObject annotations;
    public ArrayList<OwlClassNode> children;

    public OwlClassTree(OWLOntology owlontology) {
        this.owlontology = owlontology;
        this.name = "owl:Thing";
        this.id = "owl:Thing";
        this.label = "owl:Thing";
        this.annotations = new JsonObject();
        this.children = new ArrayList<OwlClassNode>();
    }

    public void init() {
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

    public String toString(){
        String result = this.name+"\n";
        for (OwlClassNode toplevelnode : this.children) {
            result += toplevelnode.toString("-");
        }
        return result;
    }
}