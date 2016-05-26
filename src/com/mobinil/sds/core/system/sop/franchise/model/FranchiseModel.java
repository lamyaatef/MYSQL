package com.mobinil.sds.core.system.sop.franchise.model;

import java.util.ArrayList;
import java.io.Serializable;

public class FranchiseModel implements Serializable{
    public FranchiseModel() {
    }
    
    private int id;
    private String code;
    private String franchiseName;
    private String date;
    private String itemCode;
    private int itemQuantity;
    private String lastLCSDate;
    private String lastPGWDat;
    private int LCSQuantity;
    private int PGWQuantity;
    private String currentDate;
    private String LCSItemCode;
    private String LCSItemDescription;
    private String PGWItemCode;
    private String PGWItemName;
    
    public String getCurrentDate()
    {
      return currentDate;
    }
    public void setCurrentDate(String date)
    {
      currentDate = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    
    public String getFranchiseName() {
        return franchiseName;
    }

    public void setFranchiseName(String franchiseName) {
        this.franchiseName = franchiseName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getLastLCSDate() {
        return lastLCSDate;
    }

    public void setLastLCSDate(String lastLCSDate) {
        this.lastLCSDate = lastLCSDate;
    }

    public String getLastPGWDat() {
        return lastPGWDat;
    }

    public void setLastPGWDat(String lastPGWDat) {
        this.lastPGWDat = lastPGWDat;
    }

    public int getLCSQuantity() {
        return LCSQuantity;
    }

    public void setLCSQuantity(int LCSQuantity) {
        this.LCSQuantity = LCSQuantity;
    }

    public int getPGWQuantity() {
        return PGWQuantity;
    }

    public void setPGWQuantity(int PGWQuantity) {
        this.PGWQuantity = PGWQuantity;
    }


    public String getLCSItemCode() {
        return LCSItemCode;
    }

    public void setLCSItemCode(String LCSItemCode) {
        this.LCSItemCode = LCSItemCode;
    }

    public String getLCSItemDescription() {
        return LCSItemDescription;
    }

    public void setLCSItemDescription(String LCSItemDescription) {
        this.LCSItemDescription = LCSItemDescription;
    }

    public String getPGWItemCode() {
        return PGWItemCode;
    }

    public void setPGWItemCode(String PGWItemCode) {
        this.PGWItemCode = PGWItemCode;
    }

    public String getPGWItemName() {
        return PGWItemName;
    }

    public void setPGWItemName(String PGWItemName) {
        this.PGWItemName = PGWItemName;
    }
}
