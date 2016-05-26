/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dp.dpManagement.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Ahmed Adel
 */
@Entity
@Table(name = "DP_STATUS_TYPE")
public class DPStatus implements Serializable {
    @Id
    @Column (name = "DP_STATUS_TYPE_ID")
    private long statusId;
    @Column(name = "DP_STATUS_TYPE_NAME")
    private String statusName;

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

    
}
