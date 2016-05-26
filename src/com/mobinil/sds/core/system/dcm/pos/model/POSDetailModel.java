package com.mobinil.sds.core.system.dcm.pos.model;

import java.sql.*;
import java.util.Vector;

import com.mobinil.sds.core.system.dcm.manager.dao.*;
import com.mobinil.sds.core.system.dcm.manager.model.*;
import com.mobinil.sds.core.system.dcm.owner.dao.*;
import com.mobinil.sds.core.system.dcm.owner.model.*;
import com.mobinil.sds.core.system.dcm.pos.dao.*;
import com.mobinil.sds.core.system.gn.user.dao.*;
import com.mobinil.sds.core.utilities.Utility;

public class POSDetailModel 
{
    private String posName;
    private String posEmail;
    private int posCommercialNumber;
    private int posTaxID;
    private String POSCode;
    private String posAddress;
    private int posDistrictID;
    private String posDistrictName;
    private int posCityID;
    private String posCityName;
    private int posStatusID;
    private String posStatusName;
    private int posRegionID;
    private String posRegionName;
    private String posLegalFormName;
    private int posLegalFormID;
    private int posPlaceTypeID;
    private String posPlaceTypeName;
    private Date timeStamp;
    private Vector posPhones;
    private Vector posPartners;
    private String posOwnerName;
    private Vector posOwnerPhones;
    private String posOwnerBirthDate;
    private String posOwnerIDTypeName;
    private int posOwnerIDTypeID;
    private String posOwnerIDNumber;
    private String posManagerName;
    private Vector posManagerPhones;
    private String posManagerBirthDate;
    private String posManagerIDTypeName;
    private int posManagerIDTypeID;
    private String posManagerIDNumber;
    private int UserID;
    private String userName;
    private int posID,pageCount;
    private int poslastPOSID;
    private String surveyID;
    private Date updatedIn;
    private String posArabicName;
    private String posArabicAddress;
    
    private boolean isSignSet;
    private boolean reportToCalidus;
    private String paymentMethod;
    private String paymentLevel;
    private String posLevel;
    private boolean isL1;
    private boolean isQC;
    private boolean isEX;
    private boolean isMobicash;
    private boolean isNomad;
    private int taxId;
    private String taxValue;
    private long mobicashNum;
    private String supervisorName;
    private String teamleaderName;
    private String salesrepName;
    /**
     * @return the posName
     */
    public String getPosName() {
        return posName;
    }

    /**
     * @param posName the posName to set
     */
    public void setPosName(String posName) {
        this.posName = posName;
    }

    /**
     * @return the posEmail
     */
    public String getPosEmail() {
        return posEmail;
    }

    /**
     * @param posEmail the posEmail to set
     */
    public void setPosEmail(String posEmail) {
        this.posEmail = posEmail;
    }

    /**
     * @return the posCommercialNumber
     */
    public int getPosCommercialNumber() {
        return posCommercialNumber;
    }

    /**
     * @param posCommercialNumber the posCommercialNumber to set
     */
    public void setPosCommercialNumber(int posCommercialNumber) {
        this.posCommercialNumber = posCommercialNumber;
    }

    /**
     * @return the posTaxID
     */
    public int getPosTaxID() {
        return posTaxID;
    }

    /**
     * @param posTaxID the posTaxID to set
     */
    public void setPosTaxID(int posTaxID) {
        this.posTaxID = posTaxID;
    }

    /**
     * @return the POSCode
     */
    public String getPOSCode() {
        return POSCode;
    }

    /**
     * @param POSCode the POSCode to set
     */
    public void setPOSCode(String POSCode) {
        this.POSCode = POSCode;
    }

    /**
     * @return the posAddress
     */
    public String getPosAddress() {
        return posAddress;
    }

    /**
     * @param posAddress the posAddress to set
     */
    public void setPosAddress(String posAddress) {
        this.posAddress = posAddress;
    }

    /**
     * @return the posDistrictID
     */
    public int getPosDistrictID() {
        return posDistrictID;
    }

    /**
     * @param posDistrictID the posDistrictID to set
     */
    public void setPosDistrictID(int posDistrictID) {
        this.posDistrictID = posDistrictID;
    }

    /**
     * @return the posDistrictName
     */
    public String getPosDistrictName() {
        return posDistrictName;
    }

    /**
     * @param posDistrictName the posDistrictName to set
     */
    public void setPosDistrictName(String posDistrictName) {
        this.posDistrictName = posDistrictName;
    }

    /**
     * @return the posCityID
     */
    public int getPosCityID() {
        return posCityID;
    }

    /**
     * @param posCityID the posCityID to set
     */
    public void setPosCityID(int posCityID) {
        this.posCityID = posCityID;
    }

    /**
     * @return the posCityName
     */
    public String getPosCityName() {
        return posCityName;
    }

    /**
     * @param posCityName the posCityName to set
     */
    public void setPosCityName(String posCityName) {
        this.posCityName = posCityName;
    }

    /**
     * @return the posStatusID
     */
    public int getPosStatusID() {
        return posStatusID;
    }

    /**
     * @param posStatusID the posStatusID to set
     */
    public void setPosStatusID(int posStatusID) {
        this.posStatusID = posStatusID;
    }

    /**
     * @return the posStatusName
     */
    public String getPosStatusName() {
        return posStatusName;
    }

    /**
     * @param posStatusName the posStatusName to set
     */
    public void setPosStatusName(String posStatusName) {
        this.posStatusName = posStatusName;
    }

    /**
     * @return the posRegionID
     */
    public int getPosRegionID() {
        return posRegionID;
    }

    /**
     * @param posRegionID the posRegionID to set
     */
    public void setPosRegionID(int posRegionID) {
        this.posRegionID = posRegionID;
    }

    /**
     * @return the posRegionName
     */
    public String getPosRegionName() {
        return posRegionName;
    }

    /**
     * @param posRegionName the posRegionName to set
     */
    public void setPosRegionName(String posRegionName) {
        this.posRegionName = posRegionName;
    }

    /**
     * @return the posLegalFormName
     */
    public String getPosLegalFormName() {
        return posLegalFormName;
    }

    /**
     * @param posLegalFormName the posLegalFormName to set
     */
    public void setPosLegalFormName(String posLegalFormName) {
        this.posLegalFormName = posLegalFormName;
    }

    /**
     * @return the posLegalFormID
     */
    public int getPosLegalFormID() {
        return posLegalFormID;
    }

    /**
     * @param posLegalFormID the posLegalFormID to set
     */
    public void setPosLegalFormID(int posLegalFormID) {
        this.posLegalFormID = posLegalFormID;
    }

    /**
     * @return the posPlaceTypeID
     */
    public int getPosPlaceTypeID() {
        return posPlaceTypeID;
    }

    /**
     * @param posPlaceTypeID the posPlaceTypeID to set
     */
    public void setPosPlaceTypeID(int posPlaceTypeID) {
        this.posPlaceTypeID = posPlaceTypeID;
    }

    /**
     * @return the posPlaceTypeName
     */
    public String getPosPlaceTypeName() {
        return posPlaceTypeName;
    }

    /**
     * @param posPlaceTypeName the posPlaceTypeName to set
     */
    public void setPosPlaceTypeName(String posPlaceTypeName) {
        this.posPlaceTypeName = posPlaceTypeName;
    }

    /**
     * @return the timeStamp
     */
    public Date getTimeStamp() {
        return timeStamp;
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * @return the posPhones
     */
    public Vector getPosPhones() {
        return posPhones;
    }

    /**
     * @param posPhones the posPhones to set
     */
    public void setPosPhones(Vector posPhones) {
        this.posPhones = posPhones;
    }

    /**
     * @return the posPartners
     */
    public Vector getPosPartners() {
        return posPartners;
    }

    /**
     * @param posPartners the posPartners to set
     */
    public void setPosPartners(Vector posPartners) {
        this.posPartners = posPartners;
    }

    /**
     * @return the posOwnerName
     */
    public String getPosOwnerName() {
        return posOwnerName;
    }

    /**
     * @param posOwnerName the posOwnerName to set
     */
    public void setPosOwnerName(String posOwnerName) {
        this.posOwnerName = posOwnerName;
    }

    /**
     * @return the posOwnerPhones
     */
    public Vector getPosOwnerPhones() {
        return posOwnerPhones;
    }

    /**
     * @param posOwnerPhones the posOwnerPhones to set
     */
    public void setPosOwnerPhones(Vector posOwnerPhones) {
        this.posOwnerPhones = posOwnerPhones;
    }

    /**
     * @return the posOwnerBirthDate
     */
    public String getPosOwnerBirthDate() {
        return posOwnerBirthDate;
    }

    /**
     * @param posOwnerBirthDate the posOwnerBirthDate to set
     */
    public void setPosOwnerBirthDate(String posOwnerBirthDate) {
        this.posOwnerBirthDate = posOwnerBirthDate;
    }

    /**
     * @return the posOwnerIDTypeName
     */
    public String getPosOwnerIDTypeName() {
        return posOwnerIDTypeName;
    }

    /**
     * @param posOwnerIDTypeName the posOwnerIDTypeName to set
     */
    public void setPosOwnerIDTypeName(String posOwnerIDTypeName) {
        this.posOwnerIDTypeName = posOwnerIDTypeName;
    }

    /**
     * @return the posOwnerIDTypeID
     */
    public int getPosOwnerIDTypeID() {
        return posOwnerIDTypeID;
    }

    /**
     * @param posOwnerIDTypeID the posOwnerIDTypeID to set
     */
    public void setPosOwnerIDTypeID(int posOwnerIDTypeID) {
        this.posOwnerIDTypeID = posOwnerIDTypeID;
    }

    /**
     * @return the posOwnerIDNumber
     */
    public String getPosOwnerIDNumber() {
        return posOwnerIDNumber;
    }

    /**
     * @param posOwnerIDNumber the posOwnerIDNumber to set
     */
    public void setPosOwnerIDNumber(String posOwnerIDNumber) {
        this.posOwnerIDNumber = posOwnerIDNumber;
    }

    /**
     * @return the posManagerName
     */
    public String getPosManagerName() {
        return posManagerName;
    }

    /**
     * @param posManagerName the posManagerName to set
     */
    public void setPosManagerName(String posManagerName) {
        this.posManagerName = posManagerName;
    }

    /**
     * @return the posManagerPhones
     */
    public Vector getPosManagerPhones() {
        return posManagerPhones;
    }

    /**
     * @param posManagerPhones the posManagerPhones to set
     */
    public void setPosManagerPhones(Vector posManagerPhones) {
        this.posManagerPhones = posManagerPhones;
    }

    /**
     * @return the posManagerBirthDate
     */
    public String getPosManagerBirthDate() {
        return posManagerBirthDate;
    }

    /**
     * @param posManagerBirthDate the posManagerBirthDate to set
     */
    public void setPosManagerBirthDate(String posManagerBirthDate) {
        this.posManagerBirthDate = posManagerBirthDate;
    }

    /**
     * @return the posManagerIDTypeName
     */
    public String getPosManagerIDTypeName() {
        return posManagerIDTypeName;
    }

    /**
     * @param posManagerIDTypeName the posManagerIDTypeName to set
     */
    public void setPosManagerIDTypeName(String posManagerIDTypeName) {
        this.posManagerIDTypeName = posManagerIDTypeName;
    }

    /**
     * @return the posManagerIDTypeID
     */
    public int getPosManagerIDTypeID() {
        return posManagerIDTypeID;
    }

    /**
     * @param posManagerIDTypeID the posManagerIDTypeID to set
     */
    public void setPosManagerIDTypeID(int posManagerIDTypeID) {
        this.posManagerIDTypeID = posManagerIDTypeID;
    }

    /**
     * @return the posManagerIDNumber
     */
    public String getPosManagerIDNumber() {
        return posManagerIDNumber;
    }

    /**
     * @param posManagerIDNumber the posManagerIDNumber to set
     */
    public void setPosManagerIDNumber(String posManagerIDNumber) {
        this.posManagerIDNumber = posManagerIDNumber;
    }

    /**
     * @return the UserID
     */
    public int getUserID() {
        return UserID;
    }

    /**
     * @param UserID the UserID to set
     */
    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the posID
     */
    public int getPosID() {
        return posID;
    }

    /**
     * @param posID the posID to set
     */
    public void setPosID(int posID) {
        this.posID = posID;
    }

    /**
     * @return the poslastPOSID
     */
    public int getPoslastPOSID() {
        return poslastPOSID;
    }

    /**
     * @param poslastPOSID the poslastPOSID to set
     */
    public void setPoslastPOSID(int poslastPOSID) {
        this.poslastPOSID = poslastPOSID;
    }

    /**
     * @return the surveyDate
     */
    public String getSurveyID() {
        return surveyID;
    }

    /**
     * @param surveyDate the surveyDate to set
     */
    public void setSurveyID(String surveyID) {
        this.surveyID = surveyID;
    }

    /**
     * @return the updatedIn
     */
    public Date getUpdatedIn() {
        return updatedIn;
    }

    /**
     * @param updatedIn the updatedIn to set
     */
    public void setUpdatedIn(Date updatedIn) {
        this.updatedIn = updatedIn;
    }
    public POSDetailModel()
    {}

public POSDetailModel(Connection con , ResultSet rs)
  {
  try{
    posID = rs.getInt("POS_DETAIL_ID");
    posName = rs.getString("POS_NAME");
    posAddress = rs.getString("POS_ADDRESS");
    posEmail = rs.getString("POS_EMAIL");                
    posRegionName = rs.getString("REGION_NAME");
    posCommercialNumber = rs.getInt("POS_COMMERCIAL_NUMBER");
    posTaxID = rs.getInt("POS_TAX_ID");
    posLegalFormName = rs.getString("LEGAL_FORM_NAME");
    posPlaceTypeName = rs.getString("POS_PLACE_TYPE_NAME"); 
    POSCode = rs.getString("POS_CODE");
    posStatusID = rs.getInt("POS_STATUS_TYPE_ID");
    posStatusName = rs.getString("POS_STATUS_TYPE_NAME");
    timeStamp = rs.getDate("TIMESTAMP");
    UserID = rs.getInt("USER_ID");
    posArabicName=rs.getString("POS_ARABIC_NAME");
    posArabicAddress=rs.getString("POS_ARABIC_ADDRESS");
    ManagerModel managerModel = new ManagerModel();
    OwnerModel ownerModel = new OwnerModel();
    posPhones = PosDAO.getPOSPhones(con , posID);
    posPartners = PosDAO.getPOSPartners(con , posID);
    Utility.logger.debug("POSDETAILMODEL POSIDDDDDD: "+posID);
    managerModel = ManagerDAO.getManagerDetailsByPOSID(con , posID);
    ownerModel = OwnerDAO.getOwnerDetailsByPOSID(con , posID);
    posManagerBirthDate = managerModel.getManagerBirthDate().toString();
    posManagerIDNumber = managerModel.getManagerIDNumber();
    posManagerIDTypeID = managerModel.getManagerIDTypeID();
    posManagerIDTypeName = managerModel.getManagerIDTypeName();    
    posManagerName = managerModel.getManagerName();
    posManagerPhones = managerModel.getManagerPhoones();    

    posOwnerBirthDate = ownerModel.getOwnerBirthDate().toString();
    posOwnerIDNumber = ownerModel.getOwnerIDNumber();
    posOwnerIDTypeID = ownerModel.getOwnerIDTypeID();
    posOwnerIDTypeName = ownerModel.getOwnerIDTypeName();    
    posOwnerName = ownerModel.getOwnerName();
    posOwnerPhones = ownerModel.getOwnerPhoones(); 
    userName = UserAccountDAO.getEmail(Utility.getConnection() ,UserID );

  }catch(Exception ex)
  {
    ex.printStackTrace();
  }
}

    /**
     * @return the posArabicName
     */
    public String getPosArabicName() {
        return posArabicName;
    }

    /**
     * @param posArabicName the posArabicName to set
     */
    public void setPosArabicName(String posArabicName) {
        this.posArabicName = posArabicName;
    }

    /**
     * @return the posArabicAddress
     */
    public String getPosArabicAddress() {
        return posArabicAddress;
    }

    /**
     * @param posArabicAddress the posArabicAddress to set
     */
    public void setPosArabicAddress(String posArabicAddress) {
        this.posArabicAddress = posArabicAddress;
    }

    /**
     * @return the pageCount
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * @param pageCount the pageCount to set
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * @return the isSignSet
     */
    public boolean isIsSignSet() {
        return isSignSet;
    }

    /**
     * @param isSignSet the isSignSet to set
     */
    public void setIsSignSet(boolean isSignSet) {
        this.isSignSet = isSignSet;
    }

    /**
     * @return the reportToCalidus
     */
    public boolean isReportToCalidus() {
        return reportToCalidus;
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
     * @return the supervisorName
     */
    public String getSupervisorName() {
        return supervisorName;
    }

    /**
     * @param supervisorName the supervisorName to set
     */
    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    /**
     * @return the teamleaderName
     */
    public String getTeamleaderName() {
        return teamleaderName;
    }

    /**
     * @param teamleaderName the teamleaderName to set
     */
    public void setTeamleaderName(String teamleaderName) {
        this.teamleaderName = teamleaderName;
    }

    /**
     * @return the salesrepName
     */
    public String getSalesrepName() {
        return salesrepName;
    }

    /**
     * @param salesrepName the salesrepName to set
     */
    public void setSalesrepName(String salesrepName) {
        this.salesrepName = salesrepName;
    }

}
