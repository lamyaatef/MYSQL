package com.mobinil.sds.core.facade.handlers;

import java.io.File;
import java.sql.*;
import java.util.*;
import com.mobinil.sds.core.system.dataMigration.DAO.DataMigrationDao;
import com.mobinil.sds.core.system.dataMigration.model.fileModel;
import com.mobinil.sds.core.system.sa.importdata.dao.DataImportTableDefDAO;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.core.utilities.DMZIP.ZipThread;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.dm.DMInterfaceKey;
import com.mobinil.sds.web.interfaces.sa.AdministrationInterfaceKey;

public class DataMigrationHandler
{

    static final int CHOOSE_ZIP_FILE = 1;
    static final int ACTION_UPLOAD_ZIP_FILE = 2;
    static final int VIEW_FILES = 3;
    static final int DELETE_FILE = 4;
    static final int PAYMENT_CASH_REPORT_IMPORT = 5;
    static final int PAYMENT_CASH_REPORT_IMPORT_PROCESS = 6;
    static final int PAYMENT_MASTER_IMPORT = 7;
    static final int PAYMENT_MASTER_IMPORT_PROCESS = 8;
    static final int PAYMENT_VISA_IMPORT = 9;
    static final int PAYMENT_VISA_IMPORT_PROCESS = 10;
    static final int COMPLEMANTRY_DISTRIBUTION_IMPORT = 11;
    static final int COMPLEMANTRY_DISTRIBUTION_IMPORT_PROCESS = 12;
    static final int VIEW_CASH = 13;
    static final int DELETE_CASH = 14;
    static final int VIEW_VISA = 15;
    static final int DELETE_VISA = 16;
    static final int VIEW_DIST = 17;
    static final int DELETE_DIST = 18;
    static final int VIEW_MASTER = 19;
    static final int DELETE_MASTER = 20;
    static final int VIEW_ZIP_UPLOAD_STATSTICS = 21;
    static final int AUTH_CALL_IMPORT = 22;
    static final int AUTH_CALL_IMPORT_PROCESS = 23;
    static final int VIEW_AUTH_CALL = 24;
    static final int DELETE_AUTH_CALL = 25;
    static final int VIEW_AUTH_CALL_INVALID_DCM_CODE = 26;
    static final int POS_PAY_HISTORY_IMPORT = 27;
    static final int POS_PAY_HISTORY_IMPORT_PROCESS = 28;
    static final int POS_DEL_CHK_IMPORT = 29;
    static final int POS_DEL_CHK_IMPORT_PROCESS = 30;
    static final int POS_ELG_CHK_IMPORT = 31;
    static final int POS_ELG_CHK_IMPORT_PROCESS = 32;
    static final int VIEW_POS_HIS = 33;
    static final int DELETE_POS_HIS = 34;
    static final int VIEW_POS_DEL = 35;
    static final int DELETE_POS_DEL = 36;
    static final int VIEW_POS_ELG = 37;
    static final int DELETE_POS_ELG = 38;
    static final int ELG_CHK_SEARCH_IMPORT = 39;
    static final int ELG_CHK_SEARCH_IMPORT_PROCESS = 40;
    static final int VIEW_ELG_CHK_SEARCH_FILE = 41;
    static final int DELETE_ELG_CHK_SEARCH_FILE = 42;
    static final int VIEW_ELG_CHK_SEARCH_FILE_DATA = 43;
    static final int VIEW_ELG_CHK_SEARCH_FILE_INVALID_POS = 44;
    static final int DEL_SEARCH_IMPORT = 45;
    static final int DEL_SEARCH_IMPORT_PROCESS = 46;
    static final int VIEW_DEL_CHK_SEARCH_FILE_DATA = 47;
    static final int VIEW_DEL_CHK_SEARCH_FILE = 48;
    static final int VIEW_DEL_CHK_SEARCH_FILE_INVALID_POS = 49;
    static final int DELETE_DEL_CHK_SEARCH_FILE = 50;
    static final int HIS_SEARCH_IMPORT = 51;
    static final int HIS_SEARCH_IMPORT_PROCESS = 52;
    static final int VIEW_PAY_HIS_SEARCH_FILE = 53;
    static final int DELETE_PAY_HIS_SEARCH_FILE = 54;
    static final int VIEW_PAY_HIS_SEARCH_FILE_INVALID_POS = 55;
    static final int VIEW_PAY_HIS_SEARCH_FILE_DATA = 56;
    static final int VIEW_REVENUE_REPORT = 57;
    static final int EXPORT_REVENUE_REPORT = 58;
    static final int VIEW_CATEGORY_LIST = 59;
    static final int CREATE_NEW_CATEGORY = 60;
    static final int NOT_INTIALIZED_SERIALS_IMPORT = 61;
    static final int NOT_INTIALIZED_SERIALS_IMPORT_PROCESS = 62;
    static final int VIEW_INTIALIZED_SERIALS_FILE = 63;
    static final int DELETE_INTIALIZED_SERIALS_FILE = 64;
    static final int DELETE_CATEGORY = 65;
    static final int SAVE_NEW_CATEGORY = 66;
    static final int VIEW_CATEGORY_PRODUCT = 67;
    static final int DELETE_CATEGORY_PRODUCT = 68;
    static final int ASSIGN_CATEGORY_PRODUCT = 69;
    static final int SAVE_CATEGORY_PRODUCT = 70;

    static final int VIEW_GENERAL_REVENUE_REPORT = 71;
    static final int GENERATE_REVENUE_REPORT = 72;

    private static int getActionType(String action)
    {
        int actionType = 0;

        if (action.equals(DMInterfaceKey.ACTION_CHOOSE_ZIP_FILE))
            actionType = CHOOSE_ZIP_FILE;
        else if (action.equals(DMInterfaceKey.ACTION_UPLOAD_ZIP_FILE))
            actionType = ACTION_UPLOAD_ZIP_FILE;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_FILES))
            actionType = VIEW_FILES;

        else if (action.equals(DMInterfaceKey.ACTION_DELETE_FILE))
            actionType = DELETE_FILE;

        else if (action.equals(DMInterfaceKey.ACTION_PAYMENT_CASH_REPORT_IMPORT))
            actionType = PAYMENT_CASH_REPORT_IMPORT;

        else if (action.equals(DMInterfaceKey.ACTION_PAYMENT_CASH_REPORT_IMPORT_PROCESS))
            actionType = PAYMENT_CASH_REPORT_IMPORT_PROCESS;

        else if (action.equals(DMInterfaceKey.ACTION_PAYMENT_MASTER_IMPORT))
            actionType = PAYMENT_MASTER_IMPORT;

        else if (action.equals(DMInterfaceKey.ACTION_PAYMENT_MASTER_IMPORT_PROCESS))
            actionType = PAYMENT_MASTER_IMPORT_PROCESS;

        else if (action.equals(DMInterfaceKey.ACTION_PAYMENT_VISA_IMPORT))
            actionType = PAYMENT_VISA_IMPORT;

        else if (action.equals(DMInterfaceKey.ACTION_PAYMENT_VISA_IMPORT_PROCESS))
            actionType = PAYMENT_VISA_IMPORT_PROCESS;

        else if (action.equals(DMInterfaceKey.ACTION_COMPLEMANTRY_DISTRIBUTION_IMPORT))
            actionType = COMPLEMANTRY_DISTRIBUTION_IMPORT;

        else if (action.equals(DMInterfaceKey.ACTION_COMPLEMANTRY_DISTRIBUTION_IMPORT_PROCESS))
            actionType = COMPLEMANTRY_DISTRIBUTION_IMPORT_PROCESS;

        else if (action.equals(DMInterfaceKey.ACTION_AUTH_CALL_IMPORT))
            actionType = AUTH_CALL_IMPORT;

        else if (action.equals(DMInterfaceKey.ACTION_AUTH_CALL_IMPORT_PROCESS))
            actionType = AUTH_CALL_IMPORT_PROCESS;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_CASH))
            actionType = VIEW_CASH;

        else if (action.equals(DMInterfaceKey.ACTION_DELETE_CASH))
            actionType = DELETE_CASH;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_MASTER))
            actionType = VIEW_MASTER;

        else if (action.equals(DMInterfaceKey.ACTION_DELETE_MASTER))
            actionType = DELETE_MASTER;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_VISA))
            actionType = VIEW_VISA;

        else if (action.equals(DMInterfaceKey.ACTION_DELETE_VISA))
            actionType = DELETE_VISA;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_DIST))
            actionType = VIEW_DIST;

        else if (action.equals(DMInterfaceKey.ACTION_DELETE_DIST))
            actionType = DELETE_DIST;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_ZIP_UPLOAD_STATSTICS))
            actionType = VIEW_ZIP_UPLOAD_STATSTICS;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_AUTH_CALL))
            actionType = VIEW_AUTH_CALL;

        else if (action.equals(DMInterfaceKey.ACTION_DELETE_AUTH_CALL))
            actionType = DELETE_AUTH_CALL;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_AUTH_CALL_INVALID_DCM_CODE))
            actionType = VIEW_AUTH_CALL_INVALID_DCM_CODE;

        else if (action.equals(DMInterfaceKey.ACTION_POS_PAY_HISTORY_IMPORT))
            actionType = POS_PAY_HISTORY_IMPORT;

        else if (action.equals(DMInterfaceKey.ACTION_POS_PAY_HISTORY_IMPORT_PROCESS))
            actionType = POS_PAY_HISTORY_IMPORT_PROCESS;

        else if (action.equals(DMInterfaceKey.ACTION_POS_DEL_CHK_IMPORT))
            actionType = POS_DEL_CHK_IMPORT;

        else if (action.equals(DMInterfaceKey.ACTION_POS_DEL_CHK_IMPORT_PROCESS))
            actionType = POS_DEL_CHK_IMPORT_PROCESS;

        else if (action.equals(DMInterfaceKey.ACTION_POS_ELG_CHK_IMPORT))
            actionType = POS_ELG_CHK_IMPORT;

        else if (action.equals(DMInterfaceKey.ACTION_POS_ELG_CHK_IMPORT_PROCESS))
            actionType = POS_ELG_CHK_IMPORT_PROCESS;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_POS_HIS))
            actionType = VIEW_POS_HIS;

        else if (action.equals(DMInterfaceKey.ACTION_DELETE_POS_HIS))
            actionType = DELETE_POS_HIS;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_POS_DEL))
            actionType = VIEW_POS_DEL;

        else if (action.equals(DMInterfaceKey.ACTION_DELETE_POS_DEL))
            actionType = DELETE_POS_DEL;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_POS_ELG))
            actionType = VIEW_POS_ELG;

        else if (action.equals(DMInterfaceKey.ACTION_DELETE_POS_ELG))
            actionType = DELETE_POS_ELG;

        else if (action.equals(DMInterfaceKey.ACTION_ELG_CHK_SEARCH_IMPORT))
            actionType = ELG_CHK_SEARCH_IMPORT;

        else if (action.equals(DMInterfaceKey.ACTION_ELG_CHK_SEARCH_IMPORT_PROCESS))
            actionType = ELG_CHK_SEARCH_IMPORT_PROCESS;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_ELG_CHK_SEARCH_FILE))
            actionType = VIEW_ELG_CHK_SEARCH_FILE;

        else if (action.equals(DMInterfaceKey.ACTION_DELETE_ELG_CHK_SEARCH_FILE))
            actionType = DELETE_ELG_CHK_SEARCH_FILE;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_ELG_CHK_SEARCH_FILE_INVALID_POS))
            actionType = VIEW_ELG_CHK_SEARCH_FILE_INVALID_POS;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_ELG_CHK_SEARCH_FILE_DATA))
            actionType = VIEW_ELG_CHK_SEARCH_FILE_DATA;

        else if (action.equals(DMInterfaceKey.ACTION_DEL_SEARCH_IMPORT))
            actionType = DEL_SEARCH_IMPORT;

        else if (action.equals(DMInterfaceKey.ACTION_DEL_SEARCH_IMPORT_PROCESS))
            actionType = DEL_SEARCH_IMPORT_PROCESS;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_DEL_CHK_SEARCH_FILE_DATA))
            actionType = VIEW_DEL_CHK_SEARCH_FILE_DATA;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_DEL_CHK_SEARCH_FILE))
            actionType = VIEW_DEL_CHK_SEARCH_FILE;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_DEL_CHK_SEARCH_FILE_INVALID_POS))
            actionType = VIEW_DEL_CHK_SEARCH_FILE_INVALID_POS;

        else if (action.equals(DMInterfaceKey.ACTION_DELETE_DEL_CHK_SEARCH_FILE))
            actionType = DELETE_DEL_CHK_SEARCH_FILE;

        else if (action.equals(DMInterfaceKey.ACTION_HIS_SEARCH_IMPORT))
            actionType = HIS_SEARCH_IMPORT;

        else if (action.equals(DMInterfaceKey.ACTION_HIS_SEARCH_IMPORT_PROCESS))
            actionType = HIS_SEARCH_IMPORT_PROCESS;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_PAY_HIS_SEARCH_FILE))
            actionType = VIEW_PAY_HIS_SEARCH_FILE;

        else if (action.equals(DMInterfaceKey.ACTION_DELETE_PAY_HIS_SEARCH_FILE))
            actionType = DELETE_PAY_HIS_SEARCH_FILE;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_PAY_HIS_SEARCH_FILE_DATA))
            actionType = VIEW_PAY_HIS_SEARCH_FILE_DATA;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_PAY_HIS_SEARCH_FILE_INVALID_POS))
            actionType = VIEW_PAY_HIS_SEARCH_FILE_INVALID_POS;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_REVENUE_REPORT))
            actionType = VIEW_REVENUE_REPORT;

        else if (action.equals(DMInterfaceKey.ACTION_EXPORT_REVENUE_REPORT))
            actionType = EXPORT_REVENUE_REPORT;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_CATEGORY_LIST))
            actionType = VIEW_CATEGORY_LIST;

        else if (action.equals(DMInterfaceKey.ACTION_CREATE_NEW_CATEGORY))
            actionType = CREATE_NEW_CATEGORY;

        else if (action.equals(DMInterfaceKey.ACTION_NOT_INTIALIZED_SERIALS_IMPORT))
            actionType = NOT_INTIALIZED_SERIALS_IMPORT;

        else if (action.equals(DMInterfaceKey.ACTION_NOT_INTIALIZED_SERIALS_IMPORT_PROCESS))
            actionType = NOT_INTIALIZED_SERIALS_IMPORT_PROCESS;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_INTIALIZED_SERIALS_FILE))
            actionType = VIEW_INTIALIZED_SERIALS_FILE;

        else if (action.equals(DMInterfaceKey.ACTION_DELETE_INTIALIZED_SERIALS_FILE))
            actionType = DELETE_INTIALIZED_SERIALS_FILE;

        else if (action.equals(DMInterfaceKey.ACTION_DELETE_CATEGORY))
            actionType = DELETE_CATEGORY;

        else if (action.equals(DMInterfaceKey.ACTION_SAVE_NEW_CATEGORY))
            actionType = SAVE_NEW_CATEGORY;

        else if (action.equals(DMInterfaceKey.ACTION_VIEW_CATEGORY_PRODUCT))
            actionType = VIEW_CATEGORY_PRODUCT;

        else if (action.equals(DMInterfaceKey.ACTION_DELETE_CATEGORY_PRODUCT))
            actionType = DELETE_CATEGORY_PRODUCT;

        else if (action.equals(DMInterfaceKey.ACTION_ASSIGN_CATEGORY_PRODUCT))
            actionType = ASSIGN_CATEGORY_PRODUCT;

        else if (action.equals(DMInterfaceKey.ACTION_SAVE_CATEGORY_PRODUCT))
            actionType = SAVE_CATEGORY_PRODUCT;

        else if (action.equals(DMInterfaceKey.VIEW_GENERAL_REVENUE_REPORT))
            actionType = VIEW_GENERAL_REVENUE_REPORT;

        else if (action.equals(DMInterfaceKey.GENERATE_REVENUE_REPORT))
            actionType = GENERATE_REVENUE_REPORT;

        return actionType;
    }

    public HashMap handle(String action, HashMap paramHashMap)
    {
        int actionType = getActionType(action);
        HashMap dataHashMap = new HashMap();

        Connection con = null;
        try
        {
            con = Utility.getConnection();

            String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

            switch (actionType)
            {

                case VIEW_GENERAL_REVENUE_REPORT :

                    break;
                case GENERATE_REVENUE_REPORT :

                    String startDate = (String) paramHashMap.get(DMInterfaceKey.GENERAL_REVENUE_REPORT_START_DATE);
                    System.out.println("Start date: " + startDate);

                    String stemp = "'";
                    stemp += startDate.substring(3, 5);
                    stemp += "/";
                    stemp += startDate.substring(0, 2);
                    stemp += "/";
                    stemp += startDate.substring(6, 10);
                    stemp += "'";
                    System.out.println("stemp: " + stemp);

                    String endDate = (String) paramHashMap.get(DMInterfaceKey.GENERAL_REVENUE_REPORT_END_DATE);
                    System.out.println("End date: " + endDate);
                    String etemp = "'";
                    etemp += endDate.substring(3, 5);
                    etemp += "/";
                    etemp += endDate.substring(0, 2);
                    etemp += "/";
                    etemp += endDate.substring(6, 10);
                    etemp += "'";
                    System.out.println("etemp: " + etemp);
                    dataHashMap.put(DMInterfaceKey.GENERAL_REVENUE_REPORT_END_DATE, etemp);
                    dataHashMap.put(DMInterfaceKey.GENERAL_REVENUE_REPORT_START_DATE, stemp);

                    break;

                case CHOOSE_ZIP_FILE :
                {

                    Long attach_id = DataMigrationDao.getZipSeq(con);
                    dataHashMap.put(InterfaceKey.ATTACH_ID, ""
                            + attach_id.intValue());

                }
                    break;
                case ACTION_UPLOAD_ZIP_FILE :
                {

                    String file_name = (String) paramHashMap.get(InterfaceKey.CONTROL_INPUT_FILE);
                    System.out.println("file_name: " + file_name);
                    file_name = Utility.replaceAll(file_name, "#", "/");
                    String year = (String) paramHashMap.get(DMInterfaceKey.CONTROL_SELECT_UPLOAD_ZIP_YEAR);
                    String month = (String) paramHashMap.get(DMInterfaceKey.CONTROL_SELECT_UPLOAD_ZIP_MONTH);
                    File zip_file = new File(file_name);
                    Long attach_id = DataMigrationDao.getZipSeq(con);
                    String message = "";
                    if (DataMigrationDao.checkFile(year, month))
                    {

                        message = "Must delete year" + year + " and month "
                                + month;
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, message);

                    }

                    else
                    {

                        ZipThread zipTH = new ZipThread(year, month, "Processing", file_name, strUserID);
                        zipTH.start();
                        // zipTH.run();
                        // Long file_id = DataMigrationDao.insertNewFile(con,
                        // year, month, "Processing");
                        // HashMap Zip_file_HM = ReadZip.getDMZIPData(con,
                        // file_name, file_id);
                        // Long file_id = DataMigrationDao.insertNewFile(con,
                        // year, month, "aCTIVE");

                        dataHashMap.put(InterfaceKey.ATTACH_ID, ""
                                + attach_id.intValue());
                        System.out.println("aaaaaaaa: " + attach_id.intValue());
                    }
                }
                    break;

                case VIEW_FILES :
                {

                    Vector files = DataMigrationDao.getallfiles(con);

                    dataHashMap.put(DMInterfaceKey.VECTOR_FILES, files);
                    dataHashMap.put(DMInterfaceKey.HIDDEN_ACTION, action);
                }
                    break;

                case DELETE_FILE :
                {

                    String fieldId = (String) paramHashMap.get("fieldId");
                    System.out.println("FILE ID ISSSSSSSSSSSS" + fieldId);
                    DataMigrationDao.updateStatus(con, fieldId, "Deleted");
                    Vector files = DataMigrationDao.getallfiles(con);
                    dataHashMap.put(DMInterfaceKey.VECTOR_FILES, files);
                    dataHashMap.put(DMInterfaceKey.HIDDEN_ACTION, action);

                    // dataHashMap.put(DMInterfaceKey.HIDDEN_ACTION,action);
                }
                    break;

                case PAYMENT_CASH_REPORT_IMPORT :

                {
                    dataHashMap = new HashMap();

                    Vector tableDefVector = DataImportTableDefDAO.getTableDefByCategory("13");
                    dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR, tableDefVector);
                }

                    break;

                case PAYMENT_CASH_REPORT_IMPORT_PROCESS :
                    break;

                case PAYMENT_MASTER_IMPORT :
                    try
                    {
                        dataHashMap = new HashMap();

                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector tableDefVector = DataImportTableDefDAO.getTableDefByCategory("14");
                        dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR, tableDefVector);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case PAYMENT_MASTER_IMPORT_PROCESS :

                    try
                    {
                        dataHashMap = new HashMap();

                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    } catch (Exception objException)
                    {

                        objException.printStackTrace();

                    }

                    break;

                case PAYMENT_VISA_IMPORT :
                    try
                    {
                        dataHashMap = new HashMap();

                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector tableDefVector = DataImportTableDefDAO.getTableDefByCategory("15");
                        dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR, tableDefVector);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case PAYMENT_VISA_IMPORT_PROCESS :

                    try
                    {
                        dataHashMap = new HashMap();

                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    } catch (Exception objException)
                    {

                        objException.printStackTrace();

                    }

                    break;

                case COMPLEMANTRY_DISTRIBUTION_IMPORT :
                    try
                    {
                        dataHashMap = new HashMap();

                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector tableDefVector = DataImportTableDefDAO.getTableDefByCategory("16");
                        System.out.println("tableDefVector saize  issssssss: "
                                + tableDefVector.size());
                        dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR, tableDefVector);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case COMPLEMANTRY_DISTRIBUTION_IMPORT_PROCESS :
                    try
                    {
                        dataHashMap = new HashMap();

                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    } catch (Exception objException)
                    {

                        objException.printStackTrace();

                    }

                    break;

                case AUTH_CALL_IMPORT :
                    try
                    {
                        dataHashMap = new HashMap();

                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector tableDefVector = DataImportTableDefDAO.getTableDefByCategory("19");
                        System.out.println("tableDefVector size  issssssss: "
                                + tableDefVector.size());
                        dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR, tableDefVector);
                        String Year = null;
                        String Month = null;
                        String Status = null;

                        String fieldId = (String) paramHashMap.get("fieldId");

                        if (fieldId != null)
                        {

                            dataHashMap.put(DMInterfaceKey.HIDDEN_ACTION_2, "AppendFile");

                            Vector v = DataMigrationDao.GetYearMonthSpecificFile(con, fieldId);

                            for (int i = 0; i < v.size(); i++)
                            {
                                fileModel model = (fileModel) v.get(i);

                                Year = model.getYEAR();
                                Month = model.getMONTH();
                                Status = model.getSTATUS();
                                System.out.println("year in handler issssssss"
                                        + Year);
                                System.out.println("Month in handler issssssss"
                                        + Month);

                            }

                            dataHashMap.put(DMInterfaceKey.CONTROL_SELECTED_YEAR, Year);
                            dataHashMap.put(DMInterfaceKey.CONTROL_SELECTED_MONTH, Month);
                            dataHashMap.put(DMInterfaceKey.HIDDEN_FILE_ID, fieldId);

                        }

                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case AUTH_CALL_IMPORT_PROCESS :
                    try
                    {
                        dataHashMap = new HashMap();

                        System.out.println("here in th e import process ");
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

                    } catch (Exception objException)
                    {

                        objException.printStackTrace();

                    }

                    break;

                case VIEW_CASH :
                {

                    Vector files = DataMigrationDao.getallCashfiles(con);

                    dataHashMap.put(DMInterfaceKey.VECTOR_FILES, files);
                    dataHashMap.put(DMInterfaceKey.HIDDEN_ACTION, action);
                }
                    break;

                case DELETE_CASH :
                {

                    String fieldId = (String) paramHashMap.get("fieldId");
                    System.out.println("FILE ID ISSSSSSSSSSSS" + fieldId);
                    DataMigrationDao.updateCashStatus(con, fieldId, "Deleted");
                    Vector files = DataMigrationDao.getallCashfiles(con);
                    dataHashMap.put(DMInterfaceKey.VECTOR_FILES, files);
                    dataHashMap.put(DMInterfaceKey.HIDDEN_ACTION, action);
                }
                    break;

                case VIEW_MASTER :
                {

                    Vector files = DataMigrationDao.getallMasterfiles(con);

                    dataHashMap.put(DMInterfaceKey.VECTOR_FILES, files);
                    dataHashMap.put(DMInterfaceKey.HIDDEN_ACTION, action);

                }
                    break;

                case DELETE_MASTER :
                {

                    String fieldId = (String) paramHashMap.get("fieldId");
                    System.out.println("FILE ID ISSSSSSSSSSSS" + fieldId);
                    DataMigrationDao.updateMasterStatus(con, fieldId, "Deleted");
                    Vector files = DataMigrationDao.getallMasterfiles(con);
                    dataHashMap.put(DMInterfaceKey.VECTOR_FILES, files);
                    dataHashMap.put(DMInterfaceKey.HIDDEN_ACTION, action);
                }
                    break;

                case VIEW_AUTH_CALL :
                {

                    Vector files = DataMigrationDao.getallAuthCallfiles(con);

                    dataHashMap.put(DMInterfaceKey.VECTOR_FILES, files);
                    dataHashMap.put(DMInterfaceKey.HIDDEN_ACTION, action);

                }
                    break;

                case DELETE_AUTH_CALL :
                {

                    String fieldId = (String) paramHashMap.get("fieldId");
                    System.out.println("FILE ID ISSSSSSSSSSSS" + fieldId);
                    DataMigrationDao.updateAuthCallStatus(con, fieldId, "Deleted");
                    Vector files = DataMigrationDao.getallAuthCallfiles(con);
                    dataHashMap.put(DMInterfaceKey.VECTOR_FILES, files);
                    dataHashMap.put(DMInterfaceKey.HIDDEN_ACTION, action);
                }
                    break;

                case VIEW_VISA :
                {

                    Vector files = DataMigrationDao.getallVisafiles(con);

                    dataHashMap.put(DMInterfaceKey.VECTOR_FILES, files);
                    dataHashMap.put(DMInterfaceKey.HIDDEN_ACTION, action);
                }
                    break;

                case DELETE_VISA :
                {

                    String fieldId = (String) paramHashMap.get("fieldId");
                    System.out.println("FILE ID ISSSSSSSSSSSS" + fieldId);
                    DataMigrationDao.updateVisaStatus(con, fieldId, "Deleted");
                    Vector files = DataMigrationDao.getallVisafiles(con);
                    dataHashMap.put(DMInterfaceKey.VECTOR_FILES, files);
                    dataHashMap.put(DMInterfaceKey.HIDDEN_ACTION, action);

                }
                    break;

                case VIEW_DIST :
                {

                    Vector files = DataMigrationDao.getallDistfiles(con);
                    dataHashMap.put(DMInterfaceKey.VECTOR_FILES, files);
                    dataHashMap.put(DMInterfaceKey.HIDDEN_ACTION, action);
                }
                    break;

                case DELETE_DIST :
                {

                    String fieldId = (String) paramHashMap.get("fieldId");
                    System.out.println("FILE ID ISSSSSSSSSSSS" + fieldId);
                    DataMigrationDao.updateDisStatus(con, fieldId, "Deleted");
                    Vector files = DataMigrationDao.getallfiles(con);
                    dataHashMap.put(DMInterfaceKey.VECTOR_FILES, files);
                    dataHashMap.put(DMInterfaceKey.HIDDEN_ACTION, action);
                }
                    break;

                case VIEW_ZIP_UPLOAD_STATSTICS :

                {

                    String fieldId = (String) paramHashMap.get("fieldId");
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    Vector pos_code_vector = DataMigrationDao.getinvalidPosCode(fieldId);
                    Vector statistics = DataMigrationDao.getStaistics(fieldId);
                    dataHashMap.put(DMInterfaceKey.VECTOR_STATISTICS, statistics);
                    dataHashMap.put(DMInterfaceKey.VECTOR_POS_CODE, pos_code_vector);
                }

                    break;

                case VIEW_AUTH_CALL_INVALID_DCM_CODE :

                {

                    String fieldId = (String) paramHashMap.get("fieldId");
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    Vector dcm_code_vector = DataMigrationDao.getinvalidDcmCode(fieldId);
                    dataHashMap.put(DMInterfaceKey.VECTOR_DCM_CODE, dcm_code_vector);
                }

                    break;

                case POS_PAY_HISTORY_IMPORT :

                {
                    Vector tableDefVector = DataImportTableDefDAO.getTableDefByCategory("20");
                    System.out.println("tableDefVector size  issssssss: "
                            + tableDefVector.size());
                    dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR, tableDefVector);
                }

                    break;

                case POS_PAY_HISTORY_IMPORT_PROCESS :

                    break;

                case POS_DEL_CHK_IMPORT :
                    try
                    {

                        Vector tableDefVector = DataImportTableDefDAO.getTableDefByCategory("21");
                        System.out.println("tableDefVector size  issssssss: "
                                + tableDefVector.size());
                        dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR, tableDefVector);
                    } catch (Exception objExp)
                    {
                        objExp.printStackTrace();
                    }
                    break;

                case POS_DEL_CHK_IMPORT_PROCESS :

                    break;

                case POS_ELG_CHK_IMPORT :

                    Vector tableDefVector = DataImportTableDefDAO.getTableDefByCategory("22");
                    System.out.println("tableDefVector size  issssssss: "
                            + tableDefVector.size());
                    dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR, tableDefVector);

                    break;

                case POS_ELG_CHK_IMPORT_PROCESS :

                    break;

                case VIEW_POS_HIS :
                {

                    Vector files = DataMigrationDao.getallpayHisfiles(con);

                    dataHashMap.put(DMInterfaceKey.VECTOR_FILES, files);
                    dataHashMap.put(DMInterfaceKey.HIDDEN_ACTION, action);

                }
                    break;

                case DELETE_POS_HIS :

                {

                    String fieldId = (String) paramHashMap.get("fieldId");
                    System.out.println("FILE ID ISSSSSSSSSSSS" + fieldId);
                    DataMigrationDao.updatepos_his_file(con, fieldId, "Deleted");
                    Vector files = DataMigrationDao.getallpayHisfiles(con);
                    dataHashMap.put(DMInterfaceKey.VECTOR_FILES, files);
                    dataHashMap.put(DMInterfaceKey.HIDDEN_ACTION, action);
                }
                    break;

                case VIEW_POS_DEL :
                {

                    Vector files = DataMigrationDao.getallpos_Delfiles(con);

                    dataHashMap.put(DMInterfaceKey.VECTOR_FILES, files);
                    dataHashMap.put(DMInterfaceKey.HIDDEN_ACTION, action);
                }

                    break;

                case DELETE_POS_DEL :

                {

                    String fieldId = (String) paramHashMap.get("fieldId");
                    System.out.println("FILE ID ISSSSSSSSSSSS" + fieldId);
                    DataMigrationDao.updatepos_del_file(con, fieldId, "Deleted");
                    Vector files = DataMigrationDao.getallpos_Delfiles(con);
                    dataHashMap.put(DMInterfaceKey.VECTOR_FILES, files);
                    dataHashMap.put(DMInterfaceKey.HIDDEN_ACTION, action);
                }
                    break;

                case VIEW_POS_ELG :

                {
                    Vector files = DataMigrationDao.getallpos_Elgfiles(con);

                    dataHashMap.put(DMInterfaceKey.VECTOR_FILES, files);
                    dataHashMap.put(DMInterfaceKey.HIDDEN_ACTION, action);
                }
                    break;

                case DELETE_POS_ELG :
                {

                    String fieldId = (String) paramHashMap.get("fieldId");

                    DataMigrationDao.updatepos_elg_file(con, fieldId, "Deleted");
                    Vector files = DataMigrationDao.getallpos_Elgfiles(con);
                    dataHashMap.put(DMInterfaceKey.VECTOR_FILES, files);
                    dataHashMap.put(DMInterfaceKey.HIDDEN_ACTION, action);
                }
                    break;

                case ELG_CHK_SEARCH_IMPORT :
                    break;
                case ELG_CHK_SEARCH_IMPORT_PROCESS :

                    break;

                case VIEW_ELG_CHK_SEARCH_FILE :
                {
                    Vector files = DataMigrationDao.getallPosElgSearchfiles(con);
                    dataHashMap.put(DMInterfaceKey.VECTOR_SEARCH_FILES, files);
                }

                    break;

                case DELETE_ELG_CHK_SEARCH_FILE :
                {
                    String fieldId = (String) paramHashMap.get("fieldId");
                    System.out.println("FILE ID ISSSSSSSSSSSS" + fieldId);
                    // AuthResDAO.updateAuthResASearchStatusFile(con,fieldId,"Deleted");
                    DataMigrationDao.updateSearchpos_elg_file(con, fieldId, "Deleted");

                    Vector files = DataMigrationDao.getallPosElgSearchfiles(con);
                    dataHashMap.put(DMInterfaceKey.VECTOR_SEARCH_FILES, files);

                }
                    break;

                case VIEW_ELG_CHK_SEARCH_FILE_DATA :
                {
                    String fieldId = (String) paramHashMap.get("fieldId");
                    String month = (String) paramHashMap.get("month");
                    String year = (String) paramHashMap.get("year");
                    System.out.println("FILE ID ISSSSSSSSSSSS" + fieldId);
                    dataHashMap.put(DMInterfaceKey.CONTROL_HIDDEN_FILE_ID, fieldId);

                    dataHashMap.put(DMInterfaceKey.VECTOR_SEARCH_FILES_DATA, DataMigrationDao.getallPosElgSearchfileData(con, fieldId, month, year));
                }

                    break;

                case VIEW_ELG_CHK_SEARCH_FILE_INVALID_POS :
                {
                    String fieldId = (String) paramHashMap.get("fieldId");
                    System.out.println("FILE ID ISSSSSSSSSSSS" + fieldId);
                    dataHashMap.put(DMInterfaceKey.CONTROL_HIDDEN_FILE_ID, fieldId);

                    // dataHashMap.put(AuthResInterfaceKey.VECTOR_SEARCH_FILES_INVALID_SIM,
                    // AuthResDAO.getInvalidsimSerials(con, fieldId));
                    dataHashMap.put(DMInterfaceKey.VECTOR_SEARCH_FILES_INVALID_POS, DataMigrationDao.getInvalidposElgPOScode(con, fieldId));
                }

                    break;

                case DEL_SEARCH_IMPORT :
                    break;
                case DEL_SEARCH_IMPORT_PROCESS :

                    break;

                case VIEW_DEL_CHK_SEARCH_FILE :
                {
                    Vector files = DataMigrationDao.getallDelElgSearchfiles(con);
                    dataHashMap.put(DMInterfaceKey.VECTOR_SEARCH_FILES, files);
                }

                    break;

                case DELETE_DEL_CHK_SEARCH_FILE :
                {
                    String fieldId = (String) paramHashMap.get("fieldId");
                    System.out.println("FILE ID ISSSSSSSSSSSS" + fieldId);
                    // AuthResDAO.updateAuthResASearchStatusFile(con,fieldId,"Deleted");
                    DataMigrationDao.updateSearchpos_DEL_file(con, fieldId, "Deleted");

                    Vector files = DataMigrationDao.getallDelElgSearchfiles(con);
                    dataHashMap.put(DMInterfaceKey.VECTOR_SEARCH_FILES, files);
                }

                    break;

                case VIEW_DEL_CHK_SEARCH_FILE_DATA :
                {
                    String fieldId = (String) paramHashMap.get("fieldId");
                    String month = (String) paramHashMap.get("month");
                    String year = (String) paramHashMap.get("year");

                    System.out.println("FILE ID ISSSSSSSSSSSS" + fieldId);
                    dataHashMap.put(DMInterfaceKey.CONTROL_HIDDEN_FILE_ID, fieldId);

                    // Vector files =AuthResDAO.getallAuthResSearchfiles(con);
                    // dataHashMap.put(AuthResInterfaceKey.VECTOR_SEARCH_FILES_DATA,
                    // AuthResDAO.getallAuthResSearchfileData(con, fieldId));
                    dataHashMap.put(DMInterfaceKey.VECTOR_SEARCH_FILES_DATA, DataMigrationDao.getallPosDelSearchfileData(con, fieldId, month, year));
                }

                    break;

                case VIEW_DEL_CHK_SEARCH_FILE_INVALID_POS :
                {
                    String fieldId = (String) paramHashMap.get("fieldId");
                    dataHashMap.put(DMInterfaceKey.CONTROL_HIDDEN_FILE_ID, fieldId);
                    dataHashMap.put(DMInterfaceKey.VECTOR_SEARCH_FILES_INVALID_POS, DataMigrationDao.getInvalidposDELPOScode(con, fieldId));
                }
                    break;

                case HIS_SEARCH_IMPORT :
                    break;
                case HIS_SEARCH_IMPORT_PROCESS :

                    break;

                case VIEW_PAY_HIS_SEARCH_FILE :
                {
                    Vector files = DataMigrationDao.getallPOSHISSearchfiles(con);
                    dataHashMap.put(DMInterfaceKey.VECTOR_SEARCH_FILES, files);
                }

                    break;

                case DELETE_PAY_HIS_SEARCH_FILE :
                {
                    String fieldId = (String) paramHashMap.get("fieldId");

                    DataMigrationDao.updateSearchpos_HIS_file(con, fieldId, "Deleted");

                    Vector files = DataMigrationDao.getallPOSHISSearchfiles(con);
                    dataHashMap.put(DMInterfaceKey.VECTOR_SEARCH_FILES, files);
                }

                    break;

                case VIEW_PAY_HIS_SEARCH_FILE_DATA :
                {

                    String fieldId = (String) paramHashMap.get("fieldId");
                    String month = (String) paramHashMap.get("month");
                    String year = (String) paramHashMap.get("year");
                    System.out.println("FILE ID ISSSSSSSSSSSS" + fieldId);
                    System.out.println("month ssssssssssssss ISSSSSSSSSSSS"
                            + month);
                    System.out.println("yaear issssssssssssss ISSSSSSSSSSSS"
                            + year);

                    dataHashMap.put(DMInterfaceKey.CONTROL_HIDDEN_FILE_ID, fieldId);
                    dataHashMap.put(DMInterfaceKey.VECTOR_SEARCH_FILES_DATA, DataMigrationDao.getallPosHisSearchfileData(con, fieldId, month, year));
                }

                    break;

                case VIEW_PAY_HIS_SEARCH_FILE_INVALID_POS :
                {
                    String fieldId = (String) paramHashMap.get("fieldId");
                    dataHashMap.put(DMInterfaceKey.CONTROL_HIDDEN_FILE_ID, fieldId);
                    dataHashMap.put(DMInterfaceKey.VECTOR_SEARCH_FILES_INVALID_POS, DataMigrationDao.getInvalidposHISScode(con, fieldId));
                }
                    break;

                case VIEW_REVENUE_REPORT :
                    break;

                case EXPORT_REVENUE_REPORT :
                {
                    HashMap revenueReport = DataMigrationDao.getRevenueReport();

                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, revenueReport);
                }
                    break;

                case VIEW_CATEGORY_LIST :
                {
                    Vector categoryVec = DataMigrationDao.getAllCategories();
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, categoryVec);
                }
                    break;

                case DELETE_CATEGORY :
                {
                    String categoryId = (String) paramHashMap.get(DMInterfaceKey.INPUT_HIDDEN_CATEGORY_ID);
                    DataMigrationDao.deleteCategory(categoryId);
                    Vector categoryVec = DataMigrationDao.getAllCategories();
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, categoryVec);
                }
                    break;

                case CREATE_NEW_CATEGORY :

                    break;

                case SAVE_NEW_CATEGORY :
                {
                    String categoryName = (String) paramHashMap.get(DMInterfaceKey.INPUT_TEXT_CATEGORY_NAME);
                    DataMigrationDao.saveNewCategory(categoryName);
                    Vector categoryVec = DataMigrationDao.getAllCategories();
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, categoryVec);
                }
                    break;

                case VIEW_CATEGORY_PRODUCT :
                {
                    String categoryId = (String) paramHashMap.get(DMInterfaceKey.INPUT_HIDDEN_CATEGORY_ID);
                    dataHashMap.put(DMInterfaceKey.INPUT_HIDDEN_CATEGORY_ID, categoryId);
                    Vector categoryProduct = DataMigrationDao.getCategoryProduct(categoryId);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, categoryProduct);
                }
                    break;

                case DELETE_CATEGORY_PRODUCT :
                {
                    String categoryId = (String) paramHashMap.get(DMInterfaceKey.INPUT_HIDDEN_CATEGORY_ID);
                    dataHashMap.put(DMInterfaceKey.INPUT_HIDDEN_CATEGORY_ID, categoryId);
                    String productId = (String) paramHashMap.get(DMInterfaceKey.INPUT_HIDDEN_PRODUCT_ID);
                    DataMigrationDao.deleteCategoryProduct(categoryId, productId);
                    Vector categoryProduct = DataMigrationDao.getCategoryProduct(categoryId);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, categoryProduct);
                }
                    break;

                case ASSIGN_CATEGORY_PRODUCT :
                {
                    String categoryId = (String) paramHashMap.get(DMInterfaceKey.INPUT_HIDDEN_CATEGORY_ID);
                    dataHashMap.put(DMInterfaceKey.INPUT_HIDDEN_CATEGORY_ID, categoryId);
                    Vector productVec = DataMigrationDao.getAllProduct();
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, productVec);
                }
                    break;

                case SAVE_CATEGORY_PRODUCT :
                {
                    String categoryId = (String) paramHashMap.get(DMInterfaceKey.INPUT_HIDDEN_CATEGORY_ID);
                    dataHashMap.put(DMInterfaceKey.INPUT_HIDDEN_CATEGORY_ID, categoryId);
                    String productId = (String) paramHashMap.get(DMInterfaceKey.CONTROL_HIDDEN_PRODUCT_ID);
                    System.out.println("product id issssssssss " + productId);
                    DataMigrationDao.saveCategoryProduct(categoryId, productId);
                    Vector categoryProduct = DataMigrationDao.getCategoryProduct(categoryId);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, categoryProduct);
                }
                    break;

                case NOT_INTIALIZED_SERIALS_IMPORT :
                {
                    Vector problematicVec = DataMigrationDao.getAllProblematicLabel();
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, problematicVec);
                    Vector paymentLevelVec = DataMigrationDao.getAllPaymentLevel();
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, paymentLevelVec);
                }
                    break;

                case NOT_INTIALIZED_SERIALS_IMPORT_PROCESS :

                    break;

                case VIEW_INTIALIZED_SERIALS_FILE :
                {
                    Vector serialsFile = DataMigrationDao.getNotIntializedSerialsFile();
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, serialsFile);
                }

                    break;

                case DELETE_INTIALIZED_SERIALS_FILE :
                {
                    String fileId = (String) paramHashMap.get(DMInterfaceKey.INPUT_HIDDEN_NOT_INTIALIZED_FILE_ID);
                    DataMigrationDao.deleteNotInializedSerialsFile(fileId);
                    Vector serialsFile = DataMigrationDao.getNotIntializedSerialsFile();
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, serialsFile);
                }
                    break;

            }

            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

            if (con != null)
            {
                Utility.closeConnection(con);
            }
        } catch (Exception objExp)
        {
            objExp.printStackTrace();
        }
        return dataHashMap;

    }
}
