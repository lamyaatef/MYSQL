/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.commission.model;

import com.mobinil.sds.core.system.Model;
import com.mobinil.sds.core.system.scm.dao.GenericDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed Adel
 */
public class RatedFileModel extends Model{

    private int fileId;
    private String dayFrom;
    private String monthFrom;
    private String yearFrom;
    private String dayTo;
    private String monthTo;
    private String yearTo;
    private String flag;
    private Timestamp startTime;
    private Timestamp endTime;
    private String total;
    /**
     * @return the fileId
     */
    public int getFileId() {
        return fileId;
    }

    /**
     * @param fileId the fileId to set
     */
    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    /**
     * @return the dayFrom
     */
    public String getDayFrom() {
        return dayFrom;
    }

    /**
     * @param dayFrom the dayFrom to set
     */
    public void setDayFrom(String dayFrom) {
        this.dayFrom = dayFrom;
    }

    /**
     * @return the monthFrom
     */
    public String getMonthFrom() {
        return monthFrom;
    }

    /**
     * @param monthFrom the monthFrom to set
     */
    public void setMonthFrom(String monthFrom) {
        this.monthFrom = monthFrom;
    }

    /**
     * @return the yearFrom
     */
    public String getYearFrom() {
        return yearFrom;
    }

    /**
     * @param yearFrom the yearFrom to set
     */
    public void setYearFrom(String yearFrom) {
        this.yearFrom = yearFrom;
    }

    /**
     * @return the dayTo
     */
    public String getDayTo() {
        return dayTo;
    }

    /**
     * @param dayTo the dayTo to set
     */
    public void setDayTo(String dayTo) {
        this.dayTo = dayTo;
    }

    /**
     * @return the monthTo
     */
    public String getMonthTo() {
        return monthTo;
    }

    /**
     * @param monthTo the monthTo to set
     */
    public void setMonthTo(String monthTo) {
        this.monthTo = monthTo;
    }

    /**
     * @return the yearTo
     */
    public String getYearTo() {
        return yearTo;
    }

    /**
     * @param yearTo the yearTo to set
     */
    public void setYearTo(String yearTo) {
        this.yearTo = yearTo;
    }
   /**
     * @return the flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
        this.setFileId(res.getInt("RATED_FILE_ID"));
        this.setDayFrom(res.getString("DAY_FROM"));
        this.setMonthFrom(res.getString("MONTH_FROM"));
        this.setYearFrom(res.getString("YEAR_FROM"));
        this.setDayTo(res.getString("DAY_TO"));
        this.setMonthTo(res.getString("MONTH_TO"));
        this.setYearTo(res.getString("YEAR_TO"));
        this.setFlag(res.getString("PROCESSING_FLAG"));
        this.setStartTime(res.getTimestamp("START_TIME"));
        this.setEndTime(res.getTimestamp("END_TIME"));
        if(this.endTime!=null)
        this.setTotal(GenericDAO.getTimeStampSubstraction(this.startTime,this.endTime));
        } catch (SQLException ex) {
            Logger.getLogger(RatedFileModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @return the startTime
     */
    public Timestamp getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the total
     */
    public String getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(String total) {
        this.total = total;
    }

 


}
