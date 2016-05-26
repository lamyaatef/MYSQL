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
 * @author Ahmed Adel
 */
public class STKDistributerStatisticsModel extends Model{

    private int DCM_ID;
    private String DCM_Code;
    private String DCM_NAME;
    private int Quantity;

    /**
     * @return the DCM_Code
     */
    public String getDCM_Code() {
        return DCM_Code;
    }

    /**
     * @param DCM_Code the DCM_Code to set
     */
    public void setDCM_Code(String DCM_Code) {
        this.DCM_Code = DCM_Code;
    }

    /**
     * @return the DCM_NAME
     */
    public String getDCM_NAME() {
        return DCM_NAME;
    }

    /**
     * @param DCM_NAME the DCM_NAME to set
     */
    public void setDCM_NAME(String DCM_NAME) {
        this.DCM_NAME = DCM_NAME;
    }

    /**
     * @return the Quantity
     */
    public int getQuantity() {
        return Quantity;
    }

    /**
     * @param Quantity the Quantity to set
     */
    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }
     /**
     * @return the DCM_ID
     */
    public int getDCM_ID() {
        return DCM_ID;
    }

    /**
     * @param DCM_ID the DCM_ID to set
     */
    public void setDCM_ID(int DCM_ID) {
        this.DCM_ID = DCM_ID;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {

            this.setDCM_Code(res.getString("DCM_Code"));
            this.setDCM_NAME(res.getString("DCM_NAME"));
            this.setQuantity(res.getInt("Quantity"));
            this.setDCM_ID(res.getInt("DCM_ID"));
        } catch (SQLException ex) {
            Logger.getLogger(STKDistributerStatisticsModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

   


}
