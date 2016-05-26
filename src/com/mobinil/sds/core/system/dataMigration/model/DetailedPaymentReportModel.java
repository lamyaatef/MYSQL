package com.mobinil.sds.core.system.dataMigration.model;
import java.sql.ResultSet;
public class DetailedPaymentReportModel {
	  
	private   String   ROW_NUM;
	    public String getROW_NUM() {
		return ROW_NUM;
	}


	public void setROW_NUM(String row_num) {
		ROW_NUM = row_num;
	}


	public String getNAME() {
		return NAME;
	}


	public void setNAME(String name) {
		NAME = name;
	}


	public String getPAYMENT_SERIAL() {
		return PAYMENT_SERIAL;
	}


	public void setPAYMENT_SERIAL(String payment_serial) {
		PAYMENT_SERIAL = payment_serial;
	}


	public String getPAYMENT_DATE() {
		return PAYMENT_DATE;
	}


	public void setPAYMENT_DATE(String payment_date) {
		PAYMENT_DATE = payment_date;
	}


	public String getCUSTOMER_CODE() {
		return CUSTOMER_CODE;
	}


	public void setCUSTOMER_CODE(String customer_code) {
		CUSTOMER_CODE = customer_code;
	}


	public String getMOBILE_NUMBER() {
		return MOBILE_NUMBER;
	}


	public void setMOBILE_NUMBER(String mobile_number) {
		MOBILE_NUMBER = mobile_number;
	}


	public String getTOTAL_AMOUNT() {
		return TOTAL_AMOUNT;
	}


	public void setTOTAL_AMOUNT(String total_amount) {
		TOTAL_AMOUNT = total_amount;
	}


		private String    NAME ;
	    private String    PAYMENT_SERIAL ;
	    private String    PAYMENT_DATE ;
	    private String    CUSTOMER_CODE ;
	    private String    MOBILE_NUMBER ;
	    private String    TOTAL_AMOUNT   ;


public DetailedPaymentReportModel()
{
	
}


		public DetailedPaymentReportModel(ResultSet res)
	    {
	      try
	      {
	    	  
	    	  ROW_NUM=res.getString("ROW_NUM");
	    	  NAME = res.getString("NAME");
	    	  PAYMENT_SERIAL = res.getString("PAYMENT_SERIAL");
	    	  PAYMENT_DATE = res.getString("PAYMENT_DATE");
	    	  CUSTOMER_CODE = res.getString("CUSTOMER_CODE");
	    	  MOBILE_NUMBER = res.getString("MOBILE_NUMBER");
	    	  TOTAL_AMOUNT = res.getString("TOTAL_AMOUNT");

	    	
     }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	    }
}