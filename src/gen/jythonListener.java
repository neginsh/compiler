package gen;// Generated from E:/Files/College Files/Session 6/Compiler/Project/Phase 2/JythonCompiler/compiler/grammar\jython.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link jythonParser}.
 */
public interface jythonListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link jythonParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(jythonParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(jythonParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#importClass}.
	 * @param ctx the parse tree
	 */
	void enterImportClass(jythonParser.ImportClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#importClass}.
	 * @param ctx the parse tree
	 */
	void exitImportClass(jythonParser.ImportClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#classDec}.
	 * @param ctx the parse tree
	 */
	void enterClassDec(jythonParser.ClassDecContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#classDec}.
	 * @param ctx the parse tree
	 */
	void exitClassDec(jythonParser.ClassDecContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(jythonParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(jythonParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#varDec}.
	 * @param ctx the parse tree
	 */
	void enterVarDec(jythonParser.VarDecContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#varDec}.
	 * @param ctx the parse tree
	 */
	void exitVarDec(jythonParser.VarDecContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#arrayDec}.
	 * @param ctx the parse tree
	 */
	void enterArrayDec(jythonParser.ArrayDecContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#arrayDec}.
	 * @param ctx the parse tree
	 */
	void exitArrayDec(jythonParser.ArrayDecContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#methodDec}.
	 * @param ctx the parse tree
	 */
	void enterMethodDec(jythonParser.MethodDecContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#methodDec}.
	 * @param ctx the parse tree
	 */
	void exitMethodDec(jythonParser.MethodDecContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#returnType}.
	 * @param ctx the parse tree
	 */
	void enterReturnType(jythonParser.ReturnTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#returnType}.
	 * @param ctx the parse tree
	 */
	void exitReturnType(jythonParser.ReturnTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#constructor}.
	 * @param ctx the parse tree
	 */
	void enterConstructor(jythonParser.ConstructorContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#constructor}.
	 * @param ctx the parse tree
	 */
	void exitConstructor(jythonParser.ConstructorContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(jythonParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(jythonParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#parameters}.
	 * @param ctx the parse tree
	 */
	void enterParameters(jythonParser.ParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#parameters}.
	 * @param ctx the parse tree
	 */
	void exitParameters(jythonParser.ParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(jythonParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(jythonParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(jythonParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(jythonParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#conditionList}.
	 * @param ctx the parse tree
	 */
	void enterConditionList(jythonParser.ConditionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#conditionList}.
	 * @param ctx the parse tree
	 */
	void exitConditionList(jythonParser.ConditionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(jythonParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(jythonParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#ifElseStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfElseStatement(jythonParser.IfElseStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#ifElseStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfElseStatement(jythonParser.IfElseStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void enterPrintStatement(jythonParser.PrintStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void exitPrintStatement(jythonParser.PrintStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(jythonParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(jythonParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void enterMethodCall(jythonParser.MethodCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void exitMethodCall(jythonParser.MethodCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(jythonParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(jythonParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(jythonParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(jythonParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#rightExp}.
	 * @param ctx the parse tree
	 */
	void enterRightExp(jythonParser.RightExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#rightExp}.
	 * @param ctx the parse tree
	 */
	void exitRightExp(jythonParser.RightExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#leftExp}.
	 * @param ctx the parse tree
	 */
	void enterLeftExp(jythonParser.LeftExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#leftExp}.
	 * @param ctx the parse tree
	 */
	void exitLeftExp(jythonParser.LeftExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(jythonParser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(jythonParser.ArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#expList}.
	 * @param ctx the parse tree
	 */
	void enterExpList(jythonParser.ExpListContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#expList}.
	 * @param ctx the parse tree
	 */
	void exitExpList(jythonParser.ExpListContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#assignmentOperators}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentOperators(jythonParser.AssignmentOperatorsContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#assignmentOperators}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentOperators(jythonParser.AssignmentOperatorsContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#eqNeq}.
	 * @param ctx the parse tree
	 */
	void enterEqNeq(jythonParser.EqNeqContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#eqNeq}.
	 * @param ctx the parse tree
	 */
	void exitEqNeq(jythonParser.EqNeqContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#relationOperators}.
	 * @param ctx the parse tree
	 */
	void enterRelationOperators(jythonParser.RelationOperatorsContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#relationOperators}.
	 * @param ctx the parse tree
	 */
	void exitRelationOperators(jythonParser.RelationOperatorsContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#addSub}.
	 * @param ctx the parse tree
	 */
	void enterAddSub(jythonParser.AddSubContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#addSub}.
	 * @param ctx the parse tree
	 */
	void exitAddSub(jythonParser.AddSubContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#multModDiv}.
	 * @param ctx the parse tree
	 */
	void enterMultModDiv(jythonParser.MultModDivContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#multModDiv}.
	 * @param ctx the parse tree
	 */
	void exitMultModDiv(jythonParser.MultModDivContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(jythonParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(jythonParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link jythonParser#jythonType}.
	 * @param ctx the parse tree
	 */
	void enterJythonType(jythonParser.JythonTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link jythonParser#jythonType}.
	 * @param ctx the parse tree
	 */
	void exitJythonType(jythonParser.JythonTypeContext ctx);
}