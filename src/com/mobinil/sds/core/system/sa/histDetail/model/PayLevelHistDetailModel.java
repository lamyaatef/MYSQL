/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.sa.histDetail.model;

import java.sql.ResultSet;

/**
 *
 * @author sand
 */
public class PayLevelHistDetailModel {
    private String historyFileId;
    private String DCMId;
    private String DCMCode;
    private String DCMPaymentLevelId;
    private String channelId;

    /**
     * @return the historyFileId
     */
    public PayLevelHistDetailModel(ResultSet rs) {
        try
        {
            
                this.historyFileId = rs.getString("history_file_id");
                this.DCMCode = rs.getString("dcm_code");
                this.DCMId = rs.getString("dcm_id");
                this.DCMPaymentLevelId = rs.getString("dcm_payment_level_id");
                this.channelId = rs.getString("channel_id");
            
        }catch(Exception e){e.printStackTrace();}
    }
    public String getHistoryFileId() {
        return historyFileId;
    }

    /**
     * @param historyFileId the historyFileId to set
     */
    public void setHistoryFileId(String historyFileId) {
        this.historyFileId = historyFileId;
    }

    /**
     * @return the DCMId
     */
    public String getDCMId() {
        return DCMId;
    }

    /**
     * @param DCMId the DCMId to set
     */
    public void setDCMId(String DCMId) {
        this.DCMId = DCMId;
    }

    /**
     * @return the DCMCode
     */
    public String getDCMCode() {
        return DCMCode;
    }

    /**
     * @param DCMCode the DCMCode to set
     */
    public void setDCMCode(String DCMCode) {
        this.DCMCode = DCMCode;
    }

    /**
     * @return the DCMPaymentLevelId
     */
    public String getDCMPaymentLevelId() {
        return DCMPaymentLevelId;
    }

    /**
     * @param DCMPaymentLevelId the DCMPaymentLevelId to set
     */
    public void setDCMPaymentLevelId(String DCMPaymentLevelId) {
        this.DCMPaymentLevelId = DCMPaymentLevelId;
    }

    /**
     * @return the channelId
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * @param channelId the channelId to set
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
