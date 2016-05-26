package com.mobinil.mcss.commissionLabel;

import java.sql.*;
import java.util.*;
import java.util.zip.*;
import java.io.*;

import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.sa.*;
import com.mobinil.sds.web.interfaces.commission.CommissionInterfaceKey;
import com.mobinil.sds.web.interfaces.gn.querybuilder.QueryBuilderInterfaceKey;
import com.mobinil.sds.core.system.gn.querybuilder.domain.QueryBuilderEngine;

import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey;
import com.mobinil.sds.core.system.dcm.genericModel.*;
import com.mobinil.sds.core.system.dcm.genericModel.DAO.*;
import com.mobinil.sds.core.system.commission.demon.*;
import com.mobinil.sds.core.system.commission.factor.dao.*;
import com.mobinil.sds.core.system.commission.factor.model.*;
import com.mobinil.sds.core.system.commission.model.*;
import com.mobinil.sds.core.system.commission.postarget.dao.POSTargetDao;
import com.mobinil.sds.core.system.commission.postarget.report.ExcelGeneratorReport;
import com.mobinil.sds.core.system.payment.model.*;
import com.mobinil.sds.core.system.gn.reports.dto.*;
import com.mobinil.sds.core.system.gn.reports.domain.*;
import com.mobinil.sds.core.system.sa.importdata.DataImportEngine;
import com.mobinil.sds.core.system.sa.importdata.ErrorInfo;
import com.mobinil.sds.core.system.sa.importdata.dao.*;
import com.mobinil.sds.core.system.sa.importdata.model.DataImportReport;
import com.mobinil.sds.core.system.sa.users.dao.UserDAO;
import com.mobinil.sds.core.utilities.Copy;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.GetUploadedFile;
import com.mobinil.sds.web.interfaces.commission.POSTargetInterface;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author sand
 */
public class CommissionLabelHandler {

    static final int VIEW_COMMISSION_LABELS = 1;
    static final int SHOW_LABEL_DATA = 2;
    static final int ADD_NEW_LABEL = 3;
    static final int UPDATE_LABEL_DATA = 4;
    static final int SAVE_LABEL_DATA = 5;
    static final int VIEW_LABEL_DETAILS_DATA = 6;
    static final int SAVE_LABEL_DETAILS_DATA = 7;
    static final int UPLOAD_COMMISSION_DATA = 8;
    static final int UPLOAD_COMMISSION_DATA_PROCESS = 9;
    static final int DELETE_ALL_LABEL_DETAILS = 10;
    static final int EXPORT_LABEL_DETAILS_DATA = 11;
    static final int DELETE_UPLOAD_FILE = 12;
    static final int DELETE_UPLOAD_FILE_PROCESS = 13;

    public static HashMap hadle(String action, HashMap paramHashMap, Connection con) throws Exception {

        int actionType = 0;
        // Connection con = null;
        HashMap dataHashMap = new HashMap(100);
        System.out.println("action iss " + action);
        try {
            // con = Utility.getConnection();
            if (action.equals(CommissionInterfaceKey.ACTION_VIEW_COMMISSION_LABELS)) {
                actionType = VIEW_COMMISSION_LABELS;
            } else if (action.equals(CommissionInterfaceKey.ACTION_SHOW_LABEL_DATA)) {
                actionType = SHOW_LABEL_DATA;
            } else if (action.equals(CommissionInterfaceKey.ACTION_ADD_NEW_LABEL)) {
                actionType = ADD_NEW_LABEL;
            } else if (action.equals(CommissionInterfaceKey.ACTION_UPDATE_LABEL_DATA)) {
                actionType = UPDATE_LABEL_DATA;
            } else if (action.equals(CommissionInterfaceKey.ACTION_SAVE_LABEL_DATA)) {
                actionType = SAVE_LABEL_DATA;
            } else if (action.equals(CommissionInterfaceKey.ACTION_VIEW_LABEL_DETAILS_DATA)) {
                actionType = VIEW_LABEL_DETAILS_DATA;
            } else if (action.equals(CommissionInterfaceKey.ACTION_SAVE_LABEL_DETAILS_DATA)) {
                actionType = SAVE_LABEL_DETAILS_DATA;
            } else if (action.equals(CommissionInterfaceKey.ACTION_UPLOAD_COMMISSION_DATA)) {
                actionType = UPLOAD_COMMISSION_DATA;
            } else if (action.equals(CommissionInterfaceKey.ACTION_UPLOAD_COMMISSION_DATA_PROCESS)) {
                actionType = UPLOAD_COMMISSION_DATA_PROCESS;
            } else if (action.equals(CommissionInterfaceKey.ACTION_DELETE_ALL_LABEL_DETAILS)) {
                actionType = DELETE_ALL_LABEL_DETAILS;
            } else if (action.equals(CommissionInterfaceKey.ACTION_EXPORT_LABEL_DETAILS_DATA)) {
                actionType = EXPORT_LABEL_DETAILS_DATA;
            } else if (action.equals(CommissionInterfaceKey.ACTION_DELETE_UPLOAD_FILE)) {
                actionType = DELETE_UPLOAD_FILE;
            } else if (action.equals(CommissionInterfaceKey.ACTION_DELETE_UPLOAD_FILE_PROCESS)) {
                actionType = DELETE_UPLOAD_FILE_PROCESS;
            }







            String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            if (strUserID != null && strUserID.compareTo("") != 0) {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
            } else {
                strUserID = getUserId(strUserID, paramHashMap);
            }
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

            switch (actionType) {
                case VIEW_COMMISSION_LABELS: {
                    Vector labelVec = CommissionLabelDAO.getAllLabels(con);

                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, labelVec);
                }
                break;

                case SHOW_LABEL_DATA: {
                    String labelId = (String) paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);
                    dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID, labelId);
                    LabelModel labelModelData = (LabelModel) CommissionLabelDAO.selectLabelData(con, labelId);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, labelModelData);
                }
                break;
                case ADD_NEW_LABEL:

                    break;

                case UPDATE_LABEL_DATA: {
                    String labelName = (String) paramHashMap.get(CommissionInterfaceKey.INPUT_TEXT_LABEL_NAME);

                    String labelDescription = (String) paramHashMap.get(CommissionInterfaceKey.INPUT_TEXT_LABEL_DESCRIPTION);

                    String labelId = (String) paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);
                   
                    String category = (String) paramHashMap.get(CommissionLabelInterfaceKey.INPUT_RADIO_RADIOCATEGORY1);

                    CommissionLabelDAO.updateLabelData(con, labelId, labelName, labelDescription,category);
                    Vector labelVec = CommissionLabelDAO.getAllLabels(con);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, labelVec);
                }
                break;
                case SAVE_LABEL_DATA: {

                    System.out.println("Save label at CommissionLabelHandler");
                    Long labelId = Utility.getSequenceNextVal(con, "SEQ_COMMISSION_LABEL_ID");
                    String labelName = (String) paramHashMap.get(CommissionInterfaceKey.INPUT_TEXT_LABEL_NAME);
                    String labelDescription = (String) paramHashMap.get(CommissionInterfaceKey.INPUT_TEXT_LABEL_DESCRIPTION);
                    String category = (String) paramHashMap.get(CommissionLabelInterfaceKey.INPUT_RADIO_RADIOCATEGORY1);
                    CommissionLabelDAO.insertNewLabel(con, labelId, labelName, labelDescription, category);
                    Vector labelVec = CommissionLabelDAO.getAllLabels(con);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, labelVec);
                }
                break;
                case VIEW_LABEL_DETAILS_DATA: {
                    String labelId = (String) paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);

                    dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID, labelId);
                    LabelModel labelModel = CommissionLabelDAO.selectLabelData(con, labelId);
                    Vector labelDetailsVec = null;
                    if (labelModel.getTwoValues() == 1) {
                        labelDetailsVec = CommissionLabelDAO.getAllLabelsDetails(con, labelId);
                    } else if (labelModel.getThreeValues() == 1) {
                        labelDetailsVec = CommissionLabelDAO.getAllLabelsDetailsThree(con, labelId);
                    }

                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, labelDetailsVec);
                    dataHashMap.put(CommissionLabelInterfaceKey.LABEL_MODEL, labelModel);
                }
                break;

                case EXPORT_LABEL_DETAILS_DATA: {
                    String labelId = (String) paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);
                    dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID, labelId);
                    LabelModel labelModel = CommissionLabelDAO.selectLabelData(con, labelId);
                    ///////////////////////////////////////////////// by Mohamed Youssef
                    if (labelModel.getTwoValues() == 1 && labelModel.getThreeValues() == 0) {
                        System.out.println("Case Two Values");
                        Vector labelDetailsVec = CommissionLabelDAO.getAllLabelsDetails(con, labelId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, labelDetailsVec);
                    } else {
                        System.out.println("Case Three Values");
                        Vector labelDetailsVec = CommissionLabelDAO.getAllLabelsDetailsThree(con, labelId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, labelDetailsVec);
                    }
                    //////////////////////////////////////////////
                }
                break;

                case DELETE_UPLOAD_FILE: {
                    String labelId = (String) paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);
                    dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID, labelId);
                    // LabelModel labelModel = CommissionDAO.selectLabelData(con, labelId);

                    Vector tableDefVector = DataImportTableDefDAO.getTableDefByCategory("25");
                    dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR, tableDefVector);

                }
                break;
                case DELETE_UPLOAD_FILE_PROCESS: {
                    String labelId = (String) paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);
                    dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID, labelId);
                }
                break;
                case DELETE_ALL_LABEL_DETAILS: {
                    String labelId = (String) paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);
                    LabelModel lable = CommissionLabelDAO.selectLabelData(con, labelId);
                    dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID, labelId);
                    if (lable.getTwoValues() == 1) {
                        CommissionLabelDAO.deleteAllLabelDetails(con, labelId);
                        Vector labelVec = CommissionLabelDAO.getAllLabels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, labelVec);
                    } else if (lable.getThreeValues() == 1) {
                        CommissionLabelDAO.deleteAllLabelDetailsThree(con, labelId);
                        Vector labelVec = CommissionLabelDAO.getAllLabels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, labelVec);
                    }
                }
                break;
                case SAVE_LABEL_DETAILS_DATA: {
                    String labelId = (String) paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);

                    LabelModel lable = CommissionLabelDAO.selectLabelData(con, labelId);

                    Vector labelDetailsVec = null;

                    ///////////////////////////////////////////////// by Mohamed Youssef
                    if (lable.getTwoValues() == 1) {
                        Vector labelVec = CommissionLabelDAO.getAllLabelsDetails(con, labelId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, labelVec);

                        dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID, labelId);
                        int labelsDetailsCount = Integer.parseInt((String) paramHashMap.get("labels__count"));


                        LabelModel labelModel = null;
                        String label_details_state = "";
                        //Vector labelDetailsVec = new Vector();
                        for (int i = 1; i <= labelsDetailsCount; i++) {
                            labelModel = new LabelModel();
                            String dcmCode = (String) paramHashMap.get("labels__R" + i + "__C1");
                            String amount = (String) paramHashMap.get("labels__R" + i + "__C2");
                            if (dcmCode.compareTo("") == 0) {
                                dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "DCM Code must not be empty.");
                            } else if (amount.compareTo("") == 0) {
                                dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Amount must not be empty.");
                            } else {

                                labelModel.setDcmCode(dcmCode);
                                labelModel.setAmount(amount);
                                String label_details_id = (String) paramHashMap.get("labels__R" + i + "__C4");

                                labelModel.setLabelDetailId(label_details_id);

                                label_details_state = (String) paramHashMap.get("labels__R" + i + "__C5");

                                if (label_details_state.equals("new")) {
                                    CommissionLabelDAO.insertLabelDetails(con, labelId, labelModel);
                                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Inserted Successfully.");
                                } else {
                                    CommissionLabelDAO.updateLabelDetails(con, labelId, labelModel);
                                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Updated Successfully");
                                }
                            }
                        }

                        labelDetailsVec = CommissionLabelDAO.getAllLabelsDetails(con, labelId);

                    } else if (lable.getThreeValues() == 1) {
                        Vector labelVec = CommissionLabelDAO.getAllLabelsDetailsThree(con, labelId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, labelVec);

                        dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID, labelId);
                        int labelsDetailsCount = Integer.parseInt((String) paramHashMap.get("labels__count"));

                        LabelModel labelModel = null;
                        String label_details_state = "";
                        //Vector labelDetailsVec = new Vector();
                        for (int i = 1; i <= labelsDetailsCount; i++) {
                            labelModel = new LabelModel();
                            String dcmCode = (String) paramHashMap.get("labels__R" + i + "__C1");
                            String amount = (String) paramHashMap.get("labels__R" + i + "__C2");
                            String category = (String) paramHashMap.get("labels__R" + i + "__C3");

                            System.out.println("dcm code =" + dcmCode);
                            System.out.println("amount = " + amount);
                            System.out.println("Category:" + category);
                            if (dcmCode.compareTo("") == 0) {
                                dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "DCM Code must not be empty.");
                            } else if (amount.compareTo("") == 0) {
                                dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Amount must not be empty.");
                            } else {

                                labelModel.setDcmCode(dcmCode);
                                labelModel.setAmount(amount);
                                labelModel.setCategory(category);

                                String label_details_id = (String) paramHashMap.get("labels__R" + i + "__C4");

                                labelModel.setLabelDetailId(label_details_id);

                                label_details_state = (String) paramHashMap.get("labels__R" + i + "__C5");

                                if (label_details_state.equals("new")) {
                                    CommissionLabelDAO.insertLabelDetailsThree(con, labelId, labelModel);
                                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Inserted Successfully.");
                                } else {
                                    CommissionLabelDAO.updateLabelDetailsThree(con, labelId, labelModel);
                                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Updated Successfully");
                                }
                            }
                        }

                        labelDetailsVec = CommissionLabelDAO.getAllLabelsDetailsThree(con, labelId);

                    }



                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, labelDetailsVec);


                    LabelModel labelModel = CommissionLabelDAO.selectLabelData(con, labelId);

                    dataHashMap.put(CommissionLabelInterfaceKey.LABEL_MODEL, labelModel);
                }
                break;
                case UPLOAD_COMMISSION_DATA: {
                    String labelId = (String) paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);
                    dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID, labelId);
                    //////////////////////////////////////////////////////// By Mohamed Youssef
                    LabelModel labelModel = CommissionLabelDAO.selectLabelData(con, labelId);
                    if (labelModel.getTwoValues() == 0 && labelModel.getThreeValues() == 1) {
                        Vector tableDefVector = DataImportTableDefDAO.getTableDefByCategory("37");
                        dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR, tableDefVector);
                    } else {
                        Vector tableDefVector = DataImportTableDefDAO.getTableDefByCategory("10");
                        dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR, tableDefVector);
                    }
                    ////////////////////////////////////////////////////////////
                }
                break;
                case UPLOAD_COMMISSION_DATA_PROCESS: {
                    String labelId = (String) paramHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID);
                    dataHashMap.put(CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID, labelId);
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
