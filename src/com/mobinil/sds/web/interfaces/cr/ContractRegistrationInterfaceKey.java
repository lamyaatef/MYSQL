package com.mobinil.sds.web.interfaces.cr;

/**
 * ContractRegistrationInterfaceKey interface holding all the keys used in the Contract Registeration.
 *
 * @version 1.01 April 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 


public interface ContractRegistrationInterfaceKey 
{

  public static final String CACH_OBJ_WARNING_STATUS ="cach_obj_warning_status";
  public static final String CACH_OBJ_WARNING_SUGGESTED_STATUS ="cach_obj_warning_suggested_status";
  public static final String CACH_OBJ_SHEET_STATUS ="cach_obj_sheet_status";
  public static final String CACH_OBJ_SHEET_TYPES ="cach_obj_sheet_types";
  public static final String CACH_OBJ_DCM_LIST_CONTRACT_REGISTRATION ="cach_obj_dcm_list_contract_registration";
  public static final String CACH_OBJ_DCM_LIST_AUTHENTICATION_CALL ="cach_obj_dcm_list_authentication_call";
  public static final String CACH_OBJ_DCM_LIST_ALL ="cach_obj_dcm_list_all";
  public static final String CACH_OBJ_ACTIVE_CONTRACT_WARNINGS ="cach_obj_active_contract_warnings";
  public static final String CACH_OBJ_CONTRACT_WARNINGS ="cach_obj_contract_warnings";
  public static final String CACH_OBJ_CONTRACT_SYSTEM_WARNINGS ="cach_obj_contract_system_warnings";
  public static final String CACH_OBJ_BATCH_TYPE ="cach_obj_batch_type";
  public static final String CACH_OBJ_BATCH_STATUS_TYPE_CONTRACT_VERIFICATION ="cach_obj_batch_status_type_contract_verification";
  public static final String CACH_OBJ_BATCH_STATUS_TYPE_AUTHENTICATION_CALL ="cach_obj_batch_status_type_authentication_call";
  ////////////////////////////////////////////////////////////////////////
  /*                 Contract Registeration Actions Keys                 /
  *//////////////////////////////////////////////////////////////////////
  public static final String SHOW_SHEET_HISTORY_EXCEL ="PROCESS_SHEET_HISTORY_EXCEL";

  public static final String SHEET_REIMPORTED_TABLE ="SHEET_REIMPORTED_TABLE";

  
   public static final String SHOW_CONTRACT_HISTORY_EXCEL ="PROCESS_contract_HISTORY_EXCEL";
  
  public static final String IMPORT_PHASE= "IMPORT";
  public static final String DELIVERY_VERIFICATION_PHASE = "DELIVERY VERIFICATION";
  public static final String CONTRACT_VERIFICATION_PHASE = "CONTRACT VERIFICATION";
  public static final String AUTHINTICATION_CALL_PHASE = "AUTHINTICATION CALL";
  public static final String COMMISSION_PHASE ="COMMISSION";
  
  
  public static final String ACTION_IMPORT_FILE= "import_file";
  public static final String ACTION_GENERATE_FILE = "generate_file";
  public static final String ACTION_GENERATE_BATCH = "generate_batch";
  public static final String ACTION_SHOW_GEN_BATCH_SCREEN= "VIEW_GEN_BATCH";
  public static final String ACTION_SHOW_ADMIN_GEN_BATCH_SCREEN= "VIEW_ADMIN_GEN_BATCH";
  public static final String ACTION_PRINT_BATCHS = "print_batchs";

  public static final String ACTION_VIEW_GEN_BATCH_FRANCHISE = "view_gen_batch_franchise";
  public static final String ACTION_VIEW_GEN_BATCH_COMPLEMANTRY = "view_gen_batch_complemantry";
  public static final String ACTION_SHOW_IMPORT_LOG_SEARCH_SCREEN_FRANCHISE = "show_import_log_search_screen_franchise";
  public static final String ACTION_SHOW_IMPORT_LOG_SEARCH_SCREEN_COMPLEMANTRY = "show_import_log_search_screen_complemantry";
  
  public static final String ACTION_SHOW_BATCH_SEARCH_SCREEN_DELIVERY_FRANCHISE = "show_batch_search_screen_delivery_franchise";
  public static final String ACTION_SHOW_BATCH_SEARCH_SCREEN_DELIVERY_COMPLEMANTRY = "show_batch_search_screen_delivery_complemantry";
  public static final String ACTION_SHOW_BATCH_SEARCH_SCREEN_CONTRACT_VERIFICATION_FRANCHISE = "show_batch_search_screen_contract_verification_franchise";
  public static final String ACTION_SHOW_BATCH_SEARCH_SCREEN_CONTRACT_VERIFICATION_COMPLEMANTRY = "show_batch_search_screen_contract_verification_complemantry";
  public static final String ACTION_VIEW_ADMIN_GEN_BATCH_FRANCHISE = "view_admin_gen_batch_franchise";
  public static final String ACTION_VIEW_ADMIN_GEN_BATCH_COMPLEMANTRY = "view_admin_gen_batch_complemantry";
  
  public static final String ACTION_ADMIN_SHOW_BATCH_SEARCH_SCREEN_CONTRACT_VERIFICATION_BY_SHEET_FRANCHISE = "admin_show_batch_search_screen_contract_verification_by_sheet_franchise";
  public static final String ACTION_ADMIN_SHOW_BATCH_SEARCH_SCREEN_CONTRACT_VERIFICATION_BY_SHEET_COMPLEMANTRY = "admin_show_batch_search_screen_contract_verification_by_sheet_complemantry";
  public static final String ACTION_SHOW_USER_DCM_SCREEN_FRANCHISE = "show_user_dcm_screen_franchise";
  public static final String ACTION_SHOW_USER_DCM_SCREEN_COMPLEMANTRY = "show_user_dcm_screen_complemantry";
  public static final String ACTION_MANAGE_BATCHES_OPENED_FOR_ARCHIVING_FRANCHISE = "manage_batches_opened_for_archiving_franchise";
  public static final String ACTION_MANAGE_BATCHES_OPENED_FOR_ARCHIVING_COMPLEMANTRY = "manage_batches_opened_for_archiving_complemantry";

  public static final String ACTION_ADMIN_SHOW_BATCH_SEARCH_SCREEN_CONTRACT_VERIFICATION_FRANCHISE = "admin_show_batch_search_screen_contract_verification_franchise";
  public static final String ACTION_ADMIN_SHOW_BATCH_SEARCH_SCREEN_CONTRACT_VERIFICATION_COMPLEMANTRY = "admin_show_batch_search_screen_contract_verification_complemantry";

  ////////////////////////////////////////////////////////////////////////
  /*                  Actions Keys of the Delivery phase                  /
  *//////////////////////////////////////////////////////////////////////
  public static final String ACTION_SHOW_SEARCH_BATCH_SCREEN_DELIVERY = "show_batch_search_screen_delivery";
  public static final String ACTION_SEARCH_BATCH_DELIVERY = "search_batch_delivery";
  public static final String ACTION_SHOW_BATCH_DETAILS = "show_batch_details";
  public static final String ACTION_UPDATE_SHEET_STATUS = "update_sheet_status";
  public static final String ACTION_APPLY_SHEET_STATUS = "apply_sheet_status";
  public static final String ACTION_SHOW_SHEET_DETAILS ="show_sheet_details";
  public static final String ACTION_UPDATE_SHEET_DETAILS_STATUS = "update_sheet_details_status";

  public static final String ACTION_SHOW_MANAGE_AUTHENTICATION_PERCENTAGE ="update_sheet_details_status";

  ////////////////////////////////////////////////////////////////////////
  /*         Actions Keys of the contrat verification phase            /
  *//////////////////////////////////////////////////////////////////////
  public static final String ACTION_SHOW_SEARCH_BATCH_SCREEN_CONTRACT_VERIFICATION = "show_batch_search_screen_contract_verification";
  public static final String ACTION_SEARCH_BATCH_CONTRACT_VERIFICATION = "search_batch_contract_verification";
  public static final String ACTION_AUTHINTICATE_BATCH_CONTRACT_VERIFICATION = "authinticate_batch_contract_verification";
  public static final String ACTION_SHOW_BATCH_DETAILS_CONTRACT_VERIFICATION = "show_batch_details_contract_verification";
  public static final String ACTION_OPEN_BATCH_FOR_VERIFICATION = "open_batch_for_verification";
  public static final String ACTION_VERIFY_SHEET = "verify_sheet";
  public static final String ACTION_SHOW_SHEET_CONTRACT_VERIFY_SCREEN = "show_sheet_contract_verify_screen";
  public static final String ACTION_VERIFY_CONTRACT = "verify_contract";
  public static final String ACTION_SHOW_CONTRACT_DETAILS = "show_contract_details";
  public static final String ACTION_UPDATE_CONTRACT = "update_contract";

  ////////////////////////////////////////////////////////////////////////
  /*Actions Keys of administrating the contrat verification phase /
  *//////////////////////////////////////////////////////////////////////
  public static final String ACTION_ADMIN_GENERATE_BATCH = "admin_generate_batch";
  public static final String ACTION_ADMIN_SHOW_SEARCH_BATCH_SCREEN_CONTRACT_VERIFICATION = "admin_show_batch_search_screen_contract_verification";
  public static final String ACTION_ADMIN_SHOW_SEARCH_BATCH_SCREEN_CONTRACT_VERIFICATION_BY_SHEET = "admin_show_batch_search_screen_contract_verification_by_sheet";
  public static final String ACTION_ADMIN_SEARCH_BATCH_CONTRACT_VERIFICATION = "admin_search_batch_contract_verification";
  public static final String ACTION_ADMIN_SEARCH_BATCH_CONTRACT_VERIFICATION_BY_SHEET = "admin_search_batch_contract_verification_by_sheet";
  public static final String ACTION_ADMIN_AUTHINTICATE_BATCH_CONTRACT_VERIFICATION = "admin_authinticate_batch_contract_verification";
  public static final String ACTION_ADMIN_SHOW_BATCH_DETAILS_CONTRACT_VERIFICATION = "admin_show_batch_details_contract_verification";
  public static final String ACTION_ADMIN_SHOW_BATCH_DETAILS_CONTRACT_VERIFICATION_BY_SHEET = "admin_show_batch_details_contract_verification_by_sheet";
  public static final String ACTION_ADMIN_OPEN_BATCH_FOR_VERIFICATION = "admin_open_batch_for_verification";
  public static final String ACTION_ADMIN_VERIFY_SHEET = "admin_verify_sheet";
  public static final String ACTION_ADMIN_SHOW_SHEET_CONTRACT_VERIFY_SCREEN = "admin_show_sheet_contract_verify_screen";
  public static final String ACTION_ADMIN_UPDATE_CONTRACT_STATUS_CONTRACT_VERIFY_SCREEN = "admin_update_contract_status_contract_verify_screen";
  public static final String ACTION_ADMIN_UPDATE_CONTRACT_STATUS_CONTRACT_VERIFY_SCREEN_BY_SHEET = "admin_update_contract_status_contract_verify_screen_by_sheet";
  public static final String ACTION_ADMIN_VERIFY_CONTRACT = "admin_verify_contract";
  public static final String ACTION_ADMIN_SHOW_CONTRACT_DETAILS = "admin_show_contract_details";
  public static final String ACTION_ADMIN_UPDATE_CONTRACT = "admin_update_contract";
  public static final String ACTION_ADMIN_RECHECK_CONTRACT_SYSTEM_WARNING = "admin_recheck_contract_system_warning";
  public static final String ACTION_RECHECK_CONTRACT_SYSTEM_WARNING = "recheck_contract_system_warning";
  
  public static final String ACTION_SHEET_PERCENTAGE_IMPORT = "sheet_percentage_import";
  public static final String ACTION_SHEET_PERCENTAGE_IMPORT_PROCESS = "sheet_percentage_import_process";
  public static final String ACTION_DCM_CHANGE_STATUS_IMPORT = "dcm_change_status_import";
  public static final String ACTION_DCM_CHANGE_STATUS_IMPORT_PROCESS = "dcm_change_status_import_process";
  ////////////////////////////////////////////////////////////////////////
  /*                     Actions Keys of sheet history                      /
  *//////////////////////////////////////////////////////////////////////
  public static final String ACTION_SHOW_SHEET_HISTORY_SCREEN = "show_sheet_history_screen";
  public static final String ACTION_SHOW_SHEET_HISTORY = "show_sheet_history";

  ////////////////////////////////////////////////////////////////////////
  /*                   Actions Keys of contract history                    /
  *//////////////////////////////////////////////////////////////////////
  public static final String ACTION_SHOW_CONTRACT_HISTORY_SCREEN = "show_contract_history_screen";
  public static final String ACTION_SHOW_CONTRACT_HISTORY = "show_contract_history";
  public static final String ACTION_SHOW_CONTRACT_HISTORY_TELL_VERIFY ="show_contract_history_until_verify";
  public static final String ACTION_SHOW_CONTRACT_HISTORY_TELL_AUTH ="show_contract_history_until_auth";
  

  ////////////////////////////////////////////////////////////////////////
  /*                      Actions Keys of Import Log                        /
  *//////////////////////////////////////////////////////////////////////
  public static final String ACTION_SHOW_IMPORT_LOG_SEARCH_SCREEN = "show_import_log_search_screen";
  public static final String ACTION_SEARCH_IMPORT_LOG = "search_import_log";

  ////////////////////////////////////////////////////////////////////////
  /*                    Actions Keys of sheet warning                      /
  *//////////////////////////////////////////////////////////////////////
  public static final String ACTION_SHOW_SHEETS_WARNINGS_SCREEN = "show_sheets_warnings_screen";
  public static final String ACTION_UPDATE_SHEET_WARNINGS_STATUSES = "update_sheet_warnings_statuses";
  public static final String ACTION_EDIT_SHEET_WARNING = "edit_sheet_warning";
  public static final String ACTION_UPDATE_SHEET_WARNING = "update_sheet_warning";
  public static final String ACTION_NEW_SHEET_WARNING = "new_sheet_warning";
  public static final String ACTION_ADD_SHEET_WARNING = "add_sheet_warning";

  ////////////////////////////////////////////////////////////////////////
  /*                  Actions Keys of contract warning                    /
  *//////////////////////////////////////////////////////////////////////
  public static final String ACTION_SHOW_CONTRACTS_WARNINGS_SCREEN = "show_contracts_warnings_screen";
  public static final String ACTION_SHOW_CONTRACTS_SYSTEM_WARNINGS_SCREEN = "show_contracts_system_warnings_screen";
  public static final String ACTION_UPDATE_CONTRACT_WARNINGS_STATUSES = "update_contract_warnings_statuses";
  public static final String ACTION_UPDATE_CONTRACT_SYSTEM_WARNINGS_STATUSES = "update_contract_system_warnings_statuses";
  public static final String ACTION_EDIT_CONTRACT_WARNING = "edit_contract_warning";
  public static final String ACTION_UPDATE_CONTRACT_WARNING = "update_contract_warning";
  public static final String ACTION_NEW_CONTRACT_WARNING = "new_contract_warning";
  public static final String ACTION_ADD_CONTRACT_WARNING = "add_contract_warning";

  ////////////////////////////////////////////////////////////////////////
  /*              Actions Keys of user DCM management                /
  *//////////////////////////////////////////////////////////////////////
  public static final String ACTION_SHOW_USER_DCM_SCREEN = "show_user_dcm_screen";
  public static final String ACTION_UPDATE_USER_DCM = "update_user_dcm";

  ////////////////////////////////////////////////////////////////////////
  /*              Actions Keys for Archiving Process                /
  *//////////////////////////////////////////////////////////////////////
  public static final String ACTION_ADMIN_OPEN_ARCHIVING_PROCESS = "admin_open_archiving_process";
  public static final String ACTION_ADMIN_OPEN_ARCHIVING_PROCESS_BY_SHEET = "admin_open_archiving_process_by_sheet";
  public static final String ACTION_OPEN_ARCHIVING_PROCESS = "open_archiving_process";
  public static final String ACTION_MANAGE_BATCHES_OPENED_FOR_ARCHIVING = "manage_batches_opened_for_archiving";
  public static final String ACTION_IMPORT_EXCEL_FROM_ARCHIVING = "import_excel_from_archiving";
  public static final String ACTION_GENERATE_EXCEL_FROM_ARCHIVING_TEMPLATE = "generate_excel_from_archiving_template";
  public static final String ACTION_IMPORT_EXCEL_FROM_ARCHIVING_PROCESS = "import_excel_from_archiving_process";
  public static final String ACTION_SEARCH_MANAGE_BATCH_OPENED_FOR_ARCHIVING = "search_manage_batch_opened_for_archiving";
  public static final String ACTION_GENERATE_EXCEL_FOR_ACCEPTED_VERIFY = "generate_excel_for_accepted_verify";
  public static final String ACTION_GENERATE_EXCEL_FOR_REJECTED_VERIFY = "generate_excel_for_rejected_verify";

  public static final String ACTION_CR_CHECK_SIM_IN_LCS = "cr_check_sim_in_lcs";
  public static final String ACTION_CR_CHECK_SIM_IN_LCS_PROCESS = "cr_check_sim_in_lcs_process";
  
  
  public static final String ACTION_IMPORT_EXCEL_FROM_ARCHIVING_PROCESS_REJECT = "import_excel_from_archiving_process_reject";
  
  public static final String ACTION_IMPORT_EXCEL_FROM_ARCHIVING_REJECT=  "import_excel_from_archiving_reject";
  
 
  ////////////////////////////////////////////////////////////////////////
  /*         Actions Keys of user Sheet Type management            /
  *//////////////////////////////////////////////////////////////////////
// Key for the list all sheet types action
  public static final String ACTION_LIST_SHEET_TYPES = "list_sheet_types";
// Key for the update sheet types action
  public static final String ACTION_UPDATE_SHEET_TYPES_STATUSES = "update_sheet_types_statuses";
// Key for the update privilege action
  public static final String ACTION_NEW_SHEET_TYPE = "new_sheet_type";
// Key for the update privilege action
  public static final String ACTION_EDIT_SHEET_TYPE = "edit_sheet_type";
// Key for the update privilege action
  public static final String ACTION_ADD_SHEET_TYPE = "add_sheet_type";
// Key for the update privilege action
  public static final String ACTION_UPDATE_SHEET_TYPE = "update_sheet_type";

  public static final String ACTION_CREATE_SHEET_SERIAL ="create_sheet_serial";
  public static final String ACTION_SHOW_CREATE_SHEET_SERIAL ="show_create_sheet_serial";
  public static final String ACTION_SELECT_SHEET_SERIAL_TO_SET_PERCENTAGE ="select_sheet_serial_to_set_percentage";
  public static final String ACTION_SHEET_SERIAL_LOCAL_AUTH_PERCENTAGE_LIST = "sheet_serial_local_auth_percentage_list";
  public static final String ACTION_SAVE_SHEET_LOCAL_PERCENTAGE = "save_sheet_local_percentage";
  
  public static final String ACTION_SHOW_UPDATE_SHEET_SERIAL ="show_update_sheet_serial";
  public static final String ACTION_VIEW_SHEET_SERIAL ="show_view_sheet_serial";
  public static final String ACTION_UPDATE_SHEET_SERIAL_ASSIGNMETN = "update_sheet_serial_assignment";

  public static final String ACTION_SHOW_LCS_PRODUCT_MAPPING = "show_lcs_product_mapping";
  public static final String ACTION_EDIT_LCS_PRODUCT_MAPPING = "edit_lcs_product_mapping";
  public static final String ACTION_ADD_LCS_PRODUCT_MAPPING = "add_lcs_product_mapping";
  public static final String ACTION_UPDATE_LCS_PRODUCT_MAPPING = "update_lcs_product_mapping";
  public static final String ACTION_SAVE_LCS_PRODUCT_MAPPING = "save_lcs_product_mapping";
  public static final String ACTION_SHOW_LCS_DCM_MAPPING = "show_lcs_dcm_mapping";
  public static final String ACTION_EDIT_LCS_DCM_MAPPING = "edit_lcs_dcm_mapping";
  public static final String ACTION_ADD_LCS_DCM_MAPPING = "add_lcs_dcm_mapping";
  public static final String ACTION_UPDATE_LCS_DCM_MAPPING = "update_lcs_dcm_mapping";
  public static final String ACTION_SAVE_LCS_DCM_MAPPING = "save_lcs_dcm_mapping";
  

  public static final String GENERATE_BATCH_FORM_NAME = "genBatchForm";
  public static final String GENERATE_BATCH_DATE = "DateField";
  public static final String GENERATE_BATCH_DCM_ID  ="dcmIdField";
  public static final String ACTION_VIEW_CHANGE_BATCH_DATE="view_change_cr_batch_date";
  public static final String ACTION_CHANGE_BATCH_DATE="change_cr_batch_date";
  public static final String ACTION_CHANGE_BATCH_DATE_FAILED="change_batch_date_failed";
  
  public static final String ACTION_VIEW_DELETE_BATCH="view_delete_cr_batch";
  public static final String ACTION_DELETE_BATCH="delete_cr_batch";

  
  public static final String ACTION_VIEW_MONTHS_MANAGEMENT_CR = "cr_view_months_management_cr_page";
  public static final String ACTION_ADD_YEAR_CR   = "cr_add_year_cr";
  public static final String ACTION_CLOSE_MONTH_COMMISSION_CR   = "cr_close_month_cr";
  public static final String ACTION_OPEN_MONTH_COMMISSION_COMMISSION   = "cr_open_month_commission_commission";

  
  public static final String ACTION_VIEW_MONTHS_MANAGEMENT  = "cr_view_months_management";
  public static final String ACTION_ADD_YEAR  = "cr_add_year";
  public static final String ACTION_CLOSE_MONTH_COMMISSION  = "cr_close_month_commission";
  public static final String ACTION_OPEN_MONTH_COMMISSION  = "cr_open_month_commission";

  ////////////////////////////////////////////////////////////////////////
  /*                      Controls Keys used in JSPs                         /
  *//////////////////////////////////////////////////////////////////////
  
  public static final String INPUT_YEAR = "cr_year";
  public static final String INPUT_HIDDEN_YEAR = "hidden_year";
  public static final String INPUT_HIDDEN_MONTH = "hidden_month";
  public static final String INPUT_HIDDEN_STATUS = "hidden_status";
  public static final String INPUT_HIDDEN_STATUS_ID = "hidden_status_id";
  public static final String INPUT_HIDDEN_MESSAGE = "hidden_message";
  
  public static final String CONTROL_HIDDEN_PHASE_NAME = "phase";
  public static final String CONTROL_HIDDEN_BATCH_ID = "batch_id";
  public static final String CONTROL_FROM_BATCH_ID = "from_batch_id";
  public static final String CONTROL_TO_BATCH_ID = "to_batch_id";
  public static final String CONTROL_HIDDEN_SHEET_ID = "sheet_id";
  public static final String CONTROL_HIDDEN_SHEET_NAME = "sheet_name";
  public static final String CONTROL_HIDDEN_SHEET_CONTRACT_COUNT = "sheet_contract_count";
  public static final String CONTROL_HIDDEN_SHEET_GUNNED_CONTRACT_COUNT = "sheet_gunned_contract_count";
  public static final String CONTROL_HIDDEN_SHEET_STATUS = "sheet_status";
  public static final String CONTROL_HIDDEN_SHEET_TYPE = "sheet_type";
  public static final String CONTROL_HIDDEN_SHEET_TYPE_ID = "sheet_type_id";
  public static final String CONTROL_HIDDEN_CONTRACT_ID = "contract_id";
  public static final String CONTROL_HIDDEN_WARNING_ID = "warning_id";
  public static final String CONTROL_HIDDEN_DCM_ID = "dcm_id";
  public static final String CONTROL_SELECT_DCM_ID = "inputselect_dcm_id";
  public static final String CONTROL_SELECT_BATCH_TYPE = "inputselect_batch_type";
  public static final String CONTROL_SELECT_BATCH_STATUS_TYPE = "inputselect_batch_status_type";
  public static final String CONTROL_SELSCT_NAME_PREFIX = "select_";
  public static final String CONTROL_SELECT_CONTRACT_STATUS="SELECT_CONTRACT_STATUS";
  public static final String CONTROL_SELECT_APPLYED_USER_WARNINGS="applied_user_warnings";
  public static final String CONTROL_SELECT_USER_WARNINGS="user_warnings";
  public static final String CONTROL_SELECT_SYSTEM_WARNINGS="system_warnings";
  public static final String CONTROL_TEXT_CONTRACT_SIM_NO = "contract_sim_no";
  public static final String CONTROL_TEXT_BATCH_LAST_STATUS_ID = "batch_last_status_id";
  public static final String CONTROL_TEXT_CONTRACT_LAST_STATUS_ID = "contract_last_status_id";
  public static final String CONTROL_TEXT_SHEET_SERIAL_NO = "sheet_serial_no";
  public static final String CONTROL_TEXT_SHEET_POS_CODE ="SHEET_POS_CODE";
  public static final String CONTROL_TEXT_SHEET_SUPER_DEALER_CODE ="SHEET_SUPER_DEALER_CODE";
  public static final String CONTROL_TEXT_SHEET_TYPE_NAME = "sheet_type_name";
  public static final String CONTROL_TEXT_SHEET_TYPE_DESC = "sheet_type_desc";
  public static final String CONTROL_TEXT_SHEET_TYPE_AUTH_PERCENT = "sheet_type_auth_percent";
  public static final String CONTROL_TEXT_WARNING_NAME = "warning_name";
  public static final String CONTROL_TEXT_WARNING_DESC = "warning_desc";
  public static final String CONTROL_TEXT_DCM_NAME = "dcm_name";
  public static final String CONTROL_CHECK_BOX_POS_QUESTION = "check_pos_question";
  public static final String CONTROL_CHECK_BOX_DISCARD_UNREAL_UNREACHABLE = "check_discard_unreal_unreachable";
  public static final String CONTROL_BUTTON_NAME_EDIT_WARNING_PREFIX = "edit_warning_";
  public static final String CONTROL_BATCH_DATE = "batch_date";
  public static final String CONTROL_IMPORT_LOG_DATE = "import_log_date";
  public static final String CONTROL_SELECT_WARNING_SUGGESTED_STATUS ="warning_suggested_status";
  public static final String CONTROL_TEXT_SUGGESTED_WARNING_STATUS = "text_suggested_warning_status";

  public static final String CONTROL_HIDDEN_CONTRACT_LAST_STATUS = "hidden_contract_last_status";

  public static final String CONTROL_HIDDEN_CHANNEL_ID = "hidden_channel_id";
  
  public static final String CONTROL_HIDDEN_ARCHIVING_FLAG = "hidden_archiving_flag";
  public static final String CONST_ARCHIVING_FLAG_NOT_ENTERED_ARCHIVING_PROCESS = "0";
  public static final String CONST_ARCHIVING_FLAG_ENTERED_ARCHIVING_PROCESS = "1";
  public static final String CONST_ARCHIVING_FLAG_FINISHED_ARCHIVING_PROCESS = "2";

  public static final String CONST_CONTRACT_STATUS_ACCEPTED_VERIFY = "ACCEPTED VERIFY";
  public static final String CONST_CONTRACT_STATUS_REJECTED_VERIFY = "REJECTED VERIFY";
  ////////////////////////////////////////////////////////////////////////
  /*                   Objects Keys used in Handlers                       /
  *//////////////////////////////////////////////////////////////////////
  public static final String OBJ_CONTRACT_HAS_PREVIOUS_WARNING="CONTRACT_PREVIOUS_WARNING_FLAG";
  public static final String OBJ_SHEET_AUTH_STATISTIC_MODEL = "OBJ_SHEET_AUTH_STATISTIC_MODEL";
  public static final String OBJ_USER_WARNING = "USER_WARNING_VECTOR";
  public static final String OBJ_SYSTEM_WARNING = "system_warning";
  public static final String OBJ_SHEET_WARNING = "SHEET_WARNING_VECTOR";
  public static final String OBJ_CONTRACT_WARNING = "CONTRACT_WARNING_OBJECT";
  public static final String OBJ_DCM_DTO = "OBJ_DCM_DTO";
  public static final String OBJ_BATCH_DTO = "OBJ_BATCH_DTO";
  public static final String OBJ_BATCH_TYPE_DTO = "OBJ_BATCH_TYPE_DTO";
  public static final String OBJ_BATCH_STATUS_TYPE_DTO = "OBJ_BATCH_STATUS_TYPE_DTO";
  public static final String OBJ_SHEET_STATUS_TYPE = "sheet_Status_Type";
  public static final String OBJ_CONTRAT_STATUS_TYPE = "contract_Status_Type";
  public static final String OBJ_SHEET_TYPE = "sheet_Type";

  public static final String OBJ_REPORT_IMPORT_ARCHIVING = "report_import_archiving";
  public static final String OBJ_SYS_REPORT_IMPORT_ARCHIVING = "sys_report_import_archiving";
  ////////////////////////////////////////////////////////////////////////////////
  /*                   Cash Objects Keys used to enhance performance       /
  *//////////////////////////////////////////////////////////////////////////////
  public static final String CASH_OBJ_CONTRACT_VERIFICATION_WARNINGS = "cash_obj_contract_verification_warnings";
  public static final String CASH_OBJ_CONTRACT_AUTHENTICATION_WARNINGS = "cash_obj_contract_authentication_warnings";
  

// SHEET SERIAL DCM ASSIGN
  public static final String CONTROL_SELECT_SHEET_ASSIGN_TYPE = "SHEET_ASSIGN_TYPE";
  public static final String CONTROL_TEXT_SHEET_SERIAL_INPUT ="sheet_serial";
  public static final String CONTROL_TEXT_BATCH_ID_INPUT ="batch_id";
  public static final String CONTROL_TEXT_SHEET_LOCAL_PERCENTAGE ="sheet_local_percentage";
  public static final String CONTROL_TEXT_SHEET_SERIAL_FROM ="FROM_sheet_serial";
  public static final String CONTROL_TEXT_SHEET_SERIAL_TO ="TO_sheet_serial";
  public static final String OBJ_SELECTED_DCM_ID = "selected_dcm";
  public static final String OBJ_SELECTED_ASSIGN_TYPE = "selected_assign_type";

  public static final String INPUT_HIDDEN_PRODUCT_ID = "hidden_product_id";
  public static final String INPUT_HIDDEN_INVENTORY_ITEM_TYPE = "hidden_inventory_item_type";
  public static final String INPUT_SEARCH_SELECT_PRODUCT_NAME = "search_select_product_name";
  public static final String INPUT_TEXT_INVENTORY_ITEM_TYPE = "inventory_item_type";
  public static final String INPUT_HIDDEN_LCS_NAME = "hidden_lcs_name";
  public static final String INPUT_HIDDEN_DCM_CODE = "hidden_dcm_code";
  public static final String INPUT_HIDDEN_DCM_NAME = "hidden_dcm_name";
  public static final String INPUT_HIDDEN_DCM_LCS_NAME_ID = "hidden_dcm_lcs_name_id";
  public static final String INPUT_TEXT_DCM_CODE = "dcm_code";
  public static final String INPUT_TEXT_DCM_NAME = "dcm_name";
  public static final String INPUT_TEXT_LCS_NAME = "lcs_name";

    public static final String OBJL_SHEET_SERIAL_INPUT = "inputed_sheet_serial";
    public static final String OBJ_SHEET_SERIAL_FROM = "inputed_sheet_serial_from";
    public static final String OBJ_SHEET_SERIAL_TO = "inputed_sheet_serial_to";

  public static final String RANGE_INPUT_TYPE_ASSIGN_SHEET ="RANGE";
  public static final String SHEET_INPUT_TYPE_ASSIGN_SHEET ="SHEET";
  public static final String SERVER_MESSAGE = "server_message";

  public static final String SHEET_ASSIGNMENT_ERROR_MESSAGE_ANOTHER_DCM ="Sheet is assigned to another DCM";
  public static final String SHEET_ASSIGNMENT_ERROR_MESSAGE_SAME_DCM ="Sheet is assigned to this DCM";
  public static final String SHEET_ASSIGNMENT_SHEET_DOES_NOT_EXIST_ERROR_MESSAGE= "Sheet Does Not Exist";
  public static final String SHEET_ASSIGNMENT_CONFIRMATION_MESSAGE ="Sheet Assignment Succeeded";

  public static final String SHEET_RANGE_ASSIGNMENT_ERROR_MESSAGE_ANOTHER_DCM ="Some Sheets in this range are assigned to another DCM";
  public static final String SHEET_RANGE_ASSIGNMENT_ERROR_MESSAGE_SAME_DCM ="Some Sheets in this range are assigned to this DCM";

  public static final String SHEET_DCM_ASSIGNMENT_VECTOR = "sheet_dcm_vector_assignment";

    //public static final String dbConnectionStringLCS = "jdbc:oracle:thin:@10.1.135.36:1521:test";
    //public static final String dbUserNameLCS = "lcs";
    //public static final String dbPasswordLCS = "lcs01";
    public static final String LCS_ISSUED_CODE = "4";
    public static final String LCS_STATUS_FIELD = "Current_status";
    
    // CR_LCS_Notification_List Jsp
    //Actions
    public static final String ACTION_VIEW_LCS_NOTIFICATION_LIST = "view_lcs_notification_list";
    public static final String ACTION_NEW_EDIT_EMAIL = "new_edit_email";
    public static final String CONTROL_HIDDEN_EMAIL_ID = "hidden_email_id";
    public static final String ACTION_DELETE_EMAIL = "delete_email";
    public static final String VEC_EMAILS = "vec_emails";
    
    

    public static final String CR_HASHMAP_KEY_COLLECTION = "cr_hashmap_key_collection";
    public static final String CR_HASHMAP_KEY_ADDITIONAL_COLLECTION = "cr_hashmap_key_additional_collection";


public static final String HASHMAP_KEY_ADDITIONAL_COLLECTION = "hashmap_key_additional_collection";

public static final String DOWNLOAD_EXCEL_LOG_ID = "download_excel_log_id";

public static final String ACTION_DOWNLOAD_EXCEL_LOG = "action_download_excel_log";
   
}
