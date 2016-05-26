package com.mobinil.sds.web.interfaces.sfr;

public interface SFRInterfaceKey 
{
  

///////////////////////////////////////////////////////////////////////


  /*                 SFR Actions Keys                 /
  

*//////////////////////////////////////////////////////////////////////
  public static final String 

ACTION_UPLOAD_POS_CONTRACTS_COUNT_FORM_EXCEL = 

"upload_pos_contracts_count_form_excel";
  public static final String 

ACTION_UPLOAD_POS_CONTRACTS_COUNT_FORM_EXCEL_PROCESS = 

"upload_pos_contracts_count_form_excel_process";
  public static final String ACTION_UPDATE_BATCH_STATUS = 

"update_batch_status";
  public static final String ACTION_ADMIN_UPDATE_BATCH_STATUS = 

"admin_update_batch_status";
  public static final String ACTION_SFR_UPDATE_SHEET_STATUS = 

"sfr_update_sheet_status";
  public static final String ACTION_ADMIN_SFR_UPDATE_SHEET_STATUS = 

"admin_sfr_update_sheet_status";
  public static final String ACTION_ADMIN_CLOSE_SHEETS_PER_BATCH = 

"admin_close_sheets_per_batch";
  public static final String ACTION_ADMIN_REJECT_SHEETS_PER_BATCH = 

"admin_reject_sheets_per_batch";
  public static final String ACTION_BACK_TO_BATCH_PAGE = 

"back_to_batch_page";
  public static final String 

ACTION_GENERATE_UPLOAD_POS_CONTRACTS_COUNT_FORM_TEMPLATE = 

"generate_upload_pos_contracts_count_from_excel_template";
  public static final String ACTION_ADD_NEW_POS_BATCH = 

"add_new_pos_batch";
  public static final String 

ACTION_ASSIGN_POS_SHEET_CONTRACTS_SIM_NUMBER = 

"assign_pos_sheet_contracts_sim_number";
  public static final String ACTION_VIEW_SHEET_CONTRACTS_SIM_NUMBER = 

"view_sheet_contracts_sim_number";  
  public static final String 

ACTION_ADMIN_VIEW_SHEET_CONTRACTS_SIM_NUMBER = 

"admin_view_sheet_contracts_sim_number";  
  public static final String ACTION_VIEW_BATCH_SHEETS = 

"view_batch_sheets"; 
  public static final String ACTION_ADMIN_VIEW_BATCH_SHEETS = 

"admin_view_batch_sheets"; 
  public static final String ACTION_VIEW_POS_SHEET_HISTORY = 

"view_pos_sheet_history"; 
  public static final String ACTION_VIEW_POS_SIM_HISTROY = 

"view_pos_sim_history"; 
  public static final String ACTION_VIEW_SHEET_WARNINGS = 

"view_sheet_warnings";
  public static final String ACTION_DELETE_SHEET = "delete_sheet";
  
  
  public static final String ACTION_VIEW_POS_BATCHES = 

"view_pos_batches";
  public static final String ACTION_ADMIN_VIEW_POS_BATCHES = 

"admin_view_pos_batches";
  public static final String ACTION_VIEW_POS_PENDING_BATCHES = 

"view_pos_pending_batches";
  public static final String ACTION_ADMIN_VIEW_POS_PENDING_BATCHES = 

"admin_view_pos_pending_batches";
  public static final String ACTION_NEXT_POS_PENDING_BATCHES = 

"next_pos_pending_batches";
  public static final String ACTION_PREVIOUS_POS_PENDING_BATCHES = 

"previous_pos_pending_batches";
  public static final String ACTION_SEARCH_BATCHES = "search_batches";
  public static final String ACTION_NEXT_SEARCH_BATCHES = 

"next_search_batches";
  public static final String ACTION_PREVIOUS_SEARCH_BATCHES = 

"previous_search_batches";
  public static final String ACTION_ADMIN_SEARCH_BATCHES = 

"admin_search_batches";
  public static final String ACTION_NEXT_ADMIN_SEARCH_BATCHES = 

"next_admin_search_batches";
  public static final String ACTION_PREVIOUS_ADMIN_SEARCH_BATCHES = 

"previous_admin_search_batches";
  public static final String ACTION_SEARCH_SHEETS = "search_sheets";
  public static final String ACTION_ADMIN_SEARCH_SHEETS = 

"admin_search_sheets";
  public static final String ACTION_SEARCH_SHEET_STATUS = 

"search_sheet_status";
  public static final String ACTION_SEARCH_SIM_STATUS = 

"search_sim_status";
  public static final String ACTION_SAVE_SHEET_CONTRACTS_SIM_NUMBER = 

"save_sheet_contracts_sim_number";  
  public static final String 

ACTION_ADMIN_SAVE_SHEET_CONTRACTS_SIM_NUMBER = 

"admin_save_sheet_contracts_sim_number";  
  public static final String ACTION_SEARCH_PENDING_BATCHES = 

"search_pending_batches";
  public static final String ACTION_NEXT_PENDING_BATCHES = 

"next_pending_batches";
  public static final String ACTION_PREVIOUS_PENDING_BATCHES = 

"previous_pending_batches";
  public static final String ACTION_VIEW_PENDING_BATCH_SHEETS = 

"view_pending_batch_sheets";
  public static final String ACTION_EDIT_PENDING_SHEET = 

"edit_pending_sheet";
  public static final String ACTION_SAVE_PENDING_SHEET = 

"save_pending_sheet";
  public static final String ACTION_DELETE_PENDING_SHEET = 

"delete_pending_sheet";
  public static final String ACTION_ADMIN_PENDING_BATCHES_SELECT_AGENT 

= "admin_pending_batches_select_agent";
  public static final String ACTION_MANAGE_PENDING_BATCHES = 

"manage_pending_batches";
  public static final String ACTION_NEXT_MANAGE_PENDING_BATCHES = 

"next_manage_pending_batches";
  public static final String ACTION_PREVIOUS_MANAGE_PENDING_BATCHES = 

"previous_manage_pending_batches";
  public static final String 

ACTION_MANAGE_PENDING_BATCHES_UPDATE_STATUS = 

"manage_pending_batches_update_status";
  public static final String ACTION_MANAGE_PENDING_BATCHES_SEARCH = 

"manage_pending_batches_search";
  public static final String ACTION_MANAGE_PENDING_BATCHES_VIEW_SHEETS 

= "manage_pending_batches_view_sheets";
  public static final String ACTION_SFR_SEARCH_IMPORT_LOG = 

"sfr_search_import_log";
  public static final String ACTION_SFR_VIEW_IMPORT_LOG = 

"sfr_view_import_log";
  public static final String ACTION_SFR_MANAGE_SHEET_WARNINGS = 

"sfr_manage_sheet_warnings";
  public static final String ACTION_SFR_EDIT_SHEET_WARNING = 

"sfr_edit_sheet_warning";
  public static final String ACTION_SFR_SAVE_SHEET_WARNING = 

"sfr_save_sheet_warning";
  public static final String ACTION_SFR_ADD_SHEET_WARNING = 

"sfr_add_sheet_warning";
  public static final String ACTION_SFR_DELETE_SHEET_WARNING = 

"sfr_delete_sheet_warning";
  public static final String ACTION_ADMIN_VIEW_SHEET_DETAILS = 

"admin_view_sheet_details";
  public static final String ACTION_ADMIN_SHEET_DETAIL_UPDATE_STATUS = 

"admin_sheet_detail_update_status";
  public static final String ACTION_SHOW_ALL_BATCHES = 

"show_all_batches";
  public static final String ACTION_ADD_NEW_BATCH = "add_new_batch";
  public static final String ACTION_SAVE_NEW_BATCH= "save_new_batch";
  public static final String ACTION_ADD_NEW_SHEET = "add_new_sheet";
  public static final String ACTION_SAVE_NEW_SHEET  = "save_new_sheet";
  public static final String ACTION_VIEW_MONTHS_MANAGEMENT  = 

"view_months_management";
  public static final String ACTION_ADD_YEAR  = "add_year";
  public static final String ACTION_CLOSE_MONTH_COMMISSION  = 

"close_month_commission";
  public static final String ACTION_OPEN_MONTH_COMMISSION  = 

"open_month_commission";
  
  
  
  public static final String ACTION_CLOSE_MONTH_SFR = 

	  "close_month_sfr";
  public static final String ACTION_VIEW_MONTHS_MANAGEMENT_SFR  = 

	  "view_months_management_sfr";
  
  public static final String ACTION_ADD_YEAR_SFR  = "add_year_sfr";
  
///////////////////////////////////////////////////////////////////////
/*                 SFR Inputs                 /
  

*//////////////////////////////////////////////////////////////////////
  public static final String INPUT_HIDDEN_YEAR = "hidden_year";
  public static final String INPUT_HIDDEN_MONTH = "hidden_month";
  public static final String INPUT_HIDDEN_STATUS = "hidden_status";
  public static final String INPUT_HIDDEN_STATUS_ID = 

"hidden_status_id";
  
  public static final String INPUT_HIDDEN_ROW_NUM = "hidden_row_num";
  public static final String INPUT_HIDDEN_COUNT = "hidden_count";
  public static final String INPUT_HIDDEN_BATCH_ID = "hidden_batch_id";
  public static final String INPUT_HIDDEN_SHEET_SERIAL = 

"hidden_sheet_serial";
  public static final String INPUT_HIDDEN_SHEET_ID = "hidden_sheet_id";
  public static final String INPUT_SELECT_BATCH_STATUS_ID = 

"select_batch_status_id";
  public static final String INPUT_SELECT_SHEET_STATUS_ID = 

"select_sheet_status_id";
  public static final String INPUT_SELECT_SHEET_WARNING_ID = 

"select_sheet_warning_id";
  public static final String INPUT_HIDDEN_SHEET_STATUS_TYPE_ID = 

"hidden_sheet_status_type_id";
  public static final String INPUT_HIDDEN_BATCH_STATUS_TYPE_ID = 

"hidden_batch_status_type_id";
  public static final String INPUT_HIDDEN_SHEET_SIM_GROUP = 

"hidden_sheet_sim_group";
  public static final String INPUT_TEXT_SHEET_SIM_COUNT = 

"text_sheet_sim_count";
  public static final String INPUT_HIDDEN_BATCH_AGENT_ID = 

"hidden_batch_agent_id";
  public static final String INPUT_HIDDEN_STATUS_FLAG = 

"hidden_status_flag";
  public static final String INPUT_HIDDEN_FLAG = "hidden_flag";
  public static final String INPUT_YEAR = "YEAR";
  public static final String INPUT_HIDDEN_WARNING_TYPE_ID = 

"hidden_warning_type_id";
  public static final String INPUT_HIDDEN_SQL_OPERATION = 

"hidden_sql_operation";
  public static final String INPUT_TEXT_WARNING = "text_warning";
  public static final String INPUT_TEXT_AGENT_ID = "text_agent_id";
  public static final String INPUT_TEXT_SHEET_SERIAL = 

"text_sheet_serial";
  public static final String INPUT_TEXT_POS_CODE = "text_pos_code";
  public static final String INPUT_TEXT_POS_AGENT_CODE = 

"text_pos_agent_code";
  public static final String INPUT_TEXT_CONTRACTS_COUNT = 

"text_contracts_count";
  
  public static final String INPUT_IMPORT_LOG_DATE = 

"input_import_log_date";
  
  public static final String INPUT_SERACH_TEXT_BATCH_ID = 

"search_text_batch_id";
  public static final String INPUT_SERACH_SELECT_AGENT_ID = 

"search_select_agent_id";
  public static final String INPUT_SERACH_SELECT_BATCH_STATUS_ID = 

"search_select_batch_status_id";
  public static final String INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM 

= "search_text_batch_creation_date_from";
  public static final String INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO = 

"search_text_batch_creation_date_to";

  public static final String INPUT_SERACH_TEXT_SHEET_SERIAL = 

"search_text_sheet_serial";
  public static final String INPUT_SERACH_SELECT_POS_AGENT_ID = 

"search_select_pos_agent_id";
  public static final String INPUT_SERACH_TEXT_POS_CODE = 

"search_text_pos_code";
  public static final String INPUT_SERACH_SELECT_SHEET_STATUS_ID = 

"search_select_sheet_status_id";
  public static final String INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_FROM 

= "search_select_sheet_change_date_from";
  public static final String INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_TO = 

"search_select_sheet_change_date_to";

  public static final String INPUT_SERACH_TEXT_SIM_SERIAL = 

"search_text_sim_serial";
  public static final String INPUT_SERACH_SELECT_SIM_STATUS_ID = 

"search_select_sim_status_id";
  public static final String INPUT_SERACH_SELECT_USER_ID = 

"search_select_user_id";
  public static final String INPUT_SERACH_SELECT_CONTRACT_TYPE_ID = 

"search_select_contract_type_id";
  public static final String 

INPUT_SERACH_SELECT_SIM_ACTIVATION_DATE_FROM = 

"search_select_sim_activation_date_from";
  public static final String INPUT_SERACH_SELECT_SIM_ACTIVATION_DATE_TO 

= "search_select_sim_activation_date_to";

  public static final String SFR_HASHMAP_KEY_COLLECTION = 

"sfr_hashmap_key_collection";
  public static final String SFR_HASHMAP_KEY_ADDITIONAL_COLLECTION = 

"sfr_hashmap_key_additional_collection";
  

///////////////////////////////////////////////////////////////////////


//                   SFR Constans                 
  
//////////////////////////////////////////////////////////////////////
  public static final String CONST_SIM_STATUS_TYPE_ACCEPTED = "1";
  public static final String CONST_SIM_STATUS_TYPE_CLOSED = "2";
  public static final String CONST_SIM_STATUS_TYPE_VERIFIED = "3";
  public static final String CONST_SIM_STATUS_TYPE_REJECTED = "4";
  
  public static final String CONST_SHEET_STATUS_TYPE_ACCEPTED = "1";
  public static final String CONST_SHEET_STATUS_TYPE_CLOSED = "2";
  public static final String CONST_SHEET_STATUS_TYPE_VERIFIED = "3";
  public static final String CONST_SHEET_STATUS_TYPE_PENDING = "4";
  public static final String CONST_SHEET_STATUS_TYPE_REJECTED = "5";

  
  public static final String CONST_BATCH_STATUS_TYPE_ACCEPTED = "1";
  public static final String CONST_BATCH_STATUS_TYPE_CLOSED = "2";
  public static final String CONST_BATCH_STATUS_TYPE_VERIFIED = "3";
  public static final String CONST_BATCH_STATUS_TYPE_PENDING = "4";
  
  public static final String CONST_SHEET_SIM_SCANED_TOTAL = 

"sheet_sim_scaned_total";
  public static final String CONST_SHEET_SIM_SCANED_UNIQUE = 

"sheet_sim_scaned_unique";
  public static final String CONST_SHEET_SIM_SCANED_DUBLICATE = 

"sheet_sim_scaned_dublicate";
  public static final String CONST_SHEET_SIM_SCANED_ACCEPTED = 

"sheet_sim_scaned_accepted";
  public static final String CONST_SHEET_SIM_SCANED_REJECTED = 

"sheet_sim_scaned_rejected";
  public static final String 

ACTION_VIEW_CHANGE_BATCH_DATE="view_change_sfr_batch_date";
  public static final String 

ACTION_CHANGE_BATCH_DATE="change_sfr_batch_date";
  
  public static final String CONTROL_HIDDEN_BATCH_ID="hidden_batch_id";
  public static final String CONTROL_FROM_BATCH_ID="from_batch_id";
  public static final String CONTROL_TO_BATCH_ID="to_batch_id";
  public static final String GENERATE_BATCH_DATE="generate_batch_id";
  public static final String ACTION_CHANGE_BATCH_DATE_FAILED="failed";
  
  public static final String 

ACTION_VIEW_DELETE_BATCH="view_delete_sfr_batch";
  public static final String ACTION_DELETE_BATCH="delete_sfr_batch";
  
  
  public static final String CONTROL_HIDDEN_MSG="hidden_message";
}