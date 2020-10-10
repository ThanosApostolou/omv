package omv.server.entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class OwlObjectPropertyNode {
    public OWLOntology owlontology;
    public OWLObjectProperty objprop;
    public String iri;
    public String name;
    public String label;
    public ArrayList<Annotation> annotations;
    public ArrayList<OwlObjectPropertyNode> children;
    public Boolean hasEquivalentRule;
    public Boolean hasLinkedWithRule;
    public Boolean hasOtherRule;

    public OwlObjectPropertyNode() {
        this.owlontology = null;
        this.objprop = null;
        this.iri = null;
        this.name = null;
        this.label = null;
        this.annotations = new ArrayList<Annotation>();
        this.children = new ArrayList<OwlObjectPropertyNode>();
        this.hasEquivalentRule = false;
        this.hasLinkedWithRule = false;
        this.hasOtherRule = false;
    }
    public void create(OWLObjectProperty objprop, OWLOntology ontology) {
        this.owlontology = ontology;
        this.objprop = objprop;
        this.iri = objprop.toStringID();
        this.name = this.iri.split("#")[1];
        Stream<OWLAnnotationAssertionAxiom> found_annotations = this.owlontology.annotationAssertionAxioms(this.objprop.getIRI());
        for (OWLAnnotationAssertionAxiom found_annotation_axiom : found_annotations.toArray(OWLAnnotationAssertionAxiom[]::new)) {
            OWLAnnotation found_owlannotation = found_annotation_axiom.getAnnotation();
            Annotation myannotation = Annotation.fromOwlAnnotation(found_owlannotation);
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
        this.children.sort(Comparator.comparing(OwlObjectPropertyNode::getLabel));
    }

    public void addChild(OwlObjectPropertyNode child) {
        this.children.add(child);
    }

    public void addSubObjectProperties() {
        Stream<OWLSubObjectPropertyOfAxiom> subObjectPropertyAxioms = this.owlontology.objectSubPropertyAxiomsForSuperProperty(this.objprop);
        for (OWLSubObjectPropertyOfAxiom axiom : subObjectPropertyAxioms.toArray(OWLSubObjectPropertyOfAxiom[]::new)) {
            //if(axiom.getSubClass().getClassExpressionType().getName().equals("Class")) {
                Stream<OWLObjectProperty> subObjectProperties = axiom.getSubProperty().objectPropertiesInSignature();
                for (OWLObjectProperty subobjprop : subObjectProperties.toArray(OWLObjectProperty[]::new)) {
                    OwlObjectPropertyNode subobjpropnode = new OwlObjectPropertyNode();
                    subobjpropnode.create(subobjprop, this.owlontology);
                    this.addChild(subobjpropnode);
                }
            //}
        }
        this.sortChildren();
        for (OwlObjectPropertyNode child : this.children) {
            child.addSubObjectProperties();
        }
    }

    public JsonObject toJsonObject() {
        JsonObject jsonobject = new JsonObject();
        jsonobject.put("iri", this.iri);
        jsonobject.put("name", this.name);
        jsonobject.put("label", this.label);
        jsonobject.put("hasEquivalentRule", this.hasEquivalentRule);
        jsonobject.put("hasLinkedWithRule", this.hasLinkedWithRule);
        jsonobject.put("hasOtherRule", this.hasOtherRule);
        jsonobject.put("annotations", Annotation.listToJsonArray(this.annotations));
        JsonArray childrenJsonArray = new JsonArray();
        for (OwlObjectPropertyNode child : this.children) {
            childrenJsonArray.add(child.toJsonObject());
        }
        jsonobject.put("children", childrenJsonArray);
        return jsonobject;
    }

    public String toString(String dashes) {
        String result = dashes+this.label+"\n";
        for (OwlObjectPropertyNode child : this.children) {
            result += child.toString(dashes+"-");
        }
        return result;
    }

    public String toString() {
        return this.toString("");
    }

    public OwlObjectPropertyNode findByIri(String giveniri) {
        OwlObjectPropertyNode foundentity = null;
        if (this.iri.equals(giveniri)) {
            foundentity = this;
            return foundentity;
        }
        for (OwlObjectPropertyNode child : this.children) {
            foundentity = child.findByIri(giveniri);
            if (foundentity != null) {
                return foundentity;
            }
        }
        return foundentity;
    }

    public void createRoot(OWLOntology owlontology) {
        this.owlontology = owlontology;
        this.name = "owl:topObjectProperty";
        this.iri = "owl:topObjectProperty";
        this.label = "owl:topObjectProperty";
        for (OWLObjectProperty objprop : this.owlontology.objectPropertiesInSignature().toArray(OWLObjectProperty[]::new)) {
            OwlObjectPropertyNode objpropnode = new OwlObjectPropertyNode();
            objpropnode.create(objprop, this.owlontology);
            if (!objpropnode.name.equals("topObjectProperty")) {
                Stream<OWLSubObjectPropertyOfAxiom> superObjectPropertyAxioms = this.owlontology.objectSubPropertyAxiomsForSubProperty(objprop);
                AtomicBoolean istoplevel = new AtomicBoolean();
                istoplevel.set(true);
                for (OWLSubObjectPropertyOfAxiom axiom : superObjectPropertyAxioms.toArray(OWLSubObjectPropertyOfAxiom[]::new)) {
                    //if(axiom.getSuperProperty().getClassExpressionType().getName().equals("Class")) {
                        Stream<OWLObjectProperty> superObjectProperties = axiom.getSuperProperty().objectPropertiesInSignature();
                        for (OWLObjectProperty superobjprop : superObjectProperties.toArray(OWLObjectProperty[]::new)) {
                            OwlObjectPropertyNode superobjpropnode = new OwlObjectPropertyNode();
                            superobjpropnode.create(superobjprop, this.owlontology);
                            if (!superobjpropnode.name.equals("topObjectProperty")) {
                                istoplevel.set(false);
                                break;
                            }
                        };
                    //}
                };
                if (istoplevel.get()) {
                    if (objpropnode != null) {
                        this.children.add(objpropnode);
                    }
                }
            }
        }
        for (OwlObjectPropertyNode toplevelnode : this.children) {
            toplevelnode.addSubObjectProperties();
        }
    }
}