package com.mobinil.sds.core.system.ma.model;

import java.sql.ResultSet;

public class PrivilageStatusModel 
{
	public PrivilageStatusModel()
	{
		
		
		
	}
	
	 String privilageStatusId;
	 String privilageStatusName;
	 String privilageStatusDesc;
	 
	 
	 public static final String PRIVILAGE_STATUS_ID="PRIVILAGE_STATUS_ID";
	 public static final String PRIVILAGE_STATUS_NAME="PRIVILAGE_STATUS_NAME";
	 public static final String PRIVILAGE_STATUS_DESC="PRIVILAGE_STATUS_DESC";
	 
	 
	 public PrivilageStatusModel(ResultSet res)
	  {
	    try
	    {
	    	privilageStatusId = res.getString(PRIVILAGE_STATUS_ID);
	    	privilageStatusName = res.getString(PRIVILAGE_STATUS_NAME);
	    	privilageStatusDesc = res.getString(PRIVILAGE_STATUS_DESC);
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
	public String getPrivilageStatusId() {
		return privilageStatusId;
	}
	public void setPrivilageStatusId(String privilageStatusId) {
		this.privilageStatusId = privilageStatusId;
	}
	public String getPrivilageStatusName() {
		return privilageStatusName;
	}
	public void setPrivilageStatusName(String privilageStatusName) {
		this.privilageStatusName = privilageStatusName;
	}
	public String getPrivilageStatusDesc() {
		return privilageStatusDesc;
	}
	public void setPrivilageStatusDesc(String privilageDesc) {
		this.privilageStatusDesc = privilageDesc;
	}
}
