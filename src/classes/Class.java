package classes;

public class Class extends MyObject {
    public String name;
    public Class parent;

    public Class(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        if (parent == null)
            return "{Class Name: " + name + "}";
        else
            return "{Class Name: " + name + " , Parent: " + parent.name + "}";
    }
}
