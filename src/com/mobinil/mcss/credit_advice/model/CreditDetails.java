/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.model;

import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author JOE
 */
public class CreditDetails {

    private long caId;
    private String creditSerial;
    private long creditPrint;
    private long creditStatusId;
    private Date creditPaymentDate;
    private long creditAmount;
    private Long scmId;
    private String scmName;
    private String dcmCode;
    private Date fileTraceDate;
    private String statusName;
    private List<CamPayment> camPayments;

    public String getCreditSerial() {
        return creditSerial;
    }

    public void setCreditSerial(String creditSerial) {
        this.creditSerial = creditSerial;
    }

    public long getCaId() {
        return caId;
    }

    public void setCaId(long caId) {
        this.caId = caId;
    }

    public long getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(long creditAmount) {
        this.creditAmount = creditAmount;
    }

    public long getCreditPrint() {
        return creditPrint;
    }

    public void setCreditPrint(long creditPrint) {
        this.creditPrint = creditPrint;
    }

    public long getCreditStatusId() {
        return creditStatusId;
    }

    public void setCreditStatusId(long creditStatusId) {
        this.creditStatusId = creditStatusId;
    }

    public Date getCreditPaymentDate() {
        return creditPaymentDate;
    }

    public void setCreditPaymentDate(Date creditPaymentDate) {
        this.creditPaymentDate = creditPaymentDate;
    }

    public List<CamPayment> getCamPayments() {
        return camPayments;
    }

    public void setCamPayments(List<CamPayment> camPayments) {
        this.camPayments = camPayments;
    }

    public String getDcmCode() {
        return dcmCode;
    }

    public void setDcmCode(String dcmCode) {
        this.dcmCode = dcmCode;
    }

    public Long getScmId() {
        return scmId;
    }

    public void setScmId(Long scmId) {
        this.scmId = scmId;
    }

    public String getScmName() {
        return scmName;
    }

    public void setScmName(String scmName) {
        this.scmName = scmName;
    }

    public Date getFileTraceDate() {
        return fileTraceDate;
    }

    public void setFileTraceDate(Date fileTraceDate) {
        this.fileTraceDate = fileTraceDate;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    
    
    
}
