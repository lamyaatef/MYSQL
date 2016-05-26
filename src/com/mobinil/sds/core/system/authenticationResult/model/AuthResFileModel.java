package com.mobinil.sds.core.system.authenticationResult.model;

import java.sql.ResultSet;



public class AuthResFileModel {
	
	   private String   FILE_ID;
	    private String  YEAR ;
	    private String   MONTH ;
	    private String   STATUS ;
	    private String   LABEL_ID ;
	    private String   LABEL_NAME ;

public String getLABEL_ID() {
			return LABEL_ID;
		}


		public void setLABEL_ID(String label_id) {
			LABEL_ID = label_id;
		}


		public String getLABEL_NAME() {
			return LABEL_NAME;
		}


		public void setLABEL_NAME(String label_name) {
			LABEL_NAME = label_name;
		}


public AuthResFileModel()
{
	
}


		public String getFILE_ID() {
	return FILE_ID;
}



public void setFILE_ID(String file_id) {
	FILE_ID = file_id;
}


public String getYEAR() {
	return YEAR;
}


public void setYEAR(String year) {
	YEAR = year;
}

public String getMONTH() {
	return MONTH;
}


public void setMONTH(String month) {
	MONTH = month;
}


public String getSTATUS() {
	return STATUS;
}


public void setSTATUS(String status) {
	STATUS = status;
}

public AuthResFileModel(ResultSet res)
	    {
	      try
	      {
	    	  
	  
		    FILE_ID = res.getString("FILE_ID");
		    YEAR = res.getString("YEAR");
		    MONTH = res.getString("MONTH");
		    STATUS = res.getString("STATUS");
		    LABEL_ID = res.getString("LABEL_ID");
		    LABEL_NAME = res.getString("LABEL_NAME");
		    
	    	
}
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }
}
