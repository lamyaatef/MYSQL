package com.mobinil.sds.core.system.dataMigration.model;
import java.sql.ResultSet;
public class fileModel {
	
	   private String FILE_ID;
	    private String  YEAR ;
	    private String   MONTH ;
	    private String   STATUS ;
	 

	

public fileModel()
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







		public fileModel(ResultSet res)
	    {
	      try
	      {
	    	  
	  
		    FILE_ID = res.getString("FILE_ID");
		    YEAR = res.getString("YEAR");
		    MONTH = res.getString("MONTH");
		    STATUS = res.getString("STATUS");
	    	
 }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }
}
