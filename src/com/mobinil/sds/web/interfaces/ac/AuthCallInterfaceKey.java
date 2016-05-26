package com.mobinil.sds.web.interfaces.ac;

/**
 * AuthCallInterfaceKey interface holding all the keys used in the Authentication Call .
 *
 * @version	1.01 April 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

public interface AuthCallInterfaceKey 
{

  ////////////////////////////////////////////////////////////////////////
  /*                   Authentication Call Actions Keys                    /
  *//////////////////////////////////////////////////////////////////////
  public static final String ACTION_SHOW_SEARCH_BATCH_SCREEN_AUTHENTICATION_CALL = "show_batch_search_screen_authentication_call";
  public static final String ACTION_SHOW_SERACH_BATCH_BY_SHEET_AUTH_CALL="show_normal_batch_search_screen_authentication_call";
  public static final String ACTION_SEARCH_BATCH_AUTHENTICATION_CALL = "search_batch_authentication_call";
  public static final String ACTION_SEARCH_BATCH_AUTHENTICATION_CALL_BY_SHEET = "search_batch_authentication_call_by_sheet";
  public static final String ACTION_SHOW_CHOOSE_CONTRACTS_FOR_AUTHENTICATION_CALL_SCREEN = "show_choose_contracts_for_authentication_call_screen";
  public static final String ACTION_SHOW_CHOOSE_CONTRACTS_FOR_AUTHENTICATION_CALL_SCREEN_BY_SHEET = "show_choose_contracts_for_authentication_call_screen_by_sheet";
  public static final String ACTION_CHOOSE_CONTRACTS_FOR_AUTHENTICATION_CALL = "choose_contracts_for_authentication_call";
    public static final String ACTION_CHOOSE_CONTRACTS_FOR_AUTHENTICATION_CALL_BATCH_PERCENTAGE = "choose_contracts_for_authentication_call_batch_percentage";
  
    public static final String ACTION_SHOW_CONTRACT_DETAILS_FOR_AUTHENTICATION_CALL_BATCH_PERCENTAGE = "show_contract_details_for_authentication_call_batch_percentage";
  public static final String ACTION_SHOW_CONTRACT_DETAILS_FOR_AUTHENTICATION_CALL = "show_contract_details_for_authentication_call";
  public static final String ACTION_SHOW_CONTRACT_DETAILS_FOR_AUTHENTICATION_CALL_BY_SHEET = "show_contract_details_for_authentication_call_by_sheet";
  public static final String ACTION_UPDATE_CONTRACT_AUTH = "update_contract_authentication";
    public static final String ACTION_UPDATE_CONTRACT_AUTH_BATCH_PERCENTAGE = "update_contract_authentication_batch_percentage";
  public static final String ACTION_UPDATE_CONTRACT_AUTH_BY_SHEET= "update_contract_authentication_by_sheet";
  public static final String ACTION_UPDATE_BATCH_TO_COMMISSION ="update_batch_to_commission";
  public static final String ACTION_UPDATE_BATCH_TO_COMMISSION_BY_SHEET ="update_batch_to_commission_by_sheet";
  public static final String ACTION_SHOW_SHEET_DETAILS_FOR_AUTHENTICATION_CALL ="show_sheet_details_For_auth_call";

  ////////////////////////////////////////////////////////////////////////
  /*        Authentication Call Administration Actions Keys           /
  *//////////////////////////////////////////////////////////////////////
  public static final String ACTION_ADMIN_SHOW_SEARCH_BATCH_SCREEN_AUTHENTICATION_CALL = "admin_show_batch_search_screen_authentication_call";
  public static final String ACTION_AUTHENTICATION_CALL_BATCH_PERCENTAGE = "authentication_call_batch_percentage";
  public static final String ACTION_ADMIN_SEARCH_BATCH_AUTHENTICATION_CALL = "admin_search_batch_authentication_call";
  
  public static final String ACTION_ADMIN_SEARCH_BATCH_AUTHENTICATION_CALL_BATCH_PERCENTAGE = "admin_search_batch_authentication_call_batchPercentage";
  public static final String ACTION_ADMIN_SHOW_CHOOSE_CONTRACTS_FOR_AUTHENTICATION_CALL_SCREEN = "admin_show_choose_contracts_for_authentication_call_screen";
 
  public static final String ACTION_ADMIN_SHOW_CHOOSE_CONTRACTS_FOR_AUTHENTICATION_CALL_SCREEN_BATCH_PERCENTAGE= "show_choose_contracts_for_authentication_call_screen_batch_percentage";
    
  public static final String ACTION_ADMIN_CHOOSE_CONTRACTS_FOR_AUTHENTICATION_CALL = "admin_choose_contracts_for_authentication_call";
  public static final String ACTION_ADMIN_SHOW_CONTRACT_DETAILS_FOR_AUTHENTICATION_CALL = "admin_show_contract_details_for_authentication_call";
  public static final String ACTION_ADMIN_UPDATE_CONTRACT_STATUS = "admin_update_contract_status";
  public static final String ACTION_ADMIN_UPDATE_CONTRACT_AUTH = "admin_update_contract_authentication";
  public static final String ACTION_ADMIN_UPDATE_BATCH_TO_COMMISSION ="admin_update_batch_to_commission";
  

  ////////////////////////////////////////////////////////////////////////
  /*  Authentication Call user DCM Administration Actions Keys    /
  *//////////////////////////////////////////////////////////////////////
  public static final String ACTION_SHOW_USER_DCM_SCREEN_AUTHENTICATION_CALL = "show_user_dcm_screen_auth_call";
  public static final String ACTION_UPDATE_USER_DCM_AUTHENTICATION_CALL = "update_user_dcm_auth_call";

  public static final String ACTION_GENERATE_AUTHENTICATION_DATA_IMPORT_TEMPLATE = "generate_authentication_data_import_template";
  public static final String ACTION_SHEET_PERCENTAGE_IMPORT_TEMPLATE = "generate_sheet_percentage_import_template";
  public static final String ACTION_AUTHENTICATION_DATA_IMPORT = "authentication_data_import";
  public static final String ACTION_AUTHENTICATION_DATA_IMPORT_PROCESS = "authentication_data_import_process";
  public static final String ACTION_VIEW_CONTRACT_DATA = "view_contract_data";
  public static final String ACTION_SEARCH_CONTRACT_DATA ="search_contract_data";
  public static final String ACTION_UPDATE_CONTRACT = "update_contract";
  ////////////////////////////////////////////////////////////////////////
  /*                          Controls in the JSPs                              /
  *///////////////////////////////////////////////////////////////////////
  public static final String CONTROL_SELECT_AUTH_CALL_STATUS = "select_auth_call_status";
  public static final String CONTROL_SELECT_AUTH_CALL_OWNER_STATUS = "select_auth_call_owner_status";
  public static final String CONTROL_SELECT_AUTH_CALL_POS_STATUS = "select_auth_call_pos_status";
  public static final String CONTROL_RADIO_VIEW_CONTRACTS = "radio_view_contracts";
  public static final String CONTROL_SELSCT_NAME_PREFIX = "select_";
  public static final String CONTROL_TEXT_SHEET_TYPE_ID = "sheet_type_id";
  public static final String CONTROL_TEXT_SHEET_AUTH_PERCENTAGE="sheet_percentage";
  public static final String CONTROL_HIDDEN_SHEET_CONTRACT = "sheet_contract";
  public static final String CONTROL_HIDDEN_SHEET_CONTRACT_OLD_STATUS = "SHEET_CONTRACT_OLD_STATUS";
  public static final String INPUT_TEXT_CONTRACT_DIAL_NUMBER = "text_contract_dial_number";
  public static final String INPUT_HIDDEN_DIAL_NUMBER = "hidden_dial_number";

  ////////////////////////////////////////////////////////////////////////
  /*     Modes for choosing contracts for authentication Call       /
  *//////////////////////////////////////////////////////////////////////
  public static final String VIEW_ALL_AUTH = "view_all_auth";
  public static final String VIEW_ALL = "view_all";
  public static final String ADD_RANDOM_CONTRACTS = "add_random_contracts";
  public static final String ADD_CONTRACT = "add_contract";
  public static final String ADD_SHEET = "add_sheet";

  ////////////////////////////////////////////////////////////////////////
  /*            Objects passed from the handdler to JSPs               /
  *///////////////////////////////////////////////////////////////////////
  public static final String OBJ_USER_WARNING = "user_warning";
  public static final String OBJ_SYSTEM_WARNING = "system_warning";
  public static final String OBJ_CONTRACT_WARNING = "contract_warning";
  public static final String OBJ_AUTH_CALL_STATUS = "auth_call_status";
  public static final String OBJ_AUTH_CALL_OWNER_STATUS = "auth_call_owner_status";
  public static final String OBJ_AUTH_CALL_POS_STATUS = "auth_call_pos_status";
  public static final String OBJ_AUTH_CALL_STATUS_BY_CONTRACT = "auth_call_status_by_contract";
  public static final String OBJ_POS_QUESTION_FLAG = "pos_question_flag";
}