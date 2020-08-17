package omv.server.entities;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

public class OwlClassTree {
    private final OWLOntology ontology;
    String rootname;
    ArrayList<OwlClassNode> toplevelnodes;

    public OwlClassTree(OWLOntology ontology) {
        this.ontology = ontology;
        this.rootname = "owl:Thing";
        this.toplevelnodes = new ArrayList<OwlClassNode>();
    }

    public void init() {
        for (OWLClass cls : this.ontology.classesInSignature().toArray(OWLClass[]::new)) {
            OwlClassNode clsnode = new OwlClassNode();
            clsnode.create(cls, this.ontology);
            if (!clsnode.owlclass.isOWLThing()) {
                Stream<OWLSubClassOfAxiom> superClassAxioms = this.ontology.subClassAxiomsForSubClass(cls);
                AtomicBoolean istoplevel = new AtomicBoolean();
                istoplevel.set(true);
                for (OWLSubClassOfAxiom axiom : superClassAxioms.toArray(OWLSubClassOfAxiom[]::new)) {
                    if(axiom.getSuperClass().getClassExpressionType().getName().equals("Class")) {
                        Stream<OWLClass> superClasses = axiom.getSuperClass().classesInSignature();
                        for (OWLClass superclass : superClasses.toArray(OWLClass[]::new)) {
                            OwlClassNode superclassnode = new OwlClassNode();
                            superclassnode.create(superclass, this.ontology);

                            if (!superclassnode.name.equals("Thing")) {
                                istoplevel.set(false);
                                break;
                            }
                        };
                    }
                };
                if (istoplevel.get()) {
                    if (clsnode != null) {
                        this.toplevelnodes.add(clsnode);
                    }
                }
            }
        }

        for (OwlClassNode toplevelnode : this.toplevelnodes) {
            toplevelnode.addSubClasses();
        }
    }

    public String toString(){
        String result = this.rootname+"\n";
        for (OwlClassNode toplevelnode : this.toplevelnodes) {
            result += toplevelnode.toString("-");
        }
        return result;
    }
}