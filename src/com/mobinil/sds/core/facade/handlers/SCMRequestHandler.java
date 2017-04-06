package com.mobinil.sds.core.facade.handlers;

import com.mobinil.sds.core.system.calidus.FilesZipper;
import com.mobinil.sds.core.system.calidus.ParticipantFileExporter;
import com.mobinil.sds.core.system.calidus.PositionFileExporter;
import com.mobinil.sds.core.system.dcm.genericModel.DAO.GenericModelDAO;
import com.mobinil.sds.core.system.dcm.genericModel.GenericModel;
import com.mobinil.sds.core.system.dcm.pos.model.POSDetailModel;
import com.mobinil.sds.core.system.dcm.region.model.RegionModel;
import com.mobinil.sds.core.system.payment.model.PaymentModel;
import com.mobinil.sds.core.system.regionPOSReport.dao.RegionPOSReportDAO;
import com.mobinil.sds.core.system.request.model.PaymentMethodModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

import com.mobinil.sds.core.system.request.dao.RequestDao;
import com.mobinil.sds.core.system.request.model.DistributerSTKDetailsModel;
import com.mobinil.sds.core.system.request.model.DistributerStaticDataModel;
import com.mobinil.sds.core.system.request.model.GeneralHistory;
import com.mobinil.sds.core.system.request.model.PosIqrarModel;
import com.mobinil.sds.core.system.request.model.PosModel;
import com.mobinil.sds.core.system.request.model.UserDataModel;
import com.mobinil.sds.core.system.request.utility.PDFIqrarPrinting;
import com.mobinil.sds.core.system.sa.importdata.DataImportEngine;
import com.mobinil.sds.core.system.sa.importdata.dao.DataImportTableDefDAO;
import com.mobinil.sds.core.system.scm.dao.POSDAO;
import com.mobinil.sds.core.system.scm.dao.PoiWriteExcelFile;
import com.mobinil.sds.core.system.scm.dao.RepManagementDAO;
import com.mobinil.sds.core.system.scm.dao.STKDAO;
import com.mobinil.sds.core.system.scm.model.POSSearchExcelModel;
import com.mobinil.sds.core.utilities.GetUploadedFile;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.sa.AdministrationInterfaceKey;
import com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey;
import java.io.*;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class SCMRequestHandler {

    public static final int action_open_request = 0;
    public static final int action_store_request = 1;
    public static final int action_store_after_print_request = 2;
    public static final int action_pos_data_entry = 3;
    public static final int action_pos_data_entry_store = 4;
    public static final int action_pos_data_similar_name = 5;
    public static final int action_pos_data_similar_city = 6;
    public static final int action_pos_data_similar_area = 7;
    public static final int action_show_iqrar_print_data_entry = 8;
    public static final int action_one_pos_iqrar_print = 9;
    public static final int actioin_one_pos_iqrar_download = 10;
    public static final int action_many_pos_iqrar_save = 11;
    public static final int action_many_pos_iqrar_view = 12;
    public static final int view_pos_data_management = 13;
    public static final int search_pos_data_management = 14;
    public static final int show_detail_pos_data_management = 15;
    public static final int action_pos_data_entry_with_stk = 16;
    public static final int action_save_pos_data_entry_with_stk = 17;
    public static final int action_pos_data_edit = 18;
    public static final int action_pos_data_edit_store = 19;
    public static final int active_pos_export_excel_management = 20;
    public static final int action_return_generate_code = 21;
    public static final int action_pos_data_view_history = 22;
    public static final int action_new_generate_code = 23;
    public static final int action_change_stk_number = 24;
    public static final int action_change_stk_number_page = 25;
    public static final int import_template_dates_for_pos = 26;
    public static final int action_generate_code = 27;
    public static final int search_pos_excel = 28;
    public static final int action_get_region_child = 29;
    public static final int action_get_region_child_for_edit = 30;
    public static final int action_param_report_next = 31;
    public static final int ACTION_PARAM_REPORT_EXPORT_EXCEL = 32;
    public static final int ACTION_PARAM_REPORT_UPLOAD_POS = 33;
    
    public static final int ACTION_SHOW_GENETATE_CALIDUS_FILE_SCREEN = 34;
    public static final int ACTION_GENERATE_CALIDUS_FILE_PROCESS = 35;
    public static final int ACTION_CALIDUS_FILE_DOWNLOAD_SCREEN = 36;
    public static final int ACTION_CALIDUS_FILE_DOWNLOAD_PROCESS=37;
    public static final int action_update_pos_letter_code = 38;
    public static final int action_show_update_pos_letter_code = 39;
    
   
    private static PosModel setPOSDataPOSDetail(/*PosModel posGeneralData,*/ /*POSDetailModel posDetailModel,*/ String posDetailId, Connection con)
    {
        
                    /////////////////////////////////
                    String myCalidus = "";
                    String mySign = "";
                    String myQC = "";
                    String myL1 = "";
                    String myEX = "";
                    String myNomad = "";
                    String myMobicash = "";
                    POSDetailModel posDetailModel = new POSDetailModel();
                    PosModel posGeneralData = new PosModel();
                    try
                    {
                        ResultSet rs = RequestDao.getFlagsByPosDetailId(con, posDetailId);
                        System.out.println("rs "+rs);
                        if (rs.next())
                        {
                            System.out.println("insde RS...");
                            posDetailModel = new POSDetailModel();
                            posGeneralData = new PosModel();
                            myCalidus = rs.getString("report_to_calidus");
                            System.out.println("after calidus : "+myCalidus);
                            mySign = rs.getString("has_sign");
                            myQC = rs.getString("is_quality_club");
                            myL1 = rs.getString("is_level_one");
                            myEX = rs.getString("is_exclusive");
                            myNomad = rs.getString("is_nomad");
                            myMobicash = rs.getString("is_mobicash");
                            if (myCalidus!=null && myCalidus.equals("1"))
                                {posGeneralData.setReportToCalidus(true);posDetailModel.setReportToCalidus(true);}

                            if (myEX!=null && myEX.equals("1"))
                                {posGeneralData.setIsEX(true);posDetailModel.setIsEX(true);}
                            if (myL1!=null && myL1.equals("1"))
                                {posGeneralData.setIsL1(true);posDetailModel.setIsL1(true);}
                            if (myMobicash!=null && myMobicash.equals("1"))
                                {posGeneralData.setIsMobicash(true);posDetailModel.setIsMobicash(true);}
                            if (myNomad!=null && myNomad.equals("1"))
                                {posGeneralData.setIsNomad(true);posDetailModel.setIsNomad(true);}
                            if (myQC!=null && myQC.equals("1"))
                                {posGeneralData.setIsQC(true);posDetailModel.setIsQC(true);}
                            if (mySign!=null && mySign.equals("1"))
                                {posGeneralData.setIsSignSet(true);posDetailModel.setIsSignSet(true);}
                            //new 
                            posGeneralData.setPosDetailModel(posDetailModel);
                            System.out.println("SET POS : "+posGeneralData.getPosDetailModel());
                            
                        }
                        
                        // new - end
                    }
                    catch (Exception e){System.out.println("EXCEPTION : "+e);}
                        ///////////////////////////////////////

                    return posGeneralData;
    }
    
    
    private static PosModel setPOSDataPOSDetailFromParams(PosModel posModel, POSDetailModel mdl, HashMap paramHashMap)
    {
        String setSign = (String) paramHashMap.get(SCMInterfaceKey.SET_SIGN);
        String setReportToCalidus = (String) paramHashMap.get(SCMInterfaceKey.REPORT_TO_CALIDUS);
        String setQC = (String) paramHashMap.get(SCMInterfaceKey.IS_QC);
        String setL1 = (String) paramHashMap.get(SCMInterfaceKey.IS_L1);
        String setEX = (String) paramHashMap.get(SCMInterfaceKey.IS_EX);
        String setMobicash = (String) paramHashMap.get(SCMInterfaceKey.IS_MOBICASH);
        String setNomad = (String) paramHashMap.get(SCMInterfaceKey.IS_NOMAD);
        System.out.println("(String)paramHashMap.get(SCMInterfaceKey.IS_MOBICASH_DIAL_NUM) "+(String)paramHashMap.get(SCMInterfaceKey.IS_MOBICASH_DIAL_NUM));
        long setMobicashNum = ((String)paramHashMap.get(SCMInterfaceKey.IS_MOBICASH_DIAL_NUM)==null || ((String)paramHashMap.get(SCMInterfaceKey.IS_MOBICASH_DIAL_NUM)).compareTo("")==0) ?  0: Long.parseLong((String)paramHashMap.get(SCMInterfaceKey.IS_MOBICASH_DIAL_NUM));
         
        
        if (setMobicash != null && setMobicash.equals("yes"))
            {
              posModel.setIsMobicash(true);
              mdl.setIsMobicash(true);
              posModel.setMobicashNum(setMobicashNum);
              mdl.setMobicashNum(setMobicashNum);
            }
         if (setNomad != null && setNomad.equals("yes"))
            {
              posModel.setIsNomad(true);
              mdl.setIsNomad(true);
            }
         if (setEX != null && setEX.equals("yes"))
            {
              posModel.setIsEX(true);
              mdl.setIsEX(true);
            }
        if (setL1 != null && setL1.equals("yes"))
            {
              posModel.setIsL1(true);
              mdl.setIsL1(true);
            }
        if (setQC != null && setQC.equals("yes"))
            {
              posModel.setIsQC(true);
              mdl.setIsQC(true);
            }
        if (setReportToCalidus != null && setReportToCalidus.equals("yes"))
            {
              posModel.setReportToCalidus(true);
              mdl.setReportToCalidus(true);
            }
                   
        if (setSign != null && setSign.equals("yes"))
            {
              posModel.setIsSignSet(true);
              mdl.setIsSignSet(true);
            }
        posModel.setPosDetailModel(mdl);
        return posModel;
    }
    
    public static HashMap handle(String action, HashMap paramHashMap, java.sql.Connection con) {
        System.out.println("welcome to SCMRequestHandler with action string : "+action);
        int actionType = 0;
        String userDataName="";
        HashMap dataHashMap = new HashMap(100);
        HashMap<String, RegionModel> allRegions = null;
        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        if (strUserID != null && strUserID.compareTo("") != 0) {
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
        } else {
            strUserID = getUserId(strUserID, paramHashMap);
        }


        try {
            
            if (action.compareTo(SCMInterfaceKey.ACTION_OPEN_REQUEST) == 0) {
                actionType = action_open_request;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_STORE_REQUEST) == 0) {
                actionType = action_store_request;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_STORE_AFTER_PRINT_REQUEST) == 0) {
                actionType = action_store_after_print_request;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_POS_DATA_ENTRY) == 0) {
                actionType = action_pos_data_entry;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_POS_DATA_ENTRY_STORE) == 0) {
                actionType = action_pos_data_entry_store;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_POS_DATA_SIMILAR_NAME) == 0) {
                actionType = action_pos_data_similar_name;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_POS_DATA_SIMILAR_CITY) == 0) {
                actionType = action_pos_data_similar_city;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_POS_DATA_SIMILAR_AREA) == 0) {
                actionType = action_pos_data_similar_area;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_VIEW_IQRAR_PRINT_DATA_ENTRY) == 0) {
                actionType = action_show_iqrar_print_data_entry;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_ONE_POS_IQRAR_PRINT) == 0) {
                actionType = action_one_pos_iqrar_print;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_ONE_POS_IQRAR_DOWNLOAD) == 0) {
                actionType = actioin_one_pos_iqrar_download;
            }
            
            
            if (action.compareTo(SCMInterfaceKey.ACTION_CALIDUS_FILE_DOWNLOAD_SCREEN) == 0) {
                actionType = ACTION_CALIDUS_FILE_DOWNLOAD_SCREEN;
            }
            
            
            if (action.compareTo(SCMInterfaceKey.ACTION_MANY_POS_IQRAR_SAVE) == 0) {
                actionType = action_many_pos_iqrar_save;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_MANY_POS_IQRAR_VIEW) == 0) {
                actionType = action_many_pos_iqrar_view;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_VIEW_POS_DATA_MANAGEMENT) == 0) {
                actionType = view_pos_data_management;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_SEARCH_POS_DATA_MANAGEMENT) == 0) {
                actionType = search_pos_data_management;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_SHOW_DETAIL_POS_DATA_MANAGEMENT) == 0) {
                actionType = show_detail_pos_data_management;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_POS_DATA_ENTRY_WITH_STK) == 0) {
                actionType = action_pos_data_entry_with_stk;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_SAVE_POS_DATA_ENTRY_WITH_STK) == 0) {
                actionType = action_save_pos_data_entry_with_stk;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_POS_DATA_EDIT) == 0) {
                actionType = action_pos_data_edit;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_POS_DATA_EDIT_STORE) == 0) {
                actionType = action_pos_data_edit_store;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_POS_EXPORT_EXCEL_MANAGEMENT) == 0) {
                actionType = active_pos_export_excel_management;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_RETURN_GENERATE_CODE) == 0) {
                actionType = action_return_generate_code;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_POS_DATA_VIEW_HISTORY) == 0) {
                actionType = action_pos_data_view_history;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_NEW_GENERATE_CODE) == 0) {
                actionType = action_new_generate_code;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_CHANGE_STK_NUMBER) == 0) {
                actionType = action_change_stk_number;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_CHANGE_STK_NUMBER_PAGE) == 0) {
                actionType = action_change_stk_number_page;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_GENERATE_CODE) == 0) {
                actionType = action_generate_code;
            }
            if (action.compareTo(SCMInterfaceKey.SEARCH_POS_EXCEL) == 0) {
                actionType = search_pos_excel;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_GET_REGION_CHILD) == 0 || action.compareTo(SCMInterfaceKey.ACTION_GET_REGION_CHILD_WITH_STK) == 0) {
                actionType = action_get_region_child;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_GET_REGION_CHILD_EDIT) == 0) {
                actionType = action_get_region_child_for_edit;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_PARAM_REPORT_NEXT) == 0) {
                actionType = action_param_report_next;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_PARAM_REPORT_UPLOAD_POS) == 0) {
                actionType = ACTION_PARAM_REPORT_UPLOAD_POS;
            }
            if (action.compareTo(SCMInterfaceKey.ACTION_PARAM_REPORT_EXPORT_EXCEL) == 0) {
                actionType = ACTION_PARAM_REPORT_EXPORT_EXCEL;
            }
            
            
            
            if (action.compareTo(SCMInterfaceKey.ACTION_GENERATE_CALIDUS_FILE_PROCESS) == 0) {
                actionType = ACTION_GENERATE_CALIDUS_FILE_PROCESS;
            }
            
            if (action.compareTo(SCMInterfaceKey.ACTION_SHOW_GENETATE_CALIDUS_FILE_SCREEN) == 0) {
                actionType = ACTION_SHOW_GENETATE_CALIDUS_FILE_SCREEN;
            }
            
            if (action.compareTo(SCMInterfaceKey.ACTION_SHOW_GENETATE_CALIDUS_FILE_SCREEN) == 0) {
                actionType = ACTION_CALIDUS_FILE_DOWNLOAD_PROCESS;
            }
            
            if (action.compareTo(SCMInterfaceKey.ACTION_UPDATE_POS_LETTER_CODE) == 0) {
                actionType = action_update_pos_letter_code;
            }
            
            if (action.compareTo(SCMInterfaceKey.ACTION_SHOW_UPDATE_POS_LETTER_CODE) == 0) {
                actionType = action_show_update_pos_letter_code;
            }

            switch (actionType) {
                case ACTION_CALIDUS_FILE_DOWNLOAD_PROCESS:
                    
                    break;
                    
                    case action_update_pos_letter_code:
                    dataHashMap.put(SCMInterfaceKey.POS_CODE_UPDATED,null);
                    System.out.println("SCMInterfaceKey.POS_CODE_UPDATED "+paramHashMap.get(SCMInterfaceKey.POS_CODE_UPDATED));
                    String oldPOSCode = (String) paramHashMap.get(SCMInterfaceKey.POS_OLD_CODE);
                    String newPOSCode = (String) paramHashMap.get(SCMInterfaceKey.POS_NEW_LETTER_CODE);
                    System.out.println("old : "+oldPOSCode+"  new : "+newPOSCode);
                    boolean codeUpdated = RequestDao.updatePOSLetterCode(con, oldPOSCode, newPOSCode);
                    if (codeUpdated)
                        dataHashMap.put(SCMInterfaceKey.POS_CODE_UPDATED,"yes");
                    else dataHashMap.put(SCMInterfaceKey.POS_CODE_UPDATED,"no");
                    break;
                        
                        
                    case action_show_update_pos_letter_code:
                        System.out.println("action SHOW POS CODE UPDATE");
                    dataHashMap.put(SCMInterfaceKey.POS_CODE_UPDATED,null);
                    
                    break;
                        
                        
                        
                case ACTION_CALIDUS_FILE_DOWNLOAD_SCREEN:
                    dataHashMap.put(SCMInterfaceKey.CALIDUS_ZIP_FILES_VECTOR, RequestDao.getCalidusCreatedFiles(con));
                    break;
                
                case ACTION_GENERATE_CALIDUS_FILE_PROCESS:{
                
                    RequestDao.clearCalidusCurrent(con);
                    System.out.println("INSERT CALIDUS CURRENT");
                    boolean inserted = RequestDao.insertCalidusCurrent(con);
                    boolean updated = RequestDao.updateCalidusCurrent(con);
                    boolean deleted = RequestDao.deleteCalidusCurrent(con);
                    boolean added = RequestDao.addRemovedCurrentRecords(con);
                    System.out.println("CALIDUS inserted and updated\n");
                    
                    //generate files
                    ResultSet rs = RequestDao.getCalidusView(con);
                    //position file
                    PositionFileExporter.constructFile(rs);
                    rs = RequestDao.getCalidusView(con);
                    //participant file
                    ParticipantFileExporter.constructFile(rs);
                    
                    //zip files
                    FilesZipper appZip = new FilesZipper();
                    System.out.println("before generate Zip List");
                    appZip.generateFileList(new File(FilesZipper.SOURCE_FOLDER));
                    Date d = new Date();
                    String sDate = d.toString();
                    String zipFileName = (FilesZipper.OUTPUT_ZIP_FILE+"_"+sDate+".zip").replaceAll("\\s+","");
                    appZip.zipIt(zipFileName);
                    RequestDao.insertCalidusFile(con,zipFileName);
                    
                    
                    deleted = RequestDao.deleteCalidusReporting(con);
                    added = RequestDao.addRecordsToReporting(con);
                    deleted = RequestDao.clearDirtyFlagForCalidusRecords(con);
                    //RequestDao.clearCalidusCurrent(con);
                    
                    
                }
                break;
                //case ACTION_DOWNLOAD_CALIDUS_FILES:break;
                case ACTION_PARAM_REPORT_UPLOAD_POS: {

                    File excelFile = GetUploadedFile.getFile(paramHashMap, SCMInterfaceKey.CONSTANT_SCM_UPLOAD_DIR);
                    Vector<String> posCode = getExcelPOSCodes(excelFile.getAbsolutePath());
                    generateExcel(con, paramHashMap, dataHashMap, posCode);

                }
                break;
                case ACTION_PARAM_REPORT_EXPORT_EXCEL: {
                    String strParamReportVal = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_SELECT_PARAMETER);
                    String selectedUserId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_SELECT_USER_PARAMETER);
                    Vector<String> posCode = RequestDao.getUserPosCodesById(strParamReportVal.compareTo("4") == 0 ? true : false, selectedUserId);
                    generateExcel(con, paramHashMap, dataHashMap, posCode);
                }
                break;
                case action_param_report_next: {
                    String strParamReportVal = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_SELECT_PARAMETER);
                    if (strParamReportVal.compareTo("3") == 0 || strParamReportVal.compareTo("4") == 0) {
                        dataHashMap.put(SCMInterfaceKey.HASHMAP_USERS_PER_LEVEL, RequestDao.getUsersByLevel(con, strParamReportVal));
                    }
                    dataHashMap.put(SCMInterfaceKey.CONTROL_SELECT_PARAMETER, strParamReportVal);

                }
                break;
                case action_open_request:

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_DATE, dateFormat.format(calendar.getTime()).toString());
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_SUPERVISOR_LIST, RequestDao.getSupervisorList(con));
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_QUANTITY_AVAILABLE, RequestDao.getAvailableStkCount(con));

                    break;


                case action_store_request:

                    calendar = Calendar.getInstance();
                    dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String requestDate = dateFormat.format(calendar.getTime()).toString();
                    String stkQuantity = (String) paramHashMap.get(SCMInterfaceKey.REP_KIT_STK_QUANTITY_REQUIRED);
                    String posQuantity = (String) paramHashMap.get(SCMInterfaceKey.REP_KIT_POS_QUANTITY);
                    String supervisorId = (String) paramHashMap.get(SCMInterfaceKey.REP_KIT_SUPERVISOR_ID);
                    int posLastID = RequestDao.getPosCount(con);
                    String greatestCode = RequestDao.getPosGreatestCode(con, "1", "9");
                    System.out.println("greatestCode : " + greatestCode);
                    greatestCode = greatestCode.replace(".001", "");
                    String posTo;
                    String posFrom = "0";
                    Vector<String> validStk = new Vector<String>();
                    Vector<String> inValidStk = new Vector<String>();

                    int generatedCodeInt = greatestCode != null && greatestCode.compareTo("") != 0
                            ? Integer.parseInt(greatestCode.substring(0, greatestCode.indexOf("."))) : 0;

                    if (posQuantity.equals("") || posQuantity == null) {
                        posQuantity = "0";
                        posTo = "0";
                        posFrom = "0";
                    } else {




                        posFrom = (generatedCodeInt) + "";
                        posTo = (generatedCodeInt + Integer.parseInt(posQuantity) - 1) + "";
                    }

                    if (stkQuantity.equals("") || stkQuantity == null) {
                        stkQuantity = "0";
                    }

                    for (int i = 0; i < Integer.parseInt(stkQuantity); i++) {
                        String name = "stkNo" + i;
                        String stkNum = (String) paramHashMap.get(name);
                        if (RequestDao.checkStkNumberAvailable(con, stkNum, SCMInterfaceKey.POS_STOCK_ID)) {
                            validStk.add(stkNum);
                        } else {
                            inValidStk.add(stkNum);
                        }
                    }

                    if (inValidStk.size() == 0) {
                        dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "");

                        for (int i = 0; i < validStk.size(); i++) {
                            RequestDao.insertStk(con, supervisorId, validStk.get(i), requestDate);
                            RequestDao.insertStkToOwnerWithRep(con, supervisorId, validStk.get(i));
                            RequestDao.updateStkStatus(con, validStk.get(i));
                        }
                    } else {
                        dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "There is Invalid STK Numbers ...");
                    }


                    for (int y = 0; y < Integer.parseInt(posQuantity); y++) {
                        posLastID = posLastID + 1;
//                            System.out.println("After Add : " + greatestCode);
                        greatestCode = (generatedCodeInt) + "";
//                            System.out.println("greatestCode isss "+greatestCode);
                        RequestDao.insertPosSupervisor(con, supervisorId, posLastID, requestDate);
                        RequestDao.insertPosDcm(con, posLastID, greatestCode + ".000", "3");
                        greatestCode = (generatedCodeInt++) + "";
//                            System.out.println("greatestCode after inc iss "+greatestCode);
                    }

                    RequestDao.insertRequestTrack(con, supervisorId, posQuantity, posFrom, posTo, strUserID, requestDate, stkQuantity);


                    if (Integer.parseInt(posQuantity) > 0) {
                        RequestDao.updateMaxPos(con, posTo + ".000", "1", "9");
                        posTo = posTo + ".000";
                        posFrom = posFrom + ".000";

                    }


                    dataHashMap.put(SCMInterfaceKey.REP_KIT_SUPERVISOR_NAME, RequestDao.getSupervisorName(con, supervisorId));
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_STK_QUANTITY_REQUIRED, stkQuantity);
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_POS_QUANTITY, posQuantity);
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_SUPERVISOR_ID, supervisorId);
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_POS_FROM, posFrom);
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_POS_TO, posTo);
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_STK_INVALID_LIST, inValidStk);
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_STK_VALID_LIST, validStk);
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_DATE, requestDate);


                    break;


                case action_store_after_print_request:

                    calendar = Calendar.getInstance();
                    dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    requestDate = dateFormat.format(calendar.getTime()).toString();
                    stkQuantity = (String) paramHashMap.get(SCMInterfaceKey.REP_KIT_STK_QUANTITY_REQUIRED);
                    posQuantity = (String) paramHashMap.get(SCMInterfaceKey.REP_KIT_POS_QUANTITY);
                    supervisorId = (String) paramHashMap.get(SCMInterfaceKey.REP_KIT_SUPERVISOR_ID);
                    posTo = (String) paramHashMap.get(SCMInterfaceKey.REP_KIT_POS_TO);
                    posFrom = (String) paramHashMap.get(SCMInterfaceKey.REP_KIT_POS_FROM);
                    validStk = (Vector) paramHashMap.get(SCMInterfaceKey.REP_KIT_STK_VALID_LIST);
                    posLastID = RequestDao.getPosCount(con);
                    greatestCode = RequestDao.getPosGreatestCode(con, "1", "3");
                    System.out.println("greatestCode : " + greatestCode);
                    greatestCode = greatestCode.replace(".001", "");

                    inValidStk = new Vector<String>();
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "");

                    for (int i = 0; i < validStk.size(); i++) {
                        RequestDao.insertStk(con, supervisorId, validStk.get(i), requestDate);
                        RequestDao.updateStkStatus(con, validStk.get(i));
                    }

                    for (int y = 0; y < Integer.parseInt(posQuantity); y++) {
                        posLastID = posLastID + 1;
                        greatestCode = (Integer.parseInt(greatestCode) + 1) + "";
                        RequestDao.insertPosSupervisor(con, supervisorId, posLastID, requestDate);

                        RequestDao.insertPosDcm(con, posLastID, greatestCode + ".001", "3");
                    }

                    RequestDao.insertRequestTrack(con, supervisorId, posQuantity, posFrom, posTo, strUserID, requestDate, stkQuantity);
                    RequestDao.updateMaxPos(con, posTo + ".001", "1", "3");
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_STK_QUANTITY_REQUIRED, stkQuantity);
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_POS_QUANTITY, posQuantity);
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_SUPERVISOR_ID, supervisorId);
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_POS_FROM, posFrom + ".001");
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_POS_TO, posTo + ".001");
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_STK_INVALID_LIST, inValidStk);
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_STK_VALID_LIST, validStk);
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_DATE, requestDate);

                    break;

                /*
                 * action_pos_data_entry used to get the data foe the pos data
                 * entry page like cities , regions , governrate , channel ....
                 * etc
                 */

                case action_pos_data_entry:
                    try {
                        loadDataBack3ak(con, dataHashMap, paramHashMap);

                        Vector regions = new Vector();
                        Vector IDTypeVector = new Vector();
                        Vector legalFormVec = new Vector();
                        Vector placeTypeVec = new Vector();

                        GenericModel gm = new GenericModel();
                        GenericModel placeTypeGM = new GenericModel();
                        GenericModel IDTypeModel = new GenericModel();

                        GenericModelDAO gmDAO = new GenericModelDAO();

                        IDTypeModel = gmDAO.getColumns(con, "DCM_ID_TYPE");
                        IDTypeVector = gmDAO.getModels(con, IDTypeModel);
                        gm = gmDAO.getColumns(con, "DCM_LEGAL_FORM");
                        legalFormVec = gmDAO.getModels(con, gm);
                        placeTypeGM = gmDAO.getColumns(con, "DCM_POS_PLACE_TYPE");
                        placeTypeVec = gmDAO.getModels(con, placeTypeGM);
                        regions = RequestDao.getAllRegionDataList(con);

                        dataHashMap.put(SCMInterfaceKey.VECTOR_ID_TYPE, IDTypeVector);
                        dataHashMap.put(SCMInterfaceKey.VECTOR_LEGAL_FORM, legalFormVec);
                        dataHashMap.put(SCMInterfaceKey.VECTOR_PLACE_TYPE, placeTypeVec);
                        dataHashMap.put(SCMInterfaceKey.VECTOR_REGIONS, regions);
                        dataHashMap.put(SCMInterfaceKey.DCM_SAVE_POS_TYPE, SCMInterfaceKey.DCM_SAVE_POS_TYPE_NEW);

                        String posCode = "\"\"";

                        dataHashMap.put(SCMInterfaceKey.CHANNEL_VECTOR, RequestDao.getChannelList(con));
                        dataHashMap.put(SCMInterfaceKey.LEVEL_VECTOR, RequestDao.getLevelList(con));
                        dataHashMap.put(SCMInterfaceKey.RATE_VECTOR, RequestDao.getPlaceList(con));
                        dataHashMap.put(SCMInterfaceKey.DOC_VECTOR, RequestDao.getDocList(con));
                        dataHashMap.put(SCMInterfaceKey.PAYMENT_LEVEL_VECTOR, RequestDao.getPaymentList(con));
                        dataHashMap.put(SCMInterfaceKey.IQRAR_TYPE_VECTOR, RequestDao.getIqrarTypeList(con));
                        dataHashMap.put(SCMInterfaceKey.POS_CODE, posCode);

                        Utility.logger.debug("USERID:  " + strUserID);

                        String strFlagSuperAdmin = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG, strFlagSuperAdmin);

                        dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "");
                        dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL, "");
                        dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL, "");
                        dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL, "");
                        dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD, "");

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;


                case action_pos_data_entry_store: {
                    try {
                        updateDocumentParamters(paramHashMap);
                        
                        String strDocLoc = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_DOC_LOC);
                        String posName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_NAME);
                        String posCode = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CODE);
                        String posArabicName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_ARABIC_POS_NAME);
                        String posArabicAddress = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_ARABIC_POS_ADDRESS);

                        
                        
                        
                        
                        
                        String channelId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL);
                        String levelId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL);
                        String paymentLevel = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL);
                        
                        String paymentMethod = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD);
                        
                        paymentLevel = paymentLevel == null || paymentLevel.compareTo("") == 0 ? (String) paramHashMap.get(SCMInterfaceKey.PAYMENT_FOR_POS) : paymentLevel;

                        // String branchOf           = (String)paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_BRANCH);

                        String demoLine = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_DEMO);

                        String proposedDocId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC);
                        String proposedDocNum = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM);

                        String rateId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_RATE);
                        //String rateDate           = (String)paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_RATE_DATE);

                        String posEmail = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_EMAIL);
                        String posAddress = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_ADDRESS);


                        int posRegion = Integer.parseInt((String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_REGION));
                        String governrateId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_GOVER);
                        String areaId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_AREA);
                        String cityId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CITY);
                        String districtId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT);

                        String posOwnerName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME);
                        String posOwnerBirthDate = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_BIRTH_DATE);
                        int posOwnerIDType = Integer.parseInt((String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE));
                        String posOwnerIDNumber = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER);

                        String posManagerName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME);
                        String posManagerBirthDate = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_BIRTH_DATE);
                        String posManagerIDTypeString = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE);

                        int posManagerIDType = 0;

                        if (!posManagerIDTypeString.equals("") && !posManagerIDTypeString.equals(null)) {
                            Integer.parseInt(posManagerIDTypeString);
                        }

                        String posManagerIDNumber = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER);

                        String stkDialNum = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_STK_DIAL);
                        String surveyID = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_STK_VERIFICATION);

                        String stkDeliveryDate = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_STK_DELIVERY_DATE);
                        String iqrarReceiveDate = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_STK_IQRAR_RECEIVED_DAYE);

                        String iqrarDeliveryDate = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_STK_IQRAR_DELIVERY_DATE);
                        String stkAssignDate = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_STK_ASSIGN_DATE);

                        String stkActiveDate = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_STK_ACTIVE_DATE);

                        String hiddenChildCode = (String) paramHashMap.get(SCMInterfaceKey.HIDDEN_IS_GENERATE_CHILD_CODE);

                        String iqrarDate = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_IQRAR_DATE);



                        int UserID = Integer.parseInt(strUserID);
                        int branchIndicator = 0;
                        String branchValue = "";
                        if (posCode.endsWith(".001")) {
                            branchIndicator = 1;
                            branchValue = posCode.substring(0, posCode.length() - 4);
                            System.out.println("###################### branch : " + branchValue);

                        }


                        String strFlagSuperAdmin = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG, strFlagSuperAdmin);

                        String posPhone = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_PHONE);
                        String posManagerPhone = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_PHONE);
                        String posOwnerPhone = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_PHONE);

                        Vector POSPhones = new Vector();

                        POSPhones.add(posPhone);



                        Vector ownerPhones = new Vector();

                        ownerPhones.add(posOwnerPhone);

                        Vector managerPhones = new Vector();

                        managerPhones.add(posManagerPhone);

                        PosModel posModel = new PosModel();
                        POSDetailModel mdl = new POSDetailModel();
                        
                        /////////////////////////lamya/////////////////////////////////////////////
                      UserDataModel userData = new UserDataModel();
                        UserDataModel teamleaderData = new UserDataModel();
                        UserDataModel salesrepData = new UserDataModel();
                        
                    userData = new UserDataModel();
                    teamleaderData = new UserDataModel();
                    salesrepData = new UserDataModel();


                    

                    String supervisorName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_NAME);
                    String supervisorMobile = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_MOBILE);        
                    String supervisorEmail = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_EMAIL);
                    String supervisorAddress = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_ADDRESS);
                    
                    userData = RequestDao.insertUserDetail(con, supervisorName,supervisorMobile,supervisorEmail,supervisorAddress, "4", districtId, null);//managerId
                    String teamleaderName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_NAME);
                    String teamleaderMobile = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_MOBILE);        
                    String teamleaderEmail = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_EMAIL);
                    String teamleaderAddress = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_ADDRESS);
                    teamleaderData = RequestDao.insertUserDetail(con, teamleaderName,teamleaderMobile,teamleaderEmail,teamleaderAddress, "5", districtId, null);//managerId
                    
                    String salesrepName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_SALESREP_NAME);
                    String salesrepMobile = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_SALESREP_MOBILE);        
                    String salesrepEmail = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_SALESREP_EMAIL);
                    String salesrepAddress = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_SALESREP_ADDRESS);
                    
                    salesrepData = RequestDao.insertUserDetail(con, salesrepName,salesrepMobile,salesrepEmail,salesrepAddress, "6", districtId, null); //managerId   


                    mdl.setSupervisorName(userData.getDcmUserId());
                    mdl.setTeamleaderName(teamleaderData.getDcmUserId());
                    mdl.setSalesrepName(salesrepData.getDcmUserId());


                      /////////////////////////lamya - end/////////////////////////////////////////////
                        
                        setPOSDataPOSDetailFromParams(posModel, mdl, paramHashMap);
                        posModel.setPaymentMethod(paymentMethod);
                        posModel.setPaymentLevel(paymentLevel);
                        
                        if (areaId != null && !areaId.equals("empty") && !areaId.equals("") && !areaId.equals("--")) 
                            posModel.setAreaId(Integer.parseInt(areaId));
                        else
                            posModel.setAreaId(0);
                        
                        posModel.setBranchOf(branchValue);
                        if (channelId != null && !channelId.equals("empty") && !channelId.equals("") && !channelId.equals("--"))
                            posModel.setChannelId(Integer.parseInt(channelId));
                        else posModel.setChannelId(0);
                        posModel.setCityId(Integer.parseInt(cityId));
                        posModel.setDemoLineNum(demoLine);
                        if (districtId != null && !districtId.equals("empty") && !districtId.equals("") && !districtId.equals("--")) 
                            posModel.setDistrictId(Integer.parseInt(districtId));
                        else
                            posModel.setDistrictId(0);
                        if (proposedDocNum != null) {
                            posModel.setDocNumber(proposedDocNum);
                        }
                        posModel.setGovernateId(Integer.parseInt(governrateId));
                        posModel.setIqrarDeliveryDate(iqrarDeliveryDate);
                        posModel.setIqrarReceiveDate(iqrarReceiveDate);
                        if (levelId != null && !levelId.equals("empty") && !levelId.equals("") && !levelId.equals("--"))
                            posModel.setLevelId(Integer.parseInt(levelId));
                        else
                            posModel.setLevelId(0);
                        if (paymentLevel != null && !paymentLevel.equals("empty") && !paymentLevel.equals("") && !paymentLevel.equals("--"))
                            posModel.setPaymentLevelId(Integer.parseInt(paymentLevel));
                        else
                            posModel.setPaymentLevelId(0);

                        if (!proposedDocId.equals("") && !proposedDocId.equals(null) && !proposedDocId.equals("empty") && !proposedDocId.equals("--")) 
                            posModel.setProposedDocId(Integer.parseInt(proposedDocId));
                        else
                            posModel.setProposedDocId(0);
                        // posModel.setRateDate(rateDate);
                        
                        if (rateId != null && !rateId.equals("empty") && !rateId.equals("") && !rateId.equals("--"))
                            posModel.setRateID(Integer.parseInt(rateId));
                        else
                            posModel.setRateID(0);
                        
                        posModel.setStkActiveDate(stkActiveDate);
                        posModel.setStkAssignDate(stkAssignDate);
                        posModel.setStkDeliveryDate(stkDeliveryDate);
                        posModel.setStkDialNumber(stkDialNum);
                        posModel.setStkVerify(iqrarDate);
                        posModel.setDocLocation(strDocLoc);

                        
                        
                        mdl.setPaymentMethod(paymentMethod);
                        mdl.setPaymentLevel(paymentLevel);
                        mdl.setPosName(posName);
                        mdl.setPosArabicName(posArabicName);
                        mdl.setPosArabicAddress(posArabicAddress);
                        int exist = 1;
                        int flag = 0;
                        while (exist != 0) {
                            exist = RequestDao.getAnyPosIdByCode(con, posCode);

                            if (exist != 0) {
                                String generatedCode = RequestDao.getPosGreatestCode(con, channelId, paymentLevel);

                                posCode = generatedCode;
                                flag = 1;
                            }
                        }

                        mdl.setPOSCode(posCode);
                        mdl.setPosAddress(posAddress);
                        mdl.setPosEmail(posEmail);
                        mdl.setUserID(UserID);
                        mdl.setPosRegionID(posRegion);
                        mdl.setPosPhones(POSPhones);
                        mdl.setPosManagerPhones(managerPhones);
                        mdl.setPosOwnerPhones(ownerPhones);
                        mdl.setPosOwnerName(posOwnerName);
                        mdl.setPosOwnerBirthDate(posOwnerBirthDate);
                        mdl.setPosOwnerIDTypeID(posOwnerIDType);
                        mdl.setPosOwnerIDNumber(posOwnerIDNumber);
                        mdl.setPosManagerName(posManagerName);
                        mdl.setPosManagerBirthDate(posManagerBirthDate);
                        mdl.setPosManagerIDTypeID(posManagerIDType);
                        mdl.setPosManagerIDNumber(posManagerIDNumber);
                        mdl.setSurveyID(surveyID);
                        
                        posModel.setPosDetailModel(mdl);


                        int posId = RequestDao.getPosCount(con) + 1;
                        boolean stkFlag = false;
                        int stkId = RequestDao.getStkIdByStkCodeStatusId(con, stkDialNum, "1"); // stk status is new

                        if (stkDialNum == null || stkDialNum.compareTo("") == 0) {
                            stkFlag = true;
                        }

                        // if(posId == 0) // pos code not valid or its data entered b4
                        //  {
                        //    dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "Sorry , Code Invalid or Can't be updated its Data .. ");
                        //  }
                        // else if(branchIndicator == 0)
                        // {
                        //  dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "Sorry , POS Code Invalid .. Must End With .000 .. ");
                        // }
                        if (stkFlag == false && stkId == 0) // stk data entered but invalid or entered b4
                        {
                            dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "Sorry , STK Dial Invalid or Can't be updated its Data .. ");
                        } else if (RequestDao.checkStkInOwner(con, stkId + "") == 1) {
                            dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "Sorry , STK Dial Assigned Before .. ");
                        } else // pos data entered and valid
                        {
                            //RequestDao.insertPosSupervisor(con, supervisorId, posLastID, requestDate);
                            RequestDao.insertPosDcm(con, posId, posCode, "3");
                            if (hiddenChildCode == null || hiddenChildCode.compareTo("true") != 0) {
                                //Ahmed Adel 30\1\2012
                            }
                            RequestDao.updateGenDcm(con, posModel, "1", posId, strUserID);
                            Long pos_detail_id = RequestDao.insertPosDetail(con, posModel, posId, "1");


                            if (stkFlag == false && RequestDao.getStkIdByStkCodeStatusId(con, stkDialNum, "1") != 0) // stk entered and valid
                            {

                                RequestDao.insertStkDataToOwner(con, iqrarDate, stkDialNum, posId + "", strUserID);
                            }
                            
                            RequestDao.insertPaymentStatus(con, "2", posId + "");

                            if (flag == 0) {
                                dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "POS Data Entered Successfully ..");
                            } else {
                                dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "POS Data Entered Successfully With New Code " + posCode);
                            }
                        }

                        /**
                         * ********************************************** Data
                         * Returned to the Page
                         * ***********************************************************
                         */
                        Vector regions = new Vector();
                        Vector IDTypeVector = new Vector();
                        Vector legalFormVec = new Vector();
                        Vector placeTypeVec = new Vector();

                        GenericModel gm = new GenericModel();
                        GenericModel placeTypeGM = new GenericModel();
                        GenericModel IDTypeModel = new GenericModel();

                        GenericModelDAO gmDAO = new GenericModelDAO();

                        IDTypeModel = gmDAO.getColumns(con, "DCM_ID_TYPE");
                        IDTypeVector = gmDAO.getModels(con, IDTypeModel);
                        gm = gmDAO.getColumns(con, "DCM_LEGAL_FORM");
                        legalFormVec = gmDAO.getModels(con, gm);
                        placeTypeGM = gmDAO.getColumns(con, "DCM_POS_PLACE_TYPE");
                        placeTypeVec = gmDAO.getModels(con, placeTypeGM);
                        regions = RequestDao.getAllRegionDataList(con);

                        dataHashMap.put(SCMInterfaceKey.VECTOR_ID_TYPE, IDTypeVector);
                        dataHashMap.put(SCMInterfaceKey.VECTOR_LEGAL_FORM, legalFormVec);
                        dataHashMap.put(SCMInterfaceKey.VECTOR_PLACE_TYPE, placeTypeVec);
                        dataHashMap.put(SCMInterfaceKey.VECTOR_REGIONS, regions);
                        dataHashMap.put(SCMInterfaceKey.DCM_SAVE_POS_TYPE, SCMInterfaceKey.DCM_SAVE_POS_TYPE_NEW);



                        dataHashMap.put(SCMInterfaceKey.CHANNEL_VECTOR, RequestDao.getChannelList(con));
                        dataHashMap.put(SCMInterfaceKey.LEVEL_VECTOR, RequestDao.getLevelList(con));
                        dataHashMap.put(SCMInterfaceKey.RATE_VECTOR, RequestDao.getPlaceList(con));
                        dataHashMap.put(SCMInterfaceKey.DOC_VECTOR, RequestDao.getDocList(con));

                        Utility.logger.debug("USERID:  " + strUserID);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG, strFlagSuperAdmin);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;


                case action_pos_data_similar_name: {

                    String posName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_NAME);
                    dataHashMap.put(SCMInterfaceKey.SIMILAR_POS_LIST, RequestDao.getSimilarName(con, posName));
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_TABLE_TITLE, "Similar POS Name");
                }
                break;


                case action_pos_data_similar_city:

                    String posCity = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CITY);
                    dataHashMap.put(SCMInterfaceKey.SIMILAR_POS_LIST, RequestDao.getSimilarCity(con, posCity));
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_TABLE_TITLE, "Similar POS City");
                    break;


                case action_pos_data_similar_area:

                    String posArea = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_AREA);
                    dataHashMap.put(SCMInterfaceKey.SIMILAR_POS_LIST, RequestDao.getSimilarArea(con, posArea));
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_TABLE_TITLE, "Similar POS Area");
                    break;


                case action_show_iqrar_print_data_entry:
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "");
                    break;

                case action_one_pos_iqrar_print: {

                    String posCode = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CODE);
                    String filePath = (String) paramHashMap.get(SCMInterfaceKey.FILE_PATH);
                    String imagePath = (String) paramHashMap.get(SCMInterfaceKey.PDF_IMAGE_PATH);

                    int posId = RequestDao.checkPosIsFound(con, posCode);
                    if (posId == 0) {
                        dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "Sorry , Error in POS Code ...");
                        dataHashMap.put(SCMInterfaceKey.FILE_PATH, "");

                    } else if (RequestDao.checkPosHasStk(con, posId) == 0) {
                        dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "Sorry , No STK Assigned to POS Code ...");
                        dataHashMap.put(SCMInterfaceKey.FILE_PATH, "");

                    } /*
                     * else if(RequestDao.checkPosOwnerStatus(con , posId)==2) {
                     * dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "Sorry ,
                     * POS Iqrar Received Before ...");
                     * dataHashMap.put(SCMInterfaceKey.FILE_PATH, "");
                     *
                     * }
                     */ else {
                        dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "2");
                        PosIqrarModel posModel = RequestDao.getPosIqrarData(con, posCode);


                        String issuingUser = RequestDao.getUserName(strUserID);
                        String userSupervisor = RequestDao.getSupervisorName(con, strUserID);

                        String filePathDownload = PDFIqrarPrinting.createPDFIqrar(filePath, imagePath, posModel, issuingUser, userSupervisor);
                        RequestDao.updateStkIqrarReceiving(con, posId + "");
                        dataHashMap.put(SCMInterfaceKey.FILE_PATH, filePathDownload);


                    }
                }
                break;

                case actioin_one_pos_iqrar_download:
                    dataHashMap.put(SCMInterfaceKey.POS_DETAIL_MODEL, (PosIqrarModel) paramHashMap.get(SCMInterfaceKey.ACTION_POS_DATA_ENTRY));
                    break;


                case action_many_pos_iqrar_view:
                    break;

                case action_many_pos_iqrar_save:
                    RequestDao.deletePosPdfTable(con);
                    break;

                case ACTION_SHOW_GENETATE_CALIDUS_FILE_SCREEN:{
                Vector tableDefVector= DataImportTableDefDAO.getTableDefByCategory("43");
                dataHashMap.put(  AdministrationInterfaceKey.TABLE_DEF_VECTOR  , tableDefVector); 
                }
                break;
                    
                    
                case view_pos_data_management: {

                    Vector regions = new Vector();
                    Vector IDTypeVector = new Vector();
                    GenericModel IDTypeModel = new GenericModel();
                    GenericModelDAO gmDAO = new GenericModelDAO();
                    IDTypeModel = gmDAO.getColumns(con, "DCM_ID_TYPE");
                    IDTypeVector = gmDAO.getModels(con, IDTypeModel);
                    regions = RequestDao.getAllRegionDataList(con);
                    
                    
                    Vector<com.mobinil.sds.core.system.scm.model.SupervisorModel> allSupervisors = RepManagementDAO.getSupervisors(con);
                    Vector<com.mobinil.sds.core.system.scm.model.TeamleaderModel> allTeamleaders = RepManagementDAO.getTeamleaders(con);
                    Vector<com.mobinil.sds.core.system.scm.model.RepModel> allReps = RepManagementDAO.getReps(con);
                    
                    dataHashMap.put("AllSupervisors", allSupervisors);
                    dataHashMap.put("AllTeamleaders", allTeamleaders);
                    dataHashMap.put("AllReps", allReps);
                    
                    dataHashMap.put(SCMInterfaceKey.CHANNEL_VECTOR, RequestDao.getChannelList(con));
                    dataHashMap.put(SCMInterfaceKey.LEVEL_VECTOR, RequestDao.getLevelList(con));
                    dataHashMap.put(SCMInterfaceKey.PAYMENT_LEVEL_VECTOR, RequestDao.getPaymentList(con));
                    dataHashMap.put(SCMInterfaceKey.POS_STATUS_VECTOR, RequestDao.getStatusList(con));
                    dataHashMap.put(SCMInterfaceKey.VECTOR_ID_TYPE, IDTypeVector);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_REGIONS, regions);
                    dataHashMap.put(SCMInterfaceKey.DOC_VECTOR, RequestDao.getDocList(con));
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "");
                    dataHashMap.put(SCMInterfaceKey.VECTOR_USER_LEVEL_TYPES, RequestDao.getUserLevelTypes(con));    
                    // Vector dataVec = RequestDao.getAllPosDataSearch(con);
                    Vector dataVec = new Vector();

                    /**
                     * ********************** Search Data Entry Fileds
                     * *****************************
                     */
                    dataHashMap.put(SCMInterfaceKey.SIMILAR_POS_LIST, dataVec);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_NAME, "");
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_CODE, "");
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_REGION, "");
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_GOVER, "");
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT, "");
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_AREA, "");
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_CITY, "");
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME, "");
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER, "");
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE, "");
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME, "");
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER, "");
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE, "");
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC, "");
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM, "");
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_STK_DIAL, "");
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL, "");
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL, "");
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL, "");
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD, "");
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_STATUS, "");
                    loadListsPosManagement(con, dataHashMap, paramHashMap, false);
                    System.out.println("allSupers "+allSupervisors);
                }
                break;

                case search_pos_data_management: {
/*NOT SURE*/
                    
                    
                    
                    String userDataId = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_USER_ID);
                    userDataName = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_SUPERVISOR_NAME);
                    /*String teamleaderId = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_TEAMLEADER_ID);
                    String teamleaderName = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_TEAMLEADER_NAME);
                    String salesrepId = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_SALESREP_ID);
                    String salesrepName = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_SALESREP_NAME);
                    */
                    
                    
                    Vector<com.mobinil.sds.core.system.scm.model.SupervisorModel> allSupervisors = RepManagementDAO.getSupervisors(con);
                    Vector<com.mobinil.sds.core.system.scm.model.TeamleaderModel> allTeamleaders = RepManagementDAO.getTeamleaders(con);
                    Vector<com.mobinil.sds.core.system.scm.model.RepModel> allReps = RepManagementDAO.getReps(con);
                    
                    dataHashMap.put("AllSupervisors", allSupervisors);
                    dataHashMap.put("AllTeamleaders", allTeamleaders);
                    dataHashMap.put("AllReps", allReps);
                    
                    System.out.println("SUPERVISOR in search "+userDataName);
                    String destinationPage = (String) paramHashMap.get(SCMInterfaceKey.DESTINATION_PAGE);
                    if (destinationPage == null) {
                        destinationPage = "0";
                    }
                    System.out.println("destinationnnn "+destinationPage);
                    Vector regions = new Vector();
                    Vector IDTypeVector = new Vector();
                    GenericModel IDTypeModel = new GenericModel();
                    GenericModelDAO gmDAO = new GenericModelDAO();
                    IDTypeModel = gmDAO.getColumns(con, "DCM_ID_TYPE");
                    IDTypeVector = gmDAO.getModels(con, IDTypeModel);
                    regions = RequestDao.getAllRegionDataList(con);

                    dataHashMap.put(SCMInterfaceKey.VECTOR_ID_TYPE, IDTypeVector);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_REGIONS, regions);
                    dataHashMap.put(SCMInterfaceKey.DOC_VECTOR, RequestDao.getDocList(con));


                    String teamleadId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER);
                    String superId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_SUPERVISOR);
                    String repId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP);
                    System.out.println("supervisor: "+superId+" teamleader: "+teamleadId+" rep: "+repId);
                    System.out.println("superId.trim() "+superId.trim());
                    String posDataName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_NAME);
                    String posDataCode = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CODE);
                    String posDataRegion = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_REGION);
                    String posDataGover = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_GOVER);
                    String posDataDistrict = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT);
                    String posDataArea = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_AREA);
                    String posDataCity = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CITY);
                    String posDataOwnerName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME);
                    String posDataOwnerIdNum = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER);
                    String posDataOwnerIdType = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE);
                    String posDataManagerName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME);
                    String posDataManagerIdNum = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER);
                    String posDataManagerIdType = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE);
                    String posDataProposedDoc = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC);
                    String posDataDocNum = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM);
                    String posDataStkNum = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_STK_DIAL);
                    String Level = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL);
                    String Payment = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL);
                    String PaymentM = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD);
                    String Channel = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL);
                    String entryDate = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_INPUT_ENTRY_DATE);
                    String englishAddress = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_INPUT_ENGLISH_ADDRESS);
                    String posPhone = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_INPUT_POS_PHONE);
                    String docLocation = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_INPUT_DOC_LOCATION);
                    String posStatusId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_SELECT_POS_STATUS);
                    String stkStatusId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_SELECT_STK_STATUS);
                    String psymentStatusId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_SELECT_PAYMENT_STATUS);
                    String posDataImgDist = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_IMAGE_DISTRICT);
                    if (posDataRegion==null || posDataRegion.compareTo("--")==0)
                        posDataRegion="";
                    if (posDataGover==null || posDataGover.compareTo("--")==0)
                        posDataGover="";
                    if(posDataDistrict==null || posDataDistrict.compareTo("--")==0)
                        posDataDistrict="";        
                    if(posDataArea==null || posDataArea.compareTo("--")==0)
                        posDataArea="";
                    if(posDataCity==null || posDataCity.compareTo("--")==0)
                       posDataCity=""; 
                    if(posDataImgDist==null || posDataCity.compareTo("--")==0)
                       posDataImgDist=""; 

//                    Integer totalSearch=RequestDao.searchPosDataTotal(con ,posDataOwnerIdType.trim() , posDataDocNum.trim() , posDataManagerName.trim() , posDataStkNum.trim() , posDataManagerIdType.trim() , posDataProposedDoc.trim() , posDataManagerIdNum.trim() , posDataName.trim() , posDataCode.trim() , posDataRegion.trim() , posDataGover.trim() , posDataDistrict.trim() , posDataArea.trim() ,posDataCity.trim() , posDataOwnerName.trim() ,posDataOwnerIdNum.trim(),Level,Payment,Channel);
                    System.out.println("before search pos data");
                    Vector<POSDetailModel> dataVec = RequestDao.searchPosData(con, 
                            posDataOwnerIdType.trim(), 
                            posDataDocNum.trim(), 
                            posDataManagerName.trim(), 
                            posDataStkNum.trim(), 
                            posDataManagerIdType.trim(), 
                            posDataProposedDoc.trim(), 
                            posDataManagerIdNum.trim(), 
                            posDataName.trim(), 
                            posDataCode.trim(), 
                            posDataRegion.trim(), 
                            posDataGover.trim(), 
                            posDataDistrict.trim(), 
                            posDataArea.trim(), 
                            posDataCity.trim(), 
                            posDataOwnerName.trim(), 
                            posDataOwnerIdNum.trim(),
                            superId.trim(),
                            teamleadId.trim(),
                            repId.trim(),
                            destinationPage, 
                            Level, 
                            Payment, 
                            Channel,
                            posStatusId, stkStatusId, psymentStatusId, posPhone, englishAddress, entryDate, docLocation,posDataImgDist.trim());
                   
                    System.out.println(" data vec size = "+ dataVec.size());
                    
                    Integer totalSearch = ((POSDetailModel) dataVec.lastElement()).getPageCount();
                    
                    
                    dataVec.removeElementAt(dataVec.size() - 1);
                    totalSearch = totalSearch / 20;
                    totalSearch = totalSearch == 0 ? 1 : totalSearch;

                    if (dataVec.size() == 0 || dataVec == null) {
                        dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "No Data Found ...");
                    } else {
                        dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "");
                    }
                    
                    dataHashMap.put("isNextSearch", "true");
                    
                    dataHashMap.put(SCMInterfaceKey.SIMILAR_POS_LIST, dataVec);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_NAME, posDataName);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_CODE, posDataCode);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_REGION, posDataRegion);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_GOVER, posDataGover);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT, posDataDistrict);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_AREA, posDataArea);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_CITY, posDataCity);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME, posDataOwnerName);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER, posDataOwnerIdNum);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE, posDataOwnerIdType);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME, posDataManagerName);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER, posDataManagerIdNum);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE, posDataManagerIdType);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC, posDataProposedDoc);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM, posDataDocNum);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_STK_DIAL, posDataStkNum);
                    dataHashMap.put(SCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER, destinationPage);
                    dataHashMap.put(SCMInterfaceKey.STRING_OF_TOTAL_PAGE_NUMBER, totalSearch.toString());
                    dataHashMap.put(SCMInterfaceKey.CHANNEL_VECTOR, RequestDao.getChannelList(con));
                    dataHashMap.put(SCMInterfaceKey.LEVEL_VECTOR, RequestDao.getLevelList(con));
                    dataHashMap.put(SCMInterfaceKey.PAYMENT_LEVEL_VECTOR, RequestDao.getPaymentList(con));
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL, Level);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL, Payment);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD, PaymentM);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL, Channel);
                    loadListsPosManagement(con, dataHashMap, paramHashMap, true);
                }
                break;



                case show_detail_pos_data_management:
                    String posDetailId = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_POS_ID);
                    String supervisorDetailId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_SUPERVISOR);
                    String teamleaderDetailId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER);
                    String salesrepDetailId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP);
                   // String userDataName = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_USER_ID);
                    System.out.println("Action in code is show_detail_pos_data_management, and posDetailId is : "+posDetailId+" and supervisor detail id is : "+supervisorDetailId+" and teamleader id : "+teamleaderDetailId+" and salesrep id : "+salesrepDetailId);
                    
                    PosModel posGeneralData = new PosModel();
                    POSDetailModel posDetailModel = new POSDetailModel();
                    UserDataModel userData = new UserDataModel();
                    UserDataModel teamleaderData = new UserDataModel();
                    UserDataModel salesrepData = new UserDataModel();
                    //ADD KEY FOR GETTING DATA FOR TEAMLEADER AND ANOTHER FOR SALESREP...DO THE SAME IN JSP KEYS
                    String DCM_CODE = RequestDao.getDCMCodeForPOS(posDetailId);
                    String mobiCashStr = RequestDao.getMobicashNum(DCM_CODE);
                    String posStatus = RequestDao.getDCMStatus(DCM_CODE);
                    long posMobicashNum = (mobiCashStr==null || mobiCashStr.compareTo("")==0) ? 0 : Long.parseLong(mobiCashStr);
                    Vector POSPhones = new Vector();
                    Vector OwnerPhones = new Vector();
                    Vector ManagerPhones = new Vector();
                    String payMethod = "";
                    String payLevel = "";
                    posGeneralData = setPOSDataPOSDetail(/*posGeneralData*//*, posDetailModel*/ posDetailId, con);
                    
                    
                    posGeneralData.setMobicashNum(posMobicashNum);
                    posGeneralData.setStatusName(posStatus);
                    posDetailModel.setMobicashNum(posMobicashNum);
                    

                    posDetailModel = RequestDao.getPOSByID(con, posDetailId);
                    
                    userData = RequestDao.getUserDataByDetailId(con, posDetailModel.getSupervisorName());
                    teamleaderData = RequestDao.getUserDataByDetailId(con, posDetailModel.getTeamleaderName());
                    salesrepData = RequestDao.getUserDataByDetailId(con, posDetailModel.getSalesrepName());
                    
                    if (supervisorDetailId!=null && supervisorDetailId.compareTo("")!=0 && supervisorDetailId.compareTo("---")!=0)
                        userData = RequestDao.getUserDataByDetailId(con, supervisorDetailId);
                    if (teamleaderDetailId!=null && teamleaderDetailId.compareTo("")!=0 && teamleaderDetailId.compareTo("---")!=0)
                        teamleaderData = RequestDao.getUserDataByDetailId(con, teamleaderDetailId);
                    if (salesrepDetailId!=null && salesrepDetailId.compareTo("")!=0 && salesrepDetailId.compareTo("---")!=0)
                        salesrepData = RequestDao.getUserDataByDetailId(con, salesrepDetailId);
                    
                    
                    
                    System.out.println("show details : posssssssss code : "+posDetailModel.getPOSCode());
                    payLevel= RequestDao.getPOSPaymentLevel(con, posDetailModel.getPOSCode());
                    payMethod= RequestDao.getPOSPaymentMethod(con, posDetailModel.getPOSCode());
                    
                    posDetailModel.setPaymentMethod(payMethod);
                    posDetailModel.setPaymentLevel(payLevel);
                    
                    
                    POSPhones = RequestDao.getPOSPhones(con, posDetailId);
                    if (POSPhones != null && POSPhones.size() > 0) {
                        posDetailModel.setPosPhones(POSPhones);
                    }

                    OwnerPhones = RequestDao.getOwnerPhones(con, posDetailId);
                    ManagerPhones = RequestDao.getManagerPhones(con, posDetailId);

                    if (ManagerPhones.size() > 0) {
                        posDetailModel.setPosManagerPhones(ManagerPhones);
                    }
                    if (OwnerPhones.size() > 0) {
                        posDetailModel.setPosOwnerPhones(OwnerPhones);
                    }
                    
                    posGeneralData.setPaymentLevel(payLevel);
                    posGeneralData.setPaymentMethod(payMethod);
                    
                    //System.out.println("level : "+posGeneralData.getPaymentLevel(payLevel));
                    
                    posGeneralData.setPosDetailModel(posDetailModel);
                    posGeneralData = RequestDao.getPosDetailData(con, posGeneralData, posDetailId);
                    dataHashMap.put(SCMInterfaceKey.SIMILAR_POS_LIST, posGeneralData);
                    dataHashMap.put(SCMInterfaceKey.SIMILAR_USER_LIST, userData);
                    dataHashMap.put(SCMInterfaceKey.SIMILAR_TEAMLEADER_LIST, teamleaderData);
                    dataHashMap.put(SCMInterfaceKey.SIMILAR_SALESREP_LIST, salesrepData);
                    break;

                case action_pos_data_entry_with_stk: {

                    try {
                        loadDataBack3ak(con, dataHashMap, paramHashMap);
                        Vector regions = new Vector();
                        Vector IDTypeVector = new Vector();
                        Vector legalFormVec = new Vector();
                        Vector placeTypeVec = new Vector();

                        GenericModel gm = new GenericModel();
                        GenericModel placeTypeGM = new GenericModel();
                        GenericModel IDTypeModel = new GenericModel();

                        GenericModelDAO gmDAO = new GenericModelDAO();

                        IDTypeModel = gmDAO.getColumns(con, "DCM_ID_TYPE");
                        IDTypeVector = gmDAO.getModels(con, IDTypeModel);
                        gm = gmDAO.getColumns(con, "DCM_LEGAL_FORM");
                        legalFormVec = gmDAO.getModels(con, gm);
                        placeTypeGM = gmDAO.getColumns(con, "DCM_POS_PLACE_TYPE");
                        placeTypeVec = gmDAO.getModels(con, placeTypeGM);
                        regions = RequestDao.getAllRegionDataList(con);

                        dataHashMap.put(SCMInterfaceKey.VECTOR_ID_TYPE, IDTypeVector);
                        dataHashMap.put(SCMInterfaceKey.VECTOR_LEGAL_FORM, legalFormVec);
                        dataHashMap.put(SCMInterfaceKey.VECTOR_PLACE_TYPE, placeTypeVec);
                        dataHashMap.put(SCMInterfaceKey.VECTOR_REGIONS, regions);
                        dataHashMap.put(SCMInterfaceKey.DCM_SAVE_POS_TYPE, SCMInterfaceKey.DCM_SAVE_POS_TYPE_NEW);
                        dataHashMap.put(SCMInterfaceKey.PAYMENT_LEVEL_VECTOR, RequestDao.getPaymentList(con));
                        dataHashMap.put(SCMInterfaceKey.IQRAR_TYPE_VECTOR, RequestDao.getIqrarTypeList(con));
                        dataHashMap.put(SCMInterfaceKey.PAYMENT_METHOD_VECTOR, RequestDao.getPaymentMethodList(con));
                        System.out.println("the payment vector : "+dataHashMap.get(SCMInterfaceKey.PAYMENT_METHOD_VECTOR));
                        dataHashMap.put(SCMInterfaceKey.CHANNEL_VECTOR, RequestDao.getChannelList(con));
                        dataHashMap.put(SCMInterfaceKey.LEVEL_VECTOR, RequestDao.getLevelList(con));
                        dataHashMap.put(SCMInterfaceKey.RATE_VECTOR, RequestDao.getPlaceList(con));
                        dataHashMap.put(SCMInterfaceKey.DOC_VECTOR, RequestDao.getDocList(con));
                        dataHashMap.put(SCMInterfaceKey.PAYMENT_LEVEL_VECTOR, RequestDao.getPaymentList(con));
                        dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD, "");
                        
                        Utility.logger.debug("USERID:  " + strUserID);

                        String strFlagSuperAdmin = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG, strFlagSuperAdmin);

                        dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "");

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }

                }
                break;


                case action_save_pos_data_entry_with_stk:


                    try {
                        String strDocLoc = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_DOC_LOC);
                        String posName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_NAME);
                        String posCode = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CODE);
                        

                        String channelId = "1";
                        String levelId = "3";
                        String paymentLevel = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL);
                        String paymentMethod = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD);
                        String posArabicName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_ARABIC_POS_NAME);
                        String posArabicAddress = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_ARABIC_POS_ADDRESS);

                        String demoLine = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_DEMO);

                        String proposedDocId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC);
                        String proposedDocNum = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM);

                        String rateId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_RATE);
                        //String rateDate           = (String)paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_RATE_DATE);

                        String posEmail = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_EMAIL);
                        String posAddress = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_ADDRESS);

                        int posRegion = Integer.parseInt((String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_REGION));
                        String governrateId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_GOVER);
                        String areaId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_AREA);
                        String cityId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CITY);
                        String districtId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT);

                        String posOwnerName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME);
                        String posOwnerBirthDate = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_BIRTH_DATE);
                        int posOwnerIDType = Integer.parseInt((String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE));
                        String posOwnerIDNumber = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER);

                        String posManagerName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME);
                        String posManagerBirthDate = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_BIRTH_DATE);
                        String posManagerIDTypeString = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE);


                        int posManagerIDType = 0;

                        if (!posManagerIDTypeString.equals("") && !posManagerIDTypeString.equals(null)) {
                            Integer.parseInt(posManagerIDTypeString);
                        }

                        String posManagerIDNumber = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER);

                        String stkDialNum = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_STK_DIAL);
                        String surveyID = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_STK_VERIFICATION);

                        String stkDeliveryDate = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_STK_DELIVERY_DATE);
                        String iqrarReceiveDate = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_STK_IQRAR_RECEIVED_DAYE);

                        String iqrarDeliveryDate = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_STK_IQRAR_DELIVERY_DATE);
                        String stkAssignDate = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_STK_ASSIGN_DATE);

                        String stkActiveDate = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_STK_ACTIVE_DATE);

                        String iqrarDate = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_IQRAR_DATE);


                        int UserID = 0;
                        String tempCheck = strUserID;
                        if(tempCheck!=null && !tempCheck.equals("") && !tempCheck.equals("empty") && !tempCheck.equals("--"))
                            UserID = Integer.parseInt(strUserID);
                        int branchIndicator = 0;
                        String branchValue = "";
                        if (posCode.endsWith(".001")) {
                            branchIndicator = 1;
                            branchValue = posCode.substring(0, posCode.length() - 4);
                            System.out.println("###################### branch : " + branchValue);

                        }


                        String strFlagSuperAdmin = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG, strFlagSuperAdmin);

                        String posPhone = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_PHONE);
                        String posManagerPhone = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_PHONE);
                        String posOwnerPhone = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_PHONE);

                        POSPhones = new Vector();

                        POSPhones.add(posPhone);


                        Vector ownerPhones = new Vector();
                        ownerPhones.add(posOwnerPhone);

                        Vector managerPhones = new Vector();
                        managerPhones.add(posManagerPhone);
                        
                        PosModel posModel = new PosModel();
                        POSDetailModel mdl = new POSDetailModel();
                            /////////////////////////lamya/////////////////////////////////////////////
                      
                      userData = new UserDataModel();
                    teamleaderData = new UserDataModel();
                    salesrepData = new UserDataModel();


                    

                    String supervisorName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_NAME);
                    String supervisorMobile = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_MOBILE);        
                    String supervisorEmail = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_EMAIL);
                    String supervisorAddress = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_ADDRESS);
                    
                    userData = RequestDao.insertUserDetail(con, supervisorName,supervisorMobile,supervisorEmail,supervisorAddress, "4", districtId, null);//managerId
                    String teamleaderName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_NAME);
                    String teamleaderMobile = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_MOBILE);        
                    String teamleaderEmail = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_EMAIL);
                    String teamleaderAddress = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_ADDRESS);
                    teamleaderData = RequestDao.insertUserDetail(con, teamleaderName,teamleaderMobile,teamleaderEmail,teamleaderAddress, "5", districtId, null);//managerId
                    
                    String salesrepName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_SALESREP_NAME);
                    String salesrepMobile = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_SALESREP_MOBILE);        
                    String salesrepEmail = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_SALESREP_EMAIL);
                    String salesrepAddress = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_SALESREP_ADDRESS);
                    
                    salesrepData = RequestDao.insertUserDetail(con, salesrepName,salesrepMobile,salesrepEmail,salesrepAddress, "6", districtId, null); //managerId   


                    mdl.setSupervisorName(userData.getDcmUserId());
                    mdl.setTeamleaderName(teamleaderData.getDcmUserId());
                    mdl.setSalesrepName(salesrepData.getDcmUserId());


                      /////////////////////////lamya - end/////////////////////////////////////////////
                    
                        setPOSDataPOSDetailFromParams(posModel, mdl, paramHashMap);
                        
                        
                        posModel.setPaymentMethod(paymentMethod);
                        posModel.setPaymentLevel(paymentLevel);
                        
                        if (areaId != null && !areaId.equals("empty") && !areaId.equals("") && !areaId.equals("--"))
                            posModel.setAreaId(Integer.parseInt(areaId));
                        else
                            posModel.setAreaId(0);
                        
                        posModel.setBranchOf(branchValue);
                        if (paymentLevel != null && !paymentLevel.equals("empty") && !paymentLevel.equals("") && !paymentLevel.equals("--"))
                            posModel.setPaymentLevelId(Integer.parseInt(paymentLevel));
                        else
                            posModel.setPaymentLevelId(0);
                        if (channelId != null && !channelId.equals("empty") && !channelId.equals("") && !channelId.equals("--"))
                            posModel.setChannelId(Integer.parseInt(channelId));
                        else
                            posModel.setChannelId(0);
                        
                        if (cityId != null && !cityId.equals("empty") && !cityId.equals("") && !cityId.equals("--"))
                            posModel.setCityId(Integer.parseInt(cityId));
                        else
                            posModel.setCityId(0);
                        
                        posModel.setDemoLineNum(demoLine);
                        if (districtId != null && !districtId.equals("empty") && !districtId.equals("")&& !districtId.equals("--")) 
                            posModel.setDistrictId(Integer.parseInt(districtId));
                        else
                            posModel.setDistrictId(0);
                        
                        posModel.setDocNumber(proposedDocNum);
                        posModel.setGovernateId(Integer.parseInt(governrateId));
                        posModel.setIqrarDeliveryDate(iqrarDeliveryDate);
                        posModel.setIqrarReceiveDate(iqrarReceiveDate);
                        posModel.setLevelId(Integer.parseInt(levelId));
                        if (proposedDocId != null && !proposedDocId.equals("") && !proposedDocId.equals("empty") && !proposedDocId.equals("--"))
                            posModel.setProposedDocId(Integer.parseInt(proposedDocId));
                        else
                            posModel.setProposedDocId(0);
                        // posModel.setRateDate(rateDate);
                        posModel.setRateID(Integer.parseInt(rateId));
                        posModel.setStkActiveDate(stkActiveDate);
                        posModel.setStkAssignDate(stkAssignDate);
                        posModel.setStkDeliveryDate(stkDeliveryDate);
                        posModel.setStkDialNumber(stkDialNum);
                        posModel.setStkVerify(iqrarDate);
                        posModel.setDocLocation(strDocLoc);

                        
                        
                        
                        
                        mdl.setPosName(posName.trim());
                        mdl.setPOSCode(posCode.trim());
                        mdl.setPosAddress(posAddress.trim());
                        mdl.setPosEmail(posEmail.trim());
                        mdl.setUserID(UserID);
                        mdl.setPosRegionID(posRegion);
                        mdl.setPosPhones(POSPhones);
                        mdl.setPosManagerPhones(managerPhones);
                        mdl.setPosOwnerPhones(ownerPhones);
                        mdl.setPosOwnerName(posOwnerName.trim());
                        mdl.setPosOwnerBirthDate(posOwnerBirthDate);
                        mdl.setPosOwnerIDTypeID(posOwnerIDType);
                        mdl.setPosOwnerIDNumber(posOwnerIDNumber.trim());
                        mdl.setPosManagerName(posManagerName.trim());
                        mdl.setPosManagerBirthDate(posManagerBirthDate);
                        mdl.setPosManagerIDTypeID(posManagerIDType);
                        mdl.setPosManagerIDNumber(posManagerIDNumber.trim());
                        mdl.setPosArabicName(posArabicName);
                        mdl.setPosArabicAddress(posArabicAddress);
                        mdl.setSurveyID(surveyID);
                        mdl.setPaymentLevel(paymentLevel);
                        mdl.setPaymentMethod(paymentMethod);
                        posModel.setPosDetailModel(mdl);

                        int posId = RequestDao.getPosIdByCode(con, mdl.getPOSCode());
                        boolean stkFlag = false;
                        int stkId = RequestDao.getStkIdBySTKnum(con, stkDialNum); // stk status is new

                        if (stkDialNum == null || stkDialNum.compareTo("") == 0) {
                            stkFlag = true;
                        }

                        if (posId == 0) // pos code not valid or its data entered b4
                        {
                            dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "Sorry , Code Invalid or Can't be updated its Data .. ");
                        } //else if(branchIndicator == 0)
                        //{
                        //dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "Sorry , POS Code Invalid .. Must End With .000 .. ");
                        //}
                        else if (stkFlag == false && stkId == 0) // stk data entered but invalid or entered b4
                        {
                            dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "Sorry , STK Dial Invalid or Can't be updated its Data .. ");
                        } else if (RequestDao.checkStkInOwner(con, stkId + "") == 1) {
                            dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "Sorry , STK Dial Assigned Before .. ");
                        } else if (posId != 0) // pos data entered and valid
                        {
                            RequestDao.updateGenDcm(con, posModel, "1", posId, strUserID);
                            Long pos_detail_id = RequestDao.insertPosDetail(con, posModel, posId, "1");


                            if (/*
                                     * stkFlag == false &&
                                     */RequestDao.getStkIdBySTKnum(con, stkDialNum) != 0) // stk entered and valid
                            {
                                RequestDao.updateStkData(con, iqrarDate, stkDialNum, posId + "");
                            }
                            //  String proposedDocId      = (String)paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC);
                            //String proposedDocNum     = (String)paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM);
             /*
                             * if(proposedDocNum !=null &&
                             * proposedDocNum.compareTo("")!=0 ) {
                             * RequestDao.insertPaymentStatus(con , "1"
                             * ,posId+""); } else {
                             */
                            RequestDao.insertPaymentStatus(con, "2", posId + "");
                            // }

                            dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "POS Data Entered Successfully ..");
                        }

                        /**
                         * ********************************************** Data
                         * Returned to the Page
                         * ***********************************************************
                         */
                        Vector regions = new Vector();
                        Vector IDTypeVector = new Vector();
                        Vector legalFormVec = new Vector();
                        Vector placeTypeVec = new Vector();

                        GenericModel gm = new GenericModel();
                        GenericModel placeTypeGM = new GenericModel();
                        GenericModel IDTypeModel = new GenericModel();

                        GenericModelDAO gmDAO = new GenericModelDAO();

                        IDTypeModel = gmDAO.getColumns(con, "DCM_ID_TYPE");
                        IDTypeVector = gmDAO.getModels(con, IDTypeModel);
                        gm = gmDAO.getColumns(con, "DCM_LEGAL_FORM");
                        legalFormVec = gmDAO.getModels(con, gm);
                        placeTypeGM = gmDAO.getColumns(con, "DCM_POS_PLACE_TYPE");
                        placeTypeVec = gmDAO.getModels(con, placeTypeGM);
                        regions = RequestDao.getAllRegionDataList(con);

                        dataHashMap.put(SCMInterfaceKey.VECTOR_ID_TYPE, IDTypeVector);
                        dataHashMap.put(SCMInterfaceKey.VECTOR_LEGAL_FORM, legalFormVec);
                        dataHashMap.put(SCMInterfaceKey.VECTOR_PLACE_TYPE, placeTypeVec);
                        dataHashMap.put(SCMInterfaceKey.VECTOR_REGIONS, regions);
                        dataHashMap.put(SCMInterfaceKey.DCM_SAVE_POS_TYPE, SCMInterfaceKey.DCM_SAVE_POS_TYPE_NEW);



                        dataHashMap.put(SCMInterfaceKey.CHANNEL_VECTOR, RequestDao.getChannelList(con));
                        dataHashMap.put(SCMInterfaceKey.LEVEL_VECTOR, RequestDao.getLevelList(con));
                        dataHashMap.put(SCMInterfaceKey.PAYMENT_LEVEL_VECTOR, RequestDao.getPaymentList(con));
                        dataHashMap.put(SCMInterfaceKey.RATE_VECTOR, RequestDao.getPlaceList(con));
                        dataHashMap.put(SCMInterfaceKey.DOC_VECTOR, RequestDao.getDocList(con));
                        Utility.logger.debug("USERID:  " + strUserID);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG, strFlagSuperAdmin);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }

                    break;

                case action_pos_data_edit:
                    System.out.println("inside action_pos_data_edit");
                    posDetailId = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_POS_ID);
                    String supervisorDetail = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_SUPERVISOR_NAME);
                    String teamleaderDetail = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_TEAMLEADER_NAME);
                    String salesrepDetail = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_SALESREP_NAME);
                   
                    
                    System.out.println("supervisor id JSP : "+supervisorDetail);
                    System.out.println("teamleader id JSP : "+teamleaderDetail);
                    System.out.println("salesrep id JSP : "+salesrepDetail);
                    
                    posGeneralData = new PosModel();
                    posDetailModel = new POSDetailModel();
                    
                    DCM_CODE = RequestDao.getDCMCodeForPOS(posDetailId);
                    
                    posMobicashNum = (RequestDao.getMobicashNum(DCM_CODE)==null || RequestDao.getMobicashNum(DCM_CODE).compareTo("")==0) ? 0 : Long.parseLong(RequestDao.getMobicashNum(DCM_CODE));
                    String channel = RequestDao.getchannelIDForPOS(posDetailId);
                    String POSLevel = RequestDao.getLevelIDForPOS(posDetailId);
                    String POSStatus = RequestDao.getStatusIDForPOS(posDetailId);
                    String PaymentLevel = RequestDao.getPaymentLevelIDForPOS(posDetailId);
                    String PaymentMethod = RequestDao.getPaymentMethodIDForPOS(posDetailId);
                    String posCodeValue =(String) paramHashMap.get("pos_code");
                    
                    System.out.println("pos detail id : "+posDetailId+" and pos code : "+posCodeValue);
                    
                    /////////////////////////////////
                    posGeneralData = setPOSDataPOSDetail(/*posGeneralData*//*, posDetailModel*/ posDetailId, con);
                    
                    posGeneralData.setPaymentLevel(PaymentLevel);
                    posGeneralData.setMobicashNum(posMobicashNum);
                    posGeneralData.setPaymentMethod(PaymentMethod);
                    //new get detail model from model posGeneralData.getPosDetailModel() instead of posGeneralData
                    posGeneralData.getPosDetailModel().setPaymentLevel(PaymentLevel);
                    posGeneralData.getPosDetailModel().setMobicashNum(posMobicashNum);
                    posGeneralData.getPosDetailModel().setPaymentMethod(PaymentMethod);
                    //////////////new - end/////////////////////////
                    
                    
                    
                    
                    
                    System.out.println("payment method from request DAO inside action_pos_data_edit : "+PaymentMethod);
                    POSPhones = new Vector();
                    OwnerPhones = new Vector();
                    ManagerPhones = new Vector();
                    // temp posDetailModel
                    posDetailModel = RequestDao.getPOSByID(con, posDetailId);
                    ////////temp - end////////
                    /* posDetails.setPosAddress(rs.getString("POS_ADDRESS"));
                posDetails.setPosArabicAddress("POS_ARABIC_ADDRESS");
                posDetails.setPosArabicName("POS_ARABIC_NAME");
                posDetails.setPosCityID(rs.getInt("POS_CITY_ID"));
                posDetails.setPOSCode(rs.getString("POS_CODE"));
                posDetails.setPosDistrictID(rs.getInt("POS_DISTRICT_ID"));
                posDetails.setPosEmail(rs.getString("POS_EMAIL"));
                posDetails.setPosName(rs.getString("POS_NAME"));
                posDetails.setSupervisorName(rs.getString("SUPERVISOR_ID"));
                posDetails.setTeamleaderName(rs.getString("TEAMLEADER_ID"));
                posDetails.setSalesrepName(rs.getString("SALESREP_ID"));*/
                    posGeneralData.getPosDetailModel().setPosAddress(posDetailModel.getPosAddress());
                    posGeneralData.getPosDetailModel().setPosArabicAddress(posDetailModel.getPosArabicAddress());
                    posGeneralData.getPosDetailModel().setPosCityID(posDetailModel.getPosCityID());
                    posGeneralData.getPosDetailModel().setPOSCode(posDetailModel.getPOSCode());
                    posGeneralData.getPosDetailModel().setPosDistrictID(posDetailModel.getPosDistrictID());
                    posGeneralData.getPosDetailModel().setPosEmail(posDetailModel.getPosEmail());
                    posGeneralData.getPosDetailModel().setPosName(posDetailModel.getPosName());
                    posGeneralData.getPosDetailModel().setSupervisorName(posDetailModel.getSupervisorName());
                    posGeneralData.getPosDetailModel().setTeamleaderName(posDetailModel.getTeamleaderName());
                    posGeneralData.getPosDetailModel().setSalesrepName(posDetailModel.getSalesrepName());
                     //////////////////
                    POSPhones = RequestDao.getPOSPhones(con, posDetailId);
                    if (POSPhones != null && POSPhones.size() > 0) {
                        posGeneralData.getPosDetailModel().setPosPhones(POSPhones);
                    }

                    OwnerPhones = RequestDao.getOwnerPhones(con, posDetailId);
                    ManagerPhones = RequestDao.getManagerPhones(con, posDetailId);
                    if (ManagerPhones != null && ManagerPhones.size() > 0) {
                        posGeneralData.getPosDetailModel().setPosManagerPhones(ManagerPhones);
                    }
                    if (OwnerPhones != null && OwnerPhones.size() > 0) {
                        posGeneralData.getPosDetailModel().setPosOwnerPhones(OwnerPhones);
                    }

                   // posGeneralData.setPosDetailModel(posDetailModel);
                    posGeneralData = RequestDao.getPosData(con, posGeneralData, DCM_CODE,posDetailId);
                    posGeneralData = RequestDao.getPosDetailData(con, posGeneralData, posDetailId);
                    System.out.println("in EDIT befire getuserbyid : "+posGeneralData.getPosDetailModel().getSupervisorName());
                    
                    
                    
                    
                    if (supervisorDetail!=null && supervisorDetail.compareTo("")!=0 && supervisorDetail.compareTo("---")!=0)
                        userData = RequestDao.getUserDataByDetailId(con, supervisorDetail);
                    else
                        userData = RequestDao.getUserDataByDetailId(con, posGeneralData.getPosDetailModel().getSupervisorName()/*supervisorDetailId*/);
                    
                    if (teamleaderDetail!=null && teamleaderDetail.compareTo("")!=0 && teamleaderDetail.compareTo("---")!=0)
                        teamleaderData = RequestDao.getUserDataByDetailId(con, teamleaderDetail);
                    else
                        teamleaderData = RequestDao.getUserDataByDetailId(con, posGeneralData.getPosDetailModel().getTeamleaderName()/*teamleaderDetailId*/);
                    
                    
                    if (salesrepDetail!=null && salesrepDetail.compareTo("")!=0 && salesrepDetail.compareTo("---")!=0)
                        salesrepData = RequestDao.getUserDataByDetailId(con, salesrepDetail);
                    else
                        salesrepData = RequestDao.getUserDataByDetailId(con, posGeneralData.getPosDetailModel().getSalesrepName()/*salesrepDetailId*/);
                    
                    System.out.println("action_pos_data_edit: posData.getPosDetailModel().getPosRegionID() "+posGeneralData.getPosDetailModel().getPosRegionID());
                    dataHashMap.put(SCMInterfaceKey.SIMILAR_POS_LIST, posGeneralData);
                    Vector regions = new Vector();
                    Vector governs = new Vector();
                    Vector cities = new Vector();
                    Vector districts = new Vector();
                    Vector imgDistricts = new Vector();
                    Vector areas = new Vector();
                    Vector IDTypeVector = new Vector();
                    Vector legalFormVec = new Vector();
                    Vector placeTypeVec = new Vector();

                    GenericModel gm = new GenericModel();
                    GenericModel placeTypeGM = new GenericModel();
                    GenericModel IDTypeModel = new GenericModel();

                    GenericModelDAO gmDAO = new GenericModelDAO();

                    IDTypeModel = gmDAO.getColumns(con, "DCM_ID_TYPE");
                    IDTypeVector = gmDAO.getModels(con, IDTypeModel);

                    regions = RequestDao.getAllRegions(con,"1");
                    governs = RequestDao.getAllRegions(con,"2");
                    cities = RequestDao.getAllRegions(con,"3");
                    districts = RequestDao.getAllRegions(con,"4");
                    imgDistricts = RequestDao.getAllRegions(con,"6");
                    areas = RequestDao.getAllRegions(con,"5");

                    dataHashMap.put(SCMInterfaceKey.VECTOR_ID_TYPE, IDTypeVector);

                    dataHashMap.put(SCMInterfaceKey.VECTOR_REGIONS, regions);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_GOVERNS, governs);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_CITIES, cities);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_DISTRICTS, districts);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_IMAGE_DISTRICTS, imgDistricts);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_AREAS, areas);
                    
                    dataHashMap.put(SCMInterfaceKey.DCM_SAVE_POS_TYPE, SCMInterfaceKey.DCM_SAVE_POS_TYPE_NEW);

                    dataHashMap.put(SCMInterfaceKey.RATE_VECTOR, RequestDao.getPlaceList(con));
                    dataHashMap.put(SCMInterfaceKey.DOC_VECTOR, RequestDao.getDocList(con));
                    dataHashMap.put(SCMInterfaceKey.INPUT_HIDDEN_POS_ID, posDetailId);
                    dataHashMap.put(SCMInterfaceKey.PAYMENT_LEVEL_VECTOR, RequestDao.getPaymentList(con));
                    dataHashMap.put(SCMInterfaceKey.PAYMENT_METHOD_VECTOR, RequestDao.getPaymentMethodList(con));
                    dataHashMap.put(SCMInterfaceKey.SURVEY_DATE , RequestDao.getSurveyDate(con,posCodeValue));
                    //System.out.println("SCMInterfaceKey.SURVEY_DATE : "+(String)dataHashMap.get(SCMInterfaceKey.SURVEY_DATE));
                    Utility.logger.debug("USERID:  " + strUserID);

                    dataHashMap.put(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG, "");
                    dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "");
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL, channel);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL, POSLevel);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_STATUS, POSStatus);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL, PaymentLevel);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD, PaymentMethod);
                    dataHashMap.put(SCMInterfaceKey.CHANNEL_VECTOR, RequestDao.getChannelList(con));
                    dataHashMap.put(SCMInterfaceKey.LEVEL_VECTOR, RequestDao.getLevelList(con));
                    dataHashMap.put(SCMInterfaceKey.STATUS_VECTOR, RequestDao.getStatusList(con));
                    dataHashMap.put("CONTROL_REGION_PARENT_ID", posGeneralData.getPosDetailModel().getPosRegionID() + "-" + posGeneralData.getGovernateId() + "-" + posGeneralData.getCityId() + "-" + posGeneralData.getDistrictId() + "-" + posGeneralData.getAreaId());
                    dataHashMap.put(SCMInterfaceKey.CHILD_REGIONS_HM, RequestDao.getChildRegionsListHM(con, ((String) dataHashMap.get("CONTROL_REGION_PARENT_ID"))));

                    dataHashMap.put(SCMInterfaceKey.POS_DETAIL_MODEL, posDetailModel);//SCMInterfaceKey.SIMILAR_POS_LIST
                    dataHashMap.put(SCMInterfaceKey.SIMILAR_POS_LIST, posGeneralData);
                    dataHashMap.put(SCMInterfaceKey.SIMILAR_USER_LIST, userData);
                    dataHashMap.put(SCMInterfaceKey.SIMILAR_TEAMLEADER_LIST, teamleaderData);
                    dataHashMap.put(SCMInterfaceKey.SIMILAR_SALESREP_LIST, salesrepData);
                    break;

                case action_pos_data_edit_store:
                    System.out.println("inside action_pos_data_edit_store");
                    posDetailId = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_POS_ID);
                    
                
                    PaymentMethodModel payMethodModel = new PaymentMethodModel();
                    PosModel posModel = new PosModel();
                    POSDetailModel mdl = new POSDetailModel();
                    
                    posModel = setPOSDataPOSDetailFromParams(posModel, mdl, paramHashMap);
                   
                    String strDocLoc = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_DOC_LOC);
                    String stkChanged = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_STK_POS_CHANGE);
                    String docChanged = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_POS_DOC_CHANGE);
                    posDetailId = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_POS_ID);

                    String posName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_NAME);
                    String posCodeValue1 = (String) paramHashMap.get("pos_code");
                    String posArabicName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_ARABIC_POS_NAME);
                    String posArabicAddress = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_ARABIC_POS_ADDRESS);
                    String channelId = (String) paramHashMap.get(SCMInterfaceKey.TEXT_POSCHANNEL);
                    System.out.println("inside action_pos_data_edit_store pos detail id : "+posDetailId);
                    String levelId = (String) paramHashMap.get(SCMInterfaceKey.LEVEL_FOR_POS);
                    String statusId = (String) paramHashMap.get(SCMInterfaceKey.STATUS_FOR_POS);
                    String Payment = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL);
                    String PaymentM = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD);
                    System.out.println("inside edit store payment method is : "+PaymentM);
                    String demoLine = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_DEMO);
                    String proposedDocId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC);
                    String proposedDocNum = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM);

                    String stkDialNum = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_STK_DIAL);
                    
                    String stkVerify = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_STK_VERIFICATION);


                    if (stkDialNum == null) {
                        stkDialNum = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_STK_NUMBER);
                        //stkVerify     = (String)paramHashMap.get("verify_stk");
                    }

                    String posManagerIDNumber = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER);
                    String posOwnerIDNumber = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER_HIDDEN);

                    if (posManagerIDNumber == null) {
                        posManagerIDNumber = (String) paramHashMap.get("manager_id");
                    }

                    if (posOwnerIDNumber == null) {
                        posOwnerIDNumber = (String) paramHashMap.get("owner_id");
                    }

                    String rateId = "1";
                    //  String rateDate           = (String)paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_RATE_DATE);

                    String posEmail = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_EMAIL);
                    String posAddress = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_ADDRESS);
                    
                    int posRegion = 0;
                    String tempCheck = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_REGION);
                    if(tempCheck!=null && !tempCheck.equals("") && !tempCheck.equals("empty") && !tempCheck.equals("--"))
                        posRegion = Integer.parseInt((String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_REGION));
                    
                    String governrateId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_GOVER);
                    String areaId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_AREA);
                    String cityId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CITY);
                    String districtId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT);
                    String imageDistrictId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_IMAGE_DISTRICT);

                    String posOwnerName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME);
                    String posOwnerBirthDate = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_BIRTH_DATE);
                    
                    int posOwnerIDType = 0;
                    tempCheck = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE);
                    if(tempCheck!=null && !tempCheck.equals("") && !tempCheck.equals("empty") && !tempCheck.equals("--"))
                        posOwnerIDType = Integer.parseInt((String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE));

                    String posManagerName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME);
                    String posManagerBirthDate = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_BIRTH_DATE);
                    
                    int posManagerIDType = 0;
                    tempCheck = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE);
                    if(tempCheck!=null && !tempCheck.equals("") && !tempCheck.equals("empty") && !tempCheck.equals("--"))
                        posManagerIDType = Integer.parseInt((String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE));


                    int UserID = 0;
                    tempCheck = strUserID;
                    if(tempCheck!=null && !tempCheck.equals("") && !tempCheck.equals("empty") && !tempCheck.equals("--"))
                        UserID = Integer.parseInt(strUserID);
                    
                    int branchIndicator = 0;
                    String branchValue = "";
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!! " + posCodeValue1);


                    String strFlagSuperAdmin = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG, strFlagSuperAdmin);
                    POSPhones = new Vector();

                    String phone = (String) paramHashMap.get("phones__R0__C1");
                    POSPhones.add(phone);
                    Vector ownerPhones = new Vector();

                    phone = (String) paramHashMap.get("owner_phones__R0__C1");
                    ownerPhones.add(phone);
                    Vector managerPhones = new Vector();

                    phone = (String) paramHashMap.get("manager_phones__R0__C1");
                    managerPhones.add(phone);
 //////////////////////new features
                    
                    
                    //lamya
                    payMethodModel.setPaymentMethodName(PaymentM);  
                    posModel.setPaymentMethod(PaymentM);
                    posModel.setPaymentLevel(Payment);
                    
                    if (areaId != null && !areaId.equals("empty") && !areaId.equals("") && !areaId.equals("--")) 
                        posModel.setAreaId(Integer.parseInt(areaId));
                    else
                        posModel.setAreaId(0);
                    
                    
                    
                    posModel.setBranchOf(branchValue);
                    if (channelId != null && !channelId.equals("empty") && !channelId.equals("") && !channelId.equals("--"))
                        posModel.setChannelId(Integer.parseInt(channelId));
                    else
                        posModel.setChannelId(0);
                    if (cityId != null && !cityId.equals("empty") && !cityId.equals("") && !cityId.equals("--"))
                        posModel.setCityId(Integer.parseInt(cityId));
                    else
                        posModel.setCityId(0);
                    posModel.setDemoLineNum(demoLine);
                    if (districtId != null && !districtId.equals("empty") && !districtId.equals("") && !districtId.equals("--")) 
                        posModel.setDistrictId(Integer.parseInt(districtId));
                    else
                        posModel.setDistrictId(0);
                    
                    if (imageDistrictId != null && !imageDistrictId.equals("empty") && !imageDistrictId.equals("") && !imageDistrictId.equals("--")) 
                        posModel.setImgDistrictId(Integer.parseInt(imageDistrictId));
                    else
                        posModel.setImgDistrictId(0);
                    posModel.setDocNumber(proposedDocNum == null ? "" : proposedDocNum.trim());
                    
                    
                    if (governrateId != null && !governrateId.equals("empty") && !governrateId.equals("") && !governrateId.equals("--")) 
                        posModel.setGovernateId(Integer.parseInt(governrateId));
                    else
                         posModel.setGovernateId(0);
                    if (Payment != null && !Payment.equals("empty") && !Payment.equals("") && !Payment.equals("--")) 
                        posModel.setPaymentLevelId(Integer.parseInt(Payment));
                    else
                        posModel.setPaymentLevelId(0);
                    
                    if (levelId != null && !levelId.equals("empty") && !levelId.equals("") && !levelId.equals("--")) 
                        posModel.setLevelId(Integer.parseInt(levelId));
                    else
                        posModel.setLevelId(0);
                    
                    
                    
                    if (statusId != null && !statusId.equals("empty") && !statusId.equals("") && !statusId.equals("--")) 
                        posModel.setStatusId(Integer.parseInt(statusId));
                    else
                        posModel.setStatusId(0);
                        
                        
                    if (proposedDocId != null && !proposedDocId.equals("--") && !proposedDocId.equals("empty") && !proposedDocId.equals("")) 
                        posModel.setProposedDocId(Integer.parseInt(proposedDocId));
                    else
                        posModel.setProposedDocId(0);
                    
                    // posModel.setRateDate(rateDate);
                    if (rateId != null && !rateId.equals("--") && !rateId.equals("empty") && !rateId.equals("")) 
                        posModel.setRateID(Integer.parseInt(rateId));
                    else
                        posModel.setRateID(0);
                    
                    if (stkDialNum != null && !stkDialNum.equals("") && !stkDialNum.equals("empty") && !stkDialNum.equals("--"))
                        posModel.setStkDialNumber(stkDialNum.trim());
                        //posModel.setStkVerify(stkVerify.trim());
                    else
                        posModel.setStkDialNumber("");
                    //mdl
                    posModel.getPosDetailModel().setPaymentMethod(PaymentM);
                    
                    // all below were mdl
                    posModel.getPosDetailModel().setPosName(posName.trim());
                    posModel.getPosDetailModel().setPOSCode(posCodeValue1);
                    posModel.getPosDetailModel().setPosAddress(posAddress);
                    posModel.getPosDetailModel().setPosEmail(posEmail.trim());
                    posModel.getPosDetailModel().setUserID(UserID);
                    posModel.getPosDetailModel().setPosRegionID(posRegion);
                    posModel.getPosDetailModel().setPosPhones(POSPhones);
                    posModel.getPosDetailModel().setPosManagerPhones(managerPhones);
                    posModel.getPosDetailModel().setPosOwnerPhones(ownerPhones);
                    posModel.getPosDetailModel().setPosOwnerName(posOwnerName.trim());
                    posModel.getPosDetailModel().setPosOwnerBirthDate(posOwnerBirthDate);
                    posModel.getPosDetailModel().setPosOwnerIDTypeID(posOwnerIDType);
                    posModel.getPosDetailModel().setPosOwnerIDNumber(posOwnerIDNumber.trim());
                    posModel.getPosDetailModel().setPosManagerName(posManagerName.trim());
                    posModel.getPosDetailModel().setPosManagerBirthDate(posManagerBirthDate);
                    posModel.getPosDetailModel().setPosManagerIDTypeID(posManagerIDType);
                    posModel.getPosDetailModel().setPosManagerIDNumber(posManagerIDNumber.trim());
                    posModel.getPosDetailModel().setPosArabicName(posArabicName);
                    posModel.getPosDetailModel().setPosArabicAddress(posArabicAddress);
                    posModel.getPosDetailModel().setPosLevel(levelId);
                    posModel.getPosDetailModel().setPosStatus(statusId);
                    posModel.getPosDetailModel().setPaymentLevel(Payment);
                    posModel.getPosDetailModel().setPaymentMethod(PaymentM);
                    
                    //posModel.setPosDetailModel(mdl);
                    
                    posModel.setDocLocation(strDocLoc);

                    int posId = RequestDao.getAnyPosIdByCode(con, mdl.getPOSCode());
                    boolean stkFlag = false;

                    int stkId = RequestDao.getEditStkIdBySTKnum(con, stkDialNum); // stk status is new



                    if (stkDialNum == null || stkDialNum.compareTo("") == 0 || stkChanged.compareTo("1") == 0) {
                        stkFlag = true;
                    }
                    System.out.println("pos id issssssssssssssssss " + posId);
                    if (stkFlag == false && stkId == 0) // stk data entered but invalid or entered b4
                    {
                        System.out.println("Sorry , STK Dial Invalid or Can't be updated its Data .. " + stkFlag + stkId);
                        dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "Sorry , STK Dial Invalid or Can't be updated its Data .. ");
                    } else if (RequestDao.checkStkInOwner(con, stkId + "") == 1 && stkFlag == false) {
                        System.out.println("Sorry , STK Dial Assigned Before ..");
                        dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "Sorry , STK Dial Assigned Before .. ");
                    } else if (posId != 0) // pos data entered and valid
                    {
                        System.out.println("posId : " + posId);

                        /**
                         * Change Request for disabling changing the status. By
                         * Ahmed Safwat 31 May 2011
                         *
                         */
                        String superId = "";
                        String teamId ="";
                        String repId ="";
                         
                        List users = RequestDao.getUsersByDistrictId(con, districtId);
                        HashMap user = new HashMap();
                        for(int i=0;i< users.size();i++){
                            user = (HashMap)users.get(i);
                            if(((String)user.get("LEVEL")).compareTo("4")==0)
                                superId = (String)user.get("ID");
                            if(((String)user.get("LEVEL")).compareTo("5")==0)
                                teamId = (String)user.get("ID");
                            if(((String)user.get("LEVEL")).compareTo("6")==0)
                                repId = (String)user.get("ID");
                        
                        }
                        
                        RequestDao.updateGenDcmUpdated(con, posModel, posId, strUserID);
                        RequestDao.deletePosDetailForEdit(con, posDetailId, strUserID);
                        
                        posModel.getPosDetailModel().setSupervisorName(superId);
                        posModel.getPosDetailModel().setTeamleaderName(teamId);
                        posModel.getPosDetailModel().setSalesrepName(repId);
                        
                       // posModel.setPosDetailModel(mdl);
                        Long pos_detail_id = RequestDao.insertPosDetail(con, posModel, posId, "1");
                        
                        if (stkFlag == false && RequestDao.getEditStkIdBySTKnum(con, stkDialNum) != 0) // stk entered and valid
                        {
                            RequestDao.updateStkData(con, stkDialNum.trim(), posId + "", strUserID, "");
                        }
                        dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "POS Data Updated Successfully ..");
                        System.out.println("TEST action_pos_data_edit_store TEST");
                        dataHashMap.put(SCMInterfaceKey.POS_DETAIL_MODEL, /*mdl*/posModel.getPosDetailModel());//SCMInterfaceKey.SIMILAR_POS_LIST
                        dataHashMap.put(SCMInterfaceKey.SIMILAR_POS_LIST, posModel);
                       /* dataHashMap.put(SCMInterfaceKey.SIMILAR_USER_LIST, userData);
                        dataHashMap.put(SCMInterfaceKey.SIMILAR_TEAMLEADER_LIST, teamleaderData);
                        dataHashMap.put(SCMInterfaceKey.SIMILAR_SALESREP_LIST, salesrepData);*/
                    }


                    break;




                case active_pos_export_excel_management: {
                    String dateFrom = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_GENERATE_DCM_TEMPLATE_FROM);
                    String dateTo = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_GENERATE_DCM_TEMPLATE_TO);
                    Vector<DistributerStaticDataModel> distributerStaticData = RequestDao.getDistributerStaticData(con);
                    Vector<DistributerSTKDetailsModel> distributerSTKData = RequestDao.getDistributerSTKDetails(con, dateFrom, dateTo);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_DISTRIBUTERS_STATIC_DATA, distributerStaticData);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_DISTRIBUTERS_STK_DETAILS, distributerSTKData);

                }
                break;

                case import_template_dates_for_pos:

                    break;

                case action_return_generate_code: {
                    loadDataBack3ak(con, dataHashMap, paramHashMap);
                    String parentCode = (String) paramHashMap.get(SCMInterfaceKey.PARENT_POS_CODE);
                    String generatedCode = POSDAO.generateCodeForChildPOS(parentCode);
                    String newParentCode = generatedCode.split("\\.")[0];
                    if (parentCode.split("\\.").length > 1) {
                        for (int i = 0; i <= parentCode.split("\\.")[1].length() - generatedCode.split("\\.")[1].length(); i++) {
                            if (parentCode.split("\\.")[1].length() > generatedCode.split("\\.")[1].length()) {
                                generatedCode = generatedCode + "0";
                            } else {
                                break;
                            }
                        }
                    } else {
                        for (int i = 0; generatedCode.split("\\.")[1].length() < 3; i++) {

                            generatedCode = generatedCode + "0";

                        }
                    }
                    // channel = POSDAO.getChannelForPOS(newParentCode);
                    // String level = POSDAO.getLevelForPOS(newParentCode);

                    //Developed in mobinil Ahmed Adel 14-3
                    channel = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL);
                    String level = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL);
                    String method = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD);


                    System.out.println("parentCode is " + parentCode);
                    System.out.println("generatedCode is " + generatedCode);
                    System.out.println("channel is " + channel);
                    System.out.println("level is " + level);
                    System.out.println("newParentCode is " + newParentCode);
                    if (generatedCode == null || generatedCode.equals("")) {

                        dataHashMap.put(SCMInterfaceKey.DCM_SAVE_POS_TYPE, SCMInterfaceKey.DCM_SAVE_POS_TYPE_NEW);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL, "1");
                        dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL, "1");
                        dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD, "1");
                        dataHashMap.put(SCMInterfaceKey.POS_CODE, "\"\"");

                        Utility.logger.debug("USERID:  " + strUserID);

                        strFlagSuperAdmin = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG, strFlagSuperAdmin);

                        dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "");
                        dataHashMap.put(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE, "Invalid parent code");


                    } else {


                        dataHashMap.put(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE, "\"\"");


                        dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL, channel);
                        dataHashMap.put(SCMInterfaceKey.DCM_SAVE_POS_TYPE, SCMInterfaceKey.DCM_SAVE_POS_TYPE_NEW);

                        dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL, level);
                        dataHashMap.put(SCMInterfaceKey.POS_CODE, generatedCode);

                        dataHashMap.put(SCMInterfaceKey.IS_GENERATE_CHILD_CODE, "true");

                        Utility.logger.debug("USERID:  " + strUserID);

                        strFlagSuperAdmin = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG, strFlagSuperAdmin);

                        dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "");
                    }
                    System.out.println("generatedCode from 1621 isss " + generatedCode);
                }
                break;

                case action_new_generate_code: {
                    loadDataBack3ak(con, dataHashMap, paramHashMap);
                    try {
                        channelId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL);
                        levelId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL);
                        String paymentlevel = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL);
                        System.out.println("paymentlevel iss " + paymentlevel);
                        String generatedCode = RequestDao.getPosGreatestCode(con, channelId, paymentlevel);
                        RequestDao.updateMaxPos(con, generatedCode, channelId, paymentlevel); // update Max Code Counter
                        if (generatedCode.equals(null) || generatedCode.equals("")) {
                            dataHashMap.put(SCMInterfaceKey.POS_CODE, "");

                            dataHashMap.put(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE, "Invalid level or channel");
                        } else {
                            loadDataPosEntryBack(con, dataHashMap, paramHashMap, generatedCode);
                        }

//                         regions = new Vector();
//                         IDTypeVector = new Vector();
//                         legalFormVec = new Vector();
//                         placeTypeVec = new Vector();
//
//                         gm = new GenericModel();
//                         placeTypeGM = new GenericModel();
//                         IDTypeModel = new GenericModel();
//
//                         gmDAO = new GenericModelDAO();
//
//                        IDTypeModel = gmDAO.getColumns(con, "DCM_ID_TYPE");
//                        IDTypeVector = gmDAO.getModels(con, IDTypeModel);
//                        gm = gmDAO.getColumns(con, "DCM_LEGAL_FORM");
//                        legalFormVec = gmDAO.getModels(con, gm);
//                        placeTypeGM = gmDAO.getColumns(con, "DCM_POS_PLACE_TYPE");
//                        placeTypeVec = gmDAO.getModels(con, placeTypeGM);
//                        regions = RequestDao.getAllRegionDataList(con);

//                        dataHashMap.put(SCMInterfaceKey.VECTOR_ID_TYPE, IDTypeVector);
//                        dataHashMap.put(SCMInterfaceKey.VECTOR_LEGAL_FORM, legalFormVec);
//                        dataHashMap.put(SCMInterfaceKey.VECTOR_PLACE_TYPE, placeTypeVec);
//                        dataHashMap.put(SCMInterfaceKey.VECTOR_REGIONS, regions);
//                        dataHashMap.put(SCMInterfaceKey.DCM_SAVE_POS_TYPE, SCMInterfaceKey.DCM_SAVE_POS_TYPE_NEW);
//                        dataHashMap.put(SCMInterfaceKey.CHANNEL_VECTOR, RequestDao.getChannelList(con));
//                        dataHashMap.put(SCMInterfaceKey.LEVEL_VECTOR, RequestDao.getLevelList(con));
//                        dataHashMap.put(SCMInterfaceKey.RATE_VECTOR, RequestDao.getPlaceList(con));
//                        dataHashMap.put(SCMInterfaceKey.DOC_VECTOR, RequestDao.getDocList(con));
//                        dataHashMap.put(SCMInterfaceKey.PAYMENT_LEVEL_VECTOR, RequestDao.getPaymentList(con));
//                        dataHashMap.put(SCMInterfaceKey.IQRAR_TYPE_VECTOR, RequestDao.getIqrarTypeList(con));
//                        dataHashMap.put(SCMInterfaceKey.POS_CODE, generatedCode );
                        //  dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL,levelId);
                        //   dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL,channelId);
                        //  dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL,paymentlevel);

                        Utility.logger.debug("USERID:  " + strUserID);

                        strFlagSuperAdmin = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
                        dataHashMap.put(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG, strFlagSuperAdmin);

                        dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "");

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }


                break;


                case action_pos_data_view_history: {

                    posDetailId = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_POS_ID);

                    Vector<GeneralHistory> historicalData = RequestDao.getHistoricalData(posDetailId, con);
                    dataHashMap.put(SCMInterfaceKey.HISTORICAL_VECTOR, historicalData);

                }
                break;
                case action_change_stk_number: {

                    String oldSTKDial = (String) paramHashMap.get(SCMInterfaceKey.OLD_STK_DIAL_NUMBER);
                    String newSTKDial = (String) paramHashMap.get(SCMInterfaceKey.NEW_STK_DIAL_NUMBER);
                    boolean checkoldSTK = STKDAO.isSTKDialExist(oldSTKDial);
                    boolean checkNewSTK = STKDAO.isSTKDialExist(newSTKDial);
                    if (checkoldSTK && !checkNewSTK) {
                        STKDAO.changeSTKDialNumber(con, oldSTKDial, newSTKDial);
                        dataHashMap.put(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE, "STK Changed");
                    } else {
                        if (!checkoldSTK) {
                            dataHashMap.put(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE, "Old STK Not Exist");
                        }
                        if (checkNewSTK) {
                            dataHashMap.put(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE, "New STK Is Exist");
                        }
                    }

                }
                break;
                case action_change_stk_number_page:
                    dataHashMap.put(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE, "");

                    break;
                case action_get_region_child: {
                    loadDataBack3ak(con, dataHashMap, paramHashMap);

                    break;

                }
                case action_get_region_child_for_edit: {
                    loadDataBack3ak_for_edit(con, dataHashMap, paramHashMap);

                    break;

                }
                case action_generate_code:
                    channelId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL);
                    levelId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL);
                    String paymentlevel = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL);
                    String paymentM = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD);
                    System.out.print("Ahmed dddddddddddddd ");
                    loadDataBack3ak(con, dataHashMap, paramHashMap);
                    channel = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL, levelId);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL, channelId);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL, paymentlevel);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD, paymentM);
                    break;
                case search_pos_excel: {
//                     regions = new Vector();
//                     IDTypeVector = new Vector();
//                     IDTypeModel = new GenericModel();
//                     gmDAO = new GenericModelDAO();
//                    IDTypeModel = gmDAO.getColumns(con, "DCM_ID_TYPE");
//                    IDTypeVector = gmDAO.getModels(con, IDTypeModel);
//                    regions = RequestDao.getAllRegionDataList(con);
//
//                    dataHashMap.put(SCMInterfaceKey.VECTOR_ID_TYPE, IDTypeVector);
//                    dataHashMap.put(SCMInterfaceKey.VECTOR_REGIONS, regions);
//                    dataHashMap.put(SCMInterfaceKey.DOC_VECTOR, RequestDao.getDocList(con));
                    System.out.println("SEARCH EXCEL");
                    System.out.println("value " + paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_NAME));
                    System.out.println("class name " + paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_NAME).getClass().getName());

                    //String posStatusId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_STATUS);
                    String posDataName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_NAME);
                    String posDataCode = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CODE);
                    String posDataRegion = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_REGION);
                    String posDataGover = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_GOVER);
                    String posDataDistrict = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT);
                    String posDataImgDistrict = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_IMAGE_DISTRICT);
                    String posDataArea = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_AREA);
                    String posDataCity = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CITY);
                    String posDataOwnerName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME);
                    String posDataOwnerIdNum = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER);
                    String posDataOwnerIdType = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE);
                    String posDataManagerName = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME);
                    String posDataManagerIdNum = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER);
                    String posDataManagerIdType = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE);
                    String posDataProposedDoc = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC);
                    String posDataDocNum = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM);
                    String posDataStkNum = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_STK_DIAL);
                    String Level = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL);
                    Payment = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL);
                    String Channel = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL);
                    String entryDate = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_INPUT_ENTRY_DATE);
                    String englishAddress = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_INPUT_ENGLISH_ADDRESS);
                    String posPhone = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_INPUT_POS_PHONE);
                    String docLocation = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_INPUT_DOC_LOCATION);
                    String posStatusId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_SELECT_POS_STATUS);
                    String stkStatusId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_SELECT_STK_STATUS);
                    String psymentStatusId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_SELECT_PAYMENT_STATUS);
                    String supervisorDetailName = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_SUPERVISOR_NAME);
                    String teamleaderDetailName = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_TEAMLEADER_NAME);
                    String salesrepDetailName = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_USER_ID);
                    supervisorDetailId = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_SUPERVISOR_NAME);
                    teamleaderDetailId = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_TEAMLEADER_ID);
                    salesrepDetailId = (String) paramHashMap.get(SCMInterfaceKey.INPUT_HIDDEN_SALESREP_ID);
                    
                    
                    
                    
                    //Vector<POSSearchExcelModel> dataVec = RequestDao.searchPosDataExcel(con, posDataOwnerIdType, posDataDocNum, posDataManagerName, posDataStkNum, posDataManagerIdType, posDataProposedDoc, posDataManagerIdNum, posDataName, posDataCode, posDataRegion, posDataGover, posDataDistrict, posDataArea, posDataCity, posDataOwnerName, posDataOwnerIdNum, Level, Payment, Channel, posStatusId, stkStatusId, psymentStatusId, posPhone, englishAddress, entryDate, docLocation, supervisorDetailId,supervisorDetailName, teamleaderDetailId, teamleaderDetailName, salesrepDetailId, salesrepDetailName);
                    //System.out.println("dataVec size " + dataVec.size());
                    String Slach = System.getProperty("file.separator");
                    String baseDirectory = (String) paramHashMap.get(SCMInterfaceKey.BASE_DIRECTION);
                    //String excelLink = PoiWriteExcelFile.exportExcelSheetForPOSSearch(dataVec, baseDirectory);
                    /*instead:*/
                    Vector files =RegionPOSReportDAO.getRegionPOSDataAll(con);
                    String excelLink = PoiWriteExcelFile.exportExcelSheetForRegionPOSData(/*dataVec*/files, baseDirectory,"");
              
                    /*end*/
                    dataHashMap.put(SCMInterfaceKey.SEARCH_EXCEL_SHEET_LINK, excelLink);
                    
                    
                    
                    
//                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_NAME, posDataName);
//                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_CODE, posDataCode);
//                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_REGION, posDataRegion);
//                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_GOVER, posDataGover);
//                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT, posDataDistrict);
//                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_AREA, posDataArea);
//                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_CITY, posDataCity);
//                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME, posDataOwnerName);
//                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER, posDataOwnerIdNum);
//                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE, posDataOwnerIdType);
//                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME, posDataManagerName);
//                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER, posDataManagerIdNum);
//                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE, posDataManagerIdType);
//                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC, posDataProposedDoc);
//                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM, posDataDocNum);
//                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_STK_DIAL, posDataStkNum);
//                    dataHashMap.put(SCMInterfaceKey.CHANNEL_VECTOR, RequestDao.getChannelList(con));
//                    dataHashMap.put(SCMInterfaceKey.LEVEL_VECTOR, RequestDao.getLevelList(con));
//                    dataHashMap.put(SCMInterfaceKey.PAYMENT_LEVEL_VECTOR, RequestDao.getPaymentList(con));
//                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL,Level);
//                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL,Payment);
//                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL,Channel);


                    break;
                }
            }

        } catch (Exception objExp) {
            objExp.printStackTrace();
        }
        return dataHashMap;
    }

    private static void loadListsPosManagement(Connection con, HashMap dataHashMap, HashMap paramHashMap, boolean returnData) {
        if (returnData) {
            dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_INPUT_ENTRY_DATE, paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_INPUT_ENTRY_DATE));
            dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_INPUT_ENGLISH_ADDRESS, paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_INPUT_ENGLISH_ADDRESS));
            dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_INPUT_POS_PHONE, paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_INPUT_POS_PHONE));
            dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_INPUT_DOC_LOCATION, paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_INPUT_DOC_LOCATION));
            dataHashMap.put(SCMInterfaceKey.CONTROL_SELECT_POS_STATUS, paramHashMap.get(SCMInterfaceKey.CONTROL_SELECT_POS_STATUS));
            dataHashMap.put(SCMInterfaceKey.CONTROL_SELECT_STK_STATUS, paramHashMap.get(SCMInterfaceKey.CONTROL_SELECT_STK_STATUS));
            dataHashMap.put(SCMInterfaceKey.CONTROL_SELECT_PAYMENT_STATUS, paramHashMap.get(SCMInterfaceKey.CONTROL_SELECT_PAYMENT_STATUS));
        }

        dataHashMap.put(SCMInterfaceKey.HASHMAP_GEN_DCM, RequestDao.getLookupFields(con, "GEN_DCM_STATUS", "DCM_STATUS_ID, DCM_STATUS_NAME"));
        dataHashMap.put(SCMInterfaceKey.HASHMAP_STK_STATUS, RequestDao.getLookupFields(con, "SCM_STK_STATUS where STK_STATUS_ID not in (1,3,6,5)", "STK_STATUS_ID, NAME "));
        dataHashMap.put(SCMInterfaceKey.HASHMAP_CAM_PAY_STATUS, RequestDao.getLookupFields(con, "CAM_PAYMENT_CAM_STATE", "ID, CAM_STATUS_FOR_PAYMENT"));

    }

    private static void loadDataPosEntryBack(Connection con, HashMap dataHashMap, HashMap paramHashMap, String generatedCode) throws Exception {


        generatedCode = generatedCode == null
                ? paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CODE) == null
                ? (String) paramHashMap.get(SCMInterfaceKey.POS_CODE)
                : (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CODE)
                : generatedCode;

        String channelId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL);
        String levelId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL);
        String paymentlevel = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL);
        dataHashMap.put(SCMInterfaceKey.POS_CODE, generatedCode);
        dataHashMap.put(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE, "\"\"");

        dataHashMap.put(SCMInterfaceKey.DCM_SAVE_POS_TYPE, SCMInterfaceKey.DCM_SAVE_POS_TYPE_NEW);
        loadVectors(con, dataHashMap);
        dataHashMap.put(SCMInterfaceKey.POS_CODE, generatedCode);
        dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL, levelId);
        dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL, channelId);
        dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL, paymentlevel);
        String strFlagSuperAdmin = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
        dataHashMap.put(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG, strFlagSuperAdmin);

        dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "");
        for (Object object : paramHashMap.keySet()) {
            if (!dataHashMap.containsKey(object)) {
                dataHashMap.put(object, paramHashMap.get(object));
            }
        }


    }

    private static String getObjFromDataHM(HashMap paramHashMap, String key) {
        return paramHashMap.get(key) == null ? "" : ((String) paramHashMap.get(key));
    }

    private static void loadDataBack3ak(Connection con, HashMap dataHashMap, HashMap paramHashMap) throws Exception {
        loadDataPosEntryBack(con, dataHashMap, paramHashMap, null);
        updateDocumentParamters(paramHashMap);
        dataHashMap.put("channel_for_pos", getObjFromDataHM(paramHashMap, "channel_for_pos"));
        dataHashMap.put("level_for_pos", getObjFromDataHM(paramHashMap, "level_for_pos"));
        dataHashMap.put("payment_for_pos", getObjFromDataHM(paramHashMap, "payment_for_pos"));
        dataHashMap.put("control_text_payment_level", getObjFromDataHM(paramHashMap, "control_text_payment_level"));
        dataHashMap.put("pos_region", getObjFromDataHM(paramHashMap, "pos_region"));
        dataHashMap.put("pos_gover", getObjFromDataHM(paramHashMap, "pos_gover"));
        dataHashMap.put("pos_city", getObjFromDataHM(paramHashMap, "pos_city"));
        dataHashMap.put("pos_district", getObjFromDataHM(paramHashMap, "pos_district"));
        dataHashMap.put("pos_area", getObjFromDataHM(paramHashMap, "pos_area"));
        dataHashMap.put("pos_owner_id_type", getObjFromDataHM(paramHashMap, "pos_owner_id_type"));
        dataHashMap.put("pos_manager_id_type", getObjFromDataHM(paramHashMap, "pos_manager_id_type"));
        dataHashMap.put("pos_proposed_doc", getObjFromDataHM(paramHashMap, "pos_proposed_doc"));
        dataHashMap.put("control_text_pos_iqrar_type", getObjFromDataHM(paramHashMap, "control_text_pos_iqrar_type"));
        dataHashMap.put("pos_code", getObjFromDataHM(paramHashMap, "pos_code"));
        dataHashMap.put("pos_name", getObjFromDataHM(paramHashMap, "pos_name"));
        dataHashMap.put("pos_arabic_name", getObjFromDataHM(paramHashMap, "pos_arabic_name"));
        dataHashMap.put("pos_address", getObjFromDataHM(paramHashMap, "pos_address"));
        dataHashMap.put("pos_arabic_address", getObjFromDataHM(paramHashMap, "pos_arabic_address"));
        dataHashMap.put("pos_rate", getObjFromDataHM(paramHashMap, "pos_rate"));
        dataHashMap.put("pos_phone", getObjFromDataHM(paramHashMap, "pos_phone"));
        dataHashMap.put("pos_owner_name", getObjFromDataHM(paramHashMap, "pos_owner_name"));
        dataHashMap.put("pos_owner_phone", getObjFromDataHM(paramHashMap, "pos_owner_phone"));
        dataHashMap.put("name1", getObjFromDataHM(paramHashMap, "name1"));
        dataHashMap.put("pos_onwer_id_number", getObjFromDataHM(paramHashMap, "pos_onwer_id_number"));
        dataHashMap.put("pos_manager_name", getObjFromDataHM(paramHashMap, "pos_manager_name"));
        dataHashMap.put("pos_manager_phone", getObjFromDataHM(paramHashMap, "pos_manager_phone"));
        dataHashMap.put("name2", getObjFromDataHM(paramHashMap, "name2"));
        dataHashMap.put("pos_manager_id_number", getObjFromDataHM(paramHashMap, "pos_manager_id_number"));
        dataHashMap.put("pos_demo", getObjFromDataHM(paramHashMap, "pos_demo"));
        dataHashMap.put("pos_email", getObjFromDataHM(paramHashMap, "pos_email"));
        dataHashMap.put("pos_doc_num", getObjFromDataHM(paramHashMap, "pos_doc_num"));
        dataHashMap.put("pos_doc_loc", getObjFromDataHM(paramHashMap, "pos_doc_loc"));
        dataHashMap.put("stk_dial", getObjFromDataHM(paramHashMap, "stk_dial"));
        dataHashMap.put("control_text_iqrar_date", getObjFromDataHM(paramHashMap, "control_text_iqrar_date"));
        dataHashMap.put("stk_verify", getObjFromDataHM(paramHashMap, "stk_verify"));
        dataHashMap.put("CONTROL_REGION_PARENT_ID", getObjFromDataHM(paramHashMap, "CONTROL_REGION_PARENT_ID"));
        dataHashMap.put("action", getObjFromDataHM(paramHashMap, "action"));
        dataHashMap.put("hidden_pos_super_admin_flag", getObjFromDataHM(paramHashMap, "hidden_pos_super_admin_flag"));
        dataHashMap.put("data_user_id", getObjFromDataHM(paramHashMap, "data_user_id"));
        dataHashMap.put("pos_channel", getObjFromDataHM(paramHashMap, "pos_channel"));
        dataHashMap.put("pos_level", getObjFromDataHM(paramHashMap, "pos_level"));

        String hiddenChildCode = (String) paramHashMap.get(SCMInterfaceKey.HIDDEN_IS_GENERATE_CHILD_CODE);
        dataHashMap.put(SCMInterfaceKey.IS_GENERATE_CHILD_CODE, getObjFromDataHM(paramHashMap, SCMInterfaceKey.HIDDEN_IS_GENERATE_CHILD_CODE));


        loadVectors(con, dataHashMap);

        String parentId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_REGION_PARENT_ID);

        HashMap<String, RegionModel> selectedRegonMdl = RequestDao.getChildRegionsListHM(con, parentId);
        dataHashMap.put(SCMInterfaceKey.CHILD_REGIONS_HM, selectedRegonMdl);
        dataHashMap.put(SCMInterfaceKey.CONTROL_REGION_PARENT_ID, paramHashMap.get(SCMInterfaceKey.CONTROL_REGION_PARENT_ID));


    }

    private static void loadDataBack3ak_for_edit(Connection con, HashMap dataHashMap, HashMap paramHashMap) throws Exception {
        loadDataPosEntryBack(con, dataHashMap, paramHashMap, null);
        PosModel posmodel = new PosModel();
        posmodel.setAreaId(Integer.parseInt(getObjFromDataHM(paramHashMap, "pos_area")));
        posmodel.setCityId(Integer.parseInt(getObjFromDataHM(paramHashMap, "pos_city")));
        posmodel.setDistrictId(Integer.parseInt(getObjFromDataHM(paramHashMap, "pos_district")));
        posmodel.setGovernateId(Integer.parseInt(getObjFromDataHM(paramHashMap, "pos_gover")));
        posmodel.setChannelId(Integer.parseInt(getObjFromDataHM(paramHashMap, "pos_channel")));
        posmodel.setLevelId(Integer.parseInt(getObjFromDataHM(paramHashMap, "pos_level")));
        posmodel.setPaymentLevelId(Integer.parseInt(getObjFromDataHM(paramHashMap, "control_text_payment_level")));
        POSDetailModel posdetailmodel = new POSDetailModel();
        posdetailmodel.setPosRegionID(Integer.parseInt(getObjFromDataHM(paramHashMap, "pos_region")));
        posdetailmodel.setPosOwnerIDTypeID(Integer.parseInt(getObjFromDataHM(paramHashMap, "pos_owner_id_type")));
        posdetailmodel.setPosManagerIDTypeID(Integer.parseInt(getObjFromDataHM(paramHashMap, "pos_manager_id_type")));
        posmodel.setDocNumber(getObjFromDataHM(paramHashMap, "pos_doc_num"));

        posdetailmodel.setPOSCode(getObjFromDataHM(paramHashMap, "pos_code"));
        posdetailmodel.setPosName(getObjFromDataHM(paramHashMap, "pos_name"));
        posdetailmodel.setPosArabicName(getObjFromDataHM(paramHashMap, "pos_arabic_name"));
        posdetailmodel.setPosAddress(getObjFromDataHM(paramHashMap, "pos_address"));
        posdetailmodel.setPosArabicAddress(getObjFromDataHM(paramHashMap, "pos_arabic_address"));
        posmodel.setRate(getObjFromDataHM(paramHashMap, "pos_rate"));
        Vector phones = new Vector();
        phones.add(getObjFromDataHM(paramHashMap, "phones__R0__C1"));
        posdetailmodel.setPosPhones(phones);
        posdetailmodel.setPosOwnerName(getObjFromDataHM(paramHashMap, "pos_owner_name"));
        Vector ownerPhones = new Vector();
        ownerPhones.add(getObjFromDataHM(paramHashMap, "owner_phones__R0__C1"));
        posdetailmodel.setPosOwnerPhones(ownerPhones);
        posdetailmodel.setPosOwnerBirthDate(getObjFromDataHM(paramHashMap, "name1"));
        posdetailmodel.setPosOwnerIDNumber(getObjFromDataHM(paramHashMap, "control_text_pos_owner_id_number_hidden"));
        posdetailmodel.setPosOwnerIDTypeID(Integer.parseInt(getObjFromDataHM(paramHashMap, "pos_owner_id_type")));
        posdetailmodel.setPosOwnerIDTypeName(RequestDao.getIdType(con, posdetailmodel.getPosOwnerIDTypeID()));
        posdetailmodel.setPosManagerName(getObjFromDataHM(paramHashMap, "pos_manager_name"));
        Vector managerPhones = new Vector();
        managerPhones.add(getObjFromDataHM(paramHashMap, "manager_phones__R0__C1"));
        posdetailmodel.setPosManagerPhones(managerPhones);
        posdetailmodel.setPosManagerBirthDate(getObjFromDataHM(paramHashMap, "name2"));
        posdetailmodel.setPosManagerIDNumber(getObjFromDataHM(paramHashMap, "pos_manager_id_number"));
        posdetailmodel.setPosManagerIDTypeID(Integer.parseInt(getObjFromDataHM(paramHashMap, "pos_manager_id_type")));
        posdetailmodel.setPosManagerIDTypeName(RequestDao.getIdType(con, posdetailmodel.getPosManagerIDTypeID()));
        posmodel.setDemoLineNum(getObjFromDataHM(paramHashMap, "pos_demo"));
        posdetailmodel.setPosEmail(getObjFromDataHM(paramHashMap, "pos_email"));
        posmodel.setDocument(getObjFromDataHM(paramHashMap, "pos_proposed_doc"));
        if (getObjFromDataHM(paramHashMap, "pos_proposed_doc").equals("--")) {
            posmodel.setDocument("0");
        }
        posmodel.setDocLocation(getObjFromDataHM(paramHashMap, "pos_doc_loc"));
        posmodel.setDocNumber(getObjFromDataHM(paramHashMap, "pos_doc_num"));
        posmodel.setStkDialNumber(getObjFromDataHM(paramHashMap, "control_text_stk_dial_hide"));



        posmodel.setLevelId(Integer.parseInt(getObjFromDataHM(paramHashMap, "pos_level")));

        posmodel.setChannelId(Integer.parseInt(getObjFromDataHM(paramHashMap, "pos_channel")));

        dataHashMap.put("action", getObjFromDataHM(paramHashMap, "action"));
        dataHashMap.put("hidden_pos_super_admin_flag", getObjFromDataHM(paramHashMap, "hidden_pos_super_admin_flag"));
        dataHashMap.put("data_user_id", getObjFromDataHM(paramHashMap, "data_user_id"));
        posmodel.setPosDetailModel(posdetailmodel);
        dataHashMap.put("similar_pos_list", posmodel);

        dataHashMap.put("CONTROL_REGION_PARENT_ID", posmodel.getPosDetailModel().getPosRegionID() + "-" + posmodel.getGovernateId() + "-" + posmodel.getCityId() + "-" + posmodel.getDistrictId() + "-" + posmodel.getAreaId());
        loadVectors(con, dataHashMap);

        String parentId = (String) paramHashMap.get(SCMInterfaceKey.CONTROL_REGION_PARENT_ID);

        HashMap<String, RegionModel> selectedRegonMdl = RequestDao.getChildRegionsListHM(con, parentId);
        dataHashMap.put(SCMInterfaceKey.CHILD_REGIONS_HM, selectedRegonMdl);
        dataHashMap.put(SCMInterfaceKey.CONTROL_REGION_PARENT_ID, paramHashMap.get(SCMInterfaceKey.CONTROL_REGION_PARENT_ID));

    }

    private static void loadVectors(Connection con, HashMap dataHashMap) throws Exception {
        Vector regions = new Vector();
        Vector IDTypeVector = new Vector();
        Vector legalFormVec = new Vector();
        Vector placeTypeVec = new Vector();

        GenericModel gm = new GenericModel();
        GenericModel placeTypeGM = new GenericModel();
        GenericModel IDTypeModel = new GenericModel();

        GenericModelDAO gmDAO = new GenericModelDAO();

        IDTypeModel = gmDAO.getColumns(con, "DCM_ID_TYPE");
        IDTypeVector = gmDAO.getModels(con, IDTypeModel);
        gm = gmDAO.getColumns(con, "DCM_LEGAL_FORM");
        legalFormVec = gmDAO.getModels(con, gm);
        placeTypeGM = gmDAO.getColumns(con, "DCM_POS_PLACE_TYPE");
        placeTypeVec = gmDAO.getModels(con, placeTypeGM);
        regions = RequestDao.getAllRegionDataList(con);
        dataHashMap.put(SCMInterfaceKey.CHANNEL_VECTOR, RequestDao.getChannelList(con));
        dataHashMap.put(SCMInterfaceKey.LEVEL_VECTOR, RequestDao.getLevelList(con));
        dataHashMap.put(SCMInterfaceKey.RATE_VECTOR, RequestDao.getPlaceList(con));
        dataHashMap.put(SCMInterfaceKey.DOC_VECTOR, RequestDao.getDocList(con));
        dataHashMap.put(SCMInterfaceKey.PAYMENT_LEVEL_VECTOR, RequestDao.getPaymentList(con));
        dataHashMap.put(SCMInterfaceKey.PAYMENT_METHOD_VECTOR, RequestDao.getPaymentMethodList(con));
        //todo
        
        dataHashMap.put(SCMInterfaceKey.IQRAR_TYPE_VECTOR, RequestDao.getIqrarTypeList(con));
        dataHashMap.put(SCMInterfaceKey.VECTOR_ID_TYPE, IDTypeVector);
        dataHashMap.put(SCMInterfaceKey.VECTOR_LEGAL_FORM, legalFormVec);
        dataHashMap.put(SCMInterfaceKey.VECTOR_PLACE_TYPE, placeTypeVec);
        dataHashMap.put(SCMInterfaceKey.VECTOR_REGIONS, regions);


    }

    public static void updateDocumentParamters(HashMap paramHashMap) {



        Object obj = null;
        obj = paramHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM);
        paramHashMap.remove(SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM);
        paramHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM, obj != null ? obj : "");
    }

    private static String getUserId(String userId, HashMap paramHashMap) {
        userId = userId == null ? (String) ((HttpServletRequest) paramHashMap.get(InterfaceKey.HASHMAP_KEY_REQUEST_FROM_SERVLET)).getSession(false).getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID) : userId;
        return userId;
    }

    private static Vector<String> getExcelPOSCodes(String filePath) throws Exception {
        FileInputStream tempIn = new FileInputStream(filePath);
        Workbook wb = org.apache.poi.ss.usermodel.WorkbookFactory.create(tempIn);
        Sheet sheet = wb.getSheetAt(0);
        Vector<String> posCodes = new Vector<String>();
        String firstCellValue = "";
        int lastRowNum = sheet.getLastRowNum() + 1;
        for (int currentRow = 1; currentRow < lastRowNum; currentRow++) {
            firstCellValue = DataImportEngine.readCell(sheet.getRow(currentRow).getCell(0));
            posCodes.add(firstCellValue);
        }
        tempIn.close();
        return posCodes;
    }

    private static String getTempFolderPath(HashMap paramHashMap) {
        HttpServletRequest request = (HttpServletRequest) paramHashMap.get(InterfaceKey.HASHMAP_KEY_REQUEST_FROM_SERVLET);
        return request.getRealPath("/") + "downloadItems";
    }

    private static void generateExcel(Connection con, HashMap paramHashMap, HashMap dataHashMap, Vector<String> posCode) throws SQLException {
        StringBuilder codes = new StringBuilder();
        for (String code : posCode) {
            codes.append(code);
            if (!code.equals(posCode.lastElement())) {
                codes.append("','");
            }
        }
        System.out.println("codes is " + codes);
        Vector<POSSearchExcelModel> dataVec = RequestDao.searchPosDataExcel(con, null, null, null, null, null, null, null, null, codes.toString(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,null,null,null,null,null,null);
        String excelLink = PoiWriteExcelFile.exportExcelSheetForPOSSearch(dataVec, getTempFolderPath(paramHashMap));
        dataHashMap.put(SCMInterfaceKey.EXPORT_EXCEL_LINK, excelLink);
        dataHashMap.put(SCMInterfaceKey.CONTROL_SELECT_PARAMETER, paramHashMap.get(SCMInterfaceKey.CONTROL_SELECT_PARAMETER));
    }
}
