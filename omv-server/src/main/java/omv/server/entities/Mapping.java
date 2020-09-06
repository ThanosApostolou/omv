package omv.server.entities;

import java.util.ArrayList;

import io.vertx.core.json.JsonObject;

public class Mapping {
    public String owl1iri;
    public String owl2iri;
    public ArrayList<Rule> equivalent;
    public ArrayList<Rule> linkedwith;
    public ArrayList<Rule> other;

    public Mapping() {
        this.owl1iri = "";
        this.owl2iri = "";
        this.equivalent = new ArrayList<Rule>();
        this.linkedwith = new ArrayList<Rule>();
        this.other = new ArrayList<Rule>();
    }

    public void init(JsonObject mappingobject) {
        this.owl1iri = mappingobject.getJsonObject("ontologies").getJsonObject("ontA").getString("uri");
        this.owl2iri = mappingobject.getJsonObject("ontologies").getJsonObject("ontB").getString("uri");
        mappingobject.getJsonObject("correspondences").getJsonArray("jsonarray").forEach((obj) -> {
            JsonObject ruleobject = (JsonObject) obj;
            Rule rule = new Rule();
            rule.init(ruleobject);
            if (rule.relation.equals("Equivalent")) {
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
            }
        });
    }

    public JsonObject toJsonOjbect() {
        JsonObject mappingjsonobject = new JsonObject();
        mappingjsonobject.put("owl1iri", this.owl1iri);
        mappingjsonobject.put("owl2iri", this.owl2iri);
        mappingjsonobject.put("equivalent", Rule.listToJsonArray(this.equivalent));
        mappingjsonobject.put("linkedwith", Rule.listToJsonArray(this.linkedwith));
        mappingjsonobject.put("other", Rule.listToJsonArray(this.other));
        return mappingjsonobject;
    }

}