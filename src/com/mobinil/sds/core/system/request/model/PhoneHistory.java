/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.request.model;

import com.mobinil.sds.core.system.Model;
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
public class PhoneHistory extends Model{
private String phoneNumber;
private Timestamp updatedIn;

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
    public void setUpdatedIn(Timestamp updatedIn) {
        this.updatedIn = updatedIn;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setPhoneNumber(res.getString("PHONE_NUMBER"));
            this.setUpdatedIn(res.getTimestamp("UPDATED_IN"));
        } catch (SQLException ex) {
            Logger.getLogger(PhoneHistory.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
