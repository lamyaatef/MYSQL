package com.mobinil.sds.core.system.authenticationResult.model;

import java.sql.ResultSet;

import com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey;



public class AuthResSearchFileModel {
	
	   private String SEARCH_FILE_ID;
	    public String getSEARCH_FILE_ID() {
		return SEARCH_FILE_ID;
	}


	public void setSEARCH_FILE_ID(String search_file_id) {
		SEARCH_FILE_ID = search_file_id;
	}


	public String getDESCRIPTION() {
		return DESCRIPTION;
	}


	public void setDESCRIPTION(String description) {
		DESCRIPTION = description;
	}

		private String  YEAR ;
	    private String   MONTH ;
	    private String   STATUS ;
	    private String   LABEL_ID ;
	    private String   LABEL_NAME ;
	   private String  DESCRIPTION;
	   private String  categoryName;
	   private String  fileTypeName;

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


public AuthResSearchFileModel()
{
	
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

public AuthResSearchFileModel(ResultSet res)
	    {
	      try
	      {
            SEARCH_FILE_ID = res.getString("SEARCH_FILE_ID");
		    YEAR = res.getString("YEAR");
		    MONTH = res.getString("MONTH");
		    STATUS = res.getString("STATUS");
		    LABEL_ID = res.getString("LABEL_ID");
		    LABEL_NAME = res.getString("LABEL_NAME");
		    DESCRIPTION=res.getString("DESCRIPTION");
		    categoryName=res.getString("cat_name");
		    
		    fileTypeName=res.getString("file_type_id");
		    if (fileTypeName==null)fileTypeName="";
		    else 
		    {
		       fileTypeName = fileTypeName.compareTo ( AuthResInterfaceKey.CONTROL_SELECT_OPTION_SIM_ONLY )==0?"Sim Only":"Sim With Extra Data";
		    }
		       
		    
		    
		    
		    
	    	
}
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }


public void setCategoryName(String categoryName)
{
   this.categoryName = categoryName;
}


public String getCategoryName()
{
   return categoryName;
}


public void setFileTypeName(String fileTypeName)
{
   this.fileTypeName = fileTypeName;
}


public String getFileTypeName()
{
   return fileTypeName;
}



}
