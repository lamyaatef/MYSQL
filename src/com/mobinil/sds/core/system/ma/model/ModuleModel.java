package com.mobinil.sds.core.system.ma.model;

import java.sql.ResultSet;

public class ModuleModel {
	public ModuleModel()
	{
		
		
		
	}
	
	 String moduleId;
	 String moduleName;
	 String moduleDesc;
	 String moduleStatusId;
	 String moduleStatusName;
	 
	 public static final String MODULE_ID="MODULE_ID";
	 public static final String MODULE_NAME="MODULE_NAME";
	 public static final String MODULE_DESC="MODULE_DESC";
	 public static final String MODULE_STATUS_ID="MODULE_STATUS_ID";
	 public static final String MODULE_STATUS_NAME="MODULE_STATUS_NAME";

	  public ModuleModel(ResultSet res)
	  {
	    try
	    {
	    	moduleId = res.getString(MODULE_ID);
	    	moduleName = res.getString(MODULE_NAME);
	    	moduleDesc = res.getString(MODULE_DESC);
	    	moduleStatusId=res.getString(MODULE_STATUS_ID);
	    	//moduleStatusName=res.getString(MODULE_STATUS_NAME);
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	  }

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleDesc() {
		return moduleDesc;
	}

	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}

	public String getModuleStatusId() {
		return moduleStatusId;
	}

	public void setModuleStatusId(String moduleStatusId) {
		this.moduleStatusId = moduleStatusId;
	}

	public String getModuleStatusName() {
		return moduleStatusName;
	}

	public void setModuleStatusName(String moduleStatusName) {
		this.moduleStatusName = moduleStatusName;
	}
}
