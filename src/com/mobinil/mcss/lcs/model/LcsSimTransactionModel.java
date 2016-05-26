/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.lcs.model;

import java.util.Date;

/**
 *
 * @author Shady Akl
 */
public class LcsSimTransactionModel {

    private String simSerial;
    private Long inventoryItemId;
    private Long transactionId;
    private String transacitonName;
    private Date transactionDate;
    private String fromLocation;
    private String toLocation;
    private Date exportingDate;
    private boolean flag;

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

    public String getSimSerial() {
        return simSerial;
    }

    public void setSimSerial(String simSerial) {
        this.simSerial = simSerial;
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

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
}
