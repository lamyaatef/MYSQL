package com.mobinil.mcss.commission;

import com.mobinil.sds.core.system.commission.demon.CommissionThread;
import com.mobinil.sds.core.system.dcm.genericModel.DAO.GenericModelDAO;
import com.mobinil.sds.core.system.dcm.genericModel.GenericModel;
import com.mobinil.sds.core.system.gn.reports.domain.ReportEngine;
import com.mobinil.sds.core.system.gn.reports.dto.ReportBuilderWizardDTO;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.commission.CommissionInterfaceKey;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

public class CommissionIIIHandler {

    static final int COMMISSION_SAVE_NEW_COMMISSION_PARAM = 1;
    static final int REVIEW_COMMISSION = 2;

    private static void prepareCommissionSearchScreenLookups(HashMap dataHashMap, String userId, Connection con) throws Exception {
        GenericModel commissionTypeModel = GenericModelDAO.getColumns(con, "COMMISSION_TYPE");
        Vector commissionTypes = GenericModelDAO.getModels(con, commissionTypeModel);
        dataHashMap.put(CommissionIIIInterfaceKey.VECTOR_COMMISSION_TYPE, commissionTypes);

        GenericModel commissionTypeCategoryModel = GenericModelDAO.getColumns(con, "COMMISSION_TYPE_CATEGORY");
        Vector commissionTypeCategories = GenericModelDAO.getModels(con, commissionTypeCategoryModel, "COMMISSION_TYPE_CATEGORY_NAME");
        dataHashMap.put(CommissionIIIInterfaceKey.VECTOR_COMMISSION_TYPE_CATEGORY, commissionTypeCategories);

        GenericModel commissionStatusModel = GenericModelDAO.getColumns(con, "COMMISSION_STATUS_TYPE");
        Vector commissionStatus = GenericModelDAO.getModels(con, commissionStatusModel);
        dataHashMap.put(CommissionIIIInterfaceKey.VECTOR_COMMISSION_STATUS, commissionStatus);

        Vector comChannelVec = GenericModelDAO.getCommissionChannel(con, userId);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, comChannelVec);
    }

    public static HashMap handle(String action, HashMap paramHashMap, Connection con) throws Exception {
        int actionType = 0;
        HashMap dataHashMap = new HashMap(100);
        System.out.println("action iss " + action);
        try {


            if (action.equals(CommissionIIIInterfaceKey.ACTION_COMMISSION_SAVE_NEW_COMMISSION_PARAM)) {
                actionType = COMMISSION_SAVE_NEW_COMMISSION_PARAM;
            }
            if (action.equals(CommissionIIIInterfaceKey.ACTION_REVIEW_COMMISSION)) {
                actionType = REVIEW_COMMISSION;

            }

            String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            if (strUserID != null && strUserID.compareTo("") != 0) {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
            } else {
                strUserID = getUserId(strUserID, paramHashMap);
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

            switch (actionType) {

                case COMMISSION_SAVE_NEW_COMMISSION_PARAM: {

                    prepareCommissionSearchScreenLookups(dataHashMap, strUserID, con);


                    dataHashMap.put(CommissionIIIInterfaceKey.INPUT_HIDDEN_COMMISSION_ACTION, CommissionIIIInterfaceKey.INPUT_HIDDEN_ALL_COMMISSION);
                    dataHashMap.put(CommissionIIIInterfaceKey.INPUT_HIDDEN_COMMISSION_STATUS, "0");

                    int commissionID = Integer.parseInt((String) paramHashMap.get(CommissionIIIInterfaceKey.INPUT_HIDDEN_COMMISSION_ID));

                    int dataViewID = CommissionIIIDAO.getCommissionByID(con, commissionID + "").getCommissionDataViewID();
                    ReportBuilderWizardDTO reportBuilderWizardDTO = CommissionIIIDAO.getDataViewParameters(con, dataViewID);
                    ReportEngine reportEngine = new ReportEngine();
                    Vector paramList = reportEngine.constructReportParamList(paramHashMap);
                    /*
                     * hagry to shady the above vector we need to loop on it and
                     * save it in a new table each object is of type
                     * parameterFieldDTO has both m_strLabelText m_nValueType
                     * they should be saved by teh commission id as well
                     *
                     */
                    CommissionIIIDAO.saveCommissionParams(con, reportEngine.constructReportParamListWithoutTypes(paramHashMap), commissionID);

                    String SQLQuery = CommissionIIIDAO.previewReport(con, reportBuilderWizardDTO.getReport(), paramList, dataViewID);

                    String dataViewType = CommissionIIIDAO.getCommissionByID(con, commissionID + "").getCommissionDataViewTypeName();

                    CommissionIIIDAO.updateCommissionSQLString(con, SQLQuery, commissionID);
                    String conStr[] = CommissionIIIDAO.getConnectionString(con);

                    Thread commissionThread = new CommissionThread(dataViewType, commissionID, strUserID, conStr);
                    commissionThread.start();

                    dataHashMap.put(CommissionInterfaceKey.SAVED_COMMISSION_ID, commissionID + "");

                }
                break;

                case REVIEW_COMMISSION: {

                    int commissionID = Integer.parseInt((String) paramHashMap.get(CommissionIIIInterfaceKey.INPUT_HIDDEN_COMMISSION_ID));

                    Vector reviews = CommissionIIIDAO.getCommissionData(con, commissionID);

                    dataHashMap.put(CommissionIIIInterfaceKey.VECTOR_COMMISSION_REVIEW, reviews);

                }
                break;



                default:

                    Utility.logger.debug("Unknown action received: " + action);

            }

        } catch (Exception objExp) {


            objExp.printStackTrace();
            throw objExp;
        }
        return dataHashMap;
    }

    private static String getUserId(String userId, HashMap paramHashMap) {
        userId = userId == null ? (String) ((HttpServletRequest) paramHashMap.get(InterfaceKey.HASHMAP_KEY_REQUEST_FROM_SERVLET)).getSession(false).getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID) : userId;
        return userId;
    }
}
