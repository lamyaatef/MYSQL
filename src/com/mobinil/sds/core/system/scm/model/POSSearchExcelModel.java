/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.model;

import com.mobinil.sds.core.system.Model;
import com.mobinil.sds.core.system.dcm.region.dao.RegionDAO;
import com.mobinil.sds.core.system.scm.dao.RepSupDAO;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed Adel
 */
public class POSSearchExcelModel extends Model{
    private String enteryBy;
    private String posId;
    private String posCode;
    private String posEnglishName;
    private String posArabicName;
    private String regionId;
    private String governrateId;
    private String cityId;
    private String districtId;
    private String areaId;
    private String posSupervisor;
    private String posRebName;
    private String Englishaddress;
    private String ArabicAddress;
    private String posPhoneNumber;
    private String posDemoLine;
    private String ownerName;
    private String ownerBirthDate;
    private String ownerPhoneNmber;
    private String ownerIDNo;
    private String ownerIDTypeNumber;
    private String managerName;
    private String managerBirthDate;
    private String managerPhoneNumber;
    private String managerIDNumber;
    private String managerIDTypeNumber;
    private String posEmail;
    private String posDocumentType;
    private String posDocumentNumber;
    private String posStatus;
    private String stkNumber;
    private Date stkDeliveryDate;
    private Date iqrarDeliveryDate;
    private String cbillCase;
    private Date iqrarReceviedDate;
    private Timestamp entryDate;
    private String posPayment;
    private String poschannel;
    private String posLevel;
    private int posPaymentstatus;
    private String surveyId;
    private String supervisorId;
    private String teamleaderId;
    private String salesrepId;

    /**
     * @return the enteryBy
     */
    public String getEnteryBy() {
        return enteryBy;
    }

    /**
     * @param enteryBy the enteryBy to set
     */
    public void setEnteryBy(String enteryBy) {
        this.enteryBy = enteryBy;
    }

    /**
     * @return the posId
     */
    public String getPosId() {
        return posId;
    }

    /**
     * @param posId the posId to set
     */
    public void setPosId(String posId) {
        this.posId = posId;
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
     * @return the posEnglishName
     */
    public String getPosEnglishName() {
        return posEnglishName;
    }

    /**
     * @param posEnglishName the posEnglishName to set
     */
    public void setPosEnglishName(String posEnglishName) {
        this.posEnglishName = posEnglishName;
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
     * @return the regionId
     */
    public String getRegionId() {
        return regionId;
    }

    /**
     * @param regionId the regionId to set
     */
    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    /**
     * @return the governrateId
     */
    public String getGovernrateId() {
        return governrateId;
    }

    /**
     * @param governrateId the governrateId to set
     */
    public void setGovernrateId(String governrateId) {
        this.governrateId = governrateId;
    }

    /**
     * @return the cityId
     */
    public String getCityId() {
        return cityId;
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    /**
     * @return the districtId
     */
    public String getDistrictId() {
        return districtId;
    }

    /**
     * @param districtId the districtId to set
     */
    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    /**
     * @return the areaId
     */
    public String getAreaId() {
        return areaId;
    }

    /**
     * @param areaId the areaId to set
     */
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    /**
     * @return the posSupervisor
     */
    public String getPosSupervisor() {
        return posSupervisor;
    }

    /**
     * @param posSupervisor the posSupervisor to set
     */
    public void setPosSupervisor(String posSupervisor) {
        this.posSupervisor = posSupervisor;
    }

    /**
     * @return the posRebName
     */
    public String getPosRebName() {
        return posRebName;
    }

    /**
     * @param posRebName the posRebName to set
     */
    public void setPosRebName(String posRebName) {
        this.posRebName = posRebName;
    }

    /**
     * @return the Englishaddress
     */
    public String getEnglishaddress() {
        return Englishaddress;
    }

    /**
     * @param Englishaddress the Englishaddress to set
     */
    public void setEnglishaddress(String Englishaddress) {
        this.Englishaddress = Englishaddress;
    }

    /**
     * @return the ArabicAddress
     */
    public String getArabicAddress() {
        return ArabicAddress;
    }

    /**
     * @param ArabicAddress the ArabicAddress to set
     */
    public void setArabicAddress(String ArabicAddress) {
        this.ArabicAddress = ArabicAddress;
    }

    /**
     * @return the posPhoneNumber
     */
    public String getPosPhoneNumber() {
        return posPhoneNumber;
    }

    /**
     * @param posPhoneNumber the posPhoneNumber to set
     */
    public void setPosPhoneNumber(String posPhoneNumber) {
        this.posPhoneNumber = posPhoneNumber;
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
    public String getOwnerBirthDate() {
        return ownerBirthDate;
    }

    /**
     * @param ownerBirthDate the ownerBirthDate to set
     */
    public void setOwnerBirthDate(String ownerBirthDate) {
        this.ownerBirthDate = ownerBirthDate;
    }

    /**
     * @return the ownerPhoneNmber
     */
    public String getOwnerPhoneNmber() {
        return ownerPhoneNmber;
    }

    /**
     * @param ownerPhoneNmber the ownerPhoneNmber to set
     */
    public void setOwnerPhoneNmber(String ownerPhoneNmber) {
        this.ownerPhoneNmber = ownerPhoneNmber;
    }

    /**
     * @return the ownerIDNo
     */
    public String getOwnerIDNo() {
        return ownerIDNo;
    }

    /**
     * @param ownerIDNo the ownerIDNo to set
     */
    public void setOwnerIDNo(String ownerIDNo) {
        this.ownerIDNo = ownerIDNo;
    }

    /**
     * @return the ownerIDTypeNumber
     */
    public String getOwnerIDTypeNumber() {
        return ownerIDTypeNumber;
    }

    /**
     * @param ownerIDTypeNumber the ownerIDTypeNumber to set
     */
    public void setOwnerIDTypeNumber(String ownerIDTypeNumber) {
        this.ownerIDTypeNumber = ownerIDTypeNumber;
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
     * @return the managerBirthDate
     */
    public String getManagerBirthDate() {
        return managerBirthDate;
    }

    /**
     * @param managerBirthDate the managerBirthDate to set
     */
    public void setManagerBirthDate(String managerBirthDate) {
        this.managerBirthDate = managerBirthDate;
    }

    /**
     * @return the managerPhoneNumber
     */
    public String getManagerPhoneNumber() {
        return managerPhoneNumber;
    }

    /**
     * @param managerPhoneNumber the managerPhoneNumber to set
     */
    public void setManagerPhoneNumber(String managerPhoneNumber) {
        this.managerPhoneNumber = managerPhoneNumber;
    }

    /**
     * @return the managerIDNumber
     */
    public String getManagerIDNumber() {
        return managerIDNumber;
    }

    /**
     * @param managerIDNumber the managerIDNumber to set
     */
    public void setManagerIDNumber(String managerIDNumber) {
        this.managerIDNumber = managerIDNumber;
    }

    /**
     * @return the managerIDTypeNumber
     */
    public String getManagerIDTypeNumber() {
        return managerIDTypeNumber;
    }

    /**
     * @param managerIDTypeNumber the managerIDTypeNumber to set
     */
    public void setManagerIDTypeNumber(String managerIDTypeNumber) {
        this.managerIDTypeNumber = managerIDTypeNumber;
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
     * @return the posDocumentType
     */
    public String getPosDocumentType() {
        return posDocumentType;
    }

    /**
     * @param posDocumentType the posDocumentType to set
     */
    public void setPosDocumentType(String posDocumentType) {
        this.posDocumentType = posDocumentType;
    }

    /**
     * @return the posDocumentNumber
     */
    public String getPosDocumentNumber() {
        return posDocumentNumber;
    }

    /**
     * @param posDocumentNumber the posDocumentNumber to set
     */
    public void setPosDocumentNumber(String posDocumentNumber) {
        this.posDocumentNumber = posDocumentNumber;
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
     * @return the stkNumber
     */
    public String getStkNumber() {
        return stkNumber;
    }

    /**
     * @param stkNumber the stkNumber to set
     */
    public void setStkNumber(String stkNumber) {
        this.stkNumber = stkNumber;
    }

    /**
     * @return the stkDeliveryDate
     */
    public Date getStkDeliveryDate() {
        return stkDeliveryDate;
    }

    /**
     * @param stkDeliveryDate the stkDeliveryDate to set
     */
    public void setStkDeliveryDate(Date stkDeliveryDate) {
        this.stkDeliveryDate = stkDeliveryDate;
    }

    /**
     * @return the iqrarDeliveryDate
     */
    public Date getIqrarDeliveryDate() {
        return iqrarDeliveryDate;
    }

    /**
     * @param iqrarDeliveryDate the iqrarDeliveryDate to set
     */
    public void setIqrarDeliveryDate(Date iqrarDeliveryDate) {
        this.iqrarDeliveryDate = iqrarDeliveryDate;
    }

    /**
     * @return the cbillCase
     */
    public String getCbillCase() {
        return cbillCase;
    }

    /**
     * @param cbillCase the cbillCase to set
     */
    public void setCbillCase(String cbillCase) {
        this.cbillCase = cbillCase;
    }

    /**
     * @return the iqrarReceviedDate
     */
    public Date getIqrarReceviedDate() {
        return iqrarReceviedDate;
    }

    /**
     * @param iqrarReceviedDate the iqrarReceviedDate to set
     */
    public void setIqrarReceviedDate(Date iqrarReceviedDate) {
        this.iqrarReceviedDate = iqrarReceviedDate;
    }

    /**
     * @return the entryDate
     */
    public Timestamp getEntryDate() {
        return entryDate;
    }

    /**
     * @param entryDate the entryDate to set
     */
    public void setEntryDate(Timestamp entryDate) {
        this.entryDate = entryDate;
    }

    /**
     * @return the posPayment
     */
    public String getPosPayment() {
        return posPayment;
    }

    /**
     * @param posPayment the posPayment to set
     */
    public void setPosPayment(String posPayment) {
        this.posPayment = posPayment;
    }

    /**
     * @return the poschannel
     */
    public String getPoschannel() {
        return poschannel;
    }

    /**
     * @param poschannel the poschannel to set
     */
    public void setPoschannel(String poschannel) {
        this.poschannel = poschannel;
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

    @Override
    public void fillInstance(ResultSet res) {
        try {
//       this.setPosId(res.getInt("POS_ID"));
       this.setPosCode(res.getString("POS_CODE"));
       this.setEnteryBy(RepSupDAO.getUserName(res.getString("USER_ID")));
       this.setPosEnglishName(res.getString("POS_NAME"));
       this.setPosArabicName(res.getString("POS_ARABIC_NAME"));
       this.setRegionId(RegionDAO.getRegionName(res.getString("region_id")));
       this.setGovernrateId(RegionDAO.getRegionName(res.getString("POS_GOVERNRATE")));
       this.setCityId(RegionDAO.getRegionName(res.getString("POS_CITY_ID")));
       this.setDistrictId(RegionDAO.getRegionName(res.getString("POS_DISTRICT_ID")));
       this.setAreaId(RegionDAO.getRegionName(res.getString("pos_area_id")));
       this.setPosSupervisor(RepSupDAO.getUserNameByUserTable(res.getString("SUP_ID")));
       this.setPosRebName(RepSupDAO.getUserNameByUserTable(res.getString("REB ID")));
       this.setEnglishaddress(res.getString("POS_ADDRESS"));
       this.setArabicAddress(res.getString("POS_ARABIC_ADDRESS"));
       this.setPosPhoneNumber(res.getString("POS_PHONE_NUMBER"));
       this.setPosDemoLine(res.getString("POS_DEMO_LINE"));
       this.setOwnerName(res.getString("POS_OWNER_NAME"));
//       this.setOwnerBirthDate(res.getDate("POS_OWNER_BIRTHDATE"));
       this.setOwnerPhoneNmber(res.getString("POS_OWNER_PHONE_NUMBER"));
       this.setOwnerIDNo(res.getString("POS_OWNER_ID_NUMBER"));
       this.setOwnerIDTypeNumber(RepSupDAO.getTypeofPersonID(res.getString("POS_OWNER_ID_TYPE_ID")));
       this.setManagerName(res.getString("POS_MANAGER_NAME"));
//       this.setManagerBirthDate(res.getDate("POS_MANAGER_BIRTHDATE"));
       this.setManagerPhoneNumber(res.getString("POS_MANAGER_PHONE_NUMBER"));
       this.setManagerIDNumber(res.getString("POS_MANAGER_ID_NUMBER"));
       this.setManagerIDTypeNumber(RepSupDAO.getTypeofPersonID(res.getString("POS_MANAGER_ID_TYPE_ID")));
       this.setPosEmail(res.getString("POS_EMAIL"));
       this.setPosDocumentNumber(res.getString("POS_DOC_NUM"));
       this.setPosDocumentType(res.getString("PROPOSED_DOCUMENT_NAME"));
       this.setPosStatus(res.getString("DCM_STATUS_NAME"));
       this.setStkNumber(res.getString("STK_NUMBER"));
       this.setStkDeliveryDate(res.getDate("STK_DELIVERY_DATE"));
       this.setCbillCase(res.getString("Case"));
       this.setIqrarDeliveryDate(res.getDate("IQRAR_DELIVERY_DATE"));
       this.setIqrarReceviedDate(res.getDate("IQRAR_RECIEVED_DATE"));
       this.setEntryDate(res.getTimestamp("UPDATED_IN"));
//       this.setPosPayment(res.getInt("DCM_PAYMENT_LEVEL_ID"));
//       this.setPoschannel(res.getInt("POS_CHANNEL_ID"));
//       this.setPosLevel(res.getInt("DCM_LEVEL_ID"));
        } catch (SQLException ex) {
            Logger.getLogger(POSSearchExcelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }

    /**
     * @return the posPaymentstatus
     */
    public int getPosPaymentstatus() {
        return posPaymentstatus;
    }

    /**
     * @param posPaymentstatus the posPaymentstatus to set
     */
    public void setPosPaymentstatus(int posPaymentstatus) {
        this.posPaymentstatus = posPaymentstatus;
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
     * @return the supervisorId
     */
    public String getSupervisorId() {
        return supervisorId;
    }

    /**
     * @param supervisorId the supervisorId to set
     */
    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId;
    }

    /**
     * @return the teamleaderId
     */
    public String getTeamleaderId() {
        return teamleaderId;
    }

    /**
     * @param teamleaderId the teamleaderId to set
     */
    public void setTeamleaderId(String teamleaderId) {
        this.teamleaderId = teamleaderId;
    }

    /**
     * @return the salesrepId
     */
    public String getSalesrepId() {
        return salesrepId;
    }

    /**
     * @param salesrepId the salesrepId to set
     */
    public void setSalesrepId(String salesrepId) {
        this.salesrepId = salesrepId;
    }




}
