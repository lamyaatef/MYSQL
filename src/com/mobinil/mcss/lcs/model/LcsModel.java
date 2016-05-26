/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.lcs.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shady Akl
 */
public class LcsModel {

    private String simSerial="";
    private String simType="";
    private String bundelSerial="";
    private String status="";
    private Date lastTransactionDate;
    private Long lastTransactionId=-1L;
    private Long inventoryItemId=-1L;
    private String transacitonName="";
    private String fromLocation="";
    private String toLocation="";
    private Date exportingDate;
    private boolean flag=false;

    public LcsModel(ResultSet res) {
        try {
            this.simSerial = res.getString("SIM_SERIAL");
            this.simType = res.getString("SIM_TYPE");
            this.bundelSerial = res.getString("BUNDLE_SERIAL");
            this.status = res.getString("STATUS");
            this.lastTransactionId = res.getLong("LAST_TRANSACTION_ID");
            this.lastTransactionDate = res.getDate("LAST_TRANS_DATE");
            this.inventoryItemId = res.getLong("INVENTORY_ITEM_ID");
            this.transacitonName = res.getString("TRANSACTION_NAME");
            this.fromLocation = res.getString("FROM_LOCATION");
            this.toLocation = res.getString("TO_LOCATION");
            this.exportingDate = res.getDate("EXPORTING_DATE");
            this.flag = res.getBoolean("FLAG");

            //the blow line was added by hagry to take into consideration the mobinil shop where the from location is empty
            if (this.fromLocation == null || this.fromLocation.compareTo("")==0)
            {
                this.fromLocation = this.toLocation;
            }


        } catch (SQLException ex) {
            Logger.getLogger(LcsModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public LcsModel() {
    }

    public String getBundelSerial() {
        return bundelSerial;
    }

    public void setBundelSerial(String bundelSerial) {
        this.bundelSerial = bundelSerial;
    }

    public Date getLastTransactionDate() {
        return lastTransactionDate;
    }

    public void setLastTransactionDate(Date lastTransactionDate) {
        this.lastTransactionDate = lastTransactionDate;
    }

    public Long getLastTransactionId() {
        return lastTransactionId;
    }

    public void setLastTransactionId(Long lastTransactionId) {
        this.lastTransactionId = lastTransactionId;
    }

    public String getSimSerial() {
        return simSerial;
    }

    public void setSimSerial(String simSerial) {
        this.simSerial = simSerial;
    }

    public String getSimType() {
        return simType;
    }

    public void setSimType(String simType) {
        this.simType = simType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getExportingDate() {
        return exportingDate;
    }

    public void setExportingDate(Date exportingDate) {
        this.exportingDate = exportingDate;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public Long getInventoryItemId() {
        return inventoryItemId;
    }

    public void setInventoryItemId(Long inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getTransacitonName() {
        return transacitonName;
    }

    public void setTransacitonName(String transacitonName) {
        this.transacitonName = transacitonName;
    }
}
