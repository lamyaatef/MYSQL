/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.commission;

import com.mobinil.sds.core.system.commission.model.CommissionModel;
import com.mobinil.sds.core.system.gn.dataview.dao.DataViewDAO;
import com.mobinil.sds.core.system.gn.querybuilder.domain.QueryBuilderEngine;
import com.mobinil.sds.core.system.gn.querybuilder.dto.*;
import com.mobinil.sds.core.system.gn.querybuilder.model.FieldModel;
import com.mobinil.sds.core.system.gn.reports.dto.ReportBuilderWizardDTO;
import com.mobinil.sds.core.system.gn.reports.dto.ReportDTO;
import com.mobinil.sds.core.system.gn.reports.dto.ReportOrderByDTO;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.gn.querybuilder.QueryBuilderInterfaceKey;
import com.mobinil.sds.web.interfaces.gn.reports.ReportInterfaceKey;
import java.sql.*;
import java.util.Vector;
import oracle.jdbc.OraclePreparedStatement;

/**
 *
 * @author Gado
 */
public class CommissionIIIDAO {

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

    public static void saveCommissionParams(Connection con, Vector<ParameterFieldDTO> params, int commissionId) throws SQLException {

        Statement stat = con.createStatement();

        for (ParameterFieldDTO parameterFieldDTO : params) {

            System.out.println("parameterFieldDTO 1 " + parameterFieldDTO.getFieldSQLCash());

            System.out.println("parameterFieldDTO 1 " + parameterFieldDTO.getFieldID());



            Statement stmt = con.createStatement();

            String sqlString = "select input_param_label_text from ADH_INPUT_PARAM where input_param_id="
                    + parameterFieldDTO.getFieldID();
            ResultSet rs = stmt.executeQuery(sqlString);
            Vector fields = new Vector();
            while (rs.next()) {

                String sql = "INSERT INTO COMMISSION_PARAMS VALUES(" + commissionId + ",'" + rs.getString("input_param_label_text") + "','" + parameterFieldDTO.getFieldSQLCash() + "')";
                System.out.println("Saving Commission Params Insert Statement:" + sql);
                stat.executeUpdate(sql);
            }
            rs.close();
            stmt.close();



        }

    }

    public static Vector<CommissionReviewModel> getCommissionData(Connection con,
            int commissionID) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select commission_name ,COMMISSION_TYPE_CATEGORY.COMMISSION_TYPE_CATEGORY_NAME , commission_start_date, commission_end_date"
                + ",commission_description , commission_data_view_id , ADH_DATA_VIEW.DATA_VIEW_NAME , commission_data_view_sql , commission_channel_id , commission_creation_date , subtractID"
                + ",commission_params.LABEL_TEXT,commission_params.VALUE_TYPE    from commission_detail , commission_type_category  , adh_data_view , commission_params"
                + " where COMMISSION_DETAIL.COMMISSION_TYPE_CATEGORY_ID = COMMISSION_TYPE_CATEGORY.COMMISSION_TYPE_CATEGORY_ID"
                + " and commission_data_view_id = ADH_DATA_VIEW.DATA_VIEW_ID"
                + " and commission_Detail_id=commission_params.COMMISSION_ID"
                + " and commission_Detail_id="
                + commissionID;
        System.out.println("SQL:" + sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        Vector<CommissionReviewModel> commissionReview = new Vector<CommissionReviewModel>();
        CommissionReviewModel commissionReviewModel = null;
        while (rs.next()) {
            commissionReviewModel = new CommissionReviewModel(con, rs);

            commissionReview.add(commissionReviewModel);
        }

        rs.close();
        stmt.close();
        return commissionReview;
    }
}
