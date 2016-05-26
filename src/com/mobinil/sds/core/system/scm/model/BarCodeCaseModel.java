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
 * @author Ahmed Adel
 */
public class BarCodeCaseModel extends Model{
  
    private String POS_Code;
    private int quantity;
    private int status;
    
    /**
     * @return the POS_Code
     */
    public String getPOS_Code() {
        return POS_Code;
    }

    /**
     * @param POS_Code the POS_Code to set
     */
    public void setPOS_Code(String POS_Code) {
        this.POS_Code = POS_Code;
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
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public void fillInstance(ResultSet res) {


            try {
                if( GenericDAO.checkColumnName("POS_CODE", res))
                this.setPOS_Code(res.getString("POS_CODE"));
                if( GenericDAO.checkColumnName("STATUS", res))
                this.setStatus(res.getInt("STATUS"));
                if( GenericDAO.checkColumnName("QUANTITY", res))
                this.setQuantity(res.getInt("QUANTITY"));
            } catch (SQLException ex) {
                Logger.getLogger(BarCodeCaseModel.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}