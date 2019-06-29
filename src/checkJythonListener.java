import classes.*;
import classes.Class;
import gen.*;
import javafx.util.Pair;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class provides an empty implementation of {@link jythonListener},
 * which can be extended to create a listener which only needs to handle a subset
 * of the available methods.
 */

public class checkJythonListener extends MyJythonListener {
    private SymbolTable currentTable;

    public checkJythonListener(SymbolTable currentScope)
    {
        super(currentScope);
        currentTable = currentScope;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterProgram(jythonParser.ProgramContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitProgram(jythonParser.ProgramContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterImportClass(jythonParser.ImportClassContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitImportClass(jythonParser.ImportClassContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterClassDec(jythonParser.ClassDecContext ctx) {
        String className = ctx.USER_TYPE(0).getText();
        thisClass = (Class) currentScope.table.get(new Pair<>(Kind.Class, className));
        if (ctx.USER_TYPE().size() > 1) {
            String parentName = ctx.USER_TYPE(1).getText();
            checkParent(parentName);
        }
        currentScope = thisClass.symbolTable;
    }

    private void checkParent(String parentName) {
        Class parentClass = (Class) currentScope.table.get(new Pair<>(Kind.Class, parentName));
        Class currentClass = thisClass;
        if (parentClass.misty) {
            System.out.println("Error106 : in line " + parentClass.line + " , cannot find class " + parentName);
        } else {
            thisClass.parent = parentClass;
            thisClass.symbolTable.parent = parentClass.symbolTable;

            ArrayList<Class> inherit = new ArrayList<>();
            while (parentClass != null) {
                if (thisClass.name.equals(parentClass.name)) {
                    System.out.print("Error107 : Invalid inheritance " + thisClass.name);
                    inherit.add(parentClass);
                    for (Class aClass : inherit)
                        System.out.print(" -> " + aClass.name);
                    System.out.println();
                    break;
                } else {
                    inherit.add(parentClass);
                    currentClass.parent = parentClass;
                    currentClass.symbolTable.parent = parentClass.symbolTable;
                    currentClass = parentClass;
                    if (parentClass.parent == null)
                        break;
                    parentClass = (Class) root.table.get(new Pair<>(Kind.Class, parentClass.parent.name));
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitClassDec(jythonParser.ClassDecContext ctx) {
        thisClass = null;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterClassBody(jythonParser.ClassBodyContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitClassBody(jythonParser.ClassBodyContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @SuppressWarnings("Duplicates")
    @Override
    public void enterVarDec(jythonParser.VarDecContext ctx) {
        String varType = ctx.type().getText();
        String varName = ctx.ID().getText();
        if (!Arrays.asList(primitiveTypes).contains(varType)) {
            Variable variable = (Variable) currentScope.table.get(new Pair<>(Kind.Variable, varName));
            if (variable.misty) {
                Class type = (Class) root.table.get(new Pair<>(Kind.Class, varType));
                if (!type.misty)
                    variable.misty = false;
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitVarDec(jythonParser.VarDecContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterArrayDec(jythonParser.ArrayDecContext ctx) {
        String arrayType = ctx.type().getText();
        String arrayName = ctx.ID().getText();
        if (!Arrays.asList(primitiveTypes).contains(arrayType)) {
            Array array = (Array) currentScope.table.get(new Pair<>(Kind.Variable, arrayName));
            if (array.misty) {
                Class type = (Class) root.table.get(new Pair<>(Kind.Class, arrayType));
                if (!type.misty)
                    array.misty = false;
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @SuppressWarnings("Duplicates")
    @Override
    public void exitArrayDec(jythonParser.ArrayDecContext ctx) {

    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterMethodDec(jythonParser.MethodDecContext ctx) {

    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitMethodDec(jythonParser.MethodDecContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterReturnType(jythonParser.ReturnTypeContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitReturnType(jythonParser.ReturnTypeContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterConstructor(jythonParser.ConstructorContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitConstructor(jythonParser.ConstructorContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterParameter(jythonParser.ParameterContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitParameter(jythonParser.ParameterContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterParameters(jythonParser.ParametersContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitParameters(jythonParser.ParametersContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterStatement(jythonParser.StatementContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitStatement(jythonParser.StatementContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterReturnStatement(jythonParser.ReturnStatementContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitReturnStatement(jythonParser.ReturnStatementContext ctx) {
        String returnStatement = ctx.expression().getText();
        String methodName = (String) currentTable.table.get(new Pair<>(Kind.Method, "methodName"));
        Method method = (Method) currentTable.parent.table.get(new Pair<>(Kind.Method, methodName));
        if (!currentTable.expressions.containsKey(returnStatement)) {
            System.out.println("Error 230 : in line " + ctx.start.getLine() + ", return type of this method must be "
                    + method.returnType + ".");
        } else {
            String returnType = currentTable.expressions.get(returnStatement);
            if (!method.returnType.equals(returnType)) {
                System.out.println("Error 230 : in line " + ctx.start.getLine() + ", return type of this method must be "
                        + method.returnType + ".");
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterConditionList(jythonParser.ConditionListContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitConditionList(jythonParser.ConditionListContext ctx) {
        boolean bool = true;
        for (int i = 0; i < ctx.expression().size(); i++) {
            String exp = ctx.expression(i).getText();
            if (!currentTable.expressions.containsKey(exp))
                bool = false;
            else if (!currentTable.expressions.get(exp).equals("boolean"))
                bool = false;
        }
        if (!bool) {
            System.out.println("Error 220 : in line " + ctx.start.getLine() + ",Condition type must be Boolean.");
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterWhileStatement(jythonParser.WhileStatementContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitWhileStatement(jythonParser.WhileStatementContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterIfElseStatement(jythonParser.IfElseStatementContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitIfElseStatement(jythonParser.IfElseStatementContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterPrintStatement(jythonParser.PrintStatementContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitPrintStatement(jythonParser.PrintStatementContext ctx) {
        String printStatement = ctx.expression().getText();
        if (!currentTable.expressions.containsKey(printStatement)) {
            System.out.println("Error 270 : in line " + ctx.start.getLine() +
                    ", Type of parameter of print built-in function must be integer, string ,float or boolean.");
        } else {
            String statementType = currentTable.expressions.get(printStatement);
            if (!statementType.equals("float") && !statementType.equals("int") && !statementType.equals("string") && !statementType.equals("boolean")) {
                System.out.println("Error 270 : in line " + ctx.start.getLine() +
                        ", Type of parameter of print built-in function must be integer, string ,float or boolean.");
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterForStatement(jythonParser.ForStatementContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitForStatement(jythonParser.ForStatementContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @SuppressWarnings("Duplicates")
    @Override
    public void enterMethodCall(jythonParser.MethodCallContext ctx) {
        if (ctx.leftExp() == null) {
            String methodName = ctx.ID().getText();
            boolean find = false;
            SymbolTable classTable = thisClass.symbolTable;
            while (classTable.parent != null && !find) {
                if (classTable.table.containsKey(new Pair<>(Kind.Method, methodName)))
                    find = true;
                else
                    classTable = classTable.parent;
            }
            if (!find) {
                classTable.table.remove(new Pair<>(Kind.Method, methodName));
                System.out.println("Error 105 : in line " + ctx.start.getLine() + ", Can not find method " + methodName);
            }
        }

    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitMethodCall(jythonParser.MethodCallContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterAssignment(jythonParser.AssignmentContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitAssignment(jythonParser.AssignmentContext ctx) {
        if (ctx.arrayDec() != null) { // array assignment
            String parameter = ctx.expression().getText();
            if (!currentTable.expressions.get(parameter).equals("int")) {
                System.out.println("Error 210 : in line " + ctx.start.getLine() + ", size of an array must be of type integer");
            }
            String arrayType = ctx.arrayDec().type().getText();
            String type = ctx.type().getText();
            checkAssignmentType(ctx, arrayType, type);
        } else if (ctx.varDec() != null) { // var assignment
            String vatType = ctx.varDec().type().getText();
            String parameter = ctx.expression().getText();
            String parameterType = currentTable.expressions.get(parameter);
            checkAssignmentType(ctx, vatType, parameterType);
        }
    }

    private void checkAssignmentType(jythonParser.AssignmentContext ctx, String varType, String parameterType) {
        Class typeClass = (Class) root.table.get(new Pair<>(Kind.Class, parameterType));
        if (typeClass != null) {
            boolean isChild = false;
            Class parentClass = typeClass.parent;
            while (parentClass.parent != null) {
                if (parentClass.name.equals(varType)) {
                    isChild = true;
                    break;
                }
            }
            if (!isChild && !parameterType.equals(varType)) {
                System.out.println("Error 250 : in line " + ctx.start.getLine() + ", incompatible types : " +
                        parameterType + "can not be converted to " + varType + ".");
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterExpression(jythonParser.ExpressionContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitExpression(jythonParser.ExpressionContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterRightExp(jythonParser.RightExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitRightExp(jythonParser.RightExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterLeftExp(jythonParser.LeftExpContext ctx) {
        if (ctx.getText().startsWith("self.")) {
            currentTable = new SymbolTable(currentTable);
            currentTable.table = thisClass.symbolTable.table;
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    //TODO: This must be over read and complete
    @SuppressWarnings("Duplicates")
    @Override
    public void exitLeftExp(jythonParser.LeftExpContext ctx) {
        System.out.println(ctx.getText());
        if (ctx.ID() != null)
            System.out.println(ctx.ID().getText());
        System.out.println("-------------------------------");

        String exp = ctx.getText();
        if (ctx.ID() == null) {
            if (exp.startsWith("self.")) {
                String exp2 = exp.substring(5);
                if (!currentTable.expressions.containsKey(exp2)) {
                    //TODO: Error
                } else {
                    // to table parent bayad exp ro add konim
                }
            }
        } else {
            if (exp.length() > ctx.ID().getText().length()) {
                String exp2 = exp.substring(0, exp.lastIndexOf(ctx.ID().getText()));
                if (!currentTable.expressions.containsKey(exp2)) {
                    //TODO: Error
                }
                else if (Arrays.asList(primitiveTypes).contains(exp2)) {
                    //TODO: no such function
                } else {
                    currentTable = (SymbolTable) root.table.get(new Pair<>(Kind.Class, exp2));
                }
            }

            if (exp.contains("(") && ctx.expression() == null) { // method
                String methodName = ctx.ID().getText();
                Variable[] variables = null;
                boolean error = false;
                if (ctx.args().expList() != null) {
                    int size = ctx.args().expList().expression().size();
                    variables = new Variable[size];
                    String[] parameters = new String[size];
                    for (int i = 0; i < size; i++) {
                        parameters[i] = ctx.args().expList().expression(i).getText();
                        if (currentTable.expressions.containsKey(parameters[i])) {
                            variables[i] = new Variable(currentTable.expressions.get(parameters[i]), parameters[i]);
                        } else {
                            System.out.println("Error 240 : in line " + ctx.start.getLine() + ", Mismatch arguments.");
                            error = true;
                            break;
                        }
                    }
                }
                Method method = new Method(methodName);
                if (variables != null) {
                    method.variables = variables;
                }
                if (!error) {
                    Method aMethod = null;
                    boolean find = false, almostFind = false;
                    while (currentTable.parent != null && !find) {
                        if (currentTable.table.containsKey(new Pair<>(Kind.Method, methodName))) {
                            almostFind = true;
                            aMethod = (Method) currentTable.table.get(new Pair<>(Kind.Method, methodName));
                            if (method.getMethodName().equals(aMethod.getMethodName())) {
                                find = true;
                            } else if (method.sameNames != null) {
                                for (Method methodi : method.sameNames) {
                                    if (method.getMethodName().equals(methodi.getMethodName())) {
                                        aMethod = methodi;
                                        find = true;
                                        break;
                                    }
                                }
                            } else {
                                currentTable = currentTable.parent;
                            }
                        } else
                            currentTable = currentTable.parent;
                    }
                    if (!find) {
                        if (almostFind)
                            System.out.println("Error 240 : in line " + ctx.start.getLine() + ", Mismatch arguments.");
                        else
                            System.out.println("Error108 : in line " + ctx.start.getLine() + " , cannot find method " + methodName);
                    } else {
                        method = aMethod;
                        currentTable.expressions.put(ctx.getText(), method.returnType);
                    }
                }
            } else if (exp.contains("[")) { // array
                String arrayName = exp.substring(0, exp.indexOf('['));
                String expressions = ctx.expression().getText();

                Variable variable = null;
                boolean find = false, varMisty = false;
                while (currentTable.parent != null && !find) {
                    if (currentTable.table.containsKey(new Pair<>(Kind.Variable, arrayName))) {
                        variable = (Variable) currentTable.table.get(new Pair<>(Kind.Variable, arrayName));
                        if (variable.type.contains("[")) {
                            find = true;
                            varMisty = variable.misty;
                        } else {
                            currentTable = currentTable.parent;
                        }
                    } else
                        currentTable = currentTable.parent;
                }
                if (!find || varMisty) {
                    System.out.println("Error108 : in line " + ctx.start.getLine() + " , cannot find variable " + arrayName);
                } else {
                    currentTable.expressions.put(variable.name, variable.type);
                }

                if (currentTable.expressions.containsKey(expressions)) { //TODO: type checking
                    String type = currentTable.expressions.get(expressions);
//                    if (!type.equals("int"))
//                        System.out.println("Error 210 : in line " + ctx.start.getLine() + ", size or index of an array must be of type integer");
                } else {

                }
            } else { // variable
                String varName = ctx.ID().getText();

                Variable variable = null;
                boolean find = false, varMisty = false;
                while (currentTable.parent != null && !find) {
                    if (currentTable.table.containsKey(new Pair<>(Kind.Variable, varName))) {
                        find = true;
                        variable = (Variable) currentTable.table.get(new Pair<>(Kind.Variable, varName));
                        varMisty = variable.misty;
                    } else
                        currentTable = currentTable.parent;
                }
                if (!find || varMisty) {
                    System.out.println("Error108 : in line " + ctx.start.getLine() + " , cannot find variable " + varName);
                } else {
                    currentTable.expressions.put(variable.name, variable.type);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterArgs(jythonParser.ArgsContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitArgs(jythonParser.ArgsContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterExpList(jythonParser.ExpListContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitExpList(jythonParser.ExpListContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterAssignmentOperators(jythonParser.AssignmentOperatorsContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitAssignmentOperators(jythonParser.AssignmentOperatorsContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterEqNeq(jythonParser.EqNeqContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitEqNeq(jythonParser.EqNeqContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterRelationOperators(jythonParser.RelationOperatorsContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitRelationOperators(jythonParser.RelationOperatorsContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterAddSub(jythonParser.AddSubContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitAddSub(jythonParser.AddSubContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterMultModDiv(jythonParser.MultModDivContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitMultModDiv(jythonParser.MultModDivContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterType(jythonParser.TypeContext ctx) {
        if (ctx.jythonType() == null) { // user type
            String type = ctx.USER_TYPE().getText();
            Class aClass = (Class) root.table.get(new Pair<>(Kind.Class, type));
            if (aClass.misty)
                System.out.println("Error106 : in line " + aClass.line + " , cannot find class " + type);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitType(jythonParser.TypeContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterJythonType(jythonParser.JythonTypeContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitJythonType(jythonParser.JythonTypeContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void visitTerminal(TerminalNode node) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void visitErrorNode(ErrorNode node) {
    }
}
