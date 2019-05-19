package classes;

import java.util.Hashtable;

public class SymbolTable {
    public SymbolTable parent;
    public Hashtable<String, String> table;

    public SymbolTable() {
        this.table = new Hashtable();
    }

    public SymbolTable(SymbolTable parent) {
        this.parent = parent;
        this.table = new Hashtable();
    }
}
