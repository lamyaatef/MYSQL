/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dp.dpManagement.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Gado
 */
@Entity
@Table(name="DP_LOG_APPROVE_DRIVINGPLAN")
public class DrivingPlanHistory implements Serializable {
    @Id
    @Column(name="APPROVE_ID")
    private long historyId;
    @ManyToOne
    @JoinColumn(name = "DRIVING_PLAN_ID")
    private DrivingPlan dp;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private GenUser userID;
    @Column(name = "APPROVE_COMMENT")
    private String comment;
    @Column(name = "DPSTATUS")
    private String status;
    @Column(name = "Modify_Date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date modifyDate;
    
    
    public long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(long historyId) {
        this.historyId = historyId;
    }

    /**
     * @return the dp
     */
    public DrivingPlan getDp() {
        return dp;
    }

    /**
     * @param dp the dp to set
     */
    public void setDp(DrivingPlan dp) {
        this.dp = dp;
    }

    /**
     * @return the userID
     */
    public GenUser getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(GenUser userID) {
        this.userID = userID;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the modifyDate
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * @param modifyDate the modifyDate to set
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
}
