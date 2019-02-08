package com.learningjava.antlr.main;

import java.io.IOException;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.learningjava.antlr.Expr.ExprLexer;
import com.learningjava.antlr.Expr.ExprParser;
import com.learningjava.antlr.LabeledExpr.EvalVisitor;
import com.learningjava.antlr.LabeledExpr.LabeledExprLexer;
import com.learningjava.antlr.LabeledExpr.LabeledExprParser;
import com.learningjava.antlr.gyoo.GYOOLexer;
import com.learningjava.antlr.gyoo.GYOOParser;
import com.learningjava.antlr.gyoo.MyListener;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void runExpr() throws IOException
	{
		String inputFile = "expr.input";
    	if( inputFile!=null ) 
    	{
	    	CharStream input = CharStreams.fromFileName(inputFile);
	    	ExprLexer lexer = new ExprLexer(input);
	    	CommonTokenStream tokens = new CommonTokenStream(lexer);
	    	ExprParser parser = new ExprParser(tokens);
	    	ParseTree tree = parser.prog(); // parse; start at prog
	    	System.out.println(tree.toStringTree(parser)); // print tree as text
    	}
	}
	
	/*
	 * 注意 lexer 和 parser一定要是语法版本匹配的
	 */
	public static void runLabeledExpr() throws IOException
	{
		String inputFile = "expr.input";
    	if( inputFile!=null ) 
    	{
	    	CharStream input = CharStreams.fromFileName(inputFile);
	    	LabeledExprLexer lexer = new LabeledExprLexer(input);
	    	CommonTokenStream tokens = new CommonTokenStream(lexer);
	    	LabeledExprParser parser = new LabeledExprParser(tokens);
	    	ParseTree tree = parser.prog(); // parse; start at prog
	    	System.out.println(tree.toStringTree(parser)); // print tree as text
	    	EvalVisitor eval = new EvalVisitor();
	    	eval.visit(tree);
    	}
	}
	
	public static void runGYOO() throws IOException
	{
		String inputFile = "gyoo.input";
    	if( inputFile!=null ) 
    	{
	    	CharStream input = CharStreams.fromFileName(inputFile);
	    	GYOOLexer lexer = new GYOOLexer(input);
	    	CommonTokenStream tokens = new CommonTokenStream(lexer);
	    	GYOOParser parser = new GYOOParser(tokens);
	    	ParseTree tree = parser.program(); // parse; start at prog
	    	System.out.println(tree.toStringTree(parser)); // print tree as text
	    	ParseTreeWalker walker = new ParseTreeWalker();
	    	walker.walk(new MyListener(), tree);
	    	System.out.println();
    	}
	}
	
    public static void main( String[] args ) throws IOException
    {
    	App.runLabeledExpr();
    	System.out.println();
    	App.runGYOO();
    }
}
