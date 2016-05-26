package com.mobinil.sds.core.facade.handlers;

import com.mobinil.sds.core.system.sa.scratch_card.dao.ScratchCardDao;
import com.mobinil.sds.core.system.sa.activation.dao.*;
import com.mobinil.sds.core.system.sa.scratch_card.model.ScratchCardModel;

import com.mobinil.sds.core.system.sa.modules.dao.*;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.sa.*;
import com.mobinil.sds.core.system.sa.revenue.dao.*;
import com.mobinil.sds.core.system.gn.dcm.dao.*;
import com.mobinil.sds.core.system.gn.dcm.dto.*;
import com.mobinil.sds.core.system.cr.bundle.dao.*;
import com.mobinil.sds.core.system.cr.bundle.model.*;
import com.mobinil.sds.core.system.cam.common.model.*;
import com.mobinil.sds.core.system.cam.memo.dao.MemoDAO;
import com.mobinil.sds.core.utilities.Wrapper.*;
import java.sql.*;
import java.util.*;
import com.mobinil.sds.core.system.cr.sheet.dao.*;
import com.mobinil.sds.core.system.sa.product.dao.*;
import com.mobinil.sds.core.system.sa.importdata.dao.*;

import com.mobinil.sds.core.utilities.CachEngine;

import com.mobinil.sds.core.utilities.CachEngine;
import com.mobinil.sds.web.interfaces.cam.MemoInterfaceKey;
import com.mobinil.sds.web.interfaces.cam.PaymentInterfaceKey;
import com.mobinil.sds.core.system.cam.payment.dao.*;
import com.mobinil.sds.core.system.cam.payment.model.*;
import com.mobinil.sds.core.system.payment.dao.*;
import com.mobinil.sds.core.system.payment.model.*;
import com.mobinil.sds.core.system.payment.dto.*;
import com.mobinil.sds.core.system.cam.payment.dao.PaymentTypeDAO;

public class CamPaymentHandler {

    static final int View_SCM_Payment_Status = 1;
    static final int ACTION_EDIT_SCM_PAYMENT_STATUS = 2;
    static final int ACTION_DO_EDIT_SCM_PAYMENT_STATUS = 3;
    static final int ACTION_VIEW_PAYMENT_MANAGEMENT_MANUAL_AUTO = 4;
    static final int ACTION_SET_PAYMENT_MANAGEMENT_AUTO = 5;
    static final int ACTION_SET_PAYMENT_MANAGEMENT_MANUAL = 6;
    static final int ACTION_VIEW_DCM_EMAIL_LIST = 7;//"view_dcm_email_list";
    static final int ACTION_INSERT_DCM_EMAIL_NOTIFICATION = 8;
    static final int ACTION_SEARCH_SCM_STATUS = 9;
    static final int ACTION_VIEW_DISTRIBUTION_CHANNEL = 10;
    static final int ACTION_VIEW_FRANCHISE_CHANNEL = 11;
    static final int ACTION_SEARCH_DISTRIBUTION_CHANNEL = 12;
    static final int ACTION_SEARCH_FRANCHISE_CHANNEL = 13;
    static final int SEARCH_PAYMENT_TYPE_GROUP = 14;
    static final int VIEW_PAYMENT_TYPE_GROUP = 15;
    static final int NEW_PAYMENT_TYPE_GROUP = 16;
    static final int EDIT_PAYMENT_TYPE_GROUP = 17;
    static final int ADD_PAYMENT_TYPE_GROUP = 18;
    static final int UPDATE_PAYMENT_TYPE_GROUP = 19;
    static final int DELETE_PAYMENT_TYPE_GROUP = 20;
    static final int ACTION_UPDATE_EMAILS_NOTIFICATION = 21;
    static final int ACTION_NEW_EMAILS_NOTIFICATION = 22;
    static final int ACTION_SEARCH_EMAILS_NOTIFICATION_FOR_EDIT = 23;
    static final int ACTION_DO_UPDATE_EMAILS_NOTIFICATION = 24;
    static final int ACTION_INSERT_NEW_EMAIL_NOTIFICATION = 25;
    static final int ACTION_SEARCH_EMAILS_NOTIFICATION = 26;
    static final int ACTION_ADD_NEW_SCM_PAYMENT_STATUS = 27;
    static final int ACTION_IMPORT_SCM_PAYMENT_STATUS = 28;
    static final int ACTION_UPDATE_ALL_SCM_PAYMENT_STATUS = 29;
    static final int ACTION_GENERATE_PAYMENT_STATUS_TEMPLATE = 30;
    static final int ACTION_DELETE_EMAIL_NOTIFICATION = 31;
    static final int ACTION_EXPORT_DCM_PAYMENT_STATUS_EXCEL = 32;
    static final int ACTION_EXPORT_DCM_DISTRIBUTION_PAYMENT_STATUS_EXCEL = 33;
    static final int ACTION_EXPORT_DCM_PAYMENT_FRANCHISE_STATUS_EXCEL = 34;
    static final int ACTION_ADD_PAYMENT_REASON = 35;//for payment reason
    static final int ACTION_DELETE_PAYMENT_REASON = 36;//for payment reason
    static final int ACTION_UPDATE_PAYMENT_REASON = 37;//for payment reason
    static final int ACTION_VIEW_PAYMENT_REASON = 38;//for payment reason
    static final int ACTION_VIEW_ADD_PAYMENT_REASON = 39;
    static final int ACTION_VIEW_UPDATE_PAYMENT_REASON = 40;
    static final int ACTION_VIEW_COMPLEMENTARY_CHANNEL = 41;
    static final int ACTION_SEARCH_COMPLEMENTARY_CHANNEL = 42;
    static final int ACTION_GENERATE_CHANNEL_PAYMENT_STATUS_TEMPLATE = 43;
    static final int ACTION_IMPORT_CHANNEL_PAYMENT_STATUS = 44;
    static final int ACTION_VIEW_IMPORT_CHANNEL_PAYMENT_STATUS = 45;
    static final int ACTION_VIEW_STK_STATUS_MANAGEMENT = 46;
    static final int ACTION_SEARCH_STK_STATUS_MANAGEMENT = 47;
    static final int ACTION_UPDATE_STK_STATUS_MANAGEMENT = 48;
    static final int ACTION_EXPORT_DCM_PAYMENT_COMPLEMENTARY_STATUS_EXCEL = 49;
    static final int ACTION_INSERT_NEW_DCM_PAYMENT_STATUS = 50;
    static final int ACTION_INSERT_NEW_DCM_STK_STATUS = 51;
    static final int ACTION_VIEW_PAYMENT_EXCEL_MANAGEMENT = 52;
    static final int ACTION_VIEW_PAYMENT_EXCEL_ATTRIBUTES = 53;
    static final int ACTION_UPDATE_PAYMENT_EXCEL_ATTRIBUTES = 54;
    static final int ACTION_INSERT_PAYMENT_EXCEL_ATTRIBUTES = 55;
    static final int ACTION_VIEW_INSERT_PAYMENT_EXCEL_ATTRIBUTES = 56;
    static final int ACTION_VIEW_UPDATE_PAYMENT_EXCEL_ATTRIBUTES = 57;
    static final int ACTION_EDIT_SCM_PAYMENT_STATUS_DISTRIBUTION = 58;
    static final int ACTION_DO_EDIT_SCM_PAYMENT_STATUS_DISTRIBUTION = 59;
    static final int ACTION_EDIT_SCM_PAYMENT_STATUS_FRANCHISE = 60;
    static final int ACTION_DO_EDIT_SCM_PAYMENT_STATUS_FRANCHISE = 61;
    static final int ACTION_EDIT_SCM_PAYMENT_STATUS_COMPLEMENTARY = 62;
    static final int ACTION_DO_EDIT_SCM_PAYMENT_STATUS_COMPLEMENTARY = 63;
    static final int ACTION_UPDATE_CAM_PAYMENT = 64;
    static final int ACTION_SEARCH_CLOSED_PAYMENT = 65;
    static final int ACTION_UPDATE_PAYMENT_DETAIL = 66;
    static final int ACTION_RELEASE_PAYMENT = 67;
    static final int ACTION_SEARCH_RELEASE_PAYMENT = 68;
    static final int ACTION_RELEASE_FROZEN_PAYMENT = 69;
    static final int ACTION_FREEZE_PAYMENT = 70;
    static final int ACTION_SEARCH_FROZEN_PAYMENT = 71;
    static final int ACTION_SUBMIT_FREEZED_PAYMENT = 72;
    static final int ACTION_SHOW_CLOSED_PAYMENT = 73;
    static final int ACTION_GENERATE_PAYMENT_LIST = 74;
    static final int ACTION_GET_PAYMENT_LIST = 75;
    static final int ACTION_UPLOAD_PAYMENT_LIST = 76;
    private final static boolean debugFlag = false;

    private static void debug(String msg) {
        if (debugFlag) {
            System.out.println(msg);
        }
    }

    public static HashMap handle(String action, HashMap paramHashMap, java.sql.Connection mConSDSConnection) {
        int actionType = 0;
        HashMap dataHashMap = new HashMap(100);
        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

        if (strUserID != null && strUserID.compareTo("") != 0) {
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
        }
        try {
            //Connection mConSDSConnection = Utility.getConnection();

            if (action.compareTo(PaymentInterfaceKey.ACTION_VIEW_SCM_PAYMENT_STATUS) == 0) {
                actionType = View_SCM_Payment_Status;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_EDIT_SCM_PAYMENT_STATUS) == 0) {
                actionType = ACTION_EDIT_SCM_PAYMENT_STATUS;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_DO_EDIT_SCM_PAYMENT_STATUS) == 0) {
                actionType = ACTION_DO_EDIT_SCM_PAYMENT_STATUS;

            } else if (action.compareTo(PaymentInterfaceKey.ACTION_VIEW_PAYMENT_MANAGEMENT_MANUAL_AUTO) == 0) {
                actionType = ACTION_VIEW_PAYMENT_MANAGEMENT_MANUAL_AUTO;

            } else if (action.compareTo(PaymentInterfaceKey.ACTION_SET_PAYMENT_MANAGEMENT_AUTO) == 0) {
                actionType = ACTION_SET_PAYMENT_MANAGEMENT_AUTO;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_SET_PAYMENT_MANAGEMENT_MANUAL) == 0) {
                actionType = ACTION_SET_PAYMENT_MANAGEMENT_MANUAL;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_VIEW_DCM_EMAIL_LIST) == 0) {
                actionType = ACTION_VIEW_DCM_EMAIL_LIST;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_INSERT_DCM_EMAIL_NOTIFICATION) == 0) {
                actionType = ACTION_INSERT_DCM_EMAIL_NOTIFICATION;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_SEARCH_SCM_STATUS) == 0) {
                actionType = ACTION_SEARCH_SCM_STATUS;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_VIEW_DISTRIBUTION_CHANNEL) == 0) {
                actionType = ACTION_VIEW_DISTRIBUTION_CHANNEL;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_VIEW_FRANCHISE_CHANNEL) == 0) {
                actionType = ACTION_VIEW_FRANCHISE_CHANNEL;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_SEARCH_DISTRIBUTION_CHANNEL) == 0) {
                actionType = ACTION_SEARCH_DISTRIBUTION_CHANNEL;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_SEARCH_FRANCHISE_CHANNEL) == 0) {
                actionType = ACTION_SEARCH_FRANCHISE_CHANNEL;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_UPDATE_EMAILS_NOTIFICATION) == 0) {
                actionType = ACTION_UPDATE_EMAILS_NOTIFICATION;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_NEW_EMAILS_NOTIFICATION) == 0) {
                actionType = ACTION_NEW_EMAILS_NOTIFICATION;

            } else if (action.compareTo(PaymentInterfaceKey.ACTION_SEARCH_EMAILS_NOTIFICATION_FOR_EDIT) == 0) {
                actionType = ACTION_SEARCH_EMAILS_NOTIFICATION_FOR_EDIT;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_INSERT_NEW_EMAIL_NOTIFICATION) == 0) {
                actionType = ACTION_INSERT_NEW_EMAIL_NOTIFICATION;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_DO_UPDATE_EMAILS_NOTIFICATION) == 0) {
                actionType = ACTION_DO_UPDATE_EMAILS_NOTIFICATION;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_SEARCH_EMAILS_NOTIFICATION) == 0) {
                actionType = ACTION_SEARCH_EMAILS_NOTIFICATION;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_ADD_NEW_SCM_PAYMENT_STATUS) == 0) {
                actionType = ACTION_ADD_NEW_SCM_PAYMENT_STATUS;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_GENERATE_PAYMENT_STATUS_TEMPLATE) == 0) {
                actionType = ACTION_GENERATE_PAYMENT_STATUS_TEMPLATE;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_DELETE_EMAIL_NOTIFICATION) == 0) {
                actionType = ACTION_DELETE_EMAIL_NOTIFICATION;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_EXPORT_DCM_DISTRIBUTION_PAYMENT_STATUS_EXCEL) == 0) {
                actionType = ACTION_EXPORT_DCM_DISTRIBUTION_PAYMENT_STATUS_EXCEL;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_EXPORT_DCM_PAYMENT_FRANCHISE_STATUS_EXCEL) == 0) {
                actionType = ACTION_EXPORT_DCM_PAYMENT_FRANCHISE_STATUS_EXCEL;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_ADD_PAYMENT_REASON) == 0) {
                actionType = ACTION_ADD_PAYMENT_REASON;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_DELETE_PAYMENT_REASON) == 0) {
                actionType = ACTION_DELETE_PAYMENT_REASON;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_UPDATE_PAYMENT_REASON) == 0) {
                actionType = ACTION_UPDATE_PAYMENT_REASON;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_VIEW_PAYMENT_REASON) == 0) {
                actionType = ACTION_VIEW_PAYMENT_REASON;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_VIEW_ADD_PAYMENT_REASON) == 0) {
                actionType = ACTION_VIEW_ADD_PAYMENT_REASON;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_VIEW_UPDATE_PAYMENT_REASON) == 0) {
                actionType = ACTION_VIEW_UPDATE_PAYMENT_REASON;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_SEARCH_PAYMENT_TYPE_GROUP) == 0) {
                actionType = SEARCH_PAYMENT_TYPE_GROUP;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_VIEW_PAYMENT_TYPE_GROUP) == 0) {
                actionType = VIEW_PAYMENT_TYPE_GROUP;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_NEW_PAYMENT_TYPE_GROUP) == 0) {
                actionType = NEW_PAYMENT_TYPE_GROUP;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_EDIT_PAYMENT_TYPE_GROUP) == 0) {
                actionType = EDIT_PAYMENT_TYPE_GROUP;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_ADD_PAYMENT_TYPE_GROUP) == 0) {
                actionType = ADD_PAYMENT_TYPE_GROUP;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_UPDATE_PAYMENT_TYPE_GROUP) == 0) {
                actionType = UPDATE_PAYMENT_TYPE_GROUP;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_DELETE_PAYMENT_TYPE_GROUP) == 0) {
                actionType = DELETE_PAYMENT_TYPE_GROUP;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_IMPORT_SCM_PAYMENT_STATUS) == 0) {
                actionType = ACTION_IMPORT_SCM_PAYMENT_STATUS;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_UPDATE_ALL_SCM_PAYMENT_STATUS) == 0) {
                actionType = ACTION_UPDATE_ALL_SCM_PAYMENT_STATUS;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_EXPORT_DCM_PAYMENT_STATUS_EXCEL) == 0) {
                actionType = ACTION_EXPORT_DCM_PAYMENT_STATUS_EXCEL;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_VIEW_COMPLEMENTARY_CHANNEL) == 0) {
                actionType = ACTION_VIEW_COMPLEMENTARY_CHANNEL;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_SEARCH_COMPLEMENTARY_CHANNEL) == 0) {
                actionType = ACTION_SEARCH_COMPLEMENTARY_CHANNEL;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_GENERATE_CHANNEL_PAYMENT_STATUS_TEMPLATE) == 0) {
                actionType = ACTION_GENERATE_CHANNEL_PAYMENT_STATUS_TEMPLATE;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_IMPORT_CHANNEL_PAYMENT_STATUS) == 0) {
                actionType = ACTION_IMPORT_CHANNEL_PAYMENT_STATUS;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_VIEW_IMPORT_CHANNEL_PAYMENT_STATUS) == 0) {
                actionType = ACTION_VIEW_IMPORT_CHANNEL_PAYMENT_STATUS;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_VIEW_STK_STATUS_MANAGEMENT) == 0) {
                actionType = ACTION_VIEW_STK_STATUS_MANAGEMENT;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_SEARCH_STK_STATUS_MANAGEMENT) == 0) {
                actionType = ACTION_SEARCH_STK_STATUS_MANAGEMENT;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_UPDATE_STK_STATUS_MANAGEMENT) == 0) {
                actionType = ACTION_UPDATE_STK_STATUS_MANAGEMENT;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_EXPORT_DCM_PAYMENT_COMPLEMENTARY_STATUS_EXCEL) == 0) {
                actionType = ACTION_EXPORT_DCM_PAYMENT_COMPLEMENTARY_STATUS_EXCEL;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_INSERT_NEW_DCM_PAYMENT_STATUS) == 0) {
                actionType = ACTION_INSERT_NEW_DCM_PAYMENT_STATUS;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_INSERT_NEW_DCM_STK_STATUS) == 0) {
                actionType = ACTION_INSERT_NEW_DCM_STK_STATUS;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_VIEW_PAYMENT_EXCEL_MANAGEMENT) == 0) {
                actionType = ACTION_VIEW_PAYMENT_EXCEL_MANAGEMENT;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_VIEW_PAYMENT_EXCEL_ATTRIBUTES) == 0) {
                actionType = ACTION_VIEW_PAYMENT_EXCEL_ATTRIBUTES;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_UPDATE_PAYMENT_EXCEL_ATTRIBUTES) == 0) {
                actionType = ACTION_UPDATE_PAYMENT_EXCEL_ATTRIBUTES;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_INSERT_PAYMENT_EXCEL_ATTRIBUTES) == 0) {
                actionType = ACTION_INSERT_PAYMENT_EXCEL_ATTRIBUTES;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_VIEW_INSERT_PAYMENT_EXCEL_ATTRIBUTES) == 0) {
                actionType = ACTION_VIEW_INSERT_PAYMENT_EXCEL_ATTRIBUTES;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_VIEW_UPDATE_PAYMENT_EXCEL_ATTRIBUTES) == 0) {
                actionType = ACTION_VIEW_UPDATE_PAYMENT_EXCEL_ATTRIBUTES;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_EDIT_SCM_PAYMENT_STATUS_DISTRIBUTION) == 0) {
                actionType = ACTION_EDIT_SCM_PAYMENT_STATUS_DISTRIBUTION;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_DO_EDIT_SCM_PAYMENT_STATUS_DISTRIBUTION) == 0) {
                actionType = ACTION_DO_EDIT_SCM_PAYMENT_STATUS_DISTRIBUTION;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_EDIT_SCM_PAYMENT_STATUS_FRANCHISE) == 0) {
                actionType = ACTION_EDIT_SCM_PAYMENT_STATUS_FRANCHISE;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_DO_EDIT_SCM_PAYMENT_STATUS_FRANCHISE) == 0) {
                actionType = ACTION_DO_EDIT_SCM_PAYMENT_STATUS_FRANCHISE;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_EDIT_SCM_PAYMENT_STATUS_COMPLEMENTARY) == 0) {
                actionType = ACTION_EDIT_SCM_PAYMENT_STATUS_COMPLEMENTARY;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_DO_EDIT_SCM_PAYMENT_STATUS_COMPLEMENTARY) == 0) {
                actionType = ACTION_DO_EDIT_SCM_PAYMENT_STATUS_COMPLEMENTARY;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_UPDATE_CAM_PAYMENT) == 0) {
                actionType = ACTION_UPDATE_CAM_PAYMENT;

            } else if (action.compareTo(PaymentInterfaceKey.ACTION_SEARCH_CLOSED_PAYMENT) == 0) {
                actionType = ACTION_SEARCH_CLOSED_PAYMENT;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_RELEASE_PAYMENT) == 0) {
                actionType = ACTION_RELEASE_PAYMENT;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_UPDATE_PAYMENT_DETAIL) == 0) {
                actionType = ACTION_UPDATE_PAYMENT_DETAIL;

            } else if (action.compareTo(PaymentInterfaceKey.ACTION_SEARCH_RELEASE_PAYMENT) == 0) {
                actionType = ACTION_SEARCH_RELEASE_PAYMENT;

            } else if (action.compareTo(PaymentInterfaceKey.ACTION_RELEASE_FROZEN_PAYMENT) == 0) {
                actionType = ACTION_RELEASE_FROZEN_PAYMENT;

            } else if (action.compareTo(PaymentInterfaceKey.ACTION_FREEZE_PAYMENT) == 0) {
                actionType = ACTION_FREEZE_PAYMENT;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_SEARCH_FROZEN_PAYMENT) == 0) {
                actionType = ACTION_SEARCH_FROZEN_PAYMENT;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_SUBMIT_FREEZED_PAYMENT) == 0) {
                actionType = ACTION_SUBMIT_FREEZED_PAYMENT;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_SHOW_CLOSED_PAYMENT) == 0) {
                actionType = ACTION_SHOW_CLOSED_PAYMENT;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_GENERATE_PAYMENT_LIST) == 0) {
                actionType = ACTION_GENERATE_PAYMENT_LIST;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_GET_PAYMENT_LIST) == 0) {
                actionType = ACTION_GET_PAYMENT_LIST;
            } else if (action.compareTo(PaymentInterfaceKey.ACTION_UPLOAD_PAYMENT_LIST) == 0) {
                actionType = ACTION_UPLOAD_PAYMENT_LIST;
            }



            switch (actionType) {
                case View_SCM_Payment_Status: {
                    try {

                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                        Vector scmPaymentStatus = new Vector();//=PaymentScmStatusDao.viewPaymentScmStatus(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_ALL_SCM_PAYMENT, scmPaymentStatus);
                        Vector paymentStatusCases = PaymentScmStatusDao.getPaymentStatusCases(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES, paymentStatusCases);
                        Vector statusReason = PaymentScmStatusDao.getPaymentStatusReasons(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, statusReason);

                        Vector dates = PaymentScmStatusDao.viewPaymentScreenManagementAuto(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_AUTO_DATES, dates);
                        Vector flag = PaymentScmStatusDao.viewPaymentScreenManagementManual(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_MANUAL_FLAG, flag);






                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_EDIT_SCM_PAYMENT_STATUS: {
                    try {

                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String scmCode = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_EDIT_TEXT_SCM_CODE);

                        Vector scmPaymentStatus = PaymentScmStatusDao.searchScmStatus(mConSDSConnection, scmCode, "", "", "", 1);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_ALL_SCM_PAYMENT, scmPaymentStatus);
                        Vector everyScmPaymentStatus = PaymentScmStatusDao.searchEveryScmStatus(mConSDSConnection, scmCode, "", "", "", 1);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS, everyScmPaymentStatus);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                        Vector paymentStatusCases = PaymentScmStatusDao.getPaymentStatusCases(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES, paymentStatusCases);
                        Vector statusReason = PaymentScmStatusDao.getPaymentStatusReasons(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, statusReason);





                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }

                }
                break;
                case ACTION_DO_EDIT_SCM_PAYMENT_STATUS: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    String scmId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_SCM_PAYMENT_ID);

                    String statusId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID);

                    String reasonId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SCM_PAYMENT_STATUS_REASON_ID);

                    PaymentScmStatusDao.editPaymentScmStatus(mConSDSConnection, scmId, statusId, reasonId, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, PaymentInterfaceKey.ACTION_VIEW_SCM_PAYMENT_STATUS);

                    Vector scmPaymentStatus = new Vector();//=PaymentScmStatusDao.viewPaymentScmStatus(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_ALL_SCM_PAYMENT, scmPaymentStatus);
                    Vector paymentStatusCases = PaymentScmStatusDao.getPaymentStatusCases(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES, paymentStatusCases);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    Vector dates = PaymentScmStatusDao.viewPaymentScreenManagementAuto(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_AUTO_DATES, dates);
                    Vector flag = PaymentScmStatusDao.viewPaymentScreenManagementManual(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_MANUAL_FLAG, flag);
                    Vector statusReason = PaymentScmStatusDao.getPaymentStatusReasons(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, statusReason);
                }
                break;
                case ACTION_EDIT_SCM_PAYMENT_STATUS_DISTRIBUTION: {
                    try {

                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String scmCode = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_EDIT_TEXT_SCM_CODE);

                        Vector scmPaymentStatus = PaymentScmStatusDao.searchScmStatus(mConSDSConnection, scmCode, "", "", "", 1);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_ALL_SCM_PAYMENT, scmPaymentStatus);
                        Vector everyScmPaymentStatus = PaymentScmStatusDao.searchEveryScmStatus(mConSDSConnection, scmCode, "", "", "", 1);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS, everyScmPaymentStatus);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                        Vector paymentStatusCases = PaymentScmStatusDao.getPaymentStatusCases(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES, paymentStatusCases);
                        Vector statusReason = PaymentScmStatusDao.getPaymentStatusReasons(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, statusReason);





                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;





                case ACTION_SEARCH_CLOSED_PAYMENT: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    String payment = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_PAYMENT_ID);
                    String paymentId = payment.trim();
                    String paymentN = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_PAYMENT_NAME);
                    String paymentName = paymentN.trim();
                    if (!paymentName.equals("") && paymentId.equals("")) {

                        Vector searchClosedPayment = PaymentScmStatusDao.getDeleteClosedPaymentForm(mConSDSConnection, paymentName);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_SEARCH_CLOSED_PAYMENT, searchClosedPayment);


                    } else if (!paymentId.equals("") && paymentName.equals("")) {

                        Vector searchClosedPayment = PaymentScmStatusDao.getDeleteClosedPaymentForm(mConSDSConnection, Integer.parseInt(paymentId));
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_SEARCH_CLOSED_PAYMENT, searchClosedPayment);


                    } else if (!paymentId.equals("") && !paymentName.equals("")) {

                        Vector searchClosedPayment = PaymentScmStatusDao.getDeleteClosedPaymentForm(mConSDSConnection, Integer.parseInt(paymentId), paymentName);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_SEARCH_CLOSED_PAYMENT, searchClosedPayment);


                    } else if (paymentId.equals("") && paymentName.equals("")) {

                        Vector searchClosedPayment = PaymentScmStatusDao.getDeleteClosedPaymentForm(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_SEARCH_CLOSED_PAYMENT, searchClosedPayment);


                    }



                    System.out.println("Payment Id:" + paymentId);
                    System.out.println("Payment Name:" + paymentName);


                }
                break;



                case ACTION_SEARCH_RELEASE_PAYMENT: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    String payment = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_RELEASED_PAYMENT_NAME);
                    String paymentName = payment.trim();
                    String paymentT = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_PAYMENT_TYPE);
                    String paymentType = paymentT.trim();
                    String dcmCod = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_DCM_CODE);
                    String dcmCode = dcmCod.trim();


                    if (!paymentName.equals("") && paymentType.equals("") && dcmCode.equals("")) {

                        Vector searchReleasedPayment = PaymentScmStatusDao.getReleasePaymentFormByPaymentName(mConSDSConnection, paymentName);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_SEARCH_RELEASED_PAYMENT, searchReleasedPayment);


                    } else if (!paymentType.equals("") && paymentName.equals("") && dcmCode.equals("")) {

                        Vector searchReleasedPayment = PaymentScmStatusDao.getReleasePaymentFormByPaymentTypeName(mConSDSConnection, Integer.parseInt(paymentType));
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_SEARCH_RELEASED_PAYMENT, searchReleasedPayment);

                    } else if (!dcmCode.equals("") && paymentName.equals("") && paymentType.equals("")) {

                        Vector searchReleasedPayment = PaymentScmStatusDao.getReleasePaymentFormByDCMCode(mConSDSConnection, dcmCode);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_SEARCH_RELEASED_PAYMENT, searchReleasedPayment);

                    }

                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_RELEASE_TYPES, paymentTypes);



                }
                break;

                case ACTION_SEARCH_FROZEN_PAYMENT: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    String payment = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_FREEZE_PAYMENT_NAME);
                    String paymentName = payment.trim();
                    String paymentT = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_FREEZE_PAYMENT_TYPE);
                    String paymentType = paymentT.trim();
                    String dcmCod = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_FREEZE_DCM_CODE);
                    String dcmCode = dcmCod.trim();


                    if (!paymentName.equals("") && paymentType.equals("") && dcmCode.equals("")) {

                        Vector searchFrozenPayment = PaymentScmStatusDao.getFreezePaymentFormByPaymentName(mConSDSConnection, paymentName);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_SEARCH_FROZEN_PAYMENT, searchFrozenPayment);


                    } else if (!paymentType.equals("") && paymentName.equals("") && dcmCode.equals("")) {

                        Vector searchFrozenPayment = PaymentScmStatusDao.getFreezePaymentFormByPaymentTypeName(mConSDSConnection, Integer.parseInt(paymentType));
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_SEARCH_FROZEN_PAYMENT, searchFrozenPayment);

                    } else if (!dcmCode.equals("") && paymentName.equals("") && paymentType.equals("")) {

                        Vector searchFrozenPayment = PaymentScmStatusDao.getFreezePaymentFormByDCMCode(mConSDSConnection, dcmCode);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_SEARCH_FROZEN_PAYMENT, searchFrozenPayment);

                    }

                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_FREEZE_TYPES, paymentTypes);



                }
                break;


                case ACTION_UPDATE_PAYMENT_DETAIL: {
                    String paymentDetailId = (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_DETAIL_ID);
                    PaymentScmStatusDao.updatePaymentStatusTypeId(mConSDSConnection, paymentDetailId);
                    PaymentScmStatusDao.deletePaymentStatusTypeId(mConSDSConnection, paymentDetailId);
                    Vector searchClosedPayment = PaymentScmStatusDao.getDeleteClosedPaymentForm(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_SEARCH_CLOSED_PAYMENT, searchClosedPayment);

                }
                break;



                case ACTION_RELEASE_FROZEN_PAYMENT: {
                    System.out.println("m1");

                    String paymentDetailId = (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_RELEASED_PAYMENT_DETAIL_ID);
                    System.out.println("m2");
                    String scmCode = (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_SCM_ID);
                    System.out.println("m3");
                    String reason = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_INPUT_REASON);
                    System.out.println("m4");
                    System.out.println("paymentDetailId : " + paymentDetailId);
                    System.out.println("scmCode : " + scmCode);
                    System.out.println("reason : " + reason);
                    int x = Integer.parseInt(paymentDetailId);
                    System.out.println("Converter : " + " x= " + x);
                    PaymentScmStatusDao.releasePayment(mConSDSConnection, Integer.parseInt(paymentDetailId), scmCode, reason);
                    System.out.println("m5");

                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_RELEASE_TYPES, paymentTypes);
                    System.out.println("m6");

                }
                break;


                case ACTION_SUBMIT_FREEZED_PAYMENT: {


                    String paymentDetailId = (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_FREEZED_PAYMENT_DETAIL_ID);
                    String scmCode = (String) paramHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_FREEZED_SCM_ID);
                    String reason = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_INPUT_FREEZE_REASON);
                    PaymentScmStatusDao.freezPayment(mConSDSConnection, Integer.parseInt(paymentDetailId), scmCode, reason);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_FREEZE_TYPES, paymentTypes);


                }
                break;


                case ACTION_DO_EDIT_SCM_PAYMENT_STATUS_DISTRIBUTION: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    String scmId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_SCM_PAYMENT_ID);

                    String statusId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID);

                    String reasonId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SCM_PAYMENT_STATUS_REASON_ID);

                    PaymentScmStatusDao.editPaymentScmStatus(mConSDSConnection, scmId, statusId, reasonId, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, PaymentInterfaceKey.ACTION_VIEW_SCM_PAYMENT_STATUS);

                    Vector scmPaymentStatus = new Vector();//=PaymentScmStatusDao.viewPaymentScmStatus(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_ALL_SCM_PAYMENT, scmPaymentStatus);
                    Vector paymentStatusCases = PaymentScmStatusDao.getPaymentStatusCases(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES, paymentStatusCases);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    Vector dates = PaymentScmStatusDao.viewPaymentScreenManagementAuto(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_AUTO_DATES, dates);
                    Vector flag = PaymentScmStatusDao.viewPaymentScreenManagementManual(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_MANUAL_FLAG, flag);
                    Vector statusReason = PaymentScmStatusDao.getPaymentStatusReasons(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, statusReason);
                }
                break;
                case ACTION_EDIT_SCM_PAYMENT_STATUS_FRANCHISE: {
                    try {

                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String scmCode = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_EDIT_TEXT_SCM_CODE);

                        Vector scmPaymentStatus = PaymentScmStatusDao.searchScmStatus(mConSDSConnection, scmCode, "", "", "", 1);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_ALL_SCM_PAYMENT, scmPaymentStatus);
                        Vector everyScmPaymentStatus = PaymentScmStatusDao.searchEveryScmStatus(mConSDSConnection, scmCode, "", "", "", 1);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS, everyScmPaymentStatus);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                        Vector paymentStatusCases = PaymentScmStatusDao.getPaymentStatusCases(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES, paymentStatusCases);
                        Vector statusReason = PaymentScmStatusDao.getPaymentStatusReasons(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, statusReason);





                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_DO_EDIT_SCM_PAYMENT_STATUS_FRANCHISE: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    String scmId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_SCM_PAYMENT_ID);

                    String statusId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID);

                    String reasonId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SCM_PAYMENT_STATUS_REASON_ID);

                    PaymentScmStatusDao.editPaymentScmStatus(mConSDSConnection, scmId, statusId, reasonId, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, PaymentInterfaceKey.ACTION_VIEW_SCM_PAYMENT_STATUS);

                    Vector scmPaymentStatus = new Vector();//=PaymentScmStatusDao.viewPaymentScmStatus(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_ALL_SCM_PAYMENT, scmPaymentStatus);
                    Vector paymentStatusCases = PaymentScmStatusDao.getPaymentStatusCases(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES, paymentStatusCases);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    Vector dates = PaymentScmStatusDao.viewPaymentScreenManagementAuto(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_AUTO_DATES, dates);
                    Vector flag = PaymentScmStatusDao.viewPaymentScreenManagementManual(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_MANUAL_FLAG, flag);
                    Vector statusReason = PaymentScmStatusDao.getPaymentStatusReasons(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, statusReason);
                }
                break;
                case ACTION_EDIT_SCM_PAYMENT_STATUS_COMPLEMENTARY: {
                    try {

                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String scmCode = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_EDIT_TEXT_SCM_CODE);

                        Vector scmPaymentStatus = PaymentScmStatusDao.searchScmStatus(mConSDSConnection, scmCode, "", "", "", 1);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_ALL_SCM_PAYMENT, scmPaymentStatus);
                        Vector everyScmPaymentStatus = PaymentScmStatusDao.searchEveryScmStatus(mConSDSConnection, scmCode, "", "", "", 1);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS, everyScmPaymentStatus);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                        Vector paymentStatusCases = PaymentScmStatusDao.getPaymentStatusCases(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES, paymentStatusCases);
                        Vector statusReason = PaymentScmStatusDao.getPaymentStatusReasons(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, statusReason);





                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_DO_EDIT_SCM_PAYMENT_STATUS_COMPLEMENTARY: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    String scmId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_SCM_PAYMENT_ID);

                    String statusId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID);

                    String reasonId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SCM_PAYMENT_STATUS_REASON_ID);

                    PaymentScmStatusDao.editPaymentScmStatus(mConSDSConnection, scmId, statusId, reasonId, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, PaymentInterfaceKey.ACTION_VIEW_SCM_PAYMENT_STATUS);

                    Vector scmPaymentStatus = new Vector();//=PaymentScmStatusDao.viewPaymentScmStatus(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_ALL_SCM_PAYMENT, scmPaymentStatus);
                    Vector paymentStatusCases = PaymentScmStatusDao.getPaymentStatusCases(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES, paymentStatusCases);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    Vector dates = PaymentScmStatusDao.viewPaymentScreenManagementAuto(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_AUTO_DATES, dates);
                    Vector flag = PaymentScmStatusDao.viewPaymentScreenManagementManual(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_MANUAL_FLAG, flag);
                    Vector statusReason = PaymentScmStatusDao.getPaymentStatusReasons(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, statusReason);
                }
                break;
                case ACTION_VIEW_PAYMENT_MANAGEMENT_MANUAL_AUTO: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    Vector flag = PaymentScmStatusDao.viewPaymentScreenManagementManual(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_MANUAL_FLAG, flag);
                    Vector dates = PaymentScmStatusDao.viewPaymentScreenManagementAuto(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_AUTO_DATES, dates);
                    String manualOrAuto = PaymentScmStatusDao.getAutoOrManual(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_WORKING_MODE, manualOrAuto);
                }
                break;
                case ACTION_SET_PAYMENT_MANAGEMENT_MANUAL: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    String flag = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_PAYMENT_SCREEN_MANAGEMENT_FLAG);
                    String manOrAuto = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_MANUAL_OR_AUTO);
                    PaymentScmStatusDao.setPaymentScreenManagementManual(mConSDSConnection, flag, manOrAuto);
                    Vector flags = PaymentScmStatusDao.viewPaymentScreenManagementManual(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_MANUAL_FLAG, flags);
                    Vector dates = PaymentScmStatusDao.viewPaymentScreenManagementAuto(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_AUTO_DATES, dates);
                    String manualOrAuto = PaymentScmStatusDao.getAutoOrManual(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_WORKING_MODE, manualOrAuto);
                }
                break;
                case ACTION_SET_PAYMENT_MANAGEMENT_AUTO: {



                    //strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

                    String start = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_INSERTED_START_DATE);
                    String end = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_INSERTED_END_DATE);

                    String manOrAuto = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_MANUAL_OR_AUTO);
                    PaymentScmStatusDao.setPaymentScreenManagementAuto(mConSDSConnection, start, end, manOrAuto);

                    Vector flag = PaymentScmStatusDao.viewPaymentScreenManagementManual(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_MANUAL_FLAG, flag);
                    Vector dates = PaymentScmStatusDao.viewPaymentScreenManagementAuto(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_AUTO_DATES, dates);
                    String manualOrAuto = PaymentScmStatusDao.getAutoOrManual(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_WORKING_MODE, manualOrAuto);
                }
                break;
                case ACTION_VIEW_DCM_EMAIL_LIST: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    Vector emailList = PaymentScmStatusDao.searchPaymentEmployeeEmails(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_DCM_EMAIL_LIST, emailList);

                }
                break;
                case ACTION_INSERT_DCM_EMAIL_NOTIFICATION: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    String scmName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_SCM_NAME_EMAIL);
                    String scmEmail = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_SCM_EMAIL);
                    PaymentScmStatusDao.setPaymentDcmEmails(mConSDSConnection, scmName, scmEmail);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;
                case ACTION_SEARCH_SCM_STATUS: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    //for paging 
                    String search = (String) paramHashMap.get(PaymentInterfaceKey.First_Time_Search);
                    int pageNum = Integer.parseInt(paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString());
                    debug("pageNum: " + pageNum);

                    String scmCode = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE);
                    debug("scmCode: " + scmCode);
                    String scmName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME);
                    debug("scmName: " + scmName);
                    String statusId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID);
                    debug("statusId: " + statusId);
                    String reasonId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_REASON_ID);
                    debug("reasonId: " + reasonId);

                    Vector paymentStatusCases = PaymentScmStatusDao.getPaymentStatusCases(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES, paymentStatusCases);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);

                    Vector ScmPaymentStatus = PaymentScmStatusDao.searchScmStatus(mConSDSConnection, scmCode, scmName, statusId, reasonId, pageNum);
                    Vector everyScmPaymentStatus = PaymentScmStatusDao.searchEveryScmStatus(mConSDSConnection, scmCode, scmName, statusId, reasonId, pageNum);
                    Vector statusReason = PaymentScmStatusDao.getPaymentStatusReasons(mConSDSConnection);
                    int totalPages = PaymentScmStatusDao.getDCMCount(mConSDSConnection, scmCode, scmName, statusId, reasonId, -1);

                    if (totalPages % 50 == 0) {
                        totalPages = (totalPages / 50);
                    } else {
                        totalPages = (totalPages / 50);
                        totalPages++;
                    }

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_HIDDEN_TOTAL_PAGES, String.valueOf(totalPages));
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER, paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER));

                    dataHashMap.put(PaymentInterfaceKey.VECTOR_ALL_SCM_PAYMENT, ScmPaymentStatus);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS, everyScmPaymentStatus);

                    dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, statusReason);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    Vector dates = PaymentScmStatusDao.viewPaymentScreenManagementAuto(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_AUTO_DATES, dates);
                    Vector flag = PaymentScmStatusDao.viewPaymentScreenManagementManual(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_MANUAL_FLAG, flag);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE, scmCode);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME, scmName);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID, statusId);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_REASON_ID, reasonId);




                }
                break;
                case ACTION_EXPORT_DCM_PAYMENT_STATUS_EXCEL: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    //for paging 
                    String search = (String) paramHashMap.get(PaymentInterfaceKey.First_Time_Search);
                    int pageNum = Integer.parseInt(paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString());

                    String scmCode = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE);
                    String scmName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME);
                    String statusId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_CHANNEL_ID, "-1");

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE, scmCode);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME, scmName);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID, statusId);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER, paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER));

                }
                break;
                case ACTION_EXPORT_DCM_DISTRIBUTION_PAYMENT_STATUS_EXCEL: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    //for paging 
                    String search = (String) paramHashMap.get(PaymentInterfaceKey.First_Time_Search);
                    int pageNum = Integer.parseInt(paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString());

                    String scmCode = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE);
                    String scmName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME);
                    String statusId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_CHANNEL_ID, "1");

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE, scmCode);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME, scmName);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID, statusId);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER, paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER));

                }
                break;
                case ACTION_EXPORT_DCM_PAYMENT_FRANCHISE_STATUS_EXCEL: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    //for paging 
                    String search = (String) paramHashMap.get(PaymentInterfaceKey.First_Time_Search);
                    int pageNum = Integer.parseInt(paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString());

                    String scmCode = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE);
                    String scmName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME);
                    String statusId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_CHANNEL_ID, "2");

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE, scmCode);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME, scmName);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID, statusId);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER, paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER));

                }
                break;
                case ACTION_EXPORT_DCM_PAYMENT_COMPLEMENTARY_STATUS_EXCEL: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    //for paging 
                    String search = (String) paramHashMap.get(PaymentInterfaceKey.First_Time_Search);
                    int pageNum = Integer.parseInt(paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString());

                    String scmCode = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE);
                    String scmName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME);
                    String statusId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_CHANNEL_ID, "2");

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE, scmCode);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME, scmName);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID, statusId);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER, paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER));


                }
                break;
                case ACTION_UPDATE_ALL_SCM_PAYMENT_STATUS: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    for (int j = 0; j < paramHashMap.size(); j++) {
                        String tempStatusString = (String) paramHashMap.keySet().toArray()[j];

                        if (tempStatusString.startsWith("selectPaymentStatus")) {
                            String keyValue = (String) paramHashMap.get(tempStatusString);


                            int typeID = new Integer(Integer.parseInt(tempStatusString.substring(20, tempStatusString.length()))).intValue();

                            //PaymentDAO.updatePaymentTypeStatus(con,typeID,keyValue);
                            PaymentScmStatusDao.updateEveryDcmPaymentStatus(mConSDSConnection, typeID, keyValue);
                        }
                        if (tempStatusString.startsWith("selectStatusReason")) {
                            String keyValue = (String) paramHashMap.get(tempStatusString);


                            int typeID = new Integer(Integer.parseInt(tempStatusString.substring(19, tempStatusString.length()))).intValue();

                            //PaymentDAO.updatePaymentTypeStatus(con,typeID,keyValue);
                            PaymentScmStatusDao.updateEveryDcmPaymentStatusReason(mConSDSConnection, typeID, keyValue);
                        }
                    }
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, PaymentInterfaceKey.ACTION_VIEW_SCM_PAYMENT_STATUS);
                    Vector scmPaymentStatus = new Vector();//=PaymentScmStatusDao.viewPaymentScmStatus(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_ALL_SCM_PAYMENT, scmPaymentStatus);
                    Vector paymentStatusCases = PaymentScmStatusDao.getPaymentStatusCases(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES, paymentStatusCases);
                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                    Vector dates = PaymentScmStatusDao.viewPaymentScreenManagementAuto(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_AUTO_DATES, dates);
                    Vector flag = PaymentScmStatusDao.viewPaymentScreenManagementManual(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_MANUAL_FLAG, flag);
                    String manualOrAuto = PaymentScmStatusDao.getAutoOrManual(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_WORKING_MODE, manualOrAuto);

                    Vector statusReason = PaymentScmStatusDao.getPaymentStatusReasons(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, statusReason);
                }
                break;
                case ACTION_VIEW_DISTRIBUTION_CHANNEL: {
                    try {

                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector scmPaymentStatus = PaymentScmStatusDao.PaymentScmStatusDistributionCh(mConSDSConnection);

                        dataHashMap.put(PaymentInterfaceKey.VECTOR_ALL_SCM_PAYMENT, scmPaymentStatus);
                        Vector paymentStatusCases = PaymentScmStatusDao.getPaymentStatusCases(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES, paymentStatusCases);
                        Vector statusReason = PaymentScmStatusDao.getPaymentStatusReasons(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, statusReason);

                        Vector dates = PaymentScmStatusDao.viewPaymentScreenManagementAuto(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_AUTO_DATES, dates);
                        Vector flag = PaymentScmStatusDao.viewPaymentScreenManagementManual(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_MANUAL_FLAG, flag);
                        String manualOrAuto = PaymentScmStatusDao.getAutoOrManual(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.CONTROL_WORKING_MODE, manualOrAuto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_VIEW_FRANCHISE_CHANNEL: {
                    try {

                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector scmPaymentStatus = PaymentScmStatusDao.PaymentScmStatusFranchiseCh(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_ALL_SCM_PAYMENT, scmPaymentStatus);
                        Vector paymentStatusCases = PaymentScmStatusDao.getPaymentStatusCases(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES, paymentStatusCases);
                        Vector statusReason = PaymentScmStatusDao.getPaymentStatusReasons(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, statusReason);

                        Vector dates = PaymentScmStatusDao.viewPaymentScreenManagementAuto(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_AUTO_DATES, dates);
                        Vector flag = PaymentScmStatusDao.viewPaymentScreenManagementManual(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_MANUAL_FLAG, flag);
                        String manualOrAuto = PaymentScmStatusDao.getAutoOrManual(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.CONTROL_WORKING_MODE, manualOrAuto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_VIEW_COMPLEMENTARY_CHANNEL: {
                    try {

                        strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector scmPaymentStatus = PaymentScmStatusDao.PaymentScmStatusComplementaryCh(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_ALL_SCM_PAYMENT, scmPaymentStatus);
                        Vector paymentStatusCases = PaymentScmStatusDao.getPaymentStatusCases(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES, paymentStatusCases);
                        Vector statusReason = PaymentScmStatusDao.getPaymentStatusReasons(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, statusReason);

                        Vector dates = PaymentScmStatusDao.viewPaymentScreenManagementAuto(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_AUTO_DATES, dates);
                        Vector flag = PaymentScmStatusDao.viewPaymentScreenManagementManual(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_MANUAL_FLAG, flag);
                        String manualOrAuto = PaymentScmStatusDao.getAutoOrManual(mConSDSConnection);
                        dataHashMap.put(PaymentInterfaceKey.CONTROL_WORKING_MODE, manualOrAuto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                }
                break;
                case ACTION_SEARCH_DISTRIBUTION_CHANNEL: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

                    int pageNum = Integer.parseInt(paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString());
                    //debug("pageNum: "+pageNum);

                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    String scmCode = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE);

                    //				debug("scmCode: "+scmCode);

                    String scmName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME);
                    //debug("scmName: "+scmName);

                    String statusId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID);
                    //debug("statusId: "+statusId);

                    String reasonId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_REASON_ID);
                    //debug("reasonId: "+reasonId);

                    Vector paymentStatusCases = PaymentScmStatusDao.getPaymentStatusCases(mConSDSConnection);

                    //				debug("statusCases: "+paymentStatusCases.size());

                    dataHashMap.put(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES, paymentStatusCases);


                    Vector scmPaymentStatus = PaymentScmStatusDao.searchDistributionCh(mConSDSConnection, scmCode, scmName, statusId, reasonId, pageNum);
                    //debug("dcm size: "+scmPaymentStatus.size());
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_ALL_SCM_PAYMENT, scmPaymentStatus);
                    Vector everyScmPaymentStatus = PaymentScmStatusDao.searchEveryDistributionStatus(mConSDSConnection, scmCode, scmName, statusId, reasonId, pageNum);
                    //	debug("everyScmPaymentStatus: "+everyScmPaymentStatus.size());
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS, everyScmPaymentStatus);
                    Vector statusReason = PaymentScmStatusDao.getPaymentStatusReasons(mConSDSConnection);
                    //debug("statusReason: "+statusReason.size());
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, statusReason);

                    int totalPages = PaymentScmStatusDao.getDCMCount(mConSDSConnection, scmCode, scmName, statusId, reasonId, 1);;
                    if (totalPages % 50 == 0) {
                        totalPages = (totalPages / 50);
                    } else {
                        totalPages = (totalPages / 50);
                        totalPages++;
                    }

                    //debug("totalPages: "+totalPages);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_HIDDEN_TOTAL_PAGES, totalPages);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER, pageNum);

                    Vector dates = PaymentScmStatusDao.viewPaymentScreenManagementAuto(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_AUTO_DATES, dates);
                    Vector flag = PaymentScmStatusDao.viewPaymentScreenManagementManual(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_MANUAL_FLAG, flag);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE, scmCode);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME, scmName);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID, statusId);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_REASON_ID, reasonId);



                    String manualOrAuto = PaymentScmStatusDao.getAutoOrManual(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_WORKING_MODE, manualOrAuto);
                }
                break;
                case ACTION_SEARCH_FRANCHISE_CHANNEL: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

                    int pageNum = Integer.parseInt(paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString());

                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    String scmCode = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE);
                    String scmName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME);
                    String statusId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID);
                    String reasonId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_REASON_ID);

                    Vector paymentStatusCases = PaymentScmStatusDao.getPaymentStatusCases(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES, paymentStatusCases);



                    Vector scmPaymentStatus = PaymentScmStatusDao.searchFranchiseCh(mConSDSConnection, scmCode, scmName, statusId, reasonId, pageNum);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_ALL_SCM_PAYMENT, scmPaymentStatus);
                    Vector everyScmPaymentStatus = PaymentScmStatusDao.searchEveryFranchiseStatus(mConSDSConnection, scmCode, scmName, statusId, reasonId, pageNum);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS, everyScmPaymentStatus);
                    Vector statusReason = PaymentScmStatusDao.getPaymentStatusReasons(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, statusReason);

                    int totalPages = PaymentScmStatusDao.getDCMCount(mConSDSConnection, scmCode, scmName, statusId, reasonId, 2);
                    if (totalPages % 50 == 0) {
                        totalPages = (totalPages / 50);
                    } else {
                        totalPages = (totalPages / 50);
                        totalPages++;
                    }

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_HIDDEN_TOTAL_PAGES, String.valueOf(totalPages));
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER, paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER));

                    Vector dates = PaymentScmStatusDao.viewPaymentScreenManagementAuto(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_AUTO_DATES, dates);
                    Vector flag = PaymentScmStatusDao.viewPaymentScreenManagementManual(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_MANUAL_FLAG, flag);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE, scmCode);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME, scmName);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID, statusId);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_REASON_ID, reasonId);


                    String manualOrAuto = PaymentScmStatusDao.getAutoOrManual(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_WORKING_MODE, manualOrAuto);
                }
                break;
                case ACTION_SEARCH_COMPLEMENTARY_CHANNEL: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

                    int pageNum = Integer.parseInt(paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString());

                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    String scmCode = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE);
                    String scmName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME);
                    String statusId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID);
                    String reasonId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_REASON_ID);

                    Vector paymentStatusCases = PaymentScmStatusDao.getPaymentStatusCases(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES, paymentStatusCases);



                    Vector scmPaymentStatus = PaymentScmStatusDao.searchComplementaryCh(mConSDSConnection, scmCode, scmName, statusId, reasonId, pageNum);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_ALL_SCM_PAYMENT, scmPaymentStatus);
                    Vector everyScmPaymentStatus = PaymentScmStatusDao.searchEveryComplementaryStatus(mConSDSConnection, scmCode, scmName, statusId, reasonId, pageNum);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS, everyScmPaymentStatus);
                    Vector statusReason = PaymentScmStatusDao.getPaymentStatusReasons(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, statusReason);

                    int totalPages = PaymentScmStatusDao.getDCMCount(mConSDSConnection, scmCode, scmName, statusId, reasonId, 3);
                    if (totalPages % 50 == 0) {
                        totalPages = (totalPages / 50);
                    } else {
                        totalPages = (totalPages / 50);
                        totalPages++;
                    }

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_HIDDEN_TOTAL_PAGES, String.valueOf(totalPages));
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER, paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER));

                    Vector dates = PaymentScmStatusDao.viewPaymentScreenManagementAuto(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_AUTO_DATES, dates);
                    Vector flag = PaymentScmStatusDao.viewPaymentScreenManagementManual(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_MANUAL_FLAG, flag);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE, scmCode);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME, scmName);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID, statusId);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_REASON_ID, reasonId);


                    String manualOrAuto = PaymentScmStatusDao.getAutoOrManual(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_WORKING_MODE, manualOrAuto);
                }
                break;
                case ACTION_UPDATE_EMAILS_NOTIFICATION: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

                    String employeeName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_EMPLOYEE_NAME);
                    Vector emailList = PaymentScmStatusDao.getPaymentEmployeeEmails(mConSDSConnection, employeeName);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_DCM_EMAIL_LIST, emailList);

                }
                break;
                case ACTION_NEW_EMAILS_NOTIFICATION: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_EXISTING_EMAIL_NOTIFICATION, "");

                }
                break;
                ////////////////////////////////////////////////////////////////////////////////////
                case ACTION_SEARCH_EMAILS_NOTIFICATION: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    String scmName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SEARCH_SCM_NAME_NOTIFICATION);
                    Vector emailList = PaymentScmStatusDao.searchPaymentEmployeeEmails(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_DCM_EMAIL_LIST, emailList);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;

                case ACTION_SEARCH_EMAILS_NOTIFICATION_FOR_EDIT: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    String scmName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SEARCH_SCM_NAME_NOTIFICATION);
                    Vector emailList = PaymentScmStatusDao.searchPaymentEmployeeEmails(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_DCM_EMAIL_LIST, emailList);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;
                case ACTION_INSERT_NEW_EMAIL_NOTIFICATION: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String scmName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_NEW_SCM_EMAIL_NOTIFICATION);
                    String scmEmail = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_NEW_EMAIL_NOTIFICATION);

                    //check if the name is already exist
                    //Vector emailList=PaymentScmStatusDao.searchPaymentEmployeeEmails(mConSDSConnection);
                    PaymentScmStatusDao.insertNewDcmEmails(mConSDSConnection, scmName, scmEmail);


                }
                break;
                case ACTION_DO_UPDATE_EMAILS_NOTIFICATION: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    String oldEmployeeName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_EMPLOYEE_NAME);

                    String newEmployeeName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_SCM_NAME_EMAIL);

                    String newEmployeeEmail = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_SCM_EMAIL);


                    PaymentScmStatusDao.updateDcmEmails(mConSDSConnection, oldEmployeeName, newEmployeeName, newEmployeeEmail);
                    Vector emailList = PaymentScmStatusDao.searchPaymentEmployeeEmails(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_DCM_EMAIL_LIST, emailList);
                }
                break;
                case ACTION_DELETE_EMAIL_NOTIFICATION: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

                    String employeeName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_EMPLOYEE_NAME);

                    PaymentScmStatusDao.deleteEmployeeEmail(mConSDSConnection, employeeName);
                    Vector emailList = PaymentScmStatusDao.searchPaymentEmployeeEmails(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_DCM_EMAIL_LIST, emailList);
                }
                break;
                case ACTION_ADD_NEW_SCM_PAYMENT_STATUS: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    int id = PaymentScmStatusDao.getNextImportPaymentFileId(mConSDSConnection);
                    dataHashMap.put(InterfaceKey.ATTACH_ID, "" + id);



                    //for the engine
                    Vector tableDefVector = DataImportTableDefDAO.getTableDefByCategory("27");
                    dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR, tableDefVector);

                }
                break;
                case ACTION_GENERATE_PAYMENT_STATUS_TEMPLATE: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;
                case ACTION_IMPORT_SCM_PAYMENT_STATUS: {
                    /*
                     * String fileName = (String)
                     * paramHashMap.get(InterfaceKey.CONTROL_INPUT_FILE);
                     *
                     * fileName = Utility.replaceAll(fileName, "#", "\\");
                     * //Vector all_dcm = //KPIDAO.getAllKPI(connection, sub_id,
                     * "normal",0,0); HashMap importedPayments =
                     * ImportExportWrapper.importExcelFile(fileName, 0, 0, 0,
                     * 0);
                     *
                     *
                     * if(importedPayments != null && importedPayments.size()>0)
                     * { Iterator keysItr =
                     * importedPayments.keySet().iterator(); Vector
                     * dcmCode=null; Vector dcmStatus=null; Vector reason=null;
                     * Vector stk=null; Vector stkStatus=null;
                     *
                     * while(keysItr.hasNext()) { String keyStr1 =
                     * (String)keysItr.next();
                     *
                     * //String key_str2 = (String)keys_itr.next();
                     * //debug("key_str2: "+ key_str2 ); //String key_str3 =
                     * (String)keys_itr.next(); //debug("key_str3: "+ key_str3
                     * );
                     *
                     *
                     *
                     * if(keyStr1.compareTo("DCM CODE")==0) {
                     *
                     * dcmCode=(Vector)importedPayments.get(keyStr1); } else
                     * if(keyStr1.compareTo("Status")==0) {
                     *
                     * dcmStatus=(Vector)importedPayments.get(keyStr1); } else
                     * if(keyStr1.compareTo("Reason")==0) {
                     *
                     * reason=(Vector)importedPayments.get(keyStr1); }else
                     * if(keyStr1.compareTo("STK Number")==0) {
                     *
                     * stk=(Vector)importedPayments.get(keyStr1); }else
                     * if(keyStr1.compareTo("STK Number Status")==0) {
                     *
                     * stkStatus=(Vector)importedPayments.get(keyStr1); }
                     *
                     *
                     * }
                     * for(int i=0;i<dcmCode.size();i++) {
                     * if(dcmCode.get(i)!=null){
                     *
                     * PaymentImportModel m=new PaymentImportModel();
                     * m.setFileID((String)
                     * paramHashMap.get(InterfaceKey.CONTROL_INPUT_FILE));
                     * m.setScmCode((String)dcmCode.get(i));
                     *
                     * m.setScmStatus((String)dcmStatus.get(i));
                     *
                     * m.setReason((String)reason.get(i));
                     *
                     * m.setStkNumber((String)stk.get(i));
                     *
                     * m.setStkNumberStatus((String)stkStatus.get(i)); //calling
                     * dao
                     * PaymentScmStatusDao.importPaymentState(mConSDSConnection,m);
                     * } } }
                     */

                    //PaymentScmStatusDao.importPaymentState(mConSDSConnection);


                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    //dataHashMap.put(InterfaceKey.ATTACH_ID, "" + PaymentScmStatusDao.getNextImportPaymentFileId(mConSDSConnection));



                }
                break;
                case ACTION_GENERATE_CHANNEL_PAYMENT_STATUS_TEMPLATE: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;
                case ACTION_VIEW_IMPORT_CHANNEL_PAYMENT_STATUS: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    //int id=PaymentScmStatusDao.getNextImportChannelPaymentFileId(mConSDSConnection);
                    //dataHashMap.put(InterfaceKey.ATTACH_ID, "" + id);

                    //for the import engine

                    Vector tableDefVector = DataImportTableDefDAO.getTableDefByCategory("28");
                    System.out.println("in handler table size: " + tableDefVector.size());
                    dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR, tableDefVector);

                }
                case ACTION_IMPORT_CHANNEL_PAYMENT_STATUS: {
                    /*
                     * String fileName = (String)
                     * paramHashMap.get(InterfaceKey.CONTROL_INPUT_FILE);
                     * fileName = Utility.replaceAll(fileName, "#", "\\");
                     * //Vector all_dcm = //KPIDAO.getAllKPI(connection, sub_id,
                     * "normal",0,0); HashMap importedPayments =
                     * ImportExportWrapper.importExcelFile(fileName, 0, 0, 0,
                     * 0);
                     *
                     *
                     * if(importedPayments != null && importedPayments.size()>0)
                     * { Iterator keysItr =
                     * importedPayments.keySet().iterator(); Vector
                     * dcmCode=null; Vector dcmStatus=null; Vector reason=null;
                     * //Vector stk=null; // Vector stk_status=null;
                     *
                     *
                     * while(keysItr.hasNext()) { String keyStr1 =
                     * (String)keysItr.next();
                     *
                     * //String key_str2 = (String)keys_itr.next();
                     * //debug("key_str2: "+ key_str2 ); //String key_str3 =
                     * (String)keys_itr.next(); //debug("key_str3: "+ key_str3
                     * );
                     *
                     *
                     *
                     * if(keyStr1.compareTo("DCM CODE")==0) {
                     *
                     * dcmCode=(Vector)importedPayments.get(keyStr1); } else
                     * if(keyStr1.compareTo("Status")==0) {
                     *
                     * dcmStatus=(Vector)importedPayments.get(keyStr1); } else
                     * if(keyStr1.compareTo("Reason")==0) {
                     *
                     * reason=(Vector)importedPayments.get(keyStr1); }
                     *
                     *
                     * }
                     * for(int i=0;i<dcmCode.size();i++) {
                     *
                     *
                     * if(dcmCode.get(i)!=null&&String.valueOf(dcmCode.get(i)).compareTo("")!=0){
                     * PaymentImportModel m=new PaymentImportModel();
                     * m.setFileID((String)
                     * paramHashMap.get(InterfaceKey.CONTROL_INPUT_FILE));
                     *
                     * m.setScmCode((String)dcmCode.get(i));
                     *
                     * m.setScmStatus((String)dcmStatus.get(i));
                     *
                     * m.setReason((String)reason.get(i)); //calling dao
                     * PaymentScmStatusDao.importChannelPaymentState(mConSDSConnection,m);
                     * debug("succeful import"); }
                     *
                     *
                     *
                     *
                     * }
                     * }
                     */

                    //PaymentScmStatusDao.importChannelPaymentState(mConSDSConnection);

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    //dataHashMap.put(InterfaceKey.ATTACH_ID, "" + PaymentScmStatusDao.getNextImportPaymentFileId(mConSDSConnection));
                }
                break;
                case ACTION_VIEW_PAYMENT_REASON: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    Vector reasons = PaymentScmStatusDao.getPaymentReasons(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, reasons);

                }
                break;
                case ACTION_VIEW_ADD_PAYMENT_REASON: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;
                case ACTION_ADD_PAYMENT_REASON: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    String reasonName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_PAYMENT_REASON_NAME);
                    String reasonDesc = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_PAYMENT_REASON_DESC);
                    PaymentScmStatusDao.addPaymentStatusReasons(mConSDSConnection, reasonName, reasonDesc);
                    //addPaymentStatusReasons

                    Vector reasons = PaymentScmStatusDao.getPaymentReasons(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, reasons);
                }
                break;
                case ACTION_DELETE_PAYMENT_REASON: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    String reasonId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_REASON_ID);
                    PaymentScmStatusDao.deletePaymentStatusReasons(mConSDSConnection, reasonId);

                    Vector reasons = PaymentScmStatusDao.getPaymentReasons(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, reasons);
                }
                break;
                case ACTION_VIEW_UPDATE_PAYMENT_REASON: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String reasonId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_REASON_ID);
                    Vector reasons = PaymentScmStatusDao.getPaymentReasonById(mConSDSConnection, reasonId);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, reasons);
                }
                break;
                case ACTION_UPDATE_PAYMENT_REASON: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String reasonId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_REASON_ID);
                    String reasonName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_PAYMENT_REASON_NAME);
                    String reasonDesc = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_PAYMENT_REASON_DESC);
                    PaymentScmStatusDao.updatePaymentStatusReasons(mConSDSConnection, reasonId, reasonName, reasonDesc);

                    Vector reasons = PaymentScmStatusDao.getPaymentReasons(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON, reasons);
                }
                break;

                case SEARCH_PAYMENT_TYPE_GROUP: {
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_STATUS, PaymentTypeDAO.getAllPayTypeGroupStatus(mConSDSConnection));
                }
                break;
                case VIEW_PAYMENT_TYPE_GROUP: {
                    String groupNameSearch = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SEARCH_GROUP_NAME);
                    String statusIdSearch = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_GROUP_STATUS);

                    int statusIds = -1;
                    if (statusIdSearch != null && !statusIdSearch.equals("")) {
                        statusIds = Integer.parseInt(statusIdSearch);
                    }

                    Vector result_group = PaymentTypeDAO.searchPayTypeGroup(mConSDSConnection, groupNameSearch, statusIds);

                    dataHashMap.put(PaymentInterfaceKey.VECTOR_GROUP, result_group);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_STATUS, PaymentTypeDAO.getAllPayTypeGroupStatus(mConSDSConnection));
                }
                break;
                case NEW_PAYMENT_TYPE_GROUP: {

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_STATUS, PaymentTypeDAO.getAllPayTypeGroupStatus(mConSDSConnection));
                }
                break;
                case EDIT_PAYMENT_TYPE_GROUP: {
                    int groupId = Integer.parseInt((String) paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_GROUP_ID));
                    PaymentTypeGroupModel groupModel = PaymentTypeDAO.getGroupById(mConSDSConnection, groupId);

                    dataHashMap.put(PaymentInterfaceKey.MODEL_GROUP, groupModel);
                    dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_STATUS, PaymentTypeDAO.getAllPayTypeGroupStatus(mConSDSConnection));
                }
                break;
                case ADD_PAYMENT_TYPE_GROUP: {
                    String groupName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_GROUP_NAME);
                    String groupDesc = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_GROUP_DESC);
                    int statusId = Integer.parseInt((String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_STATUS));
                    PaymentTypeGroupModel groupModel = new PaymentTypeGroupModel();
                    groupModel.setGroupName(groupName);
                    groupModel.setGroupDesc(groupDesc);
                    groupModel.setGroupStatusId(statusId);
                    int insertedId = PaymentTypeDAO.addPayTypeGroup(mConSDSConnection, groupModel);

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_STATUS, PaymentTypeDAO.getAllPayTypeGroupStatus(mConSDSConnection));
                }
                break;
                case UPDATE_PAYMENT_TYPE_GROUP: {
                    int groupId = Integer.parseInt((String) paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_GROUP_ID));

                    dataHashMap.put(PaymentInterfaceKey.CONTROL_SELECT_STATUS, PaymentTypeDAO.getAllPayTypeGroupStatus(mConSDSConnection));
                }
                break;
                case DELETE_PAYMENT_TYPE_GROUP: {
                }
                break;
                case ACTION_VIEW_STK_STATUS_MANAGEMENT: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;
                case ACTION_SEARCH_STK_STATUS_MANAGEMENT: {

                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);


                    String dcmName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME);
                    String dcmCode = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE);
                    String stkNumber = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_STK_NUMBER);

                    Vector dcmStkStatus = PaymentScmStatusDao.searchStkStatus(mConSDSConnection, dcmCode, dcmName, stkNumber);
                    dataHashMap.put(MemoInterfaceKey.VECTOR_DCM_DETAILS_STATUS, dcmStkStatus);
                    Vector stkStatus = PaymentScmStatusDao.getStkStatusCases(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_STK_STATUS_CASES, stkStatus);






                }
                break;

                case ACTION_UPDATE_STK_STATUS_MANAGEMENT: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);


                    String dcmPaymentStatusId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_SCM_PAYMENT_ID);
                    String statusId = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_STK_STATUS);
                    String stkStatusComment = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_STK_STATUS_COMMENT);
                    PaymentScmStatusDao.updateStkStatus(mConSDSConnection, dcmPaymentStatusId, statusId, stkStatusComment);


                }

                break;
                case ACTION_INSERT_NEW_DCM_STK_STATUS: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String dcmCode = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_NEW_SCM_CODE);

                    String stkNumber = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_STK_NUMBER);

                    String stkStatus = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_STK_STATUS);


                    PaymentScmStatusDao.insertDcmSTKStatus(mConSDSConnection, dcmCode, stkNumber, stkStatus);

                }
                break;
                case ACTION_INSERT_NEW_DCM_PAYMENT_STATUS: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String dcmCode = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_NEW_SCM_CODE);


                    String dcmStatus = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_ALL_SCM_PAYMENT_STATUS);

                    String reason = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_PAYMENT_REASON_NAME);


                    PaymentScmStatusDao.insertDcmPaymentStatus(mConSDSConnection, dcmCode, dcmStatus, reason);

                    String dcmMode = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_DCM_MODE);
                    if (Integer.parseInt(dcmMode) == 0) {
                        dataHashMap.put(PaymentInterfaceKey.CONTROL_DCM_MODE, "0");
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_VIEW_DCM_DETAILS);
                    } else {
                        dataHashMap.put(PaymentInterfaceKey.CONTROL_DCM_MODE, "1");
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, MemoInterfaceKey.ACTION_VIEW_GEN_DCM_DETAILS);
                    }
                }
                break;
                case ACTION_VIEW_PAYMENT_EXCEL_MANAGEMENT: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);

                }
                break;
                case ACTION_VIEW_PAYMENT_EXCEL_ATTRIBUTES: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String paymentTypeId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID);
                    String paymentTypeName = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME);

                    dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID, paymentTypeId);
                    dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME, paymentTypeName);

                    Vector PaymentTypeExcelModels = PaymentScmStatusDao.getPaymentTypeExcelAttributes(mConSDSConnection, paymentTypeId);

                    dataHashMap.put(PaymentInterfaceKey.PAYMENT_TYPE_EXCEL_ATTRIBUTE, PaymentTypeExcelModels);

                }
                break;
                case ACTION_VIEW_UPDATE_PAYMENT_EXCEL_ATTRIBUTES: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String paymentTypeExcelSheetId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_EXCEL_MANAGEMENT_ID);

                    //get the sheet attributes



                    dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_EXCEL_MANAGEMENT_ID, paymentTypeExcelSheetId);

                    PaymentTypeExcelModel model = PaymentScmStatusDao.getPaymentExcelSheetAttributes(mConSDSConnection, paymentTypeExcelSheetId);
                    dataHashMap.put(PaymentInterfaceKey.PAYMENT_TYPE_EXCEL_ATTRIBUTE, model);
                    String msg = "UNIQUE";
                    dataHashMap.put(PaymentInterfaceKey.EXCEL_SHEET_MESSAGE, msg);
                    //Vector searchResults = PaymentDAO.getPaymentByFilter(mConSDSConnection,
                    //	"", "0", "0","0",
                    //	"*", "*",
                    //	"*");

                    //dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION	, searchResults);
                    Vector templates = MemoDAO.getSqlTemplates(mConSDSConnection, "");
                    dataHashMap.put(MemoInterfaceKey.VECTOR_SQL_TEMPLATES, templates);
                    String paymentTypeId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID);
                    String paymentTypeName = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME);

                    dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID, paymentTypeId);

                    dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME, paymentTypeName);




                }
                break;
                case ACTION_UPDATE_PAYMENT_EXCEL_ATTRIBUTES: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    String paymentTypeId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID);

                    String paymentTypeName = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME);

                    String paymentExcelId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_EXCEL_MANAGEMENT_ID);

                    String sheetNumber = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_EXCEL_SHEET_NUMBER);

                    String sheetName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_EXCEL_SHEET_NAME);

                    String sqlStatement = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_NEW_EXCEL_SHEET_SQL_STATEMENT);

                    String templateName = (String) paramHashMap.get(PaymentInterfaceKey.PAYMENT_NAME_TEMPLATE);

                    String oldSheetNumber = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_OLD_EXCEL_SHEET_NUMBER);

                    String tempId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_SQL_TEMPLATE_ID);

                    String msg = null;
                    if (Integer.parseInt(sheetNumber) == Integer.parseInt(oldSheetNumber)) {

                        PaymentScmStatusDao.updatePaymentExcelSheet(mConSDSConnection, paymentExcelId, sheetNumber, sheetName, sqlStatement, templateName, tempId);

                        msg = "UNIQUE";
                        System.out.println(msg + "1");

                        dataHashMap.put(PaymentInterfaceKey.EXCEL_SHEET_MESSAGE, msg);

                        dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID, paymentTypeId);
                        dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME, paymentTypeName);

                        Vector paymentTypeExcelModels = PaymentScmStatusDao.getPaymentTypeExcelAttributes(mConSDSConnection, paymentTypeId);

                        dataHashMap.put(PaymentInterfaceKey.PAYMENT_TYPE_EXCEL_ATTRIBUTE, paymentTypeExcelModels);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, PaymentInterfaceKey.ACTION_VIEW_PAYMENT_EXCEL_ATTRIBUTES);
                    } //check sheet number uniqueness
                    else if (PaymentScmStatusDao.checkExcelSheetUniqueness(mConSDSConnection, paymentTypeId, sheetNumber)) {
                        PaymentScmStatusDao.updatePaymentExcelSheet(mConSDSConnection, paymentExcelId, sheetNumber, sheetName, sqlStatement, templateName, tempId);

                        msg = "UNIQUE";
                        System.out.println(msg + "2");

                        dataHashMap.put(PaymentInterfaceKey.EXCEL_SHEET_MESSAGE, msg);

                        dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID, paymentTypeId);
                        dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME, paymentTypeName);

                        Vector PaymentTypeExcelModels = PaymentScmStatusDao.getPaymentTypeExcelAttributes(mConSDSConnection, paymentTypeId);

                        dataHashMap.put(PaymentInterfaceKey.PAYMENT_TYPE_EXCEL_ATTRIBUTE, PaymentTypeExcelModels);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, PaymentInterfaceKey.ACTION_VIEW_PAYMENT_EXCEL_ATTRIBUTES);
                    } else {
                        msg = "NOT_UNIQUE";
                        System.out.println(msg + "3");

                        dataHashMap.put(PaymentInterfaceKey.EXCEL_SHEET_MESSAGE, msg);

                        dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID, paymentTypeId);
                        dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME, paymentTypeName);

                        Vector PaymentTypeExcelModels = PaymentScmStatusDao.getPaymentTypeExcelAttributes(mConSDSConnection, paymentTypeId);

                        dataHashMap.put(PaymentInterfaceKey.PAYMENT_TYPE_EXCEL_ATTRIBUTE, PaymentTypeExcelModels);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, PaymentInterfaceKey.ACTION_VIEW_PAYMENT_EXCEL_ATTRIBUTES);
                    }

                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES, paymentTypes);
                }
                break;
                case ACTION_INSERT_PAYMENT_EXCEL_ATTRIBUTES: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    String msg = null;

                    String paymentTypeId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID);

                    String paymentTypeName = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME);

                    String sheetNumber = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_NEW_EXCEL_SHEET_NUMBER);
                    System.out.println("sheet number: " + sheetNumber);

                    String sheetName = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_NEW_EXCEL_SHEET_NAME);
                    String tempId = (String) paramHashMap.get(MemoInterfaceKey.HIDDEN_SQL_TEMPLATE_ID);
                    System.out.println("tempId: " + tempId);

                    String templateId = (String) paramHashMap.get(PaymentInterfaceKey.PAYMENT_NAME_TEMPLATE);

                    String sqlStatement = (String) paramHashMap.get(PaymentInterfaceKey.CONTROL_NEW_EXCEL_SHEET_SQL_STATEMENT);


                    if (PaymentScmStatusDao.checkExcelSheetUniqueness(mConSDSConnection, paymentTypeId, sheetNumber)) {

                        PaymentScmStatusDao.insertPaymentExcelAttributes(mConSDSConnection, paymentTypeId, sheetNumber, sheetName, sqlStatement, tempId, templateId);

                        dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID, paymentTypeId);
                        dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME, paymentTypeName);

                        Vector paymentTypeExcelModels = PaymentScmStatusDao.getPaymentTypeExcelAttributes(mConSDSConnection, paymentTypeId);

                        dataHashMap.put(PaymentInterfaceKey.PAYMENT_TYPE_EXCEL_ATTRIBUTE, paymentTypeExcelModels);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, PaymentInterfaceKey.ACTION_VIEW_PAYMENT_EXCEL_ATTRIBUTES);


                    } else {
                        msg = "NOT_UNIQUE";

                        dataHashMap.put(PaymentInterfaceKey.EXCEL_SHEET_MESSAGE, msg);

                        dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID, paymentTypeId);
                        dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME, paymentTypeName);

                        Vector paymentTypeExcelModels = PaymentScmStatusDao.getPaymentTypeExcelAttributes(mConSDSConnection, paymentTypeId);

                        dataHashMap.put(PaymentInterfaceKey.PAYMENT_TYPE_EXCEL_ATTRIBUTE, paymentTypeExcelModels);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, PaymentInterfaceKey.ACTION_VIEW_PAYMENT_EXCEL_ATTRIBUTES);

                    }


                }
                break;
                case ACTION_VIEW_INSERT_PAYMENT_EXCEL_ATTRIBUTES: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                    String paymentTypeId = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID);
                    String paymentTypeName = (String) paramHashMap.get(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME);

                    dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_ID, paymentTypeId);

                    dataHashMap.put(PaymentInterfaceKey.HIDDEN_PAYMENT_TYPE_NAME, paymentTypeName);

                    Vector PaymentTypeExcelModels = PaymentScmStatusDao.getPaymentTypeExcelAttributes(mConSDSConnection, paymentTypeId);

                    dataHashMap.put(PaymentInterfaceKey.PAYMENT_TYPE_EXCEL_ATTRIBUTE, PaymentTypeExcelModels);

                    Vector templates = MemoDAO.getSqlTemplates(mConSDSConnection, "");
                    dataHashMap.put(MemoInterfaceKey.VECTOR_SQL_TEMPLATES, templates);



                }
                break;

                case ACTION_UPDATE_CAM_PAYMENT: {

                    PaymentTypeDAO.UpdateCamPaymentDials();
                }

                break;



                case ACTION_RELEASE_PAYMENT: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_RELEASE_TYPES, paymentTypes);



                    Vector releasedPayment = PaymentScmStatusDao.getReleasePaymentForm(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_SEARCH_RELEASED_PAYMENT, releasedPayment);


                }


                break;

                case ACTION_FREEZE_PAYMENT: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);

                    Vector paymentTypes = PaymentScmStatusDao.viewPaymentTypes(mConSDSConnection);
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_FREEZE_TYPES, paymentTypes);

                    //Vector frozenPayment = PaymentScmStatusDao.getFreezePaymentForm(mConSDSConnection);
                    Vector frozenPayment = new Vector();
                    dataHashMap.put(PaymentInterfaceKey.VECTOR_SEARCH_FROZEN_PAYMENT, frozenPayment);


                }


                break;

                case ACTION_SHOW_CLOSED_PAYMENT: {
                    strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, action);
                }
                break;
                    
                case ACTION_GENERATE_PAYMENT_LIST: {
                    
                }
                break;
                    
                case ACTION_GET_PAYMENT_LIST: {
                    
                }
                break;
                    
                case ACTION_UPLOAD_PAYMENT_LIST: {
                    
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
