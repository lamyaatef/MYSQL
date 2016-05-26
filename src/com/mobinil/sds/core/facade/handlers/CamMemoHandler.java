package com.mobinil.sds.core.facade.handlers;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import com.mobinil.sds.core.system.cam.deduction.dao.DeductionDAO;
import com.mobinil.sds.core.system.cam.memo.dao.MemoDAO;
import com.mobinil.sds.core.system.cam.memo.model.MemoModel;
import com.mobinil.sds.core.system.cam.memo.model.MemoPaymentTypeSettingsModel;
import com.mobinil.sds.core.system.cam.payment.dao.PaymentScmStatusDao;
import com.mobinil.sds.core.system.cam.payment.model.PaymentTypeExcelModel;
import com.mobinil.sds.core.system.payment.dao.PaymentDAO;
import com.mobinil.sds.core.system.sa.importdata.dao.DataImportTableDefDAO;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.core.utilities.Wrapper.ImportExportWrapper;
import com.mobinil.sds.core.utilities.jaspertest.JRXMLDesignerManeger;
import com.mobinil.sds.core.utilities.jaspertest.JRXMLDesignerManegerForXLS;
import com.mobinil.sds.core.utilities.jaspertest.JasperManager;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.cam.MemoInterfaceKey;
import com.mobinil.sds.web.interfaces.cam.PaymentInterfaceKey;

public class CamMemoHandler {

    static final int ACTION_VIEW_MEMO = 1;
    static final int ACTION_CREATE_NEW_MEMO = 2;
    static final int ACTION_CREATE_SPECIFIC_MEMO = 3;
    static final int ACTION_CREATE_COLLECTION_MEMO = 4;
    static final int ACTION_SEARCH_MEMO = 5;
    static final int ACTION_SEARCH_SPECIFIC_MEMO = 6;
    static final int ACTION_SEARCH_COLLECTION_MEMO = 7;
    static final int ACTION_SEARCH_SPECIFIC_COLLECTION_MEMO = 8;
    static final int ACTION_DELETE_MEMO = 9;
    static final int ACTION_ISSUE_MEMO = 10;
    static final int ACTION_REMOVE_MEMBERS_OF_MEMO = 11;
    static final int ACTION_GET_MEMO_REPORT = 12;
    static final int ACTION_SEARCH_IN_MEMO = 13;
    static final int ACTION_EXPORT_MEMO = 14;
    static final int ACTION_EXPORT_MEMO_TO_EXCEL = 15;
    static final int ACTION_VIEW_READY_MEMOS = 16;
    static final int ACTION_VIEW_ISSUED_MEMOS = 17;
    static final int ACTION_VIEW_SALES_APPROVAL = 18;
    static final int ACTION_VIEW_CLOSED_PAYMENTS = 19;
    static final int ACTION_DELETE_READY_MEMO = 20;
    static final int ACTION_DELETE_ISSUED_MEMO = 21;
    static final int ACTION_ISSUE_READY_MEMO = 22;
    static final int ACTION_SALES_APPROVED = 23;
    static final int ACTION_VIEW_DCM_DETAILS = 24;
    static final int ACTION_SEARCH_DCM_DETAILS = 25;
    static final int ACTION_SEARCH_READY_MEMOS = 26;
    static final int ACTION_SEARCH_ISSUED_MEMOS = 27;
    static final int ACTION_SEARCH_SALES_APPROVAL = 28;
    static final int ACTION_FINANCE_ISSUE_MEMO = 29;
    static final int ACTION_SEARCH_CLOSED_PAYMENTS = 30;
    static final int ACTION_VIEW_PAYMENT_CONTENTS = 31;
    static final int ACTION_EXPORT_DCM_PAYMENT_MEMBERS_EXCEL = 32;
    static final int ACTION_GET_MEMO_REASONS = 33;
    static final int ACTION_UPDATE_MEMO_REASON = 34;
    static final int ACTION_ADD_MEMO_REASON = 35;
    static final int ACTION_DELETE_MEMO_REASON = 36;
    static final int ACTION_GENERATE_MEMO_REMOVAL_TEMPLATE = 37;
    static final int ACTION_IMPORT_MEMO_REMOVAL_FILE = 38;
    static final int ACTION_VIEW_IMPORT_MEMO_REMOVAL = 39;
    static final int ACTION_VIEW_GEN_DCM_DETAILS = 40;
    static final int ACTION_SEARCH_GEN_DCM_DETAILS = 41;
    static final int ACTION_VIEW_UPDATE_MEMO_REASON = 42;
    static final int ACTION_VIEW_ADD_MEMO_REASON = 43;
    static final int ACTION_EDIT_READY_MEMO = 44;
    static final int ACTION_VIEW_MEMO_HISTORY = 45;
    static final int ACTION_SEARCH_MEMO_HISTORY = 46;
    static final int ACTION_UPDATE_MEMO_HISTORY = 47;
    static final int ACTION_VIEW_UPDATE_MEMO_HISTORY = 48;
    static final int ACTION_IMPORT_MEMO_REMOVAL_FILE_PROCESS = 49;
    static final int ACTION_VIEW_MEMO_EXCEL_MANAGEMENT = 50;
    static final int ACTION_VIEW_MEMO_EXCEL_ATTRIBUTES = 51;
    static final int ACTION_UPDATE_MEMO_EXCEL_ATTRIBUTES = 52;
    static final int ACTION_INSERT_MEMO_EXCEL_ATTRIBUTES = 53;
    static final int ACTION_VIEW_INSERT_MEMO_EXCEL_ATTRIBUTES = 54;
    static final int ACTION_VIEW_UPDATE_MEMO_EXCEL_ATTRIBUTES = 55;
    static final int ACTION_VIEW_MEMO_SETTINGS = 56;
    static final int ACTION_VIEW_MEMO_MONITOR = 57;
    static final int ACTION_VIEW_EDIT_MEMO_SETTINGS = 58;
    static final int ACTION_UPDATE_MEMO_SETTINGS = 59;
    static final int ACTION_SEARCH_MEMO_MONITOR = 60;
    static final int ACTION_EXPORT_MEMO_MONITOR_TO_EXCEL = 61;
    static final int ACTION_VIEW_MEMO_PDF_MANAGEMENT = 62;
    static final int ACTION_VIEW_MEMO_PDF_ATTRIBUTES = 63;
    static final int ACTION_UPDATE_MEMO_PDF_ATTRIBUTES = 64;
    static final int ACTION_VIEW_INSERT_MEMO_PDF_ATTRIBUTES = 65;
    static final int ACTION_INSERT_MEMO_PDF_ATTRIBUTES = 66;
    static final int ACTION_VIEW_EDIT_READY_MEMO = 67;
    static final int ACTION_VIEW_MEMO_DELAY_REASONS = 68;
    static final int ACTION_UPDATE_MEMO_DELAY_REASON = 69;
    static final int ACTION_VIEW_UPDATE_MEMO_DELAY_REASON = 70;
    static final int ACTION_ADD_MEMO_DELAY_REASON = 71;
    static final int ACTION_VIEW_ADD_MEMO_DELAY_REASON = 72;
    static final int ACTION_DELETE_MEMO_DELAY_REASON = 73;
    static final int ACTION_VIEW_FROZEN_CLOSED_PAYMENTS = 74;
    static final int ACTION_SEARCH_FROZEN_CLOSED_PAYMENTS = 75;
    static final int ACTION_VIEW_FROZEN_PAYMENT_CONTENTS = 76;
    static final int ACTION_FREEEZE_PAYMENT = 77;
    static final int ACTION_FREEEZE_DCM = 78;
    static final int ACTION_EXPORT_DCM_DETAILS_EXCEL = 79;
    static final int ACTION_EXPORT_GEN_DCM_DETAILS_EXCEL = 80;
    static final int ACTION_GENERATE_SEARCH_DCM_DETAILS_LIST_TEMPLATE = 81;
    static final int ACTION_UPLOAD_SEARCH_DCM_DETAILS_LIST = 82;
    static final int ACTION_VIEW_UPLOAD_SEARCH_DCM_DETAILS_LIST = 83;
    static final int ACTION_VIEW_RECENTLY_UPLOADED_SEARCH_LIST = 84;
    static final int ACTION_EXPORT_ALL_DCM_DETAILS_EXCEL = 85;
    static final int ACTION_SET_NEGATIVE_TO_ZERO_IN_MEMO = 86;
    static final int ACTION_VIEW_MEMO_PAYMENT_NAMES_TYPES = 87;
    static final int ACTION_DELETE_MEMO_PAYMENT_NAMES_TYPES = 88;
    static final int ACTION_VIEW_PAYMENT_ELIGIBILITY_ADDITION = 89;
    static final int ACTION_ADD_PYAMENT_DCM_ELIGIBLE = 90;
    static final int ACTION_ADD_PYAMENT_DCM_ELIGIBLE_FROM_GEN = 91;
    static final int ACTION_VIEW_SQL_TEMPLATE_MANAGEMENT = 92;
    static final int ACTION_VIEW_EDIT_SQL_TEMPLATE_MANAGEMENT = 93;
    static final int ACTION_UPDATE_SQL_TEMPLATE_MANAGEMENT = 94;
    static final int ACTION_VIEW_SQL_TABLE_BKP_MANAGEMENT = 95;
    static final int ACTION_CREATE_SQL_TABLE_BKP_FILE = 96;
    static final int ACTION_view_ADD_SQL_TEMPLATE = 97;
    static final int ACTION_ADD_SQL_TEMPLATE = 98;
    static final int ACTION_VIEW_SALES_APPROVED_MEMOS = 99;
    static final int ACTION_SEARCH_SALES_APPROVED_MEMOS = 100;
    static final int ACTION_SALES_APPROVED_TO_AP = 101;
    static final int ACTION_SALES_APPROVED_TO_LOGESTIC = 102;
    static final int ACTION_SALES_APPROVED_TO_CREDIT_MODULE = 103;
    static final int ACTION_VIEW_SALES_APPROVED_TO_AP = 104;
    static final int ACTION_SEARCH_SALES_APPROVED_TO_AP = 105;
    static final int ACTION_VIEW_SALES_APPROVED_TO_LOGESTIC = 106;
    static final int ACTION_SEARCH_SALES_APPROVED_TO_LOGESTIC = 107;
    private final static boolean debugFlag = false;

    private static void debug(String msg) {
        if (debugFlag) {
            System.out.println(msg);
        }
    }

    public static HashMap handle(String action, HashMap paramHashMap, java.sql.Connection mConSDSConnection) {
        System.out.println("handler handle");
        int actionType = 0;
        HashMap dataHashMap = new HashMap(100);
        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        if (strUserID != null && strUserID.compareTo("") != 0) {
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
        }
        try {
            // Connection mConSDSConnection = Utility.getConnection();

            if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_MEMO) == 0) {
                actionType = ACTION_VIEW_MEMO;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_CREATE_NEW_MEMO) == 0) {
                actionType = ACTION_CREATE_NEW_MEMO;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_CREATE_SPECIFIC_MEMO) == 0) {
                actionType = ACTION_CREATE_SPECIFIC_MEMO;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_CREATE_COLLECTION_MEMO) == 0) {
                actionType = ACTION_CREATE_COLLECTION_MEMO;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_SEARCH_MEMO) == 0) {
                actionType = ACTION_SEARCH_MEMO;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_SEARCH_SPECIFIC_MEMO) == 0) {
                actionType = ACTION_SEARCH_SPECIFIC_MEMO;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_SEARCH_COLLECTION_MEMO) == 0) {
                actionType = ACTION_SEARCH_COLLECTION_MEMO;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_SEARCH_SPECIFIC_COLLECTION_MEMO) == 0) {
                actionType = ACTION_SEARCH_SPECIFIC_COLLECTION_MEMO;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_DELETE_MEMO) == 0) {
                actionType = ACTION_DELETE_MEMO;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_ISSUE_MEMO) == 0) {
                actionType = ACTION_ISSUE_MEMO;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_REMOVE_MEMBERS_OF_MEMO) == 0) {
                actionType = ACTION_REMOVE_MEMBERS_OF_MEMO;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_GET_MEMO_REPORT) == 0) {
                actionType = ACTION_GET_MEMO_REPORT;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_SEARCH_IN_MEMO) == 0) {
                actionType = ACTION_SEARCH_IN_MEMO;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_EXPORT_MEMO) == 0) {
                actionType = ACTION_EXPORT_MEMO;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_EXPORT_MEMO_TO_EXCEL) == 0) {
                actionType = ACTION_EXPORT_MEMO_TO_EXCEL;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_READY_MEMOS) == 0) {
                actionType = ACTION_VIEW_READY_MEMOS;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_ISSUED_MEMOS) == 0) {
                actionType = ACTION_VIEW_ISSUED_MEMOS;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_SALES_APPROVAL) == 0) {
                actionType = ACTION_VIEW_SALES_APPROVAL;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_CLOSED_PAYMENTS) == 0) {
                actionType = ACTION_VIEW_CLOSED_PAYMENTS;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_DELETE_READY_MEMO) == 0) {
                actionType = ACTION_DELETE_READY_MEMO;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_DELETE_ISSUED_MEMO) == 0) {
                actionType = ACTION_DELETE_ISSUED_MEMO;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_ISSUE_READY_MEMO) == 0) {
                actionType = ACTION_ISSUE_READY_MEMO;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_SALES_APPROVED) == 0) {
                actionType = ACTION_SALES_APPROVED;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_DCM_DETAILS) == 0) {
                actionType = ACTION_VIEW_DCM_DETAILS;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_SEARCH_DCM_DETAILS) == 0) {
                actionType = ACTION_SEARCH_DCM_DETAILS;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_SEARCH_READY_MEMOS) == 0) {
                actionType = ACTION_SEARCH_READY_MEMOS;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_SEARCH_ISSUED_MEMOS) == 0) {
                actionType = ACTION_SEARCH_ISSUED_MEMOS;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_SEARCH_SALES_APPROVAL) == 0) {
                actionType = ACTION_SEARCH_SALES_APPROVAL;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_FINANCE_ISSUE_MEMO) == 0) {
                actionType = ACTION_FINANCE_ISSUE_MEMO;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_SEARCH_CLOSED_PAYMENTS) == 0) {
                actionType = ACTION_SEARCH_CLOSED_PAYMENTS;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_PAYMENT_CONTENTS) == 0) {
                actionType = ACTION_VIEW_PAYMENT_CONTENTS;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_EXPORT_DCM_PAYMENT_MEMBERS_EXCEL) == 0) {
                actionType = ACTION_EXPORT_DCM_PAYMENT_MEMBERS_EXCEL;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_GENERATE_MEMO_REMOVAL_TEMPLATE) == 0) {
                actionType = ACTION_GENERATE_MEMO_REMOVAL_TEMPLATE;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_IMPORT_MEMO_REMOVAL_FILE) == 0) {
                actionType = ACTION_IMPORT_MEMO_REMOVAL_FILE;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_IMPORT_MEMO_REMOVAL) == 0) {
                actionType = ACTION_VIEW_IMPORT_MEMO_REMOVAL;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_GET_MEMO_REASONS) == 0) {
                actionType = ACTION_GET_MEMO_REASONS;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_UPDATE_MEMO_REASON) == 0) {
                actionType = ACTION_UPDATE_MEMO_REASON;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_ADD_MEMO_REASON) == 0) {
                actionType = ACTION_ADD_MEMO_REASON;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_DELETE_MEMO_REASON) == 0) {
                actionType = ACTION_DELETE_MEMO_REASON;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_GEN_DCM_DETAILS) == 0) {
                actionType = ACTION_VIEW_GEN_DCM_DETAILS;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_SEARCH_GEN_DCM_DETAILS) == 0) {
                actionType = ACTION_SEARCH_GEN_DCM_DETAILS;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_UPDATE_MEMO_REASON) == 0) {
                actionType = ACTION_VIEW_UPDATE_MEMO_REASON;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_ADD_MEMO_REASON) == 0) {
                actionType = ACTION_VIEW_ADD_MEMO_REASON;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_EDIT_READY_MEMO) == 0) {
                actionType = ACTION_EDIT_READY_MEMO;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_MEMO_HISTORY) == 0) {
                actionType = ACTION_VIEW_MEMO_HISTORY;

            } else if (action.compareTo(MemoInterfaceKey.ACTION_SEARCH_MEMO_HISTORY) == 0) {
                actionType = ACTION_SEARCH_MEMO_HISTORY;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_UPDATE_MEMO_HISTORY) == 0) {
                actionType = ACTION_UPDATE_MEMO_HISTORY;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_UPDATE_MEMO_HISTORY) == 0) {
                actionType = ACTION_VIEW_UPDATE_MEMO_HISTORY;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_IMPORT_MEMO_REMOVAL_FILE_PROCESS) == 0) {
                actionType = ACTION_IMPORT_MEMO_REMOVAL_FILE_PROCESS;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_MEMO_EXCEL_MANAGEMENT) == 0) {
                actionType = ACTION_VIEW_MEMO_EXCEL_MANAGEMENT;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_MEMO_EXCEL_ATTRIBUTES) == 0) {
                actionType = ACTION_VIEW_MEMO_EXCEL_ATTRIBUTES;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_UPDATE_MEMO_EXCEL_ATTRIBUTES) == 0) {
                actionType = ACTION_UPDATE_MEMO_EXCEL_ATTRIBUTES;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_INSERT_MEMO_EXCEL_ATTRIBUTES) == 0) {
                actionType = ACTION_INSERT_MEMO_EXCEL_ATTRIBUTES;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_INSERT_MEMO_EXCEL_ATTRIBUTES) == 0) {
                actionType = ACTION_VIEW_INSERT_MEMO_EXCEL_ATTRIBUTES;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_UPDATE_MEMO_EXCEL_ATTRIBUTES) == 0) {
                actionType = ACTION_VIEW_UPDATE_MEMO_EXCEL_ATTRIBUTES;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_MEMO_SETTINGS) == 0) {
                actionType = ACTION_VIEW_MEMO_SETTINGS;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_EDIT_MEMO_SETTINGS) == 0) {
                actionType = ACTION_VIEW_EDIT_MEMO_SETTINGS;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_UPDATE_MEMO_SETTINGS) == 0) {
                actionType = ACTION_UPDATE_MEMO_SETTINGS;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_MEMO_MONITOR) == 0) {
                actionType = ACTION_VIEW_MEMO_MONITOR;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_SEARCH_MEMO_MONITOR) == 0) {
                actionType = ACTION_SEARCH_MEMO_MONITOR;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_EXPORT_MEMO_MONITOR_TO_EXCEL) == 0) {
                actionType = ACTION_EXPORT_MEMO_MONITOR_TO_EXCEL;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_MEMO_PDF_MANAGEMENT) == 0) {
                actionType = ACTION_VIEW_MEMO_PDF_MANAGEMENT;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_MEMO_PDF_ATTRIBUTES) == 0) {
                actionType = ACTION_VIEW_MEMO_PDF_ATTRIBUTES;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_UPDATE_MEMO_PDF_ATTRIBUTES) == 0) {
                actionType = ACTION_UPDATE_MEMO_PDF_ATTRIBUTES;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_INSERT_MEMO_PDF_ATTRIBUTES) == 0) {
                actionType = ACTION_VIEW_INSERT_MEMO_PDF_ATTRIBUTES;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_INSERT_MEMO_PDF_ATTRIBUTES) == 0) {
                actionType = ACTION_INSERT_MEMO_PDF_ATTRIBUTES;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_EDIT_READY_MEMO) == 0) {
                actionType = ACTION_VIEW_EDIT_READY_MEMO;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_MEMO_DELAY_REASONS) == 0) {
                actionType = ACTION_VIEW_MEMO_DELAY_REASONS;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_UPDATE_MEMO_DELAY_REASON) == 0) {
                actionType = ACTION_UPDATE_MEMO_DELAY_REASON;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_UPDATE_MEMO_DELAY_REASON) == 0) {
                actionType = ACTION_VIEW_UPDATE_MEMO_DELAY_REASON;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_ADD_MEMO_DELAY_REASON) == 0) {
                actionType = ACTION_ADD_MEMO_DELAY_REASON;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_ADD_MEMO_DELAY_REASON) == 0) {
                actionType = ACTION_VIEW_ADD_MEMO_DELAY_REASON;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_DELETE_MEMO_DELAY_REASON) == 0) {
                actionType = ACTION_DELETE_MEMO_DELAY_REASON;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_FROZEN_CLOSED_PAYMENTS) == 0) {
                actionType = ACTION_VIEW_FROZEN_CLOSED_PAYMENTS;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_SEARCH_FROZEN_CLOSED_PAYMENTS) == 0) {
                actionType = ACTION_SEARCH_FROZEN_CLOSED_PAYMENTS;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_FROZEN_PAYMENT_CONTENTS) == 0) {
                actionType = ACTION_VIEW_FROZEN_PAYMENT_CONTENTS;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_FREEEZE_PAYMENT) == 0) {
                actionType = ACTION_FREEEZE_PAYMENT;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_FREEEZE_DCM) == 0) {
                actionType = ACTION_FREEEZE_DCM;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_EXPORT_DCM_DETAILS_EXCEL) == 0) {
                actionType = ACTION_EXPORT_DCM_DETAILS_EXCEL;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_EXPORT_GEN_DCM_DETAILS_EXCEL) == 0) {
                actionType = ACTION_EXPORT_GEN_DCM_DETAILS_EXCEL;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_GENERATE_SEARCH_DCM_DETAILS_LIST_TEMPLATE) == 0) {
                actionType = ACTION_GENERATE_SEARCH_DCM_DETAILS_LIST_TEMPLATE;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_UPLOAD_SEARCH_DCM_DETAILS_LIST) == 0) {
                actionType = ACTION_UPLOAD_SEARCH_DCM_DETAILS_LIST;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_UPLOAD_SEARCH_DCM_DETAILS_LIST) == 0) {
                actionType = ACTION_VIEW_UPLOAD_SEARCH_DCM_DETAILS_LIST;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_RECENTLY_UPLOADED_SEARCH_LIST) == 0) {
                actionType = ACTION_VIEW_RECENTLY_UPLOADED_SEARCH_LIST;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_EXPORT_ALL_DCM_DETAILS_EXCEL) == 0) {
                actionType = ACTION_EXPORT_ALL_DCM_DETAILS_EXCEL;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_SET_NEGATIVE_TO_ZERO_IN_MEMO) == 0) {
                actionType = ACTION_SET_NEGATIVE_TO_ZERO_IN_MEMO;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_MEMO_PAYMENT_NAMES_TYPES) == 0) {
                actionType = ACTION_VIEW_MEMO_PAYMENT_NAMES_TYPES;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_DELETE_MEMO_PAYMENT_NAMES_TYPES) == 0) {
                actionType = ACTION_DELETE_MEMO_PAYMENT_NAMES_TYPES;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_PAYMENT_ELIGIBILITY_ADDITION) == 0) {
                actionType = ACTION_VIEW_PAYMENT_ELIGIBILITY_ADDITION;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_ADD_PYAMENT_DCM_ELIGIBLE) == 0) {
                actionType = ACTION_ADD_PYAMENT_DCM_ELIGIBLE;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_ADD_PYAMENT_DCM_ELIGIBLE_FROM_GEN) == 0) {
                actionType = ACTION_ADD_PYAMENT_DCM_ELIGIBLE_FROM_GEN;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_SQL_TEMPLATE_MANAGEMENT) == 0) {
                actionType = ACTION_VIEW_SQL_TEMPLATE_MANAGEMENT;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_EDIT_SQL_TEMPLATE_MANAGEMENT) == 0) {
                actionType = ACTION_VIEW_EDIT_SQL_TEMPLATE_MANAGEMENT;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_UPDATE_SQL_TEMPLATE_MANAGEMENT) == 0) {
                actionType = ACTION_UPDATE_SQL_TEMPLATE_MANAGEMENT;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_SQL_TABLE_BKP_MANAGEMENT) == 0) {
                actionType = ACTION_VIEW_SQL_TABLE_BKP_MANAGEMENT;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_CREATE_SQL_TABLE_BKP_FILE) == 0) {
                actionType = ACTION_CREATE_SQL_TABLE_BKP_FILE;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_SALES_APPROVED_MEMOS) == 0) {
                actionType = ACTION_VIEW_SALES_APPROVED_MEMOS;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_SEARCH_SALES_APPROVED_MEMOS) == 0) {
                actionType = ACTION_SEARCH_SALES_APPROVED_MEMOS;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_SALES_APPROVED_TO_AP) == 0) {
                actionType = ACTION_SALES_APPROVED_TO_AP;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_SALES_APPROVED_TO_LOGESTIC) == 0) {
                actionType = ACTION_SALES_APPROVED_TO_LOGESTIC;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_SALES_APPROVED_TO_CREDIT_MODULE) == 0) {
                actionType = ACTION_SALES_APPROVED_TO_CREDIT_MODULE;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_SALES_APPROVED_TO_AP) == 0) {
                actionType = ACTION_VIEW_SALES_APPROVED_TO_AP;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_SEARCH_SALES_APPROVED_TO_AP) == 0) {
                actionType = ACTION_SEARCH_SALES_APPROVED_TO_AP;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_VIEW_SALES_APPROVED_TO_LOGESTIC) == 0) {
                actionType = ACTION_VIEW_SALES_APPROVED_TO_LOGESTIC;
            } else if (action.compareTo(MemoInterfaceKey.ACTION_SEARCH_SALES_APPROVED_TO_LOGESTIC) == 0) {
                actionType = ACTION_SEARCH_SALES_APPROVED_TO_LOGESTIC;
            }
            /*
             * else
             * if(action.compareTo(MemoInterfaceKey.ACTION_view_ADD_SQL_TEMPLATE)==0){
             * actionType=ACTION_view_ADD_SQL_TEMPLATE;
             *
             * }else
             * if(action.compareTo(MemoInterfaceKey.ACTION_ADD_SQL_TEMPLATE)==0){
             * actionType=ACTION_ADD_SQL_TEMPLATE; }
             */
            switch (actionType) {
                case ACTION_VIEW_MEMO: {
                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                        String memoId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_ID);
                        String memoName = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_NAME);
                        String stateName = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_STATE_NAME);
                        Vector members = MemoDAO.viewMemo(mConSDSConnection, memoId);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);

                        dataHashMap.put(MemoInterfaceKey.CONTROL_MEMO_ID, memoId);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_MEMO_NAME, memoName);
                        dataHashMap.put(MemoInterfaceKey.HIDDEN_MEMO_STATE_NAME, stateName);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_MEMBERS, members);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_SET_NEGATIVE_TO_ZERO_IN_MEMO: {
                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);





                        String memoId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_ID);
                        String memoName = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_NAME);
                        String stateName = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_STATE_NAME);
                        Vector members = MemoDAO.viewMemo(mConSDSConnection, memoId);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);


                        //set the negative commission values to zero:

                        MemoDAO.setNegativeCommissionToZero(mConSDSConnection, Integer.parseInt(memoId));

                        dataHashMap.put(MemoInterfaceKey.CONTROL_MEMO_ID, memoId);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_MEMO_NAME, memoName);
                        dataHashMap.put(MemoInterfaceKey.HIDDEN_MEMO_STATE_NAME, stateName);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_MEMBERS, members);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;

                case ACTION_VIEW_CLOSED_PAYMENTS: {
                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                        Vector closedPayments = MemoDAO.viewClosedPayments(mConSDSConnection, "", "", "", "", "", "", "", "", "");


                        dataHashMap.put(MemoInterfaceKey.VECTOR_CLOSED_PAYMENTS, closedPayments);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_VIEW_FROZEN_CLOSED_PAYMENTS: {
                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                        Vector closedPayments = MemoDAO.viewClosedPayments(mConSDSConnection, "", "", "", "", "", "", "", "", "");


                        dataHashMap.put(MemoInterfaceKey.VECTOR_CLOSED_PAYMENTS, closedPayments);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_SEARCH_CLOSED_PAYMENTS: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String paymentName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_PAYMENT_NAME);
                    String startDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_START_DATE);
                    String endDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_END_DATE);
                    String dcmName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME);
                    String dcmCode = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE);
                    String paymentTypeId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_TYPE);
                    String addStartDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_ADDING_TO_MODULE_START_DATE);
                    String addEndDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_ADDING_TO_MODULE__END_DATE);

                    Vector closedPayments = MemoDAO.viewClosedPayments(mConSDSConnection, paymentName, dcmName, dcmCode, startDate, endDate, paymentTypeId, addStartDate, addEndDate, "");

                    dataHashMap.put(MemoInterfaceKey.VECTOR_CLOSED_PAYMENTS, closedPayments);

                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);

                }
                break;
                case ACTION_SEARCH_FROZEN_CLOSED_PAYMENTS: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String paymentName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_PAYMENT_NAME);
                    debug(paymentName);
                    String startDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_START_DATE);
                    debug(startDate);
                    String endDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_END_DATE);
                    debug(endDate);
                    String dcmName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME);
                    debug(dcmName);
                    String dcmCode = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE);
                    debug(dcmCode);
                    String frozenStatus = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_STATUS);
                    debug(frozenStatus);
                    String paymentTypeId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_TYPE);
                    String addStartDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_ADDING_TO_MODULE_START_DATE);
                    String addEndDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_ADDING_TO_MODULE__END_DATE);


                    Vector closedPayments = MemoDAO.viewClosedPayments(mConSDSConnection, paymentName, dcmName, dcmCode, startDate, endDate, paymentTypeId, addStartDate, addEndDate, frozenStatus);

                    dataHashMap.put(MemoInterfaceKey.VECTOR_CLOSED_PAYMENTS, closedPayments);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);

                }
                break;
                case ACTION_VIEW_PAYMENT_CONTENTS: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String paymentId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_ID);
                    String paymentName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAYMENT_NAME);
                    Vector payment = MemoDAO.viewPayment(mConSDSConnection, paymentId);

                    dataHashMap.put(MemoInterfaceKey.VECTOR_PAYMENT_CONTENTS, payment);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_HIDDEN_PAYMENT_NAME, paymentName);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_PAYMENT_ID, paymentId);

                }
                break;
                case ACTION_VIEW_FROZEN_PAYMENT_CONTENTS: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String paymentId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_ID);
                    String paymentName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAYMENT_NAME);
                    Vector payment = MemoDAO.viewPayment(mConSDSConnection, paymentId);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_PAYMENT_CONTENTS, payment);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_HIDDEN_PAYMENT_NAME, paymentName);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_PAYMENT_ID, paymentId);

                }
                break;
                case ACTION_FREEEZE_PAYMENT: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String paymentId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_ID);
                    debug("paymentId: " + paymentId);
                    String frozenStatusId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_FROZEN_STATUS_ID);
                    debug("frozenStatus: " + frozenStatusId);
                    MemoDAO.updatePaymentFrozenStatus(mConSDSConnection, paymentId, frozenStatusId);

                    Vector closedPayments = MemoDAO.viewClosedPayments(mConSDSConnection, "", "", "", "", "", "", "", "", "");

                    dataHashMap.put(MemoInterfaceKey.VECTOR_CLOSED_PAYMENTS, closedPayments);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                }
                break;
                case ACTION_FREEEZE_DCM: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String dcmId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_SCM_PAYMENT_ID);
                    debug("dcmId: " + dcmId);
                    String paymentId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_ID);
                    System.out.println("paymentId; " + paymentId);
                    String paymentName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAYMENT_NAME);
                    String frozenStatusId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_FROZEN_STATUS_ID);
                    debug("frozenStatus: " + frozenStatusId);
                    MemoDAO.updateDcmFrozenStatus(mConSDSConnection, paymentId, dcmId, frozenStatusId);

                    Vector payment = MemoDAO.viewPayment(mConSDSConnection, paymentId);

                    dataHashMap.put(MemoInterfaceKey.VECTOR_PAYMENT_CONTENTS, payment);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_HIDDEN_PAYMENT_NAME, paymentName);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_PAYMENT_ID, paymentId);
                }
                break;
                case ACTION_EXPORT_DCM_PAYMENT_MEMBERS_EXCEL: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String paymentId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_ID);
                    String fromFrozenPayments = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_FROZEN_STATUS_ID);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_PAYMENT_ID, paymentId);
                    dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_FROZEN_STATUS_ID, fromFrozenPayments);
                }
                break;
                case ACTION_GENERATE_MEMO_REMOVAL_TEMPLATE: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;
                case ACTION_VIEW_IMPORT_MEMO_REMOVAL: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    Vector memos = MemoDAO.getMemos(mConSDSConnection);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);


                    Vector tableDefVector = DataImportTableDefDAO.getTableDefByCategory("17");
                    dataHashMap.put(MemoInterfaceKey.TABLE_DEF_VECTOR, tableDefVector);
                    System.out.println("step one");


                }
                break;
                case ACTION_IMPORT_MEMO_REMOVAL_FILE: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

                    String memoId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_HIDDEN_MEMO_ID);
                    //debug("memo_id is: "+memo_id);


                    /*
                     * String file_name = (String)
                     * paramHashMap.get(InterfaceKey.CONTROL_INPUT_FILE);
                     * debug("file_name: "+file_name); file_name =
                     * Utility.replaceAll(file_name, "#", "/");
                     *
                     * HashMap imported_dcms =
                     * ImportExportWrapper.importExcelFile(file_name, 0, 0, 0,
                     * 0);
                     *
                     *
                     * if(imported_dcms != null && imported_dcms.size()>0) {
                     * Iterator keys_itr = imported_dcms.keySet().iterator();
                     *
                     * Vector dcmCode=null; Vector reason=null;
                     *
                     * while(keys_itr.hasNext()) { String key_str =
                     * (String)keys_itr.next(); debug("key_str: "+ key_str );
                     *
                     *
                     * if(key_str.equals("DCM CODE")) {
                     * dcmCode=(Vector)imported_dcms.get(key_str); }else
                     * if(key_str.equals("Reason")) {
                     *
                     * reason=(Vector)imported_dcms.get(key_str); } * }
                     * if(dcmCode.size()!=0||dcmCode!=null){ for(int
                     * i=1;i<dcmCode.size();i++) {
                     * if(dcmCode.get(i).toString().compareTo("")!=0&&reason.get(i).toString().compareTo("")!=0){
                     * MemoRemovalImportModel m=new
                     * MemoRemovalImportModel((String)dcmCode.get(i),(String)reason.get(i));
                     * debug("dcm code" + i +" "+(String) dcmCode.get(i));
                     *
                     * debug("reason"+i+" "+(String)reason.get(i));
                     *
                     *
                     * //calling dao try{
                     * MemoDAO.uploadMemoRemoval(mConSDSConnection, m,
                     * memo_id,file_name , strUserID); }catch(Exception e) {
                     * e.printStackTrace(); } }
                     *
                     *
                     * }
                     * }
                     *
                     * }
                     */

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);


                    Vector memos = MemoDAO.getMemos(mConSDSConnection);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);


                    System.out.println("step two");

                }
                break;
                case ACTION_IMPORT_MEMO_REMOVAL_FILE_PROCESS: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    int id = MemoDAO.getMemoUploadFileId(mConSDSConnection);
                    //int id=PaymentScmStatusDao.getNextImportPaymentFileID(mConSDSConnection);
                    dataHashMap.put(InterfaceKey.ATTACH_ID, "" + id);

                    Vector memos = MemoDAO.getMemos(mConSDSConnection);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);

                    System.out.println("step three");
                }
                break;
                case ACTION_CREATE_NEW_MEMO: {
                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                        Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                        Vector memoStates = MemoDAO.getMemoStates(mConSDSConnection);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                        Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                        Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_STATES, memoStates);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);


                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_CREATE_SPECIFIC_MEMO: {
                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                        String memoDescription = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_DESCRIPTION);
                        String channelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID);
                        String paymentTypeId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID);

                        // check if the system have payments does not in any memo
                        int payments = MemoDAO.checkPaymentAvailForSpecific(mConSDSConnection, paymentTypeId, channelId);

                        if (payments != 0) {
                            MemoDAO.createSpeificMemo(mConSDSConnection, strUserID, memoDescription, paymentTypeId, channelId);
                        }



                        //Vector memos=MemoDAO.searchSpecificCollectionMemo(mConSDSConnection,-1,"","1","","","","");
                        //dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS,memos);

                        Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                        Vector memoStates = MemoDAO.getMemoStates(mConSDSConnection);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                        Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                        Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);

                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_STATES, memoStates);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_CHECK_MEMO_CREATION, String.valueOf(payments));


                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_CREATE_COLLECTION_MEMO: {
                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                        // String memo_name=(String)paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_NAME);

                        String memoDescription = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_DESCRIPTION);
                        String channelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID);
                        String paymentMethodId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_METHOD_ID);

                        int payments = MemoDAO.checkPaymentAvailForCollective(mConSDSConnection, paymentMethodId, channelId);
                        //calling the dao
                        if (payments != 0) {
                            MemoDAO.createCollectionMemo(mConSDSConnection, strUserID, memoDescription, channelId, paymentMethodId);
                        }


                        //Vector memos=MemoDAO.searchSpecificCollectionMemo(mConSDSConnection,-1,"","1","","","","");
                        //dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS,memos);

                        Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                        Vector memoStates = MemoDAO.getMemoStates(mConSDSConnection);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                        Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                        Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);

                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_STATES, memoStates);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_CHECK_MEMO_CREATION, String.valueOf(payments));

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_SEARCH_MEMO: {
                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                        Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                        Vector memoStates = MemoDAO.getMemoStates(mConSDSConnection);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                        Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                        Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                        Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_STATES, memoStates);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);


                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_VIEW_MEMO_HISTORY: {
                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                        Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                        Vector memoStates = MemoDAO.getMemoStates(mConSDSConnection);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                        Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                        Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_STATES, memoStates);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);


                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;

                case ACTION_SEARCH_MEMO_HISTORY: {

                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);



                        String memoName = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_NAME);
                        String stateId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_STATE_ID);
                        String specificChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID);
                        String collectionChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_COLLECTION_CHANNEL_ID);
                        String subChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SUB_CHANNEL_ID);

                        String paymentTypeId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID);
                        String memoTypeId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_TYPE_ID);
                        String fromDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_START_DATE);
                        String toDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_END_DATE);

                        //calling the dao
                        if (Integer.parseInt(memoTypeId) == 1) {
                            Vector memos = MemoDAO.searchMemoHistory(mConSDSConnection, 1, memoName, stateId, specificChannelId, subChannelId, paymentTypeId, "-1", fromDate, toDate);
                            dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);

                        } else if (Integer.parseInt(memoTypeId) == 2) {
                            Vector memos = MemoDAO.searchMemoHistory(mConSDSConnection, 2, memoName, stateId, specificChannelId, subChannelId, paymentTypeId, "-1", fromDate, toDate);
                            dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);

                        } else {
                            Vector memos = MemoDAO.searchMemoHistory(mConSDSConnection, -1, memoName, stateId, specificChannelId, subChannelId, paymentTypeId, "-1", fromDate, toDate);
                            dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);

                        }
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                        //memo settings data



                        //for the search part of the page
                        Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                        Vector memoStates = MemoDAO.getMemoStates(mConSDSConnection);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                        Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                        Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_STATES, memoStates);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);
                        // the search criteria 
                        dataHashMap.put(MemoInterfaceKey.CONTROL_MEMO_NAME, memoName);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_SELECT_STATE_ID, stateId);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID, specificChannelId);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_SELECT_COLLECTION_CHANNEL_ID, collectionChannelId);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID, paymentTypeId);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_VIEW_UPDATE_MEMO_HISTORY: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    String memoId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_ID);
                    Vector memo = MemoDAO.getMemoHistoryById(mConSDSConnection, memoId);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memo);

                    //payment type settings for this memo
                    int[] memoSettings = new int[2];
                    memoSettings = MemoDAO.getMemoSettingsData(mConSDSConnection, memoId);
                    int[] data = new int[3];
                    if (memoSettings[1] == 1) {
                        dataHashMap.put(MemoInterfaceKey.MEMO_PERIOD_ID, "1");
                        data = MemoDAO.getMemoWeeksData(mConSDSConnection, memoId, String.valueOf(memoSettings[0]), String.valueOf(memoSettings[1]));
                        dataHashMap.put(MemoInterfaceKey.MEMO_SETTINGS_ARRAY_DATA, data);
                    } else if (memoSettings[1] == 2) {
                        dataHashMap.put(MemoInterfaceKey.MEMO_PERIOD_ID, "2");
                        data = MemoDAO.getMemoMonthsData(mConSDSConnection, memoId, String.valueOf(memoSettings[0]), String.valueOf(memoSettings[1]));
                        dataHashMap.put(MemoInterfaceKey.MEMO_SETTINGS_ARRAY_DATA, data);
                    } else if (memoSettings[1] == 3) {
                        dataHashMap.put(MemoInterfaceKey.MEMO_PERIOD_ID, "3");
                        String[] qrtr = new String[3];
                        qrtr = MemoDAO.getMemoQrtrData(mConSDSConnection, memoId, String.valueOf(memoSettings[0]), String.valueOf(memoSettings[1]));
                        dataHashMap.put(MemoInterfaceKey.MEMO_SETTINGS_ARRAY_DATA, qrtr);
                    }
                    Vector delayReasons = MemoDAO.getMemoDelayReasons(mConSDSConnection);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_REASONS, delayReasons);

                    Vector mempDelayReasons = MemoDAO.getMemoDelayReasons(mConSDSConnection, memoId);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_EVERY_MEMO_REASONS, mempDelayReasons);


                }
                break;
                case ACTION_UPDATE_MEMO_HISTORY: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    System.out.println("helllllllllllllllllllllllllllllllllllllllllllllllllllo");

                    String memoId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_ID);
                    String sendForValidationDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SEND_FOR_VALIDATION_DATE);
                    debug("sendForValidationDate: " + sendForValidationDate);
                    String approvalDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_APPROVAL_DATE);
                    debug("approvalDate: " + approvalDate);
                    String salesManagerApprovalDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SALES_MANAGER_APPROVAL_DATE);
                    debug("salesManagerApprovalDate: " + salesManagerApprovalDate);
                    String salesBackOfficeApprovalDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SALES_BACK_OFFICE_APPROVAL_DATE);
                    debug("salesBackOfficeApprovalDate: " + salesBackOfficeApprovalDate);
                    String salesDirectivrApprovalDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SALES_DIRECTIVE_APPROVAL_DATE);
                    debug("salesDirectivrApprovalDate: " + salesDirectivrApprovalDate);
                    String financeReceiveDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_FINANCE_RECEIVE_DATE);
                    debug("financeReceiveDate: " + financeReceiveDate);
                    String paymentDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_PAYMENT_DATE);
                    debug("paymentDate: " + paymentDate);
                    String readyCommCalcDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_COMMISSION_CALC_DATE);
                    debug("readyCommCalcDate: " + readyCommCalcDate);
                    String calcTargetedDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_CALC_TARGETED_DATE);
                    debug("calcTargetedDate: " + calcTargetedDate);
                    String paymentTargetedDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_PAYMENT_TARGETED_DATE);
                    debug("paymentTargetedDate: " + paymentTargetedDate);
                    String finishedOn = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_FINISHED_ON_DATE);
                    debug("finishedOn: " + finishedOn);
                    String commCalcDelay = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_COMMISSION_CALC_DELAY);
                    debug("commCalcDelay: " + commCalcDelay);
                    String paymentDelay = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_PAYMENT_DELAY);
                    debug("paymentDelay: " + paymentDelay);

                    if (paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_MEMO_REASONS) != null) {
                        debug("memo reasons is not null");
                        MemoDAO.deleteMemoDelayReasons(mConSDSConnection, memoId);
                        boolean b = paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_MEMO_REASONS).getClass().isArray();
                        if (b) {
                            String[] delayReasons = (String[]) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_MEMO_REASONS);

                            for (int i = 0; i < delayReasons.length; i++) {
                                debug("delayReason " + i + " " + delayReasons[i]);
                                MemoDAO.updateMemoDelayReasons(mConSDSConnection, String.valueOf(delayReasons[i]), String.valueOf(memoId));
                            }

                        } else {
                            String delayReason = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_MEMO_REASONS);
                            debug("delayReason: " + delayReason);
                            MemoDAO.updateMemoDelayReasons(mConSDSConnection, String.valueOf(delayReason), String.valueOf(memoId));
                        }
                    }


                    MemoDAO.updateMemoHistory(mConSDSConnection, Integer.parseInt(memoId), sendForValidationDate, approvalDate, salesManagerApprovalDate, salesBackOfficeApprovalDate, salesDirectivrApprovalDate, financeReceiveDate, paymentDate, readyCommCalcDate, calcTargetedDate, paymentTargetedDate, finishedOn, commCalcDelay, paymentDelay);


                    String periodId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_PERIOD_ID);
                    String assignedPeriodId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_ASSIGNED_PERIOD_ID);
                    if (Integer.parseInt(periodId) == 1) {
                        String weekId = (String) paramHashMap.get("weekNo");
                        MemoDAO.updateMemoWeeksData(mConSDSConnection, memoId, weekId, assignedPeriodId);
                    } else if (Integer.parseInt(periodId) == 2) {
                        String monthId = (String) paramHashMap.get("monthNo");
                        MemoDAO.updateMemoMonthsData(mConSDSConnection, memoId, monthId, assignedPeriodId);
                    } else if (Integer.parseInt(periodId) == 3) {
                        String qrtrId = (String) paramHashMap.get("qrtrNo");
                        MemoDAO.updateMemoQrtrData(mConSDSConnection, memoId, qrtrId, assignedPeriodId);
                    }


                    //for the search part of the page
                    Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                    Vector memoStates = MemoDAO.getMemoStates(mConSDSConnection);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                    Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_STATES, memoStates);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);


                }
                break;
                case ACTION_VIEW_MEMO_MONITOR: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                    Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);

                }
                break;
                case ACTION_SEARCH_MEMO_MONITOR: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                    Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);


                    String specificChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID);
                    String subChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SUB_CHANNEL_ID);

                    String fromDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_START_DATE);
                    String toDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_END_DATE);



                    dataHashMap.put(MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID, specificChannelId);
                    dataHashMap.put(MemoInterfaceKey.CONTROL_SELECT_SUB_CHANNEL_ID, subChannelId);
                    dataHashMap.put(MemoInterfaceKey.CONTROL_INSERTED_START_DATE, fromDate);
                    dataHashMap.put(MemoInterfaceKey.CONTROL_INSERTED_END_DATE, toDate);


                    Vector qMemos = MemoDAO.searchMemoMonitor(mConSDSConnection, specificChannelId, subChannelId, fromDate, toDate, "3");
                    dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_QUARTERLY_MEMOS, qMemos);

                    Vector mMemos = MemoDAO.searchMemoMonitor(mConSDSConnection, specificChannelId, subChannelId, fromDate, toDate, "2");
                    dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MONTHLY_MEMOS, mMemos);

                    Vector wMemos = MemoDAO.searchMemoMonitor(mConSDSConnection, specificChannelId, subChannelId, fromDate, toDate, "1");
                    dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_WEEKLY_MEMOS, wMemos);

                }
                break;
                case ACTION_EXPORT_MEMO_MONITOR_TO_EXCEL: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    dataHashMap.put(MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID, (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_CHANNEL_ID));
                    dataHashMap.put(MemoInterfaceKey.CONTROL_SELECT_SUB_CHANNEL_ID, (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_SUB_CHANNEL_ID));
                    dataHashMap.put(MemoInterfaceKey.CONTROL_INSERTED_START_DATE, (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_FROM_DATE));
                    dataHashMap.put(MemoInterfaceKey.CONTROL_INSERTED_END_DATE, (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_TO_DATE));



                }
                break;
                case ACTION_SEARCH_SPECIFIC_MEMO: {
                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                        String memoName = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_NAME);
                        String stateId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_STATE_ID);
                        String channelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID);
                        String paymentTypeId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID);
                        //calling dao
                        Vector memos = MemoDAO.searchSpecificMemo(mConSDSConnection, memoName, stateId, channelId, paymentTypeId);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);


                        //for the search part of the page
                        Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                        Vector memoStates = MemoDAO.getMemoStates(mConSDSConnection);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                        Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                        Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_STATES, memoStates);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);

                        // the search criteria 
                        dataHashMap.put(MemoInterfaceKey.CONTROL_MEMO_NAME, memoName);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_SELECT_STATE_ID, stateId);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID, channelId);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID, paymentTypeId);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_SEARCH_COLLECTION_MEMO: {
                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                        String memoName = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_NAME);
                        String stateId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_STATE_ID);
                        String channelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_COLLECTION_CHANNEL_ID);

                        //calling the dao
                        Vector memos = MemoDAO.searchCollectionMemo(mConSDSConnection, memoName, stateId, channelId);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);


                        //for the search part of the page
                        Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                        Vector memoStates = MemoDAO.getMemoStates(mConSDSConnection);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                        Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                        Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                        Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_STATES, memoStates);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);
                        // the search criteria 
                        dataHashMap.put(MemoInterfaceKey.CONTROL_MEMO_NAME, memoName);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_SELECT_STATE_ID, stateId);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_SELECT_COLLECTION_CHANNEL_ID, channelId);


                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_SEARCH_SPECIFIC_COLLECTION_MEMO: {

                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);



                        String memoName = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_NAME);
                        String stateId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_STATE_ID);
                        String specificChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID);
                        String collectionChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_COLLECTION_CHANNEL_ID);
                        String subChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SUB_CHANNEL_ID);

                        String paymentTypeId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID);
                        String memoTypeId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_TYPE_ID);
                        String fromDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_START_DATE);
                        String toDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_END_DATE);
                        String paymentMethodId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_METHOD_ID);

                        //calling the dao
                        if (Integer.parseInt(memoTypeId) == 1) {
                            Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, 1, memoName, stateId, specificChannelId, subChannelId, paymentTypeId, fromDate, toDate, paymentMethodId);
                            dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_SEARCH_COLLECTION_MEMO);

                        } else if (Integer.parseInt(memoTypeId) == 2) {
                            Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, 2, memoName, stateId, specificChannelId, subChannelId, paymentTypeId, fromDate, toDate, paymentMethodId);
                            dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_SEARCH_SPECIFIC_MEMO);
                        } else {
                            Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, -1, memoName, stateId, specificChannelId, subChannelId, paymentTypeId, fromDate, toDate, paymentMethodId);
                            dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_SEARCH_SPECIFIC_MEMO);
                        }



                        //for the search part of the page
                        Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                        Vector memoStates = MemoDAO.getMemoStates(mConSDSConnection);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                        Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                        Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                        Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_STATES, memoStates);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);
                        // the search criteria 
                        dataHashMap.put(MemoInterfaceKey.CONTROL_MEMO_NAME, memoName);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_SELECT_STATE_ID, stateId);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID, specificChannelId);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_SELECT_COLLECTION_CHANNEL_ID, collectionChannelId);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID, paymentTypeId);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_VIEW_READY_MEMOS: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

                    // modified by Medhat Amin 7-7-2011

                    //Vector memos=MemoDAO.searchSpecificCollectionMemo(mConSDSConnection,-1,"","1","","","","","","");

                    Vector memos = new Vector();

                    dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);

                    //for the search part of the page
                    Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                    Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                    Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);

                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);

                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    //get the memo payment types.

                }
                break;
                case ACTION_VIEW_EDIT_READY_MEMO: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String memoId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_ID);
                    dataHashMap.put(MemoInterfaceKey.CONTROL_MEMO_ID, memoId);

                    String[] comment_title_arr = MemoDAO.getMemoText(mConSDSConnection, memoId);
                    dataHashMap.put(MemoInterfaceKey.MEMO_COMMENT, comment_title_arr[0]);
                    dataHashMap.put(MemoInterfaceKey.MEMO_TITLE, comment_title_arr[1]);



                }
                break;
                case ACTION_EDIT_READY_MEMO: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

                    Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, -1, "", "1", "", "", "", "", "", "");
                    dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);

                    //for the search part of the page
                    Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                    Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);

                    //edit the memo
                    String memoId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_ID);
                    String comment = (String) paramHashMap.get(MemoInterfaceKey.MEMO_COMMENT);
                    String title = (String) paramHashMap.get(MemoInterfaceKey.MEMO_TITLE);


                    System.out.println("Comment " + comment);
                    System.out.println("title = " + title);

                    MemoDAO.editMemo(mConSDSConnection, memoId, comment, strUserID, title);
                }
                break;

                case ACTION_SEARCH_READY_MEMOS: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);



                    String memoName = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_NAME);
                    String memoID = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_ID);

                    //String state_id=(String)paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_STATE_ID);
                    String specificChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID);
                    String collectionChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_COLLECTION_CHANNEL_ID);
                    String subChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SUB_CHANNEL_ID);


                    String paymentTypeId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID);
                    String memoTypeId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_TYPE_ID);
                    String fromDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_START_DATE);
                    String toDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_END_DATE);
                    String paymentMethodId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_METHOD_ID);

                    //calling the dao
                    if (Integer.parseInt(memoTypeId) == 1) {
                        Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, 1, memoName, "1", specificChannelId, subChannelId, paymentTypeId, fromDate, toDate, paymentMethodId);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_SEARCH_COLLECTION_MEMO);

                    } else if (Integer.parseInt(memoTypeId) == 2) {
                        Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, 2, memoName, "1", specificChannelId, subChannelId, paymentTypeId, fromDate, toDate, paymentMethodId);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_SEARCH_SPECIFIC_MEMO);
                    } else {
                        Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, -1, memoName, "1", specificChannelId, subChannelId, paymentTypeId, fromDate, toDate, paymentMethodId);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_SEARCH_SPECIFIC_MEMO);
                    }


                    //for the search part of the page
                    Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                    Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                    Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);

                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;

                case ACTION_VIEW_ISSUED_MEMOS: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    //MemoDAO.searchSpecificCollectionMemo(mConSDSConnection,-1,"","2","","","","","","");
                    Vector memos = new Vector();
                    dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);

                    //for the search part of the page
                    Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                    Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                    Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);

                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;
                case ACTION_SEARCH_ISSUED_MEMOS: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);



                    String memoName = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_NAME);
                    //String state_id=(String)paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_STATE_ID);
                    String specificChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID);
                    String collectionChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_COLLECTION_CHANNEL_ID);
                    String subChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SUB_CHANNEL_ID);


                    String paymentTypeId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID);
                    String memoTypeId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_TYPE_ID);
                    String fromDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_START_DATE);
                    String toDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_END_DATE);
                    String paymentMethodId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_METHOD_ID);

                    //calling the dao
                    if (Integer.parseInt(memoTypeId) == 1) {
                        Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, 1, memoName, "2", specificChannelId, subChannelId, paymentTypeId, fromDate, toDate, paymentMethodId);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_SEARCH_COLLECTION_MEMO);

                    } else if (Integer.parseInt(memoTypeId) == 2) {
                        Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, 2, memoName, "2", specificChannelId, subChannelId, paymentTypeId, fromDate, toDate, paymentMethodId);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_SEARCH_SPECIFIC_MEMO);
                    } else {
                        Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, -1, memoName, "2", specificChannelId, subChannelId, paymentTypeId, fromDate, toDate, paymentMethodId);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_SEARCH_SPECIFIC_MEMO);
                    }


                    //for the search part of the page
                    Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                    Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                    Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);

                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;
//                case ACTION_VIEW_SALES_APPROVAL: {
//                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
//                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
//                    Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, -1, "", null, "4", "", "", "", "", "", "");
//                    dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
//
//                    //for the search part of the page
//                    Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
//                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
//                    Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
//                    Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
//                    Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
//                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
//                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
//                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
//                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
//                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);
//
//                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
//                }
//                break;
                case ACTION_SEARCH_SALES_APPROVAL: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);



                    String memoName = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_NAME);
                    //String state_id=(String)paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_STATE_ID);
                    String specificChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID);
                    String collectionChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_COLLECTION_CHANNEL_ID);
                    String subChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SUB_CHANNEL_ID);


                    String paymentTypeId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID);
                    String memoTypeId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_TYPE_ID);
                    String fromDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_START_DATE);
                    String toDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_END_DATE);
                    String paymentMethodId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_METHOD_ID);

                    //calling the dao
                    if (Integer.parseInt(memoTypeId) == 1) {
                        Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, 1, memoName, "4", specificChannelId, subChannelId, paymentTypeId, fromDate, toDate, paymentMethodId);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_SEARCH_COLLECTION_MEMO);

                    } else if (Integer.parseInt(memoTypeId) == 2) {
                        Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, 2, memoName, "4", specificChannelId, subChannelId, paymentTypeId, fromDate, toDate, paymentMethodId);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_SEARCH_SPECIFIC_MEMO);
                    } else {
                        Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, -1, memoName, "4", specificChannelId, subChannelId, paymentTypeId, fromDate, toDate, paymentMethodId);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_SEARCH_SPECIFIC_MEMO);
                    }


                    //for the search part of the page
                    Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                    Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                    Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);

                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;
                case ACTION_VIEW_SALES_APPROVED_MEMOS: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    Vector memos = new Vector();
                    //MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, -1, "", null, "4", "", "", "", "", "", "");
                    dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);

                    //for the search part of the page
                    Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                    Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                    Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);

                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;
                case ACTION_SEARCH_SALES_APPROVED_MEMOS: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);



                    String memoName = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_NAME);
                    //String state_id=(String)paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_STATE_ID);
                    String specificChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID);
                    String collectionChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_COLLECTION_CHANNEL_ID);
                    String subChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SUB_CHANNEL_ID);


                    String paymentTypeId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID);
                    String memoTypeId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_TYPE_ID);
                    String fromDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_START_DATE);
                    String toDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_END_DATE);
                    String paymentMethodId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_METHOD_ID);

                    //calling the dao
                    if (Integer.parseInt(memoTypeId) == 1) {
                        Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, 1, memoName, "4", specificChannelId, subChannelId, paymentTypeId, fromDate, toDate, paymentMethodId);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_SEARCH_COLLECTION_MEMO);

                    } else if (Integer.parseInt(memoTypeId) == 2) {
                        Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, 2, memoName, "4", specificChannelId, subChannelId, paymentTypeId, fromDate, toDate, paymentMethodId);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_SEARCH_SPECIFIC_MEMO);
                    } else {
                        Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, -1, memoName, "4", specificChannelId, subChannelId, paymentTypeId, fromDate, toDate, paymentMethodId);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_SEARCH_SPECIFIC_MEMO);
                    }


                    //for the search part of the page
                    Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                    Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                    Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);

                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;
                case ACTION_VIEW_SALES_APPROVED_TO_AP: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, -1, "", "6", "", "", "", "", "", "");
                    dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);

                    //for the search part of the page
                    Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                    Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                    Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);

                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;
                case ACTION_SEARCH_SALES_APPROVED_TO_AP: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);



                    String memoName = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_NAME);
                    //String state_id=(String)paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_STATE_ID);
                    String specificChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID);
                    String collectionChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_COLLECTION_CHANNEL_ID);
                    String subChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SUB_CHANNEL_ID);


                    String paymentTypeId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID);
                    String memoTypeId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_TYPE_ID);
                    String fromDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_START_DATE);
                    String toDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_END_DATE);
                    String paymentMethodId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_METHOD_ID);

                    //calling the dao
                    if (Integer.parseInt(memoTypeId) == 1) {
                        Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, 1, memoName, "6", specificChannelId, subChannelId, paymentTypeId, fromDate, toDate, paymentMethodId);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_SEARCH_COLLECTION_MEMO);

                    } else if (Integer.parseInt(memoTypeId) == 2) {
                        Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, 2, memoName, "6", specificChannelId, subChannelId, paymentTypeId, fromDate, toDate, paymentMethodId);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_SEARCH_SPECIFIC_MEMO);
                    } else {
                        Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, -1, memoName, "6", specificChannelId, subChannelId, paymentTypeId, fromDate, toDate, paymentMethodId);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_SEARCH_SPECIFIC_MEMO);
                    }


                    //for the search part of the page
                    Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                    Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                    Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);

                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;
                case ACTION_VIEW_SALES_APPROVED_TO_LOGESTIC: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, -1, "", "7", "", "", "", "", "", "");
                    dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);

                    //for the search part of the page
                    Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                    Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                    Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);

                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;
                case ACTION_SEARCH_SALES_APPROVED_TO_LOGESTIC: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);



                    String memoName = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_NAME);
                    //String state_id=(String)paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_STATE_ID);
                    String specificChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SPECIFIC_CHANNEL_ID);
                    String collectionChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_COLLECTION_CHANNEL_ID);
                    String subChannelId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_SUB_CHANNEL_ID);


                    String paymentTypeId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID);
                    String memoTypeId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_TYPE_ID);
                    String fromDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_START_DATE);
                    String toDate = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_INSERTED_END_DATE);
                    String paymentMethodId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_METHOD_ID);

                    //calling the dao
                    if (Integer.parseInt(memoTypeId) == 1) {
                        Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, 1, memoName, "7", specificChannelId, subChannelId, paymentTypeId, fromDate, toDate, paymentMethodId);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_SEARCH_COLLECTION_MEMO);

                    } else if (Integer.parseInt(memoTypeId) == 2) {
                        Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, 2, memoName, "7", specificChannelId, subChannelId, paymentTypeId, fromDate, toDate, paymentMethodId);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_SEARCH_SPECIFIC_MEMO);
                    } else {
                        Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, -1, memoName, "7", specificChannelId, subChannelId, paymentTypeId, fromDate, toDate, paymentMethodId);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_SEARCH_SPECIFIC_MEMO);
                    }


                    //for the search part of the page
                    Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                    Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                    Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);

                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;
                case ACTION_DELETE_MEMO: {
                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String memoId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                        //calling the dao
                        MemoDAO.deleteMemo(mConSDSConnection, memoId, strUserID);


                        //for the search part of the page
                        Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                        Vector memoStates = MemoDAO.getMemoStates(mConSDSConnection);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                        Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                        Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                        Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_STATES, memoStates);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_DELETE_READY_MEMO: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    String memoId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    //calling the dao
                    MemoDAO.deleteMemo(mConSDSConnection, memoId, strUserID);

                    Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, -1, "", "1", "", "", "", "", "", "");
                    dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);

                    //for the search part of the page
                    Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                    Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                    Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);



                }
                break;
                case ACTION_DELETE_ISSUED_MEMO: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    String memoId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    //calling the dao
                    MemoDAO.deleteMemo(mConSDSConnection, memoId, strUserID);

                    Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, -1, "", "2", "", "", "", "", "", "");
                    dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);

                    //for the search part of the page
                    Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                    Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                    Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);


                }
                break;
                case ACTION_ISSUE_MEMO: {
                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                        String memoId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_ID);

                        //calling the dao
                        String msg = MemoDAO.issueMemo(mConSDSConnection, memoId, strUserID);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_ACCRUAL_MESSAGE, msg);



                        //for the search part of the page
                        Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                        Vector memoStates = MemoDAO.getMemoStates(mConSDSConnection);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                        Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                        Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                        Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_STATES, memoStates);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_FINANCE_ISSUE_MEMO: {
                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                        String memoId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_ID);

                        //calling the dao
                        MemoDAO.financeIssueMemo(mConSDSConnection, memoId, strUserID);


                        //for the search part of the page
                        Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                        Vector memoStates = MemoDAO.getMemoStates(mConSDSConnection);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                        Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                        Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                        Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_STATES, memoStates);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;

                case ACTION_ISSUE_READY_MEMO: {
                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

                        String memoId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_ID);


                        //calling the dao
                        //MemoDAO.issueMemo(mConSDSConnection,memo_id,strUserID);
                        String msg = MemoDAO.issueMemo(mConSDSConnection, memoId, strUserID);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_ACCRUAL_MESSAGE, msg);
                        if (msg.equals("DONE")) {
                            //Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, -1, "", "2", "", "", "", "", "", "");
                            //dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                        } else if (msg.equals("NOT DONE")) {
                            //Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, -1, "", "1", "", "", "", "", "", "");
                            //dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_VIEW_READY_MEMOS);
                        }



                        //for the search part of the page
                        Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                        Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                        Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                        Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);



                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }

                }
                break;
                case ACTION_SALES_APPROVED: {
                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_VIEW_ISSUED_MEMOS);
                        String memoId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_ID);


                        //calling the dao
                        MemoDAO.financeIssueMemo(mConSDSConnection, memoId, strUserID);

                        Vector memos = new Vector();

                        //MemoDAO.searchSpecificCollectionMemo(mConSDSConnection,-1,"","2","","","","","","");

                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);

                        //for the search part of the page
                        Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                        Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                        Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                        Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);



                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;

                case ACTION_SALES_APPROVED_TO_AP: {
                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_VIEW_SALES_APPROVED_MEMOS);
                        String memoId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_ID);


                        //calling the dao
                        MemoDAO.approveToAp(mConSDSConnection, memoId, strUserID);

                        Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, -1, "", "4", "", "", "", "", "", "");

                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);

                        //for the search part of the page
                        Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                        Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                        Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                        Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);



                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;

                case ACTION_SALES_APPROVED_TO_LOGESTIC: {
                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_VIEW_SALES_APPROVED_MEMOS);
                        String memoId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_ID);


                        //calling the dao
                        MemoDAO.approveToLogestic(mConSDSConnection, memoId, strUserID);

                        Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, -1, "", "4", "", "", "", "", "", "");

                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);

                        //for the search part of the page
                        Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                        Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                        Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                        Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);



                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;


                case ACTION_SALES_APPROVED_TO_CREDIT_MODULE: {
                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_SALES_APPROVED_TO_CREDIT_MODULE);
                        String memoId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_ID);


                        //calling the dao
                        MemoDAO.addToCreditModule(mConSDSConnection, memoId, strUserID);

                        Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, -1, "", "4", "", "", "", "", "", "");

                        dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);

                        //for the search part of the page
                        Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                        Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                        Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                        Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);



                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_REMOVE_MEMBERS_OF_MEMO: {
                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                        String memoId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_ID);

                        String dcmId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_DCM_ID);

                        String paymentId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_PAYMENT_ID);


                        //calling the dao
                        MemoDAO.removeMembersOFMemo(mConSDSConnection, memoId, dcmId, strUserID);


                        String memoName = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_VIEW_MEMO_NAME);
                        String stateName = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_STATE_NAME);
                        Vector members = MemoDAO.viewMemo(mConSDSConnection, memoId);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_MEMO_ID, memoId);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_MEMO_NAME, memoName);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_MEMBERS, members);
                        dataHashMap.put(MemoInterfaceKey.HIDDEN_MEMO_STATE_NAME, stateName);




                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_GET_MEMO_REPORT: {
                    //  int memoReportID=0;
                    try {
                        String app_name = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_APPLIACTION_CONTEXT);
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String memo_id = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_ID);
                        //String memo_name=(String)paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_NAME);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                        MemoModel memo_model = MemoDAO.getMemoById(mConSDSConnection, Integer.parseInt(memo_id));

                        //for the search part of the page               
                        Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                        Vector memoStates = MemoDAO.getMemoStates(mConSDSConnection);
                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                        Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                        Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);

                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_STATES, memoStates);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_FILE_NAME, "temp.pdf");


                        //amr code for jasper report
                        //	String sql_file_name = null;//distributer line commission   
                        // memo type or memo method
                        String memo_type_name = MemoDAO.getMemoType(mConSDSConnection, Integer.parseInt(memo_id));
                        System.out.println("memo type = " + memo_type_name);

                        if (memo_type_name == null || (memo_model.getPaymentType().compareTo("-1") == 0)) {
                            // memo_type_name = MemoDAO.getMemoPaymentMethodType(mConSDSConnection, Integer.parseInt(memo_id));
                            memo_type_name = "Collective";
                            System.out.println("memo is collective ");

                            if (memo_model.getChannel().compareTo("3") == 0) //to handel the collective of the complemantry different
                            {
                                System.out.println("memo is collective and complementary");
                                memo_model.setPaymentType("-2");
                            } else {
                                System.out.println("memo is collective but not compelmentary ");
                            }
                        }

                        System.out.println("memo type = " + memo_type_name);

                        //String[] queries_params=null;//{memo_id+"#kk#"+memo_id+"#kk#"+memo_id+"#kk#"};

                        String sql = "select * from cam_memo_pdf_management where payment_type_id = " + memo_model.getPaymentType();
                        Statement stat = mConSDSConnection.createStatement();
                        ResultSet res = stat.executeQuery(sql);
                        String query = null;
                        String paramString = null;
                        while (res.next()) {
                            query = res.getString("pdf_sql_statement");
                            paramString = res.getString("queries_params");

                        }

                        String[] all_queries = new String[1];

                        ArrayList error_msgs = new ArrayList();
                        // memo type has no sql query
                        if (query == null) {
                            error_msgs.add("Memo type has no SQL query, Please edit Memo type query from Memo Type Management Page.");
                        } else {

                            paramString = paramString.replaceAll("memo_id", memo_id);
                            all_queries[0] = JasperManager.setQueryParameters(query, paramString);
                        }

                        GregorianCalendar current_data_calender = new GregorianCalendar();
                        String sql_query = all_queries[0];
                        Date memo_date = memo_model.getStartDate();

                        String[] params = new String[23];
                        params[0] = app_name + "/templates/A4_template.jrxml";
                        System.out.print("gadooooo" + params[0]);
                        params[1] = sql_query;
                        params[17] = new SimpleDateFormat("dd/MM/yyyy").format(memo_date);//memo_date.getDay()+"/"+memo_date.getMonth()+"/"+memo_date.getYear();

                        params[18] = "Automatically generated by SDS at " + current_data_calender.get(current_data_calender.DAY_OF_MONTH) + "/" + (current_data_calender.get(current_data_calender.MONTH) + 1) + "/" + current_data_calender.get(current_data_calender.YEAR) + " Memo ID " + memo_id; // auto generated message

                        params[19] = MemoDAO.getMemoTitle(mConSDSConnection, Integer.parseInt(memo_id));
                        if (params[19] == null) {
                            params[19] = "";
                        }
                        String[] comment_title = MemoDAO.getMemoText(mConSDSConnection, memo_id);

                        params[20] = memo_model.getMemoComment();


                        MemoDAO.getMemoSettings(mConSDSConnection, params, Integer.parseInt(memo_model.getChannel()));

                        params[6] = comment_title[1];
                        params[2] = "clip_image002.gif"; // image path related to java src folder             
                        String[] all_file_path = new String[all_queries.length];

                        int query_row_count = 0;
                        JRXMLDesignerManeger manager = new JRXMLDesignerManeger();
                        ResultSet result_set = Utility.executeSelect(mConSDSConnection, all_queries[0]);
                        System.out.println("Query: " + all_queries[0]);
                        //debug(all_queries[i]); 
                        if (result_set != null) {
                            while (result_set.next()) {
                                query_row_count++;
                            }
                            if (query_row_count == 0) {
                                error_msgs.add("Memo type query returned no data. Report is empty.");
                            }

                            if (query_row_count != 0) {
                                Vector all_report_column = Utility.getResultSetFields(result_set);

                                //String file_name = app_name+"/generated PDF files/"+unique_memo_file+".pdf";

                                String file_path = app_name + "/generated PDF files/";
                                file_path += comment_title[1];
                                String unique_memo_file = comment_title[1];
                                if (file_path == null || file_path.equals("")) {
                                    unique_memo_file = memo_model.getName() + "_" + DeductionDAO.getFileSequence(mConSDSConnection) + "_" + memo_model.getId();
                                    file_path = unique_memo_file;
                                }
                                file_path += ".pdf";
                                System.out.println("memo file name= " + file_path);
                                all_file_path[0] = app_name + "/generated XML files/" + unique_memo_file + "_classic2_pdf_0.jrxml";
                                System.out.print(all_file_path[0]);
                                manager.designJRXMLFile(params, all_report_column, all_file_path[0], query_row_count);
                                JasperManager.setParameter(all_file_path, all_queries, file_path, null);
                                JasperManager.exportPDFReport(mConSDSConnection);
                                dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH, unique_memo_file + ".pdf");
                                dataHashMap.put(InterfaceKey.MODULE_SUB_PATH, "/cam/camMemosFiles/generated PDF files/");
                            }
                        }
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_REPORT_ERROR_MSG, error_msgs);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_INCOMING_ACITON, MemoInterfaceKey.ACTION_VIEW_READY_MEMOS);
                        System.out.println("error msg: " + error_msgs);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_SEARCH_IN_MEMO: {
                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                        String memoId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_ID);

                        String dcmCode = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_DCM_CODE);

                        String dcmName = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_DCM_NAME);

                        String paymentTypeId = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ID);

                        String memoName = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_NAME);

                        String stateName = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_STATE_NAME);


                        //calling the dao
                        Vector members = MemoDAO.searchInMemo(mConSDSConnection, memoId, dcmCode, dcmName, "", paymentTypeId);


                        Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);

                        dataHashMap.put(MemoInterfaceKey.CONTROL_MEMO_ID, memoId);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_VIEW_MEMO_NAME, memoName);
                        dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_MEMBERS, members);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                        dataHashMap.put(MemoInterfaceKey.HIDDEN_MEMO_STATE_NAME, stateName);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_EXPORT_MEMO: {
                    try {
                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_EXPORT_MEMO_TO_EXCEL: {
                    String app_name = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_APPLIACTION_CONTEXT);
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    String memo_id = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_ID);

                    //the payment method 1: if FOC;0: otherwise
                    String paymentMethodFlag = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_PAYMENT_METHOD);
                    //String memo_name=(String)paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_NAME);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    MemoModel memo_model = MemoDAO.getMemoById(mConSDSConnection, Integer.parseInt(memo_id));

                    String[] all_sheet_name = {"Line commission", "Line commission details", "SD deduction details", "SD deduction", "UnReal deduction details", "Dist line commission"};
                    String[] all_queries_file_path = {app_name + "/SQL queries/positive line commission items details.sql", app_name + "//SQL queries//positive line commission details.sql", app_name + "//SQL queries//sd deduction line commission items details.sql", app_name + "//SQL queries//SD deduction line commission details.sql", app_name + "//SQL queries//unreal_deduction_line_commission_details.sql", app_name + "//SQL queries//distributer line commission.sql"};
////////////////////////////////////////////////////
                    String[] sql_file_name = null;//distributer line commission   
                    // memo type or memo method
                    String memo_type_name = MemoDAO.getMemoType(mConSDSConnection, Integer.parseInt(memo_id));
                    System.out.println("Memo type: " + memo_type_name);

                    String paymentMethodType = MemoDAO.getMemoPaymentMethodType(mConSDSConnection, Integer.parseInt(memo_id));



                    String[] queries_params = null;//{memo_id+"#kk#"+memo_id+"#kk#"+memo_id+"#kk#"};
                    String excel_file_specific_path = "";
                    boolean isEtopup = false;
                    /*
                     * if(memo_type_name.compareTo("Add & Win Line Commission
                     * Payment")==0) { excel_file_specific_path = "add and
                     * win/"; sql_file_name = new String[2]; all_sheet_name= new
                     * String[2]; queries_params= new String[2];
                     * sql_file_name[0] = "Add & Win Line Commission Payment";
                     * sql_file_name[1] = "positive commission items details";
                     * queries_params[0] = memo_id+"#kk#"+memo_id+"#kk#";
                     * queries_params[1] = memo_id+"#kk#"; all_sheet_name[0] =
                     * "Add and Win Act Report"; all_sheet_name[1] = "Add and
                     * Win W1 Commission"; }
                     *
                     * else if(memo_type_name.compareTo("Dist ARPU postpaid
                     * Commission Payment")==0)// payment method {
                     * excel_file_specific_path = "ARPU/"; sql_file_name = new
                     * String[2]; all_sheet_name= new String[2]; queries_params=
                     * new String[2]; sql_file_name[0] =
                     * "Dist_ARPU_postpaid_commission_details"; sql_file_name[1]
                     * = "Dist_ARPU_postpaid_commission"; queries_params[0] =
                     * memo_id+"#kk#"; queries_params[1] =
                     * memo_id+"#kk#"+memo_id+"#kk#"; all_sheet_name[0] = "ARPU
                     * Dist Deduction"; all_sheet_name[1] = "Dist ARPU
                     * Commission"; } else
                     */
                    if (paymentMethodType.compareTo("FOC") == 0)// payment method
                    {
                        isEtopup = true;
                        excel_file_specific_path = "EtoPup/";
                        sql_file_name = new String[1];
                        all_sheet_name = new String[1];
                        queries_params = new String[1];
                        sql_file_name[0] = "etopup payment type";
                        queries_params[0] = memo_id + "#kk#";
                        all_sheet_name[0] = "First Sheet";
                    }
                    all_queries_file_path = new String[sql_file_name.length];

                    //queries_params=new String[]{memo_id+"#kk#"+memo_id+"#kk#"};
                    for (int i = 0; i < sql_file_name.length; i++) {
                        all_queries_file_path[i] = app_name + "/SQL queries/excel/" + excel_file_specific_path + sql_file_name[i] + ".sql";
                        System.out.println("i = " + i);
                        System.out.println(all_queries_file_path[i]);
                        System.out.println("*************");



                    }

                    String[] all_queries = new String[all_queries_file_path.length];
                    //each query has parameter string contains paris of value/type 
                    JasperManager.readQueries(all_queries, all_queries_file_path, queries_params);
                    String unique_memo_file = memo_model.getName() + "_" + DeductionDAO.getFileSequence(mConSDSConnection) + "_" + memo_model.getId();
                    String[] all_generatedXMLFile_path = new String[all_queries.length];
                    boolean all_sheets_empty = true;
                    ArrayList error_msgs = new ArrayList();
                    ArrayList all_queries_al = new ArrayList();
                    for (int i = 0; i < all_queries.length; i++) {
                        JRXMLDesignerManegerForXLS manager = new JRXMLDesignerManegerForXLS();

                        ResultSet result_set = Utility.executeSelect(mConSDSConnection, all_queries[i]);

                        if (result_set.next()) // query has data
                        {
                            result_set = Utility.executeSelect(mConSDSConnection, all_queries[i]);
                            Vector all_report_column = Utility.getResultSetFields(result_set);
                            String generated_xml_file_path = app_name + "/generated XML files/" + unique_memo_file + "_classic2_xls_" + i + ".jrxml";
                            manager.designJRXMLFile(app_name + "/templates/temp2_xls.jrxml", all_queries[i], all_report_column, generated_xml_file_path, isEtopup);
                            all_generatedXMLFile_path[i] = generated_xml_file_path;
                            all_sheets_empty = false;
                            all_queries_al.add(all_queries[i]);
                            System.out.println("aaaaaaaaa: " + all_queries[i]);
                        } else {
                            error_msgs.add("Sheet " + i + " returned no data.");
                        }
                    }
                    if (all_sheets_empty == false) { //at least one query returned data
                        String file_name = app_name + "/generated XLS files/" + unique_memo_file + ".xls";
                        System.out.println("eeeeeee " + all_sheet_name[0]);
                        JasperManager.setParameter(all_generatedXMLFile_path, (String[]) all_queries_al.toArray(new String[0]), file_name, all_sheet_name);
                        JasperManager.exportXLSReport(mConSDSConnection);
                        dataHashMap.put(MemoInterfaceKey.CONTROL_FILE_NAME, unique_memo_file + ".xls");
                        dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH, unique_memo_file + ".xls");
                        dataHashMap.put(InterfaceKey.MODULE_SUB_PATH, "/cam/camMemosFiles/generated XLS files/");
                    }

                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_REPORT_ERROR_MSG, error_msgs);
                }
                break;
                case ACTION_VIEW_DCM_DETAILS: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);


                }
                break;
                case ACTION_VIEW_GEN_DCM_DETAILS: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;
                case ACTION_SEARCH_DCM_DETAILS: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String dcmName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME);
                    String dcmCode = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE);
                    String stkNumber = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_STK_NUMBER);
                    //the status - reason part
                    Vector dcmStatusModel = PaymentScmStatusDao.searchDcmStatusDetails(mConSDSConnection, dcmCode, dcmName, stkNumber);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_DETAILS_STATUS, dcmStatusModel);
                    //the payments not in any memo part
                    Vector closedPayments = MemoDAO.viewDcmInClosedPayments(mConSDSConnection, dcmName, dcmCode, stkNumber);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_CLOSED_PAYMENTS, closedPayments);
                    //all the memos include this dcm
                    Vector dcmMemos = MemoDAO.getDcmMemos(mConSDSConnection, dcmName, dcmCode, stkNumber);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, dcmMemos);
                    //all the deductions made on this dcm
                    Vector dcmDeductions = MemoDAO.getDcmDeductions(mConSDSConnection, dcmName, dcmCode, stkNumber);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_DEDUCTIONS, dcmDeductions);
                    //history of the payment Status
                    Vector dcmStatusHistory = MemoDAO.viewDcmStatusHistory(mConSDSConnection, dcmName, dcmCode, stkNumber);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_PAYMENT_STAUTS_HISTORY, dcmStatusHistory);
                    //history of the insertion and deletion of memos
                    Vector memoHistory = MemoDAO.viewDcmMemoHistory(mConSDSConnection, dcmName, dcmCode, stkNumber);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_HISTORY, memoHistory);

                    //the status cases
                    Vector paymentStatusCases = PaymentScmStatusDao.getPaymentStatusCases(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES, paymentStatusCases);
                    //reasons
                    Vector statusReason = PaymentScmStatusDao.getPaymentStatusReasons(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, statusReason);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_DCM_MODE, "0");


                }
                break;
                case ACTION_EXPORT_DCM_DETAILS_EXCEL: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_EDIT_TEXT_SCM_CODE, (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_DCM_CODE));
                }

                break;
                case ACTION_GENERATE_SEARCH_DCM_DETAILS_LIST_TEMPLATE: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;
                case ACTION_VIEW_UPLOAD_SEARCH_DCM_DETAILS_LIST: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;
                case ACTION_UPLOAD_SEARCH_DCM_DETAILS_LIST: {
                    String fileName = (String) paramHashMap.get(InterfaceKey.CONTROL_INPUT_FILE);

                    fileName = Utility.replaceAll(fileName, "#", "\\");
                    //Vector all_dcm = //KPIDAO.getAllKPI(connection, sub_id, "normal",0,0);
                    HashMap importedList = ImportExportWrapper.importExcelFile(fileName, 0, 0, 0, 0);


                    if (importedList != null && importedList.size() > 0) {
                        Iterator keysItr = importedList.keySet().iterator();
                        Vector dcmCode = null;
                        Vector dcmName = null;
                        Vector stkNumber = null;


                        while (keysItr.hasNext()) {
                            String keyStr1 = (String) keysItr.next();

                            //String key_str2 = (String)keys_itr.next();
                            //debug("key_str2: "+ key_str2 );
                            //String key_str3 = (String)keys_itr.next();
                            //debug("key_str3: "+ key_str3 );



                            if (keyStr1.compareTo("DCM Name") == 0) {

                                dcmName = (Vector) importedList.get(keyStr1);
                            } else if (keyStr1.compareTo("DCM Code") == 0) {

                                dcmCode = (Vector) importedList.get(keyStr1);
                            } else if (keyStr1.compareTo("STK Number") == 0) {

                                stkNumber = (Vector) importedList.get(keyStr1);
                            }

                        }
                        //clear the search table
                        MemoDAO.clearSearchDcmDetailsList(mConSDSConnection);


                        for (int i = 1; i < dcmName.size(); i++) {

                            //PaymentImportModel m=new PaymentImportModel();
                            //m.setFileID((String) paramHashMap.get(InterfaceKey.CONTROL_INPUT_FILE));

                            String dName = (String) dcmName.get(i);
                            debug("dcm Name: " + dName);

                            String dCode = (String) dcmCode.get(i);
                            debug("dcm Code: " + dCode);

                            String stkNo = (String) stkNumber.get(i);

                            debug("Stk Number: " + stkNo);

                            //calling dao
                            //PaymentScmStatusDao.importPaymentState(mConSDSConnection,m);
                            //set the search table	
                            if (!(dName.compareTo("") == 0 && dCode.compareTo("") == 0 && stkNo.compareTo("") == 0)) {
                                MemoDAO.setSearchDcmDetailsList(mConSDSConnection, dName, dCode, stkNo);
                            }

                        }
                    }

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    //dataHashMap.put(InterfaceKey.ATTACH_ID, "" + PaymentScmStatusDao.getNextImportPaymentFileId(mConSDSConnection));
                }
                break;
                case ACTION_VIEW_RECENTLY_UPLOADED_SEARCH_LIST: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    //
                    //Vector searchList=MemoDAO.viewSearchDcmDetailsList(mConSDSConnection);
                    //dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_DETAILS_STATUS,searchList);

                }
                break;
                case ACTION_EXPORT_ALL_DCM_DETAILS_EXCEL: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;
                case ACTION_SEARCH_GEN_DCM_DETAILS: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    String dcmName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME);
                    String dcmCode = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE);
                    String stkNumber = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_STK_NUMBER);

                    //the status - reason part
                    Vector dcmStatusModel = PaymentScmStatusDao.searchDcmStatusDetails(mConSDSConnection, dcmCode, dcmName, stkNumber);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_DETAILS_STATUS, dcmStatusModel);
                    //all the deductions made on this dcm
                    Vector dcmDeductions = MemoDAO.getDcmDeductions(mConSDSConnection, dcmName, dcmCode, stkNumber);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_DEDUCTIONS, dcmDeductions);
                    //history of the payment Status
                    Vector dcmStatusHistory = MemoDAO.viewDcmStatusHistory(mConSDSConnection, dcmName, dcmCode, stkNumber);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_PAYMENT_STAUTS_HISTORY, dcmStatusHistory);
                    //history of the insertion and deletion of memos
                    Vector memoHistory = MemoDAO.viewGenDcmMemoHistory(mConSDSConnection, dcmName, dcmCode, stkNumber);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_HISTORY, memoHistory);


                    //the status cases
                    Vector paymentStatusCases = PaymentScmStatusDao.getPaymentStatusCases(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES, paymentStatusCases);
                    //reasons
                    Vector statusReason = PaymentScmStatusDao.getPaymentStatusReasons(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, statusReason);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_DCM_MODE, "1");
                }
                break;
                case ACTION_EXPORT_GEN_DCM_DETAILS_EXCEL: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_EDIT_TEXT_SCM_CODE, (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_DCM_CODE));
                }
                break;
                case ACTION_GET_MEMO_REASONS: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    Vector reasons = MemoDAO.getMemoReasons(mConSDSConnection);

                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_REASONS, reasons);


                }
                break;

                case ACTION_VIEW_ADD_MEMO_REASON: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                }
                break;
                case ACTION_ADD_MEMO_REASON: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    String reasonName = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_REASON_NAME);
                    String reasonDesc = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_REASON_DESCRIPTION);
                    MemoDAO.addMemoReason(mConSDSConnection, reasonName, reasonDesc);

                    Vector reasons = MemoDAO.getMemoReasons(mConSDSConnection);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_REASONS, reasons);
                }
                break;
                case ACTION_DELETE_MEMO_REASON: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    String reasonId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_REASON_ID);
                    MemoDAO.deleteMemoReason(mConSDSConnection, reasonId);
                    Vector reasons = MemoDAO.getMemoReasons(mConSDSConnection);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_REASONS, reasons);


                }
                break;
                case ACTION_VIEW_UPDATE_MEMO_REASON: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String reasonId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_REASON_ID);
                    Vector reasons = MemoDAO.getMemoReasonsById(mConSDSConnection, reasonId);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_REASONS, reasons);

                }
                break;
                case ACTION_UPDATE_MEMO_REASON: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    String reasonId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_REASON_ID);
                    String reasonName = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_REASON_NAME);
                    String reasonDesc = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_REASON_DESCRIPTION);
                    MemoDAO.updateMemoReason(mConSDSConnection, reasonId, reasonName, reasonDesc);


                    Vector reasons = MemoDAO.getMemoReasons(mConSDSConnection);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_REASONS, reasons);
                }
                break;


                case ACTION_VIEW_MEMO_DELAY_REASONS: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    Vector reasons = MemoDAO.getMemoDelayReasons(mConSDSConnection);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_REASONS, reasons);


                }
                break;

                case ACTION_VIEW_ADD_MEMO_DELAY_REASON: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                }
                break;
                case ACTION_ADD_MEMO_DELAY_REASON: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    String reasonName = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_REASON_NAME);
                    debug("reason name: " + reasonName);
                    String reasonDesc = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_REASON_DESCRIPTION);
                    debug("reason desc; " + reasonDesc);
                    MemoDAO.addMemoDelayReason(mConSDSConnection, reasonName, reasonDesc);

                    Vector reasons = MemoDAO.getMemoDelayReasons(mConSDSConnection);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_REASONS, reasons);
                }
                break;
                case ACTION_DELETE_MEMO_DELAY_REASON: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    String reasonId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_REASON_ID);
                    debug("delay reason id: " + reasonId);
                    MemoDAO.deleteMemoDelayReason(mConSDSConnection, reasonId);
                    Vector reasons = MemoDAO.getMemoDelayReasons(mConSDSConnection);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_REASONS, reasons);


                }
                break;
                case ACTION_VIEW_UPDATE_MEMO_DELAY_REASON: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String reasonId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_REASON_ID);
                    Vector reasons = MemoDAO.getMemoDelayReasonsById(mConSDSConnection, reasonId);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_REASONS, reasons);

                }
                break;
                case ACTION_UPDATE_MEMO_DELAY_REASON: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    String reasonId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_REASON_ID);
                    String reasonName = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_REASON_NAME);
                    String reasonDesc = (String) paramHashMap.get(MemoInterfaceKey.CONTROL_MEMO_REASON_DESCRIPTION);
                    MemoDAO.updateMemoDelayReason(mConSDSConnection, reasonId, reasonName, reasonDesc);


                    Vector reasons = MemoDAO.getMemoDelayReasons(mConSDSConnection);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_REASONS, reasons);
                }
                break;

                case ACTION_VIEW_MEMO_EXCEL_MANAGEMENT: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                }
                break;
                case ACTION_VIEW_MEMO_EXCEL_ATTRIBUTES: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String paymentTypeId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID);
                    String paymentTypeName = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME);

                    dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID, paymentTypeId);
                    dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME, paymentTypeName);

                    Vector PaymentTypeExcelModels = MemoDAO.getMemoTypeExcelAttributes(mConSDSConnection, paymentTypeId);

                    dataHashMap.put(PaymentInterfaceKey.PAYMENT_TYPE_EXCEL_ATTRIBUTE, PaymentTypeExcelModels);

                }
                break;
                case ACTION_VIEW_UPDATE_MEMO_EXCEL_ATTRIBUTES: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String paymentTypeExcelSheetId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_EXCEL_MANAGEMENT_ID);

                    //get the sheet attributes



                    dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_EXCEL_MANAGEMENT_ID, paymentTypeExcelSheetId);

                    PaymentTypeExcelModel model = MemoDAO.getMemoExcelSheetAttributes(mConSDSConnection, paymentTypeExcelSheetId);
                    dataHashMap.put(PaymentInterfaceKey.PAYMENT_TYPE_EXCEL_ATTRIBUTE, model);
                    String msg = "UNIQUE";
                    dataHashMap.put(PaymentInterfaceKey.EXCEL_SHEET_MESSAGE, msg);
                    Vector searchResults = PaymentDAO.getPaymentByFilter(mConSDSConnection,
                            "", "0", "0", "0",
                            "*", "*",
                            "*");

                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, searchResults);



                }
                break;
                case ACTION_UPDATE_MEMO_EXCEL_ATTRIBUTES: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String paymentTypeId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID);
                    String paymentTypeName = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME);
                    String paymentExcelId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_EXCEL_MANAGEMENT_ID);
                    String sheetNumber = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_EXCEL_SHEET_NUMBER);
                    String sheetName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_EXCEL_SHEET_NAME);
                    String sqlStatement = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_EXCEL_SHEET_SQL_STATEMENT);
                    String oldSheetNumber = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_OLD_EXCEL_SHEET_NUMBER);
                    String msg = "";
                    if (Integer.parseInt(sheetNumber) == Integer.parseInt(oldSheetNumber)) {
                        MemoDAO.updateMemoExcelSheet(mConSDSConnection, paymentExcelId, sheetNumber, sheetName, sqlStatement);

                        msg = "UNIQUE";
                        dataHashMap.put(PaymentInterfaceKey.EXCEL_SHEET_MESSAGE, msg);

                        dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID, paymentTypeId);
                        dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME, paymentTypeName);

                        Vector PaymentTypeExcelModels = MemoDAO.getMemoTypeExcelAttributes(mConSDSConnection, paymentTypeId);
                        dataHashMap.put(PaymentInterfaceKey.PAYMENT_TYPE_EXCEL_ATTRIBUTE, PaymentTypeExcelModels);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_VIEW_MEMO_EXCEL_ATTRIBUTES);
                    } //check sheet number uniqueness
                    else if (MemoDAO.checkExcelSheetUniqueness(mConSDSConnection, paymentTypeId, sheetNumber)) {
                        MemoDAO.updateMemoExcelSheet(mConSDSConnection, paymentExcelId, sheetNumber, sheetName, sqlStatement);

                        msg = "UNIQUE";
                        dataHashMap.put(PaymentInterfaceKey.EXCEL_SHEET_MESSAGE, msg);

                        dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID, paymentTypeId);
                        dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME, paymentTypeName);

                        Vector PaymentTypeExcelModels = MemoDAO.getMemoTypeExcelAttributes(mConSDSConnection, paymentTypeId);
                        dataHashMap.put(PaymentInterfaceKey.PAYMENT_TYPE_EXCEL_ATTRIBUTE, PaymentTypeExcelModels);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_VIEW_MEMO_EXCEL_ATTRIBUTES);
                    } else {
                        msg = "NOT_UNIQUE";
                        dataHashMap.put(PaymentInterfaceKey.EXCEL_SHEET_MESSAGE, msg);

                        dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID, paymentTypeId);
                        dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME, paymentTypeName);

                        Vector PaymentTypeExcelModels = MemoDAO.getMemoTypeExcelAttributes(mConSDSConnection, paymentTypeId);
                        dataHashMap.put(PaymentInterfaceKey.PAYMENT_TYPE_EXCEL_ATTRIBUTE, PaymentTypeExcelModels);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_VIEW_MEMO_EXCEL_ATTRIBUTES);
                    }

                }
                break;
                case ACTION_INSERT_MEMO_EXCEL_ATTRIBUTES: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    String msg = "";

                    String paymentTypeId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID);
                    String paymentTypeName = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME);
                    String sheetNumber = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_NEW_EXCEL_SHEET_NUMBER);
                    String sheetName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_NEW_EXCEL_SHEET_NAME);
                    String tempId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_SQL_TEMPLATE_ID);
                    System.out.println("tempId: " + tempId);
                    String sqlStatement = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_NEW_EXCEL_SHEET_SQL_STATEMENT);

                    if (MemoDAO.checkExcelSheetUniqueness(mConSDSConnection, paymentTypeId, sheetNumber)) {

                        MemoDAO.insertMemoExcelAttributes(mConSDSConnection, paymentTypeId, sheetNumber, sheetName, sqlStatement, tempId);

                        dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID, paymentTypeId);
                        dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME, paymentTypeName);

                        Vector PaymentTypeExcelModels = MemoDAO.getMemoTypeExcelAttributes(mConSDSConnection, paymentTypeId);
                        dataHashMap.put(PaymentInterfaceKey.PAYMENT_TYPE_EXCEL_ATTRIBUTE, PaymentTypeExcelModels);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_VIEW_MEMO_EXCEL_ATTRIBUTES);


                    } else {
                        msg = "NOT_UNIQUE";

                        dataHashMap.put(PaymentInterfaceKey.EXCEL_SHEET_MESSAGE, msg);

                        dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID, paymentTypeId);
                        dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME, paymentTypeName);

                        Vector PaymentTypeExcelModels = MemoDAO.getMemoTypeExcelAttributes(mConSDSConnection, paymentTypeId);

                        dataHashMap.put(PaymentInterfaceKey.PAYMENT_TYPE_EXCEL_ATTRIBUTE, PaymentTypeExcelModels);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_VIEW_MEMO_EXCEL_ATTRIBUTES);

                    }


                }
                break;
                case ACTION_VIEW_INSERT_MEMO_EXCEL_ATTRIBUTES: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    String paymentTypeId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID);
                    String paymentTypeName = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME);

                    dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID, paymentTypeId);

                    dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME, paymentTypeName);

                }
                break;
                case ACTION_VIEW_MEMO_SETTINGS: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    // Vector paymentTypes=PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    // dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES,paymentTypes);

                    Vector memoSettings = MemoDAO.getMemoGenerationSettings(mConSDSConnection);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_PAYMENT_TYPE_GENERATION_SETTINGS, memoSettings);





                }
                break;
                case ACTION_VIEW_EDIT_MEMO_SETTINGS: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String memoSettingsId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_SETTINGS_ID);


                    MemoPaymentTypeSettingsModel model = null;
                    model = MemoDAO.getMemoPaymentTypeSettings(mConSDSConnection, memoSettingsId);


                    if (model.getGenerationPeriod().compareTo("WEEKLY") == 0) {

                        model.setWeeks(MemoDAO.getMemoWeekSettings(mConSDSConnection, memoSettingsId));
                    } else if (model.getGenerationPeriod().compareTo("MONTHLY") == 0) {

                        model.setMonths(MemoDAO.getMemoMonthSettings(mConSDSConnection, memoSettingsId));

                    } else if (model.getGenerationPeriod().compareTo("QUARTERLY") == 0) {

                        model.setQuarters(MemoDAO.getMemoQuarterSettings(mConSDSConnection, memoSettingsId));

                    }

                    dataHashMap.put(MemoInterfaceKey.PAYMENT_TYPE_GENERATION_SETTINGS, model);
                    Vector memoGenerationPeriods = MemoDAO.getMemoGenerationPeriods(mConSDSConnection);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_GENERATION_PERIODS, memoGenerationPeriods);



                }
                break;
                case ACTION_UPDATE_MEMO_SETTINGS: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String memoSettingsId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_SETTINGS_ID);


                    String periodId = (String) paramHashMap.get(MemoInterfaceKey.SELECTED_MEMO_GENERATION_PERIOD);

                    String trackingFlag = (String) paramHashMap.get(MemoInterfaceKey.SELECTED_MEMO_TRACKING_FLAG);

                    MemoDAO.deleteMemoSettings(mConSDSConnection, memoSettingsId);
                    if (Integer.parseInt(periodId) == 1) {
                        for (int j = 0; j < paramHashMap.size(); j++) {
                            String tempWeekString = (String) paramHashMap.keySet().toArray()[j];

                            if (tempWeekString.startsWith("weekNo")) {
                                String keyValue = (String) paramHashMap.get(tempWeekString);

                                if (keyValue == null || keyValue == "") {
                                    keyValue = "-1";
                                }
                                int weekID = new Integer(Integer.parseInt(tempWeekString.substring(7, tempWeekString.length()))).intValue();

                                //insert in to the week settings
                                MemoDAO.insertMemoWeekSettings(mConSDSConnection, memoSettingsId, weekID, keyValue);

                            }

                        }


                    } else if (Integer.parseInt(periodId) == 2) {

                        for (int j = 0; j < paramHashMap.size(); j++) {
                            String tempMonthString = (String) paramHashMap.keySet().toArray()[j];

                            if (tempMonthString.startsWith("monthNo")) {
                                String keyValue = (String) paramHashMap.get(tempMonthString);

                                if (keyValue == null || keyValue == "") {
                                    keyValue = "-1";
                                }
                                int monthID = new Integer(Integer.parseInt(tempMonthString.substring(8, tempMonthString.length()))).intValue();

                                //insert in to the week settings
                                MemoDAO.insertMemoMonthSettings(mConSDSConnection, memoSettingsId, monthID + 1, keyValue);

                            }

                        }


                    } else if (Integer.parseInt(periodId) == 3) {
                        int arr[][] = new int[4][2];
                        for (int j = 0; j < paramHashMap.size(); j++) {
                            String tempQrtrString = (String) paramHashMap.keySet().toArray()[j];

                            if (tempQrtrString.startsWith("qrtrMonth")) {
                                String keyValue = (String) paramHashMap.get(tempQrtrString);

                                if (keyValue == null || keyValue == "") {
                                    keyValue = "-1";
                                }
                                int qrtrID = new Integer(Integer.parseInt(tempQrtrString.substring(9, tempQrtrString.length()))).intValue();
                                if (qrtrID == 1) {
                                    arr[0][0] = Integer.parseInt(keyValue);
                                } else if (qrtrID == 2) {
                                    arr[1][0] = Integer.parseInt(keyValue);
                                } else if (qrtrID == 3) {
                                    arr[2][0] = Integer.parseInt(keyValue);
                                } else if (qrtrID == 4) {
                                    arr[3][0] = Integer.parseInt(keyValue);
                                }

                            }

                            if (tempQrtrString.startsWith("qrtrDay")) {
                                String keyValue = (String) paramHashMap.get(tempQrtrString);

                                if (keyValue == null || keyValue == "") {
                                    keyValue = "-1";
                                }
                                int qrtrID = new Integer(Integer.parseInt(tempQrtrString.substring(7, tempQrtrString.length()))).intValue();

                                if (qrtrID == 1) {
                                    arr[0][1] = Integer.parseInt(keyValue);
                                } else if (qrtrID == 2) {
                                    arr[1][1] = Integer.parseInt(keyValue);
                                } else if (qrtrID == 3) {
                                    arr[2][1] = Integer.parseInt(keyValue);
                                } else if (qrtrID == 4) {
                                    arr[3][1] = Integer.parseInt(keyValue);
                                }

                            }

                        }
                        for (int i = 0; i < 4; i++) {
                            MemoDAO.insertMemoQuarterSettings(mConSDSConnection, memoSettingsId, i + 1, arr[i][0], arr[i][1]);
                        }
                        //insert in to the week settings


                    }

                    MemoDAO.updateMemoGenerationSettings(mConSDSConnection, memoSettingsId, periodId, trackingFlag);

                    Vector memoSettings = MemoDAO.getMemoGenerationSettings(mConSDSConnection);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_PAYMENT_TYPE_GENERATION_SETTINGS, memoSettings);

                }
                break;
                case ACTION_VIEW_MEMO_PDF_MANAGEMENT: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);


                }
                break;
                case ACTION_VIEW_MEMO_PDF_ATTRIBUTES: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String paymentTypeId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID);
                    String paymentTypeName = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME);

                    dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID, paymentTypeId);
                    dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME, paymentTypeName);

                    Vector PaymentTypePdfModels = MemoDAO.getPaymentTypePdfAttributes(mConSDSConnection, paymentTypeId);

                    dataHashMap.put(PaymentInterfaceKey.PAYMENT_TYPE_PDF_ATTRIBUTE, PaymentTypePdfModels);
                }
                break;
                case ACTION_UPDATE_MEMO_PDF_ATTRIBUTES: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String paymentTypeId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID);

                    String paymentTypeName = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME);

                    String paymentPdfId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_EXCEL_MANAGEMENT_ID);

                    String queriesParams = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_QUERIES_PARAMS);

                    String sqlStatement = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_PDF_SQL_STATEMENT);

                    MemoDAO.updatePaymentPdfSheet(mConSDSConnection, paymentPdfId, queriesParams, sqlStatement);


                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_VIEW_MEMO_PDF_MANAGEMENT);


                }
                break;
                case ACTION_VIEW_INSERT_MEMO_PDF_ATTRIBUTES: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String paymentTypeId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID);
                    String paymentTypeName = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME);

                    dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID, paymentTypeId);

                    dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME, paymentTypeName);

                }
                break;
                case ACTION_INSERT_MEMO_PDF_ATTRIBUTES: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);


                    String paymentTypeId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID);

                    String paymentTypeName = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME);


                    String pdfName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_NEW_QUERIES_PARAMS);

                    String sqlStatement = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_NEW_PDF_SQL_STATEMENT);
                    String tempId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_SQL_TEMPLATE_ID);
                    System.out.println("tempId: " + tempId);


                    MemoDAO.insertPaymentPdfAttributes(mConSDSConnection, paymentTypeId, pdfName, sqlStatement, tempId);
                    dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID, paymentTypeId);
                    dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME, paymentTypeName);

                    Vector PaymentTypePdfModels = MemoDAO.getPaymentTypePdfAttributes(mConSDSConnection, paymentTypeId);

                    dataHashMap.put(PaymentInterfaceKey.PAYMENT_TYPE_PDF_ATTRIBUTE, PaymentTypePdfModels);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);

                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_VIEW_MEMO_PDF_MANAGEMENT);




                }
                break;
                case ACTION_VIEW_MEMO_PAYMENT_NAMES_TYPES: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    String memoId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_ID);
                    String memoName = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_NAME);

                    dataHashMap.put(MemoInterfaceKey.CONTROL_MEMO_ID, memoId);
                    dataHashMap.put(MemoInterfaceKey.CONTROL_MEMO_NAME, memoName);


                    Vector payments = MemoDAO.getMemoPayments(mConSDSConnection, memoId);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_PAYMENTS, payments);
//    	   Vector paymentTypes=PaymentScmStatusDao.getMemoPaymentTypes(mConSDSConnection,memoId);
//    	   dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES,paymentTypes);


                }
                break;

                case ACTION_DELETE_MEMO_PAYMENT_NAMES_TYPES: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    String memoId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_MEMO_ID);
                    System.out.println("memoId: " + memoId);
                    String paymentId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_PAYMENT_ID);
                    System.out.println("paymentId: " + paymentId);


                    MemoDAO.removePyamentInMemo(mConSDSConnection, memoId, paymentId, strUserID);




                    Vector memos = MemoDAO.searchSpecificCollectionMemo(mConSDSConnection, -1, "", "1", "", "", "", "", "", "");
                    dataHashMap.put(MemoInterfaceKey.VECTOR_ALL_MEMOS, memos);

                    //for the search part of the page
                    Vector memoTypes = MemoDAO.getMemoTypes(mConSDSConnection);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    Vector channels = MemoDAO.getDcmChannels(mConSDSConnection);
                    Vector subChannels = MemoDAO.getDcmSubChannels(mConSDSConnection);
                    Vector paymentMethod = MemoDAO.getPymentMethod(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE_METHOD, paymentMethod);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_SUB_CHANNELS, subChannels);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_MEMO_TYPES, memoTypes);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_CHANNELS, channels);

                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_VIEW_READY_MEMOS);

                }
                break;
                case ACTION_VIEW_PAYMENT_ELIGIBILITY_ADDITION: {
                    System.out.println("in view payment eligibility addition");
                }
                break;
                case ACTION_ADD_PYAMENT_DCM_ELIGIBLE: {
                    System.out.println("in add payment eligibility addition");
                    for (int j = 0; j < paramHashMap.size(); j++) {
                        String dcmId = (String) paramHashMap.keySet().toArray()[j];
                        System.out.println("dcmId: " + dcmId);

                        if (dcmId.startsWith(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE)) {
                            String keyValue = (String) paramHashMap.get(dcmId);
                            System.out.println("dcmCode: " + keyValue);

                            MemoDAO.setPaymentEligibleToDCM(mConSDSConnection, keyValue);
                        }
                    }

                }
                break;
                case ACTION_ADD_PYAMENT_DCM_ELIGIBLE_FROM_GEN: {
                    System.out.println("in add default dcm eligible");
                }
                break;
                case ACTION_VIEW_SQL_TEMPLATE_MANAGEMENT: {
                    Vector templates = MemoDAO.getSqlTemplates(mConSDSConnection, "");
                    dataHashMap.put(MemoInterfaceKey.VECTOR_SQL_TEMPLATES, templates);

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;

                case ACTION_view_ADD_SQL_TEMPLATE: {
                    // Vector templates=MemoDAO.getSqlTemplates(mConSDSConnection,"");
                    //dataHashMap.put(MemoInterfaceKey.VECTOR_SQL_TEMPLATES, templates);

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;

                case ACTION_ADD_SQL_TEMPLATE: {
                    // String templateId=(String)paramHashMap.get(MemoInterfaceKey.HIDDEN_SQL_TEMPLATE_ID);
                    String templateName = (String) paramHashMap.get(MemoInterfaceKey.SQL_TEMPLATE_NAME);
                    String templateSql = (String) paramHashMap.get(MemoInterfaceKey.SQL_TEMPLATE_SQL);
                    MemoDAO.addSqlTemplate(mConSDSConnection, templateSql, templateName);

                    Vector templates = MemoDAO.getSqlTemplates(mConSDSConnection, "");
                    dataHashMap.put(MemoInterfaceKey.VECTOR_SQL_TEMPLATES, templates);


                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;

                case ACTION_VIEW_EDIT_SQL_TEMPLATE_MANAGEMENT: {
                    String templateId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_SQL_TEMPLATE_ID);
                    Vector templates = MemoDAO.getSqlTemplates(mConSDSConnection, templateId);

                    dataHashMap.put(MemoInterfaceKey.VECTOR_SQL_TEMPLATES, templates);

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                }
                break;
                case ACTION_UPDATE_SQL_TEMPLATE_MANAGEMENT: {
                    String templateId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_SQL_TEMPLATE_ID);

                    String templateName = (String) paramHashMap.get(MemoInterfaceKey.SQL_TEMPLATE_NAME);
                    String templateSql = (String) paramHashMap.get(MemoInterfaceKey.SQL_TEMPLATE_SQL);

                    MemoDAO.updateSqlTemplate(mConSDSConnection, templateId, templateSql, templateName);

                    Vector templates = MemoDAO.getSqlTemplates(mConSDSConnection, "");
                    dataHashMap.put(MemoInterfaceKey.VECTOR_SQL_TEMPLATES, templates);


                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                }
                break;
                case ACTION_VIEW_SQL_TABLE_BKP_MANAGEMENT: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;
                case ACTION_CREATE_SQL_TABLE_BKP_FILE: {
                    String tableId = (String) paramHashMap.get(MemoInterfaceKey.SQL_TABLE_ID);
                    System.out.println("tableId: " + tableId);
                    dataHashMap.put(MemoInterfaceKey.SQL_TABLE_ID, tableId);
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String appName = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_APPLIACTION_CONTEXT);

//           if(Integer.parseInt(tableId)==1){
//         	  //extract payment excel file
//         	  System.out.println("extract payment excel file");
//         	  MemoDAO.createPaymentExcelsFile(appName);
//         	  //out.println("<form action=\""+appName+"/cam/template/payment excels.txt\" name=\"GenerateSheet\" method=\"post\">");
//         	 dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH, "/payment excels.zip");
//         	 dataHashMap.put(InterfaceKey.MODULE_SUB_PATH, "/cam/template");
//         	
//           }else if(Integer.parseInt(tableId)==2){
//         	  //extract memo excel file
//         	  System.out.println("extract memo excel file");
//         	  MemoDAO.createMemoExcelsFile(appName);
//         	  //out.println("<form action=\""+appName+"/cam/template/memo excels.txt\" name=\"GenerateSheet\" method=\"post\">");
//         	 dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH, "/memo excels.zip");
//         	 dataHashMap.put(InterfaceKey.MODULE_SUB_PATH, "/cam/template");
//         	
//           }else if(Integer.parseInt(tableId)==3){
//         	  //extract memo pdf file
//         	  System.out.println("extract memo pdf file");
//         	  MemoDAO.createMemoPdfsFile(appName);
//         	  //out.println("<form action=\""+appName+"/cam/template/memo pdfs.txt\" name=\"GenerateSheet\" method=\"post\">");
//         	 dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH, "/memo pdfs.zip");
//         	 dataHashMap.put(InterfaceKey.MODULE_SUB_PATH, "/cam/template");
//         	
//         	
//           }
                }
                break;


            }


            //Utility.closeConnection(mConSDSConnection);
        } catch (Exception objExp) {
            objExp.printStackTrace();
        }
        return dataHashMap;
    }
}
