/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.sa.history.model;

import java.sql.Date;
import java.sql.ResultSet;

/**
 *
 * @author sand
 */
public class PayLevelHistroyModel {
    private String userId;
    private String statusId;
    private int month;
    private int year;
    private String historyFileId;
    private Date timestamp;

    /**
     * @return the userId
     */
    
    public PayLevelHistroyModel(ResultSet rs) {
        try
        {
            
                this.month = rs.getInt("month");
                this.year = rs.getInt("year");
                this.userId = rs.getString("user_id");
                this.statusId = rs.getString("status_id");
                this.historyFileId = rs.getString("history_file_id");
                this.timestamp = rs.getDate("timestamp");
            
        }catch(Exception e){e.printStackTrace();}
    }
    public PayLevelHistroyModel()
    {}

    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the statusId
     */
    public String getStatusId() {
        return statusId;
    }

    /**
     * @param statusId the statusId to set
     */
    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    /**
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the historyFileId
     */
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
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
