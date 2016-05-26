package com.mobinil.sds.core.system.commission.model;

import java.text.SimpleDateFormat;
import java.util.Vector;
import java.sql.*;
import java.sql.Date;
import java.io.Serializable;

public class LabelModel implements Serializable {

    String labelId;
    String labelName;
    String labelDetailId;
    String dcmCode;
    String amount;
    String labelDescription;
    String radioCategory1;
    int twoValues;
    int threeValues;
    String category;
    public static final String LABEL_ID = "LABEL_ID";
    public static final String LABEL_NAME = "LABEL_NAME";
    public static final String LABEL_DESCRIPTION = "LABEL_DESCRIPTION";
    public static final String LABEL_DETAIL_ID = "LABEL_DETAIL_ID";
    public static final String DCM_CODE = "DCM_CODE";
    public static final String AMOUNT = "AMOUNT";
    public static final String CATEGORY = "CATEGORY";
    public static final String TWOVALUES = "TWO_VALUES";
    public static final String THREEVALUES = "THREE_VALUES";
    public static final String INPUT_RADIO_RADIOCATEGORY1 = "INPUT_RADIO_RADIOCATEGORY1";

    public LabelModel() {
    }

    public LabelModel(ResultSet res) {
        try {
            labelDetailId = res.getString(LABEL_DETAIL_ID);
            labelId = res.getString(LABEL_ID);
            dcmCode = res.getString(DCM_CODE);
            amount = res.getString(AMOUNT);
         //   twoValues = res.getInt(TWOVALUES);
         //   threeValues = res.getInt(THREEVALUES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String newLabelId) {
        labelId = newLabelId;
    }
    ////////////////////////////////////////////////////

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String newLabelName) {
        labelName = newLabelName;
    }
    //////////////////////////////////////////////////

    public String getLabelDescription() {
        return labelDescription;
    }

    public void setLabelDescription(String newLabelDescription) {
        labelDescription = newLabelDescription;
    }
    //////////////////////////////////////////////////By Mohamed Youssef

    public int getTwoValues() {
        return twoValues;
    }

    public void setTwoValues(int twoValues) {
        this.twoValues = twoValues;
    }
//////////////////////////////////////////////////////By Mohamed Youssef

    public int getThreeValues() {
        return threeValues;
    }

    public void setThreeValues(int threeValues) {
        this.threeValues = threeValues;
    }
    ////////////////////////////////////////////////// By Mohamed youssef

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /////////////////////////////////////////////////  
    public String getLabelDetailId() {
        return labelDetailId;
    }

    public void setLabelDetailId(String newLabelDetailId) {
        labelDetailId = newLabelDetailId;
    }
    ///////////////////////////////////////////////////

    public String getDcmCode() {
        return dcmCode;
    }

    public void setDcmCode(String newDcmCode) {
        dcmCode = newDcmCode;
    }
    /////////////////////////////////////////////////////

    public String getAmount() {
        return amount;
    }

    public void setAmount(String newAmount) {
        amount = newAmount;
    }
    /////////////////////////////////  by Mohamed Youssef

    public String getRadioCategory1() {
        return radioCategory1;
    }

    public void setRadioCategory1(String radioCategory1) {
        this.radioCategory1 = radioCategory1;
    }
}