package omv.server.entities;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.OWLOntology;

import io.vertx.core.json.JsonObject;

public class Visualization {
    public OwlInfo owl1;
    public OwlInfo owl2;
    public Mapping mapping;
    public String output;

    public void create (OWLOntology owl1Object, OWLOntology owl2Object, JsonObject mappingobject) {
        try {
            this.owl1 = new OwlInfo(owl1Object);
            this.owl2 = new OwlInfo(owl2Object);
            this.mapping = new Mapping();
            this.mapping.init(mappingobject);
            //this.markEntitiesByRule(1);
            //this.markEntitiesByRule(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void markEntitiesByRule(int owlnumber) {
        OwlInfo owl = (owlnumber == 1) ? this.owl1 : this.owl2;
        ArrayList<String> relations = new ArrayList<String>();
        relations.add("Equivalent");
        relations.add("LinkedWith");
        for (String relation : relations) {
            ArrayList<Rule> rules;
            if (relation.equals("Equivalent")) {
                rules = this.mapping.classrules;
            } else {
                rules = this.mapping.proprules;
            }
            for (Rule rule : rules) {
                RuleEntity rulentity = (owlnumber == 1) ? rule.entity1 : rule.entity2;
                for (Object classruleobject : rulentity.classes) {
                    JsonObject classrule = (JsonObject) classruleobject;
                    OwlClassNode foundentity = owl.owlclasses.findByIri(classrule.getString("iri"));
                    if (foundentity != null) {
                        if (relation.equals("Equivalent")) {
                            foundentity.hasEquivalentRule = true;
                        } else if (relation.equals("LinkedWith")) {
                            foundentity.hasLinkedWithRule = true;
                        } else {
                            foundentity.hasOtherRule = true;
                        }
                    }
                }
                for (Object objectpropruleobject : rulentity.objectprops) {
                    JsonObject objectproprule = (JsonObject) objectpropruleobject;
                    OwlObjectPropertyNode foundentity = owl.owlobjprops.findByIri(objectproprule.getString("iri"));
                    if (foundentity != null) {
                        if (relation.equals("Equivalent")) {
                            foundentity.hasEquivalentRule = true;
                        } else if (relation.equals("LinkedWith")) {
                            foundentity.hasLinkedWithRule = true;
                        } else {
                            foundentity.hasOtherRule = true;
                        }
                    }
                }
                for (Object objectpropruleobject : rulentity.dataprops) {
                    JsonObject dataproprule = (JsonObject) objectpropruleobject;
                    OwlDataPropertyNode foundentity = owl.owldataprops.findByIri(dataproprule.getString("iri"));
                    if (foundentity != null) {
                        if (relation.equals("Equivalent")) {
                            foundentity.hasEquivalentRule = true;
                        } else if (relation.equals("LinkedWith")) {
                            foundentity.hasLinkedWithRule = true;
                        } else {
                            foundentity.hasOtherRule = true;
                        }
                    }
                }
            }
        }
    }

    public JsonObject toJsonObject() {
        JsonObject result = new JsonObject();
        result.put("owl1", this.owl1.toJsonObject());
        result.put("owl2", this.owl2.toJsonObject());
        result.put("mapping", this.mapping.toJsonOjbect());
        return result;
    }
}