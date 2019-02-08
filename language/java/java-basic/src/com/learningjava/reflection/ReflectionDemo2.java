package com.learningjava.reflection;


import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class ReflectionDemo2 {

    List<String> stringList = new ArrayList<String>();
    List<Integer> integerList = new ArrayList<Integer>();

    public static void main(String... args) throws Exception {
        Field stringListField = ReflectionDemo2.class.getDeclaredField("stringList");
        ParameterizedType stringListType = (ParameterizedType) stringListField.getGenericType();
        Class<?> stringListClass = (Class<?>) stringListType.getActualTypeArguments()[0];
        System.out.println(stringListClass); // class java.lang.String.

        Field integerListField = ReflectionDemo2.class.getDeclaredField("integerList");
        ParameterizedType integerListType = (ParameterizedType) integerListField.getGenericType();
        Class<?> integerListClass = (Class<?>) integerListType.getActualTypeArguments()[0];
        System.out.println(integerListClass); // class java.lang.Integer.
        
        
        List<String> stringList = new ArrayList<String>();
        System.out.println(stringList.getClass().getComponentType()); // class java.lang.String.
    }
}