package com.mobinil.sds.core.system.dataMigration.model;

import java.sql.ResultSet;







public class ElgChkSearchFileModel {
	
	    private String SEARCH_FILE_ID;
	    private String  YEAR ;
	    private String   MONTH ;
	    private String   STATUS ;
	    private String DESCRIPTION;
	    

	 

	

public ElgChkSearchFileModel()
{
	
}








		public ElgChkSearchFileModel(ResultSet res)
	    {
	      try
	      {
	    	  
	  
	    	SEARCH_FILE_ID = res.getString("SEARCH_FILE_ID");
		    YEAR = res.getString("YEAR");
		    MONTH = res.getString("MONTH");
		    STATUS = res.getString("STATUS");
            DESCRIPTION = res.getString("DESCRIPTION");
	    	
 }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }








		public String getDESCRIPTION() {
			return DESCRIPTION;
		}








		public void setDESCRIPTION(String description) {
			DESCRIPTION = description;
		}








		public String getSEARCH_FILE_ID() {
			return SEARCH_FILE_ID;
		}








		public void setSEARCH_FILE_ID(String search_file_id) {
			SEARCH_FILE_ID = search_file_id;
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
}
