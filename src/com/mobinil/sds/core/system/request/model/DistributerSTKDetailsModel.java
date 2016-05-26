/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.request.model;

import com.mobinil.sds.core.system.Model;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed Adel
 */
public class DistributerSTKDetailsModel extends Model{

private String DCMName;
private String DCMCode;
private String DCMOwner;
private String DCMCity;
private String DCMAddress;
private String STK_DIAL;
private Date STK_ASSIGN_DATE;

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
     * @return the DCMCity
     */
    public String getDCMCity() {
        return DCMCity;
    }

    /**
     * @param DCMCity the DCMCity to set
     */
    public void setDCMCity(String DCMCity) {
        this.DCMCity = DCMCity;
    }

    /**
     * @return the DCMAddress
     */
    public String getDCMAddress() {
        return DCMAddress;
    }

    /**
     * @param DCMAddress the DCMAddress to set
     */
    public void setDCMAddress(String DCMAddress) {
        this.DCMAddress = DCMAddress;
    }

    /**
     * @return the STK_DIAL
     */
    public String getSTK_DIAL() {
        return STK_DIAL;
    }

    /**
     * @param STK_DIAL the STK_DIAL to set
     */
    public void setSTK_DIAL(String STK_DIAL) {
        this.STK_DIAL = STK_DIAL;
    }

    /**
     * @return the STK_ASSIGN_DATE
     */
    public Date getSTK_ASSIGN_DATE() {
        return STK_ASSIGN_DATE;
    }

    /**
     * @param STK_ASSIGN_DATE the STK_ASSIGN_DATE to set
     */
    public void setSTK_ASSIGN_DATE(Date STK_ASSIGN_DATE) {
        this.STK_ASSIGN_DATE = STK_ASSIGN_DATE;
    }
     /**
     * @return the DCMCode
     */
    public String getDCMCode() {
        return DCMCode;
    }

    /**
     * @param DCMCode the DCMCode to set
     */
    public void setDCMCode(String DCMCode) {
        this.DCMCode = DCMCode;
    }

    /**
     * @return the DCMOwner
     */
    public String getDCMOwner() {
        return DCMOwner;
    }

    /**
     * @param DCMOwner the DCMOwner to set
     */
    public void setDCMOwner(String DCMOwner) {
        this.DCMOwner = DCMOwner;
    }




    public void fillInstance(ResultSet res) {
        try {
            this.setDCMName(res.getString("DCM_NAME"));
            this.setDCMCity(res.getString("REGION_NAME"));
            this.setDCMAddress(res.getString("DCM_ADDRESS"));
            this.setSTK_DIAL(res.getString("STK_NUMBER"));
            this.setSTK_ASSIGN_DATE(res.getDate("STK_ASSIGN_DATE"));
            this.setDCMCode(res.getString("DCM_CODE"));
            this.setDCMOwner(res.getString("POS_OWNER_NAME"));
        } catch (SQLException ex) {
            Logger.getLogger(DistributerSTKDetailsModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


}
