package classes;

public class Variable {
    public String type, name;
    public boolean misty;

    public Variable(String type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return "{type: " + type + " , name: " + name + "}";
    }
}
