package com.mobinil.sds.core.system.dataMigration.model;
import java.sql.ResultSet;
public class InValid_Dcm_CodeModel {
	
	   private String DCM_CODE;

public String getPOS_CODE() {
		return DCM_CODE;
	}


	public void setPOS_CODE(String dcm_code) {
		DCM_CODE = dcm_code;
	}


public InValid_Dcm_CodeModel()
{
	
}


public InValid_Dcm_CodeModel(ResultSet res)
	    {
	      try
	      {
	    	  
	  
	    	  DCM_CODE = res.getString("DCM_CODE");

	    	
         }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }
}