/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.level_relation_management;

import com.mobinil.mcss.level_relation_management.model.LevelZeroToLevelOne;
import com.mobinil.sds.core.system.dataMigration.DAO.DataMigrationDao;
import com.mobinil.sds.core.system.dataMigration.model.fileModel;
import com.mobinil.sds.core.system.sa.importdata.dao.DataImportTableDefDAO;
import com.mobinil.sds.core.utilities.DMZIP.ZipThread;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.core.utilities.Wrapper.*;
import com.mobinil.sds.web.interfaces.dcm.DCMInterfaceKey;
import com.mobinil.sds.web.interfaces.dm.DMInterfaceKey;
import com.mobinil.sds.web.interfaces.sa.AdministrationInterfaceKey;
import java.io.File;
import java.sql.*;
import java.util.*;

/**
 *
 * @author SAND
 */
public class LevelRelationManagementHandler {

    static final int ACTION_UPLOAD_WAREHOUSE_TO_LEVEL_ZERO = 1;
    static final int ACTION_UPLOAD_WAREHOUSE_TO_LEVEL_ZERO_process = 2;
    static final int ACTION_LEVEL_ZERO_TO_LEVEL_ONE = 3;
    static final int ACTION_VIEW_LEVEL_ZERO_TO_LEVEL_ONE = 4;
    static final int ACTION_EDIT_LEVEL_ZERO_TO_LEVEL_ONE = 5;
    static final int ACTION_DELETE_LEVEL_ZERO_TO_LEVEL_ONE = 6;
    static final int ACTION_LEVEL_ZERO_TO_LEVEL_ONE_PROCESS = 7;
    //NR - SDS
    static final int ACTION_CHANGE_UPLOADED_EMAIL_DATES = 8;
    static final int ACTION_DELETE_UPLOADED_EMAIL_DATES=9;
    static final int ACTION_EMAIL_SEARCH=10;
    //End - NR - SDS
    private final static boolean debugFlag = false;

    private static int getActionType(String action) {
        int actionType = 0;
        System.out.println("LevelRelationManagementHandler");
        if (action.equals(LevelRelationManagementInterfaceKey.ACTION_UPLOAD_WAREHOUSE_TO_LEVEL_ZERO)) {
            actionType = ACTION_UPLOAD_WAREHOUSE_TO_LEVEL_ZERO;
        } else if (action.equals(LevelRelationManagementInterfaceKey.ACTION_UPLOAD_WAREHOUSE_TO_LEVEL_ZERO_PROCESS)) {
            actionType = ACTION_UPLOAD_WAREHOUSE_TO_LEVEL_ZERO_process;
        } else if (action.equals(LevelRelationManagementInterfaceKey.ACTION_LEVEL_ZERO_TO_LEVEL_ONE)) {
            actionType = ACTION_LEVEL_ZERO_TO_LEVEL_ONE;
        } else if (action.equals(LevelRelationManagementInterfaceKey.ACTION_VIEW_LEVEL_ZERO_TO_LEVEL_ONE)) {
            actionType = ACTION_VIEW_LEVEL_ZERO_TO_LEVEL_ONE;
        } else if (action.equals(LevelRelationManagementInterfaceKey.ACTION_EDIT_LEVEL_ZERO_TO_LEVEL_ONE)) {
            actionType = ACTION_EDIT_LEVEL_ZERO_TO_LEVEL_ONE;
        } else if (action.equals(LevelRelationManagementInterfaceKey.ACTION_DELETE_LEVEL_ZERO_TO_LEVEL_ONE)) {
            actionType = ACTION_DELETE_LEVEL_ZERO_TO_LEVEL_ONE;
        } else if (action.equals(LevelRelationManagementInterfaceKey.ACTION_LEVEL_ZERO_TO_LEVEL_ONE_PROCESS)) {
            actionType = ACTION_LEVEL_ZERO_TO_LEVEL_ONE_PROCESS;
        } 
        //NR - SDS
        else if (action.equals(LevelRelationManagementInterfaceKey.ACTION_CHANGE_UPLOADED_EMAIL_DATES)) {
            actionType = ACTION_CHANGE_UPLOADED_EMAIL_DATES;
        }
        else if (action.equals(LevelRelationManagementInterfaceKey.ACTION_DELETE_UPLOADED_EMAIL_DATES)) {
            actionType = ACTION_DELETE_UPLOADED_EMAIL_DATES;    
        }
        else if (action.equals(LevelRelationManagementInterfaceKey.ACTION_EMAIL_SEARCH)) {
            actionType = ACTION_EMAIL_SEARCH;    
        }
        //End - NR - SDS
        return actionType;
    }

    public static HashMap handle(String action, HashMap paramHashMap) {
        int actionType = getActionType(action);
        HashMap dataHashMap = new HashMap();

        Connection con = null;
        try {
            con = Utility.getConnection();

            String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

            switch (actionType) {
                
                case ACTION_UPLOAD_WAREHOUSE_TO_LEVEL_ZERO:
                    Vector vec = LevelRelationManagementDAO.getWarehouseData();
                    dataHashMap.put(LevelRelationManagementInterfaceKey.CONTROL_WAREHOUSE_DATA, vec);

                    break;
                case ACTION_UPLOAD_WAREHOUSE_TO_LEVEL_ZERO_process:


                    break;
                case ACTION_LEVEL_ZERO_TO_LEVEL_ONE:
                    Vector vec2 = LevelRelationManagementDAO.getLevelZeroData();
                    dataHashMap.put(LevelRelationManagementInterfaceKey.CONTROL_LEVEL_ZERO_DATA, vec2);

                    break;
                case ACTION_VIEW_LEVEL_ZERO_TO_LEVEL_ONE:
                    Vector viewLevelsVector = LevelRelationManagementDAO.viewLevelzeroToLevelOneData();
                    dataHashMap.put(LevelRelationManagementInterfaceKey.VECTOR_LEVEL_ZERO_TO_LEVEL_ONE_DATA, viewLevelsVector);
                    break;
                case ACTION_EDIT_LEVEL_ZERO_TO_LEVEL_ONE:
                    int paramHashMapSize = paramHashMap.size();
                    Vector mainVector = LevelRelationManagementDAO.viewLevelzeroToLevelOneData();
                    Vector<LevelZeroToLevelOne> selected = new Vector<LevelZeroToLevelOne>();

                    List<String> strings = new ArrayList<String>();
                    for (int i = 0; i < paramHashMapSize; i++) {
                        String tempKey = (String) paramHashMap.keySet().toArray()[i];


                        if (tempKey.startsWith(LevelRelationManagementInterfaceKey.CONTROL_SHOW_LEVEL_ZERO_TO_LEVEL_ONE_CHECKBOX)) {
                            String labelIdKey = tempKey.substring((LevelRelationManagementInterfaceKey.CONTROL_SHOW_LEVEL_ZERO_TO_LEVEL_ONE_CHECKBOX).length());
                            //Utility.logger.debug(reportId);
                            System.out.println("The label id issssssssssss " + labelIdKey);
                            strings.add(labelIdKey);

                        }
                    }
                    LevelRelationManagementDAO.editLevelZeroToLevelOne(strings, con);
                    Vector viewLevelsVectors = LevelRelationManagementDAO.viewLevelzeroToLevelOneData();
                    dataHashMap.put(LevelRelationManagementInterfaceKey.VECTOR_LEVEL_ZERO_TO_LEVEL_ONE_DATA, viewLevelsVectors);

                    break;
                case ACTION_DELETE_LEVEL_ZERO_TO_LEVEL_ONE:

                    String Id = (String) paramHashMap.get(LevelRelationManagementInterfaceKey.INPUT_HIDDEN_LEVEL_ZERO_TO_LEVEL_ONE_ID);
                    LevelRelationManagementDAO.deleteLevelZeroToLevelOne(Id);
                    Vector viewLevelsVectorss = LevelRelationManagementDAO.viewLevelzeroToLevelOneData();
                    dataHashMap.put(LevelRelationManagementInterfaceKey.VECTOR_LEVEL_ZERO_TO_LEVEL_ONE_DATA, viewLevelsVectorss);
                    break;
                case ACTION_LEVEL_ZERO_TO_LEVEL_ONE_PROCESS:
                    System.out.println("Action" + paramHashMap.get(AdministrationInterfaceKey.QUERY_STRING_OPERATION));
                    break;
                    
                    //NR - SDS
                    case ACTION_CHANGE_UPLOADED_EMAIL_DATES:

                    System.out.println("Action" + paramHashMap.get(AdministrationInterfaceKey.QUERY_STRING_OPERATION));
                    break;
                    
                    case ACTION_DELETE_UPLOADED_EMAIL_DATES:

                    System.out.println("Action" + paramHashMap.get(AdministrationInterfaceKey.QUERY_STRING_OPERATION));
                    break;
                        
                    case ACTION_EMAIL_SEARCH:
                        System.out.println("email search action  - LevelRelationManagementHandler");
                        
                        String incomingAction = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);            
                        String fromDate            = (String)paramHashMap.get(LevelRelationManagementInterfaceKey.CONTROL_TEXT_EMAIL_FROM_DATE);
                        String toDate            = (String)paramHashMap.get(LevelRelationManagementInterfaceKey.CONTROL_TEXT_EMAIL_TO_DATE);
                        String senderEmail = (String)paramHashMap.get(LevelRelationManagementInterfaceKey.CONTROL_TEXT_SENDER_EMAIL);
                        String posCode = (String)paramHashMap.get(LevelRelationManagementInterfaceKey.CONTROL_TEXT_EMAIL_POS_CODE);
				
                        Vector searchResults = LevelRelationManagementDAO.searchEmailsByFilter(con, senderEmail, posCode, fromDate, toDate);
                                
			dataHashMap.put(LevelRelationManagementInterfaceKey.VECTOR_EMAIL_SEARCH_RESULT , searchResults);                                                                           

                        dataHashMap.put(LevelRelationManagementInterfaceKey.CONTROL_TEXT_EMAIL_FROM_DATE,fromDate);
                        dataHashMap.put(LevelRelationManagementInterfaceKey.CONTROL_TEXT_EMAIL_TO_DATE,toDate);
                        dataHashMap.put(LevelRelationManagementInterfaceKey.CONTROL_TEXT_SENDER_EMAIL,senderEmail);
                        dataHashMap.put(LevelRelationManagementInterfaceKey.CONTROL_TEXT_EMAIL_POS_CODE,posCode);            
                        
                        System.out.println("done filling email vector action -vector size "+ searchResults.size());
                        break;
                        
                    //End - NR _SDS


            }

            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

            if (con != null) {
                Utility.closeConnection(con);
            }
        } catch (Exception objExp) {
            objExp.printStackTrace();
        }
        return dataHashMap;

    }
}
