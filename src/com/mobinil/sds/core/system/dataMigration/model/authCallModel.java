package com.mobinil.sds.core.system.dataMigration.model;
import java.sql.ResultSet;

public class authCallModel {
	  
	
	
	   
	    public String getROW_NUM() {
			return ROW_NUM;
		}


	    private  String   ROW_NUM;
		private  String    DCM_CODE;
	    private  String   UNREAL_COUNT;
	    

public String getDCM_CODE() {
			return DCM_CODE;
		}


		public void setDCM_CODE(String dcm_code) {
			DCM_CODE = dcm_code;
		}


		public String getUNREAL_COUNT() {
			return UNREAL_COUNT;
		}


		public void setUNREAL_COUNT(String unreal_count) {
			UNREAL_COUNT = unreal_count;
		}


		public void setROW_NUM(String row_num) {
			ROW_NUM = row_num;
		}


public authCallModel()
{
	
}


		public authCallModel(ResultSet res)
	    {
	      try
	      {
	    	  
	    	  
	    	  ROW_NUM=res.getString("ROW_NUM");
	    	  DCM_CODE = res.getString("DCM_CODE");
	    	  UNREAL_COUNT = res.getString("UNREAL_COUNT");
  }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }
}