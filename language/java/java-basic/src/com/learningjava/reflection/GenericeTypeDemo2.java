package com.learningjava.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class MyClass {

	  protected List<String> stringList;

	  public List<String> getStringList()
	  {
	    return this.stringList;
	  }
	  public void setStringList(Integer a, List<String> list, ArrayList<Double> aList, Map<String, Integer> map, Map<String, ArrayList<Double>> complex)
	  {
		    this.stringList = list;
	  }
	  
	  private Integer age;
}

public class GenericeTypeDemo2
{
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, NoSuchFieldException, ClassNotFoundException
	{
		Method method = MyClass.class.getMethod("getStringList");

		Type returnType = method.getGenericReturnType();

		if(returnType instanceof ParameterizedType){
		    ParameterizedType type = (ParameterizedType) returnType;
		    Type[] typeArguments = type.getActualTypeArguments();
		    for(Type typeArgument : typeArguments){
		        Class<?> typeArgClass = ( Class<?>) typeArgument;
		        System.out.println("typeArgClass = " + typeArgClass);
		    }
		}
		
		
		method = MyClass.class.getMethod("setStringList", Integer.class,Class.forName("java.util.List"), ArrayList.class, Map.class, Map.class);
		
		 for (Type t : method.getGenericParameterTypes()) {
	            System.out.println("with GPT: " + t);
	            System.out.println("with GPT: "  + t.getTypeName()+"\n");
	            if(t instanceof ParameterizedType )
	            {
	            	ParameterizedType aType = (ParameterizedType) t;
	            	 System.out.println("with GPT: ParameterizedType " + aType.getActualTypeArguments()[0]);
	            }
//	            System.out.println(Class.forName(t.getTypeName()));
//	            if(((Class<?>)t).isAssignableFrom(java.util.Map.class))
//	            {
//	            	 System.out.println("with GPT: Yes");
//	            }
	        }
        for (Type t : method.getParameterTypes()) {
            System.out.println("with PT: " + t.getTypeName() + " " + t.getClass());
        }
		
        System.out.println("\n\n");
		
		Field field = MyClass.class.getDeclaredField("age");

		Type genericFieldType = field.getGenericType();
		
		if(genericFieldType instanceof ParameterizedType){
		    ParameterizedType aType = (ParameterizedType) genericFieldType;
		    Type[] fieldArgTypes = aType.getActualTypeArguments();
		    for(Type fieldArgType : fieldArgTypes){
		    	Class<?> fieldArgClass = (Class<?>) fieldArgType;
		        System.out.println("fieldArgClass = " + fieldArgClass);
		    }
		}
		else
		{
			System.out.println("fieldArgClass = " + field.getType());
		}
		
		String typeStr = "java.util.Map<java.lang.String, java.util.ArrayList<java.lang.Double>>";
		typeStr = "java.lang.Integer";
		String[] SepString= typeStr.split("\\<");
		  /* print test */
		for(int i =0; i < SepString.length ; i++)
			System.out.println(SepString[i]);
		
		System.out.println(java.util.ArrayList.class.isAssignableFrom(java.util.List.class));
		
		try
		{
			System.out.println(float.class.isPrimitive());
			System.out.println(float.class.getTypeName());
			System.out.println(Class.forName("java.lang.Integer"));
			System.out.println(byte[].class.getTypeName());
			System.out.println(java.util.List.class.isAssignableFrom(java.util.ArrayList.class));
			System.out.println(java.util.List.class.isAssignableFrom(java.util.List.class));
			System.out.println(java.util.Map.class.isAssignableFrom(java.util.HashMap.class));
			System.out.println(java.util.Map.class.isAssignableFrom(java.util.Map.class));
			@SuppressWarnings("unchecked")
			List<Object> list = java.util.ArrayList.class.getConstructor().newInstance();
			System.out.println(list);
			System.out.println(Class.forName("byte[]"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
