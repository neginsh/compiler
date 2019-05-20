package classes;

public class Method {
    public String name;
    public Variable[] variables;

    public Method(String name) {
        this.name = name;
    }

    public Method(String name, Variable[] variables) {
        this.name = name;
        this.variables = variables;
    }
}
