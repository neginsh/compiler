import classes.Class;
import classes.*;
import gen.jythonListener;
import gen.jythonParser;
import javafx.util.Pair;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Arrays;


/**
 * This class provides an empty implementation of {@link jythonListener},
 * which can be extended to create a listener which only needs to handle a subset
 * of the available methods.
 */
public class MyJythonListener implements jythonListener {
    public String[] primitiveTypes = {"int", "float", "bool", "string"};
    public SymbolTable currentScope, root;
    public Class thisClass;

    public MyJythonListener(SymbolTable currentScope) {
        this.currentScope = currentScope;
        this.root = currentScope;
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
        boolean mistyClass = false;
        if (currentScope.table.containsKey(new Pair<>(Kind.Class, className))) {
            Class parentClass = (Class) currentScope.table.get(new Pair<>(Kind.Class, className));
            mistyClass = parentClass.misty;
            if (mistyClass) {
                currentScope.table.remove(new Pair<>(Kind.Class, className));
            } else {
                System.out.println("Error101: in line " + ctx.start.getLine() + ", " + className + " has been defined already");
                className += "(duplicate)";
            }
        }

        Class newClass = new Class(className);
        Class parentClass = null;
        if (ctx.USER_TYPE().size() > 1) {
            String parentName = ctx.USER_TYPE(1).getText();
            if (className.equals(parentName)) {
                System.out.println("Error107 : Invalid inheritance " + className + " -> " + parentName);
            }
            else if (currentScope.table.containsKey(new Pair<>(Kind.Class, parentName))) {
                parentClass = (Class) currentScope.table.get(new Pair<>(Kind.Class, parentName));
                newClass.parent = parentClass;
            } else {
                parentClass = new Class(parentName);
                parentClass.misty = true;
                newClass.parent = parentClass;
                currentScope.table.put(new Pair<>(Kind.Class, parentName), parentClass);
            }
        }

        currentScope.table.put(new Pair<>(Kind.Class, className), newClass);
        if (parentClass != null && !parentClass.misty) {
            SymbolTable parentTable = parentClass.symbolTable;
            currentScope = new SymbolTable(parentTable);
        } else {
            currentScope = new SymbolTable(currentScope);
        }

        if (mistyClass) { // set parent for classes witch extends from this class
            for (Object thisObj : root.table.values()) {
                Class thisClass = (Class) thisObj;
                if (thisClass.parent != null && thisClass.parent.name.equals(className))
                    thisClass.parent = newClass;
            }
        }

        newClass.symbolTable = currentScope;
        currentScope.table.put(new Pair<>(Kind.Class, "className"), className);
        thisClass = newClass;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitClassDec(jythonParser.ClassDecContext ctx) {
        if (!currentScope.table.keySet().contains(new Pair<>(Kind.Method, "main"))) {
            System.out.println("Error104 : in line " + ctx.stop.getLine() + " , Can not find main method in class " +
                    currentScope.table.get(new Pair<>(Kind.Class, "className")));
        }

        for (Pair<Kind, String> key : currentScope.table.keySet()) {
            Object object = currentScope.table.get(key);
            if (object.getClass() != String.class) {
                MyObject myObject = (MyObject) object;
                if (myObject.misty) {

                } else
                    System.out.println(key + " : " + currentScope.table.get(key));
            }
            else
                System.out.println(key + " : " + currentScope.table.get(key));
        }
        currentScope = currentScope.parent;
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
        String varName = ctx.ID().getText();
        if (currentScope.table.keySet().contains(new Pair<>(Kind.Variable, varName))) {
            System.out.println("Error102: in line " + ctx.start.getLine() + ", " + varName + " has been defined already in " +
                    currentScope.table.get(new Pair<>(Kind.Class, "className")));
            varName += "(duplicate " + ctx.start.getLine() + ")";
        }

        String varType = ctx.type().getText();
        Variable variable = new Variable(varType, varName);
        if (!Arrays.asList(primitiveTypes).contains(varType)) {
            if (!root.table.containsKey(new Pair<>(Kind.Class, varType)))
                variable.misty = true;
            else {
                Class varClass = (Class) root.table.get(new Pair<>(Kind.Class, varType));
                variable.misty = varClass.misty;
            }
        }
        currentScope.table.put(new Pair<>(Kind.Variable, varName), variable);
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
    @SuppressWarnings("Duplicates")
    @Override
    public void enterArrayDec(jythonParser.ArrayDecContext ctx) {
        String arrayName = ctx.ID().getText();
        if (currentScope.table.keySet().contains(new Pair<>(Kind.Variable, arrayName))) {
            System.out.println("Error102: in line " + ctx.start.getLine() + ", " + arrayName + " has been defined already in " +
                    currentScope.table.get(new Pair<>(Kind.Class, "className")));
            arrayName += "(duplicate " + ctx.start.getLine() + ")";
        }

        String arrayType = ctx.type().getText();
        Array array;
        if (ctx.expression() != null) {
            String size = ctx.expression().getText();
            array = new Array(arrayType, arrayName, size);
        } else {
            array = new Array(arrayType, arrayName);
        }
        if (!Arrays.asList(primitiveTypes).contains(arrayType)) {
            if (!root.table.containsKey(new Pair<>(Kind.Class, arrayType)))
                array.misty = true;
            else {
                Class varClass = (Class) root.table.get(new Pair<>(Kind.Class, arrayType));
                if (varClass.misty)
                    array.misty = true;
            }
        }
        array.type += "[]";
        currentScope.table.put(new Pair<>(Kind.Variable, arrayName), array);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
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
        String returnType = ctx.returnType().getText();
        Variable[] variables = null;

        if (ctx.parameters() != null) {
            int parametersNumber = ctx.parameters().parameter().size();
            variables = new Variable[parametersNumber];
            for (int i = 0; i < parametersNumber; i++) {
                String varType = "";
                String varName = "";

                if (ctx.parameters().parameter(i).varDec() != null) {
                    varType = ctx.parameters().parameter(i).varDec().type().getText();
                    varName = ctx.parameters().parameter(i).varDec().ID().getText();
                } else if (ctx.parameters().parameter(i).arrayDec() != null) {
                    varType = ctx.parameters().parameter(i).arrayDec().type().getText() + "[]";
                    varName = ctx.parameters().parameter(i).arrayDec().ID().getText();
                }

                variables[i] = new Variable(varType, varName);
            }
        }

        Method method = new Method(methodName);
        method.returnType = returnType;
        method.variables = variables;

        boolean sameName = false;

        if (currentScope.table.keySet().contains(new Pair<>(Kind.Method, methodName))) {
            Method anotherMethod = (Method) currentScope.table.get(new Pair<>(Kind.Method, methodName));
            if (anotherMethod.misty) {
                currentScope.table.remove(new Pair<>(Kind.Method, methodName));
            } else if (method.equals(anotherMethod)) {
                String className = currentScope.table.get(new Pair<>(Kind.Class, "className")).toString();
                System.out.println("Error102: in line " + ctx.start.getLine() + ", " + methodName + " has been defined already in " + className);
                methodName += "(duplicate " + ctx.start.getLine() + ")";
            } else {
                sameName = true;
            }
        }
        if (!sameName)
            currentScope.table.put(new Pair<>(Kind.Method, methodName), method);
        else {
            Method anotherMethod = (Method) currentScope.table.get(new Pair<>(Kind.Method, methodName));
            anotherMethod.addSameName(method);
        }

        currentScope = new SymbolTable(currentScope);
        method.symbolTable = currentScope;
        currentScope.table.put(new Pair<>(Kind.Method, "methodName"), methodName);

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
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterWhileStatement(jythonParser.WhileStatementContext ctx) {
        String whileName = "while" + ctx.start.getLine() + ctx.start.getCharPositionInLine();
        currentScope = new SymbolTable(currentScope);
        currentScope.parent.table.put(new Pair<>(Kind.Scope, whileName), currentScope);
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
        currentScope = new SymbolTable(currentScope);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitIfElseStatement(jythonParser.IfElseStatementContext ctx) {
        currentScope = currentScope.parent;
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
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterForStatement(jythonParser.ForStatementContext ctx) {
        currentScope = new SymbolTable(currentScope);
        String counterName = ctx.ID().getText();
        Variable counter = new Variable(counterName, "int");
        if (ctx.leftExp() != null) {
            //TODO: must now type of variable
        }
        currentScope.table.put(new Pair<>(Kind.Variable, counterName), counter);
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
//        if (ctx.leftExp() == null) {
//            String methodName = ctx.ID().getText();
//            boolean find = false;
//            SymbolTable classTable = thisClass.symbolTable;
//            while (classTable.parent != null && !find) {
//                if (classTable.table.keySet().contains(new Pair<>(Kind.Method, methodName)))
//                    find = true;
//                else
//                    classTable = classTable.parent;
//            }
//            if (!find) {
//                Method method = new Method(methodName);
//                method.misty = true;
//                method.line = ctx.start.getLine();
//                currentScope.table.put(new Pair<>(Kind.Method, methodName), method);
//            }
//        }
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

    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitLeftExp(jythonParser.LeftExpContext ctx) {
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
    @Override public void enterLeftExpEnd(jythonParser.LeftExpEndContext ctx) { }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitLeftExpEnd(jythonParser.LeftExpEndContext ctx) { }

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
            if (!root.table.containsKey(new Pair<>(Kind.Class, type))) {
                Class newClass = new Class(type);
                newClass.misty = true;
                newClass.line = ctx.start.getLine();
                root.table.put(new Pair<>(Kind.Class, type), newClass);
            }
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