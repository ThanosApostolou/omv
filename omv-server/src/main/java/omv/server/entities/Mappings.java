package omv.server.entities;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class Mappings {
    public String owl1iri;
    public String owl2iri;
    public JsonArray equivalent;
    public JsonArray linkedwith;
    public JsonArray other;

    public Mappings() {
        this.owl1iri = "";
        this.owl2iri = "";
        this.equivalent = new JsonArray();
        this.linkedwith = new JsonArray();
        this.other = new JsonArray();
    }

    public void init(JsonObject mappings) {
        this.owl1iri = mappings.getJsonObject("ontologies").getJsonObject("ontA").getString("uri");
        this.owl2iri = mappings.getJsonObject("ontologies").getJsonObject("ontB").getString("uri");
        this.equivalent = new JsonArray();
        this.linkedwith = new JsonArray();
        this.other = new JsonArray();
        mappings.getJsonObject("correspondences").getJsonArray("jsonarray").forEach((obj) -> {
            JsonObject rule = (JsonObject) obj;
            String relation = rule.getString("relation");
            if (relation.equals("Equivalent")) {
                this.equivalent.add(rule);
            } else if (relation.equals("Linked With")) {
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
        mappingsjsonobject.put("equivalent", this.equivalent);
        mappingsjsonobject.put("linkedwith", this.linkedwith);
        mappingsjsonobject.put("other", this.other);
        return mappingsjsonobject;
    }

}