// Generated from .\gyoo\GYOO.g4 by ANTLR 4.7.1
package com.learningjava.antlr.gyoo;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GYOOParser}.
 */
public interface GYOOListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GYOOParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(GYOOParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link GYOOParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(GYOOParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link GYOOParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(GYOOParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GYOOParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(GYOOParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GYOOParser#assign}.
	 * @param ctx the parse tree
	 */
	void enterAssign(GYOOParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by {@link GYOOParser#assign}.
	 * @param ctx the parse tree
	 */
	void exitAssign(GYOOParser.AssignContext ctx);
	/**
	 * Enter a parse tree produced by {@link GYOOParser#print}.
	 * @param ctx the parse tree
	 */
	void enterPrint(GYOOParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by {@link GYOOParser#print}.
	 * @param ctx the parse tree
	 */
	void exitPrint(GYOOParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by {@link GYOOParser#add}.
	 * @param ctx the parse tree
	 */
	void enterAdd(GYOOParser.AddContext ctx);
	/**
	 * Exit a parse tree produced by {@link GYOOParser#add}.
	 * @param ctx the parse tree
	 */
	void exitAdd(GYOOParser.AddContext ctx);
}