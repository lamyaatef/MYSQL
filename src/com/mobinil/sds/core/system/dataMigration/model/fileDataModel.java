package com.mobinil.sds.core.system.dataMigration.model;
import java.sql.ResultSet;
public class fileDataModel {
	
	    private String   FILE_ID;
	    private String  ACTIVATION_DATE ;
	    private String   DIAL_NO ;
	    private String   SIM ;
	    private String   SIM_EXTENDED ;
	    private String   POS_CODE ;

	 

	

public fileDataModel()
{
	
}


		public fileDataModel(ResultSet res)
	    {
	      try
	      {
	    	  
	  
		    FILE_ID = res.getString("FILE_ID");
		    ACTIVATION_DATE = res.getString("ACTIVATION_DATE");
		    DIAL_NO = res.getString("DIAL_NO");
		    SIM = res.getString("SIM");
		    SIM_EXTENDED = res.getString("SIM_EXTENDED");
		    POS_CODE = res.getString("POS_CODE");

	    	
     }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }
}