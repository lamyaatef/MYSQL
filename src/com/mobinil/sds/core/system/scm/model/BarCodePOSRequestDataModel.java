/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.model;

import com.mobinil.sds.core.system.Model;
import com.mobinil.sds.core.system.scm.dao.GenericDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sand
 */
public class BarCodePOSRequestDataModel extends Model{

private String POS_CODE;
private int CR_Delivered;
private int SFR_Delivered;
private int POSBalance;
private int requsetedQuantity;

    /**
     * @return the POS_CODE
     */
    public String getPOS_CODE() {
        return POS_CODE;
    }

    /**
     * @param POS_CODE the POS_CODE to set
     */
    public void setPOS_CODE(String POS_CODE) {
        this.POS_CODE = POS_CODE;
    }

    /**
     * @return the CR_Delivered
     */
    public int getCR_Delivered() {
        return CR_Delivered;
    }

    /**
     * @param CR_Delivered the CR_Delivered to set
     */
    public void setCR_Delivered(int CR_Delivered) {
        this.CR_Delivered = CR_Delivered;
    }

    /**
     * @return the SFR_Delivered
     */
    public int getSFR_Delivered() {
        return SFR_Delivered;
    }

    /**
     * @param SFR_Delivered the SFR_Delivered to set
     */
    public void setSFR_Delivered(int SFR_Delivered) {
        this.SFR_Delivered = SFR_Delivered;
    }

    /**
     * @return the POSBalance
     */
    public int getPOSBalance() {
        return POSBalance;
    }

    /**
     * @param POSBalance the POSBalance to set
     */
    public void setPOSBalance(int POSBalance) {
        this.POSBalance = POSBalance;
    }
       /**
     * @return the requsetedQuantity
     */
    public int getRequsetedQuantity() {
        return requsetedQuantity;
    }

    /**
     * @param requsetedQuantity the requsetedQuantity to set
     */
    public void setRequsetedQuantity(int requsetedQuantity) {
        this.requsetedQuantity = requsetedQuantity;
    }


    @Override
    public void fillInstance(ResultSet res) {
        try{
       if( GenericDAO.checkColumnName("POS_CODE", res))
            this.setPOS_CODE(res.getString("POS_CODE"));
        if( GenericDAO.checkColumnName("CR", res))
            this.setCR_Delivered(res.getInt("CR"));
           if( GenericDAO.checkColumnName("SFR", res))
            this.setSFR_Delivered(res.getInt("SFR"));
              if( GenericDAO.checkColumnName("BALANCE", res))
            this.setPOSBalance(res.getInt("BALANCE"));
       if( GenericDAO.checkColumnName("QUANTITY", res))
            this.setRequsetedQuantity(res.getInt("QUANTITY"));
        } catch (SQLException ex) {
            Logger.getLogger(BarCodePOSRequestDataModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

 
}
