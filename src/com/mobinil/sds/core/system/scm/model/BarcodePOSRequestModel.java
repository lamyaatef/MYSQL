/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.model;

import com.mobinil.sds.core.system.Model;
import com.mobinil.sds.core.system.scm.dao.GenericDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed Adel
 */
public class BarcodePOSRequestModel extends Model {
private int posId;
private int quantity;
private int dcmUserId;
private int userId;
private Timestamp Date;
private int requestId;
private String personName,noOfDownload;
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
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the dcmUserId
     */
    public int getDcmUserId() {
        return dcmUserId;
    }

    /**
     * @param dcmUserId the dcmUserId to set
     */
    public void setDcmUserId(int dcmUserId) {
        this.dcmUserId = dcmUserId;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public void fillInstance(ResultSet res) {
  
         try{
             if( GenericDAO.checkColumnName("REQUEST_ID", res))
            this.setPosId(res.getInt("REQUEST_ID"));
             if( GenericDAO.checkColumnName("DCM_USER_ID", res))
            this.setDcmUserId(res.getInt("DCM_USER_ID"));
             if( GenericDAO.checkColumnName("QUANTITY", res))
            this.setQuantity(res.getInt("QUANTITY"));
             if( GenericDAO.checkColumnName("USER_ID", res))
            this.setUserId(res.getInt("USER_ID"));
            if( GenericDAO.checkColumnName("REQUEST_ID", res))
                this.setRequestId(res.getInt("REQUEST_ID"));
                 if( GenericDAO.checkColumnName("UPDATE_IN", res))
                this.setDate(res.getTimestamp("UPDATE_IN"));
                 if( GenericDAO.checkColumnName("USER_FULL_NAME", res))
                this.setPersonName(res.getString("USER_FULL_NAME"));
                 if( GenericDAO.checkColumnName("NO_OF_DOWN", res))
                this.setNoOfDownload(res.getString("NO_OF_DOWN"));

        } catch (SQLException ex) {
            Logger.getLogger(BarcodePOSRequestModel.class.getName()).log(Level.SEVERE, null, ex);
        }
 }

    /**
     * @return the Date
     */
    public Timestamp getDate() {
        return Date;
    }

    /**
     * @param Date the Date to set
     */
    public void setDate(Timestamp Date) {
        this.Date = Date;
    }

    /**
     * @return the requestId
     */
    public int getRequestId() {
        return requestId;
    }

    /**
     * @param requestId the requestId to set
     */
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    /**
     * @return the personName
     */
    public String getPersonName() {
        return personName;
    }

    /**
     * @param personName the personName to set
     */
    public void setPersonName(String personName) {
        this.personName = personName;
    }

    /**
     * @return the noOfDownload
     */
    public String getNoOfDownload() {
        return noOfDownload;
    }

    /**
     * @param noOfDownload the noOfDownload to set
     */
    public void setNoOfDownload(String noOfDownload) {
        this.noOfDownload = noOfDownload;
    }
}
