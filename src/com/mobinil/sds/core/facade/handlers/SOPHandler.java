package com.mobinil.sds.core.facade.handlers;

import java.util.*;

import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.sop.SOPInterfaceKey;
import com.mobinil.sds.web.interfaces.sa.*;
import com.mobinil.sds.web.interfaces.gn.ua.UserAccountInterfaceKey;
import com.mobinil.sds.web.interfaces.gn.querybuilder.QueryBuilderInterfaceKey;

import com.mobinil.sds.core.utilities.*;

import com.mobinil.sds.core.system.sop.schemas.dao.*;
import com.mobinil.sds.core.system.sop.schemas.model.*;
import com.mobinil.sds.core.system.sop.requests.dao.*;
import com.mobinil.sds.core.system.sop.requests.model.*;
import com.mobinil.sds.core.system.sop.equations.dao.*;
import com.mobinil.sds.core.system.sop.equations.dto.*;
import com.mobinil.sds.core.system.sop.equations.model.*;
import com.mobinil.sds.core.system.sop.quota.dao.*;
import com.mobinil.sds.core.system.sop.quota.model.*;

import com.mobinil.sds.core.system.gn.dcm.dto.*;
import com.mobinil.sds.core.system.gn.dcm.dao.*;
import com.mobinil.sds.core.system.gn.dcm.model.*;
import com.mobinil.sds.core.system.sa.importdata.dao.*;
import com.mobinil.sds.core.system.sa.users.dao.*;

import com.mobinil.sds.core.system.gn.querybuilder.dto.QueryDTO;
import com.mobinil.sds.core.system.gn.querybuilder.domain.QueryBuilderEngine;
//COMMENT PLEASE !
import com.mobinil.sds.core.system.sop.franchise.model.*;
import com.mobinil.sds.core.system.sop.franchise.dao.*;

public class SOPHandler {

    static final int SHOW_ALL_SCHEMAS = 1;
    static final int CREATE_NEW_SCHEMA = 2;
    static final int VIEW_SCHEMA_PRODUCTS = 3;
    static final int SAVE_SCHEMA = 4;
    static final int UPDATE_SCHEMA_PRODUCTS = 5;
    static final int IMPORT_PRODUCTS = 6;
    static final int IMPORT_PRODUCTS_AND_CREATE_SCHEMA = 7;
    static final int CHANGE_SCHEMA_STATUS = 8;
    static final int SEARCH_SCHEMA = 9;
    static final int CREATE_NEW_REQUEST = 10;
    static final int REQUEST_SCHEMA_PRODUCTS = 11;
    static final int SAVE_REQUEST = 12;
    static final int SHOW_ALL_REQUESTS = 13;
    static final int UPDATE_REQUEST_STATUS = 14;
    static final int VIEW_REQUEST_DETAILS = 15;
    static final int DATA_WAREHOUSE_IMPORT = 16;
    static final int DATA_WAREHOUSE_IMPORT_PROCESS = 17;
    static final int GENERATE_DATA_WAREHOUSE_IMPORT_TEMPLATE = 18;
    static final int VIEW_STOCK_PRODUCTS = 19;
    static final int SEARCH_STOCK_PRODUCT = 20;
    static final int GENERATE_MANUAL_DCM_PRODUCT_LIMITS = 21;
    static final int ADMIN_SELECT_DCM_REQUEST_PRODUCT_LIMITS = 22;
    static final int ADMIN_SET_DCM_REQUEST_PRODUCT_LIMITS = 23;
    static final int SAVE_DCM_REQUEST_PRODUCT_LIMITS = 24;
    static final int SHOW_ALL_EQUATIONS = 25;
    static final int ADD_EQUATION = 26;
    static final int EDIT_EQUATION = 27;
    static final int UPDATE_EQUATION_STATUS = 28;
    static final int SAVE_EQUATION = 29;
    static final int SELECT_DCM_QUOTA_SETTINGS = 30;
    static final int SET_DCM_QUOTA_SETTING_VALUES = 31;
    static final int UPDATE_DCM_QUOTA_SETTING_VALUE = 32;
    static final int SELECT_DCM_QUOTA_VALUES = 33;
    static final int SET_DCM_QUOTA_VALUES = 34;
    static final int UPDATE_DCM_QUOTA_VALUE = 35;
    static final int SHOW_USER_DCM_SCREEN_SOP = 36;
    static final int SOP_UPDATE_USER_DCM = 37;
    static final int SEARCH_REQUEST = 38;
    static final int GENERATE_MANUAL_DCM_PRODUCT_LIMITS_CHECKING = 39;
    static final int UPDATE_EQUATION = 40;
    static final int ASSIGN_EQUATION_TO_PRODUCTS = 41;
    static final int SAVE_ASSIGN_EQUATION_TO_PRODUCTS = 42;
    static final int DCM_PRODUCT_LIMITS_BY_EQUATION = 43;
    static final int SAVE_DCM_PRODUCT_LIMITS_BY_EQUATION = 44;
    static final int DCM_PRODUCT_LIMITS_BY_EQUATION_SELECT_PRODUCTS = 45;
    static final int GET_DCM_REQUEST = 46;
    static final int IMPORT_PRODUCTS_FROM_PGW = 47;
    static final int FIXED_QUOTA_VALUES = 48;
    static final int SAVE_FIXED_QUOTA_VALUES = 49;
    static final int REQUEST_NOTIFICATION_MANAGEMENT = 50;
    static final int EDIT_REQUEST_NOTIFICATION = 51;
    static final int DELETE_REQUEST_NOTIFICATION = 52;
    static final int CREATE_REQUEST_NOTIFICATION = 53;
    static final int SAVE_REQUEST_NOTIFICATION = 54;
    static final int QUOTA_CALCULATION_BY_DATAVIEW = 55;
    static final int ADMIN_CREATE_NEW_REQUEST = 56;
    static final int ADMIN_REQUEST_SCHEMA_PRODUCTS = 57;
    static final int ADMIN_SAVE_REQUEST = 58;
    static final int IMPORT_LCS_PRODUCTS_PHYSICAL_AMOUNT = 59;
    static final int CALCULATE_QUOTA_BY_DATAVIEW = 60;
    static final int CREATE_NEW_PRODUCT_PGW = 61;
    static final int SAVE_PRODUCT_PGW = 62;
    static final int EDIT_PRODUCT_PGW = 63;
    static final int UPDATE_PRODUCT_PGW = 64;
    static final int CREATE_NEW_INVOICE = 65;
    static final int SAVE_NEW_INVOICE = 66;
    static final int EDIT_INVOICE = 67;
    static final int UPDATE_INVOICE_DATA = 68;
    static final int ADD_EDIT_INVOICE_DETAIL = 69;
    static final int SAVE_INVOICE_DETAIL = 70;
    static final int SHOW_INVOICE_DETAIL_DATA = 71;
    static final int CREATE_NEW_INVOICE_DETAIL = 72;
    static final int UPDATE_INVOICE_DETAIL_DATA = 73;
    static final int SHOW_ALL_REQUESTS_CHANGE_TO_PAID = 74;
    static final int SEARCH_REQUEST_CHANGE_TO_PAID = 75;
    static final int UPDATE_REQUEST_STATUS_CHANGE_TO_PAID = 76;
    static final int SHOW_PRODUCTS_FROM_LCS = 77;
    static final int CREATE_PRODUCT_LCS = 78;
    static final int SAVE_PRODUCT_LCS = 79;
    static final int EDIT_PRODUCT_LCS = 80;
    static final int UPDATE_PRODUCT_LCS = 81;
    static final int SHOW_ALL_INVOICES = 82;
    static final int SEARCH_INVOICE = 83;
    static final int INVOICE_BACK = 84;
    static final int SHOW_ALL_PRODUCTS_PGW = 85;
    static final int SEARCH_PRODUCT_PGW = 86;
    static final int SHOW_ALL_SCHEMAS_FOR_FRANCHISE = 87;
    static final int IMPORT_PRODUCTS_FOR_FRANCHISE = 88;
    static final int CREATE_NEW_REQUEST_FOR_FRANCHISE = 89;
    static final int ADMIN_CREATE_NEW_REQUEST_FOR_FRANCHISE = 90;
    static final int SHOW_ALL_REQUESTS_CHANGE_TO_PAID_FOR_FRANCHISE = 91;
    static final int SHOW_ALL_REQUESTS_FOR_FRANCHISE = 92;
    static final int SELECT_FRANCHISE_QUOTA_SETTINGS = 93;
    static final int SELECT_FRANCHISE_QUOTA_VALUES = 94;
    static final int FIXED_QUOTA_VALUES_FOR_FRANCHISE = 95;
    static final int SHOW_USER_FRANCHISE_SCREEN_SOP = 96;
    static final int DATA_WAREHOUSE_IMPORT_FOR_FRANCHISE = 97;
    static final int VIEW_STOCK_PRODUCTS_FOR_FRANCHISE = 98;
    static final int IMPORT_LCS_PRODUCTS_PHYSICAL_AMOUNT_FOR_FRANCHISE = 99;
    static final int DELETE_PRODUCT_LCS = 100;
    static final int DELETE_PRODUCT_FROM_PGW = 101;
    static final int DELETE_SCHEMA_PRODUCT = 102;
    static final int SEARCH_FRANCHISE_STATE = 103;
    static final int SEARCH_LCS_STATE = 104;
    static final int SEARCH_PGW_STATE = 105;
    static final int PRINT_REQUEST_PAYMENT_SLIP = 106;
    static final int DELETE_IMPORTED_PRODUCTS_FOR_DISTRIBUTORS = 107;
    static final int DELETE_IMPORTED_PRODUCTS_FOR_FRANCHISE = 108;
    static final int NEXT_PGW_STATE = 109;
    static final int PREVIOUS_PGW_STATE = 110;
    static final int EXCEL_PGW_STATE = 111;
    static final int NEXT_LCS_STATE = 112;
    static final int PREVIOUS_LCS_STATE = 113;
    static final int EXCEL_LCS_STATE = 114;
    static final int NEXT_FRANCHISE_STATE = 115;
    static final int PREVIOUS_FRANCHISE_STATE = 116;
    static final int EXCEL_FRANCHISE_STATE = 117;
    static final int GO_TO_SHOW_FRANCHISE_DETAILS = 118;
    static final int SEARCH_FRANCHISE_DETAILS = 119;
    static final int NEXT_FRANCHISE_DETAILS = 120;
    static final int PREVIOUS_FRANCHISE_DETAILS = 121;
    static final int SHOW_ALL_PRODUCTS_PGW_FOR_FRANCHISE = 122;
    static final int SHOW_SCRATCH_REPORT = 123;
    static final int SEARCH_SCRATCH_REPORT = 124;
    static final int SHOW_PRODUCT_REPORT = 125;
    static final int SEARCH_PRODUCT_REPORT = 126;
    static final int SHOW_TOTAL_REPORT = 127;
    static final int SEARCH_TOTAL_REPORT = 128;
    static final int SHOW_SCRATCH_REPORT_FOR_FRANCHISE = 129;
    static final int SHOW_PRODUCT_REPORT_FOR_FRANCHISE = 130;
    static final int SHOW_TOTAL_REPORT_FOR_FRANCHISE = 131;
    static final int EXPORT_SCRATCH_REPORT = 132;
    static final int EXPORT_PRODUCT_REPORT = 133;
    static final int EXPORT_TOTAL_REPORT = 134;
    static final int SHOW_ALL_SCHEMAS_FOR_COMPLEMENTARY_CHANNEL = 135;
    static final int CREATE_NEW_REQUEST_FOR_COMPLEMANTRY = 136;
    static final int ADMIN_CREATE_NEW_REQUEST_FOR_COMPLEMANTRY = 137;
    static final int SHOW_ALL_REQUESTS_CHANGE_TO_PAID_FOR_COMPLEMANTRY = 138;
    static final int SHOW_ALL_REQUESTS_FOR_COMPLEMANTRY = 139;
    static final int SELECT_COMPLEMANTRY_QUOTA_SETTINGS = 140;
    static final int SELECT_COMPLEMANTRY_QUOTA_VALUES = 141;
    static final int FIXED_QUOTA_VALUES_FOR_COMPLEMANTRY = 142;
    static final int SHOW_USER_COMPLEMANTRY_SCREEN_SOP = 143;
    static final int DATA_WAREHOUSE_IMPORT_FOR_COMPLEMANTRY = 144;
    static final int VIEW_STOCK_PRODUCTS_FOR_COMPLEMANTRY = 145;
    static final int IMPORT_LCS_PRODUCTS_PHYSICAL_AMOUNT_FOR_COMPLEMANTRY = 146;
    static final int SHOW_ALL_PRODUCTS_PGW_FOR_COMPLEMANTRY = 147;
    static final int DELETE_IMPORTED_PRODUCTS_FOR_COMPLEMANTRY = 148;
    static final int SHOW_SCRATCH_REPORT_FOR_COMPLEMENTARY = 149;
    static final int SHOW_PRODUCT_REPORT_FOR_COMPLEMENTARY = 150;
    static final int SHOW_TOTAL_REPORT_FOR_COMPLEMENTARY = 151;
    static final int IMPORT_PRODUCTS_FOR_COMPLEMENTARY = 152;
    static final int VIEW_PRODUCT_REPORT = 153;
    static final int SUBMIT_PRODUCTS = 154;
    static final int SEARCH_PRODUCTS = 155;
    static final int IMPORT_PRODUCTS_FOR_AUTHORIZED_AGENT = 156;
    static final int SHOW_ALL_SCHEMAS_FOR_AUTHORIZED_AGENT = 157;
    static final int CREATE_NEW_REQUEST_FOR_AUTHORZED_AGENT = 158;
    static final int ADMIN_CREATE_NEW_REQUEST_FOR_AUTHORIZED_AGENT = 159;
    static final int SHOW_ALL_REQUESTS_CHANGE_TO_PAID_FOR_AUTHORIZED_AGENT = 160;
    static final int SHOW_ALL_REQUESTS_FOR_AUTHORIZED_AGENT = 161;
    static final int SELECT_AUTHORIZED_AGENT_QUOTA_SETTINGS = 162;
    static final int SELECT_AUTHORIZED_AGENT_QUOTA_VALUES = 163;
    static final int FIXED_QUOTA_VALUES_FOR_AUTHORIZED_AGENT = 164;
    static final int SHOW_USER_AUTHORIZED_AGENT_SCREEN_SOP = 165;
    static final int DATA_WAREHOUSE_IMPORT_FOR_AUTHORIZED_AGENT = 166;
    static final int VIEW_STOCK_PRODUCTS_FOR_AUTHORIZED_AGENT = 167;
    static final int IMPORT_LCS_PRODUCTS_PHYSICAL_AMOUNT_FOR_AUTHORIZED_AGENT = 168;
    static final int SHOW_ALL_PRODUCTS_PGW_FOR_AUTHORIZED_AGENT = 169;
    static final int DELETE_IMPORTED_PRODUCTS_FOR_AUTHORIZED_AGENT = 170;
    static final int SHOW_SCRATCH_REPORT_FOR_AUTHORIZED_AGENT = 171;
    static final int SHOW_PRODUCT_REPORT_FOR_AUTHORIZED_AGENT = 172;
    static final int SHOW_TOTAL_REPORT_FOR_AUTHORIZED_AGENT = 173;
    static final int REQUEST_NOTIFICATION_MANAGEMENT_FOR_FRANCHISE = 174;
    static final int REQUEST_NOTIFICATION_MANAGEMENT_FOR_COMPLEMENTARY = 175;
    static final int REQUEST_NOTIFICATION_MANAGEMENT_FOR_AUTHORIZED_AGENT = 176;
    static final int SHOW_ALL_REQUESTS_CHANGE_TO_PAID_BY_CHANNEL = 177;
    static final int SHOW_ALL_REQUESTS_BY_CHANNEL = 178;
    static final int REQUEST_NOTIFICATION_MANAGEMENT_BY_CHANNEL = 179;
    static final int IMPORT_PRODUCTS_BY_CHANNEL = 180;
    static final int VIEW_WAREHOUSE_CHANNEL = 181;
    static final int EDIT_WAREHOUSE_CHANNEL = 182;
    static final int ADD_WAREHOUSE_CHANNEL = 183;
    static final int UPDATE_WAREHOUSE_CHANNEL = 184;
    static final int SAVE_WAREHOUSE_CHANNEL = 185;
    static final int DELETE_WAREHOUSE_CHANNEL = 186;
    static final int USER_CHANNEL_LIST = 187;
    static final int UPDATE_USER_CHANNEL_LIST = 188;
    static final int SHOW_ALL_SCHEMAS_BY_CHANNEL = 189;
    static final int VIEW_STOCK_PRODUCTS_BY_CHANNEL_WAREHOUSE = 190;
    static final int IMPORT_LCS_PRODUCTS_PYHSICAL_AMOUNT_BY_CHANNEL_WAREHOUSE = 191;
    static final int SHOW_USER_DCM_SCREEN_SOP_BY_CHANNEL = 192;
    static final int SHOW_SCRATCH_REPORT_BY_CHANNEL = 193;
    static final int SHOW_PRODUCT_REPORT_BY_CHANNEL = 194;
    static final int SHOW_TOTAL_REPORT_BY_CHANNEL = 195;
    static final int REQUEST_ADMIN_CHANGE_STATUS = 196;
    static final int VIEW_REQUEST_STATUS_LIST = 197;
    static final int SEARCH_REQUEST_STATUS_LIST = 198;
    static final int UPDATE_REQUEST_STATUS_LIST = 199;
    static final int SHOW_ALL_PRODUCTS_PGW_BY_CHANNEL = 200;
    static final int SELECT_DCM_QUOTA_SETTINGS_BY_CHANNEL_SCM = 201;
    static final int FIXED_QUOTA_VALUES_BY_CHANNEL = 202;
    static final int VIEW_WAREHOUSE_LIST = 203;
    static final int DELETE_WAREHOUSE_LIST = 204;
    static final int CREATE_NEW_WAREHOUSE = 205;
    static final int SAVE_NEW_WAREHOUSE = 206;
    static final int DELETE_IMPORTED_PRODUCTS_FOR_DISTRIBUTORS_BY_CHANNEL = 207;
    static final int SHOW_ALL_ITEMS = 208;
    static final int DELETE_ITEM = 209;
    static final int CREATE_ITEM = 210;
    static final int SAVE_ITEM = 211;
    static final int UPDATE_ITEM = 212;
    static final int EDIT_ITEM = 213;
    static final int DELETE_ALL_ITEMS = 214;
    static final int SHOW_USER_DELETE_CONTROL = 215;
    static final int USER_DELETE_CONTROL = 216;

    public static HashMap handle(String action, HashMap paramHashMap, java.sql.Connection con) {
        int actionType = 0;
        HashMap dataHashMap = null;

        // Connection  con = null;


        try {
            //  con = Utility.getConnection();
            if (action.equals(SOPInterfaceKey.ACTION_SHOW_ALL_SCHEMAS)) {
                actionType = SHOW_ALL_SCHEMAS;
            } else if (action.equals(SOPInterfaceKey.ACTION_CREATE_NEW_SCHEMA)) {
                actionType = CREATE_NEW_SCHEMA;
            } else if (action.equals(SOPInterfaceKey.ACTION_VIEW_SCHEMA_PRODUCTS)) {
                actionType = VIEW_SCHEMA_PRODUCTS;
            } else if (action.equals(SOPInterfaceKey.ACTION_SAVE_SCHEMA)) {
                actionType = SAVE_SCHEMA;
            } else if (action.equals(SOPInterfaceKey.ACTION_UPDATE_SCHEMA_PRODUCTS)) {
                actionType = UPDATE_SCHEMA_PRODUCTS;
            } else if (action.equals(SOPInterfaceKey.ACTION_IMPORT_PRODUCTS)) {
                actionType = IMPORT_PRODUCTS;
            } else if (action.equals(SOPInterfaceKey.ACTION_IMPORT_PRODUCTS_AND_CREATE_SCHEMA)) {
                actionType = IMPORT_PRODUCTS_AND_CREATE_SCHEMA;
            } else if (action.equals(SOPInterfaceKey.ACTION_CHANGE_SCHEMA_STATUS)) {
                actionType = CHANGE_SCHEMA_STATUS;
            } else if (action.equals(SOPInterfaceKey.ACTION_SEARCH_SCHEMA)) {
                actionType = SEARCH_SCHEMA;
            } else if (action.equals(SOPInterfaceKey.ACTION_CREATE_NEW_REQUEST)) {
                actionType = CREATE_NEW_REQUEST;
            } else if (action.equals(SOPInterfaceKey.ACTION_ADMIN_CREATE_NEW_REQUEST)) {
                actionType = ADMIN_CREATE_NEW_REQUEST;
            } else if (action.equals(SOPInterfaceKey.ACTION_REQUEST_SCHEMA_PRODUCTS)) {
                actionType = REQUEST_SCHEMA_PRODUCTS;
            } else if (action.equals(SOPInterfaceKey.ACTION_SAVE_REQUEST)) {
                actionType = SAVE_REQUEST;
            } else if (action.equals(SOPInterfaceKey.ACTION_ADMIN_REQUEST_SCHEMA_PRODUCTS)) {
                actionType = ADMIN_REQUEST_SCHEMA_PRODUCTS;
            } else if (action.equals(SOPInterfaceKey.ACTION_ADMIN_SAVE_REQUEST)) {
                actionType = ADMIN_SAVE_REQUEST;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_ALL_REQUESTS)) {
                actionType = SHOW_ALL_REQUESTS;
            } else if (action.equals(SOPInterfaceKey.ACTION_UPDATE_REQUEST_STATUS)) {
                actionType = UPDATE_REQUEST_STATUS;
            } else if (action.equals(SOPInterfaceKey.ACTION_VIEW_REQUEST_DETAILS)) {
                actionType = VIEW_REQUEST_DETAILS;
            } else if (action.equals(SOPInterfaceKey.ACTION_DATA_WAREHOUSE_IMPORT)) {
                actionType = DATA_WAREHOUSE_IMPORT;
            } else if (action.equals(SOPInterfaceKey.ACTION_DATA_WAREHOUSE_IMPORT_PROCESS)) {
                actionType = DATA_WAREHOUSE_IMPORT_PROCESS;
            } else if (action.equals(SOPInterfaceKey.ACTION_GENERATE_DATA_WAREHOUSE_IMPORT_TEMPLATE)) {
                actionType = GENERATE_DATA_WAREHOUSE_IMPORT_TEMPLATE;
            } else if (action.equals(SOPInterfaceKey.ACTION_VIEW_STOCK_PRODUCTS)) {
                actionType = VIEW_STOCK_PRODUCTS;
            } else if (action.equals(SOPInterfaceKey.ACTION_SEARCH_STOCK_PRODUCT)) {
                actionType = SEARCH_STOCK_PRODUCT;
            } else if (action.equals(SOPInterfaceKey.ACTION_GENERATE_MANUAL_DCM_PRODUCT_LIMITS)) {
                actionType = GENERATE_MANUAL_DCM_PRODUCT_LIMITS;
            } else if (action.equals(SOPInterfaceKey.ACTION_GENERATE_MANUAL_DCM_PRODUCT_LIMITS_CHECKING)) {
                actionType = GENERATE_MANUAL_DCM_PRODUCT_LIMITS_CHECKING;
            } else if (action.equals(SOPInterfaceKey.ACTION_ADMIN_SELECT_DCM_REQUEST_PRODUCT_LIMITS)) {
                actionType = ADMIN_SELECT_DCM_REQUEST_PRODUCT_LIMITS;
            } else if (action.equals(SOPInterfaceKey.ACTION_ADMIN_SET_DCM_REQUEST_PRODUCT_LIMITS)) {
                actionType = ADMIN_SET_DCM_REQUEST_PRODUCT_LIMITS;
            } else if (action.equals(SOPInterfaceKey.ACTION_SAVE_DCM_REQUEST_PRODUCT_LIMITS)) {
                actionType = SAVE_DCM_REQUEST_PRODUCT_LIMITS;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_ALL_EQUATIONS)) {
                actionType = SHOW_ALL_EQUATIONS;
            } else if (action.equals(SOPInterfaceKey.ACTION_ADD_EQUATION)) {
                actionType = ADD_EQUATION;
            } else if (action.equals(SOPInterfaceKey.ACTION_EDIT_EQUATION)) {
                actionType = EDIT_EQUATION;
            } else if (action.equals(SOPInterfaceKey.ACTION_UPDATE_EQUATION_STATUS)) {
                actionType = UPDATE_EQUATION_STATUS;
            } else if (action.equals(SOPInterfaceKey.ACTION_SAVE_EQUATION)) {
                actionType = SAVE_EQUATION;
            } else if (action.equals(SOPInterfaceKey.ACTION_SELECT_DCM_QUOTA_SETTINGS)) {
                actionType = SELECT_DCM_QUOTA_SETTINGS;
            } else if (action.equals(SOPInterfaceKey.ACTION_SET_DCM_QUOTA_SETTING_VALUES)) {
                actionType = SET_DCM_QUOTA_SETTING_VALUES;
            } else if (action.equals(SOPInterfaceKey.ACTION_UPDATE_DCM_QUOTA_SETTING_VALUE)) {
                actionType = UPDATE_DCM_QUOTA_SETTING_VALUE;
            } else if (action.equals(SOPInterfaceKey.ACTION_SELECT_DCM_QUOTA_VALUES)) {
                actionType = SELECT_DCM_QUOTA_VALUES;
            } else if (action.equals(SOPInterfaceKey.ACTION_SET_DCM_QUOTA_VALUES)) {
                actionType = SET_DCM_QUOTA_VALUES;
            } else if (action.equals(SOPInterfaceKey.ACTION_UPDATE_DCM_QUOTA_VALUE)) {
                actionType = UPDATE_DCM_QUOTA_VALUE;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_USER_DCM_SCREEN_SOP)) {
                actionType = SHOW_USER_DCM_SCREEN_SOP;
            } else if (action.equals(SOPInterfaceKey.ACTION_SOP_UPDATE_USER_DCM)) {
                actionType = SOP_UPDATE_USER_DCM;
            } else if (action.equals(SOPInterfaceKey.ACTION_SEARCH_REQUEST)) {
                actionType = SEARCH_REQUEST;
            } else if (action.equals(SOPInterfaceKey.ACTION_UPDATE_EQUATION)) {
                actionType = UPDATE_EQUATION;
            } else if (action.equals(SOPInterfaceKey.ACTION_ASSIGN_EQUATION_TO_PRODUCTS)) {
                actionType = ASSIGN_EQUATION_TO_PRODUCTS;
            } else if (action.equals(SOPInterfaceKey.ACTION_SAVE_ASSIGN_EQUATION_TO_PRODUCTS)) {
                actionType = SAVE_ASSIGN_EQUATION_TO_PRODUCTS;
            } else if (action.equals(SOPInterfaceKey.ACTION_DCM_PRODUCT_LIMITS_BY_EQUATION)) {
                actionType = DCM_PRODUCT_LIMITS_BY_EQUATION;
            } else if (action.equals(SOPInterfaceKey.ACTION_SAVE_DCM_PRODUCT_LIMITS_BY_EQUATION)) {
                actionType = SAVE_DCM_PRODUCT_LIMITS_BY_EQUATION;
            } else if (action.equals(SOPInterfaceKey.ACTION_DCM_PRODUCT_LIMITS_BY_EQUATION_SELECT_PRODUCTS)) {
                actionType = DCM_PRODUCT_LIMITS_BY_EQUATION_SELECT_PRODUCTS;
            } else if (action.equals(UserAccountInterfaceKey.ACTION_GET_DCM_REQUEST)) {
                actionType = GET_DCM_REQUEST;
            } else if (action.equals(SOPInterfaceKey.ACTION_IMPORT_PRODUCTS_FROM_PGW)) {
                actionType = IMPORT_PRODUCTS_FROM_PGW;
            } else if (action.equals(SOPInterfaceKey.ACTION_FIXED_QUOTA_VALUES)) {
                actionType = FIXED_QUOTA_VALUES;
            } else if (action.equals(SOPInterfaceKey.ACTION_SAVE_FIXED_QUOTA_VALUES)) {
                actionType = SAVE_FIXED_QUOTA_VALUES;
            } else if (action.equals(SOPInterfaceKey.ACTION_REQUEST_NOTIFICATION_MANAGEMENT)) {
                actionType = REQUEST_NOTIFICATION_MANAGEMENT;
            } else if (action.equals(SOPInterfaceKey.ACTION_EDIT_REQUEST_NOTIFICATION)) {
                actionType = EDIT_REQUEST_NOTIFICATION;
            } else if (action.equals(SOPInterfaceKey.ACTION_DELETE_REQUEST_NOTIFICATION)) {
                actionType = DELETE_REQUEST_NOTIFICATION;
            } else if (action.equals(SOPInterfaceKey.ACTION_CREATE_REQUEST_NOTIFICATION)) {
                actionType = CREATE_REQUEST_NOTIFICATION;
            } else if (action.equals(SOPInterfaceKey.ACTION_SAVE_REQUEST_NOTIFICATION)) {
                actionType = SAVE_REQUEST_NOTIFICATION;
            } else if (action.equals(SOPInterfaceKey.ACTION_QUOTA_CALCULATION_BY_DATAVIEW)) {
                actionType = QUOTA_CALCULATION_BY_DATAVIEW;
            } else if (action.equals(SOPInterfaceKey.ACTION_IMPORT_LCS_PRODUCTS_PHYSICAL_AMOUNT)) {
                actionType = IMPORT_LCS_PRODUCTS_PHYSICAL_AMOUNT;
            } else if (action.equals(SOPInterfaceKey.ACTION_CALCULATE_QUOTA_BY_DATAVIEW)) {
                actionType = CALCULATE_QUOTA_BY_DATAVIEW;
            } else if (action.equals(SOPInterfaceKey.ACTION_CREATE_NEW_PRODUCT_PGW)) {
                actionType = CREATE_NEW_PRODUCT_PGW;
            } else if (action.equals(SOPInterfaceKey.ACTION_SAVE_PRODUCT_PGW)) {
                actionType = SAVE_PRODUCT_PGW;
            } else if (action.equals(SOPInterfaceKey.ACTION_EDIT_PRODUCT_PGW)) {
                actionType = EDIT_PRODUCT_PGW;
            } else if (action.equals(SOPInterfaceKey.ACTION_UPDATE_PRODUCT_PGW)) {
                actionType = UPDATE_PRODUCT_PGW;
            } else if (action.equals(SOPInterfaceKey.ACTION_CREATE_NEW_INVOICE)) {
                actionType = CREATE_NEW_INVOICE;
            } else if (action.equals(SOPInterfaceKey.ACTION_SAVE_NEW_INVOICE)) {
                actionType = SAVE_NEW_INVOICE;
            } else if (action.equals(SOPInterfaceKey.ACTIOM_EDIT_INVOICE)) {
                actionType = EDIT_INVOICE;
            } else if (action.equals(SOPInterfaceKey.ACTION_UPDATE_INVOICE_DATA)) {
                actionType = UPDATE_INVOICE_DATA;
            } else if (action.equals(SOPInterfaceKey.ACTION_ADD_EDIT_INVOICE_DETAIL)) {
                actionType = ADD_EDIT_INVOICE_DETAIL;
            } else if (action.equals(SOPInterfaceKey.ACTION_SAVE_INVOICE_DETAIL)) {
                actionType = SAVE_INVOICE_DETAIL;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_INVOICE_DETAIL_DATA)) {
                actionType = SHOW_INVOICE_DETAIL_DATA;
            } else if (action.equals(SOPInterfaceKey.ACTION_CREATE_NEW_INVOICE_DETAIL)) {
                actionType = CREATE_NEW_INVOICE_DETAIL;
            } else if (action.equals(SOPInterfaceKey.ACTION_UPDATE_INVOICE_DETAIL_DATA)) {
                actionType = UPDATE_INVOICE_DETAIL_DATA;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_ALL_REQUESTS_CHANGE_TO_PAID)) {
                actionType = SHOW_ALL_REQUESTS_CHANGE_TO_PAID;
            } else if (action.equals(SOPInterfaceKey.ACTION_SEARCH_REQUEST_CHANGE_TO_PAID)) {
                actionType = SEARCH_REQUEST_CHANGE_TO_PAID;
            } else if (action.equals(SOPInterfaceKey.ACTION_UPDATE_REQUEST_STATUS_CHANGE_TO_PAID)) {
                actionType = UPDATE_REQUEST_STATUS_CHANGE_TO_PAID;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_PRODUCTS_FROM_LCS)) {
                actionType = SHOW_PRODUCTS_FROM_LCS;
            } else if (action.equals(SOPInterfaceKey.ACTION_CREATE_PRODUCT_LCS)) {
                actionType = CREATE_PRODUCT_LCS;
            } else if (action.equals(SOPInterfaceKey.ACTION_SAVE_PRODUCT_LCS)) {
                actionType = SAVE_PRODUCT_LCS;
            } else if (action.equals(SOPInterfaceKey.ACTION_EDIT_PRODUCT_LCS)) {
                actionType = EDIT_PRODUCT_LCS;
            } else if (action.equals(SOPInterfaceKey.ACTION_UPDATE_PRODUCT_LCS)) {
                actionType = UPDATE_PRODUCT_LCS;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_ALL_INVOICES)) {
                actionType = SHOW_ALL_INVOICES;
            } else if (action.equals(SOPInterfaceKey.ACTION_SEARCH_INVOICE)) {
                actionType = SEARCH_INVOICE;
            } else if (action.equals(SOPInterfaceKey.ACTION_INVOICE_BACK)) {
                actionType = INVOICE_BACK;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_ALL_PRODUCTS_PGW)) {
                actionType = SHOW_ALL_PRODUCTS_PGW;
            } else if (action.equals(SOPInterfaceKey.ACTION_SEARCH_PRODUCT_PGW)) {
                actionType = SEARCH_PRODUCT_PGW;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_ALL_SCHEMAS_FOR_FRANCHISE)) {
                actionType = SHOW_ALL_SCHEMAS_FOR_FRANCHISE;
            } else if (action.equals(SOPInterfaceKey.ACTION_IMPORT_PRODUCTS_FOR_FRANCHISE)) {
                actionType = IMPORT_PRODUCTS_FOR_FRANCHISE;
            } else if (action.equals(SOPInterfaceKey.ACTION_CREATE_NEW_REQUEST_FOR_FRANCHISE)) {
                actionType = CREATE_NEW_REQUEST_FOR_FRANCHISE;
            } else if (action.equals(SOPInterfaceKey.ACTION_ADMIN_CREATE_NEW_REQUEST_FOR_FRANCHISE)) {
                actionType = ADMIN_CREATE_NEW_REQUEST_FOR_FRANCHISE;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_ALL_REQUESTS_CHANGE_TO_PAID_FOR_FRANCHISE)) {
                actionType = SHOW_ALL_REQUESTS_CHANGE_TO_PAID_FOR_FRANCHISE;
            } else if (action.equals(SOPInterfaceKey.ACTION_IMPORT_PRODUCTS_FOR_COMPLEMENTARY)) {
                actionType = IMPORT_PRODUCTS_FOR_COMPLEMENTARY;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_ALL_REQUESTS_CHANGE_TO_PAID_FOR_COMPLEMANTRY)) {
                actionType = SHOW_ALL_REQUESTS_CHANGE_TO_PAID_FOR_COMPLEMANTRY;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_ALL_REQUESTS_FOR_FRANCHISE)) {
                actionType = SHOW_ALL_REQUESTS_FOR_FRANCHISE;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_ALL_REQUESTS_FOR_COMPLEMANTRY)) {
                actionType = SHOW_ALL_REQUESTS_FOR_COMPLEMANTRY;
            } else if (action.equals(SOPInterfaceKey.ACTION_SELECT_FRANCHISE_QUOTA_SETTINGS)) {
                actionType = SELECT_FRANCHISE_QUOTA_SETTINGS;
            } else if (action.equals(SOPInterfaceKey.ACTION_SELECT_COMPLEMANTRY_QUOTA_SETTINGS)) {
                actionType = SELECT_COMPLEMANTRY_QUOTA_SETTINGS;
            } else if (action.equals(SOPInterfaceKey.ACTION_SELECT_FRANCHISE_QUOTA_VALUES)) {
                actionType = SELECT_FRANCHISE_QUOTA_VALUES;
            } else if (action.equals(SOPInterfaceKey.ACTION_SELECT_COMPLEMANTRY_QUOTA_VALUES)) {
                actionType = SELECT_COMPLEMANTRY_QUOTA_VALUES;
            } else if (action.equals(SOPInterfaceKey.ACTION_FIXED_QUOTA_VALUES_FOR_FRANCHISE)) {
                actionType = FIXED_QUOTA_VALUES_FOR_FRANCHISE;
            } else if (action.equals(SOPInterfaceKey.ACTION_FIXED_QUOTA_VALUES_FOR_COMPLEMANTRY)) {
                actionType = FIXED_QUOTA_VALUES_FOR_COMPLEMANTRY;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_USER_FRANCHISE_SCREEN_SOP)) {
                actionType = SHOW_USER_FRANCHISE_SCREEN_SOP;
            } // COMPLEMANTRY ADDED
            else if (action.equals(SOPInterfaceKey.ACTION_SHOW_USER_COMPLEMANTRY_SCREEN_SOP)) {
                actionType = SHOW_USER_COMPLEMANTRY_SCREEN_SOP;
            } else if (action.equals(SOPInterfaceKey.ACTION_DATA_WAREHOUSE_IMPORT_FOR_FRANCHISE)) {
                actionType = DATA_WAREHOUSE_IMPORT_FOR_FRANCHISE;
            } else if (action.equals(SOPInterfaceKey.ACTION_DATA_WAREHOUSE_IMPORT_FOR_COMPLEMANTRY)) {
                actionType = DATA_WAREHOUSE_IMPORT_FOR_COMPLEMANTRY;
            } else if (action.equals(SOPInterfaceKey.ACTION_VIEW_STOCK_PRODUCTS_FOR_FRANCHISE)) {
                actionType = VIEW_STOCK_PRODUCTS_FOR_FRANCHISE;
            } // COMPLEMANTRY ADDED
            else if (action.equals(SOPInterfaceKey.ACTION_VIEW_STOCK_PRODUCTS_FOR_COMPLEMANTRY)) {
                actionType = VIEW_STOCK_PRODUCTS_FOR_COMPLEMANTRY;
            } else if (action.equals(SOPInterfaceKey.ACTION_IMPORT_LCS_PRODUCTS_PHYSICAL_AMOUNT_FOR_FRANCHISE)) {
                actionType = IMPORT_LCS_PRODUCTS_PHYSICAL_AMOUNT_FOR_FRANCHISE;
            } // COMPLEMANTRY ADDED
            else if (action.equals(SOPInterfaceKey.ACTION_IMPORT_LCS_PRODUCTS_PHYSICAL_AMOUNT_FOR_COMPLEMANTRY)) {
                actionType = IMPORT_LCS_PRODUCTS_PHYSICAL_AMOUNT_FOR_COMPLEMANTRY;
            } else if (action.equals(SOPInterfaceKey.ACTION_DELETE_PRODUCT_LCS)) {
                actionType = DELETE_PRODUCT_LCS;
            } else if (action.equals(SOPInterfaceKey.ACTION_DELETE_PRODUCT_FROM_PGW)) {
                actionType = DELETE_PRODUCT_FROM_PGW;
            } else if (action.equals(SOPInterfaceKey.ACTION_DELETE_SCHEMA_PRODUCT)) {
                actionType = DELETE_SCHEMA_PRODUCT;
            } else if (action.equals(SOPInterfaceKey.ACTION_DELETE_IMPORTED_PRODUCTS_FOR_DISTRIBUTORS)) {
                actionType = DELETE_IMPORTED_PRODUCTS_FOR_DISTRIBUTORS;
            } else if (action.equals(SOPInterfaceKey.ACTION_DELETE_IMPORTED_PRODUCTS_FOR_FRANCHISE)) {
                actionType = DELETE_IMPORTED_PRODUCTS_FOR_FRANCHISE;
            } else if (action.equals(SOPInterfaceKey.ACTION_DELETE_IMPORTED_PRODUCTS_FOR_COMPLEMANTRY)) {
                actionType = DELETE_IMPORTED_PRODUCTS_FOR_COMPLEMANTRY;
            } else if (action.equals(SOPInterfaceKey.ACTION_SEARCH_FRANCHISE_STATE)) {
                actionType = SEARCH_FRANCHISE_STATE;
            } else if (action.equals(SOPInterfaceKey.ACTION_EXCEL_FRANCHISE_STATE)) {
                actionType = EXCEL_FRANCHISE_STATE;
            } else if (action.equals(SOPInterfaceKey.ACTION_SEARCH_LCS_STATE)) {
                actionType = SEARCH_LCS_STATE;
            } else if (action.equals(SOPInterfaceKey.ACTION_EXCEL_LCS_STATE)) {
                actionType = EXCEL_LCS_STATE;
            } else if (action.equals(SOPInterfaceKey.ACTION_SEARCH_PGW_STATE)) {
                actionType = SEARCH_PGW_STATE;
            } else if (action.equals(SOPInterfaceKey.ACTION_EXCEL_PGW_STATE)) {
                actionType = EXCEL_PGW_STATE;
            } else if (action.equals(SOPInterfaceKey.ACTION_PRINT_REQUEST_PAYMENT_SLIP)) {
                actionType = PRINT_REQUEST_PAYMENT_SLIP;
            } else if (action.equals(SOPInterfaceKey.ACTION_NEXT_PGW_STATE)) {
                actionType = NEXT_PGW_STATE;
            } else if (action.equals(SOPInterfaceKey.ACTION_PREVIOUS_PGW_STATE)) {
                actionType = PREVIOUS_PGW_STATE;
            } else if (action.equals(SOPInterfaceKey.ACTION_NEXT_LCS_STATE)) {
                actionType = NEXT_LCS_STATE;
            } else if (action.equals(SOPInterfaceKey.ACTION_PREVIOUS_LCS_STATE)) {
                actionType = PREVIOUS_LCS_STATE;
            } else if (action.equals(SOPInterfaceKey.ACTION_NEXT_FRANCHISE_STATE)) {
                actionType = NEXT_FRANCHISE_STATE;
            } else if (action.equals(SOPInterfaceKey.ACTION_PREVIOUS_FRANCHISE_STATE)) {
                actionType = PREVIOUS_FRANCHISE_STATE;
            } else if (action.equals(SOPInterfaceKey.ACTION_GO_TO_SHOW_FRANCHISE_DETAILS)) {
                actionType = GO_TO_SHOW_FRANCHISE_DETAILS;
            } else if (action.equals(SOPInterfaceKey.ACTION_SEARCH_FRANCHISE_DETAILS)) {
                actionType = SEARCH_FRANCHISE_DETAILS;
            } else if (action.equals(SOPInterfaceKey.ACTION_NEXT_FRANCHISE_DETAILS)) {
                actionType = NEXT_FRANCHISE_DETAILS;
            } else if (action.equals(SOPInterfaceKey.ACTION_PREVIOUS_FRANCHISE_DETAILS)) {
                actionType = PREVIOUS_FRANCHISE_DETAILS;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_ALL_PRODUCTS_PGW_FOR_FRANCHISE)) {
                actionType = SHOW_ALL_PRODUCTS_PGW_FOR_FRANCHISE;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_ALL_PRODUCTS_PGW_FOR_COMPLEMANTRY)) {
                actionType = SHOW_ALL_PRODUCTS_PGW_FOR_COMPLEMANTRY;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_SCRATCH_REPORT)) {
                actionType = SHOW_SCRATCH_REPORT;
            } else if (action.equals(SOPInterfaceKey.ACTION_SEARCH_SCRATCH_REPORT)) {
                actionType = SEARCH_SCRATCH_REPORT;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_PRODUCT_REPORT)) {
                actionType = SHOW_PRODUCT_REPORT;
            } else if (action.equals(SOPInterfaceKey.ACTION_SEARCH_PRODUCT_REPORT)) {
                actionType = SEARCH_PRODUCT_REPORT;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_TOTAL_REPORT)) {
                actionType = SHOW_TOTAL_REPORT;
            } else if (action.equals(SOPInterfaceKey.ACTION_SEARCH_TOTAL_REPORT)) {
                actionType = SEARCH_TOTAL_REPORT;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_SCRATCH_REPORT_FOR_FRANCHISE)) {
                actionType = SHOW_SCRATCH_REPORT_FOR_FRANCHISE;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_PRODUCT_REPORT_FOR_FRANCHISE)) {
                actionType = SHOW_PRODUCT_REPORT_FOR_FRANCHISE;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_TOTAL_REPORT_FOR_FRANCHISE)) {
                actionType = SHOW_TOTAL_REPORT_FOR_FRANCHISE;
            } else if (action.equals(SOPInterfaceKey.ACTION_EXPORT_SCRATCH_REPORT)) {
                actionType = EXPORT_SCRATCH_REPORT;
            } else if (action.equals(SOPInterfaceKey.ACTION_EXPORT_PRODUCT_REPORT)) {
                actionType = EXPORT_PRODUCT_REPORT;
            } else if (action.equals(SOPInterfaceKey.ACTION_EXPORT_TOTAL_REPORT)) {
                actionType = EXPORT_TOTAL_REPORT;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_ALL_SCHEMAS_FOR_COMPLEMENTARY_CHANNEL)) {
                actionType = SHOW_ALL_SCHEMAS_FOR_COMPLEMENTARY_CHANNEL;
            } else if (action.equals(SOPInterfaceKey.ACTION_CREATE_NEW_REQUEST_FOR_COMPLEMANTRY)) {
                actionType = CREATE_NEW_REQUEST_FOR_COMPLEMANTRY;
            } else if (action.equals(SOPInterfaceKey.ACTION_ADMIN_CREATE_NEW_REQUEST_FOR_COMPLEMANTRY)) {
                actionType = ADMIN_CREATE_NEW_REQUEST_FOR_COMPLEMANTRY;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_SCRATCH_REPORT_FOR_COMPLEMENTARY)) {
                actionType = SHOW_SCRATCH_REPORT_FOR_COMPLEMENTARY;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_PRODUCT_REPORT_FOR_COMPLEMENTARY)) {
                actionType = SHOW_PRODUCT_REPORT_FOR_COMPLEMENTARY;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_TOTAL_REPORT_FOR_COMPLEMENTARY)) {
                actionType = SHOW_TOTAL_REPORT_FOR_COMPLEMENTARY;
            } else if (action.equals(SOPInterfaceKey.ACTION_VIEW_PRODUCT_REPORT)) {
                actionType = VIEW_PRODUCT_REPORT;
            } else if (action.equals(SOPInterfaceKey.ACTION_SUBMIT_PRODUCTS)) {
                actionType = SUBMIT_PRODUCTS;
            } else if (action.equals(SOPInterfaceKey.ACTION_SEARCH_PRODUCTS)) {
                actionType = SEARCH_PRODUCTS;
            } else if (action.equals(SOPInterfaceKey.ACTION_IMPORT_PRODUCTS_FOR_AUTHORIZED_AGENT)) {
                actionType = IMPORT_PRODUCTS_FOR_AUTHORIZED_AGENT;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_ALL_SCHEMAS_FOR_AUTHORIZED_AGENT)) {
                actionType = SHOW_ALL_SCHEMAS_FOR_AUTHORIZED_AGENT;
            } else if (action.equals(SOPInterfaceKey.ACTION_CREATE_NEW_REQUEST_FOR_AUTHORZED_AGENT)) {
                actionType = CREATE_NEW_REQUEST_FOR_AUTHORZED_AGENT;
            } else if (action.equals(SOPInterfaceKey.ACTION_ADMIN_CREATE_NEW_REQUEST_FOR_AUTHORIZED_AGENT)) {
                actionType = ADMIN_CREATE_NEW_REQUEST_FOR_AUTHORIZED_AGENT;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_ALL_REQUESTS_CHANGE_TO_PAID_FOR_AUTHORIZED_AGENT)) {
                actionType = SHOW_ALL_REQUESTS_CHANGE_TO_PAID_FOR_AUTHORIZED_AGENT;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_ALL_REQUESTS_FOR_AUTHORIZED_AGENT)) {
                actionType = SHOW_ALL_REQUESTS_FOR_AUTHORIZED_AGENT;
            } else if (action.equals(SOPInterfaceKey.ACTION_SELECT_AUTHORIZED_AGENT_QUOTA_SETTINGS)) {
                actionType = SELECT_AUTHORIZED_AGENT_QUOTA_SETTINGS;
            } else if (action.equals(SOPInterfaceKey.ACTION_SELECT_AUTHORIZED_AGENT_QUOTA_VALUES)) {
                actionType = SELECT_AUTHORIZED_AGENT_QUOTA_VALUES;
            } else if (action.equals(SOPInterfaceKey.ACTION_FIXED_QUOTA_VALUES_FOR_AUTHORIZED_AGENT)) {
                actionType = FIXED_QUOTA_VALUES_FOR_AUTHORIZED_AGENT;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_USER_AUTHORIZED_AGENT_SCREEN_SOP)) {
                actionType = SHOW_USER_AUTHORIZED_AGENT_SCREEN_SOP;
            } else if (action.equals(SOPInterfaceKey.ACTION_DATA_WAREHOUSE_IMPORT_FOR_AUTHORIZED_AGENT)) {
                actionType = DATA_WAREHOUSE_IMPORT_FOR_AUTHORIZED_AGENT;
            } else if (action.equals(SOPInterfaceKey.ACTION_VIEW_STOCK_PRODUCTS_FOR_AUTHORIZED_AGENT)) {
                actionType = VIEW_STOCK_PRODUCTS_FOR_AUTHORIZED_AGENT;
            } else if (action.equals(SOPInterfaceKey.ACTION_IMPORT_LCS_PRODUCTS_PHYSICAL_AMOUNT_FOR_AUTHORIZED_AGENT)) {
                actionType = IMPORT_LCS_PRODUCTS_PHYSICAL_AMOUNT_FOR_AUTHORIZED_AGENT;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_ALL_PRODUCTS_PGW_FOR_AUTHORIZED_AGENT)) {
                actionType = SHOW_ALL_PRODUCTS_PGW_FOR_AUTHORIZED_AGENT;
            } else if (action.equals(SOPInterfaceKey.ACTION_DELETE_IMPORTED_PRODUCTS_FOR_AUTHORIZED_AGENT)) {
                actionType = DELETE_IMPORTED_PRODUCTS_FOR_AUTHORIZED_AGENT;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_SCRATCH_REPORT_FOR_AUTHORIZED_AGENT)) {
                actionType = SHOW_SCRATCH_REPORT_FOR_AUTHORIZED_AGENT;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_PRODUCT_REPORT_FOR_AUTHORIZED_AGENT)) {
                actionType = SHOW_PRODUCT_REPORT_FOR_AUTHORIZED_AGENT;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_TOTAL_REPORT_FOR_AUTHORIZED_AGENT)) {
                actionType = SHOW_TOTAL_REPORT_FOR_AUTHORIZED_AGENT;
            } else if (action.equals(SOPInterfaceKey.ACTION_REQUEST_NOTIFICATION_MANAGEMENT_FOR_FRANCHISE)) {
                actionType = REQUEST_NOTIFICATION_MANAGEMENT_FOR_FRANCHISE;
            } else if (action.equals(SOPInterfaceKey.ACTION_REQUEST_NOTIFICATION_MANAGEMENT_FOR_COMPLEMENTARY)) {
                actionType = REQUEST_NOTIFICATION_MANAGEMENT_FOR_COMPLEMENTARY;
            } else if (action.equals(SOPInterfaceKey.ACTION_REQUEST_NOTIFICATION_MANAGEMENT_FOR_AUTHORIZED_AGENT)) {
                actionType = REQUEST_NOTIFICATION_MANAGEMENT_FOR_AUTHORIZED_AGENT;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_ALL_REQUESTS_CHANGE_TO_PAID_BY_CHANNEL)) {
                actionType = SHOW_ALL_REQUESTS_CHANGE_TO_PAID_BY_CHANNEL;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_ALL_REQUESTS_BY_CHANNEL)) {
                actionType = SHOW_ALL_REQUESTS_BY_CHANNEL;
            } else if (action.equals(SOPInterfaceKey.ACTION_REQUEST_NOTIFICATION_MANAGEMENT_BY_CHANNEL)) {
                actionType = REQUEST_NOTIFICATION_MANAGEMENT_BY_CHANNEL;
            } else if (action.equals(SOPInterfaceKey.ACTION_IMPORT_PRODUCTS_BY_CHANNEL)) {
                actionType = IMPORT_PRODUCTS_BY_CHANNEL;
            } else if (action.equals(SOPInterfaceKey.ACTION_VIEW_WAREHOUSE_CHANNEL)) {
                actionType = VIEW_WAREHOUSE_CHANNEL;
            } else if (action.equals(SOPInterfaceKey.ACTION_EDIT_WAREHOUSE_CHANNEL)) {
                actionType = EDIT_WAREHOUSE_CHANNEL;
            } else if (action.equals(SOPInterfaceKey.ACTION_ADD_WAREHOUSE_CHANNEL)) {
                actionType = ADD_WAREHOUSE_CHANNEL;
            } else if (action.equals(SOPInterfaceKey.ACTION_UPDATE_WAREHOUSE_CHANNEL)) {
                actionType = UPDATE_WAREHOUSE_CHANNEL;
            } else if (action.equals(SOPInterfaceKey.ACTION_SAVE_WAREHOUSE_CHANNEL)) {
                actionType = SAVE_WAREHOUSE_CHANNEL;
            } else if (action.equals(SOPInterfaceKey.ACTION_DELETE_WAREHOUSE_CHANNEL)) {
                actionType = DELETE_WAREHOUSE_CHANNEL;
            } else if (action.equals(SOPInterfaceKey.ACTION_USER_CHANNEL_LIST)) {
                actionType = USER_CHANNEL_LIST;
            } else if (action.equals(SOPInterfaceKey.ACTION_UPDATE_USER_CHANNEL_LIST)) {
                actionType = UPDATE_USER_CHANNEL_LIST;
            } else if (action.equals(SOPInterfaceKey.Action_SHOW_ALL_SCHEMAS_BY_CHANNEL)) {
                actionType = SHOW_ALL_SCHEMAS_BY_CHANNEL;
            } else if (action.equals(SOPInterfaceKey.ACTION_VIEW_STOCK_PRODUCTS_BY_CHANNEL_WAREHOUSE)) {
                actionType = VIEW_STOCK_PRODUCTS_BY_CHANNEL_WAREHOUSE;
            } else if (action.equals(SOPInterfaceKey.ACTION_IMPORT_LCS_PRODUCTS_PYHSICAL_AMOUNT_BY_CHANNEL_WAREHOUSE)) {
                actionType = IMPORT_LCS_PRODUCTS_PYHSICAL_AMOUNT_BY_CHANNEL_WAREHOUSE;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_USER_DCM_SCREEN_SOP_BY_CHANNEL)) {
                actionType = SHOW_USER_DCM_SCREEN_SOP_BY_CHANNEL;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_SCRATCH_REPORT_BY_CHANNEL)) {
                actionType = SHOW_SCRATCH_REPORT_BY_CHANNEL;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_PRODUCT_REPORT_BY_CHANNEL)) {
                actionType = SHOW_PRODUCT_REPORT_BY_CHANNEL;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_TOTAL_REPORT_BY_CHANNEL)) {
                actionType = SHOW_TOTAL_REPORT_BY_CHANNEL;
            } else if (action.equals(SOPInterfaceKey.ACTION_REQUEST_ADMIN_CHANGE_STATUS)) {
                actionType = REQUEST_ADMIN_CHANGE_STATUS;
            } else if (action.equals(SOPInterfaceKey.ACTION_VIEW_REQUEST_STATUS_LIST)) {
                actionType = VIEW_REQUEST_STATUS_LIST;
            } else if (action.equals(SOPInterfaceKey.ACTION_SEARCH_REQUEST_STATUS_LIST)) {
                actionType = SEARCH_REQUEST_STATUS_LIST;
            } else if (action.equals(SOPInterfaceKey.ACTION_UPDATE_REQUEST_STATUS_LIST)) {
                actionType = UPDATE_REQUEST_STATUS_LIST;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_ALL_PRODUCTS_PGW_BY_CHANNEL)) {
                actionType = SHOW_ALL_PRODUCTS_PGW_BY_CHANNEL;
            } else if (action.equals(SOPInterfaceKey.ACTION_SELECT_DCM_QUOTA_SETTINGS_BY_CHANNEL_SCM)) {
                actionType = SELECT_DCM_QUOTA_SETTINGS_BY_CHANNEL_SCM;
            } else if (action.equals(SOPInterfaceKey.ACTION_FIXED_QUOTA_VALUES_BY_CHANNEL)) {
                actionType = FIXED_QUOTA_VALUES_BY_CHANNEL;
            } else if (action.equals(SOPInterfaceKey.ACTION_VIEW_WAREHOUSE_LIST)) {
                actionType = VIEW_WAREHOUSE_LIST;
            } else if (action.equals(SOPInterfaceKey.ACTION_DELETE_WAREHOUSE_LIST)) {
                actionType = DELETE_WAREHOUSE_LIST;
            } else if (action.equals(SOPInterfaceKey.ACTION_CREATE_NEW_WAREHOUSE)) {
                actionType = CREATE_NEW_WAREHOUSE;
            } else if (action.equals(SOPInterfaceKey.ACTION_SAVE_NEW_WAREHOUSE)) {
                actionType = SAVE_NEW_WAREHOUSE;
            } else if (action.equals(SOPInterfaceKey.ACTION_DELETE_IMPORTED_PRODUCTS_FOR_DISTRIBUTORS_BY_CHANNEL)) {
                actionType = DELETE_IMPORTED_PRODUCTS_FOR_DISTRIBUTORS_BY_CHANNEL;
            } else if (action.equals(SOPInterfaceKey.ACTION_SHOW_USER_DELETE_CONTROL)) {
                actionType = SHOW_USER_DELETE_CONTROL;
            } else if (action.equals(SOPInterfaceKey.ACTION_USER_DELETE_CONTROL)) {
                actionType = USER_DELETE_CONTROL;
            }





            switch (actionType) {

                case SHOW_SCRATCH_REPORT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        HashMap scratchReport = new HashMap();
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String reportlId = SOPInterfaceKey.CONST_SCRATCH_REPROT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID, reportlId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, scratchReport);
                        Vector warehouseVec = RequestDAO.getAllWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, warehouseVec);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_SCRATCH_REPORT_FOR_FRANCHISE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        HashMap scratchReport = new HashMap();
                        String channelId = SOPInterfaceKey.CONST_GEN_CHANNEL_FRANCHISE;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String reportlId = SOPInterfaceKey.CONST_SCRATCH_REPROT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID, reportlId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, scratchReport);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_SCRATCH_REPORT_FOR_COMPLEMENTARY:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        HashMap scratchReport = new HashMap();
                        String channelId = SOPInterfaceKey.CONST_GEN_COMPLEMENTARY_CHANNEL;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String reportlId = SOPInterfaceKey.CONST_SCRATCH_REPROT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID, reportlId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, scratchReport);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_SCRATCH_REPORT_FOR_AUTHORIZED_AGENT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        HashMap scratchReport = new HashMap();
                        String channelId = SOPInterfaceKey.CONST_GEN_AUTHORIZED_AGENT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String reportlId = SOPInterfaceKey.CONST_SCRATCH_REPROT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID, reportlId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, scratchReport);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;



                case SEARCH_SCRATCH_REPORT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        System.out.println("The channel id isssssss " + channelId);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        System.out.println("The warehouse id isssssssssssssssssss " + warehouseId);
                        String reportId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID, reportId);
                        String creationDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM);
                        //System.out.println("Creation Date From isssssssssssss " + creationDateFrom );
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM, creationDateFrom);
                        String creationDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO);
                        //System.out.println("Creation Date To issssssssssssssssssssssss " + creationDateTo);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO, creationDateTo);
                        String paymentDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_FROM, paymentDateFrom);
                        String paymentDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_TO, paymentDateTo);
                        //HashMap scratchReport = RequestDAO.getFinalScratchReport(con,channelId,creationDateFrom,creationDateTo,paymentDateFrom,paymentDateTo);
                        //HashMap scratchReport = RequestDAO.addNewProductAfterCheck(con);
                        HashMap scratchReport = RequestDAO.scratchDCMProductAfterUpdate(con, channelId, creationDateFrom, creationDateTo, paymentDateFrom, paymentDateTo, reportId, warehouseId);
                        /*
                         * List mapKeys = new ArrayList(scratchReport.keySet());
                         * List mapValues = new
                         * ArrayList(scratchReport.values());
                         *
                         * int size2 = mapKeys.size(); for (int i=0; i<size2;
                         * i++) { System.out.println("DCM before " +
                         * mapKeys.get(i)); }
                         *
                         * Collections.sort(mapKeys); int size = mapKeys.size();
                         *
                         * HashMap scratchReport2 = new HashMap(); for (int i=0;
                         * i<size; i++) {
                         *
                         * // System.out.println(sortedArray[i]);
                         * System.out.println("DCM after " + mapKeys.get(i));
                         *
                         * scratchReport2.put(mapKeys.get(i),mapValues.get(i));
                         *
                         * }
                         * /*Set set = scratchReport.entrySet(); Iterator i =
                         * set.iterator(); boolean found=false;
                         * while(i.hasNext()) { Map.Entry me =
                         * (Map.Entry)i.next(); HashMap
                         * shm=(HashMap)me.getValue(); List mapKeys2 = new
                         * ArrayList(shm.keySet()); Collections.sort(mapKeys2);
                         *
                         * }
                         */
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, scratchReport);
                        Vector warehouseVec = (Vector) RequestDAO.getAllWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, warehouseVec);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case EXPORT_SCRATCH_REPORT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        String reportId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID, reportId);
                        String creationDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM);
                        //System.out.println("Creation Date From isssssssssssss " + creationDateFrom );
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM, creationDateFrom);
                        String creationDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO);
                        //System.out.println("Creation Date To issssssssssssssssssssssss " + creationDateTo);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO, creationDateTo);
                        String paymentDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_FROM, paymentDateFrom);
                        String paymentDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_TO, paymentDateTo);
                        HashMap scratchReport = RequestDAO.scratchDCMProductAfterUpdate(con, channelId, creationDateFrom, creationDateTo, paymentDateFrom, paymentDateTo, reportId, warehouseId);
                        /*
                         * Set set = scratchReport.entrySet(); Iterator i =
                         * set.iterator(); boolean found=false;
                         * while(i.hasNext()) { Map.Entry me =
                         * (Map.Entry)i.next(); //System.out.println("DCM:
                         * "+me.getKey()); ScratchHashMap
                         * shm=(ScratchHashMap)me.getValue(); Set set2 =
                         * shm.scratchReport.entrySet(); Iterator i2 =
                         * set2.iterator(); while(i2.hasNext()) { Map.Entry me2
                         * = (Map.Entry)i2.next(); //System.out.println("Product
                         * Code: "+me2.getKey()+" ProductValue:
                         * "+me2.getValue()); } }
                         */
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, scratchReport);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_PRODUCT_REPORT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        HashMap productReport = new HashMap();
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String reportlId = SOPInterfaceKey.CONST_PRODUCT_REPORT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID, reportlId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, productReport);
                        Vector warehouseVec = (Vector) RequestDAO.getAllWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, warehouseVec);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_PRODUCT_REPORT_FOR_FRANCHISE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        HashMap productReport = new HashMap();
                        String channelId = SOPInterfaceKey.CONST_GEN_CHANNEL_FRANCHISE;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String reportlId = SOPInterfaceKey.CONST_PRODUCT_REPORT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID, reportlId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, productReport);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_PRODUCT_REPORT_FOR_COMPLEMENTARY:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        HashMap productReport = new HashMap();
                        String channelId = SOPInterfaceKey.CONST_GEN_COMPLEMENTARY_CHANNEL;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String reportlId = SOPInterfaceKey.CONST_PRODUCT_REPORT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID, reportlId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, productReport);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_PRODUCT_REPORT_FOR_AUTHORIZED_AGENT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        HashMap productReport = new HashMap();
                        String channelId = SOPInterfaceKey.CONST_GEN_AUTHORIZED_AGENT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String reportlId = SOPInterfaceKey.CONST_PRODUCT_REPORT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID, reportlId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, productReport);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SEARCH_PRODUCT_REPORT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        String reportId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID, reportId);
                        String creationDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM, creationDateFrom);
                        String creationDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO, creationDateTo);
                        String paymentDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_FROM, paymentDateFrom);
                        String paymentDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_TO, paymentDateTo);
                        HashMap productReport = RequestDAO.productDCMProductAfterUpdate(con, channelId, creationDateFrom, creationDateTo, paymentDateFrom, paymentDateTo, reportId, warehouseId);
                        /*
                         * Set set = productReport.entrySet(); Iterator i =
                         * set.iterator(); boolean found=false;
                         * while(i.hasNext()) { Map.Entry me =
                         * (Map.Entry)i.next(); System.out.println("DCM:
                         * "+me.getKey()); ScratchHashMap
                         * shm=(ScratchHashMap)me.getValue(); Set set2 =
                         * shm.scratchReport.entrySet(); Iterator i2 =
                         * set2.iterator(); while(i2.hasNext()) { Map.Entry me2
                         * = (Map.Entry)i2.next(); System.out.println("Product
                         * Code: "+me2.getKey()+" ProductValue:
                         * "+me2.getValue()); } }
                         */
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, productReport);
                        Vector warehouseVec = (Vector) RequestDAO.getAllWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, warehouseVec);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case EXPORT_PRODUCT_REPORT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        String reportId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID, reportId);
                        String creationDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM, creationDateFrom);
                        String creationDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO, creationDateTo);
                        String paymentDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_FROM, paymentDateFrom);
                        String paymentDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_TO, paymentDateTo);
                        HashMap productReport = RequestDAO.productDCMProductAfterUpdate(con, channelId, creationDateFrom, creationDateTo, paymentDateFrom, paymentDateTo, reportId, warehouseId);
                        /*
                         * Set set = productReport.entrySet(); Iterator i =
                         * set.iterator(); boolean found=false;
                         * while(i.hasNext()) { Map.Entry me =
                         * (Map.Entry)i.next(); System.out.println("DCM:
                         * "+me.getKey()); ScratchHashMap
                         * shm=(ScratchHashMap)me.getValue(); Set set2 =
                         * shm.scratchReport.entrySet(); Iterator i2 =
                         * set2.iterator(); while(i2.hasNext()) { Map.Entry me2
                         * = (Map.Entry)i2.next(); System.out.println("Product
                         * Code: "+me2.getKey()+" ProductValue:
                         * "+me2.getValue()); } }
                         */
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, productReport);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_TOTAL_REPORT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        HashMap totalReport = new HashMap();
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String reportId = SOPInterfaceKey.CONST_TOTAL_REPRT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID, reportId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, totalReport);
                        Vector warehouseVec = (Vector) RequestDAO.getAllWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, warehouseVec);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_TOTAL_REPORT_FOR_FRANCHISE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        HashMap totalReport = new HashMap();
                        String channelId = SOPInterfaceKey.CONST_GEN_CHANNEL_FRANCHISE;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String reportId = SOPInterfaceKey.CONST_TOTAL_REPRT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID, reportId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, totalReport);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_TOTAL_REPORT_FOR_COMPLEMENTARY:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        HashMap totalReport = new HashMap();
                        String channelId = SOPInterfaceKey.CONST_GEN_COMPLEMENTARY_CHANNEL;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String reportId = SOPInterfaceKey.CONST_TOTAL_REPRT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID, reportId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, totalReport);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_TOTAL_REPORT_FOR_AUTHORIZED_AGENT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        HashMap totalReport = new HashMap();
                        String channelId = SOPInterfaceKey.CONST_GEN_AUTHORIZED_AGENT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String reportId = SOPInterfaceKey.CONST_TOTAL_REPRT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID, reportId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, totalReport);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SEARCH_TOTAL_REPORT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        String reportId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID, reportId);
                        String creationDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM, creationDateFrom);
                        String creationDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO, creationDateTo);
                        String paymentDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_FROM, paymentDateFrom);
                        String paymentDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_TO, paymentDateTo);
                        HashMap totalReport = RequestDAO.totalDCMProductAfterUpdate(con, channelId, creationDateFrom, creationDateTo, paymentDateFrom, paymentDateTo, reportId, warehouseId);
                        /*
                         * Set set = totalReport.entrySet(); Iterator i =
                         * set.iterator(); boolean found=false;
                         * while(i.hasNext()) { Map.Entry me =
                         * (Map.Entry)i.next(); System.out.println("DCM:
                         * "+me.getKey()); ScratchHashMap
                         * shm=(ScratchHashMap)me.getValue(); Set set2 =
                         * shm.scratchReport.entrySet(); Iterator i2 =
                         * set2.iterator(); while(i2.hasNext()) { Map.Entry me2
                         * = (Map.Entry)i2.next(); System.out.println("Product
                         * Code: "+me2.getKey()+" ProductValue:
                         * "+me2.getValue()); } }
                         */
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, totalReport);
                        Vector warehouseVec = (Vector) RequestDAO.getAllWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, warehouseVec);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case EXPORT_TOTAL_REPORT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        String reportId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REPORT_ID, reportId);
                        String creationDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM, creationDateFrom);
                        String creationDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO, creationDateTo);
                        String paymentDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_FROM, paymentDateFrom);
                        String paymentDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE_TO, paymentDateTo);
                        HashMap totalReport = RequestDAO.totalDCMProductAfterUpdate(con, channelId, creationDateFrom, creationDateTo, paymentDateFrom, paymentDateTo, reportId, warehouseId);
                        /*
                         * Set set = totalReport.entrySet(); Iterator i =
                         * set.iterator(); boolean found=false;
                         * while(i.hasNext()) { Map.Entry me =
                         * (Map.Entry)i.next(); System.out.println("DCM:
                         * "+me.getKey()); ScratchHashMap
                         * shm=(ScratchHashMap)me.getValue(); Set set2 =
                         * shm.scratchReport.entrySet(); Iterator i2 =
                         * set2.iterator(); while(i2.hasNext()) { Map.Entry me2
                         * = (Map.Entry)i2.next(); System.out.println("Product
                         * Code: "+me2.getKey()+" ProductValue:
                         * "+me2.getValue()); } }
                         */
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, totalReport);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_INVOICE_DETAIL_DATA:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String invoiceNumber = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER, invoiceNumber);
                        Vector vecInvoice = RequestDAO.selectInvoiceDetail(con, invoiceNumber);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecInvoice);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case CREATE_NEW_INVOICE_DETAIL:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String invoiceNumber = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER, invoiceNumber);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case INVOICE_BACK:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector vecInvoice = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecInvoice);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_ALL_PRODUCTS_PGW:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        Utility.logger.debug("Channel id isssssssssssssssss " + channelId);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        Vector vecSchemaProdcut = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecSchemaProdcut);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_ALL_PRODUCTS_PGW_FOR_FRANCHISE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = SOPInterfaceKey.CONST_GEN_CHANNEL_FRANCHISE;
                        Utility.logger.debug("Channel id isssssssssssssssss " + channelId);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        Vector vecSchemaProdcut = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecSchemaProdcut);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;



                case SHOW_ALL_PRODUCTS_PGW_FOR_COMPLEMANTRY:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = SOPInterfaceKey.CONST_GEN_COMPLEMENTARY_CHANNEL;
                        Utility.logger.debug("Channel id isssssssssssssssss " + channelId);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        Vector vecSchemaProdcut = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecSchemaProdcut);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_ALL_PRODUCTS_PGW_FOR_AUTHORIZED_AGENT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = SOPInterfaceKey.CONST_GEN_AUTHORIZED_AGENT;
                        Utility.logger.debug("Channel id isssssssssssssssss " + channelId);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        Vector vecSchemaProdcut = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecSchemaProdcut);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_ALL_INVOICES:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector vecInvoice = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecInvoice);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case CREATE_NEW_INVOICE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SAVE_NEW_INVOICE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Long strInvoiceNumber = Utility.getSequenceNextVal(con, "SEQ_INVOICE_NUMBER");
                        String strDcmId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_DCM_ID);
                        String strTotalAmount = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_TOTAL_AMOUNT);
                        String strPaymentSerialNumber = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PAYMENT_SERIAL_NUMBER);
                        String strPaymentDate = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PAYMENT_DATE);
                        RequestDAO.insertInvoice(con, strDcmId, strTotalAmount, strInvoiceNumber, strPaymentSerialNumber, strPaymentDate);
                        Vector vecInvoice = RequestDAO.getAllInvoices(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecInvoice);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case EDIT_INVOICE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strInvoiceNumber = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER);
                        InvoiceModel invoiceModel = (InvoiceModel) RequestDAO.selectInvoice(con, strInvoiceNumber);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, invoiceModel);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case UPDATE_INVOICE_DETAIL_DATA:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strInvoiceDetailId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_INVOICE_DETAIL_ID);
                        String strSchemaProductId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_SCHEMA_PRODUCT_ID);
                        String strProductQuantity = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PRODUCT_QUANTITY);
                        String strProductPrice = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PRODUCT_PRICE);
                        RequestDAO.updateInvoiceDetailData(con, strSchemaProductId, strProductQuantity, strProductPrice, strInvoiceDetailId);
                        String invoiceNumber = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER, invoiceNumber);
                        Vector vecInvoice = RequestDAO.selectInvoiceDetail(con, invoiceNumber);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecInvoice);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case UPDATE_INVOICE_DATA:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strDcmId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_DCM_ID);
                        String strTotalAmount = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_TOTAL_AMOUNT);
                        String strInvoiceNumber = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER);
                        String strPaymentSerialNumber = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PAYMENT_SERIAL_NUMBER);
                        String strPaymentDate = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PAYMENT_DATE);
                        RequestDAO.updateInvoiceData(con, strDcmId, strTotalAmount, strInvoiceNumber, strPaymentSerialNumber, strPaymentDate);
                        Vector vecInvoice = RequestDAO.getAllInvoices(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecInvoice);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case ADD_EDIT_INVOICE_DETAIL:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String invoiceNumber = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER, invoiceNumber);
                        String invoiceDetailid = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_INVOICE_DETAIL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_INVOICE_DETAIL_ID, invoiceDetailid);
                        InvoiceModel invoiceModel = (InvoiceModel) RequestDAO.getInviceNumber(con, invoiceDetailid);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, invoiceModel);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SAVE_INVOICE_DETAIL:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Long strInvoiceDetailId = Utility.getSequenceNextVal(con, "SEQ_INVOICE_DETAIL_ID");
                        String strInvoiceNumber = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER);
                        String strSchemaProductId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_SCHEMA_PRODUCT_ID);
                        boolean invoiceDetailExists = RequestDAO.getInvoiceDetail(con, strInvoiceNumber, strSchemaProductId);
                        String strProductQuantity = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PRODUCT_QUANTITY);
                        String strProductPrice = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PRODUCT_PRICE);
                        if (invoiceDetailExists) {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Invoice number and schema product id must be unique.");
                        } else {
                            RequestDAO.insertInvoiceDetail(con, strInvoiceNumber, strSchemaProductId, strProductQuantity, strProductPrice, strInvoiceDetailId);
                        }
                        String invoiceNumber = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER, invoiceNumber);
                        Vector vecInvoice = RequestDAO.selectInvoiceDetail(con, invoiceNumber);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecInvoice);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case UPDATE_PRODUCT_PGW:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        Utility.logger.debug("Channel id isssssssssssssssss " + channelId);
                        String strProductID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_ID);
                        String strLcsProductCode = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_LCS_PRODCUT_CODE);
                        String strIsActive = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_IS_ACTIVE);
                        String strHasQuantity = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_HAS_QUANTITY);
                        String strProductNameEnglish = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PRODUCT_NAME_IN_ENGLISH);
                        String strProductNameArabic = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PRODUCT_NAME_IN_ARABIC);
                        String strProductPrice = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PRODUCT_PRICE);
                        String strSalesTax = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_SALES_TAX);
                        SchemaDAO.updateProductPgw(con, strProductID, strLcsProductCode, strIsActive, strHasQuantity, strProductNameEnglish, strProductNameArabic, strProductPrice, strSalesTax);
                        Vector vecSchemaProdcut = SchemaDAO.getAllProducts(con, channelId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecSchemaProdcut);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case EDIT_PRODUCT_PGW:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        Utility.logger.debug("Channel id isssssssssssssssss " + channelId);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String strProductID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_ID);
                        ProductModel productModel = SchemaDAO.selectProductPgw(con, strProductID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, productModel);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case DELETE_PRODUCT_FROM_PGW:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        Utility.logger.debug("Channel id isssssssssssssssss " + channelId);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String strProductID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_ID);
                        SchemaDAO.deleteProductFromPgw(con, strProductID);
                        Vector vecSchemaProdcut = SchemaDAO.getAllProducts(con, channelId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecSchemaProdcut);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SAVE_PRODUCT_PGW:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        Utility.logger.debug("Channel id isssssssssssssssss " + channelId);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        Long strProductId = Utility.getSequenceNextVal(con, "SEQ_PGW_PRODUCT_ID");
                        String strLcsProductCode = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_LCS_PRODCUT_CODE);
                        String strIsActive = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_IS_ACTIVE);
                        String strHasQuantity = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_HAS_QUANTITY);
                        String strProductNameEnglish = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PRODUCT_NAME_IN_ENGLISH);
                        String strProductNameArabic = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PRODUCT_NAME_IN_ARABIC);
                        String strProductPrice = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PRODUCT_PRICE);
                        String strSalesTax = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_SALES_TAX);
                        SchemaDAO.insertProductPgw(con, strProductId, strLcsProductCode, strIsActive, strHasQuantity, strProductNameEnglish, strProductNameArabic, strProductPrice, strSalesTax, channelId);
                        Vector vecSchemaProdcut = SchemaDAO.getAllProducts(con, channelId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecSchemaProdcut);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case CREATE_NEW_PRODUCT_PGW:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        Utility.logger.debug("Channel id isssssssssssssssss " + channelId);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_PRODUCTS_FROM_LCS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector vecSchemaProdcut = SchemaDAO.getAllProductsLcs(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecSchemaProdcut);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case CREATE_PRODUCT_LCS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SAVE_PRODUCT_LCS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Long strProductId = Utility.getSequenceNextVal(con, "SEQ_LCS_PRODUCT_ID");
                        String strProductCode = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PRODUCT_CODE);
                        String strPhysicalAmount = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PHYSICAL_AMOUNT);
                        SchemaDAO.insertProductLcs(con, strProductId, strProductCode, strPhysicalAmount);
                        Vector vecSchemaProdcut = SchemaDAO.getAllProductsLcs(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecSchemaProdcut);

                    } catch (Exception objExp) {
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Product Code must be unique.");
                        objExp.printStackTrace();
                    }
                    break;

                case EDIT_PRODUCT_LCS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strProductID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_ID);
                        ProductModel productModel = SchemaDAO.selectProductLcs(con, strProductID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, productModel);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case DELETE_PRODUCT_LCS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strProductID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_ID);
                        SchemaDAO.deleteProductLcs(con, strProductID);
                        Vector vecSchemaProdcut = SchemaDAO.getAllProductsLcs(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecSchemaProdcut);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case UPDATE_PRODUCT_LCS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strProductId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_ID);
                        String strProductCode = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PRODUCT_CODE);
                        String strPhysicalAmount = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PHYSICAL_AMOUNT);
                        SchemaDAO.updateProductLcs(con, strProductId, strProductCode, strPhysicalAmount);
                        Vector vecSchemaProdcut = SchemaDAO.getAllProductsLcs(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecSchemaProdcut);
                    } catch (Exception objExp) {
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Product Code must be unique.");
                        Vector vecSchemaProdcut = SchemaDAO.getAllProductsLcs(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecSchemaProdcut);
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_ALL_SCHEMAS:
                    try {
                        dataHashMap = new HashMap();
                        //String orderBy = "SCHEMA_STATUS_ID,CREATION_DATE,SCHEMA_CODE";
                        //Vector schemaList = SchemaDAO.getAllSchemas(con,orderBy);
                        Vector schemaList = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, schemaList);
                        Vector schemaStatusList = SchemaDAO.getAllSchemaStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, schemaStatusList);
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);

                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String channleName = SchemaDAO.getChannelName(channelId, con);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, channleName);
                        boolean activeSchemaExists = SchemaDAO.checkActiveSchema(con, channelId);
                        dataHashMap.put(SOPInterfaceKey.ACTIVE_SCHEMA_EXISTS, activeSchemaExists + "");

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_ALL_SCHEMAS_FOR_FRANCHISE:
                    try {
                        dataHashMap = new HashMap();
                        //String orderBy = "SCHEMA_STATUS_ID,CREATION_DATE,SCHEMA_CODE";
                        //Vector schemaList = SchemaDAO.getAllSchemas(con,orderBy);
                        Vector schemaList = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, schemaList);
                        Vector schemaStatusList = SchemaDAO.getAllSchemaStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, schemaStatusList);
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = SOPInterfaceKey.CONST_GEN_CHANNEL_FRANCHISE;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        boolean activeSchemaExists = SchemaDAO.checkActiveSchema(con, channelId);
                        dataHashMap.put(SOPInterfaceKey.ACTIVE_SCHEMA_EXISTS, activeSchemaExists + "");

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_ALL_SCHEMAS_FOR_COMPLEMENTARY_CHANNEL:
                    try {
                        dataHashMap = new HashMap();
                        //String orderBy = "SCHEMA_STATUS_ID,CREATION_DATE,SCHEMA_CODE";
                        //Vector schemaList = SchemaDAO.getAllSchemas(con,orderBy);
                        Vector schemaList = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, schemaList);
                        Vector schemaStatusList = SchemaDAO.getAllSchemaStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, schemaStatusList);
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = SOPInterfaceKey.CONST_GEN_COMPLEMENTARY_CHANNEL;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        boolean activeSchemaExists = SchemaDAO.checkActiveSchema(con, channelId);
                        dataHashMap.put(SOPInterfaceKey.ACTIVE_SCHEMA_EXISTS, activeSchemaExists + "");

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_ALL_SCHEMAS_FOR_AUTHORIZED_AGENT:
                    try {
                        dataHashMap = new HashMap();
                        //String orderBy = "SCHEMA_STATUS_ID,CREATION_DATE,SCHEMA_CODE";
                        //Vector schemaList = SchemaDAO.getAllSchemas(con,orderBy);
                        Vector schemaList = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, schemaList);
                        Vector schemaStatusList = SchemaDAO.getAllSchemaStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, schemaStatusList);
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = SOPInterfaceKey.CONST_GEN_AUTHORIZED_AGENT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        boolean activeSchemaExists = SchemaDAO.checkActiveSchema(con, channelId);
                        dataHashMap.put(SOPInterfaceKey.ACTIVE_SCHEMA_EXISTS, activeSchemaExists + "");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case CREATE_NEW_SCHEMA:
                    try {
                        dataHashMap = new HashMap();
                        String orderBy = "SCHEMA_CODE";
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        Vector vecSchemaList = SchemaDAO.getAllSchemas(con, orderBy, channelId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecSchemaList);

                        String maxSchemaCode = SchemaDAO.getMaxSchemaCode(con);
                        String maxSchemaCodeNumber = maxSchemaCode.substring(2, 5);
                        int intMaxSchemaCodeNumber = Integer.parseInt(maxSchemaCodeNumber);
                        intMaxSchemaCodeNumber += 1;
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_SCHEMA_CODE, "sc" + intMaxSchemaCodeNumber);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case DELETE_SCHEMA_PRODUCT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String schemaId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_SCHEMA_ID);
                        Utility.logger.debug("Schema id isssssssssssss " + schemaId);
                        SchemaDAO.deleteSchemaProduct(con, schemaId);

                        String schemaCode = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA_CODE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA_CODE, schemaCode);
                        String schemaName = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA_NAME);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA_NAME, schemaName);
                        String createDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM, createDateFrom);
                        String createDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO, createDateTo);
                        String startDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_START_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_START_DATE_FROM, startDateFrom);
                        String startDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_START_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_START_DATE_TO, startDateTo);
                        String endDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_END_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_END_DATE_FROM, endDateFrom);
                        String endDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_END_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_END_DATE_TO, endDateTo);
                        String schemaStatus = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_SELECT_SCHEMA_STATUS);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_SELECT_SCHEMA_STATUS, schemaStatus);

                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SEARCH_SCHEMA);
                        Vector schemaList = SchemaDAO.getSchemasByFilter(con, schemaCode, schemaName, createDateFrom, createDateTo, startDateFrom, startDateTo, endDateFrom, endDateTo, schemaStatus, channelId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, schemaList);
                        //Vector schemaList = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, schemaList);
                        Vector schemaStatusList = SchemaDAO.getAllSchemaStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, schemaStatusList);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;


                case VIEW_SCHEMA_PRODUCTS:
                    try {
                        String schemaId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_SCHEMA_ID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap = new HashMap();
                        SchemaModel schemaDetails = SchemaDAO.getSchemabySchemaId(con, schemaId, channelId);
                        if (schemaDetails != null) {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, schemaDetails);
                        }
                        Vector schemaProductList = SchemaDAO.getAllSchemaProducts(con, schemaId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, schemaProductList);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SAVE_SCHEMA:
                    try {
                        dataHashMap = new HashMap();
                        String newSchemaCode = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_SCHEMA_CODE);
                        String newSchemaName = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_SCHEMA_NAME);
                        String newSchemaDescription = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_SCHEMA_DESCRIPTION);
                        String oldSchemaId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SELECT_SCHEMA_ID);
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);

                        Vector productList = SchemaDAO.getAllSchemaProducts(con, oldSchemaId);
                        boolean checkSchemaCodeExit = SchemaDAO.checkSchemaCodeExist(con, newSchemaCode, channelId);
                        if (checkSchemaCodeExit) {
                            int newSchemaId = SchemaDAO.createSchema(con, newSchemaCode, strUserID, newSchemaName, newSchemaDescription, channelId);
                            if (newSchemaId > 0) {
                                for (int i = 0; i < productList.size(); i++) {
                                    ProductModel productModel = (ProductModel) productList.get(i);
                                    SchemaDAO.insertSchemaProductFromAnotherSchema(con, newSchemaId, productModel);
                                }
                            }
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Schema created successfully.");
                        } else {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Schema code exist in the system.");
                        }
                        String orderBy = "SCHEMA_STATUS_ID,CREATION_DATE,SCHEMA_CODE";
                        Vector schemaList = SchemaDAO.getAllSchemas(con, orderBy, channelId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, schemaList);
                        Vector schemaStatusList = SchemaDAO.getAllSchemaStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, schemaStatusList);
                        boolean activeSchemaExists = SchemaDAO.checkActiveSchema(con, channelId);
                        dataHashMap.put(SOPInterfaceKey.ACTIVE_SCHEMA_EXISTS, activeSchemaExists + "");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case UPDATE_SCHEMA_PRODUCTS:
                    try {
                        for (int j = 0; j < paramHashMap.size(); j++) {
                            try {
                                String tempStatusString = (String) paramHashMap.keySet().toArray()[j];
                                System.out.println("1977 tempStatusString isss " + tempStatusString);
                                if (!(paramHashMap.get(tempStatusString) instanceof String)) {
                                    continue;
                                }
                                String keyValue = (String) paramHashMap.get(tempStatusString);

                                //Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
                                if (tempStatusString.startsWith(SOPInterfaceKey.INPUT_TEXT_PRODUCT_WEIGHT)) {

                                    int strlength = SOPInterfaceKey.INPUT_TEXT_PRODUCT_WEIGHT.length() + 1;
                                    String strSchemaProductId = tempStatusString.substring(strlength, tempStatusString.length());
                                    String isPointKey = SOPInterfaceKey.INPUT_SELECT_PRODUCT_IS_POINT + "_" + strSchemaProductId;
                                    String isPointValue = (String) paramHashMap.get(isPointKey);
                                    String isQuotaKey = SOPInterfaceKey.INPUT_SELECT_PRODUCT_IS_QUOTA + "_" + strSchemaProductId;
                                    String isQuotaValue = (String) paramHashMap.get(isQuotaKey);
                                    String productDiscountKey = SOPInterfaceKey.INPUT_TEXT_PRODUCT_DISCOUNT + "_" + strSchemaProductId;
                                    String productDiscountValue = (String) paramHashMap.get(productDiscountKey);
                                    String productPriceKey = SOPInterfaceKey.INPUT_HIDDEM_PRODUCT_PRICE + "_" + strSchemaProductId;
                                    String productPriceValue = (String) paramHashMap.get(productPriceKey);

                                    Float discountedAmount = (Float.valueOf(productDiscountValue) * Float.valueOf(productPriceValue)) / 100;
                                    Float netAmount = Float.valueOf(productPriceValue) - discountedAmount;

                                    SchemaDAO.updateSchemaProduct(con, strSchemaProductId, keyValue, isPointValue, isQuotaValue, productDiscountValue, discountedAmount.toString(), netAmount.toString());
                                }
                            } catch (Exception e) {
                                System.out.println("Exception in line 1977");
                                e.printStackTrace();
                            }
                        }
                        dataHashMap = new HashMap();

                        String schemaId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_SCHEMA_ID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        SchemaModel schemaDetails = SchemaDAO.getSchemabySchemaId(con, schemaId, channelId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, schemaDetails);

                        Vector schemaProductList = SchemaDAO.getAllSchemaProducts(con, schemaId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, schemaProductList);
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case IMPORT_PRODUCTS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);

                        String maxSchemaCode = SchemaDAO.getMaxSchemaCode(con);
                        String maxSchemaCodeNumber = maxSchemaCode.substring(2, 5);
                        int intMaxSchemaCodeNumber = Integer.parseInt(maxSchemaCodeNumber);
                        intMaxSchemaCodeNumber += 1;
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_SCHEMA_CODE, "sc" + intMaxSchemaCodeNumber);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case IMPORT_PRODUCTS_FOR_FRANCHISE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_CHANNEL_FRANCHISE;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);

                        String maxSchemaCode = SchemaDAO.getMaxSchemaCode(con);
                        String maxSchemaCodeNumber = maxSchemaCode.substring(2, 5);
                        int intMaxSchemaCodeNumber = Integer.parseInt(maxSchemaCodeNumber);
                        intMaxSchemaCodeNumber += 1;
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_SCHEMA_CODE, "sc" + intMaxSchemaCodeNumber);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case IMPORT_PRODUCTS_FOR_COMPLEMENTARY:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_COMPLEMENTARY_CHANNEL;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);

                        String maxSchemaCode = SchemaDAO.getMaxSchemaCode(con);
                        String maxSchemaCodeNumber = maxSchemaCode.substring(2, 5);
                        int intMaxSchemaCodeNumber = Integer.parseInt(maxSchemaCodeNumber);
                        intMaxSchemaCodeNumber += 1;
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_SCHEMA_CODE, "sc" + intMaxSchemaCodeNumber);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case IMPORT_PRODUCTS_FOR_AUTHORIZED_AGENT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_AUTHORIZED_AGENT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);

                        String maxSchemaCode = SchemaDAO.getMaxSchemaCode(con);
                        String maxSchemaCodeNumber = maxSchemaCode.substring(2, 5);
                        int intMaxSchemaCodeNumber = Integer.parseInt(maxSchemaCodeNumber);
                        intMaxSchemaCodeNumber += 1;
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_SCHEMA_CODE, "sc" + intMaxSchemaCodeNumber);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case IMPORT_PRODUCTS_AND_CREATE_SCHEMA:
                    try {
                        dataHashMap = new HashMap();
                        String schemaCode = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_SCHEMA_CODE);
                        String schemaName = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_SCHEMA_NAME);
                        String schemaDescription = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_SCHEMA_DESCRIPTION);
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        boolean checkSchemaCodeNotExit = SchemaDAO.checkSchemaCodeExist(con, schemaCode, channelId);
                        if (checkSchemaCodeNotExit) {
                            int createSchemaId = SchemaDAO.createSchema(con, schemaCode, strUserID, schemaName, schemaDescription, channelId);
                            if (createSchemaId > 0) {
                                SchemaDAO.insertSchemaProduct(con, createSchemaId, channelId);
                            }

                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Schema created successfully.");
                        } else {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Schema code exist in the system.");
                        }
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case IMPORT_PRODUCTS_FROM_PGW:
                    try {
                        dataHashMap = new HashMap();
                        String schemaCode = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_SCHEMA_CODE);
                        String schemaName = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_SCHEMA_NAME);
                        String schemaDescription = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_SCHEMA_DESCRIPTION);
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);

                        boolean importProduct = SchemaDAO.importProduct(con, channelId);
                        if (importProduct == true) {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Products Imported Successfully.");
                        } else {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Failed To Import Products.");
                        }
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case CHANGE_SCHEMA_STATUS:
                    try {
                        dataHashMap = new HashMap();
                        String schemaId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_SCHEMA_ID);
                        String schemaStatusId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_SCHEMA_NEXT_STATUS_ID);
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        //System.out.println("The channel id isssss " + channelId);

                        if (schemaStatusId.compareTo(SOPInterfaceKey.CONST_SCHEMA_STATUS_INACTIVE) == 0) {
                            Vector activeRequest = RequestDAO.getActiveOrInactiveRequestBySchema(con, schemaId, channelId);
                            if (activeRequest.size() == 0) {
                                SchemaDAO.updateSchemaStatus(con, schemaId, schemaStatusId, strUserID);
                            } else {
                                dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "There is already active or rejected request for this schema.");
                            }
                        } else {
                            SchemaDAO.updateSchemaStatus(con, schemaId, schemaStatusId, strUserID);
                        }
                        String orderBy = "SCHEMA_STATUS_ID,CREATION_DATE,SCHEMA_CODE";
                        Vector schemaList = SchemaDAO.getAllSchemas(con, orderBy, channelId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, schemaList);
                        Vector schemaStatusList = SchemaDAO.getAllSchemaStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, schemaStatusList);
                        boolean activeSchemaExists = SchemaDAO.checkActiveSchema(con, channelId);
                        dataHashMap.put(SOPInterfaceKey.ACTIVE_SCHEMA_EXISTS, activeSchemaExists + "");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case SEARCH_SCHEMA:
                    try {
                        dataHashMap = new HashMap();
                        String schemaCode = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA_CODE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA_CODE, schemaCode);
                        String schemaName = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA_NAME);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA_NAME, schemaName);
                        String createDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM, createDateFrom);
                        String createDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO, createDateTo);
                        String startDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_START_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_START_DATE_FROM, startDateFrom);
                        String startDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_START_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_START_DATE_TO, startDateTo);
                        String endDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_END_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_END_DATE_FROM, endDateFrom);
                        String endDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_END_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_END_DATE_TO, endDateTo);
                        String schemaStatus = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_SELECT_SCHEMA_STATUS);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_SELECT_SCHEMA_STATUS, schemaStatus);

                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SEARCH_SCHEMA);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        Vector schemaList = SchemaDAO.getSchemasByFilter(con, schemaCode, schemaName, createDateFrom, createDateTo, startDateFrom, startDateTo, endDateFrom, endDateTo, schemaStatus, channelId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, schemaList);
                        boolean activeSchemaExists = SchemaDAO.checkActiveSchema(con, channelId);
                        dataHashMap.put(SOPInterfaceKey.ACTIVE_SCHEMA_EXISTS, activeSchemaExists + "");
                        Vector schemaStatusList = SchemaDAO.getAllSchemaStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, schemaStatusList);
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case CREATE_NEW_REQUEST:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        //String channelID = SOPInterfaceKey.CONST_GEN_CHANNEL_DISTRIBUTION;
                        //dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID,channelID);
                        String strLevel = "";
                        //if(channelID.compareTo("1")==0)
                        strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        //else if (channelID.compareTo("2")==0)
                        //{strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;}
                        //DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID,channelID);
                        //dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,dcmDto);
                        Vector channelVec = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, channelVec);

                        Vector dcmChannelVec = RequestDAO.getDcmChannel(con, strLevel, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmChannelVec);
                        Vector warehouseChannelVec = RequestDAO.getChannelWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, warehouseChannelVec);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_REQUEST_SCHEMA_PRODUCTS);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "Create Request");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case CREATE_NEW_REQUEST_FOR_FRANCHISE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_CHANNEL_FRANCHISE;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_REQUEST_SCHEMA_PRODUCTS);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "Create Request for Franchise");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case CREATE_NEW_REQUEST_FOR_COMPLEMANTRY:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_COMPLEMENTARY_CHANNEL;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("3") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_REQUEST_SCHEMA_PRODUCTS);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "Create Request for Complementary");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case CREATE_NEW_REQUEST_FOR_AUTHORZED_AGENT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_AUTHORIZED_AGENT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("3") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("4") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_REQUEST_SCHEMA_PRODUCTS);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "Create Request for Complementary");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case ADMIN_CREATE_NEW_REQUEST:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        //String channelID = SOPInterfaceKey.CONST_GEN_CHANNEL_DISTRIBUTION;
                        //dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID,channelID);
                        String strLevel = "";
                        //if(channelID.compareTo("1")==0)
                        strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        //else if (channelID.compareTo("2")==0)
                        //{strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;}
                        // DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID,channelID);
                        //dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_ADMIN_REQUEST_SCHEMA_PRODUCTS);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "Admin Create Request");
                        Vector channelVec = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, channelVec);

                        Vector dcmChannelVec = RequestDAO.getDcmChannel(con, strLevel, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmChannelVec);
                        Vector warehouseChannelVec = RequestDAO.getChannelWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, warehouseChannelVec);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case ADMIN_CREATE_NEW_REQUEST_FOR_FRANCHISE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_CHANNEL_FRANCHISE;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_ADMIN_REQUEST_SCHEMA_PRODUCTS);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "Admin Create Request for Franchise");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case ADMIN_CREATE_NEW_REQUEST_FOR_COMPLEMANTRY:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_COMPLEMENTARY_CHANNEL;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("3") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_ADMIN_REQUEST_SCHEMA_PRODUCTS);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "Admin Create Request for Complemantry");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case ADMIN_CREATE_NEW_REQUEST_FOR_AUTHORIZED_AGENT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_AUTHORIZED_AGENT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("3") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("4") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_ADMIN_REQUEST_SCHEMA_PRODUCTS);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "Admin Create Request for Complemantry");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case REQUEST_SCHEMA_PRODUCTS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        Utility.logger.debug("The Channel id issssssssssssssssssssssss    " + channelId);
                        String strDcmName = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME, strDcmName);
                        String strDcmID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID, strDcmID);
                        Vector vecActiveSchemaProducts = SchemaDAO.getActiveSchemaProducts(con, strDcmID, channelId, warehouseId);
                        if (vecActiveSchemaProducts.size() == 0) {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "This DCM does not have active schema product limits.");
                        }
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecActiveSchemaProducts);
                        String dcmQuota = QuotaDAO.getDcmCurrentQuota(con, strDcmID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA, dcmQuota);
                        Vector activeDCMRequest = RequestDAO.getActiveOrInactiveRequestByDCM(con, strDcmID, warehouseId);
                        if (activeDCMRequest.size() != 0) {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "There is already accepted or rejected request for this DCM.");
                        }
                        int intDcmID = Integer.parseInt(strDcmID);
                        DCMModel dcmModel = DCMDao.getDCMModel(con, intDcmID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, dcmModel);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case ADMIN_REQUEST_SCHEMA_PRODUCTS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        String strDcmName = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME, strDcmName);
                        String strDcmID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID, strDcmID);
                        Vector vecActiveSchemaProducts = SchemaDAO.getActiveSchemaProducts(con, strDcmID, channelId, warehouseId);
                        if (vecActiveSchemaProducts.size() == 0) {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "This DCM does not have active schema product limits.");
                        }
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecActiveSchemaProducts);
                        String dcmQuota = QuotaDAO.getDcmCurrentQuota(con, strDcmID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA, dcmQuota);
                        Vector activeDCMRequest = RequestDAO.getActiveOrInactiveRequestByDCM(con, strDcmID, warehouseId);
                        if (activeDCMRequest.size() != 0) {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "There is already accepted or rejected request for this DCM.");
                        }
                        int intDcmID = Integer.parseInt(strDcmID);
                        DCMModel dcmModel = DCMDao.getDCMModel(con, intDcmID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, dcmModel);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case SAVE_REQUEST:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        Utility.logger.debug(" The Channel Id issssssssssssssssss  " + channelID);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        //if(channelID.compareTo("1")==0)
                        strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        // else if (channelID.compareTo("2")==0)
                        //{strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;}
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                        Vector channelVec = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, channelVec);

                        Vector dcmChannelVec = RequestDAO.getDcmChannel(con, strLevel, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmChannelVec);
                        Vector warehouseChannelVec = RequestDAO.getChannelWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, warehouseChannelVec);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_REQUEST_SCHEMA_PRODUCTS);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "Create Request");

                        String schemaId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_SCHEMA_ID);
                        String dcmId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID);

                        Vector activeDCMRequest = RequestDAO.getActiveOrInactiveRequestByDCM(con, dcmId, warehouseId);
                        if (activeDCMRequest.size() == 0) {
                            int insertedRequestId = RequestDAO.insertRequest(con, dcmId, schemaId, strUserID, channelID, warehouseId);
                            if (insertedRequestId > 0) {
                                for (int j = 0; j < paramHashMap.size(); j++) {
                                    try {
                                        String tempStatusString = (String) paramHashMap.keySet().toArray()[j];

                                        System.out.println("2565 tempStatusString isss " + tempStatusString);
                                        if (!(paramHashMap.get(tempStatusString) instanceof String)) {
                                            continue;
                                        }
                                        String productAmount = (String) paramHashMap.get(tempStatusString);
                                        if (productAmount.equals("") || productAmount == null) {
                                            productAmount = "0";
                                        }
                                        System.out.println("2565 productAmount isss " + productAmount);
                                        //Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
                                        if (tempStatusString.startsWith(SOPInterfaceKey.INPUT_TEXT_REQUEST_PRODUCT_AMOUNT)) {
                                            int strlength = SOPInterfaceKey.INPUT_TEXT_REQUEST_PRODUCT_AMOUNT.length() + 1;
                                            String strSchemaProductId = tempStatusString.substring(strlength, tempStatusString.length());
                                            String minimumLimit = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_MINIMUM_LIMIT + "_" + strSchemaProductId);
                                            String maximumLimit = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_MAXIMUM_LIMIT + "_" + strSchemaProductId);
                                            String productDiscount = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_DISCOUNT + "_" + strSchemaProductId);
                                            String productDiscountedAmount = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_DISCOUNT_AMOUNT + "_" + strSchemaProductId);
                                            String productNetPrice = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_NET_AMOUNT + "_" + strSchemaProductId);

                                            System.out.println("Shadyyyy" + productDiscount + "-" + productDiscountedAmount + "-" + productNetPrice);

                                            RequestDAO.insertRequestDetails(con, insertedRequestId, strSchemaProductId, minimumLimit, maximumLimit, productAmount, productDiscount, productDiscountedAmount, productNetPrice);
                                            RequestDAO.updateProductRequestLimit(con, strSchemaProductId, productAmount, "subtracting", dcmId, warehouseId);
                                            RequestDAO.updateProductStock(con, strSchemaProductId, productAmount, "subtracting", warehouseId);
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Exception in line 2565");
                                        e.printStackTrace();
                                    }

                                }
                                String dcmRequestProductsQuota = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA);
                                QuotaDAO.updateDcmQuotaAfterRequest(con, dcmId, dcmRequestProductsQuota, "-");
                                RequestModel newRequestModel = RequestDAO.getRequestById(con, insertedRequestId + "");
                                String serverName = (String) paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_NAME);
                                String serverPort = (String) paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_PORT);
                                String appName = (String) paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_CONTEXT_PATH);
                                RequestDAO.sendRequestNotificationToList(con, serverName, serverPort, appName, newRequestModel, channelID);
                            }
                        } else {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "There is already active or rejected request for this DCM.");
                        }
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case ADMIN_SAVE_REQUEST:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);

                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        System.out.println("warehouse id issssssssss " + warehouseId);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        String strLevel = "";
                        //if(channelID.compareTo("1")==0)
                        strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        //else if (channelID.compareTo("2")==0)
                        //{strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;}
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                        Vector channelVec = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, channelVec);

                        Vector dcmChannelVec = RequestDAO.getDcmChannel(con, strLevel, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmChannelVec);
                        Vector warehouseChannelVec = RequestDAO.getChannelWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, warehouseChannelVec);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_ADMIN_REQUEST_SCHEMA_PRODUCTS);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "Admin Create Request");

                        String schemaId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_SCHEMA_ID);
                        String dcmId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID);

                        Vector activeDCMRequest = RequestDAO.getActiveOrInactiveRequestByDCM(con, dcmId, warehouseId);
                        if (activeDCMRequest.size() == 0) {
                            int insertedRequestId = RequestDAO.insertRequest(con, dcmId, schemaId, strUserID, channelID, warehouseId);
                            if (insertedRequestId > 0) {
                                for (int j = 0; j < paramHashMap.size(); j++) {
                                    try {
                                        String tempStatusString = (String) paramHashMap.keySet().toArray()[j];
                                        System.out.println("2664 tempStatusString isss " + tempStatusString);
                                        if (!(paramHashMap.get(tempStatusString) instanceof String)) {
                                            continue;
                                        }
                                        String productAmount = (String) paramHashMap.get(tempStatusString);
                                        if (productAmount.equals("") || productAmount == null) {
                                            productAmount = "0";
                                        }
                                        //Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
                                        if (tempStatusString.startsWith(SOPInterfaceKey.INPUT_TEXT_REQUEST_PRODUCT_AMOUNT)) {
                                            int strlength = SOPInterfaceKey.INPUT_TEXT_REQUEST_PRODUCT_AMOUNT.length() + 1;
                                            String strSchemaProductId = tempStatusString.substring(strlength, tempStatusString.length());
                                            String minimumLimit = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_MINIMUM_LIMIT + "_" + strSchemaProductId);
                                            String maximumLimit = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_MAXIMUM_LIMIT + "_" + strSchemaProductId);
                                            RequestDAO.insertRequestDetails(con, insertedRequestId, strSchemaProductId, minimumLimit, maximumLimit, productAmount);
                                            RequestDAO.updateProductRequestLimit(con, strSchemaProductId, productAmount, "subtracting", dcmId, warehouseId);
                                            RequestDAO.updateProductStock(con, strSchemaProductId, productAmount, "subtracting", warehouseId);
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Exception in line 2664");
                                        e.printStackTrace();
                                    }
                                }
                                String dcmRequestProductsQuota = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA);
                                QuotaDAO.updateDcmQuotaAfterRequest(con, dcmId, dcmRequestProductsQuota, "-");
                                RequestModel requestModel = RequestDAO.getRequestById(con, insertedRequestId + "");
                                String serverName = (String) paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_NAME);
                                String serverPort = (String) paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_PORT);
                                String appName = (String) paramHashMap.get(UserAccountInterfaceKey.CONTROL_HIDDEN_CONTEXT_PATH);
                                RequestDAO.sendRequestNotificationToList(con, serverName, serverPort, appName, requestModel, channelID);
                            }
                        } else {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "There is already active or rejected request for this DCM.");
                        }
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case SHOW_ALL_REQUESTS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        System.out.println("The user idddddddddddddddddd isssssssssssssssss " + strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        //String warehouseId = (String)paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        //dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        //Vector vetRequestList = RequestDAO.getAllRequests(con);
                        Vector vetRequestList = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vetRequestList);
                        Vector vecRequestStatusList = RequestDAO.getAllRequestStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecRequestStatusList);
                        String strLevel = "";
                        //if(channelID.compareTo("1")==0)
                        strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        //else if (channelID.compareTo("2")==0)
                        //{strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;}
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmDto);
                        Vector warehouseVec = RequestDAO.getWarehouseByChannel(con, channelID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, warehouseVec);
                        String channleName = SchemaDAO.getChannelName(channelID, con);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, channleName);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_ALL_REQUESTS_FOR_FRANCHISE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_CHANNEL_FRANCHISE;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        //Vector vetRequestList = RequestDAO.getAllRequests(con);
                        Vector vetRequestList = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vetRequestList);
                        Vector vecRequestStatusList = RequestDAO.getAllRequestStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecRequestStatusList);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmDto);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;


                case SHOW_ALL_REQUESTS_FOR_COMPLEMANTRY:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_COMPLEMENTARY_CHANNEL;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        //Vector vetRequestList = RequestDAO.getAllRequests(con);
                        Vector vetRequestList = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vetRequestList);
                        Vector vecRequestStatusList = RequestDAO.getAllRequestStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecRequestStatusList);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("3") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmDto);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_ALL_REQUESTS_FOR_AUTHORIZED_AGENT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_AUTHORIZED_AGENT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        //Vector vetRequestList = RequestDAO.getAllRequests(con);
                        Vector vetRequestList = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vetRequestList);
                        Vector vecRequestStatusList = RequestDAO.getAllRequestStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecRequestStatusList);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("3") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("4") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmDto);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_ALL_REQUESTS_CHANGE_TO_PAID:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);

                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        //Vector vetRequestList = RequestDAO.getAllRequests(con);
                        Vector vetRequestList = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vetRequestList);
                        Vector vecRequestStatusList = RequestDAO.getAllRequestStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecRequestStatusList);
                        String strLevel = "";

                        strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;

                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmDto);

                        Vector warehouseVec = RequestDAO.getWarehouseByChannel(con, channelID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, warehouseVec);

                        String channleName = SchemaDAO.getChannelName(channelID, con);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, channleName);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_ALL_REQUESTS_CHANGE_TO_PAID_FOR_FRANCHISE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_CHANNEL_FRANCHISE;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        //Vector vetRequestList = RequestDAO.getAllRequests(con);
                        Vector vetRequestList = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vetRequestList);
                        Vector vecRequestStatusList = RequestDAO.getAllRequestStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecRequestStatusList);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("3") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmDto);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_ALL_REQUESTS_CHANGE_TO_PAID_FOR_COMPLEMANTRY:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_COMPLEMENTARY_CHANNEL;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        //Vector vetRequestList = RequestDAO.getAllRequests(con);
                        Vector vetRequestList = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vetRequestList);
                        Vector vecRequestStatusList = RequestDAO.getAllRequestStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecRequestStatusList);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("3") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmDto);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;


                case SHOW_ALL_REQUESTS_CHANGE_TO_PAID_FOR_AUTHORIZED_AGENT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_AUTHORIZED_AGENT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        //Vector vetRequestList = RequestDAO.getAllRequests(con);
                        Vector vetRequestList = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vetRequestList);
                        Vector vecRequestStatusList = RequestDAO.getAllRequestStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecRequestStatusList);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("3") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("4") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmDto);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;




                case UPDATE_REQUEST_STATUS:
                    try {
                        dataHashMap = new HashMap();
                        //////Search values//////////
                        //String strRequestCode = (String)paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CODE);
                        //dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CODE,strRequestCode);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        String strRequestId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_ID, strRequestId);
                        String strDcmId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_SELECT_DCM_NAME);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_SELECT_DCM_NAME, strDcmId);
                        String strRequestCreationDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_FROM, strRequestCreationDateFrom);
                        String strRequestCreationDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_TO, strRequestCreationDateTo);
                        String strRequestStatusId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_SELECT_REQUEST_STATUS);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_SELECT_REQUEST_STATUS, strRequestStatusId);

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

                        for (int j = 0; j < paramHashMap.size(); j++) {
                            try {
                                String tempStatusString = (String) paramHashMap.keySet().toArray()[j];
                                System.out.println("2988 tempStatusString isss " + tempStatusString);
                                if (!(paramHashMap.get(tempStatusString) instanceof String)) {
                                    continue;
                                }
                                String keyValue = (String) paramHashMap.get(tempStatusString);
                                System.out.println("2988 keyValue iss " + keyValue);
                                //Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
                                if (tempStatusString.startsWith(SOPInterfaceKey.INPUT_SELECT_REQUEST_STATUS)) {
                                    int strlength = SOPInterfaceKey.INPUT_SELECT_REQUEST_STATUS.length() + 1;
                                    strRequestId = tempStatusString.substring(strlength, tempStatusString.length());
                                    String requestOldStatusId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_OLD_REQUEST_STATUS + "_" + strRequestId);
                                    String requestDCMId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID + "_" + strRequestId);
                                    if (keyValue.compareTo(requestOldStatusId) != 0) {
                                        RequestDAO.updateRequestStatus(con, strRequestId, keyValue, strUserID, requestDCMId, channelID);
                                        if (keyValue.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_DELETED) == 0) {
                                            Vector vecRequestDetail = RequestDAO.getRequestDetails(con, strRequestId);
                                            RequestModel requestModel = RequestDAO.getRequestById(con, strRequestId);
                                            String dcmId = "";
                                            if (requestModel != null) {
                                                dcmId = requestModel.getDcmId();
                                            }
                                            for (int f = 0; f < vecRequestDetail.size(); f++) {
                                                RequestDetailModel requestDetailModel = (RequestDetailModel) vecRequestDetail.get(f);
                                                String requestDetailSchemaProductId = requestDetailModel.getSchemaProductId();
                                                String requestDetailProductAmount = requestDetailModel.getProductAmount();
                                                int intRequestDetailProductAmount = Integer.parseInt(requestDetailProductAmount);
                                                String isQuotaItem = requestDetailModel.getIsQuotaItem();
                                                String productPrice = requestDetailModel.getProductPrice();
                                                float intProductPrice = Float.parseFloat(productPrice);
                                                float returnedQuota = 0;
                                                if (isQuotaItem.compareTo("1") == 0) {
                                                    returnedQuota = intProductPrice * intRequestDetailProductAmount;
                                                    String strReturnedQuota = returnedQuota + "";
                                                    QuotaDAO.updateDcmQuotaAfterRequest(con, dcmId, strReturnedQuota, "+");
                                                }
                                                RequestDAO.updateProductRequestLimit(con, requestDetailSchemaProductId, requestDetailProductAmount, "adding", dcmId, warehouseId);
                                                RequestDAO.updateProductStock(con, requestDetailSchemaProductId, requestDetailProductAmount, "adding", warehouseId);
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Exception in line 2988");
                                e.printStackTrace();
                            }
                        }

                        Vector vetRequestList = RequestDAO.getRequestsByFilter(con, strRequestId, strDcmId, strRequestCreationDateFrom, strRequestCreationDateTo, strRequestStatusId, channelID, warehouseId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vetRequestList);
                        Vector vecRequestStatusList = RequestDAO.getAllRequestStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecRequestStatusList);
                        String strLevel = "";
                        //if(channelID.compareTo("1")==0)
                        strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        //else if (channelID.compareTo("2")==0)
                        //{strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;}
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SEARCH_REQUEST);
                        Vector warehouseVec = RequestDAO.getWarehouseByChannel(con, channelID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, warehouseVec);
                        String channleName = SchemaDAO.getChannelName(channelID, con);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, channleName);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case UPDATE_REQUEST_STATUS_CHANGE_TO_PAID:
                    try {
                        dataHashMap = new HashMap();
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        //////Search values//////////
                        //String strRequestCode = (String)paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CODE);
                        String strRequestId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_ID, strRequestId);
                        //dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CODE,strRequestCode);
                        String strDcmId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_SELECT_DCM_NAME);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_SELECT_DCM_NAME, strDcmId);
                        String strRequestCreationDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_FROM, strRequestCreationDateFrom);
                        String strRequestCreationDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_TO, strRequestCreationDateTo);
                        String strRequestStatusId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_SELECT_REQUEST_STATUS);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_SELECT_REQUEST_STATUS, strRequestStatusId);

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

                        for (int j = 0; j < paramHashMap.size(); j++) {
                            try {
                                String tempStatusString = (String) paramHashMap.keySet().toArray()[j];
                                System.out.println("3093 tempStatusString isss " + tempStatusString);
                                if (!(paramHashMap.get(tempStatusString) instanceof String)) {
                                    continue;
                                }
                                String keyValue = (String) paramHashMap.get(tempStatusString);
                                //Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
                                if (tempStatusString.startsWith(SOPInterfaceKey.INPUT_SELECT_REQUEST_STATUS)) {
                                    int strlength = SOPInterfaceKey.INPUT_SELECT_REQUEST_STATUS.length() + 1;
                                    //String strRequestId = tempStatusString.substring(strlength, tempStatusString.length());
                                    strRequestId = tempStatusString.substring(strlength, tempStatusString.length());
                                    String requestOldStatusId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_OLD_REQUEST_STATUS + "_" + strRequestId);
                                    String requestDCMId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID + "_" + strRequestId);
                                    if (keyValue.compareTo(requestOldStatusId) != 0) {
                                        RequestDAO.updateRequestStatus(con, strRequestId, keyValue, strUserID, requestDCMId, channelID);
                                        if (keyValue.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_DELETED) == 0) {
                                            Vector vecRequestDetail = RequestDAO.getRequestDetails(con, strRequestId);
                                            RequestModel requestModel = RequestDAO.getRequestById(con, strRequestId);
                                            String dcmId = "";
                                            if (requestModel != null) {
                                                dcmId = requestModel.getDcmId();
                                            }
                                            for (int f = 0; f < vecRequestDetail.size(); f++) {
                                                RequestDetailModel requestDetailModel = (RequestDetailModel) vecRequestDetail.get(f);
                                                String requestDetailSchemaProductId = requestDetailModel.getSchemaProductId();
                                                String requestDetailProductAmount = requestDetailModel.getProductAmount();
                                                int intRequestDetailProductAmount = Integer.parseInt(requestDetailProductAmount);
                                                String isQuotaItem = requestDetailModel.getIsQuotaItem();
                                                String productPrice = requestDetailModel.getProductPrice();
                                                int intProductPrice = Integer.parseInt(productPrice);
                                                int returnedQuota = 0;
                                                if (isQuotaItem.compareTo("1") == 0) {
                                                    returnedQuota = intProductPrice * intRequestDetailProductAmount;
                                                    String strReturnedQuota = returnedQuota + "";
                                                    QuotaDAO.updateDcmQuotaAfterRequest(con, dcmId, strReturnedQuota, "+");
                                                }
                                                RequestDAO.updateProductRequestLimit(con, requestDetailSchemaProductId, requestDetailProductAmount, "adding", dcmId, warehouseId);
                                                RequestDAO.updateProductStock(con, requestDetailSchemaProductId, requestDetailProductAmount, "adding", warehouseId);
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Exception in line 3093");
                                e.printStackTrace();
                            }
                        }

                        Vector vetRequestList = RequestDAO.getRequestsByFilter(con, strRequestId, strDcmId, strRequestCreationDateFrom, strRequestCreationDateTo, strRequestStatusId, channelID, warehouseId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vetRequestList);
                        Vector vecRequestStatusList = RequestDAO.getAllRequestStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecRequestStatusList);
                        String strLevel = "";
                        //if(channelID.compareTo("1")==0)
                        strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        //else if (channelID.compareTo("2")==0)
                        //{strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;}
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SEARCH_REQUEST);
                        Vector warehouseVec = RequestDAO.getWarehouseByChannel(con, channelID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, warehouseVec);
                        String channleName = SchemaDAO.getChannelName(channelID, con);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, channleName);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case VIEW_REQUEST_DETAILS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strRequestId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_ID, strRequestId);
                        Vector vecRequestDetail = RequestDAO.getRequestDetails(con, strRequestId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecRequestDetail);
                        String dcmName = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME, dcmName);
                        String requestCreationDate = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_CREATION_DATE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_CREATION_DATE, requestCreationDate);
                        String requestStatus = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_STATUS);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_STATUS, requestStatus);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case PRINT_REQUEST_PAYMENT_SLIP:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strRequestId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_ID, strRequestId);
                        String strRequestCode = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_CODE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_CODE, strRequestCode);
                        Vector vecRequestDetail = RequestDAO.getRequestDetails(con, strRequestId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecRequestDetail);
                        String dcmName = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME, dcmName);
                        String requestCreationDate = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_CREATION_DATE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_CREATION_DATE, requestCreationDate);
                        String requestStatus = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_STATUS);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_STATUS, requestStatus);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case DATA_WAREHOUSE_IMPORT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = SOPInterfaceKey.CONST_GEN_CHANNEL_DISTRIBUTION;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        Vector tableDefVector = DataImportTableDefDAO.getTableDefByCategory("4");
                        dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR, tableDefVector);
                        Vector channelVec = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, channelVec);
                        Vector warehouseChannelVec = RequestDAO.getChannelWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, warehouseChannelVec);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case DATA_WAREHOUSE_IMPORT_FOR_FRANCHISE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = SOPInterfaceKey.CONST_GEN_CHANNEL_FRANCHISE;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        Vector tableDefVector = DataImportTableDefDAO.getTableDefByCategory("4");
                        dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR, tableDefVector);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case DATA_WAREHOUSE_IMPORT_FOR_AUTHORIZED_AGENT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = SOPInterfaceKey.CONST_GEN_AUTHORIZED_AGENT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        Vector tableDefVector = DataImportTableDefDAO.getTableDefByCategory("4");
                        dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR, tableDefVector);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;


                case DATA_WAREHOUSE_IMPORT_FOR_COMPLEMANTRY:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = SOPInterfaceKey.CONST_GEN_COMPLEMENTARY_CHANNEL;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        Vector tableDefVector = DataImportTableDefDAO.getTableDefByCategory("4");
                        dataHashMap.put(AdministrationInterfaceKey.TABLE_DEF_VECTOR, tableDefVector);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case DATA_WAREHOUSE_IMPORT_PROCESS:
                    try {
                        dataHashMap = new HashMap();
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        System.out.println("The channel id in handler issssssssss " + channelId);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);

                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        System.out.println("The warehouse id in handler issssssssss " + warehouseId);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case GENERATE_DATA_WAREHOUSE_IMPORT_TEMPLATE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case VIEW_STOCK_PRODUCTS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        //Vector vecProductStock = SchemaDAO.getProductStockByDate(con,"*","*","","");
                        Vector vecProductStock = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecProductStock);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case VIEW_STOCK_PRODUCTS_FOR_FRANCHISE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = SOPInterfaceKey.CONST_GEN_CHANNEL_FRANCHISE;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        //Vector vecProductStock = SchemaDAO.getProductStockByDate(con,"*","*","","");
                        Vector vecProductStock = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecProductStock);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case VIEW_STOCK_PRODUCTS_FOR_COMPLEMANTRY:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = SOPInterfaceKey.CONST_GEN_COMPLEMENTARY_CHANNEL;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        //Vector vecProductStock = SchemaDAO.getProductStockByDate(con,"*","*","","");
                        Vector vecProductStock = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecProductStock);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case VIEW_STOCK_PRODUCTS_FOR_AUTHORIZED_AGENT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = SOPInterfaceKey.CONST_GEN_AUTHORIZED_AGENT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        //Vector vecProductStock = SchemaDAO.getProductStockByDate(con,"*","*","","");
                        Vector vecProductStock = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecProductStock);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SEARCH_STOCK_PRODUCT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        String searchImportDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_IMPORT_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_IMPORT_DATE_FROM, searchImportDateFrom);
                        String searchImportDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_IMPORT_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_IMPORT_DATE_TO, searchImportDateTo);
                        String searchProductCode = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PRODUCT_CODE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_PRODUCT_CODE, searchProductCode);
                        String searchProductNameEnglish = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PRODUCT_NAME_ENGLISH);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_PRODUCT_NAME_ENGLISH, searchProductNameEnglish);

                        Vector vecProductStock = SchemaDAO.getProductStockByDate(con, searchImportDateFrom, searchImportDateTo, searchProductCode, searchProductNameEnglish, channelId, warehouseId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecProductStock);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SEARCH_STOCK_PRODUCT);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case GENERATE_MANUAL_DCM_PRODUCT_LIMITS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        String warehouseId = "";
                        Vector stockLatestProducts = SchemaDAO.getLatestStockProductsMatchActiveSchemaProducts(channelID, warehouseId);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        SchemaDAO.deleteProductRequestLimits(con);
                        for (int i = 0; i < dcmDto.getDcmModelsSize(); i++) {
                            DCMModel model = dcmDto.getDcm(i);
                            int dcmID = model.getDcmId();
                            String dcmName = model.getDcmName();
                            for (int j = 0; j < stockLatestProducts.size(); j++) {
                                ProductModel productModel = (ProductModel) stockLatestProducts.get(j);
                                String productCode = productModel.getStockProductCode();
                                Utility.logger.debug("Product code isssssssssssss " + productCode);
                                String schemaId = productModel.getSchemaId();
                                String schemaProductId = productModel.getSchemaProductId();
                                String minimumLimit = "0";
                                String maximumLimit = productModel.getActiveAmount();
                                String physicalMaximumLimit = productModel.getPhysicalAmount();
                                String isManual = SOPInterfaceKey.CONST_PRODUCT_LIMIT_IS_MANUAL;
                                //////////////insert into SOP_PRODUCT_REQUEST_LIMIT//////////
                                SchemaDAO.insertProductRequestLimit(dcmID, schemaProductId, minimumLimit, maximumLimit, isManual, physicalMaximumLimit, productCode, schemaId, warehouseId);
                            }
                        }
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "DCM product limits have been generated successfully.");
                    } catch (Exception objExp) {
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Failed...");
                        objExp.printStackTrace();
                    }
                    break;
                case GENERATE_MANUAL_DCM_PRODUCT_LIMITS_CHECKING:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    } catch (Exception objExp) {
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Failed...");
                        objExp.printStackTrace();
                    }
                    break;
                case ADMIN_SELECT_DCM_REQUEST_PRODUCT_LIMITS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_CHANNEL_DISTRIBUTION;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        //if(channelID.compareTo("1")==0)
                        strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        Vector channelVec = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, channelVec);

                        Vector dcmChannelVec = RequestDAO.getDcmChannel(con, strLevel, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmChannelVec);
                        Vector warehouseChannelVec = RequestDAO.getChannelWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, warehouseChannelVec);
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_ADMIN_SET_DCM_REQUEST_PRODUCT_LIMITS);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "DCM Request Product Limits");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case ADMIN_SET_DCM_REQUEST_PRODUCT_LIMITS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strDcmName = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME, strDcmName);
                        String strDcmID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID, strDcmID);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        Vector vecDCMRequestProductLimit = SchemaDAO.getDCMRequestProductLimits(con, strDcmID, warehouseId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecDCMRequestProductLimit);
                        int intDcmID = Integer.parseInt(strDcmID);
                        DCMModel dcmModel = DCMDao.getDCMModel(con, intDcmID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, dcmModel);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case SAVE_DCM_REQUEST_PRODUCT_LIMITS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        //if(channelID.compareTo("1")==0)
                        strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        //else if (channelID.compareTo("2")==0)
                        //{strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;}
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                        Vector channelVec = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, channelVec);

                        Vector dcmChannelVec = RequestDAO.getDcmChannel(con, strLevel, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmChannelVec);
                        Vector warehouseChannelVec = RequestDAO.getChannelWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, warehouseChannelVec);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_ADMIN_SET_DCM_REQUEST_PRODUCT_LIMITS);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "DCM Request Product Limits");
                        for (int j = 0; j < paramHashMap.size(); j++) {
                            try {
                                String tempStatusString = (String) paramHashMap.keySet().toArray()[j];
                                System.out.println("3560 tempStatusString isss " + tempStatusString);
                                if (!(paramHashMap.get(tempStatusString) instanceof String)) {
                                    continue;
                                }
                                String minimumLimit = (String) paramHashMap.get(tempStatusString);
                                //Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
                                if (tempStatusString.startsWith(SOPInterfaceKey.INPUT_TEXT_MINIMUM_LIMIT)) {
                                    int strlength = SOPInterfaceKey.INPUT_TEXT_MINIMUM_LIMIT.length() + 1;
                                    String strProductRequestLimitId = tempStatusString.substring(strlength, tempStatusString.length());
                                    String maximumLimit = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_MAXIMUM_LIMIT + "_" + strProductRequestLimitId);
                                    SchemaDAO.updateProductRequestLimit(con, strProductRequestLimitId, minimumLimit, maximumLimit);
                                }
                            } catch (Exception e) {
                                System.out.println("Exception in line 3560");
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case SHOW_ALL_EQUATIONS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = SOPInterfaceKey.CONST_GEN_CHANNEL_DISTRIBUTION;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        Vector vecEquationStatus = EquationDAO.getAllEquationStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecEquationStatus);
                        Vector vecEquations = EquationDAO.getAllEquations(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecEquations);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();

                    }
                    break;

                case ADD_EQUATION:
                    try {
                        dataHashMap = new HashMap();
                        Vector newVec = new Vector();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        Vector vecEquationStatus = EquationDAO.getAllEquationStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecEquationStatus);
                        EquationDTO equationDTO = new EquationDTO();
                        equationDTO.setVecEquationOperator(EquationDAO.getEquationOperators(con));
                        equationDTO.setVecEquationObjectType(EquationDAO.getEquationObjects(con));
                        equationDTO.setVecEquationTerms(EquationDAO.getEquationTerms(con));
                        equationDTO.setVecEquationElements(newVec);
                        equationDTO.setVecEquation(newVec);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, equationDTO);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case EDIT_EQUATION:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strChannelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, strChannelID);
                        Vector vecEquationStatus = EquationDAO.getAllEquationStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecEquationStatus);
                        String strEquationId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_EQUATION_ID);
                        EquationDTO equationDTO = new EquationDTO();
                        equationDTO.setVecEquationOperator(EquationDAO.getEquationOperators(con));
                        equationDTO.setVecEquationObjectType(EquationDAO.getEquationObjects(con));
                        equationDTO.setVecEquationTerms(EquationDAO.getEquationTerms(con));
                        equationDTO.setVecEquationElements(EquationDAO.getEquationDetails(con, strEquationId));
                        equationDTO.setVecEquation(EquationDAO.getEquationsByFilter(con, strEquationId));
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, equationDTO);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case UPDATE_EQUATION_STATUS:
                    try {
                        dataHashMap = new HashMap();
                        for (int j = 0; j < paramHashMap.size(); j++) {
                            try {
                                String tempStatusString = (String) paramHashMap.keySet().toArray()[j];
                                System.out.println("3662 tempStatusString isss " + tempStatusString);
                                if (!(paramHashMap.get(tempStatusString) instanceof String)) {
                                    continue;
                                }
                                String keyValue = (String) paramHashMap.get(tempStatusString);
                                //Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
                                if (tempStatusString.startsWith(SOPInterfaceKey.INPUT_SELECT_EQUATION_STATUS)) {
                                    int strlength = SOPInterfaceKey.INPUT_SELECT_EQUATION_STATUS.length() + 1;
                                    String strEquationId = tempStatusString.substring(strlength, tempStatusString.length());
                                    EquationDAO.updateEquationStatus(con, strEquationId, keyValue);
                                }
                            } catch (Exception e) {
                                System.out.println("Exception in line 3662");
                                e.printStackTrace();
                            }
                        }
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        Vector vecEquationStatus = EquationDAO.getAllEquationStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecEquationStatus);
                        Vector vecEquations = EquationDAO.getAllEquations(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecEquations);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case SAVE_EQUATION:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        Vector vecEquationStatus = EquationDAO.getAllEquationStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecEquationStatus);
                        int equationElementsCount = Integer.parseInt((String) paramHashMap.get("penaltyeq_count"));
                        /*
                         * for(int j=0; j<paramHashMap.size(); j++) { String
                         * tempStatusString =
                         * (String)paramHashMap.keySet().toArray()[j]; String
                         * keyValue = ""; try{keyValue =
                         * (String)paramHashMap.get(tempStatusString);}catch(Exception
                         * e){}
                         * Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
                         * }
                         */
                        String strEquationName = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_EQUATION_NAME);
                        String strEquationDesc = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXTAREA_EQUATION_DESCRIPTION);
                        Long lEquationID = Utility.getSequenceNextVal(con, "SEQ_SOP_EQUATION_ID");
                        String strEquationID = lEquationID.toString();
                        //Utility.logger.debug("Elements Count : "+equationElementsCount+1);  
                        int returnValue = EquationDAO.insertEquation(con, lEquationID, strEquationName, strEquationDesc, 1);
                        if (returnValue > 0) {
                            for (int i = 1; i <= equationElementsCount + 1; i++) {
                                if (paramHashMap.get(SOPInterfaceKey.CONTROL_HIDDEN_NAME_EQ_ELEMENT_TYPE_ID_PREFIX + i) != null) {
                                    String elementType = (String) paramHashMap.get(SOPInterfaceKey.CONTROL_HIDDEN_NAME_EQ_ELEMENT_TYPE_ID_PREFIX + i);
                                    String elementValue = (String) paramHashMap.get(SOPInterfaceKey.CONTROL_HIDDEN_NAME_EQ_ELEMENT_VALUE_PREFIX + i);
                                    Long lEquationDetailID = Utility.getSequenceNextVal(con, "SEQ_SOP_EQUATION_DETAIL_ID");
                                    Utility.logger.debug("Element type : " + elementType + " Element Value : " + elementValue);
                                    EquationDAO.insertEquationDetail(con, lEquationDetailID, strEquationID, elementValue, elementType);
                                }
                            }
                        }
                        Vector vecEquations = EquationDAO.getAllEquations(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecEquations);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case UPDATE_EQUATION:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strChannelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, strChannelID);
                        Vector vecEquationStatus = EquationDAO.getAllEquationStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecEquationStatus);
                        int equationElementsCount = Integer.parseInt((String) paramHashMap.get("penaltyeq_count"));

                        String strEquationId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_EQUATION_ID);
                        String strEquationName = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_EQUATION_NAME);
                        String strEquationDesc = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXTAREA_EQUATION_DESCRIPTION);


                        int returnValue = EquationDAO.updateEquation(con, strEquationId, strEquationName, strEquationDesc);
                        if (returnValue > 0) {
                            EquationDAO.deleteEquationDetail(con, strEquationId);
                            for (int i = 1; i <= equationElementsCount + 1; i++) {
                                if (paramHashMap.get(SOPInterfaceKey.CONTROL_HIDDEN_NAME_EQ_ELEMENT_TYPE_ID_PREFIX + i) != null) {
                                    String elementType = (String) paramHashMap.get(SOPInterfaceKey.CONTROL_HIDDEN_NAME_EQ_ELEMENT_TYPE_ID_PREFIX + i);
                                    String elementValue = (String) paramHashMap.get(SOPInterfaceKey.CONTROL_HIDDEN_NAME_EQ_ELEMENT_VALUE_PREFIX + i);
                                    Long lEquationDetailID = Utility.getSequenceNextVal(con, "SEQ_SOP_EQUATION_DETAIL_ID");
                                    Utility.logger.debug("Element type : " + elementType + " Element Value : " + elementValue);
                                    EquationDAO.insertEquationDetail(con, lEquationDetailID, strEquationId, elementValue, elementType);
                                }
                            }
                        }
                        Vector vecEquations = EquationDAO.getAllEquations(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecEquations);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case SELECT_DCM_QUOTA_SETTINGS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        //if(channelID.compareTo("1")==0)
                        strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        //else if (channelID.compareTo("2")==0)
                        //{strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;}
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SET_DCM_QUOTA_SETTING_VALUES);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "Quota Setting");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SELECT_FRANCHISE_QUOTA_SETTINGS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_CHANNEL_FRANCHISE;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SET_DCM_QUOTA_SETTING_VALUES);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "Quota Setting");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;



                case SELECT_COMPLEMANTRY_QUOTA_SETTINGS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_COMPLEMENTARY_CHANNEL;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("3") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SET_DCM_QUOTA_SETTING_VALUES);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "Quota Setting");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SELECT_AUTHORIZED_AGENT_QUOTA_SETTINGS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_AUTHORIZED_AGENT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("3") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("4") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SET_DCM_QUOTA_SETTING_VALUES);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "Quota Setting");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;



                case SET_DCM_QUOTA_SETTING_VALUES:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String strDcmName = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME, strDcmName);
                        System.out.println("The dcm name issssssssss " + strDcmName);
                        String strDcmID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID, strDcmID);
                        int intDcmID = Integer.parseInt(strDcmID);
                        DCMModel dcmModel = DCMDao.getDCMModel(con, intDcmID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, dcmModel);
                        DCMQuotaModel dcmQuotaModel = QuotaDAO.getDcmQuotaSetting(con, strDcmID);
                        if (dcmQuotaModel != null) {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmQuotaModel);
                        }
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case UPDATE_DCM_QUOTA_SETTING_VALUE:
                    try {
                        dataHashMap = new HashMap();
                        String strDcmID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID);
                        String strDcmQuotaValidDays = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_DCM_QUOTA_VALID_DAYS);
                        String strDcmQuotaRecalculateDays = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_DCM_QUOTA_RECALCULATE_DAYS);
                        String strDcmQuotaAction = (String) paramHashMap.get(SOPInterfaceKey.DCM_QUOTA_ACTION);
                        if (strDcmQuotaAction.compareTo("update") == 0) {
                            QuotaDAO.updateDcmQuotaSetting(con, strDcmID, strDcmQuotaValidDays, strDcmQuotaRecalculateDays);
                        } else if (strDcmQuotaAction.compareTo("insert") == 0) {
                            QuotaDAO.insertDcmQuotaSetting(con, strDcmID, strDcmQuotaValidDays, strDcmQuotaRecalculateDays);
                        }

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SET_DCM_QUOTA_SETTING_VALUES);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "Quota Setting");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SELECT_DCM_QUOTA_VALUES:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        //String channelID = SOPInterfaceKey.CONST_GEN_CHANNEL_DISTRIBUTION;
                        //dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID,channelID);
                        String strLevel = "";
                        //if(channelID.compareTo("1")==0)
                        strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        //else if (channelID.compareTo("2")==0)
                        //{strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;}
                        Vector channels = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, channels);
                        String levelName = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        Vector scmChannel = RequestDAO.getDcmChannel(con, levelName, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, scmChannel);
                        //Vector warehouseChannelVec = RequestDAO.getChannelWarehouse(con);
                        //dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, warehouseChannelVec);
                        //dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SET_DCM_QUOTA_SETTING_VALUES);
                        //DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID,channelID);
                        //dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SET_DCM_QUOTA_VALUES);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "Quota Values");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SELECT_FRANCHISE_QUOTA_VALUES:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_CHANNEL_FRANCHISE;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SET_DCM_QUOTA_VALUES);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "Quota Values");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;




                case SELECT_COMPLEMANTRY_QUOTA_VALUES:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_COMPLEMENTARY_CHANNEL;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("3") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SET_DCM_QUOTA_VALUES);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "Quota Values");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SELECT_AUTHORIZED_AGENT_QUOTA_VALUES:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_AUTHORIZED_AGENT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("3") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("4") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SET_DCM_QUOTA_VALUES);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "Quota Values");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SET_DCM_QUOTA_VALUES:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strDcmName = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME);

                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME, strDcmName);
                        String strDcmID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID, strDcmID);
                        int intDcmID = Integer.parseInt(strDcmID);
                        DCMModel dcmModel = DCMDao.getDCMModel(con, intDcmID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmModel);
                        DCMQuotaModel dcmQuotaSettingModel = QuotaDAO.getDcmQuotaSetting(con, strDcmID);
                        if (dcmQuotaSettingModel != null) {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmQuotaSettingModel);
                            String dcmQuotaSettingId = dcmQuotaSettingModel.getDcmQuotaSettingId();
                            if (dcmQuotaSettingId == null) {
                                dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "DCM must have Quota Setting");
                            }
                        }
                        Vector vecDcmQuotaValues = QuotaDAO.getDcmQuota(con, strDcmID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecDcmQuotaValues);
                        Utility.logger.debug("asasdas");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case UPDATE_DCM_QUOTA_VALUE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strDcmID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID);
                        String strDcmQuotaValue = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_DCM_QUOTA_VALUE);
                        String strDcmQuotaAction = (String) paramHashMap.get(SOPInterfaceKey.DCM_QUOTA_ACTION);
                        if (strDcmQuotaAction.compareTo("insert") == 0) {
                            for (int j = 0; j < paramHashMap.size(); j++) {
                                try {
                                    String tempStatusString = (String) paramHashMap.keySet().toArray()[j];
                                    System.out.println("4116 tempStatusString isss " + tempStatusString);
                                    if (!(paramHashMap.get(tempStatusString) instanceof String)) {
                                        continue;
                                    }
                                    String dateFrom = (String) paramHashMap.get(tempStatusString);
                                    if (tempStatusString.startsWith(SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA_VALID_FROM)) {
                                        String SOPId = tempStatusString.substring(SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA_VALID_FROM.length() + 1, tempStatusString.length());
                                        String dateToKey = SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA_VALID_TO + "_" + SOPId;
                                        String dateTo = (String) paramHashMap.get(dateToKey);
                                        String quotaValueKey = SOPInterfaceKey.INPUT_TEXT_DCM_QUOTA_VALUE + "_" + SOPId;
                                        String quotaValue = (String) paramHashMap.get(quotaValueKey);
                                        Long lDCMQuotaID = Utility.getSequenceNextVal(con, "SEQ_SOP_DCM_QUOTA_ID");
                                        QuotaDAO.insertDcmQuota(con, lDCMQuotaID, strDcmID, quotaValue, dateFrom, dateTo);
                                    }
                                } catch (Exception e) {
                                    System.out.println("Exception in line 4116");
                                    e.printStackTrace();
                                }
                            }
                            String strDcmQuotaRecalculateDay = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA_RECALCULATE_DAY);
                            QuotaDAO.updateDcmQuotaSetting(con, strDcmID, strDcmQuotaRecalculateDay);
                        } else if (strDcmQuotaAction.compareTo("update") == 0) {
                            for (int j = 0; j < paramHashMap.size(); j++) {
                                try {
                                    String changeReason = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXTAREA_DCM_QUOTA_CHANGE_REASON);
                                    String tempStatusString = (String) paramHashMap.keySet().toArray()[j];
                                    System.out.println("4148 tempStatusString isss " + tempStatusString);
                                    if (!(paramHashMap.get(tempStatusString) instanceof String)) {
                                        continue;
                                    }
                                    String quotaValue = (String) paramHashMap.get(tempStatusString);
                                    if (tempStatusString.startsWith(SOPInterfaceKey.INPUT_TEXT_DCM_QUOTA_VALUE)) {
                                        String dcmQuotaId = tempStatusString.substring(SOPInterfaceKey.INPUT_TEXT_DCM_QUOTA_VALUE.length() + 1, tempStatusString.length());
                                        String quotaOldValueKey = SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA_VALUE_OLD + "_" + dcmQuotaId;
                                        String quotaOldValue = (String) paramHashMap.get(quotaOldValueKey);
                                        String validFromKey = SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA_VALID_FROM + "_" + dcmQuotaId;
                                        String validFrom = (String) paramHashMap.get(validFromKey);
                                        String validToKey = SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA_VALID_TO + "_" + dcmQuotaId;
                                        String validTo = (String) paramHashMap.get(validToKey);
                                        if (quotaOldValue.compareTo(quotaValue) != 0) {
                                            Long lDCMQuotaLogID = Utility.getSequenceNextVal(con, "SEQ_SOP_DCM_QUOTA_LOG_ID");
                                            QuotaDAO.insertDcmQuotaLog(con, lDCMQuotaLogID, dcmQuotaId, strDcmID, strUserID, quotaOldValue, quotaValue, validFrom, validTo, changeReason);
                                        }
                                        QuotaDAO.updateDcmQuota(con, dcmQuotaId, quotaValue);
                                    }
                                } catch (Exception e) {
                                    System.out.println("Exception in line 4148");
                                    e.printStackTrace();
                                }
                            }
                        }

                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SET_DCM_QUOTA_VALUES);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "Quota Values");
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case SHOW_USER_DCM_SCREEN_SOP:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.equals(null) || channelID.compareTo("") == 0) {
                        } else {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        // if(channelID.compareTo("1")==0)
                        //strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;}
                        //else if (channelID.compareTo("2")==0)
                        //{strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;}            
                        Vector usersVector = UserDAO.getSOPSystemUsersList(con);
                        HashMap additionalDataHashMap = UserDAO.getAdditionalData(con, channelID, strLevel);

                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, usersVector);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalDataHashMap);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_USER_FRANCHISE_SCREEN_SOP:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_CHANNEL_FRANCHISE;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        Vector usersVector = UserDAO.getSOPSystemUsersList(con);
                        HashMap additionalDataHashMap = UserDAO.getAdditionalData(con, channelID, strLevel);

                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, usersVector);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalDataHashMap);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;


                case SHOW_USER_COMPLEMANTRY_SCREEN_SOP:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_COMPLEMENTARY_CHANNEL;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("3") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }

                        Vector usersVector = UserDAO.getSOPSystemUsersList(con);
                        HashMap additionalDataHashMap = UserDAO.getAdditionalData(con, channelID, strLevel);

                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, usersVector);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalDataHashMap);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SHOW_USER_AUTHORIZED_AGENT_SCREEN_SOP:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_AUTHORIZED_AGENT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("3") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("4") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }

                        Vector usersVector = UserDAO.getSOPSystemUsersList(con);
                        HashMap additionalDataHashMap = UserDAO.getAdditionalData(con, channelID, strLevel);

                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, usersVector);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalDataHashMap);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SOP_UPDATE_USER_DCM:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String strLevel = "";
                        if (channelId.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelId.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } // Complementary added
                        else if (channelId.compareTo("3") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;

                        }

                        int userID = new Integer((String) paramHashMap.get(UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID)).intValue();
                        Utility.logger.debug("User id " + userID);
                        CachEngine.removeObject(SOPInterfaceKey.CACH_OBJ_DCM_LIST_SOP + userID + SOPInterfaceKey.CONST_GEN_CHANNEL_DISTRIBUTION);
                        CachEngine.removeObject(SOPInterfaceKey.CACH_OBJ_DCM_LIST_SOP + userID + SOPInterfaceKey.CONST_GEN_CHANNEL_FRANCHISE);

                        if (UserDAO.deleteSOPUserDCMs(con, userID, channelId)) {
                            for (int j = 0; j < paramHashMap.size(); j++) {
                                try {
                                    String tempString = (String) paramHashMap.keySet().toArray()[j];
                                    System.out.println("4360 tempStatusString isss " + tempString);
                                    if (!(paramHashMap.get(tempString) instanceof String)) {
                                        continue;
                                    }
                                    if (tempString.startsWith(UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX)) {
                                        int nDCMID = new Integer(tempString.substring(
                                                tempString.lastIndexOf(
                                                UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX) + 9)).intValue();

                                        UserDAO.insertSOPUserDCMs(con, userID, nDCMID, channelId);
                                    }
                                } catch (Exception e) {
                                    System.out.println("Exception in line 4360");
                                    e.printStackTrace();
                                }
                            }
                            //Vector  usersVector = UserDAO.getContractRegisterationUsersList(m_conSDSConnection);
                            Vector usersVector = UserDAO.getSOPSystemUsersList(con);
                            HashMap additionalDataHashMap = UserDAO.getAdditionalData(con, channelId, strLevel);

                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, usersVector);
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalDataHashMap);
                        }
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;


                case SEARCH_PRODUCT_PGW:
                    try {
                        dataHashMap = new HashMap();
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        Utility.logger.debug("Channel id isssssssssssssssss " + channelId);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String strLcsProductCode = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_LCS_PRODUCT_CODE);
                        Utility.logger.debug("The Lcs Product Code is " + strLcsProductCode);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_LCS_PRODUCT_CODE, strLcsProductCode);
                        String strProductNameEnglish = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_PRODUCT_NAME_ENGLISH);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_PRODUCT_NAME_ENGLISH, strProductNameEnglish);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SEARCH_PRODUCT_PGW);

                        Vector vecSchemaProdcut = SchemaDAO.getProductPgwByFilter(con, strLcsProductCode, strProductNameEnglish, channelId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecSchemaProdcut);
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SEARCH_INVOICE:
                    try {
                        dataHashMap = new HashMap();
                        String strInvoiceNumber = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_INVOICE_NUMBER);
                        Utility.logger.debug("The Invoice Number is " + strInvoiceNumber);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_INVOICE_NUMBER, strInvoiceNumber);
                        String srtDcmId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_DCM_ID);
                        Utility.logger.debug("werwerew" + srtDcmId);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_DCM_ID, srtDcmId);
                        String strPaymentDate = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE, strPaymentDate);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SEARCH_INVOICE);

                        Vector vecInvoice = RequestDAO.getInvoiceByFilter(con, strInvoiceNumber, srtDcmId, strPaymentDate);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecInvoice);
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SEARCH_REQUEST:
                    try {
                        dataHashMap = new HashMap();
                        //String strRequestCode = (String)paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CODE);
                        //dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CODE,strRequestCode);
                        String strRequestId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_ID, strRequestId);
                        String strDcmId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_SELECT_DCM_NAME);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_SELECT_DCM_NAME, strDcmId);
                        String strRequestCreationDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_FROM, strRequestCreationDateFrom);
                        String strRequestCreationDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_TO, strRequestCreationDateTo);
                        String strRequestStatusId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_SELECT_REQUEST_STATUS);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_SELECT_REQUEST_STATUS, strRequestStatusId);

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        Vector vetRequestList = RequestDAO.getRequestsByFilter(con, strRequestId, strDcmId, strRequestCreationDateFrom, strRequestCreationDateTo, strRequestStatusId, channelID, warehouseId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vetRequestList);
                        Vector vecRequestStatusList = RequestDAO.getAllRequestStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecRequestStatusList);
                        String strLevel = "";
                        //if(channelID.compareTo("1")==0)
                        strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        //else if (channelID.compareTo("2")==0)
                        //{strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;}
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SEARCH_REQUEST);
                        Vector warehouseVec = RequestDAO.getWarehouseByChannel(con, channelID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, warehouseVec);
                        String channleName = SchemaDAO.getChannelName(channelID, con);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, channleName);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SEARCH_REQUEST_CHANGE_TO_PAID:
                    try {
                        dataHashMap = new HashMap();
                        //String strRequestCode = (String)paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CODE);
                        String strRequestId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_ID);
                        //dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CODE,strRequestCode);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_ID, strRequestId);
                        String strDcmId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_SELECT_DCM_NAME);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_SELECT_DCM_NAME, strDcmId);
                        String strRequestCreationDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_FROM, strRequestCreationDateFrom);
                        String strRequestCreationDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_TO, strRequestCreationDateTo);
                        String strRequestStatusId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_SELECT_REQUEST_STATUS);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_SELECT_REQUEST_STATUS, strRequestStatusId);

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String channleName = SchemaDAO.getChannelName(channelID, con);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, channleName);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        Vector vetRequestList = RequestDAO.getRequestsByFilter(con, strRequestId, strDcmId, strRequestCreationDateFrom, strRequestCreationDateTo, strRequestStatusId, channelID, warehouseId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vetRequestList);
                        Vector vecRequestStatusList = RequestDAO.getAllRequestStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecRequestStatusList);
                        String strLevel = "";
                        //if(channelID.compareTo("1")==0)
                        strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        //else if (channelID.compareTo("2")==0)
                        //{strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;}     
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmDto);
                        Vector warehouseVec = RequestDAO.getWarehouseByChannel(con, channelID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, warehouseVec);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SEARCH_REQUEST_CHANGE_TO_PAID);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case ASSIGN_EQUATION_TO_PRODUCTS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        EquationDTO equationDTO = new EquationDTO();
                        equationDTO.setVecEquation(EquationDAO.getActiveEquations(con));
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, equationDTO);
                        String warehouseId = "";
                        Vector vecProductStock = SchemaDAO.getLatestStockProductsMatchActiveSchemaProducts(channelId, warehouseId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecProductStock);

                        Vector equationProductStock = EquationDAO.getAllEquationProductStock(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, equationProductStock);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case SAVE_ASSIGN_EQUATION_TO_PRODUCTS:
                    try {
                        dataHashMap = new HashMap();
                        EquationDAO.deleteEquationProductStock(con);
                        for (int j = 0; j < paramHashMap.size(); j++) {
                            try {
                                String tempStatusString = (String) paramHashMap.keySet().toArray()[j];
                                System.out.println("4567 tempStatusString isss " + tempStatusString);
                                if (!(paramHashMap.get(tempStatusString) instanceof String)) {
                                    continue;
                                }
                                String keyValue = (String) paramHashMap.get(tempStatusString);
                                if (tempStatusString.startsWith(SOPInterfaceKey.INPUT_SELECT_EQUATION_ID) && keyValue.compareTo("") != 0) {
                                    int strlength = SOPInterfaceKey.INPUT_SELECT_EQUATION_ID.length() + 1;
                                    String strProductCode = tempStatusString.substring(strlength, tempStatusString.length());
                                    Long lEquationProductStockID = Utility.getSequenceNextVal(con, "SEQ_SOP_EQUATION_PRODUCT_STOCK");
                                    EquationDAO.insertEquationProductStock(con, lEquationProductStockID, keyValue, strProductCode);
                                }
                            } catch (Exception e) {
                                System.out.println("Exception in line 4567");
                                e.printStackTrace();
                            }
                        }
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String strChannelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, strChannelID);
                        Vector vecEquationStatus = EquationDAO.getAllEquationStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecEquationStatus);
                        Vector vecEquations = EquationDAO.getAllEquations(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecEquations);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case DCM_PRODUCT_LIMITS_BY_EQUATION:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_CHANNEL_DISTRIBUTION;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);

                        /*
                         * java.util.Date currentDate = new java.util.Date();
                         * int currentYear = currentDate.getYear()+1900; int
                         * currentMonth = currentDate.getMonth()+1; int
                         * currentDay = currentDate.getDate(); String
                         * strCurrentDate =
                         * currentMonth+"/"+currentDay+"/"+currentYear;
                         *
                         * Vector vecProductStock =
                         * SchemaDAO.getProductStockByDate(con,strCurrentDate,strCurrentDate,"","");
                         * dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,vecProductStock);
                         */
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case SAVE_DCM_PRODUCT_LIMITS_BY_EQUATION:
                    try {
                        dataHashMap = new HashMap();
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        Vector dcmList = new Vector();
                        Vector productList = new Vector();
                        for (int k = 0; k < paramHashMap.size(); k++) {
                            try {
                                String tempStatusString = (String) paramHashMap.keySet().toArray()[k];
                                System.out.println("4643 tempStatusString isss " + tempStatusString);
                                if (!(paramHashMap.get(tempStatusString) instanceof String)) {
                                    continue;
                                }
                                String keyValue = (String) paramHashMap.get(tempStatusString);
                                if (tempStatusString.startsWith(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID)) {
                                    int strlength = SOPInterfaceKey.INPUT_HIDDEN_DCM_ID.length() + 1;
                                    String strDcmId = tempStatusString.substring(strlength, tempStatusString.length());
                                    dcmList.add(strDcmId);
                                } else if (tempStatusString.startsWith(SOPInterfaceKey.INPUT_SELECT_PRODUCT_CODE)) {
                                    int strlength = SOPInterfaceKey.INPUT_SELECT_PRODUCT_CODE.length() + 1;
                                    String productCode = tempStatusString.substring(strlength, tempStatusString.length());
                                    SchemaDAO.updateProductEquationCalculate(productCode, "1");
                                    productList.add(productCode);
                                }
                            } catch (Exception e) {
                                System.out.println("Exception in line 4643");
                                e.printStackTrace();
                            }
                        }

                        //HashMap hashMapProductActiveAmount = new HashMap();
                        HashMap hashMapDcmQuota = new HashMap();
                        for (int h = 0; h < productList.size(); h++) {
                            String productCode = (String) productList.get(h);
                            String activeAmount = "";
                            EquationProductStockModel equationProductStockModel = EquationDAO.getEquationProductStockByProductCoed(con, productCode);
                            String equationId = equationProductStockModel.getEquationId();

                            Vector equationDetails = EquationDAO.getEquationDetails(con, equationId);
                            boolean dcmTotalQuotaFlag = false;
                            for (int j = 0; j < equationDetails.size(); j++) {
                                EquationElementModel equationElementModel = (EquationElementModel) equationDetails.get(j);
                                int elementType = equationElementModel.getEquationElementType();
                                String strElementType = elementType + "";
                                if (strElementType.compareTo(SOPInterfaceKey.CONST_EQUATION_OBJECT_TYPE_TERM) == 0) {
                                    String termId = equationElementModel.getEquationElementValueId();
                                    EquationTermModel equationTermModel = EquationDAO.getEquationTermByTermId(con, termId);
                                    String equationTermName = equationTermModel.getEquationTermName();
                                    int isFromDataView = equationTermModel.getIsFromDataView();
                                    if (isFromDataView == 1) {
                                        String dataViewId = equationTermModel.getDataViewId();
                                        int intDataViewId = Integer.parseInt(dataViewId);
                                        QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine();
                                        Vector vec = new Vector();
                                        QueryDTO queryDTO = queryBuilderEngine.loadQueryDTO(con, intDataViewId, vec);
                                        String strQuery = queryBuilderEngine.constructQuerySQL(queryDTO);
                                        if (equationTermName.compareTo("DCM_QUOTA") == 0) {
                                            if (dcmList != null) {
                                                for (int i = 0; i < dcmList.size(); i++) {
                                                    String dcmId = (String) dcmList.get(i);
                                                    String strQueryGetDcmQuota = "";
                                                    strQueryGetDcmQuota += "select " + equationTermName + " from (" + strQuery + ") ";
                                                    strQueryGetDcmQuota += " where DCM_ID = " + dcmId + " ";
                                                    String dcmQuota = EquationDAO.executeQueryString(con, strQueryGetDcmQuota);
                                                    Utility.logger.debug("DCM ID number = " + dcmId + " DCM Quota : " + dcmQuota);
                                                    hashMapDcmQuota.put(dcmId, dcmQuota);
                                                }
                                            }
                                        } else if (equationTermName.compareTo("ACTIVE_AMOUNT") == 0) {
                                            String strQueryGetDcmQuota = "";
                                            strQueryGetDcmQuota += "select " + equationTermName + " from (" + strQuery + ") ";
                                            strQueryGetDcmQuota += " where PRODUCT_CODE = '" + productCode + "' ";
                                            activeAmount = EquationDAO.executeQueryString(con, strQueryGetDcmQuota);
                                            Utility.logger.debug("Product Code = " + productCode + " Active Amount : " + activeAmount);
                                            //hashMapProductActiveAmount.put(productCode,activeAmount);
                                        }
                                    } else if (isFromDataView == 0) {
                                        if (equationTermName.compareTo("DCM_TOTAL_QUOTAS") == 0) {
                                            dcmTotalQuotaFlag = true;
                                        }
                                    }
                                }
                            }
                            int dcmTotalQuotas = 0;
                            if (dcmTotalQuotaFlag) {
                                for (int q = 0; q < hashMapDcmQuota.size(); q++) {
                                    try {
                                        String strHashMapDcmId = (String) hashMapDcmQuota.keySet().toArray()[q];
                                        System.out.println("4741 tempStatusString isss " + strHashMapDcmId);
                                        if (!(paramHashMap.get(strHashMapDcmId) instanceof String)) {
                                            continue;
                                        }
                                        String strHashMapDcmQuota = (String) hashMapDcmQuota.get(strHashMapDcmId);
                                        int intHashMapDcmQuota = 0;
                                        if (strHashMapDcmQuota.compareTo("") != 0 && strHashMapDcmQuota != null) {
                                            intHashMapDcmQuota = Integer.parseInt(strHashMapDcmQuota);
                                        }
                                        dcmTotalQuotas += intHashMapDcmQuota;
                                    } catch (Exception e) {
                                        System.out.println("Exception in line 4741");
                                        e.printStackTrace();
                                    }
                                }
                            }

                            EquationDAO.updateDcmProductRequestLimitsByEquation(con, productCode, equationDetails, hashMapDcmQuota, dcmTotalQuotas, activeAmount);
                        }

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case DCM_PRODUCT_LIMITS_BY_EQUATION_SELECT_PRODUCTS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);

                        java.util.Date currentDate = new java.util.Date();
                        int currentYear = currentDate.getYear() + 1900;
                        int currentMonth = currentDate.getMonth() + 1;
                        int currentDay = currentDate.getDate();
                        String strCurrentDate = currentMonth + "/" + currentDay + "/" + currentYear;
                        String warehouseId = "";
                        Vector vecProductStock = SchemaDAO.getProductStockByDate(con, strCurrentDate, strCurrentDate, "", "", channelId, warehouseId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecProductStock);

                        Vector vecDcmList = new Vector();
                        for (int j = 0; j < paramHashMap.size(); j++) {
                            try {
                                String tempStatusString = (String) paramHashMap.keySet().toArray()[j];
                                System.out.println("4804 tempStatusString isss " + tempStatusString);
                                if (!(paramHashMap.get(tempStatusString) instanceof String)) {
                                    continue;
                                }
                                String keyValue = (String) paramHashMap.get(tempStatusString);
                                if (tempStatusString.startsWith(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID)) {
                                    int strlength = SOPInterfaceKey.INPUT_HIDDEN_DCM_ID.length() + 1;
                                    String strDcmId = tempStatusString.substring(strlength, tempStatusString.length());
                                    vecDcmList.add(strDcmId);
                                }
                            } catch (Exception e) {
                                System.out.println("Exception in line 4804");
                                e.printStackTrace();
                            }
                        }
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecDcmList);

                        Vector prdouctsNotCalculated = SchemaDAO.getProductEquationCalculate("0");
                        Vector prdouctsNotCalculatedAndHaveEquations = new Vector();
                        for (int i = 0; i < prdouctsNotCalculated.size(); i++) {
                            String productCode = (String) prdouctsNotCalculated.get(i);
                            String strProductCode = SchemaDAO.getProductHaveEquation(productCode);
                            if (strProductCode.compareTo("") != 0) {
                                prdouctsNotCalculatedAndHaveEquations.add(productCode);
                            }
                        }
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, prdouctsNotCalculatedAndHaveEquations);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case GET_DCM_REQUEST:
                    try {
                        dataHashMap = new HashMap();
                        String strDCMCode = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_CODE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_DCM_CODE, strDCMCode);
                        Vector vecRequests = RequestDAO.getActiveOrInactiveRequestByDCMCode(con, strDCMCode, "");
                        if (vecRequests.size() > 0) {
                            RequestModel requestModel = (RequestModel) vecRequests.get(0);
                            String requestId = requestModel.getRequestId();
                            Vector vecRequestDetail = RequestDAO.getRequestDetails(con, requestId);
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecRequestDetail);
                            String dcmName = requestModel.getDcmName();
                            dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME, dcmName);
                            String requestCreationDate = requestModel.getCreationDate();
                            dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_CREATION_DATE, requestCreationDate);
                            String requestStatus = requestModel.getRequestStatusName();
                            dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_STATUS, requestStatus);
                        } else {
                            String strDCMName = RequestDAO.getDCMName(con, strDCMCode);
                            dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME, strDCMName);
                        }
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case FIXED_QUOTA_VALUES:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        //if(channelID.compareTo("1")==0)
                        strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        //else if (channelID.compareTo("2")==0)
                        //{strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;} 
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case FIXED_QUOTA_VALUES_FOR_FRANCHISE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_CHANNEL_FRANCHISE;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;



                case FIXED_QUOTA_VALUES_FOR_COMPLEMANTRY:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_COMPLEMENTARY_CHANNEL;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("3") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case FIXED_QUOTA_VALUES_FOR_AUTHORIZED_AGENT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_AUTHORIZED_AGENT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("3") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("4") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SAVE_FIXED_QUOTA_VALUES:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String strLevel = "";
                        if (channelID.compareTo("1") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        } else if (channelID.compareTo("2") == 0) {
                            strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        }
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, dcmDto);

                        String strQuotaValue = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA);

                        for (int j = 0; j < paramHashMap.size(); j++) {
                            try {
                                String tempStatusString = (String) paramHashMap.keySet().toArray()[j];
                                System.out.println("4991 tempStatusString isss " + tempStatusString);
                                if (!(paramHashMap.get(tempStatusString) instanceof String)) {
                                    continue;
                                }
                                String keyValue = (String) paramHashMap.get(tempStatusString);
                                if (tempStatusString.startsWith(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID)) {
                                    int strlength = SOPInterfaceKey.INPUT_HIDDEN_DCM_ID.length() + 1;
                                    String strDCMId = tempStatusString.substring(strlength, tempStatusString.length());

                                    String strCurrentDCMQuota = QuotaDAO.selectCurrentFixedDcmQuota(con, strDCMId);
                                    if (strCurrentDCMQuota.compareTo("") != 0) {
                                        QuotaDAO.updateFixedDcmQuota(con, strDCMId, strQuotaValue);
                                    } else {
                                        DCMQuotaModel dcmQuotaSettingModel = QuotaDAO.getDcmQuotaSetting(con, strDCMId);
                                        if (dcmQuotaSettingModel != null) {
                                            int intValidDays = dcmQuotaSettingModel.getValidDays();
                                            Long lDCMQuotaID = Utility.getSequenceNextVal(con, "SEQ_SOP_DCM_QUOTA_ID");
                                            QuotaDAO.insertFixedDcmQuota(con, lDCMQuotaID, strDCMId, strQuotaValue, intValidDays);
                                        }
                                    }
                                }

                                dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Fixed Quota values added successfully.");
                            } catch (Exception e) {
                                System.out.println("Exception in line 4991");
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case REQUEST_NOTIFICATION_MANAGEMENT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        System.out.println("The channel id isssssssssssss " + channelID);
                        Vector requestNotificationList = RequestDAO.getAllRequestNotification(con, channelID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, requestNotificationList);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case REQUEST_NOTIFICATION_MANAGEMENT_FOR_FRANCHISE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_CHANNEL_FRANCHISE;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        Vector requestNotificationList = RequestDAO.getAllRequestNotification(con, channelID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, requestNotificationList);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case REQUEST_NOTIFICATION_MANAGEMENT_FOR_COMPLEMENTARY:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_COMPLEMENTARY_CHANNEL;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        Vector requestNotificationList = RequestDAO.getAllRequestNotification(con, channelID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, requestNotificationList);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case REQUEST_NOTIFICATION_MANAGEMENT_FOR_AUTHORIZED_AGENT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = SOPInterfaceKey.CONST_GEN_AUTHORIZED_AGENT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        Vector requestNotificationList = RequestDAO.getAllRequestNotification(con, channelID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, requestNotificationList);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case EDIT_REQUEST_NOTIFICATION:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);

                        String strRequestNotificationId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_NOTIFICATION_ID);
                        RequestNotificationModel requestNotificationModel = RequestDAO.getRequestNotificationById(con, strRequestNotificationId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, requestNotificationModel);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case DELETE_REQUEST_NOTIFICATION:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        System.out.println("Medhattttttttttttttttttttttttt");
                        String strRequestNotificationId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_NOTIFICATION_ID);
                        RequestDAO.deleteRequestNotification(con, strRequestNotificationId, channelID);

                        Vector requestNotificationList = RequestDAO.getAllRequestNotification(con, channelID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, requestNotificationList);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case CREATE_REQUEST_NOTIFICATION:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);

                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case SAVE_REQUEST_NOTIFICATION:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);

                        String strRequestNotificationId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_NOTIFICATION_ID);
                        String strPersonFullName = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PERSON_FULL_NAME);
                        String strPersonEmail = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PERSON_EMAIL);

                        if (strRequestNotificationId.compareTo("") == 0) {
                            RequestDAO.insertRequestNotification(con, strPersonFullName, strPersonEmail, channelID);
                        } else {
                            RequestDAO.updateRequestNotification(con, strRequestNotificationId, strPersonFullName, strPersonEmail, channelID);
                        }

                        Vector requestNotificationList = RequestDAO.getAllRequestNotification(con, channelID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, requestNotificationList);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case QUOTA_CALCULATION_BY_DATAVIEW:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

                        QueryDTO queryDTO;
                        QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine();
                        dataHashMap = new HashMap();
                        Vector vecDataViewsList = queryBuilderEngine.listDataViews(con, QueryBuilderInterfaceKey.UNIVERSE_TYPE_DCM_QUOTA_CALCULATION);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecDataViewsList);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;
                case DELETE_IMPORTED_PRODUCTS_FOR_DISTRIBUTORS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        String warehosueId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, channelId);
                        boolean checkDelete = SchemaDAO.deleteImportedProductsForDistributors(con, channelId, warehosueId);
                        if (checkDelete) {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Imported Products deleted successfully.");
                        } else {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "There is no imported products for today.");
                        }
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case DELETE_IMPORTED_PRODUCTS_FOR_FRANCHISE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = SOPInterfaceKey.CONST_GEN_CHANNEL_FRANCHISE;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String warehouseId = "";
                        boolean checkDelete = SchemaDAO.deleteImportedProductsForDistributors(con, channelId, warehouseId);
                        if (checkDelete) {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Imported Products deleted successfully.");
                        } else {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "There is no imported products for today.");
                        }
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case DELETE_IMPORTED_PRODUCTS_FOR_COMPLEMANTRY:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = SOPInterfaceKey.CONST_GEN_COMPLEMENTARY_CHANNEL;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String warehouseId = "";
                        boolean checkDelete = SchemaDAO.deleteImportedProductsForDistributors(con, channelId, warehouseId);
                        if (checkDelete) {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Imported Products deleted successfully.");
                        } else {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "There is no imported products for today.");
                        }
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case DELETE_IMPORTED_PRODUCTS_FOR_AUTHORIZED_AGENT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = SOPInterfaceKey.CONST_GEN_AUTHORIZED_AGENT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String warehouseId = "";
                        boolean checkDelete = SchemaDAO.deleteImportedProductsForDistributors(con, channelId, warehouseId);
                        if (checkDelete) {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Imported Products deleted successfully.");
                        } else {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "There is no imported products for today.");
                        }
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;


                case IMPORT_LCS_PRODUCTS_PHYSICAL_AMOUNT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        java.util.Date currentDate = new java.util.Date();

                        int currentYear = currentDate.getYear() + 1900;
                        int currentMonth = currentDate.getMonth() + 1;
                        int currentDay = currentDate.getDate();

                        String importDateFrom = currentMonth + "/" + currentDay + "/" + currentYear;
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        Vector stockProducts = SchemaDAO.getProductStockByDate(con, importDateFrom, "*", "", "", channelId, warehouseId);
                        Vector vecRequest = RequestDAO.getActiveOrInactiveRequest(con, channelId, warehouseId);
                        if (vecRequest.size() == 0) {
                            for (int i = 0; i < stockProducts.size(); i++) {
                                ProductModel productModel = (ProductModel) stockProducts.get(i);
                                String productCode = productModel.getLcsProductCode();
                                String importDate = productModel.getImportDate();
                                String schemaId = productModel.getSchemaId();
                                importDate = importDate.substring(0, 10);
                                String physicalAmount = productModel.getPhysicalAmount();
                                String lcsPhysicalAmount = SchemaDAO.getLCSProductPhysicalAmount(con, productCode, channelId);
                                RequestDAO.updateProductStockPhysicalAmount(con, productCode, importDate, lcsPhysicalAmount, channelId, warehouseId);
                                RequestDAO.updateProductRequestLimits(con, productCode, lcsPhysicalAmount, schemaId);
                            }
                        } else {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Could not import because there are active or rejected requests.");
                        }
                    } catch (Exception objExp) {
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Failed to import.");
                        objExp.printStackTrace();
                    }
                    break;

                case IMPORT_LCS_PRODUCTS_PHYSICAL_AMOUNT_FOR_FRANCHISE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = SOPInterfaceKey.CONST_GEN_CHANNEL_FRANCHISE;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        java.util.Date currentDate = new java.util.Date();

                        int currentYear = currentDate.getYear() + 1900;
                        int currentMonth = currentDate.getMonth() + 1;
                        int currentDay = currentDate.getDate();

                        String importDateFrom = currentMonth + "/" + currentDay + "/" + currentYear;
                        String warehouseId = "";
                        Vector stockProducts = SchemaDAO.getProductStockByDate(con, importDateFrom, "*", "", "", channelId, warehouseId);
                        /*
                         * Add channel id in the function to get the request for
                         * a fixed channel id
                         */
                        Vector vecRequest = RequestDAO.getActiveOrInactiveRequest(con, channelId, warehouseId);
                        if (vecRequest.size() == 0) {
                            for (int i = 0; i < stockProducts.size(); i++) {
                                ProductModel productModel = (ProductModel) stockProducts.get(i);
                                String productCode = productModel.getLcsProductCode();
                                String importDate = productModel.getImportDate();
                                String schemaId = productModel.getSchemaId();
                                importDate = importDate.substring(0, 10);
                                String physicalAmount = productModel.getPhysicalAmount();
                                String lcsPhysicalAmount = SchemaDAO.getLCSProductPhysicalAmount(con, productCode, channelId);
                                /*
                                 * update the product stock with channel id
                                 */
                                RequestDAO.updateProductStockPhysicalAmount(con, productCode, importDate, lcsPhysicalAmount, channelId, warehouseId);
                                /*
                                 * update product request limit by channel id
                                 * too
                                 */
                                RequestDAO.updateProductRequestLimits(con, productCode, lcsPhysicalAmount, schemaId);
                            }
                        } else {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Could not import because there are active or rejected requests.");
                        }
                    } catch (Exception objExp) {
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Failed to import.");
                        objExp.printStackTrace();
                    }
                    break;


                case IMPORT_LCS_PRODUCTS_PHYSICAL_AMOUNT_FOR_COMPLEMANTRY:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = SOPInterfaceKey.CONST_GEN_COMPLEMENTARY_CHANNEL;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        java.util.Date currentDate = new java.util.Date();

                        int currentYear = currentDate.getYear() + 1900;
                        int currentMonth = currentDate.getMonth() + 1;
                        int currentDay = currentDate.getDate();

                        String importDateFrom = currentMonth + "/" + currentDay + "/" + currentYear;
                        String warehouseId = "";
                        Vector stockProducts = SchemaDAO.getProductStockByDate(con, importDateFrom, "*", "", "", channelId, warehouseId);
                        /*
                         * Add channel id in the function to get the request for
                         * a fixed channel id
                         */

                        Vector vecRequest = RequestDAO.getActiveOrInactiveRequest(con, channelId, warehouseId);
                        if (vecRequest.size() == 0) {
                            for (int i = 0; i < stockProducts.size(); i++) {
                                ProductModel productModel = (ProductModel) stockProducts.get(i);
                                String productCode = productModel.getLcsProductCode();
                                String importDate = productModel.getImportDate();
                                String schemaId = productModel.getSchemaId();
                                importDate = importDate.substring(0, 10);
                                String physicalAmount = productModel.getPhysicalAmount();
                                String lcsPhysicalAmount = SchemaDAO.getLCSProductPhysicalAmount(con, productCode, channelId);
                                /*
                                 * update the product stock with channel id
                                 */
                                RequestDAO.updateProductStockPhysicalAmount(con, productCode, importDate, lcsPhysicalAmount, channelId, warehouseId);
                                /*
                                 * update product request limit by channel id
                                 * too
                                 */
                                RequestDAO.updateProductRequestLimits(con, productCode, lcsPhysicalAmount, schemaId);
                            }
                        } else {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Could not import because there are active or rejected requests.");
                        }
                    } catch (Exception objExp) {
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Failed to import.");
                        objExp.printStackTrace();
                    }
                    break;

                case IMPORT_LCS_PRODUCTS_PHYSICAL_AMOUNT_FOR_AUTHORIZED_AGENT:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = SOPInterfaceKey.CONST_GEN_AUTHORIZED_AGENT;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        java.util.Date currentDate = new java.util.Date();

                        int currentYear = currentDate.getYear() + 1900;
                        int currentMonth = currentDate.getMonth() + 1;
                        int currentDay = currentDate.getDate();

                        String importDateFrom = currentMonth + "/" + currentDay + "/" + currentYear;
                        String warehouseId = "";
                        Vector stockProducts = SchemaDAO.getProductStockByDate(con, importDateFrom, "*", "", "", channelId, warehouseId);
                        /*
                         * Add channel id in the function to get the request for
                         * a fixed channel id
                         */
                        Vector vecRequest = RequestDAO.getActiveOrInactiveRequest(con, channelId, warehouseId);
                        if (vecRequest.size() == 0) {
                            for (int i = 0; i < stockProducts.size(); i++) {
                                ProductModel productModel = (ProductModel) stockProducts.get(i);
                                String productCode = productModel.getLcsProductCode();
                                String importDate = productModel.getImportDate();
                                String schemaId = productModel.getSchemaId();
                                importDate = importDate.substring(0, 10);
                                String physicalAmount = productModel.getPhysicalAmount();
                                String lcsPhysicalAmount = SchemaDAO.getLCSProductPhysicalAmount(con, productCode, channelId);
                                /*
                                 * update the product stock with channel id
                                 */
                                RequestDAO.updateProductStockPhysicalAmount(con, productCode, importDate, lcsPhysicalAmount, channelId, warehouseId);
                                /*
                                 * update product request limit by channel id
                                 * too
                                 */
                                RequestDAO.updateProductRequestLimits(con, productCode, lcsPhysicalAmount, schemaId);
                            }
                        } else {
                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Could not import because there are active or rejected requests.");
                        }

                    } catch (Exception objExp) {
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Failed to import.");
                        objExp.printStackTrace();

                    }
                    break;

                case CALCULATE_QUOTA_BY_DATAVIEW:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

                        String strMsg = QuotaDAO.dcmQuotaCalculationsChecking(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, strMsg);
                    } catch (Exception objExp) {
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Failed to calculate quota.");
                        objExp.printStackTrace();
                    }
                    break;
                //COMMENT PLEASE !
                case SEARCH_FRANCHISE_STATE: {
                    try {
                        dataHashMap = new HashMap();
                        String temp = null;
                        FranchiseModel model = new FranchiseModel();
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setCode("Any");
                        } else {
                            model.setCode(temp);
                        }
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_ITEM_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setItemCode("Any");
                        } else {
                            model.setItemCode(temp);
                        }
                        int rowNum = 0;
                        int count = FranchiseDAO.countOfFranchise(con, model);
                        Vector result = FranchiseDAO.searchFranchiseByRowNum(con, rowNum, model);
                        //Vector result = FranchiseDAO.searchFranchise(con, model);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_COUNT, "" + count);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, result);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_FRANCHISE_SEARCH_MODEL, model);
                    } catch (Exception e) {
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Failed to get franchise state.");
                        e.printStackTrace();
                    }
                }
                break;

                case EXCEL_FRANCHISE_STATE: {
                    try {
                        dataHashMap = new HashMap();
                        String temp = null;
                        FranchiseModel model = new FranchiseModel();
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setCode("Any");
                        } else {
                            model.setCode(temp);
                        }
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_ITEM_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setItemCode("Any");
                        } else {
                            model.setItemCode(temp);
                        }
                        //Vector result = FranchiseDAO.searchFranchiseByRowNum(con, rowNum,model);   
                        Vector result = FranchiseDAO.searchFranchise(con, model);
                        //dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM,""+rowNum);
                        //dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_COUNT,""+count);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, result);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_FRANCHISE_SEARCH_MODEL, model);
                    } catch (Exception e) {
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Failed to get franchise state.");
                        e.printStackTrace();
                    }
                }
                break;

                case NEXT_FRANCHISE_STATE: {
                    try {
                        dataHashMap = new HashMap();
                        String temp = null;
                        FranchiseModel model = new FranchiseModel();
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setCode("Any");
                        } else {
                            model.setCode(temp);
                        }
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_ITEM_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setItemCode("Any");
                        } else {
                            model.setItemCode(temp);
                        }
                        int count = Integer.parseInt((String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_COUNT));
                        int rowNum = Integer.parseInt((String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        rowNum = rowNum + 1;
                        Vector result = FranchiseDAO.searchFranchiseByRowNum(con, rowNum, model);
                        //Vector result = FranchiseDAO.searchFranchise(con, model);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_COUNT, "" + count);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, result);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_FRANCHISE_SEARCH_MODEL, model);
                    } catch (Exception e) {
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Failed to get franchise state.");
                        e.printStackTrace();
                    }
                }
                break;

                case PREVIOUS_FRANCHISE_STATE: {
                    try {
                        dataHashMap = new HashMap();
                        String temp = null;
                        FranchiseModel model = new FranchiseModel();
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setCode("Any");
                        } else {
                            model.setCode(temp);
                        }
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_ITEM_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setItemCode("Any");
                        } else {
                            model.setItemCode(temp);
                        }
                        int count = Integer.parseInt((String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_COUNT));
                        int rowNum = Integer.parseInt((String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        rowNum = rowNum - 1;
                        Vector result = FranchiseDAO.searchFranchiseByRowNum(con, rowNum, model);
                        //Vector result = FranchiseDAO.searchFranchise(con, model);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_COUNT, "" + count);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, result);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_FRANCHISE_SEARCH_MODEL, model);
                    } catch (Exception e) {
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Failed to get franchise state.");
                        e.printStackTrace();
                    }
                }
                break;

                case SEARCH_LCS_STATE: {
                    try {
                        dataHashMap = new HashMap();
                        String temp = null;
                        String startDate = null, endDate = null;
                        InformationModel model = new InformationModel();
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setFranchiseCode("Any");
                        } else {
                            model.setFranchiseCode(temp);
                        }
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_ITEM_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setItemCode("Any");
                        } else {
                            model.setItemCode(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_LCS_ITEM_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setSourceItemCode("Any");
                        } else {
                            model.setSourceItemCode(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_LCS_ITEM_DESCRIPTION);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setSourceItemDesc("Any");
                        } else {
                            model.setSourceItemDesc(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_START_DATE).toString();
                        if (temp == null || temp.trim().length() == 0) {
                            startDate = "Any";
                        } else {
                            startDate = temp;
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_END_DATE).toString();
                        if (temp == null || temp.trim().length() == 0) {
                            endDate = "Any";
                        } else {
                            endDate = temp;
                        }
                        int rowNum = 0;
                        int count = FranchiseDAO.countOfLCSState(con, model, startDate, endDate);
                        Vector result = FranchiseDAO.searchLCSInformationByRowNum(con, rowNum, model, startDate, endDate);
                        //Vector result = FranchiseDAO.searchLCSInformation(con, model,startDate, endDate);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_COUNT, "" + count);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, result);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_LCS_SEARCH_MODEL, model);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_START_DATE, startDate);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_END_DATE, endDate);
                    } catch (Exception e) {
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Failed to get lcs state.");
                        e.printStackTrace();
                    }
                }
                break;

                case EXCEL_LCS_STATE: {
                    try {
                        dataHashMap = new HashMap();
                        String temp = null;
                        String startDate = null, endDate = null;
                        InformationModel model = new InformationModel();
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setFranchiseCode("Any");
                        } else {
                            model.setFranchiseCode(temp);
                        }
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_ITEM_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setItemCode("Any");
                        } else {
                            model.setItemCode(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_LCS_ITEM_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setSourceItemCode("Any");
                        } else {
                            model.setSourceItemCode(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_LCS_ITEM_DESCRIPTION);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setSourceItemDesc("Any");
                        } else {
                            model.setSourceItemDesc(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_START_DATE).toString();
                        if (temp == null || temp.trim().length() == 0) {
                            startDate = "Any";
                        } else {
                            startDate = temp;
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_END_DATE).toString();
                        if (temp == null || temp.trim().length() == 0) {
                            endDate = "Any";
                        } else {
                            endDate = temp;
                        }
                        //Vector result = FranchiseDAO.searchLCSInformationByRowNum(con, rowNum,model,startDate, endDate);
                        Vector result = FranchiseDAO.searchLCSInformation(con, model, startDate, endDate);
                        //dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM,""+rowNum);
                        //dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_COUNT,""+count);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, result);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_LCS_SEARCH_MODEL, model);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_START_DATE, startDate);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_END_DATE, endDate);
                    } catch (Exception e) {
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Failed to get lcs state.");
                        e.printStackTrace();
                    }
                }
                break;

                case NEXT_LCS_STATE: {
                    try {
                        dataHashMap = new HashMap();
                        String temp = null;
                        String startDate = null, endDate = null;
                        InformationModel model = new InformationModel();
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setFranchiseCode("Any");
                        } else {
                            model.setFranchiseCode(temp);
                        }
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_ITEM_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setItemCode("Any");
                        } else {
                            model.setItemCode(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_LCS_ITEM_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setSourceItemCode("Any");
                        } else {
                            model.setSourceItemCode(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_LCS_ITEM_DESCRIPTION);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setSourceItemDesc("Any");
                        } else {
                            model.setSourceItemDesc(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_START_DATE).toString();
                        if (temp == null || temp.trim().length() == 0) {
                            startDate = "Any";
                        } else {
                            startDate = temp;
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_END_DATE).toString();
                        if (temp == null || temp.trim().length() == 0) {
                            endDate = "Any";
                        } else {
                            endDate = temp;
                        }
                        int rowNum = Integer.parseInt((String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        int count = Integer.parseInt((String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_COUNT));
                        rowNum = rowNum + 1;
                        Vector result = FranchiseDAO.searchLCSInformationByRowNum(con, rowNum, model, startDate, endDate);
                        //Vector result = FranchiseDAO.searchLCSInformation(con, model,startDate, endDate);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_COUNT, "" + count);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, result);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_LCS_SEARCH_MODEL, model);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_START_DATE, startDate);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_END_DATE, endDate);
                    } catch (Exception e) {
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Failed to get lcs state.");
                        e.printStackTrace();
                    }
                }
                break;

                case PREVIOUS_LCS_STATE: {
                    try {
                        dataHashMap = new HashMap();
                        String temp = null;
                        String startDate = null, endDate = null;
                        InformationModel model = new InformationModel();
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setFranchiseCode("Any");
                        } else {
                            model.setFranchiseCode(temp);
                        }
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_ITEM_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setItemCode("Any");
                        } else {
                            model.setItemCode(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_LCS_ITEM_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setSourceItemCode("Any");
                        } else {
                            model.setSourceItemCode(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_LCS_ITEM_DESCRIPTION);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setSourceItemDesc("Any");
                        } else {
                            model.setSourceItemDesc(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_START_DATE).toString();
                        if (temp == null || temp.trim().length() == 0) {
                            startDate = "Any";
                        } else {
                            startDate = temp;
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_END_DATE).toString();
                        if (temp == null || temp.trim().length() == 0) {
                            endDate = "Any";
                        } else {
                            endDate = temp;
                        }
                        int rowNum = Integer.parseInt((String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        int count = Integer.parseInt((String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_COUNT));
                        rowNum = rowNum - 1;
                        Vector result = FranchiseDAO.searchLCSInformationByRowNum(con, rowNum, model, startDate, endDate);
                        //Vector result = FranchiseDAO.searchLCSInformation(con, model,startDate, endDate);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_COUNT, "" + count);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, result);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_LCS_SEARCH_MODEL, model);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_START_DATE, startDate);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_END_DATE, endDate);
                    } catch (Exception e) {
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Failed to get lcs state.");
                        e.printStackTrace();
                    }
                }
                break;

                case SEARCH_PGW_STATE: {
                    try {
                        dataHashMap = new HashMap();
                        String temp = null;
                        String startDate = null, endDate = null;
                        InformationModel model = new InformationModel();
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setFranchiseCode("Any");
                        } else {
                            model.setFranchiseCode(temp);
                        }
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_ITEM_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setItemCode("Any");
                        } else {
                            model.setItemCode(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_ITEM_NAME);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setSourceItemName("Any");
                        } else {
                            model.setSourceItemName(temp);
                        }


                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PGW_ITEM_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setSourceItemCode("Any");
                        } else {
                            model.setSourceItemCode(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_START_DATE).toString();
                        if (temp == null || temp.trim().length() == 0) {
                            startDate = "Any";
                        } else {
                            startDate = temp;
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_END_DATE).toString();
                        if (temp == null || temp.trim().length() == 0) {
                            endDate = "Any";
                        } else {
                            endDate = temp;
                        }
                        int rowNum = 0;
                        int count = FranchiseDAO.countOfPGWState(con, model, startDate, endDate);
                        //Vector result = FranchiseDAO.searchPGWInformation(con, model,startDate, endDate);
                        Vector result = FranchiseDAO.searchPGWInformationByRowNum(con, rowNum, model, startDate, endDate);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_COUNT, "" + count);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, result);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_PGW_SEARCH_MODEL, model);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_START_DATE, startDate);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_END_DATE, endDate);


                    } catch (Exception e) {
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Failed to get pgw state.");
                        e.printStackTrace();
                    }
                }
                break;

                case EXCEL_PGW_STATE: {
                    try {
                        dataHashMap = new HashMap();
                        String temp = null;
                        String startDate = null, endDate = null;
                        InformationModel model = new InformationModel();
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setFranchiseCode("Any");
                        } else {
                            model.setFranchiseCode(temp);
                        }
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_ITEM_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setItemCode("Any");
                        } else {
                            model.setItemCode(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_ITEM_NAME);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setSourceItemName("Any");
                        } else {
                            model.setSourceItemName(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PGW_ITEM_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setSourceItemCode("Any");
                        } else {
                            model.setSourceItemCode(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_START_DATE).toString();
                        if (temp == null || temp.trim().length() == 0) {
                            startDate = "Any";
                        } else {
                            startDate = temp;
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_END_DATE).toString();
                        if (temp == null || temp.trim().length() == 0) {
                            endDate = "Any";
                        } else {
                            endDate = temp;
                        }
                        //int rowNum = 0;
                        Vector result = FranchiseDAO.searchPGWInformation(con, model, startDate, endDate);
                        //Vector result = FranchiseDAO.searchPGWInformationByRowNum(con, rowNum,model,startDate, endDate);
                        //dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM,""+rowNum);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, result);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_PGW_SEARCH_MODEL, model);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_START_DATE, startDate);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_END_DATE, endDate);


                    } catch (Exception e) {
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Failed to get pgw state.");
                        e.printStackTrace();
                    }
                }
                break;

                case NEXT_PGW_STATE: {
                    try {
                        dataHashMap = new HashMap();
                        String temp = null;
                        String startDate = null, endDate = null;
                        InformationModel model = new InformationModel();
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setFranchiseCode("Any");
                        } else {
                            model.setFranchiseCode(temp);
                        }
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_ITEM_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setItemCode("Any");
                        } else {
                            model.setItemCode(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_ITEM_NAME);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setSourceItemName("Any");
                        } else {
                            model.setSourceItemName(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PGW_ITEM_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setSourceItemCode("Any");
                        } else {
                            model.setSourceItemCode(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_START_DATE).toString();
                        if (temp == null || temp.trim().length() == 0) {
                            startDate = "Any";
                        } else {
                            startDate = temp;
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_END_DATE).toString();
                        if (temp == null || temp.trim().length() == 0) {
                            endDate = "Any";
                        } else {
                            endDate = temp;
                        }
                        int count = Integer.parseInt((String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_COUNT));

                        int rowNum = Integer.parseInt((String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        Utility.logger.debug("The next row num issssssssssssssssssss " + rowNum);
                        //Vector result = FranchiseDAO.searchPGWInformation(con, model,startDate, endDate);
                        rowNum = rowNum + 1;
                        Vector result = FranchiseDAO.searchPGWInformationByRowNum(con, rowNum, model, startDate, endDate);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_COUNT, "" + count);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, result);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_PGW_SEARCH_MODEL, model);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_START_DATE, startDate);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_END_DATE, endDate);


                    } catch (Exception e) {
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Failed to get pgw state.");
                        e.printStackTrace();
                    }
                }
                break;

                case PREVIOUS_PGW_STATE: {
                    try {
                        dataHashMap = new HashMap();
                        String temp = null;
                        String startDate = null, endDate = null;
                        InformationModel model = new InformationModel();
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setFranchiseCode("Any");
                        } else {
                            model.setFranchiseCode(temp);
                        }
                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_ITEM_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setItemCode("Any");
                        } else {
                            model.setItemCode(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_ITEM_NAME);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setSourceItemName("Any");
                        } else {
                            model.setSourceItemName(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PGW_ITEM_CODE);
                        if (temp == null || temp.trim().length() == 0) {
                            model.setSourceItemCode("Any");
                        } else {
                            model.setSourceItemCode(temp);
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_START_DATE).toString();
                        if (temp == null || temp.trim().length() == 0) {
                            startDate = "Any";
                        } else {
                            startDate = temp;
                        }

                        temp = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_END_DATE).toString();
                        if (temp == null || temp.trim().length() == 0) {
                            endDate = "Any";
                        } else {
                            endDate = temp;
                        }

                        int rowNum = Integer.parseInt((String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        Utility.logger.debug("The next row num issssssssssssssssssss " + rowNum);
                        //Vector result = FranchiseDAO.searchPGWInformation(con, model,startDate, endDate);
                        int count = Integer.parseInt((String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_COUNT));
                        rowNum = rowNum - 1;
                        Vector result = FranchiseDAO.searchPGWInformationByRowNum(con, rowNum, model, startDate, endDate);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_COUNT, "" + count);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, result);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_PGW_SEARCH_MODEL, model);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_START_DATE, startDate);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_END_DATE, endDate);


                    } catch (Exception e) {
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Failed to get pgw state.");
                        e.printStackTrace();
                    }
                }
                break;

                case GO_TO_SHOW_FRANCHISE_DETAILS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector vector = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vector);
                        int rowNum = 0;
                        int count = 0;
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_COUNT, "" + count);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;

                case SEARCH_FRANCHISE_DETAILS: {
                    try {
                        dataHashMap = new HashMap();
                        String franchiseCode = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE, franchiseCode);
                        String itemCode = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_ITEM_CODE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_ITEM_CODE, itemCode);
                        String pgwStartDate = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PGW_START_DATE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_PGW_START_DATE, pgwStartDate);
                        String pgwEndDate = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PGW_END_DATE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_PGW_END_DATE, pgwEndDate);
                        String lcsStartDate = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_LCS_START_DATE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_LCS_START_DATE, lcsStartDate);
                        String lcsEndDate = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_LCS_END_DATE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_LCS_END_DATE, lcsEndDate);
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        int rowNum = 0;
                        int count = FranchiseDAO.counfOFFranchiseDetails(con, franchiseCode, itemCode, pgwStartDate, pgwEndDate, lcsStartDate, lcsEndDate);
                        Vector vector = FranchiseDAO.searchFranchiseDetailsByRowNum(con, rowNum, franchiseCode, itemCode, pgwStartDate, pgwEndDate, lcsStartDate, lcsEndDate);
                        //Vector vector = FranchiseDAO.searchFranchiseDetails(con,franchiseCode,itemCode,pgwStartDate,pgwEndDate,lcsStartDate,lcsEndDate);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vector);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_COUNT, "" + count);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SEARCH_FRANCHISE_DETAILS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

                case NEXT_FRANCHISE_DETAILS: {
                    try {
                        dataHashMap = new HashMap();
                        String franchiseCode = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE, franchiseCode);
                        String itemCode = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_ITEM_CODE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_ITEM_CODE, itemCode);
                        String pgwStartDate = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PGW_START_DATE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_PGW_START_DATE, pgwStartDate);
                        String pgwEndDate = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PGW_END_DATE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_PGW_END_DATE, pgwEndDate);
                        String lcsStartDate = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_LCS_START_DATE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_LCS_START_DATE, lcsStartDate);
                        String lcsEndDate = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_LCS_END_DATE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_LCS_END_DATE, lcsEndDate);
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        int rowNum = Integer.parseInt((String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        int count = Integer.parseInt((String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_COUNT));
                        rowNum = rowNum + 1;
                        Vector vector = FranchiseDAO.searchFranchiseDetailsByRowNum(con, rowNum, franchiseCode, itemCode, pgwStartDate, pgwEndDate, lcsStartDate, lcsEndDate);
                        //Vector vector = FranchiseDAO.searchFranchiseDetails(con,franchiseCode,itemCode,pgwStartDate,pgwEndDate,lcsStartDate,lcsEndDate);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vector);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_COUNT, "" + count);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SEARCH_FRANCHISE_DETAILS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

                case PREVIOUS_FRANCHISE_DETAILS: {
                    try {
                        dataHashMap = new HashMap();
                        String franchiseCode = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE, franchiseCode);
                        String itemCode = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_ITEM_CODE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_ITEM_CODE, itemCode);
                        String pgwStartDate = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PGW_START_DATE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_PGW_START_DATE, pgwStartDate);
                        String pgwEndDate = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_PGW_END_DATE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_PGW_END_DATE, pgwEndDate);
                        String lcsStartDate = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_LCS_START_DATE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_LCS_START_DATE, lcsStartDate);
                        String lcsEndDate = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_LCS_END_DATE);
                        dataHashMap.put(SOPInterfaceKey.INPUT_TEXT_LCS_END_DATE, lcsEndDate);
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        int rowNum = Integer.parseInt((String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM));
                        int count = Integer.parseInt((String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_COUNT));
                        rowNum = rowNum - 1;
                        Vector vector = FranchiseDAO.searchFranchiseDetailsByRowNum(con, rowNum, franchiseCode, itemCode, pgwStartDate, pgwEndDate, lcsStartDate, lcsEndDate);
                        //Vector vector = FranchiseDAO.searchFranchiseDetails(con,franchiseCode,itemCode,pgwStartDate,pgwEndDate,lcsStartDate,lcsEndDate);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vector);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM, "" + rowNum);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_COUNT, "" + count);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SEARCH_FRANCHISE_DETAILS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;


                case VIEW_PRODUCT_REPORT: {
                    try {
                        dataHashMap = new HashMap();

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

                        Vector report = RequestDAO.getAllReports(con);
                        Vector channel = RequestDAO.getAllChannels(con);

                        Vector v = new Vector();

                        dataHashMap.put(SOPInterfaceKey.PRODUCT_VECTOR, v);

                        dataHashMap.put(SOPInterfaceKey.PRODUCT_REPORT, report);

                        dataHashMap.put(SOPInterfaceKey.PRODUCT_CHANNEL, channel);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;



                case SUBMIT_PRODUCTS: {
                    try {
                        dataHashMap = new HashMap();

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
                        String reportID = (String) paramHashMap.get(SOPInterfaceKey.CONTROL_HIDDEN_REPORT_ID);
                        dataHashMap.put(SOPInterfaceKey.CONTROL_HIDDEN_REPORT_ID, reportID);

                        dataHashMap.put(SOPInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID, channelID);
                        RequestDAO.deleteProduct(con, reportID, channelID);
                        Vector v = RequestDAO.getAllProducts(con);
                        for (int i = 0; i < v.size(); i++) {
                            genProductModel product_model = (genProductModel) v.get(i);
                            String product_id = product_model.getPRODUCT_NAME();
                            String product_check = (String) paramHashMap.get(SOPInterfaceKey.CONTROL_HIDDEN_PRODUCT_CHECKED + product_id);
                            System.out.println("ddddddddddddd" + product_id);
                            if (product_check != null) {
                                RequestDAO.insertProductReport(con, product_id, reportID, channelID);
                            }

                        }


                        Vector report = RequestDAO.getAllReports(con);
                        Vector channel = RequestDAO.getAllChannels(con);

                        Vector AssignedProduct = RequestDAO.getSpecificProducts(con, reportID, channelID);
                        dataHashMap.put(SOPInterfaceKey.PRODUCT_ASSIGNED_VECTOR, AssignedProduct);
                        dataHashMap.put(SOPInterfaceKey.PRODUCT_VECTOR, v);

                        dataHashMap.put(SOPInterfaceKey.PRODUCT_REPORT, report);

                        dataHashMap.put(SOPInterfaceKey.PRODUCT_CHANNEL, channel);

                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SEARCH_PRODUCTS);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;



                case SEARCH_PRODUCTS: {
                    try {
                        dataHashMap = new HashMap();

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
                        String reportID = (String) paramHashMap.get(SOPInterfaceKey.CONTROL_HIDDEN_REPORT_ID);

                        Vector v = RequestDAO.getAllProducts(con);
                        Vector AssignedProduct = RequestDAO.getSpecificProducts(con, reportID, channelID);




                        Vector report = RequestDAO.getAllReports(con);
                        Vector channel = RequestDAO.getAllChannels(con);


                        dataHashMap.put(SOPInterfaceKey.PRODUCT_ASSIGNED_VECTOR, AssignedProduct);
                        dataHashMap.put(SOPInterfaceKey.PRODUCT_VECTOR, v);

                        dataHashMap.put(SOPInterfaceKey.PRODUCT_REPORT, report);

                        dataHashMap.put(SOPInterfaceKey.PRODUCT_CHANNEL, channel);



                        dataHashMap.put(SOPInterfaceKey.CONTROL_HIDDEN_REPORT_ID, reportID);

                        dataHashMap.put(SOPInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID, channelID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SEARCH_PRODUCTS);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

                case SHOW_ALL_REQUESTS_CHANGE_TO_PAID_BY_CHANNEL:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector channels = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, channels);
                        //Vector warehouseChannelVec = RequestDAO.getChannelWarehouse(con);
                        //dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, warehouseChannelVec);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SHOW_ALL_REQUESTS_CHANGE_TO_PAID);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case FIXED_QUOTA_VALUES_BY_CHANNEL:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector channels = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, channels);
                        //Vector warehouseChannelVec = RequestDAO.getChannelWarehouse(con);
                        //dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, warehouseChannelVec);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_FIXED_QUOTA_VALUES);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case SELECT_DCM_QUOTA_SETTINGS_BY_CHANNEL_SCM:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector channels = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, channels);
                        String levelName = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        Vector scmChannel = RequestDAO.getDcmChannel(con, levelName, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, scmChannel);
                        //Vector warehouseChannelVec = RequestDAO.getChannelWarehouse(con);
                        //dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, warehouseChannelVec);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SET_DCM_QUOTA_SETTING_VALUES);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, "Quota Settings");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case SHOW_ALL_PRODUCTS_PGW_BY_CHANNEL:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector channels = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, channels);
                        //Vector warehouseChannelVec = RequestDAO.getChannelWarehouse(con);
                        //dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, warehouseChannelVec);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SHOW_ALL_PRODUCTS_PGW);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case SHOW_ALL_REQUESTS_BY_CHANNEL:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector channels = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, channels);
                        Vector warehouseChannelVec = RequestDAO.getChannelWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, warehouseChannelVec);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SHOW_ALL_REQUESTS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case VIEW_STOCK_PRODUCTS_BY_CHANNEL_WAREHOUSE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector channels = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, channels);
                        Vector warehouseChannelVec = RequestDAO.getChannelWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, warehouseChannelVec);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_VIEW_STOCK_PRODUCTS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case DELETE_IMPORTED_PRODUCTS_FOR_DISTRIBUTORS_BY_CHANNEL:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector channels = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, channels);
                        Vector warehouseChannelVec = RequestDAO.getChannelWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, warehouseChannelVec);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_DELETE_IMPORTED_PRODUCTS_FOR_DISTRIBUTORS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case IMPORT_LCS_PRODUCTS_PYHSICAL_AMOUNT_BY_CHANNEL_WAREHOUSE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector channels = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, channels);
                        Vector warehouseChannelVec = RequestDAO.getChannelWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, warehouseChannelVec);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_IMPORT_LCS_PRODUCTS_PHYSICAL_AMOUNT);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case REQUEST_NOTIFICATION_MANAGEMENT_BY_CHANNEL:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector channels = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, channels);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_REQUEST_NOTIFICATION_MANAGEMENT);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case SHOW_USER_DCM_SCREEN_SOP_BY_CHANNEL:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector channels = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, channels);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SHOW_USER_DCM_SCREEN_SOP);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case IMPORT_PRODUCTS_BY_CHANNEL:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector channels = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, channels);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_IMPORT_PRODUCTS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case SHOW_ALL_SCHEMAS_BY_CHANNEL:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector channels = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, channels);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SHOW_ALL_SCHEMAS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case SHOW_SCRATCH_REPORT_BY_CHANNEL:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector channels = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, channels);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SHOW_SCRATCH_REPORT);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case SHOW_PRODUCT_REPORT_BY_CHANNEL:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector channels = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, channels);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SHOW_PRODUCT_REPORT);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case SHOW_TOTAL_REPORT_BY_CHANNEL:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector channels = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, channels);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SHOW_TOTAL_REPORT);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case VIEW_WAREHOUSE_CHANNEL:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector warehouseChannel = RequestDAO.getChannelWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, warehouseChannel);
                        Vector channels = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, channels);
                        Vector warehouseVec = RequestDAO.getAllWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, warehouseVec);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case EDIT_WAREHOUSE_CHANNEL:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        WarehouseChannelModel warehouseChannelModel = RequestDAO.selectWarehouseChannel(con, channelId, warehouseId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, warehouseChannelModel);
                        Vector channels = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, channels);
                        Vector warehouseVec = RequestDAO.getAllWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, warehouseVec);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case ADD_WAREHOUSE_CHANNEL:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelId);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        WarehouseChannelModel warehouseChannelModel = null;
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, warehouseChannelModel);
                        Vector channels = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, channels);
                        Vector warehouseVec = RequestDAO.getAllWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, warehouseVec);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case UPDATE_WAREHOUSE_CHANNEL:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String oldChannelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        String oldWarehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        String newChannelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_SELECT_CHANNEL_ID);
                        String newWarehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_SELECT_WAREHOUSE_ID);
                        RequestDAO.updateWarehouseChannel(con, oldChannelId, oldWarehouseId, newChannelId, newWarehouseId);
                        Vector warehouseChannel = RequestDAO.getChannelWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, warehouseChannel);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case SAVE_WAREHOUSE_CHANNEL:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_SELECT_CHANNEL_ID);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_SELECT_WAREHOUSE_ID);
                        RequestDAO.inserWarehouseChannel(con, channelId, warehouseId);
                        Vector warehouseChannel = RequestDAO.getChannelWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, warehouseChannel);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case DELETE_WAREHOUSE_CHANNEL:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        RequestDAO.deleteWarehouseChannel(con, channelId, warehouseId);
                        Vector warehouseChannel = RequestDAO.getChannelWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, warehouseChannel);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case USER_CHANNEL_LIST:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, UserDAO.getUsersList(con));
                        Vector channelVec = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, channelVec);
                        Vector userChannelVec = RequestDAO.selectUserChannel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, userChannelVec);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case UPDATE_USER_CHANNEL_LIST:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        int paramHashMapSize = paramHashMap.size();
//              String groupIdKey = (String) paramHashMap.get(ReportInterfaceKey.PARAM_GROUP_ID) ;
//              int nGroupID = new Integer (groupIdKey).intValue() ;
                        String strPersonID = (String) paramHashMap.get(UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID);
                        RequestDAO.deleteUserChannel(con, strPersonID);
                        for (int i = 0; i < paramHashMapSize; i++) {
                            try {
                                String tempKey = (String) paramHashMap.keySet().toArray()[i];
                                System.out.println("6815 tempStatusString isss " + tempKey);
                                if (!(paramHashMap.get(tempKey) instanceof String)) {
                                    continue;
                                }
                                //System.out.println(paramHashMap.get(tempKey));
//                  String tempValue = (String)paramHashMap.get(tempKey);
                                //Utility.logger.debug("wwwwwww"+tempKey+"-----------------"+tempValue);

                                if (tempKey.startsWith(UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX + strPersonID)) {
                                    String channelIdKey = tempKey.substring((UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX + strPersonID).length());
                                    //Utility.logger.debug(reportId);
                                    RequestDAO.updateUserChannel(con, strPersonID, channelIdKey);
                                }
                            } catch (Exception e) {
                                System.out.println("Exception in line 6815");
                                e.printStackTrace();
                            }
                        }
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, UserDAO.getUsersList(con));
                        Vector channelVec = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, channelVec);
                        Vector userChannelVec = RequestDAO.selectUserChannel(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, userChannelVec);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case REQUEST_ADMIN_CHANGE_STATUS:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector channels = RequestDAO.getAllChannels(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, channels);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_VIEW_REQUEST_STATUS_LIST);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case VIEW_REQUEST_STATUS_LIST:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        System.out.println("The user idddddddddddddddddd isssssssssssssssss " + strUserID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        //String warehouseId = (String)paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        //dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        //Vector vetRequestList = RequestDAO.getAllRequests(con);
                        Vector vetRequestList = new Vector();
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vetRequestList);
                        Vector vecRequestStatusList = RequestDAO.getAllRequestStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecRequestStatusList);
                        String strLevel = "";
                        //if(channelID.compareTo("1")==0)
                        strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        //else if (channelID.compareTo("2")==0)
                        //{strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;}
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmDto);
                        Vector warehouseVec = RequestDAO.getWarehouseByChannel(con, channelID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, warehouseVec);
                        String channleName = SchemaDAO.getChannelName(channelID, con);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, channleName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case SEARCH_REQUEST_STATUS_LIST:
                    try {
                        dataHashMap = new HashMap();
                        //String strRequestCode = (String)paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CODE);
                        //dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CODE,strRequestCode);
                        String strRequestId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_ID, strRequestId);
                        String strDcmId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_SELECT_DCM_NAME);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_SELECT_DCM_NAME, strDcmId);
                        String strRequestCreationDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_FROM, strRequestCreationDateFrom);
                        String strRequestCreationDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_TO, strRequestCreationDateTo);
                        String strRequestStatusId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_SELECT_REQUEST_STATUS);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_SELECT_REQUEST_STATUS, strRequestStatusId);

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        System.out.println("The warehouse id isssssssssssssssss " + warehouseId);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        Vector vetRequestList = RequestDAO.getPaidDeletedRequestsByFilter(con, strRequestId, strDcmId, strRequestCreationDateFrom, strRequestCreationDateTo, strRequestStatusId, channelID, warehouseId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vetRequestList);
                        Vector vecRequestStatusList = RequestDAO.getAllRequestStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecRequestStatusList);
                        String strLevel = "";
                        //if(channelID.compareTo("1")==0)
                        strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        //else if (channelID.compareTo("2")==0)
                        //{strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;}
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SEARCH_REQUEST_STATUS_LIST);
                        Vector warehouseVec = RequestDAO.getWarehouseByChannel(con, channelID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, warehouseVec);
                        String channleName = SchemaDAO.getChannelName(channelID, con);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, channleName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case UPDATE_REQUEST_STATUS_LIST:
                    try {
                        dataHashMap = new HashMap();
                        //////Search values//////////
                        //String strRequestCode = (String)paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CODE);
                        //dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CODE,strRequestCode);
                        String channelID = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID, channelID);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID, warehouseId);
                        System.out.println("Warehouse id issssssssss " + warehouseId);
                        String strRequestId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_ID);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_ID, strRequestId);
                        String strDcmId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_SELECT_DCM_NAME);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_SELECT_DCM_NAME, strDcmId);
                        String strRequestCreationDateFrom = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_FROM);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_FROM, strRequestCreationDateFrom);
                        String strRequestCreationDateTo = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_TO);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_TEXT_REQUEST_CREATION_DATE_TO, strRequestCreationDateTo);
                        String strRequestStatusId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_SEARCH_SELECT_REQUEST_STATUS);
                        dataHashMap.put(SOPInterfaceKey.INPUT_SEARCH_SELECT_REQUEST_STATUS, strRequestStatusId);

                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);

                        for (int j = 0; j < paramHashMap.size(); j++) {
                            try {
                                String tempStatusString = (String) paramHashMap.keySet().toArray()[j];
                                System.out.println("6976 tempStatusString isss " + tempStatusString);
                                if (!(paramHashMap.get(tempStatusString) instanceof String)) {
                                    continue;
                                }
                                String keyValue = (String) paramHashMap.get(tempStatusString);
                                //System.out.println("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
                                //Utility.logger.debug("wwwwwwwwww"+tempStatusString+"----------"+keyValue);
                                if (tempStatusString.startsWith(SOPInterfaceKey.INPUT_SELECT_REQUEST_STATUS)) {
                                    int strlength = SOPInterfaceKey.INPUT_SELECT_REQUEST_STATUS.length() + 1;
                                    strRequestId = tempStatusString.substring(strlength, tempStatusString.length());
                                    String requestOldStatusId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_OLD_REQUEST_STATUS + "_" + strRequestId);
                                    String requestDCMId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID + "_" + strRequestId);
                                    if (keyValue.compareTo(requestOldStatusId) != 0) {
                                        System.out.println("The key value isssssssssssssssssssss " + keyValue);
                                        RequestDAO.updateRequestStatus(con, strRequestId, keyValue, strUserID, requestDCMId, channelID);
                                        if (keyValue.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_DELETED) == 0) {
                                            Vector vecRequestDetail = RequestDAO.getRequestDetails(con, strRequestId);
                                            RequestModel requestModel = RequestDAO.getRequestById(con, strRequestId);
                                            String dcmId = "";
                                            if (requestModel != null) {
                                                dcmId = requestModel.getDcmId();
                                            }
                                            for (int f = 0; f < vecRequestDetail.size(); f++) {
                                                RequestDetailModel requestDetailModel = (RequestDetailModel) vecRequestDetail.get(f);
                                                String requestDetailSchemaProductId = requestDetailModel.getSchemaProductId();
                                                String requestDetailProductAmount = requestDetailModel.getProductAmount();
                                                int intRequestDetailProductAmount = Integer.parseInt(requestDetailProductAmount);
                                                String isQuotaItem = requestDetailModel.getIsQuotaItem();
                                                String productPrice = requestDetailModel.getProductPrice();
                                                float intProductPrice = Float.parseFloat(productPrice);
                                                float returnedQuota = 0;
                                                if (isQuotaItem.compareTo("1") == 0) {
                                                    returnedQuota = intProductPrice * intRequestDetailProductAmount;
                                                    String strReturnedQuota = returnedQuota + "";
                                                    QuotaDAO.updateDcmQuotaAfterRequest(con, dcmId, strReturnedQuota, "+");
                                                }
                                                RequestDAO.updateProductRequestLimit(con, requestDetailSchemaProductId, requestDetailProductAmount, "adding", dcmId, warehouseId);
                                                RequestDAO.updateProductStock(con, requestDetailSchemaProductId, requestDetailProductAmount, "adding", warehouseId);
                                            }
                                        }
                                        if (keyValue.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_PAID) == 0) {
                                            //System.out.println("Medhatttttttttttttttttttttt ");
                                            Vector vecRequestDetail = RequestDAO.getRequestDetails(con, strRequestId);
                                            RequestModel requestModel = RequestDAO.getRequestById(con, strRequestId);
                                            String dcmId = "";
                                            if (requestModel != null) {
                                                dcmId = requestModel.getDcmId();
                                            }
                                            for (int f = 0; f < vecRequestDetail.size(); f++) {
                                                RequestDetailModel requestDetailModel = (RequestDetailModel) vecRequestDetail.get(f);
                                                String requestDetailSchemaProductId = requestDetailModel.getSchemaProductId();
                                                String requestDetailProductAmount = requestDetailModel.getProductAmount();
                                                int intRequestDetailProductAmount = Integer.parseInt(requestDetailProductAmount);
                                                String isQuotaItem = requestDetailModel.getIsQuotaItem();
                                                String productPrice = requestDetailModel.getProductPrice();
                                                float intProductPrice = Float.parseFloat(productPrice);
                                                float returnedQuota = 0;
                                                if (isQuotaItem.compareTo("1") == 0) {
                                                    returnedQuota = intProductPrice * intRequestDetailProductAmount;
                                                    String strReturnedQuota = returnedQuota + "";
                                                    QuotaDAO.updateDcmQuotaAfterRequest(con, dcmId, strReturnedQuota, "-");
                                                }
                                                RequestDAO.updateProductRequestLimit(con, requestDetailSchemaProductId, requestDetailProductAmount, "subtracting", dcmId, warehouseId);
                                                RequestDAO.updateProductStock(con, requestDetailSchemaProductId, requestDetailProductAmount, "subtracting", warehouseId);
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Exception in line 6976");
                                e.printStackTrace();
                            }
                        }

                        Vector vetRequestList = RequestDAO.getPaidDeletedRequestsByFilter(con, strRequestId, strDcmId, strRequestCreationDateFrom, strRequestCreationDateTo, strRequestStatusId, channelID, warehouseId);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vetRequestList);
                        Vector vecRequestStatusList = RequestDAO.getAllRequestStatus(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecRequestStatusList);
                        String strLevel = "";
                        //if(channelID.compareTo("1")==0)
                        strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;
                        //else if (channelID.compareTo("2")==0)
                        //{strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;}
                        DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID, channelID, con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2, dcmDto);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ACTION, SOPInterfaceKey.ACTION_SEARCH_REQUEST_STATUS_LIST);
                        Vector warehouseVec = RequestDAO.getWarehouseByChannel(con, channelID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3, warehouseVec);
                        String channleName = SchemaDAO.getChannelName(channelID, con);
                        dataHashMap.put(SOPInterfaceKey.PAGE_HEADER, channleName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case VIEW_WAREHOUSE_LIST:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector vecWarehouse = RequestDAO.getAllWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecWarehouse);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case DELETE_WAREHOUSE_LIST:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String warehouseId = (String) paramHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
                        RequestDAO.deleteWarehouse(con, warehouseId);
                        Vector vecWarehouse = RequestDAO.getAllWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecWarehouse);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case CREATE_NEW_WAREHOUSE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case SAVE_NEW_WAREHOUSE:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        String warehouseName = (String) paramHashMap.get(SOPInterfaceKey.INPUT_TEXT_WAREHOUSE_NAME);
                        RequestDAO.insertNewWarehosue(con, warehouseName);
                        Vector vecWarehouse = RequestDAO.getAllWarehouse(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecWarehouse);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;



                case SHOW_USER_DELETE_CONTROL:
                    try {
                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector usersVector = UserDAO.getSOPUserPaymentDelete(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, usersVector);
                        Vector usersDeletePerVector = UserDAO.getUserPaymentPermission(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, usersDeletePerVector);
                    } catch (Exception objExp) {
                        objExp.printStackTrace();
                    }
                    break;



                case USER_DELETE_CONTROL:

                    try {

                        UserDAO.deleteUserPaymentPermission(con);
                        for (int j = 0; j < paramHashMap.size(); j++) {
                            try {
                                String tempString = (String) paramHashMap.keySet().toArray()[j];
                                System.out.println("7176 tempStatusString isss " + tempString);
                                if (!(paramHashMap.get(tempString) instanceof String)) {
                                    continue;
                                }
                                if (tempString.startsWith("check:")) {
                                    int userId = new Integer(tempString.split(":")[1].toString());
                                    UserDAO.updateUserPaymentPermission(con, userId, 1);
                                }
                            } catch (Exception e) {
                                System.out.println("Exception in line 7176");
                                e.printStackTrace();
                            }
                        }



                        /////////////////////////////// Data in the page ////////////////////////////

                        dataHashMap = new HashMap();
                        String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);
                        Vector usersVector = UserDAO.getSOPUserPaymentDelete(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, usersVector);
                        Vector usersDeletePerVector = UserDAO.getUserPaymentPermission(con);
                        dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, usersDeletePerVector);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;


                default:
                    Utility.logger.debug("Unknown action received: " + action);
            }


        } catch (Exception objExp) {
            objExp.printStackTrace();
        }

        return dataHashMap;
    }
    //////////////////////////
}