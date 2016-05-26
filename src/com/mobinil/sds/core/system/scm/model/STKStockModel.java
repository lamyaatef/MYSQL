/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.model;

import com.mobinil.sds.core.system.Model;
import com.mobinil.sds.core.system.scm.dao.GenericDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AHMED SAFWAT
 */
public class STKStockModel extends Model{
private String  stkNumber;
private int stkStatusId;
private String serialNumber;
private long boxNumberId ;
private String ownerBy;
private Date updateIn;
private String lastStatus;
private long userId;
private long stkId;
private String stkVerification;
private String stkDelieveryDate;
private String iqrarRecieveDate;
private String iqrarDelieveryDate;
private Date stkAssignDate;
private String stkActiveDate;
private Date stkImportDate;

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
     * @return the serialNumber
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * @param serialNumber the serialNumber to set
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * @return the boxNumberId
     */
    public long getBoxNumberId() {
        return boxNumberId;
    }

    /**
     * @param boxNumberId the boxNumberId to set
     */
    public void setBoxNumberId(long boxNumberId) {
        this.boxNumberId = boxNumberId;
    }

    /**
     * @return the ownerBy
     */
    public String getOwnerBy() {
        return ownerBy;
    }

    /**
     * @param ownerBy the ownerBy to set
     */
    public void setOwnerBy(String ownerBy) {
        this.ownerBy = ownerBy;
    }

    /**
     * @return the updateIn
     */
    public Date getUpdateIn() {
        return updateIn;
    }

    /**
     * @param updateIn the updateIn to set
     */
    public void setUpdateIn(Date updateIn) {
        this.updateIn = updateIn;
    }

    /**
     * @return the lastStatus
     */
    public String getLastStatus() {
        return lastStatus;
    }

    /**
     * @param lastStatus the lastStatus to set
     */
    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }

    /**
     * @return the userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return the stkId
     */
    public long getStkId() {
        return stkId;
    }

    /**
     * @param stkId the stkId to set
     */
    public void setStkId(long stkId) {
        this.stkId = stkId;
    }

    /**
     * @return the stkVerification
     */
    public String getStkVerification() {
        return stkVerification;
    }

    /**
     * @param stkVerification the stkVerification to set
     */
    public void setStkVerification(String stkVerification) {
        this.stkVerification = stkVerification;
    }

    /**
     * @return the stkDelieveryDate
     */
    public String getStkDelieveryDate() {
        return stkDelieveryDate;
    }

    /**
     * @param stkDelieveryDate the stkDelieveryDate to set
     */
    public void setStkDelieveryDate(String stkDelieveryDate) {
        this.stkDelieveryDate = stkDelieveryDate;
    }

    /**
     * @return the iqrarRecieveDate
     */
    public String getIqrarRecieveDate() {
        return iqrarRecieveDate;
    }

    /**
     * @param iqrarRecieveDate the iqrarRecieveDate to set
     */
    public void setIqrarRecieveDate(String iqrarRecieveDate) {
        this.iqrarRecieveDate = iqrarRecieveDate;
    }

    /**
     * @return the iqrarDelieveryDate
     */
    public String getIqrarDelieveryDate() {
        return iqrarDelieveryDate;
    }

    /**
     * @param iqrarDelieveryDate the iqrarDelieveryDate to set
     */
    public void setIqrarDelieveryDate(String iqrarDelieveryDate) {
        this.iqrarDelieveryDate = iqrarDelieveryDate;
    }

    /**
     * @return the stkAssignDate
     */
    public Date getStkAssignDate() {
        return stkAssignDate;
    }

    /**
     * @param stkAssignDate the stkAssignDate to set
     */
    public void setStkAssignDate(Date stkAssignDate) {
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
     * @return the stkImportDate
     */
    public Date getStkImportDate() {
        return stkImportDate;
    }

    /**
     * @param stkImportDate the stkImportDate to set
     */
    public void setStkImportDate(Date stkImportDate) {
        this.stkImportDate = stkImportDate;
    }
    /**
     * @return the stkStatusId
     */
    public int getStkStatusId() {
        return stkStatusId;
    }

    /**
     * @param stkStatusId the stkStatusId to set
     */
    public void setStkStatusId(int stkStatusId) {
        this.stkStatusId = stkStatusId;
    }

        @Override
        
    public void fillInstance(ResultSet res) {
        try {
            if( GenericDAO.checkColumnName("BOX_NUMBER_ID", res))
            this.setBoxNumberId(res.getLong("BOX_NUMBER_ID"));

            if( GenericDAO.checkColumnName("IQRAR_DELIVERY_DATE", res))
            this.setIqrarDelieveryDate(res.getString("IQRAR_DELIVERY_DATE"));

            if( GenericDAO.checkColumnName("IQRAR_RECEIVE_DATE", res))
            this.setIqrarRecieveDate(res.getString("IQRAR_RECEIVE_DATE"));

            if( GenericDAO.checkColumnName("LAST_STATUS", res))
            this.setLastStatus(res.getString("LAST_STATUS"));
            
            if( GenericDAO.checkColumnName("OWNER_BY", res))
            this.setOwnerBy(res.getString("OWNER_BY"));

            if( GenericDAO.checkColumnName("SERIAL_NUMBER", res))
            this.setSerialNumber(res.getString("SERIAL_NUMBER"));

            if( GenericDAO.checkColumnName("STK_ACTIVE_DATE", res))
            this.setStkActiveDate(res.getString("STK_ACTIVE_DATE"));

            if( GenericDAO.checkColumnName("STK_ASSIGN_DATE", res))
            this.setStkAssignDate(res.getDate("STK_ASSIGN_DATE"));

            if( GenericDAO.checkColumnName("STK_DELIVERY_DATE", res))
            this.setStkDelieveryDate(res.getString("STK_DELIVERY_DATE"));

            if( GenericDAO.checkColumnName("STK_ID", res))
            this.setStkId(res.getLong("STK_ID"));

            if( GenericDAO.checkColumnName("STK_IMPORT_DATE", res))
            this.setStkImportDate(res.getDate("STK_IMPORT_DATE"));

            if( GenericDAO.checkColumnName("STK_NUMBER", res))
            this.setStkNumber(res.getString("STK_NUMBER"));

            if( GenericDAO.checkColumnName("STK_STATUS_ID", res))
            this.setStkStatusId(res.getInt("STK_STATUS_ID"));

            if( GenericDAO.checkColumnName("STK_VERIFICATION", res))
            this.setStkVerification("STK_VERIFICATION");

            if( GenericDAO.checkColumnName("UPDATE_IN", res))
            this.setUpdateIn(res.getDate("UPDATE_IN"));

            if( GenericDAO.checkColumnName("USER_ID", res))
            this.setUserId(res.getLong("USER_ID"));

        } catch (SQLException ex) {
            Logger.getLogger(STKStockModel.class.getName()).log(Level.SEVERE, null, ex);
        }




    }




}
