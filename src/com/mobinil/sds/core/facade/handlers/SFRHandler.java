package com.mobinil.sds.core.facade.handlers;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import java.util.HashMap;
import java.util.Hashtable;
import java.lang.Object;
import java.sql.*;

import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.cr.ContractRegistrationInterfaceKey;
import com.mobinil.sds.web.interfaces.sfr.SFRInterfaceKey;
import com.mobinil.sds.web.interfaces.sa.*;
import com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey;

import com.mobinil.sds.core.utilities.*;

import com.mobinil.sds.core.system.cr.contract.dao.ContractDao;
import com.mobinil.sds.core.system.gn.dcm.dto.*;
import com.mobinil.sds.core.system.gn.dcm.dao.*;
import com.mobinil.sds.core.system.gn.dcm.model.*;
import com.mobinil.sds.core.system.request.dao.RequestDao;
import com.mobinil.sds.core.system.sa.importdata.dao.*;
import com.mobinil.sds.core.system.sa.users.dao.*;
import com.mobinil.sds.core.system.sa.product.dao.ProductDAO;

import com.mobinil.sds.core.system.sa.users.dao.UserDAO;

import com.mobinil.sds.core.system.sfr.sheets.dao.*;
import com.mobinil.sds.core.system.sfr.sheets.model.*;

public class SFRHandler
{
    static final int UPLOAD_POS_CONTRACTS_COUNT_FORM_EXCEL = 1;
    static final int GENERATE_UPLOAD_POS_CONTRACTS_COUNT_FORM_TEMPLATE = 2;
    static final int ASSIGN_POS_SHEET_CONTRACTS_SIM_NUMBER = 3;
    static final int ADD_NEW_POS_BATCH = 4;
    static final int VIEW_POS_BATCHES = 5;
    static final int UPDATE_BATCH_STATUS = 6;
    static final int SEARCH_BATCHES = 7;
    static final int VIEW_BATCH_SHEETS = 8;
    static final int SFR_UPDATE_SHEET_STATUS = 9;
    static final int SEARCH_SHEETS = 10;
    static final int VIEW_SHEET_CONTRACTS_SIM_NUMBER = 11;
    static final int SAVE_SHEET_CONTRACTS_SIM_NUMBER = 12;
    static final int VIEW_POS_SHEET_HISTORY = 13;
    static final int SEARCH_SHEET_STATUS = 14;
    static final int VIEW_POS_SIM_HISTROY = 15;
    static final int SEARCH_SIM_STATUS = 16;
    static final int VIEW_POS_PENDING_BATCHES = 17;
    static final int SEARCH_PENDING_BATCHES = 18;
    static final int VIEW_PENDING_BATCH_SHEETS = 19;
    static final int EDIT_PENDING_SHEET = 20;
    static final int SAVE_PENDING_SHEET = 21;
    static final int DELETE_PENDING_SHEET = 22;
    static final int ADMIN_VIEW_POS_PENDING_BATCHES = 23;
    static final int ADMIN_PENDING_BATCHES_SELECT_AGENT = 24;
    static final int MANAGE_PENDING_BATCHES = 25;
    static final int MANAGE_PENDING_BATCHES_UPDATE_STATUS = 26;
    static final int MANAGE_PENDING_BATCHES_SEARCH = 27;
    static final int MANAGE_PENDING_BATCHES_VIEW_SHEETS = 28;
    static final int ADMIN_VIEW_BATCH_SHEETS = 29;
    static final int ADMIN_UPDATE_BATCH_STATUS = 30;
    static final int ADMIN_SEARCH_BATCHES = 31;
    static final int ADMIN_VIEW_POS_BATCHES = 32;
    static final int ADMIN_SEARCH_SHEETS = 33;
    static final int ADMIN_VIEW_SHEET_CONTRACTS_SIM_NUMBER = 34;
    static final int ADMIN_SFR_UPDATE_SHEET_STATUS = 35;
    static final int ADMIN_SAVE_SHEET_CONTRACTS_SIM_NUMBER = 36;
    static final int SFR_VIEW_IMPORT_LOG = 37;
    static final int SFR_SEARCH_IMPORT_LOG = 38;
    static final int VIEW_SHEET_WARNINGS = 39;
    static final int SFR_MANAGE_SHEET_WARNINGS = 40;
    static final int SFR_EDIT_SHEET_WARNING = 41;
    static final int SFR_SAVE_SHEET_WARNING = 42;
    static final int SFR_ADD_SHEET_WARNING = 43;
    static final int SFR_DELETE_SHEET_WARNING = 44;
    static final int ADMIN_VIEW_SHEET_DETAILS = 45;
    static final int ADMIN_SHEET_DETAIL_UPDATE_STATUS = 46;
    static final int NEXT_PENDING_BATCHES = 47;
    static final int PREVIOUS_PENDING_BATCHES = 48;
    static final int NEXT_POS_PENDING_BATCHES = 49;
    static final int PREVIOUS_POS_PENDING_BATCHES = 50;
    static final int NEXT_MANAGE_PENDING_BATCHES = 51;
    static final int PREVIOUS_MANAGE_PENDING_BATCHES = 52;
    static final int NEXT_SEARCH_BATCHES = 53;
    static final int PREVIOUS_SEARCH_BATCHES = 54;
    static final int NEXT_ADMIN_SEARCH_BATCHES = 55;
    static final int PREVIOUS_ADMIN_SEARCH_BATCHES = 56;
    static final int ADMIN_CLOSE_SHEETS_PER_BATCH = 57;
    static final int ADMIN_REJECT_SHEETS_PER_BATCH = 58;
    static final int BACK_TO_BATCH_PAGE = 59;
    static final int SHOW_ALL_BATCHES = 60;
    static final int ADD_NEW_BATCH = 61;
    static final int SAVE_NEW_BATCH = 62;
    static final int ADD_NEW_SHEET = 63;
    static final int SAVE_NEW_SHEET = 64;
    static final int CHANGE_BATCH_DATE = 65;
    static final int VIEW_CHANGE_BATCH_DATE = 66;
    static final int ACTION_VIEW_DELETE_BATCH = 67;
    static final int ACTION_DELETE_BATCH = 68;
    static final int ACTION_DELETE_BATCH_SHEET = 69;
    static final int ACTION_VIEW_MONTHS_MANAGEMENT = 70;
    static final int ADD_YEAR = 71;
    static final int ACTION_CLOSE_MONTH_COMMISSION = 72;
    static final int ACTION_VIEW_MONTHS_MANAGEMENT_SFR = 73;
    static final int ACTION_CLOSE_MONTH_SFR = 74;
    static final int ADD_YEAR_SFR = 75;
	static final int action_open_request=76;
	
	
    public static HashMap handle(String action, HashMap paramHashMap,
            java.sql.Connection con)
    {
        int actionType = 0;
        HashMap dataHashMap = null;

        try
        {

            if (action.equals(SFRInterfaceKey.ACTION_UPLOAD_POS_CONTRACTS_COUNT_FORM_EXCEL))
                actionType = UPLOAD_POS_CONTRACTS_COUNT_FORM_EXCEL;
            else if (action.equals(SFRInterfaceKey.ACTION_GENERATE_UPLOAD_POS_CONTRACTS_COUNT_FORM_TEMPLATE))
                actionType = GENERATE_UPLOAD_POS_CONTRACTS_COUNT_FORM_TEMPLATE;
            else if (action.equals(SFRInterfaceKey.ACTION_ASSIGN_POS_SHEET_CONTRACTS_SIM_NUMBER))
                actionType = ASSIGN_POS_SHEET_CONTRACTS_SIM_NUMBER;
            else if (action.equals(SFRInterfaceKey.ACTION_ADD_NEW_POS_BATCH))
                actionType = ADD_NEW_POS_BATCH;
            else if (action.equals(SFRInterfaceKey.ACTION_VIEW_POS_BATCHES))
                actionType = VIEW_POS_BATCHES;
            else if (action.equals(SFRInterfaceKey.ACTION_ADMIN_VIEW_POS_BATCHES))
                actionType = ADMIN_VIEW_POS_BATCHES;
            else if (action.equals(SFRInterfaceKey.ACTION_VIEW_POS_PENDING_BATCHES))
                actionType = VIEW_POS_PENDING_BATCHES;
            else if (action.equals(SFRInterfaceKey.ACTION_ADMIN_VIEW_POS_PENDING_BATCHES))
                actionType = ADMIN_VIEW_POS_PENDING_BATCHES;
            else if (action.equals(SFRInterfaceKey.ACTION_UPDATE_BATCH_STATUS))
                actionType = UPDATE_BATCH_STATUS;
            else if (action.equals(SFRInterfaceKey.ACTION_ADMIN_UPDATE_BATCH_STATUS))
                actionType = ADMIN_UPDATE_BATCH_STATUS;
            else if (action.equals(SFRInterfaceKey.ACTION_SEARCH_BATCHES))
                actionType = SEARCH_BATCHES;
            else if (action.equals(SFRInterfaceKey.ACTION_ADMIN_SEARCH_BATCHES))
                actionType = ADMIN_SEARCH_BATCHES;
            else if (action.equals(SFRInterfaceKey.ACTION_VIEW_BATCH_SHEETS))
                actionType = VIEW_BATCH_SHEETS;
            else if (action.equals(SFRInterfaceKey.ACTION_ADMIN_VIEW_BATCH_SHEETS))
                actionType = ADMIN_VIEW_BATCH_SHEETS;
            else if (action.equals(SFRInterfaceKey.ACTION_SFR_UPDATE_SHEET_STATUS))
                actionType = SFR_UPDATE_SHEET_STATUS;
            else if (action.equals(SFRInterfaceKey.ACTION_ADMIN_SFR_UPDATE_SHEET_STATUS))
                actionType = ADMIN_SFR_UPDATE_SHEET_STATUS;
            else if (action.equals(SFRInterfaceKey.ACTION_SEARCH_SHEETS))
                actionType = SEARCH_SHEETS;
            else if (action.equals(SFRInterfaceKey.ACTION_ADMIN_SEARCH_SHEETS))
                actionType = ADMIN_SEARCH_SHEETS;
            else if (action.equals(SFRInterfaceKey.ACTION_VIEW_SHEET_CONTRACTS_SIM_NUMBER))
                actionType = VIEW_SHEET_CONTRACTS_SIM_NUMBER;
            else if (action.equals(SFRInterfaceKey.ACTION_ADMIN_VIEW_SHEET_CONTRACTS_SIM_NUMBER))
                actionType = ADMIN_VIEW_SHEET_CONTRACTS_SIM_NUMBER;
            else if (action.equals(SFRInterfaceKey.ACTION_SAVE_SHEET_CONTRACTS_SIM_NUMBER))
                actionType = SAVE_SHEET_CONTRACTS_SIM_NUMBER;
            else if (action.equals(SFRInterfaceKey.ACTION_ADMIN_SAVE_SHEET_CONTRACTS_SIM_NUMBER))
                actionType = ADMIN_SAVE_SHEET_CONTRACTS_SIM_NUMBER;
            else if (action.equals(SFRInterfaceKey.ACTION_VIEW_POS_SHEET_HISTORY))
                actionType = VIEW_POS_SHEET_HISTORY;
            else if (action.equals(SFRInterfaceKey.ACTION_SEARCH_SHEET_STATUS))
                actionType = SEARCH_SHEET_STATUS;
            else if (action.equals(SFRInterfaceKey.ACTION_VIEW_POS_SIM_HISTROY))
                actionType = VIEW_POS_SIM_HISTROY;
            else if (action.equals(SFRInterfaceKey.ACTION_SEARCH_SIM_STATUS))
                actionType = SEARCH_SIM_STATUS;
            else if (action.equals(SFRInterfaceKey.ACTION_SEARCH_PENDING_BATCHES))
                actionType = SEARCH_PENDING_BATCHES;
            else if (action.equals(SFRInterfaceKey.ACTION_VIEW_PENDING_BATCH_SHEETS))
                actionType = VIEW_PENDING_BATCH_SHEETS;
            else if (action.equals(SFRInterfaceKey.ACTION_EDIT_PENDING_SHEET))
                actionType = EDIT_PENDING_SHEET;
            else if (action.equals(SFRInterfaceKey.ACTION_SAVE_PENDING_SHEET))
                actionType = SAVE_PENDING_SHEET;
            else if (action.equals(SFRInterfaceKey.ACTION_DELETE_PENDING_SHEET))
                actionType = DELETE_PENDING_SHEET;
            else if (action.equals(SFRInterfaceKey.ACTION_ADMIN_PENDING_BATCHES_SELECT_AGENT))
                actionType = ADMIN_PENDING_BATCHES_SELECT_AGENT;
            else if (action.equals(SFRInterfaceKey.ACTION_MANAGE_PENDING_BATCHES))
                actionType = MANAGE_PENDING_BATCHES;
            else if (action.equals(SFRInterfaceKey.ACTION_MANAGE_PENDING_BATCHES_UPDATE_STATUS))
                actionType = MANAGE_PENDING_BATCHES_UPDATE_STATUS;
            else if (action.equals(SFRInterfaceKey.ACTION_MANAGE_PENDING_BATCHES_SEARCH))
                actionType = MANAGE_PENDING_BATCHES_SEARCH;
            else if (action.equals(SFRInterfaceKey.ACTION_MANAGE_PENDING_BATCHES_VIEW_SHEETS))
                actionType = MANAGE_PENDING_BATCHES_VIEW_SHEETS;
            else if (action.equals(SFRInterfaceKey.ACTION_SFR_VIEW_IMPORT_LOG))
                actionType = SFR_VIEW_IMPORT_LOG;
            else if (action.equals(SFRInterfaceKey.ACTION_SFR_SEARCH_IMPORT_LOG))
                actionType = SFR_SEARCH_IMPORT_LOG;
            else if (action.equals(SFRInterfaceKey.ACTION_VIEW_SHEET_WARNINGS))
                actionType = VIEW_SHEET_WARNINGS;
            else if (action.equals(SFRInterfaceKey.ACTION_DELETE_SHEET))
                actionType = ACTION_DELETE_BATCH_SHEET;
            else if (action.equals(SFRInterfaceKey.ACTION_SFR_MANAGE_SHEET_WARNINGS))
                actionType = SFR_MANAGE_SHEET_WARNINGS;
            else if (action.equals(SFRInterfaceKey.ACTION_SFR_EDIT_SHEET_WARNING))
                actionType = SFR_EDIT_SHEET_WARNING;
            else if (action.equals(SFRInterfaceKey.ACTION_SFR_SAVE_SHEET_WARNING))
                actionType = SFR_SAVE_SHEET_WARNING;
            else if (action.equals(SFRInterfaceKey.ACTION_SFR_ADD_SHEET_WARNING))
                actionType = SFR_ADD_SHEET_WARNING;
            else if (action.equals(SFRInterfaceKey.ACTION_SFR_DELETE_SHEET_WARNING))
                actionType = SFR_DELETE_SHEET_WARNING;
            else if (action.equals(SFRInterfaceKey.ACTION_ADMIN_VIEW_SHEET_DETAILS))
                actionType = ADMIN_VIEW_SHEET_DETAILS;
            else if (action.equals(SFRInterfaceKey.ACTION_ADMIN_SHEET_DETAIL_UPDATE_STATUS))
                actionType = ADMIN_SHEET_DETAIL_UPDATE_STATUS;
            else if (action.equals(SFRInterfaceKey.ACTION_NEXT_PENDING_BATCHES))
                actionType = NEXT_PENDING_BATCHES;
            else if (action.equals(SFRInterfaceKey.ACTION_PREVIOUS_PENDING_BATCHES))
                actionType = PREVIOUS_PENDING_BATCHES;
            else if (action.equals(SFRInterfaceKey.ACTION_NEXT_POS_PENDING_BATCHES))
                actionType = NEXT_POS_PENDING_BATCHES;
            else if (action.equals(SFRInterfaceKey.ACTION_PREVIOUS_POS_PENDING_BATCHES))
                actionType = PREVIOUS_POS_PENDING_BATCHES;
            else if (action.equals(SFRInterfaceKey.ACTION_NEXT_MANAGE_PENDING_BATCHES))
                actionType = NEXT_MANAGE_PENDING_BATCHES;
            else if (action.equals(SFRInterfaceKey.ACTION_PREVIOUS_MANAGE_PENDING_BATCHES))
                actionType = PREVIOUS_MANAGE_PENDING_BATCHES;
            else if (action.equals(SFRInterfaceKey.ACTION_NEXT_SEARCH_BATCHES))
                actionType = NEXT_SEARCH_BATCHES;
            else if (action.equals(SFRInterfaceKey.ACTION_PREVIOUS_SEARCH_BATCHES))
                actionType = PREVIOUS_SEARCH_BATCHES;
            else if (action.equals(SFRInterfaceKey.ACTION_NEXT_ADMIN_SEARCH_BATCHES))
                actionType = NEXT_ADMIN_SEARCH_BATCHES;
            else if (action.equals(SFRInterfaceKey.ACTION_PREVIOUS_ADMIN_SEARCH_BATCHES))
                actionType = PREVIOUS_ADMIN_SEARCH_BATCHES;
            else if (action.equals(SFRInterfaceKey.ACTION_ADMIN_CLOSE_SHEETS_PER_BATCH))
                actionType = ADMIN_CLOSE_SHEETS_PER_BATCH;
            else if (action.equals(SFRInterfaceKey.ACTION_ADMIN_REJECT_SHEETS_PER_BATCH))
                actionType = ADMIN_REJECT_SHEETS_PER_BATCH;
            else if (action.equals(SFRInterfaceKey.ACTION_BACK_TO_BATCH_PAGE))
                actionType = BACK_TO_BATCH_PAGE;
            else if (action.equals(SFRInterfaceKey.ACTION_SHOW_ALL_BATCHES))
                actionType = SHOW_ALL_BATCHES;
            else if (action.equals(SFRInterfaceKey.ACTION_ADD_NEW_BATCH))
                actionType = ADD_NEW_BATCH;
            else if (action.equals(SFRInterfaceKey.ACTION_SAVE_NEW_BATCH))
                actionType = SAVE_NEW_BATCH;
            else if (action.equals(SFRInterfaceKey.ACTION_ADD_NEW_SHEET))
                actionType = ADD_NEW_SHEET;
            else if (action.equals(SFRInterfaceKey.ACTION_SAVE_NEW_SHEET))
                actionType = SAVE_NEW_SHEET;
            else if (action.equals(SFRInterfaceKey.ACTION_CHANGE_BATCH_DATE))
                actionType = CHANGE_BATCH_DATE;
            else if (action.equals(SFRInterfaceKey.ACTION_VIEW_CHANGE_BATCH_DATE))
                actionType = VIEW_CHANGE_BATCH_DATE;
            else if (action.equals(SFRInterfaceKey.ACTION_VIEW_DELETE_BATCH))
                actionType = ACTION_VIEW_DELETE_BATCH;
            else if (action.equals(SFRInterfaceKey.ACTION_DELETE_BATCH))
                actionType = ACTION_DELETE_BATCH;
            else if (action.equals(SFRInterfaceKey.ACTION_VIEW_MONTHS_MANAGEMENT))
                actionType = ACTION_VIEW_MONTHS_MANAGEMENT;
            else if (action.equals(SFRInterfaceKey.ACTION_ADD_YEAR))
                actionType = ADD_YEAR;
            
            else if (action.equals(SFRInterfaceKey.ACTION_CLOSE_MONTH_COMMISSION))
                actionType = ACTION_CLOSE_MONTH_COMMISSION;
            
            else if (action.equals(SFRInterfaceKey.ACTION_VIEW_MONTHS_MANAGEMENT_SFR))
                actionType = ACTION_VIEW_MONTHS_MANAGEMENT_SFR;
            else if (action.equals(SFRInterfaceKey.ACTION_CLOSE_MONTH_SFR))
                actionType = ACTION_CLOSE_MONTH_SFR;
            else if (action.equals(SFRInterfaceKey.ACTION_ADD_YEAR_SFR))
                actionType = ADD_YEAR_SFR;
            else if(action.compareTo(SCMInterfaceKey.ACTION_OPEN_REQUEST)==0)
  	            actionType = action_open_request;    
  	      
  	      
            
            switch (actionType)
            {
                
                case action_open_request:
	        	
	        	Calendar calendar = Calendar.getInstance();
	            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	            String datee = dateFormat.format(calendar.getTime()).toString();
	            dataHashMap.put(SCMInterfaceKey.REP_KIT_DATE, datee);
	            Vector list = RequestDao.getSupervisorList(con);
	        	dataHashMap.put(SCMInterfaceKey.REP_KIT_SUPERVISOR_LIST, list );
	        	int rowCount = RequestDao.getAvailableStkCount(con);
	        	dataHashMap.put(SCMInterfaceKey.REP_KIT_QUANTITY_AVAILABLE, rowCount);
	        	
	        	System.out.println("###################################### " + rowCount); 
	        	System.out.println("###################################### " + datee); 
	        	System.out.println("###################################### " + list); 
	        	
	        	break; 	
	            
                case UPLOAD_POS_CONTRACTS_COUNT_FORM_EXCEL :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector tableDefVector = DataImportTableDefDAO.getTableDefByCategory("5");
                        dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR, tableDefVector);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case GENERATE_UPLOAD_POS_CONTRACTS_COUNT_FORM_TEMPLATE :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case ASSIGN_POS_SHEET_CONTRACTS_SIM_NUMBER :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String rowsCount = (String) paramHashMap.get("user_defined_data_view_count");
                        int intRowsCount = Integer.parseInt(rowsCount);
                        for (int i = 1; i <= intRowsCount; i++)
                        {
                            if (paramHashMap.get("user_defined_data_view__R"
                                    + i + "__C5") != null)
                            {
                                String posCode = (String) paramHashMap.get("user_defined_data_view__R"
                                        + i + "__C1");
                                String posAgentCode = (String) paramHashMap.get("user_defined_data_view__R"
                                        + i + "__C2");
                                String sheetSerial = (String) paramHashMap.get("user_defined_data_view__R"
                                        + i + "__C3");
                                String contractsCount = (String) paramHashMap.get("user_defined_data_view__R"
                                        + i + "__C4");
                                SheetDAO.insertTmpSfrExcelFile(con, i, posCode, posAgentCode, sheetSerial, contractsCount, strUserID);
                            }
                        }
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case ADD_NEW_POS_BATCH :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case VIEW_POS_BATCHES :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        // Vector batches = SheetDAO.getBatches(con);
                        int rowNum = 0;
                        int count = 0;
                        String flag = "2";
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_FLAG, flag);
                        Vector batches = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case ADD_NEW_BATCH :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String flag = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_FLAG);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_FLAG, flag);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case ADD_NEW_SHEET :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String batchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID, batchId);
                        // System.out.println("The batch id issssssssss " +
                        // batchId);
                        String flag = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_FLAG);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_FLAG, flag);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case SAVE_NEW_BATCH :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String flag = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_FLAG);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_FLAG, flag);
                        String agentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_TEXT_AGENT_ID);
                        Long lBatchId = null;
                        int check = SheetDAO.checkAgentId(agentId, con);
                        if (check == 0)
                        {
                            lBatchId = SheetDAO.insertBatch(agentId, "1", con);

                            System.out.println("Batch id in handler isssssss" + lBatchId);
                            SheetDAO.insertBatchStatus(lBatchId, "1", strUserID, con);
                        } else
                        {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "The Agent Id does not exist");
                        }
                        int rowNum = 0;
                        int count = 0;
                        Vector batches = SheetDAO.getBatchesByBatchId(con, lBatchId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_ALL_BATCHES :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        // Vector batches = SheetDAO.getBatches(con);
                        int rowNum = 0;
                        int count = 0;
                        Vector batches = new Vector();
                        String flag = "1";
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_FLAG, flag);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);

                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case ADMIN_VIEW_POS_BATCHES :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        // Vector batches = SheetDAO.getBatches(con);
                        int rowNum = 0;
                        int count = 0;
                        Vector batches = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);

                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case VIEW_POS_PENDING_BATCHES :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strAgentId = strUserID;
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID, strAgentId);

                        // Vector batches =
                        // SheetDAO.getPendingBatches(con,strUserID);
                        Vector batches = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);
                        int rowNum = 0;
                        int count = 0;
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case MANAGE_PENDING_BATCHES :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strAgentId = "";
                        int rowNum = 0;
                        int count = 0;
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID, strAgentId);

                        // Vector batches =
                        // SheetDAO.getBatchesByFilter(con,"","",SFRInterfaceKey.CONST_BATCH_STATUS_TYPE_PENDING,"*","*");
                        Vector batches = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case MANAGE_PENDING_BATCHES_SEARCH :
                    try
                    {
                        dataHashMap = new HashMap();

                        String strSearchBatchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID, strSearchBatchId);
                        String strSearchBatchDateFrom = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM, strSearchBatchDateFrom);
                        String strSearchBatchDateTo = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO, strSearchBatchDateTo);

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID, strAgentId);
                        int rowNum = 0;
                        int count = SheetDAO.countOFBatches(con, strSearchBatchId, strAgentId, SFRInterfaceKey.CONST_BATCH_STATUS_TYPE_PENDING, strSearchBatchDateFrom, strSearchBatchDateTo);
                        Vector batches = SheetDAO.getBatchesByFilterByRowNum(con, rowNum, strSearchBatchId, strAgentId, SFRInterfaceKey.CONST_BATCH_STATUS_TYPE_PENDING, strSearchBatchDateFrom, strSearchBatchDateTo);
                        // Vector batches =
                        // SheetDAO.getBatchesByFilter(con,strSearchBatchId,strAgentId,SFRInterfaceKey.CONST_BATCH_STATUS_TYPE_PENDING,strSearchBatchDateFrom,strSearchBatchDateTo);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case NEXT_MANAGE_PENDING_BATCHES :
                    try
                    {
                        dataHashMap = new HashMap();

                        String strSearchBatchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID, strSearchBatchId);
                        String strSearchBatchDateFrom = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM, strSearchBatchDateFrom);
                        String strSearchBatchDateTo = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO, strSearchBatchDateTo);

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID, strAgentId);
                        int rowNum = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        rowNum = rowNum + 1;
                        int count = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_COUNT));
                        Vector batches = SheetDAO.getBatchesByFilterByRowNum(con, rowNum, strSearchBatchId, strAgentId, SFRInterfaceKey.CONST_BATCH_STATUS_TYPE_PENDING, strSearchBatchDateFrom, strSearchBatchDateTo);
                        // Vector batches =
                        // SheetDAO.getBatchesByFilter(con,strSearchBatchId,strAgentId,SFRInterfaceKey.CONST_BATCH_STATUS_TYPE_PENDING,strSearchBatchDateFrom,strSearchBatchDateTo);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case PREVIOUS_MANAGE_PENDING_BATCHES :
                    try
                    {
                        dataHashMap = new HashMap();

                        String strSearchBatchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID, strSearchBatchId);
                        String strSearchBatchDateFrom = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM, strSearchBatchDateFrom);
                        String strSearchBatchDateTo = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO, strSearchBatchDateTo);

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID, strAgentId);
                        int rowNum = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        rowNum = rowNum - 1;
                        int count = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_COUNT));
                        Vector batches = SheetDAO.getBatchesByFilterByRowNum(con, rowNum, strSearchBatchId, strAgentId, SFRInterfaceKey.CONST_BATCH_STATUS_TYPE_PENDING, strSearchBatchDateFrom, strSearchBatchDateTo);
                        // Vector batches =
                        // SheetDAO.getBatchesByFilter(con,strSearchBatchId,strAgentId,SFRInterfaceKey.CONST_BATCH_STATUS_TYPE_PENDING,strSearchBatchDateFrom,strSearchBatchDateTo);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case MANAGE_PENDING_BATCHES_UPDATE_STATUS :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strAgentId = "";
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID, strAgentId);

                        String strBatchID = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        String strBatchNewStatusTypeID = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_STATUS_TYPE_ID);
                        SheetDAO.updateBatchStatus(con, strBatchID, strBatchNewStatusTypeID, strUserID);

                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);

                        String strSearchBatchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID, strSearchBatchId);
                        String strSearchAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_AGENT_ID, strSearchAgentId);
                        String strSearchBatchStatusTypeId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_BATCH_STATUS_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_BATCH_STATUS_ID, strSearchBatchStatusTypeId);
                        String strSearchBatchDateFrom = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM, strSearchBatchDateFrom);
                        String strSearchBatchDateTo = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO, strSearchBatchDateTo);

                        int rowNum = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        int count = SheetDAO.countOFBatches(con, strSearchBatchId, strSearchAgentId, strSearchBatchStatusTypeId, strSearchBatchDateFrom, strSearchBatchDateTo);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);

                        Vector batches = SheetDAO.getBatchesByFilterByRowNum(con, rowNum, strSearchBatchId, strAgentId, SFRInterfaceKey.CONST_BATCH_STATUS_TYPE_PENDING, strSearchBatchDateFrom, strSearchBatchDateTo);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case ADMIN_VIEW_POS_PENDING_BATCHES :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID, strAgentId);

                        String strAgentName = SheetDAO.getAgentName(strAgentId, con);
                        if (strAgentName.compareTo("") == 0)
                        {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Agent ID does not exist in the system.");
                        }
                        int count = SheetDAO.countOfPendingBatches(con, strAgentId);
                        int rowNum = 0;
                        Vector batches = SheetDAO.getPendingBatchesByRowNum(con, rowNum, strAgentId);
                        // Vector batches =
                        // SheetDAO.getPendingBatches(con,strAgentId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SFRInterfaceKey.ACTION_ADMIN_VIEW_POS_PENDING_BATCHES);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case NEXT_POS_PENDING_BATCHES :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID, strAgentId);

                        String strAgentName = SheetDAO.getAgentName(strAgentId, con);
                        if (strAgentName.compareTo("") == 0)
                        {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Agent ID does not exist in the system.");
                        }
                        int count = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_COUNT));
                        int rowNum = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        rowNum = rowNum + 1;
                        Vector batches = SheetDAO.getPendingBatchesByRowNum(con, rowNum, strAgentId);
                        // Vector batches =
                        // SheetDAO.getPendingBatches(con,strAgentId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SFRInterfaceKey.ACTION_ADMIN_VIEW_POS_PENDING_BATCHES);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case PREVIOUS_POS_PENDING_BATCHES :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID, strAgentId);

                        String strAgentName = SheetDAO.getAgentName(strAgentId, con);
                        if (strAgentName.compareTo("") == 0)
                        {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Agent ID does not exist in the system.");
                        }
                        int count = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_COUNT));
                        int rowNum = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        rowNum = rowNum - 1;
                        Vector batches = SheetDAO.getPendingBatchesByRowNum(con, rowNum, strAgentId);
                        // Vector batches =
                        // SheetDAO.getPendingBatches(con,strAgentId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SFRInterfaceKey.ACTION_ADMIN_VIEW_POS_PENDING_BATCHES);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case UPDATE_BATCH_STATUS :
                    try
                    {
                        dataHashMap = new HashMap();
                        String flag = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_FLAG);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_FLAG, flag);
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strBatchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        String newStatusValue = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_STATUS_TYPE_ID);

                        SheetDAO.updateBatchStatus(con, strBatchId, newStatusValue, strUserID);
                        /*
                         * for(int j=0; j<paramHashMap.size(); j++) { String
                         * tempStatusString =
                         * (String)paramHashMap.keySet().toArray()[j]; String
                         * keyValue =
                         * (String)paramHashMap.get(tempStatusString);
                         * //Utility.
                         * logger.debug("wwwwwwwwww"+tempStatusString+"----------"
                         * +keyValue);
                         * if(tempStatusString.startsWith(SFRInterfaceKey
                         * .INPUT_SELECT_BATCH_STATUS_ID)) { int strlength =
                         * SFRInterfaceKey.INPUT_SELECT_BATCH_STATUS_ID.length()
                         * + 1; String strBatchId =
                         * tempStatusString.substring(strlength,
                         * tempStatusString.length()); String newStatusValue =
                         * keyValue.substring(0,1); String oldStatusValue =
                         * keyValue.substring(2,3);
                         * if(newStatusValue.compareTo(oldStatusValue)!=0) {
                         * 
                         * } } }
                         */

                        String strSearchBatchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID, strSearchBatchId);
                        String strSearchAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_AGENT_ID, strSearchAgentId);
                        String strSearchBatchStatusTypeId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_BATCH_STATUS_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_BATCH_STATUS_ID, strSearchBatchStatusTypeId);
                        String strSearchBatchDateFrom = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM, strSearchBatchDateFrom);
                        String strSearchBatchDateTo = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO, strSearchBatchDateTo);

                        Vector batches = SheetDAO.getBatchesByFilter(con, strSearchBatchId, strSearchAgentId, strSearchBatchStatusTypeId, strSearchBatchDateFrom, strSearchBatchDateTo);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);

                        int rowNum = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        int count = SheetDAO.countOFBatches(con, strSearchBatchId, strSearchAgentId, strSearchBatchStatusTypeId, strSearchBatchDateFrom, strSearchBatchDateTo);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);

                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case ADMIN_UPDATE_BATCH_STATUS :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strBatchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        String newStatusValue = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_STATUS_TYPE_ID);

                        SheetDAO.updateBatchStatus(con, strBatchId, newStatusValue, strUserID);
                        /*
                         * for(int j=0; j<paramHashMap.size(); j++) { String
                         * tempStatusString =
                         * (String)paramHashMap.keySet().toArray()[j]; String
                         * keyValue =
                         * (String)paramHashMap.get(tempStatusString);
                         * //Utility.
                         * logger.debug("wwwwwwwwww"+tempStatusString+"----------"
                         * +keyValue);
                         * if(tempStatusString.startsWith(SFRInterfaceKey
                         * .INPUT_SELECT_BATCH_STATUS_ID)) { int strlength =
                         * SFRInterfaceKey.INPUT_SELECT_BATCH_STATUS_ID.length()
                         * + 1; String strBatchId =
                         * tempStatusString.substring(strlength,
                         * tempStatusString.length()); String newStatusValue =
                         * keyValue.substring(0,1); String oldStatusValue =
                         * keyValue.substring(2,3);
                         * if(newStatusValue.compareTo(oldStatusValue)!=0) {
                         * 
                         * } } }
                         */

                        String strSearchBatchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID, strSearchBatchId);
                        String strSearchAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_AGENT_ID, strSearchAgentId);
                        String strSearchBatchStatusTypeId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_BATCH_STATUS_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_BATCH_STATUS_ID, strSearchBatchStatusTypeId);
                        String strSearchBatchDateFrom = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM, strSearchBatchDateFrom);
                        String strSearchBatchDateTo = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO, strSearchBatchDateTo);

                        Vector batches = SheetDAO.getBatchesByFilter(con, strSearchBatchId, strSearchAgentId, strSearchBatchStatusTypeId, strSearchBatchDateFrom, strSearchBatchDateTo);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);

                        int rowNum = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        int count = SheetDAO.countOFBatches(con, strSearchBatchId, strSearchAgentId, strSearchBatchStatusTypeId, strSearchBatchDateFrom, strSearchBatchDateTo);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);

                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case SEARCH_BATCHES :
                    try
                    {
                        dataHashMap = new HashMap();
                        String flag = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_FLAG);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_FLAG, flag);
                        String strSearchBatchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID, strSearchBatchId);
                        String strSearchAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_AGENT_ID, strSearchAgentId);
                        String strSearchBatchStatusTypeId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_BATCH_STATUS_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_BATCH_STATUS_ID, strSearchBatchStatusTypeId);
                        String strSearchBatchDateFrom = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM, strSearchBatchDateFrom);
                        String strSearchBatchDateTo = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO, strSearchBatchDateTo);

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        int rowNum = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        int count = SheetDAO.countOFBatches(con, strSearchBatchId, strSearchAgentId, strSearchBatchStatusTypeId, strSearchBatchDateFrom, strSearchBatchDateTo);
                        Vector batches = SheetDAO.getBatchesByFilterByRowNumNotPending(con, rowNum, strSearchBatchId, strSearchAgentId, strSearchBatchStatusTypeId, strSearchBatchDateFrom, strSearchBatchDateTo);
                        // Vector batches =
                        // SheetDAO.getBatchesByFilter(con,strSearchBatchId,strSearchAgentId,strSearchBatchStatusTypeId,strSearchBatchDateFrom,strSearchBatchDateTo);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case NEXT_SEARCH_BATCHES :
                    try
                    {
                        dataHashMap = new HashMap();
                        String flag = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_FLAG);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_FLAG, flag);
                        String strSearchBatchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID, strSearchBatchId);
                        String strSearchAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_AGENT_ID, strSearchAgentId);
                        String strSearchBatchStatusTypeId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_BATCH_STATUS_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_BATCH_STATUS_ID, strSearchBatchStatusTypeId);
                        String strSearchBatchDateFrom = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM, strSearchBatchDateFrom);
                        String strSearchBatchDateTo = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO, strSearchBatchDateTo);

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        int rowNum = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        int count = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_COUNT));
                        rowNum = rowNum + 1;
                        Vector batches = SheetDAO.getBatchesByFilterByRowNumNotPending(con, rowNum, strSearchBatchId, strSearchAgentId, strSearchBatchStatusTypeId, strSearchBatchDateFrom, strSearchBatchDateTo);
                        // Vector batches =
                        // SheetDAO.getBatchesByFilter(con,strSearchBatchId,strSearchAgentId,strSearchBatchStatusTypeId,strSearchBatchDateFrom,strSearchBatchDateTo);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case PREVIOUS_SEARCH_BATCHES :
                    try
                    {
                        dataHashMap = new HashMap();
                        String flag = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_FLAG);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_FLAG, flag);
                        String strSearchBatchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID, strSearchBatchId);
                        String strSearchAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_AGENT_ID, strSearchAgentId);
                        String strSearchBatchStatusTypeId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_BATCH_STATUS_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_BATCH_STATUS_ID, strSearchBatchStatusTypeId);
                        String strSearchBatchDateFrom = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM, strSearchBatchDateFrom);
                        String strSearchBatchDateTo = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO, strSearchBatchDateTo);

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        int rowNum = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        int count = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_COUNT));
                        rowNum = rowNum - 1;
                        Vector batches = SheetDAO.getBatchesByFilterByRowNumNotPending(con, rowNum, strSearchBatchId, strSearchAgentId, strSearchBatchStatusTypeId, strSearchBatchDateFrom, strSearchBatchDateTo);
                        // Vector batches =
                        // SheetDAO.getBatchesByFilter(con,strSearchBatchId,strSearchAgentId,strSearchBatchStatusTypeId,strSearchBatchDateFrom,strSearchBatchDateTo);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case ADMIN_SEARCH_BATCHES :
                    try
                    {
                        dataHashMap = new HashMap();

                        String strSearchBatchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID, strSearchBatchId);
                        String strSearchAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_AGENT_ID, strSearchAgentId);
                        String strSearchBatchStatusTypeId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_BATCH_STATUS_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_BATCH_STATUS_ID, strSearchBatchStatusTypeId);
                        String strSearchBatchDateFrom = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM, strSearchBatchDateFrom);
                        String strSearchBatchDateTo = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO, strSearchBatchDateTo);

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        int rowNum = 0;
                        int count = SheetDAO.countOFBatches(con, strSearchBatchId, strSearchAgentId, strSearchBatchStatusTypeId, strSearchBatchDateFrom, strSearchBatchDateTo);
                        Vector batches = SheetDAO.getBatchesByFilterByRowNumNotPending(con, rowNum, strSearchBatchId, strSearchAgentId, strSearchBatchStatusTypeId, strSearchBatchDateFrom, strSearchBatchDateTo);

                        // Vector batches =
                        // SheetDAO.getBatchesByFilter(con,strSearchBatchId,strSearchAgentId,strSearchBatchStatusTypeId,strSearchBatchDateFrom,strSearchBatchDateTo);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case BACK_TO_BATCH_PAGE :
                    try
                    {
                        dataHashMap = new HashMap();

                        String strSearchBatchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID, strSearchBatchId);
                        // String strSearchAgentId =
                        // (String)paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_AGENT_ID);
                        // dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_AGENT_ID,strSearchAgentId);
                        // String strSearchBatchStatusTypeId =
                        // (String)paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_BATCH_STATUS_ID);
                        // dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_BATCH_STATUS_ID,strSearchBatchStatusTypeId);
                        // String strSearchBatchDateFrom =
                        // (String)paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM);
                        // dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM,strSearchBatchDateFrom);
                        // String strSearchBatchDateTo =
                        // (String)paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO);
                        // dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO,strSearchBatchDateTo);

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        int rowNum = 0;
                        int count = 0;
                        Vector batches = SheetDAO.getBatchesByFilterByRowNumNotPending(con, rowNum, strSearchBatchId, "", "", "*", "*");

                        // Vector batches =
                        // SheetDAO.getBatchesByFilter(con,strSearchBatchId,strSearchAgentId,strSearchBatchStatusTypeId,strSearchBatchDateFrom,strSearchBatchDateTo);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case NEXT_ADMIN_SEARCH_BATCHES :
                    try
                    {
                        dataHashMap = new HashMap();

                        String strSearchBatchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID, strSearchBatchId);
                        String strSearchAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_AGENT_ID, strSearchAgentId);
                        String strSearchBatchStatusTypeId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_BATCH_STATUS_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_BATCH_STATUS_ID, strSearchBatchStatusTypeId);
                        String strSearchBatchDateFrom = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM, strSearchBatchDateFrom);
                        String strSearchBatchDateTo = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO, strSearchBatchDateTo);

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        int rowNum = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        int count = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_COUNT));
                        rowNum = rowNum + 1;
                        Vector batches = SheetDAO.getBatchesByFilterByRowNumNotPending(con, rowNum, strSearchBatchId, strSearchAgentId, strSearchBatchStatusTypeId, strSearchBatchDateFrom, strSearchBatchDateTo);
                        // Vector batches =
                        // SheetDAO.getBatchesByFilter(con,strSearchBatchId,strSearchAgentId,strSearchBatchStatusTypeId,strSearchBatchDateFrom,strSearchBatchDateTo);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case PREVIOUS_ADMIN_SEARCH_BATCHES :
                    try
                    {
                        dataHashMap = new HashMap();

                        String strSearchBatchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID, strSearchBatchId);
                        String strSearchAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_AGENT_ID, strSearchAgentId);
                        String strSearchBatchStatusTypeId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_BATCH_STATUS_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_BATCH_STATUS_ID, strSearchBatchStatusTypeId);
                        String strSearchBatchDateFrom = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM, strSearchBatchDateFrom);
                        String strSearchBatchDateTo = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO, strSearchBatchDateTo);

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        int rowNum = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        int count = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_COUNT));
                        rowNum = rowNum - 1;
                        Vector batches = SheetDAO.getBatchesByFilterByRowNumNotPending(con, rowNum, strSearchBatchId, strSearchAgentId, strSearchBatchStatusTypeId, strSearchBatchDateFrom, strSearchBatchDateTo);
                        // Vector batches =
                        // SheetDAO.getBatchesByFilter(con,strSearchBatchId,strSearchAgentId,strSearchBatchStatusTypeId,strSearchBatchDateFrom,strSearchBatchDateTo);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case SEARCH_PENDING_BATCHES :
                    try
                    {
                        dataHashMap = new HashMap();

                        String strSearchBatchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID, strSearchBatchId);
                        String strSearchBatchDateFrom = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM, strSearchBatchDateFrom);
                        String strSearchBatchDateTo = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO, strSearchBatchDateTo);

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID, strAgentId);

                        String strAgentName = SheetDAO.getAgentName(strAgentId, con);
                        if (strAgentName.compareTo("") == 0)
                        {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Agent ID does not exist in the system.");
                        }
                        int rowNum = 0;
                        int count = SheetDAO.countOFBatches(con, strSearchBatchId, strAgentId, SFRInterfaceKey.CONST_BATCH_STATUS_TYPE_PENDING, strSearchBatchDateFrom, strSearchBatchDateTo);
                        Vector batches = SheetDAO.getBatchesByFilterByRowNum(con, rowNum, strSearchBatchId, strAgentId, SFRInterfaceKey.CONST_BATCH_STATUS_TYPE_PENDING, strSearchBatchDateFrom, strSearchBatchDateTo);
                        // Vector batches =
                        // SheetDAO.getBatchesByFilter(con,strSearchBatchId,strAgentId,SFRInterfaceKey.CONST_BATCH_STATUS_TYPE_PENDING,strSearchBatchDateFrom,strSearchBatchDateTo);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SFRInterfaceKey.ACTION_SEARCH_PENDING_BATCHES);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case NEXT_PENDING_BATCHES :
                    try
                    {
                        dataHashMap = new HashMap();

                        String strSearchBatchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID, strSearchBatchId);
                        String strSearchBatchDateFrom = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM, strSearchBatchDateFrom);
                        String strSearchBatchDateTo = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO, strSearchBatchDateTo);

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID, strAgentId);

                        String strAgentName = SheetDAO.getAgentName(strAgentId, con);
                        if (strAgentName.compareTo("") == 0)
                        {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Agent ID does not exist in the system.");
                        }
                        int rowNum = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        rowNum = rowNum + 1;
                        int count = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_COUNT));
                        Vector batches = SheetDAO.getBatchesByFilterByRowNum(con, rowNum, strSearchBatchId, strAgentId, SFRInterfaceKey.CONST_BATCH_STATUS_TYPE_PENDING, strSearchBatchDateFrom, strSearchBatchDateTo);
                        // Vector batches =
                        // SheetDAO.getBatchesByFilter(con,strSearchBatchId,strAgentId,SFRInterfaceKey.CONST_BATCH_STATUS_TYPE_PENDING,strSearchBatchDateFrom,strSearchBatchDateTo);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SFRInterfaceKey.ACTION_SEARCH_PENDING_BATCHES);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case PREVIOUS_PENDING_BATCHES :
                    try
                    {
                        dataHashMap = new HashMap();

                        String strSearchBatchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID, strSearchBatchId);
                        String strSearchBatchDateFrom = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM, strSearchBatchDateFrom);
                        String strSearchBatchDateTo = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO, strSearchBatchDateTo);

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID, strAgentId);

                        String strAgentName = SheetDAO.getAgentName(strAgentId, con);
                        if (strAgentName.compareTo("") == 0)
                        {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Agent ID does not exist in the system.");
                        }
                        int rowNum = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        rowNum = rowNum - 1;
                        int count = Integer.parseInt((String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_COUNT));
                        Vector batches = SheetDAO.getBatchesByFilterByRowNum(con, rowNum, strSearchBatchId, strAgentId, SFRInterfaceKey.CONST_BATCH_STATUS_TYPE_PENDING, strSearchBatchDateFrom, strSearchBatchDateTo);
                        // Vector batches =
                        // SheetDAO.getBatchesByFilter(con,strSearchBatchId,strAgentId,SFRInterfaceKey.CONST_BATCH_STATUS_TYPE_PENDING,strSearchBatchDateFrom,strSearchBatchDateTo);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, batches);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM, ""
                                + rowNum);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_COUNT, ""
                                + count);
                        Vector batchStatusTypes = SheetDAO.getAllBatcheStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, batchStatusTypes);
                        Vector usersList = UserDAO.getUsersList(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, usersList);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SFRInterfaceKey.ACTION_SEARCH_PENDING_BATCHES);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case VIEW_BATCH_SHEETS :
                    try
                    {
                        dataHashMap = new HashMap();
                        String flag = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_FLAG);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_FLAG, flag);
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String batchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID, batchId);
                        Vector sheets = SheetDAO.getSheetsByFilter(con, batchId, 0, "", "", "", "");
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheets);
                        Vector sheetStatusTypes = SheetDAO.getAllSheetStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetStatusTypes);
                        // Vector posAgents = SheetDAO.getAllSecondPOS(con);
                        // dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,posAgents);
                        Vector batchDetails = SheetDAO.getBatchesByFilter(con, batchId, "", "", "*", "*");
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, batchDetails);

                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case SAVE_NEW_SHEET :
                    try
                    {
                        dataHashMap = new HashMap();
                        String flag = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_FLAG);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_FLAG, flag);
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String batchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID, batchId);
                        String sheetSIMGroup = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP, sheetSIMGroup);
                        String sheetStatusTypeId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID, sheetStatusTypeId);
                        Long lBatchId = Long.parseLong(batchId);
                        String sheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_TEXT_SHEET_SERIAL);
                        int intSheetSerial = Integer.parseInt(sheetSerial);
                        String posCode = (String) paramHashMap.get(SFRInterfaceKey.INPUT_TEXT_POS_CODE);
                        String agentPosCode = (String) paramHashMap.get(SFRInterfaceKey.INPUT_TEXT_POS_AGENT_CODE);
                        String contractsCount = (String) paramHashMap.get(SFRInterfaceKey.INPUT_TEXT_CONTRACTS_COUNT);
                        int contractsCountInt = Integer.parseInt(contractsCount);
                        int check = SheetDAO.checkPosCode(posCode, agentPosCode, sheetSerial, con);
                        Long lSheetId = null;
                        String sheetId = "";

                        if (check == 0)
                        {
                            lSheetId = SheetDAO.insertNewSheet(con, intSheetSerial, lBatchId, posCode, agentPosCode, contractsCountInt);
                            SheetDAO.insertSheetStatus(con, lSheetId + "", "1", strUserID);

                            dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL, sheetSerial);
                            sheetId = lSheetId.toString();

                            dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID, sheetId);
                        } else if (check == 1)
                        {
                            String messgae = "POS Code Does not exist in GEN_DCM";
                            // System.out.println("The message isssssssssss " +
                            // messgae);
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, messgae);
                        } else if (check == 2)
                        {
                            String messgae = "Second POS Code Does not exist in GEN_DCM";

                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, messgae);
                        } else if (check == 3)
                        {
                            String messgae = "Sheet Serial already Exist";

                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, messgae);
                        }

                        Vector SIMList = SheetDAO.getSIMsBySheet(con, sheetId, sheetSIMGroup);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, SIMList);
                        Vector sheetDetail = SheetDAO.getSheetsByFilter(con, batchId, intSheetSerial, "", "", "", sheetId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetDetail);
                        // Vector sheets =
                        // SheetDAO.getSheetbySheetId(con,batchId,lSheetId);
                        // dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,sheets);
                        // Vector sheetStatusTypes =
                        // SheetDAO.getAllSheetStatusType(con);
                        // dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,sheetStatusTypes);
                        // // Vector posAgents = SheetDAO.getAllSecondPOS(con);
                        // //
                        // dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,posAgents);
                        // Vector batchDetails =
                        // SheetDAO.getBatchesByFilter(con,batchId,"","","*","*");
                        // dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION,batchDetails);

                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case ADMIN_VIEW_BATCH_SHEETS :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String batchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID, batchId);
                        Vector sheets = SheetDAO.getSheetsByFilter(con, batchId, 0, "", "", "", "");
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheets);
                        Vector sheetStatusTypes = SheetDAO.getAllSheetStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetStatusTypes);
                        // Vector posAgents = SheetDAO.getAllSecondPOS(con);
                        // dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,posAgents);
                        Vector batchDetails = SheetDAO.getBatchesByFilter(con, batchId, "", "", "*", "*");
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, batchDetails);
                        Vector sheetWarningTypes = SheetDAO.getAllSheetWarningType(con);
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetWarningTypes);
                        // SheetDAO.closeSheetsperBatch(con,batchId);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case VIEW_PENDING_BATCH_SHEETS :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID, strAgentId);
                        String batchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID, batchId);
                        Vector sheets = SheetDAO.getSheetsByFilter(con, batchId, 0, "", "", "", "");
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheets);
                        Vector batchDetails = SheetDAO.getBatchesByFilter(con, batchId, "", "", "*", "*");
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, batchDetails);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case MANAGE_PENDING_BATCHES_VIEW_SHEETS :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID, strAgentId);
                        String batchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID, batchId);
                        Vector sheets = SheetDAO.getSheetsByFilter(con, batchId, 0, "", "", "", "");
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheets);
                        Vector batchDetails = SheetDAO.getBatchesByFilter(con, batchId, "", "", "*", "*");
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, batchDetails);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case SAVE_PENDING_SHEET :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID, strAgentId);
                        String batchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID, batchId);

                        String sheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL);
                        String sheetId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID);
                        String sheetSIMCount = (String) paramHashMap.get(SFRInterfaceKey.INPUT_TEXT_SHEET_SIM_COUNT);
                        SheetDAO.updateSheet(con, sheetSerial, sheetSIMCount, sheetId);

                        Vector sheets = SheetDAO.getSheetsByFilter(con, batchId, 0, "", "", "", "");
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheets);
                        Vector batchDetails = SheetDAO.getBatchesByFilter(con, batchId, "", "", "*", "*");
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, batchDetails);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case DELETE_PENDING_SHEET :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID, strAgentId);

                        String batchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID, batchId);

                        String sheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL);
                        String sheetId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID);
                        SheetDAO.deleteSheet(con, sheetSerial, sheetId);

                        Vector sheets = SheetDAO.getSheetsByFilter(con, batchId, 0, "", "", "", "");
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheets);
                        Vector batchDetails = SheetDAO.getBatchesByFilter(con, batchId, "", "", "*", "*");
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, batchDetails);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case SFR_UPDATE_SHEET_STATUS :
                    try
                    {
                        dataHashMap = new HashMap();
                        String flag = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_FLAG);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_FLAG, flag);
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String batchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID, batchId);
                        String sheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL);
                        String sheetId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID);
                        String sheetNextStatus = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID);

                        SheetDAO.updateSheetStatus(con, sheetId, sheetNextStatus, strUserID);

                        String strSearchSheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL, strSearchSheetSerial);
                        String strPosAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID, strPosAgentId);
                        String strSheetStatusTypeId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID, strSheetStatusTypeId);
                        String strPosCode = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_POS_CODE);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_POS_CODE, strPosCode);

                        int intSearchSheetSerial = 0;
                        if (strSearchSheetSerial.compareTo("") != 0)
                            intSearchSheetSerial = Integer.parseInt(strSearchSheetSerial);

                        Vector sheets = SheetDAO.getSheetsByFilter(con, batchId, intSearchSheetSerial, strPosAgentId, strSheetStatusTypeId, strPosCode, "");
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheets);
                        Vector sheetStatusTypes = SheetDAO.getAllSheetStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetStatusTypes);
                        // Vector posAgents = SheetDAO.getAllSecondPOS(con);
                        // dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,posAgents);
                        Vector batchDetails = SheetDAO.getBatchesByFilter(con, batchId, "", "", "*", "*");
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, batchDetails);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case ADMIN_SFR_UPDATE_SHEET_STATUS :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String batchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID, batchId);

                        String sheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL);
                        String sheetId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID);
                        String sheetNextStatus = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID);
                        int intSheetId = Integer.parseInt(sheetId);

                        SheetDAO.updateSheetStatus(con, sheetId, sheetNextStatus, strUserID);

                        String sheetWarningId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SELECT_SHEET_WARNING_ID
                                + "_" + sheetSerial);
                        if (sheetWarningId != null)
                        {
                            if (sheetWarningId.compareTo("") != 0)
                            {
                                SheetDAO.insertSheetWarning(con, intSheetId, sheetWarningId, strUserID);
                            }
                        }

                        String strSearchSheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL, strSearchSheetSerial);
                        String strPosAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID, strPosAgentId);
                        String strSheetStatusTypeId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID, strSheetStatusTypeId);
                        String strPosCode = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_POS_CODE);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_POS_CODE, strPosCode);

                        int intSearchSheetSerial = 0;
                        if (strSearchSheetSerial.compareTo("") != 0)
                            intSearchSheetSerial = Integer.parseInt(strSearchSheetSerial);

                        Vector sheets = SheetDAO.getSheetsByFilter(con, batchId, intSearchSheetSerial, strPosAgentId, strSheetStatusTypeId, strPosCode, "");
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheets);
                        Vector sheetStatusTypes = SheetDAO.getAllSheetStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetStatusTypes);
                        // Vector posAgents = SheetDAO.getAllSecondPOS(con);
                        // dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,posAgents);
                        Vector batchDetails = SheetDAO.getBatchesByFilter(con, batchId, "", "", "*", "*");
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, batchDetails);
                        Vector sheetWarningTypes = SheetDAO.getAllSheetWarningType(con);
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetWarningTypes);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case ADMIN_CLOSE_SHEETS_PER_BATCH :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String batchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID, batchId);

                        String sheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL);
                        String sheetId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID);
                        String sheetNextStatus = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID);
                        // int intSheetId = Integer.parseInt(sheetId);

                        int closeFlag = SheetDAO.closeSheetsperBatch(con, batchId, strUserID);

                        if (closeFlag == 0)
                        {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "There is no sheets can be close in this batch");
                        } else if (closeFlag == 3)
                        {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "There is no sheets can be close in this batch without adding warning");
                        }

                        /*
                         * String sheetWarningId =
                         * (String)paramHashMap.get(SFRInterfaceKey
                         * .INPUT_SELECT_SHEET_WARNING_ID+"_"+sheetSerial);
                         * if(sheetWarningId != null) {
                         * if(sheetWarningId.compareTo("")!=0) {
                         * SheetDAO.insertSheetWarning
                         * (con,intSheetId,sheetWarningId,strUserID); } }
                         */

                        String strSearchSheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL, strSearchSheetSerial);
                        String strPosAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID, strPosAgentId);
                        String strSheetStatusTypeId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID, strSheetStatusTypeId);
                        String strPosCode = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_POS_CODE);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_POS_CODE, strPosCode);

                        int intSearchSheetSerial = 0;
                        if (strSearchSheetSerial.compareTo("") != 0)
                            intSearchSheetSerial = Integer.parseInt(strSearchSheetSerial);

                        Vector sheets = SheetDAO.getSheetsByFilter(con, batchId, intSearchSheetSerial, strPosAgentId, strSheetStatusTypeId, strPosCode, "");
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheets);
                        Vector sheetStatusTypes = SheetDAO.getAllSheetStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetStatusTypes);
                        // Vector posAgents = SheetDAO.getAllSecondPOS(con);
                        // dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,posAgents);
                        Vector batchDetails = SheetDAO.getBatchesByFilter(con, batchId, "", "", "*", "*");
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, batchDetails);
                        Vector sheetWarningTypes = SheetDAO.getAllSheetWarningType(con);
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetWarningTypes);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case ADMIN_REJECT_SHEETS_PER_BATCH :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String batchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID, batchId);

                        String sheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL);
                        String sheetId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID);
                        String sheetNextStatus = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID);
                        // int intSheetId = Integer.parseInt(sheetId);

                        int rejectFlag = SheetDAO.rejectSheetsperBatch(con, batchId, strUserID);
                        if (rejectFlag != 2)
                        {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "There is no sheets can be rejected in this batch");
                        }
                        // String strRejectFalg = Integer.toString(rejectFalg);

                        // dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_STATUS_FLAG,strRejectFalg);
                        // System.out.println("The Flag issssssssssssss" +
                        // strRejectFalg);

                        /*
                         * String sheetWarningId =
                         * (String)paramHashMap.get(SFRInterfaceKey
                         * .INPUT_SELECT_SHEET_WARNING_ID+"_"+sheetSerial);
                         * if(sheetWarningId != null) {
                         * if(sheetWarningId.compareTo("")!=0) {
                         * SheetDAO.insertSheetWarning
                         * (con,intSheetId,sheetWarningId,strUserID); } }
                         */

                        String strSearchSheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL, strSearchSheetSerial);
                        String strPosAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID, strPosAgentId);
                        String strSheetStatusTypeId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID, strSheetStatusTypeId);
                        String strPosCode = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_POS_CODE);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_POS_CODE, strPosCode);

                        int intSearchSheetSerial = 0;
                        if (strSearchSheetSerial.compareTo("") != 0)
                            intSearchSheetSerial = Integer.parseInt(strSearchSheetSerial);

                        Vector sheets = SheetDAO.getSheetsByFilter(con, batchId, intSearchSheetSerial, strPosAgentId, strSheetStatusTypeId, strPosCode, "");
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheets);
                        Vector sheetStatusTypes = SheetDAO.getAllSheetStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetStatusTypes);
                        // Vector posAgents = SheetDAO.getAllSecondPOS(con);
                        // dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,posAgents);
                        Vector batchDetails = SheetDAO.getBatchesByFilter(con, batchId, "", "", "*", "*");
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, batchDetails);
                        Vector sheetWarningTypes = SheetDAO.getAllSheetWarningType(con);
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetWarningTypes);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case SEARCH_SHEETS :
                    try
                    {
                        dataHashMap = new HashMap();
                        String flag = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_FLAG);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_FLAG, flag);
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String batchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID, batchId);

                        String strSearchSheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL, strSearchSheetSerial);
                        String strPosAgentCode = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID, strPosAgentCode);
                        String strSheetStatusTypeId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID, strSheetStatusTypeId);
                        String strPosCode = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_POS_CODE);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_POS_CODE, strPosCode);

                        int intSearchSheetSerial = 0;
                        if (strSearchSheetSerial.compareTo("") != 0)
                            intSearchSheetSerial = Integer.parseInt(strSearchSheetSerial);

                        Vector sheets = SheetDAO.getSheetsByFilter(con, batchId, intSearchSheetSerial, strPosAgentCode, strSheetStatusTypeId, strPosCode, "");
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheets);
                        Vector sheetStatusTypes = SheetDAO.getAllSheetStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetStatusTypes);
                        // Vector posAgents = SheetDAO.getAllSecondPOS(con);
                        // dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,posAgents);
                        Vector batchDetails = SheetDAO.getBatchesByFilter(con, batchId, "", "", "*", "*");
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, batchDetails);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case ADMIN_SEARCH_SHEETS :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String batchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID, batchId);

                        String strSearchSheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL, strSearchSheetSerial);
                        String strPosAgentCode = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID, strPosAgentCode);
                        String strSheetStatusTypeId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID, strSheetStatusTypeId);
                        String strPosCode = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_POS_CODE);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_POS_CODE, strPosCode);

                        int intSearchSheetSerial = 0;
                        if (strSearchSheetSerial.compareTo("") != 0)
                            intSearchSheetSerial = Integer.parseInt(strSearchSheetSerial);

                        Vector sheets = SheetDAO.getSheetsByFilter(con, batchId, intSearchSheetSerial, strPosAgentCode, strSheetStatusTypeId, strPosCode, "");
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheets);
                        Vector sheetStatusTypes = SheetDAO.getAllSheetStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetStatusTypes);
                        Vector batchDetails = SheetDAO.getBatchesByFilter(con, batchId, "", "", "*", "*");
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, batchDetails);
                        Vector sheetWarningTypes = SheetDAO.getAllSheetWarningType(con);
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetWarningTypes);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case VIEW_SHEET_CONTRACTS_SIM_NUMBER :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String flag = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_FLAG);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_FLAG, flag);
                        String batchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID, batchId);
                        String sheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL, sheetSerial);
                        String sheetId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID, sheetId);
                        String sheetSIMGroup = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP, sheetSIMGroup);

                        Vector SIMList = SheetDAO.getSIMsBySheet(con, sheetId, sheetSIMGroup);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, SIMList);
                        String sheetStatusTypeId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID, sheetStatusTypeId);

                        int intSheetSerial = Integer.parseInt(sheetSerial);
                        Vector sheetDetail = SheetDAO.getSheetsByFilter(con, batchId, intSheetSerial, "", "", "", sheetId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetDetail);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case ADMIN_VIEW_SHEET_CONTRACTS_SIM_NUMBER :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String batchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID, batchId);
                        String sheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL, sheetSerial);
                        String sheetId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID, sheetId);
                        String sheetSIMGroup = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP, sheetSIMGroup);
                        Vector SIMList = SheetDAO.getSIMsBySheet(con, sheetId, sheetSIMGroup);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, SIMList);
                        String sheetStatusTypeId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID, sheetStatusTypeId);

                        int intSheetSerial = Integer.parseInt(sheetSerial);
                        Vector sheetDetail = SheetDAO.getSheetsByFilter(con, batchId, intSheetSerial, "", "", "", sheetId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetDetail);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case SAVE_SHEET_CONTRACTS_SIM_NUMBER :

                    try
                    {

                        dataHashMap = new HashMap();
                        String flag = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_FLAG);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_FLAG, flag);
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String batchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID, batchId);
                        String sheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL, sheetSerial);
                        String sheetId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID, sheetId);

                        String sheetSIMGroup = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP, sheetSIMGroup);

                        Vector SIMList = SheetDAO.getSIMsBySheet(con, sheetId, sheetSIMGroup);

                        String rowsCount = (String) paramHashMap.get("user_defined_data_view_count");
                        int intRowsCount = Integer.parseInt(rowsCount);
                        // SheetDAO.deleteSIMBySheet(con,sheetSerial);
                        Vector vecSimId = new Vector();
                        String errMsg = "";

                        for (int i = 1; i <= intRowsCount; i++)
                        {
                            if (paramHashMap.get("user_defined_data_view__R"
                                    + i + "__C2") != null)
                            {
                                Long lsimId = null;
                                String strHiddenValue = (String) paramHashMap.get("user_defined_data_view__R"
                                        + i + "__C3");
                                vecSimId.add(strHiddenValue);
                                String contractSIMNumber = (String) paramHashMap.get("user_defined_data_view__R"
                                        + i + "__C1");
                                boolean simExists = SheetDAO.simExists(con, contractSIMNumber, SFRInterfaceKey.CONST_SIM_STATUS_TYPE_ACCEPTED);
                                String simStatusTypeId = "";
                                if (!simExists)
                                {
                                    simStatusTypeId = SFRInterfaceKey.CONST_SIM_STATUS_TYPE_ACCEPTED;
                                } else
                                {
                                    simStatusTypeId = SFRInterfaceKey.CONST_SIM_STATUS_TYPE_REJECTED;
                                }

                                if (strHiddenValue.compareTo("") == 0)
                                {

                                    // hagry
                                    // Vector simWarnings =
                                    // SheetDAO.validateSIMFromLCS(con,contractSIMNumber,sheetId);
                                    Vector simWarnings = new Vector();
                                    lsimId = SheetDAO.insertSIM(con, contractSIMNumber, sheetId, simStatusTypeId);
                                    if (simStatusTypeId.compareTo(SFRInterfaceKey.CONST_SIM_STATUS_TYPE_REJECTED) == 0)
                                    {
                                        if (errMsg.compareTo("") == 0)
                                        {
                                            errMsg += "SIM Serial exists with accepted status :"
                                                    + contractSIMNumber + " ";
                                        } else
                                        {
                                            errMsg += "," + contractSIMNumber
                                                    + " ";
                                        }
                                    }
                                } else
                                {
                                    int indexOfSplit = strHiddenValue.indexOf("_");
                                    String strSimId = strHiddenValue.substring(0, indexOfSplit);
                                    String oldContractSIMnumber = strHiddenValue.substring(indexOfSplit + 1, strHiddenValue.length());
                                    if (oldContractSIMnumber.compareTo(contractSIMNumber) != 0)
                                    {
                                        SheetDAO.updateSIM(con, contractSIMNumber, strSimId, simStatusTypeId);
                                        if (simStatusTypeId.compareTo(SFRInterfaceKey.CONST_SIM_STATUS_TYPE_REJECTED) == 0)
                                        {
                                            if (errMsg.compareTo("") == 0)
                                            {
                                                errMsg += "SIM Serial exists with accepted status :"
                                                        + contractSIMNumber
                                                        + " ";
                                            } else
                                            {
                                                errMsg += ","
                                                        + contractSIMNumber
                                                        + " ";
                                            }
                                        }
                                        lsimId = Long.valueOf(strSimId);
                                    }
                                }
                                if (lsimId != null)
                                {
                                    SheetDAO.insertSIMStatus(con, contractSIMNumber, simStatusTypeId, strUserID, lsimId);
                                }
                            }
                        }

                        if (errMsg.compareTo("") != 0)
                        {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, errMsg);
                        }

                        for (int j = 0; j < SIMList.size(); j++)
                        {
                            boolean deleteFlag = true;
                            SIMModel simModel = (SIMModel) SIMList.get(j);
                            String strSimIdJ = simModel.getSimId();
                            for (int k = 0; k < vecSimId.size(); k++)
                            {
                                String strHiddenValueK = (String) vecSimId.get(k);
                                if (strHiddenValueK.compareTo("") != 0)
                                {
                                    int indexOfSplit = strHiddenValueK.indexOf("_");
                                    String strSimIdK = strHiddenValueK.substring(0, indexOfSplit);
                                    if (strSimIdK.compareTo(strSimIdJ) == 0)
                                    {
                                        deleteFlag = false;
                                    }
                                }
                            }
                            if (deleteFlag)
                            {
                                SheetDAO.deleteSIMBySheet(con, sheetId, strSimIdJ);
                            }
                        }

                        Vector sheets = SheetDAO.getSheetsByFilter(con, batchId, 0, "", "", "", "");

                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheets);
                        Vector sheetStatusTypes = SheetDAO.getAllSheetStatusType(con);

                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetStatusTypes);
                        // Vector posAgents = SheetDAO.getAllSecondPOS(con);

                        // dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,posAgents);
                        Vector batchDetails = SheetDAO.getBatchesByFilter(con, batchId, "", "", "*", "*");

                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, batchDetails);

                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }

                    break;
                case ADMIN_SAVE_SHEET_CONTRACTS_SIM_NUMBER :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String batchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID, batchId);
                        String sheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL, sheetSerial);
                        String sheetId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID, sheetId);
                        String sheetSIMGroup = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP, sheetSIMGroup);

                        Vector SIMList = SheetDAO.getSIMsBySheet(con, sheetId, sheetSIMGroup);

                        String rowsCount = (String) paramHashMap.get("user_defined_data_view_count");
                        int intRowsCount = Integer.parseInt(rowsCount);
                        // SheetDAO.deleteSIMBySheet(con,sheetSerial);
                        Vector vecSimId = new Vector();
                        String errMsg = "";
                        for (int i = 1; i <= intRowsCount; i++)
                        {
                            if (paramHashMap.get("user_defined_data_view__R"
                                    + i + "__C2") != null)
                            {
                                Long lsimId = null;
                                String strHiddenValue = (String) paramHashMap.get("user_defined_data_view__R"
                                        + i + "__C3");
                                vecSimId.add(strHiddenValue);
                                String contractSIMNumber = (String) paramHashMap.get("user_defined_data_view__R"
                                        + i + "__C1");
                                boolean simExists = SheetDAO.simExists(con, contractSIMNumber, SFRInterfaceKey.CONST_SIM_STATUS_TYPE_ACCEPTED);
                                String simStatusTypeId = "";
                                if (!simExists)
                                {
                                    simStatusTypeId = SFRInterfaceKey.CONST_SIM_STATUS_TYPE_ACCEPTED;
                                } else
                                {
                                    simStatusTypeId = SFRInterfaceKey.CONST_SIM_STATUS_TYPE_REJECTED;
                                }

                                if (strHiddenValue.compareTo("") == 0)
                                {
                                    Vector simWarnings = new Vector(); // SheetDAO.validateSIMFromLCS(con,contractSIMNumber,sheetId);
                                    lsimId = SheetDAO.insertSIM(con, contractSIMNumber, sheetId, simStatusTypeId);
                                    if (simStatusTypeId.compareTo(SFRInterfaceKey.CONST_SIM_STATUS_TYPE_REJECTED) == 0)
                                    {
                                        if (errMsg.compareTo("") == 0)
                                        {
                                            errMsg += "SIM Serial exists with accepted status :"
                                                    + contractSIMNumber + " ";
                                        } else
                                        {
                                            errMsg += "," + contractSIMNumber
                                                    + " ";
                                        }
                                    }
                                } else
                                {
                                    int indexOfSplit = strHiddenValue.indexOf("_");
                                    String strSimId = strHiddenValue.substring(0, indexOfSplit);
                                    String oldContractSIMnumber = strHiddenValue.substring(indexOfSplit + 1, strHiddenValue.length());
                                    if (oldContractSIMnumber.compareTo(contractSIMNumber) != 0)
                                    {
                                        SheetDAO.updateSIM(con, contractSIMNumber, strSimId, simStatusTypeId);
                                        if (simStatusTypeId.compareTo(SFRInterfaceKey.CONST_SIM_STATUS_TYPE_REJECTED) == 0)
                                        {
                                            if (errMsg.compareTo("") == 0)
                                            {
                                                errMsg += "SIM Serial exists with accepted status :"
                                                        + contractSIMNumber
                                                        + " ";
                                            } else
                                            {
                                                errMsg += ","
                                                        + contractSIMNumber
                                                        + " ";
                                            }
                                        }
                                        lsimId = Long.valueOf(strSimId);
                                    }
                                }
                                if (lsimId != null)
                                {
                                    SheetDAO.insertSIMStatus(con, contractSIMNumber, simStatusTypeId, strUserID, lsimId);
                                }
                            }
                        }
                        if (errMsg.compareTo("") != 0)
                        {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, errMsg);
                        }

                        for (int j = 0; j < SIMList.size(); j++)
                        {
                            boolean deleteFlag = true;
                            SIMModel simModel = (SIMModel) SIMList.get(j);
                            String strSimIdJ = simModel.getSimId();
                            for (int k = 0; k < vecSimId.size(); k++)
                            {
                                String strHiddenValueK = (String) vecSimId.get(k);
                                if (strHiddenValueK.compareTo("") != 0)
                                {
                                    int indexOfSplit = strHiddenValueK.indexOf("_");
                                    String strSimIdK = strHiddenValueK.substring(0, indexOfSplit);
                                    if (strSimIdK.compareTo(strSimIdJ) == 0)
                                    {
                                        deleteFlag = false;
                                    }
                                }
                            }
                            if (deleteFlag)
                            {
                                SheetDAO.deleteSIMBySheet(con, sheetId, strSimIdJ);
                            }
                        }

                        Vector sheets = SheetDAO.getSheetsByFilter(con, batchId, 0, "", "", "", "");
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheets);
                        Vector sheetStatusTypes = SheetDAO.getAllSheetStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetStatusTypes);
                        // Vector posAgents = SheetDAO.getAllSecondPOS(con);
                        // dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,posAgents);
                        Vector batchDetails = SheetDAO.getBatchesByFilter(con, batchId, "", "", "*", "*");
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, batchDetails);
                        Vector sheetWarningTypes = SheetDAO.getAllSheetWarningType(con);
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetWarningTypes);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case VIEW_POS_SHEET_HISTORY :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        // Vector sheetStatus =
                        // SheetDAO.getSheetsStatusByFilter(con,"","","","*","*");
                        Vector sheetStatus = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetStatus);
                        Vector sheetStatusTypes = SheetDAO.getAllSheetStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetStatusTypes);
                        // Vector posAgents = SheetDAO.getAllSecondPOS(con);
                        Vector posAgents = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, posAgents);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case SEARCH_SHEET_STATUS :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

                        String strSearchSheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL, strSearchSheetSerial);
                        // String strPosAgentId =
                        // (String)paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID);
                        // dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID,strPosAgentId);
                        String strPosAgentId = "";
                        // String strSheetStatusTypeId =
                        // (String)paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID);
                        // dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID,strSheetStatusTypeId);
                        String strSheetStatusTypeId = "";
                        // String strSheetChangeDateFrom =
                        // (String)paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_FROM);
                        // dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_FROM,strSheetChangeDateFrom);
                        String strSheetChangeDateFrom = "*";
                        // String strSheetChangeDateTo =
                        // (String)paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_TO);
                        // dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_TO,strSheetChangeDateTo);
                        String strSheetChangeDateTo = "*";

                        Vector sheetStatus = SheetDAO.getSheetsStatusByFilter(con, strSearchSheetSerial, strPosAgentId, strSheetStatusTypeId, strSheetChangeDateFrom, strSheetChangeDateTo);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetStatus);
                        Vector sheetStatusTypes = SheetDAO.getAllSheetStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetStatusTypes);
                        // Vector posAgents = SheetDAO.getAllSecondPOS(con);
                        Vector posAgents = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, posAgents);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case VIEW_POS_SIM_HISTROY :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

                        // Vector simStatus = new Vector();
                        // dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,simStatus);

                        Vector simStatusTypes = SheetDAO.getAllSIMStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, simStatusTypes);

                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case SEARCH_SIM_STATUS :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

                        String strSearchSIMSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_SIM_SERIAL);
                        dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_SIM_SERIAL, strSearchSIMSerial);
                        // String strSearchSheetSerial =
                        // (String)paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL);
                        // dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL,strSearchSheetSerial);
                        String strSearchSheetSerial = "";
                        // String strSearchBatchId =
                        // (String)paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID);
                        // dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID,strSearchBatchId);
                        String strSearchBatchId = "";
                        // String strSearchSIMStatusTypeId =
                        // (String)paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SIM_STATUS_ID);
                        // dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_SIM_STATUS_ID,strSearchSIMStatusTypeId);
                        String strSearchSIMStatusTypeId = "";
                        // String strSearchContarctTypeId =
                        // (String)paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_CONTRACT_TYPE_ID);
                        // dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_CONTRACT_TYPE_ID,strSearchContarctTypeId);
                        // String strSearchChangeByUserId =
                        // (String)paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_USER_ID);
                        // dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_USER_ID,strSearchChangeByUserId);
                        String strSearchChangeByUserId = "";
                        // String strSearchSIMChangeDateFrom =
                        // (String)paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_FROM);
                        // dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_FROM,strSearchSIMChangeDateFrom);
                        String strSearchSIMChangeDateFrom = "*";
                        // String strSearchSIMChangeDateTo =
                        // (String)paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_TO);
                        // dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_TO,strSearchSIMChangeDateTo);
                        String strSearchSIMChangeDateTo = "*";
                        // String strSearchSIMActivationDateFrom =
                        // (String)paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SIM_ACTIVATION_DATE_FROM);
                        // dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_SIM_ACTIVATION_DATE_FROM,strSearchSIMActivationDateFrom);
                        String strSearchSIMActivationDateFrom = "*";
                        // String strSearchSIMActivationDateTo =
                        // (String)paramHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SIM_ACTIVATION_DATE_TO);
                        // dataHashMap.put(SFRInterfaceKey.INPUT_SERACH_SELECT_SIM_ACTIVATION_DATE_TO,strSearchSIMActivationDateTo);
                        String strSearchSIMActivationDateTo = "*";

                        Vector simStatus = SheetDAO.getSIMStatusByFilter(con, strSearchSIMSerial, strSearchSheetSerial, strSearchBatchId, strSearchSIMStatusTypeId, strSearchChangeByUserId, strSearchSIMChangeDateFrom, strSearchSIMChangeDateTo, strSearchSIMActivationDateFrom, strSearchSIMActivationDateTo);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, simStatus);
                        Vector simStatusTypes = SheetDAO.getAllSIMStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, simStatusTypes);

                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case EDIT_PENDING_SHEET :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strAgentId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID, strAgentId);
                        String batchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID, batchId);
                        String sheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL, sheetSerial);
                        String sheetId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID, sheetId);
                        int intSheetSerial = Integer.parseInt(sheetSerial);
                        Vector sheetDetail = SheetDAO.getSheetsByFilter(con, batchId, intSheetSerial, "", "", "", sheetId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetDetail);
                        Vector batchDetails = SheetDAO.getBatchesByFilter(con, batchId, "", "", "*", "*");
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, batchDetails);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case ADMIN_PENDING_BATCHES_SELECT_AGENT :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case SFR_VIEW_IMPORT_LOG :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case SFR_SEARCH_IMPORT_LOG :
                    try
                    {
                        Hashtable additionalCollection = new Hashtable();
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

                        String date = (String) paramHashMap.get(SFRInterfaceKey.INPUT_IMPORT_LOG_DATE);
                        additionalCollection.put(SFRInterfaceKey.INPUT_IMPORT_LOG_DATE, date);

                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalCollection);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, SheetDAO.SearchImportLog(date, con));

                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case VIEW_SHEET_WARNINGS :
                    try
                    {
                        Hashtable additionalCollection = new Hashtable();
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String sheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL, sheetSerial);
                        String sheetId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID, sheetId);
                        int intSheetSerial = Integer.parseInt(sheetSerial);
                        Vector sheetDetail = SheetDAO.getSheetsByFilter(con, "", intSheetSerial, "", "", "", sheetId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetDetail);
                        Vector sheetWarnings = SheetDAO.getSheetWarnings(con, sheetId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetWarnings);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                    
               
                case SFR_MANAGE_SHEET_WARNINGS :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector sheetWarningTypes = SheetDAO.getAllSheetWarningType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetWarningTypes);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case SFR_EDIT_SHEET_WARNING :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strSheetWarningTypeID = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_WARNING_TYPE_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_WARNING_TYPE_ID, strSheetWarningTypeID);
                        SheetWarningTypeModel sheetWarningTypeModel = SheetDAO.getSheetWarningTypeById(con, strSheetWarningTypeID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetWarningTypeModel);
                        Vector sheetStatusTypes = SheetDAO.getAllSheetStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetStatusTypes);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case SFR_SAVE_SHEET_WARNING :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

                        String warning = (String) paramHashMap.get(SFRInterfaceKey.INPUT_TEXT_WARNING);
                        String warningSuggestedStatusId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_SELECT_SHEET_STATUS_ID);

                        String strSqlOperation = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SQL_OPERATION);
                        if (strSqlOperation.compareTo("INSERT") == 0)
                        {
                            SheetDAO.insertSheetWarningType(con, warning, warningSuggestedStatusId);
                        } else if (strSqlOperation.compareTo("UPDATE") == 0)
                        {
                            String warningTypeId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_WARNING_TYPE_ID);
                            SheetDAO.updateSheetWarningType(con, warningTypeId, warning, warningSuggestedStatusId);
                        }

                        Vector sheetWarningTypes = SheetDAO.getAllSheetWarningType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetWarningTypes);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case SFR_ADD_SHEET_WARNING :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector sheetStatusTypes = SheetDAO.getAllSheetStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetStatusTypes);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case SFR_DELETE_SHEET_WARNING :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strSheetWarningTypeID = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_WARNING_TYPE_ID);
                        SheetDAO.deleteSheetWarningType(con, strSheetWarningTypeID);

                        Vector sheetWarningTypes = SheetDAO.getAllSheetWarningType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheetWarningTypes);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case ADMIN_VIEW_SHEET_DETAILS :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String batchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID, batchId);

                        String sheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL);
                        int intSheetSerial = Integer.parseInt(sheetSerial);
                        String sheetId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID);
                        Vector sheets = SheetDAO.getSheetsByFilter(con, batchId, intSheetSerial, "", "", "", sheetId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheets);
                        Vector sheetStatusTypes = SheetDAO.getAllSheetStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetStatusTypes);
                        Vector batchDetails = SheetDAO.getBatchesByFilter(con, batchId, "", "", "*", "*");
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, batchDetails);
                        Vector sheetWarningTypes = SheetDAO.getAllSheetWarningType(con);
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetWarningTypes);
                        Vector sheetWarnings = SheetDAO.getSheetWarnings(con, sheetId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, sheetWarnings);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case ADMIN_SHEET_DETAIL_UPDATE_STATUS :
                    try
                    {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String batchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID, batchId);

                        String sheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL);
                        String sheetId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID);
                        String sheetNextStatus = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID);
                        int intSheetSerial = Integer.parseInt(sheetSerial);
                        int intSheetId = Integer.parseInt(sheetId);

                        SheetDAO.updateSheetStatus(con, sheetId, sheetNextStatus, strUserID);

                        for (int j = 0; j < paramHashMap.size(); j++)
                        {
                            String tempStatusString = (String) paramHashMap.keySet().toArray()[j];
                            String keyValue = (String) paramHashMap.get(tempStatusString);
                            if (tempStatusString.startsWith(SFRInterfaceKey.INPUT_SELECT_SHEET_WARNING_ID))
                            {
                                int strlength = SFRInterfaceKey.INPUT_SELECT_SHEET_WARNING_ID.length() + 1;
                                String strWarningTypeId = tempStatusString.substring(strlength, tempStatusString.length());
                                SheetDAO.insertSheetWarning(con, intSheetId, strWarningTypeId, strUserID);
                            }
                        }

                        Vector sheets = SheetDAO.getSheetsByFilter(con, batchId, 0, "", "", "", "");
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheets);
                        Vector sheetStatusTypes = SheetDAO.getAllSheetStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetStatusTypes);
                        // Vector posAgents = SheetDAO.getAllSecondPOS(con);
                        // dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,posAgents);
                        Vector batchDetails = SheetDAO.getBatchesByFilter(con, batchId, "", "", "*", "*");
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, batchDetails);
                        Vector sheetWarningTypes = SheetDAO.getAllSheetWarningType(con);
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetWarningTypes);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;
                case VIEW_CHANGE_BATCH_DATE :
                {
                    dataHashMap = new HashMap();
                    String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    
                    dataHashMap.put(SFRInterfaceKey.CONTROL_HIDDEN_MSG, "");
                }
                case CHANGE_BATCH_DATE :
                {
                    dataHashMap = new HashMap();
                    String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    String fromBatchId = (String) paramHashMap.get(SFRInterfaceKey.CONTROL_FROM_BATCH_ID);
                    String toBatchId = (String) paramHashMap.get(SFRInterfaceKey.CONTROL_TO_BATCH_ID);

                    String batchDate = (String) paramHashMap.get(SFRInterfaceKey.GENERATE_BATCH_DATE);

                    System.out.println("from batch id=" + fromBatchId);
                    System.out.println("to batch id=" + fromBatchId);
                    
                    String closedBatchIDList = "";
                    String msg = "";
                   
                    for (int i = Integer.parseInt(fromBatchId); i <= Integer.parseInt(toBatchId); i++)
                    {
                        if (SheetDAO.checkBatchExist(con, String.valueOf(i)))
                        {
                        	 String date = SheetDAO.getSFRBatchDate(con,String.valueOf(i)).split(" ")[0];
                        	 System.out.println(date);
                        	 String month = date.split("-")[1]  ;
                        	 if(month.startsWith("0"))
                        	 {
                        	  month = month.charAt(1)+"";
                        	 }
                        		 
                        	 msg = SheetDAO.selectSFRBatchStatus(date.split("-")[0], month , con);
                        
                        	 if(msg.equals("") || msg == null)  /// batch still open and can be deleted
                        	 {	
                        	  SheetDAO.insertBatchHistory(String.valueOf(i), strUserID, "UPDATE", con); 
                        	  SheetDAO.chanegBatchDateByID(con, String.valueOf(i), batchDate);
                              dataHashMap.put(SFRInterfaceKey.ACTION_CHANGE_BATCH_DATE_FAILED, "ok");

                        	 }
                        	 else
                        	 {
                        		closedBatchIDList = closedBatchIDList +  String.valueOf(i) + "-";
                        			
                        	 }
                        }
                    }
                    dataHashMap.put(SFRInterfaceKey.CONTROL_HIDDEN_MSG, closedBatchIDList);
                    // }else{
                    // dataHashMap.put(SFRInterfaceKey.ACTION_CHANGE_BATCH_DATE_FAILED,
                    // "failed");
                    // }
                }
                    break;
                case ACTION_VIEW_DELETE_BATCH :
                {
                    dataHashMap = new HashMap();
                    String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    dataHashMap.put(SFRInterfaceKey.CONTROL_HIDDEN_MSG, "");
                }
                    break;
                case ACTION_DELETE_BATCH :
                {
                    dataHashMap = new HashMap();
                    String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String batchId = (String) paramHashMap.get(SFRInterfaceKey.CONTROL_HIDDEN_BATCH_ID);

                    if (SheetDAO.checkBatchExist(con, batchId))
                    {
                    	 String date = SheetDAO.getSFRBatchDate(con,batchId).split(" ")[0];
                    	 System.out.println(date);
                    	 String month = date.split("-")[1]  ;
                    	 if(month.startsWith("0"))
                    	 {
                    	  month = month.charAt(1)+"";
                    	 }
                    		 
                    	 String msg = SheetDAO.selectSFRBatchStatus(date.split("-")[0], month , con);
                    
                    	 if(msg.equals("") || msg == null)  /// batch still open and can be deleted
                    	 {
                           SheetDAO.insertBatchHistory(batchId, strUserID, "DELETE", con);
                           SheetDAO.deleteBatch(batchId, con);
                           dataHashMap.put(SFRInterfaceKey.ACTION_CHANGE_BATCH_DATE_FAILED, "ok");
                           dataHashMap.put(SFRInterfaceKey.CONTROL_HIDDEN_MSG, "Batch Deleted Successfully ..");
                    	 }
                    	 else
                    	 {
                    		 dataHashMap.put(SFRInterfaceKey.CONTROL_HIDDEN_MSG, "Sorry , Batch closed .. it can't be deleted .."); 
                    	 }
                    } else
                    {
                        dataHashMap.put(SFRInterfaceKey.ACTION_CHANGE_BATCH_DATE_FAILED, "failed");
                    }
                }

                    break;
                    
                case ACTION_DELETE_BATCH_SHEET :
                    try
                    {
                     //   Hashtable additionalCollection = new Hashtable();
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        
                        String sheetSerial = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL, sheetSerial);
                        
                        String sheetId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID, sheetId);
                        
                        int intSheetSerial = Integer.parseInt(sheetSerial);
                        
                        String batchId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
                        dataHashMap.put(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID, batchId);
                        
                        SheetDAO.deleteSheet(con, batchId ,intSheetSerial, sheetId);
                        SheetDAO.deleteSIM(con, sheetId);

                        
                       
                        
                        ////////////////////////// View same page data /////////////////////////////////
                        
                        Vector sheets = SheetDAO.getSheetsByFilter(con, batchId, 0, "", "", "", "");
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sheets);
                        Vector sheetStatusTypes = SheetDAO.getAllSheetStatusType(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetStatusTypes);
                        // Vector posAgents = SheetDAO.getAllSecondPOS(con);
                        // dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2,posAgents);
                        Vector batchDetails = SheetDAO.getBatchesByFilter(con, batchId, "", "", "*", "*");
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, batchDetails);
                        Vector sheetWarningTypes = SheetDAO.getAllSheetWarningType(con);
                        dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_ADDITIONAL_COLLECTION, sheetWarningTypes);
                        // SheetDAO.closeSheetsperBatch(con,batchId);
                        
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                    
                case ACTION_VIEW_MONTHS_MANAGEMENT:
                	 dataHashMap = new HashMap();
                	 String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                     dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                	 Vector sheetWarningTypes = SheetDAO.getAllMonths(con);
                     dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, sheetWarningTypes);
                     dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_ADDITIONAL_COLLECTION, "  ");
                     
                     
                	break;
                	
                case ACTION_VIEW_MONTHS_MANAGEMENT_SFR:
                	dataHashMap = new HashMap();
               	 String strUserIDD = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserIDD);
               	 Vector sheetWarningTypess = SheetDAO.getAllMonths(con);
               	 System.out.println("########### size : " + sheetWarningTypess.size());
                    dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, sheetWarningTypess);
                    dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_ADDITIONAL_COLLECTION, "  "); 
               	break;
               	
                case ADD_YEAR:
               	 dataHashMap = new HashMap();
               	 strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                 dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
               	 String year = (String) paramHashMap.get(SFRInterfaceKey.INPUT_YEAR);
               	 dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_ADDITIONAL_COLLECTION, SheetDAO.insertNewYear(year,con));
                 dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, SheetDAO.getAllMonths(con));
                 
                // dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_ADDITIONAL_COLLECTION, (String) paramHashMap.get(SFRInterfaceKey.INPUT_YEAR));
                
               	break;
               	
               	
                case ADD_YEAR_SFR:
                	dataHashMap = new HashMap();
                  	 strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                  	 String yearr = (String) paramHashMap.get(SFRInterfaceKey.INPUT_YEAR);
                  	 dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_ADDITIONAL_COLLECTION, SheetDAO.insertNewYear(yearr,con));
                    dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, SheetDAO.getAllMonths(con));
                    
                   // dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_ADDITIONAL_COLLECTION, (String) paramHashMap.get(SFRInterfaceKey.INPUT_YEAR));
                   
                  	break;
               	
               	
                case ACTION_CLOSE_MONTH_COMMISSION:
                  	 dataHashMap = new HashMap();
                  	 strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                     dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                  	 String commissionYear = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_YEAR);
                  	 String commissionMonth = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_MONTH);
                  	 String commissionStatusId = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_STATUS_ID);
                 	 String commissionStatus = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_STATUS);
                 	 dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                  	 SheetDAO.changeMonthStatus(strUserID , commissionStatusId ,commissionStatus , commissionMonth ,  commissionYear, con);
                  	 dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_ADDITIONAL_COLLECTION, "  ");
                     dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, SheetDAO.getAllMonths(con));
                   
                  	break;
               	
                case ACTION_CLOSE_MONTH_SFR:
                	 dataHashMap = new HashMap();
                  	 strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                     dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                  	 String commissionYearr = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_YEAR);
                  	 String commissionMonthh = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_MONTH);
                  	 String commissionStatusIdd = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_STATUS_ID);
                 	 String commissionStatuss = (String) paramHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_STATUS);
                 	 dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                  	 SheetDAO.changeMonthStatus(strUserID , commissionStatusIdd ,commissionStatuss , commissionMonthh ,  commissionYearr, con);
                  	 dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_ADDITIONAL_COLLECTION, "  ");
                     dataHashMap.put(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION, SheetDAO.getAllMonths(con));
                  
                 	break;
              	
                default :
                    Utility.logger.debug("Unknown action received: " + action);
            }

        } catch (Exception objExp)
        {
            objExp.printStackTrace();
        }
        return dataHashMap;
    }
}