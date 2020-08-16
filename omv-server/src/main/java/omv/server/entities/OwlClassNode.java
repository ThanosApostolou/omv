package omv.server.entities;

import java.util.ArrayList;
import java.util.Comparator;

import org.semanticweb.owlapi.model.OWLClass;

public class OwlClassNode {
    public OWLClass owlclass;
    public String id;
    public String name;
    public ArrayList<OwlClassNode> children;

    public OwlClassNode() {
        this.owlclass = null;
        this.id = null;
        this.name = null;
        this.children = new ArrayList<OwlClassNode>();
    }
    public void create(OWLClass owlclass) {
        this.owlclass = owlclass;
        this.id = owlclass.toStringID();
        this.name = this.id.split("#")[1];
    }

    public String getName() {
        return this.name;
    }

    public void sortChildren() {
        this.children.sort(Comparator.comparing(OwlClassNode::getName));
    }

    public void addChild(OwlClassNode child) {
        this.children.add(child);
    }

    public String toString(String dashes) {
        String result = dashes+this.name+"\n";
        for (OwlClassNode child : this.children) {
            result += child.toString(dashes+"-");
        }
        return result;
    }

    public String toString() {
        return this.toString("");
    }
}