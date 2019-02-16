package com.my.rpc.core.IDLFileGeneratorBase;

import java.util.Map;

public abstract class TypeMapperBase
{
	private Map<String, String> buildInTypeMapper = new java.util.HashMap<>();
	
	public TypeMapperBase()
	{
		buildInTypeMapper.put("byte", "RPCByte");
		buildInTypeMapper.put("int", "RPCInt");
		buildInTypeMapper.put("short", "RPCShort");
		buildInTypeMapper.put("long", "RPCLong");
		buildInTypeMapper.put("float", "RPCFloat");
		buildInTypeMapper.put("string", "RPCString");
		buildInTypeMapper.put("double", "RPCDouble");
		buildInTypeMapper.put("bool", "RPCBool");
		buildInTypeMapper.put("binary", "RPCBinary");
		buildInTypeMapper.put("date", "RPCDate");
		buildInTypeMapper.put("void", "void");
	}
	
	public  boolean isPrimitiveType(String typeStr)
	{
		return buildInTypeMapper.containsKey(typeStr);
	}
	
	public String getPrimitiveType(String typeStr)
	{
		return buildInTypeMapper.get(typeStr);
	}
	
	public  boolean isSeqType(String typeStr)
	{
		return typeStr.contains("seq");
	}
	
	public  boolean isMapType(String typeStr)
	{
		return typeStr.contains("map");
	}
	
	public  boolean isEnumType(String typeStr)
	{
		return typeStr.equals("Enum");
	}
	
	public  boolean isCustomType(String typeStr)
	{
		return !isPrimitiveType(typeStr) && !isSeqType(typeStr) && !isMapType(typeStr) && !isEnumType(typeStr);
	}
	public abstract String getMappedType(String originTypeStr);
	public abstract String getConstuctStatement(String typeStr, String fieldName);
}
