package classes;

public class Method extends MyObject {
    public String returnType;
    public String name;
    public Variable[] variables;

    public Method(String name) {
        this.name = name;
    }

    public Method(String name, Variable[] variables) {
        this.name = name;
        this.variables = variables;
    }

    public String getMethodName() {
        StringBuilder methodName = new StringBuilder(name + returnType);
        if (variables != null)
            for (Variable variable : variables)
                methodName.append(variable.type);
        return methodName.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass())
            return false;
        Method method = (Method) obj;
        return getMethodName().equals(method.getMethodName());
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("{name: " + name + " , return: " + returnType);
        if (variables != null) {
            string.append(" , params: ");
            for (Variable variable : variables)
                string.append(variable.type).append(" , ");
            string.delete(string.length() - 3, string.length());
        }
        return string.append("}").toString();
    }
}
