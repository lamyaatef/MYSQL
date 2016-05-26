package com.mobinil.sds.core.system.authenticationResult.model;

import java.sql.ResultSet;



public class AuthResInvalidsimSerialModel {

	    private String   SEARCH_ID;
	    public String getSEARCH_ID() {
			return SEARCH_ID;
		}




		public void setSEARCH_ID(String search_id) {
			SEARCH_ID = search_id;
		}




		public String getSIM_SERIAL() {
			return SIM_SERIAL;
		}




		public void setSIM_SERIAL(String sim_serial) {
			SIM_SERIAL = sim_serial;
		}




		private String  SIM_SERIAL;
	




public AuthResInvalidsimSerialModel()
{
	
}




public AuthResInvalidsimSerialModel(ResultSet res)
	    {
	      try
	      {
	    	  
	  
	    	  SEARCH_ID = res.getString("SEARCH_ID");
	    	  SIM_SERIAL = res.getString("SIM_SERIAL");

		    
	    	
}
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }
}
