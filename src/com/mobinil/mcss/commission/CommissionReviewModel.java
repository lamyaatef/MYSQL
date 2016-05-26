package com.mobinil.mcss.commission;

import com.mobinil.sds.core.system.commission.model.*;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.sql.*;
import java.sql.Date;
import java.io.Serializable;

public class CommissionReviewModel implements Serializable {

    int commissionID = 0;
    String commissionName = "";
    String commissionCreationDate;
    String commissionStartDate;
    String commissionEndDate;
    String commissionTypeCategoryName = "";
    String commissionDescription = "";
    String commissionDataViewName = "";
    String commissionDataViewSQL = "";
    String labelText = "";
    String value = "";

    public String getCommissionCreationDate() {
        return commissionCreationDate;
    }

    public void setCommissionCreationDate(String commissionCreationDate) {
        this.commissionCreationDate = commissionCreationDate;
    }

    public String getCommissionDataViewName() {
        return commissionDataViewName;
    }

    public void setCommissionDataViewName(String commissionDataViewName) {
        this.commissionDataViewName = commissionDataViewName;
    }

    public String getCommissionDataViewSQL() {
        return commissionDataViewSQL;
    }

    public void setCommissionDataViewSQL(String commissionDataViewSQL) {
        this.commissionDataViewSQL = commissionDataViewSQL;
    }

    public String getCommissionDescription() {
        return commissionDescription;
    }

    public void setCommissionDescription(String commissionDescription) {
        this.commissionDescription = commissionDescription;
    }

    public int getCommissionID() {
        return commissionID;
    }

    public void setCommissionID(int commissionID) {
        this.commissionID = commissionID;
    }

    public String getCommissionName() {
        return commissionName;
    }

    public void setCommissionName(String commissionName) {
        this.commissionName = commissionName;
    }

    public String getCommissionTypeCategoryName() {
        return commissionTypeCategoryName;
    }

    public void setCommissionTypeCategoryName(String commissionTypeCategoryName) {
        this.commissionTypeCategoryName = commissionTypeCategoryName;
    }

    public String getCommissionStartDate() {
        return commissionStartDate;
    }

    public void setCommissionStartDate(String commissionStartDate) {
        this.commissionStartDate = commissionStartDate;
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCommissionEndDate() {
        return commissionEndDate;
    }

    public void setCommissionEndDate(String commissionEndDate) {
        this.commissionEndDate = commissionEndDate;
    }

    public CommissionReviewModel(Connection con, ResultSet rs) throws Exception {
        // setCommissionID(rs.getInt("COMMISSION_DETAIL_ID"));
        setCommissionName(rs.getString("COMMISSION_NAME"));
        setCommissionCreationDate(rs.getString("COMMISSION_CREATION_DATE"));

        setCommissionStartDate(rs.getString("COMMISSION_START_DATE"));
        setCommissionEndDate(rs.getString("COMMISSION_END_DATE"));

        setCommissionDescription(rs.getString("COMMISSION_DESCRIPTION"));

        setCommissionTypeCategoryName(rs.getString("COMMISSION_TYPE_CATEGORY_NAME"));

        setCommissionDataViewSQL(rs.getString("COMMISSION_DATA_VIEW_SQL"));
        setCommissionDataViewName(rs.getString("DATA_VIEW_NAME"));

        setLabelText(rs.getString("LABEL_TEXT"));
        setValue(rs.getString("VALUE_TYPE"));

    }
}
