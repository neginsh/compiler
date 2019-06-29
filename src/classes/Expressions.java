package classes;

import java.util.Hashtable;

public class Expressions {
    public Exception parent;
    public Hashtable<String, String> table; // name , type

    public Expressions(Exception parent) {
        this.parent = parent;
        table = new Hashtable<>();
    }

    public Expressions() {
        table = new Hashtable<>();
    }
}
