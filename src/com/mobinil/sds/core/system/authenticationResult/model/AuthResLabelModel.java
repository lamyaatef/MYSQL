
package com.mobinil.sds.core.system.authenticationResult.model;

import java.sql.ResultSet;



public class AuthResLabelModel {
	
	
	    private String   LABEL_ID ;
	    private String   LABEL_NAME ;
	    private String   LABEL_DESCRIPTION;

public String getLABEL_DESCRIPTION() {
			return LABEL_DESCRIPTION;
		}


		public void setLABEL_DESCRIPTION(String label_description) {
			LABEL_DESCRIPTION = label_description;
		}


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


public AuthResLabelModel()
{
	
}



public AuthResLabelModel(ResultSet res)
	    {
	      try
	      {
	    	  
	  
		    
		  
		    LABEL_ID = res.getString("LABEL_ID");
		    LABEL_NAME = res.getString("LABEL_NAME");
		    LABEL_DESCRIPTION = res.getString("LABEL_DESCRIPTION");
		    
		    
	    	
}
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }
}

