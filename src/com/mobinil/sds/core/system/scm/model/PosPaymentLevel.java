/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.model;

import com.mobinil.sds.core.system.Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gado
 */
public class PosPaymentLevel extends Model{
    private int DCM_PAYMENT_LEVEL_ID;
    private String DCM_PAYMENT_LEVEL_NAME;

    /**
     * @return the DCM_PAYMENT_LEVEL_ID
     */
    public int getDCM_PAYMENT_LEVEL_ID() {
        return DCM_PAYMENT_LEVEL_ID;
    }

    /**
     * @param DCM_PAYMENT_LEVEL_ID the DCM_PAYMENT_LEVEL_ID to set
     */
    public void setDCM_PAYMENT_LEVEL_ID(int DCM_PAYMENT_LEVEL_ID) {
        this.DCM_PAYMENT_LEVEL_ID = DCM_PAYMENT_LEVEL_ID;
    }

    /**
     * @return the DCM_PAYMENT_LEVEL_NAME
     */
    public String getDCM_PAYMENT_LEVEL_NAME() {
        return DCM_PAYMENT_LEVEL_NAME;
    }

    /**
     * @param DCM_PAYMENT_LEVEL_NAME the DCM_PAYMENT_LEVEL_NAME to set
     */
    public void setDCM_PAYMENT_LEVEL_NAME(String DCM_PAYMENT_LEVEL_NAME) {
        this.DCM_PAYMENT_LEVEL_NAME = DCM_PAYMENT_LEVEL_NAME;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setDCM_PAYMENT_LEVEL_ID(res.getInt("DCM_PAYMENT_LEVEL_ID"));
            this.setDCM_PAYMENT_LEVEL_NAME(res.getString("DCM_PAYMENT_LEVEL_NAME"));
        } catch (SQLException ex) {
            Logger.getLogger(PosPaymentLevel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
