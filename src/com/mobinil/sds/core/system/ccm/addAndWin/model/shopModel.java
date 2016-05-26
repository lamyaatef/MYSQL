package com.mobinil.sds.core.system.ccm.addAndWin.model;
import java.sql.ResultSet;
public class shopModel {
	    private String DCM_ID;
	    private String DCM_NAME;
	    
	    public String getDCM_ID() {
			return DCM_ID;
		}



		public void setDCM_ID(String dcm_id) {
			DCM_ID = dcm_id;
		}



		public String getDCM_NAME() {
			return DCM_NAME;
		}



		public void setDCM_NAME(String dcm_name) {
			DCM_NAME = dcm_name;
		}



		public shopModel() {
			// TODO Auto-generated constructor stub
		}



	    public shopModel(ResultSet res)
	    {
	      try
	      {
	    	  DCM_ID = res.getString("DCM_ID");
	    	  DCM_NAME = res.getString("DCM_NAME");

	      }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }
}

	 
