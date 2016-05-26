package com.mobinil.sds.core.system.dataMigration.model;
import java.sql.ResultSet;
public class zipUploadStatisticsModel {
	
	    private String   FILE_ID;
	    public String getFILE_ID() {
			return FILE_ID;
		}


		public void setFILE_ID(String file_id) {
			FILE_ID = file_id;
		}


		public String getNO_READ_LINES() {
			return NO_READ_LINES;
		}


		public void setNO_READ_LINES(String no_read_lines) {
			NO_READ_LINES = no_read_lines;
		}


		public String getNO_INSERTED_LINES() {
			return NO_INSERTED_LINES;
		}


		public void setNO_INSERTED_LINES(String no_inserted_lines) {
			NO_INSERTED_LINES = no_inserted_lines;
		}


		public String getIN_VALID_POS_CODE() {
			return IN_VALID_POS_CODE;
		}


		public void setIN_VALID_POS_CODE(String in_valid_pos_code) {
			IN_VALID_POS_CODE = in_valid_pos_code;
		}


		public String getDISTINCT_IN_VALID_POS_CODE() {
			return DISTINCT_IN_VALID_POS_CODE;
		}


		public void setDISTINCT_IN_VALID_POS_CODE(String distinct_in_valid_pos_code) {
			DISTINCT_IN_VALID_POS_CODE = distinct_in_valid_pos_code;
		}


		private String  NO_READ_LINES ;
	    private String   NO_INSERTED_LINES ;
	    private String   IN_VALID_POS_CODE ;
	    private String   DISTINCT_IN_VALID_POS_CODE ;


	 

	

public zipUploadStatisticsModel()
{
	
}


		public zipUploadStatisticsModel(ResultSet res)
	    {
	      try
	      {
	    	  
	  
		    FILE_ID = res.getString("FILE_ID");
		    NO_READ_LINES = res.getString("NO_READ_LINES");
		    NO_INSERTED_LINES = res.getString("NO_INSERTED_LINES");
		    IN_VALID_POS_CODE = res.getString("IN_VALID_POS_CODE");
		    DISTINCT_IN_VALID_POS_CODE = res.getString("DISTINCT_IN_VALID_POS_CODE");

    	
     }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }
}