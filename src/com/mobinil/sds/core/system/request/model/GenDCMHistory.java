/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.request.model;

import com.mobinil.sds.core.system.Model;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Timestamp;


/**
 *
 * @author Ahmed Adel
 */
public class GenDCMHistory extends Model{

    private String DCMName;
    private String DCMEmail;
    private String DCM_PAYMENT_LEVEL_ID;
    private String DCM_ADDRESS;
    private Timestamp UPDATED_IN;

    /**
     * @return the DCMName
     */
    public String getDCMName() {
        return DCMName;
    }

    /**
     * @param DCMName the DCMName to set
     */
    public void setDCMName(String DCMName) {
        this.DCMName = DCMName;
    }

    /**
     * @return the DCMEmail
     */
    public String getDCMEmail() {
        return DCMEmail;
    }

    /**
     * @param DCMEmail the DCMEmail to set
     */
    public void setDCMEmail(String DCMEmail) {
        this.DCMEmail = DCMEmail;
    }

    /**
     * @return the DCM_PAYMENT_LEVEL_ID
     */
    public String getDCM_PAYMENT_LEVEL_ID() {
        return DCM_PAYMENT_LEVEL_ID;
    }

    /**
     * @param DCM_PAYMENT_LEVEL_ID the DCM_PAYMENT_LEVEL_ID to set
     */
    public void setDCM_PAYMENT_LEVEL_ID(String DCM_PAYMENT_LEVEL_ID) {
        this.DCM_PAYMENT_LEVEL_ID = DCM_PAYMENT_LEVEL_ID;
    }

    /**
     * @return the DCM_ADDRESS
     */
    public String getDCM_ADDRESS() {
        return DCM_ADDRESS;
    }

    /**
     * @param DCM_ADDRESS the DCM_ADDRESS to set
     */
    public void setDCM_ADDRESS(String DCM_ADDRESS) {
        this.DCM_ADDRESS = DCM_ADDRESS;
    }

    /**
     * @return the UPDATED_IN
     */
    public Timestamp getUPDATED_IN() {
        return UPDATED_IN;
    }

    /**
     * @param UPDATED_IN the UPDATED_IN to set
     */
    public void setUPDATED_IN(Timestamp UPDATED_IN) {
        this.UPDATED_IN = UPDATED_IN;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setDCMName(res.getString("DCM_NAME"));
            this.setDCMEmail(res.getString("DCM_EMAIL"));
            this.setDCM_ADDRESS(res.getString("DCM_ADDRESS"));
            this.setDCM_PAYMENT_LEVEL_ID(res.getString("DCM_PAYMENT_LEVEL_ID"));
            this.setUPDATED_IN(res.getTimestamp("UPDATED_IN"));
        } catch (SQLException ex) {
            Logger.getLogger(GenDCMHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

}
