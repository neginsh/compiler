package gen;// Generated from E:/Files/College Files/Session 6/Compiler/Project/Phase 2/JythonCompiler/compiler/grammar\jython.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link jythonParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface jythonVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link jythonParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(jythonParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#importClass}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportClass(jythonParser.ImportClassContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#classDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDec(jythonParser.ClassDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(jythonParser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#varDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDec(jythonParser.VarDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#arrayDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayDec(jythonParser.ArrayDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#methodDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDec(jythonParser.MethodDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#returnType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnType(jythonParser.ReturnTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#constructor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructor(jythonParser.ConstructorContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(jythonParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#parameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameters(jythonParser.ParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(jythonParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(jythonParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#conditionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionList(jythonParser.ConditionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(jythonParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#ifElseStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfElseStatement(jythonParser.IfElseStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#printStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStatement(jythonParser.PrintStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(jythonParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#methodCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCall(jythonParser.MethodCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(jythonParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(jythonParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#rightExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRightExp(jythonParser.RightExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#leftExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLeftExp(jythonParser.LeftExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(jythonParser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#expList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpList(jythonParser.ExpListContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#assignmentOperators}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentOperators(jythonParser.AssignmentOperatorsContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#eqNeq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqNeq(jythonParser.EqNeqContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#relationOperators}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationOperators(jythonParser.RelationOperatorsContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#addSub}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSub(jythonParser.AddSubContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#multModDiv}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultModDiv(jythonParser.MultModDivContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(jythonParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link jythonParser#jythonType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJythonType(jythonParser.JythonTypeContext ctx);
}