/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.regionReport.model;

import java.sql.ResultSet;

/**
 *
 * @author sand
 */
public class RegionPOSReportModel {
    private static String RECEIVED = "Received";
    private static String VERIFIED = "Verified";

    /**
     * @return the RECEIVED
     */
    public static String getRECEIVED() {
        return RECEIVED;
    }

    /**
     * @param aRECEIVED the RECEIVED to set
     */
    public static void setRECEIVED(String aRECEIVED) {
        RECEIVED = aRECEIVED;
    }

    /**
     * @return the VERIFIED
     */
    public static String getVERIFIED() {
        return VERIFIED;
    }

    /**
     * @param aVERIFIED the VERIFIED to set
     */
    public static void setVERIFIED(String aVERIFIED) {
        VERIFIED = aVERIFIED;
    }
    private String posCode="";
    private String posENName="";
    private String posARName="";
    private String ownerName="";
    private String IdNumber="";
    private String IdType="";
    private String region="";
    private String city="";
    private String disctrict="";
    private String governorate="";
    private String districtCodeId="";
    private String areaCode="";
    private String area="";
    private String channelCode="";
    private String address="";
    private String arAddress="";
    private String documentNumber="";
    private String entryDate="";
    private String posStatus="";
    private String posLevel="";
    private String regionSupervisor="";
    private String regionTeamleader="";
    private String salesRep="";
    private String stkDialNumber="";
    private String stkStatus="";
    private String stkActivationDate="";
    private String iqrarReceivedDate="";
    private String paymentStatus="";
    private String paymentLevelName="";
    private String iqrarReceived = "";//boolean
    private String verifyOk=""; //boolean
    //private String stkVerificationId="";
    private String posOwnerPhoneNumber="";
    private String L1="";
    private String Ex="";
    private String Sign="";
    private String QC="";
    private String documentLocation="";
    private String surveyId="";
    private String branch="";
    private String documents="";
    
    private String imageDistrict="";
    private String imageDistrictCode="";
    private String mbbRep="";
    private String posOwnerPhoneNumber2="";
    private String commercialGov="";

    public RegionPOSReportModel()
{
	
}
public RegionPOSReportModel(ResultSet res/*,String supervisorName, String teamleaderName*/)
{
    try
	      {
	    	   
                       
                  posARName = res.getString("pos_arabic_name");
                  posCode = res.getString("pos_code");
                    posENName = res.getString("pos_english_name");
		    
                    
                   
                    ownerName= res.getString("pos_owner_name");
                    IdNumber= res.getString("pos_owner_id_number");
                    IdType= res.getString("id_type_name");
                    region= res.getString("region_name");//region_id , supervisor_region_id
                    city= res.getString("city_name");//POS_CITY_ID , supervisor_city_id
                    disctrict= res.getString("district_name");//POS_DISTRICT_ID, salesrep_district_id
                    governorate= res.getString("govern_name");//POS_GOVERNRATE , supervisor_govern_id
                    districtCodeId= res.getString("district_code");
                    areaCode= res.getString("area_code");//salesrep_area_id,res.getString("area_code");
                    area= res.getString("area_name");//salesrep_area_name,POS_AREA_ID
                    channelCode= res.getString("channel_code");
                    address= res.getString("pos_address");
                    arAddress= res.getString("pos_arabic_address");
                    documentNumber= res.getString("pos_doc_num");
                    entryDate= res.getString("assign_date");
                    posStatus= res.getString("pos_status");
                    posLevel= res.getString("pos_level_code");
                    regionTeamleader= res.getString("teamleader_name");//teamleaderName;
                    salesRep= res.getString("salesrep_name");//sales_rep_name
                    regionSupervisor = res.getString("supervisor_name");
                    stkDialNumber= res.getString("StkDialNo");
                    stkStatus= res.getString("stk_status");
                    stkActivationDate= res.getString("stk_activation_date");
                    
                    iqrarReceivedDate= res.getString("iqrar_received_date");
                    //stkVerificationId = res.getString("STKVRFCAT_VANTIFCASEIDNO");
              
                    iqrarReceived = res.getString("is_iqrar_received");
            
                    verifyOk = res.getString("is_verified");
                    paymentStatus= res.getString("payment_status");//payment_status
                    paymentLevelName= res.getString("payment_level");
                    posOwnerPhoneNumber= res.getString("pos_owner_phone_number");
                  
                    L1= res.getString("L1")!=null && res.getString("L1").compareTo("1")==0 ? "Y":"N";
                    Ex= res.getString("EX")!=null && res.getString("EX").compareTo("1")==0 ? "Y":"N";
                    Sign= res.getString("Sign")!=null && res.getString("Sign").compareTo("1")==0 ? "Y":"N";
                    QC= res.getString("QC")!=null && res.getString("QC").compareTo("1")==0 ? "Y":"N";
                    documentLocation= res.getString("doc_location");
                    surveyId= res.getString("survey_id");
                    branch= "";//res.getString("BRANCH_ID");
                    documents = res.getString("posdocuments");
                    
                    
                   
                }
	      catch(Exception e)
	      {
	        e.printStackTrace();
	      } 
	
}        
            

    /**
     * @return the posCode
     */
    public String getPosCode() {
        return posCode;
    }

    /**
     * @param posCode the posCode to set
     */
    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    /**
     * @return the posENName
     */
    public String getPosENName() {
        return posENName;
    }

    /**
     * @param posENName the posENName to set
     */
    public void setPosENName(String posENName) {
        this.posENName = posENName;
    }

    /**
     * @return the posARName
     */
    public String getPosARName() {
        return posARName;
    }

    /**
     * @param posARName the posARName to set
     */
    public void setPosARName(String posARName) {
        this.posARName = posARName;
    }

    /**
     * @return the ownerName
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * @param ownerName the ownerName to set
     */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * @return the IdNumber
     */
    public String getIdNumber() {
        return IdNumber;
    }

    /**
     * @param IdNumber the IdNumber to set
     */
    public void setIdNumber(String IdNumber) {
        this.IdNumber = IdNumber;
    }

    /**
     * @return the IdType
     */
    public String getIdType() {
        return IdType;
    }

    /**
     * @param IdType the IdType to set
     */
    public void setIdType(String IdType) {
        this.IdType = IdType;
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
     * @return the disctrict
     */
    public String getDisctrict() {
        return disctrict;
    }

    /**
     * @param disctrict the disctrict to set
     */
    public void setDisctrict(String disctrict) {
        this.disctrict = disctrict;
    }

    /**
     * @return the governorate
     */
    public String getGovernorate() {
        return governorate;
    }

    /**
     * @param governorate the governorate to set
     */
    public void setGovernorate(String governorate) {
        this.governorate = governorate;
    }

    /**
     * @return the districtCodeId
     */
    public String getDistrictCodeId() {
        return districtCodeId;
    }

    /**
     * @param districtCodeId the districtCodeId to set
     */
    public void setDistrictCodeId(String districtCodeId) {
        this.districtCodeId = districtCodeId;
    }

    /**
     * @return the areaCode
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * @param areaCode the areaCode to set
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
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
     * @return the channelCode
     */
    public String getChannelCode() {
        return channelCode;
    }

    /**
     * @param channelCode the channelCode to set
     */
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the arAddress
     */
    public String getArAddress() {
        return arAddress;
    }

    /**
     * @param arAddress the arAddress to set
     */
    public void setArAddress(String arAddress) {
        this.arAddress = arAddress;
    }

    /**
     * @return the docNumber
     */
    public String getDocumentNumber() {
        return documentNumber;
    }

    /**
     * @param docNumber the docNumber to set
     */
    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    /**
     * @return the entryDate
     */
    public String getEntryDate() {
        return entryDate;
    }

    /**
     * @param entryDate the entryDate to set
     */
    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    /**
     * @return the posStatus
     */
    public String getPosStatus() {
        return posStatus;
    }

    /**
     * @param posStatus the posStatus to set
     */
    public void setPosStatus(String posStatus) {
        this.posStatus = posStatus;
    }

    /**
     * @return the ownerPhone
     */
 
    /**
     * @return the posLevel
     */
    public String getPosLevel() {
        return posLevel;
    }

    /**
     * @param posLevel the posLevel to set
     */
    public void setPosLevel(String posLevel) {
        this.posLevel = posLevel;
    }

    /**
     * @return the regionSupervisor
     */
    public String getRegionSupervisor() {
        return regionSupervisor;
    }

    /**
     * @param regionSupervisor the regionSupervisor to set
     */
    public void setRegionSupervisor(String regionSupervisor) {
        this.regionSupervisor = regionSupervisor;
    }

    /**
     * @return the regionTeamleader
     */
    public String getRegionTeamleader() {
        return regionTeamleader;
    }

    /**
     * @param regionTeamleader the regionTeamleader to set
     */
    public void setRegionTeamleader(String regionTeamleader) {
        this.regionTeamleader = regionTeamleader;
    }

    /**
     * @return the salesRep
     */
    public String getSalesRep() {
        return salesRep;
    }

    /**
     * @param salesRep the salesRep to set
     */
    public void setSalesRep(String salesRep) {
        this.salesRep = salesRep;
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
     * @return the stkStatus
     */
    public String getStkStatus() {
        return stkStatus;
    }

    /**
     * @param stkStatus the stkStatus to set
     */
    public void setStkStatus(String stkStatus) {
        this.stkStatus = stkStatus;
    }

    /**
     * @return the stkActivationDate
     */
    public String getStkActivationDate() {
        return stkActivationDate;
    }

    /**
     * @param stkActivationDate the stkActivationDate to set
     */
    public void setStkActivationDate(String stkActivationDate) {
        this.stkActivationDate = stkActivationDate;
    }

    /**
     * @return the iqrarReceivedDate
     */
    public String getIqrarReceivedDate() {
        return iqrarReceivedDate;
    }

    /**
     * @param iqrarReceivedDate the iqrarReceivedDate to set
     */
    public void setIqrarReceivedDate(String iqrarReceivedDate) {
        this.iqrarReceivedDate = iqrarReceivedDate;
    }

    /**
     * @return the paymentStatus
     */
    public String getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * @param paymentStatus the paymentStatus to set
     */
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * @return the paymentLevelName
     */
    public String getPaymentLevelName() {
        return paymentLevelName;
    }

    /**
     * @param paymentLevelName the paymentLevelName to set
     */
    public void setPaymentLevelName(String paymentLevelName) {
        this.paymentLevelName = paymentLevelName;
    }

    /**
     * @return the iqrarReceived
     */
    public String getIqrarReceived() {
        return iqrarReceived;
    }

    /**
     * @param iqrarReceived the iqrarReceived to set
     */
    public void setIqrarReceived(String iqrarReceived) {
        this.iqrarReceived = iqrarReceived;
    }

    /**
     * @return the verifyOk
     */
    public String getVerifyOk() {
        return verifyOk;
    }

    /**
     * @param verifyOk the verifyOk to set
     */
    public void setVerifyOk(String verifyOk) {
        this.verifyOk = verifyOk;
    }

    /**
     * @return the posOwnerPhoneNumber
     */
    public String getPosOwnerPhoneNumber() {
        return posOwnerPhoneNumber;
    }

    /**
     * @param posOwnerPhoneNumber the posOwnerPhoneNumber to set
     */
    public void setPosOwnerPhoneNumber(String posOwnerPhoneNumber) {
        this.posOwnerPhoneNumber = posOwnerPhoneNumber;
    }

    /**
     * @return the L1
     */
    public String getL1() {
        return L1;
    }

    /**
     * @param L1 the L1 to set
     */
    public void setL1(String L1) {
        this.L1 = L1;
    }

    /**
     * @return the Ex
     */
    public String getEx() {
        return Ex;
    }

    /**
     * @param Ex the Ex to set
     */
    public void setEx(String Ex) {
        this.Ex = Ex;
    }

    /**
     * @return the Sign
     */
    public String getSign() {
        return Sign;
    }

    /**
     * @param Sign the Sign to set
     */
    public void setSign(String Sign) {
        this.Sign = Sign;
    }

    /**
     * @return the QC
     */
    public String getQC() {
        return QC;
    }

    /**
     * @param QC the QC to set
     */
    public void setQC(String QC) {
        this.QC = QC;
    }

    /**
     * @return the documentLocation
     */
    public String getDocumentLocation() {
        return documentLocation;
    }

    /**
     * @param documentLocation the documentLocation to set
     */
    public void setDocumentLocation(String documentLocation) {
        this.documentLocation = documentLocation;
    }

    /**
     * @return the surveyId
     */
    public String getSurveyId() {
        return surveyId;
    }

    /**
     * @param surveyId the surveyId to set
     */
    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    /**
     * @return the branch
     */
    public String getBranch() {
        return branch;
    }

    /**
     * @param branch the branch to set
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }

    /**
     * @return the documents
     */
    public String getDocuments() {
        return documents;
    }

    /**
     * @param documents the documents to set
     */
    public void setDocuments(String documents) {
        this.documents = documents;
    }

    /**
     * @return the imageDistrict
     */
    public String getImageDistrict() {
        return imageDistrict;
    }

    /**
     * @param imageDistrict the imageDistrict to set
     */
    public void setImageDistrict(String imageDistrict) {
        this.imageDistrict = imageDistrict;
    }

    /**
     * @return the imageDistrictCode
     */
    public String getImageDistrictCode() {
        return imageDistrictCode;
    }

    /**
     * @param imageDistrictCode the imageDistrictCode to set
     */
    public void setImageDistrictCode(String imageDistrictCode) {
        this.imageDistrictCode = imageDistrictCode;
    }

    /**
     * @return the mbbRep
     */
    public String getMbbRep() {
        return mbbRep;
    }

    /**
     * @param mbbRep the mbbRep to set
     */
    public void setMbbRep(String mbbRep) {
        this.mbbRep = mbbRep;
    }

    /**
     * @return the commercialGov
     */
    public String getCommercialGov() {
        return commercialGov;
    }

    /**
     * @param commercialGov the commercialGov to set
     */
    public void setCommercialGov(String commercialGov) {
        this.commercialGov = commercialGov;
    }

    /**
     * @return the posOwnerPhoneNumber2
     */
    public String getPosOwnerPhoneNumber2() {
        return posOwnerPhoneNumber2;
    }

    /**
     * @param posOwnerPhoneNumber2 the posOwnerPhoneNumber2 to set
     */
    public void setPosOwnerPhoneNumber2(String posOwnerPhoneNumber2) {
        this.posOwnerPhoneNumber2 = posOwnerPhoneNumber2;
    }
    
}
