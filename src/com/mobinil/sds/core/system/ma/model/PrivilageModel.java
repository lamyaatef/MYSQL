package com.mobinil.sds.core.system.ma.model;

import java.sql.ResultSet;

public class PrivilageModel 
{
	 String privilageId;
	 String privilageName;
	 String privilageDesc;
	 String moduleId;
	 String moduleName;
	 String privilageStatusId;
	 String privilageStatusName;
	 String privilageActionName;
	 String orderValue;
	 String privilageTarget;
		public PrivilageModel()
		{
			
			
			
		}
		 public static final String PRIVILAGE_ID="PRIVILAGE_ID";
		 public static final String PRIVILAGE_NAME="PRIVILAGE_NAME";
		 public static final String PRIVILAGE_DESC="PRIVILAGE_DESC";
		 public static final String MODULE_ID="MODULE_ID";
		 public static final String MODULE_NAME="MODULE_NAME";
		 public static final String PRIVILAGE_STATUS_ID="PRIVILAGE_STATUS_ID";
		 public static final String PRIVILAGE_STATUS_NAME="PRIVILAGE_STATUS_NAME";
		 public static final String PRIVILAGE_ACTION_NAME="PRIVILAGE_ACTION_NAME";
		 public static final String ORDER_VALUE="ORDER_VALUE";
		 public static final String PRIVILAGE_TARGET="PRIVILAGE_TARGET";

		  public String getPrivilageId() {
			return privilageId;
		}

		public void setPrivilageId(String privilageId) {
			this.privilageId = privilageId;
		}

		public String getPrivilageName() {
			return privilageName;
		}

		public void setPrivilageName(String privilageName) {
			this.privilageName = privilageName;
		}

		public String getPrivilageDesc() {
			return privilageDesc;
		}

		public void setPrivilageDesc(String privilageDesc) {
			this.privilageDesc = privilageDesc;
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

		public String getPrivilageActionName() {
			return privilageActionName;
		}

		public void setPrivilageActionName(String privilageActionName) {
			this.privilageActionName = privilageActionName;
		}

		public String getOrderValue() {
			return orderValue;
		}

		public void setOrderValue(String orderValue) {
			this.orderValue = orderValue;
		}

		public String getPrivilageTarget() {
			return privilageTarget;
		}

		public void setPrivilageTarget(String privilageTarget) {
			this.privilageTarget = privilageTarget;
		}

		public PrivilageModel(ResultSet res)
		  {
		    try
		    {
		    	privilageId = res.getString(PRIVILAGE_ID);
		    	privilageName = res.getString(PRIVILAGE_NAME);
		    	privilageDesc = res.getString(PRIVILAGE_DESC);
		    	moduleId=res.getString(MODULE_ID);
		    	privilageStatusId=res.getString(PRIVILAGE_STATUS_ID);
		    	privilageActionName=res.getString(PRIVILAGE_ACTION_NAME);
		    	orderValue=res.getString(ORDER_VALUE);
		    	privilageTarget=res.getString(PRIVILAGE_TARGET);
		    	//moduleStatusName=res.getString(MODULE_STATUS_NAME);
		    }
		    catch(Exception e)
		    {
		      e.printStackTrace();
		    }
		  }
}
