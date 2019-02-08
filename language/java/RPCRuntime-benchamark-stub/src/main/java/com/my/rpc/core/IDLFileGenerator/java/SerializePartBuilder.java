package com.my.rpc.core.IDLFileGenerator.java;

import org.apache.commons.lang3.tuple.Pair;

public class SerializePartBuilder
{
	public static String getSerMessageText(String name, java.util.ArrayList<Pair<String, String>> fields)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("@Override\n public void serialize(ISerializer ser) throws RPCExceptionBase\n{\n");
		for(Pair<String, String> entry: fields)
		{
			builder.append("  " + serializeFiled(entry.getKey(), entry.getValue()) + "\n");
		}
		builder.append("\n}");
		return builder.toString();
	}
	
	public static String getSerMethodText(String interfaceName, String methodName, String methodIndex, 
			String responseType, java.util.ArrayList<Pair<String, String>> params)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("  public " + responseType + " " +methodName + "(");
		int inParamSize = params.size();
		int index = 0;
		for(Pair<String, String> entry: params)
		{
			index++;
			String typeStr = TypeMapper.instance().getMappedType(entry.getKey());
			builder.append("" + typeStr + " " + entry.getValue() + (index != inParamSize ? "," : ""));
		}
		builder.append(") throws RPCExceptionBase \n{");
	
		builder.append("RPCRequest request = new RPCRequest();\n");
		builder.append("request.setRequestTime(System.currentTimeMillis());\n");
		builder.append("ISerializer ser = SerializeFactory.getSerializer();\n");
		for(Pair<String, String> entry: params)
		{
			builder.append(entry.getValue() + ".serialize(ser);\n");
		}
		builder.append("request.setInterfaceId(" + interfaceName + ".class.getName());\n");
		builder.append("request.setMethodId(" + methodIndex + ");\n");
		builder.append("request.setBodyData(ser.toByteArray());\n");
		builder.append("RPCResponse response = call(request);\n");
		builder.append(responseType + " _result_ = new " + responseType + "();\n");
		builder.append("_result_.deserialize(response.getMessageDeserializer());\n");
		builder.append("return _result_;\n");
		builder.append("}\n");
		return builder.toString();
	}
	
	public static String serializeFiled(String typeName, String fieldName)
	{
		if(TypeMapper.instance().isPrimitiveType(typeName))
		{
			return serializePrimitiveFiled(typeName, fieldName);
		}
		else if(TypeMapper.instance().isSeqType(typeName))
		{
			return serializeSeqFiled(typeName, fieldName);
		}
		else if(TypeMapper.instance().isMapType(typeName))
		{
			return serializeMapFiled(typeName, fieldName);
		}
		else if(TypeMapper.instance().isEnumType(typeName))
		{
			return serializeEnumFiled(typeName, fieldName);
		}
		else
		{
			return serializeCustomTypeFiled(typeName, fieldName);
		}
	}
	
	private static String serializePrimitiveFiled(String typeName, String fieldName)
	{
		return fieldName + ".serialize(ser);";
	}
	
	private static String serializeEnumFiled(String typeName, String fieldName)
	{
		 return "ser.putInt(" + fieldName + ".toString());"; 
	}
	
	private static String serializeSeqFiled(String typeName, String fieldName)
	{
		 String innerTypeName = typeName.substring(typeName.indexOf('<')+1, typeName.indexOf('>'));
		 StringBuilder builder = new StringBuilder();
		 builder.append("int size = " +  fieldName +".size();\n");
		 builder.append("ser.putInt(size);\n");
		 String outputTypeStr = TypeMapper.instance().getMappedType(innerTypeName);
		 builder.append("for(" + outputTypeStr +" value:" + fieldName + ")\n{");
		 builder.append(serializeFiled(innerTypeName, "value"));
		 builder.append("\n}");
		 return builder.toString();
	}
	
	private static String serializeMapFiled(String typeName, String fieldName)
	{
		 String innerKeyTypeName = typeName.substring(typeName.indexOf('<')+1, typeName.indexOf(','));
		 String innerValueTypeName = typeName.substring(typeName.indexOf(',')+1, typeName.indexOf('>'));
		 StringBuilder builder = new StringBuilder();
		 builder.append("int size = " +  fieldName +".size();\n");
		 builder.append("ser.putInt(size);\n");
		 builder.append("for(Map.Entry<" +TypeMapper.instance().getMappedType(innerKeyTypeName) +"," + TypeMapper.instance().getMappedType(innerValueTypeName) + "> entry: " + fieldName + ".entrySet())\n{");
		 builder.append(serializePrimitiveFiled(innerKeyTypeName, "entry.getKey()"));
		 builder.append(serializeFiled(innerValueTypeName, "entry.getValue()"));
		 builder.append("\n}");
		 return builder.toString();
	}
	
	private static String serializeCustomTypeFiled(String typeName, String fieldName)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("" + fieldName + ".serialize(ser);");
		return builder.toString();
	}
}
