package classes;

public class Variable extends MyObject {
    public String type, name;

    public Variable(String type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return "{type: " + type + " , name: " + name + "}";
    }
}
