package omv.server.entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class OwlDataPropertyNode {
    public OWLOntology owlontology;
    public OWLDataProperty dataprop;
    public String iri;
    public String name;
    public String label;
    public ArrayList<Annotation> annotations;
    public ArrayList<OwlDataPropertyNode> children;
    public Boolean hasEquivalentRule;
    public Boolean hasLinkedWithRule;
    public Boolean hasOtherRule;

    public OwlDataPropertyNode() {
        this.owlontology = null;
        this.dataprop = null;
        this.iri = null;
        this.name = null;
        this.label = null;
        this.annotations = new ArrayList<Annotation>();
        this.children = new ArrayList<OwlDataPropertyNode>();
        this.hasEquivalentRule = false;
        this.hasLinkedWithRule = false;
        this.hasOtherRule = false;
    }
    public void create(OWLDataProperty dataprop, OWLOntology ontology) {
        this.owlontology = ontology;
        this.dataprop = dataprop;
        this.iri = dataprop.toStringID();
        this.name = this.iri.split("#")[1];
        Stream<OWLAnnotationAssertionAxiom> found_annotations = this.owlontology.annotationAssertionAxioms(this.dataprop.getIRI());
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
        this.children.sort(Comparator.comparing(OwlDataPropertyNode::getLabel));
    }

    public void addChild(OwlDataPropertyNode child) {
        this.children.add(child);
    }

    public void addSubDataProperties() {
        Stream<OWLSubDataPropertyOfAxiom> subDataPropertyAxioms = this.owlontology.dataSubPropertyAxiomsForSuperProperty(this.dataprop);
        for (OWLSubDataPropertyOfAxiom axiom : subDataPropertyAxioms.toArray(OWLSubDataPropertyOfAxiom[]::new)) {
            //if(axiom.getSubClass().getClassExpressionType().getName().equals("Class")) {
                Stream<OWLDataProperty> subDataProperties = axiom.getSubProperty().dataPropertiesInSignature();
                for (OWLDataProperty subdataprop : subDataProperties.toArray(OWLDataProperty[]::new)) {
                    OwlDataPropertyNode subdatapropnode = new OwlDataPropertyNode();
                    subdatapropnode.create(subdataprop, this.owlontology);
                    this.addChild(subdatapropnode);
                }
            //}
        }
        this.sortChildren();
        for (OwlDataPropertyNode child : this.children) {
            child.addSubDataProperties();
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
        for (OwlDataPropertyNode child : this.children) {
            childrenJsonArray.add(child.toJsonObject());
        }
        jsonobject.put("children", childrenJsonArray);
        return jsonobject;
    }

    public String toString(String dashes) {
        String result = dashes+this.label+"\n";
        for (OwlDataPropertyNode child : this.children) {
            result += child.toString(dashes+"-");
        }
        return result;
    }

    public String toString() {
        return this.toString("");
    }

    public OwlDataPropertyNode findByIri(String giveniri) {
        OwlDataPropertyNode foundentity = null;
        if (this.iri.equals(giveniri)) {
            foundentity = this;
            return foundentity;
        }
        for (OwlDataPropertyNode child : this.children) {
            foundentity = child.findByIri(giveniri);
            if (foundentity != null) {
                return foundentity;
            }
        }
        return foundentity;
    }

    public void createRoot(OWLOntology owlontology) {
        this.owlontology = owlontology;
        this.name = "owl:topDataProperty";
        this.iri = "owl:topDataProperty";
        this.label = "owl:topDataProperty";
        for (OWLDataProperty dataprop : this.owlontology.dataPropertiesInSignature().toArray(OWLDataProperty[]::new)) {
            OwlDataPropertyNode datapropnode = new OwlDataPropertyNode();
            datapropnode.create(dataprop, this.owlontology);
            if (!datapropnode.name.equals("topDataProperty")) {
                Stream<OWLSubDataPropertyOfAxiom> superDataPropertyAxioms = this.owlontology.dataSubPropertyAxiomsForSubProperty(dataprop);
                AtomicBoolean istoplevel = new AtomicBoolean();
                istoplevel.set(true);
                for (OWLSubDataPropertyOfAxiom axiom : superDataPropertyAxioms.toArray(OWLSubDataPropertyOfAxiom[]::new)) {
                    //if(axiom.getSuperProperty().getClassExpressionType().getName().equals("Class")) {
                        Stream<OWLDataProperty> superDataProperties = axiom.getSuperProperty().dataPropertiesInSignature();
                        for (OWLDataProperty superdataprop : superDataProperties.toArray(OWLDataProperty[]::new)) {
                            OwlDataPropertyNode superdatapropnode = new OwlDataPropertyNode();
                            superdatapropnode.create(superdataprop, this.owlontology);
                            if (!superdatapropnode.name.equals("topDataProperty")) {
                                istoplevel.set(false);
                                break;
                            }
                        };
                    //}
                };
                if (istoplevel.get()) {
                    if (datapropnode != null) {
                        this.children.add(datapropnode);
                    }
                }
            }
        }
        for (OwlDataPropertyNode toplevelnode : this.children) {
            toplevelnode.addSubDataProperties();
        }
    }
}