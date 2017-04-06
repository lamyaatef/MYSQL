/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.request.model;

import com.mobinil.sds.core.system.dcm.pos.model.POSDetailModel;

/**
 *
 * @author Salma
 */
public class PosModel
{

    private String entry_date;
    private int channelId;
    private int levelId;
    private String level;
    private String document;
    private String branchOf;
    private String channel;
    private String district;
    private String governrate;
    private String city;
    private String area;
    private String imgDist;
    private String region;
    private int governateId;
    private int areaId;
    private int cityId;
    private int districtId;
    private int imgDistrictId;
    private String demoLineNum;
    private int proposedDocId;
    private String docNumber;
    private String rate;
    private int rateID;
    
    private String rateDate;
    private String stkDialNumber;
    private String stkVerify;
    private String stkDeliveryDate;
    private String iqrarReceiveDate;
    private String iqrarDeliveryDate;
    private String stkAssignDate;
    private String stkActiveDate,docLocation;
    private int posId;
    private int posDetailId;
    private int paymentLevelId;
    private int taxId;
    private POSDetailModel posDetailModel;
    private String documentTypeName;
    //lamya
    private boolean isSignSet;
    private boolean reportToCalidus;
    private String paymentMethod;
    private String paymentLevel;
    //private String posLevel;
    private boolean isL1;
    private boolean isQC;
    private boolean isEX;
    private boolean isMobicash;
    private boolean isNomad;
    private String taxValue;
    private long mobicashNum;
    private int statusId;
    private String statusName;
    //lamya
    
    /**
     * @return the channelId
     */
    public int getChannelId() {
        return channelId;
    }

    /**
     * @param channelId the channelId to set
     */
    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    /**
     * @return the levelId
     */
    public int getLevelId() {
        return levelId;
    }

    /**
     * @param levelId the levelId to set
     */
    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    /**
     * @return the branchOf
     */
    public String getBranchOf() {
        return branchOf;
    }

    /**
     * @param branchOf the branchOf to set
     */
    public void setBranchOf(String branchOf) {
        this.branchOf = branchOf;
    }

    /**
     * @return the governateId
     */
    public int getGovernateId() {
        return governateId;
    }

    /**
     * @param governateId the governateId to set
     */
    public void setGovernateId(int governateId) {
        this.governateId = governateId;
    }

    /**
     * @return the areaId
     */
    public int getAreaId() {
        return areaId;
    }

    /**
     * @param areaId the areaId to set
     */
    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    /**
     * @return the demoLineNum
     */
    public String getDemoLineNum() {
        return demoLineNum;
    }

    /**
     * @param demoLineNum the demoLineNum to set
     */
    public void setDemoLineNum(String demoLineNum) {
        this.demoLineNum = demoLineNum;
    }

    /**
     * @return the proposedDocId
     */
    public int getProposedDocId() {
        return proposedDocId;
    }

    /**
     * @param proposedDocId the proposedDocId to set
     */
    public void setProposedDocId(int proposedDocId) {
        this.proposedDocId = proposedDocId;
    }

    /**
     * @return the docNumber
     */
    public String getDocNumber() {
        return docNumber;
    }

    /**
     * @param docNumber the docNumber to set
     */
    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    /**
     * @return the rateID
     */
    public int getRateID() {
        return rateID;
    }

    /**
     * @param rateID the rateID to set
     */
    public void setRateID(int rateID) {
        this.rateID = rateID;
    }

    /**
     * @return the rateDate
     */
    public String getRateDate() {
        return rateDate;
    }

    /**
     * @param rateDate the rateDate to set
     */
    public void setRateDate(String rateDate) {
        this.rateDate = rateDate;
    }

    /**
     * @return the stkDialNumber
     */
    public String getStkDialNumber() {
        return stkDialNumber;
    }

    /**
     * @param stkDialNumber the stkDialNumber to set
     */
    public void setStkDialNumber(String stkDialNumber) {
        this.stkDialNumber = stkDialNumber;
    }

    /**
     * @return the stkVerify
     */
    public String getStkVerify() {
        return stkVerify;
    }

    /**
     * @param stkVerify the stkVerify to set
     */
    public void setStkVerify(String stkVerify) {
        this.stkVerify = stkVerify;
    }

    /**
     * @return the stkDeliveryDate
     */
    public String getStkDeliveryDate() {
        return stkDeliveryDate;
    }

    /**
     * @param stkDeliveryDate the stkDeliveryDate to set
     */
    public void setStkDeliveryDate(String stkDeliveryDate) {
        this.stkDeliveryDate = stkDeliveryDate;
    }

    /**
     * @return the iqrarReceiveDate
     */
    public String getIqrarReceiveDate() {
        return iqrarReceiveDate;
    }

    /**
     * @param iqrarReceiveDate the iqrarReceiveDate to set
     */
    public void setIqrarReceiveDate(String iqrarReceiveDate) {
        this.iqrarReceiveDate = iqrarReceiveDate;
    }

    /**
     * @return the iqrarDeliveryDate
     */
    public String getIqrarDeliveryDate() {
        return iqrarDeliveryDate;
    }

    /**
     * @param iqrarDeliveryDate the iqrarDeliveryDate to set
     */
    public void setIqrarDeliveryDate(String iqrarDeliveryDate) {
        this.iqrarDeliveryDate = iqrarDeliveryDate;
    }

    /**
     * @return the stkAssignDate
     */
    public String getStkAssignDate() {
        return stkAssignDate;
    }

    /**
     * @param stkAssignDate the stkAssignDate to set
     */
    public void setStkAssignDate(String stkAssignDate) {
        this.stkAssignDate = stkAssignDate;
    }

    /**
     * @return the stkActiveDate
     */
    public String getStkActiveDate() {
        return stkActiveDate;
    }

    /**
     * @param stkActiveDate the stkActiveDate to set
     */
    public void setStkActiveDate(String stkActiveDate) {
        this.stkActiveDate = stkActiveDate;
    }

    /**
     * @return the cityId
     */
    public int getCityId() {
        return cityId;
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    /**
     * @return the districtId
     */
    public int getDistrictId() {
        return districtId;
    }

    /**
     * @param districtId the districtId to set
     */
    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    /**
     * @return the posDetailModel
     */
    public POSDetailModel getPosDetailModel() {
        return posDetailModel;
    }

    /**
     * @param posDetailModel the posDetailModel to set
     */
    public void setPosDetailModel(POSDetailModel posDetailModel) {
        this.posDetailModel = posDetailModel;
    }

    /**
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * @return the channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     * @param channel the channel to set
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * @return the district
     */
    public String getDistrict() {
        return district;
    }

    /**
     * @param district the district to set
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * @return the governrate
     */
    public String getGovernrate() {
        return governrate;
    }

    /**
     * @param governrate the governrate to set
     */
    public void setGovernrate(String governrate) {
        this.governrate = governrate;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the area
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * @return the rate
     */
    public String getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(String rate) {
        this.rate = rate;
    }

    /**
     * @return the document
     */
    public String getDocument() {
        return document;
    }

    /**
     * @param document the document to set
     */
    public void setDocument(String document) {
        this.document = document;
    }

    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * @return the posId
     */
    public int getPosId() {
        return posId;
    }

    /**
     * @param posId the posId to set
     */
    public void setPosId(int posId) {
        this.posId = posId;
    }

    /**
     * @return the posDetailId
     */
    public int getPosDetailId() {
        return posDetailId;
    }

    /**
     * @param posDetailId the posDetailId to set
     */
    public void setPosDetailId(int posDetailId) {
        this.posDetailId = posDetailId;
    }

    /**
     * @return the paymentLevelId
     */
    public int getPaymentLevelId() {
        return paymentLevelId;
    }

    /**
     * @param paymentLevelId the paymentLevelId to set
     */
    public void setPaymentLevelId(int paymentLevelId) {
        this.paymentLevelId = paymentLevelId;
    }

    /**
     * @return the docLocation
     */
    public String getDocLocation() {
        return docLocation;
    }

    /**
     * @param docLocation the docLocation to set
     */
    public void setDocLocation(String docLocation) {
        this.docLocation = docLocation;
    }

    /**
     * @return the documentTypeName
     */
    public String getDocumentTypeName() {
        return documentTypeName;
    }

    /**
     * @param documentTypeName the documentTypeName to set
     */
    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

    /**
     * @return the isSignSet
     */
    public boolean isIsSignSet() {
        return isSignSet;
    }

    /**
     * @return the reportToCalidus
     */
    public boolean isReportToCalidus() {
        return reportToCalidus;
    }

    /**
     * @return the paymentLevel
     */
   

    /**
     * @param isSignSet the isSignSet to set
     */
    public void setIsSignSet(boolean isSignSet) {
        this.isSignSet = isSignSet;
    }

    /**
     * @param reportToCalidus the reportToCalidus to set
     */
    public void setReportToCalidus(boolean reportToCalidus) {
        this.reportToCalidus = reportToCalidus;
    }

    /**
     * @return the paymentMethod
     */
   /* public String getPaymentLevel() {
        return getPaymentMethod();
    }

    
    public void setPaymentLevel(String paymentMethod) {
        this.setPaymentMethod(paymentMethod);
    }*/

    /**
     * @return the paymentMethod
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * @param paymentMethod the paymentMethod to set
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * @return the entry_date
     */
    public String getEntry_date() {
        return entry_date;
    }

    /**
     * @param entry_date the entry_date to set
     */
    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    /**
     * @return the isL1
     */
    public boolean isIsL1() {
        return isL1;
    }

    /**
     * @param isL1 the isL1 to set
     */
    public void setIsL1(boolean isL1) {
        this.isL1 = isL1;
    }

    /**
     * @return the isQC
     */
    public boolean isIsQC() {
        return isQC;
    }

    /**
     * @param isQC the isQC to set
     */
    public void setIsQC(boolean isQC) {
        this.isQC = isQC;
    }

    /**
     * @return the isEX
     */
    public boolean isIsEX() {
        return isEX;
    }

    /**
     * @param isEX the isEX to set
     */
    public void setIsEX(boolean isEX) {
        this.isEX = isEX;
    }

    /**
     * @return the isMobicash
     */
    public boolean isIsMobicash() {
        return isMobicash;
    }

    /**
     * @param isMobicash the isMobicash to set
     */
    public void setIsMobicash(boolean isMobicash) {
        this.isMobicash = isMobicash;
    }

    /**
     * @return the isNomad
     */
    public boolean isIsNomad() {
        return isNomad;
    }

    /**
     * @param isNomad the isNomad to set
     */
    public void setIsNomad(boolean isNomad) {
        this.isNomad = isNomad;
    }

    /**
     * @return the paymentLevel
     */
    public String getPaymentLevel() {
        return paymentLevel;
    }

    /**
     * @param paymentLevel the paymentLevel to set
     */
    public void setPaymentLevel(String paymentLevel) {
        this.paymentLevel = paymentLevel;
    }

    /**
     * @return the taxId
     */
    public int getTaxId() {
        return taxId;
    }

    /**
     * @param taxId the taxId to set
     */
    public void setTaxId(int taxId) {
        this.taxId = taxId;
    }

    /**
     * @return the taxValue
     */
    public String getTaxValue() {
        return taxValue;
    }

    /**
     * @param taxValue the taxValue to set
     */
    public void setTaxValue(String taxValue) {
        this.taxValue = taxValue;
    }

    /**
     * @return the mobicashNum
     */
    public long getMobicashNum() {
        return mobicashNum;
    }

    /**
     * @param mobicashNum the mobicashNum to set
     */
    public void setMobicashNum(long mobicashNum) {
        this.mobicashNum = mobicashNum;
    }

    /**
     * @return the statusId
     */
    public int getStatusId() {
        return statusId;
    }

    /**
     * @param statusId the statusId to set
     */
    public void setStatusId(int statusId) {
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

    /**
     * @return the imgDistrictId
     */
    public int getImgDistrictId() {
        return imgDistrictId;
    }

    /**
     * @param imgDistrictId the imgDistrictId to set
     */
    public void setImgDistrictId(int imgDistrictId) {
        this.imgDistrictId = imgDistrictId;
    }

    /**
     * @return the imgDist
     */
    public String getImgDist() {
        return imgDist;
    }

    /**
     * @param imgDist the imgDist to set
     */
    public void setImgDist(String imgDist) {
        this.imgDist = imgDist;
    }

    /**
     * @return the supervisorID
     */
  
    /**
     * @param teamleaderID the teamleaderID to set
     */
  

    /**
     * @return the posLevel
     */
   /* public String getPosLevel() {
        return posLevel;
    }*/

    /**
     * @param posLevel the posLevel to set
     */
   /* public void setPosLevel(String posLevel) {
        this.posLevel = posLevel;
    }*/

    /**
     * @param paymentMethod the paymentLevel to set
     */






}
