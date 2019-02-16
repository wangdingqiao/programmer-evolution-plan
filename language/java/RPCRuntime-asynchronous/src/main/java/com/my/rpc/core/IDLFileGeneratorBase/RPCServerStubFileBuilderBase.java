package com.my.rpc.core.IDLFileGeneratorBase;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

public abstract class RPCServerStubFileBuilderBase
{
	private String interfaceName;
	private Set<String> importStatments;

	public RPCServerStubFileBuilderBase()
	{
		importStatments = new HashSet<>();
		addBuildInImport();
	}
	
	private void addBuildInImport()
	{
		
	}
	
	/**
	 * @return the importStatments
	 */
	public Set<String> getImportStatments()
	{
		return importStatments;
	}

	/**
	 * @param importStatments the importStatments to set
	 */
	public void setImportStatments(Set<String> importStatments)
	{
		this.importStatments = importStatments;
	}
	/**
	 * @return the interfaceName
	 */
	public String getInterfaceName()
	{
		return interfaceName;
	}

	/**
	 * @param interfaceName the interfaceName to set
	 */
	public void setInterfaceName(String interfaceName)
	{
		this.interfaceName = interfaceName;
	}
	
	public abstract void addMethod(String name, String methodIndex, java.util.ArrayList<Pair<String, String>> inputParams, String responseType);
	public abstract String getText();
	
	public void addImportStat(String text)
	{
		if(text != null)
		{
			importStatments.add(text);
		}
	};
	public abstract void addPackageStat(String text);
}
