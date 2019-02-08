// Generated from rpcMessage.g4 by ANTLR 4.7.1
package com.my.rpc.core.IDLParser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link rpcMessageParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface rpcMessageVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link rpcMessageParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(rpcMessageParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link rpcMessageParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat(rpcMessageParser.StatContext ctx);
	/**
	 * Visit a parse tree produced by {@link rpcMessageParser#importDefStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportDefStat(rpcMessageParser.ImportDefStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link rpcMessageParser#packageDefStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageDefStat(rpcMessageParser.PackageDefStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link rpcMessageParser#packageExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageExpr(rpcMessageParser.PackageExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link rpcMessageParser#messageBlockStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMessageBlockStat(rpcMessageParser.MessageBlockStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link rpcMessageParser#messageDefStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMessageDefStat(rpcMessageParser.MessageDefStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link rpcMessageParser#messageHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMessageHeader(rpcMessageParser.MessageHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link rpcMessageParser#messageBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMessageBody(rpcMessageParser.MessageBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link rpcMessageParser#messageMember}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMessageMember(rpcMessageParser.MessageMemberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code builtInType}
	 * labeled alternative in {@link rpcMessageParser#messageDataType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBuiltInType(rpcMessageParser.BuiltInTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code seqType}
	 * labeled alternative in {@link rpcMessageParser#messageDataType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSeqType(rpcMessageParser.SeqTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mapType}
	 * labeled alternative in {@link rpcMessageParser#messageDataType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapType(rpcMessageParser.MapTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code userDefineType}
	 * labeled alternative in {@link rpcMessageParser#messageDataType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUserDefineType(rpcMessageParser.UserDefineTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link rpcMessageParser#enumDefStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumDefStat(rpcMessageParser.EnumDefStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link rpcMessageParser#enumHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumHeader(rpcMessageParser.EnumHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link rpcMessageParser#enumBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumBody(rpcMessageParser.EnumBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link rpcMessageParser#enumMember}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumMember(rpcMessageParser.EnumMemberContext ctx);
	/**
	 * Visit a parse tree produced by {@link rpcMessageParser#enumValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumValue(rpcMessageParser.EnumValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link rpcMessageParser#interfaceDefStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceDefStat(rpcMessageParser.InterfaceDefStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link rpcMessageParser#methodDefStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDefStat(rpcMessageParser.MethodDefStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link rpcMessageParser#methodParams}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodParams(rpcMessageParser.MethodParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link rpcMessageParser#methodParam}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodParam(rpcMessageParser.MethodParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link rpcMessageParser#methodIndexer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodIndexer(rpcMessageParser.MethodIndexerContext ctx);
}