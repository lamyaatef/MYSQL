/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dl.model;

import com.mobinil.mcss.dp.dpManagement.model.Month;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.*;


/**
 *
 * @author Gado
 */
@Entity
@Table(name="DL_LIST_HISTORY")
public class DCMListHistory implements Serializable {
    @Id
    @Column(name="DL_LIST_HISTORY_ID") 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SEQ_DL_HISTORY_LIST_ID")
    @SequenceGenerator(name = "GEN_SEQ_DL_HISTORY_LIST_ID", sequenceName = "SEQ_DL_HISTORY_LIST_ID", allocationSize = 1)
    private long historyId;
    @Column(name="CREATION_DATE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date creationDate;
    @Column(name="AFFECTED_ROWS")
    private Integer affectedRows;
    @ManyToOne
    @JoinColumn(name="DL_LIST_ID")
    private DCMList dcmList;
    @Column(name="DL_YEAR")
    private String year;
    @ManyToOne
    @JoinColumn (name="Month_ID")
    private Month month;
    @Column (name="DL_LIST_HISTORY_STATUS")
    private long historyStatusId;

    public DCMListHistory() {
    }

    public DCMListHistory(long historyId, long historyStatusId) {
        this.historyId = historyId;
        this.historyStatusId = historyStatusId;
    }

    /**
     * @return the historyId
     */
    public long getHistoryId() {
        return historyId;
    }

    /**
     * @param historyId the historyId to set
     */
    public void setHistoryId(long historyId) {
        this.historyId = historyId;
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
     * @return the affectedRows
     */
    public Integer getAffectedRows() {
        return affectedRows;
    }

    /**
     * @param affectedRows the affectedRows to set
     */
    public void setAffectedRows(Integer affectedRows) {
        this.affectedRows = affectedRows;
    }

    /**
     * @return the dcnList
     */
    public DCMList getDcmList() {
        return dcmList;
    }

    /**
     * @param dcnList the dcnList to set
     */
    public void setDcmList(DCMList dcmList) {
        this.dcmList = dcmList;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * @return the month
     */
    public Month getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(Month month) {
        this.month = month;
    }

    public long getHistoryStatusId() {
        return historyStatusId;
    }

    public void setHistoryStatusId(long historyStatusId) {
        this.historyStatusId = historyStatusId;
    }
    
    
    
}
