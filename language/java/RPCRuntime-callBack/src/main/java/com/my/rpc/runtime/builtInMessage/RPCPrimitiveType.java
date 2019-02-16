package com.my.rpc.runtime.builtInMessage;

import java.util.Arrays;
import java.util.Date;

import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.protocol.IRPCMessage;
import com.my.rpc.runtime.serializer.IDeserializer;
import com.my.rpc.runtime.serializer.ISerializer;

public final class RPCPrimitiveType
{
	public static class RPCVoid implements IRPCMessage
	{
		@Override
		public String toString()
		{
			return "RPCVoid()";
		}

		public RPCVoid()
		{
			
		}
		
		@Override
		public void serialize(ISerializer ser) throws RPCExceptionBase
		{
			ser.putVoid();
		}

		@Override
		public void deserialize(IDeserializer deser) throws RPCExceptionBase
		{
			deser.readVoid();
		}
	}
	
	public static class RPCInt implements IRPCMessage
	{
		private int val;
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString()
		{
			return "RPCInt(" + val + ")";
		}

		public RPCInt(int val)
		{
			this.val = val;
		}
		
		public RPCInt()
		{
			this.val = 0;
		}
		
		@Override
		public void serialize(ISerializer ser) throws RPCExceptionBase
		{
			ser.putInt(val);
		}

		@Override
		public void deserialize(IDeserializer deser) throws RPCExceptionBase
		{
			this.val = deser.readInt();
		}
		
		int getIntValue()
		{
			return this.val;
		}
		
		void valueFromInt(int val)
		{
			this.val = val;
		}
	}
	
	public static class RPCShort implements IRPCMessage
	{
		private short val;
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString()
		{
			return "RPCShort(" + val + ")";
		}

		public RPCShort(short val)
		{
			this.val = val;
		}
		
		public RPCShort()
		{
			this.val = 0;
		}
		
		@Override
		public void serialize(ISerializer ser) throws RPCExceptionBase
		{
			ser.putInt(val);
		}

		@Override
		public void deserialize(IDeserializer deser) throws RPCExceptionBase
		{
			this.val = deser.readShort();
		}
		
		short getShortValue()
		{
			return this.val;
		}
		
		void valueFromShort(short val)
		{
			this.val = val;
		}
	}
	
	
	public static class RPCLong implements IRPCMessage
	{
		private long val;
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString()
		{
			return "RPCLong(" + val + ")";
		}

		public RPCLong(long val)
		{
			this.val = val;
		}
		
		public RPCLong()
		{
			this.val = 0;
		}
		
		@Override
		public void serialize(ISerializer ser) throws RPCExceptionBase
		{
			ser.putLong(val);
		}

		@Override
		public void deserialize(IDeserializer deser) throws RPCExceptionBase
		{
			this.val = deser.readLong();
		}
		
		long getLongValue()
		{
			return this.val;
		}
		
		void valueFromLong(long val)
		{
			this.val = val;
		}
	}
	
	
	public static class RPCFloat implements IRPCMessage
	{
		private float val;
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString()
		{
			return "RPCFloat(" + val + ")";
		}

		public RPCFloat(float val)
		{
			this.val = val;
		}
		
		public RPCFloat()
		{
			this.val = 0;
		}
		
		@Override
		public void serialize(ISerializer ser) throws RPCExceptionBase
		{
			ser.putFloat(val);
		}

		@Override
		public void deserialize(IDeserializer deser) throws RPCExceptionBase
		{
			this.val = deser.readFloat();
		}
		
		public float getFloatValue()
		{
			return this.val;
		}
		
		public void valueFromFloat(float val)
		{
			this.val = val;
		}
	}
	
	public static class RPCDouble implements IRPCMessage
	{
		private double val;
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString()
		{
			return "RPCDouble(" + val + ")";
		}

		public RPCDouble(double val)
		{
			this.val = val;
		}
		
		public RPCDouble()
		{
			this.val = 0;
		}
		
		@Override
		public void serialize(ISerializer ser) throws RPCExceptionBase
		{
			ser.putDouble(val);
		}

		@Override
		public void deserialize(IDeserializer deser) throws RPCExceptionBase
		{
			this.val = deser.readDouble();
		}
		
		public double getDoubleValue()
		{
			return this.val;
		}
		
		public void valueFromDouble(double val)
		{
			this.val = val;
		}
	}
	
	
	public static class RPCString implements IRPCMessage
	{
		private String val;
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString()
		{
			return "RPCString('" + val + "')";
		}

		public RPCString(String val)
		{
			this.val = val;
		}
		
		public RPCString()
		{
			this.val = new String();
		}
		
		@Override
		public void serialize(ISerializer ser) throws RPCExceptionBase
		{
			ser.putString(val);
		}

		@Override
		public void deserialize(IDeserializer deser) throws RPCExceptionBase
		{
			this.val = deser.readString();
		}
		
		public String getStringValue()
		{
			return this.val;
		}
		
		public void valueFromString(String val)
		{
			this.val = val;
		}
	}
	
	public static class RPCByte implements IRPCMessage
	{
		private byte val;
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString()
		{
			return "RPCByte(" + val + ")";
		}

		public RPCByte(byte val)
		{
			this.val = val;
		}
		
		public RPCByte()
		{
			this.val = 0;
		}
		
		@Override
		public void serialize(ISerializer ser) throws RPCExceptionBase
		{
			ser.putByte(val);
		}

		@Override
		public void deserialize(IDeserializer deser) throws RPCExceptionBase
		{
			this.val = deser.readByte();
		}
		
		public byte getByteValue()
		{
			return this.val;
		}
		
		public void valueFromByte(byte val)
		{
			this.val = val;
		}
	}
	
	
	public static class RPCBinary implements IRPCMessage
	{
		private byte[] bytes;
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString()
		{
			return "RPCBinary(" + Arrays.toString(bytes) + ")";
		}

		public RPCBinary(byte[] val)
		{
			this.bytes = val;
		}
		
		public RPCBinary()
		{
			this.bytes = new byte[0];
		}
		
		public void serialize(ISerializer ser) throws RPCExceptionBase 
		{
		    ser.putBytes(bytes);
		}

		public void deserialize(IDeserializer deser) throws RPCExceptionBase 
		{
		   this.bytes = deser.readBytes();
		}
		
		public byte[] getBytesValue()
		{
			return this.bytes;
		}
		
		public void valueFromBytes(byte[] val)
		{
			this.bytes = val;
		}
	}
	
	
	public static class RPCBool implements IRPCMessage
	{
		private boolean val;
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString()
		{
			return "RPCBool(" + val + ")";
		}

		public RPCBool(boolean val)
		{
			this.val = val;
		}
		
		public RPCBool()
		{
			this.val = false;
		}
		
		public void serialize(ISerializer ser) throws RPCExceptionBase 
		{
		    ser.putBool(val);
		}

		public void deserialize(IDeserializer deser) throws RPCExceptionBase 
		{
			this.val = deser.readBool();
		}
		
		public boolean getBooleanValue()
		{
			return this.val;
		}
		
		public void valueFromBoolean(boolean val)
		{
			this.val = val;
		}
	}
	
	public static class RPCDate implements IRPCMessage
	{
		private Date date;
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString()
		{
			return "RPCDate(" + date + ")";
		}

		public RPCDate(long timeStamp)
		{
			this.date = new Date(timeStamp);
		}
		
		public RPCDate()
		{
			this.date = new Date();
		}
		
		public void serialize(ISerializer ser) throws RPCExceptionBase 
		{
		    ser.putLong(date.getTime());
		}

		public void deserialize(IDeserializer deser) throws RPCExceptionBase 
		{
		   this.date = new Date(deser.readLong());
		}
		
		public Date getDateValue()
		{
			return this.date;
		}
		
		public void valueFromDate(Date val)
		{
			this.date = val;
		}
	}
}
