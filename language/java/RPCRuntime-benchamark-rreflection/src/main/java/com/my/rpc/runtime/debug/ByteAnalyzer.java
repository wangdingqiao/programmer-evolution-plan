package com.my.rpc.runtime.debug;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ByteAnalyzer
{
	public static void writeBytesToFile(byte[] bytes, String fileName)
	{
		if(bytes == null)
		{
			return;
		}
		try
		{
			if(fileName == null)
			{
				fileName = "";
			}
			fileName += "-" + String.valueOf(System.currentTimeMillis());
			File outFile = new File("Debug");
			outFile.mkdir();
			FileOutputStream fio = new FileOutputStream("Debug" + File.separator + fileName + "-length-" + bytes.length+".bin");
			writeBytesToStream(bytes, fio);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeBytesToStream(byte[] bytes, OutputStream os)
	{
		if(bytes == null)
		{
			return;
		}
		try
		{
			os.write(bytes);
			os.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
