/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.sa.monthList.model;

import java.sql.ResultSet;
import java.util.Date;

/**
 *
 * @author sand
 */
public class MonthOfTheListModel {
    
    private String HISTORY_FILE_ID;
    private String USER_ID;
    private String USERNAME;
    private Date TIMESTAMP;
    private String STATUS_ID;
    private int MONTH;
    private int YEAR;
    private String LIST_NAME;

    /**
     * @return the HISTORY_FILE_ID
     */
    
     public MonthOfTheListModel(ResultSet rs) {
        try
        {
         
                this.HISTORY_FILE_ID = rs.getString("HISTORY_FILE_ID");
                this.USERNAME = rs.getString("person_full_name"); 
                this.USER_ID = rs.getString("USER_ID");
                this.TIMESTAMP = rs.getDate("TIMESTAMP");
                this.STATUS_ID = rs.getString("STATUS_NAME");
                this.MONTH = rs.getInt("MONTH");
                this.YEAR = rs.getInt("YEAR");
                this.LIST_NAME = rs.getString("LIST_NAME");
            
        }catch(Exception e){e.printStackTrace();}
    }
        
    public MonthOfTheListModel()
    {}

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
     * @return the TIMESTAMP
     */
    public Date getTIMESTAMP() {
        return TIMESTAMP;
    }

    /**
     * @param TIMESTAMP the TIMESTAMP to set
     */
    public void setTIMESTAMP(Date TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }

    /**
     * @return the STATUS_ID
     */
    public String getSTATUS_ID() {
        return STATUS_ID;
    }

    /**
     * @param STATUS_ID the STATUS_ID to set
     */
    public void setSTATUS_ID(String STATUS_ID) {
        this.STATUS_ID = STATUS_ID;
    }

    /**
     * @return the MONTH
     */
    public int getMONTH() {
        return MONTH;
    }

    /**
     * @param MONTH the MONTH to set
     */
    public void setMONTH(int MONTH) {
        this.MONTH = MONTH;
    }

    /**
     * @return the YEAR
     */
    public int getYEAR() {
        return YEAR;
    }

    /**
     * @param YEAR the YEAR to set
     */
    public void setYEAR(int YEAR) {
        this.YEAR = YEAR;
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
