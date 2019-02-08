package com.learningjava.serializationPerformance.tester;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import com.learningjava.serializationPerformance.common.Beer;
import com.learningjava.serializationPerformance.common.BeerArray;
import com.learningjava.serializationPerformance.common.SerializationTester;

import sun.misc.Unsafe;

public class UnsafeSerializationTester extends SerializationTester
{
	
	@Override
	public String getName()
	{
		return "UnSafe";
	}
	
	@Override
	public byte[] serialize(BeerArray beerArray) throws Exception
	{
		byte[] preAllocateMem = new byte[128 * beerArray.size()];
		UnsafeMemory buffer = new UnsafeMemory(preAllocateMem);
		buffer.putInt(beerArray.size());
		for(Beer beer: beerArray.getBeers())
		{
			buffer.putUTF(beer.getBrand());
			buffer.putInt(beer.getSort().size());
			for(String sortName: beer.getSort().getNames())
			{
				buffer.putUTF(sortName);
			}
			buffer.putFloat(beer.getAlcohol());
			buffer.putUTF(beer.getBrewery());
		}
		byte[] result = new byte[buffer.position()];
		System.arraycopy(preAllocateMem,0,result,0,buffer.position());
		return result;
	}

	@Override
	public BeerArray deserialze(byte[] bytes) throws Exception
	{
		UnsafeMemory buffer = new UnsafeMemory(bytes);
		BeerArray beerArray = new BeerArray();
		int beerSize = buffer.getInt();
		for(int i = 0; i < beerSize; ++i)
		{
			Beer beer = new Beer();
			beer.setBrand(buffer.getUTF());
			int sortSize = buffer.getInt();
			for(int j = 0; j < sortSize;++j)
			{
				beer.getSort().add(buffer.getUTF());
			}
			beer.setAlcohol(buffer.getFloat());
			beer.setBrewery(buffer.getUTF());
			beerArray.addBeer(beer);
		}
		return beerArray;
	}
}


/*
 * 类修改自: https://mechanical-sympathy.blogspot.com/2012/07/native-cc-like-performance-for-java.html
 * 关于Unsafe 参考文档: http://www.docjar.com/docs/api/sun/misc/Unsafe.html
 */
class UnsafeMemory
{
    private static final Unsafe unsafe;
    static
    {
        try
        {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get(null);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private static final long byteArrayOffset = unsafe.arrayBaseOffset(byte[].class);
    private static final long longArrayOffset = unsafe.arrayBaseOffset(long[].class);
    private static final long doubleArrayOffset = unsafe.arrayBaseOffset(double[].class);

    private static final int SIZE_OF_BOOLEAN = 1;
    private static final int SIZE_OF_INT = 4;
    private static final int SIZE_OF_LONG = 8;
    private static final int SIZE_OF_FLOAT = 4;

    private int pos = 0;
    private final byte[] buffer;

    public UnsafeMemory(final byte[] buffer)
    {
        if (null == buffer)
        {
            throw new NullPointerException("buffer cannot be null");
        }

        this.buffer = buffer;
    }
    
    public int position()
    {
    	return this.pos;
    }
    
    public void reset()
    {
        this.pos = 0;
    }

    public void putBoolean(final boolean value)
    {
        unsafe.putBoolean(buffer, byteArrayOffset + pos, value);
        pos += SIZE_OF_BOOLEAN;
    }

    public boolean getBoolean()
    {
        boolean value = unsafe.getBoolean(buffer, byteArrayOffset + pos);
        pos += SIZE_OF_BOOLEAN;

        return value;
    }

    public void putInt(final int value)
    {
        unsafe.putInt(buffer, byteArrayOffset + pos, value);
        pos += SIZE_OF_INT;
    }

    public int getInt()
    {
        int value = unsafe.getInt(buffer, byteArrayOffset + pos);
        pos += SIZE_OF_INT;

        return value;
    }
    
    
    public void putFloat(final float value)
    {
        unsafe.putFloat(buffer, byteArrayOffset + pos, value);
        pos += SIZE_OF_FLOAT;
    }

    public float getFloat()
    {
        float value = unsafe.getFloat(buffer, byteArrayOffset + pos);
        pos += SIZE_OF_FLOAT;

        return value;
    }

    public void putLong(final long value)
    {
        unsafe.putLong(buffer, byteArrayOffset + pos, value);
        pos += SIZE_OF_LONG;
    }

    public long getLong()
    {
        long value = unsafe.getLong(buffer, byteArrayOffset + pos);
        pos += SIZE_OF_LONG;

        return value;
    }

    public void putLongArray(final long[] values)
    {
        putInt(values.length);

        long bytesToCopy = values.length << 3;
        unsafe.copyMemory(values, longArrayOffset,
                          buffer, byteArrayOffset + pos,
                          bytesToCopy);
        pos += bytesToCopy;
    }

    public long[] getLongArray()
    {
        int arraySize = getInt();
        long[] values = new long[arraySize];

        long bytesToCopy = values.length << 3;
        unsafe.copyMemory(buffer, byteArrayOffset + pos,
                          values, longArrayOffset,
                          bytesToCopy);
        pos += bytesToCopy;

        return values;
    }

    public void putDoubleArray(final double[] values)
    {
        putInt(values.length);

        long bytesToCopy = values.length << 3;
        unsafe.copyMemory(values, doubleArrayOffset,
                          buffer, byteArrayOffset + pos,
                          bytesToCopy);
        pos += bytesToCopy;
    }

    public double[] getDoubleArray()
    {
        int arraySize = getInt();
        double[] values = new double[arraySize];

        long bytesToCopy = values.length << 3;
        unsafe.copyMemory(buffer, byteArrayOffset + pos,
                          values, doubleArrayOffset,
                          bytesToCopy);
        pos += bytesToCopy;

        return values;
    }
    
    public void putUTF(final String text) throws UnsupportedEncodingException
    {
    	byte[] bytes = text.getBytes("utf-8");
    	long bytesToCopy = bytes.length;
    	putInt(bytes.length);
        unsafe.copyMemory(bytes, byteArrayOffset,
                           buffer, byteArrayOffset + pos,
                           bytesToCopy);
         pos += bytesToCopy;
    }
    
    public String getUTF() throws UnsupportedEncodingException
    {
    	 int strLength = getInt();
         byte[] values = new byte[strLength];

         long bytesToCopy = strLength;
         unsafe.copyMemory(buffer, byteArrayOffset + pos,
                           values, byteArrayOffset,
                           bytesToCopy);
         pos += bytesToCopy;

         return  new String(values, "utf-8");
    }
}

/*
 public native  void copyMemory(Object srcBase,
    long srcOffset,
    Object destBase,
    long destOffset,
    long bytes)
Sets all bytes in a given block of memory to a copy of another
block.
 */
