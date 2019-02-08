package com.my.rpc.core.IDLFileGenerator.java;
import java.util.Map;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.apache.commons.lang3.tuple.Pair;

import com.my.rpc.core.IDLFileGeneratorBase.IFileImportManager;
import com.my.rpc.core.IDLFileGeneratorBase.RPCMessageTranslatorBase;
import com.my.rpc.core.IDLParser.rpcMessageParser;
import com.my.rpc.core.IDLParser.rpcMessageParser.EnumMemberContext;
import com.my.rpc.core.IDLParser.rpcMessageParser.MessageMemberContext;
import com.my.rpc.core.IDLParser.rpcMessageParser.MethodParamContext;
import com.my.rpc.runtime.exception.RPCMessageDefinitionException;
import com.my.rpc.runtime.exception.RPCExceptionFactory;

public class RPCMessageTranslator extends RPCMessageTranslatorBase
{
	public RPCMessageTranslator(String inputFilePath)
	{
		super(inputFilePath);
		RPCMessageFileBuilder messageBuilder = new RPCMessageFileBuilder(inputFilePath);
		RPCInterfaceFileBuilder interfaceBuilder = new RPCInterfaceFileBuilder(inputFilePath);
		IFileImportManager importHelper = new FileImportHelper();
		this.setUp(messageBuilder, interfaceBuilder, importHelper);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitProg(rpcMessageParser.ProgContext ctx) { return visitChildren(ctx); }
	/*
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitImportDefStat(rpcMessageParser.ImportDefStatContext ctx) 
	{ 
		String importName = visit(ctx.packageExpr());
		this.msgBuilder.addImportStat("import " + importName + ";");
		interfaceBuilder.addImportStat("import " + importName + ";");
		return ""; 
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitPackageDefStat(rpcMessageParser.PackageDefStatContext ctx) 
	{ 
		String packageName = visit(ctx.packageExpr());
		msgBuilder.addPackageStat(packageName);
		interfaceBuilder.addPackageStat(packageName);
		return ""; 
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitPackageExpr(rpcMessageParser.PackageExprContext ctx) 
	{
		return ctx.getText();
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitMessageBlockStat(rpcMessageParser.MessageBlockStatContext ctx) 
	{ 
		String messageBlockName = ctx.ID().getText();
		msgBuilder.setOuterClassName(messageBlockName);
		for(rpcMessageParser.MessageDefStatContext context: ctx.messageDefStat())
		{
			String messageName = visit(context);
			this.importHelper.addName(messageName, msgBuilder.getPackageLocation());
		}
		return "";
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitMessageDefStat(rpcMessageParser.MessageDefStatContext ctx) 
	{ 
		String messageName = ctx.messageHeader().ID().getText();
		Map<String, String> fields = new java.util.HashMap<>();
		java.util.ArrayList<Pair<String, String>> fileList = new java.util.ArrayList<Pair<String, String>>();
		for(MessageMemberContext mmctx: ctx.messageBody().messageMember())
		{
			String dataType = visit(mmctx.messageDataType());
			String filedId = mmctx.ID().getText();
			if(fields.containsKey(filedId))
			{
				RPCMessageDefinitionException ex =  new RPCMessageDefinitionException(RPCExceptionFactory.RPC_MessageDefinition_Exception,"Duplicate member in message name= " + messageName + " with param name= "+ filedId);
				throw new ParseCancellationException(ex);
			}
			fields.put(filedId, dataType);
			fileList.add(Pair.of(dataType, filedId));
			
		}
		msgBuilder.addMessage(messageName, fileList);
		return messageName;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitMessageHeader(rpcMessageParser.MessageHeaderContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitMessageBody(rpcMessageParser.MessageBodyContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitMessageMember(rpcMessageParser.MessageMemberContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitBuiltInType(rpcMessageParser.BuiltInTypeContext ctx) { return ctx.getText(); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitSeqType(rpcMessageParser.SeqTypeContext ctx) 
	{ 
		String innerType = visit(ctx.messageDataType());
		return "seq<" + innerType + ">";
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitMapType(rpcMessageParser.MapTypeContext ctx) 
	{ 
		String keyType = visit(ctx.messageDataType(0));
		String valueType = visit(ctx.messageDataType(1));
		return "map<" + keyType + "," + valueType + ">"; 
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitUserDefineType(rpcMessageParser.UserDefineTypeContext ctx) 
	{ 
		String typeText= visit(ctx.packageExpr());
		return typeText;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitEnumDefStat(rpcMessageParser.EnumDefStatContext ctx) 
	{
		String enumName = visit(ctx.enumHeader());
		java.util.Map<String, Integer> fields = new java.util.HashMap<>();
		Integer value = 0;
		java.util.ArrayList<Pair<String, Integer>> fileList= new java.util.ArrayList<Pair<String, Integer>>();
		for(EnumMemberContext emctx: ctx.enumBody().enumMember())
		{
			String enumSubName = emctx.ID().getText();
			if(emctx.enumValue().isEmpty())
			{
				value++;
			}
			else
			{
				value = Integer.valueOf(emctx.enumValue().getText());
			}
			if(fields.containsKey(enumSubName))
			{
				RPCMessageDefinitionException ex =  new RPCMessageDefinitionException(RPCExceptionFactory.RPC_MessageDefinition_Exception ,"Duplicate enum member in enum name= " + enumName + " with param name= "+ enumSubName);
				throw new ParseCancellationException(ex);
			}
			fields.put(enumSubName, value);
			fileList.add(Pair.of(enumSubName, value));
		}
		msgBuilder.addEnum(enumName, fileList);
		return "";
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitEnumHeader(rpcMessageParser.EnumHeaderContext ctx) { return ctx.ID().getText(); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitEnumBody(rpcMessageParser.EnumBodyContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitEnumMember(rpcMessageParser.EnumMemberContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitEnumValue(rpcMessageParser.EnumValueContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitInterfaceDefStat(rpcMessageParser.InterfaceDefStatContext ctx) 
	{ 
		String interfaceName = ctx.ID().getText();
		interfaceBuilder.setInterfaceName(interfaceName);
		for(rpcMessageParser.MethodDefStatContext context: ctx.methodDefStat())
		{
			visit(context);
		}
		return "";
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitMethodDefStat(rpcMessageParser.MethodDefStatContext ctx) 
	{ 
		String responseType = visit(ctx.messageDataType());
		String name = ctx.ID().getText();
		String methodIndex = ctx.methodIndexer().getText();
		java.util.Map<String, String> inputParams = new java.util.HashMap<>();
		java.util.ArrayList<Pair<String, String>> params = new java.util.ArrayList<Pair<String, String>>();
		for(MethodParamContext ipCtx: ctx.methodParams().methodParam())
		{
			String paramType = visit(ipCtx.messageDataType());
			String paramName = ipCtx.ID().getText();
			if(inputParams.containsKey(paramName))
			{
				RPCMessageDefinitionException ex =  new RPCMessageDefinitionException(RPCExceptionFactory.RPC_MessageDefinition_Exception ,"Duplicate  param in interface name= " + name + " with param name= "+ paramName);
				throw new ParseCancellationException(ex);
			}
			inputParams.put(paramName, paramType);
			params.add(Pair.of(paramType, paramName));
			interfaceBuilder.addImportStat(getNameImportText(paramType));
		}
		interfaceBuilder.addMethod(name, methodIndex, params, responseType);
		interfaceBuilder.addImportStat(getNameImportText(responseType));
		return "";
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitMethodParams(rpcMessageParser.MethodParamsContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitMethodParam(rpcMessageParser.MethodParamContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public  String visitMethodIndexer(rpcMessageParser.MethodIndexerContext ctx) { return visitChildren(ctx); }
	
}
