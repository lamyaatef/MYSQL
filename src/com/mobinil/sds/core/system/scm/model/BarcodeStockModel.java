/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.model;

import com.mobinil.sds.core.system.Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed Adel
 */
public class BarcodeStockModel extends Model{
private int quantity;
private int userId;
private int barcodeStockTypeId;

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

    /**
     * @return the barcodeStockTypeId
     */
    public int getBarcodeStockTypeId() {
        return barcodeStockTypeId;
    }

    /**
     * @param barcodeStockTypeId the barcodeStockTypeId to set
     */
    public void setBarcodeStockTypeId(int barcodeStockTypeId) {
        this.barcodeStockTypeId = barcodeStockTypeId;
    }

 
    @Override
    public void fillInstance(ResultSet res) {
            try{
            this.setBarcodeStockTypeId(res.getInt("BARCODE_STOCK_TYPE_ID"));
            this.setQuantity(res.getInt("QUANTITY"));
            this.setUserId(res.getInt("USER_ID"));
        } catch (SQLException ex) {
            Logger.getLogger(BarcodeStockModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
