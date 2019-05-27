package classes;

public class Array extends Variable {
    public String size;

    public Array(String type, String name) {
        super(type, name);
    }

    public Array(String type, String name, String size) {
        super(type, name);
        this.size = size;
    }
}
