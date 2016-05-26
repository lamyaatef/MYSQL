/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.commission.postarget.model;

import com.mobinil.sds.core.system.Model;
import com.mobinil.sds.web.interfaces.commission.POSTargetInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mabdelaal
 */  
public class POSTargetFileModel extends Model{

     public POSTargetFileModel() {
    }
    private long fileId;
    private int year,period_type,month,week,quartar,userId;
    private String periodTypeName;
    private Date creationDate;

    public POSTargetFileModel(long fileId, int year, int period_type, int month, int week, int quartar, int userId, Date creationDate) {
        fillPOSTargetFileModel(fileId, year, period_type, month, week, quartar, userId, creationDate);
    }
    private void fillPOSTargetFileModel(long fileId, int year, int period_type, int month, int week, int quartar, int userId, Date creationDate) {
        this.fileId = fileId;
        this.year = year;
        this.period_type = period_type;
        this.month = month;
        this.week = week;
        this.quartar = quartar;
        this.userId = userId;
        this.periodTypeName = getPeriodTypeName(period_type);
        this.creationDate = creationDate;
    }    
    private String getPeriodTypeName(int periodType){
    if (periodType== POSTargetInterface.CONSTANT_MONTH_TYPE_INT)
    {
        this.week = 0;
        this.quartar = 0;
        return "Month";
    }
    else if(periodType== POSTargetInterface.CONSTANT_QUARTER_TYPE_INT)
    {
        this.week = 0;
        this.month = 0;
        return "Quarter";
    }
    else if(periodType== POSTargetInterface.CONSTANT_WEEK_TYPE_INT)
    {        
        this.quartar = 0;
        return "Week";
    }
    else return "";
    
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
     * @return the period_type
     */
    public int getPeriod_type() {
        return period_type;
    }

    /**
     * @param period_type the period_type to set
     */
    public void setPeriod_type(int period_type) {
        this.period_type = period_type;
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
     * @return the week
     */
    public int getWeek() {
        return week;
    }

    /**
     * @param week the week to set
     */
    public void setWeek(int week) {
        this.week = week;
    }

    /**
     * @return the quartar
     */
    public int getQuartar() {
        return quartar;
    }

    /**
     * @param quartar the quartar to set
     */
    public void setQuartar(int quartar) {
        this.quartar = quartar;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the periodTypeName
     */
    public String getPeriodTypeName() {
        return periodTypeName;
    }

    /**
     * @param periodTypeName the periodTypeName to set
     */
    public void setPeriodTypeName(String periodTypeName) {
        this.periodTypeName = periodTypeName;
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    /**
     * Get the value of fileId
     *
     * @return the value of fileId
     */
    public long getFileId() {
        return fileId;
    }

    /**
     * Set the value of fileId
     *
     * @param fileId new value of fileId
     */
    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "POSTargetFileModel{" + "fileId=" + fileId + ", year=" + year + ", period_type=" + period_type + ", month=" + month + ", week=" + week + ", quartar=" + quartar + ", userId=" + userId + ", periodTypeName=" + periodTypeName + ", creationDate=" + creationDate + '}';
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            //         FILE_ID      NUMBER,
            //  YEAR         NUMBER,
            //  PERIOD_TYPE  NUMBER,
            //  MONTH        NUMBER,
            //  WEEK         NUMBER,
            //  QUARTER      NUMBER,
            //  USER_ID      NUMBER,
            //  SYSTEM_DATE  DATE   
            
                    fillPOSTargetFileModel
                            (res.getLong("FILE_ID"), res.getInt("YEAR"), 
                            res.getInt("PERIOD_TYPE"), res.getInt("MONTH"), 
                            res.getInt("WEEK"), res.getInt("QUARTER"), 
                            res.getInt("USER_ID"), res.getDate("SYSTEM_DATE"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
