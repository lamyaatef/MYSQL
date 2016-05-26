/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dl.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Gado
 */
@Entity
@Table(name="DL_LIST_STATUS")
public class DCMListStatus implements Serializable{
    @Id
    @Column(name="DL_LIST_STATUS_ID")   
    private long statusId;
    @Column(name="DL_LIST_STATUS_NAME")
    private String statusName;

    public DCMListStatus() {
    }

    public DCMListStatus(long statusId) {
        this.statusId = statusId;
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
