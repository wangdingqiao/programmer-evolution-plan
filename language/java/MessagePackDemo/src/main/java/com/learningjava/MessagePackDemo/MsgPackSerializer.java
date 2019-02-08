package com.learningjava.MessagePackDemo;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;


public class MsgPackSerializer implements IMessageSerializer
{

	@Override
	public byte[] serializeRequest(RPCRequest rpcRequest) throws Exception
	{
		return MsgPackEncoder.encode(rpcRequest);
	}

	@Override
	public RPCRequest deserialzeRequest(byte[] bytes) throws Exception
	{
		
		return MsgPackDecoder.decodeRPCRequest(bytes);
	}

	@Override
	public byte[] serializeResponse(RPCResponse rpcResponse) throws Exception
	{
		
		return MsgPackEncoder.encode(rpcResponse);
	}

	@Override
	public RPCResponse deserialzeResponse(byte[] bytes) throws Exception
	{
		
		return MsgPackDecoder.decodeRPCResponse(bytes);
	}
}


class MsgPackEncoder
{
	private static MessageBufferPacker packer;
	
	static
	{
		packer = MessagePack.newDefaultBufferPacker();
	}
	
	public static byte[] encode(RPCRequest rpcRequest) throws IOException, IllegalArgumentException, IllegalAccessException
	{
		MsgPackEncoder.packer.clear();
		MsgPackEncoder.packer.packLong(rpcRequest.requestId);
		MsgPackEncoder.packer.packString(rpcRequest.clazz.getName());
		MsgPackEncoder.packer.packString(rpcRequest.method);
		MsgPackEncoder.packer.packArrayHeader(rpcRequest.parameterTypes.length);
		for(Type paramType: rpcRequest.parameterTypes)
		{
			MsgPackEncoder.packer.packString(paramType.getTypeName());
		}
		for(Object param: rpcRequest.params)
		{
			encodeObject(param);
		}
		MsgPackEncoder.packer.packLong(rpcRequest.requestTime);
		return MsgPackEncoder.packer.toByteArray();
	}
	
	public static byte[] encode(RPCResponse rpcResponse) throws IOException, IllegalArgumentException, IllegalAccessException
	{
		MsgPackEncoder.packer.clear();
		MsgPackEncoder.packer.clear();
		MsgPackEncoder.packer.packLong(rpcResponse.requestId);
		MsgPackEncoder.packer.packString(rpcResponse.responseParamType.getTypeName());
		encodeObject(rpcResponse.response);
		FileOutputStream fos = new FileOutputStream(new File("respon.bytes"));
		fos.write(MsgPackEncoder.packer.toByteArray());
		fos.close();
		return MsgPackEncoder.packer.toByteArray();
	}
	
	public static void encodeObject(Object object) throws IllegalArgumentException, IllegalAccessException, IOException
	{
		if(object == null)
		{
			assert(false);
			return;
		}
		//System.out.println("encodeObject " + object.getClass() + " " + object);
		if(TypeTeller.canEncodeAsPrimitiveType(object.getClass()))
		{
			encodePrimitive(object);
		}
		else if(object.getClass().isEnum())
		{
			encodeEnum(object);
		}
		else if(object.getClass().isArray())
		{
			encodeArray(object);
		}
		else if(java.util.List.class.isAssignableFrom(object.getClass()))
    	{
			ArrayList<?> list = (ArrayList<?>)object;
    		encodeList(list);
    	}
		else if(java.util.Map.class.isAssignableFrom(object.getClass()))
    	{
			HashMap<?, ?> map = (HashMap<?, ?>)object;
    		encodeMap(map);
    	}
    	else 
    	{
			enCodeCustomStruct(object);
		}
	}

	
	private static void encodeEnum(Object object) throws IOException
	{
		MsgPackEncoder.packer.packInt(Integer.valueOf(object.toString()));
	}
	
	
	private static void encodeBinary(byte[] bytes) throws IOException
	{
		MsgPackEncoder.packer.packBinaryHeader(bytes.length);
		MsgPackEncoder.packer.writePayload(bytes);
	}
	
	private static void encodeArray(Object obj) throws IllegalArgumentException, IOException, ArrayIndexOutOfBoundsException, IllegalAccessException
	{
		if(byte[].class.equals(obj.getClass()))
		{
			byte[] bytes = (byte[])obj;
			encodeBinary(bytes);
		}
		else
		{
			MsgPackEncoder.packer.packArrayHeader(Array.getLength(obj));
			for(int i=0; i<Array.getLength(obj); i++)
			{
			   encodeObject(Array.get(obj, i));
			}
		}
	}
	
	private static void encodeList(List<?> list) throws IOException, IllegalArgumentException, IllegalAccessException
	{
		MsgPackEncoder.packer.packArrayHeader(list.size());
		for(Object value: list)
		{
			encodeObject(value);
		}
	}
	
	private static void encodeMap(Map<?, ?> map) throws IllegalArgumentException, IllegalAccessException, IOException
	{
		MsgPackEncoder.packer.packMapHeader(map.size());
		for (Map.Entry<?, ?> pair : map.entrySet()) 
		{
			encodeObject(pair.getKey());
		    encodeObject(pair.getValue());
		}
	}
	
	private static void encodePrimitive(Object object) throws IOException
	{
		Class<?> objType = object.getClass();
		if (Boolean.class.equals(objType) || boolean.class.equals(objType)) 
		 {
		    MsgPackEncoder.packer.packBoolean((Boolean)object);
		 }
		 else if (Byte.class.equals(objType) || byte.class.equals(objType)) 
		 {
		    MsgPackEncoder.packer.packByte((Byte)object);
		 }
		 else  if (Character.class.equals(objType) || char.class.equals(objType)) 
		 {
			    MsgPackEncoder.packer.packShort((Short)object);
		 }
		 else  if (String.class.equals(objType)) 
		 {
			    MsgPackEncoder.packer.packString((String)object);
		 }
		 else  if (Integer.class.equals(objType) || int.class.equals(objType)) 
		 {
			    MsgPackEncoder.packer.packInt((Integer)object);
		 }
		 else  if (Long.class.equals(objType) || long.class.equals(objType)) 
		 {
			 MsgPackEncoder.packer.packLong((Long)object);
		 }
		 else  if (Float.class.equals(objType) || float.class.equals(objType)) 
		 {
			 MsgPackEncoder.packer.packFloat((Float)object);
		 }
		 else  if (Double.class.equals(objType) || double.class.equals(objType)) 
		 {
			 MsgPackEncoder.packer.packDouble((Double)object);
		 }
	}
	
	private static void enCodeCustomStruct(Object object) throws IllegalArgumentException, IllegalAccessException, IOException
	{
		Field[] allFields = object.getClass().getDeclaredFields();
		ArrayList<Field> fields = new ArrayList<>();
		for (Field field : allFields) 
		{
		    if (Modifier.isStatic(field.getModifiers()) 
		    		|| Modifier.isTransient(field.getModifiers())
		    		|| Modifier.isFinal(field.getModifiers())) 
		    {
		    	continue;
		    }
		    fields.add(field);
		}
		MsgPackEncoder.packer.packArrayHeader(fields.size());
		fields.sort(Comparator.comparing(Field::getName));
		for (Field field : fields) 
		{
			field.setAccessible(true);
//		    String fieldName = field.getName();
//		    encodePrimitive(fieldName);
		    Object fieldValue = field.get(object);
	    	encodeObject(fieldValue);
		}
	}
}

class MsgPackDecoder
{
	public static RPCRequest decodeRPCRequest(byte[] bytes) throws ClassNotFoundException, IOException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(bytes);
		RPCRequest requset = new RPCRequest();
		requset.requestId = unpacker.unpackLong();
		requset.clazz = Class.forName(unpacker.unpackString());
		requset.method = unpacker.unpackString();
		int methodArgSize = unpacker.unpackArrayHeader();
		requset.parameterTypes = new Class<?>[methodArgSize];
		requset.params = new Object[methodArgSize];
		for(int i=0;i < methodArgSize;++i)
		{
			String typeNameStr = unpacker.unpackString();
			List<Class<?>> typeList = TypeTeller.parseTypeFromStr(typeNameStr);
			requset.parameterTypes[i] = typeList.get(0);
		}
		 // 这里需要通过反射找到原方法  由于java.util.ArrayList<String> 存在类型擦除 需要通过getGenericParameterTypes获取一遍完整的参数类型
		Method method = requset.clazz.getMethod(requset.method, (Class<?>[]) requset.parameterTypes);
		Type[] genericTypes = method.getGenericParameterTypes();
		for(int i =0; i < genericTypes.length;++i)
		{
			requset.params[i] = decodeObject(unpacker, genericTypes[i].getTypeName());
		}
		requset.requestTime = unpacker.unpackLong();
		return requset;
	}
	
	public static RPCResponse decodeRPCResponse(byte[] bytes) throws IOException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(bytes);
		RPCResponse response = new RPCResponse();
		response.requestId = unpacker.unpackLong();
		String responseTypeName = unpacker.unpackString();
		response.responseParamType = TypeTeller.parseTypeFromStr(responseTypeName).get(0);
		response.response = decodeObject(unpacker, responseTypeName);
		return response;
	}
	
	public static Object decodeObject(MessageUnpacker unpacker, String objTypeName) throws IOException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		List<Class<?>> typeList = TypeTeller.parseTypeFromStr(objTypeName);
		if(typeList == null)
		{
			assert(false);
			return null;
		}
		//System.out.println("Decode object with type: " + objTypeName);
		Class<?> objType = typeList.get(0);
		if(TypeTeller.canEncodeAsPrimitiveType(objType))
		{
			return decodePrimitive(unpacker, objType);
		}
		else if(objType.isEnum())
		{
			return decodeEnum(unpacker,objType);
		}
		else if(objType.isArray())
		{
			return decodeArray(unpacker, objType);
		}
		else if(java.util.List.class.isAssignableFrom((objType)))
    	{
			assert(typeList.size() >= 2);
			Class<?> subType = typeList.get(1);
    		return decodeList(unpacker, objType, subType);
    	}
		else if(java.util.Map.class.isAssignableFrom((objType)))
    	{
			assert(typeList.size() >= 3);
			Class<?> keyType = typeList.get(1);
			Class<?> valueType = typeList.get(2);
    		return decodeMap(unpacker, objType, keyType, valueType);
    	}
    	else 
    	{
			return decodeCustomStruct(unpacker, objType);
		}
	}
	
	
	private static byte[] decodeBinary(MessageUnpacker unpacker) throws IOException
	{
		int byteLen = unpacker.unpackBinaryHeader();
		byte[] bytes = new byte[byteLen];
		unpacker.readPayload(bytes);
		return bytes;
	}
	
	private static Object decodePrimitive(MessageUnpacker unpacker, Class<?> objType) throws IOException
	{
		 if (Boolean.class.equals(objType) || boolean.class.equals(objType)) 
		 {
		    return unpacker.unpackBoolean();
		 }
		 else if (Byte.class.equals(objType) || byte.class.equals(objType)) 
		 {
		    return unpacker.unpackByte();
		 }
		 else  if (Character.class.equals(objType) || char.class.equals(objType)) 
		 {
			return unpacker.unpackShort();
		 }
		 else  if (String.class.equals(objType)) 
		 {
			return unpacker.unpackString();
		 }
		 else  if (Integer.class.equals(objType) || int.class.equals(objType)) 
		 {
			return unpacker.unpackInt();
		 }
		 else  if (Long.class.equals(objType) || long.class.equals(objType)) 
		 {
			 return unpacker.unpackLong();
		 }
		 else  if (Float.class.equals(objType) || float.class.equals(objType)) 
		 {
			return unpacker.unpackFloat();
		 }
		 else  if (Double.class.equals(objType) || double.class.equals(objType)) 
		 {
			return unpacker.unpackDouble();
		 }
		 else 
		 {
			assert(false);
			return null;
		 }
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object decodeEnum(MessageUnpacker unpacker, Class<?> objType) throws IOException
	{
		String eumStr = unpacker.unpackString();
		return Enum.valueOf((Class<? extends Enum>)objType, eumStr);
	}
	
	private static Object decodeArray(MessageUnpacker unpacker, Class<?> innerType) throws IOException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		if(innerType == null)
		{
			assert(false);
			return null;
		}
		Object aObject = null;
		if(byte[].class.equals(innerType))
		{
			aObject = decodeBinary(unpacker);
		}
		else
		{
			int arrayLen = unpacker.unpackArrayHeader();
			aObject = Array.newInstance(innerType.getComponentType(), arrayLen);
	        int length = Array.getLength(aObject);
	        for (int i=0; i<length; i++)
	        {
	        	Object val = decodeObject(unpacker, innerType.getComponentType().getTypeName());
	        	Array.set(aObject, i, val);
	        }
		}
        return aObject;
	}
	
	private static Object decodeList(MessageUnpacker unpacker, Class<?> listType, Class<?> objType) throws IOException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException 
	{
		int objSize = unpacker.unpackArrayHeader();
		java.util.List<Object>  object = new java.util.ArrayList<>();
		for(int i = 0; i < objSize;++i)
		{
			Object subObj = decodeObject(unpacker, objType.getTypeName());
			object.add(subObj);
		}
		return object;
	}
	
	private static Object decodeMap(MessageUnpacker unpacker, Class<?> mapType, Class<?> keyType, Class<?> valueType) throws IOException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		int objSize = unpacker.unpackMapHeader();
		java.util.Map<Object, Object>  object = new java.util.HashMap<>();
		for(int i = 0; i < objSize;++i)
		{
			Object key = decodeObject(unpacker, keyType.getTypeName());
			Object value = decodeObject(unpacker, valueType.getTypeName());
			object.put(key, value);
		}
		return object;
	}
	
	
	
	private static Object decodeCustomStruct(MessageUnpacker unpacker, Class<?> objType) throws IllegalArgumentException, IllegalAccessException, IOException, InstantiationException, InvocationTargetException
	{
		Object object = objType.getConstructors()[0].newInstance();
		int objSize = unpacker.unpackArrayHeader();
		Field[] allFields = objType.getDeclaredFields();
		ArrayList<Field> fields = new ArrayList<>();
		for (Field field : allFields) 
		{
		    if (Modifier.isStatic(field.getModifiers()) 
		    		|| Modifier.isTransient(field.getModifiers())
		    		|| Modifier.isFinal(field.getModifiers())) 
		    {
		    	continue;
		    }
		    fields.add(field);
		}
		fields.sort(Comparator.comparing(Field::getName));
		assert(fields.size() == objSize);
		for(Field field: fields)
		{
			try
			{
				field.setAccessible(true);
				Object fieldValue = decodeObject(unpacker, field.getGenericType().getTypeName());
				field.set(object, fieldValue);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
//		Object object = objType.getConstructors()[0].newInstance();
//		int objSize = unpacker.unpackArrayHeader();
//		for(int i = 0; i < objSize;++i)
//		{
//			try
//			{
//				String fieldName = unpacker.unpackString();
//				Field field = objType.getDeclaredField(fieldName);
//				field.setAccessible(true);
//				Object fieldValue = decodeObject(unpacker, field.getGenericType().getTypeName());
//				field.set(object, fieldValue);
//			}
//			catch (Exception e)
//			{
//				System.out.println(e);
//			}
//		}
		return object;
	}
}