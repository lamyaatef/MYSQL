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
public class BarcodeRequestExcelModel extends Model{
    private String POSCode;
    private String POSName;
    private int quantity;
    private String district;
    private String repName;
    private String POSType;
    private int POSPayment;

    /**
     * @return the POSCode
     */
    public String getPOSCode() {
        return POSCode;
    }

    /**
     * @param POSCode the POSCode to set
     */
    public void setPOSCode(String POSCode) {
        this.POSCode = POSCode;
    }

    /**
     * @return the POSName
     */
    public String getPOSName() {
        return POSName;
    }

    /**
     * @param POSName the POSName to set
     */
    public void setPOSName(String POSName) {
        this.POSName = POSName;
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
     * @return the district
     */
    public String getDistrict() {
        return district;
    }

    /**
     * @param district the district to set
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * @return the repName
     */
    public String getRepName() {
        return repName;
    }

    /**
     * @param repName the repName to set
     */
    public void setRepName(String repName) {
        this.repName = repName;
    }

    /**
     * @return the POSType
     */
    public String getPOSType() {
        return POSType;
    }

    /**
     * @param POSType the POSType to set
     */
    public void setPOSType(String POSType) {
        this.POSType = POSType;
    }

    /**
     * @return the POSPayment
     */
    public int getPOSPayment() {
        return POSPayment;
    }

    /**
     * @param POSPayment the POSPayment to set
     */
    public void setPOSPayment(int POSPayment) {
        this.POSPayment = POSPayment;
    }

    @Override
    public void fillInstance(ResultSet res) {
        try {
            this.setPOSCode(res.getString("DCM_CODE"));
            this.setPOSName(res.getString("DCM_NAME"));
            this.setDistrict(res.getString("REGION_NAME"));
            this.setQuantity(res.getInt("QUANTITY"));
            this.setPOSType(res.getString("DCM_LEVEL_NAME"));
            this.setRepName(res.getString("REP_FULL_NAME"));
            this.setPOSPayment(res.getInt("DCM_PAYMENT_LEVEL_ID"));
        } catch (SQLException ex) {
            Logger.getLogger(BarcodeRequestExcelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }




}
