package com.my.rpc.core.IDLFileGenerator.java;

import java.util.Map;

import com.my.rpc.core.IDLFileGeneratorBase.IFileImportManager;

public class FileImportHelper implements IFileImportManager
{
	private  Map<String, String> name2Import = new java.util.HashMap<>();
	
	public FileImportHelper()
	{
		name2Import.put("ArrayList", "import java.util.ArrayList;");
		name2Import.put("Map", "import java.util.Map;");
		name2Import.put("HashMap", "import java.util.HashMap;");
		name2Import.put("Date", "java.util.Date");
	}
	
	public void addName(String name, String packageLocation)
	{
		name2Import.put(name, "import " + packageLocation +"." + name + ";");
	}
	
	public String getNameImportStat(String name)
	{
		if(TypeMapper.instance().isMapType(name))
		{
			return name2Import.get("HashMap");
		}
		else if(TypeMapper.instance().isSeqType(name))
		{
			return name2Import.get("ArrayList");
		}
		else if(TypeMapper.instance().isCustomType(name))
		{
			return name2Import.get(name);
		}
		return name2Import.get(name);
	}
}

