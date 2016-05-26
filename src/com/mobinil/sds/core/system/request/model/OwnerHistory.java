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

public class OwnerHistory extends Model{
    private String POS_OWNER_NAME;
    private Date POS_OWNER_BIRTHDATE;
    private String POS_OWNER_ID_TYPE_ID;
    private String POS_OWNER_ID_NUMBER;
    private String POS_DETAIL_ID;
    private String POS_OWNER_FLAG;
    private Timestamp UPDATED_IN;
    /**
     * @return the POS_OWNER_NAME
     */
    public String getPOS_OWNER_NAME() {
        return POS_OWNER_NAME;
    }

    /**
     * @param POS_OWNER_NAME the POS_OWNER_NAME to set
     */
    public void setPOS_OWNER_NAME(String POS_OWNER_NAME) {
        this.POS_OWNER_NAME = POS_OWNER_NAME;
    }

    /**
     * @return the POS_OWNER_BIRTHDATE
     */
    public Date getPOS_OWNER_BIRTHDATE() {
        return POS_OWNER_BIRTHDATE;
    }

    /**
     * @param POS_OWNER_BIRTHDATE the POS_OWNER_BIRTHDATE to set
     */
    public void setPOS_OWNER_BIRTHDATE(Date POS_OWNER_BIRTHDATE) {
        this.POS_OWNER_BIRTHDATE = POS_OWNER_BIRTHDATE;
    }

    /**
     * @return the POS_OWNER_ID_TYPE_ID
     */
    public String getPOS_OWNER_ID_TYPE_ID() {
        return POS_OWNER_ID_TYPE_ID;
    }

    /**
     * @param POS_OWNER_ID_TYPE_ID the POS_OWNER_ID_TYPE_ID to set
     */
    public void setPOS_OWNER_ID_TYPE_ID(String POS_OWNER_ID_TYPE_ID) {
        this.POS_OWNER_ID_TYPE_ID = POS_OWNER_ID_TYPE_ID;
    }

    /**
     * @return the POS_OWNER_ID_NUMBER
     */
    public String getPOS_OWNER_ID_NUMBER() {
        return POS_OWNER_ID_NUMBER;
    }

    /**
     * @param POS_OWNER_ID_NUMBER the POS_OWNER_ID_NUMBER to set
     */
    public void setPOS_OWNER_ID_NUMBER(String POS_OWNER_ID_NUMBER) {
        this.POS_OWNER_ID_NUMBER = POS_OWNER_ID_NUMBER;
    }

    /**
     * @return the POS_DETAIL_ID
     */
    public String getPOS_DETAIL_ID() {
        return POS_DETAIL_ID;
    }

    /**
     * @param POS_DETAIL_ID the POS_DETAIL_ID to set
     */
    public void setPOS_DETAIL_ID(String POS_DETAIL_ID) {
        this.POS_DETAIL_ID = POS_DETAIL_ID;
    }

    /**
     * @return the POS_OWNER_FLAG
     */
    public String getPOS_OWNER_FLAG() {
        return POS_OWNER_FLAG;
    }

    /**
     * @param POS_OWNER_FLAG the POS_OWNER_FLAG to set
     */
    public void setPOS_OWNER_FLAG(String POS_OWNER_FLAG) {
        this.POS_OWNER_FLAG = POS_OWNER_FLAG;
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
            this.setPOS_OWNER_ID_NUMBER(res.getString("POS_OWNER_ID_NUMBER"));
            this.setPOS_OWNER_NAME(res.getString("POS_OWNER_NAME"));
            this.setPOS_OWNER_ID_TYPE_ID(res.getString("POS_OWNER_ID_TYPE_ID"));
            this.setPOS_OWNER_BIRTHDATE(res.getDate("POS_OWNER_BIRTHDATE"));
            this.setPOS_OWNER_FLAG(res.getString("POS_OWNER_FLAG"));
            this.setUPDATED_IN(res.getTimestamp("UPDATED_IN"));
        } catch (SQLException ex) {
            Logger.getLogger(OwnerHistory.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

   

}
