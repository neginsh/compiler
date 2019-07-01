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

public class CheckJythonListener extends MyJythonListener {
    private LastVariable lastVariable;

    public CheckJythonListener(SymbolTable currentScope) {
        super(currentScope);
        lastVariable = new LastVariable();
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
        currentScope = currentScope.parent;
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
        String methodName = ctx.ID().getText();
        Method method = (Method) currentScope.table.get(new Pair<>(Kind.Method, methodName));
        currentScope = method.symbolTable;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitMethodDec(jythonParser.MethodDecContext ctx) {

        currentScope = currentScope.parent;

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
        Method constructor = (Method) currentScope.table.get(new Pair<>(Kind.Method, "constructor"));
        currentScope = constructor.symbolTable;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitConstructor(jythonParser.ConstructorContext ctx) {
        currentScope = currentScope.parent;
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

    //editedddddddddddddddddddddddddddd
    public void exitReturnStatement(jythonParser.ReturnStatementContext ctx) {
        String returnStatement = ctx.expression().getText();
        String methodName = (String) currentScope.table.get(new Pair<>(Kind.Method, "methodName"));
        Method method = (Method) currentScope.parent.table.get(new Pair<>(Kind.Method, methodName));

        if (returnStatement.contains("(")) {

            SymbolTable currentTable = currentScope;
            returnStatement = returnStatement.substring(0, returnStatement.indexOf("("));
            while (currentTable.parent != null) {

                if (currentTable.table.containsKey(new Pair<>(Kind.Method, returnStatement))) {
                    Method method2 = (Method) currentTable.table.get(new Pair<>(Kind.Method, returnStatement));
                    if (!method.returnType.equals(method2.returnType)) {
                        System.out.println("Error 230 : in line " + ctx.start.getLine() + ", return type of this method must be "
                                + method.returnType + ".");
                    }
                    break;
                } else {
                    currentTable = currentTable.parent;
                }

            }


        } else {

            if (!currentScope.expressions.containsKey(returnStatement)) {
                System.out.println("Error 230 : in line " + ctx.start.getLine() + ", return type of this method must be "
                        + method.returnType + ".");
            } else {
                String returnType = currentScope.expressions.get(returnStatement);
                if (!method.returnType.equals(returnType)) {
                    System.out.println("Error 230 : in line " + ctx.start.getLine() + ", return type of this method must be "
                            + method.returnType + ".");
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
    public void enterConditionList(jythonParser.ConditionListContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override

    //editedddddddddddddddddddddddddddd
    public void exitConditionList(jythonParser.ConditionListContext ctx) {
        boolean bool = true;
        for (int i = 0; i < ctx.expression().size(); i++) {
            String exp = ctx.expression(i).getText();
            if (!exp.equals("true") && !exp.equals("false"))
                if (exp.contains("(")) {

                    SymbolTable currentTable = currentScope;
                    exp = exp.substring(0, exp.indexOf("("));
                    while (currentTable.parent != null) {

                        if (currentTable.table.containsKey(new Pair<>(Kind.Method, exp))) {
                            Method method = (Method) currentTable.table.get(new Pair<>(Kind.Method, exp));
                            if (!method.returnType.equals("bool")) {
                                System.out.println("Error 220 : in line " + ctx.start.getLine() + ", Condition type must be boolean.");
                            }
                            break;
                        } else {
                            currentTable = currentTable.parent;
                        }

                    }

                } else if (!currentScope.expressions.containsKey(exp))
                    bool = false;
                else if (!currentScope.expressions.get(exp).equals("bool"))
                    bool = false;
        }
        if (!bool) {
            System.out.println("Error 220 : in line " + ctx.start.getLine() + ", Condition type must be boolean.");
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterWhileStatement(jythonParser.WhileStatementContext ctx) {
        SymbolTable whileTable;
        String whileName = "while" + ctx.start.getLine() + ctx.start.getCharPositionInLine();
        whileTable = (SymbolTable) currentScope.table.get(new Pair<>(Kind.Scope, whileName));
        currentScope = whileTable;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitWhileStatement(jythonParser.WhileStatementContext ctx) {
        currentScope = currentScope.parent;
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
        if (!currentScope.expressions.containsKey(printStatement)) {
            System.out.println("Error 270 : in line " + ctx.start.getLine() +
                    ", Type of parameter of print built-in function must be integer, string ,float or boolean.");
        } else {
            String statementType = currentScope.expressions.get(printStatement);
            if (!statementType.equals("float") && !statementType.equals("int") && !statementType.equals("string") && !statementType.equals("bool")) {
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
        SymbolTable forTable;
        String forName = "for" + ctx.start.getLine() + ctx.start.getCharPositionInLine();
        forTable = (SymbolTable) currentScope.table.get(new Pair<>(Kind.Scope, forName));
        currentScope = forTable;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitForStatement(jythonParser.ForStatementContext ctx) {
        currentScope = currentScope.parent;
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
    @SuppressWarnings("Duplicates")
    @Override


    public void exitAssignment(jythonParser.AssignmentContext ctx) {
        String rightType, leftType;
        if (ctx.type() != null) { // array assignment
            if (ctx.expression() != null) { //check array size type
                String arraySize = ctx.expression().getText();
                if (arraySize.contains("(")){
                    String parameter = arraySize;
                    SymbolTable currentTable = currentScope;
                    parameter = parameter.substring(0, parameter.indexOf("("));
                    while (currentTable.parent != null) {

                        if (currentTable.table.containsKey(new Pair<>(Kind.Method, parameter))) {
                            Method method = (Method) currentTable.table.get(new Pair<>(Kind.Method, parameter));
                            if (!method.returnType.equals("int")) {
                                System.out.println("Error 210 : in line " + ctx.start.getLine() + ", size of an array must be of type integer");
                            }
                            break;
                        } else {
                            currentTable = currentTable.parent;
                        }

                    }

                }else {
                    String sizeType = currentScope.expressions.get(arraySize);
                    if (sizeType == null) {
                        System.out.println("Error 210 : in line " + ctx.start.getLine() + ", size of an array must be of type integer");
                    } else if (!sizeType.equals("int")) {
                        System.out.println("Error 210 : in line " + ctx.start.getLine() + ", size of an array must be of type integer");
                    }
                }
            }

            rightType = ctx.type().getText();
            if (ctx.arrayDec() != null) {
                leftType = ctx.arrayDec().type().getText();
            } else if (ctx.varDec() != null) {
                leftType = ctx.varDec().type().getText();
            } else { // left Exp
                String leftExp = ctx.leftExp().getText();
                leftType = currentScope.expressions.get(leftExp);
            }
        } else {
            String rightExp = ctx.expression().getText();
            if (rightExp.equals("none"))
                return;
            rightType = currentScope.expressions.get(rightExp);
            if (ctx.varDec() != null) {
                leftType = ctx.varDec().type().getText();
            } else { // left Exp
                String leftExp = ctx.leftExp().getText();
                leftType = currentScope.expressions.get(leftExp);
            }
        }

        if (leftType == null) {
            System.out.println("Error 250 : in line " + ctx.start.getLine() + ", Incompatible types : "
                    + rightType + " can not be converted to undefined.");
            return;
        }

        if (!leftType.equals(rightType)) {
            if (root.table.containsKey(new Pair<>(Kind.Class, rightType))) {
                Class rightClass = (Class) root.table.get(new Pair<>(Kind.Class, rightType));
                boolean match = false;
                while (!match && rightClass.parent != null) {
                    if (rightClass.name.equals(leftType))
                        match = true;
                    else
                        rightClass = rightClass.parent;
                }
                if (!match) {
                    System.out.println("Error 250 : in line " + ctx.start.getLine() + ", Incompatible types : "
                            + rightType + " can not be converted to " + leftType + ".");
                }
            } else {
                System.out.println("Error 250 : in line " + ctx.start.getLine() + ", Incompatible types : "
                        + rightType + " can not be converted to " + leftType + ".");
            }
        }

        if (ctx.assignmentOperators() != null && !ctx.assignmentOperators().getText().equals("=")) {
            if (!(rightType.equals("int") || rightType.equals("float")) || !(leftType.equals("int") || leftType.equals("float")))
                System.out.println("Error 280 : in line " + ctx.start.getLine() + ", operation not defined on this types.");
        }

    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterExpression(jythonParser.ExpressionContext ctx) {
        lastVariable = new LastVariable(lastVariable);

    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @SuppressWarnings("Duplicates")
    @Override
    public void exitExpression(jythonParser.ExpressionContext ctx) {
        lastVariable = lastVariable.parent;

        if (ctx.rightExp() == null) {
            String exp = "(" + ctx.getText() + ")";
            String exp1 = ctx.expression(0).getText();
            boolean find1 = false;
            SymbolTable currentTable = currentScope;
            while (!find1 && currentTable.parent != null) {
                if (currentTable.expressions.containsKey(exp1)) {
                    find1 = true;
                    exp1 = currentTable.expressions.get(exp1);
                } else
                    currentTable = currentTable.parent;
            }

            String exp2 = ctx.expression(1).getText();
            boolean find2 = false;
            currentTable = currentScope;
            while (!find2 && currentTable.parent != null) {
                if (currentTable.expressions.containsKey(exp2)) {
                    find2 = true;
                    exp2 = currentTable.expressions.get(exp2);
                } else
                    currentTable = currentTable.parent;
            }

            if (!find1 || !find2) {
                currentScope.expressions.put(ctx.getText(), "undefined");
                return;
            }

            boolean intFloatNot = !(exp1.equals("int") || exp1.equals("float")) || !(exp2.equals("int") || exp2.equals("float"));
            if (ctx.multModDiv() != null || ctx.addSub() != null) {
                if (intFloatNot) {
                    System.out.println("Error 280 : in line " + ctx.start.getLine() + ", operation not defined on this types.");
                    currentScope.expressions.put(ctx.getText(), "undefined");
                    if (lastVariable.name != null && lastVariable.name.equals(exp))
                        lastVariable.type = "undefined";
                } else if (exp1.equals("float") || exp2.equals("float")) {
                    currentScope.expressions.put(ctx.getText(), "float");
                    if (lastVariable.name != null && lastVariable.name.equals(exp))
                        lastVariable.type = "float";
                } else {
                    currentScope.expressions.put(ctx.getText(), "int");
                    if (lastVariable.name != null && lastVariable.name.equals(exp))
                        lastVariable.type = "int";
                }
            } else { // compare
                if (!intFloatNot) {
                    if (!exp1.equals(exp2)) {
                        System.out.println("Error 280 : in line " + ctx.start.getLine() + ", operation not defined on this types.");
                    } else {
                        currentScope.expressions.put(ctx.getText(), "bool");
                    }
                } else {
                    System.out.println("Error 280 : in line " + ctx.start.getLine() + ", operation not defined on this types.");
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
    public void enterRightExp(jythonParser.RightExpContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitRightExp(jythonParser.RightExpContext ctx) {
        if (ctx.INTEGER() != null) {
            currentScope.expressions.put(ctx.INTEGER().getText(), "int");
        } else if (ctx.FLOAT() != null) {
            currentScope.expressions.put(ctx.FLOAT().getText(), "float");
        } else if (ctx.STRING() != null) {
            currentScope.expressions.put(ctx.STRING().getText(), "string");
        } else if (ctx.USER_TYPE() != null) {
            currentScope.expressions.put(ctx.getText(), ctx.USER_TYPE().getText());
        } else if (ctx.bool() != null) {
            currentScope.expressions.put(ctx.bool().getText(), "bool");
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @SuppressWarnings("Duplicates")
    @Override
    public void enterLeftExp(jythonParser.LeftExpContext ctx) {
        SymbolTable currentTable;
        lastVariable = new LastVariable(lastVariable);

        if (ctx.getText().startsWith("self.")) {
            lastVariable.name = "self";
            lastVariable.type = thisClass.name;
            return;
        }

        if (lastVariable.name != null && lastVariable.name.equals("self")) {
            currentTable = thisClass.symbolTable;
        } else {
            currentTable = currentScope;
        }

        if (ctx.expression() == null) { // Variable --------------------------------------------------------------------
            String varName = ctx.ID().getText();
            Variable variable = null;
            Method method = null;
            boolean find = false, varMisty = false;
            while (currentTable.parent != null && !find) {
                if (ctx.args() == null && currentTable.table.containsKey(new Pair<>(Kind.Variable, varName))) {
                    find = true;
                    variable = (Variable) currentTable.table.get(new Pair<>(Kind.Variable, varName));
                    varMisty = variable.misty;
                }else if (ctx.args() != null && currentTable.table.containsKey(new Pair<>(Kind.Method, varName))) {
                    find = true;
                    method = (Method) currentTable.table.get(new Pair<>(Kind.Method, varName));
                } else
                    currentTable = currentTable.parent;
            }

            if (!find || varMisty) {
                System.out.println("Error108 : in line " + ctx.start.getLine() + " , cannot find variable " + varName);
                lastVariable.type = "undefined";
                lastVariable.name = varName;
            } else if (variable != null) {
                currentScope.expressions.put(variable.name, variable.type);
                lastVariable.type = variable.type;
                lastVariable.name = variable.name;
            } else if (method != null) {
                currentScope.expressions.put(method.name, method.returnType);
                lastVariable.type = method.returnType;
                lastVariable.name = method.name;
            }
        } else  {
            lastVariable.name = "(" + ctx.expression().getText() + ")";
        }


        String s = ctx.getText().substring(0, ctx.getText().indexOf(ctx.leftExpEnd().getText()));
        //System.out.println("--- " + s);
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
        lastVariable = lastVariable.parent;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @SuppressWarnings("Duplicates")
    @Override
    public void enterLeftExpEnd(jythonParser.LeftExpEndContext ctx) {
        if (ctx.leftExpEnd() != null)
            if (ctx.getText().startsWith("."))
                if (ctx.leftExpEnd() != null) {
                    //System.out.println(ctx.ID().getText());
                    SymbolTable currentTable;
                    String className = lastVariable.type;
                    Class typeClass;
                    if (root.table.containsKey(new Pair<>(Kind.Class, className))) {
                        typeClass = (Class) root.table.get(new Pair<>(Kind.Class, className));
                        currentTable = typeClass.symbolTable;

                        if (ctx.args() != null) { // Method ---------------------------------------------------------
                            String methodName = ctx.ID().getText();
                            Method method = null;
                            boolean find = false, methodMisty = false;
                            while (currentTable.parent != null && !find) {
                                if (currentTable.table.containsKey(new Pair<>(Kind.Method, methodName))) {
                                    find = true;
                                    method = (Method) currentTable.table.get(new Pair<>(Kind.Method, methodName));
                                    method.misty = typeClass.misty;
                                    methodMisty = method.misty;
                                } else
                                    currentTable = currentTable.parent;
                            }
                            if (!find || methodMisty) {
                                System.out.println("Error105 : in line " + ctx.start.getLine() + " , cannot find method " + methodName);
                                lastVariable.type = "undefined";
                            } else {
                                if (lastVariable.type.equals("void")) {
                                    System.out.println("Error : return type of method is void");
                                    lastVariable.type = "undefined";
                                } else if (!lastVariable.type.equals("undefined")) {
                                    String expressionName = lastVariable.name + "." + ctx.getText().substring(1, ctx.getText().indexOf(ctx.args().getText()) + ctx.args().getText().length());
                                    currentScope.expressions.put(expressionName, method.returnType);
                                    lastVariable.type = method.returnType;
                                    lastVariable.name = expressionName;
                                }
                            }
                        } else { // Variable -------------------------------------------------------------------------------
                            String varName = ctx.ID().getText();
                            Variable variable = null;
                            boolean find = false, varMisty = false;
                            while (currentTable.parent != null && !find) {
                                if (currentTable.table.containsKey(new Pair<>(Kind.Variable, varName))) {
                                    find = true;
                                    variable = (Variable) currentTable.table.get(new Pair<>(Kind.Variable, varName));
                                    variable.misty = typeClass.misty;
                                    varMisty = variable.misty;
                                } else
                                    currentTable = currentTable.parent;
                            }
                            if (!find || varMisty) {
                                System.out.println("Error108 : in line " + ctx.start.getLine() + " , cannot find variable " + varName);
                                lastVariable.type = "undefined";
                            } else {
                                if (lastVariable.type.equals("void")) {
                                    System.out.println("Error : return type of method is void");
                                    lastVariable.type = "undefined";
                                } else if (!lastVariable.type.equals("undefined")) {
                                    String expressionName = lastVariable.name + "." + variable.name;
                                    currentScope.expressions.put(expressionName, variable.type);
                                    lastVariable.type = variable.type;
                                    lastVariable.name = expressionName;
                                }
                            }
                        } // Variable --------------------------------------------------------------------------------------
                    } else {
                        System.out.println("Error108 : in line " + ctx.start.getLine() + ", Can not find Variable " + ctx.ID().getText());
                    }
                } else {
//                    System.out.println(ctx.getText().substring(1));
                }
            else { // Array --------------------------------------------------------------------------------------------
                if (lastVariable.type.contains("[")) {
                    lastVariable.name = lastVariable.name + ctx.getText().substring(0, ctx.getText().indexOf(ctx.expression().getText()) + ctx.expression().getText().length() + 1);
                    lastVariable.type = lastVariable.type.substring(0, lastVariable.type.indexOf("["));
                    currentScope.expressions.put(lastVariable.name, lastVariable.type);
                } else {
                    System.out.println("Error108 : in line " + ctx.start.getLine() + " , Can not find Variable " + lastVariable.name.substring(lastVariable.name.lastIndexOf(".") + 1));
                }
            }
//                if (ctx.leftExpEnd() != null)
//                    System.out.println(ctx.getText().substring(0, ctx.getText().indexOf(ctx.leftExpEnd().getText())));
//                else
//                    System.out.println(ctx.getText());
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitLeftExpEnd(jythonParser.LeftExpEndContext ctx) {

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

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterBool(jythonParser.BoolContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitBool(jythonParser.BoolContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterIfStatement(jythonParser.IfStatementContext ctx) {
        SymbolTable ifTable;
        String ifName = "if" + ctx.start.getLine() + ctx.start.getCharPositionInLine();
        ifTable = (SymbolTable) currentScope.table.get(new Pair<>(Kind.Scope, ifName));
        currentScope = ifTable;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitIfStatement(jythonParser.IfStatementContext ctx) {
        currentScope = currentScope.parent;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterElseIfStatement(jythonParser.ElseIfStatementContext ctx) {
        SymbolTable elseIfTable;
        String elseIfName = "elseIf" + ctx.start.getLine() + ctx.start.getCharPositionInLine();
        elseIfTable = (SymbolTable) currentScope.table.get(new Pair<>(Kind.Scope, elseIfName));
        currentScope = elseIfTable;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitElseIfStatement(jythonParser.ElseIfStatementContext ctx) {
        currentScope = currentScope.parent;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterElseStatement(jythonParser.ElseStatementContext ctx) {
        SymbolTable elseTable;
        String elseName = "else" + ctx.start.getLine() + ctx.start.getCharPositionInLine();
        elseTable = (SymbolTable) currentScope.table.get(new Pair<>(Kind.Scope, elseName));
        currentScope = elseTable;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitElseStatement(jythonParser.ElseStatementContext ctx) {
        currentScope = currentScope.parent;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterForVariable(jythonParser.ForVariableContext ctx) {
        String counterName = ctx.ID().getText();
        Variable counter = new Variable(counterName, "int");
        if (ctx.leftExp() != null) {
            String counterType = currentScope.expressions.get(ctx.leftExp().getText());
            counter = new Variable(counterName, counterType);
        }
        currentScope.table.put(new Pair<>(Kind.Variable, counterName), counter);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitForVariable(jythonParser.ForVariableContext ctx) { }
}
