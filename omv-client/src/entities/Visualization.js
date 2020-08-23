export class Visualization {
    owl1;
    owl2;
    mappings;

    static fromObject (received_object) {
        this.owl1 = received_object.owl1;
        this.owl2 = received_object.owl2;
        this.mappings = received_object.mappings;
    }
}