/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.paymentHistory.model;

import java.sql.ResultSet;

/**
 *
 * @author sand
 */
public class PaymentHistoryFileModel {
            private String  HISTORY_FILE_ID;
	    private String  USER_ID ;
	    private String  FILE_TIMESTAMP ;
	    private String  DCM_CHANNEL_NAME ;
	    private String  DCM_PAYMENT_LEVEL ;
	    private String  DCM_CODE ;
            private String  DCM_ID ;
            private String  FILE_YEAR ;
            private String  FILE_MONTH;
            private String  FILE_STATUS ;
            private String USERNAME;

    /**
     * @return the HISTORY_FILE_ID
     */
            
               
public PaymentHistoryFileModel()
{
	
}
public PaymentHistoryFileModel(ResultSet res)
{
    try
	      {
	    	  
	  
		    HISTORY_FILE_ID = res.getString("HISTORY_FILE_ID");
                    FILE_TIMESTAMP = res.getString("TIMESTAMP");
                    DCM_CHANNEL_NAME =  res.getString("CHANNEL_NAME");
                    DCM_PAYMENT_LEVEL =  res.getString("DCM_PAYMENT_LEVEL_NAME");
                    DCM_CODE = res.getString("DCM_CODE");
                    FILE_YEAR = res.getString("YEAR");
                    FILE_MONTH = res.getString("MONTH");
                    FILE_STATUS= res.getString("STATUS_NAME");
                    USERNAME= res.getString("PERSON_FULL_NAME");
		    
                }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	
}        
            
            
    public String getHISTORY_FILE_ID() {
        return HISTORY_FILE_ID;
    }

    /**
     * @param HISTORY_FILE_ID the HISTORY_FILE_ID to set
     */
    public void setHISTORY_FILE_ID(String HISTORY_FILE_ID) {
        this.HISTORY_FILE_ID = HISTORY_FILE_ID;
    }

    /**
     * @return the USER_ID
     */
    public String getUSER_ID() {
        return USER_ID;
    }

    /**
     * @param USER_ID the USER_ID to set
     */
    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    /**
     * @return the FILE_TIMESTAMP
     */
    public String getFILE_TIMESTAMP() {
        return FILE_TIMESTAMP;
    }

    /**
     * @param FILE_TIMESTAMP the FILE_TIMESTAMP to set
     */
    public void setFILE_TIMESTAMP(String FILE_TIMESTAMP) {
        this.FILE_TIMESTAMP = FILE_TIMESTAMP;
    }

    /**
     * @return the DCM_CHANNEL_NAME
     */
    public String getDCM_CHANNEL_NAME() {
        return DCM_CHANNEL_NAME;
    }

    /**
     * @param DCM_CHANNEL_NAME the DCM_CHANNEL_NAME to set
     */
    public void setDCM_CHANNEL_NAME(String DCM_CHANNEL_NAME) {
        this.DCM_CHANNEL_NAME = DCM_CHANNEL_NAME;
    }

    /**
     * @return the DCM_PAYMENT_LEVEL
     */
    public String getDCM_PAYMENT_LEVEL() {
        return DCM_PAYMENT_LEVEL;
    }

    /**
     * @param DCM_PAYMENT_LEVEL the DCM_PAYMENT_LEVEL to set
     */
    public void setDCM_PAYMENT_LEVEL(String DCM_PAYMENT_LEVEL) {
        this.DCM_PAYMENT_LEVEL = DCM_PAYMENT_LEVEL;
    }

    /**
     * @return the DCM_CODE
     */
    public String getDCM_CODE() {
        return DCM_CODE;
    }

    /**
     * @param DCM_CODE the DCM_CODE to set
     */
    public void setDCM_CODE(String DCM_CODE) {
        this.DCM_CODE = DCM_CODE;
    }

    /**
     * @return the DCM_ID
     */
    public String getDCM_ID() {
        return DCM_ID;
    }

    /**
     * @param DCM_ID the DCM_ID to set
     */
    public void setDCM_ID(String DCM_ID) {
        this.DCM_ID = DCM_ID;
    }

    /**
     * @return the FILE_YEAR
     */
    public String getFILE_YEAR() {
        return FILE_YEAR;
    }

    /**
     * @param FILE_YEAR the FILE_YEAR to set
     */
    public void setFILE_YEAR(String FILE_YEAR) {
        this.FILE_YEAR = FILE_YEAR;
    }

    /**
     * @return the FILE_MONTH
     */
    public String getFILE_MONTH() {
        return FILE_MONTH;
    }

    /**
     * @param FILE_MONTH the FILE_MONTH to set
     */
    public void setFILE_MONTH(String FILE_MONTH) {
        this.FILE_MONTH = FILE_MONTH;
    }

    /**
     * @return the FILE_STATUS
     */
    public String getFILE_STATUS() {
        return FILE_STATUS;
    }

    /**
     * @param FILE_STATUS the FILE_STATUS to set
     */
    public void setFILE_STATUS(String FILE_STATUS) {
        this.FILE_STATUS = FILE_STATUS;
    }

    /**
     * @return the USERNAME
     */
    public String getUSERNAME() {
        return USERNAME;
    }

    /**
     * @param USERNAME the USERNAME to set
     */
    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }
    
}
