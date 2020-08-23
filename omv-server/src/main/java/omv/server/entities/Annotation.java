package omv.server.entities;

import java.util.ArrayList;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class Annotation {
    public String property;
    public String value;
    public String type;
    public String lang;

    public Annotation() {
        this.property = "";
        this.value = "";
        this.type = "";
        this.lang = "";
    }

    public JsonObject toJsonObject() {
        JsonObject jsonobject = new JsonObject();
        jsonobject.put("property", this.property);
        jsonobject.put("value", this.value);
        jsonobject.put("type", this.type);
        jsonobject.put("lang", this.lang);
        return jsonobject;
    }

    public static Annotation fromStrings (String propertystring, String valuestring) {
        Annotation annotation = new Annotation();
        annotation.property = propertystring;
        String s = valuestring;
        String[] sarray = s.split("\"");
        if (sarray.length > 1) {
            annotation.value = sarray[1];
        }
        sarray = s.split("^^");
        if (sarray.length > 1) {
            annotation.type = sarray[1].split("@")[0];
        }
        sarray = s.split("@");
        if (sarray.length > 1) {
            annotation.lang = sarray[1];
        }
        return annotation;
    }

    //// methods for ArrayList<Annotation> ////

    public static String getLabel(ArrayList<Annotation> annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.property.equals("rdfs:label")) {
                return annotation.value;
            }
        }
        return "";
    }

    public static JsonArray listToJsonArray(ArrayList<Annotation> annotations) {
        JsonArray jsonarray = new JsonArray();
        for (Annotation annotation : annotations) {
            jsonarray.add(annotation.toJsonObject());
        }
        return jsonarray;
    }
}