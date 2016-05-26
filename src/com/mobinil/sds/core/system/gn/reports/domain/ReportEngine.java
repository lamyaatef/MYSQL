package com.mobinil.sds.core.system.gn.reports.domain;

import java.util.Vector;
import java.sql.*;
import java.util.HashMap;


import com.mobinil.sds.core.utilities.Utility;

import com.mobinil.sds.core.system.gn.reports.dao.*;
//import com.mobinil.sds.core.system.gn.reports.dao.cmp.*;
import com.mobinil.sds.core.system.gn.reports.dto.*;
import com.mobinil.sds.core.system.gn.reports.model.*;
import com.mobinil.sds.core.system.gn.dataview.dao.*;

import com.mobinil.sds.core.system.gn.querybuilder.model.FieldModel;
import com.mobinil.sds.core.system.gn.querybuilder.model.FieldTypeModel;
import com.mobinil.sds.core.system.gn.querybuilder.dto.*;
import com.mobinil.sds.core.system.gn.querybuilder.dao.QueryBuilderDAO;
import com.mobinil.sds.core.system.gn.querybuilder.domain.QueryBuilderEngine;
import java.util.*;
import java.io.*;



/*
import com.mobinil.sds.core.system.gn.dataview.dto.*;
import com.mobinil.sds.core.system.gn.dataview.model.*;
import com.mobinil.sds.core.system.gn.dataview.dao.cmp.*;
 */
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.gn.reports.ReportInterfaceKey;
import com.mobinil.sds.web.interfaces.gn.querybuilder.QueryBuilderInterfaceKey;

public class ReportEngine {

    //Connection m_conSDSConnection ;
    HashMap dataHashMap;

    //---------------------------------------------------------------
    public ReportEngine() {
        try {
            //m_conSDSConnection = Utility.getConnection();
            dataHashMap = new HashMap();
        } catch (Exception objExp) {
            objExp.printStackTrace();
        }
    }

    //---------------------------------------------------------------
    public void debug(String place, String msg, boolean EOF) {
        if (ReportInterfaceKey.DEBUG_FLAG == true) {
            System.out.print("  ");
            if ((place != null) && (!(place.equalsIgnoreCase("")))) {
                Utility.logger.debug(place + ": ");
                Utility.logger.debug(msg);
            }
            if (EOF == true) {
                Utility.logger.debug("");
            }
        }
    }

    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    // Given a universe ID, the function returns all available dataviews
    // in that universe. One of them will be selected
    // as the base of the report
    public HashMap initializeWizard(Connection m_conSDSConnection /*int argDataViewUniverseID*/) {
        //ReportBuilderWizardDTO reportBuilderWizardDTO = null ;
        Vector vecReportsList = null;
        Vector vecGroupList = null;
        //Vector vecNonScopesList = null ;
        try {
            vecGroupList = ReportDAO.getGroups(m_conSDSConnection, true);
            vecReportsList =
                    DataViewDAO.getDataViewsListAllUniverses(m_conSDSConnection,
                    //ReportInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_REPORT ,
                    ReportInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_QUERY,
                    ReportInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_ACTIVE);
            /*
            vecNonScopesList = 
            DataViewDAO.getDataViewsListAllUniverses ( m_conSDSConnection,
            ReportInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_QUERY ,
            ReportInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_ACTIVE) ;
             */
            dataHashMap.put(ReportInterfaceKey.HASHMAP_KEY_COLLECTION_GROUP_REPORTS, vecGroupList);
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecReportsList);
            //dataHashMap.put (InterfaceKey.HASHMAP_KEY_COLLECTION, vecReportsList);

        } catch (Exception objExp) {
            objExp.printStackTrace();
        }
        return dataHashMap;
    }

    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    // Given the data of a report (name...etc) and the dataview on which
    // it is based, the function saves the report in the ADH_REPORT.
    // It also returns the ID of the newly saved report.
    public int saveReport(
            Connection m_conSDSConnection,
            //HashMap paramHashMap
            String strReportName,
            String strReportDesc,
            int nReportDataViewID,
            Vector groupList) {
        Long lNextVal = null;
        //Vw_adh_reportHome vw_adh_reportHome = null ;
        Vector vecSelectList = null;
        Vector vecOrderByList = null;
        int returnResult = 0;
        Utility.logger.debug("hhhhhh" + new Long(nReportDataViewID) + "gggggg" + nReportDataViewID);
        try {
            debug("saveReport", "------->", true);
            /*String strReportName = (String) paramHashMap.get (ReportInterfaceKey.PARAM_SAVE_REPORT_NAME) ; 
            String strReportDesc = (String) paramHashMap.get (ReportInterfaceKey.PARAM_SAVE_REPORT_DESC) ; 
            int nReportDataViewID = new Integer ( (String) paramHashMap.get(ReportInterfaceKey.PARAM_SAVE_REPORT_DATAVIEW_ID) ).intValue() ;*/

            if (reportNameExists(m_conSDSConnection, strReportName) == false) {
                //vw_adh_reportHome = (Vw_adh_reportHome)
                //      Utility.getEJBHome("Vw_adh_report",
                //      "com.mobinil.sds.core.system.gn.reports.dao.cmp.Vw_adh_reportHome");
                //PortableRemoteObject.narrow(context.lookup("Vw_adh_report"), Vw_adh_reportHome.class);
                lNextVal = Utility.getSequenceNextVal(m_conSDSConnection, "SEQ_ADH_REPORT_ID");

                ReportDAO.insertVwAdhReport(m_conSDSConnection,
                        lNextVal, //java.lang.Long report_id, 
                        strReportName, //java.lang.String report_name, 
                        strReportDesc, //java.lang.String report_description, 
                        new Long(nReportDataViewID) //java.lang.Long data_view_id 
                        );

                int reportID = lNextVal.intValue();

                ReportDAO.insertReportToGroup(m_conSDSConnection, reportID, groupList);
                debug("saveReport", "added new report. ID: " + reportID, true);

                // vecSelectList = constructReportSelectList ( paramHashMap ) ;
                vecSelectList = DataViewDAO.getDataViewFieldsListByDisplayType(m_conSDSConnection, nReportDataViewID, QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED);
                if (vecSelectList.size() > 0) {
                    saveReportSelectList(m_conSDSConnection, reportID, vecSelectList);
                }

                //vecOrderByList = constructReportOrderByList ( paramHashMap ) ;
                vecOrderByList = DataViewDAO.getOrderByListSimplified(m_conSDSConnection, nReportDataViewID);
                if (vecOrderByList.size() > 0) {
                    saveReportOrderByList(m_conSDSConnection, reportID, vecOrderByList);
                }

                returnResult = lNextVal.intValue();
            } else {
                returnResult = ReportInterfaceKey.ERR_NUM_UNIQUE_REPORT;
            }

            //dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, ReportInterfaceKey.ERR_UNIQUE_REPORT );
            //dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, ReportInterfaceKey.ERR_SAVE_REPORT );
        } catch (Exception objExp) {
            objExp.printStackTrace();
        }

        return returnResult;
    }

    public int savePersonReports(
            Connection m_conSDSConnection,
            String strPersonID,
            String strGroupID) {
        Long lNextVal = null;
        int reportSize = 0;
        int returnResult = 0;
        try {

            Vector allGroups = ReportDAO.getGroupDetails(m_conSDSConnection, true, strGroupID);
            for (int i = 0; i < allGroups.size(); i++) {
                GroupDTO gdto = (GroupDTO) allGroups.elementAt(i);
                Vector reports = gdto.getGroupReport();
                reportSize = reports.size();
                for (int j = 0; j < reportSize; j++) {
                    ReportModel rm = (ReportModel) reports.elementAt(j);
                    String strReportID = rm.getReportId();

                    lNextVal = Utility.getSequenceNextVal(m_conSDSConnection, "SEQ_ADH_REPORT_ID");
                    String insertStmt = "insert into ADH_PERSON_REPORT(PERSON_REPORT_ID,PERSON_ID,REPORT_ID,GROUP_ID)"
                            + "values (" + lNextVal + "," + strPersonID + "," + strReportID + "," + strGroupID + ")";
                    //Utility.logger.debug(insertStmt);                          
                    Statement stmt = m_conSDSConnection.createStatement();
                    stmt.executeUpdate(insertStmt);

                }


            }

            if (reportSize != 0) {
                returnResult = lNextVal.intValue();
            }
        } catch (Exception objExp) {
            objExp.printStackTrace();
        }

        return returnResult;
    }

    public void deletePersonReports(
            Connection m_conSDSConnection,
            String strPersonID) {
        try {
            String deleteStmt = "delete from ADH_PERSON_REPORT where PERSON_ID = '" + strPersonID + "'";
            //Utility.logger.debug(deleteStmt);  
            Statement stmt = m_conSDSConnection.createStatement();
            stmt.executeUpdate(deleteStmt);
        } catch (Exception objExp) {
            objExp.printStackTrace();
        }

    }

    public Vector getAllPersonReports(
            Connection m_conSDSConnection) {
        Vector vecPersonReport = new Vector();
        try {
            String getStmt = "select * from ADH_PERSON_REPORT";
            Statement stmt = m_conSDSConnection.createStatement();
            ResultSet res = stmt.executeQuery(getStmt);
            while (res.next()) {
                vecPersonReport.add(new PersonReportModel(res));
            }

        } catch (Exception objExp) {
            objExp.printStackTrace();
        }
        return vecPersonReport;
    }
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    // Given the data of a report (name...etc) and the dataview on which
    // it is based, the function updates the report in the ADH_REPORT.

    public void updateReport(
            Connection m_conSDSConnection,
            int nReportID,
            String strReportName,
            String strReportDesc,
            int nReportDataViewID) {
        Long lNextVal = null;
        //Vw_adh_reportHome vw_adh_reportHome = null ;
        //int     returnResult = 0 ;
        try {
            //debug("updateReport", "------->", true );


            //vw_adh_reportHome = (Vw_adh_reportHome)
            //      Utility.getEJBHome("Vw_adh_report",
            //      "com.mobinil.sds.core.system.gn.reports.dao.cmp.Vw_adh_reportHome");
            //PortableRemoteObject.narrow(context.lookup("Vw_adh_report"), Vw_adh_reportHome.class);

            //Vw_adh_report vw_adh_report = vw_adh_reportHome.findByPrimaryKey (new Long (nReportID) ) ;

            ReportModel newReportModel = ReportDAO.getVwAdhReport(m_conSDSConnection, nReportID);

            ReportModel reportModelData = new ReportModel();
            reportModelData.setReportId(nReportID + "");

            //Long oldDataViewID = vw_adh_report.getData_view_id() ;
            Long oldDataViewID = new Long(newReportModel.getDataviewId());
            debug(" oldDataViewID ", oldDataViewID.intValue() + " nReportDataViewID " + nReportDataViewID, true);
            if (oldDataViewID.intValue() == nReportDataViewID) {
                //vw_adh_report.setReportData (strReportName, strReportDesc, new Long ( nReportDataViewID ));
                reportModelData.setReportName(strReportName);
                reportModelData.setReportDescription(strReportDesc);
                reportModelData.setDataviewId(nReportDataViewID + "");
            } else {
                // delete all records related to the old data view ID from the selected fields table and the orderby table
                //Vw_adh_report_selectHome vw_adh_report_selectHome = (Vw_adh_report_selectHome)
                //      Utility.getEJBHome("Vw_adh_report_select",
                //      "com.mobinil.sds.core.system.gn.reports.dao.cmp.Vw_adh_report_selectHome");
                Vector vecSelectIDsList = getReportSelectIDs(m_conSDSConnection, nReportID);

                for (int i = 0; i < vecSelectIDsList.size(); i++) {
                    //Vw_adh_report_select vw_adh_report_select = 
                    //    vw_adh_report_selectHome.findByPrimaryKey ( (Long) vecSelectIDsList.elementAt(i) ) ;
                    //vw_adh_report_select.remove();
                    ReportDAO.deleteVwAdhReportSelectBy(m_conSDSConnection, (Long) vecSelectIDsList.elementAt(i));
                }

                //Vw_adh_report_order_byHome vw_adh_report_order_byHome = (Vw_adh_report_order_byHome)
                //      Utility.getEJBHome("Vw_adh_report_order_by",
                //      "com.mobinil.sds.core.system.gn.reports.dao.cmp.Vw_adh_report_order_byHome");
                Vector vecOrderByIDsList = getReportOrderByIDs(m_conSDSConnection, nReportID);
                for (int i = 0; i < vecOrderByIDsList.size(); i++) {
                    //Vw_adh_report_order_byPK vw_adh_report_order_byPK = new Vw_adh_report_order_byPK ( (Long) vecOrderByIDsList.elementAt(i) ) ;
                    //Vw_adh_report_order_by vw_adh_report_order_by = 
                    //    vw_adh_report_order_byHome.findByPrimaryKey ( vw_adh_report_order_byPK ) ;
                    //vw_adh_report_order_by.remove();
                    ReportDAO.deleteVwAdhReportOrderBy(m_conSDSConnection, (Long) vecOrderByIDsList.elementAt(i));
                }
                // set the records related to the new data view ID in the selected fields table and the orderby table
                Vector vecSelectList = DataViewDAO.getDataViewFieldsListByDisplayType(m_conSDSConnection, nReportDataViewID, QueryBuilderInterfaceKey.DISPLAY_TYPE_DISPLAYED);
                if (vecSelectList.size() > 0) {
                    saveReportSelectList(m_conSDSConnection, nReportID, vecSelectList);
                }
                Vector vecOrderByList = DataViewDAO.getOrderByListSimplified(m_conSDSConnection, nReportDataViewID);
                if (vecOrderByList.size() > 0) {
                    saveReportOrderByList(m_conSDSConnection, nReportID, vecOrderByList);
                }

                // finally update the report row itself 
                //vw_adh_report.setReportData (strReportName, strReportDesc, new Long ( nReportDataViewID ));
                reportModelData.setReportName(strReportName);
                reportModelData.setReportDescription(strReportDesc);
                reportModelData.setDataviewId(nReportDataViewID + "");


            }
            //returnResult = nReportID ;
            ReportDAO.updateReport(m_conSDSConnection, reportModelData);
            debug("updateReport", "updated report. ID: " + nReportID, true);
        } catch (Exception objExp) {
            objExp.printStackTrace();
        }
        return;
    }

    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    // Given a report ID, the function retrieves all the fields of the
    // dataview on which the report is based. Also, the fields that have 
    // been customized will also be retrieved.
    // The fields and their order by attributes are saved in 
    // reportBuilderWizardDTO which is returned to the user to be customized 
    // later.
    public ReportBuilderWizardDTO loadReport(Connection m_conSDSConnection, int nReportID) {
        ReportBuilderWizardDTO reportBuilderWizardDTO = null;
        ReportDTO reportDTO = null;

        try {
            reportBuilderWizardDTO = new ReportBuilderWizardDTO();

            reportDTO = getReportData(m_conSDSConnection, nReportID);
            int nDataViewID = reportDTO.getReportDataViewID();

            reportBuilderWizardDTO.setDetailedDataView(
                    DataViewDAO.getDataViewDetails(m_conSDSConnection, nDataViewID));

            reportBuilderWizardDTO.getDetailedDataView().setDataViewFields(
                    DataViewDAO.getDataViewFieldsListByDisplayType(m_conSDSConnection, nDataViewID, ReportInterfaceKey.DISPLAY_TYPE_DISPLAYED));

            reportBuilderWizardDTO.setReportFields(DataViewDAO.getDataViewFieldsListByDisplayType(
                    m_conSDSConnection, nDataViewID, ReportInterfaceKey.DISPLAY_TYPE_DISPLAYED));
            reportBuilderWizardDTO.setReportOrderBy(DataViewDAO.getOrderByListSimplified(m_conSDSConnection, nDataViewID));

            reportDTO.setReportSelectedFields(getReportSelectedFields(m_conSDSConnection, nReportID));
            reportDTO.setReportSelectedOrderBy(getReportSelectedOrderBy(m_conSDSConnection, nReportID));

            reportBuilderWizardDTO.setReport(reportDTO);
            //commented by hagry to try to solve the bug about parameters not get to be displayed in the report parge

            reportBuilderWizardDTO.setReportParameters(getReportParameters(m_conSDSConnection, nReportID));


            reportBuilderWizardDTO.setAvailableDataViews(
                    DataViewDAO.getDataViewsListAllUniverses(m_conSDSConnection,
                    //ReportInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_REPORT ,
                    ReportInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_QUERY,
                    ReportInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_ACTIVE));


            //debug ( "loadReport", "loaded ReportName: " + reportBuilderWizardDTO.getReport().getReportName(), true) ;


            dataHashMap.put(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, reportBuilderWizardDTO);
        } catch (Exception objExp) {
            objExp.printStackTrace();
        }
        return reportBuilderWizardDTO;
    }

    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    private Vector constructReportSelectList(HashMap paramHashMap) {
        //int     nNumParam ;
        //String  fieldID ;
        Vector vecSelectList = null;

        try {
            vecSelectList = new Vector();
            // --------------------- constructing select list -----------------------

            //nNumParam = new Integer ( (String) paramHashMap.get (ReportInterfaceKey.PARAM_SAVE_CUSTOMIZED_REPORT_SELECT_NUM) ) ;


            String[] arrSelect = null;
            Object objSelect = paramHashMap.get(ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_SELECT_LIST);
            if (objSelect == null) // if the object is null
            {
                arrSelect = new String[0];  // initialize with empty array
            } else if (!(objSelect instanceof String)) // if the object is a genuine array
            {
                //Utility.logger.debug("class name = "+ o.getClass().getName());

                arrSelect = (String[]) objSelect;
                //for (int i = 0;i<applied_user_warnings.length;i++)
                //  Utility.logger.debug("warning " + applied_user_warnings[i]);

            } else if (objSelect instanceof String) // if the object is just a string
            {
                arrSelect = new String[1];
                arrSelect[0] = (String) objSelect;
            }

            for (int i = 0; i < arrSelect.length; i++) {
                FieldModel fieldModel = new FieldModel();
                fieldModel.setFieldID(new Integer((arrSelect[i])).intValue());
                vecSelectList.addElement(fieldModel);
            }
        } catch (Exception objExp) {
            objExp.printStackTrace();
        }
        return vecSelectList;
    }

    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    public Vector constructReportParamList(HashMap paramHashMap) {
        Vector vecReportParameterList = null;
        Integer nNumParam = null;
        int nParamType;
        String paramValue;
        ResultSet rst;
        PreparedStatement stmt;
        String strQuery;
        ParameterFieldDTO parameterFieldDTO = null;

        try {
            // --------------------- constructing param list -----------------------
            vecReportParameterList = new Vector();
            nNumParam = new Integer((String) paramHashMap.get(ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAMS_NUM));
            for (int i = 0; i < nNumParam.intValue(); i++) {
                parameterFieldDTO = new ParameterFieldDTO();
                parameterFieldDTO.setFieldID(new Integer((String) paramHashMap.get(ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_ID + i)).intValue());
                nParamType = new Integer((String) paramHashMap.get(ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_TYPE + i)).intValue();
                paramValue = (String) paramHashMap.get(ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_VALUE + i);
                parameterFieldDTO.setValueType(nParamType);

                Utility.logger.debug("nParamType = " + nParamType);
                Utility.logger.debug("param value = " + paramValue);


                if (nParamType == ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_TYPE_NUMERIC) {
                    parameterFieldDTO.setFieldSQLCash(paramValue);
                } else if (nParamType == ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_TYPE_TEXT) {
                    parameterFieldDTO.setFieldSQLCash("\'" + paramValue + "\'");
                } else if (nParamType == ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_TYPE_DATE) {
                    parameterFieldDTO.setFieldSQLCash("to_date ( \'"
                            + paramValue + "\', \'"
                            + ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_TYPE_DATE_FORMATTING
                            + "\')  ");
                }

                Utility.logger.debug(parameterFieldDTO.getFieldSQLCash());

                vecReportParameterList.addElement(parameterFieldDTO);
            }
        } catch (Exception objExp) {
            objExp.printStackTrace();
        }
        return vecReportParameterList;
    }

    //---------------------------------------------------------------
    private void saveReportSelectList(Connection m_conSDSConnection, int reportID, Vector vecSelectList) {
        int fieldID;
        //Vw_adh_report_selectHome vw_adh_report_selectHome = null ;
        Long lNextVal;

        try {
            // --------------------- saving select list -----------------------
            //nNumParam = new Integer ( (String) paramHashMap.get (ReportInterfaceKey.PARAM_CUSTOMIZE_REPORT_SELECT_NUM) ) ;
            //if 
            for (int i = 0; i < vecSelectList.size(); i++) {

                fieldID = ((FieldModel) vecSelectList.elementAt(i)).getFieldID();

                //vw_adh_report_selectHome = (Vw_adh_report_selectHome)
                //      Utility.getEJBHome("Vw_adh_report_select",
                //      "com.mobinil.sds.core.system.gn.reports.dao.cmp.Vw_adh_report_selectHome");
                lNextVal = Utility.getSequenceNextVal(m_conSDSConnection, "SEQ_ADH_REPORT_SELECT_ID");

                /*Vw_adh_report_select vw_adh_report_select = vw_adh_report_selectHome.create(
                lNextVal,
                new Long (reportID) ,
                new Long (fieldID)
                );*/
                ReportDAO.insertVwAdhReportSelect(m_conSDSConnection,
                        lNextVal,
                        new Long(reportID),
                        new Long(fieldID));
                debug("saveReportSelectList", "added new report select ID: " + lNextVal
                        + " for report " + reportID, true);

            }
        } catch (Exception objExp) {
            objExp.printStackTrace();
        }
    }

    //---------------------------------------------------------------
    private Vector constructReportOrderByList(HashMap paramHashMap) {
        Integer nNumParam;
        String fieldID;
        String orderByType;
        Vector vecOrderByList = null;

        try {
            vecOrderByList = new Vector();
            // --------------------- constructing order by list -----------------------
			/*nNumParam = new Integer ( (String) paramHashMap.get (ReportInterfaceKey.PARAM_SAVE_CUSTOMIZED_REPORT_ORDER_BY_NUM) ) ;
            for (int i=0 ; i < nNumParam.intValue() ; i++)
            {
            fieldID       = new String ( ReportInterfaceKey.PARAM_SAVE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ID + i ) ;
            orderByType   = new String ( ReportInterfaceKey.PARAM_SAVE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ORDER_TYPE + i ) ;
            
            ReportOrderByDTO reportOrderByDTO = new ReportOrderByDTO () ;
            reportOrderByDTO.setOrderByFieldID (new Integer ( (String) paramHashMap.get (fieldID) ).intValue() ) ;
            reportOrderByDTO.setOrderByType ( (String) paramHashMap.get (orderByType) ) ;
            vecOrderByList.addElement(reportOrderByDTO);
            }*/

            String[] arrFieldIDs = null;
            Object objFieldIDs = paramHashMap.get(ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ID_LIST);
            if (objFieldIDs == null) // if the object is null
            {
                arrFieldIDs = new String[0];  // initialize with empty array
            } else if (!(objFieldIDs instanceof String)) // if the object is a genuine array
            {
                //Utility.logger.debug("class name = "+ o.getClass().getName());

                arrFieldIDs = (String[]) objFieldIDs;
                //for (int i = 0;i<applied_user_warnings.length;i++)
                //  Utility.logger.debug("warning " + applied_user_warnings[i]);

            } else if (objFieldIDs instanceof String) // if the object is just a string
            {
                arrFieldIDs = new String[1];
                arrFieldIDs[0] = (String) objFieldIDs;
            }


            String[] arrFieldTypes = null;
            Object objFieldTypes = paramHashMap.get(ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_ORDER_BY_FIELD_ORDER_TYPE_LIST);
            if (objFieldTypes == null) // if the object is null
            {
                arrFieldTypes = new String[0];  // initialize with empty array
            } else if (!(objFieldTypes instanceof String)) // if the object is a genuine array
            {
                //Utility.logger.debug("class name = "+ o.getClass().getName());

                arrFieldTypes = (String[]) objFieldTypes;
                //for (int i = 0;i<applied_user_warnings.length;i++)
                //  Utility.logger.debug("warning " + applied_user_warnings[i]);

            } else if (objFieldTypes instanceof String) // if the object is just a string
            {
                arrFieldTypes = new String[1];
                arrFieldTypes[0] = (String) objFieldTypes;
            }



            for (int i = 0; i < arrFieldIDs.length; i++) {
                ReportOrderByDTO reportOrderByDTO = new ReportOrderByDTO();
                reportOrderByDTO.setOrderByFieldID(new Integer(arrFieldIDs[i]).intValue());
                reportOrderByDTO.setOrderByType(arrFieldTypes[i]);
                vecOrderByList.addElement(reportOrderByDTO);
            }
        } catch (Exception objExp) {
            objExp.printStackTrace();
        }
        return vecOrderByList;
    }

    //---------------------------------------------------------------
    private void saveReportOrderByList(Connection m_conSDSConnection, int reportID, Vector vecOrderByList) {
        int fieldID;
        String fieldOrderByType;
        Long lNextVal = null;
        try {
            // --------------------- saving order by list -----------------------
            //nNumParam = new Integer ( (String) paramHashMap.get (ReportInterfaceKey.PARAM_CUSTOMIZE_REPORT_SELECT_NUM) ) ;
            //if 
            for (int i = 0; i < vecOrderByList.size(); i++) {
                fieldID = ((ReportOrderByDTO) vecOrderByList.elementAt(i)).getOrderByFieldID();
                fieldOrderByType = ((ReportOrderByDTO) vecOrderByList.elementAt(i)).getOrderByType();

                //Vw_adh_report_order_byHome vw_adh_report_order_byHome = (Vw_adh_report_order_byHome)
                //      Utility.getEJBHome("Vw_adh_report_order_by",
                //      "com.mobinil.sds.core.system.gn.reports.dao.cmp.Vw_adh_report_order_byHome");

                lNextVal = Utility.getSequenceNextVal(m_conSDSConnection, "SEQ_ADH_REPORT_ORDER_BY_ID");

                /*Vw_adh_report_order_by vw_adh_report_order_by = 
                vw_adh_report_order_byHome.create( 
                lNextVal, //java.lang.Long report_order_by_id, 
                new Long ( reportID ), //java.lang.Long report_id, 
                new Long ( fieldID ), //java.lang.Long field_id, 
                fieldOrderByType, //java.lang.String report_order_by_type, 
                new Long ( i ) //java.lang.Long report_order_by_place 
                );*/

                ReportDAO.insertVwAdhReportOrderBy(m_conSDSConnection,
                        lNextVal, //java.lang.Long report_order_by_id, 
                        new Long(reportID), //java.lang.Long report_id, 
                        new Long(fieldID), //java.lang.Long field_id, 
                        fieldOrderByType, //java.lang.String report_order_by_type, 
                        new Long(i) //java.lang.Long report_order_by_place 
                        );
                debug("saveReportOrderByList", "added new report orderby ID: " + lNextVal
                        + " for report " + reportID, true);

            }
        } catch (Exception objExp) {
            objExp.printStackTrace();
        }
    }


    /*
    //---------------------------------------------------------------
    // Given a report ID, the function reads all the keys sent from the client
    // to determine the subset of selected fields and subset of order by clause.
    // The newly constructed selected fields and order by clause are saved 
    // in the tables.
    public String saveCustomizedReport ( HashMap paramHashMap )
    {
    String  strErrorMessage = null ;
    int     reportID = 0 ;
    Vector  vecSelectList ;
    Vector  vecOrderByList ;
    //ReportBuilderWizardDTO reportBuilderWizardDTO = null ;
    //ReportDTO reportDTO = null ;
    
    try
    {
    //reportID = new Integer ( (String) paramHashMap.get (ReportInterfaceKey.PARAM_SAVE_REPORT_ID) ).intValue() ;
    
    vecSelectList = constructReportSelectList ( paramHashMap ) ;
    if ( vecSelectList.size() > 0 )
    saveReportSelectList ( reportID, vecSelectList ) ;
    
    vecOrderByList = constructReportOrderByList ( paramHashMap ) ;
    if ( vecOrderByList.size() > 0 )
    saveReportOrderByList ( reportID, vecOrderByList ) ;
    }
    catch(Exception objExp)
    {
    objExp.printStackTrace();
    }
    return strErrorMessage ;
    }
     */
    //---------------------------------------------------------------
    // Given a report ID, the function reads all the keys sent from the client
    // to determine the subset of selected fields and subset of order by clause.
    // The newly constructed selected fields and order by clause are saved 
    // in the tables.
    public String updateCustomizedReport(Connection m_conSDSConnection, HashMap paramHashMap) {
        String strErrorMessage = null;
        Vector vecSelectList;
        Vector vecOrderByList;
        int nReportID = 0;
        try {
            nReportID = new Integer((String) paramHashMap.get(ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_ID)).intValue();

            // delete all records related to the old data view ID from the selected fields table and the orderby table
            //Vw_adh_report_selectHome vw_adh_report_selectHome = (Vw_adh_report_selectHome)
            //      Utility.getEJBHome("Vw_adh_report_select",
            //      "com.mobinil.sds.core.system.gn.reports.dao.cmp.Vw_adh_report_selectHome");
            Vector vecSelectIDsList = getReportSelectIDs(m_conSDSConnection, nReportID);

            for (int i = 0; i < vecSelectIDsList.size(); i++) {
                //Vw_adh_report_select vw_adh_report_select = 
                //    vw_adh_report_selectHome.findByPrimaryKey ( (Long) vecSelectIDsList.elementAt(i) ) ;
                //vw_adh_report_select.remove();
                ReportDAO.deleteVwAdhReportSelectBy(m_conSDSConnection, (Long) vecSelectIDsList.elementAt(i));
            }

            //Vw_adh_report_order_byHome vw_adh_report_order_byHome = (Vw_adh_report_order_byHome)
            //      Utility.getEJBHome("Vw_adh_report_order_by",
            //      "com.mobinil.sds.core.system.gn.reports.dao.cmp.Vw_adh_report_order_byHome");
            Vector vecOrderByIDsList = getReportOrderByIDs(m_conSDSConnection, nReportID);
            for (int i = 0; i < vecOrderByIDsList.size(); i++) {
                //Vw_adh_report_order_byPK vw_adh_report_order_byPK = new Vw_adh_report_order_byPK ( (Long) vecOrderByIDsList.elementAt(i) ) ;
                //Vw_adh_report_order_by vw_adh_report_order_by = 
                //    vw_adh_report_order_byHome.findByPrimaryKey ( vw_adh_report_order_byPK ) ;
                //vw_adh_report_order_by.remove();
                ReportDAO.deleteVwAdhReportOrderBy(m_conSDSConnection, (Long) vecOrderByIDsList.elementAt(i));
            }

            // set the new values obtained from the user in the selected fields table and the orderby table
            vecSelectList = constructReportSelectList(paramHashMap);
            if (vecSelectList.size() > 0) {
                saveReportSelectList(m_conSDSConnection, nReportID, vecSelectList);
            }

            vecOrderByList = constructReportOrderByList(paramHashMap);
            if (vecOrderByList.size() > 0) {
                saveReportOrderByList(m_conSDSConnection, nReportID, vecOrderByList);
            }
        } catch (Exception objExp) {
            objExp.printStackTrace();
        }
        return strErrorMessage;
    }

    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    // Given a report ID, the function retrieves 
    public QueryViewerDTO previewReport(
            Connection m_conSDSConnection,
            ReportDTO reportDTO,
            Vector vecReportParameterList,
            int nPageNum,
            int nRowsPerPage,
            String userID) {


        int dataViewID;
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

            dataViewID = reportDTO.getReportDataViewID();
            queryBuilderEngine = new QueryBuilderEngine();
            queryViewerDTO = new QueryViewerDTO();
            queryViewerDTO.setM_dataViewName(reportDTO.getReportName());
            queryDTO = queryBuilderEngine.loadQueryDTO(m_conSDSConnection, dataViewID, vecReportParameterList);

            queryDTO.setRunUserID(userID);

            // synchronize between the original query 
            // and the newly selected fields
            vecReportSelectList = reportDTO.getReportSelectedFields();
            if (vecReportSelectList.size() != 0) {
                vecQueryDisplayedFields = queryDTO.getDisplayedFields();
                newDisplayedFields = new Vector();
                for (int i = 0; i < vecQueryDisplayedFields.size(); i++) {
                    boolean found = false;
                    //debug ("previewReport", "queryField ID: " + ((FieldDTO) vecQueryDisplayedFields.elementAt(i)).getFieldID() , true);
                    for (int j = 0; (j < vecReportSelectList.size()) && (found == false); j++) {


                        FieldDTO field = (FieldDTO) vecQueryDisplayedFields.elementAt(i);

                        /*
                        System.out.println("field Number  " +i );
                        System.out.println("field Type " + field.getFieldType().getFieldTypeName() +"   type id="+ field.getFieldType().getFieldTypeID());
                        System.out.println("field id = "+ field.getFieldID());
                        System.out.println("field sql cach "+ field.getFieldSQLCash());
                         */

                        //  Utility.logger.debug("SS Field ID+ "+((FieldDTO) vecQueryDisplayedFields.elementAt(i)).getFieldID());

                        if ((((FieldDTO) vecQueryDisplayedFields.elementAt(i)).getFieldID()
                                == ((FieldModel) vecReportSelectList.elementAt(j)).getFieldID())
                                || (((FieldDTO) vecQueryDisplayedFields.elementAt(i)).getFieldID()
                                == ((FieldModel) vecReportSelectList.elementAt(j)).getFieldRFObject())) {
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
            vecReportOrderByList = reportDTO.getReportSelectedOrderBy();
            if (vecReportOrderByList.size() != 0) {
                newOrderByFields = new Vector();
                for (int i = 0; i < vecReportOrderByList.size(); i++) {
                    boolean found = false;
                    for (int j = 0; (j < vecQueryDisplayedFields.size()) && (found == false); j++) {
                        //debug ("previewReport", "comparing report field ID: " +
                        //( (ReportOrderByDTO) vecReportOrderByList.elementAt(i) ).getOrderByFieldID() +
                        //" with selected field ID: " + ((FieldDTO) vecQueryDisplayedFields.elementAt(j)).getFieldID() ) ;
                        if (((ReportOrderByDTO) vecReportOrderByList.elementAt(i)).getOrderByFieldID()
                                == ((FieldDTO) vecQueryDisplayedFields.elementAt(j)).getFieldID()) {
                            //debug ("previewReport", " fields are equal") ;
                            OrderByDTO orderByDTO = new OrderByDTO();
                            orderByDTO.setOrderType(((ReportOrderByDTO) vecReportOrderByList.elementAt(i)).getOrderByType());
                            orderByDTO.setFieldDTO((FieldDTO) vecQueryDisplayedFields.elementAt(i));
                            newOrderByFields.add(orderByDTO);
                            found = true;
                        }
                    }
                }
                queryDTO.setOrderBy(newOrderByFields);
            }

            // insert parameter values 
            vecQueryParameterList = queryDTO.getParametersFields();
            //debug ("previewKPI",  " vecQueryParameterList.size(): "+vecQueryParameterList.size() +
            //          " vecKPIParameterList.size(): "+vecKPIParameterList.size(), true) ;

            //        Utility.logger.debug("vecQueryParameterList size = "+ vecQueryParameterList.size());

            for (int i = 0; i < vecQueryParameterList.size(); i++) {
                /*
                ParameterFieldDTO parameterFieldDTO = (ParameterFieldDTO) vecKPIParameterList.elementAt(i) ;
                if ( parameterFieldDTO == null )
                {
                parameterFieldDTO = new ParameterFieldDTO () ;
                vecKPIParameterList.addElement(parameterFieldDTO);
                }*/

                for (int j = 0; j < newDisplayedFields.size(); j++) {

                    FieldDTO field = (FieldDTO) newDisplayedFields.elementAt(j);
                    if (field.getFieldType().getFieldTypeID() == 5) {
                        // System.out.println("$$$$$$$$$$$44");
                        // System.out.println(field.getFieldID());
                        //System.out.println("param sql"+((ParameterFieldDTO) vecReportParameterList.elementAt(i) ).getFieldSQLCash());
                        //System.out.println(((ParameterFieldDTO) vecReportParameterList.elementAt(i) ).getFieldID());
                    }

                }


                // assumption here is that both parameter lists are in the same order 
                ((ParameterFieldDTO) vecQueryParameterList.elementAt(i)).setFieldSQLCash(
                        ((ParameterFieldDTO) vecReportParameterList.elementAt(i)).getFieldSQLCash());

                //Utility.logger.debug("DD param value : " +( (ParameterFieldDTO) vecReportParameterList.elementAt(i) ).getFieldSQLCash());

                ParameterFieldDTO originalParam = (ParameterFieldDTO) vecQueryParameterList.elementAt(i);


                Vector conditionElements = queryDTO.getQueryWhere().getConditionElements();
                int termType;
                FieldToFieldTermDTO fieldToFieldTermDTO = null;
                FieldtoDataViewTermDTO fieldtoDataViewTermDTO = null;

                for (int j = 0; j < conditionElements.size(); j++) {
                    ConditionElement conditionElement =
                            (ConditionElement) conditionElements.elementAt(j);
                    termType = conditionElement.getConditionElementType().getConditionElementTypeID();


                    if (termType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM) {

                        Term term = (Term) conditionElements.elementAt(j);
                        termType = term.getTermType().getTermTypeID();
                        if (termType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TYPE) {
                            //Utility.logger.debug ("--->FIELD_TO_FIELD_TYPE") ;
                            fieldToFieldTermDTO = (FieldToFieldTermDTO) conditionElements.elementAt(j);

                            if (originalParam.getFieldID() == fieldToFieldTermDTO.get1stOperandFieldID()) {
                                fieldToFieldTermDTO.set1stOperandFieldSQLCache(originalParam.getFieldSQLCash());
                            } else if (originalParam.getFieldID() == fieldToFieldTermDTO.get2ndOperandFieldID()) {
                                fieldToFieldTermDTO.set2ndOperandFieldSQLCache(originalParam.getFieldSQLCash());
                            }

                            /*if (fieldToFieldTermDTO.getOperatorName().equalsIgnoreCase(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_OPERATOR_LEFT_JOIN))
                            {
                            fieldToFieldTermDTO.set1stOperandFieldSQLCache( new String ( fieldToFieldTermDTO.get1stOperandFieldSQLCache() + " (+) " ) );
                            }*/


                            // Utility.logger.debug("field 1 id = " + fieldToFieldTermDTO.get1stOperandFieldID());
                            //  Utility.logger.debug("field 2 id = " + fieldToFieldTermDTO.get2ndOperandFieldID());


                            //strSQL.append ( fieldToFieldTermDTO.get1stOperandFieldSQLCache() + " ");
                            //strSQL.append ( fieldToFieldTermDTO.getOperatorSQL() + " ");
                            //strSQL.append ( fieldToFieldTermDTO.get2ndOperandFieldSQLCache() + " ");
                        } else if (termType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TYPE) {
                            //Utility.logger.debug ("--->FIELD_TO_DATAVIEW_TYPE") ;
                            fieldtoDataViewTermDTO = (FieldtoDataViewTermDTO) conditionElements.elementAt(j);
                            //  Utility.logger.debug("field 1 id = " + fieldtoDataViewTermDTO.get1stOperandFieldID());

                            if (originalParam.getFieldID() == fieldtoDataViewTermDTO.get1stOperandFieldID()) {
                                fieldtoDataViewTermDTO.set1stOperandFieldSQLCache(originalParam.getFieldSQLCash());
                            }

                            //strSQL.append   ( fieldtoDataViewTermDTO.get1stOperandFieldSQLCache() + " ");
                            //strSQL.append ( fieldtoDataViewTermDTO.getOperatorSQL() + " ");


                            /*
                            if (fieldtoDataViewTermDTO.getRelatedDataViewTypeID() != QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_SCOPE )
                            //strSQL.append ( "\n(\n" + fieldtoDataViewTermDTO.getRelatedDataViewSQL() + "\n)\n");
                            else if (fieldtoDataViewTermDTO.getRelatedDataViewTypeID() == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_TYPE_SCOPE )
                            //strSQL.append ( fieldtoDataViewTermDTO.getRelatedDataViewName() + " ");
                             */
                        }
                    }
                }


                Vector havingElements = queryDTO.getQueryHaving().getConditionElements();
                //   Utility.logger.debug("having size = "+ havingElements.size());
                fieldToFieldTermDTO = null;
                fieldtoDataViewTermDTO = null;
                for (int k = 0; k < havingElements.size(); k++) {
                    ConditionElement conditionElement =
                            (ConditionElement) havingElements.elementAt(k);
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
                /*
                Utility.logger.debug("%%%%%%%%%%%%%%");
                Utility.logger.debug(((ParameterFieldDTO) vecReportParameterList.elementAt(i) ).getFieldSQLCash());
                Utility.logger.debug(((ParameterFieldDTO) vecQueryParameterList.elementAt(i) ).getFieldSQLCash());
                Utility.logger.debug(originalParam.getFieldID());
                 */

            }


            strSQL = queryBuilderEngine.constructQuerySQL(queryDTO);
            debug("previewReport", "original SQL: \n" + strSQL, true);
            // preparing paging
            startNum = ((nPageNum - 1) * nRowsPerPage) + 1;
            endNum = nPageNum * nRowsPerPage;

            //String strPagedSQL = strSQL.substring(("  SELECT  ").length()) ; // same string that exists in QueryBuilderEngine.constructQuerySQLSelectClause()
            String strPagedSQL = new String(
                    " SELECT rownum, temp.* "
                    + " FROM ( "
                    + strSQL
                    + " ) temp ");

            if (nRowsPerPage == -1) {
                // no constraint on the number of rows returned
            } else {
                strPagedSQL += " WHERE rownum < " + nRowsPerPage;


            }

            strPagedSQL += " ORDER BY rownum ";


            System.out.println("strPagedSQL=" + strPagedSQL);

            /*
            String strPagedSQL = strSQL.substring(("  SELECT  ").length()) ; // same string that exists in QueryBuilderEngine.constructQuerySQLSelectClause()
            strPagedSQL = new String (
            " SELECT * " +
            " FROM ( " +
            " SELECT ROWNUM ROW_ID, " +
            strPagedSQL +
            " ) temp " +
            " WHERE ROW_ID BETWEEN " + startNum + " AND " + endNum + 
            " ORDER BY ROW_ID "
            ) ; 
             */
            debug("previewReport", "paged SQL: \n" + strPagedSQL, true);
            strErrorMessage = QueryBuilderDAO.verifyQuery(m_conSDSConnection, strPagedSQL);
            queryViewerDTO.setSQLCode(strSQL);
            vecHeaderFields = new Vector();

            if (strErrorMessage == null) {
                //debug ( "previewReport" , "before executing query" ) ;
                stmtQuery = m_conSDSConnection.prepareStatement(strPagedSQL);
                rstQuery = stmtQuery.executeQuery();
                Vector vecRows = new Vector();
                nColNum = rstQuery.getMetaData().getColumnCount();
                // exclude the first field which is used in paging
                for (int i = 1; i < nColNum; i++) {
                    strColName = rstQuery.getMetaData().getColumnName(i + 1);
                    //debug ( "previewReport" , "column name " + strColName ) ;
                    vecHeaderFields.addElement(strColName);
                }
                while (rstQuery.next()) {
                    DataRowDTO dataRowDTO = new DataRowDTO();
                    Vector vecValues = new Vector();
                    // exclude the first field which is used in paging
                    for (int j = 0; j < nColNum - 1; j++) {
                        //debug ( "previewReport" , "column value " + rstQuery.getString ((String) vecHeaderFields.elementAt(j)) ) ;
                        vecValues.addElement(rstQuery.getString((String) vecHeaderFields.elementAt(j)));
                    }
                    dataRowDTO.setValues(vecValues);
                    vecRows.addElement(dataRowDTO);
                }
                queryViewerDTO.setRows(vecRows);
                queryViewerDTO.setHeaderInterfaceFields(vecHeaderFields);
            } else {
                //debug ( "previewReport" , "invalid query" ) ;
                queryViewerDTO.setErrorMessage(strErrorMessage);
            }


            dataHashMap.put(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, queryViewerDTO);
        } catch (Exception objExp) {
            objExp.printStackTrace();
        }
        return queryViewerDTO;
    }

    public QueryViewerDTO exportReport(
            Connection m_conSDSConnection,
            ReportDTO reportDTO,
            Vector vecReportParameterList,
            int nPageNum,
            int nRowsPerPage,
            String userID,
            String fileName) {

        System.out.println("file Name =" + fileName);
        int dataViewID;
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

            dataViewID = reportDTO.getReportDataViewID();
            queryBuilderEngine = new QueryBuilderEngine();
            queryViewerDTO = new QueryViewerDTO();
            queryViewerDTO.setM_dataViewName(reportDTO.getReportName());
            queryDTO = queryBuilderEngine.loadQueryDTO(m_conSDSConnection, dataViewID, vecReportParameterList);

            queryDTO.setRunUserID(userID);

            // synchronize between the original query 
            // and the newly selected fields
            vecReportSelectList = reportDTO.getReportSelectedFields();
            if (vecReportSelectList.size() != 0) {
                vecQueryDisplayedFields = queryDTO.getDisplayedFields();
                newDisplayedFields = new Vector();
                for (int i = 0; i < vecQueryDisplayedFields.size(); i++) {
                    boolean found = false;
                    //debug ("previewReport", "queryField ID: " + ((FieldDTO) vecQueryDisplayedFields.elementAt(i)).getFieldID() , true);
                    for (int j = 0; (j < vecReportSelectList.size()) && (found == false); j++) {


                        FieldDTO field = (FieldDTO) vecQueryDisplayedFields.elementAt(i);


                        if ((((FieldDTO) vecQueryDisplayedFields.elementAt(i)).getFieldID()
                                == ((FieldModel) vecReportSelectList.elementAt(j)).getFieldID())
                                || (((FieldDTO) vecQueryDisplayedFields.elementAt(i)).getFieldID()
                                == ((FieldModel) vecReportSelectList.elementAt(j)).getFieldRFObject())) {
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
            vecReportOrderByList = reportDTO.getReportSelectedOrderBy();
            if (vecReportOrderByList.size() != 0) {
                newOrderByFields = new Vector();
                for (int i = 0; i < vecReportOrderByList.size(); i++) {
                    boolean found = false;
                    for (int j = 0; (j < vecQueryDisplayedFields.size()) && (found == false); j++) {
                        //debug ("previewReport", "comparing report field ID: " +
                        //( (ReportOrderByDTO) vecReportOrderByList.elementAt(i) ).getOrderByFieldID() +
                        //" with selected field ID: " + ((FieldDTO) vecQueryDisplayedFields.elementAt(j)).getFieldID() ) ;
                        if (((ReportOrderByDTO) vecReportOrderByList.elementAt(i)).getOrderByFieldID()
                                == ((FieldDTO) vecQueryDisplayedFields.elementAt(j)).getFieldID()) {
                            //debug ("previewReport", " fields are equal") ;
                            OrderByDTO orderByDTO = new OrderByDTO();
                            orderByDTO.setOrderType(((ReportOrderByDTO) vecReportOrderByList.elementAt(i)).getOrderByType());
                            orderByDTO.setFieldDTO((FieldDTO) vecQueryDisplayedFields.elementAt(i));
                            newOrderByFields.add(orderByDTO);
                            found = true;
                        }
                    }
                }
                queryDTO.setOrderBy(newOrderByFields);
            }

            // insert parameter values 
            vecQueryParameterList = queryDTO.getParametersFields();
            //debug ("previewKPI",  " vecQueryParameterList.size(): "+vecQueryParameterList.size() +
            //          " vecKPIParameterList.size(): "+vecKPIParameterList.size(), true) ;

            //            Utility.logger.debug("vecQueryParameterList size = "+ vecQueryParameterList.size());

            for (int i = 0; i < vecQueryParameterList.size(); i++) {
                /*
                ParameterFieldDTO parameterFieldDTO = (ParameterFieldDTO) vecKPIParameterList.elementAt(i) ;
                if ( parameterFieldDTO == null )
                {
                parameterFieldDTO = new ParameterFieldDTO () ;
                vecKPIParameterList.addElement(parameterFieldDTO);
                }*/

                for (int j = 0; j < newDisplayedFields.size(); j++) {

                    FieldDTO field = (FieldDTO) newDisplayedFields.elementAt(j);
                    if (field.getFieldType().getFieldTypeID() == 5) {
                    }

                }


                // assumption here is that both parameter lists are in the same order 
                ((ParameterFieldDTO) vecQueryParameterList.elementAt(i)).setFieldSQLCash(
                        ((ParameterFieldDTO) vecReportParameterList.elementAt(i)).getFieldSQLCash());



                ParameterFieldDTO originalParam = (ParameterFieldDTO) vecQueryParameterList.elementAt(i);


                Vector conditionElements = queryDTO.getQueryWhere().getConditionElements();
                int termType;
                FieldToFieldTermDTO fieldToFieldTermDTO = null;
                FieldtoDataViewTermDTO fieldtoDataViewTermDTO = null;

                for (int j = 0; j < conditionElements.size(); j++) {
                    ConditionElement conditionElement =
                            (ConditionElement) conditionElements.elementAt(j);
                    termType = conditionElement.getConditionElementType().getConditionElementTypeID();


                    if (termType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_WHERE_TYPE_TERM) {

                        Term term = (Term) conditionElements.elementAt(j);
                        termType = term.getTermType().getTermTypeID();
                        if (termType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_FIELD_TYPE) {
                            //Utility.logger.debug ("--->FIELD_TO_FIELD_TYPE") ;
                            fieldToFieldTermDTO = (FieldToFieldTermDTO) conditionElements.elementAt(j);

                            if (originalParam.getFieldID() == fieldToFieldTermDTO.get1stOperandFieldID()) {
                                fieldToFieldTermDTO.set1stOperandFieldSQLCache(originalParam.getFieldSQLCash());
                            } else if (originalParam.getFieldID() == fieldToFieldTermDTO.get2ndOperandFieldID()) {
                                fieldToFieldTermDTO.set2ndOperandFieldSQLCache(originalParam.getFieldSQLCash());
                            }

                            // Utility.logger.debug("field 1 id = " + fieldToFieldTermDTO.get1stOperandFieldID());
                            //  Utility.logger.debug("field 2 id = " + fieldToFieldTermDTO.get2ndOperandFieldID());


                            //strSQL.append ( fieldToFieldTermDTO.get1stOperandFieldSQLCache() + " ");
                            //strSQL.append ( fieldToFieldTermDTO.getOperatorSQL() + " ");
                            //strSQL.append ( fieldToFieldTermDTO.get2ndOperandFieldSQLCache() + " ");
                        } else if (termType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TYPE) {
                            //Utility.logger.debug ("--->FIELD_TO_DATAVIEW_TYPE") ;
                            fieldtoDataViewTermDTO = (FieldtoDataViewTermDTO) conditionElements.elementAt(j);
                            //  Utility.logger.debug("field 1 id = " + fieldtoDataViewTermDTO.get1stOperandFieldID());

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
                    ConditionElement conditionElement =
                            (ConditionElement) havingElements.elementAt(k);
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

                            //added by hagry 11/4/11
                                                           /*if (fieldToFieldTermDTO.getOperatorName().equalsIgnoreCase(QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_OPERATOR_LEFT_JOIN))
                            {
                            fieldToFieldTermDTO.set1stOperandFieldSQLCache( new String ( fieldToFieldTermDTO.get1stOperandFieldSQLCache() + " (+) " ) );
                            }*/


                        } else if (termType == QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_TERMS_FIELD_TO_DATAVIEW_TYPE) {

                            fieldtoDataViewTermDTO = (FieldtoDataViewTermDTO) havingElements.elementAt(k);
                            if (originalParam.getFieldID() == fieldtoDataViewTermDTO.get1stOperandFieldID()) {
                                fieldtoDataViewTermDTO.set1stOperandFieldSQLCache(originalParam.getFieldSQLCash());
                            }
                        }
                    }
                }

            }


            strSQL = queryBuilderEngine.constructQuerySQL(queryDTO);
            debug("previewReport", "original SQL: \n" + strSQL, true);

            System.out.println("original SQL: \n" + strSQL);
            // preparing paging
            startNum = ((nPageNum - 1) * nRowsPerPage) + 1;
            endNum = nPageNum * nRowsPerPage;


            String strPagedSQL = new String(
                    " SELECT rownum, temp.* "
                    + " FROM ( "
                    + strSQL
                    + " ) temp ");

            if (nRowsPerPage == -1) {
                // no constraint on the number of rows returned
            } else {
                strPagedSQL += " WHERE rownum < " + nRowsPerPage;


            }

            strPagedSQL += " ORDER BY rownum ";


            System.out.println("strPagedSQL=" + strPagedSQL);

            debug("previewReport", "paged SQL: \n" + strPagedSQL, true);

            strErrorMessage = QueryBuilderDAO.verifyQuery(m_conSDSConnection, strPagedSQL);
            queryViewerDTO.setSQLCode(strSQL);

            vecHeaderFields = new Vector();


            System.out.println(fileName + "/" + reportDTO.getReportName() + "_" + userID + ".csv");

            FileWriter outFile = new FileWriter(fileName + "/" + reportDTO.getReportName() + "_" + userID + ".csv");

            PrintWriter out = new PrintWriter(outFile);
            queryViewerDTO.isFile = true;
            queryViewerDTO.fileName = reportDTO.getReportName() + "_" + userID + ".csv";

            System.out.println("fileName = " + queryViewerDTO.fileName);

            if (strErrorMessage == null) {
                //debug ( "previewReport" , "before executing query" ) ;
                stmtQuery = m_conSDSConnection.prepareStatement(strPagedSQL);
                rstQuery = stmtQuery.executeQuery();


                nColNum = rstQuery.getMetaData().getColumnCount();
                // exclude the first field which is used in paging
                for (int i = 1; i < nColNum; i++) {
                    strColName = rstQuery.getMetaData().getColumnName(i + 1);
                    out.print(strColName);
                    if (i != nColNum - 1) {
                        out.print(",");
                    }

                    vecHeaderFields.addElement(strColName);
                }
                out.println("");
                while (rstQuery.next()) {
                    //DataRowDTO dataRowDTO = new DataRowDTO() ;
                    //Vector vecValues = new Vector () ;
                    // exclude the first field which is used in paging
                    for (int j = 0; j < nColNum - 1; j++) {
                        out.print(rstQuery.getString((String) vecHeaderFields.elementAt(j)));

                        if (j != nColNum - 2) {
                            out.print(",");
                        }
                    }
                    out.println("");
                }

                rstQuery.close();
                stmtQuery.close();
            } else {
                //debug ( "previewReport" , "invalid query" ) ;
                queryViewerDTO.setErrorMessage(strErrorMessage);
            }

            out.close();

            dataHashMap.put(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, queryViewerDTO);
        } catch (Exception objExp) {
            objExp.printStackTrace();
        }
        return queryViewerDTO;
    }

    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    // These functions should be moved to the Report- or DataViewDAO
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    public ReportDTO getReportData(Connection m_conSDSConnection, int nReportID) {

        ReportDTO reportDTO = null;
        PreparedStatement stmtReport = null;
        try {

            reportDTO = new ReportDTO();
            String strReportQuery =
                    /*
                    " select * " +
                    " from   VW_ADH_REPORT,  VW_ADH_DATA_VIEW " +
                    " where  VW_ADH_REPORT.DATA_VIEW_ID = VW_ADH_DATA_VIEW.DATA_VIEW_ID and " +
                    "     VW_ADH_REPORT.REPORT_ID = ? " ; */
                    //            " select * " +
                    //            " from   VW_ADH_REPORT, VW_ADH_DATA_VIEW " +
                    //            //" where  VW_ADH_REPORT.REPORT_ID = ? " ;
                    //            " where  VW_ADH_REPORT.DATA_VIEW_ID = VW_ADH_DATA_VIEW.DATA_VIEW_ID and " +
                    //            "        VW_ADH_REPORT.REPORT_ID =  ? " ;

                    "select *  from   ADH_REPORT, ADH_DATA_VIEW where  ADH_REPORT.DATA_VIEW_ID = ADH_DATA_VIEW.DATA_VIEW_ID and  ADH_REPORT.REPORT_ID = ?";
            stmtReport = m_conSDSConnection.prepareStatement(strReportQuery);
            stmtReport.setInt(1, nReportID);
            ResultSet rstReport = stmtReport.executeQuery();

            while (rstReport.next()) {
                reportDTO.setReportID(rstReport.getInt("REPORT_ID"));
                reportDTO.setReportName(rstReport.getString("REPORT_NAME"));
                reportDTO.setReportDescription(rstReport.getString("REPORT_DESCRIPTION"));
                reportDTO.setReportDataViewID(rstReport.getInt("DATA_VIEW_ID"));
                int statusID = rstReport.getInt("DATA_VIEW_STATUS_ID");
                if (statusID == ReportInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_ACTIVE) {
                    reportDTO.setIsDataViewActive(true);
                } else {
                    reportDTO.setIsDataViewActive(false);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return reportDTO;
    }

    //---------------------------------------------------------------
    // list all reports of a given universe
    public Vector<ReportDTO> retrieveReportList(Connection m_conSDSConnection, String reportname/*int nUniverseID*/) {

        ReportDTO reportDTO = null;
        Statement stmtReport = null;
        Vector<ReportDTO> vecReportList = new Vector<ReportDTO>(50, 10);

        if (reportname == null) {
            return vecReportList;
        }
        try {
            String strReportQuery = null;
            if (reportname.compareTo("") != 0) {
                strReportQuery = "select REPORT_ID , REPORT_STATUS_ID , REPORT_NAME, REPORT_DESCRIPTION, DATA_VIEW_STATUS_ID from   ADH_REPORT, ADH_DATA_VIEW  where ADH_REPORT.DATA_VIEW_ID = ADH_DATA_VIEW.DATA_VIEW_ID and ADH_REPORT.REPORT_STATUS_ID !='3'  and upper(ADH_REPORT.REPORT_NAME) like '%" + reportname.toUpperCase() + "%' order by  upper(ADH_REPORT.REPORT_NAME)";
            } else {
                strReportQuery = "select REPORT_ID , REPORT_STATUS_ID , REPORT_NAME, REPORT_DESCRIPTION, DATA_VIEW_STATUS_ID from   ADH_REPORT, ADH_DATA_VIEW  where ADH_REPORT.DATA_VIEW_ID = ADH_DATA_VIEW.DATA_VIEW_ID and ADH_REPORT.REPORT_STATUS_ID !='3'  order by  upper(ADH_REPORT.REPORT_NAME)";
            }

            stmtReport = m_conSDSConnection.createStatement();
            ResultSet rstReport = stmtReport.executeQuery(strReportQuery);
            while (rstReport.next()) {
                reportDTO = new ReportDTO();
                reportDTO.setReportID(rstReport.getInt("REPORT_ID"));
                reportDTO.setReportStatusID(rstReport.getInt("REPORT_STATUS_ID"));
                reportDTO.setReportName(rstReport.getString("REPORT_NAME"));
                reportDTO.setReportDescription(rstReport.getString("REPORT_DESCRIPTION"));
                int statusID = rstReport.getInt("DATA_VIEW_STATUS_ID");
                if (statusID == ReportInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_ACTIVE) {
                    reportDTO.setIsDataViewActive(true);
                } else {
                    reportDTO.setIsDataViewActive(false);
                }
                //reportDTO.setReportDataViewID (rstReport.getInt("DATA_VIEW_ID")) ;
                vecReportList.addElement(reportDTO);
            }
            rstReport.close();
            stmtReport.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return vecReportList;
    }

    public Vector retrieveReportListForUser(Connection m_conSDSConnection, String userID) {

        GroupDTO gdto = null;
        PreparedStatement stmtReport = null;
        Vector vecReportList = null;
        try {
            vecReportList = new Vector();

            String strReportQuery =
                    //          " select * " + 
                    //          " from   VW_ADH_REPORT, VW_ADH_DATA_VIEW , ADH_PERSON_REPORT " + 
                    //          " where  REPORT_STATUS_ID = 1 and ADH_PERSON_REPORT.PERSON_ID = "+userID+" and ADH_PERSON_REPORT.REPORT_ID = VW_ADH_REPORT.REPORT_ID and VW_ADH_REPORT.DATA_VIEW_ID = VW_ADH_DATA_VIEW.DATA_VIEW_ID  order by  upper(vw_adh_report.REPORT_NAME)" ; //order by VW_ADH_REPORT.GROUP_ID, upper(vw_adh_report.REPORT_NAME)" ; 
                    "SELECT   t4.GROUP_NAME,t4.id,t4.GROUP_DEC,t4.GROUP_STATUS,t5.GROUP_STATUS_NAME  FROM adh_person_report t1"
                    + "         LEFT JOIN adh_report t2 ON t1.report_id = t2.report_id"
                    + "         LEFT JOIN adh_group_report t3 ON t3.report_id = t1.report_id"
                    + "         LEFT JOIN adh_report_group t4 ON t4.ID = t3.GROUP_ID"
                    + "         LEFT JOIN ADH_REPORT_GROUP_STATUS t5 ON t4.GROUP_STATUS = t5.ID"
                    + "   WHERE t3.REPORT_STATUS_ID='1' and t4.GROUP_STATUS=1 and  person_id = " + userID
                    + "   group by t4.Group_Name,t4.id,t4.GROUP_DEC,t4.GROUP_STATUS,t5.GROUP_STATUS_NAME"
                    + "   order by t4.Group_Name";
            //" select * " + 
            //" from   VW_ADH_REPORT, VW_ADH_DATA_VIEW, VW_ADH_UNIVERSE_DEFINITION " + 
            //" where  VW_ADH_REPORT.DATA_VIEW_ID = VW_ADH_DATA_VIEW.DATA_VIEW_ID and " + 
            //"        VW_ADH_REPORT.DATA_VIEW_ID = VW_ADH_UNIVERSE_DEFINITION.DATA_VIEW_ID and " + 
            //"        VW_ADH_UNIVERSE_DEFINITION.UNIVERSE_ID = ? " ;

            stmtReport = m_conSDSConnection.prepareStatement(strReportQuery);
            ResultSet rstReport = stmtReport.executeQuery();

            while (rstReport.next()) {
                //          reportDTO.setReportID (rstReport.getInt("REPORT_ID")) ;
                //          reportDTO.setReportName (rstReport.getString("REPORT_NAME")) ;
                //          reportDTO.setReportDescription (rstReport.getString("REPORT_DESCRIPTION")) ;
                //          reportDTO.setGroupID(rstReport.getInt("GROUP_ID"));
                //          reportDTO.setGroupName(rstReport.getString("GROUP_NAME"));
                //          int statusID = rstReport.getInt("DATA_VIEW_STATUS_ID");
                //          if (statusID == ReportInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_ACTIVE)
                //            reportDTO.setIsDataViewActive(true);
                //          else 
                //            reportDTO.setIsDataViewActive(false);
                //reportDTO.setReportDataViewID (rstReport.getInt("DATA_VIEW_ID")) ;
                gdto = new GroupDTO();
                int groupId = rstReport.getInt("id");
                gdto.setGroupDesc(rstReport.getString("GROUP_DEC"));
                gdto.setGroupId(new Integer(groupId));
                gdto.setGroupName(rstReport.getString("GROUP_NAME"));
                gdto.setGroupStatus(new Integer(rstReport.getInt("GROUP_STATUS")));
                gdto.setGroupReport(ReportDAO.getUserReports(userID, groupId));

                vecReportList.addElement(gdto);
            }
            rstReport.close();
            stmtReport.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return vecReportList;
    }

    //---------------------------------------------------------------
    public Vector getReportSelectedFields(Connection m_conSDSConnection, int nReportID) {
        FieldModel fieldModel = null;
        ResultSet rstFieldList = null;
        Vector vecFieldList = null;
        try {
            PreparedStatement stmtFieldList = null;
            vecFieldList = new Vector();

            String strFieldListQuery =
                    //          " select VW_ADH_FIELD.* " +
                    //          " from   VW_ADH_REPORT, VW_ADH_REPORT_SELECT, VW_ADH_FIELD " +
                    //          " where  VW_ADH_REPORT.REPORT_ID = ? and " +
                    //          "        VW_ADH_REPORT.REPORT_ID = VW_ADH_REPORT_SELECT.REPORT_ID and " +
                    //          "        VW_ADH_REPORT_SELECT.FIELD_ID = VW_ADH_FIELD.FIELD_ID " ;
                    "select ADH_FIELD.* ,ADH_DATA_VIEW.*,ADH_FIELD_TYPE.*,ADH_FIELD_DISPLAY_TYPE.* ,ADH_FIELD_TYPE.FIELD_TYPE_TABLE_ID FIELD_TYPE_OBJECT_ID"
                    + "           from   ADH_REPORT, ADH_REPORT_SELECT, ADH_FIELD  ,ADH_DATA_VIEW,ADH_FIELD_TYPE,ADH_FIELD_DISPLAY_TYPE"
                    + "          where  ADH_REPORT.REPORT_ID = ? and"
                    + "                ADH_REPORT.REPORT_ID = ADH_REPORT_SELECT.REPORT_ID and"
                    + "               ADH_REPORT.DATA_VIEW_ID =ADH_DATA_VIEW.DATA_VIEW_ID and "
                    + "              ADH_REPORT_SELECT.FIELD_ID = ADH_FIELD.FIELD_ID  and"
                    + "             ADH_FIELD_TYPE.FIELD_TYPE_ID =   ADH_FIELD.FIELD_TYPE_ID and"
                    + "            ADH_FIELD_DISPLAY_TYPE.FIELD_DISPLAY_TYPE_ID = ADH_FIELD.FIELD_DISPLAY_TYPE_ID";


            stmtFieldList = m_conSDSConnection.prepareStatement(strFieldListQuery);
            stmtFieldList.setInt(1, nReportID);
            rstFieldList = stmtFieldList.executeQuery();
            while (rstFieldList.next()) {
                fieldModel = new FieldModel(
                        rstFieldList.getInt("DATA_VIEW_ID"),
                        rstFieldList.getInt("DATA_VIEW_ISSUE"),
                        rstFieldList.getInt("DATA_VIEW_VERSION"),
                        rstFieldList.getInt("FIELD_ID"),
                        rstFieldList.getString("FIELD_NAME"),
                        rstFieldList.getString("FIELD_DESCRIPTION"),
                        rstFieldList.getString("FIELD_SQL_CASH"),
                        rstFieldList.getInt("FIELD_DISPLAY_TYPE_ID"),
                        rstFieldList.getString("FIELD_DISPLAY_TYPE_NAME"),
                        rstFieldList.getInt("FIELD_TYPE_ID"),
                        rstFieldList.getString("FIELD_TYPE_NAME"),
                        rstFieldList.getInt("FIELD_TYPE_OBJECT_ID"),
                        rstFieldList.getInt("FIELD_RF_OBJECT"));
                vecFieldList.addElement(fieldModel);
            }
            rstFieldList.close();
            stmtFieldList.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return vecFieldList;
    }

    //---------------------------------------------------------------
    public Vector getReportSelectIDs(Connection m_conSDSConnection, int nReportID) {
        ResultSet rstList = null;
        Vector vecIDsList = null;
        PreparedStatement stmtList = null;
        try {
            vecIDsList = new Vector();
            String strQuery =
                    " select VW_ADH_REPORT_SELECT.* "
                    + " from   VW_ADH_REPORT_SELECT "
                    + " where  VW_ADH_REPORT_SELECT.REPORT_ID = ? ";
            stmtList = m_conSDSConnection.prepareStatement(strQuery);
            stmtList.setInt(1, nReportID);
            rstList = stmtList.executeQuery();
            while (rstList.next()) {
                vecIDsList.addElement(new Long(rstList.getInt("REPORT_SELECT_ID")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return vecIDsList;
    }

    //---------------------------------------------------------------
    public Vector getReportOrderByIDs(Connection m_conSDSConnection, int nReportID) {
        ResultSet rstList = null;
        Vector vecOrderByIDsList = null;
        PreparedStatement stmtList = null;
        try {
            vecOrderByIDsList = new Vector();
            String strQuery =
                    " select VW_ADH_REPORT_ORDER_BY.* "
                    + " from   VW_ADH_REPORT_ORDER_BY "
                    + " where  VW_ADH_REPORT_ORDER_BY.REPORT_ID = ? ";
            stmtList = m_conSDSConnection.prepareStatement(strQuery);
            stmtList.setInt(1, nReportID);
            rstList = stmtList.executeQuery();
            while (rstList.next()) {
                vecOrderByIDsList.addElement(new Long(rstList.getInt("REPORT_ORDER_BY_ID")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return vecOrderByIDsList;
    }

    //---------------------------------------------------------------
    public Vector getReportSelectedOrderBy(Connection m_conSDSConnection, int nReportID) {
        Vector vecOrderByList = null;
        ResultSet rstOrderByList;
        PreparedStatement stmtOrderByList;
        String strOrderByListQuery = null;
        //int fieldTypeID ;
        //int nOrderByTypeID ;

        try {
            vecOrderByList = new Vector();
            strOrderByListQuery = new String(
                    //          " select VW_ADH_REPORT_ORDER_BY.*, VW_ADH_FIELD.FIELD_NAME " + 
                    //          " from   VW_ADH_REPORT, VW_ADH_REPORT_ORDER_BY, VW_ADH_FIELD " + 
                    //          " where  VW_ADH_REPORT.REPORT_ID = ? and " + 
                    //          "        VW_ADH_REPORT.REPORT_ID = VW_ADH_REPORT_ORDER_BY.REPORT_ID and " + 
                    //          "        VW_ADH_REPORT_ORDER_BY.FIELD_ID = VW_ADH_FIELD.FIELD_ID " + 
                    //          " order by VW_ADH_REPORT_ORDER_BY.REPORT_ORDER_BY_PLACE "
                    " select ADH_REPORT_ORDER_BY.*, ADH_FIELD.FIELD_NAME "
                    + " from   ADH_REPORT, ADH_REPORT_ORDER_BY, ADH_FIELD "
                    + " where  ADH_REPORT.REPORT_ID = ? and "
                    + "        ADH_REPORT.REPORT_ID = ADH_REPORT_ORDER_BY.REPORT_ID and "
                    + "        ADH_REPORT_ORDER_BY.FIELD_ID = ADH_FIELD.FIELD_ID "
                    + " order by ADH_REPORT_ORDER_BY.REPORT_ORDER_BY_PLACE ");
            stmtOrderByList = m_conSDSConnection.prepareStatement(strOrderByListQuery);
            stmtOrderByList.setInt(1, nReportID);
            rstOrderByList = stmtOrderByList.executeQuery();

            while (rstOrderByList.next()) {
                ReportOrderByDTO reportOrderByDTO = new ReportOrderByDTO();

                reportOrderByDTO.setOrderByFieldID(rstOrderByList.getInt("FIELD_ID"));
                reportOrderByDTO.setOrderByFieldName(rstOrderByList.getString("FIELD_NAME"));
                debug("getReportSelectedOrderBy", reportOrderByDTO.getOrderByFieldName(), true);
                //nOrderByTypeID =  ;
                reportOrderByDTO.setOrderByType(rstOrderByList.getString("REPORT_ORDER_BY_TYPE"));
                vecOrderByList.addElement(reportOrderByDTO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return vecOrderByList;
    }

    // ----------------------------------------------------------------------
    //public static boolean reportNameExists (/*Connection argCon,*/
    public boolean reportNameExists(Connection m_conSDSConnection,
            String strReportName) {
        Vector vecList = new Vector();
        PreparedStatement stmtList = null;
        boolean bDataViewExisting = false;
        try {
            String strListQuery =
                    " select * "
                    + " from ADH_REPORT "
                    + " where upper (REPORT_NAME) = upper (?)";
            //stmtList = argCon.prepareStatement (strListQuery) ;
            stmtList = m_conSDSConnection.prepareStatement(strListQuery);
            stmtList.setString(1, strReportName);
            ResultSet rstList = stmtList.executeQuery();

            while (rstList.next()) {
                //rstList.getString("DATA_VIEW_NAME");
                bDataViewExisting = true;
            }

            rstList.close();
            stmtList.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bDataViewExisting;
    }

    //---------------------------------------------------------------
    public static Vector getReportParameters(Connection argCon, int nReportID) {
        ResultSet rst;
        PreparedStatement stmt;
        String strQuery = null;
        //Long lTypeID = null ;
        Vector vecReportParameterList = null;
        ParameterFieldDTO parameterFieldDTO = null;
        try {
            vecReportParameterList = new Vector();

            //Utility.logger.debug ( "  got strOrderByType: " + strOrderByType) ;
            strQuery = new String(
                    //          " select VW_ADH_REPORT.*, VW_ADH_INPUT_PARAM.* , VW_ADH_FIELD.* " +
                    //          " from   VW_ADH_REPORT, VW_ADH_DATA_VIEW, VW_ADH_INPUT_PARAM , VW_ADH_FIELD" +
                    //          " where  VW_ADH_REPORT.REPORT_ID = ? and " +
                    //          "        VW_ADH_REPORT.DATA_VIEW_ID = VW_ADH_DATA_VIEW.DATA_VIEW_ID and " +
                    //          "        VW_ADH_DATA_VIEW.DATA_VIEW_ID = VW_ADH_INPUT_PARAM.DATA_VIEW_ID " +
                    //          "          AND VW_ADH_FIELD.FIELD_ID = VW_ADH_INPUT_PARAM.FIELD_ID " +
                    //          " order by INPUT_PARAM_ID "

                    "select ADH_REPORT.*, ADH_INPUT_PARAM.* , ADH_FIELD.*"
                    + " from   ADH_REPORT, ADH_DATA_VIEW, ADH_INPUT_PARAM , ADH_FIELD "
                    + " where  ADH_REPORT.REPORT_ID = ? and "
                    + " ADH_REPORT.DATA_VIEW_ID = ADH_DATA_VIEW.DATA_VIEW_ID and  "
                    + " ADH_DATA_VIEW.DATA_VIEW_ID = ADH_INPUT_PARAM.INPUT_PARAM_DATA_VIEW_ID  "
                    + " AND ADH_FIELD.FIELD_ID = ADH_INPUT_PARAM.FIELD_ID  "
                    + " order by ADH_INPUT_PARAM.INPUT_PARAM_ID ");
            stmt = argCon.prepareStatement(strQuery);

            /*Utility.logger.debug("getting query param " );
            Utility.logger.debug(strQuery );
            Utility.logger.debug(nReportID );*/

            //debug ( "getReportParameters", strQuery, true ) ;
            stmt.setInt(1, nReportID);
            rst = stmt.executeQuery();

            while (rst.next()) {
                parameterFieldDTO = new ParameterFieldDTO();

                //debug ( "getReportParameters", ""+rst.getInt("INPUT_PARAM_ID"), true ) ;

                //Utility.logger.debug ( "  got UNIVERSE_ID: " + rstType.getInt("UNIVERSE_ID") ) ;






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

    //---------------------------------------------------------------
	/*public void finalize()
    {
    try
    {
    Utility.closeConnection ( m_conSDSConnection ) ;
    }
    catch(Exception objExp)
    {
    objExp.printStackTrace();
    }
    
    
    }*/

    /*
    //this will be a hashtable of user id each one has a hashtable of all the reports it has 
    public Hashtable retrieveUserReportList()
    {
    try{
    String sql = "select * from vw_ADH_REPORT_USER ";
    Statement stat = m_conSDSConnection.createStatement();
    ResultSet res = stat.executeQuery (sql);
    Hashtable resultTable = new Hashtable ();
    
    while (res.next())
    {
    String userId =res.getString("user_id");
    String reportId = res.getString("report_id");
    
    if (resultTable.get(userId)!=null)
    {
    ((Hashtable)resultTable.get(userId)).put(reportId,reportId);
    }
    else
    {
    resultTable.put(userId,new Hashtable());
    ((Hashtable)resultTable.get(userId)).put(reportId,reportId);          
    }
    }
    
    stat.close();
    return resultTable;
    }
    catch(Exception e)
    {
    return new Hashtable();
    }
    }
    
    public void insertUserReportAccess(int userId, int reportId)
    {
    try{
    String sql = "insert into VW_ADH_REPORT_USER (user_id,report_Id)values( "+ userId+","+reportId+")";
    Statement stat = m_conSDSConnection.createStatement();
    stat.execute (sql);
    stat.close();
    
    }
    catch (Exception e)
    {
    e.printStackTrace();
    }
    
    }
    
    //---------------------------------------------------------------
    // list all reports that this user have access to 
    public Vector retrieveReportListVisibleForUser ( String userID )
    {
    
    ReportDTO reportDTO = null ;
    PreparedStatement stmtReport = null ;
    Vector vecReportList = null ;
    try
    {
    vecReportList = new Vector () ;
    
    String strReportQuery =
    " select * " + 
    " from   VW_ADH_REPORT, VW_ADH_DATA_VIEW , VW_ADH_REPORT_USER" + 
    " where  VW_ADH_REPORT.REPORT_ID = VW_ADH_REPORT_USER.REPORT_ID AND VW_ADH_REPORT_USER.user_id = "+ userID +" AND VW_ADH_REPORT.DATA_VIEW_ID = VW_ADH_DATA_VIEW.DATA_VIEW_ID  order by upper(vw_adh_report.REPORT_NAME)" ; 
    //" select * " + 
    //" from   VW_ADH_REPORT, VW_ADH_DATA_VIEW, VW_ADH_UNIVERSE_DEFINITION " + 
    //" where  VW_ADH_REPORT.DATA_VIEW_ID = VW_ADH_DATA_VIEW.DATA_VIEW_ID and " + 
    //"        VW_ADH_REPORT.DATA_VIEW_ID = VW_ADH_UNIVERSE_DEFINITION.DATA_VIEW_ID and " + 
    //"        VW_ADH_UNIVERSE_DEFINITION.UNIVERSE_ID = ? " ;
    
    stmtReport = m_conSDSConnection.prepareStatement (strReportQuery) ;
    ResultSet rstReport = stmtReport.executeQuery();
    
    while (rstReport.next())
    {
    reportDTO = new ReportDTO () ;
    reportDTO.setReportID (rstReport.getInt("REPORT_ID")) ;
    reportDTO.setReportName (rstReport.getString("REPORT_NAME")) ;
    reportDTO.setReportDescription (rstReport.getString("REPORT_DESCRIPTION")) ;
    int statusID = rstReport.getInt("DATA_VIEW_STATUS_ID");
    if (statusID == ReportInterfaceKey.PARAM_PREVIEW_QUERY_QUERY_STATUS_ACTIVE)
    reportDTO.setIsDataViewActive(true);
    else 
    reportDTO.setIsDataViewActive(false);
    //reportDTO.setReportDataViewID (rstReport.getInt("DATA_VIEW_ID")) ;
    vecReportList.addElement( reportDTO );
    }
    }
    catch (Exception ex)
    {
    ex.printStackTrace();
    }
    return vecReportList ;
    }
    
     */
    public static Vector constructGroupSelectList(HashMap paramHashMap) {
        //int     nNumParam ;
        //String  fieldID ;
        Vector vecSelectList = null;

        try {
            vecSelectList = new Vector();
            // --------------------- constructing select list -----------------------

            //nNumParam = new Integer ( (String) paramHashMap.get (ReportInterfaceKey.PARAM_SAVE_CUSTOMIZED_REPORT_SELECT_NUM) ) ;


            String[] arrSelect = null;
            Object objSelect = paramHashMap.get(ReportInterfaceKey.PARAM_UPDATE_CUSTOMIZED_REPORT_SELECT_LIST);
            if (objSelect == null) // if the object is null
            {
                arrSelect = new String[0];  // initialize with empty array
            } else if (!(objSelect instanceof String)) // if the object is a genuine array
            {
                //Utility.logger.debug("class name = "+ o.getClass().getName());

                arrSelect = (String[]) objSelect;
                //for (int i = 0;i<applied_user_warnings.length;i++)
                //  Utility.logger.debug("warning " + applied_user_warnings[i]);

            } else if (objSelect instanceof String) // if the object is just a string
            {
                arrSelect = new String[1];
                arrSelect[0] = (String) objSelect;
            }

            for (int i = 0; i < arrSelect.length; i++) {
                GroupDTO gdto = new GroupDTO();
                gdto.setGroupId(new Integer((arrSelect[i])));
                vecSelectList.addElement(gdto);
            }
        } catch (Exception objExp) {
            objExp.printStackTrace();
        }
        return vecSelectList;
    }

    public Vector constructReportParamListWithoutTypes(HashMap paramHashMap) {
        Vector vecReportParameterList = null;
        Integer nNumParam = null;
        int nParamType;
        String paramValue;
        ResultSet rst;
        PreparedStatement stmt;
        String strQuery;
        ParameterFieldDTO parameterFieldDTO = null;

        try {
            // --------------------- constructing param list -----------------------
            vecReportParameterList = new Vector();
            nNumParam = new Integer((String) paramHashMap.get(ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAMS_NUM));
            for (int i = 0; i < nNumParam.intValue(); i++) {
                parameterFieldDTO = new ParameterFieldDTO();
                parameterFieldDTO.setFieldID(new Integer((String) paramHashMap.get(ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_ID + i)).intValue());
                nParamType = new Integer((String) paramHashMap.get(ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_TYPE + i)).intValue();
                paramValue = (String) paramHashMap.get(ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_VALUE + i);
                parameterFieldDTO.setValueType(nParamType);

                Utility.logger.debug("nParamType = " + nParamType);
                Utility.logger.debug("param value = " + paramValue);



                parameterFieldDTO.setFieldSQLCash(paramValue);


                Utility.logger.debug(parameterFieldDTO.getFieldSQLCash());

                vecReportParameterList.addElement(parameterFieldDTO);
            }
        } catch (Exception objExp) {
            objExp.printStackTrace();
        }
        return vecReportParameterList;
    }
}