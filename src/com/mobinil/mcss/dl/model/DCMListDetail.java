/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dl.model;

import com.mobinil.mcss.spring.util.GExcel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Gado
 */
@Entity
@Table(name="DL_LIST_DETAIL")
public class DCMListDetail implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SEQ_DL_LIST_DETAIL_ID")
    @SequenceGenerator(name = "GEN_SEQ_DL_LIST_DETAIL_ID", sequenceName = "SEQ_DL_LIST_DETAIL_ID", allocationSize = 1)
    @Column(name="DL_LIST_DETAIL_ID")  
    
    private Long detailId;        
    @Column(name="DL_DCM_ID")    
    private Long dcmID;
    @Column(name="DL_LIST_HISTORY_ID")
    private Long historyID;
    @Column(name="DL_DCM_CODE")
    @GExcel(excelColumn=0)
    private String dcmCode;

    public DCMListDetail(Long dcmID, String dcmCode) {
        this.dcmID = dcmID;
        this.dcmCode = dcmCode;
    }

    public DCMListDetail() {
    }
    

    /**
     * @return the detailId
     */
    public Long getDetailId() {
        return detailId;
    }

    /**
     * @param detailId the detailId to set
     */
    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    /**
     * @return the dcmID
     */
    public Long getDcmID() {
        return dcmID;
    }

    /**
     * @param dcmID the dcmID to set
     */
    public void setDcmID(Long dcmID) {
        this.dcmID = dcmID;
    }

    /**
     * @return the dcmCode
     */
    public String getDcmCode() {
        return dcmCode;
    }

    /**
     * @param dcmCode the dcmCode to set
     */
    public void setDcmCode(String dcmCode) {
        this.dcmCode = dcmCode;
    }

    /**
     * @return the historyID
     */
    public Long getHistoryID() {
        return historyID;
    }

    /**
     * @param historyID the historyID to set
     */
    public void setHistoryID(Long historyID) {
        this.historyID = historyID;
    }
    
}
