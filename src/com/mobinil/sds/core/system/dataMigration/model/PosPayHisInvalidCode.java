


package com.mobinil.sds.core.system.dataMigration.model;

import java.sql.ResultSet;



public class PosPayHisInvalidCode {

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
	




public PosPayHisInvalidCode()
{
	
}




public PosPayHisInvalidCode(ResultSet res)
	    {
	      try
	        {	  
	    	      
	    	  SEARCH_ID = res.getString("SEARCH_ID");   	  
      	          SIM_SERIAL = res.getString("code");
               }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }
}
