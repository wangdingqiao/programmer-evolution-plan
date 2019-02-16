package com.my.rpc.core.IDLFileGeneratorBase;

public interface IFileImportManager
{
	public abstract void addName(String name, String packageLocation);
	public abstract String getNameImportStat(String name);
}

