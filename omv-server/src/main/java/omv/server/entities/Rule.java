package omv.server.entities;

import java.util.ArrayList;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class Rule {
    public String label = "";
    public String relation = "";
    public String direction = "";
    public String comments = "";
    public String similarity = "";
    public String simcomments = "";
    public JsonObject directTransformation = new JsonObject();
    public JsonObject inverseTransformation = new JsonObject();
    public RuleEntity entity1 = new RuleEntity();
    public RuleEntity entity2 = new RuleEntity();

    public void init(JsonObject ruleobject) {
        this.relation = ruleobject.getString("relation");
        this.direction = ruleobject.getString("direction");
        this.comments = ruleobject.getString("comments");
        this.similarity = ruleobject.getString("similarity");
        this.simcomments = ruleobject.getString("simcomments");
        this.directTransformation = ruleobject.getJsonObject("directTransformation");
        this.inverseTransformation = ruleobject.getJsonObject("inverseTransformation");
        JsonObject entity1object = ruleobject.getJsonObject("entity1");
        this.entity1.init(entity1object);
        JsonObject entity2object = ruleobject.getJsonObject("entity2");
        this.entity2.init(entity2object);
    }

    public JsonObject toJsonObject() {
        JsonObject rulejsonobject = new JsonObject();
        rulejsonobject.put("label", this.label);
        rulejsonobject.put("relation", this.relation);
        rulejsonobject.put("direction", this.direction);
        rulejsonobject.put("comments", this.comments);
        rulejsonobject.put("similarity", this.similarity);
        rulejsonobject.put("simcomments", this.simcomments);
        rulejsonobject.put("directTransformation", this.directTransformation);
        rulejsonobject.put("inverseTransformation", this.inverseTransformation);
        rulejsonobject.put("entity1", this.entity1.toJsonObject());
        rulejsonobject.put("entity2", this.entity2.toJsonObject());
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
        // detect folded rules
        if (obj.getJsonObject("property") != null) {
            this.determineAndAddOwlEntity(obj.getJsonObject("property"));
        }
        if (obj.getJsonObject("relation") != null) {
            this.determineAndAddOwlEntity(obj.getJsonObject("relation"));
        }
        if (obj.getJsonObject("domainclass") != null) {
            this.determineAndAddOwlEntity(obj.getJsonObject("domainclass"));
        }
        if (obj.getJsonObject("rangeclass") != null) {
            this.determineAndAddOwlEntity(obj.getJsonObject("rangeclass"));
        }
        //
        JsonObject cls = obj.getJsonObject("cls");
        if (cls != null) {
            this.determineAndAddOwlEntity(cls);
        }
        JsonArray classarray = obj.getJsonArray("classarray");
        if (classarray != null) {
            classarray.forEach((obj2) -> {
                this.determineAndAddOwlEntity((JsonObject) obj2);
            });;
        }
        JsonArray proparray = obj.getJsonArray("proparray");
        if (proparray != null) {
            proparray.forEach((obj2) -> {
                this.determineAndAddOwlEntity((JsonObject) obj2);
            });;
        }
        JsonArray relationarray = obj.getJsonArray("relationarray");
        if (relationarray != null) {
            relationarray.forEach((obj2) -> {
                this.determineAndAddOwlEntity((JsonObject) obj2);
            });;
        }
    }

    private void addOwlEntity(JsonObject obj, String type) {
        JsonObject newowlentity = new JsonObject();
        newowlentity.put("pid", obj.getString("pid"));
        newowlentity.put("index", obj.getInteger("index"));
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