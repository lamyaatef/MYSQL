
package com.mobinil.sds.core.facade.handlers;
import com.mobinil.sds.core.system.dcm.region.model.RegionModel;
import com.mobinil.sds.core.system.dcm.user.model.DCMUserModel;
import com.mobinil.sds.core.system.sa.persons.model.PersonModel;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;
import com.mobinil.sds.core.system.scm.dao.*;
import com.mobinil.sds.core.system.scm.dto.STKDistRequestViewerDTO;
import com.mobinil.sds.core.system.scm.model.*;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.ExcelUtil;
import com.mobinil.sds.core.utilities.GetUploadedFile;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.ss.usermodel.*;


public class SCMHandler {

    public static final int import_stk_list = 0;
    public static final int import_stk_process = 1;
    public static final int import_stk_pos = 2;
    public static final int save_stk_pos = 3;
    public static final int assign_dist = 4;
    public static final int dist_process = 5;
    public static final int action_import_pos_cbill = 6;
    public static final int action_import_pos_cbill_process = 7;
    public static final int pos_status_process = 9;
    public static final int pos_status_excel = 10;
    public static final int pos_status = 8;
    public static final int stk_stock_report = 11;
    public static final int barcode_request = 12;
    public static final int barcode_stock = 13;
    public static final int view_barcode_balance = 14;
    public static final int accept_barcode_request = 15;
    public static final int import_barcode_stock=16;
    public static final int action_import_iqrar_recieving=17;
    public static final int action_import_single_iqrar_recieving=18;
    public static final int action_import_many_iqrar_recieving_process=19;
    public static final int action_show_pos_status=20;
    public static final int pos_get_status=21;
    public static final int change_pos_status=22;
    public static final int change_pos_status_sheet=23;
    public static final int get_stk_status=24;
    public static final int change_stk_status=25;
    public static final int show_stk_status=26;
    public static final int show_stk_distributer=27;
    public static final int show_speific_distributer_stks=28;
    public static final int active_stk_distributer_and_export_excel=29;
    public static final int show_stk_stock_remaining=30;
    public static final int show_pos_status_history=331;
    public static final int get_pos_status_history=332;
    //POS Group Management
    public static final int action_view_pos_groups = 31;
    public static final int action_new_pos_group = 32;
    public static final int action_edit_pos_group = 33;
    public static final int action_add_new_pos_group = 34;
    public static final int action_update_pos_group = 35;
    public static final int action_delete_group = 36;
    public static final int action_assign_unassign_pos_to_group = 37;
    public static final int unassign_pos_from_group = 38;
    public static final int assign_pos_to_group = 39;
    public static final int assign_many_pos_to_group = 40;
    //POS Group Management
    //Rep Management --Start
    public static final int action_view_rep_management = 41;
    public static final int action_search_rep = 42;
    public static final int action_add_new_rep_sup = 43;
    public static final int action_update_rep_sup = 44;
    public static final int action_new_rep_sup = 45;
    public static final int action_edit_rep_sup = 46;
    public static final int action_get_region_governorates = 47;
    public static final int action_get_governorate_cities = 48;
    public static final int action_get_city_districts = 49;
    public static final int action_get_district_areas = 50;
    public static final int action_submit_user_level_type = 51;
    public static final int action_delete_rep_sup = 52;
    public static final int action_view_rep_detail = 53;
    public static final int action_view_sup_detail = 54;
    public static final int action_assign_rep_pos_group=55;
    public static final int action_rep_pos_group=56;
    public static final int action_unassign_rep_pos_group=57;
    public static final int action_rep_sup_assign=58;
    public static final int action_assign_rep_to_sup=59;
    
    public static final int action_unassign_rep_from_sup=60;
    
    public static final int action_assign_rep_to_supervisor=61;
    public static final int action_supervisor_rep_assign=62;
    public static final int action_unassign_rep_to_supervisor=63;
    public static final int action_gen_user_search=64;
    public static final int action_search_gen_user=65;
    public static final int accept_barcode_request_excel=66;
    public static final int active_stk_distributer_excel=67;
    public static final int active_stk_dist_excel_process=68;
    public static final int active_stk_one_distributer_and_export_excel=69;
    public static final int export_excel_in_stock_stk=70;
    public static final int view_barcoderequest_excel=71;
    public static final int download_barcoderequest_excel=72;
    public static final int select_stk_stock_type=73;
    public static final int view_stk_dist_request_excel=74;
    public static final int download_dist_request_excel=75;
    public static final int change_pos_payment_status=77;
    public static final int ACTIVE_DOWNLOAD_PREACTIVTE_EXCEL=76;
    public static final int view_stk_dist_excel_history=78;
    public static final int ACTION_PROCESS_DELETE_STKS=79;
    public static final int ACTION_PROCESS_UPLOAD_APPROVED_MEMO=80;
    public static final int ACTION_GENERATE_PAYMENT_LIST=81;
    public static final int ACTION_UPLOAD_PAYMENT_LIST=82;
    public static final int ACTION_UPLOAD_PAYMENT_LIST_PROCESS=83;
    
    //teamleader to rep
    public static final int action_rep_teamlead_assign=84;
    public static final int action_assign_rep_to_teamlead=85;
    public static final int action_unassign_rep_from_teamlead=86;
    public static final int action_view_teamlead_detail = 87;
    public static final int action_unassign_rep_to_teamleader=88;
    public static final int action_teamleader_rep_assign=89;
    public static final int action_assign_rep_to_teamleader=90;
    
    public static final int action_supervisor_teamlead_assign=91;
    public static final int action_assign_teamlead_to_supervisor=92;
    public static final int action_unassign_teamleader_to_supervisor=93;
    public static final int action_teamleader_sup_assign=94;
    public static final int action_assign_sup_to_teamleader=95;
    public static final int action_unassign_sup_from_teamlead=96;
    public static final int action_export_salesreps = 97;
    public static final int action_export_supervisors = 98;
    public static final int action_export_teamleaders = 99;
    //Rep Management --End

    public static HashMap handle(String action, HashMap paramHashMap, java.sql.Connection con) {
        System.out.println("handle action "+action);
        int actionType = 0;
        HashMap dataHashMap = new HashMap(100);
        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        if (strUserID != null && strUserID.compareTo("") != 0) {
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
        }
        else
        {
        strUserID = getUserId(strUserID, paramHashMap);
        }
        try {


            if (action.compareTo(SCMInterfaceKey.ACTION_IMPORT_STK_SHEET) == 0) {
                actionType = import_stk_list;
            }

            if (action.compareTo(SCMInterfaceKey.ACTION_IMPORT_STK_SHEET_PROCESS) == 0) {
                actionType = import_stk_process;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_STK_POS) == 0) {
                actionType = import_stk_pos;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_SAVE_STK_POS) == 0) {
                actionType = save_stk_pos;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_ASSIGN_STK_DISTRIBUTER) == 0) {
                actionType = assign_dist;
            }
            if (action.compareTo(SCMInterfaceKey.ASSIGN_STK_DISTRIBUTER_PROCESS) == 0) {
                actionType = dist_process;
            }
            if (action.equals(SCMInterfaceKey.ACTION_IMPORT_POS_CBILL)) {

                actionType = action_import_pos_cbill;
            }
            if (action.equals(SCMInterfaceKey.ACTION_IMPORT_POS_CBILL_PROCESS)) {

                actionType = action_import_pos_cbill;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_POS_STK_STATUS) == 0) {
                actionType = pos_status;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_POS_STK_STATUS_PROCESS) == 0) {
                actionType = pos_status_process;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_POS_STATUS_EXCEL_SHEET) == 0) {
                actionType = pos_status_excel;
            }
            if (action.equals(SCMInterfaceKey.ACTION_STK_STOCK_REPORT)) {
                actionType = stk_stock_report;
            }
            if (action.equals(SCMInterfaceKey.ACTION_BARCODE_REQUEST)) {
                actionType = barcode_request;
            }
            if (action.equals(SCMInterfaceKey.ACTION_BARCODE_STOCK)) {
                actionType = barcode_stock;
            }
            if (action.equals(SCMInterfaceKey.ACTION_VIEW_POS_BARCODE_BALANCE)) {
                actionType = view_barcode_balance;
            }
            if (action.equals(SCMInterfaceKey.ACTION_ACCEPT_POS_BARCODE_REQUEST)) {
                actionType = accept_barcode_request;
            }
            if (action.equals(SCMInterfaceKey.ACTION_BARCODE_STOCK_IMPORT)) {
                actionType = import_barcode_stock;
            }
            if (action.equals(SCMInterfaceKey.ACTION_IMPORT_IQRAR_RECIEVING)) {

                actionType = action_import_iqrar_recieving;
            }
            if (action.equals(SCMInterfaceKey.ACTION_EXPORT_SALESREPS)) {

                actionType = action_export_salesreps;
            }
            if (action.equals(SCMInterfaceKey.ACTION_EXPORT_SUPERVISORS)) {

                actionType = action_export_supervisors;
            }
            if (action.equals(SCMInterfaceKey.ACTION_EXPORT_TEAMLEADERS)) {

                actionType = action_export_teamleaders;
            }
            if (action.equals(SCMInterfaceKey.ACTION_IMPORT_SINGLE_IQRAR_RECIEVING)) {

                actionType = action_import_single_iqrar_recieving;
            }
            if (action.equals(SCMInterfaceKey.ACTION_IMPORT_MANY_IQRAR_RECIEVING)) {

                actionType = action_import_many_iqrar_recieving_process;
            }
            if (action.equals(SCMInterfaceKey.ACTION_SHOW_POS_STATUS)) {

                actionType = action_show_pos_status;
            }
            if (action.equals(SCMInterfaceKey.GET_POS_STATUS)) {

                actionType = pos_get_status;
            }
             if (action.equals(SCMInterfaceKey.ACTION_CHANGE_POS_STATUS)) {

                actionType = change_pos_status;
            }
            if (action.equals(SCMInterfaceKey.CHANGE_POS_STATUS_SHEET)) {

                actionType = change_pos_status_sheet;
            }
            if (action.equals(SCMInterfaceKey.GET_STK_STATUS)) {

                actionType = get_stk_status;
            }
            if (action.equals(SCMInterfaceKey.ACTION_STK_CHANGE_STATUS)) {

                actionType = change_stk_status;
            }
             if (action.equals(SCMInterfaceKey.ACTION_SHOW_STK_STATUS)) {

                actionType = show_stk_status;
            }
               if (action.equals(SCMInterfaceKey.ACTION_DISTRIBUTER_STK_STATISTICS)) {

                actionType = show_stk_distributer;
            }
             if (action.equals(SCMInterfaceKey.ACTION_SPECIFIC_DISTRIBUTER_STKS)) {

                actionType = show_speific_distributer_stks;
            }
             if (action.equals(SCMInterfaceKey.ACTIVE_STK_DISTRIBUTERS_AND_EXPORT_EXCEL)) {

                actionType = active_stk_distributer_and_export_excel;
            }
             if (action.equals(SCMInterfaceKey.SHOW_STK_STOCK_REMAINING)) {

                actionType = show_stk_stock_remaining;
            }
            if (action.equals(SCMInterfaceKey.GET_POS_STATUS_HISTORY)) {

                actionType = get_pos_status_history;
            }
            if (action.equals(SCMInterfaceKey.SHOW_POS_STATUS_HISTORY)) {

                actionType = show_pos_status_history;
            }
            if (action.equals(SCMInterfaceKey.ACTION_VIEW_POS_GROUPS)) {

                actionType = action_view_pos_groups;
            }
            if (action.equals(SCMInterfaceKey.ACTION_NEW_POS_GROUP)) {

                actionType = action_new_pos_group;
            }
            if (action.equals(SCMInterfaceKey.ACTION_EDIT_POS_GROUP)) {

                actionType = action_edit_pos_group;
            }
            if (action.equals(SCMInterfaceKey.ACTION_ADD_NEW_POS_GROUP)) {

                actionType = action_add_new_pos_group;
            }
            if (action.equals(SCMInterfaceKey.ACTION_UPDATE_POS_GROUP)) {

                actionType = action_update_pos_group;
            }
            if (action.equals(SCMInterfaceKey.ACTION_DELETE_GROUP)) {
                actionType = action_delete_group;
            }
            if (action.equals(SCMInterfaceKey.ACTION_ASSIGN_UNASSIGN_POS_TO_GROUP)) {
                actionType = action_assign_unassign_pos_to_group;
            }
            if (action.equals(SCMInterfaceKey.ACTION_UNASIGN_POS_FROM_GROUP)) {
                actionType = unassign_pos_from_group;
            }
            if (action.equals(SCMInterfaceKey.ACTION_ASSIGN_POS_TO_GROUP)) {
                actionType = assign_pos_to_group;
            }
            if (action.equals(SCMInterfaceKey.ACTION_ASSIGN_MANY_POS_TO_GROUP)) {
                actionType = assign_many_pos_to_group;
            }
            if (action.equals(SCMInterfaceKey.ACTION_VIEW_REP_MANAGEMENT)) {
                actionType = action_view_rep_management;
            }
            if (action.equals(SCMInterfaceKey.ACTION_SEARCH_REP)) {
                actionType = action_search_rep;
            }
            if (action.equals(SCMInterfaceKey.ACTION_ADD_NEW_REP_SUP)) {

                actionType = action_add_new_rep_sup;
            }
            if (action.equals(SCMInterfaceKey.ACTION_UPDATE_REP_SUP)) {

                actionType = action_update_rep_sup;
            }
            if (action.equals(SCMInterfaceKey.ACTION_NEW_REP_SUP)) {

                actionType = action_new_rep_sup;
            }
            if (action.equals(SCMInterfaceKey.ACTION_EDIT_REP_SUP)) {

                actionType = action_edit_rep_sup;
            }
            if (action.equals(SCMInterfaceKey.ACTION_GET_REGION_GOVERNORATES)) {

                actionType = action_get_region_governorates;
            }
            if (action.equals(SCMInterfaceKey.ACTION_GET_GOVERNORATE_CITIES)) {

                actionType = action_get_governorate_cities;
            }
            if (action.equals(SCMInterfaceKey.ACTION_GET_CITY_DISTRICTS)) {

                actionType = action_get_city_districts;
            }
            if (action.equals(SCMInterfaceKey.ACTION_GET_DISTRICT_AREAS)) {

                actionType = action_get_district_areas;
            }
            if (action.equals(SCMInterfaceKey.ACTION_SUBMIT_USER_LEVEL_TYPE)) {

                actionType = action_submit_user_level_type;
            }
            if (action.equals(SCMInterfaceKey.ACTION_DELETE_REP_SUP)) {

                actionType = action_delete_rep_sup;

            }
            if (action.equals(SCMInterfaceKey.ACTION_VIEW_REP_DETAIL)) {

                actionType = action_view_rep_detail;

            }
            if (action.equals(SCMInterfaceKey.ACTION_VIEW_SUP_DETAIL)) {

                actionType = action_view_sup_detail;

            }
            if (action.equals(SCMInterfaceKey.ACTION_VIEW_TEAMLEAD_DETAIL)) {

                actionType = action_view_teamlead_detail;

            }
            if (action.equals(SCMInterfaceKey.ACTION_REP_POS_GROUP)) {

                actionType = action_rep_pos_group;

            }
            if (action.equals(SCMInterfaceKey.ACTION_ASSIGN_REP_POS_GROUP)) {

                actionType = action_assign_rep_pos_group;

            }
            if (action.equals(SCMInterfaceKey.ACTION_UNASSIGN_REP_POS_GROUP)) {

                actionType = action_unassign_rep_pos_group;

            }
            if (action.equals(SCMInterfaceKey.ACTION_REP_SUP_ASSIGN)) {

                actionType = action_rep_sup_assign;

            }
            if (action.equals(SCMInterfaceKey.ACTION_REP_TEAMLEAD_ASSIGN)) {

                actionType = action_rep_teamlead_assign;

            }
            if (action.equals(SCMInterfaceKey.ACTION_ASSIGN_REP_TO_SUP)) {

                actionType = action_assign_rep_to_sup;

            }
            if (action.equals(SCMInterfaceKey.ACTION_ASSIGN_REP_TO_TEAMLEAD)) {

                actionType = action_assign_rep_to_teamlead;

            }
            if (action.equals(SCMInterfaceKey.ACTION_UNASSIGN_REP_FROM_SUP)) {

                actionType = action_unassign_rep_from_sup;

            }
            if (action.equals(SCMInterfaceKey.ACTION_UNASSIGN_SUP_FROM_TEAMLEAD)) {

                actionType = action_unassign_sup_from_teamlead;

            }
            if (action.equals(SCMInterfaceKey.ACTION_UNASSIGN_REP_FROM_TEAMLEAD)) {

                actionType = action_unassign_rep_from_teamlead;

            }
            if (action.equals(SCMInterfaceKey.ACTION_SUPERVISOR_REP_ASSIGN)) {

                actionType = action_supervisor_rep_assign;

            }
            
            if (action.equals(SCMInterfaceKey.ACTION_SUPERVISOR_TEAMLEAD_ASSIGN)) {

                actionType = action_supervisor_teamlead_assign;

            }
            
            if (action.equals(SCMInterfaceKey.ACTION_TEAMLEADER_REP_ASSIGN)) {

                actionType = action_teamleader_rep_assign;

            }
            if (action.equals(SCMInterfaceKey.ACTION_TEAMLEADER_SUPERVISOR_ASSIGN)) {

                actionType = action_teamleader_sup_assign;

            }
            if (action.equals(SCMInterfaceKey.ACTION_ASSIGN_REP_TO_SUPERVISOR)) {

                actionType = action_assign_rep_to_supervisor;

            }
            if (action.equals(SCMInterfaceKey.ACTION_ASSIGN_TEAMLEAD_TO_SUPERVISOR)) {

                actionType = action_assign_teamlead_to_supervisor;

            }
            if (action.equals(SCMInterfaceKey.ACTION_ASSIGN_REP_TO_TEAMLEADER)) {

                actionType = action_assign_rep_to_teamleader;

            }
            if (action.equals(SCMInterfaceKey.ACTION_ASSIGN_SUP_TO_TEAMLEADER)) {

                actionType = action_assign_sup_to_teamleader;

            }
            if (action.equals(SCMInterfaceKey.ACTION_UNASSIGN_REP_TO_SUPERVISOR)) {

                actionType = action_unassign_rep_to_supervisor;

            }
            if (action.equals(SCMInterfaceKey.ACTION_UNASSIGN_TEAMLEAD_TO_SUPERVISOR)) {

                actionType = action_unassign_teamleader_to_supervisor;

            }
            if (action.equals(SCMInterfaceKey.ACTION_UNASSIGN_REP_TO_TEAMLEADER)) {

                actionType = action_unassign_rep_to_teamleader;

            }
            if (action.equals(SCMInterfaceKey.ACTION_GEN_USER_SEARCH)) {

                actionType = action_gen_user_search;

            }
            if (action.equals(SCMInterfaceKey.ACTION_SEARCH_GEN_USER)) {

                actionType = action_search_gen_user ;

            }
            if (action.equals(SCMInterfaceKey.ACCEPT_BARCODE_REQUEST_EXCEL)) {

                actionType =  accept_barcode_request_excel;

            }if (action.equals(SCMInterfaceKey.ACTIVE_STK_DIST_EXCEL)) {

                actionType =  active_stk_distributer_excel;

            }
            if (action.equals(SCMInterfaceKey.ACTIVE_STK_DIST_EXCEL_PROCESS)) {

                actionType =  active_stk_dist_excel_process;

            }
            if (action.equals(SCMInterfaceKey.ACTIVE_STK_ONE_DISTRIBUTER_AND_EXPORT_EXCEL)) {
                actionType =  active_stk_one_distributer_and_export_excel;
            }
            if (action.equals(SCMInterfaceKey.EXPORT_EXCEL_IN_STOCK_STK)) {
                actionType =  export_excel_in_stock_stk;
            }
             
            
            if (action.equals(SCMInterfaceKey.VIEW_BARCODEREQUEST_EXCEL)) {

                actionType =  view_barcoderequest_excel;

            }
            if (action.equals(SCMInterfaceKey.VIEW_STK_DIST_REQUEST_EXCEL)) {

                actionType =  view_stk_dist_request_excel;

            }
             if (action.equals(SCMInterfaceKey.DOWNLOAD_BARCODEREQUEST_EXCEL)) {

                actionType =  download_barcoderequest_excel;
            }
             if (action.equals(SCMInterfaceKey.DOWNLOAD_DIST_REQUEST_EXCEL)) {

                actionType =  download_dist_request_excel;
            }

              if (action.equals(SCMInterfaceKey.ACTION_STK_CHOOSE_STOCK_REPORT) || action.equals(SCMInterfaceKey.ACTION_STK_CHOOSE_STOCK_REPORT_REMAINING)) {
                actionType =  select_stk_stock_type;
            }
              if (action.equals(SCMInterfaceKey.ACTIVE_DOWNLOAD_PREACTIVTE_EXCEL)) {
                actionType =  ACTIVE_DOWNLOAD_PREACTIVTE_EXCEL;
            }

              if (action.equals(SCMInterfaceKey.ACTION_CHANGE_POS_PAYMENT_LEVEL)) {
                actionType =  change_pos_payment_status;
            }
                    if (action.equals(SCMInterfaceKey.ACTION_VIEW_STK_DIST_EXCEL_HISTORY)) {
                actionType =  view_stk_dist_excel_history;
            }
                    if (action.equals(SCMInterfaceKey.ACTION_PROCESS_DELETE_STKS)) {
                actionType =  ACTION_PROCESS_DELETE_STKS;
            }
                    if (action.equals(SCMInterfaceKey.ACTION_PROCESS_UPLOAD_APPROVED_MEMO)) {
                actionType =  ACTION_PROCESS_UPLOAD_APPROVED_MEMO;
            }  
                    if (action.equals(SCMInterfaceKey.ACTION_GENERATE_PAYMENT_LIST)) {
                actionType =  ACTION_GENERATE_PAYMENT_LIST;
            }       if (action.equals(SCMInterfaceKey.ACTION_UPLOAD_PAYMENT_LIST)) {
                actionType =  ACTION_UPLOAD_PAYMENT_LIST;
            }       if (action.equals(SCMInterfaceKey.ACTION_UPLOAD_PAYMENT_LIST_PROCESS)) {
                actionType =  ACTION_UPLOAD_PAYMENT_LIST_PROCESS;
            }
              

            switch (actionType) {
                
                case ACTION_PROCESS_UPLOAD_APPROVED_MEMO:
                {
                                
                   File excelFile = GetUploadedFile.getFile(paramHashMap, SCMInterfaceKey.CONSTANT_SCM_UPLOAD_DIR);
                   FileInputStream tempIn = new FileInputStream(excelFile.getAbsolutePath());
                   Workbook wb = org.apache.poi.ss.usermodel.WorkbookFactory.create(tempIn);      
                   Sheet sheet = wb.getSheetAt(0);
                   Vector<String> errorMessages =  POSDAO.uploadApprovedMemoSheet(con,sheet);
                   tempIn.close();
                   dataHashMap.put(SCMInterfaceKey.VECTOR_OF_ERROR_MESSAGES_IN_IMPORT_POS_APPROVED, errorMessages);
                                
                }
                break;
                case ACTIVE_DOWNLOAD_PREACTIVTE_EXCEL: {
                    dataHashMap.put(SCMInterfaceKey.BASE_DIR, paramHashMap.get(SCMInterfaceKey.BASE_DIR));
                    dataHashMap.put(SCMInterfaceKey.OLD_FILE_PATH, paramHashMap.get(SCMInterfaceKey.OLD_FILE_PATH));
                    dataHashMap.put(SCMInterfaceKey.POS_CODE, paramHashMap.get(SCMInterfaceKey.POS_CODE));                    
                    dataHashMap.put(SCMInterfaceKey.IS_CONTINUE, paramHashMap.get(SCMInterfaceKey.IS_CONTINUE));
                    loadDistForActivation(con, dataHashMap);

                }
                break;
                    
                case ACTION_GENERATE_PAYMENT_LIST: {
                  

                }
                break;
                    
                case ACTION_UPLOAD_PAYMENT_LIST: {
                  

                }
                break;
                    
                case ACTION_UPLOAD_PAYMENT_LIST_PROCESS: {
                  

                }
                break;
                    
                case select_stk_stock_type: {
                    dataHashMap.put(SCMInterfaceKey.HASHMAP_STOCKS_, STKDAO.getStocksHM(con));

                }
                break;
                case import_stk_list: {
                    dataHashMap.put(SCMInterfaceKey.QUERY_STK_TABLE, SCMInterfaceKey.QUERY_STK_TABLE);
                    dataHashMap.put(SCMInterfaceKey.HASHMAP_STOCKS_, STKDAO.getStocksHM(con));

                }
                break;

                case import_stk_process: {
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(SCMInterfaceKey.HASHMAP_STOCKS_, STKDAO.getStocksHM(con));
                }
                break;

                case import_stk_pos: {
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(SCMInterfaceKey.SINGLE_IMPORT_Message, null);
                }
                break;

                case save_stk_pos: {
                    String posCode = (String) paramHashMap.get(SCMInterfaceKey.POS_CODE);
                    String stkDial = (String) paramHashMap.get(SCMInterfaceKey.STK_DIAL);
                    String stkID = STKDAO.getSTKIDFromDial(stkDial);
                    String Message = STKDAO.beforeInsertFieldSTK_POS(posCode, stkDial);

                    if (Message == "") {

                        STKDAO.insertPOSSTKQuery(stkID, posCode, strUserID);
                        Message = "Operation Completed Sucessfully";

                    }

                    System.out.print(Message);
                    dataHashMap.put(SCMInterfaceKey.SINGLE_IMPORT_Message, Message);


                }
                break;

                case assign_dist: {

                    dataHashMap.put(SCMInterfaceKey.DIST_MESSAGE, null);
                    Vector <POSModel> distributers=POSDAO.getAllDistributer(con);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_DISTRIBUTERS,distributers);
                    dataHashMap.put(SCMInterfaceKey.STK_QUANTITY, "");
                }

                break;

                case dist_process: {
                    String Slach = System.getProperty("file.separator");
                    String basedir = (String) paramHashMap.get(SCMInterfaceKey.BASE_DIR);
                    String distCode = (String) paramHashMap.get(SCMInterfaceKey.DISTRIBUTER_CONTROL_SELECT);
                    String stkQuantity = (String) paramHashMap.get(SCMInterfaceKey.STK_QUANTITY);
                    int stkIntQuantity = Integer.parseInt(stkQuantity);
                    int currentStock = STKDAO.InStockSTKcount(con,SCMInterfaceKey.DIST_STOCK_ID);
                    boolean DCMdeleted = STKDAO.isPOSDeleted(distCode);
                    boolean posexist = STKDAO.isDistExist(distCode);
                    boolean isdist = STKDAO.isPOsDistributer(distCode);
                    String Message = "";
                    String Msgflag = "";
                    if (!posexist) {
                        Message = "This POS is not exists";

                    } else if (!isdist) {
                        Message = "This POS is not a distributer";
                    } else if (DCMdeleted) {
                        Message = "This POS is closed";
                    }
                    if (Message != "") {
                        Msgflag = " & ";
                    }

                    if (stkIntQuantity > currentStock) {
                        Message = Message + Msgflag + "Quantity requested more than available in stock";
                    } else if (Message == "") {


                        STKDAO.insertSTKQuantity(con,distCode, strUserID, stkQuantity,SCMInterfaceKey.DIST_STOCK_ID);
                        HashMap<String, String> excelMap = STKDAO.getExcelForDistributer(con,stkQuantity,SCMInterfaceKey.DIST_STOCK_ID);
                        STKDAO.updateSTK_to_ASSIGN_Distributer(con,stkQuantity,SCMInterfaceKey.DIST_STOCK_ID);

                        String distLink = PoiWriteExcelFile.ExportExcel(excelMap, basedir, distCode);
                        if (distLink!=null && distLink.compareTo("")!=0)
                        {
                            String path = (String) paramHashMap.get("baseDirectory");
                            path = path==null ? basedir : path;
                           STKDAO.insertRequest(con, distCode, stkQuantity, strUserID, path+Slach+distLink);
                        }


                        Message = "Operation Completed Sucessfully";
                        dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH, distLink);
                        dataHashMap.put(InterfaceKey.MODULE_SUB_PATH, "scm" + Slach + "upload" + Slach);
                    }

                     Vector <POSModel> distributers=POSDAO.getAllDistributer(con);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_DISTRIBUTERS,distributers);
                    dataHashMap.put(SCMInterfaceKey.DIST_MESSAGE, Message);
                    dataHashMap.put(SCMInterfaceKey.POS_CODE, distCode);
                    dataHashMap.put(SCMInterfaceKey.STK_QUANTITY, stkQuantity);
                }
                break;


                case action_import_pos_cbill:

                    break;

                case action_import_pos_cbill_process:

                    break;

                case pos_status:
                {

                 dataHashMap.put(SCMInterfaceKey.CONTROL_CBILL_STATUS,"");
                 dataHashMap.put(SCMInterfaceKey.CONTROL_POS_STATUS_DATE_FROM,"*");
                 dataHashMap.put(SCMInterfaceKey.CONTROL_POS_STATUS_DATE_TO,"*");
                 dataHashMap.put(SCMInterfaceKey.CONTROL_POS_CODE,"");
                 dataHashMap.put(SCMInterfaceKey.CONTROL_IQRAR_STATUS,"");


                }
                    break;

                case pos_status_process: {

                    String destinationPage=(String)paramHashMap.get(SCMInterfaceKey.DESTINATION_PAGE);
                    if(destinationPage==null){
                        destinationPage="0";
                    }

                    dataHashMap.put(SCMInterfaceKey.SINGLE_IMPORT_Message, null);
                    String posCode=(String) paramHashMap.get(SCMInterfaceKey.CONTROL_POS_CODE);
                    String Iqrar_status = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_IQRAR_STATUS);
                    String Cbill_Status = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_CBILL_STATUS);
                    String POS_Status_Date_From = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_POS_STATUS_DATE_FROM);
                    String POS_Status_Date_To = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_POS_STATUS_DATE_TO);
                    Integer totalPageNumbers=(Integer)STKDAO.getPOSStatusTotalPages(posCode,Iqrar_status, Cbill_Status, POS_Status_Date_From, POS_Status_Date_To);
                    Vector<POSModel> POSs = STKDAO.getPOSStatusSeperate(posCode,Iqrar_status, Cbill_Status, POS_Status_Date_From, POS_Status_Date_To,destinationPage);
                    Vector<POSModel> activePOSs = STKDAO.getPOSStatus(posCode,Iqrar_status, Cbill_Status, POS_Status_Date_From, POS_Status_Date_To);




                    if (POSs.isEmpty()) {
                        dataHashMap.put(SCMInterfaceKey.SINGLE_IMPORT_Message, "No data");
                        dataHashMap.put(SCMInterfaceKey.CONTROL_CBILL_STATUS, Cbill_Status);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_POS_STATUS_DATE_FROM, POS_Status_Date_From);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_POS_STATUS_DATE_TO, POS_Status_Date_To);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_POS_CODE, posCode);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_IQRAR_STATUS, Iqrar_status);

                    } else {
                        dataHashMap.put(SCMInterfaceKey.SINGLE_IMPORT_Message, "Data");
                        dataHashMap.put(SCMInterfaceKey.POS_STATUS_SEARCH_RESULT, POSs);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_CBILL_STATUS, Cbill_Status);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_POS_STATUS_DATE_FROM, POS_Status_Date_From);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_POS_STATUS_DATE_TO, POS_Status_Date_To);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_POS_CODE, posCode);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_IQRAR_STATUS, Iqrar_status);
                        dataHashMap.put(SCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER,destinationPage);
                        dataHashMap.put(SCMInterfaceKey.STRING_OF_TOTAL_PAGE_NUMBER,totalPageNumbers.toString());
                        dataHashMap.put(SCMInterfaceKey.ACTIVE_POS_STATUS_SEARCH_RESULT, activePOSs);
                    }
                }
                break;
                case pos_status_excel: {
                    String posCode=(String) paramHashMap.get(SCMInterfaceKey.CONTROL_POS_CODE);
                    String Iqrar_status = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_IQRAR_STATUS);
                    String Cbill_Status = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_CBILL_STATUS);
                    String POS_Status_Date_From = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_POS_STATUS_DATE_FROM);
                    String POS_Status_Date_To = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_POS_STATUS_DATE_TO);
                    Vector<POSModel> POSs = STKDAO.getPOSStatus(posCode,Iqrar_status, Cbill_Status, POS_Status_Date_From, POS_Status_Date_To);
                    Vector<POSModel> activePOSs=STKDAO.activeSTKforPOS(POSs);
                    dataHashMap.put(SCMInterfaceKey.POS_STATUS_SEARCH_RESULT, activePOSs);
                    break;
                }
                case stk_stock_report:{
                    String stockId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_SELECT_STOCK_TYPE);
                    Vector<STKReportModel> assignedSTKFromStock = STKDAO.getAssignedSTKfromStock(con,stockId);
                    Vector<STKReportModel> importedSTKFromStock = STKDAO.getimportedstkfromStock(con,stockId);
                    int remainigSTKQuantityInStock = STKDAO.getRemainingSTKFromStock(con,stockId);
                    System.out.println("remainigSTKQuantityInStock iss "+remainigSTKQuantityInStock);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ASSIGNED_STK_FROM_STOCK, assignedSTKFromStock);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_IMPORTED_STK_FROM_STOCK, importedSTKFromStock);
                    dataHashMap.put(SCMInterfaceKey.STK_QUANTITY_REMAINING, remainigSTKQuantityInStock);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_HIDDEN_STOCK_ID, stockId);


                }
                    break;
                case barcode_request: {
                    Vector<DCMUserDetailModel> reps = new Vector<DCMUserDetailModel>();
                    reps = BarcodeDAO.getReps(con);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_REPS, reps);
                    dataHashMap.put(SCMInterfaceKey.BARCODE_REQUEST_POS_CODE, "");
                    dataHashMap.put(SCMInterfaceKey.BARCODE_REQUEST_QUANTITY, "");
                    dataHashMap.put(SCMInterfaceKey.ACTION_BARCODE_REQUEST_TYPE,"");
                }
                break;
                case view_barcode_balance: {
                    String repID = (String) paramHashMap.get(SCMInterfaceKey.BARCODE_REQUEST_REP_ID);
                    String posCode = (String) paramHashMap.get(SCMInterfaceKey.BARCODE_REQUEST_POS_CODE);
                    String quantity = (String) paramHashMap.get(SCMInterfaceKey.BARCODE_REQUEST_QUANTITY);
                    Vector<DCMUserDetailModel> reps = new Vector<DCMUserDetailModel>();
                    reps = BarcodeDAO.getReps(con);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_REPS, reps);
                    dataHashMap.put(SCMInterfaceKey.BARCODE_REQUEST_POS_CODE, posCode);
                    dataHashMap.put(SCMInterfaceKey.BARCODE_REQUEST_QUANTITY, quantity);
                    dataHashMap.put(SCMInterfaceKey.BARCODE_REQUEST_REP_ID, repID);
                    dataHashMap.put(SCMInterfaceKey.ACTION_BARCODE_REQUEST_TYPE,"1");
                    int posId = BarcodeDAO.checkPOS(con, posCode);
                    if (posId < 0) {
                        dataHashMap.put(SCMInterfaceKey.BARCODE_REQUEST_CONF_MESSAGE, "Invalid POS");
                        break;
                    }

                    int recievedBarcodefromCR = BarcodeDAO.getRecievedBarcodefromCR(posCode);
                    int recievedBarcodefromSFR = BarcodeDAO.getRecievedBarcodefromSFR(posCode);
                    int balanceForPOS = BarcodeDAO.getBalanceforPOS(posCode);
                    int remainingInStock = BarcodeDAO.getBarcodeStockRemainingQuantity(con);
                    dataHashMap.put(SCMInterfaceKey.BARCODE_POS_CR, recievedBarcodefromCR);
                    dataHashMap.put(SCMInterfaceKey.BARCODE_POS_SFR, recievedBarcodefromSFR);
                    dataHashMap.put(SCMInterfaceKey.BARCODE_POS_BALANCE, balanceForPOS);
                    dataHashMap.put(SCMInterfaceKey.BARCODE_REQUEST_STOCK_REMAINING_BALANCE, remainingInStock);
                    String message = "backToViewBarcodeBalance";
                    dataHashMap.put(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE, message);
                }
                break;

                case accept_barcode_request: {
                    String repID = (String) paramHashMap.get(SCMInterfaceKey.BARCODE_REQUEST_REP_ID);
                    String posCode = (String) paramHashMap.get(SCMInterfaceKey.BARCODE_REQUEST_POS_CODE);
                    String quantity = (String) paramHashMap.get(SCMInterfaceKey.BARCODE_REQUEST_QUANTITY);
                    Vector<DCMUserDetailModel> reps = new Vector<DCMUserDetailModel>();
                    reps = BarcodeDAO.getReps(con);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_REPS, reps);
                    dataHashMap.put(SCMInterfaceKey.BARCODE_REQUEST_POS_CODE, posCode);
                    dataHashMap.put(SCMInterfaceKey.BARCODE_REQUEST_QUANTITY, quantity);
                    dataHashMap.put(SCMInterfaceKey.BARCODE_REQUEST_REP_ID, repID);

                    int posId = BarcodeDAO.checkPOS(con, posCode);
                    if (posId < 0) {
                        dataHashMap.put(SCMInterfaceKey.BARCODE_REQUEST_CONF_MESSAGE, "Invalid POS");
                        break;
                    }
                    BarcodePOSRequestModel barcodeRequest = new BarcodePOSRequestModel();

                    barcodeRequest.setDcmUserId(Integer.parseInt(repID));
                    barcodeRequest.setPosId(posId);
                    barcodeRequest.setQuantity(Integer.parseInt(quantity));
                    barcodeRequest.setUserId(Integer.parseInt(strUserID));


                    BarcodeStockModel barcodeStock = new BarcodeStockModel();

                    barcodeStock.setBarcodeStockTypeId(Integer.parseInt(SCMInterfaceKey.BARCODE_STOCK_OUT_STATUS));
                    barcodeStock.setQuantity(Integer.parseInt(quantity));
                    barcodeStock.setUserId(Integer.parseInt(strUserID));

                    BarcodeDAO.insertNewBarCodeRequest(barcodeRequest, barcodeStock, con);

                    dataHashMap.put(SCMInterfaceKey.BARCODE_REQUEST_CONF_MESSAGE, "Request Accepted");
                }
                break;

                case accept_barcode_request_excel:{

                    ArrayList<String> removed=new ArrayList<String>();
                    String repUserId=(String) paramHashMap.get(SCMInterfaceKey.BARCODE_REQUEST_REP_ID);

                    for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempString = (String)paramHashMap.keySet().toArray()[j];
              if(tempString.startsWith("check:"))
              {
                String POSId = tempString.split(":")[1].toString();
                removed.add(POSId);
              }
            }
                 for(int i=0; i<removed.size();i++)
                 {

                    for(int j=0;j<paramHashMap.size(); j++){
                     String tempString = (String)paramHashMap.keySet().toArray()[j];
                    if(tempString.startsWith("poscode:"))
                    {
                         String POSId = tempString.split(":")[1].toString();
                         if(removed.get(i).equals(POSId)){
                                paramHashMap.remove("poscode:"+removed.get(i));
                                paramHashMap.remove("quantity:"+removed.get(i));
                         break;
                         }
                    }
                 }
                 }

                 int sumQuantity=0;
                 Long requestId=DBUtil.getSequenceNextVal(con,"BARCODEREQUEST_SEQ");
           for(int j=0; j<paramHashMap.size(); j++)
            {
              BarcodePOSRequestModel POSRequest=new BarcodePOSRequestModel();
              String tempString = (String)paramHashMap.keySet().toArray()[j];
              POSRequest.setDcmUserId(Integer.parseInt(repUserId));
              POSRequest.setUserId(Integer.parseInt(strUserID));
              if(tempString.startsWith("poscode:"))
              {
                String POSCode = tempString.split(":")[1].toString();

                String quantity=(String)paramHashMap.get("quantity:"+POSCode);

                String POSId=POSDAO.getPOSIdFromPOSCode(POSCode);

                POSRequest.setPosId(Integer.parseInt(POSId));

                POSRequest.setQuantity(Integer.parseInt(quantity));

                BarcodeDAO.insertNewBarCodeRequestByExcelSheet(POSRequest,requestId, con);
                sumQuantity=sumQuantity+Integer.parseInt(quantity);

              }
            }

                 BarcodeStockModel stock=new BarcodeStockModel();

                 stock.setBarcodeStockTypeId(Integer.parseInt(SCMInterfaceKey.BARCODE_STOCK_OUT_STATUS));
                 stock.setQuantity(sumQuantity);
                 stock.setUserId(Integer.parseInt(repUserId));

                 BarcodeDAO.insertIntoStock(stock, con);

                 Vector<DCMUserDetailModel> reps = new Vector<DCMUserDetailModel>();
                    reps = BarcodeDAO.getReps(con);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_REPS, reps);
                    dataHashMap.put(SCMInterfaceKey.BARCODE_REQUEST_POS_CODE, "");
                    dataHashMap.put(SCMInterfaceKey.BARCODE_REQUEST_QUANTITY, "");
                    dataHashMap.put(SCMInterfaceKey.ACTION_BARCODE_REQUEST_TYPE,"");

                } break;


                case barcode_stock:{

                    Vector<BarcodeStockReportModel> barcodeStockStats = BarcodeDAO.getStockStatictics(con);
                    int remainingInStock = BarcodeDAO.getBarcodeStockRemainingQuantity(con);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_BARCODE_STOCK_STATS_REPORT, barcodeStockStats);
                    dataHashMap.put(SCMInterfaceKey.BARCODE_REQUEST_STOCK_REMAINING_BALANCE, remainingInStock);
                    dataHashMap.put(SCMInterfaceKey.BARCODE_STOCK_IMPORT_QUANTITY,"");
                }
                 break;
               case import_barcode_stock:
               {
                 String quantity =(String)paramHashMap.get(SCMInterfaceKey.BARCODE_STOCK_IMPORT_QUANTITY);
                 Integer importQuantity=Integer.parseInt(quantity);
                 BarcodeStockModel importIntoStock= new BarcodeStockModel();


                 importIntoStock.setBarcodeStockTypeId(Integer.parseInt(SCMInterfaceKey.BARCODE_STOCK_IN_STATUS));
                 importIntoStock.setQuantity(importQuantity);
                 importIntoStock.setUserId(Integer.parseInt(strUserID));

                 BarcodeDAO.insertIntoStock(importIntoStock, con);
                 Vector<BarcodeStockReportModel> barcodeStockStats = BarcodeDAO.getStockStatictics(con);
                 int remainingInStock = BarcodeDAO.getBarcodeStockRemainingQuantity(con);

                 dataHashMap.put(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE,"Operation Successfull");
                 dataHashMap.put(SCMInterfaceKey.BARCODE_STOCK_IMPORT_QUANTITY, importQuantity);
                 dataHashMap.put(SCMInterfaceKey.VECTOR_BARCODE_STOCK_STATS_REPORT, barcodeStockStats);
                 dataHashMap.put(SCMInterfaceKey.BARCODE_REQUEST_STOCK_REMAINING_BALANCE, remainingInStock);
               }
                 break;
                case action_import_iqrar_recieving:

                    break;

                case action_import_single_iqrar_recieving:
                    IqrarRecievingModel iqrarRecievingModel = new IqrarRecievingModel();
                    String posCode= (String) paramHashMap.get(SCMInterfaceKey.IQRAR_RECIEVING_POS_CODE);
                    iqrarRecievingModel.setPosCode(posCode);
                    long dcmId = IqrarRecievingDAO.checkPOS(con, iqrarRecievingModel);
                    if (dcmId < 0) {
                        dataHashMap.put(SCMInterfaceKey.IQRAR_RECIEVING_USER_CONFIRMATION_MESSAGE, "Invalid POS");
                    } else {
                        Date iqrarRecievedDate = IqrarRecievingDAO.checkPOSIqrarRecieved(con, dcmId);
                        if (iqrarRecievedDate != null) {
                            dataHashMap.put(SCMInterfaceKey.IQRAR_RECIEVING_USER_CONFIRMATION_MESSAGE, "Invalid iqrar recieving , POS iqrar already recieved at:" + iqrarRecievedDate.getDate() + "/" + (iqrarRecievedDate.getMonth() + 1) + "/" + (iqrarRecievedDate.getYear() + 1900));
                        } else {
                            long checkFlag=IqrarRecievingDAO.checkIfPOSOwnsSTKOrNot(con, dcmId);
                            if(checkFlag>0){
                                IqrarRecievingDAO.updatePosIqrarRecievedVerification(con, dcmId);
                                dataHashMap.put(SCMInterfaceKey.IQRAR_RECIEVING_USER_CONFIRMATION_MESSAGE, "POS iqrar recieved");
                            }else{
                                dataHashMap.put(SCMInterfaceKey.IQRAR_RECIEVING_USER_CONFIRMATION_MESSAGE, "Invalid POS, POS doesn't own STK.");
                            }

                        }

                    }
                    break;

                case action_import_many_iqrar_recieving_process:

                    break;
               case action_show_pos_status:
               {
                    dataHashMap.put(SCMInterfaceKey.POS_CODE,"");

               }
                    break;
               case pos_get_status:
               {
                   System.out.println("innnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
                    String POSCode=(String)paramHashMap.get(SCMInterfaceKey.POS_CODE);
                    Vector <POSModel> POSStatus=POSDAO.getPOSStatus(POSCode);
                    dataHashMap.put(SCMInterfaceKey.POS_STATUS_VECTOR,POSStatus);
                    if(!POSStatus.isEmpty())
                    {
                    dataHashMap.put(SCMInterfaceKey.POS_CODE,POSCode);
                    dataHashMap.put(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE,null);
                    }else{
                    dataHashMap.put(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE,"POS Not Found");
                    dataHashMap.put(SCMInterfaceKey.POS_CODE,"");
                    }

               }
                    break;
                case change_pos_status:
                {
                    String POSCode=(String)paramHashMap.get(SCMInterfaceKey.POS_CODE);
                    String POSStatus=(String)paramHashMap.get(SCMInterfaceKey.POS_STATUS_CHANGE_LIST);
                    String paymentStatus=(String)paramHashMap.get(SCMInterfaceKey.PAYMENT_STATUS_CHANGE_LIST);
                    String reason=(String)paramHashMap.get(SCMInterfaceKey.CHANGE_POS_REASON);
                    if(!POSStatus.equals("0"))
                    {
                        POSDAO.changePOSStatus(POSCode, POSStatus,strUserID,reason);
                        dataHashMap.put(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE,"POS Status Changed");
                        dataHashMap.put(SCMInterfaceKey.POS_CODE,"");
                    }
                    if(!paymentStatus.equals("0"))
                    {
                        POSDAO.changePaymentStatusforPOS(POSCode, paymentStatus,strUserID,reason);
                        dataHashMap.put(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE,"POS Payment Status Changed");
                        dataHashMap.put(SCMInterfaceKey.POS_CODE,"");
                    }


                }
                    break;
                case change_pos_status_sheet:

                break;

                case show_stk_status:
                dataHashMap.put(SCMInterfaceKey.STK_DIAL,"");
                break;

                case get_stk_status:
                {
                    String STKDial=(String)paramHashMap.get(SCMInterfaceKey.STK_DIAL);
                    STKOwnerModel STKStatus=STKDAO.getSTKStatus(STKDial);
                    dataHashMap.put(SCMInterfaceKey.STK_STATUS,STKStatus);
                    dataHashMap.put(SCMInterfaceKey.STK_DIAL,STKDial);
                    if(STKStatus!=null){
                    if(STKStatus.getStkStatusName()==""||STKStatus.getStkStatusName()==null)
                    {
                    dataHashMap.put(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE,"STK Not Found");
                    }
                    }else{
                    dataHashMap.put(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE,"STK Not Found");
                    }
                }
                break;

                case change_stk_status:
                {
                String STKDial=(String)paramHashMap.get(SCMInterfaceKey.STK_DIAL);
                String STKStatus=(String)paramHashMap.get(SCMInterfaceKey.STK_STATUS_CHANGE_LIST);
                String changeReason=(String)paramHashMap.get(SCMInterfaceKey.CHANGE_STK_REASON);
                STKDAO.changeSTKStatus(STKDial,STKStatus,strUserID,changeReason);
                dataHashMap.put(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE,"STK status changed");
                dataHashMap.put(SCMInterfaceKey.STK_DIAL,"");
                }
                break;
                case show_stk_distributer:
                {
                    STKDAO.updateDistributersVerifiedStatus(con,SCMInterfaceKey.DIST_STOCK_ID);
                    Vector<STKDistributerStatisticsModel> distributersStatistics=STKDAO.getSTKsDistributerStatistics();
                    dataHashMap.put(SCMInterfaceKey.VECTOR_DISTRIBUTERS_STATISTICS,distributersStatistics);
                }
                break;
                case show_speific_distributer_stks:
                    {
                        String DCM_Id=(String)paramHashMap.get(SCMInterfaceKey.DISTRIBUTER_ID);
                        String DCM_Name=POSDAO.getDistributerName(DCM_Id);
                        Vector<STKOwnerModel> distributerSTKs=STKDAO.getDistributerSTKs(DCM_Id);
                        dataHashMap.put(SCMInterfaceKey.VECTOR_STKS_FOR_DISTRIBUTER,distributerSTKs);
                        dataHashMap.put(SCMInterfaceKey.DISTRIBUTER_NAME,DCM_Name);
                    }
                break;

                case active_stk_distributer_and_export_excel:
                {
                   String dateFrom = (String)paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_GENERATE_DCM_TEMPLATE_FROM);
                   String dateTo = (String)paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_GENERATE_DCM_TEMPLATE_TO);
                   Vector<DistributerStaticDataModel> distributerStaticData = DistributerSTKDataDAO.getDistributerStaticData();
                   Vector<DistributerSTKDetailsModel> distributerSTKData=DistributerSTKDataDAO.getDistributerSTKDetails( dateFrom, dateTo);
                   dataHashMap.put(SCMInterfaceKey.VECTOR_DISTRIBUTERS_STATIC_DATA,distributerStaticData);
                   dataHashMap.put(SCMInterfaceKey.VECTOR_DISTRIBUTERS_STK_DETAILS,distributerSTKData);
                   dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_GENERATE_DCM_TEMPLATE_FROM,dateFrom);
                   dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_GENERATE_DCM_TEMPLATE_TO,dateTo);

                }
                break;
                 case show_stk_stock_remaining:{
                    String stockId = (String)paramHashMap.get(SCMInterfaceKey.CONTROL_SELECT_STOCK_TYPE);
                    int remainigSTKQuantityInStock = STKDAO.getRemainingSTKFromStock(con,stockId);
                    dataHashMap.put(SCMInterfaceKey.STK_QUANTITY_REMAINING, remainigSTKQuantityInStock);
                }
                 break;

                case show_pos_status_history:
                {
                    dataHashMap.put(SCMInterfaceKey.POS_CODE,"");
                    dataHashMap.put(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE,"");
                }
                break;
                case get_pos_status_history:{

                    String POSCode=(String)paramHashMap.get(SCMInterfaceKey.POS_CODE);
                    Vector<POSStatusHistory>POSStatusHistory=POSDAO.getPOSStatusHistory(POSCode);
                    Vector<POSPaymentStatusHistory>POSPaymentStatusHistory=POSDAO.getPOSPaymentHistory(POSCode);
                    if(POSStatusHistory.size()==0||POSStatusHistory==null&&POSPaymentStatusHistory.size()==0||POSPaymentStatusHistory==null)
                    {
                    dataHashMap.put(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE,"No data for this POS");
                    }
                    dataHashMap.put(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE,"Data");
                    dataHashMap.put(SCMInterfaceKey.POS_CODE,POSCode);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_POS_STATUS_HISTORY,POSStatusHistory);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_POS_PAYMENT_HISTORY,POSPaymentStatusHistory);
                    }
                    break;

                case action_view_pos_groups: {

                    String destinationPage=(String)paramHashMap.get(SCMInterfaceKey.DESTINATION_PAGE);
                    if(destinationPage==null){
                        destinationPage="0";
                    }

                    String totalPageNumbers=POSGroupsDAO.getPOSGroupsTotalPages(con);



                    Vector<POSGroupModel> posGroups = POSGroupsDAO.getAllPOSGroups(con,destinationPage);

                    Vector<POSGroupModel> posGroupsToReturn = new Vector();
                    if (posGroups != null && posGroups.size() != 0) {
                        int posGroupsVectorSize = posGroups.size();
                        for (int i = 0; i < posGroupsVectorSize; i++) {
                            int noOfPOSs = 0;
                            POSGroupModel posGroup = posGroups.get(i);
                            noOfPOSs = POSGroupsDAO.getGroupPOSNo(con, posGroup.getGroupId());
                            posGroup.setNoOfPOSs(noOfPOSs);
                            posGroupsToReturn.add(posGroup);
                        }
                    }

                    dataHashMap.put(SCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER,destinationPage);
                    dataHashMap.put(SCMInterfaceKey.STRING_OF_TOTAL_PAGE_NUMBER,totalPageNumbers);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_POS_GROUPS, posGroupsToReturn);
                }

                break;

                case action_add_new_pos_group: {

                    String groupName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME);
                    String description = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_DESCRIPTION);
                    String groupTypeId=(String)paramHashMap.get(SCMInterfaceKey.POS_GROUP_TYPE_ID);
                    POSGroupModel posGroup = new POSGroupModel();
                    StringEscapeUtils st = new StringEscapeUtils();
                    posGroup.setGroupName(st.unescapeJavaScript(groupName));
                    posGroup.setDescription(st.unescapeJavaScript(description));
                    posGroup.setGroupTypeId(groupTypeId);
                    POSGroupsDAO.insertNewGroup(con, posGroup, strUserID);
                    dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE, "Group Added Sucessfully");
                    Vector <GroupTypeModel> groupTypes= RepGroupDAO.getGroupTypes(con);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_POS_GROUP_TYPES, groupTypes);
                }

                break;

                case action_update_pos_group: {

                    String stGroupId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID);
                    String groupName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME);
                    String description = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_DESCRIPTION);
                    String groupTypeId=(String)paramHashMap.get(SCMInterfaceKey.POS_GROUP_TYPE_ID);
                    int groupId = Integer.parseInt(stGroupId);
                    POSGroupModel posGroup = new POSGroupModel();
                    posGroup.setGroupName(groupName);
                    posGroup.setDescription(description);
                    posGroup.setGroupId(groupId);
                    posGroup.setGroupTypeId(groupTypeId);
                    POSGroupsDAO.updatePOSGroup(con, posGroup, strUserID);
                    dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE, "Group Updated Sucessfully");
                    dataHashMap.put(SCMInterfaceKey.POS_GROUP_MODEL, posGroup);
                    Vector <GroupTypeModel> groupTypes= RepGroupDAO.getGroupTypes(con);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_POS_GROUP_TYPES, groupTypes);

                }

                break;

                case action_new_pos_group:{
                    Vector <GroupTypeModel> groupTypes= RepGroupDAO.getGroupTypes(con);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_POS_GROUP_TYPES, groupTypes);
                }

                    break;

                case action_edit_pos_group: {
                    Vector <GroupTypeModel> groupTypes= RepGroupDAO.getGroupTypes(con);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_POS_GROUP_TYPES, groupTypes);
                    String stGroupId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID);
                    int groupId = 0;
                    if (stGroupId != null) {
                        groupId = Integer.parseInt(stGroupId);
                        POSGroupModel posGroup = POSGroupsDAO.getPOSGroup(con, groupId);
                        dataHashMap.put(SCMInterfaceKey.POS_GROUP_MODEL, posGroup);
                    } else {
                        dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE, "Invalid data");
                    }
                }

                break;

                case action_delete_group: {

                    String stGroupId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID);
                    String destinationPage=(String)paramHashMap.get(SCMInterfaceKey.DESTINATION_PAGE);
                    int groupId = Integer.parseInt(stGroupId);
                    POSGroupsDAO.deleteGroup(con, groupId);

                    String totalPageNumbers=POSGroupsDAO.getPOSGroupsTotalPages(con);
                    Vector<POSGroupModel> posGroups = POSGroupsDAO.getAllPOSGroups(con,destinationPage);
                    Vector<POSGroupModel> posGroupsToReturn = new Vector();
                    if (posGroups != null && posGroups.size() != 0) {
                        int posGroupsVectorSize = posGroups.size();
                        for (int i = 0; i < posGroupsVectorSize; i++) {
                            int noOfPOSs = 0;
                            POSGroupModel posGroup = posGroups.get(i);
                            noOfPOSs = POSGroupsDAO.getGroupPOSNo(con, posGroup.getGroupId());
                            posGroup.setNoOfPOSs(noOfPOSs);
                            posGroupsToReturn.add(posGroup);
                        }
                    }
                    dataHashMap.put(SCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER,destinationPage);
                    dataHashMap.put(SCMInterfaceKey.STRING_OF_TOTAL_PAGE_NUMBER,totalPageNumbers);
                    dataHashMap.put(SCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER,destinationPage);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_POS_GROUPS, posGroupsToReturn);

                }

                break;

                case action_assign_unassign_pos_to_group: {

                    Vector<POSModel> groupAssignedPOS = new Vector();
                    String stGroupId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID);
                    int groupId = Integer.parseInt(stGroupId);
                    groupAssignedPOS = POSGroupsDAO.getGroupAssignedPOSs(con, groupId);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_GROUP_ASSIGNED_POS, groupAssignedPOS);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID, groupId);
                    String groupName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME, groupName);
                }

                break;


                case unassign_pos_from_group: {

                    Vector<POSModel> groupAssignedPOS = new Vector();
                    String stGroupId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID);
                    int groupId = Integer.parseInt(stGroupId);

                    String stPosId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_POS_ID);
                    int posId = Integer.parseInt(stPosId);
                    POSGroupsDAO.unAssignPOSFromGroup(con, groupId, posId);

                    groupAssignedPOS = POSGroupsDAO.getGroupAssignedPOSs(con, groupId);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_GROUP_ASSIGNED_POS, groupAssignedPOS);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID, groupId);
                    String groupName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME, groupName);

                }

                break;

                case assign_pos_to_group: {

                    Vector<POSModel> groupAssignedPOS = new Vector();
                    String stGroupId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID);
                    int groupId = Integer.parseInt(stGroupId);
                    String groupName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME);
                    String posCCode = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_POS_ID);

                    int posId = POSGroupsDAO.checkPOS(con, posCCode);
                    if (posId < 0) {
                        dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE, "Invalid POS");
                        groupAssignedPOS = POSGroupsDAO.getGroupAssignedPOSs(con, groupId);
                        dataHashMap.put(SCMInterfaceKey.VECTOR_GROUP_ASSIGNED_POS, groupAssignedPOS);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID, groupId);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME, groupName);
                        break;
                    }

                    if (POSGroupsDAO.checkPOSAlreadyExistInTheGroup(con, groupId, posId)) {
                        dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE, "Invalid POS, already exist");
                        groupAssignedPOS = POSGroupsDAO.getGroupAssignedPOSs(con, groupId);
                        dataHashMap.put(SCMInterfaceKey.VECTOR_GROUP_ASSIGNED_POS, groupAssignedPOS);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID, groupId);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME, groupName);
                        break;
                    }

                    POSGroupsDAO.assingPOSToGroup(con, groupId, posId);

                    groupAssignedPOS = POSGroupsDAO.getGroupAssignedPOSs(con, groupId);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_GROUP_ASSIGNED_POS, groupAssignedPOS);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID, groupId);
                    dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE, "POS Assigned Successfuly");
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME, groupName);
                }

                break;

                case assign_many_pos_to_group: {

                    String stGroupId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID);
                    int groupId = Integer.parseInt(stGroupId);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID, groupId);
                    String groupName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME, groupName);
                }

                break;

                case action_view_rep_management: {
                    Vector<RegionModel> regions = new Vector();
                    Vector<DCMUserLevelTypeModel> repLevels = new Vector();
                    regions = RepManagementDAO.getRegions(con);
                    repLevels = RepManagementDAO.getUserLevelsForSupervisorAndRep(con);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGIONS, regions);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_REP_LEVEL_TYPES, repLevels);

                }

                break;

                case action_search_rep: {
                    searchRepOrSup(paramHashMap, dataHashMap, con);

                }

                break;

                case action_add_new_rep_sup: {

                    String userLevelTypeId = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_LEVEL_TYPE_ID);
                    String userFullName = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_FULL_NAME);
                    String userAddress = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_ADDRESS);
                    String userEmail = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_EMAIL);
                    String userMobile = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_MOBILE);
                    String userRegionId = (String) paramHashMap.get(SCMInterfaceKey.REGION_ID);
                    String systemUserId=(String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    String userId = (String) paramHashMap.get(SCMInterfaceKey.PERSON_ID);
                     //String userId = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String supervisorId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_SUP_ID);
                    /*if (supervisorId==null || supervisorId.compareTo("")==0 )
                        supervisorId = userId;*/
                    String teamleaderId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_TEAMLEAD_ID);
                    /*if (teamleaderId==null || teamleaderId.compareTo("")==0 )
                        teamleaderId = userId;*/
                    
                    System.out.println("Add IDs - rep/user id "+userId+" supervisor id "+supervisorId+" teamleader id "+teamleaderId);

                    Vector<RepSupervisorModel> repSupervisors = new Vector();
                    RepSupervisorModel repSuper = null;//new RepSupervisorModel();
                    RepTeamleaderModel repTeamlead = null;//new RepTeamleaderModel();
                    Vector<RepTeamleaderModel> repTeamleaders = new Vector();
                    Vector<DCMUserModel> supervisors = new Vector();
                    Vector<DCMUserModel> teamleaders = new Vector();
                    dataHashMap.put(SCMInterfaceKey.TEAMLEAD_ID, "");
                    dataHashMap.put(SCMInterfaceKey.SUP_ID, "");
                    
//                    if (userLevelTypeId.equalsIgnoreCase("3")) {
//                        userRegionId = (String) paramHashMap.get(SCMInterfaceKey.AREA_ID);
//                    }

                    if (userLevelTypeId.equalsIgnoreCase("3")) {
                        userRegionId = (String) paramHashMap.get(SCMInterfaceKey.DISTRICT_ID);
                    }

                    if(RepManagementDAO.checkIfUserAlreadyCreated(con, userId)){
                        dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE, "Invalid, This user already created before.");
                        break;
                    }

                    DCMUserModel dcmUser = new DCMUserModel();
                    dcmUser.setRegionId(userRegionId);
                    dcmUser.setUserLevelTypeId(userLevelTypeId);
                    dcmUser.setUserId(userId);

                    DCMUserDetailModel dcmUserDetatil = new DCMUserDetailModel();
                    dcmUserDetatil.setUserFullName(userFullName);

                    dcmUserDetatil.setUserAddress(userAddress);
                    dcmUserDetatil.setUserEmail(userEmail);
                    dcmUserDetatil.setUserMobile(userMobile);

                    if (supervisorId!=null && supervisorId.compareTo("")!=0 && supervisorId.compareTo("--")!=0)
                    {
                        //repSuper = RepManagementDAO.getRepSupervisor(con, userId, supervisorId);
                        if (!RepManagementDAO.checkIfRepSupervisor(con, userId, supervisorId)/*repSuper==null*/)
                            {
                                System.out.println("ASSIGN Supervisor in ADD");
                                RepSupDAO.assignRepToSupervisor(con, userId, supervisorId, systemUserId);
                                repSuper = RepManagementDAO.getRepSupervisor(con, userId, supervisorId);
                            }
                        
                    }
                    if (teamleaderId!=null && teamleaderId.compareTo("")!=0 && teamleaderId.compareTo("--")!=0)
                    {
                        //repTeamlead = RepManagementDAO.getRepTeamleader(con, userId, teamleaderId);
                        if (!RepManagementDAO.checkIfRepTeamleader(con, userId, teamleaderId)/*repTeamlead==null*/)
                            {
                                System.out.println("ASSIGN Teamleader in ADD");
                                RepSupDAO.assignRepToTeamleader(con, userId, teamleaderId, systemUserId);
                                repTeamlead = RepManagementDAO.getRepTeamleader(con, userId, teamleaderId);
                            }
                      
                    }

                    RepManagementDAO.addNewRepOrSupervisor(con, dcmUser, dcmUserDetatil, systemUserId);
                    //RepSupDAO.assignRepToSupervisor(con, userId, userId, userId);
                    //RepSupDAO.assignRepToTeamleader(con, userId, userId, userId);
                    dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE, "Added Successfuly.");

                }

                break;

                    
                    
                case action_update_rep_sup: {
                    
                    /////////////////////////////////////////////////////////
                    Vector<RepSupervisorModel> repSupervisors = new Vector();
                    RepSupervisorModel repSuper = null;//new RepSupervisorModel();
                    RepTeamleaderModel repTeamlead = null;//new RepTeamleaderModel();
                    Vector<RepTeamleaderModel> repTeamleaders = new Vector();
                    Vector<DCMUserModel> supervisors = new Vector();
                    Vector<DCMUserModel> teamleaders = new Vector();
                    dataHashMap.put(SCMInterfaceKey.TEAMLEAD_ID, "");
                    dataHashMap.put(SCMInterfaceKey.SUP_ID, "");
                    //////////////////////////////////////////////////////////
                    String userLevelTypeId = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_LEVEL_TYPE_ID);
                    String userFullName = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_FULL_NAME);
                    String userAddress = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_ADDRESS);
                    String userEmail = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_EMAIL);
                    String userMobile = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_MOBILE);
                    String userRegionId = (String) paramHashMap.get(SCMInterfaceKey.REGION_ID);
                    String systemUserId = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    String dcmUserId = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String supervisorId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_SUP_ID);
                    String teamleaderId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_TEAMLEAD_ID);
                    System.out.println("DCM USER ID "+dcmUserId+" MY SUPERVISOR "+supervisorId+" MY REGION ID "+userRegionId+" MY TEAMLEADER "+teamleaderId);
//                    if (userLevelTypeId.equalsIgnoreCase("3")) {
//                        userRegionId = (String) paramHashMap.get(SCMInterfaceKey.AREA_ID);
//                    }
                    if (supervisorId!=null && supervisorId.compareTo("")!=0)
                    {
                        //repSuper = RepManagementDAO.getRepSupervisor(con, dcmUserId, supervisorId);
                        //System.out.println("REP SUPER$$$ "+repSuper);
                        if (!RepManagementDAO.checkIfRepSupervisor(con, dcmUserId, supervisorId)/*repSuper==null*/)
                            {
                                
                                System.out.println("ASSIGN Supervisor in UPDATE");
                                RepSupDAO.assignRepToSupervisor(con, dcmUserId, supervisorId, systemUserId);
                                repSuper = RepManagementDAO.getRepSupervisor(con, dcmUserId, supervisorId);
                            }
                    }
                    if (teamleaderId!=null && teamleaderId.compareTo("")!=0)
                    {
                        //repTeamlead = RepManagementDAO.getRepTeamleader(con, dcmUserId, teamleaderId);
                        if (!RepManagementDAO.checkIfRepTeamleader(con, dcmUserId, teamleaderId)/*repTeamlead==null*/)
                            {
                                System.out.println("ASSIGN Teamleader in UPDATE");
                                RepSupDAO.assignRepToTeamleader(con, dcmUserId, teamleaderId, systemUserId);
                                repTeamlead = RepManagementDAO.getRepTeamleader(con, dcmUserId, teamleaderId);
                            }
                    }
                    
                    if((supervisorId==null || supervisorId.compareTo("")==0) && (teamleaderId!=null || teamleaderId.compareTo("")!=0))
                    {
                        supervisorId=dcmUserId;
                        RepSupDAO.assignTeamleaderToSupervisor(con, teamleaderId, dcmUserId,systemUserId);
                    }
                    if((teamleaderId==null || teamleaderId.compareTo("")==0) && (supervisorId!=null || supervisorId.compareTo("")!=0))
                    {
                        supervisorId=dcmUserId;
                        RepSupDAO.assignTeamleaderToSupervisor(con, dcmUserId, supervisorId,systemUserId);
                    }
                    //////////////////////////////////////////////
                            supervisors=RepSupDAO.getRegionSupervisors(con, userRegionId);
                            teamleaders=RepSupDAO.getRegionTeamleaders(con, userRegionId);
                            dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGION_TEAMLEADERS, teamleaders);
                            dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGION_SUPERVISORS, supervisors);

                            dataHashMap.put(SCMInterfaceKey.SELECTED_REP_SUPERVISOR, repSuper);
                            dataHashMap.put(SCMInterfaceKey.SELECTED_REP_TEAMLEADER, repTeamlead);
                            dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGION_SUPERVISORS, supervisors);
                            
                            repSupervisors = RepManagementDAO.getRepSupervisors(con, dcmUserId);
                            repTeamleaders = RepManagementDAO.getRepTeamleaders(con, dcmUserId);
                            dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REP_SUPERVISORS, repSupervisors);
                            dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REP_TEAMLEADS, repTeamleaders);
                            
                            for(int i=0; i< supervisors.size();i++)
                            {
                                String regionSupervisorId = ((DCMUserModel) supervisors.get(i)).getDcmUserId();
                                for(int j=0; j<repSupervisors.size();j++)
                                {
                                    String repSupervisorId = ((RepSupervisorModel) repSupervisors.get(j)).getSupId();
                                    if(regionSupervisorId.compareTo(repSupervisorId)==0)
                                    
                                        
                                        dataHashMap.put(SCMInterfaceKey.SUP_ID, repSupervisorId);
                                   
                                 
                                }
                            }
                            
                            for(int i=0; i< teamleaders.size();i++)
                            {
                                String regionTeamleaderId = ((DCMUserModel) teamleaders.get(i)).getDcmUserId();
                                for(int j=0; j<repTeamleaders.size();j++)
                                {
                                    String repTeamleaderId = ((RepTeamleaderModel) repTeamleaders.get(j)).getTeamleadId();
                                    if(regionTeamleaderId.compareTo(repTeamleaderId)==0)
                                      dataHashMap.put(SCMInterfaceKey.TEAMLEAD_ID, repTeamleaderId);
                                    
                                }
                            }
                    //////////////////////////////////////////////

                    if (userLevelTypeId.equalsIgnoreCase("3")) {
                        userRegionId = (String) paramHashMap.get(SCMInterfaceKey.DISTRICT_ID);
                    }

                    DCMUserModel dcmUserOld = RepManagementDAO.getDcmUser(con, dcmUserId);
                    DCMUserModel dcmUser = new DCMUserModel();
                    dcmUser.setDcmUserId(dcmUserId);
                    dcmUser.setRegionId(userRegionId);
                    dcmUser.setUserLevelTypeId(userLevelTypeId);
                    dcmUser.setUserDetailId(dcmUserOld.getUserDetailId());

                    DCMUserDetailModel dcmUserDetatil = new DCMUserDetailModel();
                    dcmUserDetatil.setUserFullName(userFullName);
                    dcmUserDetatil.setUserAddress(userAddress);
                    dcmUserDetatil.setUserEmail(userEmail);
                    dcmUserDetatil.setUserMobile(userMobile);


                    RepManagementDAO.updateRepOrSupervisor(con, dcmUser, dcmUserDetatil, systemUserId);
                    System.out.println("SCMInterfaceKey.VECTOR_ALL_REP_SUPERVISORS "+dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_REP_SUPERVISORS));
                    System.out.println("SCMInterfaceKey.VECTOR_ALL_REP_TEAMLEADS "+dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_REP_TEAMLEADS));
                            
                            
                    dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE, "Data Updated Successfuly.");
                    paramHashMap.put(SCMInterfaceKey.REGION_ID, userRegionId);
                    paramHashMap.put(SCMInterfaceKey.PERSON_ID, dcmUserId);
                    putRepAddOrUpdateBasicData(paramHashMap, dataHashMap, con);
                }

                break;

                case action_delete_rep_sup: {
                    String dcmUserId = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String userLevelTypeId= (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_LEVEL_TYPE_ID);
                    DCMUserModel dcmUser = RepManagementDAO.getDcmUser(con, dcmUserId);
                    RepManagementDAO.deleteRepOrSupervisor(con, dcmUserId,userLevelTypeId);
                    searchRepOrSup(paramHashMap, dataHashMap, con);

                }

                break;

                case action_new_rep_sup: {
                    System.out.println("action_new_rep_sup");
                    String personId=(String)paramHashMap.get(SCMInterfaceKey.PERSON_ID);
                    String systemUserId=(String)paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    System.out.println("LOADING ADD person id "+personId);
                    if(RepManagementDAO.checkIfUserAlreadyCreated(con, personId)){
                        dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE, "Invalid, This user already created before.");

                    }
                    else{
                        String personName=(String)paramHashMap.get(SCMInterfaceKey.PERSON_NAME);
                        String personEmail=(String)paramHashMap.get(SCMInterfaceKey.PERSON_EMAIL);
                        String personAddress=(String)paramHashMap.get(SCMInterfaceKey.PERSON_ADDRESS);

                        DCMUserModel dcmUser = new DCMUserModel();
                        dcmUser.setUserId(personId);
                        dcmUser.setDcmUserId(systemUserId);

                        DCMUserDetailModel dcmUserDetatil = new DCMUserDetailModel();
                        dcmUserDetatil.setUserFullName(personName);
                        dcmUserDetatil.setUserAddress(personAddress);
                        dcmUserDetatil.setUserEmail(personEmail);

                        dataHashMap.put(SCMInterfaceKey.PERSON_ID,personId);
                        dataHashMap.put(SCMInterfaceKey.DCM_USER_ID,systemUserId);
                        dataHashMap.put(SCMInterfaceKey.DCM_USER_MODEL,dcmUser);
                        dataHashMap.put(SCMInterfaceKey.DCM_USER_DETAIL_MODEL,dcmUserDetatil);


                    }
                }

                break;

                case action_edit_rep_sup: {
                    System.out.println("action_edit_rep_sup");
                    String stDcmUserId = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    System.out.println("^^^^^^^ DCM_USER_ID ^^^^^^^^ "+stDcmUserId);
                    dataHashMap.put(SCMInterfaceKey.TEAMLEAD_ID, "");
                    dataHashMap.put(SCMInterfaceKey.SUP_ID, "");
                    
                    String districtID="";
                    String regionId = "";
                    Vector<RepSupervisorModel> repSupervisors = new Vector();
                    Vector<RepTeamleaderModel> repTeamleaders = new Vector();
                    Vector<DCMUserModel> supervisors = new Vector();
                    Vector<DCMUserModel> teamleaders = new Vector();
                  
                    
                    
                    if (stDcmUserId != null) {

                        System.out.println("stDcmUserId in EDIT "+stDcmUserId);
                        DCMUserModel dcmUser = RepManagementDAO.getDcmUser(con, stDcmUserId);
                        
                        if (dcmUser.getUserDetailId() != null) {

                            DCMUserDetailModel dcmUserDetail = RepManagementDAO.getDcmUserDetail(con, dcmUser.getUserDetailId());
                            System.out.println("dcm user region id "+dcmUser.getRegionId());
                            
                            districtID = dcmUser.getRegionId();
                            regionId=RepSupDAO.getDistrictRegionId(con, districtID);
                            if (regionId=="" || regionId==null)
                                regionId = districtID;
                            System.out.println("REGION ID : "+regionId+" DISTRICT ID : "+districtID);
                            dataHashMap.put(SCMInterfaceKey.DCM_USER_MODEL, dcmUser);

                            dataHashMap.put(SCMInterfaceKey.DCM_USER_DETAIL_MODEL, dcmUserDetail);

                            supervisors=RepSupDAO.getRegionSupervisors(con, regionId);
                            teamleaders=RepSupDAO.getRegionTeamleaders(con, regionId);
                            dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGION_TEAMLEADERS, teamleaders);
                            dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGION_SUPERVISORS, supervisors);

                            repSupervisors = RepManagementDAO.getRepSupervisors(con, stDcmUserId);
                            repTeamleaders = RepManagementDAO.getRepTeamleaders(con, stDcmUserId);
                            dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REP_SUPERVISORS, repSupervisors);
                            dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REP_TEAMLEADS, repTeamleaders);
                            
                            for(int i=0; i< supervisors.size();i++)
                            {
                                String regionSupervisorId = ((DCMUserModel) supervisors.get(i)).getDcmUserId();
                                for(int j=0; j<repSupervisors.size();j++)
                                {
                                    String repSupervisorId = ((RepSupervisorModel) repSupervisors.get(j)).getSupId();
                                    if(regionSupervisorId.compareTo(repSupervisorId)==0)
                                    {
                                     System.out.println("REP SUPER ID ^^^^^^^ : "+repSupervisorId);
                                        dataHashMap.put(SCMInterfaceKey.SUP_ID, repSupervisorId);
                                    }
                                 
                                }
                            }
                            
                            for(int i=0; i< teamleaders.size();i++)
                            {
                                String regionTeamleaderId = ((DCMUserModel) teamleaders.get(i)).getDcmUserId();
                                for(int j=0; j<repTeamleaders.size();j++)
                                {
                                    String repTeamleaderId = ((RepTeamleaderModel) repTeamleaders.get(j)).getTeamleadId();
                                    if(regionTeamleaderId.compareTo(repTeamleaderId)==0)
                                      dataHashMap.put(SCMInterfaceKey.TEAMLEAD_ID, repTeamleaderId);
                                    
                                }
                            }
                            
                        } else {

                            dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE, "Invalid data");

                        }

                        
                    } else {

                        dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE, "Invalid data");

                    }
                    
                    

                }

                break;

                case action_submit_user_level_type: {

                    putRepAddOrUpdateBasicData(paramHashMap, dataHashMap, con);
                    getAndPutRegionGovernorates(paramHashMap, dataHashMap, con);
                    
                }
                break;

                case action_get_region_governorates: {
                    
                    Vector<RepSupervisorModel> repSupervisors = new Vector();
                    Vector<RepTeamleaderModel> repTeamleaders = new Vector();
                    Vector<DCMUserModel> supervisors = new Vector();
                    Vector<DCMUserModel> teamleaders = new Vector();
                    RepSupervisorModel repSuper = null;//new RepSupervisorModel();
                    RepTeamleaderModel repTeamlead = null;//new RepTeamleaderModel();
                    
                    
                    dataHashMap.put(SCMInterfaceKey.TEAMLEAD_ID, "");
                    dataHashMap.put(SCMInterfaceKey.SUP_ID, "");
                    
                    String supervisorId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_SUP_ID);
                    String teamleaderId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_TEAMLEAD_ID);
                    String userRegionId = (String) paramHashMap.get(SCMInterfaceKey.REGION_ID);
                    String systemUserId = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    String dcmUserId = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    System.out.println("IDs %%% "+"region id : "+userRegionId+" "+"supervisor id : "+supervisorId+" "+"teamleader id : "+teamleaderId+" "+"user/salesRep id : "+dcmUserId);
                    
                    
                            supervisors=RepSupDAO.getRegionSupervisors(con, userRegionId);
                            teamleaders=RepSupDAO.getRegionTeamleaders(con, userRegionId);
                            dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGION_TEAMLEADERS, teamleaders);
                            dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGION_SUPERVISORS, supervisors);

                            ////cut here into another func/////
                                if (dcmUserId.compareTo("")!=0)
                                {
                                    repSupervisors = RepManagementDAO.getRepSupervisors(con, dcmUserId);
                                    repTeamleaders = RepManagementDAO.getRepTeamleaders(con, dcmUserId);
                                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REP_SUPERVISORS, repSupervisors);
                                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REP_TEAMLEADS, repTeamleaders);
                                    if (supervisorId.compareTo("")!=0)
                                    {
                                        repSuper = RepManagementDAO.getRepSupervisor(con, dcmUserId, supervisorId);

                                        if (repSuper==null)
                                            {
                                                repSuper = RepManagementDAO.getRepSupervisor(con, dcmUserId, supervisorId);
                                            }
                                    }
                                    if (teamleaderId.compareTo("")!=0)
                                    {
                                        repTeamlead = RepManagementDAO.getRepTeamleader(con, dcmUserId, teamleaderId);
                                        if (repTeamlead==null)
                                            {
                                                repTeamlead = RepManagementDAO.getRepTeamleader(con, dcmUserId, teamleaderId);
                                            }
                                    }
                            }
                            
                            
                            for(int i=0; i< supervisors.size();i++)
                            {
                                String regionSupervisorId = ((DCMUserModel) supervisors.get(i)).getDcmUserId();
                                for(int j=0; j<repSupervisors.size();j++)
                                {
                                    String repSupervisorId = ((RepSupervisorModel) repSupervisors.get(j)).getSupId();
                                    if(regionSupervisorId.compareTo(repSupervisorId)==0)
                                    {
                                        dataHashMap.put(SCMInterfaceKey.SUP_ID, repSupervisorId);
                                    }
                                 
                                }
                            }
                            
                            for(int i=0; i< teamleaders.size();i++)
                            {
                                String regionTeamleaderId = ((DCMUserModel) teamleaders.get(i)).getDcmUserId();
                                for(int j=0; j<repTeamleaders.size();j++)
                                {
                                    String repTeamleaderId = ((RepTeamleaderModel) repTeamleaders.get(j)).getTeamleadId();
                                    if(regionTeamleaderId.compareTo(repTeamleaderId)==0)
                                        dataHashMap.put(SCMInterfaceKey.TEAMLEAD_ID, repTeamleaderId);
                                    
                                }
                            }
                   ////end of cut///////////
                   putRepAddOrUpdateBasicData(paramHashMap, dataHashMap, con);
                   getAndPutRegionGovernorates(paramHashMap, dataHashMap, con);
                }

                break;

                case action_get_governorate_cities: {
                    putRepAddOrUpdateBasicData(paramHashMap, dataHashMap, con);
                    getAndPutRegionGovernorates(paramHashMap, dataHashMap, con);
                    getAndPutGovernorateCities(paramHashMap, dataHashMap, con);
                }

                break;

                case action_get_city_districts: {
                    putRepAddOrUpdateBasicData(paramHashMap, dataHashMap, con);
                    getAndPutRegionGovernorates(paramHashMap, dataHashMap, con);
                    getAndPutGovernorateCities(paramHashMap, dataHashMap, con);
                    getAndPutCityDistricts(paramHashMap, dataHashMap, con);
                }

                break;

                case action_get_district_areas: {
                    putRepAddOrUpdateBasicData(paramHashMap, dataHashMap, con);
                    getAndPutRegionGovernorates(paramHashMap, dataHashMap, con);
                    getAndPutGovernorateCities(paramHashMap, dataHashMap, con);
                    getAndPutCityDistricts(paramHashMap, dataHashMap, con);
                    getAndPutDistrictAreas(paramHashMap, dataHashMap, con);
                }

                break;

                case action_view_rep_detail: {
                    String dcmUserId = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String userLevelTypeId = (String) paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);

                    DCMUserDetailModel repDetails = new DCMUserDetailModel();
                    RepPOSGroupModel repPOSGroup = new RepPOSGroupModel();
                    Vector<RepSupervisorModel> repSupervisors = new Vector();
                    Vector<RepTeamleaderModel> repTeamleaders = new Vector();
                    
                    repSupervisors = RepManagementDAO.getRepSupervisors(con, dcmUserId);
                    repTeamleaders = RepManagementDAO.getRepTeamleaders(con, dcmUserId);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REP_SUPERVISORS, repSupervisors);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REP_TEAMLEADS, repTeamleaders);
                    
                    repPOSGroup = RepManagementDAO.getRepPOSGroup(con, dcmUserId);
                    repDetails = RepManagementDAO.getRepSupDetail(con, userLevelTypeId, dcmUserId);
                    

                    dataHashMap.put(SCMInterfaceKey.REP_SUP_DETAILS, repDetails);
                    dataHashMap.put(SCMInterfaceKey.REP_POS_GROUP, repPOSGroup);


                }

                break;

                case action_view_sup_detail: {
                    String supId = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String userLevelTypeId = (String) paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);
                    String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

                    DCMUserDetailModel supDetails = new DCMUserDetailModel();
                    //Vector<RepSupervisorModel> supervisorReps = new Vector();
                    Vector<SupervisorRepsModel> supervisorReps = new Vector();
                    
                    supDetails = RepManagementDAO.getRepSupDetail(con, userLevelTypeId, supId);
                    //supervisorReps = RepManagementDAO.getSupervisorReps(con, supId);
                    supervisorReps = RepManagementDAO.getSupervisorSalesReps(con, supId);

                    dataHashMap.put(SCMInterfaceKey.REP_SUP_DETAILS, supDetails);
                    //dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_SUP_REPS, supervisorReps);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_SUP_REPS, supervisorReps);
                    dataHashMap.put(SCMInterfaceKey.DCM_USER_ID, supId);
                    dataHashMap.put(SCMInterfaceKey.USER_LEVEL_TYPE_ID, userLevelTypeId);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, systemUserId);

                }

                break;
                    case action_view_teamlead_detail: {
                    String teamleadId = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String userLevelTypeId = (String) paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);
                    String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

                    DCMUserDetailModel teamleadDetails = new DCMUserDetailModel();
                    Vector<TeamleaderRepsModel> teamleaderReps = new Vector();
                    Vector<TeamleaderSupervisorsModel> teamleaderSupervisors = new Vector();

                    teamleadDetails = RepManagementDAO.getRepTeamleadDetail(con, userLevelTypeId, teamleadId);
                    teamleaderReps = RepManagementDAO.getTeamleaderReps(con, teamleadId);
                    teamleaderSupervisors = RepManagementDAO.getTeamleaderSupervisors(con, teamleadId);
                        System.out.println("teamleaderSupervisors "+teamleaderSupervisors.size());
                    dataHashMap.put(SCMInterfaceKey.REP_TEAMLEAD_DETAILS, teamleadDetails);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_TEAMLEAD_REPS, teamleaderReps);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_TEAMLEAD_SUPERVISORS, teamleaderSupervisors);
                    dataHashMap.put(SCMInterfaceKey.DCM_USER_ID, teamleadId);
                    dataHashMap.put(SCMInterfaceKey.USER_LEVEL_TYPE_ID, userLevelTypeId);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, systemUserId);

                }

                break;

                case action_assign_rep_pos_group:{

                    String repId=(String)paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                String userLevelTypeId=(String)paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);
                String groupId=(String)paramHashMap.get(SCMInterfaceKey.POS_GROUP_ID);
                String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

                if(RepGroupDAO.checkIFRepAlreadyHasAnAssignedPOSGroup(con, repId)){
                    dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE, "Invalid, Rep already has a group.");
                }else{
                    RepGroupDAO.assignPOSGroup(con, groupId, repId, systemUserId);
                }


                    DCMUserDetailModel repDetails = new DCMUserDetailModel();
                    RepPOSGroupModel repPOSGroup = new RepPOSGroupModel();
                    Vector<RepSupervisorModel> repSupervisors = new Vector();

                    repPOSGroup = RepManagementDAO.getRepPOSGroup(con, repId);
                    repDetails = RepManagementDAO.getRepSupDetail(con, userLevelTypeId, repId);
                    repSupervisors = RepManagementDAO.getRepSupervisors(con, repId);

                    dataHashMap.put(SCMInterfaceKey.REP_SUP_DETAILS, repDetails);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REP_SUPERVISORS, repSupervisors);
                    dataHashMap.put(SCMInterfaceKey.REP_POS_GROUP, repPOSGroup);


                }
                break;

                case action_rep_pos_group:{
                String groupTypeId=(String)paramHashMap.get(SCMInterfaceKey.POS_GROUP_TYPE_ID);
                String repId=(String)paramHashMap.get(SCMInterfaceKey.DCM_USER_ID );
                String userLevelTypeId=(String)paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);
                Vector <GroupTypeModel> groupTypes= RepGroupDAO.getGroupTypes(con);
                String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

                dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_POS_GROUP_TYPES, groupTypes);
                dataHashMap.put(SCMInterfaceKey.DCM_USER_ID, repId);
                dataHashMap.put(SCMInterfaceKey.USER_LEVEL_TYPE_ID, userLevelTypeId);
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, systemUserId);

                if(groupTypeId!=null){
                    Vector <POSGroupModel> posGroups= RepGroupDAO.getPOSGroupsByGroupType(con,groupTypeId);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_POS_GROUPS_BY_GROUP_TYPE,posGroups);
                    dataHashMap.put(SCMInterfaceKey.POS_GROUP_TYPE_ID,groupTypeId);
                }

                }
                break;

                case action_unassign_rep_pos_group:{
                String repId=(String)paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                String userLevelTypeId=(String)paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);
                String groupId=(String)paramHashMap.get(SCMInterfaceKey.POS_GROUP_ID);
                String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

                RepGroupDAO.unassigPOSGroup(con, repId, groupId);

                    DCMUserDetailModel repDetails = new DCMUserDetailModel();
                    RepPOSGroupModel repPOSGroup = new RepPOSGroupModel();
                    Vector<RepSupervisorModel> repSupervisors = new Vector();

                    repPOSGroup = RepManagementDAO.getRepPOSGroup(con, repId);
                    repDetails = RepManagementDAO.getRepSupDetail(con, userLevelTypeId, repId);
                    repSupervisors = RepManagementDAO.getRepSupervisors(con, repId);

                    dataHashMap.put(SCMInterfaceKey.REP_SUP_DETAILS, repDetails);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REP_SUPERVISORS, repSupervisors);
                    dataHashMap.put(SCMInterfaceKey.REP_POS_GROUP, repPOSGroup);
                }
                break;

                case action_rep_sup_assign:{
                    Vector <DCMUserModel> supervisors=new Vector();

//                    String areaId=(String)paramHashMap.get(SCMInterfaceKey.REGION_ID);
                    String districtID=(String)paramHashMap.get(SCMInterfaceKey.REGION_ID);
                    String regionId=RepSupDAO.getDistrictRegionId(con, districtID);
                    String repId=(String)paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String userLevelTypeId=(String)paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);
                    String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

                    
                    supervisors=RepSupDAO.getRegionSupervisors(con, regionId);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGION_SUPERVISORS, supervisors);
                    dataHashMap.put(SCMInterfaceKey.DCM_USER_ID, repId);
                    dataHashMap.put(SCMInterfaceKey.USER_LEVEL_TYPE_ID,userLevelTypeId);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, systemUserId);
                }

                break;
                    
                    
                    
                
                case action_rep_teamlead_assign:{
                    Vector <DCMUserModel> teamleaders=new Vector();

//                    String areaId=(String)paramHashMap.get(SCMInterfaceKey.REGION_ID);
                    String districtID=(String)paramHashMap.get(SCMInterfaceKey.REGION_ID);
                    String repId=(String)paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String userLevelTypeId=(String)paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);
                    String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

                    String regionId=RepSupDAO.getDistrictRegionId(con, districtID);
                    teamleaders=RepSupDAO.getRegionTeamleaders(con, regionId);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGION_TEAMLEADERS, teamleaders);
                    dataHashMap.put(SCMInterfaceKey.DCM_USER_ID, repId);
                    dataHashMap.put(SCMInterfaceKey.USER_LEVEL_TYPE_ID,userLevelTypeId);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, systemUserId);
                }

                break;    

                case action_assign_rep_to_sup:{
                    String supId=(String)paramHashMap.get(SCMInterfaceKey.SUP_ID);
                    String repId=(String)paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    String userLevelTypeId=(String)paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);

                    if(RepSupDAO.checkIfRepAssigntoMoreThanTwoSupervisors(con, repId))
                        dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE,"Invalid, Rep already assigned to 2 supervisors.");
                    else if(RepSupDAO.checkIfRepAlreadyAssignedToThisSup(con, repId, supId))
                        dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE,"Invalid, Rep already assigned to this supervisor.");
                    else
                        RepSupDAO.assignRepToSupervisor(con, repId, supId,systemUserId);

                    DCMUserDetailModel repDetails = new DCMUserDetailModel();
                    RepPOSGroupModel repPOSGroup = new RepPOSGroupModel();
                    Vector<RepSupervisorModel> repSupervisors = new Vector();

                    repPOSGroup = RepManagementDAO.getRepPOSGroup(con, repId);
                    repDetails = RepManagementDAO.getRepSupDetail(con, userLevelTypeId, repId);
                    repSupervisors = RepManagementDAO.getRepSupervisors(con, repId);

                    dataHashMap.put(SCMInterfaceKey.REP_SUP_DETAILS, repDetails);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REP_SUPERVISORS, repSupervisors);
                    dataHashMap.put(SCMInterfaceKey.REP_POS_GROUP, repPOSGroup);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, systemUserId);


                }

                break;
                    //actual assign
                case action_assign_rep_to_teamlead:{
                    String teamleadId=(String)paramHashMap.get(SCMInterfaceKey.TEAMLEAD_ID);
                    String repId=(String)paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    String userLevelTypeId=(String)paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);

                    if(RepSupDAO.checkIfRepAssigntoMoreThanTwoTeamleaders(con, repId))
                        dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE,"Invalid, Rep already assigned to 2 teamleaders.");
                    else if(RepSupDAO.checkIfRepAlreadyAssignedToThisTeamlead(con, repId, teamleadId))
                        dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE,"Invalid, Rep already assigned to this teamleader.");
                    else
                        RepSupDAO.assignRepToTeamleader(con, repId, teamleadId,systemUserId);

                    DCMUserDetailModel repDetails = new DCMUserDetailModel();
                    RepPOSGroupModel repPOSGroup = new RepPOSGroupModel();
                    Vector<RepSupervisorModel> repSupervisors = new Vector();
                    Vector<RepTeamleaderModel> repTeamleaders = new Vector();

                    repPOSGroup = RepManagementDAO.getRepPOSGroup(con, repId);
                    repDetails = RepManagementDAO.getRepSupDetail(con, userLevelTypeId, repId);
                    repSupervisors = RepManagementDAO.getRepSupervisors(con, repId);
                    repTeamleaders = RepManagementDAO.getRepTeamleaders(con, repId);
                    
                    dataHashMap.put(SCMInterfaceKey.REP_SUP_DETAILS, repDetails);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REP_SUPERVISORS, repSupervisors);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REP_TEAMLEADS, repTeamleaders);
                    dataHashMap.put(SCMInterfaceKey.REP_POS_GROUP, repPOSGroup);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, systemUserId);


                }

                break;    
                    
                    
                case action_unassign_rep_from_sup:{
                    String supId=(String)paramHashMap.get(SCMInterfaceKey.SUP_ID);
                    String repId=(String)paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    String userLevelTypeId=(String)paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);

                        RepSupDAO.unassignRepFromSupervisor(con, repId, supId);

                    DCMUserDetailModel repDetails = new DCMUserDetailModel();
                    RepPOSGroupModel repPOSGroup = new RepPOSGroupModel();
                    Vector<RepSupervisorModel> repSupervisors = new Vector();

                    repPOSGroup = RepManagementDAO.getRepPOSGroup(con, repId);
                    repDetails = RepManagementDAO.getRepSupDetail(con, userLevelTypeId, repId);
                    repSupervisors = RepManagementDAO.getRepSupervisors(con, repId);

                    dataHashMap.put(SCMInterfaceKey.REP_SUP_DETAILS, repDetails);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REP_SUPERVISORS, repSupervisors);
                    dataHashMap.put(SCMInterfaceKey.REP_POS_GROUP, repPOSGroup);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, systemUserId);
                }

                break;
                    
                case action_unassign_rep_from_teamlead:{
                    String teamleadId=(String)paramHashMap.get(SCMInterfaceKey.TEAMLEAD_ID);
                    String repId=(String)paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    String userLevelTypeId=(String)paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);

                        RepSupDAO.unassignRepFromTeamleader(con, repId, teamleadId);

                    DCMUserDetailModel repDetails = new DCMUserDetailModel();
                    RepPOSGroupModel repPOSGroup = new RepPOSGroupModel();
                    Vector<RepSupervisorModel> repSupervisors = new Vector();
                    Vector<RepTeamleaderModel> repTeamleaders = new Vector();

                    repPOSGroup = RepManagementDAO.getRepPOSGroup(con, repId);
                    repDetails = RepManagementDAO.getRepSupDetail(con, userLevelTypeId, repId);
                    repSupervisors = RepManagementDAO.getRepSupervisors(con, repId);
                    repTeamleaders = RepManagementDAO.getRepTeamleaders(con, repId);

                    dataHashMap.put(SCMInterfaceKey.REP_SUP_DETAILS, repDetails);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REP_SUPERVISORS, repSupervisors);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REP_TEAMLEADS, repTeamleaders);
                    dataHashMap.put(SCMInterfaceKey.REP_POS_GROUP, repPOSGroup);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, systemUserId);
                }

                break;    
                    
                    
                case action_supervisor_rep_assign:{
                    Vector <DCMUserModel> reps=new Vector();
                    Vector <RegionModel> areas=new Vector();
                    StringBuffer areasId=new StringBuffer("");
                    String regionId=(String)paramHashMap.get(SCMInterfaceKey.REGION_ID);
                    String supId=(String)paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String userLevelTypeId=(String)paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);
                    String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

//                    areas=RepSupDAO.getRegionAreas(con, regionId);
                    areas=RepSupDAO.getRegionDistricts(con, regionId.toString());
                    if(areas!=null&&areas.size()!=0){
                        for(int i=0;i<areas.size();i++){
                           RegionModel area=(RegionModel)areas.get(i) ;
                           areasId.append(area.getRegionId());
                           if(areas.size()!=i+1){
                           areasId.append(",");
                           }
                        }
                    reps=RepSupDAO.getRegionReps(con, areasId.toString());
                    }
                    dataHashMap.put(SCMInterfaceKey.REGION_ID, regionId);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGION_REPS, reps);
                    dataHashMap.put(SCMInterfaceKey.DCM_USER_ID, supId);
                    dataHashMap.put(SCMInterfaceKey.USER_LEVEL_TYPE_ID,userLevelTypeId);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, systemUserId);

                }

                break;
                    
                    
                    case action_supervisor_teamlead_assign:{
                    Vector <DCMUserModel> teamleaders=new Vector();
                    //Vector <RegionModel> areas=new Vector();
                    StringBuffer areasId=new StringBuffer("");
                    String regionId=(String)paramHashMap.get(SCMInterfaceKey.REGION_ID);
                    String supId=(String)paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String userLevelTypeId=(String)paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);
                    String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

//                    areas=RepSupDAO.getRegionAreas(con, regionId);
                    /*areas=RepSupDAO.getRegionDistricts(con, regionId.toString());
                    if(areas!=null&&areas.size()!=0){
                        for(int i=0;i<areas.size();i++){
                           RegionModel area=(RegionModel)areas.get(i) ;
                           areasId.append(area.getRegionId());
                           if(areas.size()!=i+1){
                           areasId.append(",");
                           }
                        }
                    reps=RepSupDAO.getRegionTeamleaders(con, areasId.toString());
                    }*/
                    teamleaders=RepSupDAO.getRegionTeamleaders(con, /*areasId.toString()*/regionId);
                    dataHashMap.put(SCMInterfaceKey.REGION_ID, regionId);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGION_TEAMLEADERS, teamleaders);
                    dataHashMap.put(SCMInterfaceKey.DCM_USER_ID, supId);
                    dataHashMap.put(SCMInterfaceKey.USER_LEVEL_TYPE_ID,userLevelTypeId);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, systemUserId);

                }

                break;
                    
                 case action_teamleader_rep_assign:{
                    Vector <DCMUserModel> reps=new Vector();
                    Vector <RegionModel> areas=new Vector();
                    StringBuffer areasId=new StringBuffer("");
                    String regionId=(String)paramHashMap.get(SCMInterfaceKey.REGION_ID);
                    String teamleadId=(String)paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String userLevelTypeId=(String)paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);
                    String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

//                    areas=RepSupDAO.getRegionAreas(con, regionId);
                    areas=RepSupDAO.getRegionDistricts(con, regionId.toString());
                    if(areas!=null&&areas.size()!=0){
                        for(int i=0;i<areas.size();i++){
                           RegionModel area=(RegionModel)areas.get(i) ;
                           areasId.append(area.getRegionId());
                           if(areas.size()!=i+1){
                           areasId.append(",");
                           }
                        }
                    reps=RepSupDAO.getRegionReps(con, areasId.toString());
                    }
                    dataHashMap.put(SCMInterfaceKey.REGION_ID, regionId);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGION_REPS, reps);
                    dataHashMap.put(SCMInterfaceKey.DCM_USER_ID, teamleadId);
                    dataHashMap.put(SCMInterfaceKey.USER_LEVEL_TYPE_ID,userLevelTypeId);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, systemUserId);

                }

                break;

                     
                case action_teamleader_sup_assign:{
                    //Vector <DCMUserModel> reps=new Vector();
                    Vector <DCMUserModel> supervisors=new Vector();
                    Vector <RegionModel> areas=new Vector();
                    StringBuffer areasId=new StringBuffer("");
                    String regionId=(String)paramHashMap.get(SCMInterfaceKey.REGION_ID);
                    String teamleadId=(String)paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String userLevelTypeId=(String)paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);
                    String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

//                    areas=RepSupDAO.getRegionAreas(con, regionId);
                  /*  areas=RepSupDAO.getRegionDistricts(con, regionId.toString());
                    if(areas!=null&&areas.size()!=0){
                        for(int i=0;i<areas.size();i++){
                           RegionModel area=(RegionModel)areas.get(i) ;
                           areasId.append(area.getRegionId());
                           if(areas.size()!=i+1){
                           areasId.append(",");
                           }
                        }
                    reps=RepSupDAO.getRegionReps(con, areasId.toString());
                    }*/
                    supervisors=RepSupDAO.getRegionSupervisors(con, regionId.toString());
                    dataHashMap.put(SCMInterfaceKey.REGION_ID, regionId);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGION_SUPERVISORS, supervisors);
                    dataHashMap.put(SCMInterfaceKey.DCM_USER_ID, teamleadId);
                    dataHashMap.put(SCMInterfaceKey.USER_LEVEL_TYPE_ID,userLevelTypeId);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, systemUserId);

                }

                break;     
                     
                     
                     
                case action_assign_rep_to_supervisor:{
                    String repId=(String)paramHashMap.get(SCMInterfaceKey.REP_ID);
                    String regionId=(String)paramHashMap.get(SCMInterfaceKey.REGION_ID);
                    String supId=(String)paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    String userLevelTypeId=(String)paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);
                    Vector <DCMUserModel> reps=new Vector();
                    Vector <RegionModel> areas=new Vector();
                    StringBuffer areasId=new StringBuffer("");

                    if(RepSupDAO.checkIfRepAssigntoMoreThanTwoSupervisors(con, repId))
                        dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE,"Invalid, Rep already assigned to 2 supervisors.");
                    else if(RepSupDAO.checkIfRepAlreadyAssignedToThisSup(con, repId, supId))
                        dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE,"Invalid, Rep already assigned to this supervisor.");
                    else{
                        RepSupDAO.assignRepToSupervisor(con, repId, supId,systemUserId);
                                dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE, "Rep assigned successfuly.");
                    }

//                    areas=RepSupDAO.getRegionAreas(con, regionId);
                    areas=RepSupDAO.getRegionDistricts(con, regionId);
                    if(areas!=null&&areas.size()!=0){
                        for(int i=0;i<areas.size();i++){
                           RegionModel area=(RegionModel)areas.get(i) ;
                           areasId.append(area.getRegionId());
                           if(areas.size()!=i+1){
                           areasId.append(",");
                           }
                        }
                    reps=RepSupDAO.getRegionReps(con, areasId.toString());
                    }
                    dataHashMap.put(SCMInterfaceKey.REGION_ID, regionId);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGION_REPS, reps);
                    dataHashMap.put(SCMInterfaceKey.DCM_USER_ID, supId);
                    dataHashMap.put(SCMInterfaceKey.USER_LEVEL_TYPE_ID, userLevelTypeId);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, systemUserId);
                }
                break;
                    
                    case action_assign_teamlead_to_supervisor:{
                    String teamleadId=(String)paramHashMap.get(SCMInterfaceKey.TEAMLEAD_ID);
                    String regionId=(String)paramHashMap.get(SCMInterfaceKey.REGION_ID);
                    String supId=(String)paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    String userLevelTypeId=(String)paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);
                    Vector <DCMUserModel> teamleaders=new Vector();
                    //Vector <RegionModel> areas=new Vector();
                    //StringBuffer areasId=new StringBuffer("");

                    if(RepSupDAO.checkIfTeamleadAssigntoMoreThanTwoSupervisors(con, teamleadId))
                        dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE,"Invalid, Teamleader already assigned to 2 supervisors.");
                    else if(RepSupDAO.checkIfTeamleadAlreadyAssignedToThisSup(con, teamleadId, supId))
                        dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE,"Invalid, Teamleader already assigned to this supervisor.");
                    else{
                        RepSupDAO.assignTeamleaderToSupervisor(con, teamleadId, supId,systemUserId);
                                dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE, "Teamleader assigned successfuly.");
                    }

//                    areas=RepSupDAO.getRegionAreas(con, regionId);
                    /*areas=RepSupDAO.getRegionDistricts(con, regionId);
                    if(areas!=null&&areas.size()!=0){
                        for(int i=0;i<areas.size();i++){
                           RegionModel area=(RegionModel)areas.get(i) ;
                           areasId.append(area.getRegionId());
                           if(areas.size()!=i+1){
                           areasId.append(",");
                           }
                        }
                    reps=RepSupDAO.getRegionReps(con, areasId.toString());
                    }*/
                    
                    teamleaders=RepSupDAO.getRegionTeamleaders(con, regionId);
                    
                    dataHashMap.put(SCMInterfaceKey.REGION_ID, regionId);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGION_TEAMLEADERS, teamleaders);
                    dataHashMap.put(SCMInterfaceKey.DCM_USER_ID, supId);
                    dataHashMap.put(SCMInterfaceKey.USER_LEVEL_TYPE_ID, userLevelTypeId);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, systemUserId);
                }
                break;
                    
                    
                    case action_assign_rep_to_teamleader:{
                    String repId=(String)paramHashMap.get(SCMInterfaceKey.REP_ID);
                    String regionId=(String)paramHashMap.get(SCMInterfaceKey.REGION_ID);
                    String teamleadId=(String)paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    String userLevelTypeId=(String)paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);
                    Vector <DCMUserModel> reps=new Vector();
                    Vector <RegionModel> areas=new Vector();
                    StringBuffer areasId=new StringBuffer("");

                    if(RepSupDAO.checkIfRepAssigntoMoreThanTwoTeamleaders(con, repId))
                        dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE,"Invalid, Rep already assigned to 2 teamleader.");
                    else if(RepSupDAO.checkIfRepAlreadyAssignedToThisTeamlead(con, repId, teamleadId))
                        dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE,"Invalid, Rep already assigned to this teamleader.");
                    else{
                        RepSupDAO.assignRepToTeamleader(con, repId, teamleadId,systemUserId);
                                dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE, "Rep assigned successfuly.");
                    }

//                    areas=RepSupDAO.getRegionAreas(con, regionId);
                    areas=RepSupDAO.getRegionDistricts(con, regionId);
                    if(areas!=null&&areas.size()!=0){
                        for(int i=0;i<areas.size();i++){
                           RegionModel area=(RegionModel)areas.get(i) ;
                           areasId.append(area.getRegionId());
                           if(areas.size()!=i+1){
                           areasId.append(",");
                           }
                        }
                    reps=RepSupDAO.getRegionReps(con, areasId.toString());
                    }
                    dataHashMap.put(SCMInterfaceKey.REGION_ID, regionId);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGION_REPS, reps);
                    dataHashMap.put(SCMInterfaceKey.DCM_USER_ID, teamleadId);
                    dataHashMap.put(SCMInterfaceKey.USER_LEVEL_TYPE_ID, userLevelTypeId);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, systemUserId);
                }
                break;
                        
                    case action_assign_sup_to_teamleader:{
                        System.out.println("inside action_assign_sup_to_teamleader");
                    //String repId=(String)paramHashMap.get(SCMInterfaceKey.REP_ID);
                    String supId=(String)paramHashMap.get(SCMInterfaceKey.SUP_ID);
                    String regionId=(String)paramHashMap.get(SCMInterfaceKey.REGION_ID);
                    String teamleadId=(String)paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    String userLevelTypeId=(String)paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);
                    //Vector <DCMUserModel> reps=new Vector();
                    Vector <DCMUserModel> supervisors=new Vector();
                    Vector <RegionModel> areas=new Vector();
                    StringBuffer areasId=new StringBuffer("");

                    if(RepSupDAO.checkIfTeamleadAssigntoMoreThanTwoSupervisors(con, teamleadId))
                        dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE,"Invalid, teamleader already assigned to 2 supervisors.");
                    else if(RepSupDAO.checkIfTeamleadAlreadyAssignedToThisSup(con, teamleadId, supId))
                        dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE,"Invalid, teamleader already assigned to this supervisor.");
                    else{
                        RepSupDAO.assignTeamleaderToSupervisor(con, teamleadId, supId,systemUserId);
                                dataHashMap.put(SCMInterfaceKey.CONFIRMATION_MESSAGE, "supervisor assigned successfuly.");
                    }

//                    areas=RepSupDAO.getRegionAreas(con, regionId);
                    /*areas=RepSupDAO.getRegionDistricts(con, regionId);
                    if(areas!=null&&areas.size()!=0){
                        for(int i=0;i<areas.size();i++){
                           RegionModel area=(RegionModel)areas.get(i) ;
                           areasId.append(area.getRegionId());
                           if(areas.size()!=i+1){
                           areasId.append(",");
                           }
                        }
                    reps=RepSupDAO.getRegionReps(con, areasId.toString());
                    }*/
                    supervisors=RepSupDAO.getRegionSupervisors(con, regionId.toString());
                    dataHashMap.put(SCMInterfaceKey.REGION_ID, regionId);
                    //dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGION_REPS, reps);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGION_SUPERVISORS, supervisors);
                    dataHashMap.put(SCMInterfaceKey.DCM_USER_ID, teamleadId);
                    dataHashMap.put(SCMInterfaceKey.USER_LEVEL_TYPE_ID, userLevelTypeId);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, systemUserId);
                }
                break;
                        
                        
                        
                case action_unassign_rep_to_supervisor:{
                    String repId=(String)paramHashMap.get(SCMInterfaceKey.REP_ID);
                    String regionId=(String)paramHashMap.get(SCMInterfaceKey.REGION_ID);
                    String supId=(String)paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    String userLevelTypeId=(String)paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);


                    RepSupDAO.unassignRepFromSupervisor(con, repId, supId);

                    DCMUserDetailModel supDetails = new DCMUserDetailModel();
                    //Vector<RepSupervisorModel> supervisorReps = new Vector();
                    Vector<SupervisorRepsModel> supervisorReps = new Vector();
                    
                    supDetails = RepManagementDAO.getRepSupDetail(con, userLevelTypeId, supId);
                    //supervisorReps = RepManagementDAO.getSupervisorReps(con, supId);
                    supervisorReps = RepManagementDAO.getSupervisorSalesReps(con, supId);

                    dataHashMap.put(SCMInterfaceKey.REP_SUP_DETAILS, supDetails);
                    //dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_SUP_REPS, supervisorReps);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_SUP_REPS, supervisorReps);
                    dataHashMap.put(SCMInterfaceKey.DCM_USER_ID, supId);
                    dataHashMap.put(SCMInterfaceKey.USER_LEVEL_TYPE_ID, userLevelTypeId);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, systemUserId);
                    dataHashMap.put(SCMInterfaceKey.REGION_ID, regionId);



                }
                break;
                
                    case action_unassign_teamleader_to_supervisor:{
                    String teamleadId=(String)paramHashMap.get(SCMInterfaceKey.TEAMLEAD_ID);
                    String regionId=(String)paramHashMap.get(SCMInterfaceKey.REGION_ID);
                    String supId=(String)paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    String userLevelTypeId=(String)paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);


                    RepSupDAO.unassignTeamleadFromSupervisor(con, teamleadId, supId);

                    DCMUserDetailModel supDetails = new DCMUserDetailModel();
                    Vector<SupervisorRepsModel> supervisorReps = new Vector();
                    
                    Vector<SupervisorTeamleadersModel> supervisorTeamleaders = new Vector();

                    supDetails = RepManagementDAO.getRepSupDetail(con, userLevelTypeId, supId);
                    supervisorReps = RepManagementDAO.getSupervisorSalesReps(con, supId);
                    supervisorTeamleaders = RepManagementDAO.getSupervisorTeamleaders(con, supId);
                    
                    

                    dataHashMap.put(SCMInterfaceKey.REP_SUP_DETAILS, supDetails);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_SUP_REPS, supervisorReps);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_SUP_TEAMLEADERS, supervisorTeamleaders);
                    dataHashMap.put(SCMInterfaceKey.DCM_USER_ID, supId);
                    dataHashMap.put(SCMInterfaceKey.USER_LEVEL_TYPE_ID, userLevelTypeId);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, systemUserId);
                    dataHashMap.put(SCMInterfaceKey.REGION_ID, regionId);



                }
                break;
                        
                    case action_unassign_sup_from_teamlead:{
                    String teamleadId = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String supId = (String) paramHashMap.get(SCMInterfaceKey.SUP_ID);
                    String userLevelTypeId = (String) paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);
                    String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

                    DCMUserDetailModel teamleadDetails = new DCMUserDetailModel();
                    Vector<TeamleaderRepsModel> teamleaderReps = new Vector();
                    Vector<TeamleaderSupervisorsModel> teamleaderSupervisors = new Vector();

                    RepSupDAO.unassignTeamleadFromSupervisor(con, teamleadId, supId);
                    teamleadDetails = RepManagementDAO.getRepTeamleadDetail(con, userLevelTypeId, teamleadId);
                    teamleaderReps = RepManagementDAO.getTeamleaderReps(con, teamleadId);
                    teamleaderSupervisors = RepManagementDAO.getTeamleaderSupervisors(con, teamleadId);
                        System.out.println("teamleaderSupervisors "+teamleaderSupervisors.size());
                    dataHashMap.put(SCMInterfaceKey.REP_TEAMLEAD_DETAILS, teamleadDetails);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_TEAMLEAD_REPS, teamleaderReps);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_TEAMLEAD_SUPERVISORS, teamleaderSupervisors);
                    dataHashMap.put(SCMInterfaceKey.DCM_USER_ID, teamleadId);
                    dataHashMap.put(SCMInterfaceKey.USER_LEVEL_TYPE_ID, userLevelTypeId);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, systemUserId);

                    

                    



                }
                break;
                
                    
                    
                case action_unassign_rep_to_teamleader:{
                    String repId=(String)paramHashMap.get(SCMInterfaceKey.REP_ID);
                    String regionId=(String)paramHashMap.get(SCMInterfaceKey.REGION_ID);
                    String teamleadId=(String)paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
                    String systemUserId=(String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    String userLevelTypeId=(String)paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);


                    RepSupDAO.unassignRepFromTeamleader(con, repId, teamleadId);

                    DCMUserDetailModel teamleadDetails = new DCMUserDetailModel();
                    Vector<TeamleaderRepsModel> teamleaderReps = new Vector();

                    teamleadDetails = RepManagementDAO.getRepTeamleadDetail(con, userLevelTypeId, teamleadId);
                    teamleaderReps = RepManagementDAO.getTeamleaderReps(con, teamleadId);

                    dataHashMap.put(SCMInterfaceKey.REP_TEAMLEAD_DETAILS, teamleadDetails);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_TEAMLEAD_REPS, teamleaderReps);
                    dataHashMap.put(SCMInterfaceKey.DCM_USER_ID, teamleadId);
                    dataHashMap.put(SCMInterfaceKey.USER_LEVEL_TYPE_ID, userLevelTypeId);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, systemUserId);
                    dataHashMap.put(SCMInterfaceKey.REGION_ID, regionId);



                }
                break;

                case action_gen_user_search:{

                }

                break;

                case action_search_gen_user:{

                Vector<PersonModel> persons=new Vector();
                String personName=(String)paramHashMap.get(SCMInterfaceKey.PERSON_NAME);
                persons=RepManagementDAO.searchGENPersons(con, personName);
                dataHashMap.put(SCMInterfaceKey.PERSON_NAME,personName);
                dataHashMap.put(SCMInterfaceKey.VECTOR_GEN_USER_SEARCH_RESULTS,persons);
                }

                break;
                    
         case action_export_salesreps:
          {
             // Vector<POSSearchExcelModel> dataVec = RequestDao.searchPosDataExcel(con, posDataOwnerIdType, posDataDocNum, posDataManagerName, posDataStkNum, posDataManagerIdType, posDataProposedDoc, posDataManagerIdNum, posDataName, posDataCode, posDataRegion, posDataGover, posDataDistrict, posDataArea, posDataCity, posDataOwnerName, posDataOwnerIdNum, Level, Payment, Channel, posStatusId, stkStatusId, psymentStatusId, posPhone, englishAddress, entryDate, docLocation, supervisorDetailId,supervisorDetailName, teamleaderDetailId, teamleaderDetailName, salesrepDetailId, salesrepDetailName);
            //System.out.println("Action -- action_export_salesreps - check Vector: "+((HttpServletRequest) paramHashMap.get(InterfaceKey.HASHMAP_KEY_REQUEST_FROM_SERVLET)));
              
            String Slach = System.getProperty("file.separator");
              System.out.println("BASE_DIRECTION test values "+paramHashMap.get("baseDirectory"));
              String baseDirectory = (String) paramHashMap.get("baseDirectory");//SCMInterfaceKey.BASE_DIRECTION
              //Vector results = (Vector) paramHashMap.get(SCMInterfaceKey.VECTOR_REP_SEARCH_RESULTS);
              //System.out.println("Action -- action_export_salesreps - check Vector: "+results);
              Vector files =RepManagementDAO.getAllRepsData(con);
              String excelLink = PoiWriteExcelFile.exportExcelSheetForAllRepsData(/*dataVec*/files, baseDirectory);
              dataHashMap.put(SCMInterfaceKey.SEARCH_EXCEL_SHEET_LINK, excelLink);
          }
          break;     
                     
         case action_export_supervisors:
          {
             // Vector<POSSearchExcelModel> dataVec = RequestDao.searchPosDataExcel(con, posDataOwnerIdType, posDataDocNum, posDataManagerName, posDataStkNum, posDataManagerIdType, posDataProposedDoc, posDataManagerIdNum, posDataName, posDataCode, posDataRegion, posDataGover, posDataDistrict, posDataArea, posDataCity, posDataOwnerName, posDataOwnerIdNum, Level, Payment, Channel, posStatusId, stkStatusId, psymentStatusId, posPhone, englishAddress, entryDate, docLocation, supervisorDetailId,supervisorDetailName, teamleaderDetailId, teamleaderDetailName, salesrepDetailId, salesrepDetailName);
            System.out.println("Action -- action_export_supervisors");
              String Slach = System.getProperty("file.separator");
              System.out.println("BASE_DIRECTION test values "+paramHashMap.get("baseDirectory"));
              String baseDirectory = (String) paramHashMap.get("baseDirectory");//SCMInterfaceKey.BASE_DIRECTION
              Vector files =RepManagementDAO.getAllSupervisorsData(con);
              String excelLink = PoiWriteExcelFile.exportExcelSheetForAllSupervisorsData(/*dataVec*/files, baseDirectory);
              dataHashMap.put(SCMInterfaceKey.SEARCH_EXCEL_SHEET_LINK, excelLink);
          }
          break;   
        case action_export_teamleaders:
          {
             // Vector<POSSearchExcelModel> dataVec = RequestDao.searchPosDataExcel(con, posDataOwnerIdType, posDataDocNum, posDataManagerName, posDataStkNum, posDataManagerIdType, posDataProposedDoc, posDataManagerIdNum, posDataName, posDataCode, posDataRegion, posDataGover, posDataDistrict, posDataArea, posDataCity, posDataOwnerName, posDataOwnerIdNum, Level, Payment, Channel, posStatusId, stkStatusId, psymentStatusId, posPhone, englishAddress, entryDate, docLocation, supervisorDetailId,supervisorDetailName, teamleaderDetailId, teamleaderDetailName, salesrepDetailId, salesrepDetailName);
            System.out.println("Action -- action_export_teamleaders");
              String Slach = System.getProperty("file.separator");
              System.out.println("BASE_DIRECTION test values "+paramHashMap.get("baseDirectory"));
              String baseDirectory = (String) paramHashMap.get("baseDirectory");//SCMInterfaceKey.BASE_DIRECTION
              Vector files =RepManagementDAO.getAllTeamleadersData(con);
              String excelLink = PoiWriteExcelFile.exportExcelSheetForAllTeamleadersData(/*dataVec*/files, baseDirectory);
              dataHashMap.put(SCMInterfaceKey.SEARCH_EXCEL_SHEET_LINK, excelLink);
          }
          break;   
                    
                    
                    
                    
                    
                    
            case active_stk_distributer_excel:{
            loadDistForActivation(con, dataHashMap);
            }
            break;

                case active_stk_dist_excel_process :
                {
                    String  dist_Code = (String)paramHashMap.get(SCMInterfaceKey.DISTRIBUTER_CONTROL_SELECT);
                   boolean distcheck= STKDAO.isPOsDistributer(dist_Code);
                   if(!distcheck)
                   {
                        dataHashMap.put(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE,"faild");
                   }else
                   {
                       dataHashMap.put(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE, "");
                       dataHashMap.put(SCMInterfaceKey.DISTRIBUTER_CONTROL_SELECT, dist_Code);

                    }

              }
                break;

                case active_stk_one_distributer_and_export_excel:{

                    String dist_Code=(String)paramHashMap.get(SCMInterfaceKey.POS_CODE);
                    Vector<DistributerStaticDataModel> distributerStaticData = DistributerSTKDataDAO.getDistributerStaticData();
                   Vector<DistributerSTKDetailsModel> distributerSTKData=DistributerSTKDataDAO.getDistributerSTKDetails(dist_Code);
                   dataHashMap.put(SCMInterfaceKey.POS_CODE,dist_Code);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_DISTRIBUTERS_STATIC_DATA,distributerStaticData);
                   dataHashMap.put(SCMInterfaceKey.VECTOR_DISTRIBUTERS_STK_DETAILS,distributerSTKData);
                }
                    break;

                case export_excel_in_stock_stk:{
                    String stockId = (String)paramHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_STOCK_ID);
                    Vector<String> STKRemaining=STKDAO.STKStockRemainingDialsNumbers(con,stockId);
                    dataHashMap.put(SCMInterfaceKey.STOCK_REMAINING_VECTOR,STKRemaining);
                }
                    break;
                case view_barcoderequest_excel:{
                    String destinationPage=(String)paramHashMap.get(SCMInterfaceKey.DESTINATION_PAGE);
                    if(destinationPage==null){
                        destinationPage="0";
                    }
                    Vector <BarcodePOSRequestModel> BracodeRequests=BarcodeDAO.getallRequests(con,destinationPage);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_BARCODE_REQUESTS,BracodeRequests);
                    dataHashMap.put(SCMInterfaceKey.STRING_OF_TOTAL_PAGE_NUMBER,BarcodeDAO.getallRequestsCountPages(con).toString());
                    dataHashMap.put(SCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER,destinationPage);
                }
                case view_stk_dist_request_excel:{
                    Vector <STKDistRequestViewerDTO> distRequests=BarcodeDAO.getallDistRequests(con);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_DIST_REQUESTS,distRequests);
                }
                    break;

                case download_barcoderequest_excel:
//                    String personName = (String)paramHashMap.get(SCMInterfaceKey.BARCODE_PERSON_NAME);
                    dataHashMap.put(SCMInterfaceKey.BARCODE_PERSON_NAME, paramHashMap.get(SCMInterfaceKey.BARCODE_PERSON_NAME));
                        String barcodeRequestId=(String)paramHashMap.get(SCMInterfaceKey.BARCODE_REQUEST_ID);
                        Vector <BarcodeRequestExcelModel> barcodeExcel=BarcodeDAO.getExcelData(con, barcodeRequestId);
                        if (barcodeExcel != null && !barcodeExcel.isEmpty()) {
                        if (barcodeRequestId != null && barcodeRequestId.compareTo("") != 0) {
                            BarcodeDAO.updateDownloadCount(con, barcodeRequestId);
                        }
                    }
                        dataHashMap.put(SCMInterfaceKey.VECTOR_BARCODE_REQUEST_STATICTIS, barcodeExcel);
                        dataHashMap.put(SCMInterfaceKey.BARCODE_REQUEST_ID, barcodeRequestId);
                        break;
                case download_dist_request_excel:{
                        String distRequestId=(String)paramHashMap.get(SCMInterfaceKey.DIST_REQUEST_ID);
                        STKDistRequestViewerDTO distExcel=BarcodeDAO.getDistRequestsById(con, distRequestId);
                        if (distExcel != null ) {
                        if (distRequestId != null && distRequestId.compareTo("") != 0) {
                            BarcodeDAO.updateDistDownloadCount(con, distRequestId);
                        }
                    }
                        dataHashMap.put(SCMInterfaceKey.MODEL_DIST_REQUEST_STATICTIS, distExcel);
                        dataHashMap.put(SCMInterfaceKey.DIST_REQUEST_ID, distRequestId);
                        break;
                }

                case change_pos_payment_status:
                {
                    Vector paymentlevels=POSDAO.getAllPaymentLevel();
                    dataHashMap.put(SCMInterfaceKey.POS_PAYMENT_LEVELS, paymentlevels);
                }

                case view_stk_dist_excel_history :{
                    Vector <DistributerStkExcelViewer> DistributerStkExcelViewers=POSDAO.getAllDistributerStkActivation();
                    dataHashMap.put(SCMInterfaceKey.VECTOR_DIST_REQUESTS,DistributerStkExcelViewers);
                }
                case ACTION_PROCESS_DELETE_STKS :{
                    importExcelToDeleteSTK(con, paramHashMap, dataHashMap, strUserID);
                }
                   
                default:{
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                }


                
                break;

            }

        } catch (Exception objExp) {
            objExp.printStackTrace();
        }
        return dataHashMap;
    }
    // Rep Management Functions

    public static void putRepAddOrUpdateBasicData(HashMap paramHashMap, HashMap dataHashMap, Connection con) {
        /*System.out.println("func putRepAddOrUpdateBasicData");
        System.out.println("...action_submit_user_level_type...");
        
                    
        String userLevelTypeId = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_LEVEL_TYPE_ID);
        String userFullName = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_FULL_NAME);
        String userAddress = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_ADDRESS);
        String userEmail = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_EMAIL);
        String userMobile = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_MOBILE);
        String userId = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
        System.out.println("^^^^^^^ DCM_USER_ID ^^^^^^^^ "+userId+" "+userLevelTypeId);
        String regionId = (String) paramHashMap.get(SCMInterfaceKey.REGION_ID);
        String genUserId=(String)paramHashMap.get(SCMInterfaceKey.PERSON_ID);

        DCMUserModel dcmUser = new DCMUserModel();
        DCMUserDetailModel dcmUserDetail = new DCMUserDetailModel();

        dcmUser.setDcmUserId(userId == null ? "" : userId);
        dcmUser.setUserLevelTypeId(userLevelTypeId == null ? "" : userLevelTypeId);
        dcmUser.setUserId(genUserId==null?"":genUserId);

        dcmUser.setRegionId(regionId == null ? "" : regionId);

        dcmUserDetail.setUserFullName(userFullName == null ? "" : userFullName);
        dcmUserDetail.setUserAddress(userAddress == null ? "" : userAddress);
        dcmUserDetail.setUserEmail(userEmail == null ? "" : userEmail);
        dcmUserDetail.setUserMobile(userMobile == null ? "" : userMobile);

        dataHashMap.put(SCMInterfaceKey.DCM_USER_MODEL, dcmUser);
        dataHashMap.put(SCMInterfaceKey.DCM_USER_DETAIL_MODEL, dcmUserDetail);*/
        
        System.out.println("func putRepAddOrUpdateBasicData");
        System.out.println("...action_submit_user_level_type or action_update...");
        
                    
        String userLevelTypeId = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_LEVEL_TYPE_ID);
        String userFullName = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_FULL_NAME);
        String userAddress = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_ADDRESS);
        String userEmail = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_EMAIL);
        String userMobile = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_MOBILE);
        String userId = (String) paramHashMap.get(SCMInterfaceKey.DCM_USER_ID);
        String regionId = (String) paramHashMap.get(SCMInterfaceKey.REGION_ID);
        String genUserId=(String)paramHashMap.get(SCMInterfaceKey.PERSON_ID);
        System.out.println("region id again : "+regionId+" and genUserId : "+genUserId);
        

        DCMUserModel dcmUser = new DCMUserModel();
        DCMUserDetailModel dcmUserDetail = new DCMUserDetailModel();

        dcmUser.setDcmUserId(userId == null ? "" : userId);
        dcmUser.setUserLevelTypeId(userLevelTypeId == null ? "" : userLevelTypeId);
        dcmUser.setUserId(genUserId==null?"":genUserId);

        dcmUser.setRegionId(regionId == null ? "" : regionId);

        dcmUserDetail.setUserFullName(userFullName == null ? "" : userFullName);
        dcmUserDetail.setUserAddress(userAddress == null ? "" : userAddress);
        dcmUserDetail.setUserEmail(userEmail == null ? "" : userEmail);
        dcmUserDetail.setUserMobile(userMobile == null ? "" : userMobile);

        dataHashMap.put(SCMInterfaceKey.DCM_USER_MODEL, dcmUser);
        dataHashMap.put(SCMInterfaceKey.DCM_USER_DETAIL_MODEL, dcmUserDetail);
        ////////////////lamya////////////////////////////////
        dataHashMap.put(SCMInterfaceKey.TEAMLEAD_ID, "");
                    dataHashMap.put(SCMInterfaceKey.SUP_ID, "");
                 
                    
                    String districtID="";
                    Vector<RepSupervisorModel> repSupervisors = new Vector();
                    Vector<RepTeamleaderModel> repTeamleaders = new Vector();
                    Vector<DCMUserModel> supervisors = new Vector();
                    Vector<DCMUserModel> teamleaders = new Vector();
                  /*districtID = dcmUser.getRegionId();
                            
                  if (districtID.compareTo("")!=0)
                        regionId=RepSupDAO.getDistrictRegionId(con, districtID);*/
                  
                  System.out.println("region id from district id "+regionId+" user id "+genUserId+" district id "+districtID); 
                            
                  if (regionId.compareTo("")!=0)
                  {
                      System.out.println("FILLLLLLL");
                            supervisors=RepSupDAO.getRegionSupervisors(con, regionId);
                            teamleaders=RepSupDAO.getRegionTeamleaders(con, regionId);
                  }
                  
                            dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGION_TEAMLEADERS, teamleaders);
                            dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGION_SUPERVISORS, supervisors);
                  
                            repSupervisors = RepManagementDAO.getRepSupervisors(con, genUserId);
                            repTeamleaders = RepManagementDAO.getRepTeamleaders(con, genUserId);
                            dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REP_SUPERVISORS, repSupervisors);
                            dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REP_TEAMLEADS, repTeamleaders);
                            
                            for(int i=0; i< supervisors.size();i++)
                            {
                                String regionSupervisorId = ((DCMUserModel) supervisors.get(i)).getDcmUserId();
                                for(int j=0; j<repSupervisors.size();j++)
                                {
                                    String repSupervisorId = ((RepSupervisorModel) repSupervisors.get(j)).getSupId();
                                    if(regionSupervisorId.compareTo(repSupervisorId)==0)
                                    {
                                     System.out.println("REP SUPER ID ^^^^^^^ : "+repSupervisorId);
                                        dataHashMap.put(SCMInterfaceKey.SUP_ID, repSupervisorId);
                                    }
                                 
                                }
                            }
                            
                            for(int i=0; i< teamleaders.size();i++)
                            {
                                String regionTeamleaderId = ((DCMUserModel) teamleaders.get(i)).getDcmUserId();
                                for(int j=0; j<repTeamleaders.size();j++)
                                {
                                    String repTeamleaderId = ((RepTeamleaderModel) repTeamleaders.get(j)).getTeamleadId();
                                    if(regionTeamleaderId.compareTo(repTeamleaderId)==0)
                                      dataHashMap.put(SCMInterfaceKey.TEAMLEAD_ID, repTeamleaderId);
                                    
                                }
                            }
       
    }

    public static void getAndPutRegionGovernorates(HashMap paramHashMap, HashMap dataHashMap, Connection con) {

        Vector<RegionModel> regionGovernorates = new Vector();
        String regionId = (String) paramHashMap.get(SCMInterfaceKey.REGION_ID);
        System.out.println("REGION IDDDD "+regionId);
        if (regionId != null && regionId.compareTo("")!=0) {

            regionGovernorates = RepManagementDAO.getGovernorates(con, regionId);
            dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGION_TEAMLEADERS, RepSupDAO.getRegionTeamleaders(con, regionId));
            dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGION_SUPERVISORS, RepSupDAO.getRegionSupervisors(con, regionId));
            dataHashMap.put(SCMInterfaceKey.VECTOR_REGION_GOVERNORATES, regionGovernorates);
            dataHashMap.put(SCMInterfaceKey.REGION_ID, regionId);
        }
    }

    public static void getAndPutGovernorateCities(HashMap paramHashMap, HashMap dataHashMap, Connection con) {

        Vector<RegionModel> governorateCities = new Vector();
        String governorateId = (String) paramHashMap.get(SCMInterfaceKey.GOVERNORATE_ID);

        if (governorateId != null) {

            governorateCities = RepManagementDAO.getCities(con, governorateId);
            dataHashMap.put(SCMInterfaceKey.VECTOR_GOVERNORATE_CITIES, governorateCities);
            dataHashMap.put(SCMInterfaceKey.GOVERNORATE_ID, governorateId);
        }

    }

    public static void getAndPutCityDistricts(HashMap paramHashMap, HashMap dataHashMap, Connection con) {
        Vector<RegionModel> cityDistricts = new Vector();
        String cityId = (String) paramHashMap.get(SCMInterfaceKey.CITY_ID);

        if (cityId != null) {

            cityDistricts = RepManagementDAO.getDistricts(con, cityId);
            dataHashMap.put(SCMInterfaceKey.VECTOR_CITY_DISTRICTS, cityDistricts);
            dataHashMap.put(SCMInterfaceKey.CITY_ID, cityId);
        }
    }

    public static void getAndPutDistrictAreas(HashMap paramHashMap, HashMap dataHashMap, Connection con) {
        Vector<RegionModel> districtAreas = new Vector();
        String districtId = (String) paramHashMap.get(SCMInterfaceKey.DISTRICT_ID);

        if (districtId != null) {

            districtAreas = RepManagementDAO.getAreas(con, districtId);
            dataHashMap.put(SCMInterfaceKey.VECTOR_DISTRICT_AREAS, districtAreas);
            dataHashMap.put(SCMInterfaceKey.DISTRICT_ID, districtId);

        }
    }

    public static void searchRepOrSup(HashMap paramHashMap, HashMap dataHashMap, Connection con) {
        System.out.println("SEARCH");
        String destinationPage=(String) paramHashMap.get(SCMInterfaceKey.DESTINATION_PAGE);

        if(destinationPage==null){
            destinationPage="0";
        }


        String passedRegionId = (String) paramHashMap.get(SCMInterfaceKey.REGION_ID);
        String passedSearchName = (String) paramHashMap.get(SCMInterfaceKey.SEARCH_NAME);
        String passedUserLevelTypeId = (String) paramHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);



        Vector<DCMUserModel> repSearchResults = new Vector();
        Integer regionId = null;
        Integer userLevelTypeId = null;
        String searchName = null;
        if (passedRegionId != null && !passedRegionId.trim().equalsIgnoreCase("")) {
            regionId = Integer.parseInt(passedRegionId);
        }
        if (passedUserLevelTypeId != null && !passedUserLevelTypeId.trim().equalsIgnoreCase("")) {
            userLevelTypeId = Integer.parseInt(passedUserLevelTypeId);
        }
        if (passedSearchName != null) {
            if (passedSearchName.trim().equalsIgnoreCase("")) {
                //Keep it null
            } else {
                searchName = passedSearchName;
            }
        }

        String totalPageNumbers= RepManagementDAO.getAllRepsAndSupPageCount(con,searchName,regionId,userLevelTypeId);
        repSearchResults = RepManagementDAO.searchRepsAndSupervisor(con, searchName, regionId, userLevelTypeId,destinationPage);
        dataHashMap.put(SCMInterfaceKey.VECTOR_REP_SEARCH_RESULTS, repSearchResults);
        Vector<RegionModel> regions = new Vector();
        Vector<DCMUserLevelTypeModel> repLevels = new Vector();
        regions = RepManagementDAO.getRegions(con);
        repLevels = RepManagementDAO.getUserLevelsForSupervisorAndRep(con);
        dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_REGIONS, regions);
        dataHashMap.put(SCMInterfaceKey.VECTOR_REP_LEVEL_TYPES, repLevels);
        dataHashMap.put(SCMInterfaceKey.REGION_ID, passedRegionId);
        dataHashMap.put(SCMInterfaceKey.SEARCH_NAME, passedSearchName);
        dataHashMap.put(SCMInterfaceKey.USER_LEVEL_TYPE_ID, passedUserLevelTypeId);
        dataHashMap.put(SCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER,destinationPage);
        dataHashMap.put(SCMInterfaceKey.STRING_OF_TOTAL_PAGE_NUMBER,totalPageNumbers);

    }
    //Rep Management End Of Functions

    private static void loadDistForActivation(Connection con,HashMap dataHashMap){

    STKDAO.updateDistributersVerifiedStatus(con,SCMInterfaceKey.DIST_STOCK_ID);
          dataHashMap.put(SCMInterfaceKey.DIST_MESSAGE, "");
          Vector <POSModel> distributers=POSDAO.getAllDistributer(con);
          dataHashMap.put(SCMInterfaceKey.VECTOR_ALL_DISTRIBUTERS,distributers);
    }

    private static void importExcelToDeleteSTK(Connection con, HashMap paramHashMap, HashMap dataHashMap, String strUserID) {
        String delType = (String) paramHashMap.get(SCMInterfaceKey.SELECT_FOR_ONE_OR_MANY);
        java.sql.Statement st = null;
        try {
            st = con.createStatement();
            STKDAO stkDAO = new STKDAO(st);
            Vector<String> invalidDials = new Vector<String>();
            if (delType.compareTo(SCMInterfaceKey.CONSTANT_ONE_DELETION) == 0) {
                String stkNumber = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_INPUT_TEXT_STK_NUMBER);
                if (stkDAO.deleteStkNumber(stkNumber, strUserID) < 1) {
                    invalidDials.add(stkNumber);
                    dataHashMap.put(SCMInterfaceKey.HTML_TABLE_ERROR_STKS, buildErrorTable(invalidDials));
                }
                else dataHashMap.put(SCMInterfaceKey.HTML_TABLE_ERROR_STKS, null);
            } else if (delType.compareTo(SCMInterfaceKey.CONSTANT_FILE_DELETION) == 0) {
                File excelFile = null;

                excelFile = GetUploadedFile.getFile(paramHashMap, SCMInterfaceKey.CONSTANT_SCM_UPLOAD_DIRECTORY);
                Vector<String> excelDials = ExcelUtil.readExcelFirstCol(excelFile);
                if (excelDials.size() > 0) {
                    for (String dial : excelDials) {
                        if (stkDAO.deleteStkNumber(dial, strUserID) < 1) {
                            invalidDials.add(dial);
                        }
                    }
                    
                    dataHashMap.put(SCMInterfaceKey.HTML_TABLE_ERROR_STKS, buildErrorTable(invalidDials));
                    dataHashMap.put(SCMInterfaceKey.EXCEL_ERROR_STKS, ExcelUtil.writeExcelFile(invalidDials, excelFile.getParent()));

                }
                else {
                    dataHashMap.put(SCMInterfaceKey.HTML_TABLE_ERROR_STKS, null);
                    dataHashMap.put(SCMInterfaceKey.EXCEL_ERROR_STKS, null);
                }

            }
        } catch (Exception ex) {
        } finally {
            DBUtil.close(st);            
        }
    }

    private static String buildErrorTable(Vector<String> errorStks) {
        StringBuilder htmlTable = new StringBuilder("<table style=\"BORDER-COLLAPSE: collapse\" cellSpacing=3 cellPadding=3 width=\"80%\" border=\"1\"><tr><td class=TableTextNote nowrap align=center>STK Number</td><td class=TableTextNote nowrap align=center>Error Description</td><tr>");
        for (String dial : errorStks) {
            htmlTable.append("<tr><td class=TableTextNote nowrap align=center>");
            htmlTable.append(dial);
            htmlTable.append("</td><td class=TableTextNote nowrap align=center>Invalid STK number</td></tr>");
        }
        htmlTable.append("</table>");
        return htmlTable.toString();
    }

    private static String getUserId(String userId, HashMap paramHashMap) {
        userId = userId == null ? (String) ((HttpServletRequest) paramHashMap.get(InterfaceKey.HASHMAP_KEY_REQUEST_FROM_SERVLET)).getSession(false).getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID) : userId;
        return userId;
    }
    
//    public static void main(String [] args){
//        HashMap paramHashMap = new HashMap();
//        paramHashMap.put(SCMInterfaceKey.SELECT_FOR_ONE_OR_MANY, SCMInterfaceKey.CONSTANT_ONE_DELETION);
//        
//        
//    
//    }
}
