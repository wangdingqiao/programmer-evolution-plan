package com.my.rpc.runtime.exception;

import com.my.rpc.runtime.serializer.IDeserializer;
import com.my.rpc.runtime.serializer.ISerializer;

public class RPCExceptionFactory
{
	public static final byte RPC_Unknown_Exception = 1;
	public static final byte RPC_IO_Exception = 2;
	public static final byte RPC_Deserialize_Exception = 3;
	public static final byte RPC_Serialize_Exception = 4;
	public static final byte RPC_MethodFunctionOff_Exception = 5;
	public static final byte RPC_InterfaceNotFound_Exception = 6;
	public static final byte RPC_MethodNotFound_Exception = 7;
	public static final byte RPC_NullPointer_Exception = 8;
	public static final byte RPC_MessageDefinition_Exception = 9;
	public static final byte RPC_GenerateFile_Exception = 10;
	public static final byte RPC_InterfaceRuntime_Exception = 11;
	
	
	public static RPCExceptionBase createException(final byte exceptionId, Exception e)
	{
		return createException(exceptionId, e.getMessage());
	}
	
	public static RPCExceptionBase createException(final byte exceptionId, String message)
	{
		switch (exceptionId)
		{
		case RPC_IO_Exception:
			 	return new RPCIOException(exceptionId, message);
		case RPC_Deserialize_Exception:
				return new RPCDeserializeException(exceptionId, message);
		case RPC_Serialize_Exception:
				return new RPCSerializeException(exceptionId, message);
		case RPC_MethodFunctionOff_Exception:
				return new RPCMethodFunctionOffException(exceptionId, message);
		case RPC_InterfaceNotFound_Exception:
				return new RPCInterfaceNotFoundException(exceptionId, message);
		case RPC_MethodNotFound_Exception:
				return new RPCMethodNotFoundException(exceptionId, message);
		case RPC_NullPointer_Exception:
				return new RPCNullPointerException(exceptionId, message);
		case RPC_MessageDefinition_Exception:
				return new RPCMessageDefinitionException(exceptionId, message);
		case RPC_GenerateFile_Exception:
			    return new RPCGenerateFileException(exceptionId, message);
		case RPC_InterfaceRuntime_Exception:
				return new RPCInterfaceRuntimeException(exceptionId, message);
		case RPC_Unknown_Exception:
			return new RPCUnknowException(exceptionId, message);
		default:
			return null;
		}
	}
	
	public static RPCExceptionBase deserializeException(IDeserializer deser) throws RPCExceptionBase
	{
		if(deser == null)
		{
			throw createException(RPCExceptionFactory.RPC_NullPointer_Exception, "deserializeException null pointer.");
		}
		byte exceptionId = deser.readByte();
		if(exceptionId == 0)
		{
			return null;
		}
		String message = deser.readString();
		return createException(exceptionId, message);
	}
	
	public static void serializeException(RPCExceptionBase e, ISerializer ser) throws RPCExceptionBase
	{
		if(ser == null)
		{
			throw createException(RPCExceptionFactory.RPC_NullPointer_Exception, "serializeException null pointer.");
		}
		if(e == null)
		{
			ser.putByte((byte)0);
			return;
		}
		ser.putByte(e.getExceptionId());
		ser.putString(e.getMessage());
	}
}
