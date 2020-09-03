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
                this.equivalent.add(rule);
            } else if (rule.relation.equals("Linked With")) {
                this.linkedwith.add(rule);
            } else {
                this.other.add(rule);
            }
        });
    }

    public JsonObject toJsonOjbect() {
        JsonObject mappingsjsonobject = new JsonObject();
        mappingsjsonobject.put("owl1iri", this.owl1iri);
        mappingsjsonobject.put("owl2iri", this.owl2iri);
        mappingsjsonobject.put("equivalent", Rule.listToJsonArray(this.equivalent));
        mappingsjsonobject.put("linkedwith", Rule.listToJsonArray(this.linkedwith));
        mappingsjsonobject.put("other", Rule.listToJsonArray(this.other));
        return mappingsjsonobject;
    }

}