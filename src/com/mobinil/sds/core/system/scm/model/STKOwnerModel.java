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
public class STKOwnerModel extends Model {
        private long stkId;
        private long dcmId;
        private long dcmUserId;
        private long userId;
        private Date updatedIn;
        private int dcmVerifiedStatusId;
        private int iqrarRecievingStatusId;
        private int stkStatusId;
        private Date iqrarRecievedDate;
        private Date dcmVerificationDate;
        private String stkDial;
        private String stkStatusName;
        private String dcmVerifiedStatusName;
        private String reason;
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
     * @return the dcmId
     */
    public long getDcmId() {
        return dcmId;
    }

    /**
     * @param dcmId the dcmId to set
     */
    public void setDcmId(long dcmId) {
        this.dcmId = dcmId;
    }

    /**
     * @return the dcmUserId
     */
    public long getDcmUserId() {
        return dcmUserId;
    }

    /**
     * @param dcmUserId the dcmUserId to set
     */
    public void setDcmUserId(long dcmUserId) {
        this.dcmUserId = dcmUserId;
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

    /**
     * @return the iqrarRecievedDate
     */
    public Date getIqrarRecievedDate() {
        return iqrarRecievedDate;
    }

    /**
     * @param iqrarRecievedDate the iqrarRecievedDate to set
     */
    public void setIqrarRecievedDate(Date iqrarRecievedDate) {
        this.iqrarRecievedDate = iqrarRecievedDate;
    }

    /**
     * @return the dcmVerificationDate
     */
    public Date getDcmVerificationDate() {
        return dcmVerificationDate;
    }

    /**
     * @param dcmVerificationDate the dcmVerificationDate to set
     */
    public void setDcmVerificationDate(Date dcmVerificationDate) {
        this.dcmVerificationDate = dcmVerificationDate;
    }

    /**
     * @return the dcmVerifiedStatusId
     */
    public int getDcmVerifiedStatusId() {
        return dcmVerifiedStatusId;
    }

    /**
     * @param dcmVerifiedStatusId the dcmVerifiedStatusId to set
     */
    public void setDcmVerifiedStatusId(int dcmVerifiedStatusId) {
        this.dcmVerifiedStatusId = dcmVerifiedStatusId;
    }

    /**
     * @return the iqrarRecievingStatusId
     */
    public int getIqrarRecievingStatusId() {
        return iqrarRecievingStatusId;
    }

    /**
     * @param iqrarRecievingStatusId the iqrarRecievingStatusId to set
     */
    public void setIqrarRecievingStatusId(int iqrarRecievingStatusId) {
        this.iqrarRecievingStatusId = iqrarRecievingStatusId;
    }
       /**
     * @return the stkDial
     */
    public String getStkDial() {
        return stkDial;
    }

    /**
     * @param stkDial the stkDial to set
     */
    public void setStkDial(String stkDial) {
        this.stkDial = stkDial;
    }
      /**
     * @return the stkStatusName
     */
    public String getStkStatusName() {
        return stkStatusName;
    }

    /**
     * @param stkStatusName the stkStatusName to set
     */
    public void setStkStatusName(String stkStatusName) {
        this.stkStatusName = stkStatusName;
    }

    /**
     * @return the dcmVerifiedStatusName
     */
    public String getDcmVerifiedStatusName() {
        return dcmVerifiedStatusName;
    }

    /**
     * @param dcmVerifiedStatusName the dcmVerifiedStatusName to set
     */
    public void setDcmVerifiedStatusName(String dcmVerifiedStatusName) {
        this.dcmVerifiedStatusName = dcmVerifiedStatusName;
    }
      /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

        @Override

    public void fillInstance(ResultSet res) {
        try {
            if( GenericDAO.checkColumnName("DCM_ID", res))
            this.setDcmId(res.getLong("DCM_ID"));

            if( GenericDAO.checkColumnName("DCM_USER_ID", res))
            this.setDcmUserId(res.getLong("DCM_USER_ID"));

            if( GenericDAO.checkColumnName("DCM_VERIFICATION_DATE", res))
            this.setDcmVerificationDate(res.getDate("DCM_VERIFICATION_DATE"));

            if( GenericDAO.checkColumnName("DCM_VERIFIED_STATUS_ID", res))
            this.setDcmVerifiedStatusId(res.getInt("DCM_VERIFIED_STATUS_ID"));

            if( GenericDAO.checkColumnName("IQRAR_RECEVING_STATUS_ID", res))
            this.setIqrarRecievingStatusId(res.getInt("IQRAR_RECEVING_STATUS_ID"));

            if( GenericDAO.checkColumnName("IQRAR_RECIEVED_DATE", res))
            this.setIqrarRecievedDate(res.getDate("IQRAR_RECIEVED_DATE"));

            if( GenericDAO.checkColumnName("STK_ID", res))
            this.setStkId(res.getLong("STK_ID"));

            if( GenericDAO.checkColumnName("STK_STATUS_ID", res))
            this.setStkStatusId(res.getInt("STK_STATUS_ID"));

            if( GenericDAO.checkColumnName("UPDATED_IN", res))
            this.setUpdatedIn(res.getDate("UPDATED_IN"));

            if( GenericDAO.checkColumnName("USER_ID", res))
            this.setUserId(res.getLong("USER_ID"));

            if( GenericDAO.checkColumnName("STK_DIAL", res))
            this.setStkDial(res.getString("STK_DIAL"));

            if( GenericDAO.checkColumnName("STK_STATUS_NAME", res))
            this.setStkStatusName(res.getString("STK_STATUS_NAME"));

            if( GenericDAO.checkColumnName("DCM_VERIFIED_STATUS_NAME", res))
            this.setDcmVerifiedStatusName(res.getString("DCM_VERIFIED_STATUS_NAME"));

             if( GenericDAO.checkColumnName("REASON", res))
                 if(res.getString("REASON")==null)
                    this.setReason("");
                 else
                     this.setReason(res.getString("REASON"));

        } catch (SQLException ex) {
            Logger.getLogger(STKOwnerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  


  

 



}
