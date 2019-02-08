package com.learningjava.MessagePackDemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TypeTeller
{
	private static final Set<Class<?>> WRAPPER_TYPES = new HashSet<Class<?>>();
	private static final Map<String, Class<?>> primitiveMap = new HashMap<String, Class<?>>();
	private static final Map<Class<?>, Class<?>> primitiveToWrapperMap = new HashMap<Class<?>, Class<?>>();
	private static final Map<String, Class<?>> primitiveArrayMap = new HashMap<String, Class<?>>();
	static 
	{
		
		WRAPPER_TYPES.add(Boolean.class);
		WRAPPER_TYPES.add(Character.class);
		WRAPPER_TYPES.add(Byte.class);
		WRAPPER_TYPES.add(Short.class);
		WRAPPER_TYPES.add(Integer.class);
		WRAPPER_TYPES.add(Long.class);
		WRAPPER_TYPES.add(Float.class);
		WRAPPER_TYPES.add(Double.class);
		WRAPPER_TYPES.add(Void.class);
		WRAPPER_TYPES.add(String.class);
		
		primitiveMap.put(boolean.class.getTypeName(), boolean.class);
	    primitiveMap.put(byte.class.getTypeName(), byte.class);
	    primitiveMap.put(short.class.getTypeName(), short.class);
	    primitiveMap.put(char.class.getTypeName(), char.class);
	    primitiveMap.put(int.class.getTypeName(), int.class);
	    primitiveMap.put(long.class.getTypeName(), long.class);
	    primitiveMap.put(float.class.getTypeName(), float.class);
	    primitiveMap.put(double.class.getTypeName(), double.class);
	    
	    primitiveArrayMap.put(boolean[].class.getTypeName(), boolean[].class);
	    primitiveArrayMap.put(byte[].class.getTypeName(), byte[].class);
	    primitiveArrayMap.put(short[].class.getTypeName(), short[].class);
	    primitiveArrayMap.put(char[].class.getTypeName(), char[].class);
	    primitiveArrayMap.put(int[].class.getTypeName(), int[].class);
	    primitiveArrayMap.put(long[].class.getTypeName(), long[].class);
	    primitiveArrayMap.put(float[].class.getTypeName(), float[].class);
	    primitiveArrayMap.put(double[].class.getTypeName(), double[].class);
	    
	    primitiveToWrapperMap.put(boolean.class, Boolean.class);
	    primitiveToWrapperMap.put(byte.class, Byte.class);
	    primitiveToWrapperMap.put(short.class, Short.class);
	    primitiveToWrapperMap.put(char.class, Character.class);
	    primitiveToWrapperMap.put(int.class, Integer.class);
	    primitiveToWrapperMap.put(long.class, Long.class);
	    primitiveToWrapperMap.put(float.class, Float.class);
	    primitiveToWrapperMap.put(double.class, Double.class);
	}
	
	public static boolean canEncodeAsPrimitiveType(Class<?> clazz)
	{
		return clazz.isPrimitive() || isPrimitiveWrapper(clazz);
	}
	
	public static Class<?> getPrimitiveArrayClass(String primitiveName)
	{
		return primitiveArrayMap.get(primitiveName);
	}
	
	public static boolean isPrimitiveWrapper(Class<?> clazz)
	{
	    return WRAPPER_TYPES.contains(clazz);
	}
	
	public static Class<?> getPrimitiveClass(String primitiveName)
	{
		return primitiveMap.get(primitiveName);
	}
	
	public static Class<?> primitiveToWrapper(Class<?> clazz)
	{
		return primitiveToWrapperMap.get(clazz);
	}
	
	public static List<Class<?>> parseTypeFromStr(String typeStr)
	{
		if(typeStr == null || typeStr.isEmpty())
		{
			return null;
		}
		ArrayList<Class<?>> typeList = new ArrayList<>();
		Class<?> primitiveArrayClass = TypeTeller.getPrimitiveArrayClass(typeStr);
		if(primitiveArrayClass != null)
		{
			typeList.add(primitiveArrayClass);
			return typeList;
		}
		Class<?> primitiveClass = TypeTeller.getPrimitiveClass(typeStr);
		if(primitiveClass != null)
		{
			typeList.add(primitiveClass);
			return typeList;
		}
		typeStr = typeStr.replace("<", "#").replace(",","#").replace(" ", "");
		String[] sepString= typeStr.split("\\#");
		for(String typeText: sepString)
		{
			Class<?> subType = null;
			try
			{
				subType = Class.forName(typeText.replace(">", ""));
			}
			catch (Exception e) 
			{
				System.out.println(e);
				return null;
			}
			typeList.add(subType);
		}
		return typeList;
	}
}
