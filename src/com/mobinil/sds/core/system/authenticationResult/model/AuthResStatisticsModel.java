package com.mobinil.sds.core.system.authenticationResult.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;



public class AuthResStatisticsModel {
	
	
	
	
	    public String getFILE_ID() {
		return FILE_ID;
	}



	public void setFILE_ID(String file_id) {
		FILE_ID = file_id;
	}



	public String getNO_OF_READ_LINES() {
		return NO_OF_READ_LINES;
	}



	public void setNO_OF_READ_LINES(String no_of_read_lines) {
		NO_OF_READ_LINES = no_of_read_lines;
	}



	public String getNO_OF_INSERTED_LINES() {
		return NO_OF_INSERTED_LINES;
	}



	public void setNO_OF_INSERTED_LINES(String no_of_inserted_lines) {
		NO_OF_INSERTED_LINES = no_of_inserted_lines;
	}
	
	public Timestamp getStartTimesStamp() {
		return START_TIMESTAMP;
	}



	public void setStartTimeStamp(Timestamp startTimeStamp) {
		START_TIMESTAMP = startTimeStamp;
	}
	
	public Timestamp getEndTimesStamp() {
		return END_TIMESTAMP;
	}



	public void setEndTimeStamp(Timestamp endTimeStamp) {
		END_TIMESTAMP = endTimeStamp;
	}



		private String   FILE_ID;
	    private String  NO_OF_READ_LINES ;
	    private String  NO_OF_INSERTED_LINES ;
	    private Timestamp START_TIMESTAMP;
	    private Timestamp END_TIMESTAMP;
	 



public AuthResStatisticsModel()
{
	
}



public AuthResStatisticsModel(ResultSet res)
	    {
	      try
	      {
	    	  
	  
		    FILE_ID = res.getString("FILE_ID");
		    NO_OF_READ_LINES = res.getString("NO_OF_READ_LINES");
		    NO_OF_INSERTED_LINES = res.getString("NO_OF_INSERTED_LINES");
		    START_TIMESTAMP = res.getTimestamp("START_TIMESTAMP");
		    END_TIMESTAMP = res.getTimestamp("END_TIMESTAMP");
		
		    
	    	
            }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }
}
