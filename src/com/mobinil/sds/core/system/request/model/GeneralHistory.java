/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.request.model;

import com.mobinil.sds.core.system.Model;
import com.mobinil.sds.core.system.scm.dao.GenericDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.sql.Timestamp;

/**
 *
 * @author sand
 */
public class GeneralHistory extends Model {
    private Integer posId;
    private String posName;
    private String posEmail;
    private String posAddress;
    private Integer regionId;
    private Integer posGovernrate;
    private Integer posArea;
    private Integer posDistrict;
    private Integer posCity;
    private String posDemoLine;
    private Timestamp updatedIn;
    private String userId;
    private String managerName;
    private Date mangerBirthDate;
    private Integer managerType;
    private String managerIdNumber;
    private String ownerName;
    private Date ownerBirthDate;
    private Integer ownerIdType;
    private String ownerIdNumber;
    private Integer paymentLevelId;
    private String managerPhone;
    private String ownerPhone;
    private String posPhone,docLocation,POS_ARABIC_ADDRESS;

    /**
     * @return the posId
     */
    public Integer getPosId() {
        return posId;
    }

    /**
     * @param posId the posId to set
     */
    public void setPosId(int posId) {
        this.posId = posId;
    }

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
     * @return the regionId
     */
    public Integer getRegionId() {
        return regionId;
    }

    /**
     * @param regionId the regionId to set
     */
    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    /**
     * @return the posGovernrate
     */
    public Integer getPosGovernrate() {
        return posGovernrate;
    }

    /**
     * @param posGovernrate the posGovernrate to set
     */
    public void setPosGovernrate(int posGovernrate) {
        this.posGovernrate = posGovernrate;
    }

    /**
     * @return the posArea
     */
    public Integer getPosArea() {
        return posArea;
    }

    /**
     * @param posArea the posArea to set
     */
    public void setPosArea(int posArea) {
        this.posArea = posArea;
    }

    /**
     * @return the posDistrict
     */
    public Integer getPosDistrict() {
        return posDistrict;
    }

    /**
     * @param posDistrict the posDistrict to set
     */
    public void setPosDistrict(int posDistrict) {
        this.posDistrict = posDistrict;
    }

    /**
     * @return the posCity
     */
    public Integer getPosCity() {
        return posCity;
    }

    /**
     * @param posCity the posCity to set
     */
    public void setPosCity(int posCity) {
        this.posCity = posCity;
    }

    /**
     * @return the posDemoLine
     */
    public String getPosDemoLine() {
        return posDemoLine;
    }

    /**
     * @param posDemoLine the posDemoLine to set
     */
    public void setPosDemoLine(String posDemoLine) {
        this.posDemoLine = posDemoLine;
    }

    /**
     * @return the updatedIn
     */
    public Timestamp getUpdatedIn() {
        return updatedIn;
    }

    /**
     * @param updatedIn the updatedIn to set
     */
    public void setUpdatedIn(Timestamp updatedIn) {
        this.updatedIn = updatedIn;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the managerName
     */
    public String getManagerName() {
        return managerName;
    }

    /**
     * @param managerName the managerName to set
     */
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    /**
     * @return the mangerBirthDate
     */
    public Date getMangerBirthDate() {
        return mangerBirthDate;
    }

    /**
     * @param mangerBirthDate the mangerBirthDate to set
     */
    public void setMangerBirthDate(Date mangerBirthDate) {
        this.mangerBirthDate = mangerBirthDate;
    }

    /**
     * @return the managerType
     */
    public Integer getManagerType() {
        return managerType;
    }

    /**
     * @param managerType the managerType to set
     */
    public void setManagerType(int managerType) {
        this.managerType = managerType;
    }

    /**
     * @return the managerIdNumber
     */
    public String getManagerIdNumber() {
        return managerIdNumber;
    }

    /**
     * @param managerIdNumber the managerIdNumber to set
     */
    public void setManagerIdNumber(String managerIdNumber) {
        this.managerIdNumber = managerIdNumber;
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
     * @return the ownerBirthDate
     */
    public Date getOwnerBirthDate() {
        return ownerBirthDate;
    }

    /**
     * @param ownerBirthDate the ownerBirthDate to set
     */
    public void setOwnerBirthDate(Date ownerBirthDate) {
        this.ownerBirthDate = ownerBirthDate;
    }

    /**
     * @return the ownerIdType
     */
    public Integer getOwnerIdType() {
        return ownerIdType;
    }

    /**
     * @param ownerIdType the ownerIdType to set
     */
    public void setOwnerIdType(int ownerIdType) {
        this.ownerIdType = ownerIdType;
    }

    /**
     * @return the ownerIdNumber
     */
    public String getOwnerIdNumber() {
        return ownerIdNumber;
    }

    /**
     * @param ownerIdNumber the ownerIdNumber to set
     */
    public void setOwnerIdNumber(String ownerIdNumber) {
        this.ownerIdNumber = ownerIdNumber;
    }
/**
     * @return the paymentLevelId
     */
    public Integer getPaymentLevelId() {
        return paymentLevelId;
    }

    /**
     * @param paymentLevelId the paymentLevelId to set
     */
    public void setPaymentLevelId(int paymentLevelId) {
        this.paymentLevelId = paymentLevelId;
    }
    @Override
    public void fillInstance(ResultSet res) {
        try {
    this.setManagerIdNumber(res.getString("POS_MANAGER_ID_NUMBER"));
    this.setManagerName(res.getString("POS_MANAGER_NAME"));
    this.setManagerType(res.getInt("POS_MANAGER_ID_TYPE_ID"));
    this.setMangerBirthDate(res.getDate("POS_MANAGER_BIRTHDATE"));
    this.setOwnerBirthDate(res.getDate("POS_OWNER_BIRTHDATE"));
    this.setOwnerIdNumber(res.getString("POS_OWNER_ID_NUMBER"));
    this.setOwnerIdType(res.getInt("POS_OWNER_ID_TYPE_ID"));
    this.setOwnerName(res.getString("POS_OWNER_NAME"));
    this.setPosAddress(res.getString("POS_ADDRESS"));
    this.setPosArea(res.getInt("POS_AREA_ID"));
    this.setPosCity(res.getInt("POS_CITY_ID"));
    this.setPosDemoLine(res.getString("POS_DEMO_LINE"));
    this.setPosDistrict(res.getInt("POS_DISTRICT_ID"));
    this.setPosEmail(res.getString("POS_EMAIL"));
    this.setPosGovernrate(res.getInt("POS_GOVERNRATE"));
    this.setPosId(res.getInt("POS_ID"));
    this.setPosName(res.getString("POS_NAME"));
    this.setRegionId(res.getInt("REGION_ID"));
    this.setUpdatedIn(res.getTimestamp("UPDATED_IN"));
    this.setUserId(res.getString("USER_ID"));
    this.setPaymentLevelId(res.getInt("DCM_PAYMENT_LEVEL_ID"));
    this.setManagerPhone(res.getString("POS_MANAGER_PHONE_NUMBER"));
    this.setOwnerPhone(res.getString("POS_OWNER_PHONE_NUMBER"));
    this.setPosPhone(res.getString("POS_PHONE_NUMBER"));
    if( GenericDAO.checkColumnName("DOC_LOCATION", res))
        this.setDocLocation(res.getString("DOC_LOCATION"));
    if( GenericDAO.checkColumnName("POS_ARABIC_ADDRESS", res))
        this.setPOS_ARABIC_ADDRESS(res.getString("POS_ARABIC_ADDRESS"));
    
        } catch (SQLException ex) {
            Logger.getLogger(GeneralHistory.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    /**
     * @return the managerPhone
     */
    public String getManagerPhone() {
        return managerPhone;
    }

    /**
     * @param managerPhone the managerPhone to set
     */
    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    /**
     * @return the ownerPhone
     */
    public String getOwnerPhone() {
        return ownerPhone;
    }

    /**
     * @param ownerPhone the ownerPhone to set
     */
    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    /**
     * @return the posPhone
     */
    public String getPosPhone() {
        return posPhone;
    }

    /**
     * @param posPhone the posPhone to set
     */
    public void setPosPhone(String posPhone) {
        this.posPhone = posPhone;
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
     * @return the POS_ARABIC_ADDRESS
     */
    public String getPOS_ARABIC_ADDRESS() {
        return POS_ARABIC_ADDRESS;
    }

    /**
     * @param POS_ARABIC_ADDRESS the POS_ARABIC_ADDRESS to set
     */
    public void setPOS_ARABIC_ADDRESS(String POS_ARABIC_ADDRESS) {
        this.POS_ARABIC_ADDRESS = POS_ARABIC_ADDRESS;
    }

    





}
