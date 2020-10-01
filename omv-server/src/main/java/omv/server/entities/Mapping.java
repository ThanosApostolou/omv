package omv.server.entities;

import java.util.ArrayList;

import io.vertx.core.json.JsonObject;

public class Mapping {
    public String owl1iri;
    public String owl2iri;
    public ArrayList<Rule> classrules;
    public ArrayList<Rule> proprules;

    public Mapping() {
        this.owl1iri = "";
        this.owl2iri = "";
        this.classrules = new ArrayList<Rule>();
        this.proprules = new ArrayList<Rule>();
    }

    public void init(JsonObject mappingobject) {
        this.owl1iri = mappingobject.getJsonObject("ontologies").getJsonObject("ontA").getString("uri");
        this.owl2iri = mappingobject.getJsonObject("ontologies").getJsonObject("ontB").getString("uri");
        mappingobject.getJsonObject("correspondences").getJsonArray("jsonarray").forEach((obj) -> {
            JsonObject ruleobject = (JsonObject) obj;
            Rule rule = new Rule();
            rule.init(ruleobject);
            if (rule.entity1.objectprops.isEmpty() && rule.entity1.dataprops.isEmpty() &&
                rule.entity2.objectprops.isEmpty() && rule.entity2.dataprops.isEmpty()) {
                int index = this.classrules.size();
                rule.label = "cr" + index ;
                this.classrules.add(rule);
            } else {
                int index = this.proprules.size();
                rule.label = "pr" + index ;
                this.proprules.add(rule);
            }
            /*if (rule.relation.equals("Equivalent")) {
                int index = this.equivalent.size();
                rule.label = "Eq " + index ;
                this.equivalent.add(rule);
            } else if (rule.relation.equals("Linked With")) {
                int index = this.linkedwith.size();
                rule.label = "LW " + index ;
                this.linkedwith.add(rule);
            } else {
                int index = this.other.size();
                rule.label = "Oth " + index ;
                this.other.add(rule);
            }*/
        });
    }

    public JsonObject toJsonOjbect() {
        JsonObject mappingjsonobject = new JsonObject();
        mappingjsonobject.put("owl1iri", this.owl1iri);
        mappingjsonobject.put("owl2iri", this.owl2iri);
        mappingjsonobject.put("classrules", Rule.listToJsonArray(this.classrules));
        mappingjsonobject.put("proprules", Rule.listToJsonArray(this.proprules));
        return mappingjsonobject;
    }

}