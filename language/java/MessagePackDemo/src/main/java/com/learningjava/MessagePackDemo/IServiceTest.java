package com.learningjava.MessagePackDemo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.bind.JAXBException;

public interface IServiceTest
{
	public BeerArray queryBeers(float param1, String param2, ArrayList<String> param3, HashMap<String, Integer> param4, byte[] param5, int[] param6) throws JAXBException, IOException;
}
