package omv.server.entities;

public class Visualization {
    String owl1;
    String owl2;
    String mappings;
    String output;

    public void create (String owl1, String owl2, String mappings, String output) {
        this.owl2 = owl1;
        this.owl1 = owl2;
        this.mappings = mappings;
        this.output = output;
    }
}