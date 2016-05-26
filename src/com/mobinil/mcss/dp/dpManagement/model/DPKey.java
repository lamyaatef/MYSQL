/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.dp.dpManagement.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Gado
 */
@Embeddable
public class DPKey implements Serializable {
    
    
    @ManyToOne
    @JoinColumn(name = "CHANNEL_ID")
    private GenChannel channel;
    @ManyToOne
    @JoinColumn(name = "DP_DCM_LEVEL_ID")
    private GenDcmLevel dcmLevel;
    @ManyToOne
    @JoinColumn(name = "DP_DCM_PAYMENT_LEVEL_ID")
    private GenDcmPaymentLevel dcmPaymentLevel;
    @ManyToOne
    @JoinColumn(name= "DP_MONTH")
    private Month DPMonth;
    @Column(name= "DP_YEAR")
    private String DPYear;
    
    /**
     * @return the channel
     */
    public GenChannel getChannel() {
        return channel;
    }

    /**
     * @param channel the channel to set
     */
    public void setChannel(GenChannel channel) {
        this.channel = channel;
    }

    /**
     * @return the dcmLevel
     */
    public GenDcmLevel getDcmLevel() {
        return dcmLevel;
    }

    /**
     * @param dcmLevel the dcmLevel to set
     */
    public void setDcmLevel(GenDcmLevel dcmLevel) {
        this.dcmLevel = dcmLevel;
    }

    /**
     * @return the dcmPaymentLevel
     */
    public GenDcmPaymentLevel getDcmPaymentLevel() {
        return dcmPaymentLevel;
    }

    /**
     * @param dcmPaymentLevel the dcmPaymentLevel to set
     */
    public void setDcmPaymentLevel(GenDcmPaymentLevel dcmPaymentLevel) {
        this.dcmPaymentLevel = dcmPaymentLevel;
    }

    /**
     * @return the DPMonth
     */
    public Month getDPMonth() {
        return DPMonth;
    }

    /**
     * @param DPMonth the DPMonth to set
     */
    public void setDPMonth(Month DPMonth) {
        this.DPMonth = DPMonth;
    }

    /**
     * @return the DPYear
     */
    public String getDPYear() {
        return DPYear;
    }

    /**
     * @param DPYear the DPYear to set
     */
    public void setDPYear(String DPYear) {
        this.DPYear = DPYear;
    }
}
