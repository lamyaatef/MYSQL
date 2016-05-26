
package com.mobinil.sds.core.system.dataMigration.model;
import java.sql.ResultSet;
public class CompDistModel {
	  
	
	
	   
	    public String getROW_NUM() {
			return ROW_NUM;
		}


	    private   String   ROW_NUM;
		private String    SCM_CODE;
	    private String    SCS ;
	    private String    ETOPUP;
	    
	   


public String getSCM_CODE() {
			return SCM_CODE;
		}


		public void setSCM_CODE(String scm_code) {
			SCM_CODE = scm_code;
		}


		public String getSCS() {
			return SCS;
		}


		public void setSCS(String scs) {
			SCS = scs;
		}


		public String getETOPUP() {
			return ETOPUP;
		}


		public void setETOPUP(String etopup) {
			ETOPUP = etopup;
		}


		public void setROW_NUM(String row_num) {
			ROW_NUM = row_num;
		}


public CompDistModel()
{
	
}


		public CompDistModel(ResultSet res)
	    {
	      try
	      {

	    	  
	    	  ROW_NUM=res.getString("ROW_NUM");
	    	  SCM_CODE = res.getString("SCM_CODE");
	    	  SCS = res.getString("SCS");
	    	  ETOPUP = res.getString("ETOPUP");
	    
	    	
     }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }
}