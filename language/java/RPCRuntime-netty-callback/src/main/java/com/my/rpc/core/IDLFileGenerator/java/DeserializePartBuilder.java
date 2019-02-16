package com.my.rpc.core.IDLFileGenerator.java;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

public class DeserializePartBuilder
{
private static final Map<String, String> serPrimitiveSentences = new HashMap<String, String>();
	
	static
	{
		serPrimitiveSentences.put("int", "readInt()");
		serPrimitiveSentences.put("short", "readShort()");
		serPrimitiveSentences.put("long", "readLong()");
		serPrimitiveSentences.put("float", "readFloat()");
		serPrimitiveSentences.put("double", "readDouble()");
		serPrimitiveSentences.put("bool", "readBool()");
		serPrimitiveSentences.put("byte", "readByte()");
		serPrimitiveSentences.put("binary", "readBytes()");
		serPrimitiveSentences.put("string", "readString()");
		serPrimitiveSentences.put("date", "readDate()");
	}
	
	public static String getDeserMessageText(String name, java.util.ArrayList<Pair<String, String>> fields)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("@Override\n public void deserialize(IDeserializer deser) throws RPCExceptionBase\n{\n");
		for(Pair<String, String> entry: fields)
		{
			String typeName = entry.getKey();
			if(TypeMapper.instance().isPrimitiveType(typeName) || TypeMapper.instance().isCustomType(typeName))
			{
				builder.append("this." +entry.getValue() + deserializeFiled(entry.getKey(),entry.getValue()) + ";\n");
			}
			else
			{
				builder.append(deserializeFiled(entry.getKey(), entry.getValue()) + "\n");
			}
		}
		builder.append("\n}");
		return builder.toString();
	}
	
	
	public static String getDeserMethodText(String name, String returnType, java.util.ArrayList<Pair<String, String>> params)
	{
		StringBuilder builder = new StringBuilder();
		for(Pair<String, String> entry: params)
		{
			String typeName = entry.getKey();
			builder.append(TypeMapper.instance().getMappedType(typeName) + " " + entry.getValue() + " = new " + TypeMapper.instance().getMappedType(typeName) + "();\n");
			builder.append(entry.getValue() + deserializeFiled(entry.getKey(),entry.getValue()) + ";\n");
		}
		if(returnType != "void")
		{
			builder.append(returnType + " retValue_ = " + "this." + name + "(");
		}
		else
		{
			builder.append("this." + name + "(");
		}
		int inParamSize = params.size();
		int index = 0;
		for(Pair<String, String> entry: params)
		{
			index++;
			builder.append(entry.getValue() + (index != inParamSize ? "," : ""));
		}
		builder.append(");");
		if(returnType != "void")
		{
			builder.append(" return callInfo.reply(retValue_);");
		}
		else
		{
			builder.append(" return callInfo.reply();");
		}
		return builder.toString();
	}
	
	
	public static String deserializeFiled(String typeName, String fieldName)
	{
		if(TypeMapper.instance().isPrimitiveType(typeName))
		{
			return deserializePrimitiveFiled(typeName, fieldName);
		}
		else if(TypeMapper.instance().isSeqType(typeName))
		{
			return deserializeSeqFiled(typeName,fieldName);
		}
		else if(TypeMapper.instance().isMapType(typeName))
		{
			return deserializeMapFiled(typeName, fieldName);
		}
		else if(TypeMapper.instance().isEnumType(typeName))
		{
			return deserializeEnumFiled(typeName, fieldName);
		}
		else
		{
			return deserializeCustomTypeFiled(typeName, fieldName);
		}
	}
	
	private static String deserializePrimitiveFiled(String typeName, String fieldName)
	{
		 String readStr = serPrimitiveSentences.get(typeName);
		 if(readStr == null)
		 {
			System.out.println(typeName.length());
		 }
		 return ".deserialize(deser)";
	}
	
	private static String deserializeEnumFiled(String typeName, String fieldName)
	{
		 return "deser.readInt()";
	}
	
	private static String deserializeSeqFiled(String typeName, String fieldName)
	{
		 String innerTypeName = typeName.substring(typeName.indexOf('<')+1, typeName.indexOf('>'));
		 StringBuilder builder = new StringBuilder();
		 builder.append("int size = deser.readInt();\n");
		 builder.append("for(int i=0;i<size;++i)\n{");
		 builder.append(TypeMapper.instance().getMappedType(innerTypeName) + " value = " + " new " + TypeMapper.instance().getMappedType(innerTypeName) + "();\n");
		 builder.append("value" + deserializeFiled(innerTypeName, "") + ";\n");
		 builder.append("this." + fieldName + ".add(value);\n");
		 builder.append("\n}");
		 return builder.toString();
	}
	
	private static String deserializeMapFiled(String typeName, String fieldName)
	{
		 String innerKeyTypeName = typeName.substring(typeName.indexOf('<')+1, typeName.indexOf(','));
		 String innerValueTypeName = typeName.substring(typeName.indexOf(',')+1, typeName.indexOf('>'));
		 StringBuilder builder = new StringBuilder();
		 builder.append("int size = deser.readInt();\n");
		 builder.append("for(int i=0;i<size;++i)\n{");
		 builder.append(TypeMapper.instance().getMappedType(innerKeyTypeName) + " key = " + " new " + TypeMapper.instance().getMappedType(innerKeyTypeName) + "();\n");
		 builder.append(" key " + deserializeFiled(innerKeyTypeName, "") + ";\n");
		 builder.append(TypeMapper.instance().getMappedType(innerValueTypeName) + " value = " + " new " + TypeMapper.instance().getMappedType(innerValueTypeName) + "();\n");
		 builder.append("value" + deserializeFiled(innerValueTypeName, "")+ ";\n");
		 builder.append("this." + fieldName +".put(key,value);\n");
		 builder.append("\n}");
		 return builder.toString();
	}
	
	private static String deserializeCustomTypeFiled(String typeName, String fieldName)
	{
		return ".deserialize(deser)";
	}
}
