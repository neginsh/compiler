package classes;

import javafx.util.Pair;
import java.util.Hashtable;

public class SymbolTable {
    public SymbolTable parent;
    public Hashtable<Pair<Kind, String>, Object> table;
    public Hashtable<String, String> expressions; // name , type

    public SymbolTable() {
        this.table = new Hashtable<>();
    }

    public SymbolTable(SymbolTable parent) {
        this.parent = parent;
        this.table = new Hashtable<>();
        this.expressions = new Hashtable<>();
    }

}
