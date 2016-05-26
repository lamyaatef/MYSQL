package com.mobinil.sds.core.system.sop.requests.model;
import java.io.Serializable;
import java.sql.ResultSet;

  public class chanelModel implements Serializable
	  {

		 	  

	  private  String CHANNEL_ID ;
	  private  String CHANNEL_NAME;


	    

		public String getCHANNEL_ID() {
		return CHANNEL_ID;
	}

	public void setCHANNEL_ID(String channel_id) {
		CHANNEL_ID = channel_id;
	}

	public String getCHANNEL_NAME() {
		return CHANNEL_NAME;
	}

	public void setCHANNEL_NAME(String channel_name) {
		CHANNEL_NAME = channel_name;
	}

		public chanelModel()
	    {
	      
	    }

	    public chanelModel(ResultSet res)
	    {
	      try
	      {
	    	  CHANNEL_ID = res.getString("CHANNEL_ID"); 
	    	  CHANNEL_NAME = res.getString("CHANNEL_NAME");
	    
	       
	      }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }


	
	 
	  }
	  
