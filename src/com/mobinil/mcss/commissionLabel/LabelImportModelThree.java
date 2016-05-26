package com.mobinil.mcss.commissionLabel;

import com.mobinil.sds.core.system.commission.model.*;
import java.sql.*;
import java.io.*;

public class LabelImportModelThree implements Serializable {

    String rowNum;
    String dcmCode;
    String amount;
    String category;
    public static final String ROW_NUM = "ROW_NUM";
    public static final String DCM_CODE = "DCM_CODE";
    public static final String AMOUNT = "AMOUNT";
    public static final String CATEGORY = "CATEGORY";

    public LabelImportModelThree(ResultSet res) {
        try {
            rowNum = res.getString(ROW_NUM);
            dcmCode = res.getString(DCM_CODE);
            amount = res.getString(AMOUNT);
            category = res.getString(CATEGORY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LabelImportModelThree() {
    }

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String newRowNum) {
        rowNum = newRowNum;
    }

    public String getDcmCode() {
        return dcmCode;
    }

    public void setDcmCode(String newDcmCode) {
        dcmCode = newDcmCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String newAmount) {
        amount = newAmount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String newCategory) {
        category = newCategory;
    }
}