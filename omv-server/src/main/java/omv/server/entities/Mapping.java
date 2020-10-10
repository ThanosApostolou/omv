package omv.server.entities;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import io.vertx.core.json.JsonObject;

public class Mapping {
    public Visualization visualization = null;
    public String owl1iri;
    public String owl2iri;
    public ArrayList<Rule> classrules;
    public ArrayList<Rule> proprules;
    String error = null;

    public Mapping() {
        this.owl1iri = "";
        this.owl2iri = "";
        this.classrules = new ArrayList<Rule>();
        this.proprules = new ArrayList<Rule>();
    }

    public void init(JsonObject mappingobject, Visualization visualization) {
        this.visualization = visualization;
        this.owl1iri = mappingobject.getJsonObject("ontologies").getJsonObject("ontA").getString("uri");
        this.owl2iri = mappingobject.getJsonObject("ontologies").getJsonObject("ontB").getString("uri");
        if (!this.visualization.owl1.iri.equals(this.owl1iri) || !this.visualization.owl2.iri.equals(this.owl2iri)) {
            this.error = "Wrong owl ontologies. Expected: (Left owl: " + this.owl1iri + ", Right owl: " + this.owl2iri +")";
            return;
        }
        AtomicInteger index = new AtomicInteger();
        index.set(0);
        mappingobject.getJsonObject("correspondences").getJsonArray("jsonarray").forEach((obj) -> {
            JsonObject ruleobject = (JsonObject) obj;
            Rule rule = new Rule();
            rule.init(ruleobject);
            rule.label = "MR" + index.get();
            if (rule.entity1.objectprops.isEmpty() && rule.entity1.dataprops.isEmpty() &&
                rule.entity2.objectprops.isEmpty() && rule.entity2.dataprops.isEmpty()) {
                //int index = this.classrules.size();
                //rule.label = "cr" + index ;
                this.classrules.add(rule);
            } else {
                //int index = this.proprules.size();
                //rule.label = "pr" + index ;
                this.proprules.add(rule);
            }
            index.set(index.get()+1);
        });
    }

    public JsonObject toJsonOjbect() {
        JsonObject mappingjsonobject = new JsonObject();
        mappingjsonobject.put("owl1iri", this.owl1iri);
        mappingjsonobject.put("owl2iri", this.owl2iri);
        mappingjsonobject.put("classrules", Rule.listToJsonArray(this.classrules));
        mappingjsonobject.put("proprules", Rule.listToJsonArray(this.proprules));
        mappingjsonobject.put("error", this.error);
        return mappingjsonobject;
    }

}