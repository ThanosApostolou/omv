package omv.server.entities;

import java.util.ArrayList;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class Rule {
    public String relation;
    public RuleEntity entity1;
    public RuleEntity entity2;
    public JsonObject content;

    public Rule() {
        this.relation = "";
        this.entity1 = new RuleEntity();
        this.entity2 = new RuleEntity();
        this.content = new JsonObject();
    }

    public void init(JsonObject ruleobject) {
        this.relation = ruleobject.getString("relation");
        JsonObject entity1object = ruleobject.getJsonObject("entity1");
        this.entity1.init(entity1object);
        JsonObject entity2object = ruleobject.getJsonObject("entity2");
        this.entity2.init(entity2object);
        this.content = ruleobject;
    }

    public JsonObject toJsonObject() {
        JsonObject rulejsonobject = new JsonObject();
        rulejsonobject.put("relation", this.relation);
        rulejsonobject.put("entity1", this.entity1.toJsonObject());
        rulejsonobject.put("entity2", this.entity2.toJsonObject());
        rulejsonobject.put("content", this.content);
        return rulejsonobject;
    }

    public static JsonArray listToJsonArray(ArrayList<Rule> rules) {
        JsonArray rulesjsonobject = new JsonArray();
        rules.forEach((rule) -> {
            rulesjsonobject.add(rule.toJsonObject());
        });
        return rulesjsonobject;

    }
}

class RuleEntity {
    public String pid;
    public JsonArray classes;
    public JsonArray objectprops;
    public JsonArray dataprops;

    public RuleEntity() {
        this.pid = "";
        this.classes = new JsonArray();
        this.objectprops = new JsonArray();
        this.dataprops = new JsonArray();
    }

    public void init(JsonObject entityobject) {
        this.pid = entityobject.getString("pid");
        this.determineAndAddOwlEntity(entityobject);
        JsonObject cls = entityobject.getJsonObject("cls");
        if (cls != null) {
            this.determineAndAddOwlEntity(cls);
        }
        JsonArray classarray = entityobject.getJsonArray("classarray");
        if (classarray != null) {
            classarray.forEach((obj) -> {
                this.determineAndAddOwlEntity((JsonObject) obj);
            });;
        }
        JsonArray proparray = entityobject.getJsonArray("proparray");
        if (proparray != null) {
            proparray.forEach((obj) -> {
                this.determineAndAddOwlEntity((JsonObject) obj);
            });;
        }
    }

    private void determineAndAddOwlEntity(JsonObject obj) {
        if (obj.getString("classuri") != null) {
            this.addOwlEntity(obj, "class");
        }
        if (obj.getString("relationuri") != null) {
            this.addOwlEntity(obj, "objectprop");
        }
        if (obj.getString("propertyuri") != null) {
            this.addOwlEntity(obj, "dataprop");
        }
    }

    private void addOwlEntity(JsonObject obj, String type) {
        JsonObject newowlentity = new JsonObject();
        newowlentity.put("pid", obj.getString("pid"));
        if (type.equals("class")) {
            newowlentity.put("iri", obj.getString("classuri"));
            this.classes.add(newowlentity);
        } else if (type.equals("objectprop")) {
            newowlentity.put("iri", obj.getString("relationuri"));
            this.objectprops.add(newowlentity);
        } else if (type.equals("dataprop")) {
            newowlentity.put("iri", obj.getString("propertyuri"));
            this.dataprops.add(newowlentity);
        }
    }

    public JsonObject toJsonObject() {
        JsonObject entityjsonobject = new JsonObject();
        entityjsonobject.put("pid", this.pid);
        entityjsonobject.put("classes", this.classes);
        entityjsonobject.put("objectprops", this.objectprops);
        entityjsonobject.put("dataprops", this.dataprops);
        return entityjsonobject;
    }
}