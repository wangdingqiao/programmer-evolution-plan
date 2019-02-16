package com.my.rpc.core.IDLFileGeneratorBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.googlejavaformat.java.FormatterException;
import com.my.rpc.core.IDLParser.rpcMessageBaseVisitor;
import com.my.rpc.core.IDLParser.rpcMessageLexer;
import com.my.rpc.core.IDLParser.rpcMessageParser;
import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.exception.RPCExceptionFactory;

public class RPCMessageTranslatorBase extends rpcMessageBaseVisitor<String>
{
	protected RPCMessageFileBuilderBase msgBuilder;
	protected RPCInterfaceFileBuilderBase interfaceBuilder;
	protected IFileImportManager importHelper;
	private String inputFilePath;
	private static Logger logger = LogManager.getLogger(RPCMessageTranslatorBase.class);
	
	public RPCMessageTranslatorBase(String inputFilePath)
	{
		this.inputFilePath = inputFilePath;
		this.msgBuilder = null;
		this.interfaceBuilder = null;
		this.importHelper = null;
	}
	
	public void setUp(RPCMessageFileBuilderBase messageBuilder, RPCInterfaceFileBuilderBase interfaceBuilder, IFileImportManager importHelper)
	{
		this.msgBuilder = messageBuilder;
		this.interfaceBuilder = interfaceBuilder;
		this.importHelper = importHelper;
	}
	
	public void generateFile(String outputPath) throws RPCExceptionBase
	{
		if(outputPath == null)
    	{
    		throw new NullPointerException("outputPath null.");
    	}
		this.msgBuilder.setInputFilePath(inputFilePath);
		this.interfaceBuilder.setInputFilePath(inputFilePath);
		try
		{
			ParseTree tree = getParseTree(inputFilePath);
	    	this.visit(tree);
	    	this.writeFile(outputPath);
		}
		catch (NoSuchFileException e) {
			logger.error(" generateFile e=" + e.toString());
			throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_IO_Exception, 
					"File " + this.inputFilePath + " is not found.");
		}
		catch (FormatterException e) {
			logger.error(" generateFile e=" + e.toString());
			throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_GenerateFile_Exception, 
					"failed to formate generate file for inputFile= " + this.inputFilePath + " exception= " + e.getMessage());
		}
		catch (Exception e) {
			logger.error(" generateFile e=" + e.toString());
			throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_GenerateFile_Exception, 
					"failed to generate file for inputFile= " + this.inputFilePath + " exception= " + e.toString());
		}
	}
	
	private ParseTree getParseTree(String filePath) throws IOException
	{
		CharStream input= CharStreams.fromFileName(filePath);
    	rpcMessageLexer lexer = new rpcMessageLexer(input);
    	CommonTokenStream tokens = new CommonTokenStream(lexer);
    	rpcMessageParser parser = new rpcMessageParser(tokens);
    	return parser.prog(); // parse; starStringaStringprog
	}
	
	public void showASTTree() throws IOException
	{
		CharStream input = CharStreams.fromFileName(inputFilePath);
    	rpcMessageLexer lexer = new rpcMessageLexer(input);
    	CommonTokenStream tokens = new CommonTokenStream(lexer);
    	rpcMessageParser parser = new rpcMessageParser(tokens);
    	ParseTree tree = parser.prog();
    	System.out.println(tree.toStringTree(parser));
    	 //show ASStringin GUI
        JFrame frame = new JFrame("ASStringTree for file: " + inputFilePath);
        JPanel panel = new JPanel();
        JScrollPane scrPane = new JScrollPane(panel);
        TreeViewer viewr = new TreeViewer(Arrays.asList(parser.getRuleNames()),tree);
        viewr.setScale(1.5);//scale a little
        panel.add(viewr);
        frame.add(scrPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
	}
	
	public void writeFile(String outputPath) throws FileNotFoundException, FormatterException
	{
		msgBuilder.endMessage(importHelper, outputPath);
		interfaceBuilder.endInterface(importHelper, outputPath);
	}
	
	public void addNameLocation(String name, String packageLocation)
	{
		this.importHelper.addName(name, packageLocation);
	}
	
	public String getNameImportText(String typeName)
	{
		return this.importHelper.getNameImportStat(typeName);
	}
}
