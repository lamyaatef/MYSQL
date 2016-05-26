/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;

public class CrGenDcm {

    private Long dcmId;
    private String dcmName;
    private String dcmEmail;
    private String dcmPhone;
    private Long dcmStatusId;
    private Long dcmLevelId;
    private String dcmCode;
    private Float dcmRank;
    private String dcmCityId;
    private String dcmDistrictId;
    private Long dcmPaymentLevelId;
    private String dcmAddress;
    private Long channelId;
    private String stkNumber;
    private Long mobinilSign;
    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getDcmAddress() {
        return dcmAddress;
    }

    public void setDcmAddress(String dcmAddress) {
        this.dcmAddress = dcmAddress;
    }

    public String getDcmCityId() {
        return dcmCityId;
    }

    public void setDcmCityId(String dcmCityId) {
        this.dcmCityId = dcmCityId;
    }

    public String getDcmCode() {
        return dcmCode;
    }

    public void setDcmCode(String dcmCode) {
        this.dcmCode = dcmCode;
    }

    public String getDcmDistrictId() {
        return dcmDistrictId;
    }

    public void setDcmDistrictId(String dcmDistrictId) {
        this.dcmDistrictId = dcmDistrictId;
    }

    public String getDcmEmail() {
        return dcmEmail;
    }

    public void setDcmEmail(String dcmEmail) {
        this.dcmEmail = dcmEmail;
    }

    public Long getDcmId() {
        return dcmId;
    }

    public void setDcmId(Long dcmId) {
        this.dcmId = dcmId;
    }

    public Long getDcmLevelId() {
        return dcmLevelId;
    }

    public void setDcmLevelId(Long dcmLevelId) {
        this.dcmLevelId = dcmLevelId;
    }

    public String getDcmName() {
        return dcmName;
    }

    public void setDcmName(String dcmName) {
        this.dcmName = dcmName;
    }

    public Long getDcmPaymentLevelId() {
        return dcmPaymentLevelId;
    }

    public void setDcmPaymentLevelId(Long dcmPaymentLevelId) {
        this.dcmPaymentLevelId = dcmPaymentLevelId;
    }

    public String getDcmPhone() {
        return dcmPhone;
    }

    public void setDcmPhone(String dcmPhone) {
        this.dcmPhone = dcmPhone;
    }

    public Float getDcmRank() {
        return dcmRank;
    }

    public void setDcmRank(Float dcmRank) {
        this.dcmRank = dcmRank;
    }

    public Long getDcmStatusId() {
        return dcmStatusId;
    }

    public void setDcmStatusId(Long dcmStatusId) {
        this.dcmStatusId = dcmStatusId;
    }

    public Long getMobinilSign() {
        return mobinilSign;
    }

    public void setMobinilSign(Long mobinilSign) {
        this.mobinilSign = mobinilSign;
    }

    public String getStkNumber() {
        return stkNumber;
    }

    public void setStkNumber(String stkNumber) {
        this.stkNumber = stkNumber;
    }
}
