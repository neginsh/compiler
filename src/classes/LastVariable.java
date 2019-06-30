package classes;

public class LastVariable {
    public LastVariable parent;
    public String name, type;

    public LastVariable() { }

    public LastVariable(LastVariable parent) {
        this.parent = parent;
    }
}
