package com.my.rpc.core.IDLFileGenerator.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import org.apache.commons.lang3.tuple.Pair;
import com.google.googlejavaformat.java.FormatterException;
import com.my.rpc.core.IDLFileGeneratorBase.IFileImportManager;
import com.my.rpc.core.IDLFileGeneratorBase.RPCMessageFileBuilderBase;

public class RPCMessageFileBuilder extends RPCMessageFileBuilderBase
{
	public RPCMessageFileBuilder(String inputFileName)
	{
		super(inputFileName);
	}
	
	public void addBuildInImport()
	{
		importStatments.add("import java.util.ArrayList;\n");
		importStatments.add("import java.util.Map;\n");
		importStatments.add("import java.util.HashMap;\n");
		importStatments.add("import com.my.rpc.runtime.exception.RPCExceptionBase;\n");
		importStatments.add("import com.my.rpc.runtime.serializer.ISerializer;\n");
		importStatments.add("import com.my.rpc.runtime.protocol.IRPCMessage;\n");
		importStatments.add("import com.my.rpc.runtime.serializer.IDeserializer;\n");
		importStatments.add("import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.*;\n");
	}
	
	public void setOuterClassName(String className)
	{
		outerClassName = className;
		outerClassBuilder.append("\n public final class " + className + "\n{\n");
	}
	
	public String getPackageLocation()
	{
		return packageName + "." + outerClassName;
	}
	
	public void addImportStat(String text)
	{
		if(text != null)
		{
			importStatments.add(text);
		}
	};
	
	public void addPackageStat(String text)
	{
		packageStatBuilder.append("package " +text + ";\n");
		packageName = text;
	};
	
	public void addMessage(String name, java.util.ArrayList<Pair<String, String>> fields)
	{
		msgBodyBuilder.append("\npublic static class " + name + " implements IRPCMessage \n{\n");
		for(Pair<String, String> entry: fields)
		{
			String typeStr = TypeMapper.instance().getMappedType(entry.getKey());
			msgBodyBuilder.append("public " + typeStr + " " + entry.getValue() + ";\n");
		}
		msgBodyBuilder.append("\n public " + name + "()\n{");
		for(Pair<String, String> entry: fields)
		{
			String constructStat = TypeMapper.instance().getConstuctStatement(entry.getKey(), entry.getValue());
			if(!constructStat.isEmpty())
			{
				msgBodyBuilder.append(constructStat+"\n");
			}
		}
		msgBodyBuilder.append("\n}\n");
		msgBodyBuilder.append(SerializePartBuilder.getSerMessageText(name, fields));
		msgBodyBuilder.append(DeserializePartBuilder.getDeserMessageText(name, fields));
		msgBodyBuilder.append(getToStringMethodText(name, fields));
		msgBodyBuilder.append("\n}\n");
	}
	
	public String getToStringMethodText(String name, java.util.ArrayList<Pair<String, String>> fields)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("@Override \npublic String toString(){\n");
		builder.append("StringBuilder builder = new StringBuilder();");
		for(Pair<String, String> entry: fields)
		{
			String fieldName = entry.getValue();
			String typeName = entry.getKey();
			if(TypeMapper.instance().isMapType(entry.getKey()))
			{
				 String innerKeyTypeName = typeName.substring(typeName.indexOf('<')+1, typeName.indexOf(','));
				 String innerValueTypeName = typeName.substring(typeName.indexOf(',')+1, typeName.indexOf('>'));
				 builder.append("String " + fieldName + "Text = new String(\"{\");\n");
				 builder.append("for(Map.Entry<" +TypeMapper.instance().getMappedType(innerKeyTypeName) +"," + TypeMapper.instance().getMappedType(innerValueTypeName) + "> entry: " + fieldName + ".entrySet())\n{");
				 builder.append(fieldName + "Text +=entry.getKey().toString() + \":\"  + entry.getValue().toString() + \",\";\n");
				 builder.append("}\n");
				 builder.append(fieldName + "Text += \"}\"\n;");
				 builder.append("builder.append(" + fieldName + "Text);\n");
			}
			else if(TypeMapper.instance().isSeqType(entry.getKey()))
			{
				 String innerTypeName = typeName.substring(typeName.indexOf('<')+1, typeName.indexOf('>'));
				 String outputTypeStr = TypeMapper.instance().getMappedType(innerTypeName);
				 builder.append("String " + fieldName + "Text = new String(\"[\");\n");
				 builder.append("for(" + outputTypeStr +" value:" + fieldName + ")\n{");
				 builder.append(fieldName + "Text +=value.toString()+\",\";\n");
				 builder.append("}\n");
				 builder.append(fieldName + "Text += \"]\"\n;");
				 builder.append("builder.append(" + fieldName + "Text);\n");
			}
			else 
			{
				
				builder.append("builder.append(\"" + fieldName +"=\"+" + fieldName +".toString() +\",\"" +");");
			}
			
		}
		builder.append("return \"" + name + "\" + \"[\" + builder.toString() +\"]\";");
		builder.append("\n}\n");
		return builder.toString();
	}
	
	public void addEnum(String name,  java.util.ArrayList<Pair<String, Integer>> fields)
	{
		msgBodyBuilder.append("\nenum " + name + "\n{");
		int value = 0;
		for(Pair<String, Integer> entry: fields)
		{
			if(entry.getValue() != null)
			{
				value = entry.getValue();
			}
			else
			{
				value++;
			}
			msgBodyBuilder.append(entry.getKey() + "(" + value + "),\n");
		}
		msgBodyBuilder.append("private int value;");
		msgBodyBuilder.append("private Enum " + name + "(int val){\nthis.value=val;\n}\n");
		msgBodyBuilder.append("public int getValue() \n{ return this.value;\n}\n");
		msgBodyBuilder.append("public int setValue(int val) \n{this.value = val;\n}\n");
		msgBodyBuilder.append("}\n");
	}

	@Override
	public void endMessage(IFileImportManager importHelper, String outputPath)
			throws FileNotFoundException, FormatterException
	{
		String filePath = packageName;
		filePath = outputPath + File.separator  +filePath.replaceAll("\\.", Matcher.quoteReplacement(File.separator));
		File directory = new File(filePath);
		if (! directory.exists())
		{
			directory.mkdir();
		}
		StringWriter out = new StringWriter();
		PrintWriter  pw = new PrintWriter(out);
		pw.write(this.getDescriptionText());
		pw.write(packageStatBuilder.toString());
		for(String importText: this.importStatments)
		{
			if(!importText.isEmpty())
			{
				pw.write(importText);
			}
		}
		pw.write(outerClassBuilder.toString());
		pw.write(msgBodyBuilder.toString());
		pw.write("}\n");
		pw.close();
		String sourceStr = out.toString();
		filePath += File.separator + outerClassName+".java";
		try
		{
			String formattedSource = new com.google.googlejavaformat.java.Formatter().formatSource(sourceStr);
			PrintWriter fromatedPW = new PrintWriter(new FileOutputStream(filePath));
			fromatedPW.write(formattedSource);
			fromatedPW.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(sourceStr);
		}
		System.out.println("RPCMessageFileBuilder endMessage filePath= " + filePath);
	}
}
