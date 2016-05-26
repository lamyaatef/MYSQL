/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.te.dto;

import com.mobinil.sds.core.system.Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed Adel
 */
public class NomadTaskDTO extends Model{
    private int taskId;
    private Timestamp startTime;
    private Timestamp endTime;
    private String updatedRows;
    private String totalTime;
    private String taskName;

    /**
     * @return the taskId
     */
    public int getTaskId() {
        return taskId;
    }

    /**
     * @param taskId the taskId to set
     */
    public void setTaskId(int taskId) {
        this.taskId = taskId;
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
     * @return the updatedRows
     */
    public String getUpdatedRows() {
        return updatedRows;
    }

    /**
     * @param updatedRows the updatedRows to set
     */
    public void setUpdatedRows(String updatedRows) {
        this.updatedRows = updatedRows;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setTaskId(res.getInt("TASK_ID"));
            this.setStartTime(res.getTimestamp("START_TIME"));
            this.setEndTime(res.getTimestamp("END_TIME"));
            this.setUpdatedRows(res.getString("UPDATED_ROWS"));
            this.setTaskName(res.getString("TASK_NAME"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @return the totalTime
     */
    public String getTotalTime() {
        return totalTime;
    }

    /**
     * @param totalTime the totalTime to set
     */
    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    /**
     * @return the taskName
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * @param taskName the taskName to set
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
