/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.monthListFile.model;

import java.sql.ResultSet;

/**
 *
 * @author sand
 */
public class MonthListFileModel {
     private String  HISTORY_FILE_ID;
	    private String  USER_ID ;
	    private String  FILE_TIMESTAMP ;
	    private String  FILE_YEAR ;
            private String  FILE_MONTH;
            private String  FILE_STATUS_ID ;
            private String  STATUS_NAME ;
            private String  USERNAME ;
            private String LIST_NAME;

    /**
     * @return the HISTORY_FILE_ID
     */
            
               
public MonthListFileModel()
{
	
}
public MonthListFileModel(ResultSet res)
{
    try
	      {
	    	  
	  
		    HISTORY_FILE_ID = res.getString("HISTORY_FILE_ID");
                    FILE_TIMESTAMP = res.getString("TIMESTAMP");
                    FILE_MONTH = res.getString("MONTH");
                    FILE_YEAR = res.getString("YEAR");
                    FILE_STATUS_ID= res.getString("STATUS_ID");
                    USER_ID = res.getString("USER_ID");
                    USERNAME = res.getString("PERSON_FULL_NAME");
                    STATUS_NAME = res.getString("STATUS_NAME");
                    LIST_NAME = res.getString("LIST_NAME");
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
     * @return the FILE_STATUS_ID
     */
    public String getFILE_STATUS_ID() {
        return FILE_STATUS_ID;
    }

    /**
     * @param FILE_STATUS_ID the FILE_STATUS_ID to set
     */
    public void setFILE_STATUS_ID(String FILE_STATUS_ID) {
        this.FILE_STATUS_ID = FILE_STATUS_ID;
    }

    /**
     * @return the STATUS_NAME
     */
    public String getSTATUS_NAME() {
        return STATUS_NAME;
    }

    /**
     * @param STATUS_NAME the STATUS_NAME to set
     */
    public void setSTATUS_NAME(String STATUS_NAME) {
        this.STATUS_NAME = STATUS_NAME;
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

    /**
     * @return the LIST_NAME
     */
    public String getLIST_NAME() {
        return LIST_NAME;
    }

    /**
     * @param LIST_NAME the LIST_NAME to set
     */
    public void setLIST_NAME(String LIST_NAME) {
        this.LIST_NAME = LIST_NAME;
    }
    
}
