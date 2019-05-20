package classes;

public class Class {
    public String name, parentName;

    public Class(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        if (parentName == null)
            return "{Class Name: " + name + "}";
        else
            return "{Class Name: " + name + " , Parent: " + parentName + "}";
    }
}
