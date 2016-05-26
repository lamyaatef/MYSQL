package com.mobinil.sds.core.system.sop.requests.model;

import java.io.Serializable;

import java.sql.ResultSet;

  public class reportModel implements Serializable
	  {

		 	  

	  private  String REPORT_ID ;
	  private  String REPORT_NAME;


	    

	    public String getREPORT_ID() {
		return REPORT_ID;
	}

	public void setREPORT_ID(String report_id) {
		REPORT_ID = report_id;
	}

	public String getREPORT_NAME() {
		return REPORT_NAME;
	}

	public void setREPORT_NAME(String report_name) {
		REPORT_NAME = report_name;
	}

		public reportModel()
	    {
	      
	    }

	    public reportModel(ResultSet res)
	    {
	      try
	      {
	    	  REPORT_ID = res.getString("REPORT_ID"); 
	    	  REPORT_NAME = res.getString("REPORT_NAME");
	    
	       
	      }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }


	
	 
	  }
	  
