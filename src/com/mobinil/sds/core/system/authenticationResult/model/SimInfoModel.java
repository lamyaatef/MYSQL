/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.authenticationResult.model;

import com.mobinil.sds.core.system.Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author mabdelaal
 */
public class SimInfoModel extends Model {

    private String fileId, status, userId, labelId,rowCount,duration;
    private Timestamp timeStamp,startTime,endTime;

    public SimInfoModel( String status, String userId, String labelId) {        
        this.status = status;
        this.userId = userId;
        this.labelId = labelId;
        
    }

    public SimInfoModel() {
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            setFileId(res.getString("file_id"));
            setStatus(res.getString("Status"));
            setUserId(res.getString("user_id"));
            setLabelId(res.getString("label_id"));
            setRowCount(res.getString("row_count"));
            setTimeStamp(res.getTimestamp("time_stamp"));
            setStartTime(res.getTimestamp("start_time"));
            setEndTime(res.getTimestamp("end_time"));
            setDuration(checkColExsitsInRS("Duration", res)? res.getString("Duration"): "0");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private boolean checkColExsitsInRS(String colName,ResultSet res){
        try {
            res.getObject(colName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getFileId() {
        return fileId;
    }

    public String getStatus() {
        return status;
    }

    public String getUserId() {
        return userId;
    }

    public String getLabelId() {
        return labelId;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    private void setStatus(String status) {
        this.status = status;
    }

    private void setUserId(String userId) {
        this.userId = userId;
    }

    private void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    private void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    private void setRowCount(String rowCount) {
        this.rowCount = rowCount;
    }

    private void setDuration(String duration) {
        this.duration = duration;
    }

    private void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    private void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getRowCount() {
        return rowCount;
    }

    public String getDuration() {
        return duration;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }
}
