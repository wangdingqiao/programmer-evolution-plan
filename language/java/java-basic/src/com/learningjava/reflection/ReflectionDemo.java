package com.learningjava.reflection;


import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

enum Day {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
    THURSDAY, FRIDAY, SATURDAY 
}

class CompanyA {

    String orgName;
    int count;
    List<String> comments;
    Set<String> branches;
    Map<String, String> extra;
    
    List<Integer> bytes;
    //...
    Day day;
}

public class ReflectionDemo
{
	public static void main(String[] args)
	{
		 Field[] fields = CompanyA.class.getDeclaredFields();
	        for(Field f : fields){
	            Class<?> t = f.getType();
	            System.out.println("field name : " + f.getName() + " , type : " + t + " is primitive: " + f.getType().isPrimitive());
	            if(t.isAssignableFrom(java.util.List.class))
	            {
	            	System.out.println("field name : " + f.getName());
	            }
	            if(f.getName() == "bytes")
	            {
	            	System.out.println("is array" + f.getType().isArray());
	            }
	        }

	}
}
