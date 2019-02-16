package com.my.rpc.core.IDLFileGenerator.java;

import com.my.rpc.core.IDLFileGeneratorBase.TypeMapperBase;

public class TypeMapper extends TypeMapperBase
{
	private static TypeMapper instance = null;
	private  TypeMapper()
	{
		
	}
	public static TypeMapper instance()
	{
		if(instance == null)
		{
			instance = new TypeMapper();
		}
		return instance;
	}
	public String getMappedType(String originTypeStr)
	{
		if(isPrimitiveType(originTypeStr))
		{
			return getPrimitiveType(originTypeStr);
		}
		else if(isEnumType(originTypeStr))
		{
			return getPrimitiveType(originTypeStr);
		}
		else if(originTypeStr.contains("seq"))
		{
			String innerType = originTypeStr.substring(originTypeStr.indexOf("<")+1, originTypeStr.indexOf(">"));
			innerType = getMappedType(innerType);
			return "ArrayList<" + innerType + ">";
		}
		else if(originTypeStr.contains("map"))
		{
			String innerKeyType = originTypeStr.substring(originTypeStr.indexOf("<")+1, originTypeStr.indexOf(","));
			innerKeyType = getMappedType(innerKeyType);
			String innerValueType = originTypeStr.substring(originTypeStr.indexOf(",")+1, originTypeStr.indexOf(">"));
			innerValueType = getMappedType(innerValueType);
			return "HashMap<" + innerKeyType +  "," +  innerValueType + ">";
		}
		else 
		{
			return originTypeStr;
		}
	}
	
	public String getConstuctStatement(String typeStr, String fieldName)
	{
		if(isCustomType(typeStr))
		{
			return "this." + fieldName + "= new " + typeStr + "();";
		}
		return "this." + fieldName + "= new " + getMappedType(typeStr) + "();";
	}
}
