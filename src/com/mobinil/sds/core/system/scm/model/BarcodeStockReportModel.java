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
 * @author Ahmed Adel & Ahmed Safwat
 */
public class BarcodeStockReportModel extends Model {
private int quantity;
private String userLogin;
private String barcodeStockType;
private int barcodeStockTypeId;
private Date updateIn;

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
     * @return the userLogin
     */
    public String getUserLogin() {
        return userLogin;
    }

    /**
     * @param userLogin the userLogin to set
     */
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    /**
     * @return the barcodeStockType
     */
    public String getBarcodeStockType() {
        return barcodeStockType;
    }

    /**
     * @param barcodeStockType the barcodeStockType to set
     */
    public void setBarcodeStockType(String barcodeStockType) {
        this.barcodeStockType = barcodeStockType;
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

    /**
     * @return the updateIn
     */
    public Date getUpdateIn() {
        return updateIn;
    }

    /**
     * @param updateIn the updateIn to set
     */
    public void setUpdateIn(Date updateIn) {
        this.updateIn = updateIn;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setBarcodeStockType(res.getString("BARCODE_STOCK_TYPE"));
            this.setBarcodeStockTypeId(res.getInt("BARCODE_STOCK_TYPE_ID"));
            this.setQuantity(res.getInt("QUANTITY"));
            this.setUpdateIn(res.getDate("UPDATE_IN"));
            this.setUserLogin(res.getString("PERSON_FULL_NAME"));
        } catch (SQLException ex) {
            Logger.getLogger(BarcodeStockReportModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
