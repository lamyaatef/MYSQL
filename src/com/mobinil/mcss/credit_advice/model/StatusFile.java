/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;

/**
 *
 * @author SAND
 */
public class StatusFile {

    private long statusId;
    private String statusName;

    /**
     * @return the statusId
     */
    /**
     * @return the statusName
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * @param statusName the statusName to set
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * @return the statusId
     */
    public long getStatusId() {
        return statusId;
    }

    /**
     * @param statusId the statusId to set
     */
    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }
}
