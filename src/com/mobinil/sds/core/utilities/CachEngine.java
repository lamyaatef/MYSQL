package com.mobinil.sds.core.utilities;
import java.util.*;

public class CachEngine  {
	private CachEngine() {
	}
	private static Hashtable storeTable;

	static {
		storeTable = new Hashtable();
	}

	public static void storeObject(String key , Object o ) 
	{
		storeTable.put(key,o);

	}

	public static void removeObject(String key) {
		for(int j=0; j<storeTable.size(); j++)
		{
			String tempStatusString = (String)storeTable.keySet().toArray()[j];
			if((key.compareToIgnoreCase(tempStatusString)) == 0) {
				storeTable.remove(tempStatusString);     
			}
		}
		//Utility.logger.debug("key length : "+key.length());
		//Utility.logger.debug(key);
		//storeTable.remove(key);     
	}

	public static void showAllObjects(String key){
		for(int j=0; j<storeTable.size(); j++)
		{
			String tempStatusString = (String)storeTable.keySet().toArray()[j];
			Utility.logger.debug("key cpmpare to keys in hashtable : "+key.compareToIgnoreCase(tempStatusString));
			Utility.logger.debug("wwwwwwwwww"+tempStatusString);
		}    
	}
	public static Object getObject(String key) {
		return storeTable.get(key);
	}
}