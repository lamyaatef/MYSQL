package com.mobinil.sds.core.system.commission.dao;

import com.mobinil.sds.core.system.gn.querybuilder.dao.*;
import com.mobinil.sds.core.system.gn.querybuilder.domain.*;
import com.mobinil.sds.core.system.gn.querybuilder.dto.*;
import com.mobinil.sds.core.system.gn.querybuilder.model.*;
import com.mobinil.sds.core.system.commission.factor.model.*;
import java.sql.*;
import java.util.*;
import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.core.system.commission.model.*;
import com.mobinil.sds.core.system.payment.model.*;
import com.mobinil.sds.core.system.commission.model.LabelModel;
import com.mobinil.sds.web.interfaces.commission.*;
import com.mobinil.sds.core.system.gn.reports.dto.*;
import com.mobinil.sds.core.system.gn.dataview.dao.*;
import com.mobinil.sds.web.interfaces.gn.reports.*;
import com.mobinil.sds.web.interfaces.*;
import com.mobinil.sds.web.interfaces.gn.querybuilder.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import oracle.jdbc.OraclePreparedStatement;

public class CommissionDAO {

    public static int addNewCommission(Connection con, String commissionName,
            String commissionStartDate, String commissionEndDate,
            String commissionDesc, String commissionCategoryType,
            String userID, String dataViewID, String dataViewType,
            String commissionBaseID, String commissionChannel, String subtractId, String drivingPlanId)
            throws Exception {
        Statement stmt = con.createStatement();
        Long commissionID = Utility.getSequenceNextVal(con, "SEQ_COMMISSION_DETAIL");
        int commission_id = commissionID.intValue();
        String strQuery = "select * from ADH_DATA_VIEW where DATA_VIEW_ID =  "
                + dataViewID
                + " and data_view_version = (select max(data_view_version) from ADH_DATA_VIEW where DATA_VIEW_ID = "
                + dataViewID + " )";
        ResultSet res = stmt.executeQuery(strQuery);
        String strDataViewQuery = "";
        if (res.next()) {
            int intDataViewId = res.getInt("DATA_VIEW_ID");

            QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine();
            Vector vec = new Vector();
            QueryDTO queryDTO = queryBuilderEngine.loadQueryDTO(con, intDataViewId, vec);
            strDataViewQuery = queryBuilderEngine.constructQuerySQL(queryDTO);
            Utility.logger.debug(strDataViewQuery);
        }
        res.close();
        strDataViewQuery = strDataViewQuery.replaceAll("'", "''");
        int dataViewTypeID = 0;
        Utility.logger.debug(dataViewType);
        if (dataViewType.equals(CommissionInterfaceKey.DATA_VIEW_TYPE_A)) {
            dataViewTypeID = 1;
        }
        if (dataViewType.equals(CommissionInterfaceKey.DATA_VIEW_TYPE_B)) {
            dataViewTypeID = 2;
        }
        commissionName = commissionName + commissionID;

        try {
            Integer.parseInt(subtractId);
        } catch (Exception e) {
            subtractId = "null";
        }

        String sqlString = "INSERT INTO COMMISSION_DETAIL (COMMISSION_DETAIL_ID , COMMISSION_NAME , COMMISSION_STATUS_TYPE_ID,"
                + " COMMISSION_TYPE_CATEGORY_ID, COMMISSION_START_DATE , COMMISSION_END_DATE , COMMISSION_DESCRIPTION,"
                + " COMMISSION_DATA_VIEW_ID ,COMMISSION_DATA_VIEW_SQL , DATA_VIEW_TYPE_ID , COMMISSION_BASE_ID,COMMISSION_CHANNEL_ID,COMMISSION_CREATION_DATE,subtractID,DP_ID)"
                + " VALUES("
                + commission_id
                + ",'"
                + commissionName
                + "','1','"
                + commissionCategoryType
                + "',TO_DATE('"
                + commissionStartDate
                + "','dd/mm/yyyy'),"
                + "TO_DATE('"
                + commissionEndDate
                + "','dd/mm/yyyy'),'"
                + commissionDesc
                + "',"
                + dataViewID
                + ",?, "
                + dataViewTypeID
                + ","
                + commissionBaseID
                + ",'"
                + commissionChannel + "',sysdate," + subtractId + ",'" + drivingPlanId + "')";

        oracle.jdbc.OraclePreparedStatement pstmt = (oracle.jdbc.OraclePreparedStatement) con.prepareStatement(sqlString);

        pstmt.setStringForClob(1, strDataViewQuery);

        pstmt.execute();
        pstmt.close();

        // Utility.logger.debug(sqlString);
        // int rows = stmt.executeUpdate(sqlString);
        Long commissionStatusID = Utility.getSequenceNextVal(con, "SEQ_COMMISSION_STATUS");
        int commission_status_id = commissionStatusID.intValue();
        sqlString = "INSERT INTO COMMISSION_STATUS (COMMISSION_STATUS_ID, COMMISSION_DETAIL_ID,COMMISSION_STATUS_TYPE_ID,COMMISSION_STATUS_DATE,USER_ID)"
                + " VALUES("
                + commission_status_id
                + ","
                + commission_id
                + ",1,TO_DATE(SYSDATE,'dd/mm/yyyy')," + userID + ")";
        stmt.executeUpdate(sqlString);

        stmt.close();
        return commission_id;
    }

    public static Vector searchCommissionByFilter(Connection con,
            String commissionId, String commissionName,
            String commissionStatus, String commissionType,
            String commissionCategory, String commissionStartDateFrom,
            String commissionStartDateTo, String commissionEndDateFrom,
            String commissionEndDateTo, String commissionChannel, String userId)
            throws Exception {
        Statement stmt = con.createStatement();
        boolean andFlag = false;
        String sqlString = "SELECT commission_detail_id,commission_name,commission_status_type_id,commission_type_category_id, to_char(commission_start_date,'dd/mm/yyyy') commission_start_date, to_char(commission_end_date,'dd/mm/yyyy') commission_end_date ,commission_description,commission_data_view_id,data_view_type_id,commission_channel_id, to_Char (commission_creation_date , 'dd/mm/yyyy') commission_creation_date,commission_status_type_name, commission_type_category_name,commission_type_id,  commission_channel_name,commission_type_name,data_view_type_name,commission_base_id,subtractid FROM vw_COMMISSION_DETAIL ";

        if (!commissionId.equals("")) {
            if (andFlag == false) {
                sqlString += " WHERE COMMISSION_detail_ID = '" + commissionId
                        + "' ";
            } else {
                sqlString += " AND COMMISSION_detail_ID = '" + commissionId
                        + "' ";
            }
            andFlag = true;
        }

        if (!commissionName.equals("")) {
            if (andFlag == false) {
                sqlString += " WHERE COMMISSION_NAME = '" + commissionName
                        + "' ";
            } else {
                sqlString += " AND COMMISSION_NMAE = '" + commissionName + "' ";
            }
            andFlag = true;
        }

        if (!commissionStatus.equals("0")) {
            if (andFlag == false) {
                sqlString += " WHERE COMMISSION_STATUS_TYPE_ID = '"
                        + commissionStatus + "' ";
            } else {
                sqlString += " AND COMMISSION_STATUS_TYPE_ID = '"
                        + commissionStatus + "' ";
            }
            andFlag = true;
        }

        if (!commissionType.equals("0")) {
            if (andFlag == false) {
                sqlString += " WHERE COMMISSION_TYPE_ID = '" + commissionType
                        + "' ";
            } else {
                sqlString += " AND COMMISSION_TYPE_ID = '" + commissionType
                        + "' ";
            }
            andFlag = true;
        }

        if (!commissionCategory.equals("0")) {
            if (andFlag == false) {
                sqlString += " WHERE COMMISSION_TYPE_CATEGORY_ID = '"
                        + commissionCategory + "' ";
            } else {
                sqlString += " AND COMMISSION_TYPE_CATEGORY_ID = '"
                        + commissionCategory + "' ";
            }
            andFlag = true;
        }

        if (!(commissionStartDateFrom.compareTo("*") == 0)) {
            if (andFlag == false) {
                sqlString += " WHERE COMMISSION_START_DATE >= TO_DATE('"
                        + commissionStartDateFrom + "','dd/mm/yyyy') ";
            } else {
                sqlString += " AND COMMISSION_START_DATE >= TO_DATE('"
                        + commissionStartDateFrom + "','dd/mm/yyyy') ";
            }
            andFlag = true;
        }

        if (!(commissionStartDateTo.compareTo("*") == 0)) {
            if (andFlag == false) {
                sqlString += " WHERE COMMISSION_START_DATE <= TO_DATE('"
                        + commissionStartDateTo + "','dd/mm/yyyy') ";
            } else {
                sqlString += " AND COMMISSION_START_DATE <= TO_DATE('"
                        + commissionStartDateTo + "','dd/mm/yyyy') ";
            }
            andFlag = true;
        }

        if (!(commissionEndDateFrom.compareTo("*") == 0)) {
            if (andFlag == false) {
                sqlString += " WHERE COMMISSION_END_DATE >= TO_DATE('"
                        + commissionEndDateFrom + "','dd/mm/yyyy') ";
            } else {
                sqlString += " AND COMMISSION_END_DATE >= TO_DATE('"
                        + commissionEndDateFrom + "','dd/mm/yyyy') ";
            }
            andFlag = true;
        }

        if (!(commissionEndDateTo.compareTo("*") == 0)) {
            if (andFlag == false) {
                sqlString += " WHERE COMMISSION_END_DATE <= TO_DATE('"
                        + commissionEndDateTo + "','dd/mm/yyyy') ";
            } else {
                sqlString += " AND COMMISSION_END_DATE <= TO_DATE('"
                        + commissionEndDateTo + "','dd/mm/yyyy') ";
            }
            andFlag = true;
        }

        if (!commissionChannel.equals("")) {

            if (andFlag == false) {
                sqlString += " WHERE COMMISSION_CHANNEL_ID ='"
                        + commissionChannel + "'";
            } else {
                sqlString += " AND COMMISSION_CHANNEL_ID ='"
                        + commissionChannel + "'";
            }
            andFlag = true;
        } else {
            if (andFlag == false) {
                sqlString += " WHERE COMMISSION_CHANNEL_ID in (select Commission_channel_id from commission_User_channel where COMMISSION_USER_ID="
                        + userId + ") ";
            } else {
                sqlString += " AND COMMISSION_CHANNEL_ID in (select Commission_channel_id from commission_User_channel where COMMISSION_USER_ID="
                        + userId + ") ";
            }
            andFlag = true;
        }

        sqlString += " ORDER BY COMMISSION_CHANNEL_ID,COMMISSION_STATUS_TYPE_ID,COMMISSION_NAME";
        Utility.logger.debug("Commission Search SQL:  " + sqlString);
        System.out.println("Commission Search SQL:  " + sqlString);

        ResultSet rs = stmt.executeQuery(sqlString);
        CommissionModel commissionModel = null;
        Vector commissionSearchResults = new Vector();
        while (rs.next()) {
            commissionModel = new CommissionModel(con, rs);
            commissionSearchResults.add(commissionModel);
        }
        rs.close();
        stmt.close();
        return commissionSearchResults;
    }
    private static final String AddNewCommissionCategorySQL = "INSERT INTO COMMISSION_TYPE_CATEGORY (COMMISSION_TYPE_CATEGORY_ID, "
            + " COMMISSION_TYPE_CATEGORY_NAME , COMMISSION_TYPE_CATEGORY_DESC , COMMISSION_TYPE_ID) "
            + " VALUES(SEQ_COMMISSION_TYPE_CATEGORY.nextval,?,?,?)";

    public static void addNewCommissionCategory(Connection con,
            String commissionType, String commissionCategoryName,
            String commissionCategoryDesc) throws Exception {

        DBUtil.executePreparedStatment(AddNewCommissionCategorySQL, con, commissionCategoryName, commissionCategoryDesc, commissionType);
    }

    public static void updateCommissionCategory(Connection con,
            String CategoryName, String categoryDesc, String categoryID)
            throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "UPDATE COMMISSION_TYPE_CATEGORY SET COMMISSION_TYPE_CATEGORY_NAME = '"
                + CategoryName
                + "' , COMMISSION_TYPE_CATEGORY_DESC = '"
                + categoryDesc
                + "' WHERE COMMISSION_TYPE_CATEGORY_ID = '"
                + categoryID + "'";
        int rows = stmt.executeUpdate(sqlString);
        stmt.close();
    }

    public static void updateCommissionStatus(Connection con,
            String CommissionID, String StatusID, String userID)
            throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "UPDATE COMMISSION_DETAIL SET COMMISSION_STATUS_TYPE_ID = "
                + StatusID + " WHERE COMMISSION_DETAIL_ID=" + CommissionID + "";
        int rows = stmt.executeUpdate(sqlString);
        Long commissionStatusID = Utility.getSequenceNextVal(con, "SEQ_COMMISSION_STATUS");
        int commission_status_id = commissionStatusID.intValue();
        sqlString = "INSERT INTO COMMISSION_STATUS (COMMISSION_STATUS_ID, COMMISSION_DETAIL_ID,COMMISSION_STATUS_TYPE_ID,COMMISSION_STATUS_DATE,USER_ID)"
                + " VALUES("
                + commission_status_id
                + ","
                + CommissionID
                + ","
                + StatusID + ",sysdate," + userID + ")";
        rows = stmt.executeUpdate(sqlString);

        stmt.close();
    }

    public static CommissionModel getCommissionByID(Connection con,
            String commissionID) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "SELECT * FROM VW_COMMISSION_DETAIL WHERE COMMISSION_DETAIL_ID = "
                + commissionID;
        ResultSet rs = stmt.executeQuery(sqlString);
        CommissionModel commissionModel = null;
        if (rs.next()) {
            commissionModel = new CommissionModel(con, rs);
        }

        rs.close();
        stmt.close();
        return commissionModel;
    }

    public static boolean hasParameters(Connection con, String dataViewID)
            throws Exception {
        Statement stmt = con.createStatement();
        boolean res = false;
        String sqlString = "SELECT * FROM ADH_FIELD WHERE DATA_VIEW_ID = "
                + dataViewID + " AND FIELD_TYPE_ID = 5";
        ResultSet rs = stmt.executeQuery(sqlString);
        if (rs.next()) {
            res = true;
        }
        return res;
    }

    // get DATA VIEW details
    public static ReportBuilderWizardDTO getDataViewParameters(Connection con,
            int dataViewID) throws Exception {
        ReportBuilderWizardDTO reportBuilderWizardDTO = new ReportBuilderWizardDTO();

        reportBuilderWizardDTO.setDetailedDataView(DataViewDAO.getDataViewDetails(con, dataViewID));

        reportBuilderWizardDTO.getDetailedDataView().setDataViewFields(DataViewDAO.getDataViewFieldsListByDisplayType(con, dataViewID, ReportInterfaceKey.DISPLAY_TYPE_DISPLAYED));

        reportBuilderWizardDTO.setReportFields(DataViewDAO.getDataViewFieldsListByDisplayType(con, dataViewID, ReportInterfaceKey.DISPLAY_TYPE_DISPLAYED));
        reportBuilderWizardDTO.setReportOrderBy(DataViewDAO.getOrderByListSimplified(con, dataViewID));

        // commented by hagry to try to solve the bug about parameters not get
        // to be displayed in the report parge
        reportBuilderWizardDTO.setReportParameters(getReportParameters(con, dataViewID));

        reportBuilderWizardDTO.setAvailableDataViews(DataViewDAO.getDataViewsListAllUniverses(con,
                // ReportInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_REPORT ,
                ReportInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_QUERY, ReportInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_ACTIVE));

        // debug ( "loadReport", "loaded ReportName: " +
        // reportBuilderWizardDTO.getReport().getReportName(), true) ;

        return reportBuilderWizardDTO;
    }

    public static Vector getReportParameters(Connection argCon, int dataViewID) {
        ResultSet rst;
        PreparedStatement stmt;
        String strQuery = null;
        // Long lTypeID = null ;
        Vector vecReportParameterList = null;
        ParameterFieldDTO parameterFieldDTO = null;
        try {
            vecReportParameterList = new Vector();

            // Utility.logger.debug ( "  got strOrderByType: " + strOrderByType)
            // ;
            strQuery = new String(" select  VW_ADH_INPUT_PARAM.* , VW_ADH_FIELD.* "
                    + " from    VW_ADH_DATA_VIEW, VW_ADH_INPUT_PARAM , VW_ADH_FIELD"
                    + " where  "
                    + "         VW_ADH_DATA_VIEW.DATA_VIEW_ID = "
                    + dataViewID
                    + " and "
                    + "        VW_ADH_DATA_VIEW.DATA_VIEW_ID = VW_ADH_INPUT_PARAM.DATA_VIEW_ID "
                    + "          AND VW_ADH_FIELD.FIELD_ID = VW_ADH_INPUT_PARAM.FIELD_ID "
                    + " order by INPUT_PARAM_ID ");
            Utility.logger.debug(strQuery);
            stmt = argCon.prepareStatement(strQuery);

            // debug ( "getReportParameters", strQuery, true ) ;

            rst = stmt.executeQuery();

            while (rst.next()) {
                parameterFieldDTO = new ParameterFieldDTO();

                // debug ( "getReportParameters",
                // ""+rst.getInt("INPUT_PARAM_ID"), true ) ;

                // Utility.logger.debug ( "  got UNIVERSE_ID: " +
                // rstType.getInt("UNIVERSE_ID") ) ;

                parameterFieldDTO.setFieldID(rst.getInt("INPUT_PARAM_ID"));
                parameterFieldDTO.setFieldName(rst.getString("FIELD_NAME"));
                parameterFieldDTO.setLabelText(rst.getString("INPUT_PARAM_LABEL_TEXT"));
                parameterFieldDTO.setOrder(rst.getInt("INPUT_PARAM_ORDER"));
                parameterFieldDTO.setFieldDescription(rst.getString("INPUT_PARAM_DESCRIPTION"));
                parameterFieldDTO.setControlTypeID(rst.getInt("INPUT_CONTROL_TYPES_ID"));
                parameterFieldDTO.setFieldSQLCash(rst.getString("FIELD_SQL_CASH"));

                vecReportParameterList.add(parameterFieldDTO);
            }
            rst.close();
            stmt.close();
        } catch (Exception objExp) {
            objExp.printStackTrace();
        }
        return vecReportParameterList;
    }

    public static String previewReport(Connection con, ReportDTO reportDTO,
            Vector vecReportParameterList, int dataViewID) {

        Vector vecReportSelectList = null;
        Vector vecReportOrderByList = null;
        Vector vecQueryDisplayedFields = null;
        Vector newDisplayedFields = null;
        Vector newOrderByFields = null;

        String strSQL = null;
        int nColNum = 0;
        String strColName;
        PreparedStatement stmtQuery = null;
        ResultSet rstQuery = null;
        String strErrorMessage;
        Vector vecHeaderFields;
        Vector vecQueryParameterList;
        QueryViewerDTO queryViewerDTO = null;
        QueryBuilderEngine queryBuilderEngine = null;
        QueryDTO queryDTO = null;
        int startNum = 0;
        int endNum = 0;
        try {

            // dataViewID = reportDTO.getReportDataViewID() ;
            queryBuilderEngine = new QueryBuilderEngine();
            queryViewerDTO = new QueryViewerDTO();
            // queryViewerDTO.setM_dataViewName(reportDTO.getReportName());
            Vector vec = new Vector();
            queryDTO = queryBuilderEngine.loadQueryDTO(con, dataViewID, vec);

            // synchronize between the original query
            // and the newly selected fields

            vecReportSelectList = getDataViewSelectFields(con, dataViewID);

            if (vecReportSelectList.size() != 0) {

                vecQueryDisplayedFields = queryDTO.getDisplayedFields();
                newDisplayedFields = new Vector();
                for (int i = 0; i < vecQueryDisplayedFields.size(); i++) {
                    FieldDTO temp = (FieldDTO) vecQueryDisplayedFields.elementAt(i);

                    boolean found = false;
                    for (int j = 0; (j < vecReportSelectList.size())
                            && (found == false); j++) {
                        if ((((FieldDTO) vecQueryDisplayedFields.elementAt(i)).getFieldID() == ((FieldModel) vecReportSelectList.elementAt(j)).getFieldID())
                                || (((FieldDTO) vecQueryDisplayedFields.elementAt(i)).getFieldID() == ((FieldModel) vecReportSelectList.elementAt(j)).getFieldRFObject())) {
                            newDisplayedFields.add((FieldDTO) vecQueryDisplayedFields.elementAt(i));
                            found = true;
                        }
                    }
                }
                queryDTO.setDisplayedFields(newDisplayedFields);
            }

            // Construct a new list of order by clause, ignoring the
            // old list completely. An assumption here is that the
            // order by list a also a subset of the select clause.
            newOrderByFields = new Vector();
            vecReportOrderByList = queryDTO.getOrderBy();
            for (int i = 0; i < vecReportOrderByList.size(); i++) {
                boolean found = false;
                for (int j = 0; (j < vecQueryDisplayedFields.size())
                        && (found == false); j++) {
                    if (((ReportOrderByDTO) vecReportOrderByList.elementAt(i)).getOrderByFieldID() == ((FieldDTO) vecQueryDisplayedFields.elementAt(j)).getFieldID()) {
                        OrderByDTO orderByDTO = new OrderByDTO();
                        orderByDTO.setOrderType(((ReportOrderByDTO) vecReportOrderByList.elementAt(i)).getOrderByType());
                        orderByDTO.setFieldDTO((FieldDTO) vecQueryDisplayedFields.elementAt(i));
                        newOrderByFields.add(orderByDTO);
                        found = true;
                    }
                }
            }
            queryDTO.setOrderBy(newOrderByFields);

            // insert parameter values
            vecQueryParameterList = queryDTO.getParametersFields();

            for (int i = 0; i < vecQueryParameterList.size(); i++) {

                // assumption here is that both parameter lists are in the same
                // order
                ((ParameterFieldDTO) vecQueryParameterList.elementAt(i)).setFieldSQLCash(((ParameterFieldDTO) vecReportParameterList.elementAt(i)).getFieldSQLCash());

                ParameterFieldDTO originalParam = (ParameterFieldDTO) vecQueryParameterList.elementAt(i);

                Vector conditionElements = queryDTO.getQueryWhere().getConditionElements();
                int termType;
                FieldToFieldTermDTO fieldToFieldTermDTO = null;
                FieldtoDataViewTermDTO fieldtoDataViewTermDTO = null;

                for (int j = 0; j < conditionElements.size(); j++) {
                    ConditionElement conditionElement = (ConditionElement) conditionElements.elementAt(j);
                    termType = conditionElement.getConditionElementType().getConditionElementTypeID();

                    if (termType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM) {

                        Term term = (Term) conditionElements.elementAt(j);
                        termType = term.getTermType().getTermTypeID();
                        if (termType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TYPE) {
                            // Utility.logger.debug ("--->FIELD_TO_FIELD_TYPE")
                            // ;
                            fieldToFieldTermDTO = (FieldToFieldTermDTO) conditionElements.elementAt(j);

                            if (originalParam.getFieldID() == fieldToFieldTermDTO.get1stOperandFieldID()) {
                                fieldToFieldTermDTO.set1stOperandFieldSQLCache(originalParam.getFieldSQLCash());
                            } else if (originalParam.getFieldID() == fieldToFieldTermDTO.get2ndOperandFieldID()) {
                                fieldToFieldTermDTO.set2ndOperandFieldSQLCache(originalParam.getFieldSQLCash());
                            }

                            // Utility.logger.debug("field 1 id = " +
                            // fieldToFieldTermDTO.get1stOperandFieldID());
                            // Utility.logger.debug("field 2 id = " +
                            // fieldToFieldTermDTO.get2ndOperandFieldID());

                            // strSQL.append (
                            // fieldToFieldTermDTO.get1stOperandFieldSQLCache()
                            // + " ");
                            // strSQL.append (
                            // fieldToFieldTermDTO.getOperatorSQL() + " ");
                            // strSQL.append (
                            // fieldToFieldTermDTO.get2ndOperandFieldSQLCache()
                            // + " ");
                        } else if (termType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TYPE) {
                            // Utility.logger.debug
                            // ("--->FIELD_TO_DATAVIEW_TYPE") ;
                            fieldtoDataViewTermDTO = (FieldtoDataViewTermDTO) conditionElements.elementAt(j);
                            // Utility.logger.debug("field 1 id = " +
                            // fieldtoDataViewTermDTO.get1stOperandFieldID());

                            if (originalParam.getFieldID() == fieldtoDataViewTermDTO.get1stOperandFieldID()) {
                                fieldtoDataViewTermDTO.set1stOperandFieldSQLCache(originalParam.getFieldSQLCash());
                            }

                        }
                    }
                }

                Vector havingElements = queryDTO.getQueryHaving().getConditionElements();
                fieldToFieldTermDTO = null;
                fieldtoDataViewTermDTO = null;
                for (int k = 0; k < havingElements.size(); k++) {
                    ConditionElement conditionElement = (ConditionElement) havingElements.elementAt(k);
                    termType = conditionElement.getConditionElementType().getConditionElementTypeID();

                    if (termType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM) {
                        Term term = (Term) havingElements.elementAt(k);
                        termType = term.getTermType().getTermTypeID();
                        if (termType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TYPE) {
                            fieldToFieldTermDTO = (FieldToFieldTermDTO) havingElements.elementAt(k);

                            if (originalParam.getFieldID() == fieldToFieldTermDTO.get1stOperandFieldID()) {
                                fieldToFieldTermDTO.set1stOperandFieldSQLCache(originalParam.getFieldSQLCash());
                            } else if (originalParam.getFieldID() == fieldToFieldTermDTO.get2ndOperandFieldID()) {
                                fieldToFieldTermDTO.set2ndOperandFieldSQLCache(originalParam.getFieldSQLCash());
                            }
                        } else if (termType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TYPE) {

                            fieldtoDataViewTermDTO = (FieldtoDataViewTermDTO) havingElements.elementAt(k);
                            if (originalParam.getFieldID() == fieldtoDataViewTermDTO.get1stOperandFieldID()) {
                                fieldtoDataViewTermDTO.set1stOperandFieldSQLCache(originalParam.getFieldSQLCash());
                            }
                        }
                    }
                }

            }

            System.out.println("queryBuilderEngine = " + queryBuilderEngine);
            System.out.println("queryDTO = " + queryDTO);

            strSQL = queryBuilderEngine.constructQuerySQL(queryDTO);

            queryViewerDTO.setSQLCode(strSQL);

        } catch (Exception objExp) {
            objExp.printStackTrace();
        }
        return strSQL;
    }

    public static Vector getDataViewSelectFields(Connection con, int dataViewID)
            throws Exception {
        Statement stmt = con.createStatement();

        String sqlString = "SELECT * FROM ADH_FIELD WHERE FIELD_DISPLAY_TYPE_ID = 3 AND DATA_VIEW_ID = "
                + dataViewID;
        ResultSet rs = stmt.executeQuery(sqlString);
        Vector fields = new Vector();
        while (rs.next()) {
            FieldModel fieldDTO = new FieldModel(rs.getInt("DATA_VIEW_ID"), rs.getInt("DATA_VIEW_ISSUE"), rs.getInt("DATA_VIEW_VERSION"), rs.getInt("FIELD_ID"), rs.getString("FIELD_NAME"), rs.getString("FIELD_DESCRIPTION"), rs.getString("FIELD_SQL_CASH"), rs.getInt("FIELD_DISPLAY_TYPE_ID"), rs.getString("FIELD_DISPLAY_TYPE_NAME"), rs.getInt("FIELD_TYPE_ID"), rs.getString("FIELD_TYPE_NAME"), rs.getInt("FIELD_TYPE_OBJECT_ID"), rs.getInt("FIELD_RF_OBJECT"));
            fields.add(fieldDTO);
            fieldDTO = null;

        }
        rs.close();
        stmt.close();
        return fields;
    }

    public static void updateCommissionSQLString(Connection con,
            String DataViewSQL, int commissionID) throws Exception {

        String sqlString = "UPDATE COMMISSION_DETAIL SET COMMISSION_DATA_VIEW_SQL =? WHERE COMMISSION_DETAIL_ID = "
                + commissionID;
        OraclePreparedStatement ostat = (OraclePreparedStatement) con.prepareStatement(sqlString);
        ostat.setStringForClob(1, DataViewSQL);

        ostat.executeUpdate();
        ostat.close();
    }

    public static void getCommissionBaseFactorValue(Connection con,
            String commissionBaseID, String commissionID) throws Exception {
        String factorName = "";
        Statement stat = con.createStatement();
        String sql = "SELECT COMMISSION_FACTOR_NAME FROM COMMISSION_FACTOR WHERE COMMISSION_DETAIL_ID = "
                + commissionID;
        ResultSet res = stat.executeQuery(sql);
        while (res.next()) {
            factorName = res.getString("COMMISSION_FACTOR_NAME");
            Statement stmt = con.createStatement();
            String sqlString = "SELECT COMMISSION_FACTOR_VALUE FROM COMMISSION_FACTOR WHERE COMMISSION_DETAIL_ID = "
                    + commissionBaseID
                    + " AND "
                    + "COMMISSION_FACTOR_NAME = '"
                    + factorName + "'";
            ResultSet rs = stmt.executeQuery(sqlString);
            double factorValue = 0;
            if (rs.next()) {
                factorValue = rs.getDouble("COMMISSION_FACTOR_VALUE");
                sqlString = "UPDATE COMMISSION_FACTOR SET COMMISSION_FACTOR_VALUE = "
                        + factorValue
                        + " WHERE COMMISSION_DETAIL_ID ="
                        + commissionID
                        + " AND COMMISSION_FACTOR_NAME='"
                        + factorName + "'";
                Statement stmt2 = con.createStatement();
                int rows = stmt2.executeUpdate(sqlString);
                stmt2.close();
            }
            rs.close();
            stmt.close();
        }
        res.close();
        stat.close();
    }

    public static Vector getPaymentForDcm(Connection con, String dcmCode,
            String paymentStatus, String paymentStartDateFrom,
            String paymentStartDateTo, String paymentEndDateFrom,
            String paymentEndDateTo) throws Exception {
        Statement stat = con.createStatement();
        String strSql = "SELECT * FROM vw_payment_detail WHERE payment_detail_id IN "
                + "(SELECT payment_detail_id FROM PAYMENT_COMMISSION WHERE commission_detail_id IN"
                + "(SELECT commission_detail_id FROM vw_commission_item_factor WHERE dcm_code = '"
                + dcmCode + "'))";

        if (paymentStatus.compareTo("") != 0) {
            strSql += "and PAYMENT_STATUS_TYPE_ID = '" + paymentStatus + "' ";
        }
        if (paymentStatus.compareTo("") == 0) {
            strSql += "and PAYMENT_STATUS_TYPE_ID >= 2 AND PAYMENT_STATUS_TYPE_ID < 4 ";
        }
        if (paymentStartDateFrom.compareTo("*") != 0) {
            strSql += "and PAYMENT_START_DATE >= TO_DATE('"
                    + paymentStartDateFrom + "','mm/dd/yyyy') ";
        }
        if (paymentStartDateTo.compareTo("*") != 0) {
            strSql += "and PAYMENT_START_DATE <= TO_DATE('"
                    + paymentStartDateTo + "','mm/dd/yyyy') ";
        }
        if (paymentEndDateFrom.compareTo("*") != 0) {
            strSql += "and PAYMENT_END_DATE >= TO_DATE('" + paymentEndDateFrom
                    + "','mm/dd/yyyy') ";
        }
        if (paymentEndDateTo.compareTo("*") != 0) {
            strSql += "and PAYMENT_END_DATE <= TO_DATE('" + paymentEndDateTo
                    + "','mm/dd/yyyy') ";
        }
        Utility.logger.debug("The execute query is " + strSql);
        ResultSet res = stat.executeQuery(strSql);
        PaymentModel paymentModel = null;
        Vector paymentSearchResult = new Vector();
        while (res.next()) {
            paymentModel = new PaymentModel(con, res);
            paymentSearchResult.add(paymentModel);
        }
        return paymentSearchResult;
    }

    public static Vector getCommissionsForDcm(Connection con, String dcmCode,
            int paymentId) throws Exception {
        Statement stat = con.createStatement();
        String strSql = "SELECT COMMISSION_FACTOR_ID , COMMISSION_FACTOR_NAME ,COMMISSION_FACTOR_VALUE"
                + ",COMMISSION_ITEM_NAME,COMMISSION_ITEM_AMOUNT,DCM_ID ,DCM_NAME,DCM_CODE,COMMISSION_NAME,COMMISSION_TYPE_NAME,COMMISSION_TYPE_CATEGORY_NAME  "
                + "FROM VW_COMMISSION_ITEM_FACTOR WHERE commission_detail_id IN"
                + "(SELECT commission_detail_id FROM PAYMENT_COMMISSION WHERE payment_detail_id = '"
                + paymentId + "')" + " and dcm_code = '" + dcmCode + "'";
        Utility.logger.debug("The Commission query isssssssssssss " + strSql);
        ResultSet res = stat.executeQuery(strSql);
        FactorModel factorModel = null;
        Vector commissionDetails = new Vector();
        while (res.next()) {
            factorModel = new FactorModel();
            factorModel.setCommissionFactorID(res.getInt("COMMISSION_FACTOR_ID"));
            factorModel.setCommissionFactorName(res.getString("COMMISSION_FACTOR_NAME"));
            factorModel.setCommissionFactorValue(res.getDouble("COMMISSION_FACTOR_VALUE"));
            factorModel.setCommissionItemName(res.getString("COMMISSION_ITEM_NAME"));
            factorModel.setCommissionItemAmount(res.getInt("COMMISSION_ITEM_AMOUNT"));
            factorModel.setCommissionDCMID(res.getInt("DCM_ID"));
            factorModel.setCommissionDCMName(res.getString("DCM_NAME"));
            factorModel.setCommissionDCMCode(res.getString("DCM_CODE"));
            factorModel.setCommissionName(res.getString("COMMISSION_NAME"));
            factorModel.setCommissionType(res.getString("COMMISSION_TYPE_NAME"));
            factorModel.setCommissionCategory(res.getString("COMMISSION_TYPE_CATEGORY_NAME"));
            commissionDetails.add(factorModel);
        }
        return commissionDetails;
    }

    public static Vector getPreparingCommission(Connection con)
            throws Exception {
        Statement stmt = con.createStatement();
        Vector commissions = new Vector();
        String sqlString = "SELECT COMMISSION_DETAIL_ID FROM COMMISSION_DETAIL WHERE COMMISSION_STATUS_TYPE_ID = 1";
        ResultSet rs = stmt.executeQuery(sqlString);
        while (rs.next()) {
            commissions.add(rs.getInt("COMMISSION_DETAIL_ID") + "");
        }
        rs.close();
        stmt.close();
        return commissions;
    }

    public static String getCommissionUserID(Connection con, String CommissionID)
            throws Exception {
        Statement stmt = con.createStatement();
        String userID = "";
        String sqlString = "SELECT USER_ID FROM COMMISSION_STATUS WHERE COMMISSION_STATUS_TYPE_ID = 1 AND COMMISSION_DETAIL_ID = "
                + CommissionID;
        ResultSet rs = stmt.executeQuery(sqlString);
        if (rs.next()) {
            userID = rs.getInt("USER_ID") + "";
        }
        rs.close();
        stmt.close();
        return userID;

    }

    public static Vector getAllLabels(Connection con) throws Exception {
        Vector labelVec = new Vector();
        LabelModel labelModel = null;
        Statement stat = con.createStatement();
        String strSql = "select * from COMMISSION_LABEL";
        ResultSet res = stat.executeQuery(strSql);
        while (res.next()) {
            labelModel = new LabelModel();
            labelModel.setLabelId(res.getString(labelModel.LABEL_ID));
            labelModel.setLabelName(res.getString(labelModel.LABEL_NAME));
            labelModel.setLabelDescription(res.getString(labelModel.LABEL_DESCRIPTION));
            labelVec.add(labelModel);
        }
        return labelVec;
    }

    public static LabelModel selectLabelData(Connection con, String labelId)
            throws Exception {
        LabelModel labelModel = null;
        Statement stat = con.createStatement();
        String strSql = "select * from COMMISSION_LABEL where LABEL_ID = '"
                + labelId + "'";
        ResultSet res = stat.executeQuery(strSql);
        while (res.next()) {
            labelModel = new LabelModel();
            labelModel.setLabelName(res.getString(labelModel.LABEL_NAME));
            labelModel.setLabelDescription(res.getString(labelModel.LABEL_DESCRIPTION));
            labelModel.setTwoValues(res.getInt(labelModel.TWOVALUES));
            labelModel.setThreeValues(res.getInt(labelModel.THREEVALUES));
        }
        return labelModel;
    }

    public static void updateLabelData(Connection con, String labelId,
            String labelName, String labelDescription) throws Exception {
        Statement stat = con.createStatement();
        String strSql = "update COMMISSION_LABEL" + " set LABEL_NAME = '"
                + labelName + "' , LABEL_DESCRIPTION = '" + labelDescription
                + "'" + " where LABEL_ID = '" + labelId + "'";
        stat.executeUpdate(strSql);

    }

    public static void insertNewLabel(Connection con, Long labelId,
            String labelName, String labelDescription) throws Exception {
        Statement stat = con.createStatement();
        String strSql = "insert into COMMISSION_LABEL"
                + "(LABEL_ID,LABEL_NAME,LABEL_DESCRIPTION) values " + "('"
                + labelId + "','" + labelName + "','" + labelDescription + "')";
        stat.executeUpdate(strSql);
        Utility.logger.debug("The insert query is " + strSql);
    }

    public static Vector getAllLabelsDetails(Connection con, String labelId)
            throws Exception {
        Vector labelDetailsVec = new Vector();
        LabelModel labelDetailsModel = null;
        Statement stat = con.createStatement();
        String strSql = "select * from COMMISSION_LABEL_DETAIL where LABEL_ID = '"
                + labelId + "'";
        ResultSet res = stat.executeQuery(strSql);
        while (res.next()) {
            labelDetailsModel = new LabelModel(res);
            labelDetailsVec.add(labelDetailsModel);
        }
        return labelDetailsVec;
    }

    public static void updateLabelDetails(Connection con, String labelId,
            LabelModel labelModel) throws Exception {
        Statement stat = con.createStatement();
        String strSql = "update COMMISSION_LABEL_DETAIL" + " set DCM_CODE = '"
                + labelModel.getDcmCode() + "' , AMOUNT = '"
                + labelModel.getAmount() + "'" + " where LABEL_ID = '"
                + labelId + "' and LABEL_DETAIL_ID = '"
                + labelModel.getLabelDetailId() + "'";
        Utility.logger.debug("The update Query is " + strSql);
        stat.executeUpdate(strSql);
        stat.close();
    }

    public static void deleteAllLabelDetails(Connection con, String labelId) {
        String strSql = "DELETE FROM COMMISSION_LABEL_DETAIL WHERE LABEL_ID = ?";
        DBUtil.executePreparedStatment(strSql, con, labelId);
    }

    public static void deleteLableDcms(String labelId, Connection con) {
        String strSql = "DELETE FROM COMMISSION_LABEL_DETAIL WHERE DCM_CODE IN (SELECT DCM_CODE FROM TMP_DCM_CODE"
                + " WHERE COMMISSION_LABEL_DETAIL.DCM_CODE = TMP_DCM_CODE.DCM_CODE) AND LABEL_ID = ?";
        DBUtil.executePreparedStatment(strSql, con, labelId);
    }

    public static void deleteTmpDcm(Connection con) {
        String strSql = "delete  from tmp_dcm_code";
        DBUtil.executeSQL(strSql, con);
    }

    public static void insertLabelDetails(Connection con, String labelId,
            LabelModel labelModel) throws Exception {
        Statement stat = con.createStatement();
        Long labelDetailsId = Utility.getSequenceNextVal(con, "SEQ_COMMISSION_LABEL_DETAIL_ID");
        String strSql = "insert into COMMISSION_LABEL_DETAIL"
                + " (LABEL_DETAIL_ID,LABEL_ID,DCM_CODE,AMOUNT)" + " values ('"
                + labelDetailsId + "' , '" + labelId + "' , '"
                + labelModel.getDcmCode() + "' , '" + labelModel.getAmount()
                + "')";
        stat.executeUpdate(strSql);
        stat.close();
    }

    public static void insertLabelDetailsFromExcel(String labelId,
            String dcmCode, String amount, Connection con) {

        String strSql = "insert into COMMISSION_LABEL_DETAIL (LABEL_DETAIL_ID,LABEL_ID,DCM_CODE,AMOUNT) values (SEQ_COMMISSION_LABEL_DETAIL_ID.nextval , '"
                + labelId + "' , '" + dcmCode + "' , '" + amount + "')";


        DBUtil.executeSQL(strSql, con);


    }

    public static void insertLabelDetailsFromExcel(String labelId,
            String dcmCode, String amount, Statement stat) {

        String strSql = "insert into COMMISSION_LABEL_DETAIL (LABEL_DETAIL_ID,LABEL_ID,DCM_CODE,AMOUNT) values (SEQ_COMMISSION_LABEL_DETAIL_ID.nextval , '"
                + labelId + "' , '" + dcmCode + "' , '" + amount + "')";

        try {

            stat.execute(strSql);
        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    public static Vector getTmpCommissionLabel(Connection con) {
        Vector labelVec = new Vector();
        try {

            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from TMP_COMMISSION_LABEL ");
            while (res.next()) {
                labelVec.add(new LabelImportModel(res));
            }
            stat.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return labelVec;
    }

    public static void deleteTmpCommissionLabel(Connection con) {
        String deleteSql = "delete from TMP_COMMISSION_LABEL";
        DBUtil.executeSQL(deleteSql, con);
    }

    public static String[] getConnectionString(Connection con) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "SELECT * FROM COMMISSION_CONNECTION_SETTING";
        ResultSet rs = stmt.executeQuery(sqlString);
        String[] connectionStr = new String[3];
        if (rs.next()) {
            connectionStr[0] = rs.getString("CONNECTION_STRING");
            connectionStr[1] = rs.getString("DB_USER_NAME");
            connectionStr[2] = rs.getString("DB_PASSWORD");
        }
        rs.close();
        stmt.close();
        return connectionStr;
    }

    public static Boolean getCommissionPayments(Connection con, int commissionID) {
        String sqlString = "select 1 from PAYMENT_COMMISSION where COMMISSION_DETAIL_ID="
                + commissionID;
        Boolean comHasPay = DBUtil.executeSQLExistCheck(sqlString, con);

        return comHasPay;
    }

    public static Vector getCommissionChannels(Connection con,
            boolean channelflag, String channelId) throws SQLException {
        Statement stmt = con.createStatement();
        String chnlId = "";
        if (channelflag) {
            chnlId = "and COMMISSION_CHANNEL_ID=" + channelId;
        }

        String sqlString = "select * from COMMISSION_CHANNEL where 1=1 "
                + chnlId + " Order By COMMISSION_CHANNEL_NAME";
        ResultSet rs = stmt.executeQuery(sqlString);
        Vector comChannelVec = new Vector();
        Boolean comHasPay;
        while (rs.next()) {
            comChannelVec.addElement(new ChannelModel(rs));
        }

        rs.close();
        stmt.close();
        return comChannelVec;
    }

    //
    public static Vector<ChannelModel> getUserChannels(Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        String sqlString = "select Commission_user_id user_id,Commission_channel_id from commission_User_channel";
        ResultSet rs = stmt.executeQuery(sqlString);
        Vector<ChannelModel> comUserChannelMap = new Vector<ChannelModel>();
        ChannelModel cm = null;
        while (rs.next()) {
            cm = new ChannelModel();
            Integer Key = new Integer(rs.getInt("user_id"));
            String value = rs.getString("Commission_channel_id");
            cm.setChannelId(Key);
            cm.setChannelName(value);
            comUserChannelMap.addElement(cm);
        }

        rs.close();
        stmt.close();
        return comUserChannelMap;
    }

    public static void insertCommissionChannels(Connection con,
            String channelName) {
        //Ahmed Adel
        String sqlString = "insert into COMMISSION_CHANNEL VALUES(SEQ_ID_COMMISSION_CHANNEL.nextVal,?)";
        DBUtil.executePreparedStatment(sqlString, con, channelName);

    }

    public static void updateCommissionChannels(Connection con,
            String channelId, String channelName) {
        String sqlString = "update COMMISSION_CHANNEL set COMMISSION_CHANNEL_NAME= ? where COMMISSION_CHANNEL_ID= ? ";
        DBUtil.executePreparedStatment(sqlString, con, channelName, channelId);
    }

    public static void deleteUserChannels(Connection con, String userId) {
        String sqlString = "delete from COMMISSION_USER_CHANNEL where COMMISSION_USER_ID= ?";
        DBUtil.executePreparedStatment(sqlString, con, userId);
    }

    public static void insertUserChannels(Connection con, String userId,
            String channelId) {
        String sqlString = "insert Into COMMISSION_USER_CHANNEL values(? ,?)";
        DBUtil.executePreparedStatment(sqlString, con, userId, channelId);
    }

    public static String getChannelName(Connection con, String channelId) {
        String sqlString = "select COMMISSION_CHANNEL_NAME from COMMISSION_CHANNEL where COMMISSION_CHANNEL_ID="
                + channelId;
        String channelName = DBUtil.executeQuerySingleValueString(sqlString, "COMMISSION_CHANNEL_NAME", con);
        return channelName;
    }

    public static String getCommissionCategoryName(Connection con,
            String categoryId) {
        String sqlString = "select COMMISSION_TYPE_CATEGORY_NAME from COMMISSION_TYPE_CATEGORY where COMMISSION_TYPE_CATEGORY_ID=" + categoryId;
        String catName = DBUtil.executeQuerySingleValueString(sqlString, "COMMISSION_TYPE_CATEGORY_NAME", con);
        return catName;
    }
//ahmed adel

    public static void insertNewRatedFile(String fileId, String DayFrom, String MonthFrom, String YearFrom, String DayTo, String MonthTo, String YearTo, String userId) {
        String query = "INSERT INTO SDS.COMMISSION_RATED_ACTIVITY ("
                + "RATED_FILE_ID, UPDATED_IN, USER_ID, "
                + "PROCESSING_FLAG, DAY_FROM, MONTH_FROM, "
                + "YEAR_FROM, DAY_TO, MONTH_TO, "
                + "YEAR_TO,START_TIME)"
                + "VALUES (" + fileId + ",sysdate," + userId + ","
                + "1," + DayFrom + "," + MonthFrom + ","
                + "" + YearFrom + "," + DayTo + "," + MonthTo + "," + YearTo + ",sysdate)";


        DBUtil.executeSQL(query);

    }

    //ahmed adel
    public static void insertRatedFileData(String file_Id, java.sql.PreparedStatement sql, String Subscription_Id, String Access_Method_Dial, String SIM_Card_Serial_Number, String Activation_Dt, String First_Rated_Activity_Dt, String Revenue, String days, String First_Call_Cell_Id, String Governorate, String District) {
        try {
            java.sql.Date Rated = null;
            java.sql.Date Activation = null;
            if (!First_Rated_Activity_Dt.equals("?")) {
                String tempDate1[] = First_Rated_Activity_Dt.split("/");
                Rated = java.sql.Date.valueOf(tempDate1[2] + "-" + tempDate1[1] + "-" + tempDate1[0]);
            }
            if (!Activation_Dt.equals("?")) {
                String tempDate2[] = Activation_Dt.split("/");
                Activation = java.sql.Date.valueOf(tempDate2[2] + "-" + tempDate2[1] + "-" + tempDate2[0]);
            }

            sql.setInt(1, Integer.parseInt(file_Id));
            if (!Subscription_Id.equals("?")) {
                sql.setInt(2, Integer.parseInt(Subscription_Id));
            } else {
                sql.setInt(2, Integer.parseInt("0"));
            }
            sql.setString(3, Access_Method_Dial);
            if (containsOnlyNumbers(SIM_Card_Serial_Number)) {
                sql.setString(4, SIM_Card_Serial_Number);
            } else {
                sql.setNull(4, java.sql.Types.VARCHAR);
            }
            if (Activation != null) {
                sql.setDate(5, Activation);
            } else {
                sql.setNull(5, java.sql.Types.DATE);
            }
            if (Rated != null) {
                sql.setDate(6, Rated);
            } else {
                sql.setNull(6, java.sql.Types.DATE);
            }
            if (!Revenue.equals("?")) {
                sql.setFloat(7, Float.parseFloat(Revenue));
            } else {
                sql.setFloat(7, Float.parseFloat("0"));
            }
            if (!days.equals("?")) {
                sql.setInt(8, Integer.parseInt(days));
            } else {
                sql.setInt(8, Integer.parseInt("0"));
            }
            if (!First_Call_Cell_Id.equals("?")) {
                sql.setInt(9, Integer.parseInt(First_Call_Cell_Id));
            } else {
                sql.setInt(9, Integer.parseInt("0"));
            }

            sql.setString(10, Governorate);
            sql.setString(11, District);
            System.out.print("############################\n");
            System.out.print(sql.toString());
        } catch (SQLException ex) {
            Logger.getLogger(CommissionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insertErrorRatedFileData(String file_Id, java.sql.PreparedStatement sql, String Subscription_Id, String Access_Method_Dial, String SIM_Card_Serial_Number, String Activation_Dt, String First_Rated_Activity_Dt, String Revenue, String days, String First_Call_Cell_Id, String Governorate, String District, int lineNumber) {
        try {
            java.sql.Date Rated = null;
            java.sql.Date Activation = null;
            if (!First_Rated_Activity_Dt.equals("?")) {
                String tempDate1[] = First_Rated_Activity_Dt.split("/");
                Rated = java.sql.Date.valueOf(tempDate1[2] + "-" + tempDate1[1] + "-" + tempDate1[0]);
            }
            if (!Activation_Dt.equals("?")) {
                String tempDate2[] = Activation_Dt.split("/");
                Activation = java.sql.Date.valueOf(tempDate2[2] + "-" + tempDate2[1] + "-" + tempDate2[0]);
            }

            sql.setInt(1, Integer.parseInt(file_Id));
            if (!Subscription_Id.equals("?")) {
                sql.setInt(2, Integer.parseInt(Subscription_Id));
            } else {
                sql.setInt(2, Integer.parseInt("0"));
            }
            sql.setString(3, Access_Method_Dial);
            if (containsOnlyNumbers(SIM_Card_Serial_Number)) {
                sql.setString(4, SIM_Card_Serial_Number);
            } else {
                sql.setNull(4, java.sql.Types.VARCHAR);
            }
            if (Activation != null) {
                sql.setDate(5, Activation);
            } else {
                sql.setNull(5, java.sql.Types.DATE);
            }
            if (Rated != null) {
                sql.setDate(6, Rated);
            } else {
                sql.setNull(6, java.sql.Types.DATE);
            }
            if (!Revenue.equals("?")) {
                sql.setFloat(7, Float.parseFloat(Revenue));
            } else {
                sql.setFloat(7, Float.parseFloat("0"));
            }
            if (!days.equals("?")) {
                sql.setInt(8, Integer.parseInt(days));
            } else {
                sql.setInt(8, Integer.parseInt("0"));
            }
            if (!First_Call_Cell_Id.equals("?")) {
                sql.setInt(9, Integer.parseInt(First_Call_Cell_Id));
            } else {
                sql.setInt(9, Integer.parseInt("0"));
            }

            sql.setString(10, Governorate);
            sql.setString(11, District);
            sql.setInt(12, lineNumber);
            System.out.print("############################\n");
            System.out.print(sql.toString());
        } catch (SQLException ex) {
            Logger.getLogger(CommissionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Integer getPassRecordsOfZipFile(String fileId, Connection con) {

        String query = "SELECT COUNT (RATED_FILE_ID) FROM COMMISSION_RATED_ACTIVITY_DATA WHERE RATED_FILE_ID=" + fileId;
        System.out.print(query);
        return DBUtil.executeQuerySingleValueInt(query, "COUNT(RATED_FILE_ID)", con);
    }

    public static Integer getFalidRecordsOfZipFile(String fileId, Connection con) {
        String query = "SELECT COUNT (RATED_FILE_ID) FROM COMMISSION_RATED_REFUESD_DATA WHERE RATED_FILE_ID=" + fileId;
        System.out.print(query);
        return DBUtil.executeQuerySingleValueInt(query, "COUNT(RATED_FILE_ID)", con);

    }

    public static Vector<RatedFileError> getFalidSIMSNOfZipFile(String fileId, Connection con) {
        String query = "SELECT SIM_CARD_SERIAL_NUMBER,LINE_NUMBER,FIRST_RATED_ACTIVITY_DATE FROM COMMISSION_RATED_REFUESD_DATA WHERE RATED_FILE_ID=" + fileId;

        return DBUtil.executeSqlQueryMultiValue(query, RatedFileError.class, con);

    }

    public static CommissionModel getCommissionDetailsForDataViewAndDrivingPlan(Connection con, String commissionId) {
        CommissionModel comModel = null;
        Statement st = null;
        ResultSet rs = null;
        StringBuilder strBuilderQuery = new StringBuilder();
        strBuilderQuery.append(" select com.*,pln.DP_NAME,dview.DATA_VIEW_NAME ");
        strBuilderQuery.append(" from VW_COMMISSION_DETAIL com , DP_DRIVING_PLAN pln , ADH_DATA_VIEW dview");
        strBuilderQuery.append(" where com.DP_ID = pln.DP_ID");
        strBuilderQuery.append(" and com.COMMISSION_DATA_VIEW_ID = dview.DATA_VIEW_ID");
        strBuilderQuery.append(" and  com.COMMISSION_DETAIL_ID=");
        strBuilderQuery.append(commissionId);

        try {
            st = con.createStatement();
            rs = st.executeQuery(strBuilderQuery.toString());
            if (rs.next()) {
                comModel = new CommissionModel(con, rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs);
            DBUtil.close(st);
        }


        return comModel;

    }

    public static Vector<RatedFileModel> getAllRatedFiles(Connection con) {
        String query = "SELECT DISTINCT RATED_FILE_ID ,PROCESSING_FLAG, DAY_FROM, MONTH_FROM, "
                + "YEAR_FROM, DAY_TO, MONTH_TO, "
                + "YEAR_TO ,START_TIME,END_TIME FROM COMMISSION_RATED_ACTIVITY";

        return DBUtil.executeSqlQueryMultiValue(query, RatedFileModel.class, con);
    }

    public static void deleteFile(String fileId) {
        String dataquery = "DELETE FROM COMMISSION_RATED_ACTIVITY_DATA WHERE RATED_FILE_ID=" + fileId;
        String refusedquery = "DELETE FROM COMMISSION_RATED_REFUESD_DATA WHERE RATED_FILE_ID=" + fileId;
        String filequery = "DELETE FROM COMMISSION_RATED_ACTIVITY WHERE RATED_FILE_ID=" + fileId;
        DBUtil.executeSQL(dataquery);
        DBUtil.executeSQL(refusedquery);
        DBUtil.executeSQL(filequery);
    }

    public static int isfileactive(String fileId, Connection con) {
        String dataquery = "SELECT COUNT(COMMISSION_DATE) FROM COMMISSION_RATED_ACTIVITY_DATA WHERE RATED_FILE_ID=" + fileId;
        return DBUtil.executeQuerySingleValueInt(dataquery, "COUNT(COMMISSION_DATE)", con);
    }

    public static void activefile(String fileId) {
        String dataquery = "UPDATE SDS.COMMISSION_RATED_ACTIVITY"
                + " SET PROCESSING_FLAG = 3"
                + " WHERE RATED_FILE_ID=" + fileId;
        DBUtil.executeSQL(dataquery);
    }

    public static Vector getfileStatus(String fileId, Connection con) {
        String query = "SELECT RATED_FILE_ID, PROCESSING_FLAG, DAY_FROM, MONTH_FROM, "
                + "YEAR_FROM, DAY_TO, MONTH_TO, "
                + "YEAR_TO FROM COMMISSION_RATED_ACTIVITY WHERE RATED_FILE_ID=" + fileId;
        Vector<RatedFileModel> RatedFileModels = DBUtil.executeSqlQueryMultiValue(query, RatedFileModel.class, con);

        return RatedFileModels;
    }

    public static void updatefileStatus(String fileId, Connection con) {
        String query = "UPDATE COMMISSION_RATED_ACTIVITY  SET PROCESSING_FLAG =2 ,END_TIME=sysdate WHERE RATED_FILE_ID=" + fileId;
        DBUtil.executeSQL(query);
    }

    public static int isuploadingfile() {
        Connection con;
        try {
            con = Utility.getConnection();
            String query = "SELECT COUNT(RATED_FILE_ID) FROM COMMISSION_RATED_ACTIVITY WHERE PROCESSING_FLAG =1";
            return DBUtil.executeQuerySingleValueInt(query, "COUNT(RATED_FILE_ID)", con);
        } catch (SQLException ex) {
            Logger.getLogger(CommissionDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    public static HashMap<String, String> getDrivingPlanMap(Connection con, String cat_id) {

        return DBUtil.getMap(con, "select DP_ID, DP_NAME from VW_DP_DRIVING_PLAN where DP_COMMISION_CATEGORY='" + cat_id + "'");
    }

    public static boolean containsOnlyNumbers(String str) {

        //It can't contain only numbers if it's null or empty...
        if (str == null || str.length() == 0) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {

            //If we find a non-digit character we return false.
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    private CommissionDAO() {
    }
}
