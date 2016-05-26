/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.legacy_models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mabdelaal
 */
@Entity
@Table(name = "GEN_DCM")
@NamedQueries({
    @NamedQuery(name = "GenDcm.findAll", query = "SELECT g FROM GenDcm g")})
public class GenDcm implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DCM_ID")
    private BigDecimal dcmId;
    @Size(max = 150)
    @Column(name = "DCM_NAME")
    private String dcmName;
    @Size(max = 250)
    @Column(name = "DCM_EMAIL")
    private String dcmEmail;
    @Size(max = 30)
    @Column(name = "DCM_PHONE")
    private String dcmPhone;
    @Column(name = "DCM_STATUS_ID")
    private BigInteger dcmStatusId;
    @Column(name = "DCM_LEVEL_ID")
    private BigInteger dcmLevelId;
    @Size(max = 50)
    @Column(name = "DCM_CODE")
    private String dcmCode;
    @Column(name = "DCM_RANK")
    private Double dcmRank;
    @Size(max = 10)
    @Column(name = "DCM_CITY_ID")
    private String dcmCityId;
    @Size(max = 10)
    @Column(name = "DCM_DISTRICT_ID")
    private String dcmDistrictId;
    @Column(name = "DCM_PAYMENT_LEVEL_ID")
    private BigInteger dcmPaymentLevelId;
    @Size(max = 512)
    @Column(name = "DCM_ADDRESS")
    private String dcmAddress;
    @Column(name = "CHANNEL_ID")
    private BigInteger channelId;
    @Size(max = 50)
    @Column(name = "STK_NUMBER")
    private String stkNumber;
    @Column(name = "MOBINIL_SIGN")
    private BigInteger mobinilSign;

    public GenDcm() {
    }

    public GenDcm(BigDecimal dcmId) {
        this.dcmId = dcmId;
    }

    public BigDecimal getDcmId() {
        return dcmId;
    }

    public void setDcmId(BigDecimal dcmId) {
        this.dcmId = dcmId;
    }

    public String getDcmName() {
        return dcmName;
    }

    public void setDcmName(String dcmName) {
        this.dcmName = dcmName;
    }

    public String getDcmEmail() {
        return dcmEmail;
    }

    public void setDcmEmail(String dcmEmail) {
        this.dcmEmail = dcmEmail;
    }

    public String getDcmPhone() {
        return dcmPhone;
    }

    public void setDcmPhone(String dcmPhone) {
        this.dcmPhone = dcmPhone;
    }

    public BigInteger getDcmStatusId() {
        return dcmStatusId;
    }

    public void setDcmStatusId(BigInteger dcmStatusId) {
        this.dcmStatusId = dcmStatusId;
    }

    public BigInteger getDcmLevelId() {
        return dcmLevelId;
    }

    public void setDcmLevelId(BigInteger dcmLevelId) {
        this.dcmLevelId = dcmLevelId;
    }

    public String getDcmCode() {
        return dcmCode;
    }

    public void setDcmCode(String dcmCode) {
        this.dcmCode = dcmCode;
    }

    public Double getDcmRank() {
        return dcmRank;
    }

    public void setDcmRank(Double dcmRank) {
        this.dcmRank = dcmRank;
    }

    public String getDcmCityId() {
        return dcmCityId;
    }

    public void setDcmCityId(String dcmCityId) {
        this.dcmCityId = dcmCityId;
    }

    public String getDcmDistrictId() {
        return dcmDistrictId;
    }

    public void setDcmDistrictId(String dcmDistrictId) {
        this.dcmDistrictId = dcmDistrictId;
    }

    public BigInteger getDcmPaymentLevelId() {
        return dcmPaymentLevelId;
    }

    public void setDcmPaymentLevelId(BigInteger dcmPaymentLevelId) {
        this.dcmPaymentLevelId = dcmPaymentLevelId;
    }

    public String getDcmAddress() {
        return dcmAddress;
    }

    public void setDcmAddress(String dcmAddress) {
        this.dcmAddress = dcmAddress;
    }

    public BigInteger getChannelId() {
        return channelId;
    }

    public void setChannelId(BigInteger channelId) {
        this.channelId = channelId;
    }

    public String getStkNumber() {
        return stkNumber;
    }

    public void setStkNumber(String stkNumber) {
        this.stkNumber = stkNumber;
    }

    public BigInteger getMobinilSign() {
        return mobinilSign;
    }

    public void setMobinilSign(BigInteger mobinilSign) {
        this.mobinilSign = mobinilSign;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dcmId != null ? dcmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenDcm)) {
            return false;
        }
        GenDcm other = (GenDcm) object;
        if ((this.dcmId == null && other.dcmId != null) || (this.dcmId != null && !this.dcmId.equals(other.dcmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mobinil.mcss.legacy_models.GenDcm[ dcmId=" + dcmId + " ]";
    }
    
}
