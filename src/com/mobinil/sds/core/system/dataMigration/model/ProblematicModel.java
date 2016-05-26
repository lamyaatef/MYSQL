package com.mobinil.sds.core.system.dataMigration.model;
import java.io.Serializable;
import java.sql.ResultSet;

public class ProblematicModel implements Serializable{
	
	String problematicId;
	public String getProblematicId() {
		return problematicId;
	}
	public void setProblematicId(String problematicId) {
		this.problematicId = problematicId;
	}
	public String getProblematicName() {
		return problematicName;
	}
	public void setProblematicName(String problematicName) {
		this.problematicName = problematicName;
	}
	String problematicName;
	
	public static final String PROBLEMATIC_LABEL_ID = "PROBLEMATIC_LABEL_ID";
	public static final String PROBLEMATIC_LABEL_NAME = "PROBLEMATIC_LABEL_NAME";
	
	public ProblematicModel(){
		
	}
	public ProblematicModel(ResultSet res)
	{
		try
		{
			problematicId = res.getString(PROBLEMATIC_LABEL_ID);
			problematicName = res.getString(PROBLEMATIC_LABEL_NAME);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
