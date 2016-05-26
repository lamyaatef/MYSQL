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
public class CamPayment {

    private long recordId;
    private long paymentId;
    private long scmId;
    private long scmCommissionValue;
    private long accrualId;
    private long paymentTypeId;
    private String flag;
    private long deductionsValue;
    private String frozenFlag;
    private long CreditAdviceId;
    private Float total;

    /**
     * @return the recordId
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

    /**
     * @return the deductionsValue
     */
    /**
     * @return the frozenFlag
     */
    public String getFrozenFlag() {
        return frozenFlag;
    }

    /**
     * @param frozenFlag the frozenFlag to set
     */
    public void setFrozenFlag(String frozenFlag) {
        this.frozenFlag = frozenFlag;
    }

    /**
     * @return the recordId
     */
    public long getRecordId() {
        return recordId;
    }

    /**
     * @param recordId the recordId to set
     */
    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    /**
     * @return the paymentId
     */
    public long getPaymentId() {
        return paymentId;
    }

    /**
     * @param paymentId the paymentId to set
     */
    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * @return the scmCommissionValue
     */
    public long getScmCommissionValue() {
        return scmCommissionValue;
    }

    /**
     * @param scmCommissionValue the scmCommissionValue to set
     */
    public void setScmCommissionValue(long scmCommissionValue) {
        this.scmCommissionValue = scmCommissionValue;
    }

    /**
     * @return the accrualId
     */
    public long getAccrualId() {
        return accrualId;
    }

    /**
     * @param accrualId the accrualId to set
     */
    public void setAccrualId(long accrualId) {
        this.accrualId = accrualId;
    }

    /**
     * @return the paymentTypeId
     */
    public long getPaymentTypeId() {
        return paymentTypeId;
    }

    /**
     * @param paymentTypeId the paymentTypeId to set
     */
    public void setPaymentTypeId(long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public long getDeductionsValue() {
        return deductionsValue;
    }

    /**
     * @param deductionsValue the deductionsValue to set
     */
    public void setDeductionsValue(long deductionsValue) {
        this.deductionsValue = deductionsValue;
    }

    /**
     * @return the CreditAdviceId
     */
    public long getCreditAdviceId() {
        return CreditAdviceId;
    }

    /**
     * @param CreditAdviceId the CreditAdviceId to set
     */
    public void setCreditAdviceId(long CreditAdviceId) {
        this.CreditAdviceId = CreditAdviceId;
    }

    public long getScmId() {
        return scmId;
    }

    public void setScmId(long scmId) {
        this.scmId = scmId;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
