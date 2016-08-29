/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.sa.monthListDetail.model;

import java.sql.Date;
import java.sql.ResultSet;

/**
 *
 * @author sand
 */
public class MonthOfTheListDetailModel {
    private String HISTORY_FILE_ID;
    private String DCM_ID;
    private String DCM_CODE;
    private String DCM_PAYMENT_LEVEL_ID;
    private String CHANNEL_ID;
    private String LIST_NAME;

    /**
     * @return the userId
     */
    
    
        public MonthOfTheListDetailModel(ResultSet rs) {
        try
        {
         
                this.HISTORY_FILE_ID = rs.getString("HISTORY_FILE_ID");
                this.DCM_ID = rs.getString("DCM_ID");
                this.DCM_CODE = rs.getString("DCM_CODE");
                this.DCM_PAYMENT_LEVEL_ID = rs.getString("DCM_PAYMENT_LEVEL_ID");
                this.CHANNEL_ID = rs.getString("CHANNEL_ID");
                this.LIST_NAME = rs.getString("LIST_NAME");
                
            
        }catch(Exception e){e.printStackTrace();}
    }
        
    public MonthOfTheListDetailModel()
    {}

    /**
     * @return the HISTORY_FILE_ID
     */
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
     * @return the DCM_PAYMENT_LEVEL_ID
     */
    public String getDCM_PAYMENT_LEVEL_ID() {
        return DCM_PAYMENT_LEVEL_ID;
    }

    /**
     * @param DCM_PAYMENT_LEVEL_ID the DCM_PAYMENT_LEVEL_ID to set
     */
    public void setDCM_PAYMENT_LEVEL_ID(String DCM_PAYMENT_LEVEL_ID) {
        this.DCM_PAYMENT_LEVEL_ID = DCM_PAYMENT_LEVEL_ID;
    }

    /**
     * @return the CHANNEL_ID
     */
    public String getCHANNEL_ID() {
        return CHANNEL_ID;
    }

    /**
     * @param CHANNEL_ID the CHANNEL_ID to set
     */
    public void setCHANNEL_ID(String CHANNEL_ID) {
        this.CHANNEL_ID = CHANNEL_ID;
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
