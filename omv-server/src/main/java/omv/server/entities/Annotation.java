package omv.server.entities;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.OWLAnnotation;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class Annotation {
    OWLAnnotation owlannotation;
    public String property;
    public String value;
    public String type;
    public String lang;
    public Boolean isValueIri;

    public Annotation() {
        this.property = "";
        this.value = "";
        this.type = "";
        this.lang = "";
        this.isValueIri = false;
    }

    public JsonObject toJsonObject() {
        JsonObject jsonobject = new JsonObject();
        jsonobject.put("property", this.property);
        jsonobject.put("value", this.value);
        jsonobject.put("type", this.type);
        jsonobject.put("lang", this.lang);
        jsonobject.put("isValueIri", this.isValueIri);
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
        sarray = s.split("\\^\\^");
        if (sarray.length > 1) {
            annotation.type = sarray[1].split("\\@")[0];
        }
        sarray = s.split("\\@");
        if (sarray.length > 1) {
            annotation.lang = sarray[1];
        }
        return annotation;
    }
    public static Annotation fromOwlAnnotation(OWLAnnotation owlannotation) {
        Annotation annotation = new Annotation();
        annotation.owlannotation = owlannotation;
        annotation.property = owlannotation.getProperty().getIRI().getIRIString();
        if (owlannotation.getValue().asIRI().isPresent()) {
            annotation.value = owlannotation.getValue().asIRI().get().getIRIString();
            annotation.isValueIri = true;
            //System.out.println(annotation.property);
            //System.out.println(owlannotation.getValue().asIRI().toString());
            //System.out.println();
        } else {
            String s = owlannotation.getValue().toString();
            String[] sarray = s.split("\"");
            if (sarray.length > 1) {
                annotation.value = sarray[1];
            }
            sarray = s.split("\\^\\^");
            if (sarray.length > 1) {
                annotation.type = sarray[1].split("\\@")[0];
            }
            sarray = s.split("\\@");
            if (sarray.length > 1) {
                annotation.lang = sarray[1];
            }
        }
        return annotation;
    }

    //// methods for ArrayList<Annotation> ////

    public static String getLabel(ArrayList<Annotation> annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.owlannotation.getProperty().isLabel()) {
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